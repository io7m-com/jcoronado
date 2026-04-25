/*
 * Copyright © 2026 Mark Raynsford <code@io7m.com> https://www.io7m.com
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
 * Bitmask specifying descriptor set layout binding properties.
 */

@VulkanAPIEnumType(vulkanEnum = "VkDescriptorBindingFlagBits")
public enum VulkanDescriptorBindingFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies that if descriptors in this binding are updated between when the
   * descriptor set is bound in a command buffer and when that command buffer
   * is submitted to a queue, then the submission will use the most recently
   * set descriptors for this binding and the updates do not invalidate the
   * command buffer.
   */

  VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND(0x00000001),

  /**
   * Specifies that descriptors in this binding can be updated after a command
   * buffer has bound this descriptor set, or while a command buffer that uses
   * this descriptor set is pending execution, as long as the descriptors that
   * are updated are not used by those command buffers.
   */

  VK_DESCRIPTOR_BINDING_UPDATE_UNUSED_WHILE_PENDING(0x00000002),

  /**
   * Specifies that descriptors in this binding that are not dynamically used
   * need not contain valid descriptors at the time the descriptors are
   * consumed.
   */

  VK_DESCRIPTOR_BINDING_PARTIALLY_BOUND(0x00000004),

  /**
   * Specifies that this is a variable-sized descriptor binding whose size will
   * be specified when a descriptor set is allocated using this layout.
   */

  VK_DESCRIPTOR_BINDING_VARIABLE_DESCRIPTOR_COUNT(0x00000008);

  private final int value;

  VulkanDescriptorBindingFlag(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
