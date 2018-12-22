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

import com.io7m.jcoronado.api.VulkanAccessFlag;
import com.io7m.jcoronado.api.VulkanDependencyFlag;
import com.io7m.jcoronado.api.VulkanPipelineStageFlag;
import com.io7m.jcoronado.api.VulkanSubpassDependency;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLSubpasses;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VulkanLWJGLSubpassesTest
{
  private static final Logger LOG = LoggerFactory.getLogger(VulkanLWJGLSubpassesTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPackSubpassDependency()
  {
    final var dependency =
      VulkanSubpassDependency.builder()
        .addDependencyFlags(VulkanDependencyFlag.values())
        .addDstAccessMask(VulkanAccessFlag.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT)
        .addDstStageMask(VulkanPipelineStageFlag.VK_PIPELINE_STAGE_BOTTOM_OF_PIPE_BIT)
        .addSrcAccessMask(VulkanAccessFlag.VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT)
        .addSrcStageMask(VulkanPipelineStageFlag.VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT)
        .setSrcSubpass(23)
        .setDstSubpass(24)
        .build();

    final var packed =
      VulkanLWJGLSubpasses.packSubpassDependency(this.stack, dependency);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0b111, packed.dependencyFlags());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT, packed.dstAccessMask());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_PIPELINE_STAGE_BOTTOM_OF_PIPE_BIT, packed.dstStageMask());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT, packed.srcAccessMask());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT, packed.srcStageMask());
      },
      () -> {
        Assertions.assertEquals(23, packed.srcSubpass());
      },
      () -> {
        Assertions.assertEquals(24, packed.dstSubpass());
      }
    );
  }
}
