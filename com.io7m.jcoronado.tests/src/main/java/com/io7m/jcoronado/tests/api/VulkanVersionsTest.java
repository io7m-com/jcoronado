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

import com.io7m.jcoronado.api.VulkanVersion;
import com.io7m.jcoronado.api.VulkanVersions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public final class VulkanVersionsTest
{
  @Test
  public void testIdentity()
  {
    for (var major = 0; major < 100; ++major) {
      for (var minor = 0; minor < 100; ++minor) {
        for (var patch = 0; patch < 100; ++patch) {
          final var v = VulkanVersions.encode(major, minor, patch);
          final var p = VulkanVersions.decode(v);
          Assertions.assertEquals(p.major(), major);
          Assertions.assertEquals(p.minor(), minor);
          Assertions.assertEquals(p.patch(), patch);
          final var q = VulkanVersions.encode(p);
          Assertions.assertEquals(v, q);
        }
      }
    }
  }

  @Test
  public void testCompare0()
  {
    final var v0 = VulkanVersion.of(1, 0, 0);
    final var v1 = VulkanVersion.of(2, 0, 0);
    Assertions.assertTrue(v0.compareTo(v1) < 0);
    Assertions.assertTrue(v1.compareTo(v0) > 0);
    Assertions.assertTrue(v0.compareTo(v0) == 0);
  }

  @Test
  public void testCompare1()
  {
    final var v0 = VulkanVersion.of(1, 0, 0);
    final var v1 = VulkanVersion.of(1, 1, 0);
    Assertions.assertTrue(v0.compareTo(v1) < 0);
    Assertions.assertTrue(v1.compareTo(v0) > 0);
  }

  @Test
  public void testCompare2()
  {
    final var v0 = VulkanVersion.of(1, 0, 0);
    final var v1 = VulkanVersion.of(1, 0, 1);
    Assertions.assertTrue(v0.compareTo(v1) < 0);
    Assertions.assertTrue(v1.compareTo(v0) > 0);
  }

  @Test
  public void testToHumanString()
  {
    final var v0 = VulkanVersion.of(1, 0, 0);
    final var v1 = VulkanVersion.of(1, 1, 0);
    final var v2 = VulkanVersion.of(1, 1, 1);
    final var v3 = VulkanVersion.of(1, 0, 0);
    final var v4 = VulkanVersion.of(1, 1, 0);
    final var v5 = VulkanVersion.of(1, 1, 1);

    final HashSet<String> ss = new HashSet<>();
    ss.add(v0.toHumanString());
    ss.add(v1.toHumanString());
    ss.add(v2.toHumanString());
    ss.add(v3.toHumanString());
    ss.add(v4.toHumanString());
    ss.add(v5.toHumanString());

    Assertions.assertEquals(3, ss.size());
  }
}
