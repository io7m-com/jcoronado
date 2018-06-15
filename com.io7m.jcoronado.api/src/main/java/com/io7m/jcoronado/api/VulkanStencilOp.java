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
 * @see "VkStencilOp"
 */

public enum VulkanStencilOp implements VulkanEnumIntegerType
{
  /**
   * VK_STENCIL_OP_KEEP keeps the current value.
   */

  VK_STENCIL_OP_KEEP(0),

  /**
   * VK_STENCIL_OP_ZERO sets the value to 0.
   */

  VK_STENCIL_OP_ZERO(1),

  /**
   * VK_STENCIL_OP_REPLACE sets the value to reference.
   */

  VK_STENCIL_OP_REPLACE(2),

  /**
   * VK_STENCIL_OP_INCREMENT_AND_CLAMP increments the current value and clamps to the maximum
   * representable unsigned value.
   */

  VK_STENCIL_OP_INCREMENT_AND_CLAMP(3),

  /**
   * VK_STENCIL_OP_DECREMENT_AND_CLAMP decrements the current value and clamps to 0.
   */

  VK_STENCIL_OP_DECREMENT_AND_CLAMP(4),

  /**
   * VK_STENCIL_OP_INVERT bitwise-inverts the current value.
   */

  VK_STENCIL_OP_INVERT(5),

  /**
   * VK_STENCIL_OP_INCREMENT_AND_WRAP increments the current value and wraps to 0 when the maximum
   * value would have been exceeded.
   */

  VK_STENCIL_OP_INCREMENT_AND_WRAP(6),

  /**
   * VK_STENCIL_OP_DECREMENT_AND_WRAP decrements the current value and wraps to the maximum possible
   * value when the value would go below 0.
   */

  VK_STENCIL_OP_DECREMENT_AND_WRAP(7);

  private final int value;

  VulkanStencilOp(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
