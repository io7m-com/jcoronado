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

import com.io7m.jcoronado.vma.VMAAllocatorType;

import java.util.Objects;

/**
 * @see "VmaAllocator"
 */

public final class VMALWJGLAllocator implements VMAAllocatorType
{
  private final VulkanLWJGLLogicalDevice device;
  private final long allocator_address;

  VMALWJGLAllocator(
    final VulkanLWJGLLogicalDevice in_device,
    final long in_allocator_address)
  {
    this.device = Objects.requireNonNull(in_device, "device");
    this.allocator_address = in_allocator_address;
  }

  @Override
  public boolean equals(final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || !Objects.equals(this.getClass(), o.getClass())) {
      return false;
    }
    final VMALWJGLAllocator that = (VMALWJGLAllocator) o;
    return this.allocator_address == that.allocator_address
      && Objects.equals(this.device, that.device);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(this.device, Long.valueOf(this.allocator_address));
  }

  @Override
  public String toString()
  {
    return new StringBuilder(32)
      .append("[VMALWJGLAllocator 0x")
      .append(Long.toUnsignedString(this.allocator_address, 16))
      .append(']')
      .toString();
  }
}
