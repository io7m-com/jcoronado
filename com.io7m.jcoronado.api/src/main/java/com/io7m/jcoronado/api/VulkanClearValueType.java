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

import static com.io7m.jcoronado.api.VulkanClearValueType.Type.COLOR;
import static com.io7m.jcoronado.api.VulkanClearValueType.Type.DEPTH_STENCIL;
import static com.io7m.jcoronado.api.VulkanClearValueType.VulkanClearValueColorType.ColorType.COLOR_FLOATING_POINT;
import static com.io7m.jcoronado.api.VulkanClearValueType.VulkanClearValueColorType.ColorType.COLOR_INTEGER_SIGNED;
import static com.io7m.jcoronado.api.VulkanClearValueType.VulkanClearValueColorType.ColorType.COLOR_INTEGER_UNSIGNED;

/**
 * Union specifying a clear value.
 *
 * @see "VkClearValue"
 */

@VulkanAPIStructType(vulkanStruct = "VkClearValue")
public interface VulkanClearValueType
{
  /**
   * @return The type of value
   */

  Type type();

  /**
   * The actual type of clea value.
   */

  enum Type
  {
    /**
     * A depth/stencil value.
     */

    DEPTH_STENCIL,

    /**
     * A color value.
     */

    COLOR,
  }

  /**
   * A depth/stencil value.
   */

  @VulkanAPIStructType(vulkanStruct = "VkClearValue")
  @ImmutablesStyleType
  @Value.Immutable
  interface VulkanClearValueDepthStencilType extends VulkanClearValueType
  {
    @Override
    default Type type()
    {
      return DEPTH_STENCIL;
    }

    /**
     * @return The depth value
     */

    @Value.Parameter
    float depth();

    /**
     * @return The stencil value
     */

    @Value.Parameter
    int stencil();
  }

  /**
   * A depth/stencil value.
   */

  @VulkanAPIStructType(vulkanStruct = "VkClearValue")
  interface VulkanClearValueColorType extends VulkanClearValueType
  {
    @Override
    default Type type()
    {
      return COLOR;
    }

    /**
     * @return The type of value
     */

    ColorType colorType();

    /**
     * The actual type of clea value.
     */

    enum ColorType
    {
      /**
       * A color consisting of signed integer components.
       */

      COLOR_INTEGER_SIGNED,

      /**
       * A color consisting of unsigned integer components.
       */

      COLOR_INTEGER_UNSIGNED,

      /**
       * A color consisting of floating-point components.
       */

      COLOR_FLOATING_POINT
    }
  }

  /**
   * A color consisting of signed integer components.
   */

  @VulkanAPIStructType(vulkanStruct = "VkClearValue")
  @ImmutablesStyleType
  @Value.Immutable
  interface VulkanClearValueColorIntegerSignedType extends
    VulkanClearValueColorType
  {
    @Override
    default ColorType colorType()
    {
      return COLOR_INTEGER_SIGNED;
    }

    /**
     * @return The red channel
     */

    @Value.Parameter
    int red();

    /**
     * @return The green channel
     */

    @Value.Parameter
    int green();

    /**
     * @return The blue channel
     */

    @Value.Parameter
    int blue();

    /**
     * @return The alpha channel
     */

    @Value.Parameter
    int alpha();
  }

  /**
   * A color consisting of unsigned integer components.
   */

  @VulkanAPIStructType(vulkanStruct = "VkClearValue")
  @ImmutablesStyleType
  @Value.Immutable
  interface VulkanClearValueColorIntegerUnsignedType extends
    VulkanClearValueColorType
  {
    @Override
    default ColorType colorType()
    {
      return COLOR_INTEGER_UNSIGNED;
    }

    /**
     * @return The red channel
     */

    @Value.Parameter
    int red();

    /**
     * @return The green channel
     */

    @Value.Parameter
    int green();

    /**
     * @return The blue channel
     */

    @Value.Parameter
    int blue();

    /**
     * @return The alpha channel
     */

    @Value.Parameter
    int alpha();
  }

  /**
   * A color consisting of floating-point components.
   */

  @VulkanAPIStructType(vulkanStruct = "VkClearValue")
  @ImmutablesStyleType
  @Value.Immutable
  interface VulkanClearValueColorFloatingPointType extends
    VulkanClearValueColorType
  {
    @Override
    default ColorType colorType()
    {
      return COLOR_FLOATING_POINT;
    }

    /**
     * @return The red channel
     */

    @Value.Parameter
    float red();

    /**
     * @return The green channel
     */

    @Value.Parameter
    float green();

    /**
     * @return The blue channel
     */

    @Value.Parameter
    float blue();

    /**
     * @return The alpha channel
     */

    @Value.Parameter
    float alpha();
  }
}
