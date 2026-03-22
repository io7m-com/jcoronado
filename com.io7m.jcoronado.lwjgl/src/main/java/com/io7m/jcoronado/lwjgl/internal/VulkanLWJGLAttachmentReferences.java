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
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK13;
import org.lwjgl.vulkan.VkAttachmentReference2;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack attachment descriptions.
 */

public final class VulkanLWJGLAttachmentReferences
{
  private VulkanLWJGLAttachmentReferences()
  {

  }

  /**
   * Pack attachment references.
   *
   * @param stack      A stack
   * @param references The references
   *
   * @return A packed buffer
   */

  public static VkAttachmentReference2.Buffer packAttachmentReferences(
    final MemoryStack stack,
    final List<VulkanAttachmentReference> references)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(references, "references");

    final var buffer =
      VkAttachmentReference2.calloc(references.size(), stack);

    for (var index = 0; index < references.size(); ++index) {
      final var source =
        references.get(index);
      final var target =
        VkAttachmentReference2.create(buffer.address(index));
      packInto(source, target);
    }
    return buffer;
  }

  /**
   * Pack attachment references.
   *
   * @param stack     A stack
   * @param reference The reference
   *
   * @return A packed reference
   */

  public static VkAttachmentReference2 packAttachmentReference(
    final MemoryStack stack,
    final VulkanAttachmentReference reference)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(reference, "reference");

    return packInto(reference, VkAttachmentReference2.calloc(stack));
  }

  private static VkAttachmentReference2 packInto(
    final VulkanAttachmentReference reference,
    final VkAttachmentReference2 target)
  {
    return target.sType(VK13.VK_STRUCTURE_TYPE_ATTACHMENT_REFERENCE_2)
      .attachment(reference.attachment())
      .aspectMask(VulkanEnumMaps.packValues(reference.aspectMask()))
      .layout(reference.layout().value());
  }
}
