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
import com.io7m.jcoronado.api.VulkanShaderModuleCreateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkShaderModuleCreateInfo;

import java.util.Objects;

/**
 * Functions to pack shader modules.
 */

public final class VulkanLWJGLShaderModules
{
  private VulkanLWJGLShaderModules()
  {

  }

  /**
   * Pack a shader module structure.
   *
   * @param stack       A stack
   * @param create_info The info structure
   *
   * @return A packed structure
   */

  public static VkShaderModuleCreateInfo pack(
    final MemoryStack stack,
    final VulkanShaderModuleCreateInfo create_info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(create_info, "create_info");

    return VkShaderModuleCreateInfo.calloc(stack)
      .sType(VK10.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO)
      .pNext(0L)
      .pCode(create_info.data())
      .flags(VulkanEnumMaps.packValues(create_info.flags()));
  }
}
