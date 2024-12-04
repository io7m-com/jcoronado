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

import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanHostAllocatorType;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanInstanceExtensionInfoType;
import com.io7m.jcoronado.api.VulkanInstanceProviderType;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanMissingRequiredVersionException;
import com.io7m.jcoronado.api.VulkanVersion;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLExtensionsRegistry;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLHostAllocatorProxy;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLInstance;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLSupportException;
import com.io7m.jcoronado.lwjgl.internal.VulkanStrings;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.VkLayerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.io7m.jcoronado.api.VulkanChecks.checkReturnCode;

/**
 * A LWJGL-based instance provider.
 */

public final class VulkanLWJGLInstanceProvider
  implements VulkanInstanceProviderType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLInstanceProvider.class);

  private static final VulkanVersion VULKAN_13 =
    VulkanVersion.of(1, 3, 0);

  private final MemoryStack initialStack;
  private final VulkanLWJGLExtensionsRegistry extensions;

  private VulkanLWJGLInstanceProvider(
    final MemoryStack in_stack,
    final VulkanLWJGLExtensionsRegistry in_extensions)
  {
    this.initialStack =
      Objects.requireNonNull(in_stack, "stack");
    this.extensions =
      Objects.requireNonNull(in_extensions, "extensions");
  }

  /**
   * @return A new instance provider
   */

  public static VulkanInstanceProviderType create()
  {
    return new VulkanLWJGLInstanceProvider(
      MemoryStack.create(),
      new VulkanLWJGLExtensionsRegistry()
    );
  }

  /**
   * @return The extension registry for this provider
   */

  VulkanLWJGLExtensionsRegistry extensionRegistry()
  {
    return this.extensions;
  }

  @Override
  public String providerName()
  {
    return "com.io7m.jcoronado.lwjgl";
  }

  @Override
  public String providerVersion()
  {
    final var pack = this.getClass().getPackage();
    final var version = pack.getImplementationVersion();
    return version == null ? "0.0.0" : version;
  }

  @Override
  public VulkanVersion findSupportedInstanceVersion()
  {
    return VulkanVersions.decode(VK.getInstanceVersionSupported());
  }

  @Override
  public Map<String, VulkanExtensionProperties> extensions()
    throws VulkanException
  {
    try (var stack = this.initialStack.push()) {
      final var count = new int[1];

      checkReturnCode(
        VK10.vkEnumerateInstanceExtensionProperties(
          (CharSequence) null,
          count,
          null),
        "vkEnumerateInstanceExtensionProperties");

      final var size = count[0];
      if (size == 0) {
        return Map.of();
      }

      final var instanceExtensions =
        VkExtensionProperties.malloc(size, stack);

      checkReturnCode(
        VK10.vkEnumerateInstanceExtensionProperties(
          (CharSequence) null,
          count,
          instanceExtensions),
        "vkEnumerateInstanceExtensionProperties");

      final HashMap<String, VulkanExtensionProperties> availableExtensions =
        new HashMap<>(size);

      for (var index = 0; index < size; ++index) {
        instanceExtensions.position(index);
        final var extension =
          VulkanExtensionProperties.of(
            instanceExtensions.extensionNameString(),
            instanceExtensions.specVersion());
        availableExtensions.put(extension.name(), extension);
      }

      return availableExtensions;
    }
  }

  @Override
  public Map<String, VulkanLayerProperties> layers()
    throws VulkanException
  {
    try (var stack = this.initialStack.push()) {
      final var count = new int[1];

      checkReturnCode(
        VK10.vkEnumerateInstanceLayerProperties(
          count,
          null),
        "vkEnumerateInstanceLayerProperties");

      final var size = count[0];
      if (size == 0) {
        return Map.of();
      }

      final var layersBuffer =
        VkLayerProperties.malloc(size, stack);

      checkReturnCode(
        VK10.vkEnumerateInstanceLayerProperties(
          count,
          layersBuffer),
        "vkEnumerateInstanceLayerProperties");

      final HashMap<String, VulkanLayerProperties> layers = new HashMap<>(size);
      for (var index = 0; index < size; ++index) {
        layersBuffer.position(index);

        final var layer =
          VulkanLayerProperties.of(
            layersBuffer.layerNameString(),
            layersBuffer.descriptionString(),
            layersBuffer.specVersion(),
            layersBuffer.implementationVersion());

        layers.put(layer.name(), layer);
      }
      return layers;
    }
  }

  @Override
  public VulkanInstanceType createInstance(
    final VulkanInstanceCreateInfo info,
    final Optional<VulkanHostAllocatorType> allocator)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");
    Objects.requireNonNull(allocator, "allocator");

    final var enabledLayers =
      info.enabledLayers();
    final var enabledExtensions =
      info.enabledExtensions();

    final var requestedVersion =
      VulkanVersions.decode(info.applicationInfo().vulkanAPIVersion());

    if (LOG.isDebugEnabled()) {
      LOG.debug("Requested Vulkan version: {}", requestedVersion);
      LOG.debug("Required Vulkan version:  {}", VULKAN_13);
    }

    /*
     * We require Vulkan 1.3+ as various extensions such as
     * VK_KHR_synchronization2 were moved to core.
     */

    if (requestedVersion.compareTo(VULKAN_13) < 0) {
      throw new VulkanMissingRequiredVersionException(
        requestedVersion,
        VULKAN_13,
        Optional.empty()
      );
    }

    if (LOG.isDebugEnabled()) {
      enabledLayers
        .forEach(layer -> LOG.debug("Enabling layer: {}", layer));
      enabledExtensions
        .forEach(extension -> LOG.debug("Enabling extension: {}", extension));
    }

    try (var stack = this.initialStack.push()) {
      final var enableLayersPtr =
        VulkanStrings.stringsToPointerBuffer(stack, enabledLayers);
      final var enableExtensionsPtr =
        VulkanStrings.stringsToPointerBuffer(stack, enabledExtensions);

      final var applicationInfo =
        info.applicationInfo();
      final var appNamePtr =
        stack.ASCII(applicationInfo.applicationName());
      final var engineNamePtr =
        stack.ASCII(applicationInfo.engineName());

      final var vkApplicationInfo =
        VkApplicationInfo.malloc(stack)
          .sType(VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO)
          .pNext(0L)
          .pApplicationName(appNamePtr)
          .applicationVersion(applicationInfo.applicationVersion())
          .pEngineName(engineNamePtr)
          .engineVersion(applicationInfo.engineVersion())
          .apiVersion(applicationInfo.vulkanAPIVersion());

      LOG.trace("vkApplicationInfo: {}", vkApplicationInfo);

      final var vkInstanceCreateInfoExtensions =
        this.createInstanceCreateInfoExtensions(stack, info.extensionInfo());

      final var vkInstanceCreateInfo =
        VkInstanceCreateInfo.malloc(stack)
          .sType(VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
          .pNext(vkInstanceCreateInfoExtensions)
          .flags(0)
          .pApplicationInfo(vkApplicationInfo)
          .ppEnabledLayerNames(enableLayersPtr)
          .ppEnabledExtensionNames(enableExtensionsPtr);

      LOG.trace("vkInstanceCreateInfo: {}", vkInstanceCreateInfo);

      final var instancePtr =
        stack.mallocPointer(1);

      final var allocatorProxy =
        VulkanLWJGLHostAllocatorProxy.create(stack, allocator);

      final var err =
        VK10.vkCreateInstance(
          vkInstanceCreateInfo,
          allocatorProxy.callbackBuffer(),
          instancePtr);
      checkReturnCode(err, "vkCreateInstance");

      final var instance =
        new VkInstance(instancePtr.get(), vkInstanceCreateInfo);

      LOG.debug("Created instance: {}", instance);

      final var enabled =
        this.extensions.ofNames(info.enabledExtensions());

      final var supported =
        this.findSupportedInstanceVersion();

      if (supported.compareTo(requestedVersion) < 0) {
        VK10.vkDestroyInstance(instance, allocatorProxy.callbackBuffer());

        throw new VulkanMissingRequiredVersionException(
          requestedVersion,
          VULKAN_13,
          Optional.of(supported)
        );
      }

      return new VulkanLWJGLInstance(
        instance,
        this.extensions,
        enabled,
        allocatorProxy,
        this.findSupportedInstanceVersion(),
        VulkanVersions.decode(info.applicationInfo().vulkanAPIVersion())
      );
    }
  }

  private long createInstanceCreateInfoExtensions(
    final MemoryStack stack,
    final List<VulkanInstanceExtensionInfoType> infos)
    throws VulkanException
  {
    if (infos.isEmpty()) {
      return 0L;
    }

    final var handlers =
      this.extensions.instanceExtensionHandlers();

    final var chain =
      new ArrayList<Map.Entry<Long, Long>>();

    var next = 0L;
    INFO_LOOP: for (int infoIndex = infos.size() - 1; infoIndex >= 0; --infoIndex) {
      final var info = infos.get(infoIndex);
      for (final var handler : handlers) {
        if (handler.supports(info.getClass())) {
          final var newNext = handler.transform(stack, info, next);
          chain.add(Map.entry(newNext, next));
          LOG.trace(
            "Handler {} returned 0x{}",
            handler.getClass().getName(),
            Long.toUnsignedString(newNext, 16)
          );
          next = newNext;
          continue INFO_LOOP;
        }
      }
      throw new VulkanLWJGLSupportException(
        "No instance extension info handler is available for values of type %s"
          .formatted(info.getClass())
      );
    }

    for (final var entry : chain) {
      LOG.trace(
        "Extension chain: 0x{} -> 0x{}",
        Long.toUnsignedString(entry.getKey().longValue(), 16),
        Long.toUnsignedString(entry.getValue().longValue(), 16)
      );
    }

    return next;
  }
}
