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

import com.io7m.jcoronado.api.VulkanBufferImageCopy;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanImageAspectFlag;
import com.io7m.jcoronado.api.VulkanImageSubresourceLayers;
import com.io7m.jcoronado.api.VulkanOffset3D;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLBufferImageCopy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkBufferImageCopy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class VulkanLWJGLBufferImageCopiesTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLBufferImageCopiesTest.class);

  private MemoryStack stack = MemoryStack.create();

  private static void checkPacked(
    final VkBufferImageCopy out)
  {
    assertEquals(0L, out.bufferOffset());
    assertEquals(200, out.bufferRowLength());
    assertEquals(300, out.bufferImageHeight());

    assertEquals(100, out.imageOffset().x());
    assertEquals(200, out.imageOffset().y());
    assertEquals(300, out.imageOffset().z());

    assertEquals(1000, out.imageExtent().width());
    assertEquals(2000, out.imageExtent().height());
    assertEquals(3000, out.imageExtent().depth());

    final var srcLayers = out.imageSubresource();
    assertEquals(0b1111111, srcLayers.aspectMask());
    assertEquals(1, srcLayers.mipLevel());
    assertEquals(2, srcLayers.baseArrayLayer());
    assertEquals(3, srcLayers.layerCount());
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
      VulkanBufferImageCopy.of(
        0L,
        200,
        300,
        VulkanImageSubresourceLayers.of(
          EnumSet.allOf(VulkanImageAspectFlag.class),
          1,
          2,
          3
        ),
        VulkanOffset3D.of(100, 200, 300),
        VulkanExtent3D.of(1000, 2000, 3000)
      );

    final var out =
      VulkanLWJGLBufferImageCopy.pack(this.stack, source);

    checkPacked(out);
  }

  @Test
  public void testOffsetPackList()
    throws VulkanException
  {
    final var source =
      VulkanBufferImageCopy.of(
        0L,
        200,
        300,
        VulkanImageSubresourceLayers.of(
          EnumSet.allOf(VulkanImageAspectFlag.class),
          1,
          2,
          3
        ),
        VulkanOffset3D.of(100, 200, 300),
        VulkanExtent3D.of(1000, 2000, 3000)
      );

    final var out =
      VulkanLWJGLBufferImageCopy.packList(
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
      VulkanBufferImageCopy.of(
        0L,
        200,
        300,
        VulkanImageSubresourceLayers.of(
          EnumSet.allOf(VulkanImageAspectFlag.class),
          1,
          2,
          3
        ),
        VulkanOffset3D.of(100, 200, 300),
        VulkanExtent3D.of(1000, 2000, 3000)
      );

    final var packed =
      VulkanLWJGLBufferImageCopy.packListOrNull(
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
