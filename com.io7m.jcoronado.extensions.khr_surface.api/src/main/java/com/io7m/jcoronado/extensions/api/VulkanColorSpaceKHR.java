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

import com.io7m.jcoronado.api.VulkanEnumIntegerType;
import com.io7m.jcoronado.api.VulkanEnumMaps;

import java.util.Map;
import java.util.Optional;

/**
 * @see "VkColorSpaceKHR"
 */

public enum VulkanColorSpaceKHR implements VulkanEnumIntegerType
{
  /**
   * The presentation engine supports the sRGB colorspace.
   */

  VK_COLOR_SPACE_SRGB_NONLINEAR_KHR(0);

  private static final Map<Integer, VulkanColorSpaceKHR> VALUES =
    VulkanEnumMaps.map(VulkanColorSpaceKHR.values());

  private final int value;

  VulkanColorSpaceKHR(
    final int in_value)
  {
    this.value = in_value;
  }

  /**
   * @param v The constant's integer value
   *
   * @return The constant associated with the given integer value
   */

  public static Optional<VulkanColorSpaceKHR> fromInteger(
    final int v)
  {
    return Optional.ofNullable(VALUES.get(Integer.valueOf(v)));
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
