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

import com.io7m.jcoronado.api.VulkanBlendConstants;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanLogicOp;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendStateCreateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineColorBlendStateCreateInfo;

import java.util.Objects;
import java.util.Optional;

/**
 * Functions to pack pipeline creation info.
 */

public final class VulkanLWJGLPipelineColorBlendStateCreateInfos
{
  private VulkanLWJGLPipelineColorBlendStateCreateInfos()
  {

  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param info  A structure
   *
   * @return A packed structure
   */

  public static VkPipelineColorBlendStateCreateInfo pack(
    final MemoryStack stack,
    final VulkanPipelineColorBlendStateCreateInfo info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    final var target =
      VkPipelineColorBlendStateCreateInfo.malloc(stack);

    target
      .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO)
      .pNext(0L)
      .flags(VulkanEnumMaps.packValues(info.flags()));

    packLogicOp(target, info.logicOp());
    packBlendConstants(target, info.blendConstants());
    target.pAttachments(VulkanLWJGLColorBlendAttachmentStates.packAll(
      stack,
      info.attachments()));
    return target;
  }

  private static void packBlendConstants(
    final VkPipelineColorBlendStateCreateInfo target,
    final VulkanBlendConstants constants)
  {
    target.blendConstants(0, constants.r());
    target.blendConstants(1, constants.g());
    target.blendConstants(2, constants.b());
    target.blendConstants(3, constants.a());
  }

  private static void packLogicOp(
    final VkPipelineColorBlendStateCreateInfo target,
    final Optional<VulkanLogicOp> op_opt)
  {
    if (op_opt.isPresent()) {
      final var logic = op_opt.get();
      target.logicOpEnable(true);
      target.logicOp(logic.value());
    } else {
      target.logicOpEnable(false);
      target.logicOp(0);
    }
  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param info  A structure
   *
   * @return A packed structure (or null)
   */

  public static VkPipelineColorBlendStateCreateInfo packOptional(
    final MemoryStack stack,
    final Optional<VulkanPipelineColorBlendStateCreateInfo> info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return info.map(iinfo -> pack(stack, iinfo)).orElse(null);
  }
}
