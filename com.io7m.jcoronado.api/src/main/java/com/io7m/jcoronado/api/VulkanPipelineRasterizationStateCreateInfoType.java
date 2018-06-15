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

import java.util.Set;

/**
 * @see "VkPipelineRasterizationStateCreateInfo"
 */

@ImmutablesStyleType
@Value.Immutable
public interface VulkanPipelineRasterizationStateCreateInfoType
{
  /**
   * @return State creation flags
   */

  @Value.Parameter
  Set<VulkanPipelineRasterizationStateCreateFlag> flags();

  /**
   * @return controls whether to clamp the fragment’s depth values instead of clipping primitives to
   * the z planes of the frustum.
   */

  @Value.Parameter
  boolean depthClampEnable();

  /**
   * @return controls whether primitives are discarded immediately before the rasterization stage.
   */

  @Value.Parameter
  boolean rasterizerDiscardEnable();

  /**
   * @return the triangle rendering mode.
   */

  @Value.Parameter
  VulkanPolygonMode polygonMode();

  /**
   * @return the triangle facing direction used for primitive culling.
   */

  @Value.Parameter
  Set<VulkanCullModeFlag> cullMode();

  /**
   * @return the front-facing triangle orientation to be used for culling.
   */

  @Value.Parameter
  VulkanFrontFace frontFace();

  /**
   * @return controls whether to bias fragment depth values.
   */

  @Value.Parameter
  boolean depthBiasEnable();

  /**
   * @return a scalar factor controlling the constant depth value added to each fragment.
   */

  @Value.Parameter
  float depthBiasConstantFactor();

  /**
   * @return the maximum (or minimum) depth bias of a fragment.
   */

  @Value.Parameter
  float depthBiasClamp();

  /**
   * @return a scalar factor applied to a fragment’s slope in depth bias calculations.
   */

  @Value.Parameter
  float depthBiasSlopeFactor();

  /**
   * @return the width of rasterized line segments.
   */

  @Value.Parameter
  float lineWidth();
}
