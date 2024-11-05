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

import com.io7m.jcoronado.api.VulkanBufferMemoryBarrier;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkBufferMemoryBarrier;

import java.util.List;
import java.util.Objects;

import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLClassChecks.checkInstanceOf;

/**
 * Functions to pack barriers.
 */

public final class VulkanLWJGLBufferMemoryBarriers
{
  private VulkanLWJGLBufferMemoryBarriers()
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

  public static VkBufferMemoryBarrier pack(
    final MemoryStack stack,
    final VulkanBufferMemoryBarrier info)
    throws VulkanIncompatibleClassException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(info, VkBufferMemoryBarrier.malloc(stack));
  }

  /**
   * Pack a structure.
   *
   * @param source The input structure
   * @param target The output structure
   *
   * @return A packed structure
   *
   * @throws VulkanIncompatibleClassException On errors
   */

  public static VkBufferMemoryBarrier packInto(
    final VulkanBufferMemoryBarrier source,
    final VkBufferMemoryBarrier target)
    throws VulkanIncompatibleClassException
  {
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    return target
      .sType(VK10.VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER)
      .pNext(0L)
      .buffer(checkInstanceOf(
        source.buffer(),
        VulkanLWJGLBuffer.class).handle())
      .dstAccessMask(VulkanEnumMaps.packValues(source.targetAccessMask()))
      .dstQueueFamilyIndex(source.targetQueueFamilyIndex().value())
      .offset(source.offset())
      .size(source.size())
      .srcAccessMask(VulkanEnumMaps.packValues(source.sourceAccessMask()))
      .srcQueueFamilyIndex(source.sourceQueueFamilyIndex().value());
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

  public static VkBufferMemoryBarrier.Buffer packList(
    final MemoryStack stack,
    final List<VulkanBufferMemoryBarrier> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      (sstack, value, output) -> packInto(value, output),
      VkBufferMemoryBarrier::malloc,
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

  public static VkBufferMemoryBarrier.Buffer packListOrNull(
    final MemoryStack stack,
    final List<VulkanBufferMemoryBarrier> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.packOrNull(
      infos,
      (sstack, value, output) -> packInto(value, output),
      VkBufferMemoryBarrier::malloc,
      stack
    );
  }
}
