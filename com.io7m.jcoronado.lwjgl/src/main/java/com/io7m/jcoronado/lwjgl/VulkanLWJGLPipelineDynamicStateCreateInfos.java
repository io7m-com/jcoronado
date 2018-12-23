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

import com.io7m.jcoronado.api.VulkanDynamicState;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanPipelineDynamicStateCreateInfo;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineDynamicStateCreateInfo;

import java.nio.IntBuffer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Functions to pack pipeline creation info.
 */

public final class VulkanLWJGLPipelineDynamicStateCreateInfos
{
  private VulkanLWJGLPipelineDynamicStateCreateInfos()
  {

  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param info  A structure
   *
   * @return A packed structure
   *
   * @throws VulkanException On errors
   */

  public static VkPipelineDynamicStateCreateInfo pack(
    final MemoryStack stack,
    final VulkanPipelineDynamicStateCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    final var target =
      VkPipelineDynamicStateCreateInfo.mallocStack(stack);

    return target
      .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO)
      .pNext(0L)
      .flags(VulkanEnumMaps.packValues(info.flags()))
      .pDynamicStates(packStates(stack, info.dynamicStates()));
  }

  private static IntBuffer packStates(
    final MemoryStack stack,
    final List<VulkanDynamicState> states)
    throws VulkanException
  {
    return VulkanLWJGLIntegerArrays.packIntsOrNull(stack, states, VulkanDynamicState::value);
  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param info  A structure
   *
   * @return A packed structure (or null)
   *
   * @throws VulkanException On errors
   */

  public static VkPipelineDynamicStateCreateInfo packOptional(
    final MemoryStack stack,
    final Optional<VulkanPipelineDynamicStateCreateInfo> info)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    try {
      return info.map(iinfo -> {
        try {
          return pack(stack, iinfo);
        } catch (VulkanException e) {
          throw new VulkanUncheckedException(e);
        }
      }).orElse(null);
    } catch (final VulkanUncheckedException e) {
      throw e.getCause();
    }
  }
}
