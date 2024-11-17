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
import com.io7m.jcoronado.api.VulkanBufferImageCopy;
import com.io7m.jcoronado.api.VulkanBufferType;
import com.io7m.jcoronado.api.VulkanClearValueColorFloatingPoint;
import com.io7m.jcoronado.api.VulkanCommandBufferBeginInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanCommandPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandPoolType;
import com.io7m.jcoronado.api.VulkanComponentMappingType;
import com.io7m.jcoronado.api.VulkanDescriptorBufferInfo;
import com.io7m.jcoronado.api.VulkanDescriptorImageInfo;
import com.io7m.jcoronado.api.VulkanDescriptorPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanDescriptorPoolSize;
import com.io7m.jcoronado.api.VulkanDescriptorSetAllocateInfo;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutBinding;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutType;
import com.io7m.jcoronado.api.VulkanDescriptorSetType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanExtensions;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanFormat;
import com.io7m.jcoronado.api.VulkanFramebufferCreateInfo;
import com.io7m.jcoronado.api.VulkanFramebufferType;
import com.io7m.jcoronado.api.VulkanGraphicsPipelineCreateInfo;
import com.io7m.jcoronado.api.VulkanImageCreateInfo;
import com.io7m.jcoronado.api.VulkanImageKind;
import com.io7m.jcoronado.api.VulkanImageLayout;
import com.io7m.jcoronado.api.VulkanImageMemoryBarrier;
import com.io7m.jcoronado.api.VulkanImageSubresourceLayers;
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
import com.io7m.jcoronado.api.VulkanMissingRequiredExtensionsException;
import com.io7m.jcoronado.api.VulkanOffset2D;
import com.io7m.jcoronado.api.VulkanOffset3D;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendAttachmentState;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineInputAssemblyStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineLayoutType;
import com.io7m.jcoronado.api.VulkanPipelineMultisampleStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineRasterizationStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineShaderStageCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineStageFlag;
import com.io7m.jcoronado.api.VulkanPipelineType;
import com.io7m.jcoronado.api.VulkanPipelineVertexInputStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineViewportStateCreateInfo;
import com.io7m.jcoronado.api.VulkanQueueFamilyIndex;
import com.io7m.jcoronado.api.VulkanQueueIndex;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanRectangle2D;
import com.io7m.jcoronado.api.VulkanRenderPassBeginInfo;
import com.io7m.jcoronado.api.VulkanRenderPassCreateInfo;
import com.io7m.jcoronado.api.VulkanRenderPassType;
import com.io7m.jcoronado.api.VulkanResourceException;
import com.io7m.jcoronado.api.VulkanSamplerCreateInfo;
import com.io7m.jcoronado.api.VulkanSamplerType;
import com.io7m.jcoronado.api.VulkanSemaphoreCreateInfo;
import com.io7m.jcoronado.api.VulkanSemaphoreType;
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
import com.io7m.jcoronado.api.VulkanWriteDescriptorSet;
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
import com.io7m.jcoronado.lwjgl.VMALWJGLAllocatorProvider;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLHostAllocatorJeMalloc;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLTemporaryAllocator;
import com.io7m.jcoronado.vma.VMAAllocationCreateInfo;
import com.io7m.jcoronado.vma.VMAAllocationResult;
import com.io7m.jcoronado.vma.VMAAllocatorCreateInfo;
import com.io7m.jcoronado.vma.VMAAllocatorType;
import com.io7m.jmulticlose.core.CloseableCollection;
import com.io7m.jmulticlose.core.CloseableCollectionType;
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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_COLOR_ATTACHMENT_READ_BIT;
import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT;
import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_SHADER_READ_BIT;
import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_TRANSFER_WRITE_BIT;
import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_CLEAR;
import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_STORE;
import static com.io7m.jcoronado.api.VulkanBorderColor.VK_BORDER_COLOR_FLOAT_OPAQUE_WHITE;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_INDEX_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_TRANSFER_SRC_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanCommandBufferLevel.VK_COMMAND_BUFFER_LEVEL_PRIMARY;
import static com.io7m.jcoronado.api.VulkanCommandBufferUsageFlag.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT;
import static com.io7m.jcoronado.api.VulkanCommandBufferUsageFlag.VK_COMMAND_BUFFER_USAGE_SIMULTANEOUS_USE_BIT;
import static com.io7m.jcoronado.api.VulkanCullModeFlag.VK_CULL_MODE_BACK_BIT;
import static com.io7m.jcoronado.api.VulkanDescriptorType.VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER;
import static com.io7m.jcoronado.api.VulkanDescriptorType.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER;
import static com.io7m.jcoronado.api.VulkanFilter.VK_FILTER_NEAREST;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_B8G8R8A8_UNORM;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R32G32B32_SFLOAT;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R32G32_SFLOAT;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R8G8B8A8_UNORM;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_UNDEFINED;
import static com.io7m.jcoronado.api.VulkanFrontFace.VK_FRONT_FACE_CLOCKWISE;
import static com.io7m.jcoronado.api.VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED;
import static com.io7m.jcoronado.api.VulkanImageTiling.VK_IMAGE_TILING_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_SAMPLED_BIT;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_TRANSFER_DST_BIT;
import static com.io7m.jcoronado.api.VulkanImageViewKind.VK_IMAGE_VIEW_TYPE_2D;
import static com.io7m.jcoronado.api.VulkanIndexType.VK_INDEX_TYPE_UINT16;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineBindPoint.VK_PIPELINE_BIND_POINT_GRAPHICS;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_TRANSFER_BIT;
import static com.io7m.jcoronado.api.VulkanPolygonMode.VK_POLYGON_MODE_FILL;
import static com.io7m.jcoronado.api.VulkanPrimitiveTopology.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST;
import static com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag.VK_QUEUE_GRAPHICS_BIT;
import static com.io7m.jcoronado.api.VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT;
import static com.io7m.jcoronado.api.VulkanSamplerAddressMode.VK_SAMPLER_ADDRESS_MODE_REPEAT;
import static com.io7m.jcoronado.api.VulkanSamplerMipmapMode.VK_SAMPLER_MIPMAP_MODE_LINEAR;
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
import static com.io7m.jcoronado.vma.VMAMemoryUsage.VMA_MEMORY_USAGE_CPU_ONLY;
import static com.io7m.jcoronado.vma.VMAMemoryUsage.VMA_MEMORY_USAGE_GPU_ONLY;

public final class HelloVulkanWithVMA implements ExampleType
{
  private static final Logger LOG = LoggerFactory.getLogger(HelloVulkanWithVMA.class);

  private static final GLFWErrorCallback GLFW_ERROR_CALLBACK =
    GLFWErrorCallback.createPrint();

  private static final int VERTEX_POSITION_SIZE = 2 * 4;
  private static final int VERTEX_COLOR_OFFSET = VERTEX_POSITION_SIZE;
  private static final int VERTEX_COLOR_SIZE = 3 * 4;
  private static final int VERTEX_UV_OFFSET = VERTEX_COLOR_OFFSET + VERTEX_COLOR_SIZE;
  private static final int VERTEX_UV_SIZE = 2 * 4;
  private static final int VERTEX_SIZE = VERTEX_POSITION_SIZE + VERTEX_COLOR_SIZE + VERTEX_UV_SIZE;
  private static final long UNIFORM_BUFFER_SIZE = 4L;
  private static final List<Vertex> VERTICES =
    List.of(
      new Vertex(0.0, -0.5, 1.0, 0.0, 0.0, 0.0, 0.0),
      new Vertex(0.5, 0.5, 0.0, 1.0, 0.0, 1.0, 0.0),
      new Vertex(-0.5, 0.5, 0.0, 0.0, 1.0, 1.0, 1.0));

  public HelloVulkanWithVMA()
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

  /**
   * Create a texture by:
   *
   * 1. Allocating a staging buffer on the CPU 2. Populating the staging buffer
   * 3. Allocating an image on the GPU 4. Copying the staging buffer to the
   * image 5. Creating an image view for the image 6. Creating a sampler for the
   * image
   */

  private static Texture createTexture(
    final CloseableCollectionType<VulkanException> resources,
    final VulkanLogicalDeviceType device,
    final VMAAllocatorType vma_allocator,
    final VulkanQueueType graphics_queue,
    final VulkanCommandPoolType transfer_command_pool)
    throws VulkanException
  {
    final var texture_width = 256;
    final var texture_height = 256;
    final var texture_bpp = 4;
    final var texture_format = VK_FORMAT_R8G8B8A8_UNORM;

    final var staging_result =
      createTextureStagingBuffer(
        resources,
        vma_allocator,
        texture_width,
        texture_height);
    final var gpu_result =
      createTextureGPU(
        resources,
        vma_allocator,
        texture_width,
        texture_height,
        texture_format);

    final var gpu_image = gpu_result.result();
    final var cpu_buffer = staging_result.result();

    transitionImageLayout(
      device,
      graphics_queue,
      transfer_command_pool,
      gpu_image,
      VK_IMAGE_LAYOUT_UNDEFINED,
      VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL);

    copyBufferToImage(
      device,
      graphics_queue,
      transfer_command_pool,
      cpu_buffer,
      gpu_image,
      texture_width,
      texture_height);

    transitionImageLayout(
      device,
      graphics_queue,
      transfer_command_pool,
      gpu_image,
      VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL,
      VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL);

    final var image_view =
      device.createImageView(
        VulkanImageViewCreateInfo.builder()
          .setComponents(VulkanComponentMappingType.identity())
          .setFormat(texture_format)
          .setImage(gpu_image)
          .setViewType(VK_IMAGE_VIEW_TYPE_2D)
          .setSubresourceRange(
            VulkanImageSubresourceRange.builder()
              .setLayerCount(1)
              .setBaseArrayLayer(0)
              .setLevelCount(1)
              .setBaseMipLevel(0)
              .addAspectMask(VK_IMAGE_ASPECT_COLOR_BIT)
              .build())
          .build());
    resources.add(image_view);

    final var sampler =
      device.createSampler(
        VulkanSamplerCreateInfo.builder()
          .setAddressModeU(VK_SAMPLER_ADDRESS_MODE_REPEAT)
          .setAddressModeV(VK_SAMPLER_ADDRESS_MODE_REPEAT)
          .setAddressModeW(VK_SAMPLER_ADDRESS_MODE_REPEAT)
          .setBorderColor(VK_BORDER_COLOR_FLOAT_OPAQUE_WHITE)
          .setMagFilter(VK_FILTER_NEAREST)
          .setMinFilter(VK_FILTER_NEAREST)
          .setMipmapMode(VK_SAMPLER_MIPMAP_MODE_LINEAR)
          .setMaxLod(0.0f)
          .setMinLod(0.0f)
          .setMipLodBias(0.0f)
          .setUnnormalizedCoordinates(false)
          .build());
    resources.add(sampler);

    return new Texture(image_view, sampler);
  }

  private static void copyBufferToImage(
    final VulkanLogicalDeviceType device,
    final VulkanQueueType queue,
    final VulkanCommandPoolType command_pool,
    final VulkanBufferType source,
    final VulkanImageType target,
    final int texture_width,
    final int texture_height)
    throws VulkanException
  {
    try (var commands = device.createCommandBuffer(
      command_pool,
      VK_COMMAND_BUFFER_LEVEL_PRIMARY)) {
      commands.beginCommandBuffer(VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);

      commands.copyBufferToImage(
        source,
        target,
        VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL,
        List.of(VulkanBufferImageCopy.builder()
                  .setBufferImageHeight(0)
                  .setBufferOffset(0L)
                  .setBufferRowLength(0)
                  .setImageExtent(VulkanExtent3D.of(
                    texture_width,
                    texture_height,
                    1))
                  .setImageOffset(VulkanOffset3D.of(0, 0, 0))
                  .setImageSubresource(
                    VulkanImageSubresourceLayers.builder()
                      .addAspectMask(VK_IMAGE_ASPECT_COLOR_BIT)
                      .setBaseArrayLayer(0)
                      .setLayerCount(1)
                      .setMipLevel(0)
                      .build())
                  .build()));

      commands.endCommandBuffer();

      LOG.trace("submitting image copy");
      queue.submit(List.of(VulkanSubmitInfo.builder()
                             .addCommandBuffers(commands)
                             .build()));

      LOG.trace("submitting waiting for image copy");
      queue.waitIdle();
    }
  }

  private static void transitionImageLayout(
    final VulkanLogicalDeviceType device,
    final VulkanQueueType queue,
    final VulkanCommandPoolType command_pool,
    final VulkanImageType image,
    final VulkanImageLayout old_layout,
    final VulkanImageLayout new_layout)
    throws VulkanException
  {
    final var barrier_builder =
      VulkanImageMemoryBarrier.builder()
        .setSourceQueueFamilyIndex(new VulkanQueueFamilyIndex(-1))
        .setTargetQueueFamilyIndex(new VulkanQueueFamilyIndex(-1))
        .setImage(image)
        .setSubresourceRange(
          VulkanImageSubresourceRange.builder()
            .addAspectMask(VK_IMAGE_ASPECT_COLOR_BIT)
            .setBaseMipLevel(0)
            .setBaseArrayLayer(0)
            .setLevelCount(1)
            .setLayerCount(1)
            .build())
        .setOldLayout(old_layout)
        .setNewLayout(new_layout);

    final Set<VulkanPipelineStageFlag> source_stage;
    final Set<VulkanPipelineStageFlag> target_stage;

    if (old_layout == VK_IMAGE_LAYOUT_UNDEFINED
      && new_layout == VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL) {
      barrier_builder.setSourceAccessMask(Set.of());
      barrier_builder.setTargetAccessMask(Set.of(VK_ACCESS_TRANSFER_WRITE_BIT));
      source_stage = Set.of(VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT);
      target_stage = Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT);
    } else if (old_layout == VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL
      && new_layout == VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL) {
      barrier_builder.setSourceAccessMask(Set.of(VK_ACCESS_TRANSFER_WRITE_BIT));
      barrier_builder.setTargetAccessMask(Set.of(VK_ACCESS_SHADER_READ_BIT));
      source_stage = Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT);
      target_stage = Set.of(VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT);
    } else {
      throw new UnsupportedOperationException("Unsupported layout transition");
    }

    try (var commands = device.createCommandBuffer(
      command_pool,
      VK_COMMAND_BUFFER_LEVEL_PRIMARY)) {
      commands.beginCommandBuffer(VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);

      commands.pipelineBarrier(
        source_stage,
        target_stage,
        Set.of(),
        List.of(),
        List.of(),
        List.of(barrier_builder.build()));

      commands.endCommandBuffer();

      LOG.trace(
        "submitting image transition {} -> {} barrier",
        old_layout,
        new_layout);
      queue.submit(List.of(VulkanSubmitInfo.builder()
                             .addCommandBuffers(commands)
                             .build()));

      LOG.trace("waiting for image transition barrier");
      queue.waitIdle();
    }
  }

  /**
   * Create a GPU-side texture.
   */

  private static VMAAllocationResult<VulkanImageType> createTextureGPU(
    final CloseableCollectionType<VulkanException> resources,
    final VMAAllocatorType vma_allocator,
    final int texture_width,
    final int texture_height,
    final VulkanFormat format)
    throws VulkanException
  {
    LOG.trace(
      "creating {}x{} gpu texture",
      Integer.valueOf(texture_width),
      Integer.valueOf(texture_height));

    final var texture_buffer_create_info =
      VulkanImageCreateInfo.builder()
        .setArrayLayers(1)
        .setExtent(VulkanExtent3D.of(texture_width, texture_height, 1))
        .setFormat(format)
        .setImageType(VulkanImageKind.VK_IMAGE_TYPE_2D)
        .setInitialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
        .setMipLevels(1)
        .setSamples(Set.of(VK_SAMPLE_COUNT_1_BIT))
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .setTiling(VK_IMAGE_TILING_OPTIMAL)
        .addUsage(VK_IMAGE_USAGE_SAMPLED_BIT)
        .addUsage(VK_IMAGE_USAGE_TRANSFER_DST_BIT)
        .build();

    final var vma_gpu_alloc_create_info =
      VMAAllocationCreateInfo.builder()
        .setUsage(VMA_MEMORY_USAGE_GPU_ONLY)
        .addRequiredFlags(VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT)
        .setMemoryTypeBits(0L)
        .build();

    final var texture_result =
      vma_allocator.createImage(
        vma_gpu_alloc_create_info,
        texture_buffer_create_info);

    resources.add(texture_result.result());
    return texture_result;
  }

  /**
   * Create a CPU-side staging buffer for a texture.
   */

  private static VMAAllocationResult<VulkanBufferType> createTextureStagingBuffer(
    final CloseableCollectionType<VulkanException> resources,
    final VMAAllocatorType vma_allocator,
    final int texture_width,
    final int texture_height)
    throws VulkanException
  {
    LOG.trace(
      "creating {}x{} texture staging buffer",
      Integer.valueOf(texture_width),
      Integer.valueOf(texture_height));

    final var vma_cpu_alloc_create_info =
      VMAAllocationCreateInfo.builder()
        .setUsage(VMA_MEMORY_USAGE_CPU_ONLY)
        .addRequiredFlags(VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT)
        .setMemoryTypeBits(0L)
        .build();

    final var size =
      Integer.toUnsignedLong(texture_width) * Integer.toUnsignedLong(
        texture_height) * 4L;

    final var texture_staging_buffer_create_info =
      VulkanBufferCreateInfo.builder()
        .setSize(size)
        .addUsageFlags(VK_BUFFER_USAGE_TRANSFER_SRC_BIT)
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .build();

    final var staging_buffer_result =
      vma_allocator.createBuffer(
        vma_cpu_alloc_create_info,
        texture_staging_buffer_create_info);

    resources.add(staging_buffer_result.result());

    final var rng = new Random(23L);
    try (var map = vma_allocator.mapMemory(staging_buffer_result.allocation())) {
      final var bytes = map.asByteBuffer();
      for (var index = 0; (long) index < size; ++index) {
        bytes.put(index, (byte) (rng.nextInt(256) & 0xff));
      }
      map.flush();
    }

    return staging_buffer_result;
  }

  /**
   * Create descriptor sets.
   */

  private static List<VulkanDescriptorSetType> createDescriptorSets(
    final CloseableCollectionType<VulkanException> resources,
    final VulkanLogicalDeviceType device,
    final VulkanKHRSwapChainType swapChain,
    final ArrayList<VulkanBufferType> uniformBuffers,
    final Texture texture,
    final VulkanDescriptorSetLayoutType descriptorSetLayout)
    throws VulkanException
  {
    final var imageCount = swapChain.images().size();

    LOG.trace(
      "swap chain has {} images, allocating {} descriptor sets",
      imageCount,
      imageCount);

    final var descriptorPoolUniformBuffer =
      VulkanDescriptorPoolSize.of(
        VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER,
        imageCount);
    final var descriptorPoolSampler =
      VulkanDescriptorPoolSize.of(
        VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER,
        imageCount);

    final var descriptorPoolCreateInfo =
      VulkanDescriptorPoolCreateInfo.builder()
        .setMaxSets(imageCount)
        .addPoolSizes(descriptorPoolUniformBuffer)
        .addPoolSizes(descriptorPoolSampler)
        .build();

    final var descriptorPool =
      resources.add(device.createDescriptorPool(descriptorPoolCreateInfo));

    final var descriptorSetLayouts =
      IntStream.range(0, imageCount)
        .mapToObj(ignore -> descriptorSetLayout)
        .collect(Collectors.toList());

    final var descriptorSets =
      device.allocateDescriptorSets(
        VulkanDescriptorSetAllocateInfo.builder()
          .setSetLayouts(descriptorSetLayouts)
          .setDescriptorPool(descriptorPool)
          .build());

    for (var index = 0; index < imageCount; ++index) {
      final var uniformBufferInfo =
        VulkanDescriptorBufferInfo.builder()
          .setBuffer(uniformBuffers.get(index))
          .setOffset(0L)
          .setRange(UNIFORM_BUFFER_SIZE)
          .build();

      final var uniformBufferWrite =
        VulkanWriteDescriptorSet.builder()
          .setDestinationBinding(0)
          .setDestinationSet(descriptorSets.get(index))
          .setDescriptorType(VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER)
          .setDescriptorCount(1)
          .addBufferInfos(uniformBufferInfo)
          .build();

      final var imageInfo =
        VulkanDescriptorImageInfo.builder()
          .setImageView(texture.image_view)
          .setImageLayout(VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
          .setSampler(texture.sampler)
          .build();

      final var imageWrite =
        VulkanWriteDescriptorSet.builder()
          .setDestinationBinding(1)
          .setDestinationSet(descriptorSets.get(index))
          .setDescriptorType(VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER)
          .setDescriptorCount(1)
          .addImageInfos(imageInfo)
          .build();

      LOG.trace("updating descriptor set {}", Integer.valueOf(index));
      device.updateDescriptorSets(
        List.of(uniformBufferWrite, imageWrite),
        List.of());
    }
    return descriptorSets;
  }

  /**
   * Create a descriptor set layout.
   */

  private static VulkanDescriptorSetLayoutType createDescriptorSetLayout(
    final CloseableCollectionType<VulkanException> resources,
    final VulkanLogicalDeviceType device)
    throws VulkanException
  {
    final var uniform_binding =
      VulkanDescriptorSetLayoutBinding.builder()
        .setBinding(0)
        .setDescriptorType(VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER)
        .setDescriptorCount(1)
        .addStageFlags(VK_SHADER_STAGE_FRAGMENT_BIT)
        .build();

    final var sampler_binding =
      VulkanDescriptorSetLayoutBinding.builder()
        .setBinding(1)
        .setDescriptorType(VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER)
        .setDescriptorCount(1)
        .addStageFlags(VK_SHADER_STAGE_FRAGMENT_BIT)
        .build();

    final var descriptor_set_layout_create_info =
      VulkanDescriptorSetLayoutCreateInfo.builder()
        .addBindings(uniform_binding)
        .addBindings(sampler_binding)
        .build();

    return resources.add(device.createDescriptorSetLayout(
      descriptor_set_layout_create_info));
  }

  /**
   * Configure the render pass.
   *
   * This specifies that:
   *
   * We don't want multisampling.
   *
   * Reads from the image attachment should behave as if the attachment has been
   * cleared before rendering.
   *
   * Writes to the image attachment should be stored in the image.
   *
   * We don't care about reads or writes to the stencil buffer.
   *
   * We don't care about the initial layout of the image.
   *
   * The final layout of the image must be something usable for presentation to
   * the screen.
   */

  private static VulkanRenderPassType createRenderPass(
    final CloseableCollectionType<VulkanException> resources,
    final VulkanSurfaceFormatKHR surface_format,
    final VulkanLogicalDeviceType device)
    throws VulkanException
  {
    final var color_attachment_description =
      VulkanAttachmentDescription.builder()
        .setFormat(surface_format.format())
        .setSamples(VK_SAMPLE_COUNT_1_BIT)
        .setLoadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
        .setStoreOp(VK_ATTACHMENT_STORE_OP_STORE)
        .setStencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
        .setStencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
        .setInitialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
        .setFinalLayout(VK_IMAGE_LAYOUT_PRESENT_SRC_KHR)
        .build();

    final var color_attachment_reference =
      VulkanAttachmentReference.builder()
        .setAttachment(0)
        .setLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
        .build();

    final var subpass_description =
      VulkanSubpassDescription.builder()
        .setPipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS)
        .addColorAttachments(color_attachment_reference)
        .build();

    final var subpass_dependency =
      VulkanSubpassDependency.builder()
        .setSrcSubpass(VulkanSubpassDependencyType.EXTERNAL)
        .addSrcStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
        .setDstSubpass(0)
        .addDstStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
        .addDstAccessMask(VK_ACCESS_COLOR_ATTACHMENT_READ_BIT)
        .addDstAccessMask(VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT)
        .build();

    final var render_pass_create_info =
      VulkanRenderPassCreateInfo.builder()
        .addAttachments(color_attachment_description)
        .addSubpasses(subpass_description)
        .addDependencies(subpass_dependency)
        .build();

    return resources.add(device.createRenderPass(render_pass_create_info));
  }

  /**
   * Configure the rendering pipeline.
   */

  private static VulkanPipelineType createPipeline(
    final VulkanExtent2D surface_extent,
    final VulkanLogicalDeviceType device,
    final VulkanShaderModuleType shaders,
    final VulkanPipelineLayoutType pipeline_layout,
    final VulkanRenderPassType render_pass)
    throws VulkanException
  {
    final var vertex_stage_info =
      VulkanPipelineShaderStageCreateInfo.builder()
        .setStage(VK_SHADER_STAGE_VERTEX_BIT)
        .setModule(shaders)
        .setShaderEntryPoint("R3_clip_triangle_vert_main")
        .build();

    final var fragment_stage_info =
      VulkanPipelineShaderStageCreateInfo.builder()
        .setStage(VK_SHADER_STAGE_FRAGMENT_BIT)
        .setModule(shaders)
        .setShaderEntryPoint("R3_clip_triangle_frag_ub_texture_main")
        .build();

    final var vertex_binding_description =
      VulkanVertexInputBindingDescription.builder()
        .setBinding(0)
        .setInputRate(VK_VERTEX_INPUT_RATE_VERTEX)
        .setStride(VERTEX_SIZE)
        .build();

    final var vertex_position_attribute =
      VulkanVertexInputAttributeDescription.builder()
        .setBinding(0)
        .setLocation(0)
        .setFormat(VK_FORMAT_R32G32_SFLOAT)
        .setOffset(0)
        .build();

    final var vertex_color_attribute =
      VulkanVertexInputAttributeDescription.builder()
        .setBinding(0)
        .setLocation(1)
        .setFormat(VK_FORMAT_R32G32B32_SFLOAT)
        .setOffset(VERTEX_COLOR_OFFSET)
        .build();

    final var vertex_uv_attribute =
      VulkanVertexInputAttributeDescription.builder()
        .setBinding(0)
        .setLocation(2)
        .setFormat(VK_FORMAT_R32G32_SFLOAT)
        .setOffset(VERTEX_UV_OFFSET)
        .build();

    final var vertex_input_info =
      VulkanPipelineVertexInputStateCreateInfo.builder()
        .addVertexBindingDescriptions(vertex_binding_description)
        .addVertexAttributeDescriptions(vertex_position_attribute)
        .addVertexAttributeDescriptions(vertex_color_attribute)
        .addVertexAttributeDescriptions(vertex_uv_attribute)
        .build();

    final var input_assembly_info =
      VulkanPipelineInputAssemblyStateCreateInfo.builder()
        .setTopology(VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST)
        .setPrimitiveRestartEnable(false)
        .build();

    final var viewport_state_info =
      VulkanPipelineViewportStateCreateInfo.builder()
        .addScissors(VulkanRectangle2D.of(
          VulkanOffset2D.of(0, 0),
          surface_extent))
        .addViewports(VulkanViewport.of(
          0.0f,
          0.0f,
          (float) surface_extent.width(),
          (float) surface_extent.height(),
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

    final var blend_state =
      VulkanPipelineColorBlendAttachmentState.builder()
        .setEnable(false)
        .build();

    final var color_blending =
      VulkanPipelineColorBlendStateCreateInfo.builder()
        .addAttachments(blend_state)
        .build();

    final var multisample =
      VulkanPipelineMultisampleStateCreateInfo.builder()
        .setAlphaToCoverageEnable(false)
        .setAlphaToOneEnable(false)
        .setMinSampleShading(1.0f)
        .setRasterizationSamples(VK_SAMPLE_COUNT_1_BIT)
        .setSampleShadingEnable(false)
        .build();

    final var pipeline_info =
      VulkanGraphicsPipelineCreateInfo.builder()
        .addStages(fragment_stage_info)
        .addStages(vertex_stage_info)
        .setColorBlendState(color_blending)
        .setInputAssemblyState(input_assembly_info)
        .setLayout(pipeline_layout)
        .setMultisampleState(multisample)
        .setRasterizationState(rasterizer)
        .setRenderPass(render_pass)
        .setSubpass(0)
        .setVertexInputState(vertex_input_info)
        .setViewportState(viewport_state_info)
        .build();

    return device.createGraphicsPipeline(pipeline_info);
  }

  /**
   * Create and populate an index buffer.
   */

  private static VulkanBufferType createIndexBuffer(
    final CloseableCollectionType<VulkanException> resources,
    final VMAAllocatorType vma_allocator)
    throws VulkanException
  {
    final var vma_alloc_create_info =
      VMAAllocationCreateInfo.builder()
        .setUsage(VMA_MEMORY_USAGE_GPU_ONLY)
        .addRequiredFlags(VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT)
        .setMemoryTypeBits(0L)
        .build();

    final var index_buffer_create_info =
      VulkanBufferCreateInfo.builder()
        .setSize(3L * 2L)
        .addUsageFlags(VK_BUFFER_USAGE_INDEX_BUFFER_BIT)
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .build();

    final var vma_index_buffer_result =
      vma_allocator.createBuffer(
        vma_alloc_create_info,
        index_buffer_create_info);

    final var index_buffer =
      resources.add(vma_index_buffer_result.result());

    LOG.trace("allocated index buffer: {}", index_buffer);

    /*
     * Populate index buffer by mapping the memory and writing to it. Closing the mapped
     * memory will automatically unmap it.
     */

    try (var map = vma_allocator.mapMemory(vma_index_buffer_result.allocation())) {
      final var buffer = map.asByteBuffer();
      buffer.putShort(0, (short) 0);
      buffer.putShort(2, (short) 1);
      buffer.putShort(4, (short) 2);

      map.flush();
    }
    return index_buffer;
  }

  /**
   * Create and populate a vertex buffer.
   */

  private static VulkanBufferType createVertexBuffer(
    final CloseableCollectionType<VulkanException> resources,
    final VMAAllocatorType vma_allocator)
    throws VulkanException
  {
    final var vma_alloc_create_info =
      VMAAllocationCreateInfo.builder()
        .setUsage(VMA_MEMORY_USAGE_GPU_ONLY)
        .addRequiredFlags(VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT)
        .setMemoryTypeBits(0L)
        .build();

    final var vertex_buffer_create_info =
      VulkanBufferCreateInfo.builder()
        .setSize(3L * (long) VERTEX_SIZE)
        .addUsageFlags(VK_BUFFER_USAGE_VERTEX_BUFFER_BIT)
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .build();

    final var vma_vertex_buffer_result =
      vma_allocator.createBuffer(
        vma_alloc_create_info,
        vertex_buffer_create_info);

    final var vertex_buffer =
      resources.add(vma_vertex_buffer_result.result());

    LOG.trace("allocated vertex buffer: {}", vertex_buffer);

    /*
     * Populate vertex buffer by mapping the memory and writing to it. Closing the mapped
     * memory will automatically unmap it.
     */

    try (var map = vma_allocator.mapMemory(vma_vertex_buffer_result.allocation())) {
      final var buffer = map.asByteBuffer();

      final var v0 = VERTICES.get(0);
      final var v0o = 0;
      buffer.putFloat(v0o, (float) v0.x);
      buffer.putFloat(v0o + 4, (float) v0.y);
      buffer.putFloat(v0o + VERTEX_COLOR_OFFSET, (float) v0.r);
      buffer.putFloat(v0o + VERTEX_COLOR_OFFSET + 4, (float) v0.g);
      buffer.putFloat(v0o + VERTEX_COLOR_OFFSET + 8, (float) v0.b);
      buffer.putFloat(v0o + VERTEX_UV_OFFSET, (float) v0.u);
      buffer.putFloat(v0o + VERTEX_UV_OFFSET + 4, (float) v0.v);

      final var v1 = VERTICES.get(1);
      final var v1o = VERTEX_SIZE;
      buffer.putFloat(v1o, (float) v1.x);
      buffer.putFloat(v1o + 4, (float) v1.y);
      buffer.putFloat(v1o + VERTEX_COLOR_OFFSET, (float) v1.r);
      buffer.putFloat(v1o + VERTEX_COLOR_OFFSET + 4, (float) v1.g);
      buffer.putFloat(v1o + VERTEX_COLOR_OFFSET + 8, (float) v1.b);
      buffer.putFloat(v1o + VERTEX_UV_OFFSET, (float) v1.u);
      buffer.putFloat(v1o + VERTEX_UV_OFFSET + 4, (float) v1.v);

      final var v2 = VERTICES.get(2);
      final var v2o = 2 * VERTEX_SIZE;
      buffer.putFloat(v2o, (float) v2.x);
      buffer.putFloat(v2o + 4, (float) v2.y);
      buffer.putFloat(v2o + VERTEX_COLOR_OFFSET, (float) v2.r);
      buffer.putFloat(v2o + VERTEX_COLOR_OFFSET + 4, (float) v2.g);
      buffer.putFloat(v2o + VERTEX_COLOR_OFFSET + 8, (float) v2.b);
      buffer.putFloat(v2o + VERTEX_UV_OFFSET, (float) v2.u);
      buffer.putFloat(v2o + VERTEX_UV_OFFSET + 4, (float) v2.v);

      map.flush();
    }
    return vertex_buffer;
  }

  /**
   * Create uniform buffers.
   */

  private static ArrayList<VulkanBufferType> createUniformBuffers(
    final CloseableCollectionType<VulkanException> resources,
    final VMAAllocatorType vma_allocator,
    final VulkanKHRSwapChainType swap_chain)
    throws VulkanException
  {
    final var vma_alloc_create_info =
      VMAAllocationCreateInfo.builder()
        .setUsage(VMA_MEMORY_USAGE_GPU_ONLY)
        .addRequiredFlags(VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT)
        .setMemoryTypeBits(0L)
        .build();

    final var uniform_buffer_create_info =
      VulkanBufferCreateInfo.builder()
        .setSize(UNIFORM_BUFFER_SIZE)
        .addUsageFlags(VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT)
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .build();

    final var uniform_buffers = new ArrayList<VulkanBufferType>(swap_chain.images().size());
    for (var index = 0; index < swap_chain.images().size(); ++index) {
      final var vma_uniform_buffer_result =
        vma_allocator.createBuffer(
          vma_alloc_create_info,
          uniform_buffer_create_info);

      final var uniform_buffer =
        resources.add(vma_uniform_buffer_result.result());

      LOG.trace("allocated uniform buffer: {}", uniform_buffer);
      uniform_buffers.add(uniform_buffer);

      /*
       * Populate uniform buffer by mapping the memory and writing to it. Closing the mapped
       * memory will automatically unmap it.
       */

      try (var map = vma_allocator.mapMemory(vma_uniform_buffer_result.allocation())) {
        final var buffer = map.asByteBuffer();
        buffer.putFloat(0, 1.0f);
        map.flush();
      }
    }
    return uniform_buffers;
  }

  /**
   * Draw a single frame.
   */

  private static void drawFrame(
    final VulkanExtKHRSwapChainType khr_swapchain_ext,
    final VulkanKHRSwapChainType swap_chain,
    final VulkanSemaphoreType image_available,
    final VulkanSemaphoreType render_finished,
    final List<VulkanCommandBufferType> graphics_command_buffers,
    final VulkanQueueType graphics_queue,
    final VulkanQueueType queue_presentation)
    throws VulkanException
  {
    /*
     * Try to acquire an image from the swap chain, waiting indefinitely until one is available.
     * There isn't really anything sensible that we can do if an image can't be acquired in this
     * example code, so all that will happen is that the code will immediately try again.
     */

    final var acquisition =
      swap_chain.acquireImageWithSemaphore(
        0xffff_ffff_ffff_ffffL,
        image_available);

    final var image_index_option = acquisition.imageIndex();
    if (!image_index_option.isPresent()) {
      LOG.error("could not acquire image");
      return;
    }

    final var image_index = image_index_option.getAsInt();
    final var graphics_command_buffer = graphics_command_buffers.get(image_index);

    /*
     * Wait until the image is available (via the image available semaphore) before writing
     * any color data to the image. When writing has completed, signal that rendering has finished
     * (via the render finished semaphore).
     */

    final var submit_info =
      VulkanSubmitInfo.builder()
        .addWaitSemaphores(image_available)
        .addWaitStageMasks(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
        .addCommandBuffers(graphics_command_buffer)
        .addSignalSemaphores(render_finished)
        .build();

    graphics_queue.submit(List.of(submit_info), Optional.empty());

    /*
     * Tell the presentation queue to present.
     */

    final var presentation_info =
      VulkanPresentInfoKHR.builder()
        .addImageIndices(image_index)
        .addSwapChains(swap_chain)
        .addWaitSemaphores(render_finished)
        .build();

    khr_swapchain_ext.queuePresent(queue_presentation, presentation_info);

    /*
     * Wait until the presentation operation has finished before continuing. The reason for doing
     * this is that it's possible to submit new work to the GPU at a vastly quicker rate than it
     * can actually process it, meaning the queue of commands grows indefinitely and gives the
     * appearance of a memory leak. Waiting for the presentation queue is not the most efficient
     * way that this can be handled (because it implies that the GPU is waiting when it could
     * otherwise be rendering the next frame), but it is the simplest.
     */

    queue_presentation.waitIdle();
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
    try (var input = HelloVulkanWithVMA.class.getResourceAsStream(name)) {
      return input.readAllBytes();
    }
  }

  private static VulkanImageViewType createImageViewForImage(
    final VulkanSurfaceFormatKHR surface_format,
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
        surface_format.format(),
        VulkanComponentMappingType.identity(),
        range));
    } catch (final VulkanException e) {
      throw new VulkanUncheckedException(e);
    }
  }

  private static VulkanKHRSwapChainType createSwapChain(
    final VulkanExtKHRSwapChainType khr_swapchain_ext,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface,
    final VulkanSurfaceFormatKHR surface_format,
    final VulkanPresentModeKHR surface_present,
    final VulkanSurfaceCapabilitiesKHR surface_caps,
    final VulkanExtent2D surface_extent,
    final VulkanLogicalDeviceType device,
    final VulkanQueueType graphics_queue,
    final VulkanQueueType presentation_queue)
    throws VulkanException
  {
    final var minimum_image_count =
      pickMinimumImageCount(surface_caps);
    final List<VulkanQueueFamilyIndex> queueFamilyIndices =
      new ArrayList<>();
    final var image_sharing_mode =
      pickImageSharingMode(graphics_queue, presentation_queue, queueFamilyIndices);
    final var image_usage_flags =
      Set.of(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT);
    final var surface_alpha_flags =
      Set.of(VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR);

    LOG.debug(
      "swap chain image count: {}",
      Integer.valueOf(minimum_image_count));
    LOG.debug("swap chain image mode: {}", image_sharing_mode);

    final var swap_chain_create_info =
      VulkanSwapChainCreateInfo.of(
        surface,
        minimum_image_count,
        surface_format.format(),
        surface_format.colorSpace(),
        surface_extent,
        1,
        image_usage_flags,
        image_sharing_mode,
        queueFamilyIndices,
        surface_caps.currentTransform(),
        surface_alpha_flags,
        surface_present,
        true,
        Optional.empty());

    return khr_swapchain_ext.swapChainCreate(device, swap_chain_create_info);
  }

  private static VulkanPhysicalDeviceType pickPhysicalDeviceOrAbort(
    final VulkanExtKHRSurfaceType khr_surface_ext,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface,
    final List<VulkanPhysicalDeviceType> physical_devices)
    throws VulkanException
  {
    return pickPhysicalDevice(khr_surface_ext, surface, physical_devices)
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
    final VulkanSurfaceCapabilitiesKHR surface_caps)
  {
    /*
     * Select the minimum number of images required to do double-buffering.
     */

    final var count = surface_caps.minImageCount();
    if (surface_caps.maxImageCount() > 0 && count > surface_caps.maxImageCount()) {
      return surface_caps.maxImageCount();
    }
    return count;
  }

  private static VulkanExtent2D pickExtent(
    final VulkanSurfaceCapabilitiesKHR surface_caps)
  {
    /*
     * Work out the extent of the rendered image based on the implementation-defined supported
     * limits.
     */

    if (surface_caps.currentExtent().width() != 0xffff_ffff) {
      return surface_caps.currentExtent();
    }

    return VulkanExtent2D.of(
      Math.max(
        surface_caps.minImageExtent().width(),
        Math.min(surface_caps.maxImageExtent().width(), 640)),
      Math.max(
        surface_caps.minImageExtent().height(),
        Math.min(surface_caps.maxImageExtent().height(), 480))
    );
  }

  private static VulkanPresentModeKHR pickPresentationMode(
    final VulkanPhysicalDeviceType device,
    final VulkanExtKHRSurfaceType khr_surface_ext,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface)
    throws VulkanException
  {
    /*
     * Pick the best presentation mode available.
     */

    final var modes =
      khr_surface_ext.surfacePresentModes(device, surface);

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
    final VulkanExtKHRSurfaceType khr_surface_ext,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface)
    throws VulkanException
  {
    final var formats = khr_surface_ext.surfaceFormats(device, surface);

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
        .filter(HelloVulkanWithVMA::physicalDeviceHasSwapChainExtension)
        .filter(HelloVulkanWithVMA::physicalDeviceHasGraphicsQueue)
        .filter(device -> physicalDeviceHasPresentationQueue(
          khr_surface_ext,
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

      final var queues_presentable =
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
    throws VulkanException, IOException
  {
    GLFW_ERROR_CALLBACK.set();

    final var hostAllocatorMain =
      new VulkanLWJGLHostAllocatorJeMalloc();
    final var hostAllocator =
      new VulkanHostAllocatorTracker(hostAllocatorMain);

    final var finished = new AtomicBoolean(false);

    /*
     * Create an allocator for temporary objects.
     */

    final var alloc =
      VulkanLWJGLTemporaryAllocator.create();

    final Supplier<VulkanException> exceptionSupplier =
      () -> new VulkanResourceException("Could not close one or more resources.");

    try (var resources = CloseableCollection.create(exceptionSupplier)) {
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
       * Determine which extensions are required by the window system, and which layers are
       * required.
       */

      final var requiredLayers =
        Set.of(
          "VK_LAYER_KHRONOS_validation"
        );

      final var requiredExtensions = new HashSet<String>();
      requiredExtensions.addAll(requiredGLFWExtensions());
      requiredExtensions.add("VK_EXT_debug_utils");

      availableExtensions
        .forEach(HelloVulkanWithVMA::showInstanceAvailableExtension);
      availableLayers
        .forEach(HelloVulkanWithVMA::showInstanceAvailableLayer);
      requiredExtensions
        .forEach(HelloVulkanWithVMA::showInstanceRequiredExtension);
      requiredLayers
        .forEach(HelloVulkanWithVMA::showInstanceRequiredLayer);

      /*
       * Filter the available extensions and layers based on the requirements expressed above.
       * If any required extensions are not available, an exception is raised.
       */

      final var enableExtensions =
        VulkanExtensions.filterRequiredExtensions(
          availableExtensions, Set.of(), requiredExtensions);
      final var enableLayers =
        VulkanLayers.filterRequiredLayers(
          availableLayers, Set.of(), requiredLayers);

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

      for (final var format : VulkanFormat.values()) {
        final var properties = physicalDevice.formatProperties(format);

        LOG.trace(
          "physical device: format {} -> linear  {}",
          format,
          properties.linearTilingFeatures());
        LOG.trace(
          "physical device: format {} -> optimal {}",
          format,
          properties.optimalTilingFeatures());
        LOG.trace(
          "physical device: format {} -> buffer  {}",
          format,
          properties.bufferFeatures());
      }

      /*
       * Require the VK_KHR_get_memory_requirements2 extension in order to use VMA.
       */

      final var deviceExtensions =
        physicalDevice.extensions(Optional.empty());

      deviceExtensions.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(HelloVulkanWithVMA::showPhysicalDeviceAvailableExtension);

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
       * The VK_QUEUE_GRAPHICS_BIT implies VK_QUEUE_TRANSFER_BIT, so just searching for
       * VK_QUEUE_GRAPHICS_BIT is enough for both.
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

      final var device = resources.add(
        physicalDevice.createLogicalDevice(
          logicalDeviceInfoBuilder
            .addEnabledExtensions("VK_KHR_swapchain")
            .addEnabledExtensions("VK_KHR_get_memory_requirements2")
            .build()));

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
        device.queue(graphicsQueueProps.queueFamilyIndex(), new VulkanQueueIndex(0))
          .orElseThrow(() -> new IllegalStateException(
            "Could not find graphics queue"));
      final var presentationQueue =
        device.queue(presentationQueueProps.queueFamilyIndex(), new VulkanQueueIndex(0))
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

      final var images = swapChain.images();

      final var views =
        images.stream()
          .map(image -> createImageViewForImage(surfaceFormat, device, image))
          .map(resources::add)
          .collect(Collectors.toList());

      /*
       * Create a command pools and buffers. If the graphics and presentation queues are separate,
       * then separate command pools are required. A separate command buffer pool is created for
       * transfer commands because those commands are short-lived.
       */

      final var graphicsPoolInfo =
        VulkanCommandPoolCreateInfo.builder()
          .setQueueFamilyIndex(graphicsQueue.queueFamilyIndex())
          .build();

      final var graphicsCommandPool =
        resources.add(device.createCommandPool(graphicsPoolInfo));

      final var graphicsCommandBufferCount =
        swapChain.images()
          .size();

      final var graphicsCommandBufferInfo =
        VulkanCommandBufferCreateInfo.builder()
          .setCount(graphicsCommandBufferCount)
          .setLevel(VK_COMMAND_BUFFER_LEVEL_PRIMARY)
          .setPool(graphicsCommandPool)
          .build();

      final var graphicsCommandBuffers =
        device.createCommandBuffers(graphicsCommandBufferInfo);

      final var transferPoolInfo =
        VulkanCommandPoolCreateInfo.builder()
          .setQueueFamilyIndex(graphicsQueue.queueFamilyIndex())
          .build();

      final var transferCommandPool =
        resources.add(device.createCommandPool(transferPoolInfo));

      /*
       * Create and upload textures.
       */

      final var texture =
        createTexture(
          resources,
          device,
          vmaAllocator,
          graphicsQueue,
          transferCommandPool);

      /*
       * Allocate one uniform buffer per frame.
       */

      final var uniformBuffers =
        createUniformBuffers(resources, vmaAllocator, swapChain);

      /*
       * Load a shader module.
       */

      final var shaders =
        resources.add(createShaderModule(
          device,
          alloc,
          readShaderModule("shaders.spv")));

      /*
       * Configure descriptor sets for the shader.
       */

      final var descriptorSetLayout =
        createDescriptorSetLayout(resources, device);
      final var descriptorSets =
        createDescriptorSets(
          resources,
          device,
          swapChain,
          uniformBuffers,
          texture,
          descriptorSetLayout);

      final var pipelineLayoutInfo =
        VulkanPipelineLayoutCreateInfo.builder()
          .addSetLayouts(descriptorSetLayout)
          .build();

      final var pipelineLayout =
        resources.add(device.createPipelineLayout(pipelineLayoutInfo));

      /*
       * Create a render pass.
       */

      final var renderPass =
        createRenderPass(resources, surfaceFormat, device);

      /*
       * Create a pipeline for rendering.
       */

      final var pipeline =
        resources.add(createPipeline(
          surfaceExtent, device, shaders, pipelineLayout, renderPass));

      /*
       * Create framebuffers.
       */

      final List<VulkanFramebufferType> framebuffers =
        new ArrayList<>(images.size());

      for (var index = 0; index < images.size(); ++index) {
        final var framebufferInfo =
          VulkanFramebufferCreateInfo.builder()
            .addAttachments(views.get(index))
            .setHeight(surfaceExtent.height())
            .setWidth(surfaceExtent.width())
            .setLayers(1)
            .setRenderPass(renderPass)
            .build();

        framebuffers.add(resources.add(device.createFramebuffer(framebufferInfo)));
      }

      /*
       * Create a vertex buffer.
       */

      final var vertexBuffer =
        createVertexBuffer(resources, vmaAllocator);

      /*
       * Create index buffer.
       */

      final var indexBuffer =
        createIndexBuffer(resources, vmaAllocator);

      /*
       * Create a pair of semaphores for synchronizing command execution.
       */

      final var renderFinished =
        resources.add(device.createSemaphore(VulkanSemaphoreCreateInfo.builder().build()));
      final var imageAvailable =
        resources.add(device.createSemaphore(VulkanSemaphoreCreateInfo.builder().build()));

      /*
       * Start recording commands.
       */

      for (var index = 0; index < graphicsCommandBufferCount; ++index) {
        final var framebuffer = framebuffers.get(index);
        final var graphicsCommandBuffer = graphicsCommandBuffers.get(index);

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
          renderInfo,
          VK_SUBPASS_CONTENTS_INLINE);
        graphicsCommandBuffer.bindPipeline(
          VK_PIPELINE_BIND_POINT_GRAPHICS,
          pipeline);
        graphicsCommandBuffer.bindVertexBuffers(
          0, 1, List.of(vertexBuffer), List.of(Long.valueOf(0L)));
        graphicsCommandBuffer.bindIndexBuffer(
          indexBuffer,
          0L,
          VK_INDEX_TYPE_UINT16);
        graphicsCommandBuffer.bindDescriptorSets(
          VK_PIPELINE_BIND_POINT_GRAPHICS,
          pipelineLayout,
          0,
          List.of(descriptorSets.get(index)),
          List.of());
        graphicsCommandBuffer.drawIndexed(3, 1, 0, 0, 0);
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
          showMemoryStatistics(hostAllocator);
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

  private static final class Vertex
  {
    public final double x;
    public final double y;
    public final double r;
    public final double g;
    public final double b;
    public final double u;
    public final double v;

    public Vertex(
      final double in_x,
      final double in_y,
      final double in_r,
      final double in_g,
      final double in_b,
      final double in_u,
      final double in_v)
    {
      this.x = in_x;
      this.y = in_y;
      this.r = in_r;
      this.g = in_g;
      this.b = in_b;
      this.u = in_u;
      this.v = in_v;
    }
  }

  private static final class Texture
  {
    final VulkanImageViewType image_view;
    final VulkanSamplerType sampler;

    public Texture(
      final VulkanImageViewType in_image_view,
      final VulkanSamplerType in_sampler)
    {
      this.image_view = Objects.requireNonNull(in_image_view, "image_view");
      this.sampler = Objects.requireNonNull(in_sampler, "sampler");
    }
  }
}
