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

import com.io7m.jcoronado.api.VulkanPipelineMultisampleStateCreateInfo;
import com.io7m.jcoronado.api.VulkanSampleCountFlag;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLPipelineMultisampleStateCreateInfos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineMultisampleStateCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class VulkanLWJGLPipelineMultisampleStateCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLPipelineMultisampleStateCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPipelineMultisampleStateCreateInfo()
  {
    final VulkanPipelineMultisampleStateCreateInfo info =
      VulkanPipelineMultisampleStateCreateInfo.builder()
        .setAlphaToCoverageEnable(true)
        .setMinSampleShading(0.5f)
        .setRasterizationSamples(VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT)
        .setSampleShadingEnable(true)
        .setAlphaToOneEnable(true)
        .setSampleMask(new int[]{1, 2, 3})
        .build();

    final VkPipelineMultisampleStateCreateInfo packed =
      VulkanLWJGLPipelineMultisampleStateCreateInfos.pack(this.stack, info);

    checkPacked(packed);
    checkPacked(
      VulkanLWJGLPipelineMultisampleStateCreateInfos.packOptional(this.stack, Optional.of(info)));
    Assertions.assertNull(
      VulkanLWJGLPipelineMultisampleStateCreateInfos.packOptional(this.stack, Optional.empty()));
  }

  static void checkPacked(
    final VkPipelineMultisampleStateCreateInfo packed)
  {
    Assertions.assertNotNull(packed, "VkPipelineMultisampleStateCreateInfo");

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO,
          packed.sType());
      },
      () -> {
        Assertions.assertEquals(
          0.5f,
          packed.minSampleShading());
        Assertions.assertEquals(
          VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT.value(),
          packed.rasterizationSamples());
        Assertions.assertTrue(packed.sampleShadingEnable());
        Assertions.assertTrue(packed.alphaToOneEnable());
        Assertions.assertTrue(packed.alphaToCoverageEnable());
      }
    );
  }
}
