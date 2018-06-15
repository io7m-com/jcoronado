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

/**
 * Indicate which dynamic state is taken from dynamic state commands.
 *
 * @see "VkDynamicState"
 */

public enum VulkanDynamicState implements VulkanEnumIntegerType
{
  /**
   * VK_DYNAMIC_STATE_VIEWPORT specifies that the pViewports state in
   * VkPipelineViewportStateCreateInfo will be ignored and must be set dynamically with
   * vkCmdSetViewport before any draw commands. The number of viewports used by a pipeline is still
   * specified by the viewportCount member of VkPipelineViewportStateCreateInfo.
   */

  VK_DYNAMIC_STATE_VIEWPORT(0),

  /**
   * VK_DYNAMIC_STATE_SCISSOR specifies that the pScissors state in VkPipelineViewportStateCreateInfo
   * will be ignored and must be set dynamically with vkCmdSetScissor before any draw commands. The
   * number of scissor rectangles used by a pipeline is still specified by the scissorCount member
   * of VkPipelineViewportStateCreateInfo.
   */

  VK_DYNAMIC_STATE_SCISSOR(1),

  /**
   * VK_DYNAMIC_STATE_LINE_WIDTH specifies that the lineWidth state in
   * VkPipelineRasterizationStateCreateInfo will be ignored and must be set dynamically with
   * vkCmdSetLineWidth before any draw commands that generate line primitives for the rasterizer.
   */

  VK_DYNAMIC_STATE_LINE_WIDTH(2),

  /**
   * VK_DYNAMIC_STATE_DEPTH_BIAS specifies that the depthBiasConstantFactor, depthBiasClamp and
   * depthBiasSlopeFactor states in VkPipelineRasterizationStateCreateInfo will be ignored and must
   * be set dynamically with vkCmdSetDepthBias before any draws are performed with depthBiasEnable
   * in VkPipelineRasterizationStateCreateInfo set to {@code true}.
   */

  VK_DYNAMIC_STATE_DEPTH_BIAS(3),

  /**
   * VK_DYNAMIC_STATE_BLEND_CONSTANTS specifies that the blendConstants state in
   * VkPipelineColorBlendStateCreateInfo will be ignored and must be set dynamically with
   * vkCmdSetBlendConstants before any draws are performed with a pipeline state with
   * VkPipelineColorBlendAttachmentState member blendEnable set to {@code true} and any of the blend
   * functions using a constant blend color.
   */

  VK_DYNAMIC_STATE_BLEND_CONSTANTS(4),

  /**
   * VK_DYNAMIC_STATE_DEPTH_BOUNDS specifies that the minDepthBounds and maxDepthBounds states of
   * VkPipelineDepthStencilStateCreateInfo will be ignored and must be set dynamically with
   * vkCmdSetDepthBounds before any draws are performed with a pipeline state with
   * VkPipelineDepthStencilStateCreateInfo member depthBoundsTestEnable set to {@code true}.
   */

  VK_DYNAMIC_STATE_DEPTH_BOUNDS(5),

  /**
   * VK_DYNAMIC_STATE_STENCIL_COMPARE_MASK specifies that the compareMask state in
   * VkPipelineDepthStencilStateCreateInfo for both front and back will be ignored and must be set
   * dynamically with vkCmdSetStencilCompareMask before any draws are performed with a pipeline
   * state with VkPipelineDepthStencilStateCreateInfo member stencilTestEnable set to {@code true}.
   */

  VK_DYNAMIC_STATE_STENCIL_COMPARE_MASK(6),

  /**
   * VK_DYNAMIC_STATE_STENCIL_WRITE_MASK specifies that the writeMask state in
   * VkPipelineDepthStencilStateCreateInfo for both front and back will be ignored and must be set
   * dynamically with vkCmdSetStencilWriteMask before any draws are performed with a pipeline state
   * with VkPipelineDepthStencilStateCreateInfo member stencilTestEnable set to {@code true}.
   */

  VK_DYNAMIC_STATE_STENCIL_WRITE_MASK(7),

  /**
   * VK_DYNAMIC_STATE_STENCIL_REFERENCE specifies that the reference state in
   * VkPipelineDepthStencilStateCreateInfo for both front and back will be ignored and must be set
   * dynamically with vkCmdSetStencilReference before any draws are performed with a pipeline state
   * with VkPipelineDepthStencilStateCreateInfo member stencilTestEnable set to {@code true}.
   */

  VK_DYNAMIC_STATE_STENCIL_REFERENCE(8);

  private final int value;

  VulkanDynamicState(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
