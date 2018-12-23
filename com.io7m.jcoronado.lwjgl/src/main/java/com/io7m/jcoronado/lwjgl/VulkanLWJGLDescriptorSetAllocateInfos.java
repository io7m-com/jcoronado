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

import com.io7m.jcoronado.api.VulkanDescriptorPoolType;
import com.io7m.jcoronado.api.VulkanDescriptorSetAllocateInfo;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDescriptorSetAllocateInfo;

import java.nio.LongBuffer;
import java.util.List;
import java.util.Objects;

/**
 * Functions to pack descriptor set allocation info.
 */

public final class VulkanLWJGLDescriptorSetAllocateInfos
{
  private VulkanLWJGLDescriptorSetAllocateInfos()
  {

  }

  /**
   * Pack a value into a Vulkan structure.
   *
   * @param stack The stack used for allocations
   * @param info  The input value
   *
   * @return A packed Vulkan structure
   *
   * @throws VulkanException On errors
   */

  public static VkDescriptorSetAllocateInfo packDescriptorSetAllocateInfo(
    final MemoryStack stack,
    final VulkanDescriptorSetAllocateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    final var buffer = VkDescriptorSetAllocateInfo.mallocStack(stack);
    packDescriptorSetAllocateInfoInto(stack, info, buffer);
    return buffer;
  }

  /**
   * Pack a value into a Vulkan structure.
   *
   * @param stack  The stack used for allocations
   * @param info   The input value
   * @param buffer The output value
   *
   * @return A packed Vulkan structure
   *
   * @throws VulkanException On errors
   */

  public static VkDescriptorSetAllocateInfo packDescriptorSetAllocateInfoInto(
    final MemoryStack stack,
    final VulkanDescriptorSetAllocateInfo info,
    final VkDescriptorSetAllocateInfo buffer)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");
    Objects.requireNonNull(buffer, "buffer");

    return buffer
      .sType(VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO)
      .pNext(0L)
      .descriptorPool(packDescriptorPool(info.descriptorPool()))
      .pSetLayouts(packLayouts(stack, info.setLayouts()));
  }

  private static LongBuffer packLayouts(
    final MemoryStack stack,
    final List<VulkanDescriptorSetLayoutType> layouts)
    throws VulkanException
  {
    return VulkanLWJGLIntegerArrays.packLongsOrNull(
      stack,
      layouts,
      layout ->
        VulkanLWJGLClassChecks.check(layout, VulkanLWJGLDescriptorSetLayout.class)
          .handle());
  }

  private static long packDescriptorPool(final VulkanDescriptorPoolType pool)
    throws VulkanIncompatibleClassException
  {
    return VulkanLWJGLClassChecks.check(pool, VulkanLWJGLDescriptorPool.class).handle();
  }
}
