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

import com.io7m.jcoronado.api.VulkanPipelineDepthStencilStateCreateInfo;
import com.io7m.jcoronado.api.VulkanStencilOpState;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLPipelineDepthStencilStateCreateInfos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineDepthStencilStateCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.io7m.jcoronado.api.VulkanCompareOp.VK_COMPARE_OP_ALWAYS;
import static com.io7m.jcoronado.api.VulkanCompareOp.VK_COMPARE_OP_LESS_OR_EQUAL;
import static com.io7m.jcoronado.api.VulkanCompareOp.VK_COMPARE_OP_NEVER;
import static com.io7m.jcoronado.api.VulkanStencilOp.VK_STENCIL_OP_DECREMENT_AND_CLAMP;
import static com.io7m.jcoronado.api.VulkanStencilOp.VK_STENCIL_OP_INCREMENT_AND_CLAMP;
import static com.io7m.jcoronado.api.VulkanStencilOp.VK_STENCIL_OP_INVERT;
import static com.io7m.jcoronado.api.VulkanStencilOp.VK_STENCIL_OP_KEEP;
import static com.io7m.jcoronado.api.VulkanStencilOp.VK_STENCIL_OP_REPLACE;
import static com.io7m.jcoronado.api.VulkanStencilOp.VK_STENCIL_OP_ZERO;

public final class VulkanLWJGLPipelineDepthStencilStateCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLPipelineDepthStencilStateCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPipelineDepthStencilStateCreateInfo()
  {
    final var front =
      VulkanStencilOpState.of(
        VK_STENCIL_OP_KEEP,
        VK_STENCIL_OP_DECREMENT_AND_CLAMP,
        VK_STENCIL_OP_INCREMENT_AND_CLAMP,
        VK_COMPARE_OP_ALWAYS,
        23,
        24,
        25);

    final var back =
      VulkanStencilOpState.of(
        VK_STENCIL_OP_INVERT,
        VK_STENCIL_OP_REPLACE,
        VK_STENCIL_OP_ZERO,
        VK_COMPARE_OP_NEVER,
        33,
        34,
        35);

    final var info =
      VulkanPipelineDepthStencilStateCreateInfo.builder()
        .setMinDepthBounds(2.4f)
        .setMaxDepthBounds(23.0f)
        .setStencilTestEnable(true)
        .setDepthBoundsTestEnable(true)
        .setDepthCompareOp(VK_COMPARE_OP_LESS_OR_EQUAL)
        .setDepthWriteEnable(true)
        .setDepthTestEnable(true)
        .setFront(front)
        .setBack(back)
        .build();

    final var packed =
      VulkanLWJGLPipelineDepthStencilStateCreateInfos.pack(this.stack, info);

    checkPacked(packed);
    checkPacked(
      VulkanLWJGLPipelineDepthStencilStateCreateInfos.packOptional(this.stack, Optional.of(info)));
    Assertions.assertNull(
      VulkanLWJGLPipelineDepthStencilStateCreateInfos.packOptional(this.stack, Optional.empty()));
  }

  static void checkPacked(
    final VkPipelineDepthStencilStateCreateInfo packed)
  {
    Assertions.assertNotNull(packed, "VkPipelineDepthStencilStateCreateInfo");

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO,
          packed.sType());
      },
      () -> {
        Assertions.assertTrue(packed.depthBoundsTestEnable());
        Assertions.assertTrue(packed.depthTestEnable());
        Assertions.assertTrue(packed.depthWriteEnable());
        Assertions.assertTrue(packed.stencilTestEnable());
        Assertions.assertEquals(
          VK_COMPARE_OP_LESS_OR_EQUAL.value(), packed.depthCompareOp());
        Assertions.assertEquals(2.4f, packed.minDepthBounds());
        Assertions.assertEquals(23.0f, packed.maxDepthBounds());

        Assertions.assertEquals(
          VK_STENCIL_OP_KEEP.value(),
          packed.front().failOp(),
          "front.failOp");
        Assertions.assertEquals(
          VK_STENCIL_OP_DECREMENT_AND_CLAMP.value(),
          packed.front().passOp(),
          "front.passOp");
        Assertions.assertEquals(
          VK_STENCIL_OP_INCREMENT_AND_CLAMP.value(),
          packed.front().depthFailOp(),
          "front.depthFailOp");
        Assertions.assertEquals(
          VK_COMPARE_OP_ALWAYS.value(),
          packed.front().compareOp(),
          "front.compareOp");
        Assertions.assertEquals(23, packed.front().compareMask(), "front.compareMask");
        Assertions.assertEquals(24, packed.front().writeMask(), "front.writeMask");
        Assertions.assertEquals(25, packed.front().reference(), "front.reference");

        Assertions.assertEquals(
          VK_STENCIL_OP_INVERT.value(),
          packed.back().failOp(),
          "back.failOp");
        Assertions.assertEquals(
          VK_STENCIL_OP_REPLACE.value(),
          packed.back().passOp(),
          "back.passOp");
        Assertions.assertEquals(
          VK_STENCIL_OP_ZERO.value(),
          packed.back().depthFailOp(),
          "back.depthFailOp");
        Assertions.assertEquals(
          VK_COMPARE_OP_NEVER.value(),
          packed.back().compareOp(),
          "back.compareOp");
        Assertions.assertEquals(33, packed.back().compareMask(), "back.compareMask");
        Assertions.assertEquals(34, packed.back().writeMask(), "back.writeMask");
        Assertions.assertEquals(35, packed.back().reference(), "back.reference");
      }
    );
  }
}
