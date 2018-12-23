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

import com.io7m.jcoronado.api.VulkanDescriptorPoolSize;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkDescriptorPoolSize;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack descriptor pool sizes.
 */

public final class VulkanLWJGLDescriptorPoolSizes
{
  private VulkanLWJGLDescriptorPoolSizes()
  {

  }

  /**
   * Pack a list of values into a Vulkan structure.
   *
   * @param stack The stack used for allocations
   * @param infos The input values
   *
   * @return A packed Vulkan structure
   */

  public static VkDescriptorPoolSize.Buffer packDescriptorPoolSizes(
    final MemoryStack stack,
    final List<VulkanDescriptorPoolSize> infos)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    final var buffer = VkDescriptorPoolSize.mallocStack(infos.size(), stack);
    for (var index = 0; index < infos.size(); ++index) {
      packDescriptorPoolSizeInto(infos.get(index), buffer.get(index));
    }
    return buffer;
  }

  /**
   * Pack a value into a Vulkan structure.
   *
   * @param stack The stack used for allocations
   * @param info  The input value
   *
   * @return A packed Vulkan structure
   */

  public static VkDescriptorPoolSize packDescriptorPoolSize(
    final MemoryStack stack,
    final VulkanDescriptorPoolSize info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    final var buffer = VkDescriptorPoolSize.mallocStack(stack);
    packDescriptorPoolSizeInto(info, buffer);
    return buffer;
  }

  /**
   * Pack a value into a Vulkan structure.
   *
   * @param info   The input value
   * @param buffer The output value
   *
   * @return A packed Vulkan structure
   */

  public static VkDescriptorPoolSize packDescriptorPoolSizeInto(
    final VulkanDescriptorPoolSize info,
    final VkDescriptorPoolSize buffer)
  {
    Objects.requireNonNull(info, "info");
    Objects.requireNonNull(buffer, "buffer");

    return buffer.set(
      info.type().value(),
      info.descriptorCount());
  }
}
