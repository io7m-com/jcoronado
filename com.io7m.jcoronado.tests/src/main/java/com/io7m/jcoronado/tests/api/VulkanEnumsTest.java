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

package com.io7m.jcoronado.tests.api;

import com.io7m.jcoronado.api.VulkanEnumBitmaskType;
import com.io7m.jcoronado.api.VulkanEnumIntegerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public final class VulkanEnumsTest
{
  /**
   * Check that enum integer values are unique.
   */

  @Test
  public void testEnumIntegerValues()
  {
    final var reflections = new Reflections("com.io7m.jcoronado");

    final var enums =
      reflections.getSubTypesOf(VulkanEnumIntegerType.class);

    final Collection<Executable> executables = new ArrayList<>();

    for (final var c : enums) {
      final List<VulkanEnumIntegerType> constants = List.of(c.getEnumConstants());

      final var numbers = new HashSet<Integer>();
      for (final var constant : constants) {
        numbers.add(Integer.valueOf(constant.value()));
      }

      executables.add(() -> {
        Assertions.assertEquals(
          constants.size(),
          numbers.size(),
          new StringBuilder(32)
            .append("Enum values must be unique for type ")
            .append(c.getCanonicalName())
            .toString());
      });
    }

    Assertions.assertAll(executables);
  }

  /**
   * Check that enum integer values are unique.
   */

  @Test
  public void testEnumBitmaskValues()
  {
    final var reflections = new Reflections("com.io7m.jcoronado");

    final var enums =
      reflections.getSubTypesOf(VulkanEnumBitmaskType.class);

    final Collection<Executable> executables = new ArrayList<>();

    for (final var c : enums) {
      final List<VulkanEnumBitmaskType> constants = List.of(c.getEnumConstants());

      final var numbers = new HashSet<Integer>();
      for (final var constant : constants) {
        numbers.add(Integer.valueOf(constant.value()));
      }

      executables.add(() -> {
        Assertions.assertEquals(
          constants.size(),
          numbers.size(),
          new StringBuilder(32)
            .append("Enum values must be unique for type ")
            .append(c.getCanonicalName())
            .toString());
      });
    }

    Assertions.assertAll(executables);
  }
}
