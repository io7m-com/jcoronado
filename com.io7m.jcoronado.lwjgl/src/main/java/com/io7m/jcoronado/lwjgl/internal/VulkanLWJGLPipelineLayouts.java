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

import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutType;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanPipelineLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanPushConstantRange;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineLayoutCreateInfo;
import org.lwjgl.vulkan.VkPushConstantRange;

import java.nio.LongBuffer;
import java.util.List;
import java.util.Objects;

/**
 * Functions to pack pipeline layouts.
 */

public final class VulkanLWJGLPipelineLayouts
{
  private VulkanLWJGLPipelineLayouts()
  {

  }

  private static VkPushConstantRange.Buffer packPushConstantRanges(
    final MemoryStack stack,
    final List<VulkanPushConstantRange> ranges)
  {
    final var buffer = VkPushConstantRange.calloc(ranges.size(), stack);
    for (var index = 0; index < ranges.size(); ++index) {
      final var source = ranges.get(index);
      final var target = VkPushConstantRange.create(buffer.address(index));
      packPushConstantRangeInto(source, target);
    }
    return buffer;
  }

  private static VkPushConstantRange packPushConstantRangeInto(
    final VulkanPushConstantRange source,
    final VkPushConstantRange target)
  {
    return target
      .offset(source.offset())
      .size(source.size())
      .stageFlags(VulkanEnumMaps.packValues(source.stageFlags()));
  }

  /**
   * Pack a creation info structure.
   *
   * @param stack A stack
   * @param info  An info structure
   *
   * @return A packed structure
   *
   * @throws VulkanException On errors
   */

  public static VkPipelineLayoutCreateInfo packPipelineLayoutCreateInfo(
    final MemoryStack stack,
    final VulkanPipelineLayoutCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    final var buffer = VkPipelineLayoutCreateInfo.calloc(stack);
    packPipelineLayoutCreateInfoInto(stack, info, buffer);
    return buffer;
  }

  private static VkPipelineLayoutCreateInfo packPipelineLayoutCreateInfoInto(
    final MemoryStack stack,
    final VulkanPipelineLayoutCreateInfo info,
    final VkPipelineLayoutCreateInfo buffer)
    throws VulkanException
  {
    return buffer
      .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO)
      .pNext(0L)
      .pPushConstantRanges(packPushConstantRanges(
        stack,
        info.pushConstantRanges()))
      .pSetLayouts(packSetLayouts(stack, info.setLayouts()))
      .flags(VulkanEnumMaps.packValues(info.flags()));
  }

  private static LongBuffer packSetLayouts(
    final MemoryStack stack,
    final List<VulkanDescriptorSetLayoutType> layouts)
    throws VulkanException
  {
    return VulkanLWJGLScalarArrays.packLongsOrNull(
      stack,
      layouts,
      layout -> VulkanLWJGLClassChecks.checkInstanceOf(
        layout,
        VulkanLWJGLDescriptorSetLayout.class).handle());
  }
}
