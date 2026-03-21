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
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanImageAspectFlag;
import com.io7m.jcoronado.api.VulkanImageCopy;
import com.io7m.jcoronado.api.VulkanImageSubresourceLayers;
import com.io7m.jcoronado.api.VulkanOffset3D;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLImageCopies;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkImageCopy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class VulkanLWJGLImageCopiesTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLImageCopiesTest.class);

  private MemoryStack stack = MemoryStack.create();

  private static void checkPacked(
    final VkImageCopy out)
  {
    final var srcOffset0 = out.srcOffset();
    assertEquals(100, srcOffset0.x());
    assertEquals(200, srcOffset0.y());
    assertEquals(300, srcOffset0.z());

    final var dstOffset0 = out.dstOffset();
    assertEquals(400, dstOffset0.x());
    assertEquals(500, dstOffset0.y());
    assertEquals(600, dstOffset0.z());

    final var extent = out.extent();
    assertEquals(1000, extent.width());
    assertEquals(2000, extent.height());
    assertEquals(3000, extent.depth());

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
    final var layers0 =
      VulkanImageSubresourceLayers.builder()
        .setAspectMask(EnumSet.allOf(VulkanImageAspectFlag.class))
        .setBaseArrayLayer(2)
        .setLayerCount(3)
        .setMipLevel(1)
        .build();

    final var layers1 =
      VulkanImageSubresourceLayers.builder()
        .setAspectMask(EnumSet.allOf(VulkanImageAspectFlag.class))
        .setBaseArrayLayer(20)
        .setLayerCount(30)
        .setMipLevel(10)
        .build();

    final var extent =
      VulkanExtent3D.builder()
        .setWidth(1000)
        .setHeight(2000)
        .setDepth(3000)
        .build();

    final var sourceOff =
      VulkanOffset3D.builder()
        .setX(100)
        .setY(200)
        .setZ(300)
        .build();

    final var targetOff =
      VulkanOffset3D.builder()
        .setX(400)
        .setY(500)
        .setZ(600)
        .build();

    final var source =
      VulkanImageCopy.builder()
        .setSourceSubresource(layers0)
        .setTargetSubresource(layers1)
        .setExtent(extent)
        .setSourceOffset(sourceOff)
        .setTargetOffset(targetOff)
        .build();

    final var out =
      VulkanLWJGLImageCopies.pack(this.stack, source);

    checkPacked(out);
  }

  @Test
  public void testOffsetPackList()
    throws VulkanException
  {
    final var layers0 =
      VulkanImageSubresourceLayers.builder()
        .setAspectMask(EnumSet.allOf(VulkanImageAspectFlag.class))
        .setBaseArrayLayer(2)
        .setLayerCount(3)
        .setMipLevel(1)
        .build();

    final var layers1 =
      VulkanImageSubresourceLayers.builder()
        .setAspectMask(EnumSet.allOf(VulkanImageAspectFlag.class))
        .setBaseArrayLayer(20)
        .setLayerCount(30)
        .setMipLevel(10)
        .build();

    final var extent =
      VulkanExtent3D.builder()
        .setWidth(1000)
        .setHeight(2000)
        .setDepth(3000)
        .build();

    final var sourceOff =
      VulkanOffset3D.builder()
        .setX(100)
        .setY(200)
        .setZ(300)
        .build();

    final var targetOff =
      VulkanOffset3D.builder()
        .setX(400)
        .setY(500)
        .setZ(600)
        .build();

    final var source =
      VulkanImageCopy.builder()
        .setSourceSubresource(layers0)
        .setTargetSubresource(layers1)
        .setExtent(extent)
        .setSourceOffset(sourceOff)
        .setTargetOffset(targetOff)
        .build();

    final var out =
      VulkanLWJGLImageCopies.packList(
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
    final var layers0 =
      VulkanImageSubresourceLayers.builder()
        .setAspectMask(EnumSet.allOf(VulkanImageAspectFlag.class))
        .setBaseArrayLayer(2)
        .setLayerCount(3)
        .setMipLevel(1)
        .build();

    final var layers1 =
      VulkanImageSubresourceLayers.builder()
        .setAspectMask(EnumSet.allOf(VulkanImageAspectFlag.class))
        .setBaseArrayLayer(20)
        .setLayerCount(30)
        .setMipLevel(10)
        .build();

    final var extent =
      VulkanExtent3D.builder()
        .setWidth(1000)
        .setHeight(2000)
        .setDepth(3000)
        .build();

    final var sourceOff =
      VulkanOffset3D.builder()
        .setX(100)
        .setY(200)
        .setZ(300)
        .build();

    final var targetOff =
      VulkanOffset3D.builder()
        .setX(400)
        .setY(500)
        .setZ(600)
        .build();

    final var source =
      VulkanImageCopy.builder()
        .setSourceSubresource(layers0)
        .setTargetSubresource(layers1)
        .setExtent(extent)
        .setSourceOffset(sourceOff)
        .setTargetOffset(targetOff)
        .build();

    final var packed =
      VulkanLWJGLImageCopies.packListOrNull(
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
      VulkanLWJGLImageCopies.packListOrNull(
        this.stack,
        List.of());

    assertNull(packed);
  }
}
