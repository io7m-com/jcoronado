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

import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutBinding;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkDescriptorSetLayoutBinding;

import java.util.Objects;

import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLClassChecks.checkInstanceOf;

/**
 * Functions to pack descriptor set layout bindings.
 */

public final class VulkanLWJGLDescriptorSetLayoutBindings
{
  private VulkanLWJGLDescriptorSetLayoutBindings()
  {

  }

  /**
   * Pack a value into a Vulkan structure.
   *
   * @param stack   The stack used for allocations
   * @param binding The input value
   *
   * @return A packed Vulkan structure
   *
   * @throws VulkanException On errors
   */

  public static VkDescriptorSetLayoutBinding pack(
    final MemoryStack stack,
    final VulkanDescriptorSetLayoutBinding binding)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(binding, "binding");

    final var buffer = VkDescriptorSetLayoutBinding.calloc(stack);
    packInto(stack, binding, buffer);
    return buffer;
  }

  /**
   * Pack a value into a Vulkan structure.
   *
   * @param stack  The stack used for allocations
   * @param source The input value
   * @param target The output structure
   *
   * @return A packed Vulkan structure
   *
   * @throws VulkanException On errors
   */

  public static VkDescriptorSetLayoutBinding packInto(
    final MemoryStack stack,
    final VulkanDescriptorSetLayoutBinding source,
    final VkDescriptorSetLayoutBinding target)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(source, "binding");
    Objects.requireNonNull(target, "output");

    final var binding_index = source.binding();
    final var descriptor_type = source.descriptorType().value();
    final var descriptor_count = source.descriptorCount();

    final var samplers =
      VulkanLWJGLScalarArrays.packLongsOrNull(
        stack,
        source.immutableSamplers(),
        sampler -> checkInstanceOf(sampler, VulkanLWJGLSampler.class).handle());

    final var stage_flags =
      VulkanEnumMaps.packValues(source.stageFlags());

    target.set(
      binding_index,
      descriptor_type,
      descriptor_count,
      stage_flags,
      samplers);
    return target;
  }
}
