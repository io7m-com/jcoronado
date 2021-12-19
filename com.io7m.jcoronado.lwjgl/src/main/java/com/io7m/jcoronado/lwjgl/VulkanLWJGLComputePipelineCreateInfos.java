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

import com.io7m.jcoronado.api.VulkanComputePipelineCreateInfo;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkComputePipelineCreateInfo;

import java.util.List;
import java.util.Objects;

import static com.io7m.jcoronado.lwjgl.VulkanLWJGLClassChecks.checkInstanceOf;

/**
 * Functions to pack pipeline creation info.
 */

public final class VulkanLWJGLComputePipelineCreateInfos
{
  private VulkanLWJGLComputePipelineCreateInfos()
  {

  }

  /**
   * Pack a list of structures.
   *
   * @param stack          A stack
   * @param pipeline_infos A list of structure
   *
   * @return A packed list of structures
   *
   * @throws VulkanIncompatibleClassException If one or more incompatible classes were specified
   * @throws VulkanException                  On errors
   */

  public static VkComputePipelineCreateInfo.Buffer pack(
    final MemoryStack stack,
    final List<VulkanComputePipelineCreateInfo> pipeline_infos)
    throws VulkanException, VulkanIncompatibleClassException
  {
    Objects.requireNonNull(pipeline_infos, "pipeline_infos");

    final var buffer =
      VkComputePipelineCreateInfo.malloc(pipeline_infos.size(), stack);

    for (var index = 0; index < pipeline_infos.size(); ++index) {
      final var source = pipeline_infos.get(index);
      final var target = VkComputePipelineCreateInfo.create(buffer.address(index));
      packInto(stack, source, target);
    }

    return buffer;
  }

  private static VkComputePipelineCreateInfo packInto(
    final MemoryStack stack,
    final VulkanComputePipelineCreateInfo source,
    final VkComputePipelineCreateInfo target)
    throws VulkanException
  {
    return target.set(
      VK10.VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO,
      0L,
      VulkanEnumMaps.packValues(source.flags()),
      VulkanLWJGLPipelineShaderStageCreateInfos.pack(stack, source.stage()),
      checkInstanceOf(
        source.layout(),
        VulkanLWJGLPipelineLayout.class).handle(),
      mapBasePipelineHandle(source),
      source.basePipelineIndex().orElse(-1));
  }

  private static long mapBasePipelineHandle(
    final VulkanComputePipelineCreateInfo source)
    throws VulkanException
  {
    try {
      return source.basePipeline().map(pipe -> {
        try {
          return Long.valueOf(checkInstanceOf(
            pipe,
            VulkanLWJGLPipeline.class).handle());
        } catch (final VulkanIncompatibleClassException e) {
          throw new VulkanUncheckedException(e);
        }
      }).orElse(Long.valueOf(0L)).longValue();
    } catch (final VulkanUncheckedException e) {
      throw e.getCause();
    }
  }
}
