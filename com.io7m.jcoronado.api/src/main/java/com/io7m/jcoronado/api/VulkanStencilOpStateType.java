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

/**
 * @see "VkStencilOpState"
 */

@VulkanAPIStructType(vulkanStruct = "VkStencilOpState")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanStencilOpStateType
{
  /**
   * @return The action performed on samples that fail the stencil test.
   */

  @Value.Default
  default VulkanStencilOp failOp()
  {
    return VulkanStencilOp.VK_STENCIL_OP_KEEP;
  }

  /**
   * @return The action performed on samples that pass both the depth and stencil tests.
   */

  @Value.Default
  default VulkanStencilOp passOp()
  {
    return VulkanStencilOp.VK_STENCIL_OP_KEEP;
  }

  /**
   * @return The action performed on samples that pass the stencil test and fail the depth test.
   */

  @Value.Default
  default VulkanStencilOp depthFailOp()
  {
    return VulkanStencilOp.VK_STENCIL_OP_KEEP;
  }

  /**
   * @return The comparison operator used in the stencil test.
   */

  @Value.Default
  default VulkanCompareOp compareOp()
  {
    return VulkanCompareOp.VK_COMPARE_OP_NEVER;
  }

  /**
   * @return The bits of the unsigned integer stencil values participating in the stencil test.
   */

  @Value.Default
  default int compareMask()
  {
    return 0;
  }

  /**
   * @return The bits of the unsigned integer stencil values updated by the stencil test in the
   * stencil framebuffer attachment.
   */

  @Value.Default
  default int writeMask()
  {
    return 0;
  }

  /**
   * @return An integer reference value that is used in the unsigned stencil comparison.
   */

  @Value.Default
  default int reference()
  {
    return 0;
  }
}
