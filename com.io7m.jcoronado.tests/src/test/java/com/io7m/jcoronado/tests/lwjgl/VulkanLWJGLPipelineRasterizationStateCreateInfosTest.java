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

import com.io7m.jcoronado.api.VulkanCullModeFlag;
import com.io7m.jcoronado.api.VulkanFrontFace;
import com.io7m.jcoronado.api.VulkanPipelineRasterizationStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPolygonMode;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLPipelineRasterizationStateCreateInfos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineRasterizationStateCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

public final class VulkanLWJGLPipelineRasterizationStateCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLPipelineRasterizationStateCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPipelineRasterizationStateCreateInfo()
  {
    final VulkanPipelineRasterizationStateCreateInfo info =
      VulkanPipelineRasterizationStateCreateInfo.builder()
        .setDepthBiasSlopeFactor(2.5f)
        .setDepthBiasClamp(3.0f)
        .setDepthBiasConstantFactor(0.5f)
        .setDepthBiasEnable(true)
        .setFrontFace(VulkanFrontFace.VK_FRONT_FACE_CLOCKWISE)
        .setCullMode(Set.of(VulkanCullModeFlag.VK_CULL_MODE_BACK_BIT))
        .setPolygonMode(VulkanPolygonMode.VK_POLYGON_MODE_FILL)
        .setLineWidth(50.0f)
        .setRasterizerDiscardEnable(true)
        .setDepthClampEnable(true)
        .build();

    checkPacked(
      VulkanLWJGLPipelineRasterizationStateCreateInfos.pack(this.stack, info));
    checkPacked(
      VulkanLWJGLPipelineRasterizationStateCreateInfos.packOptional(this.stack, Optional.of(info)));
    Assertions.assertNull(
      VulkanLWJGLPipelineRasterizationStateCreateInfos.packOptional(this.stack, Optional.empty()));
  }

  static void checkPacked(
    final VkPipelineRasterizationStateCreateInfo packed)
  {
    Assertions.assertNotNull(packed, "VkPipelineRasterizationStateCreateInfo");

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO,
          packed.sType());
      },
      () -> {
        Assertions.assertEquals(
          2.5f,
          packed.depthBiasSlopeFactor());
      },
      () -> {
        Assertions.assertEquals(
          3.0f,
          packed.depthBiasClamp());
      },
      () -> {
        Assertions.assertEquals(
          0.5f,
          packed.depthBiasConstantFactor());
      },
      () -> {
        Assertions.assertTrue(packed.depthBiasEnable());
      },
      () -> {
        Assertions.assertTrue(packed.depthClampEnable());
      },
      () -> {
        Assertions.assertTrue(packed.rasterizerDiscardEnable());
      },
      () -> {
        Assertions.assertEquals(
          VulkanFrontFace.VK_FRONT_FACE_CLOCKWISE.value(), packed.frontFace());
      },
      () -> {
        Assertions.assertEquals(
          VulkanPolygonMode.VK_POLYGON_MODE_FILL.value(), packed.polygonMode());
      },
      () -> {
        Assertions.assertEquals(
          50.0f,
          packed.lineWidth());
      },
      () -> {
        Assertions.assertEquals(
          VulkanCullModeFlag.VK_CULL_MODE_BACK_BIT.value(),
          packed.cullMode());
      }
    );
  }
}
