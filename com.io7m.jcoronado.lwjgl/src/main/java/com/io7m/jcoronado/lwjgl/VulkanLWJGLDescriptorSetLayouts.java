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

import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutBinding;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDescriptorSetLayoutBinding;
import org.lwjgl.vulkan.VkDescriptorSetLayoutCreateInfo;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack descriptor set layouts.
 */

public final class VulkanLWJGLDescriptorSetLayouts
{
  private VulkanLWJGLDescriptorSetLayouts()
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

  public static VkDescriptorSetLayoutCreateInfo packDescriptorSetLayoutCreateInfo(
    final MemoryStack stack,
    final VulkanDescriptorSetLayoutCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    final var buffer = VkDescriptorSetLayoutCreateInfo.mallocStack(stack);
    packDescriptorSetLayoutCreateInfoInto(stack, info, buffer);
    return buffer;
  }

  private static VkDescriptorSetLayoutCreateInfo packDescriptorSetLayoutCreateInfoInto(
    final MemoryStack stack,
    final VulkanDescriptorSetLayoutCreateInfo info,
    final VkDescriptorSetLayoutCreateInfo buffer)
    throws VulkanException
  {
    final var packed_bindings = packBindings(stack, info.bindings());

    return buffer
      .sType(VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO)
      .pNext(0L)
      .pBindings(packed_bindings)
      .flags(VulkanEnumMaps.packValues(info.flags()));
  }

  private static VkDescriptorSetLayoutBinding.Buffer packBindings(
    final MemoryStack stack,
    final List<VulkanDescriptorSetLayoutBinding> bindings)
    throws VulkanException
  {
    final var count = bindings.size();
    if (count == 0) {
      return null;
    }

    final var vk_bindings = VkDescriptorSetLayoutBinding.mallocStack(count);
    for (var index = 0; index < count; ++index) {
      final var source = bindings.get(index);
      final var target = vk_bindings.get(index);
      VulkanLWJGLDescriptorSetLayoutBindings
        .packDescriptorSetLayoutBindingInto(stack, source, target);
    }

    return vk_bindings;
  }
}
