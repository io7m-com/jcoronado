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
import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanClearValueColorFloatingPoint;
import com.io7m.jcoronado.api.VulkanCommandBufferBeginInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferSubmitInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanCommandPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanComponentMapping;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanExtensions;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanFramebufferCreateInfo;
import com.io7m.jcoronado.api.VulkanFramebufferType;
import com.io7m.jcoronado.api.VulkanGraphicsPipelineCreateInfo;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanImageViewCreateFlag;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import com.io7m.jcoronado.api.VulkanImageViewType;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanLayers;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanMemoryAllocateInfo;
import com.io7m.jcoronado.api.VulkanMissingRequiredExtensionsException;
import com.io7m.jcoronado.api.VulkanOffset2D;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendAttachmentState;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineInputAssemblyStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineMultisampleStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineRasterizationStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineShaderStageCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineVertexInputStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineViewportStateCreateInfo;
import com.io7m.jcoronado.api.VulkanQueueFamilyIndex;
import com.io7m.jcoronado.api.VulkanQueueIndex;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanRectangle2D;
import com.io7m.jcoronado.api.VulkanRenderPassBeginInfo;
import com.io7m.jcoronado.api.VulkanRenderPassCreateInfo;
import com.io7m.jcoronado.api.VulkanResourceException;
import com.io7m.jcoronado.api.VulkanSemaphoreBinaryCreateInfo;
import com.io7m.jcoronado.api.VulkanSemaphoreBinaryType;
import com.io7m.jcoronado.api.VulkanSemaphoreSubmitInfo;
import com.io7m.jcoronado.api.VulkanShaderModuleCreateInfo;
import com.io7m.jcoronado.api.VulkanShaderModuleType;
import com.io7m.jcoronado.api.VulkanSharingMode;
import com.io7m.jcoronado.api.VulkanSubmitInfo;
import com.io7m.jcoronado.api.VulkanSubpassDependency;
import com.io7m.jcoronado.api.VulkanSubpassDependencyType;
import com.io7m.jcoronado.api.VulkanSubpassDescription;
import com.io7m.jcoronado.api.VulkanTemporaryAllocatorType;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.api.VulkanVertexInputAttributeDescription;
import com.io7m.jcoronado.api.VulkanVertexInputBindingDescription;
import com.io7m.jcoronado.api.VulkanViewport;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageSeverityFlag;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageTypeFlag;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessengerCreateInfoEXT;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsSLF4J;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsType;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanExtKHRSurfaceType;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanSurfaceCapabilitiesKHR;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanSurfaceFormatKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanExtKHRSwapChainType;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanExtKHRSwapChainType.VulkanKHRSwapChainType;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentInfoKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentModeKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainCreateInfo;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainNotReady;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainOK;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainOutOfDate;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainSubOptimal;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainTimedOut;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLHostAllocatorJeMalloc;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLTemporaryAllocator;
import com.io7m.jmulticlose.core.CloseableCollection;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVulkan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_COLOR_ATTACHMENT_READ_BIT;
import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT;
import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_CLEAR;
import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_STORE;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanCommandBufferLevel.VK_COMMAND_BUFFER_LEVEL_PRIMARY;
import static com.io7m.jcoronado.api.VulkanCommandBufferUsageFlag.VK_COMMAND_BUFFER_USAGE_SIMULTANEOUS_USE_BIT;
import static com.io7m.jcoronado.api.VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_IDENTITY;
import static com.io7m.jcoronado.api.VulkanCullModeFlag.VK_CULL_MODE_BACK_BIT;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_B8G8R8A8_UNORM;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R32G32B32_SFLOAT;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R32G32_SFLOAT;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_UNDEFINED;
import static com.io7m.jcoronado.api.VulkanFrontFace.VK_FRONT_FACE_CLOCKWISE;
import static com.io7m.jcoronado.api.VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT;
import static com.io7m.jcoronado.api.VulkanImageViewKind.VK_IMAGE_VIEW_TYPE_2D;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineBindPoint.VK_PIPELINE_BIND_POINT_GRAPHICS;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT;
import static com.io7m.jcoronado.api.VulkanPolygonMode.VK_POLYGON_MODE_FILL;
import static com.io7m.jcoronado.api.VulkanPrimitiveTopology.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST;
import static com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag.VK_QUEUE_GRAPHICS_BIT;
import static com.io7m.jcoronado.api.VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT;
import static com.io7m.jcoronado.api.VulkanShaderStageFlag.VK_SHADER_STAGE_FRAGMENT_BIT;
import static com.io7m.jcoronado.api.VulkanShaderStageFlag.VK_SHADER_STAGE_VERTEX_BIT;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_CONCURRENT;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE;
import static com.io7m.jcoronado.api.VulkanSubpassContents.VK_SUBPASS_CONTENTS_INLINE;
import static com.io7m.jcoronado.api.VulkanVertexInputRate.VK_VERTEX_INPUT_RATE_VERTEX;
import static com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR;
import static com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanCompositeAlphaFlagKHR.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR;
import static com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentModeKHR.VK_PRESENT_MODE_FIFO_KHR;
import static com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentModeKHR.VK_PRESENT_MODE_IMMEDIATE_KHR;
import static com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentModeKHR.VK_PRESENT_MODE_MAILBOX_KHR;

public final class HelloVulkan implements ExampleType
{
  private static final Logger LOG = LoggerFactory.getLogger(HelloVulkan.class);

  private static final GLFWErrorCallback GLFW_ERROR_CALLBACK =
    GLFWErrorCallback.createPrint();

  private static final int VERTEX_POSITION_SIZE = 2 * 4;
  private static final int VERTEX_COLOR_SIZE = 3 * 4;
  private static final int VERTEX_SIZE = VERTEX_POSITION_SIZE + VERTEX_COLOR_SIZE;

  public HelloVulkan()
  {

  }

  /**
   * Draw a single frame.
   */

  private static void drawFrame(
    final VulkanExtKHRSwapChainType khrSwapchainExt,
    final VulkanKHRSwapChainType swapChain,
    final VulkanSemaphoreBinaryType imageAvailable,
    final VulkanSemaphoreBinaryType renderFinished,
    final List<VulkanCommandBufferType> graphicsCommandBuffers,
    final VulkanQueueType graphicsQueue,
    final VulkanQueueType queuePresentation)
    throws VulkanException
  {
    /*
     * Try to acquire an image from the swap chain, waiting indefinitely until one is available.
     * There isn't really anything sensible that we can do if an image can't be acquired in this
     * example code, so all that will happen is that the code will immediately try again.
     */

    final var acquisition =
      swapChain.acquireImageWithSemaphore(
        0xffff_ffff_ffff_ffffL,
        imageAvailable);

    final int imageIndex;
    switch (acquisition) {
      case final VulkanSwapChainOK r -> {
        imageIndex = r.imageIndex();
      }
      case final VulkanSwapChainNotReady r -> {
        LOG.error("Could not acquire image ({})", r);
        return;
      }
      case final VulkanSwapChainOutOfDate r -> {
        LOG.error("Could not acquire image ({})", r);
        return;
      }
      case final VulkanSwapChainSubOptimal r -> {
        LOG.error("Could not acquire image ({})", r);
        return;
      }
      case final VulkanSwapChainTimedOut r -> {
        LOG.error("Could not acquire image ({})", r);
        return;
      }
    }

    final var graphicsCommandBuffer =
      graphicsCommandBuffers.get(imageIndex);

    /*
     * Wait until the image is available (via the image available semaphore) before writing
     * any color data to the image. When writing has completed, signal that rendering has finished
     * (via the render finished semaphore).
     */

    final var submitInfo =
      VulkanSubmitInfo.builder()
        .addWaitSemaphores(
          VulkanSemaphoreSubmitInfo.builder()
            .setStageMask(Set.of(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT))
            .setSemaphore(imageAvailable)
            .build()
        )
        .addCommandBuffers(
          VulkanCommandBufferSubmitInfo.builder()
            .setCommandBuffer(graphicsCommandBuffer)
            .build()
        )
        .addSignalSemaphores(
          VulkanSemaphoreSubmitInfo.builder()
            .setSemaphore(renderFinished)
            .build()
        )
        .build();

    graphicsQueue.submit(List.of(submitInfo), Optional.empty());

    /*
     * Tell the presentation queue to present.
     */

    final var presentationInfo =
      VulkanPresentInfoKHR.builder()
        .addImageIndices(imageIndex)
        .addSwapChains(swapChain)
        .addWaitSemaphores(renderFinished)
        .build();

    khrSwapchainExt.queuePresent(queuePresentation, presentationInfo);

    /*
     * Wait until the presentation operation has finished before continuing. The reason for doing
     * this is that it's possible to submit new work to the GPU at a vastly quicker rate than it
     * can actually process it, meaning the queue of commands grows indefinitely and gives the
     * appearance of a memory leak. Waiting for the presentation queue is not the most efficient
     * way that this can be handled (because it implies that the GPU is waiting when it could
     * otherwise be rendering the next frame), but it is the simplest.
     */

    queuePresentation.waitIdle();
  }

  private static VulkanShaderModuleType createShaderModule(
    final VulkanLogicalDeviceType device,
    final VulkanTemporaryAllocatorType allocator,
    final byte[] data)
    throws VulkanException
  {
    return allocator.withAllocationBufferInitialized(
      data,
      4L,
      buffer -> device.createShaderModule(
        VulkanShaderModuleCreateInfo.of(
          Set.of(),
          buffer,
          buffer.capacity())));
  }

  private static byte[] readShaderModule(
    final String name)
    throws IOException
  {
    try (var input = HelloVulkan.class.getResourceAsStream(name)) {
      return input.readAllBytes();
    }
  }

  private static VulkanImageViewType createImageViewForImage(
    final VulkanSurfaceFormatKHR surfaceFormat,
    final VulkanLogicalDeviceType device,
    final VulkanImageType image)
  {
    try {
      final var range =
        VulkanImageSubresourceRange.of(
          Set.of(VK_IMAGE_ASPECT_COLOR_BIT),
          0,
          1,
          0,
          1);

      final Set<VulkanImageViewCreateFlag> flags = Set.of();
      return device.createImageView(VulkanImageViewCreateInfo.of(
        flags,
        image,
        VK_IMAGE_VIEW_TYPE_2D,
        surfaceFormat.format(),
        VulkanComponentMapping.of(
          VK_COMPONENT_SWIZZLE_IDENTITY,
          VK_COMPONENT_SWIZZLE_IDENTITY,
          VK_COMPONENT_SWIZZLE_IDENTITY,
          VK_COMPONENT_SWIZZLE_IDENTITY),
        range)
      );
    } catch (final VulkanException e) {
      throw new VulkanUncheckedException(e);
    }
  }

  private static VulkanKHRSwapChainType createSwapChain(
    final VulkanExtKHRSwapChainType khrSwapchainExt,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface,
    final VulkanSurfaceFormatKHR surfaceFormat,
    final VulkanPresentModeKHR surfacePresent,
    final VulkanSurfaceCapabilitiesKHR surfaceCaps,
    final VulkanExtent2D surfaceExtent,
    final VulkanLogicalDeviceType device,
    final VulkanQueueType graphicsQueue,
    final VulkanQueueType presentationQueue)
    throws VulkanException
  {
    final var minimumImageCount =
      pickMinimumImageCount(surfaceCaps);
    final List<VulkanQueueFamilyIndex> queueIndices =
      new ArrayList<>();
    final var imageSharingMode =
      pickImageSharingMode(graphicsQueue, presentationQueue, queueIndices);
    final var imageUsageFlags =
      Set.of(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT);
    final var surfaceAlphaFlags =
      Set.of(VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR);

    LOG.debug(
      "swap chain image count: {}",
      Integer.valueOf(minimumImageCount));
    LOG.debug("swap chain image mode: {}", imageSharingMode);

    final var swapChainCreateInfo =
      VulkanSwapChainCreateInfo.of(
        surface,
        minimumImageCount,
        surfaceFormat.format(),
        surfaceFormat.colorSpace(),
        surfaceExtent,
        1,
        imageUsageFlags,
        imageSharingMode,
        queueIndices,
        surfaceCaps.currentTransform(),
        surfaceAlphaFlags,
        surfacePresent,
        true,
        Optional.empty()
      );

    return khrSwapchainExt.swapChainCreate(device, swapChainCreateInfo);
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

  private static VulkanSharingMode pickImageSharingMode(
    final VulkanQueueType graphicsQueue,
    final VulkanQueueType presentationQueue,
    final List<VulkanQueueFamilyIndex> queueIndices)
  {
    /*
     * If the graphics and presentation queues are separate families, then add the indices of
     * those families into the given list and enable concurrent sharing mode. Otherwise, don't
     * add any indices, and use exclusive sharing mode.
     */

    final var graphicsFamily =
      graphicsQueue.queueFamilyProperties().queueFamilyIndex();
    final var presentationFamily =
      presentationQueue.queueFamilyProperties().queueFamilyIndex();

    if (!Objects.equals(graphicsFamily, presentationFamily)) {
      queueIndices.add(graphicsFamily);
      queueIndices.add(presentationFamily);
      return VK_SHARING_MODE_CONCURRENT;
    }
    return VK_SHARING_MODE_EXCLUSIVE;
  }

  private static int pickMinimumImageCount(
    final VulkanSurfaceCapabilitiesKHR surfaceCaps)
  {
    /*
     * Select the minimum number of images required to do double-buffering.
     */

    final var count = surfaceCaps.minImageCount();
    if (surfaceCaps.maxImageCount() > 0 && count > surfaceCaps.maxImageCount()) {
      return surfaceCaps.maxImageCount();
    }
    return count;
  }

  private static VulkanExtent2D pickExtent(
    final VulkanSurfaceCapabilitiesKHR surfaceCaps)
  {
    /*
     * Work out the extent of the rendered image based on the implementation-defined supported
     * limits.
     */

    if (surfaceCaps.currentExtent().width() != 0xffff_ffff) {
      return surfaceCaps.currentExtent();
    }

    return VulkanExtent2D.of(
      Math.max(
        surfaceCaps.minImageExtent().width(),
        Math.min(surfaceCaps.maxImageExtent().width(), 640)),
      Math.max(
        surfaceCaps.minImageExtent().height(),
        Math.min(surfaceCaps.maxImageExtent().height(), 480))
    );
  }

  private static VulkanPresentModeKHR pickPresentationMode(
    final VulkanPhysicalDeviceType device,
    final VulkanExtKHRSurfaceType khrSurfaceExt,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface)
    throws VulkanException
  {
    /*
     * Pick the best presentation mode available.
     */

    final var modes =
      khrSurfaceExt.surfacePresentModes(device, surface);

    var preferred = VK_PRESENT_MODE_FIFO_KHR;
    for (final var mode : modes) {
      if (mode == VK_PRESENT_MODE_MAILBOX_KHR) {
        return mode;
      }
      if (mode == VK_PRESENT_MODE_IMMEDIATE_KHR) {
        preferred = mode;
      }
    }

    return preferred;
  }

  private static VulkanSurfaceFormatKHR pickSurfaceFormat(
    final VulkanPhysicalDeviceType device,
    final VulkanExtKHRSurfaceType khrSurfaceExt,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface)
    throws VulkanException
  {
    final var formats =
      khrSurfaceExt.surfaceFormats(device, surface);

    /*
     * If there are no formats, try a commonly supported one.
     */

    if (formats.isEmpty()) {
      return VulkanSurfaceFormatKHR.of(
        VK_FORMAT_B8G8R8A8_UNORM,
        VK_COLOR_SPACE_SRGB_NONLINEAR_KHR);
    }

    /*
     * If there's one VK_FORMAT_UNDEFINED format, then this means that the implementation
     * doesn't have a preferred format and anything can be used.
     */

    if (formats.size() == 1) {
      final var format0 = formats.get(0);
      if (format0.format() == VK_FORMAT_UNDEFINED) {
        return VulkanSurfaceFormatKHR.of(
          VK_FORMAT_B8G8R8A8_UNORM,
          VK_COLOR_SPACE_SRGB_NONLINEAR_KHR);
      }
    }

    /*
     * Otherwise, look for a linear BGRA unsigned normalized format, with an SRGB color space.
     */

    for (final var format : formats) {
      if (format.format() == VK_FORMAT_B8G8R8A8_UNORM
          && format.colorSpace() == VK_COLOR_SPACE_SRGB_NONLINEAR_KHR) {
        return format;
      }
    }

    /*
     * Otherwise, use whatever was first.
     */

    return formats.get(0);
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
        .filter(HelloVulkan::physicalDeviceHasSwapChainExtension)
        .filter(HelloVulkan::physicalDeviceHasGraphicsQueue)
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
        "checking device \"{}\" for VK_KHR_swapchain support",
        device.properties().name());

      return device.extensions(Optional.empty()).containsKey("VK_KHR_swapchain");
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
        "checking device \"{}\" for presentation support",
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

    final var window =
      GLFW.glfwCreateWindow(640, 480, "com.io7m.jcoronado.tests.Demo", 0L, 0L);
    if (window == 0L) {
      throw new IllegalStateException("Could not create window");
    }
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

  @Override
  public void execute()
    throws VulkanException, IOException
  {
    GLFW_ERROR_CALLBACK.set();

    final var hostAllocatorMain =
      new VulkanLWJGLHostAllocatorJeMalloc();
    final var hostAllocatorTracker =
      new VulkanHostAllocatorTracker(hostAllocatorMain);

    final var finished = new AtomicBoolean(false);

    /*
     * Create an allocator for temporary objects.
     */

    final var alloc =
      VulkanLWJGLTemporaryAllocator.create();
    final Supplier<VulkanException> exceptionSupplier =
      () -> new VulkanResourceException("Could not close one or more resources.");

    try (var resources =
           CloseableCollection.create(exceptionSupplier)) {

      final var window = createWindow();

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
        Set.of(
          "VK_LAYER_KHRONOS_validation"
        );

      final var requiredExtensions = new HashSet<String>();
      requiredExtensions.addAll(requiredGLFWExtensions());
      requiredExtensions.add("VK_EXT_debug_utils");

      availableExtensions
        .forEach(HelloVulkan::showInstanceAvailableExtension);
      availableLayers
        .forEach(HelloVulkan::showInstanceAvailableLayer);
      requiredExtensions
        .forEach(HelloVulkan::showInstanceRequiredExtension);
      requiredLayers
        .forEach(HelloVulkan::showInstanceRequiredLayer);

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
              VulkanVersions.encode(instances.minimumRequiredVersion())))
          .setEnabledExtensions(enableExtensions)
          .setEnabledLayers(enableLayers)
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
        resources.add(pickPhysicalDeviceOrAbort(
          khrSurfaceExt,
          surface,
          physicalDevices));

      LOG.debug("physical device: {}", physicalDevice);

      /*
       * Require the VK_KHR_get_memory_requirements2 extension in order to use VMA.
       */

      final var deviceExtensions =
        physicalDevice.extensions(Optional.empty());

      deviceExtensions.forEach(HelloVulkan::showPhysicalDeviceAvailableExtension);
      if (!deviceExtensions.containsKey("VK_KHR_get_memory_requirements2")) {
        throw new VulkanMissingRequiredExtensionsException(
          Set.of("VK_KHR_get_memory_requirements2"),
          "Missing required extension");
      }

      /*
       * Determine the format, presentation mode, and size of the surface that will be
       * used for rendering.
       */

      final var surfaceFormat =
        pickSurfaceFormat(physicalDevice, khrSurfaceExt, surface);
      final var surfacePresent =
        pickPresentationMode(physicalDevice, khrSurfaceExt, surface);
      final var surfaceCaps =
        khrSurfaceExt.surfaceCapabilities(physicalDevice, surface);
      final var surfaceExtent =
        pickExtent(surfaceCaps);

      LOG.debug("selected surface format: {}", surfaceFormat);
      LOG.debug("selected surface presentation mode: {}", surfacePresent);
      LOG.debug("selected surface extent: {}", surfaceExtent);

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
              .addEnabledExtensions("VK_KHR_get_memory_requirements2")
              .build())
        );

      LOG.debug("logical device: {}", device);

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

      LOG.debug("graphics queue: {}", graphicsQueue);
      LOG.debug("presentation queue: {}", presentationQueue);

      /*
       * Create a swap chain used to display images onscreen.
       */

      final var khrSwapchainExt =
        getSwapChainExtension(device);

      final var swapChain =
        resources.add(createSwapChain(
          khrSwapchainExt,
          surface,
          surfaceFormat,
          surfacePresent,
          surfaceCaps,
          surfaceExtent,
          device,
          graphicsQueue,
          presentationQueue));

      final var swapChainImages =
        swapChain.images();
      final var swapChainViews =
        swapChainImages.stream()
          .map(image -> createImageViewForImage(surfaceFormat, device, image))
          .map(resources::add)
          .collect(Collectors.toList());

      /*
       * Load a shader module.
       */

      final var data =
        readShaderModule("shaders.spv");
      final var shaders =
        resources.add(createShaderModule(device, alloc, data));

      /*
       * Configure the render pass.
       *
       * This specifies that:
       *
       * We don't want multisampling.
       *
       * Reads from the image attachment should behave as if the attachment has been cleared before
       * rendering.
       *
       * Writes to the image attachment should be stored in the image.
       *
       * We don't care about reads or writes to the stencil buffer.
       *
       * We don't care about the initial layout of the image.
       *
       * The final layout of the image must be something usable for presentation to the screen.
       */

      final var colorAttachmentDescription =
        VulkanAttachmentDescription.builder()
          .setFormat(surfaceFormat.format())
          .setSamples(VK_SAMPLE_COUNT_1_BIT)
          .setLoadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
          .setStoreOp(VK_ATTACHMENT_STORE_OP_STORE)
          .setStencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
          .setStencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
          .setInitialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
          .setFinalLayout(VK_IMAGE_LAYOUT_PRESENT_SRC_KHR)
          .build();

      final var colorAttachmentReference =
        VulkanAttachmentReference.builder()
          .setAttachment(0)
          .setLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
          .build();

      final var subpassDescription =
        VulkanSubpassDescription.builder()
          .setPipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS)
          .addColorAttachments(colorAttachmentReference)
          .build();

      final var subpassDependency =
        VulkanSubpassDependency.builder()
          .setSrcSubpass(VulkanSubpassDependencyType.EXTERNAL)
          .addSrcStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
          .setDstSubpass(0)
          .addDstStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
          .addDstAccessMask(VK_ACCESS_COLOR_ATTACHMENT_READ_BIT)
          .addDstAccessMask(VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT)
          .build();

      final var renderPassCreateInfo =
        VulkanRenderPassCreateInfo.builder()
          .addAttachments(colorAttachmentDescription)
          .addSubpasses(subpassDescription)
          .addDependencies(subpassDependency)
          .build();

      final var renderPass =
        resources.add(device.createRenderPass(renderPassCreateInfo));

      /*
       * Configure the rendering pipeline.
       */

      final var vertexStageInfo =
        VulkanPipelineShaderStageCreateInfo.builder()
          .setStage(VK_SHADER_STAGE_VERTEX_BIT)
          .setModule(shaders)
          .setShaderEntryPoint("R3_clip_triangle_vert_main")
          .build();

      final var fragmentStageInfo =
        VulkanPipelineShaderStageCreateInfo.builder()
          .setStage(VK_SHADER_STAGE_FRAGMENT_BIT)
          .setModule(shaders)
          .setShaderEntryPoint("R3_clip_triangle_frag_main")
          .build();

      final var vertexBindingDescription =
        VulkanVertexInputBindingDescription.builder()
          .setBinding(0)
          .setInputRate(VK_VERTEX_INPUT_RATE_VERTEX)
          .setStride(VERTEX_SIZE)
          .build();

      final var vertexPositionAttribute =
        VulkanVertexInputAttributeDescription.builder()
          .setBinding(0)
          .setLocation(0)
          .setFormat(VK_FORMAT_R32G32_SFLOAT)
          .setOffset(0)
          .build();

      final var vertexColorAttribute =
        VulkanVertexInputAttributeDescription.builder()
          .setBinding(0)
          .setLocation(1)
          .setFormat(VK_FORMAT_R32G32B32_SFLOAT)
          .setOffset(VERTEX_POSITION_SIZE)
          .build();

      final var vertexInputInfo =
        VulkanPipelineVertexInputStateCreateInfo.builder()
          .addVertexBindingDescriptions(vertexBindingDescription)
          .addVertexAttributeDescriptions(vertexPositionAttribute)
          .addVertexAttributeDescriptions(vertexColorAttribute)
          .build();

      final var inputAssemblyInfo =
        VulkanPipelineInputAssemblyStateCreateInfo.builder()
          .setTopology(VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST)
          .setPrimitiveRestartEnable(false)
          .build();

      final var viewportStateInfo =
        VulkanPipelineViewportStateCreateInfo.builder()
          .addScissors(VulkanRectangle2D.of(
            VulkanOffset2D.of(0, 0),
            surfaceExtent))
          .addViewports(VulkanViewport.of(
            0.0f,
            0.0f,
            (float) surfaceExtent.width(),
            (float) surfaceExtent.height(),
            0.0f,
            1.0f))
          .build();

      final var rasterizer =
        VulkanPipelineRasterizationStateCreateInfo.builder()
          .setCullMode(EnumSet.of(VK_CULL_MODE_BACK_BIT))
          .setDepthBiasClamp(0.0f)
          .setDepthBiasConstantFactor(0.0f)
          .setDepthBiasEnable(false)
          .setDepthBiasSlopeFactor(0.0f)
          .setDepthClampEnable(false)
          .setFrontFace(VK_FRONT_FACE_CLOCKWISE)
          .setLineWidth(1.0f)
          .setPolygonMode(VK_POLYGON_MODE_FILL)
          .setRasterizerDiscardEnable(false)
          .build();

      final var blendState =
        VulkanPipelineColorBlendAttachmentState.builder()
          .setEnable(false)
          .build();

      final var colorBlending =
        VulkanPipelineColorBlendStateCreateInfo.builder()
          .addAttachments(blendState)
          .build();

      final var pipelineLayoutInfo =
        VulkanPipelineLayoutCreateInfo.builder()
          .build();

      final var pipelineLayout =
        resources.add(device.createPipelineLayout(pipelineLayoutInfo));

      final var multisample =
        VulkanPipelineMultisampleStateCreateInfo.builder()
          .setAlphaToCoverageEnable(false)
          .setAlphaToOneEnable(false)
          .setMinSampleShading(1.0f)
          .setRasterizationSamples(VK_SAMPLE_COUNT_1_BIT)
          .setSampleShadingEnable(false)
          .build();

      final var pipelineInfo =
        VulkanGraphicsPipelineCreateInfo.builder()
          .addStages(fragmentStageInfo)
          .addStages(vertexStageInfo)
          .setColorBlendState(colorBlending)
          .setInputAssemblyState(inputAssemblyInfo)
          .setLayout(pipelineLayout)
          .setMultisampleState(multisample)
          .setRasterizationState(rasterizer)
          .setRenderPass(renderPass)
          .setSubpass(0)
          .setVertexInputState(vertexInputInfo)
          .setViewportState(viewportStateInfo)
          .build();

      final var pipeline =
        resources.add(device.createGraphicsPipeline(pipelineInfo));

      /*
       * Create framebuffers.
       */

      final List<VulkanFramebufferType> framebuffers =
        new ArrayList<>(swapChainImages.size());

      for (var index = 0; index < swapChainImages.size(); ++index) {
        final var framebufferInfo =
          VulkanFramebufferCreateInfo.builder()
            .addAttachments(swapChainViews.get(index))
            .setHeight(surfaceExtent.height())
            .setWidth(surfaceExtent.width())
            .setLayers(1)
            .setRenderPass(renderPass)
            .build();

        final var framebuffer =
          resources.add(device.createFramebuffer(framebufferInfo));

        framebuffers.add(framebuffer);
      }

      /*
       * Create vertex buffers.
       */

      final var vertexBufferCreateInfo =
        VulkanBufferCreateInfo.builder()
          .setSize(3L * VERTEX_SIZE)
          .addUsageFlags(VK_BUFFER_USAGE_VERTEX_BUFFER_BIT)
          .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
          .build();

      final var vertexBuffer =
        resources.add(device.createBuffer(vertexBufferCreateInfo));

      final var vertexBufferRequirements =
        device.getBufferMemoryRequirements(vertexBuffer);

      LOG.debug(
        "buffer memory requirements: size {} alignment {} type 0x{}",
        Long.valueOf(vertexBufferRequirements.size()),
        Long.valueOf(vertexBufferRequirements.alignment()),
        Integer.toUnsignedString(
          vertexBufferRequirements.memoryTypeBits(),
          16));

      final var vertexBufferMemoryType =
        physicalDevice.memory()
          .findSuitableMemoryType(
            vertexBufferRequirements,
            Set.of(
              VK_MEMORY_PROPERTY_HOST_COHERENT_BIT,
              VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT));

      LOG.debug("buffer memory type: {}", vertexBufferMemoryType);

      final var vertexBufferAllocation =
        VulkanMemoryAllocateInfo.builder()
          .setMemoryTypeIndex(vertexBufferMemoryType.index())
          .setSize(vertexBufferRequirements.size())
          .build();

      final var vertexBufferMemory =
        resources.add(device.allocateMemory(vertexBufferAllocation));

      device.bindBufferMemory(vertexBuffer, vertexBufferMemory, 0L);

      /*
       * Populate vertex buffer by mapping the memory and writing to it. Closing the mapped
       * memory will automatically unmap it.
       */

      try (var vertexMapped =
             device.mapMemory(
               vertexBufferMemory,
               0L,
               3L * VERTEX_SIZE,
               Set.of())) {

        final var buffer = vertexMapped.asByteBuffer();
        buffer.putFloat(0, 0.0f);
        buffer.putFloat(4, -0.5f);
        buffer.putFloat(8, 1.0f);
        buffer.putFloat(12, 0.0f);
        buffer.putFloat(16, 0.0f);

        buffer.putFloat(20, 0.5f);
        buffer.putFloat(24, 0.5f);
        buffer.putFloat(28, 0.0f);
        buffer.putFloat(32, 1.0f);
        buffer.putFloat(36, 0.0f);

        buffer.putFloat(40, -0.5f);
        buffer.putFloat(44, 0.5f);
        buffer.putFloat(48, 0.0f);
        buffer.putFloat(52, 0.0f);
        buffer.putFloat(56, 1.0f);
      }

      /*
       * Create a command pool and buffers. If the graphics and presentation queues are separate,
       * then separate command pools are required.
       */

      final var graphicsPoolInfo =
        VulkanCommandPoolCreateInfo.builder()
          .setQueueFamilyIndex(graphicsQueue.queueFamilyIndex())
          .build();

      final var graphicsCommandPool =
        resources.add(device.createCommandPool(graphicsPoolInfo));

      final var commandBufferCount = swapChain.images().size();
      final var graphicsCommandBufferInfo =
        VulkanCommandBufferCreateInfo.builder()
          .setCount(commandBufferCount)
          .setLevel(VK_COMMAND_BUFFER_LEVEL_PRIMARY)
          .setPool(graphicsCommandPool)
          .build();

      final var graphicsCommandBuffers =
        device.createCommandBuffers(graphicsCommandBufferInfo);

      /*
       * Create a pair of semaphores for synchronizing command execution.
       */

      final var renderFinished =
        resources.add(device.createBinarySemaphore(
          VulkanSemaphoreBinaryCreateInfo.builder().build()
        ));

      final var imageAvailable =
        resources.add(device.createBinarySemaphore(
          VulkanSemaphoreBinaryCreateInfo.builder().build()
        ));

      /*
       * Start recording commands.
       */

      for (var index = 0; index < commandBufferCount; ++index) {
        final var framebuffer = framebuffers.get(index);

        final var graphicsCommandBuffer =
          graphicsCommandBuffers.get(index);

        final var beginInfo =
          VulkanCommandBufferBeginInfo.builder()
            .addFlags(VK_COMMAND_BUFFER_USAGE_SIMULTANEOUS_USE_BIT)
            .build();

        final var renderInfo =
          VulkanRenderPassBeginInfo.builder()
            .setFramebuffer(framebuffer)
            .setRenderArea(VulkanRectangle2D.of(
              VulkanOffset2D.of(0, 0),
              surfaceExtent))
            .setRenderPass(renderPass)
            .addClearValues(VulkanClearValueColorFloatingPoint.of(
              0.0f,
              0.0f,
              0.0f,
              1.0f))
            .build();

        graphicsCommandBuffer.beginCommandBuffer(beginInfo);
        graphicsCommandBuffer.beginRenderPass(
          renderInfo, VK_SUBPASS_CONTENTS_INLINE);
        graphicsCommandBuffer.bindPipeline(
          VK_PIPELINE_BIND_POINT_GRAPHICS, pipeline);
        graphicsCommandBuffer.bindVertexBuffers(
          0, 1, List.of(vertexBuffer), List.of(Long.valueOf(0L)));
        graphicsCommandBuffer.draw(3, 1, 0, 0);
        graphicsCommandBuffer.endRenderPass();
        graphicsCommandBuffer.endCommandBuffer();
      }

      /*
       * Start rendering frames.
       */

      var frame = 0;
      while (!finished.get()) {
        GLFW.glfwPollEvents();

        if (GLFW.glfwWindowShouldClose(window)) {
          finished.set(true);
        }

        drawFrame(
          khrSwapchainExt,
          swapChain,
          imageAvailable,
          renderFinished,
          graphicsCommandBuffers,
          graphicsQueue,
          presentationQueue);

        if (frame % 10_000 == 0) {
          LOG.debug(
            "allocated command scoped memory:  {} octets",
            Long.valueOf(hostAllocatorTracker.allocatedCommandScopeOctets()));
          LOG.debug(
            "allocated object scoped memory:   {} octets",
            Long.valueOf(hostAllocatorTracker.allocatedObjectScopeOctets()));
          LOG.debug(
            "allocated cache scoped memory:    {} octets",
            Long.valueOf(hostAllocatorTracker.allocatedCacheScopeOctets()));
          LOG.debug(
            "allocated device scoped memory:   {} octets",
            Long.valueOf(hostAllocatorTracker.allocatedDeviceScopeOctets()));
          LOG.debug(
            "allocated instance scoped memory: {} octets",
            Long.valueOf(hostAllocatorTracker.allocatedInstanceScopeOctets()));
        }
        ++frame;
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
}
