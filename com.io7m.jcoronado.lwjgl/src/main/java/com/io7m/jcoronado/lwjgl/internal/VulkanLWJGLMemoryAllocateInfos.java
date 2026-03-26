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

import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanMemoryAllocateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK11;
import org.lwjgl.vulkan.VkMemoryAllocateFlagsInfo;
import org.lwjgl.vulkan.VkMemoryAllocateInfo;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack subresource ranges.
 */

public final class VulkanLWJGLMemoryAllocateInfos
{
  private VulkanLWJGLMemoryAllocateInfos()
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

  public static VkMemoryAllocateInfo pack(
    final MemoryStack stack,
    final VulkanMemoryAllocateInfo info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(stack, info, VkMemoryAllocateInfo.calloc(stack));
  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param source The input structure
   * @param target The output structure
   *
   * @return A packed structure
   */

  public static VkMemoryAllocateInfo packInto(
    final MemoryStack stack,
    final VulkanMemoryAllocateInfo source,
    final VkMemoryAllocateInfo target)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    final var flagsInfo = VkMemoryAllocateFlagsInfo.calloc(stack);
    flagsInfo.sType(VK11.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_FLAGS_INFO);
    flagsInfo.flags(VulkanEnumMaps.packValues(source.flags()));

    return target.set(
      VK10.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO,
      flagsInfo.address(),
      source.size(),
      source.memoryTypeIndex().value()
    );
  }

  /**
   * Pack structures.
   *
   * @param stack A stack
   * @param infos A list of structures
   *
   * @return A list of packed structure
   *
   * @throws VulkanException On errors
   */

  public static VkMemoryAllocateInfo.Buffer packList(
    final MemoryStack stack,
    final List<VulkanMemoryAllocateInfo> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      VulkanLWJGLMemoryAllocateInfos::packInto,
      VkMemoryAllocateInfo::calloc,
      stack
    );
  }

  /**
   * Pack structures.
   *
   * @param stack A stack
   * @param infos A list of structures
   *
   * @return A list of packed structure
   *
   * @throws VulkanException On errors
   */

  public static VkMemoryAllocateInfo.Buffer packListOrNull(
    final MemoryStack stack,
    final List<VulkanMemoryAllocateInfo> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.packOrNull(
      infos,
      VulkanLWJGLMemoryAllocateInfos::packInto,
      VkMemoryAllocateInfo::calloc,
      stack
    );
  }
}
