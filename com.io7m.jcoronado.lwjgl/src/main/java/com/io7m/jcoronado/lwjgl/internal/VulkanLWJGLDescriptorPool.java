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

import com.io7m.jcoronado.api.VulkanDescriptorPoolType;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * LWJGL {@link VulkanDescriptorPoolType}.
 */

public final class VulkanLWJGLDescriptorPool
  extends VulkanLWJGLHandle implements VulkanDescriptorPoolType
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLDescriptorPool.class);

  private final long handle;
  private final VkDevice device;

  VulkanLWJGLDescriptorPool(
    final Ownership ownership,
    final VkDevice in_device,
    final long in_handle,
    final VulkanLWJGLHostAllocatorProxy in_host_allocator_proxy)
  {
    super(ownership, in_host_allocator_proxy);
    this.device = Objects.requireNonNull(in_device, "device");
    this.handle = in_handle;
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
    final var that = (VulkanLWJGLDescriptorPool) o;
    return this.handle == that.handle;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(Long.valueOf(this.handle));
  }

  @Override
  public String toString()
  {
    return new StringBuilder(32)
      .append("[VulkanLWJGLDescriptorPool 0x")
      .append(Long.toUnsignedString(this.handle, 16))
      .append("]")
      .toString();
  }

  @Override
  protected Logger logger()
  {
    return LOG;
  }

  @Override
  protected void closeActual()
  {
    if (LOG.isTraceEnabled()) {
      LOG.trace("Destroying descriptor pool: {}", this);
    }
    VK10.vkDestroyDescriptorPool(
      this.device, this.handle, this.hostAllocatorProxy().callbackBuffer());
  }

  /**
   * @return The raw handle
   */

  public long handle()
  {
    return this.handle;
  }
}
