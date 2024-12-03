/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanDebuggingType;
import com.io7m.jcoronado.api.VulkanHandleType;

import java.util.Objects;

/**
 * A debugging interface.
 */

public final class VulkanLWJGLDebuggingNoOp
  implements VulkanDebuggingType
{
  VulkanLWJGLDebuggingNoOp()
  {

  }

  @Override
  public void setObjectName(
    final VulkanHandleType handle,
    final String name)
  {
    Objects.requireNonNull(handle, "handle");
    Objects.requireNonNull(name, "name");
  }

  @Override
  public VulkanDebugRegionType begin(
    final VulkanCommandBufferType commandBuffer,
    final String label,
    final Color color)
  {
    return new DebugRegion();
  }

  private static final class DebugRegion
    implements VulkanDebugRegionType
  {
    private boolean closed;

    DebugRegion()
    {

    }

    @Override
    public void insertInto(
      final String label,
      final Color color)
    {

    }

    @Override
    public void insertInto(
      final String label)
    {

    }

    @Override
    public void close()
    {
      this.closed = true;
    }

    @Override
    public boolean isClosed()
    {
      return this.closed;
    }
  }
}
