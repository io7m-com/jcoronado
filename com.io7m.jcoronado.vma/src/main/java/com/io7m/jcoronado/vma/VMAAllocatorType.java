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

import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanBufferType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanHandleDispatchableType;
import com.io7m.jcoronado.api.VulkanImageCreateInfo;
import com.io7m.jcoronado.api.VulkanImageType;

/**
 * A configured VMA allocator.
 */

public interface VMAAllocatorType extends VulkanHandleDispatchableType
{
  /**
   * Allocate a buffer.
   *
   * @param alloc_create_info  The allocation creation info
   * @param buffer_create_info The buffer creation info
   *
   * @return An allocation
   *
   * @throws VulkanException On errors
   */

  VMAAllocationResult<VulkanBufferType> createBuffer(
    VMAAllocationCreateInfo alloc_create_info,
    VulkanBufferCreateInfo buffer_create_info)
    throws VulkanException;

  /**
   * Allocate an image.
   *
   * @param alloc_create_info The allocation creation info
   * @param image_create_info The image creation info
   *
   * @return An allocation
   *
   * @throws VulkanException On errors
   */

  VMAAllocationResult<VulkanImageType> createImage(
    VMAAllocationCreateInfo alloc_create_info,
    VulkanImageCreateInfo image_create_info)
    throws VulkanException;

  /**
   * Map the memory associated with the given allocation.
   *
   * @param allocation The VMA allocation
   *
   * @return The memory, mapped
   *
   * @throws VulkanException On errors
   */

  VMAMappedMemoryType mapMemory(
    VMAAllocationType allocation)
    throws VulkanException;
}
