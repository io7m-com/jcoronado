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
import com.io7m.jcoronado.api.VulkanSubpassDependency;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK13;
import org.lwjgl.vulkan.VkSubpassDependency2;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack subpasses.
 */

public final class VulkanLWJGLSubpasses
{
  private VulkanLWJGLSubpasses()
  {

  }

  /**
   * Pack subpass dependencies.
   *
   * @param stack        A stack
   * @param dependencies Dependencies
   *
   * @return Packed dependencies
   */

  public static VkSubpassDependency2.Buffer packSubpassDependencies(
    final MemoryStack stack,
    final List<VulkanSubpassDependency> dependencies)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(dependencies, "dependencies");

    final var buffer =
      VkSubpassDependency2.calloc(dependencies.size(), stack);

    for (var index = 0; index < dependencies.size(); ++index) {
      final var source =
        dependencies.get(index);
      final var target =
        VkSubpassDependency2.create(buffer.address(index));
      packSubpassDependencyInto(source, target);
    }

    return buffer;
  }

  /**
   * Pack a subpass dependency.
   *
   * @param stack      A stack
   * @param dependency A dependency
   *
   * @return A dependency
   */

  public static VkSubpassDependency2 packSubpassDependency(
    final MemoryStack stack,
    final VulkanSubpassDependency dependency)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(dependency, "dependency");
    return packSubpassDependencyInto(
      dependency,
      VkSubpassDependency2.calloc(stack)
    );
  }

  private static VkSubpassDependency2 packSubpassDependencyInto(
    final VulkanSubpassDependency dependency,
    final VkSubpassDependency2 target)
  {
    final var dstAccessMask =
      (int) VulkanEnumMaps.packValuesLong(dependency.dstAccessMask());
    final var dstStageMask =
      (int) VulkanEnumMaps.packValuesLong(dependency.dstStageMask());
    final var srcAccessMask =
      (int) VulkanEnumMaps.packValuesLong(dependency.srcAccessMask());
    final var srcStageMask =
      (int) VulkanEnumMaps.packValuesLong(dependency.srcStageMask());
    final var depFlags =
      VulkanEnumMaps.packValues(dependency.dependencyFlags());

    return target.sType(VK13.VK_STRUCTURE_TYPE_SUBPASS_DEPENDENCY_2)
      .dependencyFlags(depFlags)
      .dstAccessMask(dstAccessMask)
      .dstStageMask(dstStageMask)
      .dstSubpass(dependency.dstSubpass())
      .srcAccessMask(srcAccessMask)
      .srcStageMask(srcStageMask)
      .srcSubpass(dependency.srcSubpass());
  }
}
