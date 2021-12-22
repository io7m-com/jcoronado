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

package com.io7m.jcoronado.lwjgl;

import com.io7m.jcoronado.api.VulkanAllocationFailedException;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanTemporaryAllocatorType;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * An LWJGL based implementation of the {@link VulkanTemporaryAllocatorType}
 * interface.
 */

public final class VulkanLWJGLTemporaryAllocator implements
  VulkanTemporaryAllocatorType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLTemporaryAllocator.class);

  private final MemoryUtil.MemoryAllocator alloc;

  private VulkanLWJGLTemporaryAllocator()
  {
    this.alloc = MemoryUtil.getAllocator();
  }

  /**
   * @return A new temporary allocator
   */

  public static VulkanTemporaryAllocatorType create()
  {
    return new VulkanLWJGLTemporaryAllocator();
  }

  @Override
  public <T, E extends Exception> T withAllocation(
    final long size,
    final long alignment,
    final RawMemoryReceiverType<T, E> receiver)
    throws VulkanException, E
  {
    Objects.requireNonNull(receiver, "receiver");

    var address = 0L;
    try {
      address = this.alloc.aligned_alloc(alignment, size);
      if (address == 0L) {
        throw new VulkanAllocationFailedException(
          new StringBuilder(64)
            .append("Could not allocate ")
            .append(Long.toUnsignedString(size))
            .append(" bytes with alignment ")
            .append(Long.toUnsignedString(alignment))
            .toString());
      }

      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "allocated 0x{} (size {} align {})",
          Long.toUnsignedString(address, 16),
          Long.toUnsignedString(size),
          Long.toUnsignedString(alignment));
      }

      return receiver.receive(address, size);
    } finally {
      if (address != 0L) {
        if (LOG.isTraceEnabled()) {
          LOG.trace(
            "freed 0x{} (size {} align {})",
            Long.toUnsignedString(address, 16),
            Long.toUnsignedString(size),
            Long.toUnsignedString(alignment));
        }
        this.alloc.aligned_free(address);
      }
    }
  }

  @Override
  public <T, E extends Exception> T withAllocationBuffer(
    final long size,
    final long alignment,
    final ByteBufferMemoryReceiverType<T, E> receiver)
    throws VulkanException, E
  {
    Objects.requireNonNull(receiver, "receiver");

    return this.withAllocation(
      size,
      alignment,
      (address, capacity) -> receiver.receive(
        MemoryUtil.memByteBuffer(address, Math.toIntExact(capacity))));
  }

  @Override
  public <T, E extends Exception> T withAllocationBufferInitialized(
    final byte[] data,
    final long alignment,
    final ByteBufferMemoryReceiverType<T, E> receiver)
    throws VulkanException, E
  {
    Objects.requireNonNull(data, "data");
    Objects.requireNonNull(receiver, "receiver");

    return this.withAllocationBuffer(
      data.length,
      alignment,
      address -> {
        address.put(data);
        address.position(0);
        return receiver.receive(address);
      });
  }
}
