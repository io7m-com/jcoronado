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

import org.slf4j.Logger;

import java.util.Objects;
import java.util.Set;

import static com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageSeverityFlag.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT;
import static com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageSeverityFlag.VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT;
import static com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageSeverityFlag.VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT;
import static com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageSeverityFlag.VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT;

/**
 * A convenience callback that delegates logging to SLF4J.
 */

public final class VulkanDebugUtilsSLF4J
  implements VulkanDebugUtilsMessengerCallbackEXTType
{
  private final Logger logger;

  /**
   * Construct a callback.
   *
   * @param inLogger The logger
   */

  public VulkanDebugUtilsSLF4J(
    final Logger inLogger)
  {
    this.logger = Objects.requireNonNull(inLogger, "logger");
  }

  @Override
  public boolean call(
    final Set<VulkanDebugUtilsMessageSeverityFlag> severity,
    final Set<VulkanDebugUtilsMessageTypeFlag> types,
    final VulkanDebugUtilsMessengerCallbackDataEXT data)
  {
    final var format = "0x{}: {}: {}";
    if (severity.contains(VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT)) {
      this.logger.error(
        format,
        Integer.toUnsignedString(data.messageIdNumber(), 16),
        data.messageIdName(),
        data.message());
    } else if (severity.contains(VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT)) {
      this.logger.debug(
        format,
        Integer.toUnsignedString(data.messageIdNumber(), 16),
        data.messageIdName(),
        data.message());
    } else if (severity.contains(VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT)) {
      this.logger.info(
        format,
        Integer.toUnsignedString(data.messageIdNumber(), 16),
        data.messageIdName(),
        data.message());
    } else if (severity.contains(VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT)) {
      this.logger.warn(
        format,
        Integer.toUnsignedString(data.messageIdNumber(), 16),
        data.messageIdName(),
        data.message());
    }
    return false;
  }
}
