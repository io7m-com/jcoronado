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

import com.io7m.jcoronado.api.VulkanPipelineMultisampleStateCreateInfoType;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.immutables.value.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class VulkanEqualsTest
{
  private static final Logger LOG = LoggerFactory.getLogger(VulkanEqualsTest.class);

  private static void checkTypeException(
    final Class<?> type,
    final Class<?> subtype,
    final AssertionError e)
  {
    if (Objects.equals(
      type,
      VulkanPipelineMultisampleStateCreateInfoType.class)) {
      if (e.getMessage().contains("sampleMask")) {
        LOG.error(
          "{}: {}: applying exception for comparison failure: ",
          type,
          subtype,
          e);
        return;
      }
    }

    throw e;
  }

  private static boolean isAttribute(final Method m)
  {
    if (Objects.equals(m.getDeclaringClass(), Object.class)) {
      return false;
    }

    if (m.isDefault()) {
      final var ignore_names =
        Set.of(
          "compareTo",
          "colorType",
          "checkPreconditions",
          "findSuitableMemoryType",
          "toHumanString",
          "type");

      return !ignore_names.contains(m.getName());
    }

    return (m.getModifiers() & Modifier.ABSTRACT) == Modifier.ABSTRACT;
  }

  @Test
  public void testEqualsReflectively()
  {
    final var reflections = new Reflections("com.io7m.jcoronado");
    final var types = reflections.getTypesAnnotatedWith(Value.Immutable.class);

    Assertions.assertTrue(types.size() > 30, "At least 30 subtypes must exist");

    final Collection<Executable> executables = new ArrayList<>();

    for (final var type : types) {
      if (!type.isInterface()) {
        continue;
      }

      final var subtypes =
        reflections.getSubTypesOf(type);

      final var names =
        Stream.of(type.getMethods())
          .filter(VulkanEqualsTest::isAttribute)
          .map(Method::getName)
          .collect(Collectors.toList());

      for (final var subtype : subtypes) {
        final Executable task = () -> {
          final var name_array = new String[names.size()];
          names.toArray(name_array);

          LOG.debug(
            "checking: {}: names: {}",
            subtype.getCanonicalName(),
            names);

          try {
            EqualsVerifier.forClass(subtype)
              .withNonnullFields(name_array)
              .verify();
          } catch (final AssertionError e) {
            checkTypeException(type, subtype, e);
          }
        };

        executables.add(task);
      }
    }

    Assertions.assertAll(executables);
  }
}
