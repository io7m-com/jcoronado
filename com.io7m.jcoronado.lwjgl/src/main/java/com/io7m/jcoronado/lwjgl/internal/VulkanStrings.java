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

package com.io7m.jcoronado.lwjgl.internal;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;

import java.util.Collection;
import java.util.Objects;

/**
 * Functions over strings.
 */

public final class VulkanStrings
{
  private VulkanStrings()
  {

  }

  /**
   * Convert a collection of strings to a pointer buffer.
   *
   * @param stack The stack
   * @param input The input
   *
   * @return The string pointers
   */

  public static PointerBuffer stringsToPointerBuffer(
    final MemoryStack stack,
    final Collection<String> input)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(input, "input");

    final var buffer =
      stack.mallocPointer(input.size());

    var index = 0;
    for (final var string : input) {
      buffer.position(index);
      buffer.put(stack.ASCII(string));
      ++index;
    }

    buffer.position(0);
    return buffer;
  }
}
