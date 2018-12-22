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

import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanPipelineVertexInputStateCreateInfo;
import com.io7m.jcoronado.api.VulkanVertexInputAttributeDescription;
import com.io7m.jcoronado.api.VulkanVertexInputBindingDescription;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineVertexInputStateCreateInfo;
import org.lwjgl.vulkan.VkVertexInputAttributeDescription;
import org.lwjgl.vulkan.VkVertexInputBindingDescription;

import java.util.Objects;

/**
 * Functions to pack pipeline creation info.
 */

public final class VulkanLWJGLPipelineVertexInputStateCreateInfos
{
  private VulkanLWJGLPipelineVertexInputStateCreateInfos()
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

  public static VkPipelineVertexInputStateCreateInfo pack(
    final MemoryStack stack,
    final VulkanPipelineVertexInputStateCreateInfo info)
  {
    Objects.requireNonNull(info, "info");

    final var target =
      VkPipelineVertexInputStateCreateInfo.mallocStack(stack);

    return target.sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO)
      .pNext(0L)
      .flags(VulkanEnumMaps.packValues(info.flags()))
      .pVertexAttributeDescriptions(packVertexInputAttributeDescriptions(stack, info))
      .pVertexBindingDescriptions(packVertexInputBindingDescriptions(stack, info));
  }

  private static VkVertexInputBindingDescription.Buffer packVertexInputBindingDescriptions(
    final MemoryStack stack,
    final VulkanPipelineVertexInputStateCreateInfo info)
  {
    final var descs = info.vertexBindingDescriptions();
    final var descs_count = descs.size();
    if (descs_count == 0) {
      return null;
    }

    final var buffer =
      VkVertexInputBindingDescription.mallocStack(descs_count, stack);
    for (var index = 0; index < descs_count; ++index) {
      final var source = descs.get(index);
      final var target =
        VkVertexInputBindingDescription.create(buffer.address(index));
      packVertexInputBindingDescriptionInto(source, target);
    }
    return buffer;
  }

  private static VkVertexInputBindingDescription packVertexInputBindingDescriptionInto(
    final VulkanVertexInputBindingDescription source,
    final VkVertexInputBindingDescription target)
  {
    return target.binding(source.binding())
      .inputRate(source.inputRate().value())
      .stride(source.stride());
  }

  private static VkVertexInputAttributeDescription.Buffer packVertexInputAttributeDescriptions(
    final MemoryStack stack,
    final VulkanPipelineVertexInputStateCreateInfo info)
  {
    final var descs = info.vertexAttributeDescriptions();
    final var descs_count = descs.size();
    if (descs_count == 0) {
      return null;
    }

    final var buffer =
      VkVertexInputAttributeDescription.mallocStack(descs_count, stack);
    for (var index = 0; index < descs_count; ++index) {
      final var source = descs.get(index);
      final var target =
        VkVertexInputAttributeDescription.create(buffer.address(index));
      packVertexInputAttributeDescriptionInto(source, target);
    }
    return buffer;
  }

  private static VkVertexInputAttributeDescription packVertexInputAttributeDescriptionInto(
    final VulkanVertexInputAttributeDescription source,
    final VkVertexInputAttributeDescription target)
  {
    return target.binding(source.binding())
      .format(source.format().value())
      .location(source.location())
      .offset(source.offset());
  }
}
