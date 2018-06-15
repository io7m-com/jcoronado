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

import java.nio.ByteBuffer;

/**
 * An allocator for temporary, aligned memory. The API is designed such that it should be very
 * difficult, short of deliberate sabotage, to leak memory or use memory after it has been freed.
 */

public interface VulkanTemporaryAllocatorType
{
  /**
   * Allocate {@code size} octets of memory, aligned to {@code alignment}, pass it to {@code
   * receiver}, then unconditionally deallocate the memory before returning the value of {@code T}
   * returned by {@code receiver}.
   *
   * @param size      The size of the memory
   * @param alignment The alignment of the memory
   * @param receiver  The receiver of the allocated memory
   * @param <T>       The type of returned values
   * @param <E>       The type of raised exceptions
   *
   * @return The value returned by {@code receiver}
   *
   * @throws VulkanException If {@code receiver} raises {@code VulkanException}
   * @throws E               If {@code receiver} raises {@code E}
   */

  <T, E extends Exception> T withAllocation(
    long size,
    long alignment,
    RawMemoryReceiverType<T, E> receiver)
    throws VulkanException, E;

  /**
   * Allocate {@code size} octets of memory, aligned to {@code alignment}, pass it to {@code
   * receiver}, then unconditionally deallocate the memory before returning the value of {@code T}
   * returned by {@code receiver}.
   *
   * @param size      The size of the memory
   * @param alignment The alignment of the memory
   * @param receiver  The receiver of the allocated memory
   * @param <T>       The type of returned values
   * @param <E>       The type of raised exceptions
   *
   * @return The value returned by {@code receiver}
   *
   * @throws VulkanException If {@code receiver} raises {@code VulkanException}
   * @throws E               If {@code receiver} raises {@code E}
   */

  <T, E extends Exception> T withAllocationBuffer(
    long size,
    long alignment,
    ByteBufferMemoryReceiverType<T, E> receiver)
    throws VulkanException, E;

  /**
   * Allocate {@code data.length} octets of memory, aligned to {@code alignment}, initialize it with
   * the contents of {@code data}, pass it to {@code receiver}, then unconditionally deallocate the
   * memory before returning the value of {@code T} returned by {@code receiver}.
   *
   * @param data      The initial contents of the memory
   * @param alignment The alignment of the memory
   * @param receiver  The receiver of the allocated memory
   * @param <T>       The type of returned values
   * @param <E>       The type of raised exceptions
   *
   * @return The value returned by {@code receiver}
   *
   * @throws VulkanException If {@code receiver} raises {@code VulkanException}
   * @throws E               If {@code receiver} raises {@code E}
   */

  <T, E extends Exception> T withAllocationBufferInitialized(
    byte[] data,
    long alignment,
    ByteBufferMemoryReceiverType<T, E> receiver)
    throws VulkanException, E;

  /**
   * A receiver of allocated memory.
   *
   * @param <T> The type of returned values
   * @param <E> The type of raised exceptions (other than {@link VulkanException})
   */

  @FunctionalInterface
  interface RawMemoryReceiverType<T, E extends Exception>
  {
    /**
     * Receive allocated memory.
     *
     * @param address The address of the allocated memory
     * @param size    The size of the allocated memory
     *
     * @return A value of {@code T}
     *
     * @throws VulkanException If required
     * @throws E               If required
     */

    T receive(
      long address,
      long size)
      throws VulkanException, E;
  }

  /**
   * A receiver of allocated memory.
   *
   * @param <T> The type of returned values
   * @param <E> The type of raised exceptions (other than {@link VulkanException})
   */

  @FunctionalInterface
  interface ByteBufferMemoryReceiverType<T, E extends Exception>
  {
    /**
     * Receive allocated memory.
     *
     * @param buffer The allocated memory as a byte buffer
     *
     * @return A value of {@code T}
     *
     * @throws VulkanException If required
     * @throws E               If required
     */

    T receive(ByteBuffer buffer)
      throws VulkanException, E;
  }
}
