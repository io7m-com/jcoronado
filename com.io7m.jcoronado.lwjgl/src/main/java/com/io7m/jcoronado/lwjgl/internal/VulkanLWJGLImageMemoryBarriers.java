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
import com.io7m.jcoronado.api.VulkanImageMemoryBarrier;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkImageMemoryBarrier;

import java.util.List;
import java.util.Objects;

import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLClassChecks.checkInstanceOf;

/**
 * Functions to pack barriers.
 */

public final class VulkanLWJGLImageMemoryBarriers
{
  private VulkanLWJGLImageMemoryBarriers()
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

  public static VkImageMemoryBarrier pack(
    final MemoryStack stack,
    final VulkanImageMemoryBarrier info)
    throws VulkanIncompatibleClassException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(stack, info, VkImageMemoryBarrier.malloc(stack));
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
   * @throws VulkanIncompatibleClassException On errors
   */

  public static VkImageMemoryBarrier packInto(
    final MemoryStack stack,
    final VulkanImageMemoryBarrier source,
    final VkImageMemoryBarrier target)
    throws VulkanIncompatibleClassException
  {
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    return target
      .sType(VK10.VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER)
      .pNext(0L)
      .dstAccessMask(VulkanEnumMaps.packValues(source.targetAccessMask()))
      .dstQueueFamilyIndex(source.targetQueueFamilyIndex().value())
      .image(checkInstanceOf(source.image(), VulkanLWJGLImage.class).handle())
      .newLayout(source.newLayout().value())
      .oldLayout(source.oldLayout().value())
      .subresourceRange(VulkanLWJGLImageSubresourceRanges.pack(
        stack,
        source.subresourceRange()))
      .srcAccessMask(VulkanEnumMaps.packValues(source.sourceAccessMask()))
      .srcQueueFamilyIndex(source.sourceQueueFamilyIndex().value());
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

  public static VkImageMemoryBarrier.Buffer packList(
    final MemoryStack stack,
    final List<VulkanImageMemoryBarrier> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      (sstack, value, output) -> packInto(stack, value, output),
      VkImageMemoryBarrier::malloc,
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

  public static VkImageMemoryBarrier.Buffer packListOrNull(
    final MemoryStack stack,
    final List<VulkanImageMemoryBarrier> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.packOrNull(
      infos,
      (sstack, value, output) -> packInto(stack, value, output),
      VkImageMemoryBarrier::malloc,
      stack
    );
  }
}
