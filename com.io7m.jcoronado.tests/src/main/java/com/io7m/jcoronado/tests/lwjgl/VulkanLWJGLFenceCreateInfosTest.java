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

import com.io7m.jcoronado.api.VulkanFenceCreateFlag;
import com.io7m.jcoronado.api.VulkanFenceCreateInfo;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLFenceCreateInfos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class VulkanLWJGLFenceCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLFenceCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testFenceCreateInfo()
  {
    final var info =
      VulkanFenceCreateInfo.builder()
        .addAllFlags(List.of(VulkanFenceCreateFlag.values()))
        .build();

    final var packed =
      VulkanLWJGLFenceCreateInfos.pack(this.stack, info);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO,
          packed.sType());
      },
      () -> {
        Assertions.assertEquals(
          1,
          packed.flags());
      },
      () -> {
        Assertions.assertEquals(
          0L,
          packed.pNext());
      }
    );
  }
}
