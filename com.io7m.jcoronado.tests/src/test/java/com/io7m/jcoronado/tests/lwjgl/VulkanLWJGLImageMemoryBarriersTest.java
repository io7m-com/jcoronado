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
import com.io7m.jcoronado.api.VulkanImageAspectFlag;
import com.io7m.jcoronado.api.VulkanImageLayout;
import com.io7m.jcoronado.api.VulkanImageMemoryBarrier;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLBuffer;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLImage;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLImageMemoryBarriers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkImageMemoryBarrier;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_LAYOUT_UNDEFINED;

public final class VulkanLWJGLImageMemoryBarriersTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLImageMemoryBarriersTest.class);

  private MemoryStack stack = MemoryStack.create();

  private static void checkPacked(
    final VkImageMemoryBarrier out)
  {
    assertEquals(0b1111_1111_1111_1111_1111, out.srcAccessMask());
    assertEquals(0b1111_1111_1111_1111_1111, out.dstAccessMask());
    assertEquals(23, out.srcQueueFamilyIndex());
    assertEquals(25, out.dstQueueFamilyIndex());
    assertEquals(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL, out.newLayout());
    assertEquals(VK_IMAGE_LAYOUT_UNDEFINED, out.oldLayout());
    assertEquals(0b1111111, out.subresourceRange().aspectMask());
    assertEquals(1, out.subresourceRange().baseMipLevel());
    assertEquals(2, out.subresourceRange().levelCount());
    assertEquals(3, out.subresourceRange().baseArrayLayer());
    assertEquals(4, out.subresourceRange().layerCount());
  }

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @AfterEach
  public void tearDown()
  {
    LOG.debug("tearDown");
    this.stack.pop();
  }

  @Test
  public void testOffsetPack()
    throws VulkanIncompatibleClassException
  {
    final var image =
      Mockito.mock(VulkanLWJGLImage.class);

    final var subresource =
      VulkanImageSubresourceRange.of(
      EnumSet.allOf(VulkanImageAspectFlag.class),
      1,
      2,
      3,
      4
    );

    final var source =
      VulkanImageMemoryBarrier.of(
        EnumSet.allOf(VulkanAccessFlag.class),
        EnumSet.allOf(VulkanAccessFlag.class),
        VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED,
        VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL,
        23,
        25,
        image,
        subresource
      );

    final var out =
      VulkanLWJGLImageMemoryBarriers.pack(this.stack, source);

    checkPacked(out);
  }

  @Test
  public void testOffsetPackList()
    throws VulkanException
  {
    final var image =
      Mockito.mock(VulkanLWJGLImage.class);

    final var subresource =
      VulkanImageSubresourceRange.of(
        EnumSet.allOf(VulkanImageAspectFlag.class),
        1,
        2,
        3,
        4
      );

    final var source =
      VulkanImageMemoryBarrier.of(
        EnumSet.allOf(VulkanAccessFlag.class),
        EnumSet.allOf(VulkanAccessFlag.class),
        VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED,
        VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL,
        23,
        25,
        image,
        subresource
      );

    final var out =
      VulkanLWJGLImageMemoryBarriers.packList(
        this.stack,
        List.of(source, source, source));

    checkPacked(out.get(0));
    checkPacked(out.get(1));
    checkPacked(out.get(2));
  }

  @Test
  public void testOffsetPackListOrNull()
    throws VulkanException
  {
    final var image =
      Mockito.mock(VulkanLWJGLImage.class);

    final var subresource =
      VulkanImageSubresourceRange.of(
        EnumSet.allOf(VulkanImageAspectFlag.class),
        1,
        2,
        3,
        4
      );

    final var source =
      VulkanImageMemoryBarrier.of(
        EnumSet.allOf(VulkanAccessFlag.class),
        EnumSet.allOf(VulkanAccessFlag.class),
        VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED,
        VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL,
        23,
        25,
        image,
        subresource
      );

    final var packed =
      VulkanLWJGLImageMemoryBarriers.packListOrNull(
        this.stack,
        List.of(source, source, source));

    checkPacked(packed.get(0));
    checkPacked(packed.get(1));
    checkPacked(packed.get(2));
  }

  @Test
  public void testOffsetPackListOrNullNull()
    throws VulkanException
  {
    final var packed =
      VulkanLWJGLImageMemoryBarriers.packListOrNull(
        this.stack,
        List.of());

    assertNull(packed);
  }
}
