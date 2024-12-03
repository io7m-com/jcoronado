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
 * Bitmask specifying sample counts supported for an image used for storage operations.
 *
 * @see "VkSampleCountFlagBits"
 */

@VulkanAPIEnumType(vulkanEnum = "VkSampleCountFlagBits")
public enum VulkanSampleCountFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies an image with one sample per pixel.
   */

  VK_SAMPLE_COUNT_1_BIT(0x00000001),

  /**
   * Specifies an image with 2 samples per pixel.
   */

  VK_SAMPLE_COUNT_2_BIT(0x00000002),

  /**
   * Specifies an image with 4 samples per pixel.
   */

  VK_SAMPLE_COUNT_4_BIT(0x00000004),

  /**
   * Specifies an image with 8 samples per pixel.
   */

  VK_SAMPLE_COUNT_8_BIT(0x00000008),

  /**
   * Specifies an image with 16 samples per pixel.
   */

  VK_SAMPLE_COUNT_16_BIT(0x00000010),

  /**
   * Specifies an image with 32 samples per pixel.
   */

  VK_SAMPLE_COUNT_32_BIT(0x00000020),

  /**
   * Specifies an image with 64 samples per pixel.
   */

  VK_SAMPLE_COUNT_64_BIT(0x00000040);

  private final int value;

  VulkanSampleCountFlag(
    final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
