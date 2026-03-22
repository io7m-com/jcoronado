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

import com.io7m.seltzer.api.SStructuredErrorExceptionType;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * The base type of exceptions raised by the API.
 */

public abstract class VulkanException
  extends Exception
  implements SStructuredErrorExceptionType<String>
{
  private final Map<String, String> attributes;
  private final String errorCode;
  private final Optional<String> remediatingAction;

  /**
   * Construct an exception.
   *
   * @param message             The message
   * @param inAttributes        The attributes
   * @param inErrorCode         The error code
   * @param inRemediatingAction The remediating action
   */

  public VulkanException(
    final String message,
    final Map<String, String> inAttributes,
    final String inErrorCode,
    final Optional<String> inRemediatingAction)
  {
    super(message);

    this.attributes =
      Map.copyOf(inAttributes);
    this.errorCode =
      Objects.requireNonNull(inErrorCode, "errorCode");
    this.remediatingAction =
      Objects.requireNonNull(inRemediatingAction, "remediatingAction");
  }

  /**
   * Construct an exception.
   *
   * @param message             The message
   * @param cause               The cause
   * @param inAttributes        The attributes
   * @param inErrorCode         The error code
   * @param inRemediatingAction The remediating action
   */

  public VulkanException(
    final String message,
    final Throwable cause,
    final Map<String, String> inAttributes,
    final String inErrorCode,
    final Optional<String> inRemediatingAction)
  {
    super(message, cause);

    this.attributes =
      Map.copyOf(inAttributes);
    this.errorCode =
      Objects.requireNonNull(inErrorCode, "errorCode");
    this.remediatingAction =
      Objects.requireNonNull(inRemediatingAction, "remediatingAction");
  }

  @Override
  public final Map<String, String> attributes()
  {
    return this.attributes;
  }

  @Override
  public final String errorCode()
  {
    return this.errorCode;
  }

  @Override
  public final Optional<String> remediatingAction()
  {
    return this.remediatingAction;
  }

  @Override
  public final Optional<Throwable> exception()
  {
    return Optional.of(this);
  }
}
