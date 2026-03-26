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

package com.io7m.jcoronado.vma;

import com.io7m.jcoronado.api.VulkanEnumBitmaskType;

/**
 * @see "VmaAllocatorCreateFlagBits"
 */

public enum VMAAllocatorCreateFlag implements VulkanEnumBitmaskType
{
  /**
   * Allocator and all objects created from it will not be synchronized
   * internally, so you must guarantee they are used from only one thread at
   * a time or synchronized externally by you.
   */

  VMA_ALLOCATOR_CREATE_EXTERNALLY_SYNCHRONIZED_BIT(0x00000001),

  /**
   * Enables usage of VK_KHR_dedicated_allocation extension.
   */

  VMA_ALLOCATOR_CREATE_KHR_DEDICATED_ALLOCATION_BIT(0x00000002),

  /**
   * Enables usage of VK_KHR_bind_memory2 extension.
   */

  VMA_ALLOCATOR_CREATE_KHR_BIND_MEMORY2_BIT(0x00000004),

  /**
   * Enables usage of VK_EXT_memory_budget extension.
   */

  VMA_ALLOCATOR_CREATE_EXT_MEMORY_BUDGET_BIT(0x00000008),

  /**
   * Enables usage of "buffer device address" feature, which allows you to use
   * function `vkGetBufferDeviceAddress*` to get raw GPU pointer to a buffer
   * and pass it for usage inside a shader.
   */

  VMA_ALLOCATOR_CREATE_BUFFER_DEVICE_ADDRESS_BIT(0x00000020);

  private final int value;

  VMAAllocatorCreateFlag(
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
