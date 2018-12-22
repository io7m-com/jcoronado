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

import com.io7m.jcoronado.api.VulkanBlendConstants;
import com.io7m.jcoronado.api.VulkanLogicOp;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendAttachmentState;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendStateCreateInfo;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLPipelineColorBlendStateCreateInfos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineColorBlendStateCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.io7m.jcoronado.api.VulkanBlendFactor.VK_BLEND_FACTOR_CONSTANT_ALPHA;
import static com.io7m.jcoronado.api.VulkanBlendFactor.VK_BLEND_FACTOR_CONSTANT_COLOR;
import static com.io7m.jcoronado.api.VulkanBlendFactor.VK_BLEND_FACTOR_ONE;
import static com.io7m.jcoronado.api.VulkanBlendFactor.VK_BLEND_FACTOR_ONE_MINUS_DST_COLOR;
import static com.io7m.jcoronado.api.VulkanBlendOp.VK_BLEND_OP_ADD;
import static com.io7m.jcoronado.api.VulkanBlendOp.VK_BLEND_OP_HSL_LUMINOSITY_EXT;

public final class VulkanLWJGLPipelineColorBlendStateCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLPipelineColorBlendStateCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPipelineColorBlendStateCreateInfo()
  {
    final var state =
      VulkanPipelineColorBlendAttachmentState.builder()
        .setEnable(true)
        .setAlphaBlendOp(VK_BLEND_OP_ADD)
        .setColorBlendOp(VK_BLEND_OP_HSL_LUMINOSITY_EXT)
        .setDstAlphaBlendFactor(VK_BLEND_FACTOR_ONE)
        .setDstColorBlendFactor(VK_BLEND_FACTOR_ONE_MINUS_DST_COLOR)
        .setSrcAlphaBlendFactor(VK_BLEND_FACTOR_CONSTANT_ALPHA)
        .setSrcColorBlendFactor(VK_BLEND_FACTOR_CONSTANT_COLOR)
        .build();

    final var info =
      VulkanPipelineColorBlendStateCreateInfo.builder()
        .setLogicOp(VulkanLogicOp.VK_LOGIC_OP_AND_INVERTED)
        .setBlendConstants(VulkanBlendConstants.of(1.0f, 2.0f, 3.0f, 4.0f))
        .addAttachments(state)
        .build();

    final var packed =
      VulkanLWJGLPipelineColorBlendStateCreateInfos.pack(this.stack, info);

    checkPacked(packed, true);
    checkPacked(
      VulkanLWJGLPipelineColorBlendStateCreateInfos.packOptional(this.stack, Optional.of(info)),
      true);
    Assertions.assertNull(
      VulkanLWJGLPipelineColorBlendStateCreateInfos.packOptional(this.stack, Optional.empty()));
  }

  static void checkPacked(
    final VkPipelineColorBlendStateCreateInfo packed,
    final boolean logic)
  {
    Assertions.assertNotNull(packed, "VkPipelineColorBlendStateCreateInfo");

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO,
          packed.sType());
      },
      () -> {
        if (logic) {
          Assertions.assertEquals(
            VulkanLogicOp.VK_LOGIC_OP_AND_INVERTED.value(),
            packed.logicOp(),
            "logic op");
        } else {
          Assertions.assertEquals(
            0,
            packed.logicOp(),
            "logic op");
        }

        Assertions.assertEquals(
          Boolean.valueOf(logic),
          Boolean.valueOf(packed.logicOpEnable()),
          "logic");
        Assertions.assertEquals(1.0f, packed.blendConstants().get(0), "constant r");
        Assertions.assertEquals(2.0f, packed.blendConstants().get(1), "constant g");
        Assertions.assertEquals(3.0f, packed.blendConstants().get(2), "constant b");
        Assertions.assertEquals(4.0f, packed.blendConstants().get(3), "constant a");

        final var attach = packed.pAttachments();
        Assertions.assertEquals(VK_BLEND_OP_ADD.value(), attach.alphaBlendOp(), "alphaBlendOp");
        Assertions.assertEquals(
          VK_BLEND_OP_HSL_LUMINOSITY_EXT.value(),
          attach.colorBlendOp(),
          "colorBlendOp");
        Assertions.assertEquals(
          VK_BLEND_FACTOR_ONE.value(),
          attach.dstAlphaBlendFactor(),
          "dstAlphaBlendFactor");
        Assertions.assertEquals(
          VK_BLEND_FACTOR_ONE_MINUS_DST_COLOR.value(),
          attach.dstColorBlendFactor(),
          "dstColorBlendFactor");
        Assertions.assertEquals(
          VK_BLEND_FACTOR_CONSTANT_ALPHA.value(),
          attach.srcAlphaBlendFactor(),
          "srcAlphaBlendFactor");
        Assertions.assertEquals(
          VK_BLEND_FACTOR_CONSTANT_COLOR.value(),
          attach.srcColorBlendFactor(),
          "srcColorBlendFactor");
      }
    );
  }

  @Test
  public void testPipelineColorBlendStateCreateInfoNoLogic()
  {
    final var state =
      VulkanPipelineColorBlendAttachmentState.builder()
        .setEnable(true)
        .setAlphaBlendOp(VK_BLEND_OP_ADD)
        .setColorBlendOp(VK_BLEND_OP_HSL_LUMINOSITY_EXT)
        .setDstAlphaBlendFactor(VK_BLEND_FACTOR_ONE)
        .setDstColorBlendFactor(VK_BLEND_FACTOR_ONE_MINUS_DST_COLOR)
        .setSrcAlphaBlendFactor(VK_BLEND_FACTOR_CONSTANT_ALPHA)
        .setSrcColorBlendFactor(VK_BLEND_FACTOR_CONSTANT_COLOR)
        .build();

    final var info =
      VulkanPipelineColorBlendStateCreateInfo.builder()
        .setLogicOp(Optional.empty())
        .setBlendConstants(VulkanBlendConstants.of(1.0f, 2.0f, 3.0f, 4.0f))
        .addAttachments(state)
        .build();

    final var packed =
      VulkanLWJGLPipelineColorBlendStateCreateInfos.pack(this.stack, info);

    checkPacked(packed, false);
    checkPacked(
      VulkanLWJGLPipelineColorBlendStateCreateInfos.packOptional(this.stack, Optional.of(info)),
      false);
    Assertions.assertNull(
      VulkanLWJGLPipelineColorBlendStateCreateInfos.packOptional(this.stack, Optional.empty()));
  }
}
