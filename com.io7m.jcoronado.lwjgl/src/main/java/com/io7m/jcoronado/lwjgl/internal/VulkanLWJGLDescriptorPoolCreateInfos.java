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

import com.io7m.jcoronado.api.VulkanDescriptorPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDescriptorPoolCreateInfo;

import java.util.Objects;

/**
 * Functions to pack descriptor pool creation info.
 */

public final class VulkanLWJGLDescriptorPoolCreateInfos
{
  private VulkanLWJGLDescriptorPoolCreateInfos()
  {

  }

  /**
   * Pack a value into a Vulkan structure.
   *
   * @param stack The stack used for allocations
   * @param info  The input value
   *
   * @return A packed Vulkan structure
   */

  public static VkDescriptorPoolCreateInfo packDescriptorPoolCreateInfo(
    final MemoryStack stack,
    final VulkanDescriptorPoolCreateInfo info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packDescriptorPoolCreateInfoInto(
      stack, info, VkDescriptorPoolCreateInfo.calloc(stack));
  }

  /**
   * Pack a value into a Vulkan structure.
   *
   * @param stack  The stack used for allocations
   * @param info   The input value
   * @param buffer The output structure
   *
   * @return A packed Vulkan structure
   */

  public static VkDescriptorPoolCreateInfo packDescriptorPoolCreateInfoInto(
    final MemoryStack stack,
    final VulkanDescriptorPoolCreateInfo info,
    final VkDescriptorPoolCreateInfo buffer)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");
    Objects.requireNonNull(buffer, "buffer");

    buffer.sType(VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO);
    buffer.pNext(0L);
    buffer.flags(VulkanEnumMaps.packValues(info.flags()));
    buffer.maxSets(info.maxSets());
    buffer.pPoolSizes(
      VulkanLWJGLDescriptorPoolSizes.packDescriptorPoolSizes(
        stack,
        info.poolSizes()));
    return buffer;
  }
}
