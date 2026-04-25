/*
 * Copyright © 2026 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import com.io7m.jcoronado.api.VulkanClearValueColorFloatingPoint;
import com.io7m.jcoronado.api.VulkanClearValueColorIntegerSigned;
import com.io7m.jcoronado.api.VulkanClearValueColorIntegerUnsigned;
import org.junit.jupiter.api.Test;

import java.nio.ByteOrder;

import static java.nio.ByteOrder.BIG_ENDIAN;
import static java.nio.ByteOrder.LITTLE_ENDIAN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class VulkanClearValueTest
{
  @Test
  public void testColorSigned()
  {
    final var color =
      VulkanClearValueColorIntegerSigned.builder()
        .setRed(0x7f)
        .setGreen(0x40)
        .setBlue(0x20)
        .setAlpha(0x10)
        .build();

    final var cb =
      color.asFillBufferInteger(BIG_ENDIAN);
    final var cl =
      color.asFillBufferInteger(LITTLE_ENDIAN);

    assertEquals("7f402010", Integer.toUnsignedString(cb, 16));
    assertEquals("1020407f", Integer.toUnsignedString(cl, 16));

  }

  @Test
  public void testColorUnsigned()
  {
    final var color =
      VulkanClearValueColorIntegerUnsigned.builder()
        .setRed(0xff)
        .setGreen(0x40)
        .setBlue(0x20)
        .setAlpha(0x10)
        .build();

    final var cb =
      color.asFillBufferInteger(BIG_ENDIAN);
    final var cl =
      color.asFillBufferInteger(LITTLE_ENDIAN);

    assertEquals("ff402010", Integer.toUnsignedString(cb, 16));
    assertEquals("102040ff", Integer.toUnsignedString(cl, 16));
  }

  @Test
  public void testColorFloat()
  {
    final var color =
      VulkanClearValueColorFloatingPoint.builder()
        .setRed(1.0f)
        .setGreen(0.5f)
        .setBlue(0.25f)
        .setAlpha(0.125f)
        .build();

    final var cb =
      color.asFillBufferInteger(BIG_ENDIAN);
    final var cl =
      color.asFillBufferInteger(LITTLE_ENDIAN);

    assertEquals("ff7f3f1f", Integer.toUnsignedString(cb, 16));
    assertEquals("1f3f7fff", Integer.toUnsignedString(cl, 16));
  }
}
