/*
 * Copyright © 2026 Mark Raynsford <code@io7m.com> https://www.io7m.com
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
 * @see "VkMemoryAllocateFlagBits"
 */

@VulkanAPIEnumType(vulkanEnum = "VkMemoryAllocateFlagBits")
public enum VulkanMemoryAllocateFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies that memory will be allocated for the devices in
   * VkMemoryAllocateFlagsInfo::deviceMask.
   */
  VK_MEMORY_ALLOCATE_DEVICE_MASK_BIT(0x00000001),
  /**
   * Specifies that the memory can be attached to a buffer object created with
   * the VK_BUFFER_USAGE_SHADER_DEVICE_ADDRESS_BIT usage flag set.
   */
  VK_MEMORY_ALLOCATE_DEVICE_ADDRESS_BIT(0x00000002),
  /**
   * Specifies that the memory’s address can be saved and reused on a
   * subsequent run (e.g. for trace capture and replay), see
   * VkBufferOpaqueCaptureAddressCreateInfo for more detail. If this bit is
   * set, VK_MEMORY_ALLOCATE_DEVICE_ADDRESS_BIT must also be set.
   */
  VK_MEMORY_ALLOCATE_DEVICE_ADDRESS_CAPTURE_REPLAY_BIT(0x00000004);

  private final int value;

  VulkanMemoryAllocateFlag(
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
