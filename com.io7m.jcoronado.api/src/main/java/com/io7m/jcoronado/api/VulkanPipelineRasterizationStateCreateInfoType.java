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

import java.util.EnumSet;
import java.util.Set;

/**
 * @see "VkPipelineRasterizationStateCreateInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkPipelineRasterizationStateCreateInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPipelineRasterizationStateCreateInfoType
{
  /**
   * @return State creation flags
   */

  Set<VulkanPipelineRasterizationStateCreateFlag> flags();

  /**
   * @return controls whether to clamp the fragment’s depth values instead of clipping primitives to
   * the z planes of the frustum.
   */

  @Value.Default
  default boolean depthClampEnable()
  {
    return false;
  }

  /**
   * @return controls whether primitives are discarded immediately before the rasterization stage.
   */

  @Value.Default
  default boolean rasterizerDiscardEnable()
  {
    return false;
  }

  /**
   * @return the triangle rendering mode.
   */

  @Value.Default
  default VulkanPolygonMode polygonMode()
  {
    return VulkanPolygonMode.VK_POLYGON_MODE_FILL;
  }

  /**
   * @return the triangle facing direction used for primitive culling.
   */

  @Value.Default
  default Set<VulkanCullModeFlag> cullMode()
  {
    return EnumSet.of(VulkanCullModeFlag.VK_CULL_MODE_BACK_BIT);
  }

  /**
   * @return the front-facing triangle orientation to be used for culling.
   */

  @Value.Default
  default VulkanFrontFace frontFace()
  {
    return VulkanFrontFace.VK_FRONT_FACE_COUNTER_CLOCKWISE;
  }

  /**
   * @return controls whether to bias fragment depth values.
   */

  @Value.Default
  default boolean depthBiasEnable()
  {
    return false;
  }

  /**
   * @return a scalar factor controlling the constant depth value added to each fragment.
   */

  @Value.Default
  default float depthBiasConstantFactor()
  {
    return 0.0f;
  }

  /**
   * @return the maximum (or minimum) depth bias of a fragment.
   */

  @Value.Default
  default float depthBiasClamp()
  {
    return 0.0f;
  }

  /**
   * @return a scalar factor applied to a fragment’s slope in depth bias calculations.
   */

  @Value.Default
  default float depthBiasSlopeFactor()
  {
    return 0.0f;
  }

  /**
   * @return the width of rasterized line segments.
   */

  @Value.Default
  default float lineWidth()
  {
    return 1.0f;
  }
}
