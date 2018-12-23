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
import com.io7m.jcoronado.api.VulkanFramebufferCreateInfo;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLFramebufferCreateInfos;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLImageView;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLRenderPass;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class VulkanLWJGLFramebufferCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLFramebufferCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testFramebufferCreateInfo(
    final @Mocked VulkanLWJGLRenderPass render_pass,
    final @Mocked VulkanLWJGLImageView image_view)
    throws VulkanException
  {
    new Expectations()
    {{
      render_pass.handle();
      this.result = Long.valueOf(0x200L);
    }};

    final var info =
      VulkanFramebufferCreateInfo.builder()
        .setRenderPass(render_pass)
        .setLayers(1)
        .setWidth(640)
        .setHeight(480)
        .addAttachments(image_view)
        .build();

    final var packed =
      VulkanLWJGLFramebufferCreateInfos.pack(this.stack, info, List.of(image_view), render_pass);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO,
          packed.sType());
      },
      () -> {
        Assertions.assertEquals(
          640,
          packed.width());
      },
      () -> {
        Assertions.assertEquals(
          480,
          packed.height());
      },
      () -> {
        Assertions.assertEquals(
          render_pass.handle(),
          packed.renderPass());
      },
      () -> {
        Assertions.assertEquals(
          1,
          packed.attachmentCount());
        Assertions.assertEquals(
          image_view.handle(),
          packed.pAttachments().get(0));
      }
    );
  }
}
