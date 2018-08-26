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

import com.io7m.immutables.styles.ImmutablesStyleType;
import com.io7m.jcoronado.api.VulkanDeviceMemoryType;
import org.immutables.value.Value;

import java.util.Optional;

/**
 * Information about an allocation. This is an immutable snapshot of an allocation.
 */

@Value.Immutable
@ImmutablesStyleType
public interface VMAAllocationInfoType
{
  /**
   * @return The memory type index that this allocation was allocated from.
   */

  @Value.Parameter
  long memoryType();

  /**
   * @return A handle to the Vulkan memory object.
   *
   * Same memory object can be shared by multiple allocations.
   *
   * It can change after call to vmaDefragment() if this allocation is passed to the function, or if
   * allocation is lost.
   *
   * If the allocation is lost, it is equal to `VK_NULL_HANDLE`.
   */

  @Value.Parameter
  Optional<VulkanDeviceMemoryType> deviceMemory();

  /**
   * @return The offset into the device memory object to the beginning of this allocation, in bytes.
   * (deviceMemory, offset) pair is unique to this allocation.
   *
   * It can change after call to vmaDefragment() if this allocation is passed to the function, or if
   * allocation is lost.
   */

  @Value.Parameter
  long offset();

  /**
   * @return The size of this allocation, in bytes.
   *
   * It never changes, unless allocation is lost.
   */

  @Value.Parameter
  long size();
}
