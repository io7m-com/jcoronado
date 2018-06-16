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
 * Bitmask specifying how execution and memory dependencies are formed.
 *
 * @see "VkDependencyFlagBits"
 */

public enum VulkanDependencyFlag implements VulkanEnumBitmaskType
{
  /**
   * VK_DEPENDENCY_BY_REGION_BIT specifies that dependencies will be framebuffer-local.
   */

  VK_DEPENDENCY_BY_REGION_BIT(0x00000001),

  /**
   * VK_DEPENDENCY_DEVICE_GROUP_BIT specifies that dependencies are non-device-local dependency.
   */

  VK_DEPENDENCY_DEVICE_GROUP_BIT(0x00000004),

  /**
   * VK_DEPENDENCY_VIEW_LOCAL_BIT specifies that a subpass has more than one view.
   */

  VK_DEPENDENCY_VIEW_LOCAL_BIT(0x00000002);

  private final int value;

  VulkanDependencyFlag(
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
