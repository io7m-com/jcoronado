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

import com.io7m.jcoronado.api.VulkanShaderModuleCreateFlag;
import com.io7m.jcoronado.api.VulkanShaderModuleCreateInfo;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLShaderModules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public final class VulkanLWJGLShaderModulesTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLShaderModulesTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testShaderModuleCreateInfo()
  {
    final var data = ByteBuffer.allocateDirect(256);
    for (var index = 0; index < 256; ++index) {
      data.put(index, (byte) (index & 0xff));
    }

    final var size = 256L;

    final var info =
      VulkanShaderModuleCreateInfo.builder()
        .addFlags(VulkanShaderModuleCreateFlag.values())
        .setData(data)
        .setSize(size)
        .build();

    final var packed =
      VulkanLWJGLShaderModules.pack(this.stack, info);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO,
          packed.sType());
      },

      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },

      () -> {
        Assertions.assertEquals(256L, packed.codeSize());
      },

      () -> {
        Assertions.assertEquals(0L, packed.flags());
      },

      () -> {
        final var pdata = packed.pCode();
        for (var index = 0; index < 256; ++index) {
          Assertions.assertEquals(index, (int) pdata.get(index) & 0xff);
        }
      });
  }
}
