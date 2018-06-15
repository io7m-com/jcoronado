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
 * The logical operations supported by Vulkan are summarized in the following table in which:
 *
 * <ul>
 * <li>¬ is bitwise invert</li>
 *
 * <li>∧ is bitwise and</li>
 *
 * <li>∨ is bitwise or</li>
 *
 * <li>⊕ is bitwise exclusive or</li>
 *
 * <li> s is the fragment’s Rs0, Gs0, Bs0 or As0 component value for the fragment output
 * corresponding to the color attachment being updated</li>
 *
 * <li>d is the color attachment’s R, G, B or A component value</li>
 * </ul>
 *
 * @see "VkLogicOp"
 */

public enum VulkanLogicOp implements VulkanEnumIntegerType
{
  /**
   * 0
   */

  VK_LOGIC_OP_CLEAR(0),

  /**
   * s ∧ d
   */

  VK_LOGIC_OP_AND(1),

  /**
   * s ∧ ¬ d
   */

  VK_LOGIC_OP_AND_REVERSE(2),

  /**
   * s
   */

  VK_LOGIC_OP_COPY(3),

  /**
   * ¬ s ∧ d
   */

  VK_LOGIC_OP_AND_INVERTED(4),

  /**
   * d
   */

  VK_LOGIC_OP_NO_OP(5),

  /**
   * s ⊕ d
   */

  VK_LOGIC_OP_XOR(6),

  /**
   * s ∨ d
   */

  VK_LOGIC_OP_OR(7),

  /**
   * ¬ (s ∨ d)
   */

  VK_LOGIC_OP_NOR(8),

  /**
   * ¬ (s ⊕ d)
   */

  VK_LOGIC_OP_EQUIVALENT(9),

  /**
   * ¬ d
   */

  VK_LOGIC_OP_INVERT(10),

  /**
   * s ∨ ¬ d
   */

  VK_LOGIC_OP_OR_REVERSE(11),

  /**
   * ¬ s
   */

  VK_LOGIC_OP_COPY_INVERTED(12),

  /**
   * ¬ s ∨ d
   */

  VK_LOGIC_OP_OR_INVERTED(13),

  /**
   * ¬ (s ∧ d)
   */

  VK_LOGIC_OP_NAND(14),

  /**
   * all 1s
   */

  VK_LOGIC_OP_SET(15);

  private final int value;

  VulkanLogicOp(
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
