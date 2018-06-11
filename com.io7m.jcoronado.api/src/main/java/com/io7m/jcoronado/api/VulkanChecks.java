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

import java.util.Objects;

/**
 * Functions to check the return values of Vulkan calls.
 */

public final class VulkanChecks
{
  private VulkanChecks()
  {

  }

  /**
   * Check a return code.
   *
   * @param code     The return code
   * @param function The function name
   *
   * @throws VulkanCallFailedException If the return code denotes an error
   */

  public static void checkReturnCode(
    final int code,
    final String function)
    throws VulkanCallFailedException
  {
    Objects.requireNonNull(function, "function");

    if (code != 0) {
      throw new VulkanCallFailedException(
        code,
        function,
        new StringBuilder(64)
          .append("Function ")
          .append(function)
          .append(" returned 0x")
          .append(Integer.toUnsignedString(code, 16))
          .append(" (")
          .append(Integer.toString(code))
          .append(") (")
          .append(VulkanErrorCodes.errorName(code).orElse("Unrecognized error code"))
          .append(')')
          .toString());
    }
  }
}
