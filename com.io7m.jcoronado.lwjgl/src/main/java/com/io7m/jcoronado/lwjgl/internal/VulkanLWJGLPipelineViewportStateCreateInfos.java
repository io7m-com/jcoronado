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
import com.io7m.jcoronado.api.VulkanPipelineViewportStateCreateInfo;
import com.io7m.jcoronado.api.VulkanRectangle2D;
import com.io7m.jcoronado.api.VulkanViewport;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkExtent2D;
import org.lwjgl.vulkan.VkOffset2D;
import org.lwjgl.vulkan.VkPipelineViewportStateCreateInfo;
import org.lwjgl.vulkan.VkRect2D;
import org.lwjgl.vulkan.VkViewport;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Functions to pack pipeline creation info.
 */

public final class VulkanLWJGLPipelineViewportStateCreateInfos
{
  private VulkanLWJGLPipelineViewportStateCreateInfos()
  {

  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param info  A structure
   *
   * @return A packed structure
   */

  public static VkPipelineViewportStateCreateInfo pack(
    final MemoryStack stack,
    final VulkanPipelineViewportStateCreateInfo info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    final var target =
      VkPipelineViewportStateCreateInfo.calloc(stack);

    return target
      .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO)
      .pNext(0L)
      .flags(VulkanEnumMaps.packValues(info.flags()))
      .viewportCount(info.viewports().size())
      .pViewports(packViewports(stack, info.viewports()))
      .scissorCount(info.scissors().size())
      .pScissors(packScissors(stack, info.scissors()));
  }

  private static VkRect2D.Buffer packScissors(
    final MemoryStack stack,
    final List<VulkanRectangle2D> scissors)
  {
    final var buffer = VkRect2D.calloc(scissors.size(), stack);
    for (var index = 0; index < scissors.size(); ++index) {
      final var source = scissors.get(index);
      final var target = VkRect2D.create(buffer.address(index));
      final var extent = source.extent();
      final var offset = source.offset();
      target.extent(VkExtent2D.calloc(stack).set(
        extent.width(),
        extent.height()));
      target.offset(VkOffset2D.calloc(stack).set(offset.x(), offset.y()));
    }
    return buffer;
  }

  private static VkViewport.Buffer packViewports(
    final MemoryStack stack,
    final List<VulkanViewport> viewports)
  {
    final var buffer = VkViewport.calloc(viewports.size(), stack);
    for (var index = 0; index < viewports.size(); ++index) {
      final var source = viewports.get(index);
      final var target = VkViewport.create(buffer.address(index));
      target.set(
        source.x(),
        source.y(),
        source.width(),
        source.height(),
        source.minDepth(),
        source.maxDepth());
    }
    return buffer;
  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param info  A structure
   *
   * @return A packed structure (or null)
   */

  public static VkPipelineViewportStateCreateInfo packOptional(
    final MemoryStack stack,
    final Optional<VulkanPipelineViewportStateCreateInfo> info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return info.map(iinfo -> pack(stack, iinfo)).orElse(null);
  }
}
