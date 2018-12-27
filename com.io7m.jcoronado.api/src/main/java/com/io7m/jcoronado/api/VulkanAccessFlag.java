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
 * Bitmask specifying memory access types that will participate in a memory dependency.
 *
 * @see "VkAccessFlagBits"
 */

@VulkanAPIEnumType(vulkanEnum = "VkAccessFlagBits")
public enum VulkanAccessFlag implements VulkanEnumBitmaskType
{
  /**
   * VK_ACCESS_INDIRECT_COMMAND_READ_BIT specifies read access to an indirect command structure read
   * as part of an indirect drawing or dispatch command.
   */

  VK_ACCESS_INDIRECT_COMMAND_READ_BIT(0x00000001),

  /**
   * VK_ACCESS_INDEX_READ_BIT specifies read access to an index buffer as part of an indexed drawing
   * command, bound by vkCmdBindIndexBuffer.
   */

  VK_ACCESS_INDEX_READ_BIT(0x00000002),

  /**
   * VK_ACCESS_VERTEX_ATTRIBUTE_READ_BIT specifies read access to a vertex buffer as part of a
   * drawing command, bound by vkCmdBindVertexBuffers.
   */

  VK_ACCESS_VERTEX_ATTRIBUTE_READ_BIT(0x00000004),

  /**
   * VK_ACCESS_UNIFORM_READ_BIT specifies read access to a uniform buffer.
   */

  VK_ACCESS_UNIFORM_READ_BIT(0x00000008),

  /**
   * VK_ACCESS_INPUT_ATTACHMENT_READ_BIT specifies read access to an input attachment within a
   * render pass during fragment shading.
   */

  VK_ACCESS_INPUT_ATTACHMENT_READ_BIT(0x00000010),

  /**
   * VK_ACCESS_SHADER_READ_BIT specifies read access to a storage buffer, uniform texel buffer,
   * storage texel buffer, sampled image, or storage image.
   */

  VK_ACCESS_SHADER_READ_BIT(0x00000020),

  /**
   * VK_ACCESS_SHADER_WRITE_BIT specifies write access to a storage buffer, storage texel buffer, or
   * storage image.
   */

  VK_ACCESS_SHADER_WRITE_BIT(0x00000040),

  /**
   * VK_ACCESS_COLOR_ATTACHMENT_READ_BIT specifies read access to a color attachment, such as via
   * blending, logic operations, or via certain subpass load operations. It does not include
   * advanced blend operations.
   */

  VK_ACCESS_COLOR_ATTACHMENT_READ_BIT(0x00000080),

  /**
   * VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT specifies write access to a color or resolve attachment
   * during a render pass or via certain subpass load and store operations.
   */

  VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT(0x00000100),

  /**
   * VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_READ_BIT specifies read access to a depth/stencil
   * attachment, via depth or stencil operations or via certain subpass load operations.
   */

  VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_READ_BIT(0x00000200),

  /**
   * VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT specifies write access to a depth/stencil
   * attachment, via depth or stencil operations or via certain subpass load and store operations.
   */

  VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT(0x00000400),

  /**
   * VK_ACCESS_TRANSFER_READ_BIT specifies read access to an image or buffer in a copy operation.
   */

  VK_ACCESS_TRANSFER_READ_BIT(0x00000800),

  /**
   * VK_ACCESS_TRANSFER_WRITE_BIT specifies write access to an image or buffer in a clear or copy
   * operation.
   */

  VK_ACCESS_TRANSFER_WRITE_BIT(0x00001000),

  /**
   * VK_ACCESS_HOST_READ_BIT specifies read access by a host operation. Accesses of this type are
   * not performed through a resource, but directly on memory.
   */

  VK_ACCESS_HOST_READ_BIT(0x00002000),

  /**
   * VK_ACCESS_HOST_WRITE_BIT specifies write access by a host operation. Accesses of this type are
   * not performed through a resource, but directly on memory.
   */

  VK_ACCESS_HOST_WRITE_BIT(0x00004000),

  /**
   * VK_ACCESS_MEMORY_READ_BIT specifies read access via non-specific entities. These entities
   * include the Vulkan device and host, but may also include entities external to the Vulkan device
   * or otherwise not part of the core Vulkan pipeline. When included in a destination access mask,
   * makes all available writes visible to all future read accesses on entities known to the Vulkan
   * device.
   */

  VK_ACCESS_MEMORY_READ_BIT(0x00008000),

  /**
   * VK_ACCESS_MEMORY_WRITE_BIT specifies write access via non-specific entities. These entities
   * include the Vulkan device and host, but may also include entities external to the Vulkan device
   * or otherwise not part of the core Vulkan pipeline. When included in a source access mask, all
   * writes that are performed by entities known to the Vulkan device are made available. When
   * included in a destination access mask, makes all available writes visible to all future write
   * accesses on entities known to the Vulkan device.
   */

  VK_ACCESS_MEMORY_WRITE_BIT(0x00010000),

  /**
   * VK_ACCESS_COMMAND_PROCESS_READ_BIT_NVX specifies reads from VkBuffer inputs to
   * vkCmdProcessCommandsNVX.
   */

  VK_ACCESS_COMMAND_PROCESS_READ_BIT_NVX(0x00020000),

  /**
   * VK_ACCESS_COMMAND_PROCESS_WRITE_BIT_NVX specifies writes to the target command buffer in
   * vkCmdProcessCommandsNVX.
   */

  VK_ACCESS_COMMAND_PROCESS_WRITE_BIT_NVX(0x00040000),

  /**
   * VK_ACCESS_COLOR_ATTACHMENT_READ_NONCOHERENT_BIT_EXT is similar to
   * VK_ACCESS_COLOR_ATTACHMENT_READ_BIT, but also includes advanced blend operations.
   */

  VK_ACCESS_COLOR_ATTACHMENT_READ_NONCOHERENT_BIT_EXT(0x00080000);

  private final int value;

  VulkanAccessFlag(
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
