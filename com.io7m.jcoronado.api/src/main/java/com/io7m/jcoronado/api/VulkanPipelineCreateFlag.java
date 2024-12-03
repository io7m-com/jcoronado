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
 * Bitmask controlling how a pipeline is created.
 *
 * @see "VkPipelineCreateFlagBits"
 */

@VulkanAPIEnumType(vulkanEnum = "VkPipelineCreateFlagBits")
public enum VulkanPipelineCreateFlag implements VulkanEnumBitmaskType
{
  /**
   * VK_PIPELINE_CREATE_DISABLE_OPTIMIZATION_BIT specifies that the created pipeline will not be
   * optimized. Using this flag may reduce the time taken to create the pipeline.
   */

  VK_PIPELINE_CREATE_DISABLE_OPTIMIZATION_BIT(0x00000001),

  /**
   * VK_PIPELINE_CREATE_ALLOW_DERIVATIVES_BIT specifies that the pipeline to be created is allowed
   * to be the parent of a pipeline that will be created in a subsequent call to
   * vkCreateGraphicsPipelines or vkCreateComputePipelines.
   */

  VK_PIPELINE_CREATE_ALLOW_DERIVATIVES_BIT(0x00000002),

  /**
   * VK_PIPELINE_CREATE_DERIVATIVE_BIT specifies that the pipeline to be created will be a child of
   * a previously created parent pipeline.
   */

  VK_PIPELINE_CREATE_DERIVATIVE_BIT(0x00000004),

  /**
   * VK_PIPELINE_CREATE_VIEW_INDEX_FROM_DEVICE_INDEX_BIT specifies that any shader input variables
   * decorated as ViewIndex will be assigned values as if they were decorated as DeviceIndex.
   */

  VK_PIPELINE_CREATE_VIEW_INDEX_FROM_DEVICE_INDEX_BIT(0x00000008),

  /**
   * VK_PIPELINE_CREATE_DISPATCH_BASE specifies that a compute pipeline can be used with
   * vkCmdDispatchBase with a non-zero base workgroup.
   */

  VK_PIPELINE_CREATE_DISPATCH_BASE(0x00000010);

  private final int value;

  VulkanPipelineCreateFlag(
    final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
