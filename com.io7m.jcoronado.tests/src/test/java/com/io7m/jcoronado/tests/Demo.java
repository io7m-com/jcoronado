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

package com.io7m.jcoronado.tests;

import com.io7m.jcoronado.api.VulkanApplicationInfo;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanExtensions;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanInstanceProviderType;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanLayers;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVulkan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag.VK_QUEUE_GRAPHICS_BIT;

public final class Demo
{
  private static final Logger LOG = LoggerFactory.getLogger(Demo.class);

  private Demo()
  {

  }

  public static void main(
    final String[] args)
    throws VulkanException
  {
    // GLFWErrorCallback.createPrint().set();

    if (!GLFW.glfwInit()) {
      throw new IllegalStateException(
        "Unable to initialize GLFW");
    }

    if (!GLFWVulkan.glfwVulkanSupported()) {
      throw new IllegalStateException(
        "Cannot find a compatible Vulkan installable client driver (ICD)");
    }

    final VulkanInstanceProviderType instances =
      VulkanLWJGLInstanceProvider.create();

    final Map<String, VulkanExtensionProperties> available_extensions =
      instances.extensions();
    final Map<String, VulkanLayerProperties> available_layers =
      instances.layers();

    final Set<String> required_layers =
      Set.of("VK_LAYER_LUNARG_standard_validation");
    final Set<String> required_extensions =
      requiredGLFWExtensions();

    available_extensions.forEach(Demo::showAvailableExtension);
    available_layers.forEach(Demo::showAvailableLayer);
    required_extensions.forEach(Demo::showRequiredExtension);
    required_layers.forEach(Demo::showRequiredLayer);

    final Set<String> enable_extensions =
      VulkanExtensions.filterRequiredExtensions(
        available_extensions,
        Set.of(),
        required_extensions);

    final Set<String> enable_layers =
      VulkanLayers.filterRequiredLayers(
        available_layers,
        Set.of(),
        required_layers);

    final VulkanInstanceCreateInfo create_info =
      VulkanInstanceCreateInfo.builder()
        .setApplicationInfo(
          VulkanApplicationInfo.of(
            "com.io7m.jcoronado.tests.Demo",
            VulkanVersions.encode(0, 0, 1),
            "com.io7m.jcoronado.tests",
            VulkanVersions.encode(0, 0, 1),
            VulkanVersions.encode(1, 0, 0)))
        .setEnabledExtensions(enable_extensions)
        .setEnabledLayers(enable_layers)
        .build();

    try (final VulkanInstanceType instance =
           instances.createInstance(create_info)) {

      final List<VulkanPhysicalDeviceType> physical_devices =
        instance.physicalDevices();

      for (final VulkanPhysicalDeviceType physical_device : physical_devices) {
        LOG.debug("physical device: {}", physical_device);

        physical_device.layers().forEach(Demo::showAvailableLayer);
        physical_device.layers().forEach((name, props) -> {
          try {
            physical_device.extensions(Optional.of(name))
              .forEach(Demo::showAvailableExtension);
          } catch (final VulkanException e) {
            LOG.error("error fetching extensions: ", e);
          }
        });

        final Optional<VulkanQueueFamilyProperties> queue_option =
          physical_device.queueFamilies()
            .stream()
            .filter(queue -> queue.queueFlags().contains(VK_QUEUE_GRAPHICS_BIT))
            .findFirst();

        if (!queue_option.isPresent()) {
          throw new IllegalStateException(
            "No queue available with " + VK_QUEUE_GRAPHICS_BIT);
        }

        final VulkanQueueFamilyProperties queue_family = queue_option.get();

        final VulkanLogicalDeviceQueueCreateInfo queue_info =
          VulkanLogicalDeviceQueueCreateInfo.builder()
            .setQueueCount(1)
            .setQueueFamilyIndex(queue_family.queueFamilyIndex())
            .setQueuePriorities(1.0f)
            .build();

        final VulkanLogicalDeviceCreateInfo logical_create_info =
          VulkanLogicalDeviceCreateInfo.builder()
            .addQueueCreateInfos(queue_info)
            .build();

        try (final VulkanLogicalDeviceType device =
               physical_device.createLogicalDevice(logical_create_info)) {
          LOG.debug("logical device: {}", device);

          device.queues().forEach(queue -> {
            LOG.debug("queue: {}", queue.queueFamilyProperties());
          });
        }
      }
    }
  }

  private static void showRequiredLayer(
    final String name)
  {
    LOG.debug("required layer: {}", name);
  }

  private static void showRequiredExtension(
    final String name)
  {
    LOG.debug("required extension: {}", name);
  }

  private static void showAvailableLayer(
    final String name,
    final VulkanLayerProperties layer)
  {
    LOG.debug(
      "available layer: {}: {}, specification 0x{} implementation 0x{}",
      layer.name(),
      layer.description(),
      Integer.toUnsignedString(layer.specificationVersion(), 16),
      Integer.toUnsignedString(layer.implementationVersion(), 16));
  }

  private static void showAvailableExtension(
    final String name,
    final VulkanExtensionProperties extension)
  {
    LOG.debug(
      "available extension: {} 0x{}",
      extension.name(),
      Integer.toUnsignedString(extension.version(), 16));
  }

  private static Set<String> requiredGLFWExtensions()
  {
    final PointerBuffer glfw_required_extensions =
      GLFWVulkan.glfwGetRequiredInstanceExtensions();

    final HashSet<String> required =
      new HashSet<>(glfw_required_extensions.capacity());
    for (int index = 0; index < glfw_required_extensions.capacity(); ++index) {
      glfw_required_extensions.position(index);
      required.add(glfw_required_extensions.getStringASCII());
    }
    return required;
  }
}
