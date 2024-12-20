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

import com.io7m.jcoronado.utility.allocation_tracker.VulkanHostAllocatorTracker;
import com.io7m.jcoronado.api.VulkanApplicationInfo;
import com.io7m.jcoronado.api.VulkanBufferCopy;
import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanBufferMemoryBarrier;
import com.io7m.jcoronado.api.VulkanCommandBufferSubmitInfo;
import com.io7m.jcoronado.api.VulkanCommandPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanDependencyInfo;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanExtensions;
import com.io7m.jcoronado.api.VulkanFenceCreateInfo;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanLayers;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanMemoryAllocateInfo;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanResourceException;
import com.io7m.jcoronado.api.VulkanSubmitInfo;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageSeverityFlag;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageTypeFlag;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessengerCreateInfoEXT;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsSLF4J;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsType;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLHostAllocatorJeMalloc;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import com.io7m.jmulticlose.core.CloseableCollection;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVulkan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_HOST_READ_BIT;
import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_TRANSFER_READ_BIT;
import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_TRANSFER_WRITE_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_TRANSFER_DST_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_TRANSFER_SRC_BIT;
import static com.io7m.jcoronado.api.VulkanCommandBufferLevel.VK_COMMAND_BUFFER_LEVEL_PRIMARY;
import static com.io7m.jcoronado.api.VulkanCommandBufferUsageFlag.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_HOST_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_TRANSFER_BIT;
import static com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag.VK_QUEUE_GRAPHICS_BIT;
import static com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag.VK_QUEUE_TRANSFER_BIT;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE;

public final class BufferCopy implements ExampleType
{
  private static final Logger LOG = LoggerFactory.getLogger(BufferCopy.class);

  private static final GLFWErrorCallback GLFW_ERROR_CALLBACK =
    GLFWErrorCallback.createPrint();

  public BufferCopy()
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
        .filter(BufferCopy::physicalDeviceHasGraphicsQueue)
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

      return device.queueFamilyFindWithFlags(VK_QUEUE_GRAPHICS_BIT).isPresent();
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

    try (var resources =
           CloseableCollection.create(exceptionSupplier)) {

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
        .forEach(BufferCopy::showInstanceAvailableExtension);
      availableLayers
        .forEach(BufferCopy::showInstanceAvailableLayer);
      requiredExtensions
        .forEach(BufferCopy::showInstanceRequiredExtension);
      requiredLayers
        .forEach(BufferCopy::showInstanceRequiredLayer);

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
              VulkanVersions.encode(instances.minimumRequiredVersion())))
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
       * We know that the selected physical device has a graphics queue family, and a queue
       * family for presentation. We don't know if these are the same queue families or not.
       * The VK_QUEUE_GRAPHICS_BIT implies VK_QUEUE_TRANSFER_BIT, so just searching for
       * VK_QUEUE_GRAPHICS_BIT is enough for both.
       */

      final var graphicsQueueProps =
        physicalDevice.queueFamilyFindWithFlags(VK_QUEUE_GRAPHICS_BIT)
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
          .setQueueFamilyIndex(graphicsQueueProps.queueFamilyIndex())
          .addQueuePriorities(1.0f)
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

      showMemoryStatistics(hostAllocator);

      final var bufferCreateInfo =
        VulkanBufferCreateInfo.builder()
          .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
          .setSize(128L)
          .setUsageFlags(Set.of(
            VK_BUFFER_USAGE_TRANSFER_SRC_BIT,
            VK_BUFFER_USAGE_TRANSFER_DST_BIT))
          .build();

      final var buffer0 =
        resources.add(device.createBuffer(bufferCreateInfo));
      final var buffer1 =
        resources.add(device.createBuffer(bufferCreateInfo));

      final var buffer0Requirements =
        device.getBufferMemoryRequirements(buffer0);
      final var buffer1Requirements =
        device.getBufferMemoryRequirements(buffer1);

      final var bufferMemoryType =
        physicalDevice.memory()
          .findSuitableMemoryType(
            buffer0Requirements,
            Set.of(
              VK_MEMORY_PROPERTY_HOST_COHERENT_BIT,
              VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT));

      final var memory0 =
        resources.add(
          device.allocateMemory(
            VulkanMemoryAllocateInfo.of(
              buffer0Requirements.size(),
              bufferMemoryType.index()))
        );
      final var memory1 =
        resources.add(
          device.allocateMemory(
            VulkanMemoryAllocateInfo.of(
              buffer1Requirements.size(),
              bufferMemoryType.index()))
        );

      final var map0 =
        resources.add(
          device.mapMemory(memory0, 0L, 128L, Set.of()));
      final var map1 =
        resources.add(
          device.mapMemory(memory1, 0L, 128L, Set.of()));

      device.bindBufferMemory(buffer0, memory0, 0L);
      device.bindBufferMemory(buffer1, memory1, 0L);

      final var queue =
        device.queues()
          .stream()
          .filter(BufferCopy::isSuitableCopyQueue)
          .findFirst()
          .orElseThrow();

      final var commandPool =
        resources.add(
          device.createCommandPool(
            VulkanCommandPoolCreateInfo.builder()
              .setQueueFamilyIndex(queue.queueFamilyIndex())
              .build())
        );

      final var commandBuffer =
        resources.add(
          device.createCommandBuffer(
            commandPool,
            VK_COMMAND_BUFFER_LEVEL_PRIMARY)
        );

      final var fence =
        resources.add(
          device.createFence(VulkanFenceCreateInfo.of(Set.of()))
        );

      final var fillBarrier0 =
        VulkanBufferMemoryBarrier.builder()
          .setBuffer(buffer0)
          .setOffset(0L)
          .setSize(128L)
          .setSrcAccessMask(Set.of(VK_ACCESS_TRANSFER_WRITE_BIT))
          .setSrcStageMask(Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT))
          .setSrcQueueFamilyIndex(queue.queueFamilyIndex())
          .setDstAccessMask(Set.of(VK_ACCESS_TRANSFER_READ_BIT))
          .setDstStageMask(Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT))
          .setDstQueueFamilyIndex(queue.queueFamilyIndex())
          .build();

      final var fillBarrier1 =
        VulkanBufferMemoryBarrier.builder()
          .setBuffer(buffer1)
          .setOffset(0L)
          .setSize(128L)
          .setSrcAccessMask(Set.of(VK_ACCESS_TRANSFER_WRITE_BIT))
          .setSrcStageMask(Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT))
          .setSrcQueueFamilyIndex(queue.queueFamilyIndex())
          .setDstAccessMask(Set.of(VK_ACCESS_TRANSFER_READ_BIT))
          .setDstStageMask(Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT))
          .setDstQueueFamilyIndex(queue.queueFamilyIndex())
          .build();

      final var copyBarrier0 =
        VulkanBufferMemoryBarrier.builder()
          .setBuffer(buffer0)
          .setOffset(0L)
          .setSize(128L)
          .setSrcAccessMask(Set.of(VK_ACCESS_TRANSFER_WRITE_BIT))
          .setSrcStageMask(Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT))
          .setSrcQueueFamilyIndex(queue.queueFamilyIndex())
          .setDstAccessMask(Set.of(VK_ACCESS_HOST_READ_BIT))
          .setDstStageMask(Set.of(VK_PIPELINE_STAGE_HOST_BIT))
          .setDstQueueFamilyIndex(queue.queueFamilyIndex())
          .build();

      final var copyBarrier1 =
        VulkanBufferMemoryBarrier.builder()
          .setBuffer(buffer1)
          .setOffset(0L)
          .setSize(128L)
          .setSrcAccessMask(Set.of(VK_ACCESS_TRANSFER_WRITE_BIT))
          .setSrcStageMask(Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT))
          .setSrcQueueFamilyIndex(queue.queueFamilyIndex())
          .setDstAccessMask(Set.of(VK_ACCESS_HOST_READ_BIT))
          .setDstStageMask(Set.of(VK_PIPELINE_STAGE_HOST_BIT))
          .setDstQueueFamilyIndex(queue.queueFamilyIndex())
          .build();

      commandBuffer.beginCommandBuffer(
        VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);

      debug.insertInto(commandBuffer, "fill");
      commandBuffer.fillBuffer(buffer0, 0L, 128L, 0x41414141);
      commandBuffer.fillBuffer(buffer1, 0L, 128L, 0x42424242);
      commandBuffer.pipelineBarrier(
        VulkanDependencyInfo.builder()
          .addBufferMemoryBarriers(fillBarrier0)
          .addBufferMemoryBarriers(fillBarrier1)
          .build()
      );

      debug.insertInto(commandBuffer, "copy");
      commandBuffer.copyBuffer(
        buffer0,
        buffer1,
        List.of(VulkanBufferCopy.of(0L, 0L, 128L)));

      commandBuffer.pipelineBarrier(
        VulkanDependencyInfo.builder()
          .addBufferMemoryBarriers(copyBarrier0)
          .addBufferMemoryBarriers(copyBarrier1)
          .build()
      );
      commandBuffer.endCommandBuffer();

      queue.submit(List.of(
        VulkanSubmitInfo.builder()
          .addCommandBuffers(
            VulkanCommandBufferSubmitInfo.builder()
              .setCommandBuffer(commandBuffer)
              .build()
          )
          .build()
      ), Optional.of(fence));

      LOG.debug("waiting for fence");
      device.waitForFence(fence, 1_000_000_000L);

      final var mBuffer0 =
        map0.asByteBuffer();
      final var mBuffer1 =
        map1.asByteBuffer();

      final var expBytes = new byte[128];
      Arrays.fill(expBytes, (byte) 0x41);

      final var mBytes0 = new byte[128];
      mBuffer0.get(mBytes0);
      final var mBytes1 = new byte[128];
      mBuffer1.get(mBytes1);

      {
        final var text = new StringBuilder(128);
        for (final var b : mBytes0) {
          text.append(Character.valueOf((char) b));
        }
        LOG.debug("mbytes0: {}", text);
      }

      {
        final var text = new StringBuilder(128);
        for (final var b : mBytes1) {
          text.append(Character.valueOf((char) b));
        }
        LOG.debug("mbytes1: {}", text);
      }

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

  private static boolean isSuitableCopyQueue(
    final VulkanQueueType q)
  {
    final var familyProperties =
      q.queueFamilyProperties();
    final var queueFlags =
      familyProperties.queueFlags();

    return queueFlags.contains(VK_QUEUE_TRANSFER_BIT);
  }
}
