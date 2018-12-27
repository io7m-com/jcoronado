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
 * @see "VkBufferUsageFlagBits"
 */

@VulkanAPIEnumType(vulkanEnum = "VkBufferUsageFlagBits")
public enum VulkanBufferUsageFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies that the buffer can be used as the source of a transfer command (see the definition
   * of VK_PIPELINE_STAGE_TRANSFER_BIT).
   */

  VK_BUFFER_USAGE_TRANSFER_SRC_BIT(0x00000001),

  /**
   * Specifies that the buffer can be used as the destination of a transfer command.
   */

  VK_BUFFER_USAGE_TRANSFER_DST_BIT(0x00000002),

  /**
   * Specifies that the buffer can be used to create a VkBufferView suitable for occupying a
   * VkDescriptorSet slot of type VK_DESCRIPTOR_TYPE_UNIFORM_TEXEL_BUFFER.
   */

  VK_BUFFER_USAGE_UNIFORM_TEXEL_BUFFER_BIT(0x00000004),

  /**
   * Specifies that the buffer can be used to create a VkBufferView suitable for occupying a
   * VkDescriptorSet slot of type VK_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER.
   */

  VK_BUFFER_USAGE_STORAGE_TEXEL_BUFFER_BIT(0x00000008),

  /**
   * Specifies that the buffer can be used in a VkDescriptorBufferInfo suitable for occupying a
   * VkDescriptorSet slot either of type VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER or
   * VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC.
   */

  VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT(0x00000010),

  /**
   * Specifies that the buffer can be used in a VkDescriptorBufferInfo suitable for occupying a
   * VkDescriptorSet slot either of type VK_DESCRIPTOR_TYPE_STORAGE_BUFFER or
   * VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC.
   */

  VK_BUFFER_USAGE_STORAGE_BUFFER_BIT(0x00000020),

  /**
   * Specifies that the buffer is suitable for passing as the buffer parameter to
   * vkCmdBindIndexBuffer.
   */

  VK_BUFFER_USAGE_INDEX_BUFFER_BIT(0x00000040),

  /**
   * Specifies that the buffer is suitable for passing as an element of the pBuffers array to
   * vkCmdBindVertexBuffers.
   */

  VK_BUFFER_USAGE_VERTEX_BUFFER_BIT(0x00000080),

  /**
   * Specifies that the buffer is suitable for passing as the buffer parameter to vkCmdDrawIndirect,
   * vkCmdDrawIndexedIndirect, or vkCmdDispatchIndirect. It is also suitable for passing as the
   * buffer member of VkIndirectCommandsTokenNVX, or sequencesCountBuffer or sequencesIndexBuffer
   * member of VkCmdProcessCommandsInfoNVX
   */

  VK_BUFFER_USAGE_INDIRECT_BUFFER_BIT(0x00000100),

  /**
   * VK_BUFFER_USAGE_CONDITIONAL_RENDERING_BIT_EXT specifies that the buffer is suitable for passing
   * as the buffer parameter to vkCmdBeginConditionalRenderingEXT.
   */

  VK_BUFFER_USAGE_CONDITIONAL_RENDERING_BIT_EXT(0x00000200);

  private final int value;

  VulkanBufferUsageFlag(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
