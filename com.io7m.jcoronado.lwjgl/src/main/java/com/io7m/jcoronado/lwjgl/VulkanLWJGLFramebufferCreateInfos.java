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
import com.io7m.jcoronado.api.VulkanFramebufferCreateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkFramebufferCreateInfo;

import java.nio.LongBuffer;
import java.util.List;
import java.util.Objects;

/**
 * Functions to pack framebuffer creation info.
 */

public final class VulkanLWJGLFramebufferCreateInfos
{
  private VulkanLWJGLFramebufferCreateInfos()
  {

  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param info  A structure
   * @param views The list of views
   * @param pass  A render pass
   *
   * @return A packed structure
   */

  public static VkFramebufferCreateInfo pack(
    final MemoryStack stack,
    final VulkanFramebufferCreateInfo info,
    final List<VulkanLWJGLImageView> views,
    final VulkanLWJGLRenderPass pass)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "create_info");
    Objects.requireNonNull(views, "views");
    Objects.requireNonNull(pass, "pass");

    return VkFramebufferCreateInfo.mallocStack(stack)
      .sType(VK10.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO)
      .pNext(0L)
      .flags(VulkanEnumMaps.packValues(info.flags()))
      .renderPass(pass.handle())
      .pAttachments(packAttachments(stack, views))
      .height(info.height())
      .width(info.width())
      .layers(info.layers());
  }

  private static LongBuffer packAttachments(
    final MemoryStack stack,
    final List<VulkanLWJGLImageView> views)
  {
    final var buffer = stack.mallocLong(views.size());
    for (var index = 0; index < views.size(); ++index) {
      final var view = views.get(index);
      buffer.put(index, view.handle());
    }
    return buffer;
  }
}
