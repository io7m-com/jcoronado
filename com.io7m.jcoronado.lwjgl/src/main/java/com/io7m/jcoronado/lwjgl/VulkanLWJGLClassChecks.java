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

package com.io7m.jcoronado.lwjgl;

import com.io7m.jcoronado.api.VulkanIncompatibleClassException;

import java.util.Objects;

final class VulkanLWJGLClassChecks
{
  private VulkanLWJGLClassChecks()
  {

  }

  @SuppressWarnings("unchecked")
  public static <T> T check(
    final Object object,
    final Class<T> clazz)
    throws VulkanIncompatibleClassException
  {
    Objects.requireNonNull(object, "object");
    Objects.requireNonNull(clazz, "clazz");

    final var object_class = object.getClass();
    if (Objects.equals(object_class, clazz)) {
      return (T) object;
    }

    final var separator = System.lineSeparator();
    throw new VulkanIncompatibleClassException(
      new StringBuilder(128)
        .append("Incompatible class.")
        .append(separator)
        .append("  Expected: ")
        .append(clazz.getCanonicalName())
        .append(separator)
        .append("  Received: ")
        .append(object_class.getCanonicalName())
        .append(separator)
        .toString());
  }
}
