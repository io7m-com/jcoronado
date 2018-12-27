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
 * Structure specifying a pipeline color blend attachment state.
 *
 * @see "VkPipelineColorBlendAttachmentState"
 */

@VulkanAPIStructType(vulkanStruct = "VkPipelineColorBlendAttachmentState")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPipelineColorBlendAttachmentStateType
{
  /**
   * @return {@code true} if blending is enabled for the corresponding color attachment. If blending
   * is not enabled, the source fragment’s color for that attachment is passed through unmodified.
   */

  @Value.Parameter
  boolean enable();

  /**
   * @return the blend factor used to determine the source factors  (Sr,Sg,Sb).
   */

  @Value.Parameter
  @Value.Default
  default VulkanBlendFactor srcColorBlendFactor()
  {
    return VulkanBlendFactor.VK_BLEND_FACTOR_ONE;
  }

  /**
   * @return the blend factor used to determine the destination factors (Dr,Dg,Db).
   */

  @Value.Parameter
  @Value.Default
  default VulkanBlendFactor dstColorBlendFactor()
  {
    return VulkanBlendFactor.VK_BLEND_FACTOR_ZERO;
  }

  /**
   * @return the blend operation used to calculate the RGB values to write to the color attachment.
   */

  @Value.Parameter
  @Value.Default
  default VulkanBlendOp colorBlendOp()
  {
    return VulkanBlendOp.VK_BLEND_OP_ADD;
  }

  /**
   * @return the blend factor used to determine the source factor Sa.
   */

  @Value.Parameter
  @Value.Default
  default VulkanBlendFactor srcAlphaBlendFactor()
  {
    return VulkanBlendFactor.VK_BLEND_FACTOR_ONE;
  }

  /**
   * @return the blend factor used to determine the destination factor Da.
   */

  @Value.Parameter
  @Value.Default
  default VulkanBlendFactor dstAlphaBlendFactor()
  {
    return VulkanBlendFactor.VK_BLEND_FACTOR_ZERO;
  }

  /**
   * @return the blend operation used to calculate the alpha values to write to the color
   * attachment.
   */

  @Value.Parameter
  @Value.Default
  default VulkanBlendOp alphaBlendOp()
  {
    return VulkanBlendOp.VK_BLEND_OP_ADD;
  }

  /**
   * @return the R, G, B, and/or A components enabled for writing.
   */

  @Value.Parameter
  @Value.Default
  default Set<VulkanColorComponentFlag> colorWriteMask()
  {
    return EnumSet.allOf(VulkanColorComponentFlag.class);
  }
}
