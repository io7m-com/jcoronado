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
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanPipelineShaderStageCreateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineShaderStageCreateInfo;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack pipeline shader stage create infos.
 */

public final class VulkanLWJGLPipelineShaderStageCreateInfos
{
  private VulkanLWJGLPipelineShaderStageCreateInfos()
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
   * @throws VulkanIncompatibleClassException If an incompatible class is specified
   */

  public static VkPipelineShaderStageCreateInfo pack(
    final MemoryStack stack,
    final VulkanPipelineShaderStageCreateInfo info)
    throws VulkanIncompatibleClassException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    final var module =
      VulkanLWJGLClassChecks.checkInstanceOf(info.module(), VulkanLWJGLShaderModule.class);

    final var target =
      VkPipelineShaderStageCreateInfo.mallocStack(stack);

    return packInto(stack, info, module, target);
  }

  private static VkPipelineShaderStageCreateInfo packInto(
    final MemoryStack stack,
    final VulkanPipelineShaderStageCreateInfo source,
    final VulkanLWJGLShaderModule module,
    final VkPipelineShaderStageCreateInfo target)
  {
    return target
      .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO)
      .pNext(0L)
      .flags(VulkanEnumMaps.packValues(source.flags()))
      .stage(source.stage().value())
      .module(module.handle())
      .pName(stack.ASCII(source.shaderEntryPoint()))
      .pSpecializationInfo(VulkanLWJGLSpecializationInfos.packOptional(
        stack, source.specializationInfo()));
  }

  /**
   * Pack all structures.
   *
   * @param stack  A stack
   * @param stages A list of structures
   *
   * @return A packed list of structures
   *
   * @throws VulkanIncompatibleClassException If an incompatible class is specified
   */

  public static VkPipelineShaderStageCreateInfo.Buffer packAll(
    final MemoryStack stack,
    final List<VulkanPipelineShaderStageCreateInfo> stages)
    throws VulkanIncompatibleClassException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(stages, "stages");

    final var buffer =
      VkPipelineShaderStageCreateInfo.mallocStack(stages.size(), stack);

    for (var index = 0; index < stages.size(); ++index) {
      final var source =
        stages.get(index);
      final var module =
        VulkanLWJGLClassChecks.checkInstanceOf(source.module(), VulkanLWJGLShaderModule.class);
      final var target =
        VkPipelineShaderStageCreateInfo.create(buffer.address(index));
      packInto(stack, source, module, target);
    }

    return buffer;
  }
}
