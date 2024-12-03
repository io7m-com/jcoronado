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
 * The type of image view.
 *
 * @see "VkImageViewType"
 */

@VulkanAPIEnumType(vulkanEnum = "VkImageViewType")
public enum VulkanImageViewKind implements VulkanEnumIntegerType
{
  /**
   * A 1D image
   */

  VK_IMAGE_VIEW_TYPE_1D(0),

  /**
   * A 2D image
   */

  VK_IMAGE_VIEW_TYPE_2D(1),

  /**
   * A 3D image
   */

  VK_IMAGE_VIEW_TYPE_3D(2),

  /**
   * A cube map
   */

  VK_IMAGE_VIEW_TYPE_CUBE(3),

  /**
   * A 1D array image
   */

  VK_IMAGE_VIEW_TYPE_1D_ARRAY(4),

  /**
   * A 2D array image
   */

  VK_IMAGE_VIEW_TYPE_2D_ARRAY(5),

  /**
   * A cube map array image
   */

  VK_IMAGE_VIEW_TYPE_CUBE_ARRAY(6);

  private final int value;

  VulkanImageViewKind(
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
