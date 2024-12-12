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
import com.io7m.jcoronado.api.VulkanClearValueColorFloatingPoint;
import com.io7m.jcoronado.api.VulkanCommandBufferSubmitInfo;
import com.io7m.jcoronado.api.VulkanCommandPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandPoolType;
import com.io7m.jcoronado.api.VulkanDependencyInfo;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanExtensions;
import com.io7m.jcoronado.api.VulkanImageAspectFlag;
import com.io7m.jcoronado.api.VulkanImageMemoryBarrier;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanLayers;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanMissingRequiredExtensionsException;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyIndex;
import com.io7m.jcoronado.api.VulkanQueueIndex;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanResourceException;
import com.io7m.jcoronado.api.VulkanSemaphoreSubmitInfo;
import com.io7m.jcoronado.api.VulkanSubmitInfo;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageSeverityFlag;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageTypeFlag;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessengerCreateInfoEXT;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsSLF4J;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsType;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingsCreateInfo;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanExtKHRSurfaceType;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanExtKHRSwapChainType;
import com.io7m.jcoronado.layers.khronos_validation.api.VulkanValidationValidateSync;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLHostAllocatorJeMalloc;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainConfiguration;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainImageIndex;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainImageType;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainManager;
import com.io7m.jmulticlose.core.CloseableCollection;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_MEMORY_READ_BIT;
import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_MEMORY_WRITE_BIT;
import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_TRANSFER_WRITE_BIT;
import static com.io7m.jcoronado.api.VulkanCommandBufferLevel.VK_COMMAND_BUFFER_LEVEL_PRIMARY;
import static com.io7m.jcoronado.api.VulkanCommandBufferUsageFlag.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT;
import static com.io7m.jcoronado.api.VulkanCommandPoolCreateFlag.VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_TRANSFER_DST_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_CLEAR_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_TRANSFER_BIT;
import static com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag.VK_QUEUE_GRAPHICS_BIT;
import static com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanCompositeAlphaFlagKHR.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR;
import static com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentModeKHR.VK_PRESENT_MODE_FIFO_KHR;
import static com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentModeKHR.VK_PRESENT_MODE_IMMEDIATE_KHR;
import static com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentModeKHR.VK_PRESENT_MODE_MAILBOX_KHR;

public final class HelloSwapChain implements ExampleType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(HelloSwapChain.class);

  private static final GLFWErrorCallback GLFW_ERROR_CALLBACK =
    GLFWErrorCallback.createPrint();

  private static final Duration ONE_FRAME =
    Duration.of(16L, ChronoUnit.MILLIS);

  private static final int FRAME_LIMIT = 1000;

  private static final VulkanClearValueColorFloatingPoint RED =
    VulkanClearValueColorFloatingPoint.of(1.0f, 0.0f, 0.0f, 1.0f);

  private final AtomicBoolean windowIsResizing;

  public HelloSwapChain()
  {
    this.windowIsResizing = new AtomicBoolean(false);
  }

  private static Optional<VulkanPhysicalDeviceType> pickPhysicalDevice(
    final VulkanExtKHRSurfaceType khrSurfaceExt,
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
        .filter(HelloSwapChain::physicalDeviceHasSwapChainExtension)
        .filter(HelloSwapChain::physicalDeviceHasGraphicsQueue)
        .filter(device -> physicalDeviceHasPresentationQueue(
          khrSurfaceExt,
          surface,
          device))
        .findFirst();
    } catch (final VulkanUncheckedException e) {
      throw e.getCause();
    }
  }

  private static boolean physicalDeviceHasSwapChainExtension(
    final VulkanPhysicalDeviceType device)
  {
    /*
     * Determine if the device supports the swapchain extension.
     */

    try {
      LOG.debug(
        "Checking device \"{}\" for VK_KHR_swapchain support",
        device.properties().name());

      final var extensions =
        device.extensions(Optional.empty());

      return extensions.containsKey("VK_KHR_swapchain")
             && extensions.containsKey("VK_EXT_swapchain_maintenance1");
    } catch (final VulkanException e) {
      throw new VulkanUncheckedException(e);
    }
  }

  private static boolean physicalDeviceHasPresentationQueue(
    final VulkanExtKHRSurfaceType khrSurfaceExt,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface,
    final VulkanPhysicalDeviceType device)
  {
    /*
     * Determine which, if any, of the available queues are capable of "presentation". That
     * is, rendering to a surface that will appear onscreen.
     */

    try {
      LOG.debug(
        "Checking device \"{}\" for presentation support",
        device.properties().name());

      final var queues_presentable =
        khrSurfaceExt.surfaceSupport(device, surface);
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

      return device.queueFamilyFindWithFlags(VK_QUEUE_GRAPHICS_BIT).isPresent();
    } catch (final VulkanException e) {
      throw new VulkanUncheckedException(e);
    }
  }

  private long createWindow()
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

    final var window =
      GLFW.glfwCreateWindow(640, 480, "com.io7m.jcoronado.tests.Demo", 0L, 0L);
    if (window == 0L) {
      throw new IllegalStateException("Could not create window");
    }

    GLFW.glfwSetWindowSizeCallback(
      window,
      GLFWWindowSizeCallback.create((_, _, _) -> {
        this.windowIsResizing.set(true);
      })
    );
    return window;
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
    final String name,
    final VulkanExtensionProperties extension)
  {
    LOG.debug(
      "device available extension: {} 0x{}",
      extension.name(),
      Integer.toUnsignedString(extension.version(), 16));
  }

  private static Set<String> requiredGLFWExtensions()
  {
    final var glfwRequiredExtensions =
      GLFWVulkan.glfwGetRequiredInstanceExtensions();

    final HashSet<String> required =
      new HashSet<>(glfwRequiredExtensions.capacity());
    for (var index = 0; index < glfwRequiredExtensions.capacity(); ++index) {
      glfwRequiredExtensions.position(index);
      required.add(glfwRequiredExtensions.getStringASCII());
    }
    return required;
  }

  private static VulkanPhysicalDeviceType pickPhysicalDeviceOrAbort(
    final VulkanExtKHRSurfaceType khrSurfaceExt,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface,
    final List<VulkanPhysicalDeviceType> physicalDevices)
    throws VulkanException
  {
    return pickPhysicalDevice(khrSurfaceExt, surface, physicalDevices)
      .orElseThrow(() -> new IllegalStateException("No suitable device found"));
  }

  private static VulkanExtKHRSwapChainType getSwapChainExtension(
    final VulkanLogicalDeviceType device)
    throws VulkanException
  {
    return device.findEnabledExtension(
        "VK_KHR_swapchain",
        VulkanExtKHRSwapChainType.class)
      .orElseThrow(() -> new IllegalStateException(
        "Missing VK_KHR_swapchain extension"));
  }

  @Override
  public void execute()
    throws VulkanException, TimeoutException
  {
    GLFW_ERROR_CALLBACK.set();

    final var hostAllocatorMain =
      new VulkanLWJGLHostAllocatorJeMalloc();
    final var hostAllocatorTracker =
      new VulkanHostAllocatorTracker(hostAllocatorMain);

    final var finished =
      new AtomicBoolean(false);

    /*
     * Create an allocator for temporary objects.
     */

    final Supplier<VulkanException> exceptionSupplier =
      () -> new VulkanResourceException("Could not close one or more resources.");

    try (var resources =
           CloseableCollection.create(exceptionSupplier)) {

      final var window = this.createWindow();

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
       * Determine which extensions are required by the window system, and
       * which layers are required.
       */

      final var requiredLayers =
        Set.of("VK_LAYER_KHRONOS_validation");

      final var requiredExtensions = new HashSet<String>();
      requiredExtensions.addAll(requiredGLFWExtensions());
      requiredExtensions.add("VK_EXT_debug_utils");
      requiredExtensions.add("VK_EXT_surface_maintenance1");
      requiredExtensions.add("VK_KHR_get_surface_capabilities2");

      availableExtensions
        .forEach(HelloSwapChain::showInstanceAvailableExtension);
      availableLayers
        .forEach(HelloSwapChain::showInstanceAvailableLayer);
      requiredExtensions
        .forEach(HelloSwapChain::showInstanceRequiredExtension);
      requiredLayers
        .forEach(HelloSwapChain::showInstanceRequiredLayer);

      /*
       * Filter the available extensions and layers based on the requirements expressed above.
       * If any required extensions are not available, an exception is raised.
       */

      final var enableExtensions =
        VulkanExtensions.filterRequiredExtensions(
          availableExtensions,
          Set.of(),
          requiredExtensions);

      final var enableLayers =
        VulkanLayers.filterRequiredLayers(
          availableLayers,
          Set.of(),
          requiredLayers);

      /*
       * Create a new instance.
       */

      final var instanceCreateInfo =
        VulkanInstanceCreateInfo.builder()
          .setApplicationInfo(
            VulkanApplicationInfo.of(
              "com.io7m.jcoronado.tests.Demo",
              VulkanVersions.encode(0, 0, 1),
              "com.io7m.jcoronado.tests",
              VulkanVersions.encode(0, 0, 1),
              VulkanVersions.encode(instances.minimumRequiredVersion()))
          )
          .setEnabledExtensions(enableExtensions)
          .setEnabledLayers(enableLayers)
          .addExtensionInfo(new VulkanLayerSettingsCreateInfo(
            List.of(
              new VulkanValidationValidateSync(true)
                .toSetting()
            )
          ))
          .build();

      final var instance =
        resources.add(instances.createInstance(
          instanceCreateInfo,
          Optional.of(hostAllocatorTracker)));

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
       * Get access to the VK_KHR_surface extension in order to produce a renderable
       * surface from the created window.
       */

      final var khrSurfaceExt =
        instance.findEnabledExtension(
            "VK_KHR_surface",
            VulkanExtKHRSurfaceType.class)
          .orElseThrow(() -> new IllegalStateException(
            "Missing VK_KHR_surface extension"));

      final var surface =
        resources.add(khrSurfaceExt.surfaceFromWindow(instance, window));

      /*
       * List the available physical devices and pick the "best" one.
       */

      final var physicalDevices =
        instance.physicalDevices();
      final var physicalDevice =
        resources.add(
          pickPhysicalDeviceOrAbort(
            khrSurfaceExt,
            surface,
            physicalDevices));

      LOG.debug("Physical device: {}", physicalDevice);

      /*
       * Require the VK_KHR_get_memory_requirements2 extension in order to use VMA.
       */

      final var deviceExtensions =
        physicalDevice.extensions(Optional.empty());

      deviceExtensions.forEach(HelloSwapChain::showPhysicalDeviceAvailableExtension);
      if (!deviceExtensions.containsKey("VK_KHR_get_memory_requirements2")) {
        throw new VulkanMissingRequiredExtensionsException(
          Set.of("VK_KHR_get_memory_requirements2"),
          "Missing required extension");
      }

      /*
       * We know that the selected physical device has a graphics queue family, and a queue
       * family for presentation. We don't know if these are the same queue families or not.
       */

      final var graphicsQueueProps =
        physicalDevice.queueFamilyFindWithFlags(VK_QUEUE_GRAPHICS_BIT)
          .orElseThrow(() -> new IllegalStateException(
            "Could not find graphics queue"));

      final var presentationQueueProps =
        khrSurfaceExt.surfaceSupport(physicalDevice, surface)
          .stream()
          .findFirst()
          .orElseThrow(() -> new IllegalStateException(
            "Could not find presentation queue"));

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

      if (!Objects.equals(
        graphicsQueueProps.queueFamilyIndex(),
        presentationQueueProps.queueFamilyIndex())) {
        logicalDeviceInfoBuilder.addQueueCreateInfos(
          VulkanLogicalDeviceQueueCreateInfo.builder()
            .setQueueFamilyIndex(presentationQueueProps.queueFamilyIndex())
            .addQueuePriorities(1.0f)
            .build());
      }

      /*
       * Create the logical device.
       */

      final var device =
        resources.add(
          physicalDevice.createLogicalDevice(
            logicalDeviceInfoBuilder
              .addEnabledExtensions("VK_KHR_swapchain")
              .addEnabledExtensions("VK_EXT_swapchain_maintenance1")
              .addEnabledExtensions("VK_KHR_get_memory_requirements2")
              .build())
        );

      LOG.debug("Logical device: {}", device);

      /*
       * Find the graphics and presentation queues.
       */

      final var graphicsQueue =
        device.queue(
            graphicsQueueProps.queueFamilyIndex(),
            new VulkanQueueIndex(0))
          .orElseThrow(() -> new IllegalStateException(
            "Could not find graphics queue"));
      final var presentationQueue =
        device.queue(
            presentationQueueProps.queueFamilyIndex(),
            new VulkanQueueIndex(0))
          .orElseThrow(() -> new IllegalStateException(
            "Could not find presentation queue"));

      LOG.debug("Graphics queue: {}", graphicsQueue);
      LOG.debug("Presentation queue: {}", presentationQueue);

      /*
       * Create a swapchain and have the manager manage it.
       */

      final var khrSwapchainExt =
        getSwapChainExtension(device);

      final var swapChainManager =
        resources.add(
          JCSwapchainManager.create(
            JCSwapchainConfiguration.builder()
              .setDevice(device)
              .setGraphicsQueue(graphicsQueue)
              .setPresentationQueue(presentationQueue)
              .setSurface(surface)
              .setSurfaceExtension(khrSurfaceExt)
              .setSwapChainExtension(khrSwapchainExt)
              .addSurfaceAlphaFlags(VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR)
              .addImageUsageFlags(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT)
              .addImageUsageFlags(VK_IMAGE_USAGE_TRANSFER_DST_BIT)
              .addPreferredModes(VK_PRESENT_MODE_MAILBOX_KHR)
              .addPreferredModes(VK_PRESENT_MODE_FIFO_KHR)
              .addPreferredModes(VK_PRESENT_MODE_IMMEDIATE_KHR)
              .build()
          )
        );

      /*
       * Start rendering frames.
       */

      final var commandPools =
        new HashMap<JCSwapchainImageIndex, VulkanCommandPoolType>();
      for (final var index : swapChainManager.imageIndices()) {
        final var commandPool =
          resources.add(
            device.createCommandPool(
              VulkanCommandPoolCreateInfo.builder()
                .addFlags(VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT)
                .setQueueFamilyIndex(graphicsQueue.queueFamilyIndex())
                .build()
            )
          );
        commandPools.put(index, commandPool);
      }

      var frame = 0;
      while (!finished.get()) {
        LOG.debug("Frame {}", Integer.valueOf(frame));

        this.windowIsResizing.set(false);
        GLFW.glfwPollEvents();

        if (GLFW.glfwWindowShouldClose(window)) {
          finished.set(true);
        }

        try {
          if (this.windowIsResizing.get()) {
            try {
              Thread.sleep(ONE_FRAME);
            } catch (final InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          } else {
            try (var image = swapChainManager.acquire()) {
              final var commandPool =
                commandPools.get(image.index());

              device.resetCommandPool(commandPool);

              this.drawFrame(
                device,
                commandPool,
                graphicsQueue,
                presentationQueue,
                image
              );
            }
          }
        } catch (final Exception e) {
          throw e;
        }

        ++frame;

        if (frame == FRAME_LIMIT) {
          break;
        }
      }

      /*
       * Wait until the device is idle before exiting.
       */

      LOG.debug("Waiting for device to idle");
      device.waitIdle();

    } catch (final VulkanException e) {
      LOG.error("vulkan error: ", e);
      throw e;
    } finally {
      GLFW_ERROR_CALLBACK.close();
    }
  }

  private void drawFrame(
    final VulkanLogicalDeviceType device,
    final VulkanCommandPoolType commandPool,
    final VulkanQueueType graphicsQueue,
    final VulkanQueueType presentationQueue,
    final JCSwapchainImageType image)
    throws VulkanException
  {
    final var debugging = device.debugging();

    try (final var cmds =
           device.createCommandBuffer(
             commandPool,
             VK_COMMAND_BUFFER_LEVEL_PRIMARY)) {

      try (var _ = debugging.begin(cmds, "Clear")) {
        final var wholeImage =
          VulkanImageSubresourceRange.builder()
            .setBaseArrayLayer(0)
            .setBaseMipLevel(0)
            .setLayerCount(1)
            .setLevelCount(1)
            .addAspectMask(VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT)
            .build();

        cmds.beginCommandBuffer(VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);

        {
          final var preClearBarrier =
            VulkanImageMemoryBarrier.builder()
              .setImage(image.image())
              .setSrcQueueFamilyIndex(VulkanQueueFamilyIndex.ignored())
              .addSrcStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
              .addSrcAccessMask()
              .setDstQueueFamilyIndex(VulkanQueueFamilyIndex.ignored())
              .addDstStageMask(VK_PIPELINE_STAGE_CLEAR_BIT)
              .addDstAccessMask(VK_ACCESS_TRANSFER_WRITE_BIT)
              .setOldLayout(VK_IMAGE_LAYOUT_UNDEFINED)
              .setNewLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL)
              .setSubresourceRange(wholeImage)
              .build();

          cmds.pipelineBarrier(
            VulkanDependencyInfo.builder()
              .addImageMemoryBarriers(preClearBarrier)
              .build()
          );
        }

        cmds.clearColorImage(
          image.image(),
          VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL,
          RED,
          List.of(wholeImage)
        );

        {
          final var postClearBarrier =
            VulkanImageMemoryBarrier.builder()
              .setImage(image.image())
              .setSrcQueueFamilyIndex(VulkanQueueFamilyIndex.ignored())
              .addSrcStageMask(VK_PIPELINE_STAGE_CLEAR_BIT)
              .addSrcAccessMask(VK_ACCESS_MEMORY_WRITE_BIT)
              .setDstQueueFamilyIndex(VulkanQueueFamilyIndex.ignored())
              .addDstStageMask(VK_PIPELINE_STAGE_TRANSFER_BIT)
              .addDstAccessMask(VK_ACCESS_MEMORY_READ_BIT)
              .setOldLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL)
              .setNewLayout(VK_IMAGE_LAYOUT_PRESENT_SRC_KHR)
              .setSubresourceRange(wholeImage)
              .build();

          cmds.pipelineBarrier(
            VulkanDependencyInfo.builder()
              .addImageMemoryBarriers(postClearBarrier)
              .build()
          );
        }
        cmds.endCommandBuffer();
      }

      final var waitSemaphore =
        VulkanSemaphoreSubmitInfo.builder()
          .addStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
          .setSemaphore(image.imageReadySemaphore())
          .build();

      final var commandBuffers =
        VulkanCommandBufferSubmitInfo.builder()
          .setCommandBuffer(cmds)
          .build();

      final var signalSemaphore =
        VulkanSemaphoreSubmitInfo.builder()
          .setSemaphore(image.renderFinishedSemaphore())
          .addStageMask(VK_PIPELINE_STAGE_CLEAR_BIT)
          .build();

      final var submitInfo =
        VulkanSubmitInfo.builder()
          .addWaitSemaphores(waitSemaphore)
          .addCommandBuffers(commandBuffers)
          .addSignalSemaphores(signalSemaphore)
          .build();

      LOG.trace("Submit: signal {}", image.renderFinishedFence());

      graphicsQueue.submit(
        List.of(submitInfo),
        Optional.of(image.renderFinishedFence())
      );
      image.present();
      presentationQueue.waitIdle();
    }
  }
}
