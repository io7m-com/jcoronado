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
import com.io7m.jcoronado.api.VulkanImageCreateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkImageCreateInfo;

import java.nio.IntBuffer;
import java.util.List;
import java.util.Objects;

/**
 * Functions to pack image creation info.
 */

public final class VulkanLWJGLImageCreateInfos
{
  private VulkanLWJGLImageCreateInfos()
  {

  }

  /**
   * Pack structure.
   *
   * @param stack  A stack
   * @param source The source structure
   *
   * @return A packed info
   *
   * @throws VulkanException On errors
   */

  public static VkImageCreateInfo pack(
    final MemoryStack stack,
    final VulkanImageCreateInfo source)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(source, "source");

    final var output = VkImageCreateInfo.mallocStack(stack);
    return packInto(stack, source, output);
  }

  /**
   * Pack structures.
   *
   * @param stack  A stack
   * @param source The source structure
   * @param output The output structure
   *
   * @return A packed info
   *
   * @throws VulkanException On errors
   */

  public static VkImageCreateInfo packInto(
    final MemoryStack stack,
    final VulkanImageCreateInfo source,
    final VkImageCreateInfo output)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(output, "output");

    return output
      .sType(VK10.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO)
      .pNext(0L)
      .arrayLayers(source.arrayLayers())
      .extent(VulkanLWJGLExtent3Ds.pack(stack, source.extent()))
      .flags(VulkanEnumMaps.packValues(source.flags()))
      .format(source.format().value())
      .imageType(source.imageType().value())
      .initialLayout(source.initialLayout().value())
      .mipLevels(source.mipLevels())
      .sharingMode(source.sharingMode().value())
      .samples(VulkanEnumMaps.packValues(source.samples()))
      .tiling(source.tiling().value())
      .usage(VulkanEnumMaps.packValues(source.usage()))
      .pQueueFamilyIndices(packQueueFamilies(stack, source.queueFamilyIndices()));
  }

  private static IntBuffer packQueueFamilies(
    final MemoryStack stack,
    final List<Integer> integers)
    throws VulkanException
  {
    return VulkanLWJGLIntegerArrays.packIntsOrNull(stack, integers, Integer::intValue);
  }
}
