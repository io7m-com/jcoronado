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

import com.io7m.jcoronado.api.VulkanDependencyInfo;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK13;
import org.lwjgl.vulkan.VkDependencyInfo;

import java.util.Objects;

/**
 * Functions to pack dependency infos.
 */

public final class VulkanLWJGLDependencyInfos
{
  private VulkanLWJGLDependencyInfos()
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
   * @throws VulkanIncompatibleClassException On errors
   */

  public static VkDependencyInfo pack(
    final MemoryStack stack,
    final VulkanDependencyInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(stack, info, VkDependencyInfo.calloc(stack));
  }

  /**
   * Pack a structure.
   *
   * @param stack  A stack
   * @param source The input structure
   * @param target The output structure
   *
   * @return A packed structure
   *
   * @throws VulkanException On errors
   */

  public static VkDependencyInfo packInto(
    final MemoryStack stack,
    final VulkanDependencyInfo source,
    final VkDependencyInfo target)
    throws VulkanException
  {
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    return target.sType(VK13.VK_STRUCTURE_TYPE_DEPENDENCY_INFO)
      .pNext(0L)
      .dependencyFlags(
        VulkanEnumMaps.packValues(source.flags()))
      .pBufferMemoryBarriers(
        VulkanLWJGLBufferMemoryBarriers.packListOrNull(
          stack,
          source.bufferMemoryBarriers()
        )
      )
      .pImageMemoryBarriers(
        VulkanLWJGLImageMemoryBarriers.packListOrNull(
          stack,
          source.imageMemoryBarriers()
        )
      )
      .pMemoryBarriers(
        VulkanLWJGLMemoryBarriers.packListOrNull(
          stack,
          source.memoryBarriers()
        )
      );
  }
}
