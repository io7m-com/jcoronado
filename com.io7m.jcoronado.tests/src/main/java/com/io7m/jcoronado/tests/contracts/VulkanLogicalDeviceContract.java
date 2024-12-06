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

package com.io7m.jcoronado.tests.contracts;

import com.io7m.jcoronado.api.VulkanAttachmentDescription;
import com.io7m.jcoronado.api.VulkanAttachmentReference;
import com.io7m.jcoronado.api.VulkanBufferCopy;
import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanBufferMemoryBarrier;
import com.io7m.jcoronado.api.VulkanBufferViewCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferSubmitInfo;
import com.io7m.jcoronado.api.VulkanCommandPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanComponentMapping;
import com.io7m.jcoronado.api.VulkanDependencyInfo;
import com.io7m.jcoronado.api.VulkanEventCreateInfo;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanFenceCreateInfo;
import com.io7m.jcoronado.api.VulkanFramebufferCreateInfo;
import com.io7m.jcoronado.api.VulkanImageCreateInfo;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import com.io7m.jcoronado.api.VulkanImageViewKind;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanMappedMemoryType;
import com.io7m.jcoronado.api.VulkanMemoryAllocateInfo;
import com.io7m.jcoronado.api.VulkanOffset2D;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanRectangle2D;
import com.io7m.jcoronado.api.VulkanRenderPassBeginInfo;
import com.io7m.jcoronado.api.VulkanRenderPassCreateInfo;
import com.io7m.jcoronado.api.VulkanSamplerCreateInfo;
import com.io7m.jcoronado.api.VulkanSemaphoreBinaryCreateInfo;
import com.io7m.jcoronado.api.VulkanSemaphoreTimelineCreateInfo;
import com.io7m.jcoronado.api.VulkanShaderModuleCreateInfo;
import com.io7m.jcoronado.api.VulkanSubmitInfo;
import com.io7m.jcoronado.api.VulkanSubpassContents;
import com.io7m.jcoronado.api.VulkanSubpassDescription;
import com.io7m.jmulticlose.core.CloseableCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_HOST_READ_BIT;
import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_TRANSFER_READ_BIT;
import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_TRANSFER_WRITE_BIT;
import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanBorderColor.VK_BORDER_COLOR_INT_OPAQUE_BLACK;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_STORAGE_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_STORAGE_TEXEL_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_TRANSFER_DST_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_TRANSFER_SRC_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanCommandBufferLevel.VK_COMMAND_BUFFER_LEVEL_PRIMARY;
import static com.io7m.jcoronado.api.VulkanCommandBufferUsageFlag.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT;
import static com.io7m.jcoronado.api.VulkanCompareOp.VK_COMPARE_OP_ALWAYS;
import static com.io7m.jcoronado.api.VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_IDENTITY;
import static com.io7m.jcoronado.api.VulkanFilter.VK_FILTER_NEAREST;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R5G6B5_UNORM_PACK16;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R8G8B8A8_UNORM;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R8_UNORM;
import static com.io7m.jcoronado.api.VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT;
import static com.io7m.jcoronado.api.VulkanImageKind.VK_IMAGE_TYPE_2D;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED;
import static com.io7m.jcoronado.api.VulkanImageTiling.VK_IMAGE_TILING_LINEAR;
import static com.io7m.jcoronado.api.VulkanImageTiling.VK_IMAGE_TILING_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_SAMPLED_BIT;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_TRANSFER_SRC_BIT;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineBindPoint.VK_PIPELINE_BIND_POINT_GRAPHICS;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_HOST_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_TRANSFER_BIT;
import static com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag.VK_QUEUE_TRANSFER_BIT;
import static com.io7m.jcoronado.api.VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT;
import static com.io7m.jcoronado.api.VulkanSamplerAddressMode.VK_SAMPLER_ADDRESS_MODE_REPEAT;
import static com.io7m.jcoronado.api.VulkanSamplerMipmapMode.VK_SAMPLER_MIPMAP_MODE_NEAREST;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class VulkanLogicalDeviceContract extends VulkanOnDeviceContract
{
  private VulkanPhysicalDeviceType physicalDevice;
  private VulkanLogicalDeviceType device;

  private static boolean isSuitableCopyQueue(
    final VulkanQueueType q)
  {
    final var familyProperties =
      q.queueFamilyProperties();
    final var queueFlags =
      familyProperties.queueFlags();

    return queueFlags.contains(VK_QUEUE_TRANSFER_BIT);
  }

  protected abstract VulkanInstanceType instance();

  protected abstract VulkanPhysicalDeviceType createPhysicalDevice()
    throws VulkanException;

  protected abstract VulkanLogicalDeviceType createLogicalDevice(
    VulkanPhysicalDeviceType device)
    throws VulkanException;

  @BeforeEach
  public void testSetup()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    this.physicalDevice =
      this.createPhysicalDevice();
    this.device =
      this.createLogicalDevice(this.physicalDevice);
  }

  @AfterEach
  public void tearDown()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    this.device.close();
    this.physicalDevice.close();
    this.instance().close();
  }

  @Test
  public final void testLogicalDevice()
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    assertEquals(this.physicalDevice, this.device.physicalDevice());
    assertFalse(this.physicalDevice.isClosed(), "Device is not closed");
  }

  /**
   * Try creating an image.
   *
   * @throws VulkanException On errors
   */

  @Test
  public final void testCreateImage()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var image = this.device.createImage(
      VulkanImageCreateInfo.builder()
        .setArrayLayers(1)
        .setSamples(Set.of(VK_SAMPLE_COUNT_1_BIT))
        .setExtent(VulkanExtent3D.of(256, 256, 1))
        .setFormat(VK_FORMAT_R8_UNORM)
        .setImageType(VK_IMAGE_TYPE_2D)
        .setInitialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
        .setMipLevels(1)
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .setTiling(VK_IMAGE_TILING_OPTIMAL)
        .setUsage(Set.of(VK_IMAGE_USAGE_SAMPLED_BIT))
        .build()
    )) {
      assertFalse(image.isClosed());
    }
  }

  /**
   * Try creating a buffer.
   *
   * @throws VulkanException On errors
   */

  @Test
  public final void testCreateBuffer()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var buffer = this.device.createBuffer(
      VulkanBufferCreateInfo.builder()
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .setSize(128L)
        .setUsageFlags(Set.of(VK_BUFFER_USAGE_VERTEX_BUFFER_BIT))
        .build()
    )) {
      assertFalse(buffer.isClosed());
    }
  }

  /**
   * Try creating buffers and scheduling copies between them.
   *
   * @throws Exception On errors
   */

  @Test
  public final void testCreateBufferCopy()
    throws Exception
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var createInfo =
      VulkanBufferCreateInfo.builder()
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .setSize(128L)
        .setUsageFlags(Set.of(
          VK_BUFFER_USAGE_TRANSFER_SRC_BIT,
          VK_BUFFER_USAGE_TRANSFER_DST_BIT))
        .build();

    try (var resources =
           CloseableCollection.create()) {

      final var buffer0 =
        resources.add(this.device.createBuffer(createInfo));
      final var buffer1 =
        resources.add(this.device.createBuffer(createInfo));

      final var buffer0Requirements =
        this.device.getBufferMemoryRequirements(buffer0);
      final var buffer1Requirements =
        this.device.getBufferMemoryRequirements(buffer1);

      final var bufferMemoryType =
        this.physicalDevice.memory()
          .findSuitableMemoryType(
            buffer0Requirements,
            Set.of(
              VK_MEMORY_PROPERTY_HOST_COHERENT_BIT,
              VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT));

      final var memory0 =
        resources.add(
          this.device.allocateMemory(
            VulkanMemoryAllocateInfo.of(
              buffer0Requirements.size(),
              bufferMemoryType.index()))
        );
      final var memory1 =
        resources.add(
          this.device.allocateMemory(
            VulkanMemoryAllocateInfo.of(
              buffer1Requirements.size(),
              bufferMemoryType.index()))
        );

      final var map0 =
        resources.add(
          this.device.mapMemory(memory0, 0L, 128L, Set.of()));
      final var map1 =
        resources.add(
          this.device.mapMemory(memory1, 0L, 128L, Set.of()));

      this.device.bindBufferMemory(buffer0, memory0, 0L);
      this.device.bindBufferMemory(buffer1, memory1, 0L);

      final var queue =
        this.device.queues()
          .stream()
          .filter(VulkanLogicalDeviceContract::isSuitableCopyQueue)
          .findFirst()
          .orElseThrow();

      final var queueFamilyIndex =
        queue.queueFamilyProperties().queueFamilyIndex();

      final var commandPool =
        resources.add(
          this.device.createCommandPool(
            VulkanCommandPoolCreateInfo.builder()
              .setQueueFamilyIndex(queueFamilyIndex)
              .build())
        );

      final var commandBuffer =
        resources.add(
          this.device.createCommandBuffer(
            commandPool,
            VK_COMMAND_BUFFER_LEVEL_PRIMARY)
        );

      final var fence =
        resources.add(
          this.device.createFence(VulkanFenceCreateInfo.of(Set.of()))
        );

      final var fillBarrier0 =
        VulkanBufferMemoryBarrier.builder()
          .setBuffer(buffer0)
          .setSrcStageMask(Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT))
          .setSrcAccessMask(Set.of(VK_ACCESS_TRANSFER_WRITE_BIT))
          .setSrcQueueFamilyIndex(queueFamilyIndex)
          .setDstStageMask(Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT))
          .setDstAccessMask(Set.of(VK_ACCESS_TRANSFER_READ_BIT))
          .setDstQueueFamilyIndex(queueFamilyIndex)
          .setOffset(0L)
          .setSize(128L)
          .build();

      final var fillBarrier1 =
        VulkanBufferMemoryBarrier.builder()
          .setBuffer(buffer1)
          .setSrcStageMask(Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT))
          .setSrcAccessMask(Set.of(VK_ACCESS_TRANSFER_WRITE_BIT))
          .setSrcQueueFamilyIndex(queueFamilyIndex)
          .setDstStageMask(Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT))
          .setDstAccessMask(Set.of(VK_ACCESS_TRANSFER_READ_BIT))
          .setDstQueueFamilyIndex(queueFamilyIndex)
          .setOffset(0L)
          .setSize(128L)
          .build();

      final var copyBarrier0 =
        VulkanBufferMemoryBarrier.builder()
          .setBuffer(buffer0)
          .setSrcStageMask(Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT))
          .setSrcAccessMask(Set.of(VK_ACCESS_TRANSFER_WRITE_BIT))
          .setSrcQueueFamilyIndex(queueFamilyIndex)
          .setDstStageMask(Set.of(VK_PIPELINE_STAGE_HOST_BIT))
          .setDstAccessMask(Set.of(VK_ACCESS_HOST_READ_BIT))
          .setDstQueueFamilyIndex(queueFamilyIndex)
          .setSize(128L)
          .setOffset(0L)
          .build();

      final var copyBarrier1 =
        VulkanBufferMemoryBarrier.builder()
          .setBuffer(buffer1)
          .setSrcStageMask(Set.of(VK_PIPELINE_STAGE_TRANSFER_BIT))
          .setSrcAccessMask(Set.of(VK_ACCESS_TRANSFER_WRITE_BIT))
          .setSrcQueueFamilyIndex(queueFamilyIndex)
          .setDstStageMask(Set.of(VK_PIPELINE_STAGE_HOST_BIT))
          .setDstAccessMask(Set.of(VK_ACCESS_HOST_READ_BIT))
          .setDstQueueFamilyIndex(queueFamilyIndex)
          .setSize(128L)
          .setOffset(0L)
          .build();

      commandBuffer.beginCommandBuffer(
        VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);

      commandBuffer.fillBuffer(buffer0, 0L, 128L, 0x41414141);
      commandBuffer.fillBuffer(buffer1, 0L, 128L, 0x42424242);

      commandBuffer.pipelineBarrier(
        VulkanDependencyInfo.builder()
          .addBufferMemoryBarriers(fillBarrier0)
          .addBufferMemoryBarriers(fillBarrier1)
          .build()
      );

      commandBuffer.copyBuffer(
        buffer0,
        buffer1,
        List.of(VulkanBufferCopy.of(0L, 0L, 128L))
      );

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
          ).build()
      ), Optional.of(fence));

      this.device.waitForFence(fence, 1_000_000_000L);

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

      Assertions.assertArrayEquals(expBytes, mBytes0);
      Assertions.assertArrayEquals(expBytes, mBytes1);
    }
  }

  /**
   * Try creating a buffer view.
   *
   * @throws VulkanException On errors
   */

  @Test
  public final void testCreateBufferView()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var buffer = this.device.createBuffer(
      VulkanBufferCreateInfo.builder()
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .setSize(128L)
        .setUsageFlags(Set.of(VK_BUFFER_USAGE_STORAGE_TEXEL_BUFFER_BIT))
        .build()
    )) {
      final var bufferMemoryRequirements =
        this.device.getBufferMemoryRequirements(buffer);

      final var bufferMemoryType =
        this.physicalDevice.memory()
          .findSuitableMemoryType(
            bufferMemoryRequirements,
            Set.of(
              VK_MEMORY_PROPERTY_HOST_COHERENT_BIT,
              VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT));

      try (var memory =
             this.device.allocateMemory(
               VulkanMemoryAllocateInfo.builder()
                 .setSize(bufferMemoryRequirements.size())
                 .setMemoryTypeIndex(bufferMemoryType.index())
                 .build())) {

        this.device.bindBufferMemory(buffer, memory, 0L);

        try (var view = this.device.createBufferView(
          VulkanBufferViewCreateInfo.builder()
            .setFormat(VK_FORMAT_R8_UNORM)
            .setBuffer(buffer)
            .setOffset(0L)
            .setRange(1L)
            .build()
        )) {
          assertFalse(view.isClosed());
          assertEquals(buffer, view.buffer());
        }
      }
    }
  }

  /**
   * Try recording a simple command buffer.
   *
   * @throws VulkanException On errors
   */

  @Test
  public final void testRecordCommandBuffer()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var queue =
      this.device.queues()
        .get(0);

    try (var pool = this.device.createCommandPool(
      VulkanCommandPoolCreateInfo.builder()
        .setQueueFamilyIndex(queue.queueFamilyIndex())
        .build())) {

      try (var buffer =
             this.device.createCommandBuffer(
               pool,
               VK_COMMAND_BUFFER_LEVEL_PRIMARY)) {
        buffer.beginCommandBuffer();
        buffer.endCommandBuffer();
      }
    }
  }

  /**
   * Try creating an image view.
   *
   * @throws VulkanException On errors
   */

  @Test
  public final void testCreateImageView()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var image = this.device.createImage(
      VulkanImageCreateInfo.builder()
        .setArrayLayers(1)
        .setSamples(Set.of(VK_SAMPLE_COUNT_1_BIT))
        .setExtent(VulkanExtent3D.of(256, 256, 1))
        .setFormat(VK_FORMAT_R8G8B8A8_UNORM)
        .setImageType(VK_IMAGE_TYPE_2D)
        .setInitialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
        .setMipLevels(1)
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .setTiling(VK_IMAGE_TILING_LINEAR)
        .setUsage(Set.of(
          VK_IMAGE_USAGE_TRANSFER_SRC_BIT,
          VK_IMAGE_USAGE_SAMPLED_BIT))
        .build()
    )) {
      final var subresourceRange =
        VulkanImageSubresourceRange.builder()
          .setLayerCount(1)
          .setLevelCount(1)
          .setBaseArrayLayer(0)
          .setBaseMipLevel(0)
          .setAspectMask(Set.of(VK_IMAGE_ASPECT_COLOR_BIT))
          .build();

      final var imageMemoryRequirements =
        this.device.getImageMemoryRequirements(image);

      final var imageMemoryType =
        this.physicalDevice.memory()
          .findSuitableMemoryType(
            imageMemoryRequirements,
            Set.of(
              VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT));

      try (var memory =
             this.device.allocateMemory(
               VulkanMemoryAllocateInfo.builder()
                 .setSize(imageMemoryRequirements.size())
                 .setMemoryTypeIndex(imageMemoryType.index())
                 .build())) {

        this.device.bindImageMemory(image, memory, 0L);

        try (var imageView = this.device.createImageView(
          VulkanImageViewCreateInfo.builder()
            .setFormat(VK_FORMAT_R8G8B8A8_UNORM)
            .setImage(image)
            .setComponents(VulkanComponentMapping.of(
              VK_COMPONENT_SWIZZLE_IDENTITY,
              VK_COMPONENT_SWIZZLE_IDENTITY,
              VK_COMPONENT_SWIZZLE_IDENTITY,
              VK_COMPONENT_SWIZZLE_IDENTITY
            ))
            .setViewType(VulkanImageViewKind.VK_IMAGE_VIEW_TYPE_2D)
            .setSubresourceRange(subresourceRange)
            .build()
        )) {
          assertFalse(imageView.isClosed());
        }
      }
    }
  }

  /**
   * Try creating a framebuffer.
   *
   * @throws VulkanException On errors
   */

  @Test
  public final void testCreateFramebuffer565()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var image = this.device.createImage(
      VulkanImageCreateInfo.builder()
        .setArrayLayers(1)
        .setSamples(Set.of(VK_SAMPLE_COUNT_1_BIT))
        .setExtent(VulkanExtent3D.of(256, 256, 1))
        .setFormat(VK_FORMAT_R5G6B5_UNORM_PACK16)
        .setImageType(VK_IMAGE_TYPE_2D)
        .setInitialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
        .setMipLevels(1)
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .setTiling(VK_IMAGE_TILING_OPTIMAL)
        .setUsage(Set.of(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT))
        .build()
    )) {
      final var subresourceRange =
        VulkanImageSubresourceRange.builder()
          .setLayerCount(1)
          .setLevelCount(1)
          .setBaseArrayLayer(0)
          .setBaseMipLevel(0)
          .setAspectMask(Set.of(VK_IMAGE_ASPECT_COLOR_BIT))
          .build();

      final var imageMemoryRequirements =
        this.device.getImageMemoryRequirements(image);

      final var imageMemoryType =
        this.physicalDevice.memory()
          .findSuitableMemoryType(
            imageMemoryRequirements,
            Set.of(VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT));

      try (var memory =
             this.device.allocateMemory(
               VulkanMemoryAllocateInfo.builder()
                 .setSize(imageMemoryRequirements.size())
                 .setMemoryTypeIndex(imageMemoryType.index())
                 .build())) {

        this.device.bindImageMemory(image, memory, 0L);

        try (var imageView =
               this.device.createImageView(
                 VulkanImageViewCreateInfo.builder()
                   .setFormat(VK_FORMAT_R5G6B5_UNORM_PACK16)
                   .setImage(image)
                   .setComponents(VulkanComponentMapping.of(
                     VK_COMPONENT_SWIZZLE_IDENTITY,
                     VK_COMPONENT_SWIZZLE_IDENTITY,
                     VK_COMPONENT_SWIZZLE_IDENTITY,
                     VK_COMPONENT_SWIZZLE_IDENTITY
                   ))
                   .setViewType(VulkanImageViewKind.VK_IMAGE_VIEW_TYPE_2D)
                   .setSubresourceRange(subresourceRange)
                   .build()
               )) {

          try (var renderPass =
                 this.device.createRenderPass(
                   VulkanRenderPassCreateInfo.builder()
                     .addAttachments(
                       VulkanAttachmentDescription.builder()
                         .setInitialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
                         .setFormat(VK_FORMAT_R5G6B5_UNORM_PACK16)
                         .setSamples(VK_SAMPLE_COUNT_1_BIT)
                         .setFinalLayout(
                           VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
                         .setLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
                         .setStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
                         .setStencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
                         .setStencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
                         .build())
                     .addSubpasses(
                       VulkanSubpassDescription.builder()
                         .addColorAttachments(
                           VulkanAttachmentReference.builder()
                             .setAttachment(0)
                             .setLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
                             .build()
                         ).setPipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS)
                         .build())
                     .build()
                 )) {

            try (var framebuffer =
                   this.device.createFramebuffer(
                     VulkanFramebufferCreateInfo.builder()
                       .setLayers(1)
                       .setWidth(256)
                       .setHeight(256)
                       .addAttachments(imageView)
                       .setRenderPass(renderPass)
                       .build())) {
              assertFalse(framebuffer.isClosed());
            }
          }
        }
      }
    }
  }

  /**
   * Try recording a command buffer involving a render pass.
   *
   * @throws VulkanException On errors
   */

  @Test
  public final void testCreateCommandBufferRenderPass()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var image = this.device.createImage(
      VulkanImageCreateInfo.builder()
        .setArrayLayers(1)
        .setSamples(Set.of(VK_SAMPLE_COUNT_1_BIT))
        .setExtent(VulkanExtent3D.of(256, 256, 1))
        .setFormat(VK_FORMAT_R5G6B5_UNORM_PACK16)
        .setImageType(VK_IMAGE_TYPE_2D)
        .setInitialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
        .setMipLevels(1)
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .setTiling(VK_IMAGE_TILING_OPTIMAL)
        .setUsage(Set.of(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT))
        .build()
    )) {
      final var subresourceRange =
        VulkanImageSubresourceRange.builder()
          .setLayerCount(1)
          .setLevelCount(1)
          .setBaseArrayLayer(0)
          .setBaseMipLevel(0)
          .setAspectMask(Set.of(VK_IMAGE_ASPECT_COLOR_BIT))
          .build();

      final var imageMemoryRequirements =
        this.device.getImageMemoryRequirements(image);

      final var imageMemoryType =
        this.physicalDevice.memory()
          .findSuitableMemoryType(
            imageMemoryRequirements,
            Set.of(VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT));

      try (var memory =
             this.device.allocateMemory(
               VulkanMemoryAllocateInfo.builder()
                 .setSize(imageMemoryRequirements.size())
                 .setMemoryTypeIndex(imageMemoryType.index())
                 .build())) {

        this.device.bindImageMemory(image, memory, 0L);

        try (var imageView =
               this.device.createImageView(
                 VulkanImageViewCreateInfo.builder()
                   .setFormat(VK_FORMAT_R5G6B5_UNORM_PACK16)
                   .setImage(image)
                   .setComponents(VulkanComponentMapping.of(
                     VK_COMPONENT_SWIZZLE_IDENTITY,
                     VK_COMPONENT_SWIZZLE_IDENTITY,
                     VK_COMPONENT_SWIZZLE_IDENTITY,
                     VK_COMPONENT_SWIZZLE_IDENTITY
                   ))
                   .setViewType(VulkanImageViewKind.VK_IMAGE_VIEW_TYPE_2D)
                   .setSubresourceRange(subresourceRange)
                   .build()
               )) {

          final var subpassDescription =
            VulkanSubpassDescription.builder()
              .addColorAttachments(
                VulkanAttachmentReference.builder()
                  .setAttachment(0)
                  .setLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
                  .build()
              )
              .setPipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS)
              .build();

          final var attachmentDescription =
            VulkanAttachmentDescription.builder()
              .setInitialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
              .setFormat(VK_FORMAT_R5G6B5_UNORM_PACK16)
              .setSamples(VK_SAMPLE_COUNT_1_BIT)
              .setFinalLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
              .setLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
              .setStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
              .setStencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
              .setStencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
              .build();

          try (var renderPass =
                 this.device.createRenderPass(
                   VulkanRenderPassCreateInfo.builder()
                     .addAttachments(attachmentDescription)
                     .addSubpasses(subpassDescription)
                     .build()
                 )) {

            final var queue =
              this.device.queues()
                .get(0);

            try (var pool =
                   this.device.createCommandPool(
                     VulkanCommandPoolCreateInfo.builder()
                       .setQueueFamilyIndex(queue.queueFamilyIndex())
                       .build())) {

              try (var buffer =
                     this.device.createCommandBuffer(
                       pool,
                       VK_COMMAND_BUFFER_LEVEL_PRIMARY)) {

                try (var framebuffer =
                       this.device.createFramebuffer(
                         VulkanFramebufferCreateInfo.builder()
                           .setLayers(1)
                           .setWidth(256)
                           .setHeight(256)
                           .addAttachments(imageView)
                           .setRenderPass(renderPass)
                           .build())) {
                  buffer.beginCommandBuffer();
                  buffer.beginRenderPass(
                    VulkanRenderPassBeginInfo.builder()
                      .setRenderPass(renderPass)
                      .setFramebuffer(framebuffer)
                      .setRenderArea(
                        VulkanRectangle2D.builder()
                          .setExtent(VulkanExtent2D.of(256, 256))
                          .setOffset(VulkanOffset2D.of(0, 0))
                          .build())
                      .build(),
                    VulkanSubpassContents.VK_SUBPASS_CONTENTS_INLINE
                  );
                  buffer.endRenderPass();
                  buffer.endCommandBuffer();
                }
              }
            }
          }
        }
      }
    }
  }

  /**
   * Try creating a sampler.
   *
   * @throws VulkanException On errors
   */

  @Test
  public final void testCreateSampler()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var sampler =
           this.device.createSampler(
             VulkanSamplerCreateInfo.builder()
               .setAddressModeU(VK_SAMPLER_ADDRESS_MODE_REPEAT)
               .setAddressModeV(VK_SAMPLER_ADDRESS_MODE_REPEAT)
               .setAddressModeW(VK_SAMPLER_ADDRESS_MODE_REPEAT)
               .setBorderColor(VK_BORDER_COLOR_INT_OPAQUE_BLACK)
               .setCompareOp(VK_COMPARE_OP_ALWAYS)
               .setMagFilter(VK_FILTER_NEAREST)
               .setMinFilter(VK_FILTER_NEAREST)
               .setMipmapMode(VK_SAMPLER_MIPMAP_MODE_NEAREST)
               .setMipLodBias(0.0f)
               .setMinLod(1.0f)
               .setMaxLod(1.0f)
               .setUnnormalizedCoordinates(false)
               .build()
           )) {
      assertFalse(sampler.isClosed());
    }
  }

  /**
   * Try creating a shader module.
   *
   * @throws Exception On errors
   */

  @Test
  public final void testCreateShaderModule()
    throws Exception
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var stream =
           VulkanLogicalDeviceContract.class.getResourceAsStream(
             "/com/io7m/jcoronado/tests/input.vert.spv")) {

      final var bytecode =
        stream.readAllBytes();
      final var bytecodeBuffer =
        ByteBuffer.allocateDirect(bytecode.length);

      bytecodeBuffer.put(bytecode);
      bytecodeBuffer.rewind();

      try (var shaderModule =
             this.device.createShaderModule(
               VulkanShaderModuleCreateInfo.builder()
                 .setData(bytecodeBuffer)
                 .setSize(bytecode.length)
                 .build()
             )) {
        assertFalse(shaderModule.isClosed());
      }
    }
  }

  /**
   * Try creating an event.
   *
   * @throws Exception On errors
   */

  @Test
  public final void testCreateEvent()
    throws Exception
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var event =
           this.device.createEvent(
             VulkanEventCreateInfo.builder()
               .build())) {
      assertFalse(event.isClosed());
    }
  }

  /**
   * Try creating a semaphore.
   *
   * @throws Exception On errors
   */

  @Test
  public final void testCreateSemaphoreBinary()
    throws Exception
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var semaphore =
           this.device.createBinarySemaphore(
             VulkanSemaphoreBinaryCreateInfo.builder().build())) {
      assertFalse(semaphore.isClosed());
    }
  }

  /**
   * Try creating a semaphore.
   *
   * @throws Exception On errors
   */

  @Test
  public final void testCreateSemaphoreTimeline()
    throws Exception
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var semaphore =
           this.device.createTimelineSemaphore(
             VulkanSemaphoreTimelineCreateInfo.builder()
               .setInitialValue(23L)
               .build())) {
      assertFalse(semaphore.isClosed());
    }
  }

  /**
   * Try mapping, writing, and flushing memory.
   *
   * @throws Exception On errors
   */

  @Test
  public final void testCreateMapFlush()
    throws Exception
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var buffer = this.device.createBuffer(
      VulkanBufferCreateInfo.builder()
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .setUsageFlags(Set.of(VK_BUFFER_USAGE_STORAGE_BUFFER_BIT))
        .setSize(128L)
        .build()
    )) {
      final var bufferMemoryRequirements =
        this.device.getBufferMemoryRequirements(buffer);

      final var memoryType =
        this.physicalDevice.memory()
          .findSuitableMemoryType(
            bufferMemoryRequirements,
            Set.of(VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT));

      try (var memory =
             this.device.allocateMemory(
               VulkanMemoryAllocateInfo.of(
                 bufferMemoryRequirements.size(),
                 memoryType.index())
             )) {

        final VulkanMappedMemoryType unmapped;
        try (var map =
               this.device.mapMemory(memory, 0L, 128L, Set.of())) {
          unmapped = map;
          assertTrue(map.isMapped());

          final var byteBuffer = map.asByteBuffer();
          for (int index = 0; index < 128; ++index) {
            byteBuffer.put(index, (byte) index);
          }
          map.flushRange(0L, 128L);

          for (int index = 0; index < 128; ++index) {
            byteBuffer.put(index, (byte) (index + 2));
          }
          map.flush();
        }

        this.logger().debug("checking unmapped...");
        assertFalse(unmapped.isMapped());
      }
    }
  }
}
