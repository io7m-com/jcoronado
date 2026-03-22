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

import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanOffset2D;
import com.io7m.jcoronado.api.VulkanPipelineViewportStateCreateInfo;
import com.io7m.jcoronado.api.VulkanRectangle2D;
import com.io7m.jcoronado.api.VulkanViewport;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLPipelineViewportStateCreateInfos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineViewportStateCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class VulkanLWJGLPipelineViewportStateCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLPipelineViewportStateCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  static void checkPacked(
    final VkPipelineViewportStateCreateInfo packed)
  {
    Assertions.assertNotNull(packed, "VkPipelineViewportStateCreateInfo");

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO,
          packed.sType());
      },
      () -> {
        Assertions.assertEquals(
          2,
          packed.viewportCount());

        final var vps = packed.pViewports();
        Assertions.assertEquals(
          101.0f,
          vps.x());
        Assertions.assertEquals(
          102.0f,
          vps.y());
        Assertions.assertEquals(
          103.0f,
          vps.width());
        Assertions.assertEquals(
          104.0f,
          vps.height());
        Assertions.assertEquals(
          0.0f,
          vps.minDepth());
        Assertions.assertEquals(
          1.0f,
          vps.maxDepth());

        vps.position(1);
        Assertions.assertEquals(
          105.0f,
          vps.x());
        Assertions.assertEquals(
          106.0f,
          vps.y());
        Assertions.assertEquals(
          107.0f,
          vps.width());
        Assertions.assertEquals(
          108.0f,
          vps.height());
        Assertions.assertEquals(
          0.0f,
          vps.minDepth());
        Assertions.assertEquals(
          1.0f,
          vps.maxDepth());
      },
      () -> {
        Assertions.assertEquals(
          2,
          packed.scissorCount());

        final var vps = packed.pScissors();
        Assertions.assertEquals(
          5,
          vps.offset().x());
        Assertions.assertEquals(
          17,
          vps.offset().y());
        Assertions.assertEquals(
          23,
          vps.extent().width());
        Assertions.assertEquals(
          34,
          vps.extent().height());

        vps.position(1);
        Assertions.assertEquals(
          6,
          vps.offset().x());
        Assertions.assertEquals(
          18,
          vps.offset().y());
        Assertions.assertEquals(
          25,
          vps.extent().width());
        Assertions.assertEquals(
          37,
          vps.extent().height());
      }
    );
  }

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPipelineViewportStateCreateInfo()
  {
    final var viewport_0 =
      VulkanViewport.builder()
        .setX(101.0f)
        .setY(102.0f)
        .setWidth(103.0f)
        .setHeight(104.0f)
        .setMinDepth(0.0f)
        .setMaxDepth(1.0f)
        .build();

    final var viewport_1 =
      VulkanViewport.builder()
        .setX(105.0f)
        .setY(106.0f)
        .setWidth(107.0f)
        .setHeight(108.0f)
        .setMinDepth(0.0f)
        .setMaxDepth(1.0f)
        .build();

    final var extent_0 =
      VulkanExtent2D.builder()
        .setWidth(23)
        .setHeight(34)
        .build();

    final var extent_1 =
      VulkanExtent2D.builder()
        .setWidth(25)
        .setHeight(37)
        .build();

    final var offset_0 =
      VulkanOffset2D.builder()
        .setX(5)
        .setY(17)
        .build();

    final var offset_1 =
      VulkanOffset2D.builder()
        .setX(6)
        .setY(18)
        .build();

    final var scissor_0 =
      VulkanRectangle2D.builder()
        .setOffset(offset_0)
        .setExtent(extent_0)
        .build();
    final var scissor_1 =
      VulkanRectangle2D.builder()
        .setOffset(offset_1)
        .setExtent(extent_1)
        .build();

    final var info =
      VulkanPipelineViewportStateCreateInfo.builder()
        .addViewports(viewport_0)
        .addViewports(viewport_1)
        .addScissors(scissor_0)
        .addScissors(scissor_1)
        .build();

    final var packed =
      VulkanLWJGLPipelineViewportStateCreateInfos.pack(this.stack, info);

    checkPacked(packed);

    checkPacked(VulkanLWJGLPipelineViewportStateCreateInfos.packOptional(
      this.stack, Optional.of(info)));
    Assertions.assertNull(VulkanLWJGLPipelineViewportStateCreateInfos.packOptional(
      this.stack, Optional.empty()));
  }
}
