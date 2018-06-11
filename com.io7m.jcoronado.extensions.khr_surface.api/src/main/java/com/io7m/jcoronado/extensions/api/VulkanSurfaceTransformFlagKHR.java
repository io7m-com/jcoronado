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

package com.io7m.jcoronado.extensions.api;

/**
 * @see "VkSurfaceTransformFlagBitsKHR"
 */

public enum VulkanSurfaceTransformFlagKHR
{
  /**
   * The image content is presented without being transformed.
   */

  VK_SURFACE_TRANSFORM_IDENTITY_BIT_KHR(0x00000001),

  /**
   * The image content is rotated 90 degrees clockwise.
   */

  VK_SURFACE_TRANSFORM_ROTATE_90_BIT_KHR(0x00000002),

  /**
   * The image content is rotated 180 degrees clockwise.
   */

  VK_SURFACE_TRANSFORM_ROTATE_180_BIT_KHR(0x00000004),

  /**
   * The image content is rotated 270 degrees clockwise.
   */

  VK_SURFACE_TRANSFORM_ROTATE_270_BIT_KHR(0x00000008),

  /**
   * The image content is mirrored horizontally.
   */

  VK_SURFACE_TRANSFORM_HORIZONTAL_MIRROR_BIT_KHR(0x00000010),

  /**
   * The image content is mirrored horizontally, then rotated 90 degrees clockwise.
   */

  VK_SURFACE_TRANSFORM_HORIZONTAL_MIRROR_ROTATE_90_BIT_KHR(0x00000020),

  /**
   * The image content is mirrored horizontally, then rotated 180 degrees clockwise.
   */

  VK_SURFACE_TRANSFORM_HORIZONTAL_MIRROR_ROTATE_180_BIT_KHR(0x00000040),

  /**
   * The image content is mirrored horizontally, then rotated 270 degrees clockwise.
   */

  VK_SURFACE_TRANSFORM_HORIZONTAL_MIRROR_ROTATE_270_BIT_KHR(0x00000080),

  /**
   * The presentation transform is not specified, and is instead determined by platform-specific
   * considerations and mechanisms outside Vulkan.
   */

  VK_SURFACE_TRANSFORM_INHERIT_BIT_KHR(0x00000100);

  private final int value;

  VulkanSurfaceTransformFlagKHR(
    final int i)
  {
    this.value = i;
  }

  /**
   * @return The integer value of the constant
   */

  public int value()
  {
    return this.value;
  }
}
