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

import com.io7m.jcoronado.api.VulkanAttachmentDescription;
import com.io7m.jcoronado.api.VulkanAttachmentDescriptionFlag;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLAttachmentDescriptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_CLEAR;
import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_STORE;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_B8G8R8A8_UNORM;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT;

public final class VulkanLWJGLAttachmentDescriptionsTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLAttachmentDescriptionsTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPackAttachments()
  {
    final var description_0 =
      VulkanAttachmentDescription.builder()
        .setFinalLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL)
        .setInitialLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
        .setStencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
        .setStencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
        .setStoreOp(VK_ATTACHMENT_STORE_OP_STORE)
        .setLoadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
        .setSamples(VK_SAMPLE_COUNT_1_BIT)
        .setFormat(VK_FORMAT_B8G8R8A8_UNORM)
        .addFlags(VulkanAttachmentDescriptionFlag.values())
        .build();

    final var description_1 =
      VulkanAttachmentDescription.builder()
        .setFinalLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL)
        .setInitialLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
        .setStencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
        .setStencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
        .setStoreOp(VK_ATTACHMENT_STORE_OP_STORE)
        .setLoadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
        .setSamples(VK_SAMPLE_COUNT_1_BIT)
        .setFormat(VK_FORMAT_B8G8R8A8_UNORM)
        .addFlags(VulkanAttachmentDescriptionFlag.values())
        .build();

    final var description_2 =
      VulkanAttachmentDescription.builder()
        .setFinalLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL)
        .setInitialLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
        .setStencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
        .setStencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
        .setStoreOp(VK_ATTACHMENT_STORE_OP_STORE)
        .setLoadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
        .setSamples(VK_SAMPLE_COUNT_1_BIT)
        .setFormat(VK_FORMAT_B8G8R8A8_UNORM)
        .addFlags(VulkanAttachmentDescriptionFlag.values())
        .build();

    final var attachments =
      List.of(
        description_0,
        description_1,
        description_2);

    final var packed =
      VulkanLWJGLAttachmentDescriptions.packAttachments(
        this.stack,
        attachments);

    for (var index = 0; index < 3; ++index) {
      packed.position(index);

      Assertions.assertAll(
        () -> {
          Assertions.assertEquals(
            VK10.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, packed.finalLayout());
        },
        () -> {
          Assertions.assertEquals(
            VK10.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL,
            packed.initialLayout());
        },
        () -> {
          Assertions.assertEquals(
            VK10.VK_ATTACHMENT_STORE_OP_DONT_CARE, packed.stencilStoreOp());
        },
        () -> {
          Assertions.assertEquals(
            VK10.VK_ATTACHMENT_LOAD_OP_DONT_CARE, packed.stencilLoadOp());
        },
        () -> {
          Assertions.assertEquals(
            VK10.VK_ATTACHMENT_STORE_OP_STORE, packed.storeOp());
        },
        () -> {
          Assertions.assertEquals(
            VK10.VK_ATTACHMENT_LOAD_OP_CLEAR, packed.loadOp());
        },
        () -> {
          Assertions.assertEquals(
            VK10.VK_SAMPLE_COUNT_1_BIT, packed.samples());
        },
        () -> {
          Assertions.assertEquals(
            VK10.VK_FORMAT_B8G8R8A8_UNORM, packed.format());
        },
        () -> {
          Assertions.assertEquals(
            0b0000_0001, packed.flags());
        }
      );
    }
  }

  @Test
  public void testPackAttachment()
  {
    final var description_0 =
      VulkanAttachmentDescription.builder()
        .setFinalLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL)
        .setInitialLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
        .setStencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
        .setStencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
        .setStoreOp(VK_ATTACHMENT_STORE_OP_STORE)
        .setLoadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
        .setSamples(VK_SAMPLE_COUNT_1_BIT)
        .setFormat(VK_FORMAT_B8G8R8A8_UNORM)
        .addFlags(VulkanAttachmentDescriptionFlag.values())
        .build();

    final var packed =
      VulkanLWJGLAttachmentDescriptions.packAttachment(
        this.stack,
        description_0);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(
          VK10.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, packed.finalLayout());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL,
          packed.initialLayout());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_ATTACHMENT_STORE_OP_DONT_CARE, packed.stencilStoreOp());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_ATTACHMENT_LOAD_OP_DONT_CARE, packed.stencilLoadOp());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_ATTACHMENT_STORE_OP_STORE, packed.storeOp());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_ATTACHMENT_LOAD_OP_CLEAR, packed.loadOp());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_SAMPLE_COUNT_1_BIT, packed.samples());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_FORMAT_B8G8R8A8_UNORM, packed.format());
      },
      () -> {
        Assertions.assertEquals(
          0b0000_0001, packed.flags());
      }
    );
  }
}
