/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
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
 * The index of a queue family.
 *
 * @param value The actual index value
 */

public record VulkanQueueFamilyIndex(int value)
  implements Comparable<VulkanQueueFamilyIndex>
{
  private static final VulkanQueueFamilyIndex IGNORED =
    new VulkanQueueFamilyIndex(-1);

  private static final VulkanQueueFamilyIndex EXTERNAL =
    new VulkanQueueFamilyIndex(-2);

  /**
   * @return The special queue family index that indicates that a queue family
   * parameter or member is ignored.
   *
   * @see "VK_QUEUE_FAMILY_IGNORED"
   */

  public static VulkanQueueFamilyIndex ignored()
  {
    return IGNORED;
  }

  /**
   * @return The special queue family index that represents any queue external
   * to the resource's current Vulkan instance.
   *
   * @see "VK_QUEUE_FAMILY_EXTERNAL"
   */

  public static VulkanQueueFamilyIndex external()
  {
    return EXTERNAL;
  }

  @Override
  public int compareTo(
    final VulkanQueueFamilyIndex other)
  {
    return Integer.compare(this.value, other.value);
  }
}
