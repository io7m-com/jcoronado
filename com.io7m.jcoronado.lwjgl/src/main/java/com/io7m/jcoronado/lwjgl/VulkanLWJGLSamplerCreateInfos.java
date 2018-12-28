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

import com.io7m.jcoronado.api.VulkanCompareOp;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanSamplerCreateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkSamplerCreateInfo;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack sampler creation info.
 */

public final class VulkanLWJGLSamplerCreateInfos
{
  private VulkanLWJGLSamplerCreateInfos()
  {

  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param info  A structure
   *
   * @return A packed structure
   */

  public static VkSamplerCreateInfo pack(
    final MemoryStack stack,
    final VulkanSamplerCreateInfo info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return packInto(info, VkSamplerCreateInfo.mallocStack(stack));
  }

  /**
   * Pack a structure.
   *
   * @param source The input structure
   * @param target The output structure
   *
   * @return A packed structure
   */

  public static VkSamplerCreateInfo packInto(
    final VulkanSamplerCreateInfo source,
    final VkSamplerCreateInfo target)
  {
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    final var compare_op = source.compareOp();
    final var anisotropy = source.maxAnisotropy();

    return target.set(
      VK10.VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO,
      0L,
      VulkanEnumMaps.packValues(source.flags()),
      source.magFilter().value(),
      source.minFilter().value(),
      source.mipmapMode().value(),
      source.addressModeU().value(),
      source.addressModeV().value(),
      source.addressModeW().value(),
      source.mipLodBias(),
      anisotropy.isPresent(),
      (float) anisotropy.orElse(1.0),
      compare_op.isPresent(),
      compare_op.orElse(VulkanCompareOp.VK_COMPARE_OP_ALWAYS).value(),
      source.minLod(),
      source.maxLod(),
      source.borderColor().value(),
      source.unnormalizedCoordinates());
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

  public static VkSamplerCreateInfo.Buffer packList(
    final MemoryStack stack,
    final List<VulkanSamplerCreateInfo> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.pack(
      infos,
      (sstack, value, output) -> packInto(value, output),
      (sstack, count) -> VkSamplerCreateInfo.mallocStack(count, sstack),
      stack);
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

  public static VkSamplerCreateInfo.Buffer packListOrNull(
    final MemoryStack stack,
    final List<VulkanSamplerCreateInfo> infos)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(infos, "infos");

    return VulkanLWJGLArrays.packOrNull(
      infos,
      (sstack, value, output) -> packInto(value, output),
      (sstack, count) -> VkSamplerCreateInfo.mallocStack(count, sstack),
      stack);
  }
}
