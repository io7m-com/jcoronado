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

import com.io7m.jcoronado.api.VulkanSpecializationMap;
import com.io7m.jcoronado.api.VulkanSpecializationMapEntry;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLSpecializationInfos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Optional;

public final class VulkanLWJGLSpecializationInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLSpecializationInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testSpecializationInfo()
  {
    final var entry_0 =
      VulkanSpecializationMapEntry.of(0, 23L, 36L);
    final var entry_1 =
      VulkanSpecializationMapEntry.of(1, 12L, 23L);
    final var entry_2 =
      VulkanSpecializationMapEntry.of(2, 7L, 6L);

    final var map =
      VulkanSpecializationMap.builder()
        .setData(ByteBuffer.allocateDirect(100))
        .addEntries(entry_0)
        .addEntries(entry_1)
        .addEntries(entry_2)
        .build();

    final var packed =
      VulkanLWJGLSpecializationInfos.pack(this.stack, map);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(100, packed.pData().capacity());
      },
      () -> {
        Assertions.assertEquals(3, packed.mapEntryCount());
      },
      () -> {
        final var pe = packed.pMapEntries().position(0);
        Assertions.assertEquals(23, pe.offset());
        Assertions.assertEquals(36L, pe.size());
        Assertions.assertEquals(0, pe.constantID());
      },
      () -> {
        final var pe = packed.pMapEntries().position(1);
        Assertions.assertEquals(12, pe.offset());
        Assertions.assertEquals(23L, pe.size());
        Assertions.assertEquals(1, pe.constantID());
      },
      () -> {
        final var pe = packed.pMapEntries().position(2);
        Assertions.assertEquals(7, pe.offset());
        Assertions.assertEquals(6L, pe.size());
        Assertions.assertEquals(2, pe.constantID());
      }
    );
  }

  @Test
  public void testSpecializationInfoNull()
  {
    final var packed =
      VulkanLWJGLSpecializationInfos.packOptional(this.stack, Optional.empty());

    Assertions.assertEquals(null, packed);
  }
}
