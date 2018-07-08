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

import com.io7m.jcoronado.api.VulkanAttachmentReference;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkAttachmentReference;

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

  public static VkAttachmentReference.Buffer packAttachmentReferences(
    final MemoryStack stack,
    final List<VulkanAttachmentReference> references)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(references, "references");

    final VkAttachmentReference.Buffer buffer =
      VkAttachmentReference.mallocStack(references.size(), stack);

    for (int index = 0; index < references.size(); ++index) {
      final VulkanAttachmentReference source = references.get(index);
      final VkAttachmentReference target =
        VkAttachmentReference.create(buffer.address(index));
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

  public static VkAttachmentReference packAttachmentReference(
    final MemoryStack stack,
    final VulkanAttachmentReference reference)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(reference, "reference");

    return packInto(reference, VkAttachmentReference.mallocStack(stack));
  }

  private static VkAttachmentReference packInto(
    final VulkanAttachmentReference reference,
    final VkAttachmentReference target)
  {
    return target
      .attachment(reference.attachment())
      .layout(reference.layout().value());
  }
}
