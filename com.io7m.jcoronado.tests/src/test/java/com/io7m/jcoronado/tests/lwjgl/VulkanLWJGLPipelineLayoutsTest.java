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

import com.io7m.jcoronado.api.VulkanPipelineLayoutCreateFlag;
import com.io7m.jcoronado.api.VulkanPipelineLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanPushConstantRange;
import com.io7m.jcoronado.api.VulkanShaderStageFlag;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLDescriptorSetLayout;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLPipelineLayouts;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VulkanLWJGLPipelineLayoutsTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLPipelineLayoutsTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPipelineLayoutCreateInfo(
    final @Mocked VulkanLWJGLDescriptorSetLayout layout_0,
    final @Mocked VulkanLWJGLDescriptorSetLayout layout_1,
    final @Mocked VulkanLWJGLDescriptorSetLayout layout_2)
    throws Exception
  {
    new Expectations()
    {{
      layout_0.handle();
      this.result = Long.valueOf(0x100L);
      layout_1.handle();
      this.result = Long.valueOf(0x101L);
      layout_2.handle();
      this.result = Long.valueOf(0x102L);
    }};

    final var push_range_0 =
      VulkanPushConstantRange.builder()
        .addStageFlags(VulkanShaderStageFlag.VK_SHADER_STAGE_VERTEX_BIT)
        .setOffset(0)
        .setSize(3)
        .build();

    final var push_range_1 =
      VulkanPushConstantRange.builder()
        .addStageFlags(VulkanShaderStageFlag.VK_SHADER_STAGE_FRAGMENT_BIT)
        .setOffset(2)
        .setSize(5)
        .build();

    final var push_range_2 =
      VulkanPushConstantRange.builder()
        .addStageFlags(VulkanShaderStageFlag.values())
        .setOffset(5)
        .setSize(10)
        .build();

    final var info =
      VulkanPipelineLayoutCreateInfo.builder()
        .addFlags(VulkanPipelineLayoutCreateFlag.values())
        .addPushConstantRanges(push_range_0)
        .addPushConstantRanges(push_range_1)
        .addPushConstantRanges(push_range_2)
        .addSetLayouts(layout_0)
        .addSetLayouts(layout_1)
        .addSetLayouts(layout_2)
        .build();

    final var packed =
      VulkanLWJGLPipelineLayouts.packPipelineLayoutCreateInfo(this.stack, info);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },

      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO,
          packed.sType());
      },

      () -> {
        Assertions.assertEquals(
          3,
          packed.pushConstantRangeCount(),
          "range count");
      },

      () -> {
        final var r = packed.pPushConstantRanges().position(0);
        Assertions.assertEquals(3, r.size(), "size");
        Assertions.assertEquals(0, r.offset(), "offset");
      },

      () -> {
        final var r = packed.pPushConstantRanges().position(1);
        Assertions.assertEquals(5, r.size(), "size");
        Assertions.assertEquals(2, r.offset(), "offset");
      },

      () -> {
        final var r = packed.pPushConstantRanges().position(2);
        Assertions.assertEquals(10, r.size(), "size");
        Assertions.assertEquals(5, r.offset(), "offset");
      },

      () -> {
        Assertions.assertEquals(3, packed.setLayoutCount(), "layout count");
      },

      () -> {
        Assertions.assertEquals(0b0, packed.flags());
      }
    );
  }

  @Test
  public void testPipelineLayoutCreateInfoNoLayouts()
    throws Exception
  {
    final var push_range_0 =
      VulkanPushConstantRange.builder()
        .addStageFlags(VulkanShaderStageFlag.VK_SHADER_STAGE_VERTEX_BIT)
        .setOffset(0)
        .setSize(3)
        .build();

    final var push_range_1 =
      VulkanPushConstantRange.builder()
        .addStageFlags(VulkanShaderStageFlag.VK_SHADER_STAGE_FRAGMENT_BIT)
        .setOffset(2)
        .setSize(5)
        .build();

    final var push_range_2 =
      VulkanPushConstantRange.builder()
        .addStageFlags(VulkanShaderStageFlag.values())
        .setOffset(5)
        .setSize(10)
        .build();

    final var info =
      VulkanPipelineLayoutCreateInfo.builder()
        .addFlags(VulkanPipelineLayoutCreateFlag.values())
        .addPushConstantRanges(push_range_0)
        .addPushConstantRanges(push_range_1)
        .addPushConstantRanges(push_range_2)
        .build();

    final var packed =
      VulkanLWJGLPipelineLayouts.packPipelineLayoutCreateInfo(this.stack, info);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },

      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO,
          packed.sType());
      },

      () -> {
        Assertions.assertEquals(
          3,
          packed.pushConstantRangeCount(),
          "range count");
      },

      () -> {
        final var r = packed.pPushConstantRanges().position(0);
        Assertions.assertEquals(3, r.size(), "size");
        Assertions.assertEquals(0, r.offset(), "offset");
      },

      () -> {
        final var r = packed.pPushConstantRanges().position(1);
        Assertions.assertEquals(5, r.size(), "size");
        Assertions.assertEquals(2, r.offset(), "offset");
      },

      () -> {
        final var r = packed.pPushConstantRanges().position(2);
        Assertions.assertEquals(10, r.size(), "size");
        Assertions.assertEquals(5, r.offset(), "offset");
      },

      () -> {
        Assertions.assertEquals(0, packed.setLayoutCount(), "layout count");
      },

      () -> {
        Assertions.assertEquals(0b0, packed.flags());
      }
    );
  }
}
