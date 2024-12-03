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
import com.io7m.jcoronado.api.VulkanPipelineRasterizationStateCreateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineRasterizationStateCreateInfo;

import java.util.Objects;
import java.util.Optional;

/**
 * Functions to pack pipeline creation info.
 */

public final class VulkanLWJGLPipelineRasterizationStateCreateInfos
{
  private VulkanLWJGLPipelineRasterizationStateCreateInfos()
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

  public static VkPipelineRasterizationStateCreateInfo pack(
    final MemoryStack stack,
    final VulkanPipelineRasterizationStateCreateInfo info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    final var target =
      VkPipelineRasterizationStateCreateInfo.malloc(stack);

    return target
      .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO)
      .pNext(0L)
      .cullMode(VulkanEnumMaps.packValues(info.cullMode()))
      .depthBiasClamp(info.depthBiasClamp())
      .depthBiasConstantFactor(info.depthBiasConstantFactor())
      .depthBiasEnable(info.depthBiasEnable())
      .depthBiasSlopeFactor(info.depthBiasSlopeFactor())
      .depthClampEnable(info.depthClampEnable())
      .flags(VulkanEnumMaps.packValues(info.flags()))
      .frontFace(info.frontFace().value())
      .lineWidth(info.lineWidth())
      .polygonMode(info.polygonMode().value())
      .rasterizerDiscardEnable(info.rasterizerDiscardEnable());
  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param info  A structure
   *
   * @return A packed structure (or null)
   */

  public static VkPipelineRasterizationStateCreateInfo packOptional(
    final MemoryStack stack,
    final Optional<VulkanPipelineRasterizationStateCreateInfo> info)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(info, "info");

    return info.map(iinfo -> pack(stack, iinfo)).orElse(null);
  }
}