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
  extends VulkanLWJGLHandle
  implements VulkanDescriptorPoolType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLDescriptorPool.class);

  private final VkDevice device;

  VulkanLWJGLDescriptorPool(
    final Ownership ownership,
    final VkDevice inDevice,
    final long handle,
    final VulkanLWJGLHostAllocatorProxy inHostAllocatorProxy)
  {
    super(ownership, inHostAllocatorProxy, handle);
    this.device = Objects.requireNonNull(inDevice, "device");
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
      this.device,
      this.handle(),
      this.hostAllocatorProxy().callbackBuffer()
    );
  }
}
