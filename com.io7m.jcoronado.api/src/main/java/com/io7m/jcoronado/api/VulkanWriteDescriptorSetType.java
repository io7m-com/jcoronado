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

/**
 * @see "VkWriteDescriptorSet"
 */

@VulkanAPIStructType(vulkanStruct = "VkWriteDescriptorSet")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanWriteDescriptorSetType
{
  /**
   * @return The destination descriptor set to update.
   */

  @Value.Parameter
  VulkanDescriptorSetType destinationSet();

  /**
   * @return The descriptor binding within that set.
   */

  @Value.Parameter
  int destinationBinding();

  /**
   * @return The starting element in that array. If the descriptor binding identified by
   * destinationSet and destinationBinding has a descriptor type of VK_DESCRIPTOR_TYPE_INLINE_UNIFORM_BLOCK_EXT
   * then destinationArrayElement specifies the starting byte offset within the binding.
   */

  @Value.Parameter
  @Value.Default
  default int destinationArrayElement()
  {
    return 0;
  }

  /**
   * @return The number of descriptors to update (the number of elements in imageInfos, bufferInfos,
   * or texelBufferViews , or a value matching the dataSize member of an instance of
   * VkWriteDescriptorSetInlineUniformBlockEXT in the pNext chain , or a value matching the
   * accelerationStructureCount of an instance of VkWriteDescriptorSetAccelerationStructureNV in the
   * pNext chain ). If the descriptor binding identified by destinationSet and destinationBinding
   * has a descriptor type of VK_DESCRIPTOR_TYPE_INLINE_UNIFORM_BLOCK_EXT then descriptorCount
   * specifies the number of bytes to update.
   */

  @Value.Parameter
  int descriptorCount();

  /**
   * @return A VkDescriptorType specifying the type of each descriptor in imageInfos, bufferInfos,
   * or texelBufferViews, as described below. It must be the same type as that specified in
   * VkDescriptorSetLayoutBinding for destinationSet at destinationBinding. The type of the
   * descriptor also controls which array the descriptors are taken from.
   */

  @Value.Parameter
  VulkanDescriptorType descriptorType();

  /**
   * @return An array of VulkanDescriptorImageInfo structures
   */

  @Value.Parameter
  List<VulkanDescriptorImageInfo> imageInfos();


  /**
   * @return An array of VulkanDescriptorBufferInfo structures
   */

  @Value.Parameter
  List<VulkanDescriptorBufferInfo> bufferInfos();

  /**
   * @return An array of VulkanBufferViewType handles
   */

  @Value.Parameter
  List<VulkanBufferViewType> texelBufferViews();
}
