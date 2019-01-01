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

import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanComputeWorkGroupCount;
import com.io7m.jcoronado.api.VulkanComputeWorkGroupSize;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionType;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLineWidthRange;
import com.io7m.jcoronado.api.VulkanMemoryHeap;
import com.io7m.jcoronado.api.VulkanMemoryHeapFlag;
import com.io7m.jcoronado.api.VulkanMemoryPropertyFlag;
import com.io7m.jcoronado.api.VulkanMemoryType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceLimits;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceMemoryProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanPointSizeRange;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.api.VulkanViewportBoundsRange;
import com.io7m.jcoronado.api.VulkanViewportDimensions;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkExtent3D;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkMemoryHeap;
import org.lwjgl.vulkan.VkMemoryType;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;
import org.lwjgl.vulkan.VkPhysicalDeviceLimits;
import org.lwjgl.vulkan.VkPhysicalDeviceMemoryProperties;
import org.lwjgl.vulkan.VkPhysicalDeviceProperties;
import org.lwjgl.vulkan.VkQueueFamilyProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * LWJGL {@link VkInstance}
 */

public final class VulkanLWJGLInstance
  extends VulkanLWJGLHandle implements VulkanInstanceType
{
  private static final Logger LOG = LoggerFactory.getLogger(VulkanLWJGLInstance.class);

  private final VkInstance instance;
  private final MemoryStack initial_stack;
  private final Map<String, VulkanExtensionType> extensions_enabled_read_only;
  private final VulkanLWJGLExtensionsRegistry extension_registry;

  VulkanLWJGLInstance(
    final VkInstance in_instance,
    final VulkanLWJGLExtensionsRegistry in_extension_registry,
    final Map<String, VulkanExtensionType> in_extensions_enabled,
    final VulkanLWJGLHostAllocatorProxy in_host_allocator_proxy)
  {
    super(Ownership.USER_OWNED, in_host_allocator_proxy);

    this.instance =
      Objects.requireNonNull(in_instance, "instance");
    this.extension_registry =
      Objects.requireNonNull(in_extension_registry, "extension_registry");
    this.initial_stack =
      MemoryStack.create();
    this.extensions_enabled_read_only =
      Collections.unmodifiableMap(
        Objects.requireNonNull(in_extensions_enabled, "in_extensions"));
  }

  private static List<VulkanQueueFamilyProperties> parsePhysicalDeviceQueueFamilies(
    final MemoryStack stack_initial,
    final VkPhysicalDevice vk_device)
  {
    final ArrayList<VulkanQueueFamilyProperties> families;
    try (var stack = stack_initial.push()) {
      final var count = new int[1];

      VK10.vkGetPhysicalDeviceQueueFamilyProperties(vk_device, count, null);

      final var queue_family_count = count[0];
      if (queue_family_count == 0) {
        return List.of();
      }

      final var vk_queue_families =
        VkQueueFamilyProperties.mallocStack(queue_family_count, stack);

      VK10.vkGetPhysicalDeviceQueueFamilyProperties(
        vk_device, count, vk_queue_families);

      families = new ArrayList<>(queue_family_count);
      for (var index = 0; index < queue_family_count; ++index) {
        vk_queue_families.position(index);

        final var properties =
          VulkanQueueFamilyProperties.of(
            index,
            vk_queue_families.queueCount(),
            parseQueueFlags(vk_queue_families.queueFlags()),
            vk_queue_families.timestampValidBits(),
            parseExtent3D(vk_queue_families.minImageTransferGranularity()));

        families.add(properties);
      }
    }

    return families;
  }

  private static VulkanExtent3D parseExtent3D(
    final VkExtent3D e)
  {
    return VulkanExtent3D.of(
      e.width(),
      e.height(),
      e.depth());
  }

  private static Set<VulkanQueueFamilyPropertyFlag> parseQueueFlags(
    final int input)
  {
    final var results =
      EnumSet.noneOf(VulkanQueueFamilyPropertyFlag.class);
    for (final var flag : VulkanQueueFamilyPropertyFlag.values()) {
      final var value = flag.value();
      if ((input & value) == value) {
        results.add(flag);
      }
    }
    return results;
  }

  private static VulkanPhysicalDeviceProperties parsePhysicalDeviceProperties(
    final VkPhysicalDeviceProperties vk_properties,
    final int index)
  {
    final var device_name =
      vk_properties.deviceNameString();
    final var device_type =
      VulkanPhysicalDeviceProperties.Type.ofInt(vk_properties.deviceType());
    final var device_id =
      vk_properties.deviceID();
    final var device_vendor =
      vk_properties.vendorID();
    final var device_api =
      vk_properties.apiVersion();
    final var device_driver_version =
      vk_properties.driverVersion();
    final var version =
      VulkanVersions.decode(device_api);
    final var driver_version =
      VulkanVersions.decode(device_driver_version);

    if (LOG.isDebugEnabled()) {
      LOG.debug(
        "device [{}]: property device name: {}",
        Integer.valueOf(index),
        device_name);
      LOG.debug(
        "device [{}]: property device type: {}",
        Integer.valueOf(index),
        device_type);
      LOG.debug(
        "device [{}]: property device id: 0x{}",
        Integer.valueOf(index),
        Integer.toUnsignedString(device_id, 16));
      LOG.debug(
        "device [{}]: property device vendor: 0x{}",
        Integer.valueOf(index),
        Integer.toUnsignedString(device_vendor, 16));
      LOG.debug(
        "device [{}]: property device api: 0x{} ({}.{}.{})",
        Integer.valueOf(index),
        Integer.toUnsignedString(device_api, 16),
        Integer.valueOf(version.major()),
        Integer.valueOf(version.minor()),
        Integer.valueOf(version.patch()));
      LOG.debug(
        "device [{}]: property device driver version: 0x{} ({}.{}.{})",
        Integer.valueOf(index),
        Integer.toUnsignedString(device_api, 16),
        Integer.valueOf(driver_version.major()),
        Integer.valueOf(driver_version.minor()),
        Integer.valueOf(driver_version.patch()));
    }

    return VulkanPhysicalDeviceProperties.of(
      device_name,
      device_type,
      device_id,
      device_vendor,
      version,
      driver_version);
  }

  private static VulkanPhysicalDeviceMemoryProperties
  parsePhysicalDeviceMemoryProperties(
    final VkPhysicalDeviceMemoryProperties vk_memory)
  {
    final var builder =
      VulkanPhysicalDeviceMemoryProperties.builder();

    for (var index = 0; index < vk_memory.memoryHeapCount(); ++index) {
      final var heap = vk_memory.memoryHeaps(index);
      builder.addHeaps(parseHeap(heap));
    }

    for (var index = 0; index < vk_memory.memoryTypeCount(); ++index) {
      final var type = vk_memory.memoryTypes(index);
      builder.addTypes(parseType(type));
    }

    return builder.build();
  }

  private static VulkanMemoryType parseType(
    final VkMemoryType type)
  {
    return VulkanMemoryType.of(
      type.heapIndex(), parseTypeFlags(type.propertyFlags()));
  }

  private static Set<VulkanMemoryPropertyFlag> parseTypeFlags(
    final int flags)
  {
    final var values =
      EnumSet.noneOf(VulkanMemoryPropertyFlag.class);

    for (final var flag : VulkanMemoryPropertyFlag.values()) {
      final var value = flag.value();
      if ((flags & value) == value) {
        values.add(flag);
      }
    }

    return values;
  }

  private static VulkanMemoryHeap parseHeap(
    final VkMemoryHeap heap)
  {
    return VulkanMemoryHeap.of(heap.size(), parseHeapFlags(heap.flags()));
  }

  private static Set<VulkanMemoryHeapFlag> parseHeapFlags(
    final int flags)
  {
    final var values =
      EnumSet.noneOf(VulkanMemoryHeapFlag.class);

    for (final var flag : VulkanMemoryHeapFlag.values()) {
      final var value = flag.value();
      if ((flags & value) == value) {
        values.add(flag);
      }
    }

    return values;
  }

  /**
   * This method is not hand-written: See features-set.sh
   */

  private static VulkanPhysicalDeviceFeatures parsePhysicalDeviceFeatures(
    final VkPhysicalDeviceFeatures vk_features)
  {
    return VulkanPhysicalDeviceFeatures.builder()
      .setAlphaToOne(vk_features.alphaToOne())
      .setDepthBiasClamp(vk_features.depthBiasClamp())
      .setDepthBounds(vk_features.depthBounds())
      .setDepthClamp(vk_features.depthClamp())
      .setDrawIndirectFirstInstance(vk_features.drawIndirectFirstInstance())
      .setDualSrcBlend(vk_features.dualSrcBlend())
      .setFillModeNonSolid(vk_features.fillModeNonSolid())
      .setFragmentStoresAndAtomics(vk_features.fragmentStoresAndAtomics())
      .setFullDrawIndexUint32(vk_features.fullDrawIndexUint32())
      .setGeometryShader(vk_features.geometryShader())
      .setImageCubeArray(vk_features.imageCubeArray())
      .setIndependentBlend(vk_features.independentBlend())
      .setInheritedQueries(vk_features.inheritedQueries())
      .setLargePoints(vk_features.largePoints())
      .setLogicOp(vk_features.logicOp())
      .setMultiDrawIndirect(vk_features.multiDrawIndirect())
      .setMultiViewport(vk_features.multiViewport())
      .setOcclusionQueryPrecise(vk_features.occlusionQueryPrecise())
      .setPipelineStatisticsQuery(vk_features.pipelineStatisticsQuery())
      .setRobustBufferAccess(vk_features.robustBufferAccess())
      .setSamplerAnisotropy(vk_features.samplerAnisotropy())
      .setSampleRateShading(vk_features.sampleRateShading())
      .setShaderClipDistance(vk_features.shaderClipDistance())
      .setShaderCullDistance(vk_features.shaderCullDistance())
      .setShaderFloat64(vk_features.shaderFloat64())
      .setShaderImageGatherExtended(vk_features.shaderImageGatherExtended())
      .setShaderInt16(vk_features.shaderInt16())
      .setShaderInt64(vk_features.shaderInt64())
      .setShaderResourceMinLod(vk_features.shaderResourceMinLod())
      .setShaderResourceResidency(vk_features.shaderResourceResidency())
      .setShaderSampledImageArrayDynamicIndexing(vk_features.shaderSampledImageArrayDynamicIndexing())
      .setShaderStorageBufferArrayDynamicIndexing(vk_features.shaderStorageBufferArrayDynamicIndexing())
      .setShaderStorageImageArrayDynamicIndexing(vk_features.shaderStorageImageArrayDynamicIndexing())
      .setShaderStorageImageExtendedFormats(vk_features.shaderStorageImageExtendedFormats())
      .setShaderStorageImageMultisample(vk_features.shaderStorageImageMultisample())
      .setShaderStorageImageReadWithoutFormat(vk_features.shaderStorageImageReadWithoutFormat())
      .setShaderStorageImageWriteWithoutFormat(vk_features.shaderStorageImageWriteWithoutFormat())
      .setShaderTessellationAndGeometryPointSize(vk_features.shaderTessellationAndGeometryPointSize())
      .setShaderUniformBufferArrayDynamicIndexing(vk_features.shaderUniformBufferArrayDynamicIndexing())
      .setSparseBinding(vk_features.sparseBinding())
      .setSparseResidency16Samples(vk_features.sparseResidency16Samples())
      .setSparseResidency2Samples(vk_features.sparseResidency2Samples())
      .setSparseResidency4Samples(vk_features.sparseResidency4Samples())
      .setSparseResidency8Samples(vk_features.sparseResidency8Samples())
      .setSparseResidencyAliased(vk_features.sparseResidencyAliased())
      .setSparseResidencyBuffer(vk_features.sparseResidencyBuffer())
      .setSparseResidencyImage2D(vk_features.sparseResidencyImage2D())
      .setSparseResidencyImage3D(vk_features.sparseResidencyImage3D())
      .setTessellationShader(vk_features.tessellationShader())
      .setTextureCompressionASTC_LDR(vk_features.textureCompressionASTC_LDR())
      .setTextureCompressionBC(vk_features.textureCompressionBC())
      .setTextureCompressionETC2(vk_features.textureCompressionETC2())
      .setVariableMultisampleRate(vk_features.variableMultisampleRate())
      .setVertexPipelineStoresAndAtomics(vk_features.vertexPipelineStoresAndAtomics())
      .setWideLines(vk_features.wideLines())
      .build();
  }

  /**
   * This method is not hand-written: See limits-set.sh
   */

  private static VulkanPhysicalDeviceLimits parsePhysicalDeviceLimits(
    final VkPhysicalDeviceLimits vk_limits)
  {
    return VulkanPhysicalDeviceLimits.builder()
      .setBufferImageGranularity(vk_limits.bufferImageGranularity())
      .setDiscreteQueuePriorities(vk_limits.discreteQueuePriorities())
      .setFramebufferColorSampleCounts(vk_limits.framebufferColorSampleCounts())
      .setFramebufferDepthSampleCounts(vk_limits.framebufferDepthSampleCounts())
      .setFramebufferNoAttachmentsSampleCounts(vk_limits.framebufferNoAttachmentsSampleCounts())
      .setFramebufferStencilSampleCounts(vk_limits.framebufferStencilSampleCounts())
      .setLineWidthGranularity(vk_limits.lineWidthGranularity())
      .setLineWidthRange(parseLineWidthRange(vk_limits.lineWidthRange()))
      .setMaxBoundDescriptorSets(vk_limits.maxBoundDescriptorSets())
      .setMaxClipDistances(vk_limits.maxClipDistances())
      .setMaxColorAttachments(vk_limits.maxColorAttachments())
      .setMaxCombinedClipAndCullDistances(vk_limits.maxCombinedClipAndCullDistances())
      .setMaxComputeSharedMemorySize(vk_limits.maxComputeSharedMemorySize())
      .setMaxComputeWorkGroupCount(parseComputeWorkGroupCount(vk_limits.maxComputeWorkGroupCount()))
      .setMaxComputeWorkGroupInvocations(vk_limits.maxComputeWorkGroupInvocations())
      .setMaxComputeWorkGroupSize(parseComputeWorkGroupSize(vk_limits.maxComputeWorkGroupSize()))
      .setMaxCullDistances(vk_limits.maxCullDistances())
      .setMaxDescriptorSetInputAttachments(vk_limits.maxDescriptorSetInputAttachments())
      .setMaxDescriptorSetSampledImages(vk_limits.maxDescriptorSetSampledImages())
      .setMaxDescriptorSetSamplers(vk_limits.maxDescriptorSetSamplers())
      .setMaxDescriptorSetStorageBuffersDynamic(vk_limits.maxDescriptorSetStorageBuffersDynamic())
      .setMaxDescriptorSetStorageBuffers(vk_limits.maxDescriptorSetStorageBuffers())
      .setMaxDescriptorSetStorageImages(vk_limits.maxDescriptorSetStorageImages())
      .setMaxDescriptorSetUniformBuffersDynamic(vk_limits.maxDescriptorSetUniformBuffersDynamic())
      .setMaxDescriptorSetUniformBuffers(vk_limits.maxDescriptorSetUniformBuffers())
      .setMaxDrawIndexedIndexValue(vk_limits.maxDrawIndexedIndexValue())
      .setMaxDrawIndirectCount(vk_limits.maxDrawIndirectCount())
      .setMaxFragmentCombinedOutputResources(vk_limits.maxFragmentCombinedOutputResources())
      .setMaxFragmentDualSrcAttachments(vk_limits.maxFragmentDualSrcAttachments())
      .setMaxFragmentInputComponents(vk_limits.maxFragmentInputComponents())
      .setMaxFragmentOutputAttachments(vk_limits.maxFragmentOutputAttachments())
      .setMaxFramebufferHeight(vk_limits.maxFramebufferHeight())
      .setMaxFramebufferLayers(vk_limits.maxFramebufferLayers())
      .setMaxFramebufferWidth(vk_limits.maxFramebufferWidth())
      .setMaxGeometryInputComponents(vk_limits.maxGeometryInputComponents())
      .setMaxGeometryOutputComponents(vk_limits.maxGeometryOutputComponents())
      .setMaxGeometryOutputVertices(vk_limits.maxGeometryOutputVertices())
      .setMaxGeometryShaderInvocations(vk_limits.maxGeometryShaderInvocations())
      .setMaxGeometryTotalOutputComponents(vk_limits.maxGeometryTotalOutputComponents())
      .setMaxImageArrayLayers(vk_limits.maxImageArrayLayers())
      .setMaxImageDimension1D(vk_limits.maxImageDimension1D())
      .setMaxImageDimension2D(vk_limits.maxImageDimension2D())
      .setMaxImageDimension3D(vk_limits.maxImageDimension3D())
      .setMaxImageDimensionCube(vk_limits.maxImageDimensionCube())
      .setMaxInterpolationOffset(vk_limits.maxInterpolationOffset())
      .setMaxMemoryAllocationCount(vk_limits.maxMemoryAllocationCount())
      .setMaxPerStageDescriptorInputAttachments(vk_limits.maxPerStageDescriptorInputAttachments())
      .setMaxPerStageDescriptorSampledImages(vk_limits.maxPerStageDescriptorSampledImages())
      .setMaxPerStageDescriptorSamplers(vk_limits.maxPerStageDescriptorSamplers())
      .setMaxPerStageDescriptorStorageBuffers(vk_limits.maxPerStageDescriptorStorageBuffers())
      .setMaxPerStageDescriptorStorageImages(vk_limits.maxPerStageDescriptorStorageImages())
      .setMaxPerStageDescriptorUniformBuffers(vk_limits.maxPerStageDescriptorUniformBuffers())
      .setMaxPerStageResources(vk_limits.maxPerStageResources())
      .setMaxPushConstantsSize(vk_limits.maxPushConstantsSize())
      .setMaxSampleMaskWords(vk_limits.maxSampleMaskWords())
      .setMaxSamplerAllocationCount(vk_limits.maxSamplerAllocationCount())
      .setMaxSamplerAnisotropy(vk_limits.maxSamplerAnisotropy())
      .setMaxSamplerLodBias(vk_limits.maxSamplerLodBias())
      .setMaxStorageBufferRange(vk_limits.maxStorageBufferRange())
      .setMaxTessellationControlPerPatchOutputComponents(vk_limits.maxTessellationControlPerPatchOutputComponents())
      .setMaxTessellationControlPerVertexInputComponents(vk_limits.maxTessellationControlPerVertexInputComponents())
      .setMaxTessellationControlPerVertexOutputComponents(vk_limits.maxTessellationControlPerVertexOutputComponents())
      .setMaxTessellationControlTotalOutputComponents(vk_limits.maxTessellationControlTotalOutputComponents())
      .setMaxTessellationEvaluationInputComponents(vk_limits.maxTessellationEvaluationInputComponents())
      .setMaxTessellationEvaluationOutputComponents(vk_limits.maxTessellationEvaluationOutputComponents())
      .setMaxTessellationGenerationLevel(vk_limits.maxTessellationGenerationLevel())
      .setMaxTessellationPatchSize(vk_limits.maxTessellationPatchSize())
      .setMaxTexelBufferElements(vk_limits.maxTexelBufferElements())
      .setMaxTexelGatherOffset(vk_limits.maxTexelGatherOffset())
      .setMaxTexelOffset(vk_limits.maxTexelOffset())
      .setMaxUniformBufferRange(vk_limits.maxUniformBufferRange())
      .setMaxVertexInputAttributeOffset(vk_limits.maxVertexInputAttributeOffset())
      .setMaxVertexInputAttributes(vk_limits.maxVertexInputAttributes())
      .setMaxVertexInputBindings(vk_limits.maxVertexInputBindings())
      .setMaxVertexInputBindingStride(vk_limits.maxVertexInputBindingStride())
      .setMaxVertexOutputComponents(vk_limits.maxVertexOutputComponents())
      .setMaxViewportDimensions(parseViewportDimensions(vk_limits.maxViewportDimensions()))
      .setMaxViewports(vk_limits.maxViewports())
      .setMinInterpolationOffset(vk_limits.minInterpolationOffset())
      .setMinMemoryMapAlignment(vk_limits.minMemoryMapAlignment())
      .setMinStorageBufferOffsetAlignment(vk_limits.minStorageBufferOffsetAlignment())
      .setMinTexelBufferOffsetAlignment(vk_limits.minTexelBufferOffsetAlignment())
      .setMinTexelGatherOffset(vk_limits.minTexelGatherOffset())
      .setMinTexelOffset(vk_limits.minTexelOffset())
      .setMinUniformBufferOffsetAlignment(vk_limits.minUniformBufferOffsetAlignment())
      .setMipmapPrecisionBits(vk_limits.mipmapPrecisionBits())
      .setNonCoherentAtomSize(vk_limits.nonCoherentAtomSize())
      .setOptimalBufferCopyOffsetAlignment(vk_limits.optimalBufferCopyOffsetAlignment())
      .setOptimalBufferCopyRowPitchAlignment(vk_limits.optimalBufferCopyRowPitchAlignment())
      .setPointSizeGranularity(vk_limits.pointSizeGranularity())
      .setPointSizeRange(parsePointSizeRange(vk_limits.pointSizeRange()))
      .setSampledImageColorSampleCounts(vk_limits.sampledImageColorSampleCounts())
      .setSampledImageDepthSampleCounts(vk_limits.sampledImageDepthSampleCounts())
      .setSampledImageIntegerSampleCounts(vk_limits.sampledImageIntegerSampleCounts())
      .setSampledImageStencilSampleCounts(vk_limits.sampledImageStencilSampleCounts())
      .setSparseAddressSpaceSize(vk_limits.sparseAddressSpaceSize())
      .setStandardSampleLocations(vk_limits.standardSampleLocations())
      .setStorageImageSampleCounts(vk_limits.storageImageSampleCounts())
      .setStrictLines(vk_limits.strictLines())
      .setSubPixelInterpolationOffsetBits(vk_limits.subPixelInterpolationOffsetBits())
      .setSubPixelPrecisionBits(vk_limits.subPixelPrecisionBits())
      .setSubTexelPrecisionBits(vk_limits.subTexelPrecisionBits())
      .setTimestampComputeAndGraphics(vk_limits.timestampComputeAndGraphics())
      .setTimestampPeriod(vk_limits.timestampPeriod())
      .setViewportBoundsRange(parseViewportBoundsRange(vk_limits.viewportBoundsRange()))
      .setViewportSubPixelBits(vk_limits.viewportSubPixelBits())
      .build();
  }

  private static VulkanViewportBoundsRange parseViewportBoundsRange(
    final FloatBuffer buffer)
  {
    return VulkanViewportBoundsRange.of(
      buffer.get(0),
      buffer.get(1));
  }

  private static VulkanPointSizeRange parsePointSizeRange(
    final FloatBuffer buffer)
  {
    return VulkanPointSizeRange.of(
      buffer.get(0),
      buffer.get(1));
  }

  private static VulkanViewportDimensions parseViewportDimensions(
    final IntBuffer buffer)
  {
    return VulkanViewportDimensions.of(
      buffer.get(0),
      buffer.get(1));
  }

  private static VulkanComputeWorkGroupSize parseComputeWorkGroupSize(
    final IntBuffer buffer)
  {
    return VulkanComputeWorkGroupSize.of(
      buffer.get(0),
      buffer.get(1),
      buffer.get(2));
  }

  private static VulkanComputeWorkGroupCount parseComputeWorkGroupCount(
    final IntBuffer buffer)
  {
    return VulkanComputeWorkGroupCount.of(
      buffer.get(0),
      buffer.get(1),
      buffer.get(2));
  }

  private static VulkanLineWidthRange parseLineWidthRange(
    final FloatBuffer buffer)
  {
    return VulkanLineWidthRange.of(
      buffer.get(0),
      buffer.get(1));
  }

  @Override
  public boolean equals(final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || !Objects.equals(this.getClass(), o.getClass())) {
      return false;
    }
    final var that = (VulkanLWJGLInstance) o;
    return Objects.equals(this.instance, that.instance);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(this.instance);
  }

  VulkanLWJGLExtensionsRegistry extensionRegistry()
  {
    return this.extension_registry;
  }

  @Override
  public String toString()
  {
    return new StringBuilder(64)
      .append("[VulkanLWJGLInstance 0x")
      .append(Long.toUnsignedString(this.instance.address(), 16))
      .append(']')
      .toString();
  }

  @Override
  protected Logger logger()
  {
    return LOG;
  }

  @Override
  protected void closeActual()
  {
    if (LOG.isTraceEnabled()) {
      LOG.trace("destroying instance: {}", this);
    }

    final var host_allocator = this.hostAllocatorProxy();
    VK10.vkDestroyInstance(this.instance, host_allocator.callbackBuffer());
    host_allocator.close();
  }

  @Override
  public Stream<VulkanPhysicalDeviceType> enumeratePhysicalDevices()
    throws VulkanException
  {
    this.checkNotClosed();

    final ArrayList<VulkanPhysicalDeviceType> devices;
    try (var stack = this.initial_stack.push()) {
      final var count = new int[1];
      VulkanChecks.checkReturnCode(
        VK10.vkEnumeratePhysicalDevices(this.instance, count, null),
        "vkEnumeratePhysicalDevices");

      final var device_count = count[0];
      if (device_count == 0) {
        return Stream.empty();
      }

      final var vk_physical_devices =
        stack.mallocPointer(device_count);
      VulkanChecks.checkReturnCode(
        VK10.vkEnumeratePhysicalDevices(
          this.instance, count, vk_physical_devices),
        "vkEnumeratePhysicalDevices");

      final var vk_features =
        VkPhysicalDeviceFeatures.mallocStack(stack);
      final var vk_properties =
        VkPhysicalDeviceProperties.mallocStack(stack);
      final var vk_memory =
        VkPhysicalDeviceMemoryProperties.mallocStack(stack);

      devices = new ArrayList<>(device_count);
      for (var index = 0; index < device_count; ++index) {
        vk_physical_devices.position(index);

        final var device_ptr = vk_physical_devices.get();
        final var vk_device = new VkPhysicalDevice(device_ptr, this.instance);

        final var device =
          this.parsePhysicalDevice(
            stack,
            vk_device,
            index,
            vk_features,
            vk_properties,
            vk_memory);

        devices.add(device);
      }
    }

    return devices.stream();
  }

  @Override
  public Map<String, VulkanExtensionType> enabledExtensions()
  {
    return this.extensions_enabled_read_only;
  }

  private VulkanLWJGLPhysicalDevice parsePhysicalDevice(
    final MemoryStack stack,
    final VkPhysicalDevice vk_device,
    final int index,
    final VkPhysicalDeviceFeatures vk_features,
    final VkPhysicalDeviceProperties vk_properties,
    final VkPhysicalDeviceMemoryProperties vk_memory)
  {
    VK10.vkGetPhysicalDeviceProperties(vk_device, vk_properties);
    VK10.vkGetPhysicalDeviceFeatures(vk_device, vk_features);
    VK10.vkGetPhysicalDeviceMemoryProperties(vk_device, vk_memory);

    final var properties =
      parsePhysicalDeviceProperties(vk_properties, index);
    final var limits =
      parsePhysicalDeviceLimits(vk_properties.limits());
    final var features =
      parsePhysicalDeviceFeatures(vk_features);
    final var memory =
      parsePhysicalDeviceMemoryProperties(vk_memory);
    final var queue_families =
      parsePhysicalDeviceQueueFamilies(stack, vk_device);

    return new VulkanLWJGLPhysicalDevice(
      this,
      vk_device,
      properties,
      limits,
      features,
      memory,
      queue_families,
      this.hostAllocatorProxy());
  }

  VkInstance instance()
  {
    return this.instance;
  }
}
