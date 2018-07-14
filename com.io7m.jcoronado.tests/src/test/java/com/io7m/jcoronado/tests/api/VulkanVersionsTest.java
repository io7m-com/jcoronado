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

public final class VulkanVersionsTest
{
  @Test
  public void testIdentity()
  {
    for (int major = 0; major < 100; ++major) {
      for (int minor = 0; minor < 100; ++minor) {
        for (int patch = 0; patch < 100; ++patch) {
          final int v = VulkanVersions.encode(major, minor, patch);
          final VulkanVersion p = VulkanVersions.decode(v);
          Assertions.assertEquals(p.major(), major);
          Assertions.assertEquals(p.minor(), minor);
          Assertions.assertEquals(p.patch(), patch);
          final int q = VulkanVersions.encode(p);
          Assertions.assertEquals(v, q);
        }
      }
    }
  }
}
