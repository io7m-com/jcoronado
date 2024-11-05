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

import com.io7m.jcoronado.api.VulkanDescriptorImageInfo;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLDescriptorImageInfos;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLImageView;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLSampler;
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

import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL;

@ExtendWith(MockitoExtension.class)
public final class VulkanLWJGLDescriptorImageInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLDescriptorImageInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testInfo(
    final @Mock VulkanLWJGLSampler sampler,
    final @Mock VulkanLWJGLImageView image_view)
    throws Exception
  {
    Mockito.when(sampler.handle())
      .thenReturn(100L);
    Mockito.when(image_view.handle())
      .thenReturn(23L);

    final var info =
      VulkanDescriptorImageInfo.builder()
        .setImageLayout(VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
        .setImageView(image_view)
        .setSampler(sampler)
        .build();

    final var packed =
      VulkanLWJGLDescriptorImageInfos.pack(this.stack, info);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(100L, packed.sampler());
      },
      () -> {
        Assertions.assertEquals(
          VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL.value(),
          packed.imageLayout());
      },
      () -> {
        Assertions.assertEquals(23L, packed.imageView());
      }
    );
  }
}
