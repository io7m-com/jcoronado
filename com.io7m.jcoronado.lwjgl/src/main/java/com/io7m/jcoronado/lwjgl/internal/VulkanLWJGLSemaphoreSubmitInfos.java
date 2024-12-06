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

import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanSemaphoreBinaryType;
import com.io7m.jcoronado.api.VulkanSemaphoreSubmitInfo;
import com.io7m.jcoronado.api.VulkanSemaphoreTimelineType;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK13;
import org.lwjgl.vulkan.VkSemaphoreSubmitInfo;

import java.util.Objects;

import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLClassChecks.checkInstanceOf;

/**
 * Functions to pack semaphore submit info.
 */

public final class VulkanLWJGLSemaphoreSubmitInfos
{
  private VulkanLWJGLSemaphoreSubmitInfos()
  {

  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param info  A structure
   *
   * @return A packed structure
   *
   * @throws VulkanIncompatibleClassException On incompatible classes
   */

  public static VkSemaphoreSubmitInfo pack(
    final MemoryStack stack,
    final VulkanSemaphoreSubmitInfo info)
    throws VulkanIncompatibleClassException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(info, VkSemaphoreSubmitInfo.calloc(stack));
  }

  /**
   * Pack a structure.
   *
   * @param output The output
   * @param info   A structure
   *
   * @return output
   *
   * @throws VulkanIncompatibleClassException On incompatible classes
   */

  public static VkSemaphoreSubmitInfo packInto(
    final VulkanSemaphoreSubmitInfo info,
    final VkSemaphoreSubmitInfo output)
    throws VulkanIncompatibleClassException
  {
    final var semaphoreHandle =
      switch (info.semaphore()) {
        case final VulkanSemaphoreBinaryType b -> {
          yield checkInstanceOf(b, VulkanLWJGLSemaphoreBinary.class)
            .handle();
        }
        case final VulkanSemaphoreTimelineType t -> {
          yield checkInstanceOf(t, VulkanLWJGLSemaphoreTimeline.class)
            .handle();
        }
      };

    return output.sType(VK13.VK_STRUCTURE_TYPE_SEMAPHORE_SUBMIT_INFO)
      .pNext(0L)
      .semaphore(semaphoreHandle)
      .value(info.value())
      .deviceIndex((int) info.deviceIndex())
      .stageMask(VulkanEnumMaps.packValuesLong(info.stageMask()));
  }
}
