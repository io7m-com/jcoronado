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

import com.io7m.jcoronado.api.VulkanCopyDescriptorSet;
import com.io7m.jcoronado.api.VulkanException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkCopyDescriptorSet;

import java.util.List;
import java.util.Objects;

import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLClassChecks.checkInstanceOf;

/**
 * Functions to pack descriptor set copy infos.
 */

public final class VulkanLWJGLCopyDescriptorSets
{
  private VulkanLWJGLCopyDescriptorSets()
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
   * @throws VulkanException On errors
   */

  public static VkCopyDescriptorSet pack(
    final MemoryStack stack,
    final VulkanCopyDescriptorSet info)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(stack, info, VkCopyDescriptorSet.malloc(stack));
  }

  /**
   * Pack a structure.
   *
   * @param stack  A stack
   * @param source A structure
   * @param target The output structure
   *
   * @return A packed structure
   *
   * @throws VulkanException On errors
   */

  public static VkCopyDescriptorSet packInto(
    final MemoryStack stack,
    final VulkanCopyDescriptorSet source,
    final VkCopyDescriptorSet target)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    final var dst_set =
      checkInstanceOf(source.destinationSet(), VulkanLWJGLDescriptorSet.class);
    final var src_set =
      checkInstanceOf(source.sourceSet(), VulkanLWJGLDescriptorSet.class);

    return target
      .sType(VK10.VK_STRUCTURE_TYPE_COPY_DESCRIPTOR_SET)
      .pNext(0L)
      .descriptorCount(source.descriptorCount())
      .dstArrayElement(source.destinationArrayElement())
      .dstBinding(source.destinationBinding())
      .dstSet(dst_set.handle())
      .srcArrayElement(source.sourceArrayElement())
      .srcBinding(source.sourceBinding())
      .srcSet(src_set.handle());
  }

  /**
   * Pack a list of structures.
   *
   * @param stack A stack
   * @param infos A list of structure
   *
   * @return A packed array
   *
   * @throws VulkanException On errors
   */

  public static VkCopyDescriptorSet.Buffer packList(
    final MemoryStack stack,
    final List<VulkanCopyDescriptorSet> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      VulkanLWJGLCopyDescriptorSets::packInto,
      VkCopyDescriptorSet::malloc,
      stack
    );
  }
}
