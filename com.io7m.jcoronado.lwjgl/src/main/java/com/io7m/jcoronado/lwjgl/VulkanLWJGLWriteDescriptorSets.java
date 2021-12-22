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

import com.io7m.jcoronado.api.VulkanBufferViewType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanWriteDescriptorSet;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkWriteDescriptorSet;

import java.util.List;
import java.util.Objects;

import static com.io7m.jcoronado.lwjgl.VulkanLWJGLClassChecks.checkInstanceOf;
import static com.io7m.jcoronado.lwjgl.VulkanLWJGLDescriptorBufferInfos.packListOrNull;
import static com.io7m.jcoronado.lwjgl.VulkanLWJGLDescriptorImageInfos.packListOrNull;
import static com.io7m.jcoronado.lwjgl.VulkanLWJGLIntegerArrays.packLongsOrNull;

/**
 * Functions to pack descriptor set write infos.
 */

public final class VulkanLWJGLWriteDescriptorSets
{
  private VulkanLWJGLWriteDescriptorSets()
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

  public static VkWriteDescriptorSet pack(
    final MemoryStack stack,
    final VulkanWriteDescriptorSet info)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(stack, info, VkWriteDescriptorSet.malloc(stack));
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

  public static VkWriteDescriptorSet packInto(
    final MemoryStack stack,
    final VulkanWriteDescriptorSet source,
    final VkWriteDescriptorSet target)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    final var dst_set =
      checkInstanceOf(source.destinationSet(), VulkanLWJGLDescriptorSet.class);

    final var bufferInfos =
      source.bufferInfos();
    final var imageInfos =
      source.imageInfos();
    final var texelViews =
      source.texelBufferViews();

    final var targetBufferInfos =
      packListOrNull(stack, bufferInfos);
    final var targetImageInfos =
      packListOrNull(stack, imageInfos);
    final var targetTexelBufferViews =
      packLongsOrNull(
        stack,
        texelViews,
        VulkanLWJGLWriteDescriptorSets::viewHandle);

    target
      .sType(VK10.VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET)
      .pNext(0L)
      .pBufferInfo(targetBufferInfos)
      .pImageInfo(targetImageInfos)
      .pTexelBufferView(targetTexelBufferViews)
      .descriptorType(source.descriptorType().value())
      .descriptorCount(source.descriptorCount())
      .dstArrayElement(source.destinationArrayElement())
      .dstBinding(source.destinationBinding())
      .dstSet(dst_set.handle());

    return target;
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

  public static VkWriteDescriptorSet.Buffer packList(
    final MemoryStack stack,
    final List<VulkanWriteDescriptorSet> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      VulkanLWJGLWriteDescriptorSets::packInto,
      (sstack, count) -> VkWriteDescriptorSet.malloc(count, sstack),
      stack);
  }

  private static long viewHandle(final VulkanBufferViewType value)
    throws VulkanIncompatibleClassException
  {
    return checkInstanceOf(value, VulkanLWJGLBufferView.class).handle();
  }
}
