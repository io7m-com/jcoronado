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
import com.io7m.jcoronado.api.VulkanImageAspectFlag;
import com.io7m.jcoronado.api.VulkanImageSubresource;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLBufferImageCopy;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLImageSubresources;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkImageSubresource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class VulkanLWJGLImageSubresourcesTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLImageSubresourcesTest.class);

  private MemoryStack stack = MemoryStack.create();

  private static void checkPacked(
    final VkImageSubresource out)
  {
    assertEquals(0b1111111, out.aspectMask());
    assertEquals(1, out.mipLevel());
    assertEquals(2, out.arrayLayer());
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
    throws VulkanException
  {
    final var source =
      VulkanImageSubresource.of(
        EnumSet.allOf(VulkanImageAspectFlag.class),
        1,
        2
      );

    final var out =
      VulkanLWJGLImageSubresources.pack(this.stack, source);

    checkPacked(out);
  }

  @Test
  public void testOffsetPackList()
    throws VulkanException
  {
    final var source =
      VulkanImageSubresource.of(
        EnumSet.allOf(VulkanImageAspectFlag.class),
        1,
        2
      );

    final var out =
      VulkanLWJGLImageSubresources.packList(
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
      VulkanImageSubresource.of(
        EnumSet.allOf(VulkanImageAspectFlag.class),
        1,
        2
      );

    final var packed =
      VulkanLWJGLImageSubresources.packListOrNull(
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
      VulkanLWJGLBufferImageCopy.packListOrNull(
        this.stack,
        List.of());

    assertNull(packed);
  }
}
