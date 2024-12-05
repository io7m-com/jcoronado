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

import com.io7m.jcoronado.api.VulkanHostAllocatorType;
import com.io7m.jcoronado.api.VulkanInternalAllocation;
import com.io7m.jcoronado.api.VulkanSystemAllocationScope;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkAllocationCallbacks;
import org.lwjgl.vulkan.VkAllocationFunction;
import org.lwjgl.vulkan.VkFreeFunction;
import org.lwjgl.vulkan.VkInternalAllocationNotification;
import org.lwjgl.vulkan.VkInternalFreeNotification;
import org.lwjgl.vulkan.VkReallocationFunction;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A holder for allocated callbacks.
 */

public final class VulkanLWJGLHostAllocatorProxy implements AutoCloseable
{
  private final VkAllocationCallbacks callback_buffer;
  private final VulkanHostAllocatorType java_allocator;
  private final VkAllocationFunction pfn_allocate;
  private final VkFreeFunction pfn_free;
  private final VkReallocationFunction pfn_reallocate;
  private final VkInternalAllocationNotification pfn_internal_allocation;
  private final VkInternalFreeNotification pfn_internal_free;
  private final AtomicBoolean closed;

  private VulkanLWJGLHostAllocatorProxy(
    final VkAllocationCallbacks in_callback_buffer,
    final VulkanHostAllocatorType in_java_allocator,
    final VkAllocationFunction in_pfn_allocate,
    final VkFreeFunction in_pfn_free,
    final VkReallocationFunction in_pfn_reallocate,
    final VkInternalAllocationNotification in_pfn_internal_allocation,
    final VkInternalFreeNotification in_pfn_internal_free)
  {
    this.java_allocator = in_java_allocator;
    if (in_java_allocator != null) {
      Objects.requireNonNull(in_callback_buffer, "callback_buffer");
      Objects.requireNonNull(in_pfn_allocate, "pfn_allocate");
      Objects.requireNonNull(in_pfn_free, "pfn_free");
      Objects.requireNonNull(in_pfn_reallocate, "pfn_reallocate");
      Objects.requireNonNull(
        in_pfn_internal_allocation,
        "pfn_internal_allocation");
      Objects.requireNonNull(in_pfn_internal_free, "pfn_internal_free");
    }

    this.callback_buffer = in_callback_buffer;
    this.pfn_allocate = in_pfn_allocate;
    this.pfn_free = in_pfn_free;
    this.pfn_reallocate = in_pfn_reallocate;
    this.pfn_internal_allocation = in_pfn_internal_allocation;
    this.pfn_internal_free = in_pfn_internal_free;
    this.closed = new AtomicBoolean(false);
  }

  /**
   * Create a proxy for the given allocator.
   *
   * @param stack     A stack allocator
   * @param allocator The host allocator
   *
   * @return A proxy
   */

  public static VulkanLWJGLHostAllocatorProxy create(
    final MemoryStack stack,
    final Optional<VulkanHostAllocatorType> allocator)
  {
    if (allocator.isPresent()) {
      final var java_allocator = allocator.get();

      final var pfn_allocate =
        VkAllocationFunction.create(
          (user_data, size, alignment, scope) ->
            java_allocator.allocate(
              size,
              alignment,
              VulkanSystemAllocationScope.ofInt(scope)));

      final var pfn_free =
        VkFreeFunction.create((user_data, address) -> java_allocator.deallocate(
          address));

      final var pfn_reallocate =
        VkReallocationFunction.create(
          (user_data, address, size, alignment, scope) ->
            java_allocator.reallocate(
              address,
              size,
              alignment,
              VulkanSystemAllocationScope.ofInt(scope)));

      final var pfn_internal_allocation =
        VkInternalAllocationNotification.create(
          (user_data, size, type, scope) ->
            java_allocator.onAllocation(
              size,
              VulkanInternalAllocation.ofInt(type),
              VulkanSystemAllocationScope.ofInt(scope)));

      final var pfn_internal_free =
        VkInternalFreeNotification.create(
          (user_data, size, type, scope) ->
            java_allocator.onFree(
              size,
              VulkanInternalAllocation.ofInt(type),
              VulkanSystemAllocationScope.ofInt(scope)));

      final var callback_buffer =
        VkAllocationCallbacks.calloc(stack)
          .pfnAllocation(pfn_allocate)
          .pfnFree(pfn_free)
          .pfnReallocation(pfn_reallocate)
          .pfnInternalAllocation(pfn_internal_allocation)
          .pfnInternalFree(pfn_internal_free);

      return new VulkanLWJGLHostAllocatorProxy(
        callback_buffer,
        java_allocator,
        pfn_allocate,
        pfn_free,
        pfn_reallocate,
        pfn_internal_allocation,
        pfn_internal_free);
    }

    return new VulkanLWJGLHostAllocatorProxy(
      null,
      null,
      null,
      null,
      null,
      null,
      null);
  }

  /**
   * @return The callback buffer
   */

  public VkAllocationCallbacks callbackBuffer()
  {
    if (this.closed.get()) {
      throw new IllegalStateException("Callbacks have been closed");
    }
    return this.callback_buffer;
  }

  @Override
  public void close()
  {
    if (this.closed.compareAndSet(false, true)) {
      if (this.java_allocator != null) {
        this.pfn_allocate.free();
        this.pfn_free.free();
        this.pfn_reallocate.free();
        this.pfn_internal_allocation.free();
        this.pfn_internal_free.free();
      }
    }
  }
}
