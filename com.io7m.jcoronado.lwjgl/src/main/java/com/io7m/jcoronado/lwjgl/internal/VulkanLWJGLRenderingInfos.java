/*
 * Copyright © 2026 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

package com.io7m.jcoronado.lwjgl.internal;

import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanRenderingAttachmentInfo;
import com.io7m.jcoronado.api.VulkanRenderingInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK14;
import org.lwjgl.vulkan.VkRenderingAttachmentInfo;
import org.lwjgl.vulkan.VkRenderingInfo;

import java.util.Objects;

/**
 * Functions to pack rendering infos.
 */

public final class VulkanLWJGLRenderingInfos
{
  private VulkanLWJGLRenderingInfos()
  {

  }

  private static void packRenderingAttachmentInfoInto(
    final MemoryStack stack,
    final VulkanRenderingAttachmentInfo info,
    final VkRenderingAttachmentInfo output)
    throws VulkanIncompatibleClassException
  {
    final var imageView =
      VulkanLWJGLClassChecks.checkInstanceOf(
        info.imageView(),
        VulkanLWJGLImageView.class
      );

    final long resolveView;
    if (info.resolveImageView().isPresent()) {
      resolveView = VulkanLWJGLClassChecks.checkInstanceOf(
        info.resolveImageView().get(),
        VulkanLWJGLImageView.class
      ).handle();
    } else {
      resolveView = 0L;
    }

    final var imageLayout =
      info.imageLayout().value();
    final var resolveImageLayout =
      info.resolveImageLayout().value();
    final var resolveMode =
      VulkanEnumMaps.packValues(info.resolveMode());

    output.sType(VK14.VK_STRUCTURE_TYPE_RENDERING_ATTACHMENT_INFO)
      .pNext(0L)
      .clearValue(VulkanLWJGLClearValues.pack(stack, info.clearValue()))
      .imageLayout(imageLayout)
      .imageView(imageView.handle())
      .loadOp(info.loadOp().value())
      .resolveImageLayout(resolveImageLayout)
      .resolveImageView(resolveView)
      .resolveMode(resolveMode)
      .storeOp(info.storeOp().value());
  }

  /**
   * Pack rendering info.
   *
   * @param stack The stack
   * @param info  The info
   *
   * @return The packed info
   *
   * @throws VulkanException On errors
   */

  public static VkRenderingInfo pack(
    final MemoryStack stack,
    final VulkanRenderingInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    final var colorAttachments =
      VulkanLWJGLArrays.packOrNull(
        info.colorAttachments(),
        VulkanLWJGLRenderingInfos::packRenderingAttachmentInfoInto,
        VkRenderingAttachmentInfo::calloc,
        stack
      );

    final var depthAttachment =
      VulkanLWJGLOptionals.pack(
        info.depthAttachment(),
        VulkanLWJGLRenderingInfos::packRenderingAttachmentInfoInto,
        VkRenderingAttachmentInfo::calloc,
        stack
      );

    final var stencilAttachment =
      VulkanLWJGLOptionals.pack(
        info.stencilAttachment(),
        VulkanLWJGLRenderingInfos::packRenderingAttachmentInfoInto,
        VkRenderingAttachmentInfo::calloc,
        stack
      );

    return VkRenderingInfo.calloc(stack)
      .sType(VK14.VK_STRUCTURE_TYPE_RENDERING_INFO)
      .pNext(0L)
      .flags(VulkanEnumMaps.packValues(info.flags()))
      .layerCount((int) info.layerCount())
      .pColorAttachments(colorAttachments)
      .pDepthAttachment(depthAttachment)
      .pStencilAttachment(stencilAttachment)
      .renderArea(VulkanLWJGLRect2Ds.pack(stack, info.renderArea()))
      .viewMask((int) info.viewMask());
  }
}
