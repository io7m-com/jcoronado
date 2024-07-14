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
import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanExtensions;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanFormat;
import com.io7m.jcoronado.api.VulkanImageCreateInfo;
import com.io7m.jcoronado.api.VulkanImageKind;
import com.io7m.jcoronado.api.VulkanImageLayout;
import com.io7m.jcoronado.api.VulkanImageTiling;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanLayers;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanResourceException;
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
import com.io7m.jmulticlose.core.CloseableCollectionType;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVulkan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_STORAGE_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_TRANSFER_DST_BIT;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_SAMPLED_BIT;
import static com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag.VK_QUEUE_GRAPHICS_BIT;
import static com.io7m.jcoronado.api.VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE;

public final class MemoryRequirements implements ExampleType
{
  private static final Logger LOG = LoggerFactory.getLogger(MemoryRequirements.class);

  private static final GLFWErrorCallback GLFW_ERROR_CALLBACK =
    GLFWErrorCallback.createPrint();

  public MemoryRequirements()
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
        .filter(MemoryRequirements::physicalDeviceHasGraphicsQueue)
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
        .forEach(MemoryRequirements::showInstanceAvailableExtension);
      availableLayers
        .forEach(MemoryRequirements::showInstanceAvailableLayer);
      requiredExtensions
        .forEach(MemoryRequirements::showInstanceRequiredExtension);
      requiredLayers
        .forEach(MemoryRequirements::showInstanceRequiredLayer);

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
              VulkanVersions.encode(1, 3, 0)))
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

      tryBuffers(resources, device);
      tryImages(resources, device);

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

  private static void tryImages(
    final CloseableCollectionType<VulkanException> resources,
    final VulkanLogicalDeviceType device)
    throws VulkanException, IOException
  {
    try (var out = Files.newBufferedWriter(Paths.get("image-sizes.txt"))) {
      out.append("# OS: ");
      out.append(System.getProperty("os.name"));
      out.append("; ");
      out.append(System.getProperty("os.arch"));
      out.append("; ");
      out.append(System.getProperty("os.version"));
      out.newLine();

      final var devProps =
        device.physicalDevice().properties();
      out.append("# Device: ");
      out.append(devProps.name());
      out.append("; ");
      out.append(devProps.driverVersion().toHumanString());
      out.append("; 0x");
      out.append(Integer.toUnsignedString(devProps.id(), 16));
      out.newLine();

      device.physicalDevice().driverProperties().ifPresent(p -> {
        try {
          out.append("# Driver: ");
          out.append(p.driverInfo());
          out.append("; ");
          out.append(p.driverName());
          out.newLine();
        } catch (final IOException e) {
          // Don't care.
        }
      });

      out.append("# Width Height Size Alignment");
      out.newLine();

      for (int y = 1; y <= 12; ++y) {
        for (int x = 1; x <= 12; ++x) {
          final var w = 1 << x;
          final var h = 1 << y;

          final var imageCreateInfo =
            VulkanImageCreateInfo.builder()
              .addSamples(VK_SAMPLE_COUNT_1_BIT)
              .addUsage(VK_IMAGE_USAGE_SAMPLED_BIT)
              .setArrayLayers(1)
              .setExtent(VulkanExtent3D.of(w, h, 1))
              .setFormat(VulkanFormat.VK_FORMAT_R8G8B8A8_UNORM)
              .setImageType(VulkanImageKind.VK_IMAGE_TYPE_2D)
              .setInitialLayout(VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED)
              .setMipLevels(1)
              .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
              .setTiling(VulkanImageTiling.VK_IMAGE_TILING_OPTIMAL)
              .build();

          final var image =
            resources.add(device.createImage(imageCreateInfo));

          final var imageRequirements =
            device.getImageMemoryRequirements(image);

          out.append(
            String.format(
            "%d %d %d %d%n",
            Integer.valueOf(w),
            Integer.valueOf(h),
            Long.valueOf(imageRequirements.size()),
            Long.valueOf(imageRequirements.alignment())
            )
          );
        }
      }
    }
  }

  private static void tryBuffers(
    final CloseableCollectionType<VulkanException> resources,
    final VulkanLogicalDeviceType device)
    throws VulkanException, IOException
  {
    try (var out = Files.newBufferedWriter(Paths.get("buffer-sizes.txt"))) {
      out.append("# OS: ");
      out.append(System.getProperty("os.name"));
      out.append("; ");
      out.append(System.getProperty("os.arch"));
      out.append("; ");
      out.append(System.getProperty("os.version"));
      out.newLine();

      final var devProps =
        device.physicalDevice().properties();
      out.append("# Device: ");
      out.append(devProps.name());
      out.append("; ");
      out.append(devProps.driverVersion().toHumanString());
      out.append("; 0x");
      out.append(Integer.toUnsignedString(devProps.id(), 16));
      out.newLine();

      device.physicalDevice().driverProperties().ifPresent(p -> {
        try {
          out.append("# Driver: ");
          out.append(p.driverInfo());
          out.append("; ");
          out.append(p.driverName());
          out.newLine();
        } catch (final IOException e) {
          // Don't care.
        }
      });

      out.append("# Length Size Alignment");
      out.newLine();

      final var baseSizes =
        new int[]{1, 2, 4, 8};
      final var baseMult =
        new int[]{1,10,100,1_000,10_000,100_000,1_000_000,10_000_000,100_000_000};

      for (final var s : baseSizes) {
        for (final var m : baseMult) {
          final var t = s * m;

          final var bufferCreateInfo =
            VulkanBufferCreateInfo.builder()
              .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
              .setSize(t)
              .setUsageFlags(Set.of(VK_BUFFER_USAGE_STORAGE_BUFFER_BIT))
              .build();

          final var buffer =
            resources.add(device.createBuffer(bufferCreateInfo));

          final var bufferRequirements =
            device.getBufferMemoryRequirements(buffer);

          LOG.info(
            "size {} requires size {}, alignment {}",
            Long.valueOf(bufferCreateInfo.size()),
            Long.valueOf(bufferRequirements.size()),
            Long.valueOf(bufferRequirements.alignment())
          );

          out.append(
            String.format(
              "%d %d %d%n",
              Integer.valueOf(t),
              Long.valueOf(bufferRequirements.size()),
              Long.valueOf(bufferRequirements.alignment())
            )
          );
        }
      }
    }
  }
}
