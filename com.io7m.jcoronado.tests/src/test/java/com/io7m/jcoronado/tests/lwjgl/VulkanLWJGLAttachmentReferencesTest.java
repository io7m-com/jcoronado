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

import com.io7m.jcoronado.api.VulkanAttachmentReference;
import com.io7m.jcoronado.api.VulkanImageLayout;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLAttachmentReferences;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkAttachmentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class VulkanLWJGLAttachmentReferencesTest
{
  private static final Logger LOG = LoggerFactory.getLogger(VulkanLWJGLAttachmentReferencesTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPackReferences()
  {
    final VulkanAttachmentReference reference_0 =
      VulkanAttachmentReference.builder()
        .setLayout(VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
        .setAttachment(0)
        .build();

    final VulkanAttachmentReference reference_1 =
      VulkanAttachmentReference.builder()
        .setLayout(VulkanImageLayout.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL)
        .setAttachment(1)
        .build();

    final VulkanAttachmentReference reference_2 =
      VulkanAttachmentReference.builder()
        .setLayout(VulkanImageLayout.VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL)
        .setAttachment(2)
        .build();

    final List<VulkanAttachmentReference> references =
      List.of(reference_0, reference_1, reference_2);

    final VkAttachmentReference.Buffer packed =
      VulkanLWJGLAttachmentReferences.packAttachmentReferences(this.stack, references);

    for (int index = 0; index < 3; ++index) {
      final int f_index = index;

      Assertions.assertAll(
        () -> {
          packed.position(f_index);
          Assertions.assertEquals(f_index, packed.attachment());
        },

        () -> {
          packed.position(f_index);
          switch (f_index) {
            case 0: {
              Assertions.assertEquals(
                VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL.value(),
                packed.layout());
              break;
            }
            case 1: {
              Assertions.assertEquals(
                VulkanImageLayout.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL.value(),
                packed.layout());
              break;
            }
            case 2: {
              Assertions.assertEquals(
                VulkanImageLayout.VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL.value(),
                packed.layout());
              break;
            }
            default: {
              throw new AssertionError("Unreachable");
            }
          }
        });
    }


  }
}
