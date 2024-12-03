/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
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


package com.io7m.jcoronado.layers.khronos_validation.api;

/**
 * Specifies what action is to be taken when a layer reports information.
 */

public enum VulkanValidationDebugActionValue
{
  /**
   * Log a txt message to stdout or to a log filename.
   */

  VK_DBG_LAYER_ACTION_LOG_MSG,

  /**
   * Log a txt message using the Windows OutputDebugString function.
   */

  VK_DBG_LAYER_ACTION_DEBUG_OUTPUT,

  /**
   * Trigger a breakpoint if a debugger is in use.
   */

  VK_DBG_LAYER_ACTION_BREAK
}