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
 * @see "VkCompareOp"
 */

@VulkanAPIEnumType(vulkanEnum = "VkCompareOp")
public enum VulkanCompareOp implements VulkanEnumIntegerType
{
  /**
   * Specifies that the test never passes.
   */

  VK_COMPARE_OP_NEVER(0),

  /**
   * Specifies that the test passes when R &lt; S.
   */

  VK_COMPARE_OP_LESS(1),

  /**
   * Specifies that the test passes when R = S.
   */

  VK_COMPARE_OP_EQUAL(2),

  /**
   * Specifies that the test passes when R ≤ S.
   */

  VK_COMPARE_OP_LESS_OR_EQUAL(3),

  /**
   * Specifies that the test passes when R &gt; S.
   */

  VK_COMPARE_OP_GREATER(4),

  /**
   * Specifies that the test passes when R ≠ S.
   */

  VK_COMPARE_OP_NOT_EQUAL(5),

  /**
   * Specifies that the test passes when R ≥ S.
   */

  VK_COMPARE_OP_GREATER_OR_EQUAL(6),

  /**
   * Specifies that the test always passes.
   */

  VK_COMPARE_OP_ALWAYS(7);

  private final int value;

  VulkanCompareOp(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
