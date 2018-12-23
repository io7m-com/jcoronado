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
 * @see "VkFormatFeatureFlagBits"
 */

public enum VulkanFormatFeatureFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies that an image view can be sampled from.
   */

  VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT(0x00000001),

  /**
   * Specifies that an image view can be used as a storage images.
   */

  VK_FORMAT_FEATURE_STORAGE_IMAGE_BIT(0x00000002),

  /**
   * Specifies that an image view can be used as storage image that supports atomic operations.
   */

  VK_FORMAT_FEATURE_STORAGE_IMAGE_ATOMIC_BIT(0x00000004),

  /**
   * Specifies that an image view can be used as a framebuffer color attachment and as an input
   * attachment.
   */

  VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT(0x00000080),

  /**
   * Specifies that an image view can be used as a framebuffer color attachment that supports
   * blending and as an input attachment.
   */

  VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT(0x00000100),

  /**
   * Specifies that an image view can be used as a framebuffer depth/stencil attachment and as an
   * input attachment.
   */

  VK_FORMAT_FEATURE_DEPTH_STENCIL_ATTACHMENT_BIT(0x00000200),

  /**
   * Specifies that an image can be used as srcImage for the vkCmdBlitImage command.
   */

  VK_FORMAT_FEATURE_BLIT_SRC_BIT(0x00000400),

  /**
   * Specifies that an image can be used as dstImage for the vkCmdBlitImage command.
   */

  VK_FORMAT_FEATURE_BLIT_DST_BIT(0x00000800),

  /**
   * Specifies that if VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT is also set, an image view can be used
   * with a sampler that has either of magFilter or minFilter set to VK_FILTER_LINEAR, or mipmapMode
   * set to VK_SAMPLER_MIPMAP_MODE_LINEAR. If VK_FORMAT_FEATURE_BLIT_SRC_BIT is also set, an image
   * can be used as the srcImage to vkCmdBlitImage with a filter of VK_FILTER_LINEAR. This bit must
   * only be exposed for formats that also support the VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT or
   * VK_FORMAT_FEATURE_BLIT_SRC_BIT. If the format being queried is a depth/stencil format, this bit
   * only specifies that the depth aspect (not the stencil aspect) of an image of this format
   * supports linear filtering, and that linear filtering of the depth aspect is supported whether
   * depth compare is enabled in the sampler or not. If this bit is not present, linear filtering
   * with depth compare disabled is unsupported and linear filtering with depth compare enabled is
   * supported, but may compute the filtered value in an implementation-dependent manner which
   * differs from the normal rules of linear filtering. The resulting value must be in the range
   * [0,1] and should be proportional to, or a weighted average of, the number of comparison passes
   * or failures.
   */

  VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT(0x00001000),

  /**
   * Specifies that an image can be used as a source image for copy commands.
   */

  VK_FORMAT_FEATURE_TRANSFER_SRC_BIT(0x00004000),

  /**
   * Specifies that an image can be used as a destination image for copy commands and clear
   * commands.
   */

  VK_FORMAT_FEATURE_TRANSFER_DST_BIT(0x00008000),

  /**
   * Specifies VkImage can be used as a sampled image with a min or max VkSamplerReductionModeEXT.
   * This bit must only be exposed for formats that also support the VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT.
   */

  VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_MINMAX_BIT_EXT(0x00010000),

  /**
   * Specifies that VkImage can be used with a sampler that has either of magFilter or minFilter set
   * to VK_FILTER_CUBIC_IMG, or be the source image for a blit with filter set to
   * VK_FILTER_CUBIC_IMG. This bit must only be exposed for formats that also support the
   * VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT. If the format being queried is a depth/stencil format,
   * this only specifies that the depth aspect is cubic filterable.
   */

  VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_CUBIC_BIT_IMG(0x00002000),

  /**
   * Specifies that an application can define a sampler Y’CBCR conversion using this format as a
   * source, and that an image of this format can be used with a VkSamplerYcbcrConversionCreateInfo
   * xChromaOffset and/or yChromaOffset of VK_CHROMA_LOCATION_MIDPOINT. Otherwise both xChromaOffset
   * and yChromaOffset must be VK_CHROMA_LOCATION_COSITED_EVEN. If a format does not incorporate
   * chroma downsampling (it is not a “422” or “420” format) but the implementation supports sampler
   * Y’CBCR conversion for this format, the implementation must set VK_FORMAT_FEATURE_MIDPOINT_CHROMA_SAMPLES_BIT.
   */

  VK_FORMAT_FEATURE_MIDPOINT_CHROMA_SAMPLES_BIT(0x00020000),

  /**
   * Specifies that an application can define a sampler Y’CBCR conversion using this format as a
   * source, and that an image of this format can be used with a VkSamplerYcbcrConversionCreateInfo
   * xChromaOffset and/or yChromaOffset of VK_CHROMA_LOCATION_COSITED_EVEN. Otherwise both
   * xChromaOffset and yChromaOffset must be VK_CHROMA_LOCATION_MIDPOINT. If neither
   * VK_FORMAT_FEATURE_COSITED_CHROMA_SAMPLES_BIT nor VK_FORMAT_FEATURE_MIDPOINT_CHROMA_SAMPLES_BIT
   * is set, the application must not define a sampler Y’CBCR conversion using this format as a
   * source.
   */

  VK_FORMAT_FEATURE_COSITED_CHROMA_SAMPLES_BIT(0x00800000),

  /**
   * Specifies that a multi-planar image can have the VK_IMAGE_CREATE_DISJOINT_BIT set during image
   * creation. An implementation must not set VK_FORMAT_FEATURE_DISJOINT_BIT for single-plane
   * formats.
   */

  VK_FORMAT_FEATURE_DISJOINT_BIT(0x00400000),

  /**
   * Specifies that an image view can be used as a fragment density map attachment.
   */

  VK_FORMAT_FEATURE_FRAGMENT_DENSITY_MAP_BIT_EXT(0x01000000),

  /**
   * Specifies that the format can be used to create a buffer view that can be bound to a
   * VK_DESCRIPTOR_TYPE_UNIFORM_TEXEL_BUFFER descriptor.
   */

  VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT(0x00000008),

  /**
   * Specifies that the format can be used to create a buffer view that can be bound to a
   * VK_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER descriptor.
   */

  VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT(0x00000010),

  /**
   * Specifies that atomic operations are supported on VK_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER with
   * this format.
   */

  VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_ATOMIC_BIT(0x00000020),

  /**
   * Specifies that the format can be used as a vertex attribute format.
   */

  VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT(0x00000040);

  private final int value;

  VulkanFormatFeatureFlag(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
