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

import com.io7m.jcoronado.api.VulkanClearRectangle;
import com.io7m.jcoronado.api.VulkanException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkClearRect;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack image subresource layers.
 */

public final class VulkanLWJGLClearRectangles
{
  private VulkanLWJGLClearRectangles()
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

  public static VkClearRect pack(
    final MemoryStack stack,
    final VulkanClearRectangle info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(stack, info, VkClearRect.malloc(stack));
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

  public static VkClearRect packInto(
    final MemoryStack stack,
    final VulkanClearRectangle source,
    final VkClearRect target)
  {
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    return target.set(
      VulkanLWJGLRect2Ds.pack(stack, source.rectangle()),
      source.baseArrayLayer(),
      source.layerCount());
  }

  /**
   * Pack structures.
   *
   * @param stack A stack
   * @param infos A list of structure
   *
   * @return A list of packed structure
   *
   * @throws VulkanException On errors
   */

  public static VkClearRect.Buffer packList(
    final MemoryStack stack,
    final List<VulkanClearRectangle> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      VulkanLWJGLClearRectangles::packInto,
      (sstack, count) -> VkClearRect.malloc(count, sstack),
      stack);
  }

  /**
   * Pack structures.
   *
   * @param stack A stack
   * @param infos A list of structure
   *
   * @return A list of packed structure
   *
   * @throws VulkanException On errors
   */

  public static VkClearRect.Buffer packListOrNull(
    final MemoryStack stack,
    final List<VulkanClearRectangle> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.packOrNull(
      infos,
      VulkanLWJGLClearRectangles::packInto,
      (sstack, count) -> VkClearRect.malloc(count, sstack),
      stack);
  }
}
