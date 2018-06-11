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
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.extensions.api.VulkanExtKHRSurfaceType;
import com.io7m.jcoronado.extensions.api.VulkanSurfaceCapabilitiesKHR;
import com.io7m.jcoronado.extensions.api.VulkanSurfaceFormatKHR;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
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

  private static final GLFWErrorCallback GLFW_ERROR_CALLBACK =
    GLFWErrorCallback.createPrint();

  private Demo()
  {

  }

  public static void main(
    final String[] args)
    throws VulkanException
  {
    GLFW_ERROR_CALLBACK.set();

    try {
      final long window = createWindow();

      /*
       * Create an instance provider.
       */

      final VulkanInstanceProviderType instances = VulkanLWJGLInstanceProvider.create();
      LOG.debug("instance provider: {} {}", instances.providerName(), instances.providerVersion());

      final Map<String, VulkanExtensionProperties> available_extensions =
        instances.extensions();
      final Map<String, VulkanLayerProperties> available_layers =
        instances.layers();

      /*
       * Determine which extensions are required by the window system, and which layers are
       * required.
       */

      final Set<String> required_layers =
        Set.of("VK_LAYER_LUNARG_standard_validation");
      final Set<String> required_extensions =
        requiredGLFWExtensions();

      available_extensions.forEach(Demo::showAvailableExtension);
      available_layers.forEach(Demo::showAvailableLayer);
      required_extensions.forEach(Demo::showRequiredExtension);
      required_layers.forEach(Demo::showRequiredLayer);

      /*
       * Filter the available extensions and layers based on the requirements expressed above.
       * If any required extensions are not available, an exception is raised.
       */

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

      /*
       * Create a new instance.
       */

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

      try (final VulkanInstanceType instance = instances.createInstance(create_info)) {

        /*
         * Get access to the VK_KHR_surface extension in order to produce a renderable
         * surface from the created window.
         */

        final VulkanExtKHRSurfaceType khr_surface_ext =
          instance.findEnabledExtension("VK_KHR_surface", VulkanExtKHRSurfaceType.class)
            .orElseThrow(() -> new IllegalStateException("Missing VK_KHR_surface extension"));

        final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface =
          khr_surface_ext.surfaceFromWindow(instance, window);

        /*
         * List the available physical devices and pick the "best" one.
         */

        final List<VulkanPhysicalDeviceType> physical_devices = instance.physicalDevices();

        try (final VulkanPhysicalDeviceType physical_device =
               pickPhysicalDevice(khr_surface_ext, surface, physical_devices)
                 .orElseThrow(() -> new IllegalStateException("No suitable device found"))) {

          LOG.debug("physical device: {}", physical_device);

          /*
           * Show the preferred formats, and the capabilities of the surface.
           */

          final List<VulkanSurfaceFormatKHR> formats =
            khr_surface_ext.surfaceFormats(physical_device, surface);

          formats.forEach(format -> LOG.debug(
            "preferred surface format: {} {}",
            format.format(),
            format.colorSpace()));

          final VulkanSurfaceCapabilitiesKHR capabilities =
            khr_surface_ext.surfaceCapabilities(physical_device, surface);

          LOG.debug("surface capabilities: {}", capabilities);

          /*
           * We know that the selected physical device has a graphics queue family, and a queue
           * family for presentation. We don't know if these are the same queue families or not.
           */

          final VulkanQueueFamilyProperties graphics_queue_props =
            physical_device.queueFamilies()
              .stream()
              .filter(queue -> queue.queueFlags().contains(VK_QUEUE_GRAPHICS_BIT))
              .findFirst()
              .orElseThrow(() -> new IllegalStateException("Could not find graphics queue"));

          final VulkanQueueFamilyProperties presentation_queue_props =
            khr_surface_ext.surfaceSupport(physical_device, surface)
              .stream()
              .findFirst()
              .orElseThrow(() -> new IllegalStateException("Could not find presentation queue"));

          /*
           * Put together the information needed to create a logical device. In this case,
           * all that is really needed is to specify the creation of one or more queues.
           */

          final VulkanLogicalDeviceCreateInfo.Builder logical_device_info_builder =
            VulkanLogicalDeviceCreateInfo.builder();

          logical_device_info_builder.addQueueCreateInfos(
            VulkanLogicalDeviceQueueCreateInfo.builder()
              .setQueueCount(1)
              .setQueueFamilyIndex(graphics_queue_props.queueFamilyIndex())
              .setQueuePriorities(1.0f)
              .build());

          if (graphics_queue_props.queueFamilyIndex() != presentation_queue_props.queueFamilyIndex()) {
            logical_device_info_builder.addQueueCreateInfos(
              VulkanLogicalDeviceQueueCreateInfo.builder()
                .setQueueCount(1)
                .setQueueFamilyIndex(presentation_queue_props.queueFamilyIndex())
                .setQueuePriorities(1.0f)
                .build());
          }

          try (final VulkanLogicalDeviceType device =
                 physical_device.createLogicalDevice(logical_device_info_builder.build())) {
            LOG.debug("logical device: {}", device);

            final VulkanQueueType graphics_queue =
              device.queue(graphics_queue_props.queueFamilyIndex(), 0)
                .orElseThrow(() -> new IllegalStateException("Could not find graphics queue"));
            final VulkanQueueType presentation_queue =
              device.queue(presentation_queue_props.queueFamilyIndex(), 0)
                .orElseThrow(() -> new IllegalStateException("Could not find presentation queue"));

            LOG.debug("graphics queue: {}", graphics_queue);
            LOG.debug("presentation queue: {}", presentation_queue);
          }
        }
      }
    } finally {
      GLFW_ERROR_CALLBACK.close();
    }
  }

  private static Optional<VulkanPhysicalDeviceType> pickPhysicalDevice(
    final VulkanExtKHRSurfaceType khr_surface_ext,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface,
    final List<VulkanPhysicalDeviceType> devices)
    throws VulkanException
  {
    /*
     * Consider a device usable if it has a queue capable of graphics operations, and a queue
     * capable of presentation operations (these do not need to be the same queue).
     *
     * Then, arbitrarily pick the first one matching these requirements. Real programs should
     * give the user control over the selection.
     */

    try {
      return devices.stream()
        .filter(Demo::physicalDeviceHasGraphicsQueue)
        .filter(device -> physicalDeviceHasPresentationQueue(khr_surface_ext, surface, device))
        .findFirst();
    } catch (final VulkanUncheckedException e) {
      throw e.getCause();
    }
  }

  private static boolean physicalDeviceHasPresentationQueue(
    final VulkanExtKHRSurfaceType khr_surface_ext,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface,
    final VulkanPhysicalDeviceType device)
  {
    /*
     * Determine which, if any, of the available queues are capable of "presentation". That
     * is, rendering to a surface that will appear onscreen.
     */

    try {
      LOG.debug(
        "checking device \"{}\" for presentation support",
        device.properties().name());

      final List<VulkanQueueFamilyProperties> queues_presentable =
        khr_surface_ext.surfaceSupport(device, surface);
      return !queues_presentable.isEmpty();
    } catch (final VulkanException e) {
      throw new VulkanUncheckedException(e);
    }
  }

  private static boolean physicalDeviceHasGraphicsQueue(
    final VulkanPhysicalDeviceType device)
  {
    try {
      LOG.debug(
        "checking device \"{}\" for graphics queue support",
        device.properties().name());

      final List<VulkanQueueFamilyProperties> queues = device.queueFamilies();
      return queues.stream().anyMatch(queue -> queue.queueFlags().contains(VK_QUEUE_GRAPHICS_BIT));
    } catch (final VulkanException e) {
      throw new VulkanUncheckedException(e);
    }
  }

  private static long createWindow()
  {
    if (!GLFW.glfwInit()) {
      throw new IllegalStateException(
        "Unable to initialize GLFW");
    }

    if (!GLFWVulkan.glfwVulkanSupported()) {
      throw new IllegalStateException(
        "Cannot find a compatible Vulkan installable client driver (ICD)");
    }

    /*
     * Specify NO_API: If this is not done, trying to use the KHR_surface extension will
     * result in a VK_ERROR_NATIVE_WINDOW_IN_USE_KHR error code.
     */

    GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API);

    final long window =
      GLFW.glfwCreateWindow(640, 480, "com.io7m.jcoronado.tests.Demo", 0L, 0L);
    if (window == 0L) {
      throw new IllegalStateException("Could not create window");
    }
    return window;
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
