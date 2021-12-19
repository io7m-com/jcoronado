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

import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionType;
import com.io7m.jcoronado.api.VulkanInstanceType;

/**
 * @see "VK_EXT_debug_utils"
 */

public interface VulkanDebugUtilsType extends VulkanExtensionType
{
  @Override
  default String name()
  {
    return "VK_EXT_debug_utils";
  }

  /**
   * Create a debug messenger object.
   *
   * @param instance The instance
   * @param info     The creation info
   *
   * @return A messenger
   *
   * @throws VulkanException On errors
   */

  VulkanDebugUtilsMessengerEXTType createDebugUtilsMessenger(
    VulkanInstanceType instance,
    VulkanDebugUtilsMessengerCreateInfoEXT info)
    throws VulkanException;
}
