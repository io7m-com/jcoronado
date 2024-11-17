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

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanFormatColorChannels.COLOR_CHANNELS_ABGR;
import static com.io7m.jcoronado.api.VulkanFormatColorChannels.COLOR_CHANNELS_ARGB;
import static com.io7m.jcoronado.api.VulkanFormatColorChannels.COLOR_CHANNELS_BGR;
import static com.io7m.jcoronado.api.VulkanFormatColorChannels.COLOR_CHANNELS_BGRA;
import static com.io7m.jcoronado.api.VulkanFormatColorChannels.COLOR_CHANNELS_R;
import static com.io7m.jcoronado.api.VulkanFormatColorChannels.COLOR_CHANNELS_RG;
import static com.io7m.jcoronado.api.VulkanFormatColorChannels.COLOR_CHANNELS_RGB;
import static com.io7m.jcoronado.api.VulkanFormatColorChannels.COLOR_CHANNELS_RGBA;
import static com.io7m.jcoronado.api.VulkanFormatCompressed.FORMAT_COMPRESSED;
import static com.io7m.jcoronado.api.VulkanFormatCompressed.FORMAT_UNCOMPRESSED;
import static com.io7m.jcoronado.api.VulkanFormatData.FORMAT_DATA_FLOATING_POINT_SIGNED;
import static com.io7m.jcoronado.api.VulkanFormatData.FORMAT_DATA_FLOATING_POINT_UNSIGNED;
import static com.io7m.jcoronado.api.VulkanFormatData.FORMAT_DATA_INTEGER_SIGNED;
import static com.io7m.jcoronado.api.VulkanFormatData.FORMAT_DATA_INTEGER_UNSIGNED;
import static com.io7m.jcoronado.api.VulkanFormatData.FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
import static com.io7m.jcoronado.api.VulkanFormatData.FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
import static com.io7m.jcoronado.api.VulkanFormatData.FORMAT_DATA_OPAQUE;
import static com.io7m.jcoronado.api.VulkanFormatData.FORMAT_DATA_SCALED_SIGNED;
import static com.io7m.jcoronado.api.VulkanFormatData.FORMAT_DATA_SCALED_UNSIGNED;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_BLIT_DST_BIT;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_BLIT_SRC_BIT;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_DEPTH_STENCIL_ATTACHMENT_BIT;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_STORAGE_IMAGE_ATOMIC_BIT;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_STORAGE_IMAGE_BIT;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_ATOMIC_BIT;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanFormatFeatureFlag.VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanFormatInterpretation.FORMAT_INTERPRETATION_COLOR;
import static com.io7m.jcoronado.api.VulkanFormatInterpretation.FORMAT_INTERPRETATION_DEPTH;
import static com.io7m.jcoronado.api.VulkanFormatInterpretation.FORMAT_INTERPRETATION_DEPTH_STENCIL;
import static com.io7m.jcoronado.api.VulkanFormatInterpretation.FORMAT_INTERPRETATION_OPAQUE;
import static com.io7m.jcoronado.api.VulkanFormatInterpretation.FORMAT_INTERPRETATION_STENCIL;
import static com.io7m.jcoronado.api.VulkanFormatSpace.FORMAT_SPACE_LINEAR;
import static com.io7m.jcoronado.api.VulkanFormatSpace.FORMAT_SPACE_NONE;
import static com.io7m.jcoronado.api.VulkanFormatSpace.FORMAT_SPACE_SRGB;


/**
 * Note: This enum is not hand-written: See formats.sh
 *
 * @see "VkFormat"
 */

@VulkanAPIEnumType(vulkanEnum = "VkFormat")
public enum VulkanFormat implements VulkanEnumIntegerType
{
  /**
   * The format is not specified.
   */

  VK_FORMAT_UNDEFINED(0) {
    @Override
    public int texelSizeOctets()
    {
      return 0;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_OPAQUE;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_OPAQUE;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_NONE;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.empty();
    }
  },

  /**
   * A two-component, 8-bit packed unsigned normalized format that has a 4-bit R component in bits
   * 4..7, and a 4-bit G component in bits 0..3.
   */

  VK_FORMAT_R4G4_UNORM_PACK8(1) {
    @Override
    public int texelSizeOctets()
    {
      return 1;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A four-component, 16-bit packed unsigned normalized format that has a 4-bit R component in bits
   * 12..15, a 4-bit G component in bits 8..11, a 4-bit B component in bits 4..7, and a 4-bit A
   * component in bits 0..3.
   */

  VK_FORMAT_R4G4B4A4_UNORM_PACK16(2) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 16-bit packed unsigned normalized format that has a 4-bit B component in bits
   * 12..15, a 4-bit G component in bits 8..11, a 4-bit R component in bits 4..7, and a 4-bit A
   * component in bits 0..3.
   */

  VK_FORMAT_B4G4R4A4_UNORM_PACK16(3) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGRA);
    }
  },

  /**
   * A three-component, 16-bit packed unsigned normalized format that has a 5-bit R component in
   * bits 11..15, a 6-bit G component in bits 5..10, and a 5-bit B component in bits 0..4.
   */

  VK_FORMAT_R5G6B5_UNORM_PACK16(4) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT
      );
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 16-bit packed unsigned normalized format that has a 5-bit B component in
   * bits 11..15, a 6-bit G component in bits 5..10, and a 5-bit R component in bits 0..4.
   */

  VK_FORMAT_B5G6R5_UNORM_PACK16(5) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGR);
    }
  },

  /**
   * A four-component, 16-bit packed unsigned normalized format that has a 5-bit R component in bits
   * 11..15, a 5-bit G component in bits 6..10, a 5-bit B component in bits 1..5, and a 1-bit A
   * component in bit 0.
   */

  VK_FORMAT_R5G5B5A1_UNORM_PACK16(6) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 16-bit packed unsigned normalized format that has a 5-bit B component in bits
   * 11..15, a 5-bit G component in bits 6..10, a 5-bit R component in bits 1..5, and a 1-bit A
   * component in bit 0.
   */

  VK_FORMAT_B5G5R5A1_UNORM_PACK16(7) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGRA);
    }
  },

  /**
   * A four-component, 16-bit packed unsigned normalized format that has a 1-bit A component in bit
   * 15, a 5-bit R component in bits 10..14, a 5-bit G component in bits 5..9, and a 5-bit B
   * component in bits 0..4.
   */

  VK_FORMAT_A1R5G5B5_UNORM_PACK16(8) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ARGB);
    }
  },

  /**
   * A one-component, 8-bit unsigned normalized format that has a single 8-bit R component.
   */

  VK_FORMAT_R8_UNORM(9) {
    @Override
    public int texelSizeOctets()
    {
      return 1;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 8-bit signed normalized format that has a single 8-bit R component.
   */

  VK_FORMAT_R8_SNORM(10) {
    @Override
    public int texelSizeOctets()
    {
      return 1;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 8-bit unsigned scaled integer format that has a single 8-bit R component.
   */

  VK_FORMAT_R8_USCALED(11) {
    @Override
    public int texelSizeOctets()
    {
      return 1;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 8-bit signed scaled integer format that has a single 8-bit R component.
   */

  VK_FORMAT_R8_SSCALED(12) {
    @Override
    public int texelSizeOctets()
    {
      return 1;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 8-bit unsigned integer format that has a single 8-bit R component.
   */

  VK_FORMAT_R8_UINT(13) {
    @Override
    public int texelSizeOctets()
    {
      return 1;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 8-bit signed integer format that has a single 8-bit R component.
   */

  VK_FORMAT_R8_SINT(14) {
    @Override
    public int texelSizeOctets()
    {
      return 1;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 8-bit unsigned normalized format that has a single 8-bit R component stored
   * with sRGB nonlinear encoding.
   */

  VK_FORMAT_R8_SRGB(15) {
    @Override
    public int texelSizeOctets()
    {
      return 1;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A two-component, 16-bit unsigned normalized format that has an 8-bit R component in byte 0, and
   * an 8-bit G component in byte 1.
   */

  VK_FORMAT_R8G8_UNORM(16) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 16-bit signed normalized format that has an 8-bit R component in byte 0, and
   * an 8-bit G component in byte 1.
   */

  VK_FORMAT_R8G8_SNORM(17) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 16-bit unsigned scaled integer format that has an 8-bit R component in byte 0,
   * and an 8-bit G component in byte 1.
   */

  VK_FORMAT_R8G8_USCALED(18) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 16-bit signed scaled integer format that has an 8-bit R component in byte 0,
   * and an 8-bit G component in byte 1.
   */

  VK_FORMAT_R8G8_SSCALED(19) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 16-bit unsigned integer format that has an 8-bit R component in byte 0, and an
   * 8-bit G component in byte 1.
   */

  VK_FORMAT_R8G8_UINT(20) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 16-bit signed integer format that has an 8-bit R component in byte 0, and an
   * 8-bit G component in byte 1.
   */

  VK_FORMAT_R8G8_SINT(21) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 16-bit unsigned normalized format that has an 8-bit R component stored with
   * sRGB nonlinear encoding in byte 0, and an 8-bit G component stored with sRGB nonlinear encoding
   * in byte 1.
   */

  VK_FORMAT_R8G8_SRGB(22) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A three-component, 24-bit unsigned normalized format that has an 8-bit R component in byte 0,
   * an 8-bit G component in byte 1, and an 8-bit B component in byte 2.
   */

  VK_FORMAT_R8G8B8_UNORM(23) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 24-bit signed normalized format that has an 8-bit R component in byte 0, an
   * 8-bit G component in byte 1, and an 8-bit B component in byte 2.
   */

  VK_FORMAT_R8G8B8_SNORM(24) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 24-bit unsigned scaled format that has an 8-bit R component in byte 0, an
   * 8-bit G component in byte 1, and an 8-bit B component in byte 2.
   */

  VK_FORMAT_R8G8B8_USCALED(25) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 24-bit signed scaled format that has an 8-bit R component in byte 0, an
   * 8-bit G component in byte 1, and an 8-bit B component in byte 2.
   */

  VK_FORMAT_R8G8B8_SSCALED(26) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 24-bit unsigned integer format that has an 8-bit R component in byte 0, an
   * 8-bit G component in byte 1, and an 8-bit B component in byte 2.
   */

  VK_FORMAT_R8G8B8_UINT(27) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 24-bit signed integer format that has an 8-bit R component in byte 0, an
   * 8-bit G component in byte 1, and an 8-bit B component in byte 2.
   */

  VK_FORMAT_R8G8B8_SINT(28) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 24-bit unsigned normalized format that has an 8-bit R component stored with
   * sRGB nonlinear encoding in byte 0, an 8-bit G component stored with sRGB nonlinear encoding in
   * byte 1, and an 8-bit B component stored with sRGB nonlinear encoding in byte 2.
   */

  VK_FORMAT_R8G8B8_SRGB(29) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 24-bit unsigned normalized format that has an 8-bit B component in byte 0,
   * an 8-bit G component in byte 1, and an 8-bit R component in byte 2.
   */

  VK_FORMAT_B8G8R8_UNORM(30) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGR);
    }
  },

  /**
   * A three-component, 24-bit signed normalized format that has an 8-bit B component in byte 0, an
   * 8-bit G component in byte 1, and an 8-bit R component in byte 2.
   */

  VK_FORMAT_B8G8R8_SNORM(31) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGR);
    }
  },

  /**
   * A three-component, 24-bit unsigned scaled format that has an 8-bit B component in byte 0, an
   * 8-bit G component in byte 1, and an 8-bit R component in byte 2.
   */

  VK_FORMAT_B8G8R8_USCALED(32) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGR);
    }
  },

  /**
   * A three-component, 24-bit signed scaled format that has an 8-bit B component in byte 0, an
   * 8-bit G component in byte 1, and an 8-bit R component in byte 2.
   */

  VK_FORMAT_B8G8R8_SSCALED(33) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGR);
    }
  },

  /**
   * A three-component, 24-bit unsigned integer format that has an 8-bit B component in byte 0, an
   * 8-bit G component in byte 1, and an 8-bit R component in byte 2.
   */

  VK_FORMAT_B8G8R8_UINT(34) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGR);
    }
  },

  /**
   * A three-component, 24-bit signed integer format that has an 8-bit B component in byte 0, an
   * 8-bit G component in byte 1, and an 8-bit R component in byte 2.
   */

  VK_FORMAT_B8G8R8_SINT(35) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGR);
    }
  },

  /**
   * A three-component, 24-bit unsigned normalized format that has an 8-bit B component stored with
   * sRGB nonlinear encoding in byte 0, an 8-bit G component stored with sRGB nonlinear encoding in
   * byte 1, and an 8-bit R component stored with sRGB nonlinear encoding in byte 2.
   */

  VK_FORMAT_B8G8R8_SRGB(36) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGR);
    }
  },

  /**
   * A four-component, 32-bit unsigned normalized format that has an 8-bit R component in byte 0, an
   * 8-bit G component in byte 1, an 8-bit B component in byte 2, and an 8-bit A component in byte
   * 3.
   */

  VK_FORMAT_R8G8B8A8_UNORM(37) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT,
        VK_FORMAT_FEATURE_STORAGE_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 32-bit signed normalized format that has an 8-bit R component in byte 0, an
   * 8-bit G component in byte 1, an 8-bit B component in byte 2, and an 8-bit A component in byte
   * 3.
   */

  VK_FORMAT_R8G8B8A8_SNORM(38) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT,
        VK_FORMAT_FEATURE_STORAGE_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 32-bit unsigned scaled format that has an 8-bit R component in byte 0, an
   * 8-bit G component in byte 1, an 8-bit B component in byte 2, and an 8-bit A component in byte
   * 3.
   */

  VK_FORMAT_R8G8B8A8_USCALED(39) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 32-bit signed scaled format that has an 8-bit R component in byte 0, an 8-bit
   * G component in byte 1, an 8-bit B component in byte 2, and an 8-bit A component in byte 3.
   */

  VK_FORMAT_R8G8B8A8_SSCALED(40) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 32-bit unsigned integer format that has an 8-bit R component in byte 0, an
   * 8-bit G component in byte 1, an 8-bit B component in byte 2, and an 8-bit A component in byte
   * 3.
   */

  VK_FORMAT_R8G8B8A8_UINT(41) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 32-bit signed integer format that has an 8-bit R component in byte 0, an
   * 8-bit G component in byte 1, an 8-bit B component in byte 2, and an 8-bit A component in byte
   * 3.
   */

  VK_FORMAT_R8G8B8A8_SINT(42) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 32-bit unsigned normalized format that has an 8-bit R component stored with
   * sRGB nonlinear encoding in byte 0, an 8-bit G component stored with sRGB nonlinear encoding in
   * byte 1, an 8-bit B component stored with sRGB nonlinear encoding in byte 2, and an 8-bit A
   * component in byte 3.
   */

  VK_FORMAT_R8G8B8A8_SRGB(43) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 32-bit unsigned normalized format that has an 8-bit B component in byte 0, an
   * 8-bit G component in byte 1, an 8-bit R component in byte 2, and an 8-bit A component in byte
   * 3.
   */

  VK_FORMAT_B8G8R8A8_UNORM(44) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGRA);
    }
  },

  /**
   * A four-component, 32-bit signed normalized format that has an 8-bit B component in byte 0, an
   * 8-bit G component in byte 1, an 8-bit R component in byte 2, and an 8-bit A component in byte
   * 3.
   */

  VK_FORMAT_B8G8R8A8_SNORM(45) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGRA);
    }
  },

  /**
   * A four-component, 32-bit unsigned scaled format that has an 8-bit B component in byte 0, an
   * 8-bit G component in byte 1, an 8-bit R component in byte 2, and an 8-bit A component in byte
   * 3.
   */

  VK_FORMAT_B8G8R8A8_USCALED(46) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGRA);
    }
  },

  /**
   * A four-component, 32-bit signed scaled format that has an 8-bit B component in byte 0, an 8-bit
   * G component in byte 1, an 8-bit R component in byte 2, and an 8-bit A component in byte 3.
   */

  VK_FORMAT_B8G8R8A8_SSCALED(47) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGRA);
    }
  },

  /**
   * A four-component, 32-bit unsigned integer format that has an 8-bit B component in byte 0, an
   * 8-bit G component in byte 1, an 8-bit R component in byte 2, and an 8-bit A component in byte
   * 3.
   */

  VK_FORMAT_B8G8R8A8_UINT(48) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGRA);
    }
  },

  /**
   * A four-component, 32-bit signed integer format that has an 8-bit B component in byte 0, an
   * 8-bit G component in byte 1, an 8-bit R component in byte 2, and an 8-bit A component in byte
   * 3.
   */

  VK_FORMAT_B8G8R8A8_SINT(49) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGRA);
    }
  },

  /**
   * A four-component, 32-bit unsigned normalized format that has an 8-bit B component stored with
   * sRGB nonlinear encoding in byte 0, an 8-bit G component stored with sRGB nonlinear encoding in
   * byte 1, an 8-bit R component stored with sRGB nonlinear encoding in byte 2, and an 8-bit A
   * component in byte 3.
   */

  VK_FORMAT_B8G8R8A8_SRGB(50) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGRA);
    }
  },

  /**
   * A four-component, 32-bit packed unsigned normalized format that has an 8-bit A component in
   * bits 24..31, an 8-bit B component in bits 16..23, an 8-bit G component in bits 8..15, and an
   * 8-bit R component in bits 0..7.
   */

  VK_FORMAT_A8B8G8R8_UNORM_PACK32(51) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A four-component, 32-bit packed signed normalized format that has an 8-bit A component in bits
   * 24..31, an 8-bit B component in bits 16..23, an 8-bit G component in bits 8..15, and an 8-bit R
   * component in bits 0..7.
   */

  VK_FORMAT_A8B8G8R8_SNORM_PACK32(52) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A four-component, 32-bit packed unsigned scaled integer format that has an 8-bit A component in
   * bits 24..31, an 8-bit B component in bits 16..23, an 8-bit G component in bits 8..15, and an
   * 8-bit R component in bits 0..7.
   */

  VK_FORMAT_A8B8G8R8_USCALED_PACK32(53) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A four-component, 32-bit packed signed scaled integer format that has an 8-bit A component in
   * bits 24..31, an 8-bit B component in bits 16..23, an 8-bit G component in bits 8..15, and an
   * 8-bit R component in bits 0..7.
   */

  VK_FORMAT_A8B8G8R8_SSCALED_PACK32(54) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A four-component, 32-bit packed unsigned integer format that has an 8-bit A component in bits
   * 24..31, an 8-bit B component in bits 16..23, an 8-bit G component in bits 8..15, and an 8-bit R
   * component in bits 0..7.
   */

  VK_FORMAT_A8B8G8R8_UINT_PACK32(55) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A four-component, 32-bit packed signed integer format that has an 8-bit A component in bits
   * 24..31, an 8-bit B component in bits 16..23, an 8-bit G component in bits 8..15, and an 8-bit R
   * component in bits 0..7.
   */

  VK_FORMAT_A8B8G8R8_SINT_PACK32(56) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A four-component, 32-bit packed unsigned normalized format that has an 8-bit A component in
   * bits 24..31, an 8-bit B component stored with sRGB nonlinear encoding in bits 16..23, an 8-bit
   * G component stored with sRGB nonlinear encoding in bits 8..15, and an 8-bit R component stored
   * with sRGB nonlinear encoding in bits 0..7.
   */

  VK_FORMAT_A8B8G8R8_SRGB_PACK32(57) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A four-component, 32-bit packed unsigned normalized format that has a 2-bit A component in bits
   * 30..31, a 10-bit R component in bits 20..29, a 10-bit G component in bits 10..19, and a 10-bit
   * B component in bits 0..9.
   */

  VK_FORMAT_A2R10G10B10_UNORM_PACK32(58) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ARGB);
    }
  },

  /**
   * A four-component, 32-bit packed signed normalized format that has a 2-bit A component in bits
   * 30..31, a 10-bit R component in bits 20..29, a 10-bit G component in bits 10..19, and a 10-bit
   * B component in bits 0..9.
   */

  VK_FORMAT_A2R10G10B10_SNORM_PACK32(59) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ARGB);
    }
  },

  /**
   * A four-component, 32-bit packed unsigned scaled integer format that has a 2-bit A component in
   * bits 30..31, a 10-bit R component in bits 20..29, a 10-bit G component in bits 10..19, and a
   * 10-bit B component in bits 0..9.
   */

  VK_FORMAT_A2R10G10B10_USCALED_PACK32(60) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ARGB);
    }
  },

  /**
   * A four-component, 32-bit packed signed scaled integer format that has a 2-bit A component in
   * bits 30..31, a 10-bit R component in bits 20..29, a 10-bit G component in bits 10..19, and a
   * 10-bit B component in bits 0..9.
   */

  VK_FORMAT_A2R10G10B10_SSCALED_PACK32(61) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ARGB);
    }
  },

  /**
   * A four-component, 32-bit packed unsigned integer format that has a 2-bit A component in bits
   * 30..31, a 10-bit R component in bits 20..29, a 10-bit G component in bits 10..19, and a 10-bit
   * B component in bits 0..9.
   */

  VK_FORMAT_A2R10G10B10_UINT_PACK32(62) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ARGB);
    }
  },

  /**
   * A four-component, 32-bit packed signed integer format that has a 2-bit A component in bits
   * 30..31, a 10-bit R component in bits 20..29, a 10-bit G component in bits 10..19, and a 10-bit
   * B component in bits 0..9.
   */

  VK_FORMAT_A2R10G10B10_SINT_PACK32(63) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ARGB);
    }
  },

  /**
   * A four-component, 32-bit packed unsigned normalized format that has a 2-bit A component in bits
   * 30..31, a 10-bit B component in bits 20..29, a 10-bit G component in bits 10..19, and a 10-bit
   * R component in bits 0..9.
   */

  VK_FORMAT_A2B10G10R10_UNORM_PACK32(64) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A four-component, 32-bit packed signed normalized format that has a 2-bit A component in bits
   * 30..31, a 10-bit B component in bits 20..29, a 10-bit G component in bits 10..19, and a 10-bit
   * R component in bits 0..9.
   */

  VK_FORMAT_A2B10G10R10_SNORM_PACK32(65) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A four-component, 32-bit packed unsigned scaled integer format that has a 2-bit A component in
   * bits 30..31, a 10-bit B component in bits 20..29, a 10-bit G component in bits 10..19, and a
   * 10-bit R component in bits 0..9.
   */

  VK_FORMAT_A2B10G10R10_USCALED_PACK32(66) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A four-component, 32-bit packed signed scaled integer format that has a 2-bit A component in
   * bits 30..31, a 10-bit B component in bits 20..29, a 10-bit G component in bits 10..19, and a
   * 10-bit R component in bits 0..9.
   */

  VK_FORMAT_A2B10G10R10_SSCALED_PACK32(67) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A four-component, 32-bit packed unsigned integer format that has a 2-bit A component in bits
   * 30..31, a 10-bit B component in bits 20..29, a 10-bit G component in bits 10..19, and a 10-bit
   * R component in bits 0..9.
   */

  VK_FORMAT_A2B10G10R10_UINT_PACK32(68) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A four-component, 32-bit packed signed integer format that has a 2-bit A component in bits
   * 30..31, a 10-bit B component in bits 20..29, a 10-bit G component in bits 10..19, and a 10-bit
   * R component in bits 0..9.
   */

  VK_FORMAT_A2B10G10R10_SINT_PACK32(69) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_ABGR);
    }
  },

  /**
   * A one-component, 16-bit unsigned normalized format that has a single 16-bit R component.
   */

  VK_FORMAT_R16_UNORM(70) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 16-bit signed normalized format that has a single 16-bit R component.
   */

  VK_FORMAT_R16_SNORM(71) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 16-bit unsigned scaled integer format that has a single 16-bit R component.
   */

  VK_FORMAT_R16_USCALED(72) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 16-bit signed scaled integer format that has a single 16-bit R component.
   */

  VK_FORMAT_R16_SSCALED(73) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 16-bit unsigned integer format that has a single 16-bit R component.
   */

  VK_FORMAT_R16_UINT(74) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 16-bit signed integer format that has a single 16-bit R component.
   */

  VK_FORMAT_R16_SINT(75) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 16-bit signed floating-point format that has a single 16-bit R component.
   */

  VK_FORMAT_R16_SFLOAT(76) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A two-component, 32-bit unsigned normalized format that has a 16-bit R component in bytes 0..1,
   * and a 16-bit G component in bytes 2..3.
   */

  VK_FORMAT_R16G16_UNORM(77) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 32-bit signed normalized format that has a 16-bit R component in bytes 0..1,
   * and a 16-bit G component in bytes 2..3.
   */

  VK_FORMAT_R16G16_SNORM(78) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 32-bit unsigned scaled integer format that has a 16-bit R component in bytes
   * 0..1, and a 16-bit G component in bytes 2..3.
   */

  VK_FORMAT_R16G16_USCALED(79) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 32-bit signed scaled integer format that has a 16-bit R component in bytes
   * 0..1, and a 16-bit G component in bytes 2..3.
   */

  VK_FORMAT_R16G16_SSCALED(80) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 32-bit unsigned integer format that has a 16-bit R component in bytes 0..1,
   * and a 16-bit G component in bytes 2..3.
   */

  VK_FORMAT_R16G16_UINT(81) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 32-bit signed integer format that has a 16-bit R component in bytes 0..1, and
   * a 16-bit G component in bytes 2..3.
   */

  VK_FORMAT_R16G16_SINT(82) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 32-bit signed floating-point format that has a 16-bit R component in bytes
   * 0..1, and a 16-bit G component in bytes 2..3.
   */

  VK_FORMAT_R16G16_SFLOAT(83) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A three-component, 48-bit unsigned normalized format that has a 16-bit R component in bytes
   * 0..1, a 16-bit G component in bytes 2..3, and a 16-bit B component in bytes 4..5.
   */

  VK_FORMAT_R16G16B16_UNORM(84) {
    @Override
    public int texelSizeOctets()
    {
      return 3 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 48-bit signed normalized format that has a 16-bit R component in bytes 0..1,
   * a 16-bit G component in bytes 2..3, and a 16-bit B component in bytes 4..5.
   */

  VK_FORMAT_R16G16B16_SNORM(85) {
    @Override
    public int texelSizeOctets()
    {
      return 3 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 48-bit unsigned scaled integer format that has a 16-bit R component in bytes
   * 0..1, a 16-bit G component in bytes 2..3, and a 16-bit B component in bytes 4..5.
   */

  VK_FORMAT_R16G16B16_USCALED(86) {
    @Override
    public int texelSizeOctets()
    {
      return 3 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 48-bit signed scaled integer format that has a 16-bit R component in bytes
   * 0..1, a 16-bit G component in bytes 2..3, and a 16-bit B component in bytes 4..5.
   */

  VK_FORMAT_R16G16B16_SSCALED(87) {
    @Override
    public int texelSizeOctets()
    {
      return 3 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 48-bit unsigned integer format that has a 16-bit R component in bytes 0..1,
   * a 16-bit G component in bytes 2..3, and a 16-bit B component in bytes 4..5.
   */

  VK_FORMAT_R16G16B16_UINT(88) {
    @Override
    public int texelSizeOctets()
    {
      return 3 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 48-bit signed integer format that has a 16-bit R component in bytes 0..1, a
   * 16-bit G component in bytes 2..3, and a 16-bit B component in bytes 4..5.
   */

  VK_FORMAT_R16G16B16_SINT(89) {
    @Override
    public int texelSizeOctets()
    {
      return 3 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 48-bit signed floating-point format that has a 16-bit R component in bytes
   * 0..1, a 16-bit G component in bytes 2..3, and a 16-bit B component in bytes 4..5.
   */

  VK_FORMAT_R16G16B16_SFLOAT(90) {
    @Override
    public int texelSizeOctets()
    {
      return 3 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A four-component, 64-bit unsigned normalized format that has a 16-bit R component in bytes
   * 0..1, a 16-bit G component in bytes 2..3, a 16-bit B component in bytes 4..5, and a 16-bit A
   * component in bytes 6..7.
   */

  VK_FORMAT_R16G16B16A16_UNORM(91) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 64-bit signed normalized format that has a 16-bit R component in bytes 0..1,
   * a 16-bit G component in bytes 2..3, a 16-bit B component in bytes 4..5, and a 16-bit A
   * component in bytes 6..7.
   */

  VK_FORMAT_R16G16B16A16_SNORM(92) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 64-bit unsigned scaled integer format that has a 16-bit R component in bytes
   * 0..1, a 16-bit G component in bytes 2..3, a 16-bit B component in bytes 4..5, and a 16-bit A
   * component in bytes 6..7.
   */

  VK_FORMAT_R16G16B16A16_USCALED(93) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 64-bit signed scaled integer format that has a 16-bit R component in bytes
   * 0..1, a 16-bit G component in bytes 2..3, a 16-bit B component in bytes 4..5, and a 16-bit A
   * component in bytes 6..7.
   */

  VK_FORMAT_R16G16B16A16_SSCALED(94) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_SCALED_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 64-bit unsigned integer format that has a 16-bit R component in bytes 0..1, a
   * 16-bit G component in bytes 2..3, a 16-bit B component in bytes 4..5, and a 16-bit A component
   * in bytes 6..7.
   */

  VK_FORMAT_R16G16B16A16_UINT(95) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 64-bit signed integer format that has a 16-bit R component in bytes 0..1, a
   * 16-bit G component in bytes 2..3, a 16-bit B component in bytes 4..5, and a 16-bit A component
   * in bytes 6..7.
   */

  VK_FORMAT_R16G16B16A16_SINT(96) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 64-bit signed floating-point format that has a 16-bit R component in bytes
   * 0..1, a 16-bit G component in bytes 2..3, a 16-bit B component in bytes 4..5, and a 16-bit A
   * component in bytes 6..7.
   */

  VK_FORMAT_R16G16B16A16_SFLOAT(97) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BLEND_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT,
        VK_FORMAT_FEATURE_STORAGE_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A one-component, 32-bit unsigned integer format that has a single 32-bit R component.
   */

  VK_FORMAT_R32_UINT(98) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_IMAGE_ATOMIC_BIT,
        VK_FORMAT_FEATURE_STORAGE_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_ATOMIC_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 32-bit signed integer format that has a single 32-bit R component.
   */

  VK_FORMAT_R32_SINT(99) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_DST_BIT,
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_COLOR_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_IMAGE_ATOMIC_BIT,
        VK_FORMAT_FEATURE_STORAGE_IMAGE_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_ATOMIC_BIT,
        VK_FORMAT_FEATURE_STORAGE_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_UNIFORM_TEXEL_BUFFER_BIT,
        VK_FORMAT_FEATURE_VERTEX_BUFFER_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 32-bit signed floating-point format that has a single 32-bit R component.
   */

  VK_FORMAT_R32_SFLOAT(100) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A two-component, 64-bit unsigned integer format that has a 32-bit R component in bytes 0..3,
   * and a 32-bit G component in bytes 4..7.
   */

  VK_FORMAT_R32G32_UINT(101) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 64-bit signed integer format that has a 32-bit R component in bytes 0..3, and
   * a 32-bit G component in bytes 4..7.
   */

  VK_FORMAT_R32G32_SINT(102) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 64-bit signed floating-point format that has a 32-bit R component in bytes
   * 0..3, and a 32-bit G component in bytes 4..7.
   */

  VK_FORMAT_R32G32_SFLOAT(103) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A three-component, 96-bit unsigned integer format that has a 32-bit R component in bytes 0..3,
   * a 32-bit G component in bytes 4..7, and a 32-bit B component in bytes 8..11.
   */

  VK_FORMAT_R32G32B32_UINT(104) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 96-bit signed integer format that has a 32-bit R component in bytes 0..3, a
   * 32-bit G component in bytes 4..7, and a 32-bit B component in bytes 8..11.
   */

  VK_FORMAT_R32G32B32_SINT(105) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 96-bit signed floating-point format that has a 32-bit R component in bytes
   * 0..3, a 32-bit G component in bytes 4..7, and a 32-bit B component in bytes 8..11.
   */

  VK_FORMAT_R32G32B32_SFLOAT(106) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A four-component, 128-bit unsigned integer format that has a 32-bit R component in bytes 0..3,
   * a 32-bit G component in bytes 4..7, a 32-bit B component in bytes 8..11, and a 32-bit A
   * component in bytes 12..15.
   */

  VK_FORMAT_R32G32B32A32_UINT(107) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 128-bit signed integer format that has a 32-bit R component in bytes 0..3, a
   * 32-bit G component in bytes 4..7, a 32-bit B component in bytes 8..11, and a 32-bit A component
   * in bytes 12..15.
   */

  VK_FORMAT_R32G32B32A32_SINT(108) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 128-bit signed floating-point format that has a 32-bit R component in bytes
   * 0..3, a 32-bit G component in bytes 4..7, a 32-bit B component in bytes 8..11, and a 32-bit A
   * component in bytes 12..15.
   */

  VK_FORMAT_R32G32B32A32_SFLOAT(109) {
    @Override
    public int texelSizeOctets()
    {
      return 4 * 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A one-component, 64-bit unsigned integer format that has a single 64-bit R component.
   */

  VK_FORMAT_R64_UINT(110) {
    @Override
    public int texelSizeOctets()
    {
      return 8 * 1;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 64-bit signed integer format that has a single 64-bit R component.
   */

  VK_FORMAT_R64_SINT(111) {
    @Override
    public int texelSizeOctets()
    {
      return 8 * 1;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, 64-bit signed floating-point format that has a single 64-bit R component.
   */

  VK_FORMAT_R64_SFLOAT(112) {
    @Override
    public int texelSizeOctets()
    {
      return 8 * 1;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A two-component, 128-bit unsigned integer format that has a 64-bit R component in bytes 0..7,
   * and a 64-bit G component in bytes 8..15.
   */

  VK_FORMAT_R64G64_UINT(113) {
    @Override
    public int texelSizeOctets()
    {
      return 8 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 128-bit signed integer format that has a 64-bit R component in bytes 0..7, and
   * a 64-bit G component in bytes 8..15.
   */

  VK_FORMAT_R64G64_SINT(114) {
    @Override
    public int texelSizeOctets()
    {
      return 8 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, 128-bit signed floating-point format that has a 64-bit R component in bytes
   * 0..7, and a 64-bit G component in bytes 8..15.
   */

  VK_FORMAT_R64G64_SFLOAT(115) {
    @Override
    public int texelSizeOctets()
    {
      return 8 * 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A three-component, 192-bit unsigned integer format that has a 64-bit R component in bytes 0..7,
   * a 64-bit G component in bytes 8..15, and a 64-bit B component in bytes 16..23.
   */

  VK_FORMAT_R64G64B64_UINT(116) {
    @Override
    public int texelSizeOctets()
    {
      return 8 * 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 192-bit signed integer format that has a 64-bit R component in bytes 0..7, a
   * 64-bit G component in bytes 8..15, and a 64-bit B component in bytes 16..23.
   */

  VK_FORMAT_R64G64B64_SINT(117) {
    @Override
    public int texelSizeOctets()
    {
      return 8 * 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, 192-bit signed floating-point format that has a 64-bit R component in bytes
   * 0..7, a 64-bit G component in bytes 8..15, and a 64-bit B component in bytes 16..23.
   */

  VK_FORMAT_R64G64B64_SFLOAT(118) {
    @Override
    public int texelSizeOctets()
    {
      return 8 * 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A four-component, 256-bit unsigned integer format that has a 64-bit R component in bytes 0..7,
   * a 64-bit G component in bytes 8..15, a 64-bit B component in bytes 16..23, and a 64-bit A
   * component in bytes 24..31.
   */

  VK_FORMAT_R64G64B64A64_UINT(119) {
    @Override
    public int texelSizeOctets()
    {
      return 8 * 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 256-bit signed integer format that has a 64-bit R component in bytes 0..7, a
   * 64-bit G component in bytes 8..15, a 64-bit B component in bytes 16..23, and a 64-bit A
   * component in bytes 24..31.
   */

  VK_FORMAT_R64G64B64A64_SINT(120) {
    @Override
    public int texelSizeOctets()
    {
      return 8 * 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, 256-bit signed floating-point format that has a 64-bit R component in bytes
   * 0..7, a 64-bit G component in bytes 8..15, a 64-bit B component in bytes 16..23, and a 64-bit A
   * component in bytes 24..31.
   */

  VK_FORMAT_R64G64B64A64_SFLOAT(121) {
    @Override
    public int texelSizeOctets()
    {
      return 8 * 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A three-component, 32-bit packed unsigned floating-point format that has a 10-bit B component
   * in bits 22..31, an 11-bit G component in bits 11..21, an 11-bit R component in bits 0..10. See
   * Section 2.7.4, “Unsigned 10-Bit Floating-Point Numbers” and Section 2.7.3, “Unsigned 11-Bit
   * Floating-Point Numbers”.
   */

  VK_FORMAT_B10G11R11_UFLOAT_PACK32(122) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGR);
    }
  },

  /**
   * A three-component, 32-bit packed unsigned floating-point format that has a 5-bit shared
   * exponent in bits 27..31, a 9-bit B component mantissa in bits 18..26, a 9-bit G component
   * mantissa in bits 9..17, and a 9-bit R component mantissa in bits 0..8.
   */

  VK_FORMAT_E5B9G9R9_UFLOAT_PACK32(123) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_BGR);
    }
  },

  /**
   * A one-component, 16-bit unsigned normalized format that has a single 16-bit depth component.
   */

  VK_FORMAT_D16_UNORM(124) {
    @Override
    public int texelSizeOctets()
    {
      return 2;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_DEPTH;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_DEPTH_STENCIL_ATTACHMENT_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.empty();
    }
  },

  /**
   * A two-component, 32-bit format that has 24 unsigned normalized bits in the depth component and,
   * optionally, 8 bits that are unused.
   */

  VK_FORMAT_X8_D24_UNORM_PACK32(125) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_DEPTH;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.empty();
    }
  },

  /**
   * A one-component, 32-bit signed floating-point format that has 32-bits in the depth component.
   */

  VK_FORMAT_D32_SFLOAT(126) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_DEPTH;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of(
        VK_FORMAT_FEATURE_BLIT_SRC_BIT,
        VK_FORMAT_FEATURE_SAMPLED_IMAGE_BIT);
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.empty();
    }
  },

  /**
   * A one-component, 8-bit unsigned integer format that has 8-bits in the stencil component.
   */

  VK_FORMAT_S8_UINT(127) {
    @Override
    public int texelSizeOctets()
    {
      return 1;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_INTEGER_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_STENCIL;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_NONE;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.empty();
    }
  },

  /**
   * A two-component, 24-bit format that has 16 unsigned normalized bits in the depth component and
   * 8 unsigned integer bits in the stencil component.
   */

  VK_FORMAT_D16_UNORM_S8_UINT(128) {
    @Override
    public int texelSizeOctets()
    {
      return 3;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_DEPTH_STENCIL;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.empty();
    }
  },

  /**
   * A two-component, 32-bit packed format that has 8 unsigned integer bits in the stencil
   * component, and 24 unsigned normalized bits in the depth component.
   */

  VK_FORMAT_D24_UNORM_S8_UINT(129) {
    @Override
    public int texelSizeOctets()
    {
      return 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_DEPTH_STENCIL;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.empty();
    }
  },

  /**
   * A two-component format that has 32 signed float bits in the depth component and 8 unsigned
   * integer bits in the stencil component. There are optionally 24-bits that are unused.
   */

  VK_FORMAT_D32_SFLOAT_S8_UINT(130) {
    @Override
    public int texelSizeOctets()
    {
      return 4 + 4;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_UNCOMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_DEPTH_STENCIL;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.empty();
    }
  },

  /**
   * A three-component, block-compressed format where each 64-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGB texel data. This format has no alpha and is considered
   * opaque.
   */

  VK_FORMAT_BC1_RGB_UNORM_BLOCK(131) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, block-compressed format where each 64-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGB texel data with sRGB nonlinear encoding. This format
   * has no alpha and is considered opaque.
   */

  VK_FORMAT_BC1_RGB_SRGB_BLOCK(132) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A four-component, block-compressed format where each 64-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGB texel data, and provides 1 bit of alpha.
   */

  VK_FORMAT_BC1_RGBA_UNORM_BLOCK(133) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, block-compressed format where each 64-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGB texel data with sRGB nonlinear encoding, and provides
   * 1 bit of alpha.
   */

  VK_FORMAT_BC1_RGBA_SRGB_BLOCK(134) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, block-compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGBA texel data with the first 64 bits encoding alpha
   * values followed by 64 bits encoding RGB values.
   */

  VK_FORMAT_BC2_UNORM_BLOCK(135) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, block-compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGBA texel data with the first 64 bits encoding alpha
   * values followed by 64 bits encoding RGB values with sRGB nonlinear encoding.
   */

  VK_FORMAT_BC2_SRGB_BLOCK(136) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, block-compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGBA texel data with the first 64 bits encoding alpha
   * values followed by 64 bits encoding RGB values.
   */

  VK_FORMAT_BC3_UNORM_BLOCK(137) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, block-compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGBA texel data with the first 64 bits encoding alpha
   * values followed by 64 bits encoding RGB values with sRGB nonlinear encoding.
   */

  VK_FORMAT_BC3_SRGB_BLOCK(138) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A one-component, block-compressed format where each 64-bit compressed texel block encodes a 4x4
   * rectangle of unsigned normalized red texel data.
   */

  VK_FORMAT_BC4_UNORM_BLOCK(139) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, block-compressed format where each 64-bit compressed texel block encodes a 4x4
   * rectangle of signed normalized red texel data.
   */

  VK_FORMAT_BC4_SNORM_BLOCK(140) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A two-component, block-compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RG texel data with the first 64 bits encoding red values
   * followed by 64 bits encoding green values.
   */

  VK_FORMAT_BC5_UNORM_BLOCK(141) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, block-compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of signed normalized RG texel data with the first 64 bits encoding red values
   * followed by 64 bits encoding green values.
   */

  VK_FORMAT_BC5_SNORM_BLOCK(142) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A three-component, block-compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned floating-point RGB texel data.
   */

  VK_FORMAT_BC6H_UFLOAT_BLOCK(143) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, block-compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of signed floating-point RGB texel data.
   */

  VK_FORMAT_BC6H_SFLOAT_BLOCK(144) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_FLOATING_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A four-component, block-compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_BC7_UNORM_BLOCK(145) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, block-compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_BC7_SRGB_BLOCK(146) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A three-component, ETC2 compressed format where each 64-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGB texel data. This format has no alpha and is considered
   * opaque.
   */

  VK_FORMAT_ETC2_R8G8B8_UNORM_BLOCK(147) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A three-component, ETC2 compressed format where each 64-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGB texel data with sRGB nonlinear encoding. This format
   * has no alpha and is considered opaque.
   */

  VK_FORMAT_ETC2_R8G8B8_SRGB_BLOCK(148) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A four-component, ETC2 compressed format where each 64-bit compressed texel block encodes a 4x4
   * rectangle of unsigned normalized RGB texel data, and provides 1 bit of alpha.
   */

  VK_FORMAT_ETC2_R8G8B8A1_UNORM_BLOCK(149) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ETC2 compressed format where each 64-bit compressed texel block encodes a 4x4
   * rectangle of unsigned normalized RGB texel data with sRGB nonlinear encoding, and provides 1
   * bit of alpha.
   */

  VK_FORMAT_ETC2_R8G8B8A1_SRGB_BLOCK(150) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGB);
    }
  },

  /**
   * A four-component, ETC2 compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGBA texel data with the first 64 bits encoding alpha
   * values followed by 64 bits encoding RGB values.
   */

  VK_FORMAT_ETC2_R8G8B8A8_UNORM_BLOCK(151) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ETC2 compressed format where each 64-bit compressed texel block encodes a 4x4
   * rectangle of unsigned normalized RGBA texel data with the first 64 bits encoding alpha values
   * followed by 64 bits encoding RGB values with sRGB nonlinear encoding applied.
   */

  VK_FORMAT_ETC2_R8G8B8A8_SRGB_BLOCK(152) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A one-component, ETC2 compressed format where each 64-bit compressed texel block encodes a 4x4
   * rectangle of unsigned normalized red texel data.
   */

  VK_FORMAT_EAC_R11_UNORM_BLOCK(153) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A one-component, ETC2 compressed format where each 64-bit compressed texel block encodes a 4x4
   * rectangle of signed normalized red texel data.
   */

  VK_FORMAT_EAC_R11_SNORM_BLOCK(154) {
    @Override
    public int texelSizeOctets()
    {
      return 8;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_R);
    }
  },

  /**
   * A two-component, ETC2 compressed format where each 128-bit compressed texel block encodes a 4x4
   * rectangle of unsigned normalized RG texel data with the first 64 bits encoding red values
   * followed by 64 bits encoding green values.
   */

  VK_FORMAT_EAC_R11G11_UNORM_BLOCK(155) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A two-component, ETC2 compressed format where each 128-bit compressed texel block encodes a 4x4
   * rectangle of signed normalized RG texel data with the first 64 bits encoding red values
   * followed by 64 bits encoding green values.
   */

  VK_FORMAT_EAC_R11G11_SNORM_BLOCK(156) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RG);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_4x4_UNORM_BLOCK(157) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 4x4 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_4x4_SRGB_BLOCK(158) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 5x4 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_5x4_UNORM_BLOCK(159) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 5x4 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_5x4_SRGB_BLOCK(160) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 5x5 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_5x5_UNORM_BLOCK(161) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 5x5 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_5x5_SRGB_BLOCK(162) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 6x5 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_6x5_UNORM_BLOCK(163) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 6x5 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_6x5_SRGB_BLOCK(164) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 6x6 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_6x6_UNORM_BLOCK(165) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 6x6 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_6x6_SRGB_BLOCK(166) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 8x5 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_8x5_UNORM_BLOCK(167) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 8x5 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_8x5_SRGB_BLOCK(168) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 8x6 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_8x6_UNORM_BLOCK(169) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 8x6 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_8x6_SRGB_BLOCK(170) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 8x8 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_8x8_UNORM_BLOCK(171) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 8x8 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_8x8_SRGB_BLOCK(172) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 10x5 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_10x5_UNORM_BLOCK(173) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 10x5 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_10x5_SRGB_BLOCK(174) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 10x6 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_10x6_UNORM_BLOCK(175) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 10x6 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_10x6_SRGB_BLOCK(176) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 10x8 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_10x8_UNORM_BLOCK(177) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 10x8 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_10x8_SRGB_BLOCK(178) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 10x10 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_10x10_UNORM_BLOCK(179) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 10x10 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_10x10_SRGB_BLOCK(180) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 12x10 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_12x10_UNORM_BLOCK(181) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 12x10 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_12x10_SRGB_BLOCK(182) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 12x12 rectangle of unsigned normalized RGBA texel data.
   */

  VK_FORMAT_ASTC_12x12_UNORM_BLOCK(183) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_LINEAR;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  },

  /**
   * A four-component, ASTC compressed format where each 128-bit compressed texel block encodes a
   * 12x12 rectangle of unsigned normalized RGBA texel data with sRGB nonlinear encoding applied to
   * the RGB components.
   */

  VK_FORMAT_ASTC_12x12_SRGB_BLOCK(184) {
    @Override
    public int texelSizeOctets()
    {
      return 16;
    }

    @Override
    public VulkanFormatCompressed compressed()
    {
      return FORMAT_COMPRESSED;
    }

    @Override
    public VulkanFormatData data()
    {
      return FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED;
    }

    @Override
    public VulkanFormatInterpretation interpretation()
    {
      return FORMAT_INTERPRETATION_COLOR;
    }

    @Override
    public VulkanFormatSpace space()
    {
      return FORMAT_SPACE_SRGB;
    }

    @Override
    public Set<VulkanFormatFeatureFlag> mandatoryFeatures()
    {
      return Set.of();
    }

    @Override
    public Optional<VulkanFormatColorChannels> colorChannels()
    {
      return Optional.of(COLOR_CHANNELS_RGBA);
    }
  };

  private static final Map<Integer, VulkanFormat> VALUES =
    VulkanEnumMaps.map(values());

  private final int value;

  VulkanFormat(
    final int in_value)
  {
    this.value = in_value;
  }

  /**
   * @param v The constant's integer value
   *
   * @return The constant associated with the given integer value
   */

  public static Optional<VulkanFormat> fromInteger(
    final int v)
  {
    return Optional.ofNullable(VALUES.get(Integer.valueOf(v)));
  }

  @Override
  public int value()
  {
    return this.value;
  }

  /**
   * @return The size of a single texel in octets
   */

  public abstract int texelSizeOctets();

  /**
   * @return Whether this format is compressed
   */

  public abstract VulkanFormatCompressed compressed();

  /**
   * @return The format data type
   */

  public abstract VulkanFormatData data();

  /**
   * @return The format interpretation
   */

  public abstract VulkanFormatInterpretation interpretation();

  /**
   * @return The format space
   */

  public abstract VulkanFormatSpace space();

  /**
   * Some formats in the Vulkan specification have a degree of mandatory
   * support. That is, there are some formats in the specification that
   * are guaranteed to be provided by all Vulkan implementations, and there
   * is a set of features for each required format that must be supported.
   *
   * @return The set of features mandated by the specification
   */

  public abstract Set<VulkanFormatFeatureFlag> mandatoryFeatures();

  /**
   * @return The color channels present, if any
   */

  public abstract Optional<VulkanFormatColorChannels> colorChannels();
}
