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

import com.io7m.jcoronado.api.VulkanCommandBufferCreateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo;

import java.util.Objects;

/**
 * Functions to pack command buffer creation info.
 */

public final class VulkanLWJGLCommandBufferCreateInfos
{
  private VulkanLWJGLCommandBufferCreateInfos()
  {

  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param info  A structure
   * @param pool  The command pool
   *
   * @return A packed structure
   */

  public static VkCommandBufferAllocateInfo pack(
    final MemoryStack stack,
    final VulkanCommandBufferCreateInfo info,
    final VulkanLWJGLCommandPool pool)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");
    Objects.requireNonNull(pool, "pool");

    return VkCommandBufferAllocateInfo.mallocStack(stack)
      .sType(VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO)
      .pNext(0L)
      .level(info.level().value())
      .commandBufferCount(info.count())
      .commandPool(pool.handle());
  }
}
