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
import com.io7m.jcoronado.api.VulkanImageBlit;
import com.io7m.jcoronado.api.VulkanImageSubresourceLayers;
import com.io7m.jcoronado.api.VulkanOffset3D;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLImageBlits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkImageBlit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class VulkanLWJGLImageBlitsTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLImageBlitsTest.class);

  private MemoryStack stack = MemoryStack.create();

  private static void checkPacked(
    final VkImageBlit out)
  {
    final var srcOffset0 =
      out.srcOffsets(0);
    final var srcOffset1 =
      out.srcOffsets(1);
    final var dstOffset0 =
      out.dstOffsets(0);
    final var dstOffset1 =
      out.dstOffsets(1);

    assertEquals(100, srcOffset0.x());
    assertEquals(200, srcOffset0.y());
    assertEquals(300, srcOffset0.z());
    assertEquals(400, srcOffset1.x());
    assertEquals(500, srcOffset1.y());
    assertEquals(600, srcOffset1.z());

    assertEquals(1100, dstOffset0.x());
    assertEquals(1200, dstOffset0.y());
    assertEquals(1300, dstOffset0.z());
    assertEquals(1400, dstOffset1.x());
    assertEquals(1500, dstOffset1.y());
    assertEquals(1600, dstOffset1.z());

    final var srcLayers = out.srcSubresource();
    final var dstLayers = out.dstSubresource();

    assertEquals(0b1111111, srcLayers.aspectMask());
    assertEquals(1, srcLayers.mipLevel());
    assertEquals(2, srcLayers.baseArrayLayer());
    assertEquals(3, srcLayers.layerCount());

    assertEquals(0b1111111, dstLayers.aspectMask());
    assertEquals(10, dstLayers.mipLevel());
    assertEquals(20, dstLayers.baseArrayLayer());
    assertEquals(30, dstLayers.layerCount());
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
      VulkanImageBlit.of(
        VulkanImageSubresourceLayers.of(
          EnumSet.allOf(VulkanImageAspectFlag.class),
          1,
          2,
          3
        ),
        VulkanOffset3D.of(100, 200, 300),
        VulkanOffset3D.of(400, 500, 600),
        VulkanImageSubresourceLayers.of(
          EnumSet.allOf(VulkanImageAspectFlag.class),
          10,
          20,
          30
        ),
        VulkanOffset3D.of(1100, 1200, 1300),
        VulkanOffset3D.of(1400, 1500, 1600)
      );

    final var out =
      VulkanLWJGLImageBlits.pack(this.stack, source);

    checkPacked(out);
  }

  @Test
  public void testOffsetPackList()
    throws VulkanException
  {
    final var source =
      VulkanImageBlit.of(
        VulkanImageSubresourceLayers.of(
          EnumSet.allOf(VulkanImageAspectFlag.class),
          1,
          2,
          3
        ),
        VulkanOffset3D.of(100, 200, 300),
        VulkanOffset3D.of(400, 500, 600),
        VulkanImageSubresourceLayers.of(
          EnumSet.allOf(VulkanImageAspectFlag.class),
          10,
          20,
          30
        ),
        VulkanOffset3D.of(1100, 1200, 1300),
        VulkanOffset3D.of(1400, 1500, 1600)
      );

    final var out =
      VulkanLWJGLImageBlits.packList(
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
      VulkanImageBlit.of(
        VulkanImageSubresourceLayers.of(
          EnumSet.allOf(VulkanImageAspectFlag.class),
          1,
          2,
          3
        ),
        VulkanOffset3D.of(100, 200, 300),
        VulkanOffset3D.of(400, 500, 600),
        VulkanImageSubresourceLayers.of(
          EnumSet.allOf(VulkanImageAspectFlag.class),
          10,
          20,
          30
        ),
        VulkanOffset3D.of(1100, 1200, 1300),
        VulkanOffset3D.of(1400, 1500, 1600)
      );

    final var packed =
      VulkanLWJGLImageBlits.packListOrNull(
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
      VulkanLWJGLImageBlits.packListOrNull(
        this.stack,
        List.of());

    assertNull(packed);
  }
}
