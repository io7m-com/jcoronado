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
import com.io7m.jcoronado.api.VulkanAttachmentDescription;
import com.io7m.jcoronado.api.VulkanAttachmentDescriptionFlag;
import com.io7m.jcoronado.api.VulkanAttachmentReference;
import com.io7m.jcoronado.api.VulkanDependencyFlag;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanPipelineStageFlag;
import com.io7m.jcoronado.api.VulkanRenderPassCreateFlag;
import com.io7m.jcoronado.api.VulkanRenderPassCreateInfo;
import com.io7m.jcoronado.api.VulkanSubpassDependency;
import com.io7m.jcoronado.api.VulkanSubpassDescription;
import com.io7m.jcoronado.api.VulkanSubpassDescriptionFlag;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLRenderPasses;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_CLEAR;
import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_STORE;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_B8G8R8A8_UNORM;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanPipelineBindPoint.VK_PIPELINE_BIND_POINT_GRAPHICS;
import static com.io7m.jcoronado.api.VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT;

public final class VulkanLWJGLRenderPassesTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLRenderPassesTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPackRenderPassCreateInfo()
    throws VulkanException
  {
    final var subpass_dependency =
      VulkanSubpassDependency.builder()
        .addDependencyFlags(VulkanDependencyFlag.values())
        .addDstAccessMask(VulkanAccessFlag.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT)
        .addDstStageMask(VulkanPipelineStageFlag.VK_PIPELINE_STAGE_BOTTOM_OF_PIPE_BIT)
        .addSrcAccessMask(VulkanAccessFlag.VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT)
        .addSrcStageMask(VulkanPipelineStageFlag.VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT)
        .setSrcSubpass(23)
        .setDstSubpass(24)
        .build();

    final var attachment_0 =
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

    final var reference_0 =
      VulkanAttachmentReference.builder()
        .setAttachment(0)
        .setLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
        .build();

    final var reference_1 =
      VulkanAttachmentReference.builder()
        .setAttachment(1)
        .setLayout(VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL)
        .build();

    final var reference_2 =
      VulkanAttachmentReference.builder()
        .setAttachment(2)
        .setLayout(VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL)
        .build();

    final var reference_3 =
      VulkanAttachmentReference.builder()
        .setAttachment(3)
        .setLayout(VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
        .build();

    final var subpass_0 =
      VulkanSubpassDescription.builder()
        .addColorAttachments(reference_0)
        .addFlags(VulkanSubpassDescriptionFlag.values())
        .addInputAttachments(reference_3)
        .addPreserveAttachments(23)
        .addResolveAttachments(reference_1)
        .setDepthStencilAttachment(reference_2)
        .setPipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS)
        .build();

    final var info =
      VulkanRenderPassCreateInfo.builder()
        .addSubpasses(subpass_0)
        .addAttachments(attachment_0)
        .addFlags(VulkanRenderPassCreateFlag.values())
        .addDependencies(subpass_dependency)
        .build();

    final var packed =
      VulkanLWJGLRenderPasses.packRenderPassCreateInfo(this.stack, info);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO,
          packed.sType());
      },
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        final var b = packed.pSubpasses();
        Assertions.assertEquals(1, packed.subpassCount());

        Assertions.assertEquals(
          1,
          b.preserveAttachmentCount());
        Assertions.assertEquals(
          23,
          b.pPreserveAttachments().get(0));

        Assertions.assertEquals(
          1,
          b.colorAttachmentCount());
        Assertions.assertEquals(
          VK10.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL,
          b.pColorAttachments().layout());
        Assertions.assertEquals(
          0,
          b.pColorAttachments().attachment());

        Assertions.assertEquals(
          VK10.VK_PIPELINE_BIND_POINT_GRAPHICS,
          b.pipelineBindPoint());
      },
      () -> {
        final var b = packed.pAttachments();
        Assertions.assertEquals(1, packed.attachmentCount());
      },
      () -> {
        final var b = packed.pDependencies();
        Assertions.assertEquals(1, packed.dependencyCount());
      }
    );
  }
}
