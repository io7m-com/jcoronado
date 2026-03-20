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
 * Bitmask indicating supported depth and stencil resolve modes.
 *
 * @see "VkResolveModeFlagBits"
 */

@VulkanAPIEnumType(vulkanEnum = "VkSampleCountFlagBits")
public enum VulkanResolveModeFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies that no resolve operation is done.
   */
  VK_RESOLVE_MODE_NONE(0),
  /**
   * Specifies that result of the resolve operation is equal to the value of sample 0.
   */
  VK_RESOLVE_MODE_SAMPLE_ZERO(0x00000001),
  /**
   * Specifies that result of the resolve operation is the average of the sample values.
   */
  VK_RESOLVE_MODE_AVERAGE(0x00000002),
  /**
   * Specifies that result of the resolve operation is the minimum of the sample values.
   */
  VK_RESOLVE_MODE_MIN(0x00000004),
  /**
   * Specifies that result of the resolve operation is the maximum of the sample values.
   */
  VK_RESOLVE_MODE_MAX(0x00000008);

  private final int value;

  VulkanResolveModeFlag(
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
