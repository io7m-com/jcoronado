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
 * An exception type used to temporarily wrap exceptions in an unchecked wrapper (for use in
 * streams and the like).
 */

public final class VulkanUncheckedException extends RuntimeException
{
  private final VulkanException cause;

  /**
   * Construct an exception.
   *
   * @param in_cause The cause
   */

  public VulkanUncheckedException(
    final VulkanException in_cause)
  {
    super(Objects.requireNonNull(in_cause, "cause"));
    this.cause = in_cause;
  }

  @Override
  public synchronized VulkanException getCause()
  {
    return this.cause;
  }
}
