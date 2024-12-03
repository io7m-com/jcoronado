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
 * @see "VmaAllocationCreateFlagBits"
 */

public enum VMAAllocationCreateFlag implements VulkanEnumBitmaskType
{
  /**
   * Set this flag if the allocation should have its own memory block.
   *
   * Use it for special, big resources, like fullscreen images used as attachments.
   *
   * This flag must also be used for host visible resources that you want to map simultaneously
   * because otherwise they might end up as regions of the same `VkDeviceMemory`, while mapping same
   * `VkDeviceMemory` multiple times simultaneously is illegal.
   *
   * You should not use this flag if VmaAllocationCreateInfo::pool is not null.
   */

  VMA_ALLOCATION_CREATE_DEDICATED_MEMORY_BIT(0x00000001),

  /**
   * Set this flag to only try to allocate from existing `VkDeviceMemory` blocks and never create
   * new such block.
   *
   * If new allocation cannot be placed in any of the existing blocks, allocation fails with
   * `VK_ERROR_OUT_OF_DEVICE_MEMORY` error.
   *
   * You should not use #VMA_ALLOCATION_CREATE_DEDICATED_MEMORY_BIT and
   * #VMA_ALLOCATION_CREATE_NEVER_ALLOCATE_BIT at the same time. It makes no sense.
   *
   * If VmaAllocationCreateInfo::pool is not null, this flag is implied and ignored.
   */

  VMA_ALLOCATION_CREATE_NEVER_ALLOCATE_BIT(0x00000002),

  /**
   * Set this flag to use a memory that will be persistently mapped and retrieve pointer to it.
   *
   * Pointer to mapped memory will be returned through VmaAllocationInfo::pMappedData.
   *
   * Is it valid to use this flag for allocation made from memory type that is not `HOST_VISIBLE`.
   * This flag is then ignored and memory is not mapped. This is useful if you need an allocation
   * that is efficient to use on GPU (`DEVICE_LOCAL`) and still want to map it directly if possible
   * on platforms that support it (e.g. Intel GPU).
   *
   * You should not use this flag together with #VMA_ALLOCATION_CREATE_CAN_BECOME_LOST_BIT.
   */

  VMA_ALLOCATION_CREATE_MAPPED_BIT(0x00000004),

  /**
   * Allocation created with this flag can become lost as a result of another allocation with
   * #VMA_ALLOCATION_CREATE_CAN_MAKE_OTHER_LOST_BIT flag, so you must check it before use.
   *
   * To check if allocation is not lost, call vmaGetAllocationInfo() and check if
   * VmaAllocationInfo::deviceMemory is not `VK_NULL_HANDLE`.
   *
   * For details about supporting lost allocations, see Lost Allocations chapter of User Guide on
   * Main Page.
   *
   * You should not use this flag together with #VMA_ALLOCATION_CREATE_MAPPED_BIT.
   */

  VMA_ALLOCATION_CREATE_CAN_BECOME_LOST_BIT(0x00000008),

  /**
   * While creating allocation using this flag, other allocations that were created with flag
   * #VMA_ALLOCATION_CREATE_CAN_BECOME_LOST_BIT can become lost.
   *
   * For details about supporting lost allocations, see Lost Allocations chapter of User Guide on
   * Main Page.
   */

  VMA_ALLOCATION_CREATE_CAN_MAKE_OTHER_LOST_BIT(0x00000010),

  /**
   * Set this flag to treat VmaAllocationCreateInfo::pUserData as pointer to a null-terminated
   * string. Instead of copying pointer value, a local copy of the string is made and stored in
   * allocation's `pUserData`. The string is automatically freed together with the allocation. It is
   * also used in vmaBuildStatsString().
   */

  VMA_ALLOCATION_CREATE_USER_DATA_COPY_STRING_BIT(0x00000020);

  private final int value;

  VMAAllocationCreateFlag(
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
