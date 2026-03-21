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
 * Structure specifying parameters of a newly created pipeline depth stencil state.
 *
 * @see "VkPipelineDepthStencilStateCreateInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkPipelineDepthStencilStateCreateInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPipelineDepthStencilStateCreateInfoType
{
  /**
   * @return Creation flags
   */

  Set<VulkanPipelineDepthStencilStateCreateFlag> flags();

  /**
   * @return {@code true} if depth testing is enabled.
   */

  @Value.Default
  default boolean depthTestEnable()
  {
    return false;
  }

  /**
   * @return {@code true} if depth writing is enabled.
   */

  @Value.Default
  default boolean depthWriteEnable()
  {
    return false;
  }

  /**
   * @return The comparison operator used in the depth test.
   */

  @Value.Default
  default VulkanCompareOp depthCompareOp()
  {
    return VulkanCompareOp.VK_COMPARE_OP_NEVER;
  }

  /**
   * @return {@code true} if depth bounds testing is enabled.
   */

  @Value.Default
  default boolean depthBoundsTestEnable()
  {
    return false;
  }

  /**
   * @return {@code true} if stencil testing is enabled.
   */

  @Value.Default
  default boolean stencilTestEnable()
  {
    return false;
  }

  /**
   * @return The stencil op state for front faces.
   */

  @Value.Default
  default VulkanStencilOpState front()
  {
    return VulkanStencilOpState.builder().build();
  }

  /**
   * @return The stencil op state for back faces.
   */

  @Value.Default
  default VulkanStencilOpState back()
  {
    return VulkanStencilOpState.builder().build();
  }

  /**
   * @return The minimum depth for bounds testing
   */

  @Value.Default
  default float minDepthBounds()
  {
    return 0.0f;
  }

  /**
   * @return The maximum depth for bounds testing
   */

  @Value.Default
  default float maxDepthBounds()
  {
    return 0.0f;
  }
}
