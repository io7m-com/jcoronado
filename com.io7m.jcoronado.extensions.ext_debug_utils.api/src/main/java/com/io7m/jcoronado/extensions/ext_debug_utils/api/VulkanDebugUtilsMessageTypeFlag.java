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

package com.io7m.jcoronado.extensions.ext_debug_utils.api;

import com.io7m.jcoronado.api.VulkanAPIEnumType;
import com.io7m.jcoronado.api.VulkanEnumBitmaskType;

/**
 * Bitmask specifying which types of events cause a debug messenger callback.
 */

@VulkanAPIEnumType(vulkanEnum = "VkDebugUtilsMessageTypeFlagBitsEXT")
public enum VulkanDebugUtilsMessageTypeFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies that some general event has occurred.
   */

  VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT(0x00000001),

  /**
   * Specifies that something has occurred during validation against the Vulkan
   * specification that may indicate invalid behavior.
   */

  VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT(0x00000002),

  /**
   * Specifies a potentially non-optimal use of Vulkan, e.g. using
   * vkCmdClearColorImage when setting VkAttachmentDescription::loadOp
   * to VK_ATTACHMENT_LOAD_OP_CLEAR would have worked.
   */

  VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT(0x00000004);

  private final int value;

  VulkanDebugUtilsMessageTypeFlag(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
