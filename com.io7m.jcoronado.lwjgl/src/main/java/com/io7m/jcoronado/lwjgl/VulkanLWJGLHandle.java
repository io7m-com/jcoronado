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

import com.io7m.jcoronado.api.VulkanDestroyedException;
import com.io7m.jcoronado.api.VulkanHandleType;
import org.slf4j.Logger;

import java.util.Objects;

abstract class VulkanLWJGLHandle implements VulkanHandleType
{
  private final Ownership ownership;
  private final VulkanLWJGLHostAllocatorProxy host_allocator_proxy;
  private boolean closed;

  VulkanLWJGLHandle(
    final Ownership in_ownership,
    final VulkanLWJGLHostAllocatorProxy in_host_allocator_proxy)
  {
    this.closed = false;
    this.ownership =
      Objects.requireNonNull(in_ownership, "ownership");
    this.host_allocator_proxy =
      Objects.requireNonNull(
        in_host_allocator_proxy,
        "in_host_allocator_proxy");
  }

  protected final VulkanLWJGLHostAllocatorProxy hostAllocatorProxy()
  {
    return this.host_allocator_proxy;
  }

  @Override
  public final boolean isClosed()
  {
    return this.closed;
  }

  protected abstract Logger logger();

  @Override
  public final void close()
  {
    if (!this.closed) {
      try {
        switch (this.ownership) {
          case USER_OWNED:
            this.closeActual();
            break;
          case VULKAN_OWNED:
            // XXX: Vulkan will free the object itself, but should the user be told that
            //      they did something wrong by trying to close it themselves?
            break;
        }


      } finally {
        this.closed = true;
      }
    }
  }

  protected final void checkNotClosed()
    throws VulkanDestroyedException
  {
    if (this.closed) {
      throw new VulkanDestroyedException("Object has been closed/destroyed.");
    }
  }

  protected abstract void closeActual();

  /**
   * The ownership status of the object.
   */

  enum Ownership
  {

    /**
     * The user owns the object and is responsible for destroying it.
     */

    USER_OWNED,

    /**
     * Vulkan owns the object and the user is not responsible for destroying
     * it.
     */

    VULKAN_OWNED
  }
}
