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
 * Bitmask controlling how a command pool is created.
 *
 * @see "VkCommandPoolCreateFlags"
 */

@VulkanAPIEnumType(vulkanEnum = "VkCommandPoolCreateFlags")
public enum VulkanCommandPoolCreateFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies that command buffers allocated from the pool will be short-lived, meaning that they
   * will be reset or freed in a relatively short timeframe. This flag may be used by the
   * implementation to control memory allocation behavior within the pool.
   */

  VK_COMMAND_POOL_CREATE_TRANSIENT_BIT(0x00000001),

  /**
   * Allows any command buffer allocated from a pool to be individually reset to the initial state;
   * either by calling vkResetCommandBuffer, or via the implicit reset when calling
   * vkBeginCommandBuffer. If this flag is not set on a pool, then vkResetCommandBuffer must not be
   * called for any command buffer allocated from that pool.
   */

  VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT(0x00000002),

  /**
   * Specifies that command buffers allocated from the pool are protected command buffers. If the
   * protected memory feature is not enabled, the VK_COMMAND_POOL_CREATE_PROTECTED_BIT bit of flags
   * must not be set.
   */

  VK_COMMAND_POOL_CREATE_PROTECTED_BIT(0x00000004);

  private final int value;

  VulkanCommandPoolCreateFlag(
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
