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
import com.io7m.jcoronado.api.VulkanViewport;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkViewport;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack viewports.
 */

public final class VulkanLWJGLViewports
{
  private VulkanLWJGLViewports()
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

  public static VkViewport pack(
    final MemoryStack stack,
    final VulkanViewport info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(info, VkViewport.malloc(stack));
  }

  /**
   * Pack a structure.
   *
   * @param source The input structure
   * @param target The output structure
   *
   * @return A packed structure
   */

  public static VkViewport packInto(
    final VulkanViewport source,
    final VkViewport target)
  {
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    return target.set(
      source.x(),
      source.y(),
      source.width(),
      source.height(),
      source.minDepth(),
      source.maxDepth());
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

  public static VkViewport.Buffer packList(
    final MemoryStack stack,
    final List<VulkanViewport> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      (stack1, source, target) -> packInto(source, target),
      (sstack, count) -> VkViewport.malloc(count, sstack),
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

  public static VkViewport.Buffer packListOrNull(
    final MemoryStack stack,
    final List<VulkanViewport> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.packOrNull(
      infos,
      (stack1, source, target) -> packInto(source, target),
      (sstack, count) -> VkViewport.malloc(count, sstack),
      stack);
  }
}
