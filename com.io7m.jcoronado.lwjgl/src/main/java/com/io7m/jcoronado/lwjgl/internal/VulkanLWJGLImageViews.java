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

import com.io7m.jcoronado.api.VulkanComponentMapping;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkComponentMapping;
import org.lwjgl.vulkan.VkImageSubresourceRange;
import org.lwjgl.vulkan.VkImageViewCreateInfo;

import java.util.Objects;

/**
 * Functions to pack image views.
 */

public final class VulkanLWJGLImageViews
{
  private VulkanLWJGLImageViews()
  {

  }

  private static VkImageSubresourceRange packSubresourceRange(
    final MemoryStack stack,
    final VulkanImageSubresourceRange range)
  {
    return packSubresourceRangeInto(
      range,
      VkImageSubresourceRange.malloc(stack));
  }

  private static VkImageSubresourceRange packSubresourceRangeInto(
    final VulkanImageSubresourceRange source,
    final VkImageSubresourceRange target)
  {
    return target
      .aspectMask(VulkanEnumMaps.packValues(source.aspectMask()))
      .baseArrayLayer(source.baseArrayLayer())
      .baseMipLevel(source.baseMipLevel())
      .layerCount(source.layerCount())
      .levelCount(source.levelCount());
  }

  private static VkComponentMapping packComponentMapping(
    final MemoryStack stack,
    final VulkanComponentMapping components)
  {
    return packComponentMappingInto(
      components,
      VkComponentMapping.malloc(stack));
  }

  private static VkComponentMapping packComponentMappingInto(
    final VulkanComponentMapping source,
    final VkComponentMapping target)
  {
    return target
      .set(
        source.r().value(),
        source.g().value(),
        source.b().value(),
        source.a().value());
  }

  /**
   * Pack an {@code VkImageViewCreateInfo}.
   *
   * @param stack A memory stack
   * @param info  An info structure
   * @param image An image
   *
   * @return A packed structure
   */

  public static VkImageViewCreateInfo packImageViewCreateInfo(
    final MemoryStack stack,
    final VulkanImageViewCreateInfo info,
    final VulkanLWJGLImage image)
  {
    Objects.requireNonNull(info, "info");
    Objects.requireNonNull(image, "image");
    Objects.requireNonNull(stack, "stack");

    final var target = VkImageViewCreateInfo.malloc(stack);
    return packImageViewCreateInfoInto(stack, info, image, target);
  }

  private static VkImageViewCreateInfo packImageViewCreateInfoInto(
    final MemoryStack stack,
    final VulkanImageViewCreateInfo info,
    final VulkanLWJGLImage image,
    final VkImageViewCreateInfo target)
  {
    return target
      .sType(VK10.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO)
      .pNext(0L)
      .components(packComponentMapping(stack, info.components()))
      .flags(VulkanEnumMaps.packValues(info.flags()))
      .format(info.format().value())
      .image(image.handle())
      .subresourceRange(packSubresourceRange(stack, info.subresourceRange()))
      .viewType(info.viewType().value());
  }
}
