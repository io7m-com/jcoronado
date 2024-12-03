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
 * Bitmask specifying which severities of events cause a debug messenger callback.
 */

@VulkanAPIEnumType(vulkanEnum = "VkDebugUtilsMessengerCreateFlagsEXT")
public enum VulkanDebugUtilsMessageSeverityFlag implements VulkanEnumBitmaskType
{
  /**
   * Specifies the most verbose output indicating all diagnostic messages from
   * the Vulkan loader, layers, and drivers should be captured.
   */

  VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT(0x00000001),

  /**
   * Specifies an informational message such as resource details that may be
   * handy when debugging an application.
   */

  VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT(0x00000010),

  /**
   * Specifies use of Vulkan that may expose an app bug.
   */

  VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT(0x00000100),

  /**
   * Specifies that the application has violated a valid usage condition of
   * the specification.
   */

  VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT(0x00001000);

  private final int value;

  VulkanDebugUtilsMessageSeverityFlag(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
