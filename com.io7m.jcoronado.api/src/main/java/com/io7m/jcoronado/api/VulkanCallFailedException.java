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
 * An exception raised by a failing Vulkan call.
 */

public final class VulkanCallFailedException extends VulkanException
{
  private final int code;
  private final String function;

  /**
   * Construct an exception.
   *
   * @param in_code     The returned error code
   * @param in_function The function that failed
   * @param message     The error message
   */

  public VulkanCallFailedException(
    final int in_code,
    final String in_function,
    final String message)
  {
    super(Objects.requireNonNull(message, "message"));
    this.code = in_code;
    this.function = Objects.requireNonNull(in_function, "function");
  }

  @Override
  public boolean equals(final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || !Objects.equals(this.getClass(), o.getClass())) {
      return false;
    }
    final var that = (VulkanCallFailedException) o;
    return this.code == that.code && Objects.equals(
      this.function,
      that.function);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(Integer.valueOf(this.code), this.function);
  }

  /**
   * @return The name of the function that failed
   */

  public String function()
  {
    return this.function;
  }

  /**
   * @return The error code returned by the function that failed
   */

  public int errorCode()
  {
    return this.code;
  }
}
