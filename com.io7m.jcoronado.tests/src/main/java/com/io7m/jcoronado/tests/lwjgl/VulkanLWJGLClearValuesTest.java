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

package com.io7m.jcoronado.tests.lwjgl;

import com.io7m.jcoronado.api.VulkanClearValueColorFloatingPoint;
import com.io7m.jcoronado.api.VulkanClearValueColorIntegerSigned;
import com.io7m.jcoronado.api.VulkanClearValueColorIntegerUnsigned;
import com.io7m.jcoronado.api.VulkanClearValueDepthStencil;
import com.io7m.jcoronado.api.VulkanClearValueType;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLClearValues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class VulkanLWJGLClearValuesTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLClearValuesTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testClearColorValueCreateInfoSigned()
  {
    final List<VulkanClearValueType> values =
      List.of(
        VulkanClearValueColorIntegerSigned.of(0, 0, 0, 0),
        VulkanClearValueColorIntegerSigned.of(1, 1, 1, 1),
        VulkanClearValueColorIntegerSigned.of(2, 2, 2, 2),
        VulkanClearValueColorIntegerSigned.of(3, 3, 3, 3));

    final var packed_structures =
      VulkanLWJGLClearValues.packAll(this.stack, values);

    for (var index = 0; index < 4; ++index) {
      final var value = packed_structures.get(index);

      final var ci = value.color().int32();
      final var r = ci.get(0);
      final var g = ci.get(1);
      final var b = ci.get(2);
      final var a = ci.get(3);
      LOG.debug(
        "{} r 0x{}",
        Integer.valueOf(index),
        Integer.toUnsignedString(r, 16));
      LOG.debug(
        "{} g 0x{}",
        Integer.valueOf(index),
        Integer.toUnsignedString(g, 16));
      LOG.debug(
        "{} b 0x{}",
        Integer.valueOf(index),
        Integer.toUnsignedString(b, 16));
      LOG.debug(
        "{} a 0x{}",
        Integer.valueOf(index),
        Integer.toUnsignedString(a, 16));

      Assertions.assertEquals(index, r, "r-" + index);
      Assertions.assertEquals(index, g, "g-" + index);
      Assertions.assertEquals(index, b, "b-" + index);
      Assertions.assertEquals(index, a, "a-" + index);
    }
  }

  @Test
  public void testClearColorValueCreateInfoUnsigned()
  {
    final List<VulkanClearValueType> values =
      List.of(
        VulkanClearValueColorIntegerUnsigned.of(0, 0, 0, 0),
        VulkanClearValueColorIntegerUnsigned.of(1, 1, 1, 1),
        VulkanClearValueColorIntegerUnsigned.of(2, 2, 2, 2),
        VulkanClearValueColorIntegerUnsigned.of(3, 3, 3, 3));

    final var packed_structures =
      VulkanLWJGLClearValues.packAll(this.stack, values);

    for (var index = 0; index < 4; ++index) {
      final var value = packed_structures.get(index);

      final var ci = value.color().uint32();
      final var r = ci.get(0);
      final var g = ci.get(1);
      final var b = ci.get(2);
      final var a = ci.get(3);
      LOG.debug(
        "{} r 0x{}",
        Integer.valueOf(index),
        Integer.toUnsignedString(r, 16));
      LOG.debug(
        "{} g 0x{}",
        Integer.valueOf(index),
        Integer.toUnsignedString(g, 16));
      LOG.debug(
        "{} b 0x{}",
        Integer.valueOf(index),
        Integer.toUnsignedString(b, 16));
      LOG.debug(
        "{} a 0x{}",
        Integer.valueOf(index),
        Integer.toUnsignedString(a, 16));

      Assertions.assertEquals(index, r, "r-" + index);
      Assertions.assertEquals(index, g, "g-" + index);
      Assertions.assertEquals(index, b, "b-" + index);
      Assertions.assertEquals(index, a, "a-" + index);
    }
  }

  @Test
  public void testClearColorValueCreateInfoFloat()
  {
    final List<VulkanClearValueType> values =
      List.of(
        VulkanClearValueColorFloatingPoint.of(0, 0, 0, 0),
        VulkanClearValueColorFloatingPoint.of(1, 1, 1, 1),
        VulkanClearValueColorFloatingPoint.of(2, 2, 2, 2),
        VulkanClearValueColorFloatingPoint.of(3, 3, 3, 3));

    final var packed_structures =
      VulkanLWJGLClearValues.packAll(this.stack, values);

    for (var index = 0; index < 4; ++index) {
      final var value = packed_structures.get(index);

      final var ci = value.color().float32();
      final var r = ci.get(0);
      final var g = ci.get(1);
      final var b = ci.get(2);
      final var a = ci.get(3);
      LOG.debug("{} r {}", Integer.valueOf(index), Float.valueOf(r));
      LOG.debug("{} g {}", Integer.valueOf(index), Float.valueOf(g));
      LOG.debug("{} b {}", Integer.valueOf(index), Float.valueOf(b));
      LOG.debug("{} a {}", Integer.valueOf(index), Float.valueOf(a));

      Assertions.assertEquals(index, r, "r-" + index);
      Assertions.assertEquals(index, g, "g-" + index);
      Assertions.assertEquals(index, b, "b-" + index);
      Assertions.assertEquals(index, a, "a-" + index);
    }
  }

  @Test
  public void testClearColorValueCreateInfoDepthStencil()
  {
    final List<VulkanClearValueType> values =
      List.of(
        VulkanClearValueDepthStencil.of(0, 0),
        VulkanClearValueDepthStencil.of(1, 1),
        VulkanClearValueDepthStencil.of(2, 2),
        VulkanClearValueDepthStencil.of(3, 3));

    final var packed_structures =
      VulkanLWJGLClearValues.packAll(this.stack, values);

    for (var index = 0; index < 4; ++index) {
      final var value = packed_structures.get(index);

      final var ci = value.depthStencil();
      LOG.debug(
        "{} depth   {}",
        Integer.valueOf(index),
        Float.valueOf(ci.depth()));
      LOG.debug(
        "{} stencil {}",
        Integer.valueOf(index),
        Integer.valueOf(ci.stencil()));

      Assertions.assertEquals(index, ci.depth(), "depth-" + index);
      Assertions.assertEquals(index, ci.stencil(), "stencil-" + index);
    }
  }
}
