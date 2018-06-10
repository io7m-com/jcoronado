/*
 * Copyright © 2018 Mark Raynsford <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jcoronado.lwjgl;

import com.io7m.jcoronado.api.VulkanApplicationInfo;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanInstanceProviderType;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.VkLayerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanChecks.checkReturnCode;

/**
 * A LWJGL-based instance provider.
 */

public final class VulkanLWJGLInstanceProvider implements
  VulkanInstanceProviderType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLInstanceProvider.class);

  private final MemoryStack initial_stack;

  private VulkanLWJGLInstanceProvider(
    final MemoryStack in_stack)
  {
    this.initial_stack = Objects.requireNonNull(in_stack, "stack");
  }

  /**
   * @return A new instance provider
   */

  public static VulkanInstanceProviderType create()
  {
    return new VulkanLWJGLInstanceProvider(MemoryStack.create());
  }

  @Override
  public Map<String, VulkanExtensionProperties> extensions()
    throws VulkanException
  {
    try (MemoryStack stack = this.initial_stack.push()) {
      final int[] count = new int[1];

      checkReturnCode(
        VK10.vkEnumerateInstanceExtensionProperties(
          (CharSequence) null,
          count,
          null),
        "vkEnumerateInstanceExtensionProperties");

      final int size = count[0];
      if (size == 0) {
        return Map.of();
      }

      final VkExtensionProperties.Buffer instance_extensions =
        VkExtensionProperties.mallocStack(size, stack);

      checkReturnCode(
        VK10.vkEnumerateInstanceExtensionProperties(
          (CharSequence) null,
          count,
          instance_extensions),
        "vkEnumerateInstanceExtensionProperties");

      final HashMap<String, VulkanExtensionProperties> extensions =
        new HashMap<>(size);

      for (int index = 0; index < size; ++index) {
        instance_extensions.position(index);
        final VulkanExtensionProperties extension =
          VulkanExtensionProperties.of(
            instance_extensions.extensionNameString(),
            instance_extensions.specVersion());
        extensions.put(extension.name(), extension);
      }

      return extensions;
    }
  }

  @Override
  public Map<String, VulkanLayerProperties> layers()
    throws VulkanException
  {
    try (MemoryStack stack = this.initial_stack.push()) {
      final int[] count = new int[1];

      checkReturnCode(
        VK10.vkEnumerateInstanceLayerProperties(
          count,
          null),
        "vkEnumerateInstanceLayerProperties");

      final int size = count[0];
      if (size == 0) {
        return Map.of();
      }

      final VkLayerProperties.Buffer layers_buffer =
        VkLayerProperties.mallocStack(size, stack);

      checkReturnCode(
        VK10.vkEnumerateInstanceLayerProperties(
          count,
          layers_buffer),
        "vkEnumerateInstanceLayerProperties");

      final HashMap<String, VulkanLayerProperties> layers = new HashMap<>(size);
      for (int index = 0; index < size; ++index) {
        layers_buffer.position(index);

        final VulkanLayerProperties layer =
          VulkanLayerProperties.of(
            layers_buffer.layerNameString(),
            layers_buffer.descriptionString(),
            layers_buffer.specVersion(),
            layers_buffer.implementationVersion());

        layers.put(layer.name(), layer);
      }
      return layers;
    }
  }

  @Override
  public VulkanInstanceType createInstance(
    final VulkanInstanceCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    LOG.debug("creating instance");

    try (MemoryStack stack = this.initial_stack.push()) {
      final Set<String> enable_layers = info.enabledLayers();
      final Set<String> enable_extensions = info.enabledExtensions();

      final PointerBuffer enable_layers_ptr =
        VulkanStrings.stringsToPointerBuffer(stack, enable_layers);
      final PointerBuffer enable_extensions_ptr =
        VulkanStrings.stringsToPointerBuffer(stack, enable_extensions);

      final VulkanApplicationInfo app_info =
        info.applicationInfo();
      final ByteBuffer app_name_ptr =
        stack.ASCII(app_info.applicationName());
      final ByteBuffer engine_name_ptr =
        stack.ASCII(app_info.engineName());

      final VkApplicationInfo application_info =
        VkApplicationInfo.mallocStack(stack)
          .sType(VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO)
          .pNext(0L)
          .pApplicationName(app_name_ptr)
          .applicationVersion(app_info.applicationVersion())
          .pEngineName(engine_name_ptr)
          .engineVersion(app_info.engineVersion())
          .apiVersion(app_info.vulkanAPIVersion());

      LOG.trace("application_info: {}", application_info);

      final VkInstanceCreateInfo instance_info =
        VkInstanceCreateInfo.mallocStack(stack)
          .sType(VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
          .pNext(0L)
          .flags(0)
          .pApplicationInfo(application_info)
          .ppEnabledLayerNames(enable_layers_ptr)
          .ppEnabledExtensionNames(enable_extensions_ptr);

      LOG.trace("instance_info: {}", instance_info);

      final PointerBuffer instance_ptr = stack.mallocPointer(1);
      final int err =
        VK10.vkCreateInstance(instance_info, null, instance_ptr);
      checkReturnCode(err, "vkCreateInstance");

      final VkInstance instance =
        new VkInstance(instance_ptr.get(), instance_info);

      LOG.debug("created instance: {}", instance);
      return new VulkanLWJGLInstance(instance);
    }
  }
}
