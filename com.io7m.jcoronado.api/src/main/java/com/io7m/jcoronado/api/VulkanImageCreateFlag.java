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
 * Flags specified when creating images.
 *
 * @see "VkImageCreateFlagBits"
 */

@VulkanAPIEnumType(vulkanEnum = "VkImageCreateFlagBits")
public enum VulkanImageCreateFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies that the image will be backed using sparse memory binding.
   */

  VK_IMAGE_CREATE_SPARSE_BINDING_BIT(0x00000001),

  /**
   * Specifies that the image can be partially backed using sparse memory binding. Images created
   * with this flag must also be created with the VK_IMAGE_CREATE_SPARSE_BINDING_BIT flag.
   */

  VK_IMAGE_CREATE_SPARSE_RESIDENCY_BIT(0x00000002),

  /**
   * Specifies that the image will be backed using sparse memory binding with memory ranges that
   * might also simultaneously be backing another image (or another portion of the same image).
   * Images created with this flag must also be created with the VK_IMAGE_CREATE_SPARSE_BINDING_BIT
   * flag.
   */

  VK_IMAGE_CREATE_SPARSE_ALIASED_BIT(0x00000004),

  /**
   * Specifies that the image can be used to create a VkImageView with a different format from the
   * image. For multi-planar formats, VK_IMAGE_CREATE_MUTABLE_FORMAT_BIT specifies that a
   * VkImageView can be created of a plane of the image.
   */

  VK_IMAGE_CREATE_MUTABLE_FORMAT_BIT(0x00000008),

  /**
   * Specifies that the image can be used to create a VkImageView of type VK_IMAGE_VIEW_TYPE_CUBE or
   * VK_IMAGE_VIEW_TYPE_CUBE_ARRAY.
   */

  VK_IMAGE_CREATE_CUBE_COMPATIBLE_BIT(0x00000010),

  /**
   * Specifies that two images created with the same creation parameters and aliased to the same
   * memory can interpret the contents of the memory consistently with each other, subject to the
   * rules described in the Memory Aliasing section. This flag further specifies that each plane of
   * a disjoint image can share an in-memory non-linear representation with single-plane images, and
   * that a single-plane image can share an in-memory non-linear representation with a plane of a
   * multi-planar disjoint image.
   */

  VK_IMAGE_CREATE_ALIAS_BIT(0x00000400),

  /**
   * Specifies that the image can be used with a non-zero value of the splitInstanceBindRegionCount
   * member of a VkBindImageMemoryDeviceGroupInfo structure passed into vkBindImageMemory2. This
   * flag also has the effect of making the image use the standard sparse image block dimensions.
   */

  VK_IMAGE_CREATE_SPLIT_INSTANCE_BIND_REGIONS_BIT(0x00000040),

  /**
   * Specifies that the image can be used to create a VkImageView of type VK_IMAGE_VIEW_TYPE_2D or
   * VK_IMAGE_VIEW_TYPE_2D_ARRAY.
   */

  VK_IMAGE_CREATE_2D_ARRAY_COMPATIBLE_BIT(0x00000020),

  /**
   * Specifies that the image having a compressed format can be used to create a VkImageView with an
   * uncompressed format where each texel in the image view corresponds to a compressed texel block
   * of the image.
   */

  VK_IMAGE_CREATE_BLOCK_TEXEL_VIEW_COMPATIBLE_BIT(0x00000080),

  /**
   * Specifies that the image can be created with usage flags that are not supported for the format
   * the image is created with but are supported for at least one format a VkImageView created from
   * the image can have.
   */

  VK_IMAGE_CREATE_EXTENDED_USAGE_BIT(0x00000100),

  /**
   * Specifies that the image is a protected image.
   */

  VK_IMAGE_CREATE_PROTECTED_BIT(0x00000800),

  /**
   * Specifies that an image with a multi-planar format must have each plane separately bound to
   * memory, rather than having a single memory binding for the whole image; the presence of this
   * bit distinguishes a disjoint image from an image without this bit set.
   */

  VK_IMAGE_CREATE_DISJOINT_BIT(0x00000200),

  /**
   * Specifies that the image is a corner-sampled image.
   */

  VK_IMAGE_CREATE_CORNER_SAMPLED_BIT_NV(0x00002000),

  /**
   * Specifies that an image with a depth or depth/stencil format can be used with custom sample
   * locations when used as a depth/stencil attachment.
   */

  VK_IMAGE_CREATE_SAMPLE_LOCATIONS_COMPATIBLE_DEPTH_BIT_EXT(0x00001000),

  /**
   * Specifies that an image can be in a subsampled format which may be more optimal when written as
   * an attachment by a render pass that has a fragment density map attachment.
   */

  VK_IMAGE_CREATE_SUBSAMPLED_BIT_EXT(0x00004000);

  private final int value;

  VulkanImageCreateFlag(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
