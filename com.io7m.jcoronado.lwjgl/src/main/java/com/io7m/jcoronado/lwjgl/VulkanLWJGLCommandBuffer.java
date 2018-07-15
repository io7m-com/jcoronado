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

import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanCommandBufferBeginInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkCommandBufferBeginInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * LWJGL {@link VulkanCommandBufferType}.
 */

public final class VulkanLWJGLCommandBuffer
  extends VulkanLWJGLHandle implements VulkanCommandBufferType
{
  private static final Logger LOG = LoggerFactory.getLogger(VulkanLWJGLCommandBuffer.class);

  private final VkCommandBuffer handle;
  private final MemoryStack stack_initial;

  VulkanLWJGLCommandBuffer(
    final Ownership ownership,
    final VkCommandBuffer in_handle)
  {
    super(ownership);
    this.handle = in_handle;

    this.stack_initial =
      MemoryStack.create();
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
    final VulkanLWJGLCommandBuffer that = (VulkanLWJGLCommandBuffer) o;
    return Objects.equals(this.handle, that.handle);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(Long.valueOf(this.handle.address()));
  }

  @Override
  public String toString()
  {
    return new StringBuilder(32)
      .append("[VulkanLWJGLCommandBuffer 0x")
      .append(Long.toUnsignedString(this.handle.address(), 16))
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

  }

  @Override
  public void beginCommandBuffer(
    final VulkanCommandBufferBeginInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkCommandBufferBeginInfo packed =
        VulkanLWJGLCommandBufferBeginInfos.pack(stack, info);

      VulkanChecks.checkReturnCode(
        VK10.vkBeginCommandBuffer(this.handle, packed),
        "vkBeginCommandBuffer");
    }
  }
}
