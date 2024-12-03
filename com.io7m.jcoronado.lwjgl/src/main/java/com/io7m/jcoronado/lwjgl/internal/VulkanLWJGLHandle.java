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

import com.io7m.jcoronado.api.VulkanDestroyedException;
import com.io7m.jcoronado.api.VulkanHandleType;
import org.slf4j.Logger;

import java.util.Objects;

sealed abstract class VulkanLWJGLHandle
  implements VulkanHandleType
  permits
  VMALWJGLAllocator,
  VMALWJGLAllocator.VMALWJGLMappedMemory,
  VulkanLWJGLBuffer,
  VulkanLWJGLBufferView,
  VulkanLWJGLCommandBuffer,
  VulkanLWJGLCommandPool,
  VulkanLWJGLDescriptorPool,
  VulkanLWJGLDescriptorSet,
  VulkanLWJGLDescriptorSetLayout,
  VulkanLWJGLDeviceMemory,
  VulkanLWJGLEvent,
  VulkanLWJGLExtDebugUtilsMessenger,
  VulkanLWJGLExtKHRSurfaceValue,
  VulkanLWJGLExtKHRSwapChain.VulkanLWJGLKHRSwapChain,
  VulkanLWJGLFence,
  VulkanLWJGLFramebuffer,
  VulkanLWJGLImage,
  VulkanLWJGLImageView,
  VulkanLWJGLInstance,
  VulkanLWJGLLogicalDevice,
  VulkanLWJGLPhysicalDevice,
  VulkanLWJGLPipeline,
  VulkanLWJGLPipelineCache,
  VulkanLWJGLPipelineLayout,
  VulkanLWJGLQueryPool,
  VulkanLWJGLQueue,
  VulkanLWJGLRenderPass,
  VulkanLWJGLSampler,
  VulkanLWJGLSemaphore,
  VulkanLWJGLShaderModule
{
  private final Ownership ownership;
  private final VulkanLWJGLHostAllocatorProxy host_allocator_proxy;
  private final long handle;
  private boolean closed;

  VulkanLWJGLHandle(
    final Ownership in_ownership,
    final VulkanLWJGLHostAllocatorProxy in_host_allocator_proxy,
    final long inHandle)
  {
    this.closed = false;
    this.ownership =
      Objects.requireNonNull(in_ownership, "ownership");
    this.host_allocator_proxy =
      Objects.requireNonNull(
        in_host_allocator_proxy,
        "in_host_allocator_proxy"
      );
    this.handle = inHandle;
  }

  /**
   * @return The underlying host allocator proxy
   */

  public final VulkanLWJGLHostAllocatorProxy hostAllocatorProxy()
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

  @Override
  public final boolean equals(
    final Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof final VulkanLWJGLHandle that)) {
      return false;
    }
    return this.handle == that.handle;
  }

  @Override
  public final int hashCode()
  {
    return Objects.hashCode(Long.valueOf(this.handle));
  }

  @Override
  public final String toString()
  {
    return String.format(
      "[%s 0x%s]",
      this.getClass().getSimpleName(),
      Long.toUnsignedString(this.handle, 16)
    );
  }

  /**
   * @return The raw handle
   */

  public final long handle()
  {
    return this.handle;
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
