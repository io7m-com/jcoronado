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

package com.io7m.jcoronado.examples;

import com.io7m.jcoronado.allocation_tracker.VulkanHostAllocatorTracker;
import com.io7m.jcoronado.api.VulkanApplicationInfo;
import com.io7m.jcoronado.api.VulkanAttachmentDescription;
import com.io7m.jcoronado.api.VulkanAttachmentReference;
import com.io7m.jcoronado.api.VulkanComponentMappingType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanExtensions;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanFramebufferCreateInfo;
import com.io7m.jcoronado.api.VulkanImageCreateInfo;
import com.io7m.jcoronado.api.VulkanImageKind;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanLayers;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanMissingRequiredExtensionsException;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanRenderPassCreateInfo;
import com.io7m.jcoronado.api.VulkanResourceException;
import com.io7m.jcoronado.api.VulkanSubpassDescription;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageSeverityFlag;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageTypeFlag;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessengerCreateInfoEXT;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsSLF4J;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsType;
import com.io7m.jcoronado.lwjgl.VMALWJGLAllocatorProvider;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLHostAllocatorJeMalloc;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import com.io7m.jcoronado.vma.VMAAllocationCreateInfo;
import com.io7m.jcoronado.vma.VMAAllocatorCreateInfo;
import com.io7m.jmulticlose.core.CloseableCollection;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVulkan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Supplier;

import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_CLEAR;
import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_STORE;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R8G8B8A8_UNORM;
import static com.io7m.jcoronado.api.VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED;
import static com.io7m.jcoronado.api.VulkanImageTiling.VK_IMAGE_TILING_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT;
import static com.io7m.jcoronado.api.VulkanImageViewKind.VK_IMAGE_VIEW_TYPE_2D;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineBindPoint.VK_PIPELINE_BIND_POINT_GRAPHICS;
import static com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag.VK_QUEUE_GRAPHICS_BIT;
import static com.io7m.jcoronado.api.VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE;
import static com.io7m.jcoronado.vma.VMAMemoryUsage.VMA_MEMORY_USAGE_GPU_ONLY;

public final class Framebuffer implements ExampleType
{
  private static final Logger LOG = LoggerFactory.getLogger(Framebuffer.class);

  private static final GLFWErrorCallback GLFW_ERROR_CALLBACK =
    GLFWErrorCallback.createPrint();

  public Framebuffer()
  {

  }

  private static void showMemoryStatistics(
    final VulkanHostAllocatorTracker host_allocator)
  {
    LOG.debug(
      "allocated command scoped memory:  {} octets ({}MB)",
      Long.valueOf(host_allocator.allocatedCommandScopeOctets()),
      Double.valueOf((double) host_allocator.allocatedCommandScopeOctets() / 1_000_000.0));
    LOG.debug(
      "allocated object scoped memory:   {} octets ({}MB)",
      Long.valueOf(host_allocator.allocatedObjectScopeOctets()),
      Double.valueOf((double) host_allocator.allocatedObjectScopeOctets() / 1_000_000.0));
    LOG.debug(
      "allocated cache scoped memory:    {} octets ({}MB)",
      Long.valueOf(host_allocator.allocatedCacheScopeOctets()),
      Double.valueOf((double) host_allocator.allocatedCacheScopeOctets() / 1_000_000.0));
    LOG.debug(
      "allocated device scoped memory:   {} octets ({}MB)",
      Long.valueOf(host_allocator.allocatedDeviceScopeOctets()),
      Double.valueOf((double) host_allocator.allocatedDeviceScopeOctets() / 1_000_000.0));
    LOG.debug(
      "allocated instance scoped memory: {} octets ({}MB)",
      Long.valueOf(host_allocator.allocatedInstanceScopeOctets()),
      Double.valueOf((double) host_allocator.allocatedInstanceScopeOctets() / 1_000_000.0));
  }

  private static VulkanPhysicalDeviceType pickPhysicalDeviceOrAbort(
    final List<VulkanPhysicalDeviceType> physical_devices)
    throws VulkanException
  {
    return pickPhysicalDevice(physical_devices)
      .orElseThrow(() -> new IllegalStateException("No suitable device found"));
  }

  private static Optional<VulkanPhysicalDeviceType> pickPhysicalDevice(
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
        .filter(Framebuffer::physicalDeviceHasGraphicsQueue)
        .findFirst();
    } catch (final VulkanUncheckedException e) {
      throw e.getCause();
    }
  }

  private static boolean physicalDeviceHasGraphicsQueue(
    final VulkanPhysicalDeviceType device)
  {
    try {
      LOG.debug(
        "checking device \"{}\" for graphics queue support",
        device.properties().name());

      final var queues = device.queueFamilies();
      return queues.stream().anyMatch(queue -> queue.queueFlags().contains(
        VK_QUEUE_GRAPHICS_BIT));
    } catch (final VulkanException e) {
      throw new VulkanUncheckedException(e);
    }
  }

  private static void showInstanceRequiredLayer(
    final String name)
  {
    LOG.debug("instance required layer: {}", name);
  }

  private static void showInstanceRequiredExtension(
    final String name)
  {
    LOG.debug("instance required extension: {}", name);
  }

  private static void showInstanceAvailableLayer(
    final String name,
    final VulkanLayerProperties layer)
  {
    LOG.debug(
      "instance available layer: {}: {}, specification 0x{} implementation 0x{}",
      layer.name(),
      layer.description(),
      Integer.toUnsignedString(layer.specificationVersion(), 16),
      Integer.toUnsignedString(layer.implementationVersion(), 16));
  }

  private static void showInstanceAvailableExtension(
    final String name,
    final VulkanExtensionProperties extension)
  {
    LOG.debug(
      "instance available extension: {} 0x{}",
      extension.name(),
      Integer.toUnsignedString(extension.version(), 16));
  }

  private static void showPhysicalDeviceAvailableExtension(
    final Map.Entry<String, VulkanExtensionProperties> entry)
  {
    LOG.debug(
      "device available extension: {} 0x{}",
      entry.getKey(),
      Integer.toUnsignedString(entry.getValue().version(), 16));
  }

  private static Set<String> requiredGLFWExtensions()
  {
    final var glfw_required_extensions =
      GLFWVulkan.glfwGetRequiredInstanceExtensions();

    final HashSet<String> required =
      new HashSet<>(glfw_required_extensions.capacity());
    for (var index = 0; index < glfw_required_extensions.capacity(); ++index) {
      glfw_required_extensions.position(index);
      required.add(glfw_required_extensions.getStringASCII());
    }
    return required;
  }

  @Override
  public void execute()
    throws Exception
  {
    GLFW_ERROR_CALLBACK.set();
    GLFW.glfwInit();

    final var hostAllocatorMain =
      new VulkanLWJGLHostAllocatorJeMalloc();
    final var hostAllocator =
      new VulkanHostAllocatorTracker(hostAllocatorMain);

    final Supplier<VulkanException> exceptionSupplier =
      () -> new VulkanResourceException("Could not close one or more resources.");

    try (var resources = CloseableCollection.create(exceptionSupplier)) {

      /*
       * Create an instance provider.
       */

      final var instances =
        VulkanLWJGLInstanceProvider.create();

      LOG.debug(
        "instance provider: {} {}",
        instances.providerName(),
        instances.providerVersion());

      final var supported = instances.findSupportedInstanceVersion();
      LOG.debug("supported instance version is: {}", supported.toHumanString());

      final var availableExtensions =
        instances.extensions();
      final var availableLayers =
        instances.layers();

      /*
       * Determine which extensions are required by the window system, and which layers are
       * required.
       */

      final var requiredLayers =
        Set.of("VK_LAYER_KHRONOS_validation");

      final var requiredExtensions = new HashSet<String>();
      requiredExtensions.addAll(requiredGLFWExtensions());
      requiredExtensions.add("VK_EXT_debug_utils");

      availableExtensions
        .forEach(Framebuffer::showInstanceAvailableExtension);
      availableLayers
        .forEach(Framebuffer::showInstanceAvailableLayer);
      requiredExtensions
        .forEach(Framebuffer::showInstanceRequiredExtension);
      requiredLayers
        .forEach(Framebuffer::showInstanceRequiredLayer);

      /*
       * Filter the available extensions and layers based on the requirements expressed above.
       * If any required extensions are not available, an exception is raised.
       */

      final var optionalExtensions =
        Set.<String>of();

      final var enableExtensions =
        VulkanExtensions.filterRequiredExtensions(
          availableExtensions,
          optionalExtensions,
          requiredExtensions);

      final var optionalLayers =
        Set.<String>of();

      final var enableLayers =
        VulkanLayers.filterRequiredLayers(
          availableLayers,
          optionalLayers,
          requiredLayers);

      /*
       * Create a new instance.
       */

      final var createInfo =
        VulkanInstanceCreateInfo.builder()
          .setApplicationInfo(
            VulkanApplicationInfo.of(
              "com.io7m.jcoronado.tests.Demo",
              VulkanVersions.encode(0, 0, 1),
              "com.io7m.jcoronado.tests",
              VulkanVersions.encode(0, 0, 1),
              VulkanVersions.encode(1, 0, 0)))
          .setEnabledExtensions(enableExtensions)
          .setEnabledLayers(enableLayers)
          .build();

      final var instance =
        resources.add(instances.createInstance(
          createInfo,
          Optional.of(hostAllocator)));

      /*
       * Enable debug messages.
       */

      final var debug =
        instance.findEnabledExtension(
          "VK_EXT_debug_utils",
          VulkanDebugUtilsType.class
        ).orElseThrow(() -> {
          return new IllegalStateException(
            "Missing VK_EXT_debug_utils extension");
        });

      resources.add(
        debug.createDebugUtilsMessenger(
          instance,
          VulkanDebugUtilsMessengerCreateInfoEXT.builder()
            .setSeverity(EnumSet.allOf(VulkanDebugUtilsMessageSeverityFlag.class))
            .setType(EnumSet.allOf(VulkanDebugUtilsMessageTypeFlag.class))
            .setCallback(new VulkanDebugUtilsSLF4J(LOG))
            .build()
        )
      );

      /*
       * List the available physical devices and pick the "best" one.
       */

      final var physicalDevices =
        instance.physicalDevices();
      final var physicalDevice =
        resources.add(pickPhysicalDeviceOrAbort(physicalDevices));

      LOG.debug("physical device: {}", physicalDevice);


      /*
       * Require the VK_KHR_get_memory_requirements2 extension in order to use VMA.
       */

      final var deviceExtensions =
        physicalDevice.extensions(Optional.empty());

      deviceExtensions.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(Framebuffer::showPhysicalDeviceAvailableExtension);

      if (!deviceExtensions.containsKey("VK_KHR_get_memory_requirements2")) {
        throw new VulkanMissingRequiredExtensionsException(
          Set.of("VK_KHR_get_memory_requirements2"),
          "Missing required extension");
      }


      /*
       * We know that the selected physical device has a graphics queue family, and a queue
       * family for presentation. We don't know if these are the same queue families or not.
       * The VK_QUEUE_GRAPHICS_BIT implies VK_QUEUE_TRANSFER_BIT, so just searching for
       * VK_QUEUE_GRAPHICS_BIT is enough for both.
       */

      final var graphicsQueueProps =
        physicalDevice.queueFamilies()
          .stream()
          .filter(queue -> queue.queueFlags().contains(VK_QUEUE_GRAPHICS_BIT))
          .findFirst()
          .orElseThrow(() -> new IllegalStateException(
            "Could not find graphics queue"));

      /*
       * Put together the information needed to create a logical device. In this case,
       * all that is really needed is to specify the creation of one or more queues.
       */

      final var logicalDeviceInfoBuilder =
        VulkanLogicalDeviceCreateInfo.builder();

      logicalDeviceInfoBuilder.addQueueCreateInfos(
        VulkanLogicalDeviceQueueCreateInfo.builder()
          .setQueueCount(1)
          .setQueueFamilyIndex(graphicsQueueProps.queueFamilyIndex())
          .setQueuePriorities(1.0f)
          .build());

      /*
       * Create the logical device.
       */

      final var device =
        resources.add(
          physicalDevice.createLogicalDevice(
            logicalDeviceInfoBuilder
              .addEnabledExtensions("VK_KHR_get_memory_requirements2")
              .build())
        );

      LOG.debug("logical device: {}", device);

      /*
       * Create a VMA allocator.
       */

      final var vmaAllocators =
        VMALWJGLAllocatorProvider.create();

      LOG.debug(
        "vma allocator provider: {} {}",
        vmaAllocators.providerName(),
        vmaAllocators.providerVersion());

      final var vmaCreateInfo =
        VMAAllocatorCreateInfo.builder()
          .setFrameInUseCount(OptionalInt.empty())
          .setLogicalDevice(device)
          .build();

      final var vmaAllocator =
        resources.add(vmaAllocators.createAllocator(vmaCreateInfo));

      /*
       * Find the graphics and presentation queues.
       */

      final var graphicsQueue =
        device.queue(graphicsQueueProps.queueFamilyIndex(), 0)
          .orElseThrow(() -> new IllegalStateException(
            "Could not find graphics queue"));

      LOG.debug("graphics queue: {}", graphicsQueue);

      /*
       * Start rendering frames.
       */

      showMemoryStatistics(hostAllocator);

      final var imageAllocInfo =
        VMAAllocationCreateInfo.builder()
          .setUsage(VMA_MEMORY_USAGE_GPU_ONLY)
          .addRequiredFlags(VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT)
          .setMemoryTypeBits(0L)
          .build();

      final var imageCreateInfo =
        VulkanImageCreateInfo.builder()
          .setArrayLayers(1)
          .setExtent(VulkanExtent3D.of(640, 480, 1))
          .setFormat(VK_FORMAT_R8G8B8A8_UNORM)
          .setImageType(VulkanImageKind.VK_IMAGE_TYPE_2D)
          .setInitialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
          .setMipLevels(1)
          .setSamples(Set.of(VK_SAMPLE_COUNT_1_BIT))
          .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
          .setTiling(VK_IMAGE_TILING_OPTIMAL)
          .addUsage(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT)
          .build();

      final var imageAllocation0 =
        vmaAllocator.createImage(imageAllocInfo, imageCreateInfo);

      resources.add(imageAllocation0.result());

      final var imageViewRange0 =
        VulkanImageSubresourceRange.builder()
          .setLayerCount(1)
          .setBaseMipLevel(0)
          .setBaseArrayLayer(0)
          .setLevelCount(1)
          .addAspectMask(VK_IMAGE_ASPECT_COLOR_BIT)
          .build();

      final var imageViewCreateInfo =
        VulkanImageViewCreateInfo.builder()
          .setImage(imageAllocation0.result())
          .setViewType(VK_IMAGE_VIEW_TYPE_2D)
          .setFormat(VK_FORMAT_R8G8B8A8_UNORM)
          .setComponents(VulkanComponentMappingType.identity())
          .setSubresourceRange(imageViewRange0)
          .build();

      final var imageView0 =
        resources.add(device.createImageView(imageViewCreateInfo));

      final var attachmentDescription0 =
        VulkanAttachmentDescription.builder()
          .setFinalLayout(VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
          .setInitialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
          .setFormat(VK_FORMAT_R8G8B8A8_UNORM)
          .setSamples(VK_SAMPLE_COUNT_1_BIT)
          .setLoadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
          .setStoreOp(VK_ATTACHMENT_STORE_OP_STORE)
          .setStencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
          .setStencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
          .build();

      final var attachmentReference0 =
        VulkanAttachmentReference.builder()
          .setAttachment(0)
          .setLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
          .build();

      final var subPassInfo =
        VulkanSubpassDescription.builder()
          .addColorAttachments(attachmentReference0)
          .setPipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS)
          .build();

      final var renderPassInfo =
        VulkanRenderPassCreateInfo.builder()
          .addSubpasses(subPassInfo)
          .addAttachments(attachmentDescription0)
          .build();

      final var renderPass =
        resources.add(device.createRenderPass(renderPassInfo));

      final var framebufferCreateInfo =
        VulkanFramebufferCreateInfo.builder()
          .addAttachments(imageView0)
          .setWidth(640)
          .setHeight(480)
          .setLayers(1)
          .setRenderPass(renderPass)
          .build();

      final var framebuffer =
        resources.add(device.createFramebuffer(framebufferCreateInfo));

      showMemoryStatistics(hostAllocator);

      /*
       * Wait until the device is idle before exiting.
       */

      LOG.debug("waiting for device to idle");
      device.waitIdle();

    } catch (final VulkanException e) {
      LOG.error("vulkan error: ", e);
      throw e;
    } finally {
      GLFW_ERROR_CALLBACK.close();
    }
  }
}
