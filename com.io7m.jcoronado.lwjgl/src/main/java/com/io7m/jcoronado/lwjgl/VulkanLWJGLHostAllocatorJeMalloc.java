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

import com.io7m.jcoronado.api.VulkanHostAllocatorCallbacks;
import com.io7m.jcoronado.api.VulkanHostAllocatorType;
import com.io7m.jcoronado.api.VulkanInternalAllocation;
import com.io7m.jcoronado.api.VulkanSystemAllocationScope;
import org.lwjgl.system.jemalloc.JEmalloc;

/**
 * A JeMalloc based host allocator.
 */

public final class VulkanLWJGLHostAllocatorJeMalloc
  implements VulkanHostAllocatorType
{
  /**
   * Construct an allocator.
   */

  public VulkanLWJGLHostAllocatorJeMalloc()
  {

  }

  @Override
  public long allocate(
    final long size,
    final long alignment,
    final VulkanSystemAllocationScope scope)
  {
    return JEmalloc.nje_aligned_alloc(alignment, size);
  }

  @Override
  public long reallocate(
    final long address,
    final long size,
    final long alignment,
    final VulkanSystemAllocationScope scope)
  {
    return JEmalloc.nje_realloc(address, size);
  }

  @Override
  public void deallocate(final long address)
  {
    JEmalloc.nje_free(address);
  }

  @Override
  public void onAllocation(
    final long size,
    final VulkanInternalAllocation type,
    final VulkanSystemAllocationScope scope)
  {

  }

  @Override
  public void onFree(
    final long size,
    final VulkanInternalAllocation type,
    final VulkanSystemAllocationScope scope)
  {

  }

  @Override
  public VulkanHostAllocatorCallbacks createCallbacks()
  {
    return VulkanHostAllocatorCallbacks.of(this, this, this, this, this);
  }
}
