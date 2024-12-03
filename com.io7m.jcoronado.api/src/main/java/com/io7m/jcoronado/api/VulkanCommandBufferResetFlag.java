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
 * Bitmask specifying usage behavior for command buffer resets.
 *
 * @see "VkCommandBufferResetFlagBits"
 */

@VulkanAPIEnumType(vulkanEnum = "VkCommandBufferResetFlagBits")
public enum VulkanCommandBufferResetFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies that most or all memory resources currently owned by the command
   * buffer should be returned to the parent command pool. If this flag is not
   * set, then the command buffer may hold onto memory resources and reuse them
   * when recording commands. commandBuffer is moved to the initial state.
   */

  VULKAN_COMMAND_BUFFER_RESET_RELEASE_RESOURCES_BIT(0x00000001);

  private final int value;

  VulkanCommandBufferResetFlag(
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
