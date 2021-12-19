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
import com.io7m.jcoronado.api.VulkanOffset2D;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkOffset2D;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack rectangles.
 */

public final class VulkanLWJGLOffset2Ds
{
  private VulkanLWJGLOffset2Ds()
  {

  }

  /**
   * Pack a structure.
   *
   * @param stack  A stack
   * @param offset A structure
   *
   * @return A packed structure
   */

  public static VkOffset2D pack(
    final MemoryStack stack,
    final VulkanOffset2D offset)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(offset, "offset");

    return packInto(offset, VkOffset2D.malloc(stack));
  }

  private static VkOffset2D packInto(
    final VulkanOffset2D source,
    final VkOffset2D target)
  {
    return target.set(source.x(), source.y());
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

  public static VkOffset2D.Buffer packList(
    final MemoryStack stack,
    final List<VulkanOffset2D> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      (sstack, value, output) -> packInto(value, output),
      (sstack, count) -> VkOffset2D.malloc(count, sstack),
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

  public static VkOffset2D.Buffer packListOrNull(
    final MemoryStack stack,
    final List<VulkanOffset2D> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.packOrNull(
      infos,
      (sstack, value, output) -> packInto(value, output),
      (sstack, count) -> VkOffset2D.malloc(count, sstack),
      stack);
  }
}
