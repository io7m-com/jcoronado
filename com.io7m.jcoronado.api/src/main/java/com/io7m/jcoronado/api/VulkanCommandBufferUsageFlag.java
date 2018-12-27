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
 * Bitmask specifying usage behavior for command buffer.
 *
 * @see "VkCommandBufferUsageFlags"
 */

@VulkanAPIEnumType(vulkanEnum = "VkCommandBufferUsageFlags")
public enum VulkanCommandBufferUsageFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies that each recording of the command buffer will only be submitted once, and the
   * command buffer will be reset and recorded again between each submission.
   */

  VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT(0x00000001),

  /**
   * Specifies that a secondary command buffer is considered to be entirely inside a render pass. If
   * this is a primary command buffer, then this bit is ignored.
   */

  VK_COMMAND_BUFFER_USAGE_RENDER_PASS_CONTINUE_BIT(0x00000002),

  /**
   * Specifies that a command buffer can be resubmitted to a queue while it is in the pending state,
   * and recorded into multiple primary command buffers.
   */

  VK_COMMAND_BUFFER_USAGE_SIMULTANEOUS_USE_BIT(0x00000004);

  private final int value;

  VulkanCommandBufferUsageFlag(
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
