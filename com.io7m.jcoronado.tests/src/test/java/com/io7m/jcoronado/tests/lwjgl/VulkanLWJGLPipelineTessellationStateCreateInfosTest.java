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

import com.io7m.jcoronado.api.VulkanPipelineTessellationStateCreateInfo;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLPipelineTessellationStateCreateInfos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineTessellationStateCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class VulkanLWJGLPipelineTessellationStateCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLPipelineTessellationStateCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  static void checkPacked(
    final VkPipelineTessellationStateCreateInfo packed)
  {
    Assertions.assertNotNull(packed, "VkPipelineTessellationStateCreateInfo");

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO,
          packed.sType());
      },
      () -> {
        Assertions.assertEquals(
          3,
          packed.patchControlPoints());
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
  public void testPipelineVertexInputStateCreateInfo()
  {
    final var info =
      VulkanPipelineTessellationStateCreateInfo.builder()
        .setPatchControlPoints(3)
        .build();

    final var packed =
      VulkanLWJGLPipelineTessellationStateCreateInfos.pack(this.stack, info);

    checkPacked(packed);
    checkPacked(
      VulkanLWJGLPipelineTessellationStateCreateInfos.packOptional(
        this.stack,
        Optional.of(info)));
    Assertions.assertNull(
      VulkanLWJGLPipelineTessellationStateCreateInfos.packOptional(
        this.stack,
        Optional.empty()));
  }
}
