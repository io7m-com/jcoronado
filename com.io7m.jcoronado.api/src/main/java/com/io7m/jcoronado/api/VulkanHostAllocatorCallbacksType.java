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

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

/**
 * Vulkan host allocation functions.
 *
 * @see "VkAllocationCallbacks"
 */

@VulkanAPIStructType(vulkanStruct = "VkAllocationCallbacks")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanHostAllocatorCallbacksType
{
  /**
   * @return The allocation function
   */

  @Value.Parameter
  AllocationFunctionType allocation();

  /**
   * @return The deallocation function
   */

  @Value.Parameter
  DeallocationFunctionType deallocation();

  /**
   * @return The reallocation function
   */

  @Value.Parameter
  ReallocationFunctionType reallocation();

  /**
   * @return The internal allocation notification function
   */

  @Value.Parameter
  InternalAllocationNotificationType onInternalAllocation();

  /**
   * @return The internal deallocation notification function
   */

  @Value.Parameter
  InternalFreeNotificationType onInternalDeallocation();

  /**
   * A function to allocate memory.
   */

  interface AllocationFunctionType
  {
    /**
     * Allocate {@code size} octets of memory, aligned to {@code alignment}, of scope {@code scope}.
     * The function must return the address of the allocated memory as an integer, or {@code 0} if
     * the allocation has failed. The function MUST NOT raise exceptions under any circumstances.
     *
     * @param size      The size of the allocation
     * @param alignment The alignment of the allocation
     * @param scope     The scope of the allocation
     *
     * @return The memory address
     */

    long allocate(
      long size,
      long alignment,
      VulkanSystemAllocationScope scope);
  }

  /**
   * A function to reallocate memory.
   */

  interface ReallocationFunctionType
  {
    /**
     * Reallocate {@code size} octets of memory, aligned to {@code alignment}, of scope {@code
     * scope}. The function must return the address of the allocated memory as an integer, or {@code
     * 0} if the allocation has failed. The function MUST NOT raise exceptions under any
     * circumstances.
     *
     * @param original  The original memory address
     * @param size      The size of the allocation
     * @param alignment The alignment of the allocation
     * @param scope     The scope of the allocation
     *
     * @return The memory address
     */

    long reallocate(
      long original,
      long size,
      long alignment,
      VulkanSystemAllocationScope scope);
  }

  /**
   * A function to deallocate memory.
   */

  interface DeallocationFunctionType
  {
    /**
     * Deallocate memory. The function MUST NOT raise exceptions under any circumstances.
     *
     * @param original The original memory address
     */

    void deallocate(
      long original);
  }

  /**
   * A function called on internal allocations.
   */

  interface InternalAllocationNotificationType
  {
    /**
     * Called upon internal allocations. The function MUST NOT raise exceptions under any
     * circumstances.
     *
     * @param size  The allocation size in bytes
     * @param type  The type of allocation
     * @param scope The scope of the allocation
     */

    void onAllocation(
      long size,
      VulkanInternalAllocation type,
      VulkanSystemAllocationScope scope);
  }

  /**
   * A function called on internal allocations.
   */

  interface InternalFreeNotificationType
  {
    /**
     * Called upon internal deallocations. The function MUST NOT raise exceptions under any
     * circumstances.
     *
     * @param size  The allocation size in bytes
     * @param type  The type of allocation
     * @param scope The scope of the allocation
     */

    void onFree(
      long size,
      VulkanInternalAllocation type,
      VulkanSystemAllocationScope scope);
  }
}
