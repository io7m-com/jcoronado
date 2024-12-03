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

package com.io7m.jcoronado.api;

/**
 * @see "VkImageLayout"
 */

@VulkanAPIEnumType(vulkanEnum = "VkImageLayout")
public enum VulkanImageLayout implements VulkanEnumIntegerType
{
  /**
   * VK_IMAGE_LAYOUT_UNDEFINED does not support device access. This layout must only be used as the
   * initialLayout member of VkImageCreateInfo or VkAttachmentDescription, or as the oldLayout in an
   * image transition. When transitioning out of this layout, the contents of the memory are not
   * guaranteed to be preserved.
   */

  VK_IMAGE_LAYOUT_UNDEFINED(0),

  /**
   * VK_IMAGE_LAYOUT_PREINITIALIZED does not support device access. This layout must only be used as
   * the initialLayout member of VkImageCreateInfo or VkAttachmentDescription, or as the oldLayout
   * in an image transition. When transitioning out of this layout, the contents of the memory are
   * preserved. This layout is intended to be used as the initial layout for an image whose contents
   * are written by the host, and hence the data can be written to memory immediately, without first
   * executing a layout transition. Currently, VK_IMAGE_LAYOUT_PREINITIALIZED is only useful with
   * VK_IMAGE_TILING_LINEAR images because there is not a standard layout defined for
   * VK_IMAGE_TILING_OPTIMAL images.
   */

  VK_IMAGE_LAYOUT_GENERAL(1),

  /**
   * VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL must only be used as a color or resolve attachment in
   * a VkFramebuffer. This layout is valid only for image subresources of images created with the
   * VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT usage bit enabled.
   */

  VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL(2),

  /**
   * VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL must only be used as a depth/stencil
   * attachment in a VkFramebuffer. This layout is valid only for image subresources of images
   * created with the VK_IMAGE_USAGE_DEPTH_STENCIL_ATTACHMENT_BIT usage bit enabled.
   */

  VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL(3),

  /**
   * VK_IMAGE_LAYOUT_DEPTH_STENCIL_READ_ONLY_OPTIMAL must only be used as a read-only depth/stencil
   * attachment in a VkFramebuffer and/or as a read-only image in a shader (which can be read as a
   * sampled image, combined image/sampler and/or input attachment). This layout is valid only for
   * image subresources of images created with the VK_IMAGE_USAGE_DEPTH_STENCIL_ATTACHMENT_BIT usage
   * bit enabled. Only image subresources of images created with VK_IMAGE_USAGE_SAMPLED_BIT can be
   * used as a sampled image or combined image/sampler in a shader. Similarly, only image
   * subresources of images created with VK_IMAGE_USAGE_INPUT_ATTACHMENT_BIT can be used as input
   * attachments.
   */

  VK_IMAGE_LAYOUT_DEPTH_STENCIL_READ_ONLY_OPTIMAL(4),

  /**
   * VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL must only be used as a read-only image in a shader
   * (which can be read as a sampled image, combined image/sampler and/or input attachment). This
   * layout is valid only for image subresources of images created with the
   * VK_IMAGE_USAGE_SAMPLED_BIT or VK_IMAGE_USAGE_INPUT_ATTACHMENT_BIT usage bit enabled.
   */

  VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL(5),

  /**
   * VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL must only be used as a source image of a transfer command
   * (see the definition of VK_PIPELINE_STAGE_TRANSFER_BIT). This layout is valid only for image
   * subresources of images created with the VK_IMAGE_USAGE_TRANSFER_SRC_BIT usage bit enabled.
   */

  VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL(6),

  /**
   * VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL must only be used as a destination image of a transfer
   * command. This layout is valid only for image subresources of images created with the
   * VK_IMAGE_USAGE_TRANSFER_DST_BIT usage bit enabled.
   */

  VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL(7),

  /**
   * VK_IMAGE_LAYOUT_PREINITIALIZED does not support device access. This layout must only be used as
   * the initialLayout member of VkImageCreateInfo or VkAttachmentDescription, or as the oldLayout
   * in an image transition. When transitioning out of this layout, the contents of the memory are
   * preserved. This layout is intended to be used as the initial layout for an image whose contents
   * are written by the host, and hence the data can be written to memory immediately, without first
   * executing a layout transition. Currently, VK_IMAGE_LAYOUT_PREINITIALIZED is only useful with
   * VK_IMAGE_TILING_LINEAR images because there is not a standard layout defined for
   * VK_IMAGE_TILING_OPTIMAL images.
   */

  VK_IMAGE_LAYOUT_PREINITIALIZED(8),

  /**
   * VK_IMAGE_LAYOUT_DEPTH_READ_ONLY_STENCIL_ATTACHMENT_OPTIMAL
   */

  VK_IMAGE_LAYOUT_DEPTH_READ_ONLY_STENCIL_ATTACHMENT_OPTIMAL(1000117000),

  /**
   * VK_IMAGE_LAYOUT_DEPTH_ATTACHMENT_STENCIL_READ_ONLY_OPTIMAL
   */

  VK_IMAGE_LAYOUT_DEPTH_ATTACHMENT_STENCIL_READ_ONLY_OPTIMAL(1000117001),

  /**
   * VK_IMAGE_LAYOUT_PRESENT_SRC_KHR must only be used for presenting a presentable image for
   * display. A swapchain’s image must be transitioned to this layout before calling
   * vkQueuePresentKHR, and must be transitioned away from this layout after calling
   * vkAcquireNextImageKHR.
   */

  VK_IMAGE_LAYOUT_PRESENT_SRC_KHR(1000001002),

  /**
   * VK_IMAGE_LAYOUT_SHARED_PRESENT_KHR is valid only for shared presentable images, and must be
   * used for any usage the image supports.
   */

  VK_IMAGE_LAYOUT_SHARED_PRESENT_KHR(1000111000);

  private final int value;

  VulkanImageLayout(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
