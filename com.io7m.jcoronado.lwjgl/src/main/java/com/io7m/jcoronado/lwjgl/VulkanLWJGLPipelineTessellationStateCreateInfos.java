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
import com.io7m.jcoronado.api.VulkanPipelineTessellationStateCreateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineTessellationStateCreateInfo;

import java.util.Objects;
import java.util.Optional;

/**
 * Functions to pack pipeline creation info.
 */

public final class VulkanLWJGLPipelineTessellationStateCreateInfos
{
  private VulkanLWJGLPipelineTessellationStateCreateInfos()
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

  public static VkPipelineTessellationStateCreateInfo pack(
    final MemoryStack stack,
    final VulkanPipelineTessellationStateCreateInfo info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    final var target =
      VkPipelineTessellationStateCreateInfo.malloc(stack);

    return target
      .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO)
      .pNext(0L)
      .flags(VulkanEnumMaps.packValues(info.flags()))
      .patchControlPoints(info.patchControlPoints());
  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param info  A structure
   *
   * @return A packed structure (or null)
   */

  public static VkPipelineTessellationStateCreateInfo packOptional(
    final MemoryStack stack,
    final Optional<VulkanPipelineTessellationStateCreateInfo> info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return info.map(iinfo -> pack(stack, iinfo)).orElse(null);
  }
}
