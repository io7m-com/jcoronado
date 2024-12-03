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
 * The features supported by a physical device.
 *
 * @see "VkPhysicalDeviceFeatures"
 */

@VulkanAPIStructType(vulkanStruct = "VkPhysicalDeviceFeatures")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPhysicalDeviceFeatures10Type
{
  /**
   * @return alphaToOne : specifies whether the implementation is able to replace the alpha value of
   * the color fragment output from the fragment shader with the maximum representable alpha value
   * for fixed-point colors or 1.0 for floating-point colors. If this feature is not enabled, then
   * the alphaToOneEnable member of the VkPipelineMultisampleStateCreateInfo structure must be set
   * to VK_FALSE. Otherwise setting alphaToOneEnable to VK_TRUE will enable alpha-to-one behavior.
   */


  @Value.Default
  default boolean alphaToOne()
  {
    return false;
  }

  /**
   * @return depthBiasClamp : specifies whether depth bias clamping is supported. If this feature is
   * not enabled, the depthBiasClamp member of the VkPipelineRasterizationStateCreateInfo structure
   * must be set to 0.0 unless the VK_DYNAMIC_STATE_DEPTH_BIAS dynamic state is enabled, and the
   * depthBiasClamp parameter to vkCmdSetDepthBias must be set to 0.0.
   */


  @Value.Default
  default boolean depthBiasClamp()
  {
    return false;
  }

  /**
   * @return depthBounds : specifies whether depth bounds tests are supported. If this feature is
   * not enabled, the depthBoundsTestEnable member of the VkPipelineDepthStencilStateCreateInfo
   * structure must be set to VK_FALSE. When depthBoundsTestEnable is set to VK_FALSE, the
   * minDepthBounds and maxDepthBounds members of the VkPipelineDepthStencilStateCreateInfo
   * structure are ignored.
   */


  @Value.Default
  default boolean depthBounds()
  {
    return false;
  }

  /**
   * @return depthClamp : specifies whether depth clamping is supported. If this feature is not
   * enabled, the depthClampEnable member of the VkPipelineRasterizationStateCreateInfo structure
   * must be set to VK_FALSE. Otherwise, setting depthClampEnable to VK_TRUE will enable depth
   * clamping.
   */


  @Value.Default
  default boolean depthClamp()
  {
    return false;
  }

  /**
   * @return drawIndirectFirstInstance : specifies whether indirect draw calls support the
   * firstInstance parameter. If this feature is not enabled, the firstInstance member of all
   * VkDrawIndirectCommand and VkDrawIndexedIndirectCommand structures that are provided to the
   * vkCmdDrawIndirect and vkCmdDrawIndexedIndirect commands must be 0.
   */


  @Value.Default
  default boolean drawIndirectFirstInstance()
  {
    return false;
  }

  /**
   * @return dualSrcBlend : specifies whether blend operations which take two sources are supported.
   * If this feature is not enabled, the VK_BLEND_FACTOR_SRC1_COLOR, VK_BLEND_FACTOR_ONE_MINUS_SRC1_COLOR,
   * VK_BLEND_FACTOR_SRC1_ALPHA, and VK_BLEND_FACTOR_ONE_MINUS_SRC1_ALPHA enum values must not be
   * used as source or destination blending factors. See ../../html/vkspec.html#framebuffer-dsb.
   */


  @Value.Default
  default boolean dualSrcBlend()
  {
    return false;
  }

  /**
   * @return fillModeNonSolid : specifies whether point and wireframe fill modes are supported. If
   * this feature is not enabled, the VK_POLYGON_MODE_POINT and VK_POLYGON_MODE_LINE enum values
   * must not be used.
   */


  @Value.Default
  default boolean fillModeNonSolid()
  {
    return false;
  }

  /**
   * @return fragmentStoresAndAtomics : specifies whether storage buffers and images support stores
   * and atomic operations in the fragment shader stage. If this feature is not enabled, all storage
   * image, storage texel buffers, and storage buffer variables used by the fragment stage in shader
   * modules must be decorated with the NonWriteable decoration (or the readonly memory qualifier in
   * GLSL).
   */


  @Value.Default
  default boolean fragmentStoresAndAtomics()
  {
    return false;
  }

  /**
   * @return fullDrawIndexUint32 : specifies the full 32-bit range of indices is supported for
   * indexed draw calls when using a VkIndexType of VK_INDEX_TYPE_UINT32. maxDrawIndexedIndexValue
   * is the maximum index value that may be used (aside from the primitive restart index, which is
   * always 232-1 when the VkIndexType is VK_INDEX_TYPE_UINT32). If this feature is supported,
   * maxDrawIndexedIndexValue must be 232-1; otherwise it must be no smaller than 224-1. See
   * maxDrawIndexedIndexValue.
   */


  @Value.Default
  default boolean fullDrawIndexUint32()
  {
    return false;
  }

  /**
   * @return geometryShader : specifies whether geometry shaders are supported. If this feature is
   * not enabled, the VK_SHADER_STAGE_GEOMETRY_BIT and VK_PIPELINE_STAGE_GEOMETRY_SHADER_BIT enum
   * values must not be used. This also specifies whether shader modules can declare the Geometry
   * capability.
   */


  @Value.Default
  default boolean geometryShader()
  {
    return false;
  }

  /**
   * @return imageCubeArray : specifies whether image views with a VkImageViewType of
   * VK_IMAGE_VIEW_TYPE_CUBE_ARRAY can be created, and that the corresponding SampledCubeArray and
   * ImageCubeArray SPIR-V capabilities can be used in shader code.
   */


  @Value.Default
  default boolean imageCubeArray()
  {
    return false;
  }

  /**
   * @return independentBlend : specifies whether the VkPipelineColorBlendAttachmentState settings
   * are controlled independently per-attachment. If this feature is not enabled, the
   * VkPipelineColorBlendAttachmentState settings for all color attachments must be identical.
   * Otherwise, a different VkPipelineColorBlendAttachmentState can be provided for each bound color
   * attachment.
   */


  @Value.Default
  default boolean independentBlend()
  {
    return false;
  }

  /**
   * @return inheritedQueries : specifies whether a secondary command buffer may be executed while a
   * query is active.
   */


  @Value.Default
  default boolean inheritedQueries()
  {
    return false;
  }

  /**
   * @return largePoints : specifies whether points with size greater than 1.0 are supported. If
   * this feature is not enabled, only a point size of 1.0 written by a shader is supported. The
   * range and granularity of supported point sizes are indicated by the pointSizeRange and
   * pointSizeGranularity members of the VkPhysicalDeviceLimits structure, respectively.
   */


  @Value.Default
  default boolean largePoints()
  {
    return false;
  }

  /**
   * @return logicOp : specifies whether logic operations are supported. If this feature is not
   * enabled, the logicOpEnable member of the VkPipelineColorBlendStateCreateInfo structure must be
   * set to VK_FALSE, and the logicOp member is ignored.
   */


  @Value.Default
  default boolean logicOp()
  {
    return false;
  }

  /**
   * @return multiDrawIndirect : specifies whether multiple draw indirect is supported. If this
   * feature is not enabled, the drawCount parameter to the vkCmdDrawIndirect and
   * vkCmdDrawIndexedIndirect commands must be 0 or 1. The maxDrawIndirectCount member of the
   * VkPhysicalDeviceLimits structure must also be 1 if this feature is not supported. See
   * maxDrawIndirectCount.
   */


  @Value.Default
  default boolean multiDrawIndirect()
  {
    return false;
  }

  /**
   * @return multiViewport : specifies whether more than one viewport is supported. If this feature
   * is not enabled, the viewportCount and scissorCount members of the
   * VkPipelineViewportStateCreateInfo structure must be set to 1. Similarly, the viewportCount
   * parameter to the vkCmdSetViewport command and the scissorCount parameter to the vkCmdSetScissor
   * command must be 1, and the firstViewport parameter to the vkCmdSetViewport command and the
   * firstScissor parameter to the vkCmdSetScissor command must be 0.
   */


  @Value.Default
  default boolean multiViewport()
  {
    return false;
  }

  /**
   * @return occlusionQueryPrecise : specifies whether occlusion queries returning actual sample
   * counts are supported. Occlusion queries are created in a VkQueryPool by specifying the
   * queryType of VK_QUERY_TYPE_OCCLUSION in the VkQueryPoolCreateInfo structure which is passed to
   * vkCreateQueryPool. If this feature is enabled, queries of this type can enable
   * VK_QUERY_CONTROL_PRECISE_BIT in the flags parameter to vkCmdBeginQuery. If this feature is not
   * supported, the implementation supports only boolean occlusion queries. When any samples are
   * passed, boolean queries will return a non-zero result value, otherwise a result value of zero
   * is returned. When this feature is enabled and VK_QUERY_CONTROL_PRECISE_BIT is set, occlusion
   * queries will report the actual number of samples passed.
   */


  @Value.Default
  default boolean occlusionQueryPrecise()
  {
    return false;
  }

  /**
   * @return pipelineStatisticsQuery : specifies whether the pipeline statistics queries are
   * supported. If this feature is not enabled, queries of type VK_QUERY_TYPE_PIPELINE_STATISTICS
   * cannot be created, and none of the VkQueryPipelineStatisticFlagBits bits can be set in the
   * pipelineStatistics member of the VkQueryPoolCreateInfo structure.
   */


  @Value.Default
  default boolean pipelineStatisticsQuery()
  {
    return false;
  }

  /**
   * @return robustBufferAccess : specifies that accesses to buffers are bounds-checked against the
   * range of the buffer descriptor (as determined by VkDescriptorBufferInfo::range,
   * VkBufferViewCreateInfo::range, or the size of the buffer). Out of bounds accesses must not
   * cause application termination, and the effects of shader loads, stores, and atomics must
   * conform to an implementation-dependent behavior as described below.
   */


  @Value.Default
  default boolean robustBufferAccess()
  {
    return false;
  }

  /**
   * @return samplerAnisotropy : specifies whether anisotropic filtering is supported. If this
   * feature is not enabled, the anisotropyEnable member of the VkSamplerCreateInfo structure must
   * be VK_FALSE.
   */


  @Value.Default
  default boolean samplerAnisotropy()
  {
    return false;
  }

  /**
   * @return sampleRateShading : specifies whether Sample Shading and multisample interpolation are
   * supported. If this feature is not enabled, the sampleShadingEnable member of the
   * VkPipelineMultisampleStateCreateInfo structure must be set to VK_FALSE and the minSampleShading
   * member is ignored. This also specifies whether shader modules can declare the SampleRateShading
   * capability.
   */


  @Value.Default
  default boolean sampleRateShading()
  {
    return false;
  }

  /**
   * @return shaderClipDistance : specifies whether clip distances are supported in shader code. If
   * this feature is not enabled, any members decorated with the ClipDistance built-in decoration
   * must not be read from or written to in shader modules. This also specifies whether shader
   * modules can declare the ClipDistance capability.
   */


  @Value.Default
  default boolean shaderClipDistance()
  {
    return false;
  }

  /**
   * @return shaderCullDistance : specifies whether cull distances are supported in shader code. If
   * this feature is not enabled, any members decorated with the CullDistance built-in decoration
   * must not be read from or written to in shader modules. This also specifies whether shader
   * modules can declare the CullDistance capability.
   */


  @Value.Default
  default boolean shaderCullDistance()
  {
    return false;
  }

  /**
   * @return shaderFloat64 : specifies whether 64-bit floats (doubles) are supported in shader code.
   * If this feature is not enabled, 64-bit floating-point types must not be used in shader code.
   * This also specifies whether shader modules can declare the Float64 capability.
   */


  @Value.Default
  default boolean shaderFloat64()
  {
    return false;
  }

  /**
   * @return shaderImageGatherExtended : specifies whether the extended set of image gather
   * instructions are available in shader code. If this feature is not enabled, the OpImage*Gather
   * instructions do not support the Offset and ConstOffsets operands. This also specifies whether
   * shader modules can declare the ImageGatherExtended capability.
   */


  @Value.Default
  default boolean shaderImageGatherExtended()
  {
    return false;
  }

  /**
   * @return shaderInt16 : specifies whether 16-bit integers (signed and unsigned) are supported in
   * shader code. If this feature is not enabled, 16-bit integer types must not be used in shader
   * code. This also specifies whether shader modules can declare the Int16 capability.
   */


  @Value.Default
  default boolean shaderInt16()
  {
    return false;
  }

  /**
   * @return shaderInt64 : specifies whether 64-bit integers (signed and unsigned) are supported in
   * shader code. If this feature is not enabled, 64-bit integer types must not be used in shader
   * code. This also specifies whether shader modules can declare the Int64 capability.
   */


  @Value.Default
  default boolean shaderInt64()
  {
    return false;
  }

  /**
   * @return shaderResourceMinLod : specifies whether image operations that specify the minimum
   * resource LOD are supported in shader code. If this feature is not enabled, the MinLod image
   * operand must not be used in shader code. This also specifies whether shader modules can declare
   * the MinLod capability.
   */


  @Value.Default
  default boolean shaderResourceMinLod()
  {
    return false;
  }

  /**
   * @return shaderResourceResidency : specifies whether image operations that return resource
   * residency information are supported in shader code. If this feature is not enabled, the
   * OpImageSparse* instructions must not be used in shader code. This also specifies whether shader
   * modules can declare the SparseResidency capability. The feature requires at least one of the
   * sparseResidency* features to be supported.
   */


  @Value.Default
  default boolean shaderResourceResidency()
  {
    return false;
  }

  /**
   * @return shaderSampledImageArrayDynamicIndexing : specifies whether arrays of samplers or
   * sampled images can be indexed by dynamically uniform integer expressions in shader code. If
   * this feature is not enabled, resources with a descriptor type of VK_DESCRIPTOR_TYPE_SAMPLER,
   * VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER, or VK_DESCRIPTOR_TYPE_SAMPLED_IMAGE must be indexed
   * only by constant integral expressions when aggregated into arrays in shader code. This also
   * specifies whether shader modules can declare the SampledImageArrayDynamicIndexing capability.
   */


  @Value.Default
  default boolean shaderSampledImageArrayDynamicIndexing()
  {
    return false;
  }

  /**
   * @return shaderStorageBufferArrayDynamicIndexing : specifies whether arrays of storage buffers
   * can be indexed by dynamically uniform integer expressions in shader code. If this feature is
   * not enabled, resources with a descriptor type of VK_DESCRIPTOR_TYPE_STORAGE_BUFFER or
   * VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC must be indexed only by constant integral expressions
   * when aggregated into arrays in shader code. This also specifies whether shader modules can
   * declare the StorageBufferArrayDynamicIndexing capability.
   */


  @Value.Default
  default boolean shaderStorageBufferArrayDynamicIndexing()
  {
    return false;
  }

  /**
   * @return shaderStorageImageArrayDynamicIndexing : specifies whether arrays of storage images can
   * be indexed by dynamically uniform integer expressions in shader code. If this feature is not
   * enabled, resources with a descriptor type of VK_DESCRIPTOR_TYPE_STORAGE_IMAGE must be indexed
   * only by constant integral expressions when aggregated into arrays in shader code. This also
   * specifies whether shader modules can declare the StorageImageArrayDynamicIndexing capability.
   */


  @Value.Default
  default boolean shaderStorageImageArrayDynamicIndexing()
  {
    return false;
  }

  /**
   * @return shaderStorageImageExtendedFormats : specifies whether the extended storage image
   * formats are available in shader code. If this feature is not enabled, the formats requiring the
   * StorageImageExtendedFormats capability are not supported for storage images. This also
   * specifies whether shader modules can declare the StorageImageExtendedFormats capability.
   */


  @Value.Default
  default boolean shaderStorageImageExtendedFormats()
  {
    return false;
  }

  /**
   * @return shaderStorageImageMultisample : specifies whether multisampled storage images are
   * supported. If this feature is not enabled, images that are created with a usage that includes
   * VK_IMAGE_USAGE_STORAGE_BIT must be created with samples equal to VK_SAMPLE_COUNT_1_BIT. This
   * also specifies whether shader modules can declare the StorageImageMultisample capability.
   */


  @Value.Default
  default boolean shaderStorageImageMultisample()
  {
    return false;
  }

  /**
   * @return shaderStorageImageReadWithoutFormat : specifies whether storage images require a format
   * qualifier to be specified when reading from storage images. If this feature is not enabled, the
   * OpImageRead instruction must not have an OpTypeImage of Unknown. This also specifies whether
   * shader modules can declare the StorageImageReadWithoutFormat capability.
   */


  @Value.Default
  default boolean shaderStorageImageReadWithoutFormat()
  {
    return false;
  }

  /**
   * @return shaderStorageImageWriteWithoutFormat : specifies whether storage images require a
   * format qualifier to be specified when writing to storage images. If this feature is not
   * enabled, the OpImageWrite instruction must not have an OpTypeImage of Unknown. This also
   * specifies whether shader modules can declare the StorageImageWriteWithoutFormat capability.
   */


  @Value.Default
  default boolean shaderStorageImageWriteWithoutFormat()
  {
    return false;
  }

  /**
   * @return shaderTessellationAndGeometryPointSize : specifies whether the PointSize built-in
   * decoration is available in the tessellation control, tessellation evaluation, and geometry
   * shader stages. If this feature is not enabled, members decorated with the PointSize built-in
   * decoration must not be read from or written to and all points written from a tessellation or
   * geometry shader will have a size of 1.0. This also specifies whether shader modules can declare
   * the TessellationPointSize capability for tessellation control and evaluation shaders, or if the
   * shader modules can declare the GeometryPointSize capability for geometry shaders. An
   * implementation supporting this feature must also support one or both of the tessellationShader
   * or geometryShader features.
   */


  @Value.Default
  default boolean shaderTessellationAndGeometryPointSize()
  {
    return false;
  }

  /**
   * @return shaderUniformBufferArrayDynamicIndexing : specifies whether arrays of uniform buffers
   * can be indexed by dynamically uniform integer expressions in shader code. If this feature is
   * not enabled, resources with a descriptor type of VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER or
   * VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC must be indexed only by constant integral expressions
   * when aggregated into arrays in shader code. This also specifies whether shader modules can
   * declare the UniformBufferArrayDynamicIndexing capability.
   */


  @Value.Default
  default boolean shaderUniformBufferArrayDynamicIndexing()
  {
    return false;
  }

  /**
   * @return sparseBinding : specifies whether resource memory can be managed at opaque sparse block
   * level instead of at the object level. If this feature is not enabled, resource memory must be
   * bound only on a per-object basis using the vkBindBufferMemory and vkBindImageMemory commands.
   * In this case, buffers and images must not be created with VK_BUFFER_CREATE_SPARSE_BINDING_BIT
   * and VK_IMAGE_CREATE_SPARSE_BINDING_BIT set in the flags member of the VkBufferCreateInfo and
   * VkImageCreateInfo structures, respectively. Otherwise resource memory can be managed as
   * described in Sparse Resource Features.
   */


  @Value.Default
  default boolean sparseBinding()
  {
    return false;
  }

  /**
   * @return sparseResidency16Samples : specifies whether the physical device can access partially
   * resident 2D images with 16 samples per pixel. If this feature is not enabled, images with an
   * imageType of VK_IMAGE_TYPE_2D and samples set to VK_SAMPLE_COUNT_16_BIT must not be created
   * with VK_IMAGE_CREATE_SPARSE_RESIDENCY_BIT set in the flags member of the VkImageCreateInfo
   * structure.
   */


  @Value.Default
  default boolean sparseResidency16Samples()
  {
    return false;
  }

  /**
   * @return sparseResidency2Samples : specifies whether the physical device can access partially
   * resident 2D images with 2 samples per pixel. If this feature is not enabled, images with an
   * imageType of VK_IMAGE_TYPE_2D and samples set to VK_SAMPLE_COUNT_2_BIT must not be created with
   * VK_IMAGE_CREATE_SPARSE_RESIDENCY_BIT set in the flags member of the VkImageCreateInfo
   * structure.
   */


  @Value.Default
  default boolean sparseResidency2Samples()
  {
    return false;
  }

  /**
   * @return sparseResidency4Samples : specifies whether the physical device can access partially
   * resident 2D images with 4 samples per pixel. If this feature is not enabled, images with an
   * imageType of VK_IMAGE_TYPE_2D and samples set to VK_SAMPLE_COUNT_4_BIT must not be created with
   * VK_IMAGE_CREATE_SPARSE_RESIDENCY_BIT set in the flags member of the VkImageCreateInfo
   * structure.
   */


  @Value.Default
  default boolean sparseResidency4Samples()
  {
    return false;
  }

  /**
   * @return sparseResidency8Samples : specifies whether the physical device can access partially
   * resident 2D images with 8 samples per pixel. If this feature is not enabled, images with an
   * imageType of VK_IMAGE_TYPE_2D and samples set to VK_SAMPLE_COUNT_8_BIT must not be created with
   * VK_IMAGE_CREATE_SPARSE_RESIDENCY_BIT set in the flags member of the VkImageCreateInfo
   * structure.
   */


  @Value.Default
  default boolean sparseResidency8Samples()
  {
    return false;
  }

  /**
   * @return sparseResidencyAliased : specifies whether the physical device can correctly access
   * data aliased into multiple locations. If this feature is not enabled, the
   * VK_BUFFER_CREATE_SPARSE_ALIASED_BIT and VK_IMAGE_CREATE_SPARSE_ALIASED_BIT enum values must not
   * be used in flags members of the VkBufferCreateInfo and VkImageCreateInfo structures,
   * respectively.
   */


  @Value.Default
  default boolean sparseResidencyAliased()
  {
    return false;
  }

  /**
   * @return sparseResidencyBuffer : specifies whether the device can access partially resident
   * buffers. If this feature is not enabled, buffers must not be created with
   * VK_BUFFER_CREATE_SPARSE_RESIDENCY_BIT set in the flags member of the VkBufferCreateInfo
   * structure.
   */


  @Value.Default
  default boolean sparseResidencyBuffer()
  {
    return false;
  }

  /**
   * @return sparseResidencyImage2D : specifies whether the device can access partially resident 2D
   * images with 1 sample per pixel. If this feature is not enabled, images with an imageType of
   * VK_IMAGE_TYPE_2D and samples set to VK_SAMPLE_COUNT_1_BIT must not be created with
   * VK_IMAGE_CREATE_SPARSE_RESIDENCY_BIT set in the flags member of the VkImageCreateInfo
   * structure.
   */


  @Value.Default
  default boolean sparseResidencyImage2D()
  {
    return false;
  }

  /**
   * @return sparseResidencyImage3D : specifies whether the device can access partially resident 3D
   * images. If this feature is not enabled, images with an imageType of VK_IMAGE_TYPE_3D must not
   * be created with VK_IMAGE_CREATE_SPARSE_RESIDENCY_BIT set in the flags member of the
   * VkImageCreateInfo structure.
   */


  @Value.Default
  default boolean sparseResidencyImage3D()
  {
    return false;
  }

  /**
   * @return tessellationShader : specifies whether tessellation control and evaluation shaders are
   * supported. If this feature is not enabled, the VK_SHADER_STAGE_TESSELLATION_CONTROL_BIT,
   * VK_SHADER_STAGE_TESSELLATION_EVALUATION_BIT, VK_PIPELINE_STAGE_TESSELLATION_CONTROL_SHADER_BIT,
   * VK_PIPELINE_STAGE_TESSELLATION_EVALUATION_SHADER_BIT, and VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO
   * enum values must not be used. This also specifies whether shader modules can declare the
   * Tessellation capability.
   */


  @Value.Default
  default boolean tessellationShader()
  {
    return false;
  }

  /**
   * @return textureCompressionASTC_LDR : specifies whether all of the ASTC LDR compressed texture
   * formats are supported. vkGetPhysicalDeviceFormatProperties and vkGetPhysicalDeviceImageFormatProperties
   * can be used to check for additional supported properties of individual formats.
   */


  @Value.Default
  default boolean textureCompressionASTC_LDR()
  {
    return false;
  }

  /**
   * @return textureCompressionBC : specifies whether all of the BC compressed texture formats are
   * supported. vkGetPhysicalDeviceFormatProperties and vkGetPhysicalDeviceImageFormatProperties can
   * be used to check for additional supported properties of individual formats.
   */


  @Value.Default
  default boolean textureCompressionBC()
  {
    return false;
  }

  /**
   * @return textureCompressionETC2 : specifies whether all of the ETC2 and EAC compressed texture
   * formats are supported. vkGetPhysicalDeviceFormatProperties and vkGetPhysicalDeviceImageFormatProperties
   * can be used to check for additional supported properties of individual formats.
   */


  @Value.Default
  default boolean textureCompressionETC2()
  {
    return false;
  }

  /**
   * @return variableMultisampleRate : specifies whether all pipelines that will be bound to a
   * command buffer during a subpass with no attachments must have the same value for
   * VkPipelineMultisampleStateCreateInfo::rasterizationSamples. If set to VK_TRUE, the
   * implementation supports variable multisample rates in a subpass with no attachments. If set to
   * VK_FALSE, then all pipelines bound in such a subpass must have the same multisample rate. This
   * has no effect in situations where a subpass uses any attachments.
   */


  @Value.Default
  default boolean variableMultisampleRate()
  {
    return false;
  }

  /**
   * @return vertexPipelineStoresAndAtomics : specifies whether storage buffers and images support
   * stores and atomic operations in the vertex, tessellation, and geometry shader stages. If this
   * feature is not enabled, all storage image, storage texel buffers, and storage buffer variables
   * used by these stages in shader modules must be decorated with the NonWriteable decoration (or
   * the readonly memory qualifier in GLSL).
   */


  @Value.Default
  default boolean vertexPipelineStoresAndAtomics()
  {
    return false;
  }

  /**
   * @return wideLines : specifies whether lines with width other than 1.0 are supported. If this
   * feature is not enabled, the lineWidth member of the VkPipelineRasterizationStateCreateInfo
   * structure must be set to 1.0 unless the VK_DYNAMIC_STATE_LINE_WIDTH dynamic state is enabled,
   * and the lineWidth parameter to vkCmdSetLineWidth must be set to 1.0. When this feature is
   * supported, the range and granularity of supported line widths are indicated by the
   * lineWidthRange and lineWidthGranularity members of the VkPhysicalDeviceLimits structure,
   * respectively.
   */


  @Value.Default
  default boolean wideLines()
  {
    return false;
  }
}
