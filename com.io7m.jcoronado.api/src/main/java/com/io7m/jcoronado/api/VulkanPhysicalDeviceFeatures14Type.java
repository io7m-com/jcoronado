/*
 * Copyright © 2026 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

/**
 * The features supported by a physical Vulkan 1.4 device.
 *
 * @see "VkPhysicalDeviceVulkan14Features"
 */

@VulkanAPIStructType(vulkanStruct = "VkPhysicalDeviceVulkan14Features")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPhysicalDeviceFeatures14Type
{
  /**
   * Indicates whether the implementation supports the ability to query global
   * queue priorities.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean globalPriorityQuery()
  {
    return false;
  }

  /**
   * Indicates whether shader modules can declare the GroupNonUniformRotateKHR
   * capability.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean shaderSubgroupRotate()
  {
    return false;
  }

  /**
   * Indicates whether shader modules can declare the
   * GroupNonUniformClusteredRotateKHR capability.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean shaderSubgroupRotateClustered()
  {
    return false;
  }

  /**
   * Indicates whether shader modules can declare the FloatControls2 capability.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean shaderFloatControls2()
  {
    return false;
  }

  /**
   * Indicates whether shader modules can declare the Assume capability.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean shaderExpectAssume()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports the
   * VK_LINE_RASTERIZATION_MODE_RECTANGULAR line rasterization mode.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean rectangularLines()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports the
   * VK_LINE_RASTERIZATION_MODE_BRESENHAM line rasterization mode.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean bresenhamLines()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports smooth lines with a
   * functionally correct and deterministic rasterization coverage mask.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean smoothLines()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports stippled rectangular lines
   * with theVK_LINE_RASTERIZATION_MODE_RECTANGULAR line rasterization mode.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean stippledRectangularLines()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports stippled smooth lines.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean stippledSmoothLines()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports non-zero divisor values for
   * vertex attribute fetch with divisor.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean vertexAttributeInstanceRateDivisor()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports a divisor value of zero for
   * vertex attribute fetch with divisor.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean vertexAttributeInstanceRateZeroDivisor()
  {
    return false;
  }

  /**
   * Indicates whether the VK_INDEX_TYPE_UINT8 index type is supported.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean indexTypeUint8()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports the
   * VK_RENDERING_LOCAL_READ_LINEAR_READ_ATTACHMENT_FB_READ flag.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean dynamicRenderingLocalRead()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports the ability to query
   * maintenance5 features.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean maintenance5()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports the ability to query
   * maintenance6 features.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean maintenance6()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports pipelineRobustness.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean pipelineRobustness()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports host image copy.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean hostImageCopy()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports push descriptors.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean pushDescriptor()
  {
    return false;
  }
}
