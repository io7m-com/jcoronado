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
 * Allocation scope.
 *
 * @see "VkSystemAllocationScope"
 */

@VulkanAPIEnumType(vulkanEnum = "VkSystemAllocationScope")
public enum VulkanSystemAllocationScope implements VulkanEnumIntegerType
{
  /**
   * Specifies that the allocation is scoped to the duration of the Vulkan command.
   */

  VK_SYSTEM_ALLOCATION_SCOPE_COMMAND(0),

  /**
   * Specifies that the allocation is scoped to the lifetime of the Vulkan object that is being
   * created or used.
   */

  VK_SYSTEM_ALLOCATION_SCOPE_OBJECT(1),

  /**
   * Specifies that the allocation is scoped to the lifetime of a VkPipelineCache or
   * VkValidationCacheEXT object.
   */

  VK_SYSTEM_ALLOCATION_SCOPE_CACHE(2),

  /**
   * Specifies that the allocation is scoped to the lifetime of the Vulkan device.
   */

  VK_SYSTEM_ALLOCATION_SCOPE_DEVICE(3),

  /**
   * Specifies that the allocation is scoped to the lifetime of the Vulkan instance.
   */

  VK_SYSTEM_ALLOCATION_SCOPE_INSTANCE(4);

  private final int value;

  VulkanSystemAllocationScope(final int i)
  {
    this.value = i;
  }

  /**
   * Create a scope from the given integer.
   *
   * @param index The integer value
   *
   * @return The scope associated with the integer
   */

  public static VulkanSystemAllocationScope ofInt(final int index)
  {
    switch (index) {
      case 0:
        return VK_SYSTEM_ALLOCATION_SCOPE_COMMAND;
      case 1:
        return VK_SYSTEM_ALLOCATION_SCOPE_OBJECT;
      case 2:
        return VK_SYSTEM_ALLOCATION_SCOPE_CACHE;
      case 3:
        return VK_SYSTEM_ALLOCATION_SCOPE_DEVICE;
      case 4:
        return VK_SYSTEM_ALLOCATION_SCOPE_INSTANCE;
      default:
        throw new IllegalArgumentException("Unknown scope: " + index);
    }
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
