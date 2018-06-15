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
 * @see "VkShaderStageFlagBits"
 */

public enum VulkanShaderStageFlag implements VulkanEnumBitmaskType
{
  /**
   * The vertex stage.
   */

  VK_SHADER_STAGE_VERTEX_BIT(0x00000001),

  /**
   * The tessellation control stage.
   */

  VK_SHADER_STAGE_TESSELLATION_CONTROL_BIT(0x00000002),

  /**
   * The tessellation evaluation stage.
   */

  VK_SHADER_STAGE_TESSELLATION_EVALUATION_BIT(0x00000004),

  /**
   * The geometry stage.
   */

  VK_SHADER_STAGE_GEOMETRY_BIT(0x00000008),

  /**
   * The fragment stage.
   */

  VK_SHADER_STAGE_FRAGMENT_BIT(0x00000010),

  /**
   * The compute stage.
   */

  VK_SHADER_STAGE_COMPUTE_BIT(0x00000020),

  /**
   * A combination of bits used as shorthand to specify all graphics stages defined above (excluding
   * the compute stage).
   */

  VK_SHADER_STAGE_ALL_GRAPHICS(0x0000001F),

  /**
   * A combination of bits used as shorthand to specify all shader stages supported by the device,
   * including all additional stages which are introduced by extensions.
   */

  VK_SHADER_STAGE_ALL(0x7FFFFFFF);

  private final int value;

  VulkanShaderStageFlag(
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
