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
 * The properties of a heap.
 *
 * @see "VkMemoryPropertyFlagBits"
 */

public enum VulkanMemoryPropertyFlag
{
  /**
   * Specifies that memory allocated with this type is the most efficient for
   * device access.
   */

  VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT(0x00000001),

  /**
   * Specifies that memory allocated with this type can be mapped for host
   * access.
   */

  VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT(0x00000002),

  /**
   * Specifies that the host cache management commands vkFlushMappedMemoryRanges
   * and vkInvalidateMappedMemoryRanges are not needed to flush host writes to
   * the device or make device writes visible to the host, respectively.
   */

  VK_MEMORY_PROPERTY_HOST_COHERENT_BIT(0x00000004),

  /**
   * Specifies that memory allocated with this type is cached on the host. Host
   * memory accesses to uncached memory are slower than to cached memory,
   * however uncached memory is always host coherent.
   */

  VK_MEMORY_PROPERTY_HOST_CACHED_BIT(0x00000008),

  /**
   * Specifies that the memory type only allows device access to the memory and
   * is lazily allocated.
   */

  VK_MEMORY_PROPERTY_LAZILY_ALLOCATED_BIT(0x00000010),

  /**
   * Specifies that the memory type only allows device access to the memory,
   * and allows protected queue operations to access the memory.
   */

  VK_MEMORY_PROPERTY_PROTECTED_BIT(0x00000020);

  private final int value;

  VulkanMemoryPropertyFlag(
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
