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
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkImageSubresourceRange;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack subresource ranges.
 */

public final class VulkanLWJGLImageSubresourceRanges
{
  private VulkanLWJGLImageSubresourceRanges()
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

  public static VkImageSubresourceRange pack(
    final MemoryStack stack,
    final VulkanImageSubresourceRange info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(info, VkImageSubresourceRange.malloc(stack));
  }

  /**
   * Pack a structure.
   *
   * @param source The input structure
   * @param target The output structure
   *
   * @return A packed structure
   */

  public static VkImageSubresourceRange packInto(
    final VulkanImageSubresourceRange source,
    final VkImageSubresourceRange target)
  {
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    return target
      .aspectMask(VulkanEnumMaps.packValues(source.aspectMask()))
      .baseArrayLayer(source.baseArrayLayer())
      .baseMipLevel(source.baseMipLevel())
      .layerCount(source.layerCount())
      .levelCount(source.levelCount());
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

  public static VkImageSubresourceRange.Buffer packList(
    final MemoryStack stack,
    final List<VulkanImageSubresourceRange> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      (sstack, value, output) -> packInto(value, output),
      (sstack, count) -> VkImageSubresourceRange.malloc(count, sstack),
      stack);
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

  public static VkImageSubresourceRange.Buffer packListOrNull(
    final MemoryStack stack,
    final List<VulkanImageSubresourceRange> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.packOrNull(
      infos,
      (sstack, value, output) -> packInto(value, output),
      (sstack, count) -> VkImageSubresourceRange.malloc(count, sstack),
      stack);
  }
}
