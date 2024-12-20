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
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanMemoryBarrier;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLMemoryBarriers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkMemoryBarrier;
import org.lwjgl.vulkan.VkMemoryBarrier2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class VulkanLWJGLMemoryBarriersTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLMemoryBarriersTest.class);

  private MemoryStack stack = MemoryStack.create();

  private static void checkPacked(
    final VkMemoryBarrier2 out)
  {
    assertEquals(0b11100000000000000011111111111111111L, out.srcAccessMask());
    assertEquals(0b11100000000000000011111111111111111L, out.dstAccessMask());
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
  {
    final var source =
      VulkanMemoryBarrier.of(
        EnumSet.allOf(VulkanAccessFlag.class),
        EnumSet.allOf(VulkanAccessFlag.class)
      );

    final var out =
      VulkanLWJGLMemoryBarriers.pack(this.stack, source);

    checkPacked(out);
  }

  @Test
  public void testOffsetPackList()
    throws VulkanException
  {
    final var source =
      VulkanMemoryBarrier.of(
        EnumSet.allOf(VulkanAccessFlag.class),
        EnumSet.allOf(VulkanAccessFlag.class)
      );

    final var out =
      VulkanLWJGLMemoryBarriers.packList(
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
    final var source =
      VulkanMemoryBarrier.of(
        EnumSet.allOf(VulkanAccessFlag.class),
        EnumSet.allOf(VulkanAccessFlag.class)
      );

    final var packed =
      VulkanLWJGLMemoryBarriers.packListOrNull(
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
      VulkanLWJGLMemoryBarriers.packListOrNull(
        this.stack,
        List.of());

    assertNull(packed);
  }
}
