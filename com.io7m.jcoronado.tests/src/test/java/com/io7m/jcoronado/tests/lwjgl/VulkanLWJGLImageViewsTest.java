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

import com.io7m.jcoronado.api.VulkanComponentMapping;
import com.io7m.jcoronado.api.VulkanComponentSwizzle;
import com.io7m.jcoronado.api.VulkanImageAspectFlag;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageViewCreateFlag;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLImage;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLImageViews;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_B8G8R8A8_UNORM;
import static com.io7m.jcoronado.api.VulkanImageViewKind.VK_IMAGE_VIEW_TYPE_2D;

public final class VulkanLWJGLImageViewsTest
{
  private static final Logger LOG = LoggerFactory.getLogger(VulkanLWJGLImageViewsTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testImageViewCreateInfo(
    final @Mocked VulkanLWJGLImage image)
  {
    new Expectations()
    {{
      image.handle();
      this.result = Long.valueOf(0x200L);
    }};

    final var components =
      VulkanComponentMapping.builder()
        .setA(VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_A)
        .setR(VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_R)
        .setG(VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_G)
        .setB(VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_B)
        .build();

    final var subresource_range =
      VulkanImageSubresourceRange.builder()
        .addFlags(VulkanImageAspectFlag.values())
        .setBaseArrayLayer(3)
        .setBaseMipLevel(2)
        .setLayerCount(4)
        .setLevelCount(5)
        .build();

    final var info =
      VulkanImageViewCreateInfo.builder()
        .addFlags(VulkanImageViewCreateFlag.values())
        .setComponents(components)
        .setFormat(VK_FORMAT_B8G8R8A8_UNORM)
        .setImage(image)
        .setViewType(VK_IMAGE_VIEW_TYPE_2D)
        .setSubresourceRange(subresource_range)
        .build();

    final var packed =
      VulkanLWJGLImageViews.packImageViewCreateInfo(this.stack, info, image);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },

      () -> {
        Assertions.assertEquals(VK10.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO, packed.sType());
      },

      () -> {
        Assertions.assertEquals(0, packed.flags());
      },

      () -> {
        Assertions.assertEquals(0x200L, packed.image());
      },

      () -> {
        Assertions.assertEquals(VK_FORMAT_B8G8R8A8_UNORM.value(), packed.format());
      },

      () -> {
        Assertions.assertEquals(VK_IMAGE_VIEW_TYPE_2D.value(), packed.viewType());
      },

      () -> {
        final var vk_components = packed.components();
        Assertions.assertEquals(VK10.VK_COMPONENT_SWIZZLE_A, vk_components.a());
        Assertions.assertEquals(VK10.VK_COMPONENT_SWIZZLE_R, vk_components.r());
        Assertions.assertEquals(VK10.VK_COMPONENT_SWIZZLE_G, vk_components.g());
        Assertions.assertEquals(VK10.VK_COMPONENT_SWIZZLE_B, vk_components.b());
      },

      () -> {
        final var vk_range = packed.subresourceRange();
        Assertions.assertEquals(0b1111111, vk_range.aspectMask());
        Assertions.assertEquals(3, vk_range.baseArrayLayer());
        Assertions.assertEquals(2, vk_range.baseMipLevel());
        Assertions.assertEquals(4, vk_range.layerCount());
        Assertions.assertEquals(5, vk_range.levelCount());
      }
    );
  }
}
