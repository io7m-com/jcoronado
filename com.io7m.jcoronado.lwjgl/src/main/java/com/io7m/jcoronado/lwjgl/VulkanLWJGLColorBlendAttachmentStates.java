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

import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendAttachmentState;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPipelineColorBlendAttachmentState;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack attachment states.
 */

public final class VulkanLWJGLColorBlendAttachmentStates
{
  private VulkanLWJGLColorBlendAttachmentStates()
  {

  }

  /**
   * Pack a list of structures.
   *
   * @param stack       A stack
   * @param attachments A list of structure
   *
   * @return A packed list of structures
   */

  public static VkPipelineColorBlendAttachmentState.Buffer packAll(
    final MemoryStack stack,
    final List<VulkanPipelineColorBlendAttachmentState> attachments)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(attachments, "attachments");

    final var count = attachments.size();
    if (count == 0) {
      return null;
    }

    final var buffer =
      VkPipelineColorBlendAttachmentState.malloc(count, stack);

    for (var index = 0; index < count; ++index) {
      final var source = attachments.get(index);
      final var target = VkPipelineColorBlendAttachmentState.create(buffer.address(
        index));
      packInto(source, target);
    }
    return buffer;
  }

  private static void packInto(
    final VulkanPipelineColorBlendAttachmentState source,
    final VkPipelineColorBlendAttachmentState target)
  {
    target.alphaBlendOp(source.alphaBlendOp().value())
      .blendEnable(source.enable())
      .colorBlendOp(source.colorBlendOp().value())
      .colorWriteMask(VulkanEnumMaps.packValues(source.colorWriteMask()))
      .dstAlphaBlendFactor(source.dstAlphaBlendFactor().value())
      .dstColorBlendFactor(source.dstColorBlendFactor().value())
      .srcAlphaBlendFactor(source.srcAlphaBlendFactor().value())
      .srcColorBlendFactor(source.srcColorBlendFactor().value());
  }
}
