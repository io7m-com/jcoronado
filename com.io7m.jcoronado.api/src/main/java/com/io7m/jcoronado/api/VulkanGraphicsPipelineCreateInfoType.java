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

package com.io7m.jcoronado.api;

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;

/**
 * Structure specifying parameters of a newly created graphics pipeline.
 *
 * @see "VkGraphicsPipelineCreateInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkGraphicsPipelineCreateInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanGraphicsPipelineCreateInfoType
{
  /**
   * @return Flags specifying how the pipeline will be generated.
   */

  @Value.Parameter
  Set<VulkanPipelineCreateFlag> flags();

  /**
   * @return The shader stages to be included in the graphics pipeline.
   */

  @Value.Parameter
  List<VulkanPipelineShaderStageCreateInfo> stages();

  /**
   * @return State for the vertex shading stage
   */

  @Value.Parameter
  VulkanPipelineVertexInputStateCreateInfo vertexInputState();

  /**
   * @return State for the input assembly stage
   */

  @Value.Parameter
  VulkanPipelineInputAssemblyStateCreateInfo inputAssemblyState();

  /**
   * @return State for the tesselation stage
   */

  @Value.Parameter
  Optional<VulkanPipelineTessellationStateCreateInfo> tessellationState();

  /**
   * @return State for the viewport
   */

  @Value.Parameter
  Optional<VulkanPipelineViewportStateCreateInfo> viewportState();

  /**
   * @return State for the rasterization stage
   */

  @Value.Parameter
  VulkanPipelineRasterizationStateCreateInfo rasterizationState();

  /**
   * @return State for multisampling
   */

  @Value.Parameter
  Optional<VulkanPipelineMultisampleStateCreateInfo> multisampleState();

  /**
   * @return State for the depth/stencil buffer
   */

  @Value.Parameter
  Optional<VulkanPipelineDepthStencilStateCreateInfo> depthStencilState();

  /**
   * @return State for the color blending stage
   */

  @Value.Parameter
  Optional<VulkanPipelineColorBlendStateCreateInfo> colorBlendState();

  /**
   * @return Dynamic state
   */

  @Value.Parameter
  Optional<VulkanPipelineDynamicStateCreateInfo> dynamicState();

  /**
   * @return The description of binding locations used by both the pipeline and descriptor sets used
   * with the pipeline.
   */

  @Value.Parameter
  VulkanPipelineLayoutType layout();

  /**
   * @return A handle to a render pass object describing the environment in which the pipeline will
   * be used; the pipeline must only be used with an instance of any render pass compatible with the
   * one provided.
   */

  @Value.Parameter
  VulkanRenderPassType renderPass();

  /**
   * @return The index of the subpass in the render pass where this pipeline will be used.
   */

  @Value.Parameter
  int subpass();

  /**
   * @return A pipeline to derive from.
   */

  @Value.Parameter
  Optional<VulkanPipelineType> basePipeline();

  /**
   * @return An index into the createInfos parameter to use as a pipeline to derive from.
   */

  @Value.Parameter
  OptionalInt basePipelineIndex();
}
