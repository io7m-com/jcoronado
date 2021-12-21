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
 * The features supported by a physical Vulkan 1.1 device.
 *
 * @see "VkPhysicalDeviceVulkan11Features"
 */

@VulkanAPIStructType(vulkanStruct = "VkPhysicalDeviceVulkan11Features")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPhysicalDeviceFeatures11Type
{
  /**
   * multiview specifies whether the implementation supports multiview rendering
   * within a render pass. If this feature is not enabled, the view mask of each
   * subpass must always be zero.
   *
   * @return {@code true if supported}
   */

  
  @Value.Default
  default boolean multiview()
  {
    return false;
  }

  /**
   * multiviewGeometryShader specifies whether the implementation supports
   * multiview rendering within a render pass, with geometry shaders. If this
   * feature is not enabled, then a pipeline compiled against a subpass with a
   * non-zero view mask must not include a geometry shader.
   *
   * @return {@code true if supported}
   */

  
  @Value.Default
  default boolean multiviewGeometryShader()
  {
    return false;
  }

  /**
   * multiviewTessellationShader specifies whether the implementation supports
   * multiview rendering within a render pass, with tessellation shaders. If
   * this feature is not enabled, then a pipeline compiled against a subpass
   * with a non-zero view mask must not include any tessellation shaders.
   *
   * @return {@code true if supported}
   */

  
  @Value.Default
  default boolean multiviewTessellationShader()
  {
    return false;
  }

  /**
   * protectedMemory specifies whether protected memory is supported.
   *
   * @return {@code true if supported}
   */

  
  @Value.Default
  default boolean protectedMemory()
  {
    return false;
  }

  /**
   * samplerYcbcrConversion specifies whether the implementation supports
   * sampler Y′CBCR conversion. If samplerYcbcrConversion is VK_FALSE, sampler
   * Y′CBCR conversion is not supported, and samplers using sampler Y′CBCR
   * conversion must not be used.
   *
   * @return {@code true if supported}
   */

  
  @Value.Default
  default boolean samplerYcbcrConversion()
  {
    return false;
  }

  /**
   * shaderDrawParameters specifies whether the implementation supports the
   * SPIR-V DrawParameters capability. When this feature is not enabled, shader
   * modules must not declare the SPV_KHR_shader_draw_parameters extension or
   * the DrawParameters capability.
   *
   * @return {@code true if supported}
   */

  
  @Value.Default
  default boolean shaderDrawParameters()
  {
    return false;
  }

  /**
   * storageBuffer16BitAccess specifies whether objects in the StorageBuffer,
   * ShaderRecordBufferKHR, or PhysicalStorageBuffer storage class with the
   * Block decoration can have 16-bit integer and 16-bit floating-point members.
   * If this feature is not enabled, 16-bit integer or 16-bit floating-point
   * members must not be used in such objects. This also specifies whether
   * shader modules can declare the StorageBuffer16BitAccess capability.
   *
   * @return {@code true if supported}
   */

  
  @Value.Default
  default boolean storageBuffer16BitAccess()
  {
    return false;
  }

  /**
   * storageInputOutput16 specifies whether objects in the Input and Output
   * storage classes can have 16-bit integer and 16-bit floating-point members.
   * If this feature is not enabled, 16-bit integer or 16-bit floating-point
   * members must not be used in such objects. This also specifies whether
   * shader modules can declare the StorageInputOutput16 capability.
   *
   * @return {@code true if supported}
   */

  
  @Value.Default
  default boolean storageInputOutput16()
  {
    return false;
  }

  /**
   * storagePushConstant16 specifies whether objects in the PushConstant storage
   * class can have 16-bit integer and 16-bit floating-point members. If this
   * feature is not enabled, 16-bit integer or floating-point members must not
   * be used in such objects. This also specifies whether shader modules can
   * declare the StoragePushConstant16 capability.
   *
   * @return {@code true if supported}
   */

  
  @Value.Default
  default boolean storagePushConstant16()
  {
    return false;
  }

  /**
   * uniformAndStorageBuffer16BitAccess specifies whether objects in the Uniform
   * storage class with the Block decoration can have 16-bit integer and 16-bit
   * floating-point members. If this feature is not enabled, 16-bit integer or
   * 16-bit floating-point members must not be used in such objects. This also
   * specifies whether shader modules can declare the UniformAndStorageBuffer16BitAccess
   * capability.
   *
   * @return {@code true if supported}
   */

  
  @Value.Default
  default boolean uniformAndStorageBuffer16BitAccess()
  {
    return false;
  }

  /**
   * variablePointers specifies whether the implementation supports the SPIR-V
   * VariablePointers capability. When this feature is not enabled, shader
   * modules must not declare the VariablePointers capability.
   *
   * @return {@code true if supported}
   */

  
  @Value.Default
  default boolean variablePointers()
  {
    return false;
  }

  /**
   * variablePointersStorageBuffer specifies whether the implementation supports
   * the SPIR-V VariablePointersStorageBuffer capability. When this feature is
   * not enabled, shader modules must not declare the SPV_KHR_variable_pointers
   * extension or the VariablePointersStorageBuffer capability.
   *
   * @return {@code true if supported}
   */

  
  @Value.Default
  default boolean variablePointersStorageBuffer()
  {
    return false;
  }
}
