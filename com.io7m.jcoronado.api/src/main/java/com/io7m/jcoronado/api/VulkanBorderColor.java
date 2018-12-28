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
 * @see "VkBorderColor"
 */

@VulkanAPIEnumType(vulkanEnum = "VkBorderColor")
public enum VulkanBorderColor implements VulkanEnumIntegerType
{
  /**
   * Specifies a transparent, floating-point format, black color.
   */

  VK_BORDER_COLOR_FLOAT_TRANSPARENT_BLACK(0),

  /**
   * Specifies a transparent, integer format, black color.
   */

  VK_BORDER_COLOR_INT_TRANSPARENT_BLACK(1),

  /**
   * Specifies an opaque, floating-point format, black color.
   */

  VK_BORDER_COLOR_FLOAT_OPAQUE_BLACK(2),

  /**
   * Specifies an opaque, integer format, black color.
   */

  VK_BORDER_COLOR_INT_OPAQUE_BLACK(3),

  /**
   * Specifies an opaque, floating-point format, white color.
   */

  VK_BORDER_COLOR_FLOAT_OPAQUE_WHITE(4),

  /**
   * Specifies an opaque, integer format, white color.
   */

  VK_BORDER_COLOR_INT_OPAQUE_WHITE(5);

  private final int value;

  VulkanBorderColor(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
