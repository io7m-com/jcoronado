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

package com.io7m.jcoronado.api;

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

import java.util.List;
import java.util.Set;

/**
 * Structure specifying a descriptor set layout binding.
 *
 * @see "VkDescriptorSetLayoutBinding"
 */

@VulkanAPIStructType(vulkanStruct = "VkDescriptorSetLayoutBinding")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanDescriptorSetLayoutBindingType
{
  /**
   * @return The binding number of this entry and corresponds to a resource of the same binding
   * number in the shader stages
   */

  @Value.Parameter
  int binding();

  /**
   * @return A VulkanDescriptorType specifying which type of resource descriptors are used for this
   * binding.
   */

  @Value.Parameter
  VulkanDescriptorType descriptorType();

  /**
   * @return The number of descriptors contained in the binding, accessed in a shader as an array ,
   * except if descriptorType is VK_DESCRIPTOR_TYPE_INLINE_UNIFORM_BLOCK_EXT in which case
   * descriptorCount is the size in bytes of the inline uniform block . If descriptorCount is zero
   * this binding entry is reserved and the resource must not be accessed from any stage via this
   * binding within any pipeline using the set layout.
   */

  @Value.Parameter
  int descriptorCount();

  /**
   * @return A set of VulkanShaderStageFlag values specifying which pipeline shader stages can
   * access a resource for this binding. VK_SHADER_STAGE_ALL is a shorthand specifying that all
   * defined shader stages, including any additional stages defined by extensions, can access the
   * resource.
   */

  @Value.Parameter
  Set<VulkanShaderStageFlag> stageFlags();

  /**
   * @return A set of samplers to be initialized
   */

  @Value.Parameter
  List<VulkanSamplerType> immutableSamplers();
}
