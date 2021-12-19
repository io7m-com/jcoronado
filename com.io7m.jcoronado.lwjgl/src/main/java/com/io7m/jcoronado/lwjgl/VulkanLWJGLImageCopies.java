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

import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanImageCopy;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkImageCopy;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack image copy info.
 */

public final class VulkanLWJGLImageCopies
{
  private VulkanLWJGLImageCopies()
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

  public static VkImageCopy pack(
    final MemoryStack stack,
    final VulkanImageCopy info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(stack, info, VkImageCopy.malloc(stack));
  }

  /**
   * Pack a structure.
   *
   * @param stack  A stack
   * @param source The input structure
   * @param target The output structure
   *
   * @return A packed structure
   */

  public static VkImageCopy packInto(
    final MemoryStack stack,
    final VulkanImageCopy source,
    final VkImageCopy target)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    return target.set(
      VulkanLWJGLImageSubresourceLayers.pack(stack, source.sourceSubresource()),
      VulkanLWJGLOffset3Ds.pack(stack, source.sourceOffset()),
      VulkanLWJGLImageSubresourceLayers.pack(stack, source.targetSubresource()),
      VulkanLWJGLOffset3Ds.pack(stack, source.targetOffset()),
      VulkanLWJGLExtent3Ds.pack(stack, source.extent()));
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

  public static VkImageCopy.Buffer packList(
    final MemoryStack stack,
    final List<VulkanImageCopy> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      (sstack, value, output) -> packInto(stack, value, output),
      (sstack, count) -> VkImageCopy.malloc(count, sstack),
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

  public static VkImageCopy.Buffer packListOrNull(
    final MemoryStack stack,
    final List<VulkanImageCopy> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.packOrNull(
      infos,
      (sstack, value, output) -> packInto(stack, value, output),
      (sstack, count) -> VkImageCopy.malloc(count, sstack),
      stack);
  }
}
