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

import com.io7m.jcoronado.api.VulkanDescriptorSetAllocateInfo;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLDescriptorPool;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLDescriptorSetAllocateInfos;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLDescriptorSetLayout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lwjgl.system.MemoryStack;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
public final class VulkanLWJGLDescriptorSetAllocateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLDescriptorSetAllocateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testInfo(
    final @Mock VulkanLWJGLDescriptorPool pool,
    final @Mock VulkanLWJGLDescriptorSetLayout layout_0,
    final @Mock VulkanLWJGLDescriptorSetLayout layout_1)
    throws Exception
  {
    Mockito.when(pool.handle())
      .thenReturn(100L);
    Mockito.when(layout_0.handle())
      .thenReturn(23L);
    Mockito.when(layout_1.handle())
      .thenReturn(64L);

    final var info =
      VulkanDescriptorSetAllocateInfo.builder()
        .setDescriptorPool(pool)
        .addSetLayouts(layout_0)
        .addSetLayouts(layout_1)
        .build();

    final var packed =
      VulkanLWJGLDescriptorSetAllocateInfos.pack(this.stack, info);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(100L, packed.descriptorPool());
      },
      () -> {
        Assertions.assertEquals(23L, packed.pSetLayouts().get(0));
      },
      () -> {
        Assertions.assertEquals(64L, packed.pSetLayouts().get(1));
      }
    );
  }
}
