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

import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanOffset2D;
import com.io7m.jcoronado.api.VulkanRectangle2D;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLRect2Ds;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkRect2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class VulkanLWJGLVulkanRectangle2DTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLVulkanRectangle2DTest.class);

  private MemoryStack stack = MemoryStack.create();

  private static void checkPacked(
    final VkRect2D out)
  {
    assertEquals(0, out.offset().x());
    assertEquals(0, out.offset().y());
    assertEquals(128, out.extent().width());
    assertEquals(128, out.extent().height());
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
      VulkanRectangle2D.of(
        VulkanOffset2D.of(0, 0),
        VulkanExtent2D.of(128, 128));

    final var out =
      VulkanLWJGLRect2Ds.pack(this.stack, source);

    checkPacked(out);
  }

  @Test
  public void testOffsetPackList()
    throws VulkanException
  {
    final var source =
      VulkanRectangle2D.of(
        VulkanOffset2D.of(0, 0),
        VulkanExtent2D.of(128, 128));

    final var out =
      VulkanLWJGLRect2Ds.packList(
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
      VulkanRectangle2D.of(
        VulkanOffset2D.of(0, 0),
        VulkanExtent2D.of(128, 128));

    final var packed =
      VulkanLWJGLRect2Ds.packListOrNull(
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
      VulkanLWJGLRect2Ds.packListOrNull(
        this.stack,
        List.of());

    assertNull(packed);
  }
}
