/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

/**
 * The features supported by a physical Vulkan 1.3 device.
 *
 * @see "VkPhysicalDeviceVulkan13Features"
 */

@VulkanAPIStructType(vulkanStruct = "VkPhysicalDeviceVulkan13Features")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPhysicalDeviceFeatures13Type
{
  /**
   * Indicates whether image accesses are tightly bounds-checked against the
   * dimensions of the image view. Invalid texels resulting from out of bounds
   * image loads will be replaced as described in Texel Replacement, with either
   * (0,0,1) or (0,0,0) values inserted for missing G, B, or A components
   * based on the format.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean robustImageAccess()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports inline uniform block
   * descriptors. If this feature is not enabled,
   * VK_DESCRIPTOR_TYPE_INLINE_UNIFORM_BLOCK must not be used.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean inlineUniformBlock()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports updating inline uniform
   * block descriptors after a set is bound. If this feature is not
   * enabled, VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND_BIT must not be used
   * with VK_DESCRIPTOR_TYPE_INLINE_UNIFORM_BLOCK.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean descriptorBindingInlineUniformBlockUpdateAfterBind()
  {
    return false;
  }

  /**
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean pipelineCreationCacheControl()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports private data.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean privateData()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports the SPIR-V
   * DemoteToHelperInvocationEXT capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderDemoteToHelperInvocation()
  {
    return false;
  }

  /**
   * Specifies whether the implementation supports SPIR-V modules that use
   * the SPV_KHR_terminate_invocation extension.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderTerminateInvocation()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports controlling shader subgroup
   * sizes via the VK_PIPELINE_SHADER_STAGE_CREATE_ALLOW_VARYING_SUBGROUP_SIZE_BIT
   * flag and the VkPipelineShaderStageRequiredSubgroupSizeCreateInfo structure.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean subgroupSizeControl()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports requiring full subgroups in
   * compute, mesh, or task shaders via the
   * VK_PIPELINE_SHADER_STAGE_CREATE_REQUIRE_FULL_SUBGROUPS_BIT flag.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean computeFullSubgroups()
  {
    return false;
  }

  /**
   * Indicates whether the implementation supports the new set of synchronization
   * commands introduced in VK_KHR_synchronization2.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean synchronization2()
  {
    return false;
  }

  /**
   * Indicates whether all of the ASTC HDR compressed texture formats are
   * supported.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean textureCompressionASTC_HDR()
  {
    return false;
  }

  /**
   * Specifies whether the implementation supports initializing a variable in
   * Workgroup storage class.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderZeroInitializeWorkgroupMemory()
  {
    return false;
  }

  /**
   * Specifies that the implementation supports dynamic render pass instances
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean dynamicRendering()
  {
    return false;
  }

  /**
   * Specifies whether shader modules can declare the DotProductInputAllKHR,
   * DotProductInput4x8BitKHR, DotProductInput4x8BitPackedKHR and
   * DotProductKHR capabilities.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderIntegerDotProduct()
  {
    return false;
  }

  /**
   * Indicates that the implementation supports maintenance4.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean maintenance4()
  {
    return false;
  }
}
