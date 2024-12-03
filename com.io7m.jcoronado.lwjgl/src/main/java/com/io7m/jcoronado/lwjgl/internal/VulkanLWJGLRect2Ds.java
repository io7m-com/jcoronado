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

import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanRectangle2D;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkRect2D;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack rectangles.
 */

public final class VulkanLWJGLRect2Ds
{
  private VulkanLWJGLRect2Ds()
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

  public static VkRect2D pack(
    final MemoryStack stack,
    final VulkanRectangle2D info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(stack, info, VkRect2D.malloc(stack));
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

  public static VkRect2D packInto(
    final MemoryStack stack,
    final VulkanRectangle2D source,
    final VkRect2D target)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    return target.set(
      VulkanLWJGLOffset2Ds.pack(stack, source.offset()),
      VulkanLWJGLExtent2Ds.pack(stack, source.extent()));
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

  public static VkRect2D.Buffer packList(
    final MemoryStack stack,
    final List<VulkanRectangle2D> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      VulkanLWJGLRect2Ds::packInto,
      VkRect2D::malloc,
      stack
    );
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

  public static VkRect2D.Buffer packListOrNull(
    final MemoryStack stack,
    final List<VulkanRectangle2D> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.packOrNull(
      infos,
      VulkanLWJGLRect2Ds::packInto,
      VkRect2D::malloc,
      stack
    );
  }
}
