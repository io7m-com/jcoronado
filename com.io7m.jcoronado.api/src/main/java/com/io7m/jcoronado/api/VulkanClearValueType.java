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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Union specifying a clear value.
 *
 * @see "VkClearValue"
 */

@VulkanAPIStructType(vulkanStruct = "VkClearValue")
public sealed interface VulkanClearValueType
{
  /**
   * A depth/stencil value.
   */

  @VulkanAPIStructType(vulkanStruct = "VkClearValue")
  @ImmutablesStyleType
  @Value.Immutable
  non-sealed interface VulkanClearValueDepthStencilType
    extends VulkanClearValueType
  {
    /**
     * @return The depth value
     */

    @Value.Default
    default float depth()
    {
      return 0.0f;
    }

    /**
     * @return The stencil value
     */

    @Value.Default
    default int stencil()
    {
      return 0;
    }
  }

  /**
   * A depth/stencil value.
   */

  @VulkanAPIStructType(vulkanStruct = "VkClearValue")
  sealed interface VulkanClearValueColorType
    extends VulkanClearValueType
  {
    /**
     * Pack the color value as an integer value suitable for passing to
     * {@link VulkanCommandBufferType#fillBuffer(VulkanBufferType, long, long, int)}.
     *
     * @param order The target byte order
     *
     * @return The packed color value
     */

    int asFillBufferInteger(ByteOrder order);

    /**
     * Pack the color value as an integer value suitable for passing to
     * {@link VulkanCommandBufferType#fillBuffer(VulkanBufferType, long, long, int)}.
     *
     * The value is packed based on the host byte order.
     *
     * @return The packed color value
     */

    default int asFillBufferInteger()
    {
      return this.asFillBufferInteger(ByteOrder.nativeOrder());
    }
  }

  /**
   * A color consisting of signed integer components.
   */

  @VulkanAPIStructType(vulkanStruct = "VkClearValue")
  @ImmutablesStyleType
  @Value.Immutable
  non-sealed interface VulkanClearValueColorIntegerSignedType
    extends VulkanClearValueColorType
  {
    @Override
    default int asFillBufferInteger(
      final ByteOrder order)
    {
      final var buffer = ByteBuffer.allocate(4);
      buffer.order(order);
      buffer.put(0, (byte) this.red());
      buffer.put(1, (byte) this.green());
      buffer.put(2, (byte) this.blue());
      buffer.put(3, (byte) this.alpha());
      return buffer.getInt(0);
    }

    /**
     * @return The red channel
     */

    @Value.Default
    default int red()
    {
      return 0;
    }

    /**
     * @return The green channel
     */

    @Value.Default
    default int green()
    {
      return 0;
    }

    /**
     * @return The blue channel
     */

    @Value.Default
    default int blue()
    {
      return 0;
    }

    /**
     * @return The alpha channel
     */

    @Value.Default
    default int alpha()
    {
      return 0;
    }
  }

  /**
   * A color consisting of unsigned integer components.
   */

  @VulkanAPIStructType(vulkanStruct = "VkClearValue")
  @ImmutablesStyleType
  @Value.Immutable
  non-sealed interface VulkanClearValueColorIntegerUnsignedType
    extends VulkanClearValueColorType
  {
    @Override
    default int asFillBufferInteger(
      final ByteOrder order)
    {
      final var buffer = ByteBuffer.allocate(4);
      buffer.order(order);
      buffer.put(0, (byte) (this.red() & 0xff));
      buffer.put(1, (byte) (this.green() & 0xff));
      buffer.put(2, (byte) (this.blue() & 0xff));
      buffer.put(3, (byte) (this.alpha() & 0xff));
      return buffer.getInt(0);
    }

    /**
     * @return The red channel
     */

    @Value.Default
    default int red()
    {
      return 0;
    }

    /**
     * @return The green channel
     */

    @Value.Default
    default int green()
    {
      return 0;
    }

    /**
     * @return The blue channel
     */

    @Value.Default
    default int blue()
    {
      return 0;
    }

    /**
     * @return The alpha channel
     */

    @Value.Default
    default int alpha()
    {
      return 0;
    }
  }

  /**
   * A color consisting of floating-point components.
   */

  @VulkanAPIStructType(vulkanStruct = "VkClearValue")
  @ImmutablesStyleType
  @Value.Immutable
  non-sealed interface VulkanClearValueColorFloatingPointType
    extends VulkanClearValueColorType
  {
    @Override
    default int asFillBufferInteger(
      final ByteOrder order)
    {
      final var buffer = ByteBuffer.allocate(4);
      buffer.order(order);
      buffer.put(0, (byte) (this.red() * 255.0f));
      buffer.put(1, (byte) (this.green() * 255.0f));
      buffer.put(2, (byte) (this.blue() * 255.0f));
      buffer.put(3, (byte) (this.alpha() * 255.0f));
      return buffer.getInt(0);
    }

    /**
     * @return The red channel
     */

    @Value.Default
    default float red()
    {
      return 0.0f;
    }

    /**
     * @return The green channel
     */

    @Value.Default
    default float green()
    {
      return 0.0f;
    }

    /**
     * @return The blue channel
     */

    @Value.Default
    default float blue()
    {
      return 0.0f;
    }

    /**
     * @return The alpha channel
     */

    @Value.Default
    default float alpha()
    {
      return 0.0f;
    }
  }
}
