/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import com.io7m.immutables.styles.ImmutablesStyleType;
import com.io7m.jcoronado.api.VulkanAPIStructType;
import org.immutables.value.Value;

import java.util.Set;

/**
 * Structure specifying parameters of a newly created debug messenger.
 */

@VulkanAPIStructType(api = "VK_EXT_debug_utils", vulkanStruct = "VkDebugUtilsMessengerCreateInfoEXT")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanDebugUtilsMessengerCreateInfoEXTType
{
  /**
   * @return The creation flags
   */

  Set<VulkanDebugUtilsMessengerCreateFlag> flags();

  /**
   * @return The message severity selection
   */

  Set<VulkanDebugUtilsMessageSeverityFlag> severity();

  /**
   * @return The message type selection
   */

  Set<VulkanDebugUtilsMessageTypeFlag> type();

  /**
   * @return The message callback
   */

  VulkanDebugUtilsMessengerCallbackEXTType callback();
}
