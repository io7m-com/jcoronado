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

package com.io7m.jcoronado.lwjgl.internal;

import com.io7m.jcoronado.api.VulkanAttachmentReference;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanRenderPassCreateInfo;
import com.io7m.jcoronado.api.VulkanSubpassDescription;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK13;
import org.lwjgl.vulkan.VkRenderPassCreateInfo2;
import org.lwjgl.vulkan.VkSubpassDescription2;

import java.nio.IntBuffer;
import java.util.List;
import java.util.Objects;

import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLAttachmentDescriptions.packAttachments;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLAttachmentReferences.packAttachmentReference;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLAttachmentReferences.packAttachmentReferences;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLSubpasses.packSubpassDependencies;

/**
 * Functions to pack render passes.
 */

public final class VulkanLWJGLRenderPasses
{
  private VulkanLWJGLRenderPasses()
  {

  }

  /**
   * Pack a creation info structure.
   *
   * @param stack A stack
   * @param info  An info structure
   *
   * @return A packed structure
   *
   * @throws VulkanException On errors
   */

  public static VkRenderPassCreateInfo2 packRenderPassCreateInfo(
    final MemoryStack stack,
    final VulkanRenderPassCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return VkRenderPassCreateInfo2.calloc(stack)
      .sType(VK13.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO_2)
      .pNext(0L)
      .pAttachments(packAttachments(stack, info.attachments()))
      .pSubpasses(packSubpasses(stack, info.subpasses()))
      .pDependencies(packSubpassDependencies(stack, info.dependencies()))
      .pCorrelatedViewMasks(null)
      .flags(VulkanEnumMaps.packValues(info.flags()));
  }

  private static VkSubpassDescription2.Buffer packSubpasses(
    final MemoryStack stack,
    final List<VulkanSubpassDescription> subpasses)
    throws VulkanException
  {
    final var buffer =
      VkSubpassDescription2.calloc(subpasses.size(), stack);

    for (var index = 0; index < subpasses.size(); ++index) {
      final var description = subpasses.get(index);

      buffer.position(index)
        .sType(VK13.VK_STRUCTURE_TYPE_SUBPASS_DESCRIPTION_2)
        .flags(VulkanEnumMaps.packValues(description.flags()))
        .pipelineBindPoint(description.pipelineBindPoint().value());

      packColorAttachments(stack, buffer, description.colorAttachments());
      packInputAttachments(stack, buffer, description.inputAttachments());
      packPreserveAttachments(stack, buffer, description.preserveAttachments());
      packResolveAttachments(stack, buffer, description.resolveAttachments());
      packDepthStencilAttachment(stack, buffer, description);
    }

    return buffer;
  }

  private static void packDepthStencilAttachment(
    final MemoryStack stack,
    final VkSubpassDescription2.Buffer buffer,
    final VulkanSubpassDescription description)
  {
    final var attach_opt =
      description.depthStencilAttachment();
    if (attach_opt.isPresent()) {
      final var attach = attach_opt.get();
      buffer.pDepthStencilAttachment(packAttachmentReference(stack, attach));
    } else {
      buffer.pDepthStencilAttachment(null);
    }
  }

  private static void packResolveAttachments(
    final MemoryStack stack,
    final VkSubpassDescription2.Buffer buffer,
    final List<VulkanAttachmentReference> resolve)
  {
    if (resolve.size() > 0) {
      buffer.pResolveAttachments(packAttachmentReferences(stack, resolve));
    } else {
      buffer.pResolveAttachments(null);
    }
  }

  private static void packPreserveAttachments(
    final MemoryStack stack,
    final VkSubpassDescription2.Buffer buffer,
    final List<Integer> preserve)
    throws VulkanException
  {
    if (preserve.size() > 0) {
      buffer.pPreserveAttachments(packPreserveAttachments(stack, preserve));
    } else {
      buffer.pPreserveAttachments(null);
    }
  }

  private static void packInputAttachments(
    final MemoryStack stack,
    final VkSubpassDescription2.Buffer buffer,
    final List<VulkanAttachmentReference> input)
  {
    if (input.size() > 0) {
      buffer.pInputAttachments(packAttachmentReferences(stack, input));
    } else {
      buffer.pInputAttachments(null);
    }
  }

  private static void packColorAttachments(
    final MemoryStack stack,
    final VkSubpassDescription2.Buffer buffer,
    final List<VulkanAttachmentReference> color)
  {
    if (color.size() > 0) {
      buffer.colorAttachmentCount(color.size());
      buffer.pColorAttachments(packAttachmentReferences(stack, color));
    } else {
      buffer.pColorAttachments(null);
      buffer.colorAttachmentCount(0);
    }
  }

  private static IntBuffer packPreserveAttachments(
    final MemoryStack stack,
    final List<Integer> integers)
    throws VulkanException
  {
    return VulkanLWJGLScalarArrays.packIntsOrNull(
      stack,
      integers,
      Integer::intValue);
  }
}
