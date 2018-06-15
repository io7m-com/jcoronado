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
 * @see "VkImageUsageFlags"
 */

public enum VulkanImageUsageFlag implements VulkanEnumBitmaskType
{
  /**
   * Can be used as a source of transfer operations
   */

  VK_IMAGE_USAGE_TRANSFER_SRC_BIT(1),

  /**
   * Can be used as a destination of transfer operations
   */

  VK_IMAGE_USAGE_TRANSFER_DST_BIT(2),

  /**
   * Can be sampled from (SAMPLED_IMAGE and COMBINED_IMAGE_SAMPLER descriptor types)
   */

  VK_IMAGE_USAGE_SAMPLED_BIT(4),

  /**
   * Can be used as storage image (STORAGE_IMAGE descriptor type)
   */

  VK_IMAGE_USAGE_STORAGE_BIT(8),

  /**
   * Can be used as framebuffer color attachment
   */

  VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT(16),

  /**
   * Can be used as framebuffer depth/stencil attachment
   */

  VK_IMAGE_USAGE_DEPTH_STENCIL_ATTACHMENT_BIT(32),

  /**
   * Image data not needed outside of rendering
   */

  VK_IMAGE_USAGE_TRANSIENT_ATTACHMENT_BIT(64),

  /**
   * Can be used as framebuffer input attachment
   */

  VK_IMAGE_USAGE_INPUT_ATTACHMENT_BIT(128);

  private final int value;

  VulkanImageUsageFlag(
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
