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
import org.lwjgl.vulkan.VK13;
import org.lwjgl.vulkan.VkImageMemoryBarrier2;

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

  public static VkImageMemoryBarrier2 pack(
    final MemoryStack stack,
    final VulkanImageMemoryBarrier info)
    throws VulkanIncompatibleClassException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(stack, info, VkImageMemoryBarrier2.calloc(stack));
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

  public static VkImageMemoryBarrier2 packInto(
    final MemoryStack stack,
    final VulkanImageMemoryBarrier source,
    final VkImageMemoryBarrier2 target)
    throws VulkanIncompatibleClassException
  {
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    final var dstStageMask =
      VulkanEnumMaps.packValuesLong(source.dstStageMask());
    final var dstAccessMask =
      VulkanEnumMaps.packValuesLong(source.dstAccessMask());
    final var dstQueue =
      source.dstQueueFamilyIndex().value();

    final var imageHandle =
      checkInstanceOf(source.image(), VulkanLWJGLImage.class).handle();

    final var newLayout =
      source.newLayout().value();
    final var oldLayout =
      source.oldLayout().value();

    final var subresource =
      VulkanLWJGLImageSubresourceRanges.pack(stack, source.subresourceRange());

    final var srcAccessMask =
      VulkanEnumMaps.packValuesLong(source.srcAccessMask());
    final var srcStageMask =
      VulkanEnumMaps.packValuesLong(source.srcStageMask());
    final var srcQueueIndex =
      source.srcQueueFamilyIndex().value();

    return target
      .sType(VK13.VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER_2)
      .pNext(0L)
      .dstAccessMask(dstAccessMask)
      .dstQueueFamilyIndex(dstQueue)
      .dstStageMask(dstStageMask)
      .image(imageHandle)
      .newLayout(newLayout)
      .oldLayout(oldLayout)
      .srcAccessMask(srcAccessMask)
      .srcQueueFamilyIndex(srcQueueIndex)
      .srcStageMask(srcStageMask)
      .subresourceRange(subresource);
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

  public static VkImageMemoryBarrier2.Buffer packList(
    final MemoryStack stack,
    final List<VulkanImageMemoryBarrier> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      VulkanLWJGLImageMemoryBarriers::packInto,
      VkImageMemoryBarrier2::calloc,
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

  public static VkImageMemoryBarrier2.Buffer packListOrNull(
    final MemoryStack stack,
    final List<VulkanImageMemoryBarrier> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.packOrNull(
      infos,
      VulkanLWJGLImageMemoryBarriers::packInto,
      VkImageMemoryBarrier2::calloc,
      stack
    );
  }
}
