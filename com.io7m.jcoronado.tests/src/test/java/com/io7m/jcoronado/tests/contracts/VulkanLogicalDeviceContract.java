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
import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanBufferUsageFlag;
import com.io7m.jcoronado.api.VulkanBufferViewCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanComponentMapping;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanFramebufferCreateInfo;
import com.io7m.jcoronado.api.VulkanImageCreateInfo;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import com.io7m.jcoronado.api.VulkanImageViewKind;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanMemoryAllocateInfo;
import com.io7m.jcoronado.api.VulkanOffset2D;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanRectangle2D;
import com.io7m.jcoronado.api.VulkanRenderPassBeginInfo;
import com.io7m.jcoronado.api.VulkanRenderPassCreateInfo;
import com.io7m.jcoronado.api.VulkanSubpassContents;
import com.io7m.jcoronado.api.VulkanSubpassDescription;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanCommandBufferLevel.VK_COMMAND_BUFFER_LEVEL_PRIMARY;
import static com.io7m.jcoronado.api.VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_IDENTITY;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R5G6B5_UNORM_PACK16;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R8_UNORM;
import static com.io7m.jcoronado.api.VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT;
import static com.io7m.jcoronado.api.VulkanImageKind.VK_IMAGE_TYPE_2D;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED;
import static com.io7m.jcoronado.api.VulkanImageTiling.VK_IMAGE_TILING_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_SAMPLED_BIT;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineBindPoint.VK_PIPELINE_BIND_POINT_GRAPHICS;
import static com.io7m.jcoronado.api.VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT;
import static com.io7m.jcoronado.api.VulkanSampleCountFlag.VK_SAMPLE_COUNT_8_BIT;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class VulkanLogicalDeviceContract extends VulkanOnDeviceContract
{
  private VulkanPhysicalDeviceType physicalDevice;
  private VulkanLogicalDeviceType device;

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
        .setSamples(Set.of(VK_SAMPLE_COUNT_8_BIT))
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
        .setUsageFlags(Set.of(VulkanBufferUsageFlag.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT))
        .build()
    )) {
      assertFalse(buffer.isClosed());
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
        .setUsageFlags(Set.of(VulkanBufferUsageFlag.VK_BUFFER_USAGE_STORAGE_TEXEL_BUFFER_BIT))
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
                 .setMemoryTypeIndex(bufferMemoryType.heapIndex())
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
        .setQueueFamilyIndex(queue.queueIndex())
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
            Set.of(
              VK_MEMORY_PROPERTY_HOST_COHERENT_BIT,
              VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT));

      try (var memory =
             this.device.allocateMemory(
               VulkanMemoryAllocateInfo.builder()
                 .setSize(imageMemoryRequirements.size())
                 .setMemoryTypeIndex(imageMemoryType.heapIndex())
                 .build())) {

        this.device.bindImageMemory(image, memory, 0L);

        try (var imageView = this.device.createImageView(
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
            Set.of(
              VK_MEMORY_PROPERTY_HOST_COHERENT_BIT,
              VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT));

      try (var memory =
             this.device.allocateMemory(
               VulkanMemoryAllocateInfo.builder()
                 .setSize(imageMemoryRequirements.size())
                 .setMemoryTypeIndex(imageMemoryType.heapIndex())
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
                         .addColorAttachments(VulkanAttachmentReference.of(
                           0,
                           VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL))
                         .setPipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS)
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
            Set.of(
              VK_MEMORY_PROPERTY_HOST_COHERENT_BIT,
              VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT));

      try (var memory =
             this.device.allocateMemory(
               VulkanMemoryAllocateInfo.builder()
                 .setSize(imageMemoryRequirements.size())
                 .setMemoryTypeIndex(imageMemoryType.heapIndex())
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
                         .addColorAttachments(VulkanAttachmentReference.of(
                           0,
                           VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL))
                         .setPipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS)
                         .build())
                     .build()
                 )) {

            final var queue =
              this.device.queues()
                .get(0);

            try (var pool =
                   this.device.createCommandPool(
                     VulkanCommandPoolCreateInfo.builder()
                       .setQueueFamilyIndex(queue.queueIndex())
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
}
