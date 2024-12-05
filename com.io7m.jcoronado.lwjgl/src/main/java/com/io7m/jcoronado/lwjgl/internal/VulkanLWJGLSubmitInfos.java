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

import com.io7m.jcoronado.api.VulkanCommandBufferSubmitInfo;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanSemaphoreSubmitInfo;
import com.io7m.jcoronado.api.VulkanSubmitInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK13;
import org.lwjgl.vulkan.VkCommandBufferSubmitInfo;
import org.lwjgl.vulkan.VkSemaphoreSubmitInfo;
import org.lwjgl.vulkan.VkSubmitInfo2;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack submit infos.
 */

public final class VulkanLWJGLSubmitInfos
{
  private VulkanLWJGLSubmitInfos()
  {

  }

  /**
   * Pack a structure.
   *
   * @param stack  A stack
   * @param infos  A list of structures
   * @param buffer A buffer
   *
   * @throws VulkanException On errors
   */

  public static void packInfos(
    final MemoryStack stack,
    final List<VulkanSubmitInfo> infos,
    final VkSubmitInfo2.Buffer buffer)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "info");
    Objects.requireNonNull(buffer, "buffer");

    for (var index = 0; index < infos.size(); ++index) {
      final var info = infos.get(index);
      buffer.position(index)
        .pNext(0L)
        .sType(VK13.VK_STRUCTURE_TYPE_SUBMIT_INFO_2)
        .pWaitSemaphoreInfos(
          packSemaphoreInfos(stack, info.waitSemaphores()))
        .pCommandBufferInfos(
          packCommandBufferInfos(stack, info.commandBuffers()))
        .pSignalSemaphoreInfos(
          packSemaphoreInfos(stack, info.signalSemaphores()));
    }
    buffer.position(0);
  }

  private static VkCommandBufferSubmitInfo.Buffer packCommandBufferInfos(
    final MemoryStack stack,
    final List<VulkanCommandBufferSubmitInfo> infos)
    throws VulkanException
  {
    return VulkanLWJGLArrays.packOrNull(
      infos,
      (_, value, output) -> {
        VulkanLWJGLCommandBufferSubmitInfos.packInto(value, output);
      },
      VkCommandBufferSubmitInfo::calloc,
      stack
    );
  }

  private static VkSemaphoreSubmitInfo.Buffer packSemaphoreInfos(
    final MemoryStack stack,
    final List<VulkanSemaphoreSubmitInfo> infos)
    throws VulkanException
  {
    return VulkanLWJGLArrays.packOrNull(
      infos,
      (_, value, output) -> {
        VulkanLWJGLSemaphoreSubmitInfos.packInto(value, output);
      },
      VkSemaphoreSubmitInfo::calloc,
      stack
    );
  }
}
