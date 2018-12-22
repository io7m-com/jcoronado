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
import com.io7m.jcoronado.api.VulkanGraphicsPipelineCreateInfo;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkGraphicsPipelineCreateInfo;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack pipeline creation info.
 */

public final class VulkanLWJGLGraphicsPipelineCreateInfos
{
  private VulkanLWJGLGraphicsPipelineCreateInfos()
  {

  }

  /**
   * Pack a list of structures.
   *
   * @param stack          A stack
   * @param pipeline_infos A list of structure
   *
   * @return A packed list of structures
   *
   * @throws VulkanIncompatibleClassException If one or more incompatible classes were specified
   */

  public static VkGraphicsPipelineCreateInfo.Buffer pack(
    final MemoryStack stack,
    final List<VulkanGraphicsPipelineCreateInfo> pipeline_infos)
    throws VulkanIncompatibleClassException
  {
    Objects.requireNonNull(pipeline_infos, "pipeline_infos");

    final var buffer =
      VkGraphicsPipelineCreateInfo.mallocStack(pipeline_infos.size(), stack);

    for (var index = 0; index < pipeline_infos.size(); ++index) {
      final var source =
        pipeline_infos.get(index);
      final var target =
        VkGraphicsPipelineCreateInfo.create(buffer.address(index));
      packInto(stack, source, target);
    }

    return buffer;
  }

  private static void packInto(
    final MemoryStack stack,
    final VulkanGraphicsPipelineCreateInfo source,
    final VkGraphicsPipelineCreateInfo target)
    throws VulkanIncompatibleClassException
  {
    target
      .pNext(0L)
      .sType(VK10.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO)
      .flags(VulkanEnumMaps.packValues(source.flags()));

    packIntoBasePipelineIndex(source, target);
    packIntoBasePipelineHandle(source, target);
    packIntoLayout(source, target);
    packIntoSubpass(source, target);
    packIntoRenderPass(source, target);

    target.pColorBlendState(
      VulkanLWJGLPipelineColorBlendStateCreateInfos.packOptional(
        stack, source.colorBlendState()));
    target.pDepthStencilState(
      VulkanLWJGLPipelineDepthStencilStateCreateInfos.packOptional(
        stack, source.depthStencilState()));
    target.pDynamicState(
      VulkanLWJGLPipelineDynamicStateCreateInfos.packOptional(
        stack, source.dynamicState()));
    target.pInputAssemblyState(
      VulkanLWJGLPipelineInputAssemblyStateCreateInfos.pack(
        stack, source.inputAssemblyState()));
    target.pMultisampleState(
      VulkanLWJGLPipelineMultisampleStateCreateInfos.packOptional(
        stack, source.multisampleState()));
    target.pRasterizationState(
      VulkanLWJGLPipelineRasterizationStateCreateInfos.pack(
        stack, source.rasterizationState()));
    target.pTessellationState(
      VulkanLWJGLPipelineTessellationStateCreateInfos.packOptional(
        stack, source.tessellationState()));
    target.pVertexInputState(
      VulkanLWJGLPipelineVertexInputStateCreateInfos.pack(
        stack, source.vertexInputState()));
    target.pViewportState(
      VulkanLWJGLPipelineViewportStateCreateInfos.packOptional(
        stack, source.viewportState()));
    target.pStages(
      VulkanLWJGLPipelineShaderStageCreateInfos.packAll(stack, source.stages()));
  }

  private static void packIntoSubpass(
    final VulkanGraphicsPipelineCreateInfo source,
    final VkGraphicsPipelineCreateInfo target)
  {
    target.subpass(source.subpass());
  }

  private static void packIntoRenderPass(
    final VulkanGraphicsPipelineCreateInfo source,
    final VkGraphicsPipelineCreateInfo target)
    throws VulkanIncompatibleClassException
  {
    final var rpl =
      VulkanLWJGLClassChecks.check(source.renderPass(), VulkanLWJGLRenderPass.class);
    target.renderPass(rpl.handle());
  }

  private static void packIntoLayout(
    final VulkanGraphicsPipelineCreateInfo source,
    final VkGraphicsPipelineCreateInfo target)
    throws VulkanIncompatibleClassException
  {
    final var layout_l =
      VulkanLWJGLClassChecks.check(source.layout(), VulkanLWJGLPipelineLayout.class);
    target.layout(layout_l.handle());
  }

  private static void packIntoBasePipelineHandle(
    final VulkanGraphicsPipelineCreateInfo source,
    final VkGraphicsPipelineCreateInfo target)
    throws VulkanIncompatibleClassException
  {
    final var bp = source.basePipeline();
    if (bp.isPresent()) {
      final var bppl =
        VulkanLWJGLClassChecks.check(bp.get(), VulkanLWJGLPipeline.class);
      target.basePipelineHandle(bppl.handle());
    }
  }

  private static void packIntoBasePipelineIndex(
    final VulkanGraphicsPipelineCreateInfo source,
    final VkGraphicsPipelineCreateInfo target)
  {
    final var bpi = source.basePipelineIndex();
    if (bpi.isPresent()) {
      target.basePipelineIndex(bpi.getAsInt());
    }
  }
}
