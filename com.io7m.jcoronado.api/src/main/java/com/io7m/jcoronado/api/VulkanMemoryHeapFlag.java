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
 * @see "VkMemoryHeapFlagBits"
 */

public enum VulkanMemoryHeapFlag
{
  /**
   * Specifies that the heap corresponds to device local memory.
   */

  VK_MEMORY_HEAP_DEVICE_LOCAL_BIT(0x00000001),

  /**
   * Specifies that in a logical device representing more than one physical
   * device, there is a per-physical device instance of the heap memory. By
   * default, an allocation from such a heap will be replicated to each
   * physical device’s instance of the heap.
   */

  VK_MEMORY_HEAP_MULTI_INSTANCE_BIT(0x00000002);

  private final int value;

  VulkanMemoryHeapFlag(
    final int i)
  {
    this.value = i;
  }

  /**
   * @return The integer value of the flag
   */

  public int value()
  {
    return this.value;
  }
}
