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

package com.io7m.jcoronado.lwjgl;

import com.io7m.jcoronado.api.VulkanAttachmentDescription;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkAttachmentDescription;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack attachment descriptions.
 */

public final class VulkanLWJGLAttachmentDescriptions
{
  private VulkanLWJGLAttachmentDescriptions()
  {

  }

  /**
   * Pack attachments.
   *
   * @param stack       A stack
   * @param attachments A list of attachments
   *
   * @return A packed buffer of attachments
   */

  public static VkAttachmentDescription.Buffer packAttachments(
    final MemoryStack stack,
    final List<VulkanAttachmentDescription> attachments)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(attachments, "attachments");

    final var buffer = VkAttachmentDescription.mallocStack(attachments.size(), stack);
    for (var index = 0; index < attachments.size(); ++index) {
      final var source = attachments.get(index);
      final var target = VkAttachmentDescription.create(buffer.address(index));
      packAttachmentInto(source, target);
    }

    return buffer;
  }

  /**
   * Pack attachment.
   *
   * @param stack      A stack
   * @param attachment An attachment
   *
   * @return A packed attachment
   */

  public static VkAttachmentDescription packAttachment(
    final MemoryStack stack,
    final VulkanAttachmentDescription attachment)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(attachment, "attachment");

    final var buffer = VkAttachmentDescription.mallocStack(stack);
    return packAttachmentInto(attachment, buffer);
  }

  private static VkAttachmentDescription packAttachmentInto(
    final VulkanAttachmentDescription source,
    final VkAttachmentDescription target)
  {
    return target
      .finalLayout(source.finalLayout().value())
      .flags(VulkanEnumMaps.packValues(source.flags()))
      .format(source.format().value())
      .initialLayout(source.initialLayout().value())
      .loadOp(source.loadOp().value())
      .samples(source.samples().value())
      .storeOp(source.storeOp().value())
      .stencilLoadOp(source.stencilLoadOp().value())
      .stencilStoreOp(source.stencilStoreOp().value());
  }
}
