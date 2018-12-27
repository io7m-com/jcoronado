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
 * Flags specifying certain supported operations on a descriptor pool
 *
 * @see "VkDescriptorPoolCreateFlagBits"
 */

@VulkanAPIEnumType(vulkanEnum = "VkDescriptorPoolCreateFlagBits")
public enum VulkanDescriptorPoolCreateFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies that descriptor sets can return their individual allocations to the pool, i.e. all of
   * vkAllocateDescriptorSets, vkFreeDescriptorSets, and vkResetDescriptorPool are allowed.
   * Otherwise, descriptor sets allocated from the pool must not be individually freed back to the
   * pool, i.e. only vkAllocateDescriptorSets and vkResetDescriptorPool are allowed.
   */

  VK_DESCRIPTOR_POOL_CREATE_FREE_DESCRIPTOR_SET_BIT(0x00000001);

  private final int value;

  VulkanDescriptorPoolCreateFlag(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
