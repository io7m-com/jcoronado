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
 * The features supported by a physical Vulkan 1.2 device.
 *
 * @see "VkPhysicalDeviceVulkan12Features"
 */

@VulkanAPIStructType(vulkanStruct = "VkPhysicalDeviceVulkan12Features")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPhysicalDeviceFeatures12Type
{
  /**
   * bufferDeviceAddress indicates that the implementation supports accessing
   * buffer memory in shaders as storage buffers via an address queried from
   * vkGetBufferDeviceAddress.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean bufferDeviceAddress()
  {
    return false;
  }

  /**
   * bufferDeviceAddressCaptureReplay indicates that the implementation supports
   * saving and reusing buffer and device addresses, e.g. for trace capture and
   * replay.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean bufferDeviceAddressCaptureReplay()
  {
    return false;
  }

  /**
   * bufferDeviceAddressMultiDevice indicates that the implementation supports
   * the bufferDeviceAddress , rayTracingPipeline and rayQuery features for
   * logical devices created with multiple physical devices. If this feature is
   * not supported, buffer and acceleration structure addresses must not be
   * queried on a logical device created with more than one physical device.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean bufferDeviceAddressMultiDevice()
  {
    return false;
  }

  /**
   * descriptorBindingPartiallyBound indicates whether the implementation
   * supports statically using a descriptor set binding in which some
   * descriptors are not valid. If this feature is not enabled,
   * VK_DESCRIPTOR_BINDING_PARTIALLY_BOUND_BIT must not be used.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean descriptorBindingPartiallyBound()
  {
    return false;
  }

  /**
   * descriptorBindingSampledImageUpdateAfterBind indicates whether the
   * implementation supports updating sampled image descriptors after a set is
   * bound. If this feature is not enabled, VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND_BIT
   * must not be used with VK_DESCRIPTOR_TYPE_SAMPLER, VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER,
   * or VK_DESCRIPTOR_TYPE_SAMPLED_IMAGE.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean descriptorBindingSampledImageUpdateAfterBind()
  {
    return false;
  }

  /**
   * descriptorBindingStorageBufferUpdateAfterBind indicates whether the
   * implementation supports updating storage buffer descriptors after a set is
   * bound. If this feature is not enabled, VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND_BIT
   * must not be used with VK_DESCRIPTOR_TYPE_STORAGE_BUFFER.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean descriptorBindingStorageBufferUpdateAfterBind()
  {
    return false;
  }

  /**
   * descriptorBindingStorageImageUpdateAfterBind indicates whether the
   * implementation supports updating storage image descriptors after a set is
   * bound. If this feature is not enabled, VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND_BIT
   * must not be used with VK_DESCRIPTOR_TYPE_STORAGE_IMAGE.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean descriptorBindingStorageImageUpdateAfterBind()
  {
    return false;
  }

  /**
   * descriptorBindingStorageTexelBufferUpdateAfterBind indicates whether the
   * implementation supports updating storage texel buffer descriptors after a
   * set is bound. If this feature is not enabled, VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND_BIT
   * must not be used with VK_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean descriptorBindingStorageTexelBufferUpdateAfterBind()
  {
    return false;
  }

  /**
   * descriptorBindingUniformBufferUpdateAfterBind indicates whether the
   * implementation supports updating uniform buffer descriptors after a set is
   * bound. If this feature is not enabled, VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND_BIT
   * must not be used with VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean descriptorBindingUniformBufferUpdateAfterBind()
  {
    return false;
  }

  /**
   * descriptorBindingUniformTexelBufferUpdateAfterBind indicates whether the
   * implementation supports updating uniform texel buffer descriptors after a
   * set is bound. If this feature is not enabled, VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND_BIT
   * must not be used with VK_DESCRIPTOR_TYPE_UNIFORM_TEXEL_BUFFER.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean descriptorBindingUniformTexelBufferUpdateAfterBind()
  {
    return false;
  }

  /**
   * descriptorBindingUpdateUnusedWhilePending indicates whether the
   * implementation supports updating descriptors while the set is in use. If
   * this feature is not enabled, VK_DESCRIPTOR_BINDING_UPDATE_UNUSED_WHILE_PENDING_BIT
   * must not be used.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean descriptorBindingUpdateUnusedWhilePending()
  {
    return false;
  }

  /**
   * descriptorBindingVariableDescriptorCount indicates whether the
   * implementation supports descriptor sets with a variable-sized last binding.
   * If this feature is not enabled, VK_DESCRIPTOR_BINDING_VARIABLE_DESCRIPTOR_COUNT_BIT
   * must not be used.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean descriptorBindingVariableDescriptorCount()
  {
    return false;
  }

  /**
   * descriptorIndexing indicates whether the implementation supports the
   * minimum set of descriptor indexing features as described in the Feature
   * Requirements section. Enabling the descriptorIndexing member when
   * vkCreateDevice is called does not imply the other minimum descriptor
   * indexing features are also enabled. Those other descriptor indexing
   * features must be enabled individually as needed by the application.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean descriptorIndexing()
  {
    return false;
  }

  /**
   * drawIndirectCount indicates whether the implementation supports the
   * vkCmdDrawIndirectCount and vkCmdDrawIndexedIndirectCount functions. If this
   * feature is not enabled, these functions must not be used.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean drawIndirectCount()
  {
    return false;
  }

  /**
   * hostQueryReset indicates that the implementation supports resetting queries
   * from the host with vkResetQueryPool.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean hostQueryReset()
  {
    return false;
  }

  /**
   * imagelessFramebuffer indicates that the implementation supports specifying
   * the image view for attachments at render pass begin time via
   * VkRenderPassAttachmentBeginInfo.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean imagelessFramebuffer()
  {
    return false;
  }

  /**
   * runtimeDescriptorArray indicates whether the implementation supports the
   * SPIR-V RuntimeDescriptorArray capability. If this feature is not enabled,
   * descriptors must not be declared in runtime arrays.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean runtimeDescriptorArray()
  {
    return false;
  }

  /**
   * samplerFilterMinmax indicates whether the implementation supports a minimum
   * set of required formats supporting min/max filtering as defined by the
   * filterMinmaxSingleComponentFormats property minimum requirements. If this
   * feature is not enabled, then no VkSamplerCreateInfo pNext chain can include
   * a VkSamplerReductionModeCreateInfo structure.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean samplerFilterMinmax()
  {
    return false;
  }

  /**
   * samplerMirrorClampToEdge indicates whether the implementation supports the
   * VK_SAMPLER_ADDRESS_MODE_MIRROR_CLAMP_TO_EDGE sampler address mode. If this
   * feature is not enabled, the VK_SAMPLER_ADDRESS_MODE_MIRROR_CLAMP_TO_EDGE
   * sampler address mode must not be used.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean samplerMirrorClampToEdge()
  {
    return false;
  }

  /**
   * scalarBlockLayout indicates that the implementation supports the layout of
   * resource blocks in shaders using scalar alignment.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean scalarBlockLayout()
  {
    return false;
  }

  /**
   * separateDepthStencilLayouts indicates whether the implementation supports a
   * VkImageMemoryBarrier for a depth/stencil image with only one of
   * VK_IMAGE_ASPECT_DEPTH_BIT or VK_IMAGE_ASPECT_STENCIL_BIT set, and whether
   * VK_IMAGE_LAYOUT_DEPTH_ATTACHMENT_OPTIMAL, VK_IMAGE_LAYOUT_DEPTH_READ_ONLY_OPTIMAL,
   * VK_IMAGE_LAYOUT_STENCIL_ATTACHMENT_OPTIMAL, or VK_IMAGE_LAYOUT_STENCIL_READ_ONLY_OPTIMAL
   * can be used.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean separateDepthStencilLayouts()
  {
    return false;
  }

  /**
   * shaderBufferInt64Atomics indicates whether shaders can perform 64-bit
   * unsigned and signed integer atomic operations on buffers.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderBufferInt64Atomics()
  {
    return false;
  }

  /**
   * shaderFloat16 indicates whether 16-bit floats (halfs) are supported in
   * shader code. This also indicates whether shader modules can declare the
   * Float16 capability. However, this only enables a subset of the storage
   * classes that SPIR-V allows for the Float16 SPIR-V capability: Declaring and
   * using 16-bit floats in the Private, Workgroup (for non-Block variables),
   * and Function storage classes is enabled, while declaring them in the
   * interface storage classes (e.g., UniformConstant, Uniform, StorageBuffer,
   * Input, Output, and PushConstant) is not enabled.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderFloat16()
  {
    return false;
  }

  /**
   * shaderInputAttachmentArrayDynamicIndexing indicates whether arrays of input
   * attachments can be indexed by dynamically uniform integer expressions in
   * shader code. If this feature is not enabled, resources with a descriptor
   * type of VK_DESCRIPTOR_TYPE_INPUT_ATTACHMENT must be indexed only by
   * constant integral expressions when aggregated into arrays in shader code.
   * This also indicates whether shader modules can declare the
   * InputAttachmentArrayDynamicIndexing capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderInputAttachmentArrayDynamicIndexing()
  {
    return false;
  }

  /**
   * shaderInputAttachmentArrayNonUniformIndexing indicates whether arrays of
   * input attachments can be indexed by non-uniform integer expressions in
   * shader code. If this feature is not enabled, resources with a descriptor
   * type of VK_DESCRIPTOR_TYPE_INPUT_ATTACHMENT must not be indexed by
   * non-uniform integer expressions when aggregated into arrays in shader code.
   * This also indicates whether shader modules can declare the
   * InputAttachmentArrayNonUniformIndexing capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderInputAttachmentArrayNonUniformIndexing()
  {
    return false;
  }

  /**
   * shaderInt8 indicates whether 8-bit integers (signed and unsigned) are
   * supported in shader code. This also indicates whether shader modules can
   * declare the Int8 capability. However, this only enables a subset of the
   * storage classes that SPIR-V allows for the Int8 SPIR-V capability:
   * Declaring and using 8-bit integers in the Private, Workgroup (for non-Block
   * variables), and Function storage classes is enabled, while declaring them
   * in the interface storage classes (e.g., UniformConstant, Uniform,
   * StorageBuffer, Input, Output, and PushConstant) is not enabled.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderInt8()
  {
    return false;
  }

  /**
   * shaderOutputLayer indicates whether the implementation supports the
   * ShaderLayer SPIR-V capability enabling variables decorated with the Layer
   * built-in to be exported from vertex or tessellation evaluation shaders. If
   * this feature is not enabled, the Layer built-in decoration must not be used
   * on outputs in vertex or tessellation evaluation shaders.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderOutputLayer()
  {
    return false;
  }

  /**
   * shaderOutputViewportIndex indicates whether the implementation supports the
   * ShaderViewportIndex SPIR-V capability enabling variables decorated with the
   * ViewportIndex built-in to be exported from vertex or tessellation
   * evaluation shaders. If this feature is not enabled, the ViewportIndex
   * built-in decoration must not be used on outputs in vertex or tessellation
   * evaluation shaders.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderOutputViewportIndex()
  {
    return false;
  }

  /**
   * shaderSampledImageArrayNonUniformIndexing indicates whether arrays of
   * samplers or sampled images can be indexed by non-uniform integer
   * expressions in shader code. If this feature is not enabled, resources with
   * a descriptor type of VK_DESCRIPTOR_TYPE_SAMPLER, VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER,
   * or VK_DESCRIPTOR_TYPE_SAMPLED_IMAGE must not be indexed by non-uniform
   * integer expressions when aggregated into arrays in shader code. This also
   * indicates whether shader modules can declare the SampledImageArrayNonUniformIndexing
   * capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderSampledImageArrayNonUniformIndexing()
  {
    return false;
  }

  /**
   * shaderSharedInt64Atomics indicates whether shaders can perform 64-bit
   * unsigned and signed integer atomic operations on shared memory.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderSharedInt64Atomics()
  {
    return false;
  }

  /**
   * shaderStorageBufferArrayNonUniformIndexing indicates whether arrays of
   * storage buffers can be indexed by non-uniform integer expressions in shader
   * code. If this feature is not enabled, resources with a descriptor type of
   * VK_DESCRIPTOR_TYPE_STORAGE_BUFFER or VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC
   * must not be indexed by non-uniform integer expressions when aggregated into
   * arrays in shader code. This also indicates whether shader modules can
   * declare the StorageBufferArrayNonUniformIndexing capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderStorageBufferArrayNonUniformIndexing()
  {
    return false;
  }

  /**
   * shaderStorageImageArrayNonUniformIndexing indicates whether arrays of
   * storage images can be indexed by non-uniform integer expressions in shader
   * code. If this feature is not enabled, resources with a descriptor type of
   * VK_DESCRIPTOR_TYPE_STORAGE_IMAGE must not be indexed by non-uniform integer
   * expressions when aggregated into arrays in shader code. This also indicates
   * whether shader modules can declare the StorageImageArrayNonUniformIndexing
   * capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderStorageImageArrayNonUniformIndexing()
  {
    return false;
  }

  /**
   * shaderStorageTexelBufferArrayDynamicIndexing indicates whether arrays of
   * storage texel buffers can be indexed by dynamically uniform integer
   * expressions in shader code. If this feature is not enabled, resources with
   * a descriptor type of VK_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER must be
   * indexed only by constant integral expressions when aggregated into arrays
   * in shader code. This also indicates whether shader modules can declare the
   * StorageTexelBufferArrayDynamicIndexing capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderStorageTexelBufferArrayDynamicIndexing()
  {
    return false;
  }

  /**
   * shaderStorageTexelBufferArrayNonUniformIndexing indicates whether arrays of
   * storage texel buffers can be indexed by non-uniform integer expressions in
   * shader code. If this feature is not enabled, resources with a descriptor
   * type of VK_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER must not be indexed by
   * non-uniform integer expressions when aggregated into arrays in shader code.
   * This also indicates whether shader modules can declare the
   * StorageTexelBufferArrayNonUniformIndexing capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderStorageTexelBufferArrayNonUniformIndexing()
  {
    return false;
  }

  /**
   * shaderSubgroupExtendedTypes is a boolean specifying whether subgroup
   * operations can use 8-bit integer, 16-bit integer, 64-bit integer, 16-bit
   * floating-point, and vectors of these types in group operations with
   * subgroup scope, if the implementation supports the types.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderSubgroupExtendedTypes()
  {
    return false;
  }

  /**
   * shaderUniformBufferArrayNonUniformIndexing indicates whether arrays of
   * uniform buffers can be indexed by non-uniform integer expressions in shader
   * code. If this feature is not enabled, resources with a descriptor type of
   * VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER or VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC
   * must not be indexed by non-uniform integer expressions when aggregated into
   * arrays in shader code. This also indicates whether shader modules can
   * declare the UniformBufferArrayNonUniformIndexing capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderUniformBufferArrayNonUniformIndexing()
  {
    return false;
  }

  /**
   * shaderUniformTexelBufferArrayDynamicIndexing indicates whether arrays of
   * uniform texel buffers can be indexed by dynamically uniform integer
   * expressions in shader code. If this feature is not enabled, resources with
   * a descriptor type of VK_DESCRIPTOR_TYPE_UNIFORM_TEXEL_BUFFER must be
   * indexed only by constant integral expressions when aggregated into arrays
   * in shader code. This also indicates whether shader modules can declare the
   * UniformTexelBufferArrayDynamicIndexing capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderUniformTexelBufferArrayDynamicIndexing()
  {
    return false;
  }

  /**
   * shaderUniformTexelBufferArrayNonUniformIndexing indicates whether arrays of
   * uniform texel buffers can be indexed by non-uniform integer expressions in
   * shader code. If this feature is not enabled, resources with a descriptor
   * type of VK_DESCRIPTOR_TYPE_UNIFORM_TEXEL_BUFFER must not be indexed by
   * non-uniform integer expressions when aggregated into arrays in shader code.
   * This also indicates whether shader modules can declare the
   * UniformTexelBufferArrayNonUniformIndexing capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean shaderUniformTexelBufferArrayNonUniformIndexing()
  {
    return false;
  }

  /**
   * storageBuffer8BitAccess indicates whether objects in the StorageBuffer,
   * ShaderRecordBufferKHR, or PhysicalStorageBuffer storage class with the
   * Block decoration can have 8-bit integer members. If this feature is not
   * enabled, 8-bit integer members must not be used in such objects. This also
   * indicates whether shader modules can declare the StorageBuffer8BitAccess
   * capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean storageBuffer8BitAccess()
  {
    return false;
  }

  /**
   * storagePushConstant8 indicates whether objects in the PushConstant storage
   * class can have 8-bit integer members. If this feature is not enabled, 8-bit
   * integer members must not be used in such objects. This also indicates
   * whether shader modules can declare the StoragePushConstant8 capability.
   *
   * @return {@code true} if supported
   */

  @Value.Default
  default boolean storagePushConstant8()
  {
    return false;
  }

  /**
   * If subgroupBroadcastDynamicId is VK_TRUE, the “Id” operand of
   * OpGroupNonUniformBroadcast can be dynamically uniform within a subgroup,
   * and the “Index” operand of OpGroupNonUniformQuadBroadcast can be
   * dynamically uniform within the derivative group. If it is VK_FALSE, these
   * operands must be constants.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean subgroupBroadcastDynamicId()
  {
    return false;
  }

  /**
   * timelineSemaphore indicates whether semaphores created with a
   * VkSemaphoreType of VK_SEMAPHORE_TYPE_TIMELINE are supported.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean timelineSemaphore()
  {
    return false;
  }

  /**
   * uniformAndStorageBuffer8BitAccess indicates whether objects in the Uniform
   * storage class with the Block decoration can have 8-bit integer members. If
   * this feature is not enabled, 8-bit integer members must not be used in such
   * objects. This also indicates whether shader modules can declare the
   * UniformAndStorageBuffer8BitAccess capability.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean uniformAndStorageBuffer8BitAccess()
  {
    return false;
  }

  /**
   * uniformBufferStandardLayout indicates that the implementation supports the
   * same layouts for uniform buffers as for storage and other kinds of buffers.
   * See Standard Buffer Layout.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean uniformBufferStandardLayout()
  {
    return false;
  }

  /**
   * vulkanMemoryModel indicates whether the Vulkan Memory Model is supported,
   * as defined in Vulkan Memory Model. This also indicates whether shader
   * modules can declare the VulkanMemoryModel capability.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean vulkanMemoryModel()
  {
    return false;
  }

  /**
   * vulkanMemoryModelAvailabilityVisibilityChains indicates whether the Vulkan
   * Memory Model can use availability and visibility chains with more than one
   * element.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean vulkanMemoryModelAvailabilityVisibilityChains()
  {
    return false;
  }

  /**
   * vulkanMemoryModelDeviceScope indicates whether the Vulkan Memory Model can
   * use Device scope synchronization. This also indicates whether shader
   * modules can declare the VulkanMemoryModelDeviceScope capability.
   *
   * @return {@code true} if supported
   */
  @Value.Default
  default boolean vulkanMemoryModelDeviceScope()
  {
    return false;
  }
}
