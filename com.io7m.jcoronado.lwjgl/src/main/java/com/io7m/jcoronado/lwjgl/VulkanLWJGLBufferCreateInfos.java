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

import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkBufferCreateInfo;

import java.nio.IntBuffer;
import java.util.List;
import java.util.Objects;

/**
 * Functions to pack buffer creation info.
 */

public final class VulkanLWJGLBufferCreateInfos
{
  private VulkanLWJGLBufferCreateInfos()
  {

  }

  /**
   * Pack info.
   *
   * @param stack A stack
   * @param info  The info
   *
   * @return A packed info
   */

  public static VkBufferCreateInfo packInfo(
    final MemoryStack stack,
    final VulkanBufferCreateInfo info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return VkBufferCreateInfo.mallocStack(stack)
      .sType(VK10.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO)
      .pNext(0L)
      .flags(VulkanEnumMaps.packValues(info.flags()))
      .sharingMode(info.sharingMode().value())
      .size(info.size())
      .usage(VulkanEnumMaps.packValues(info.usageFlags()))
      .pQueueFamilyIndices(packQueueFamilies(stack, info.queueFamilyIndices()));
  }

  private static IntBuffer packQueueFamilies(
    final MemoryStack stack,
    final List<Integer> integers)
  {
    final int size = integers.size();
    if (size > 0) {
      final IntBuffer buffer = stack.mallocInt(size);
      for (int index = 0; index < size; ++index) {
        buffer.put(index, integers.get(index).intValue());
      }
    }
    return null;
  }
}
