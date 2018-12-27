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
 * @see "VkSubpassContents"
 */

@VulkanAPIEnumType(vulkanEnum = "VkSubpassContents")
public enum VulkanSubpassContents implements VulkanEnumIntegerType
{
  /**
   * Specifies that the contents of the subpass will be recorded inline in the primary command
   * buffer, and secondary command buffers must not be executed within the subpass.
   */

  VK_SUBPASS_CONTENTS_INLINE(0),

  /**
   * Specifies that the contents are recorded in secondary command buffers that will be called from
   * the primary command buffer, and vkCmdExecuteCommands is the only valid command on the command
   * buffer until vkCmdNextSubpass or vkCmdEndRenderPass.
   */

  VK_SUBPASS_CONTENTS_SECONDARY_COMMAND_BUFFERS(1);

  private final int value;

  VulkanSubpassContents(
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
