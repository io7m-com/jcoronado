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

/**
 * The limits of a physical device.
 */

// CHECKSTYLE:OFF
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPhysicalDeviceLimitsType
{
  //CHECKSTYLE:ON

  /**
   * @return the granularity, in bytes, at which buffer or linear image resources, and optimal image
   * resources can be bound to adjacent offsets in the same VkDeviceMemory object without aliasing.
   */

  @Value.Parameter
  long bufferImageGranularity();

  /**
   * @return the number of discrete priorities that can be assigned to a queue based on the value of
   * each member of VkDeviceQueueCreateInfo::pQueuePriorities. This must be at least 2, and levels
   * must be spread evenly over the range, with at least one level at 1.0, and another at 0.0. For
   * example, if this value is 2 bits then when linearly filtering between two levels, each level
   * could: contribute: 0%, 33%, 66%, or 100% (this is just an example and the amount of
   * contribution should be covered by different equations in the spec).
   */

  @Value.Parameter
  int discreteQueuePriorities();

  /**
   * @return a bitmask1 of VkSampleCountFlagBits indicating the color sample counts that are
   * supported for all framebuffer color attachments with floating- or fixed-point formats. There is
   * no limit that specifies the color sample counts that are supported for all color attachments
   * with integer formats.
   */

  @Value.Parameter
  int framebufferColorSampleCounts();

  /**
   * @return a bitmask1 of VkSampleCountFlagBits indicating the supported depth sample counts for
   * all framebuffer depth/stencil attachments, when the format includes a depth component.
   */

  @Value.Parameter
  int framebufferDepthSampleCounts();

  /**
   * @return a bitmask1 of VkSampleCountFlagBits indicating the supported sample counts for a
   * framebuffer with no attachments.
   */

  @Value.Parameter
  int framebufferNoAttachmentsSampleCounts();

  /**
   * @return a bitmask1 of VkSampleCountFlagBits indicating the supported stencil sample counts for
   * all framebuffer depth/stencil attachments, when the format includes a stencil component.
   */

  @Value.Parameter
  int framebufferStencilSampleCounts();

  /**
   * @return the granularity of supported line widths. Not all line widths in the range defined by
   * lineWidthRange are supported. This limit specifies the granularity (or increment) between
   * successive supported line widths.
   */

  @Value.Parameter
  float lineWidthGranularity();

  /**
   * @return the range [minimum,maximum] of supported widths for lines. Values specified by the
   * lineWidth member of the VkPipelineRasterizationStateCreateInfo or the lineWidth parameter to
   * vkCmdSetLineWidth are clamped to this range.
   */

  @Value.Parameter
  VulkanLineWidthRange lineWidthRange();

  /**
   * @return the maximum number of descriptor sets that can be simultaneously used by a pipeline.
   * All DescriptorSet decorations in shader modules must have a value less than
   * maxBoundDescriptorSets.
   */

  @Value.Parameter
  int maxBoundDescriptorSets();

  /**
   * @return the maximum number of clip distances that can be used in a single shader stage. The
   * size of any array declared with the ClipDistance built-in decoration in a shader module must be
   * less than or equal to this limit.
   */

  @Value.Parameter
  int maxClipDistances();

  /**
   * @return the maximum number of color attachments that can be used by a subpass in a render pass.
   * The colorAttachmentCount member of the VkSubpassDescription structure must be less than or
   * equal to this limit.
   */

  @Value.Parameter
  int maxColorAttachments();

  /**
   * @return the maximum combined number of clip and cull distances that can be used in a single
   * shader stage. The sum of the sizes of any pair of arrays declared with the ClipDistance and
   * CullDistance built-in decoration used by a single shader stage in a shader module must be less
   * than or equal to this limit.
   */

  @Value.Parameter
  int maxCombinedClipAndCullDistances();

  /**
   * @return the maximum total storage size, in bytes, of all variables declared with the
   * WorkgroupLocal storage class in shader modules (or with the shared storage qualifier in GLSL)
   * in the compute shader stage.
   */

  @Value.Parameter
  int maxComputeSharedMemorySize();

  /**
   * @return the maximum number of local workgroups that can be dispatched by a single dispatch
   * command. These three values represent the maximum number of local workgroups for the X, Y, and
   * Z dimensions, respectively. The workgroup count parameters to the dispatch commands must be
   * less than or equal to the corresponding limit.
   */

  @Value.Parameter
  VulkanComputeWorkGroupCount maxComputeWorkGroupCount();

  /**
   * @return the maximum total number of compute shader invocations in a single local workgroup. The
   * product of the X, Y, and Z sizes as specified by the LocalSize execution mode in shader modules
   * and by the object decorated by the WorkgroupSize decoration must be less than or equal to this
   * limit.
   */

  @Value.Parameter
  int maxComputeWorkGroupInvocations();

  /**
   * @return the maximum size of a local compute workgroup, per dimension. These three values
   * represent the maximum local workgroup size in the X, Y, and Z dimensions, respectively. The x,
   * y, and z sizes specified by the LocalSize execution mode and by the object decorated by the
   * WorkgroupSize decoration in shader modules must be less than or equal to the corresponding
   * limit.
   */

  @Value.Parameter
  VulkanComputeWorkGroupSize maxComputeWorkGroupSize();

  /**
   * @return the maximum number of cull distances that can be used in a single shader stage. The
   * size of any array declared with the CullDistance built-in decoration in a shader module must be
   * less than or equal to this limit.
   */

  @Value.Parameter
  int maxCullDistances();

  /**
   * @return the maximum number of input attachments that can be included in descriptor bindings in
   * a pipeline layout across all pipeline shader stages and descriptor set numbers. Descriptors
   * with a type of VK_DESCRIPTOR_TYPE_INPUT_ATTACHMENT count against this limit. Only descriptors
   * in descriptor set layouts created without the
   * VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT
   * bit set count against this limit.
   */

  @Value.Parameter
  int maxDescriptorSetInputAttachments();

  /**
   * @return the maximum number of sampled images that can be included in descriptor bindings in a
   * pipeline layout across all pipeline shader stages and descriptor set numbers. Descriptors with
   * a type of VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER, VK_DESCRIPTOR_TYPE_SAMPLED_IMAGE, or
   * VK_DESCRIPTOR_TYPE_UNIFORM_TEXEL_BUFFER count against this limit. Only descriptors in
   * descriptor set layouts created without the
   * VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT
   * bit set count against this limit.
   */

  @Value.Parameter
  int maxDescriptorSetSampledImages();

  /**
   * @return the maximum number of samplers that can be included in descriptor bindings in a
   * pipeline layout across all pipeline shader stages and descriptor set numbers. Descriptors with
   * a type of VK_DESCRIPTOR_TYPE_SAMPLER or VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER count against
   * this limit. Only descriptors in descriptor set layouts created without the
   * VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT bit set count against this
   * limit.
   */

  @Value.Parameter
  int maxDescriptorSetSamplers();

  /**
   * @return the maximum number of dynamic storage buffers that can be included in descriptor
   * bindings in a pipeline layout across all pipeline shader stages and descriptor set numbers.
   * Descriptors with a type of VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC count against this limit.
   * Only descriptors in descriptor set layouts created without the
   * VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT
   * bit set count against this limit.
   */

  @Value.Parameter
  int maxDescriptorSetStorageBuffersDynamic();

  /**
   * @return the maximum number of storage buffers that can be included in descriptor bindings in a
   * pipeline layout across all pipeline shader stages and descriptor set numbers. Descriptors with
   * a type of VK_DESCRIPTOR_TYPE_STORAGE_BUFFER or VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC count
   * against this limit. Only descriptors in descriptor set layouts created without the
   * VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT bit set count against this
   * limit.
   */

  @Value.Parameter
  int maxDescriptorSetStorageBuffers();

  /**
   * @return the maximum number of storage images that can be included in descriptor bindings in a
   * pipeline layout across all pipeline shader stages and descriptor set numbers. Descriptors with
   * a type of VK_DESCRIPTOR_TYPE_STORAGE_IMAGE, or VK_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER count
   * against this limit. Only descriptors in descriptor set layouts created without the
   * VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT bit set count against this
   * limit.
   */

  @Value.Parameter
  int maxDescriptorSetStorageImages();

  /**
   * @return the maximum number of dynamic uniform buffers that can be included in descriptor
   * bindings in a pipeline layout across all pipeline shader stages and descriptor set numbers.
   * Descriptors with a type of VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC count against this limit.
   * Only descriptors in descriptor set layouts created without the
   * VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT
   * bit set count against this limit.
   */

  @Value.Parameter
  int maxDescriptorSetUniformBuffersDynamic();

  /**
   * @return the maximum number of uniform buffers that can be included in descriptor bindings in a
   * pipeline layout across all pipeline shader stages and descriptor set numbers. Descriptors with
   * a type of VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER or VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC count
   * against this limit. Only descriptors in descriptor set layouts created without the
   * VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT bit set count against this
   * limit.
   */

  @Value.Parameter
  int maxDescriptorSetUniformBuffers();

  /**
   * @return the maximum index value that can be used for indexed draw calls when using 32-bit
   * indices. This excludes the primitive restart index value of 0xFFFFFFFF.
   */

  @Value.Parameter
  int maxDrawIndexedIndexValue();

  /**
   * @return the maximum draw count that is supported for indirect draw calls.
   */

  @Value.Parameter
  int maxDrawIndirectCount();

  /**
   * @return the total number of storage buffers, storage images, and output buffers which can be
   * used in the fragment shader stage.
   */

  @Value.Parameter
  int maxFragmentCombinedOutputResources();

  /**
   * @return the maximum number of output attachments which can be written to by the fragment shader
   * stage when blending is enabled and one of the dual source blend modes is in use.
   */

  @Value.Parameter
  int maxFragmentDualSrcAttachments();

  /**
   * @return the maximum number of components of input variables which can be provided as inputs to
   * the fragment shader stage.
   */

  @Value.Parameter
  int maxFragmentInputComponents();

  /**
   * @return the maximum number of output attachments which can be written to by the fragment shader
   * stage.
   */

  @Value.Parameter
  int maxFragmentOutputAttachments();

  /**
   * @return the maximum height for a framebuffer. The height member of the VkFramebufferCreateInfo
   * structure must be less than or equal to this limit.
   */

  @Value.Parameter
  int maxFramebufferHeight();

  /**
   * @return the maximum layer count for a layered framebuffer. The layers member of the
   * VkFramebufferCreateInfo structure must be less than or equal to this limit.
   */

  @Value.Parameter
  int maxFramebufferLayers();

  /**
   * @return the maximum width for a framebuffer. The width member of the VkFramebufferCreateInfo
   * structure must be less than or equal to this limit.
   */

  @Value.Parameter
  int maxFramebufferWidth();

  /**
   * @return the maximum number of components of input variables which can be provided as inputs to
   * the geometry shader stage.
   */

  @Value.Parameter
  int maxGeometryInputComponents();

  /**
   * @return the maximum number of components of output variables which can be output from the
   * geometry shader stage.
   */

  @Value.Parameter
  int maxGeometryOutputComponents();

  /**
   * @return the maximum number of vertices which can be emitted by any geometry shader.
   */

  @Value.Parameter
  int maxGeometryOutputVertices();

  /**
   * @return the maximum invocation count supported for instanced geometry shaders. The value
   * provided in the Invocations execution mode of shader modules must be less than or equal to this
   * limit.
   */

  @Value.Parameter
  int maxGeometryShaderInvocations();

  /**
   * @return the maximum total number of components of output, across all emitted vertices, which
   * can be output from the geometry shader stage.
   */

  @Value.Parameter
  int maxGeometryTotalOutputComponents();

  /**
   * @return the maximum number of layers (arrayLayers) for an image.
   */

  @Value.Parameter
  int maxImageArrayLayers();

  /**
   * @return the maximum dimension (width) supported for all images created with an imageType of
   * VK_IMAGE_TYPE_1D.
   */

  @Value.Parameter
  int maxImageDimension1D();

  /**
   * @return the maximum dimension (width or height) supported for all images created with an
   * imageType of VK_IMAGE_TYPE_2D and without VK_IMAGE_CREATE_CUBE_COMPATIBLE_BIT set in flags.
   */

  @Value.Parameter
  int maxImageDimension2D();

  /**
   * @return the maximum dimension (width, height, or depth) supported for all images created with
   * an imageType of VK_IMAGE_TYPE_3D.
   */

  @Value.Parameter
  int maxImageDimension3D();

  /**
   * @return the maximum dimension (width or height) supported for all images created with an
   * imageType of VK_IMAGE_TYPE_2D and with VK_IMAGE_CREATE_CUBE_COMPATIBLE_BIT set in flags.
   */

  @Value.Parameter
  int maxImageDimensionCube();

  /**
   * @return the maximum positive offset value for the offset operand of the InterpolateAtOffset
   * extended instruction.
   */

  @Value.Parameter
  float maxInterpolationOffset();

  /**
   * @return the maximum number of device memory allocations, as created by vkAllocateMemory, which
   * can simultaneously exist.
   */

  @Value.Parameter
  int maxMemoryAllocationCount();

  /**
   * @return the maximum number of input attachments that can be accessible to a single shader stage
   * in a pipeline layout. Descriptors with a type of VK_DESCRIPTOR_TYPE_INPUT_ATTACHMENT count
   * against this limit. Only descriptors in descriptor set layouts created without the
   * VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT bit set count against this
   * limit. A descriptor is accessible to a pipeline shader stage when the stageFlags member of the
   * VkDescriptorSetLayoutBinding structure has the bit for that shader stage set. These are only
   * supported for the fragment stage.
   */

  @Value.Parameter
  int maxPerStageDescriptorInputAttachments();

  /**
   * @return the maximum number of sampled images that can be accessible to a single shader stage in
   * a pipeline layout. Descriptors with a type of VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER,
   * VK_DESCRIPTOR_TYPE_SAMPLED_IMAGE, or VK_DESCRIPTOR_TYPE_UNIFORM_TEXEL_BUFFER count against this
   * limit. Only descriptors in descriptor set layouts created without the
   * VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT bit set count against this
   * limit. A descriptor is accessible to a pipeline shader stage when the stageFlags member of the
   * VkDescriptorSetLayoutBinding structure has the bit for that shader stage set.
   */

  @Value.Parameter
  int maxPerStageDescriptorSampledImages();

  /**
   * @return the maximum number of samplers that can be accessible to a single shader stage in a
   * pipeline layout. Descriptors with a type of VK_DESCRIPTOR_TYPE_SAMPLER or
   * VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER count against this limit. Only descriptors in
   * descriptor set layouts created without the VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT
   * bit set count against this limit. A descriptor is accessible to a shader stage when the
   * stageFlags member of the VkDescriptorSetLayoutBinding structure has the bit for that shader
   * stage set.
   */

  @Value.Parameter
  int maxPerStageDescriptorSamplers();

  /**
   * @return the maximum number of storage buffers that can be accessible to a single shader stage
   * in a pipeline layout. Descriptors with a type of VK_DESCRIPTOR_TYPE_STORAGE_BUFFER or
   * VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC count against this limit. Only descriptors in
   * descriptor set layouts created without the VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT
   * bit set count against this limit. A descriptor is accessible to a pipeline shader stage when
   * the stageFlags member of the VkDescriptorSetLayoutBinding structure has the bit for that shader
   * stage set.
   */

  @Value.Parameter
  int maxPerStageDescriptorStorageBuffers();

  /**
   * @return the maximum number of storage images that can be accessible to a single shader stage in
   * a pipeline layout. Descriptors with a type of VK_DESCRIPTOR_TYPE_STORAGE_IMAGE, or
   * VK_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER count against this limit. Only descriptors in
   * descriptor set layouts created without the VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT
   * bit set count against this limit. A descriptor is accessible to a pipeline shader stage when
   * the stageFlags member of the VkDescriptorSetLayoutBinding structure has the bit for that shader
   * stage set.
   */

  @Value.Parameter
  int maxPerStageDescriptorStorageImages();

  /**
   * @return the maximum number of uniform buffers that can be accessible to a single shader stage
   * in a pipeline layout. Descriptors with a type of VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER or
   * VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC count against this limit. Only descriptors in
   * descriptor set layouts created without the VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT
   * bit set count against this limit. A descriptor is accessible to a shader stage when the
   * stageFlags member of the VkDescriptorSetLayoutBinding structure has the bit for that shader
   * stage set.
   */

  @Value.Parameter
  int maxPerStageDescriptorUniformBuffers();

  /**
   * @return the maximum number of resources that can be accessible to a single shader stage in a
   * pipeline layout. Descriptors with a type of VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER,
   * VK_DESCRIPTOR_TYPE_SAMPLED_IMAGE, VK_DESCRIPTOR_TYPE_STORAGE_IMAGE,
   * VK_DESCRIPTOR_TYPE_UNIFORM_TEXEL_BUFFER, VK_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER,
   * VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER, VK_DESCRIPTOR_TYPE_STORAGE_BUFFER,
   * VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC, VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC, or
   * VK_DESCRIPTOR_TYPE_INPUT_ATTACHMENT count against this limit. Only descriptors in descriptor
   * set layouts created without the VK_DESCRIPTOR_SET_LAYOUT_CREATE_UPDATE_AFTER_BIND_POOL_BIT_EXT
   * bit set count against this limit. For the fragment shader stage the framebuffer color
   * attachments also count against this limit.
   */

  @Value.Parameter
  int maxPerStageResources();

  /**
   * @return the maximum size, in bytes, of the pool of push constant memory. For each of the push
   * constant ranges indicated by the pPushConstantRanges member of the VkPipelineLayoutCreateInfo
   * structure, (offset + size) must be less than or equal to this limit.
   */

  @Value.Parameter
  int maxPushConstantsSize();

  /**
   * @return the maximum number of array elements of a variable decorated with the SampleMask
   * built-in decoration.
   */

  @Value.Parameter
  int maxSampleMaskWords();

  /**
   * @return the maximum number of sampler objects, as created by vkCreateSampler, which can
   * simultaneously exist on a device.
   */

  @Value.Parameter
  int maxSamplerAllocationCount();

  /**
   * @return the maximum degree of sampler anisotropy. The maximum degree of anisotropic filtering
   * used for an image sampling operation is the minimum of the maxAnisotropy member of the
   * VkSamplerCreateInfo structure and this limit.
   */

  @Value.Parameter
  float maxSamplerAnisotropy();

  /**
   * @return the maximum absolute sampler LOD bias. The sum of the mipLodBias member of the
   * VkSamplerCreateInfo structure and the Bias operand of image sampling operations in shader
   * modules (or 0 if no Bias operand is provided to an image sampling operation) are clamped to the
   * range [-maxSamplerLodBias,+maxSamplerLodBias].
   */

  @Value.Parameter
  float maxSamplerLodBias();

  /**
   * @return the maximum value that can be specified in the range member of any
   * VkDescriptorBufferInfo structures passed to a call to vkUpdateDescriptorSets for descriptors of
   * type VK_DESCRIPTOR_TYPE_STORAGE_BUFFER or VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC.
   */

  @Value.Parameter
  int maxStorageBufferRange();

  /**
   * @return the maximum number of components of per-patch output variables which can be output from
   * the tessellation control shader stage.
   */

  @Value.Parameter
  int maxTessellationControlPerPatchOutputComponents();

  /**
   * @return the maximum number of components of input variables which can be provided as per-vertex
   * inputs to the tessellation control shader stage.
   */

  @Value.Parameter
  int maxTessellationControlPerVertexInputComponents();

  /**
   * @return the maximum number of components of per-vertex output variables which can be output
   * from the tessellation control shader stage.
   */

  @Value.Parameter
  int maxTessellationControlPerVertexOutputComponents();

  /**
   * @return the maximum total number of components of per-vertex and per-patch output variables
   * which can be output from the tessellation control shader stage.
   */

  @Value.Parameter
  int maxTessellationControlTotalOutputComponents();

  /**
   * @return the maximum number of components of input variables which can be provided as per-vertex
   * inputs to the tessellation evaluation shader stage.
   */

  @Value.Parameter
  int maxTessellationEvaluationInputComponents();

  /**
   * @return the maximum number of components of per-vertex output variables which can be output
   * from the tessellation evaluation shader stage.
   */

  @Value.Parameter
  int maxTessellationEvaluationOutputComponents();

  /**
   * @return the maximum tessellation generation level supported by the fixed-function tessellation
   * primitive generator.
   */

  @Value.Parameter
  int maxTessellationGenerationLevel();

  /**
   * @return the maximum patch size, in vertices, of patches that can be processed by the
   * tessellation control shader and tessellation primitive generator. The patchControlPoints member
   * of the VkPipelineTessellationStateCreateInfo structure specified at pipeline creation time and
   * the value provided in the OutputVertices execution mode of shader modules must be less than or
   * equal to this limit.
   */

  @Value.Parameter
  int maxTessellationPatchSize();

  /**
   * @return the maximum number of addressable texels for a buffer view created on a buffer which
   * was created with the VK_BUFFER_USAGE_UNIFORM_TEXEL_BUFFER_BIT or
   * VK_BUFFER_USAGE_STORAGE_TEXEL_BUFFER_BIT set in the usage member of the VkBufferCreateInfo
   * structure.
   */

  @Value.Parameter
  int maxTexelBufferElements();

  /**
   * @return the maximum offset value for the Offset or ConstOffsets image operands of any of the
   * OpImage*Gather image instructions.
   */

  @Value.Parameter
  int maxTexelGatherOffset();

  /**
   * @return the maximum offset value for the ConstOffset image operand of any of the OpImageSample*
   * or OpImageFetch* image instructions.
   */

  @Value.Parameter
  int maxTexelOffset();

  /**
   * @return the maximum value that can be specified in the range member of any
   * VkDescriptorBufferInfo structures passed to a call to vkUpdateDescriptorSets for descriptors of
   * type VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER or VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC.
   */

  @Value.Parameter
  int maxUniformBufferRange();

  /**
   * @return the maximum vertex input attribute offset that can be added to the vertex input binding
   * stride. The offset member of the VkVertexInputAttributeDescription structure must be less than
   * or equal to this limit.
   */

  @Value.Parameter
  int maxVertexInputAttributeOffset();

  /**
   * @return the maximum number of vertex input attributes that can be specified for a graphics
   * pipeline. These are described in the array of VkVertexInputAttributeDescription structures that
   * are provided at graphics pipeline creation time via the pVertexAttributeDescriptions member of
   * the VkPipelineVertexInputStateCreateInfo structure.
   */

  @Value.Parameter
  int maxVertexInputAttributes();

  /**
   * @return the maximum number of vertex buffers that can be specified for providing vertex
   * attributes to a graphics pipeline. These are described in the array of
   * VkVertexInputBindingDescription structures that are provided at graphics pipeline creation time
   * via the pVertexBindingDescriptions member of the VkPipelineVertexInputStateCreateInfo
   * structure. The binding member of VkVertexInputBindingDescription must be less than this limit.
   */

  @Value.Parameter
  int maxVertexInputBindings();

  /**
   * @return the maximum vertex input binding stride that can be specified in a vertex input
   * binding. The stride member of the VkVertexInputBindingDescription structure must be less than
   * or equal to this limit.
   */

  @Value.Parameter
  int maxVertexInputBindingStride();

  /**
   * @return the maximum number of components of output variables which can be output by a vertex
   * shader.
   */

  @Value.Parameter
  int maxVertexOutputComponents();

  /**
   * @return are the maximum viewport dimensions in the X (width) and Y (height) dimensions,
   * respectively. The maximum viewport dimensions must be greater than or equal to the largest
   * image which can be created and used as a framebuffer attachment.
   */

  @Value.Parameter
  VulkanViewportDimensions maxViewportDimensions();

  /**
   * @return the maximum number of active viewports. The viewportCount member of the
   * VkPipelineViewportStateCreateInfo structure that is provided at pipeline creation must be less
   * than or equal to this limit.
   */

  @Value.Parameter
  int maxViewports();

  /**
   * @return the minimum negative offset value for the offset operand of the InterpolateAtOffset
   * extended instruction.
   */

  @Value.Parameter
  float minInterpolationOffset();

  /**
   * @return the minimum required alignment, in bytes, of host visible memory allocations within the
   * host address space. When mapping a memory allocation with vkMapMemory, subtracting offset bytes
   * from the returned pointer will always produce an integer multiple of this limit.
   */

  @Value.Parameter
  long minMemoryMapAlignment();

  /**
   * @return the minimum required alignment, in bytes, for the offset member of the
   * VkDescriptorBufferInfo structure for storage buffers. When a descriptor of type
   * VK_DESCRIPTOR_TYPE_STORAGE_BUFFER or VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC is updated, the
   * offset must be an integer multiple of this limit. Similarly, dynamic offsets for storage
   * buffers must be multiples of this limit.
   */

  @Value.Parameter
  long minStorageBufferOffsetAlignment();

  /**
   * @return the minimum required alignment, in bytes, for the offset member of the
   * VkBufferViewCreateInfo structure for texel buffers. When a buffer view is created for a buffer
   * which was created with VK_BUFFER_USAGE_UNIFORM_TEXEL_BUFFER_BIT or
   * VK_BUFFER_USAGE_STORAGE_TEXEL_BUFFER_BIT set in the usage member of the VkBufferCreateInfo
   * structure, the offset must be an integer multiple of this limit.
   */

  @Value.Parameter
  long minTexelBufferOffsetAlignment();

  /**
   * @return the minimum offset value for the Offset or ConstOffsets image operands of any of the
   * OpImage*Gather image instructions.
   */

  @Value.Parameter
  int minTexelGatherOffset();

  /**
   * @return the minimum offset value for the ConstOffset image operand of any of the OpImageSample*
   * or OpImageFetch* image instructions.
   */

  @Value.Parameter
  int minTexelOffset();

  /**
   * @return the minimum required alignment, in bytes, for the offset member of the
   * VkDescriptorBufferInfo structure for uniform buffers. When a descriptor of type
   * VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER or VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC is updated, the
   * offset must be an integer multiple of this limit. Similarly, dynamic offsets for uniform
   * buffers must be multiples of this limit.
   */

  @Value.Parameter
  long minUniformBufferOffsetAlignment();

  /**
   * @return the number of bits of division that the LOD calculation for mipmap fetching get snapped
   * to when determining the contribution from each mip level to the mip filtered results.
   * 2mipmapPrecisionBits is the actual number of divisions.
   */

  @Value.Parameter
  int mipmapPrecisionBits();

  /**
   * @return the size and alignment in bytes that bounds concurrent access to host-mapped device
   * memory.
   */

  @Value.Parameter
  long nonCoherentAtomSize();

  /**
   * @return the optimal buffer offset alignment in bytes for vkCmdCopyBufferToImage and
   * vkCmdCopyImageToBuffer. The per texel alignment requirements are enforced, but applications
   * should use the optimal alignment for optimal performance and power use.
   */

  @Value.Parameter
  long optimalBufferCopyOffsetAlignment();

  /**
   * @return the optimal buffer row pitch alignment in bytes for vkCmdCopyBufferToImage and
   * vkCmdCopyImageToBuffer. Row pitch is the number of bytes between texels with the same X
   * coordinate in adjacent rows (Y coordinates differ by one). The per texel alignment requirements
   * are enforced, but applications should use the optimal alignment for optimal performance and
   * power use.
   */

  @Value.Parameter
  long optimalBufferCopyRowPitchAlignment();

  /**
   * @return the granularity of supported point sizes. Not all point sizes in the range defined by
   * pointSizeRange are supported. This limit specifies the granularity (or increment) between
   * successive supported point sizes.
   */

  @Value.Parameter
  float pointSizeGranularity();

  /**
   * @return the range [minimum,maximum] of supported sizes for points. Values written to variables
   * decorated with the PointSize built-in decoration are clamped to this range.
   */

  @Value.Parameter
  VulkanPointSizeRange pointSizeRange();

  /**
   * @return a bitmask1 of VkSampleCountFlagBits indicating the sample counts supported for all 2D
   * images created with VK_IMAGE_TILING_OPTIMAL, usage containing VK_IMAGE_USAGE_SAMPLED_BIT, and a
   * non-integer color format.
   */

  @Value.Parameter
  int sampledImageColorSampleCounts();

  /**
   * @return a bitmask1 of VkSampleCountFlagBits indicating the sample counts supported for all 2D
   * images created with VK_IMAGE_TILING_OPTIMAL, usage containing VK_IMAGE_USAGE_SAMPLED_BIT, and a
   * depth format.
   */

  @Value.Parameter
  int sampledImageDepthSampleCounts();

  /**
   * @return a bitmask1 of VkSampleCountFlagBits indicating the sample counts supported for all 2D
   * images created with VK_IMAGE_TILING_OPTIMAL, usage containing VK_IMAGE_USAGE_SAMPLED_BIT, and
   * an integer color format.
   */

  @Value.Parameter
  int sampledImageIntegerSampleCounts();

  /**
   * @return a bitmask1 of VkSampleCountFlagBits indicating the sample supported for all 2D images
   * created with VK_IMAGE_TILING_OPTIMAL, usage containing VK_IMAGE_USAGE_SAMPLED_BIT, and a
   * stencil format.
   */

  @Value.Parameter
  int sampledImageStencilSampleCounts();

  /**
   * @return the total amount of address space available, in bytes, for sparse memory resources.
   * This is an upper bound on the sum of the size of all sparse resources, regardless of whether
   * any memory is bound to them.
   */

  @Value.Parameter
  long sparseAddressSpaceSize();

  /**
   * @return specifies whether rasterization uses the standard sample locations as documented in
   * Multisampling. If set to VK_TRUE, the implementation uses the documented sample locations. If
   * set to VK_FALSE, the implementation may use different sample locations.
   */

  @Value.Parameter
  boolean standardSampleLocations();

  /**
   * @return a bitmask1 of VkSampleCountFlagBits indicating the sample counts supported for all 2D
   * images created with VK_IMAGE_TILING_OPTIMAL, and usage containing VK_IMAGE_USAGE_STORAGE_BIT.
   */

  @Value.Parameter
  int storageImageSampleCounts();

  /**
   * @return specifies whether lines are rasterized according to the preferred method of
   * rasterization. If set to VK_FALSE, lines may be rasterized under a relaxed set of rules. If set
   * to VK_TRUE, lines are rasterized as per the strict definition.
   */

  @Value.Parameter
  boolean strictLines();

  /**
   * @return the number of subpixel fractional bits that the x and y offsets to the
   * InterpolateAtOffset extended instruction may be rounded to as fixed-point values.
   */

  @Value.Parameter
  int subPixelInterpolationOffsetBits();

  /**
   * @return the number of bits of subpixel precision in framebuffer coordinates xf and yf.
   */

  @Value.Parameter
  int subPixelPrecisionBits();

  /**
   * @return the number of bits of precision in the division along an axis of an image used for
   * minification and magnification filters. 2subTexelPrecisionBits is the actual number of
   * divisions along each axis of the image represented. Sub-texel values calculated during image
   * sampling will snap to these locations when generating the filtered results.
   */

  @Value.Parameter
  int subTexelPrecisionBits();

  /**
   * @return specifies support for timestamps on all graphics and compute queues. If this limit is
   * set to VK_TRUE, all queues that advertise the VK_QUEUE_GRAPHICS_BIT or VK_QUEUE_COMPUTE_BIT in
   * the VkQueueFamilyProperties::queueFlags support VkQueueFamilyProperties::timestampValidBits of
   * at least 36.
   */

  @Value.Parameter
  boolean timestampComputeAndGraphics();

  /**
   * @return the number of nanoseconds required for a timestamp query to be incremented by 1.
   */

  @Value.Parameter
  float timestampPeriod();

  /**
   * @return the [minimum, maximum] range that the corners of a viewport must be contained in. This
   * range must be at least [-2 × size, 2 × size - 1], where size = max(maxViewportDimensions[0],
   * maxViewportDimensions[1]). The intent of the viewportBoundsRange limit is to allow a maximum
   * sized viewport to be arbitrarily shifted relative to the output target as long as at least some
   * portion intersects. This would give a bounds limit of [-size + 1, 2 × size - 1] which would
   * allow all possible non-empty-set intersections of the output target and the viewport. Since
   * these numbers are typically powers of two, picking the signed number range using the smallest
   * possible number of bits ends up with the specified range.
   */

  @Value.Parameter
  VulkanViewportBoundsRange viewportBoundsRange();

  /**
   * @return the number of bits of subpixel precision for viewport bounds. The subpixel precision
   * that floating-point viewport bounds are interpreted at is given by this limit.
   */

  @Value.Parameter
  int viewportSubPixelBits();
}
