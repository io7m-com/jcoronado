/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
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


package com.io7m.jcoronado.tests.api;

import com.io7m.jcoronado.api.VulkanFormat;
import com.io7m.jcoronado.api.VulkanFormatCompressed;
import com.io7m.jcoronado.api.VulkanFormatData;
import com.io7m.jcoronado.api.VulkanFormatInterpretation;
import com.io7m.jcoronado.api.VulkanFormatInterpretation.*;
import com.io7m.jcoronado.api.VulkanFormatSpace;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_D16_UNORM_S8_UINT;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_D24_UNORM_S8_UINT;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_D32_SFLOAT_S8_UINT;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for format transcription errors.
 */

public final class VulkanFormatTest
{
  @Test
  public void testDepth()
  {
    for (final var format : VulkanFormat.values()) {
      if (format.name().contains("_D")) {
        assertTrue(
          (format.interpretation() == VulkanFormatInterpretation.FORMAT_INTERPRETATION_DEPTH) ||
          (format.interpretation() == VulkanFormatInterpretation.FORMAT_INTERPRETATION_DEPTH_STENCIL),
          "Format %s must have a depth interpretation".formatted(format)
        );
      }
    }
  }

  @Test
  public void testColor()
  {
    for (final var format : VulkanFormat.values()) {
      if (format.name().contains("_R")) {
        assertTrue(
          (format.interpretation() == VulkanFormatInterpretation.FORMAT_INTERPRETATION_COLOR),
          "Format %s must have a color interpretation".formatted(format)
        );
      }
    }
  }

  @Test
  public void testCompressed()
  {
    final var compressionTags =
      Set.of(
        "_ASTC_",
        "_BC1_",
        "_BC2_",
        "_BC3_",
        "_BC4_",
        "_BC5_",
        "_BC6H_",
        "_BC7_",
        "_ETC2_",
        "_EAC_"
      );

    FORMAT_CHECK: for (final var format : VulkanFormat.values()) {
      for (final var tag : compressionTags) {
        if (format.name().contains(tag)) {
          assertTrue(
            format.compressed() == VulkanFormatCompressed.FORMAT_COMPRESSED,
            "Format %s must be compressed".formatted(format)
          );
          continue FORMAT_CHECK;
        }
      }
      assertTrue(
        format.compressed() == VulkanFormatCompressed.FORMAT_UNCOMPRESSED,
        "Format %s must be uncompressed".formatted(format)
      );
    }
  }

  @Test
  public void testUNORM()
  {
    for (final var format : VulkanFormat.values()) {
      if (format.name().contains("UNORM")) {
        assertTrue(
          format.data() == VulkanFormatData.FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED,
          "Format %s must be unsigned normalized".formatted(format)
        );
      }
    }
  }

  @Test
  public void testSNORM()
  {
    for (final var format : VulkanFormat.values()) {
      if (format.name().contains("SNORM")) {
        assertTrue(
          format.data() == VulkanFormatData.FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED,
          "Format %s must be signed normalized".formatted(format)
        );
      }
    }
  }

  @Test
  public void testUFLOAT()
  {
    for (final var format : VulkanFormat.values()) {
      if (format.name().contains("UFLOAT")) {
        assertTrue(
          format.data() == VulkanFormatData.FORMAT_DATA_FLOATING_POINT_UNSIGNED,
          "Format %s must be unsigned floating".formatted(format)
        );
      }
    }
  }

  @Test
  public void testSFLOAT()
  {
    for (final var format : VulkanFormat.values()) {
      if (format.name().contains("SFLOAT")) {
        assertTrue(
          format.data() == VulkanFormatData.FORMAT_DATA_FLOATING_POINT_SIGNED,
          "Format %s must be signed floating".formatted(format)
        );
      }
    }
  }

  @Test
  public void testUSCALED()
  {
    for (final var format : VulkanFormat.values()) {
      if (format.name().contains("USCALED")) {
        assertTrue(
          format.data() == VulkanFormatData.FORMAT_DATA_SCALED_UNSIGNED,
          "Format %s must be unsigned scaled".formatted(format)
        );
      }
    }
  }

  @Test
  public void testSSCALED()
  {
    for (final var format : VulkanFormat.values()) {
      if (format.name().contains("SSCALED")) {
        assertTrue(
          format.data() == VulkanFormatData.FORMAT_DATA_SCALED_SIGNED,
          "Format %s must be signed scaled".formatted(format)
        );
      }
    }
  }

  @Test
  public void testUINT()
  {
    for (final var format : VulkanFormat.values()) {
      if (format == VK_FORMAT_D16_UNORM_S8_UINT) {
        continue;
      }
      if (format == VK_FORMAT_D24_UNORM_S8_UINT) {
        continue;
      }
      if (format == VK_FORMAT_D32_SFLOAT_S8_UINT) {
        continue;
      }

      if (format.name().contains("UINT")) {
        assertTrue(
          format.data() == VulkanFormatData.FORMAT_DATA_INTEGER_UNSIGNED,
          "Format %s must be unsigned integer".formatted(format)
        );
      }
    }
  }

  @Test
  public void testSINT()
  {
    for (final var format : VulkanFormat.values()) {
      if (format.name().contains("SINT")) {
        assertTrue(
          format.data() == VulkanFormatData.FORMAT_DATA_INTEGER_SIGNED,
          "Format %s must be signed integer".formatted(format)
        );
      }
    }
  }

  @Test
  public void testSRGB()
  {
    for (final var format : VulkanFormat.values()) {
      if (format.name().contains("SRGB")) {
        assertTrue(
          format.space() == VulkanFormatSpace.FORMAT_SPACE_SRGB,
          "Format %s must be SRGB".formatted(format)
        );
      } else {
        assertTrue(
          format.space() != VulkanFormatSpace.FORMAT_SPACE_SRGB,
          "Format %s must not be SRGB".formatted(format)
        );
      }
    }
  }
}
