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

import com.io7m.jcoronado.api.VulkanAccessFlag;
import com.io7m.jcoronado.api.VulkanBufferMemoryBarrier;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanMemoryBarrier;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLBuffer;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLBufferMemoryBarriers;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLMemoryBarriers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkBufferMemoryBarrier;
import org.lwjgl.vulkan.VkMemoryBarrier;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class VulkanLWJGLBufferMemoryBarriersTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLBufferMemoryBarriersTest.class);

  private MemoryStack stack = MemoryStack.create();

  private static void checkPacked(
    final VkBufferMemoryBarrier out)
  {
    assertEquals(0b1111_1111_1111_1111_1111, out.srcAccessMask());
    assertEquals(0b1111_1111_1111_1111_1111, out.dstAccessMask());
    assertEquals(23, out.srcQueueFamilyIndex());
    assertEquals(25, out.dstQueueFamilyIndex());
    assertEquals(100L, out.offset());
    assertEquals(200L, out.size());
  }

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @AfterEach
  public void tearDown()
  {
    LOG.debug("tearDown");
    this.stack.pop();
  }

  @Test
  public void testOffsetPack()
    throws VulkanIncompatibleClassException
  {
    final var buffer =
      Mockito.mock(VulkanLWJGLBuffer.class);

    final var source =
      VulkanBufferMemoryBarrier.of(
        EnumSet.allOf(VulkanAccessFlag.class),
        EnumSet.allOf(VulkanAccessFlag.class),
        23,
        25,
        buffer,
        100L,
        200L
      );

    final var out =
      VulkanLWJGLBufferMemoryBarriers.pack(this.stack, source);

    checkPacked(out);
  }

  @Test
  public void testOffsetPackList()
    throws VulkanException
  {
    final var buffer =
      Mockito.mock(VulkanLWJGLBuffer.class);

    final var source =
      VulkanBufferMemoryBarrier.of(
        EnumSet.allOf(VulkanAccessFlag.class),
        EnumSet.allOf(VulkanAccessFlag.class),
        23,
        25,
        buffer,
        100L,
        200L
      );

    final var out =
      VulkanLWJGLBufferMemoryBarriers.packList(
        this.stack,
        List.of(source, source, source));

    checkPacked(out.get(0));
    checkPacked(out.get(1));
    checkPacked(out.get(2));
  }

  @Test
  public void testOffsetPackListOrNull()
    throws VulkanException
  {
    final var buffer =
      Mockito.mock(VulkanLWJGLBuffer.class);

    final var source =
      VulkanBufferMemoryBarrier.of(
        EnumSet.allOf(VulkanAccessFlag.class),
        EnumSet.allOf(VulkanAccessFlag.class),
        23,
        25,
        buffer,
        100L,
        200L
      );

    final var packed =
      VulkanLWJGLBufferMemoryBarriers.packListOrNull(
        this.stack,
        List.of(source, source, source));

    checkPacked(packed.get(0));
    checkPacked(packed.get(1));
    checkPacked(packed.get(2));
  }

  @Test
  public void testOffsetPackListOrNullNull()
    throws VulkanException
  {
    final var packed =
      VulkanLWJGLBufferMemoryBarriers.packListOrNull(
        this.stack,
        List.of());

    assertNull(packed);
  }
}
