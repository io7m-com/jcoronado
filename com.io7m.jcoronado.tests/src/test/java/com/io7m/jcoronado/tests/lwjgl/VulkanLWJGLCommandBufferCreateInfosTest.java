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

import com.io7m.jcoronado.api.VulkanCommandBufferCreateInfo;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLCommandBufferCreateInfos;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLCommandPool;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.io7m.jcoronado.api.VulkanCommandBufferLevel.*;

public final class VulkanLWJGLCommandBufferCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLCommandBufferCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testCommandPoolCreateInfo(
    final @Mocked VulkanLWJGLCommandPool pool)
  {
    new Expectations() {{
      pool.handle();
      this.result = Long.valueOf(0x300L);
    }};

    final var info =
      VulkanCommandBufferCreateInfo.builder()
        .setPool(pool)
        .setLevel(VK_COMMAND_BUFFER_LEVEL_PRIMARY)
        .setCount(23)
        .build();

    final var packed =
      VulkanLWJGLCommandBufferCreateInfos.pack(this.stack, info, pool);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO,
          packed.sType());
      },
      () -> {
        Assertions.assertEquals(
          23,
          packed.commandBufferCount());
      },
      () -> {
        Assertions.assertEquals(
          VK_COMMAND_BUFFER_LEVEL_PRIMARY.value(),
          packed.level());
      },
      () -> {
        Assertions.assertEquals(
          0x300L,
          packed.commandPool());
      }
    );
  }
}
