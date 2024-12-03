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
 * @see "VkBufferCreateFlagBits"
 */

@VulkanAPIEnumType(vulkanEnum = "VkBufferCreateFlagBits")
public enum VulkanBufferCreateFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies that the buffer will be backed using sparse memory binding.
   */

  VK_BUFFER_CREATE_SPARSE_BINDING_BIT(0x00000001),

  /**
   * Specifies that the buffer can be partially backed using sparse memory binding. Buffers created
   * with this flag must also be created with the VK_BUFFER_CREATE_SPARSE_BINDING_BIT flag.
   */

  VK_BUFFER_CREATE_SPARSE_RESIDENCY_BIT(0x00000002),

  /**
   * Specifies that the buffer will be backed using sparse memory binding with memory ranges that
   * might also simultaneously be backing another buffer (or another portion of the same buffer).
   * Buffers created with this flag must also be created with the VK_BUFFER_CREATE_SPARSE_BINDING_BIT
   * flag.
   */

  VK_BUFFER_CREATE_SPARSE_ALIASED_BIT(0x00000004),

  /**
   * Specifies that the buffer is a protected buffer.
   */

  VK_BUFFER_CREATE_PROTECTED_BIT(0x00000008);

  private final int value;

  VulkanBufferCreateFlag(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
