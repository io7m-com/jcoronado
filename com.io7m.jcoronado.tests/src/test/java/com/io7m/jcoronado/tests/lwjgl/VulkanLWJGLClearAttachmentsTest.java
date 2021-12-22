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

import com.io7m.jcoronado.api.VulkanClearAttachment;
import com.io7m.jcoronado.api.VulkanClearValueColorFloatingPoint;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanImageAspectFlag;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLClearAttachments;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkClearAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class VulkanLWJGLClearAttachmentsTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLClearAttachmentsTest.class);

  private MemoryStack stack = MemoryStack.create();

  private static void checkPacked(
    final VkClearAttachment vkAttach)
  {
    assertEquals(23, vkAttach.colorAttachment());
    assertEquals(VK10.VK_IMAGE_ASPECT_COLOR_BIT, vkAttach.aspectMask());
    assertEquals(0.5f, vkAttach.clearValue().color().float32().get(0));
    assertEquals(0.6f, vkAttach.clearValue().color().float32().get(1));
    assertEquals(0.7f, vkAttach.clearValue().color().float32().get(2));
    assertEquals(0.8f, vkAttach.clearValue().color().float32().get(3));
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
  public void testClearAttachmentsPackInfo()
  {
    final var vkAttach =
      VkClearAttachment.malloc(this.stack);

    final var source =
      VulkanClearAttachment.builder()
        .setClearValue(VulkanClearValueColorFloatingPoint.of(
          0.5f,
          0.6f,
          0.7f,
          0.8f))
        .setAspectMask(Set.of(VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT))
        .setColorAttachment(23)
        .build();

    VulkanLWJGLClearAttachments.packInto(this.stack, source, vkAttach);

    checkPacked(vkAttach);
  }

  @Test
  public void testClearAttachmentsPack()
  {
    final var source =
      VulkanClearAttachment.builder()
        .setClearValue(VulkanClearValueColorFloatingPoint.of(
          0.5f,
          0.6f,
          0.7f,
          0.8f))
        .setAspectMask(Set.of(VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT))
        .setColorAttachment(23)
        .build();

    final var vkAttach =
      VulkanLWJGLClearAttachments.pack(this.stack, source);

    checkPacked(vkAttach);
  }

  @Test
  public void testClearAttachmentsPackList()
    throws VulkanException
  {
    final var source =
      VulkanClearAttachment.builder()
        .setClearValue(VulkanClearValueColorFloatingPoint.of(
          0.5f,
          0.6f,
          0.7f,
          0.8f))
        .setAspectMask(Set.of(VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT))
        .setColorAttachment(23)
        .build();

    final var packed =
      VulkanLWJGLClearAttachments.packList(
        this.stack,
        List.of(source, source, source));

    checkPacked(packed.get(0));
    checkPacked(packed.get(1));
    checkPacked(packed.get(2));
  }

  @Test
  public void testClearAttachmentsPackListOrNull()
    throws VulkanException
  {
    final var source =
      VulkanClearAttachment.builder()
        .setClearValue(VulkanClearValueColorFloatingPoint.of(
          0.5f,
          0.6f,
          0.7f,
          0.8f))
        .setAspectMask(Set.of(VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT))
        .setColorAttachment(23)
        .build();

    final var packed =
      VulkanLWJGLClearAttachments.packListOrNull(
        this.stack,
        List.of(source, source, source));

    checkPacked(packed.get(0));
    checkPacked(packed.get(1));
    checkPacked(packed.get(2));
  }

  @Test
  public void testClearAttachmentsPackListOrNullNull()
    throws VulkanException
  {
    final var packed =
      VulkanLWJGLClearAttachments.packListOrNull(
        this.stack,
        List.of());

    assertNull(packed);
  }
}
