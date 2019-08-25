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

import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanPipelineStageFlag;
import com.io7m.jcoronado.api.VulkanSemaphoreType;
import com.io7m.jcoronado.api.VulkanSubmitInfo;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkSubmitInfo;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
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
   * @throws VulkanIncompatibleClassException On incompatible classes
   * @throws VulkanException                  On errors
   */

  public static void packInfos(
    final MemoryStack stack,
    final List<VulkanSubmitInfo> infos,
    final VkSubmitInfo.Buffer buffer)
    throws VulkanException, VulkanIncompatibleClassException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "info");
    Objects.requireNonNull(buffer, "buffer");

    for (var index = 0; index < infos.size(); ++index) {
      final var info = infos.get(index);
      buffer.position(index)
        .pNext(0L)
        .sType(VK10.VK_STRUCTURE_TYPE_SUBMIT_INFO)
        .pWaitDstStageMask(packWaitStages(stack, info.waitStageMasks()))
        .pCommandBuffers(packCommandBuffers(stack, info.commandBuffers()))
        .pSignalSemaphores(packSemaphores(stack, info.signalSemaphores()))
        .pWaitSemaphores(packSemaphores(stack, info.waitSemaphores()))
        .waitSemaphoreCount(info.waitSemaphores().size());
    }
    buffer.position(0);
  }

  private static IntBuffer packWaitStages(
    final MemoryStack stack,
    final List<VulkanPipelineStageFlag> stages)
    throws VulkanException
  {
    return VulkanLWJGLIntegerArrays.packIntsOrNull(stack, stages, VulkanPipelineStageFlag::value);
  }

  private static LongBuffer packSemaphores(
    final MemoryStack stack,
    final List<VulkanSemaphoreType> semaphores)
    throws VulkanException
  {
    return VulkanLWJGLIntegerArrays.packLongsOrNull(
      stack,
      semaphores,
      semaphore -> VulkanLWJGLClassChecks.checkInstanceOf(
        semaphore,
        VulkanLWJGLSemaphore.class).handle());
  }

  private static PointerBuffer packCommandBuffers(
    final MemoryStack stack,
    final List<VulkanCommandBufferType> command_buffers)
    throws VulkanException
  {
    return VulkanLWJGLIntegerArrays.packPointersOrNull(
      stack,
      command_buffers,
      command_buffer ->
        VulkanLWJGLClassChecks.checkInstanceOf(command_buffer, VulkanLWJGLCommandBuffer.class)
          .handle()
          .address());
  }
}
