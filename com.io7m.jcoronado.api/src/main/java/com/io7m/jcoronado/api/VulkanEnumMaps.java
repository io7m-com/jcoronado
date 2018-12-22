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

import java.util.HashMap;
import java.util.Map;

/**
 * Functions over enum ←→ integer maps.
 */

public final class VulkanEnumMaps
{
  private VulkanEnumMaps()
  {

  }

  /**
   * Bitwise OR the integer values of all of the given constants.
   *
   * @param values The values
   * @param <T>    The precise type of enum
   *
   * @return The integer-packed values
   */

  public static <T extends Enum<T> & VulkanEnumBitmaskType> int packValues(
    final Iterable<T> values)
  {
    var result = 0;
    for (final var constant : values) {
      result |= constant.value();
    }
    return result;
  }

  /**
   * Produce an efficient map of integers to enum constants.
   *
   * @param values The list of enum values
   * @param <T>    The precise type of enum
   *
   * @return The resulting map
   */

  public static <T extends Enum<T> & VulkanEnumIntegerType> Map<Integer, T> map(
    final T[] values)
  {
    final HashMap<Integer, T> m = new HashMap<>(values.length);
    for (final var value : values) {
      final var vv = value.value();
      if (m.containsKey(Integer.valueOf(vv))) {
        throw new IllegalStateException("Duplicate integer value: " + vv);
      }
      m.put(Integer.valueOf(vv), value);
    }
    return m;
  }
}
