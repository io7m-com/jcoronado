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
import com.io7m.jcoronado.api.VulkanViewport;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLViewports;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkViewport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class VulkanLWJGLVulkanViewportsTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLVulkanViewportsTest.class);

  private MemoryStack stack = MemoryStack.create();

  private static void checkPacked(
    final VkViewport out)
  {
    assertEquals(1.0f, out.x());
    assertEquals(2.0f, out.y());
    assertEquals(3.0f, out.width());
    assertEquals(4.0f, out.height());
    assertEquals(0.0f, out.minDepth());
    assertEquals(2.0f, out.maxDepth());
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
  public void testPack()
  {
    final var source =
      VulkanViewport.of(
        1.0f, 2.0f, 3.0f, 4.0f, 0.0f, 2.0f
      );

    final var out =
      VulkanLWJGLViewports.pack(this.stack, source);

    checkPacked(out);
  }

  @Test
  public void testPackList()
    throws VulkanException
  {
    final var source =
      VulkanViewport.of(
        1.0f, 2.0f, 3.0f, 4.0f, 0.0f, 2.0f
      );

    final var out =
      VulkanLWJGLViewports.packList(
        this.stack,
        List.of(source, source, source));

    checkPacked(out.get(0));
    checkPacked(out.get(1));
    checkPacked(out.get(2));
  }

  @Test
  public void testPackListOrNull()
    throws VulkanException
  {
    final var source =
      VulkanViewport.of(
        1.0f, 2.0f, 3.0f, 4.0f, 0.0f, 2.0f
      );

    final var packed =
      VulkanLWJGLViewports.packListOrNull(
        this.stack,
        List.of(source, source, source));

    checkPacked(packed.get(0));
    checkPacked(packed.get(1));
    checkPacked(packed.get(2));
  }

  @Test
  public void testPackListOrNullNull()
    throws VulkanException
  {
    final var packed =
      VulkanLWJGLViewports.packListOrNull(
        this.stack,
        List.of());

    assertNull(packed);
  }
}
