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


package com.io7m.jcoronado.tests.api;

import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeaturesFunctions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class VulkanDeviceFeaturesTest
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanDeviceFeaturesTest.class);

  @Test
  public void testMapOf()
    throws Exception
  {
    final var f =
      VulkanPhysicalDeviceFeaturesFunctions.random();
    final var m =
      VulkanPhysicalDeviceFeaturesFunctions.mapOf(f);

    final var f10 =
      VulkanPhysicalDeviceFeaturesFunctions.mapOf10(f.features10());
    final var f11 =
      VulkanPhysicalDeviceFeaturesFunctions.mapOf11(f.features11());
    final var f12 =
      VulkanPhysicalDeviceFeaturesFunctions.mapOf12(f.features12());
    final var f13 =
      VulkanPhysicalDeviceFeaturesFunctions.mapOf13(f.features13());

    assertEquals(
      f10.size() + f11.size() + f12.size() + f13.size(),
      m.size()
    );

    for (final var e : m.entrySet()) {
      LOG.debug("{} : {}", e.getKey(), e.getValue());
    }
  }

  @Test
  public void testOr10()
    throws Exception
  {
    final var a =
      VulkanPhysicalDeviceFeaturesFunctions.random10();
    final var b =
      VulkanPhysicalDeviceFeaturesFunctions.random10();

    compareBooleanMethods(
      a,
      b,
      VulkanPhysicalDeviceFeaturesFunctions.or(a, b)
    );
  }

  @Test
  public void testOr11()
    throws Exception
  {
    final var a =
      VulkanPhysicalDeviceFeaturesFunctions.random11();
    final var b =
      VulkanPhysicalDeviceFeaturesFunctions.random11();

    compareBooleanMethods(
      a,
      b,
      VulkanPhysicalDeviceFeaturesFunctions.or(a, b)
    );
  }

  @Test
  public void testOr12()
    throws Exception
  {
    final var a =
      VulkanPhysicalDeviceFeaturesFunctions.random12();
    final var b =
      VulkanPhysicalDeviceFeaturesFunctions.random12();

    compareBooleanMethods(
      a,
      b,
      VulkanPhysicalDeviceFeaturesFunctions.or(a, b)
    );
  }

  @Test
  public void testOr13()
    throws Exception
  {
    final var a =
      VulkanPhysicalDeviceFeaturesFunctions.random13();
    final var b =
      VulkanPhysicalDeviceFeaturesFunctions.random13();

    compareBooleanMethods(
      a,
      b,
      VulkanPhysicalDeviceFeaturesFunctions.or(a, b)
    );
  }

  @Test
  public void testOrAll()
    throws Exception
  {
    final var a =
      VulkanPhysicalDeviceFeaturesFunctions.random();
    final var b =
      VulkanPhysicalDeviceFeaturesFunctions.random();
    final var r =
      VulkanPhysicalDeviceFeaturesFunctions.or(a, b);

    compareBooleanMethods(a.features10(), b.features10(), r.features10());
    compareBooleanMethods(a.features11(), b.features11(), r.features11());
    compareBooleanMethods(a.features12(), b.features12(), r.features12());
    compareBooleanMethods(a.features13(), b.features13(), r.features13());
  }

  private static <T> void compareBooleanMethods(
    final T a,
    final T b,
    final T r)
    throws IllegalAccessException, InvocationTargetException
  {
    final var methods =
      Stream.of(a.getClass().getMethods())
        .filter(m -> !"equals".equals(m.getName()))
        .filter(m -> !"hashCode".equals(m.getName()))
        .filter(m -> m.getParameterCount() == 1)
        .filter(m -> m.getReturnType().equals(boolean.class))
        .toList();

    for (final var method : methods) {
      final var x = (boolean) method.invoke(a);
      final var y = (boolean) method.invoke(b);
      final var rr = (boolean) method.invoke(r);

      assertEquals(
        Boolean.valueOf(rr),
        Boolean.valueOf(x || y),
        "(%s) %s == %s || %s".formatted(
          method.getName(),
          Boolean.valueOf(rr),
          Boolean.valueOf(x),
          Boolean.valueOf(y)
        )
      );
    }
  }
}
