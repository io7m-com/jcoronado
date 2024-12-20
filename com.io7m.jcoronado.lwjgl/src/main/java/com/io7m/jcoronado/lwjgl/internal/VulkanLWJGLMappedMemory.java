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

package com.io7m.jcoronado.lwjgl.internal;

import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanMappedMemoryType;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * A section of mapped memory.
 */

public final class VulkanLWJGLMappedMemory implements VulkanMappedMemoryType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLMappedMemory.class);

  private final VkDevice device;
  private final ByteBuffer buffer;
  private final VulkanLWJGLDeviceMemory memory;
  private final VulkanLWJGLFlushRangedFunctionType flush;
  private boolean mapped;

  VulkanLWJGLMappedMemory(
    final VkDevice in_device,
    final VulkanLWJGLDeviceMemory in_cmemory,
    final VulkanLWJGLFlushRangedFunctionType flush_callback,
    final long in_address,
    final long in_size)
  {
    this.device = Objects.requireNonNull(in_device, "device");
    this.memory = Objects.requireNonNull(in_cmemory, "memory");
    this.flush = Objects.requireNonNull(flush_callback, "flush_callback");
    this.mapped = true;
    this.buffer = MemoryUtil.memByteBuffer(
      in_address,
      Math.toIntExact(in_size));
  }

  @Override
  public boolean isMapped()
  {
    return this.mapped;
  }

  @Override
  public void flushRange(
    final long offset,
    final long size)
    throws VulkanException
  {
    if (this.mapped) {
      this.flush.flushRange(this.memory, offset, size);
    }
  }

  @Override
  public void flush()
    throws VulkanException
  {
    if (this.mapped) {
      this.flush.flushRange(this.memory, 0L, VK10.VK_WHOLE_SIZE);
    }
  }

  @Override
  public void close()
  {
    if (this.mapped) {
      try {
        final var address = this.memory.handle();
        if (LOG.isTraceEnabled()) {
          LOG.trace(
            "Unmapping memory: 0x{}",
            Long.toUnsignedString(address, 16));
        }
        VK10.vkUnmapMemory(this.device, address);
      } finally {
        this.mapped = false;
      }
    }
  }

  @Override
  public ByteBuffer asByteBuffer()
  {
    return this.buffer;
  }
}
