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
import com.io7m.jcoronado.api.VulkanConformanceVersion;
import com.io7m.jcoronado.api.VulkanDriverIdType;
import com.io7m.jcoronado.api.VulkanDriverKnownId;
import com.io7m.jcoronado.api.VulkanDriverUnknownId;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionType;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLineWidthRange;
import com.io7m.jcoronado.api.VulkanMemoryHeap;
import com.io7m.jcoronado.api.VulkanMemoryHeapFlag;
import com.io7m.jcoronado.api.VulkanMemoryHeapIndex;
import com.io7m.jcoronado.api.VulkanMemoryPropertyFlag;
import com.io7m.jcoronado.api.VulkanMemoryType;
import com.io7m.jcoronado.api.VulkanMemoryTypeIndex;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceDriverProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures10;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures11;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures12;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures13;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceIDProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceLimits;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceMemoryProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanPointSizeRange;
import com.io7m.jcoronado.api.VulkanQueueFamilyIndex;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag;
import com.io7m.jcoronado.api.VulkanVersion;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.api.VulkanViewportBoundsRange;
import com.io7m.jcoronado.api.VulkanViewportDimensions;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK11;
import org.lwjgl.vulkan.VkConformanceVersion;
import org.lwjgl.vulkan.VkExtent3D;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkMemoryHeap;
import org.lwjgl.vulkan.VkMemoryType;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceDriverProperties;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures2;
import org.lwjgl.vulkan.VkPhysicalDeviceIDProperties;
import org.lwjgl.vulkan.VkPhysicalDeviceLimits;
import org.lwjgl.vulkan.VkPhysicalDeviceMemoryProperties;
import org.lwjgl.vulkan.VkPhysicalDeviceProperties;
import org.lwjgl.vulkan.VkPhysicalDeviceProperties2;
import org.lwjgl.vulkan.VkPhysicalDeviceVulkan11Features;
import org.lwjgl.vulkan.VkPhysicalDeviceVulkan12Features;
import org.lwjgl.vulkan.VkPhysicalDeviceVulkan13Features;
import org.lwjgl.vulkan.VkQueueFamilyProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Stream;

import static org.lwjgl.vulkan.VK11.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2;
import static org.lwjgl.vulkan.VK11.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_ID_PROPERTIES;
import static org.lwjgl.vulkan.VK11.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_PROPERTIES_2;
import static org.lwjgl.vulkan.VK12.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_DRIVER_PROPERTIES;
import static org.lwjgl.vulkan.VK12.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_1_FEATURES;
import static org.lwjgl.vulkan.VK12.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_2_FEATURES;
import static org.lwjgl.vulkan.VK13.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_3_FEATURES;

/**
 * LWJGL {@link VkInstance}
 */

public final class VulkanLWJGLInstance
  extends VulkanLWJGLHandle implements VulkanInstanceType
{
  private static final Logger LOG = LoggerFactory.getLogger(VulkanLWJGLInstance.class);

  private final VkInstance instance;
  private final MemoryStack initialStack;
  private final Map<String, VulkanExtensionType> extensionsEnabledReadOnly;
  private final VulkanLWJGLExtensionsRegistry extensionsRegistry;
  private final VulkanVersion apiVersionMaximumSupported;
  private final VulkanVersion apiVersionUsed;

  VulkanLWJGLInstance(
    final VkInstance inInstance,
    final VulkanLWJGLExtensionsRegistry inExtensionRegistry,
    final Map<String, VulkanExtensionType> inExtensionsEnabled,
    final VulkanLWJGLHostAllocatorProxy inHostAllocatorProxy,
    final VulkanVersion inApiVersionMaximumSupported,
    final VulkanVersion inApiVersionUsed)
  {
    super(Ownership.USER_OWNED, inHostAllocatorProxy);

    this.instance =
      Objects.requireNonNull(inInstance, "instance");
    this.extensionsRegistry =
      Objects.requireNonNull(inExtensionRegistry, "extension_registry");
    this.apiVersionMaximumSupported =
      Objects.requireNonNull(
        inApiVersionMaximumSupported,
        "apiVersionMaximumSupported");
    this.apiVersionUsed =
      Objects.requireNonNull(inApiVersionUsed, "apiVersionUsed");
    this.initialStack =
      MemoryStack.create();
    this.extensionsEnabledReadOnly =
      Collections.unmodifiableMap(
        Objects.requireNonNull(inExtensionsEnabled, "in_extensions"));
  }

  private static SortedMap<VulkanQueueFamilyIndex, VulkanQueueFamilyProperties>
  parsePhysicalDeviceQueueFamilies(
    final MemoryStack stackInitial,
    final VkPhysicalDevice vkDevice)
  {
    final SortedMap<VulkanQueueFamilyIndex, VulkanQueueFamilyProperties> families;
    try (var stack = stackInitial.push()) {
      final var count = new int[1];

      VK10.vkGetPhysicalDeviceQueueFamilyProperties(vkDevice, count, null);

      final var queueFamilyCount = count[0];
      if (queueFamilyCount == 0) {
        return Collections.emptySortedMap();
      }

      final var vkQueueFamilies =
        VkQueueFamilyProperties.malloc(queueFamilyCount, stack);

      VK10.vkGetPhysicalDeviceQueueFamilyProperties(
        vkDevice, count, vkQueueFamilies);

      families = new TreeMap<>();
      for (var index = 0; index < queueFamilyCount; ++index) {
        vkQueueFamilies.position(index);

        final var properties =
          VulkanQueueFamilyProperties.of(
            new VulkanQueueFamilyIndex(index),
            vkQueueFamilies.queueCount(),
            parseQueueFlags(vkQueueFamilies.queueFlags()),
            vkQueueFamilies.timestampValidBits(),
            parseExtent3D(vkQueueFamilies.minImageTransferGranularity()));

        families.put(
          new VulkanQueueFamilyIndex(index),
          properties
        );
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

    final var heaps = new HashMap<VulkanMemoryHeapIndex, VulkanMemoryHeap>();
    for (var index = 0; index < vk_memory.memoryHeapCount(); ++index) {
      final var heap = vk_memory.memoryHeaps(index);
      final var parsed = parseHeap(index, heap);
      heaps.put(parsed.index(), parsed);
    }

    final var types = new HashMap<VulkanMemoryTypeIndex, VulkanMemoryType>();
    for (var index = 0; index < vk_memory.memoryTypeCount(); ++index) {
      final var type = vk_memory.memoryTypes(index);
      final var parsed = parseType(index, type);
      types.put(parsed.index(), parsed);
    }

    builder.setHeaps(new TreeMap<>(heaps));
    builder.setTypes(new TreeMap<>(types));
    return builder.build();
  }

  private static VulkanMemoryType parseType(
    final int typeIndex,
    final VkMemoryType type)
  {
    return VulkanMemoryType.of(
      new VulkanMemoryTypeIndex(typeIndex),
      new VulkanMemoryHeapIndex(type.heapIndex()),
      parseTypeFlags(type.propertyFlags())
    );
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
    final int heapIndex,
    final VkMemoryHeap heap)
  {
    return VulkanMemoryHeap.of(
      new VulkanMemoryHeapIndex(heapIndex),
      heap.size(),
      parseHeapFlags(heap.flags())
    );
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
   * This method is not hand-written: See GenerateFeatures10MethodNamesImmutable.
   */

  private static VulkanPhysicalDeviceFeatures10 parsePhysicalDeviceFeatures10(
    final VkPhysicalDeviceFeatures vkFeatures)
  {
    return VulkanPhysicalDeviceFeatures10.builder()
      .setAlphaToOne(
        vkFeatures.alphaToOne())
      .setDepthBiasClamp(
        vkFeatures.depthBiasClamp())
      .setDepthBounds(
        vkFeatures.depthBounds())
      .setDepthClamp(
        vkFeatures.depthClamp())
      .setDrawIndirectFirstInstance(
        vkFeatures.drawIndirectFirstInstance())
      .setDualSrcBlend(
        vkFeatures.dualSrcBlend())
      .setFillModeNonSolid(
        vkFeatures.fillModeNonSolid())
      .setFragmentStoresAndAtomics(
        vkFeatures.fragmentStoresAndAtomics())
      .setFullDrawIndexUint32(
        vkFeatures.fullDrawIndexUint32())
      .setGeometryShader(
        vkFeatures.geometryShader())
      .setImageCubeArray(
        vkFeatures.imageCubeArray())
      .setIndependentBlend(
        vkFeatures.independentBlend())
      .setInheritedQueries(
        vkFeatures.inheritedQueries())
      .setLargePoints(
        vkFeatures.largePoints())
      .setLogicOp(
        vkFeatures.logicOp())
      .setMultiDrawIndirect(
        vkFeatures.multiDrawIndirect())
      .setMultiViewport(
        vkFeatures.multiViewport())
      .setOcclusionQueryPrecise(
        vkFeatures.occlusionQueryPrecise())
      .setPipelineStatisticsQuery(
        vkFeatures.pipelineStatisticsQuery())
      .setRobustBufferAccess(
        vkFeatures.robustBufferAccess())
      .setSamplerAnisotropy(
        vkFeatures.samplerAnisotropy())
      .setSampleRateShading(
        vkFeatures.sampleRateShading())
      .setShaderClipDistance(
        vkFeatures.shaderClipDistance())
      .setShaderCullDistance(
        vkFeatures.shaderCullDistance())
      .setShaderFloat64(
        vkFeatures.shaderFloat64())
      .setShaderImageGatherExtended(
        vkFeatures.shaderImageGatherExtended())
      .setShaderInt16(
        vkFeatures.shaderInt16())
      .setShaderInt64(
        vkFeatures.shaderInt64())
      .setShaderResourceMinLod(
        vkFeatures.shaderResourceMinLod())
      .setShaderResourceResidency(
        vkFeatures.shaderResourceResidency())
      .setShaderSampledImageArrayDynamicIndexing(
        vkFeatures.shaderSampledImageArrayDynamicIndexing())
      .setShaderStorageBufferArrayDynamicIndexing(
        vkFeatures.shaderStorageBufferArrayDynamicIndexing())
      .setShaderStorageImageArrayDynamicIndexing(
        vkFeatures.shaderStorageImageArrayDynamicIndexing())
      .setShaderStorageImageExtendedFormats(
        vkFeatures.shaderStorageImageExtendedFormats())
      .setShaderStorageImageMultisample(
        vkFeatures.shaderStorageImageMultisample())
      .setShaderStorageImageReadWithoutFormat(
        vkFeatures.shaderStorageImageReadWithoutFormat())
      .setShaderStorageImageWriteWithoutFormat(
        vkFeatures.shaderStorageImageWriteWithoutFormat())
      .setShaderTessellationAndGeometryPointSize(
        vkFeatures.shaderTessellationAndGeometryPointSize())
      .setShaderUniformBufferArrayDynamicIndexing(
        vkFeatures.shaderUniformBufferArrayDynamicIndexing())
      .setSparseBinding(
        vkFeatures.sparseBinding())
      .setSparseResidency16Samples(
        vkFeatures.sparseResidency16Samples())
      .setSparseResidency2Samples(
        vkFeatures.sparseResidency2Samples())
      .setSparseResidency4Samples(
        vkFeatures.sparseResidency4Samples())
      .setSparseResidency8Samples(
        vkFeatures.sparseResidency8Samples())
      .setSparseResidencyAliased(
        vkFeatures.sparseResidencyAliased())
      .setSparseResidencyBuffer(
        vkFeatures.sparseResidencyBuffer())
      .setSparseResidencyImage2D(
        vkFeatures.sparseResidencyImage2D())
      .setSparseResidencyImage3D(
        vkFeatures.sparseResidencyImage3D())
      .setTessellationShader(
        vkFeatures.tessellationShader())
      .setTextureCompressionASTC_LDR(
        vkFeatures.textureCompressionASTC_LDR())
      .setTextureCompressionBC(
        vkFeatures.textureCompressionBC())
      .setTextureCompressionETC2(
        vkFeatures.textureCompressionETC2())
      .setVariableMultisampleRate(
        vkFeatures.variableMultisampleRate())
      .setVertexPipelineStoresAndAtomics(
        vkFeatures.vertexPipelineStoresAndAtomics())
      .setWideLines(
        vkFeatures.wideLines())
      .build();
  }

  /**
   * This method is not hand-written: See GenerateLimitsMethodNamesImmutable.
   */

  private static VulkanPhysicalDeviceLimits parsePhysicalDeviceLimits(
    final VkPhysicalDeviceLimits vkLimits)
  {
    return VulkanPhysicalDeviceLimits.builder()
      .setBufferImageGranularity(
        vkLimits.bufferImageGranularity())
      .setDiscreteQueuePriorities(
        vkLimits.discreteQueuePriorities())
      .setFramebufferColorSampleCounts(
        vkLimits.framebufferColorSampleCounts())
      .setFramebufferDepthSampleCounts(
        vkLimits.framebufferDepthSampleCounts())
      .setFramebufferNoAttachmentsSampleCounts(
        vkLimits.framebufferNoAttachmentsSampleCounts())
      .setFramebufferStencilSampleCounts(
        vkLimits.framebufferStencilSampleCounts())
      .setLineWidthGranularity(
        vkLimits.lineWidthGranularity())
      .setLineWidthRange(parseLineWidthRange(
        vkLimits.lineWidthRange()))
      .setMaxBoundDescriptorSets(
        vkLimits.maxBoundDescriptorSets())
      .setMaxClipDistances(
        vkLimits.maxClipDistances())
      .setMaxColorAttachments(
        vkLimits.maxColorAttachments())
      .setMaxCombinedClipAndCullDistances(
        vkLimits.maxCombinedClipAndCullDistances())
      .setMaxComputeSharedMemorySize(
        vkLimits.maxComputeSharedMemorySize())
      .setMaxComputeWorkGroupCount(parseComputeWorkGroupCount(
        vkLimits.maxComputeWorkGroupCount()))
      .setMaxComputeWorkGroupInvocations(
        vkLimits.maxComputeWorkGroupInvocations())
      .setMaxComputeWorkGroupSize(parseComputeWorkGroupSize(
        vkLimits.maxComputeWorkGroupSize()))
      .setMaxCullDistances(
        vkLimits.maxCullDistances())
      .setMaxDescriptorSetInputAttachments(
        vkLimits.maxDescriptorSetInputAttachments())
      .setMaxDescriptorSetSampledImages(
        vkLimits.maxDescriptorSetSampledImages())
      .setMaxDescriptorSetSamplers(
        vkLimits.maxDescriptorSetSamplers())
      .setMaxDescriptorSetStorageBuffersDynamic(
        vkLimits.maxDescriptorSetStorageBuffersDynamic())
      .setMaxDescriptorSetStorageBuffers(
        vkLimits.maxDescriptorSetStorageBuffers())
      .setMaxDescriptorSetStorageImages(
        vkLimits.maxDescriptorSetStorageImages())
      .setMaxDescriptorSetUniformBuffersDynamic(
        vkLimits.maxDescriptorSetUniformBuffersDynamic())
      .setMaxDescriptorSetUniformBuffers(
        vkLimits.maxDescriptorSetUniformBuffers())
      .setMaxDrawIndexedIndexValue(
        vkLimits.maxDrawIndexedIndexValue())
      .setMaxDrawIndirectCount(
        vkLimits.maxDrawIndirectCount())
      .setMaxFragmentCombinedOutputResources(
        vkLimits.maxFragmentCombinedOutputResources())
      .setMaxFragmentDualSrcAttachments(
        vkLimits.maxFragmentDualSrcAttachments())
      .setMaxFragmentInputComponents(
        vkLimits.maxFragmentInputComponents())
      .setMaxFragmentOutputAttachments(
        vkLimits.maxFragmentOutputAttachments())
      .setMaxFramebufferHeight(
        vkLimits.maxFramebufferHeight())
      .setMaxFramebufferLayers(
        vkLimits.maxFramebufferLayers())
      .setMaxFramebufferWidth(
        vkLimits.maxFramebufferWidth())
      .setMaxGeometryInputComponents(
        vkLimits.maxGeometryInputComponents())
      .setMaxGeometryOutputComponents(
        vkLimits.maxGeometryOutputComponents())
      .setMaxGeometryOutputVertices(
        vkLimits.maxGeometryOutputVertices())
      .setMaxGeometryShaderInvocations(
        vkLimits.maxGeometryShaderInvocations())
      .setMaxGeometryTotalOutputComponents(
        vkLimits.maxGeometryTotalOutputComponents())
      .setMaxImageArrayLayers(
        vkLimits.maxImageArrayLayers())
      .setMaxImageDimension1D(
        vkLimits.maxImageDimension1D())
      .setMaxImageDimension2D(
        vkLimits.maxImageDimension2D())
      .setMaxImageDimension3D(
        vkLimits.maxImageDimension3D())
      .setMaxImageDimensionCube(
        vkLimits.maxImageDimensionCube())
      .setMaxInterpolationOffset(
        vkLimits.maxInterpolationOffset())
      .setMaxMemoryAllocationCount(
        vkLimits.maxMemoryAllocationCount())
      .setMaxPerStageDescriptorInputAttachments(
        vkLimits.maxPerStageDescriptorInputAttachments())
      .setMaxPerStageDescriptorSampledImages(
        vkLimits.maxPerStageDescriptorSampledImages())
      .setMaxPerStageDescriptorSamplers(
        vkLimits.maxPerStageDescriptorSamplers())
      .setMaxPerStageDescriptorStorageBuffers(
        vkLimits.maxPerStageDescriptorStorageBuffers())
      .setMaxPerStageDescriptorStorageImages(
        vkLimits.maxPerStageDescriptorStorageImages())
      .setMaxPerStageDescriptorUniformBuffers(
        vkLimits.maxPerStageDescriptorUniformBuffers())
      .setMaxPerStageResources(
        vkLimits.maxPerStageResources())
      .setMaxPushConstantsSize(
        vkLimits.maxPushConstantsSize())
      .setMaxSampleMaskWords(
        vkLimits.maxSampleMaskWords())
      .setMaxSamplerAllocationCount(
        vkLimits.maxSamplerAllocationCount())
      .setMaxSamplerAnisotropy(
        vkLimits.maxSamplerAnisotropy())
      .setMaxSamplerLodBias(
        vkLimits.maxSamplerLodBias())
      .setMaxStorageBufferRange(
        vkLimits.maxStorageBufferRange())
      .setMaxTessellationControlPerPatchOutputComponents(
        vkLimits.maxTessellationControlPerPatchOutputComponents())
      .setMaxTessellationControlPerVertexInputComponents(
        vkLimits.maxTessellationControlPerVertexInputComponents())
      .setMaxTessellationControlPerVertexOutputComponents(
        vkLimits.maxTessellationControlPerVertexOutputComponents())
      .setMaxTessellationControlTotalOutputComponents(
        vkLimits.maxTessellationControlTotalOutputComponents())
      .setMaxTessellationEvaluationInputComponents(
        vkLimits.maxTessellationEvaluationInputComponents())
      .setMaxTessellationEvaluationOutputComponents(
        vkLimits.maxTessellationEvaluationOutputComponents())
      .setMaxTessellationGenerationLevel(
        vkLimits.maxTessellationGenerationLevel())
      .setMaxTessellationPatchSize(
        vkLimits.maxTessellationPatchSize())
      .setMaxTexelBufferElements(
        vkLimits.maxTexelBufferElements())
      .setMaxTexelGatherOffset(
        vkLimits.maxTexelGatherOffset())
      .setMaxTexelOffset(
        vkLimits.maxTexelOffset())
      .setMaxUniformBufferRange(
        vkLimits.maxUniformBufferRange())
      .setMaxVertexInputAttributeOffset(
        vkLimits.maxVertexInputAttributeOffset())
      .setMaxVertexInputAttributes(
        vkLimits.maxVertexInputAttributes())
      .setMaxVertexInputBindings(
        vkLimits.maxVertexInputBindings())
      .setMaxVertexInputBindingStride(
        vkLimits.maxVertexInputBindingStride())
      .setMaxVertexOutputComponents(
        vkLimits.maxVertexOutputComponents())
      .setMaxViewportDimensions(parseViewportDimensions(
        vkLimits.maxViewportDimensions()))
      .setMaxViewports(
        vkLimits.maxViewports())
      .setMinInterpolationOffset(
        vkLimits.minInterpolationOffset())
      .setMinMemoryMapAlignment(
        vkLimits.minMemoryMapAlignment())
      .setMinStorageBufferOffsetAlignment(
        vkLimits.minStorageBufferOffsetAlignment())
      .setMinTexelBufferOffsetAlignment(
        vkLimits.minTexelBufferOffsetAlignment())
      .setMinTexelGatherOffset(
        vkLimits.minTexelGatherOffset())
      .setMinTexelOffset(
        vkLimits.minTexelOffset())
      .setMinUniformBufferOffsetAlignment(
        vkLimits.minUniformBufferOffsetAlignment())
      .setMipmapPrecisionBits(
        vkLimits.mipmapPrecisionBits())
      .setNonCoherentAtomSize(
        vkLimits.nonCoherentAtomSize())
      .setOptimalBufferCopyOffsetAlignment(
        vkLimits.optimalBufferCopyOffsetAlignment())
      .setOptimalBufferCopyRowPitchAlignment(
        vkLimits.optimalBufferCopyRowPitchAlignment())
      .setPointSizeGranularity(
        vkLimits.pointSizeGranularity())
      .setPointSizeRange(parsePointSizeRange(
        vkLimits.pointSizeRange()))
      .setSampledImageColorSampleCounts(
        vkLimits.sampledImageColorSampleCounts())
      .setSampledImageDepthSampleCounts(
        vkLimits.sampledImageDepthSampleCounts())
      .setSampledImageIntegerSampleCounts(
        vkLimits.sampledImageIntegerSampleCounts())
      .setSampledImageStencilSampleCounts(
        vkLimits.sampledImageStencilSampleCounts())
      .setSparseAddressSpaceSize(
        vkLimits.sparseAddressSpaceSize())
      .setStandardSampleLocations(
        vkLimits.standardSampleLocations())
      .setStorageImageSampleCounts(
        vkLimits.storageImageSampleCounts())
      .setStrictLines(
        vkLimits.strictLines())
      .setSubPixelInterpolationOffsetBits(
        vkLimits.subPixelInterpolationOffsetBits())
      .setSubPixelPrecisionBits(
        vkLimits.subPixelPrecisionBits())
      .setSubTexelPrecisionBits(
        vkLimits.subTexelPrecisionBits())
      .setTimestampComputeAndGraphics(
        vkLimits.timestampComputeAndGraphics())
      .setTimestampPeriod(
        vkLimits.timestampPeriod())
      .setViewportBoundsRange(parseViewportBoundsRange(
        vkLimits.viewportBoundsRange()))
      .setViewportSubPixelBits(
        vkLimits.viewportSubPixelBits())
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

  private static VulkanPhysicalDeviceFeatures parseAllFeatures(
    final MemoryStack stack,
    final VkPhysicalDevice vkDevice,
    final VulkanVersion requestedApiVersion)
  {
    /*
     * For Vulkan 1.0, we need to call the plain vkGetPhysicalDeviceFeatures
     * function.
     */

    if (requestedApiVersion.major() == 1 && requestedApiVersion.minor() == 0) {
      LOG.debug(
        "requested API version is {}; physical device features retrieved using vkGetPhysicalDeviceFeatures",
        requestedApiVersion.toHumanString()
      );

      final var vkFeatures =
        VkPhysicalDeviceFeatures.calloc(stack);

      VK10.vkGetPhysicalDeviceFeatures(vkDevice, vkFeatures);

      final var features10 =
        parsePhysicalDeviceFeatures10(vkFeatures);
      final var features11 =
        VulkanPhysicalDeviceFeatures11.builder()
          .build();
      final var features12 =
        VulkanPhysicalDeviceFeatures12.builder()
          .build();
      final var features13 =
        VulkanPhysicalDeviceFeatures13.builder()
          .build();

      return VulkanPhysicalDeviceFeatures.builder()
        .setFeatures10(features10)
        .setFeatures11(features11)
        .setFeatures12(features12)
        .setFeatures13(features13)
        .build();
    }

    /*
     * For newer versions of Vulkan, we call vkGetPhysicalDeviceFeatures2
     * and fetch all the new embedded structures.
     */

    final var vkFeatures13 =
      VkPhysicalDeviceVulkan13Features.calloc(stack);
    vkFeatures13.sType(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_3_FEATURES);

    final var vkFeatures12 =
      VkPhysicalDeviceVulkan12Features.calloc(stack);
    vkFeatures12.sType(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_2_FEATURES);

    final var vkFeatures11 =
      VkPhysicalDeviceVulkan11Features.calloc(stack);
    vkFeatures11.sType(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_1_FEATURES);

    final var vkFeatures =
      VkPhysicalDeviceFeatures2.calloc(stack);
    vkFeatures.sType(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2);

    vkFeatures12.pNext(vkFeatures13.address());
    vkFeatures11.pNext(vkFeatures12.address());
    vkFeatures.pNext(vkFeatures11.address());

    LOG.debug(
      "requested API version is {}; physical device features retrieved using vkGetPhysicalDeviceFeatures2",
      requestedApiVersion.toHumanString()
    );

    VK11.vkGetPhysicalDeviceFeatures2(vkDevice, vkFeatures);

    final var features10 =
      parsePhysicalDeviceFeatures10(vkFeatures.features());
    final var features11 =
      parsePhysicalDeviceFeatures11(vkFeatures11);
    final var features12 =
      parsePhysicalDeviceFeatures12(vkFeatures12);
    final var features13 =
      parsePhysicalDeviceFeatures13(vkFeatures13);

    return VulkanPhysicalDeviceFeatures.builder()
      .setFeatures10(features10)
      .setFeatures11(features11)
      .setFeatures12(features12)
      .setFeatures13(features13)
      .build();
  }

  private static VulkanPhysicalDeviceFeatures12 parsePhysicalDeviceFeatures12(
    final VkPhysicalDeviceVulkan12Features features12)
  {
    return VulkanPhysicalDeviceFeatures12.builder()
      .setBufferDeviceAddress(
        features12.bufferDeviceAddress())
      .setBufferDeviceAddressCaptureReplay(
        features12.bufferDeviceAddressCaptureReplay())
      .setBufferDeviceAddressMultiDevice(
        features12.bufferDeviceAddressMultiDevice())
      .setDescriptorBindingPartiallyBound(
        features12.descriptorBindingPartiallyBound())
      .setDescriptorBindingSampledImageUpdateAfterBind(
        features12.descriptorBindingSampledImageUpdateAfterBind())
      .setDescriptorBindingStorageBufferUpdateAfterBind(
        features12.descriptorBindingStorageBufferUpdateAfterBind())
      .setDescriptorBindingStorageImageUpdateAfterBind(
        features12.descriptorBindingStorageImageUpdateAfterBind())
      .setDescriptorBindingStorageTexelBufferUpdateAfterBind(
        features12.descriptorBindingStorageTexelBufferUpdateAfterBind())
      .setDescriptorBindingUniformBufferUpdateAfterBind(
        features12.descriptorBindingUniformBufferUpdateAfterBind())
      .setDescriptorBindingUniformTexelBufferUpdateAfterBind(
        features12.descriptorBindingUniformTexelBufferUpdateAfterBind())
      .setDescriptorBindingUpdateUnusedWhilePending(
        features12.descriptorBindingUpdateUnusedWhilePending())
      .setDescriptorBindingVariableDescriptorCount(
        features12.descriptorBindingVariableDescriptorCount())
      .setDescriptorIndexing(
        features12.descriptorIndexing())
      .setDrawIndirectCount(
        features12.drawIndirectCount())
      .setHostQueryReset(
        features12.hostQueryReset())
      .setImagelessFramebuffer(
        features12.imagelessFramebuffer())
      .setRuntimeDescriptorArray(
        features12.runtimeDescriptorArray())
      .setSamplerFilterMinmax(
        features12.samplerFilterMinmax())
      .setSamplerMirrorClampToEdge(
        features12.samplerMirrorClampToEdge())
      .setScalarBlockLayout(
        features12.scalarBlockLayout())
      .setSeparateDepthStencilLayouts(
        features12.separateDepthStencilLayouts())
      .setShaderBufferInt64Atomics(
        features12.shaderBufferInt64Atomics())
      .setShaderFloat16(
        features12.shaderFloat16())
      .setShaderInputAttachmentArrayDynamicIndexing(
        features12.shaderInputAttachmentArrayDynamicIndexing())
      .setShaderInputAttachmentArrayNonUniformIndexing(
        features12.shaderInputAttachmentArrayNonUniformIndexing())
      .setShaderInt8(
        features12.shaderInt8())
      .setShaderOutputLayer(
        features12.shaderOutputLayer())
      .setShaderOutputViewportIndex(
        features12.shaderOutputViewportIndex())
      .setShaderSampledImageArrayNonUniformIndexing(
        features12.shaderSampledImageArrayNonUniformIndexing())
      .setShaderSharedInt64Atomics(
        features12.shaderSharedInt64Atomics())
      .setShaderStorageBufferArrayNonUniformIndexing(
        features12.shaderStorageBufferArrayNonUniformIndexing())
      .setShaderStorageImageArrayNonUniformIndexing(
        features12.shaderStorageImageArrayNonUniformIndexing())
      .setShaderStorageTexelBufferArrayDynamicIndexing(
        features12.shaderStorageTexelBufferArrayDynamicIndexing())
      .setShaderStorageTexelBufferArrayNonUniformIndexing(
        features12.shaderStorageTexelBufferArrayNonUniformIndexing())
      .setShaderSubgroupExtendedTypes(
        features12.shaderSubgroupExtendedTypes())
      .setShaderUniformBufferArrayNonUniformIndexing(
        features12.shaderUniformBufferArrayNonUniformIndexing())
      .setShaderUniformTexelBufferArrayDynamicIndexing(
        features12.shaderUniformTexelBufferArrayDynamicIndexing())
      .setShaderUniformTexelBufferArrayNonUniformIndexing(
        features12.shaderUniformTexelBufferArrayNonUniformIndexing())
      .setStorageBuffer8BitAccess(
        features12.storageBuffer8BitAccess())
      .setStoragePushConstant8(
        features12.storagePushConstant8())
      .setSubgroupBroadcastDynamicId(
        features12.subgroupBroadcastDynamicId())
      .setTimelineSemaphore(
        features12.timelineSemaphore())
      .setUniformAndStorageBuffer8BitAccess(
        features12.uniformAndStorageBuffer8BitAccess())
      .setUniformBufferStandardLayout(
        features12.uniformBufferStandardLayout())
      .setVulkanMemoryModel(
        features12.vulkanMemoryModel())
      .setVulkanMemoryModelAvailabilityVisibilityChains(
        features12.vulkanMemoryModelAvailabilityVisibilityChains())
      .setVulkanMemoryModelDeviceScope(
        features12.vulkanMemoryModelDeviceScope())
      .build();
  }

  private static VulkanPhysicalDeviceFeatures11 parsePhysicalDeviceFeatures11(
    final VkPhysicalDeviceVulkan11Features features11)
  {
    return VulkanPhysicalDeviceFeatures11.builder()
      .setMultiview(
        features11.multiview())
      .setMultiviewGeometryShader(
        features11.multiviewGeometryShader())
      .setMultiviewTessellationShader(
        features11.multiviewTessellationShader())
      .setProtectedMemory(
        features11.protectedMemory())
      .setSamplerYcbcrConversion(
        features11.samplerYcbcrConversion())
      .setShaderDrawParameters(
        features11.shaderDrawParameters())
      .setStorageBuffer16BitAccess(
        features11.storageBuffer16BitAccess())
      .setStorageInputOutput16(
        features11.storageInputOutput16())
      .setStoragePushConstant16(
        features11.storagePushConstant16())
      .setUniformAndStorageBuffer16BitAccess(
        features11.uniformAndStorageBuffer16BitAccess())
      .setVariablePointers(
        features11.variablePointers())
      .setVariablePointersStorageBuffer(
        features11.variablePointersStorageBuffer())
      .build();
  }

  private static VulkanPhysicalDeviceFeatures13 parsePhysicalDeviceFeatures13(
    final VkPhysicalDeviceVulkan13Features features13)
  {
    return VulkanPhysicalDeviceFeatures13.builder()
      .setComputeFullSubgroups(
        features13.computeFullSubgroups())
      .setDescriptorBindingInlineUniformBlockUpdateAfterBind(
        features13.descriptorBindingInlineUniformBlockUpdateAfterBind())
      .setDynamicRendering(
        features13.dynamicRendering())
      .setInlineUniformBlock(
        features13.inlineUniformBlock())
      .setMaintenance4(
        features13.maintenance4())
      .setPipelineCreationCacheControl(
        features13.pipelineCreationCacheControl())
      .setPrivateData(
        features13.privateData())
      .setRobustImageAccess(
        features13.robustImageAccess())
      .setShaderDemoteToHelperInvocation(
        features13.shaderDemoteToHelperInvocation())
      .setShaderIntegerDotProduct(
        features13.shaderIntegerDotProduct())
      .setShaderZeroInitializeWorkgroupMemory(
        features13.shaderZeroInitializeWorkgroupMemory())
      .setSubgroupSizeControl(
        features13.subgroupSizeControl())
      .setSynchronization2(
        features13.synchronization2())
      .setTextureCompressionASTC_HDR(
        features13.textureCompressionASTC_HDR())
      .build();
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
    return this.extensionsRegistry;
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
    try (var stack = this.initialStack.push()) {
      final var count = new int[1];
      VulkanChecks.checkReturnCode(
        VK10.vkEnumeratePhysicalDevices(this.instance, count, null),
        "vkEnumeratePhysicalDevices");

      final var deviceCount = count[0];
      if (deviceCount == 0) {
        return Stream.empty();
      }

      final var vkPhysicalDevices =
        stack.mallocPointer(deviceCount);
      VulkanChecks.checkReturnCode(
        VK10.vkEnumeratePhysicalDevices(
          this.instance, count, vkPhysicalDevices),
        "vkEnumeratePhysicalDevices");

      final var vkProperties =
        VkPhysicalDeviceProperties.malloc(stack);
      final var vkMemory =
        VkPhysicalDeviceMemoryProperties.malloc(stack);

      devices = new ArrayList<>(deviceCount);
      for (var index = 0; index < deviceCount; ++index) {
        vkPhysicalDevices.position(index);

        final var devicePtr =
          vkPhysicalDevices.get();
        final var vkDevice =
          new VkPhysicalDevice(devicePtr, this.instance);

        final var device =
          this.parsePhysicalDevice(
            stack,
            vkDevice,
            index,
            vkProperties,
            vkMemory,
            this.apiVersionUsed);

        devices.add(device);
      }
    }

    return devices.stream();
  }

  @Override
  public VulkanVersion apiVersionMaximumSupported()
  {
    return this.apiVersionMaximumSupported;
  }

  @Override
  public VulkanVersion apiVersionUsed()
  {
    return this.apiVersionUsed;
  }

  @Override
  public Map<String, VulkanExtensionType> enabledExtensions()
  {
    return this.extensionsEnabledReadOnly;
  }

  private VulkanLWJGLPhysicalDevice parsePhysicalDevice(
    final MemoryStack stack,
    final VkPhysicalDevice vkDevice,
    final int index,
    final VkPhysicalDeviceProperties vkProperties,
    final VkPhysicalDeviceMemoryProperties vkMemory,
    final VulkanVersion apiVersion)
  {
    final var properties =
      parseAllProperties(stack, vkDevice, index, apiVersion);

    final var features =
      parseAllFeatures(
        stack,
        vkDevice,
        apiVersion
      );

    VK10.vkGetPhysicalDeviceMemoryProperties(vkDevice, vkMemory);

    final var limits =
      parsePhysicalDeviceLimits(vkProperties.limits());
    final var memory =
      parsePhysicalDeviceMemoryProperties(vkMemory);
    final var queueFamilies =
      parsePhysicalDeviceQueueFamilies(stack, vkDevice);

    return new VulkanLWJGLPhysicalDevice(
      this,
      vkDevice,
      properties.properties(),
      limits,
      features,
      memory,
      queueFamilies,
      this.hostAllocatorProxy(),
      properties.driverProperties(),
      properties.idProperties()
    );
  }

  private static PropertiesAndExtras parseAllProperties(
    final MemoryStack stack,
    final VkPhysicalDevice vkDevice,
    final int index,
    final VulkanVersion apiVersion)
  {
    /*
     * For Vulkan 1.0, we need to call the plain vkGetPhysicalDeviceProperties
     * function.
     */

    if (apiVersion.major() == 1 && apiVersion.minor() == 0) {
      LOG.debug(
        "requested API version is {}; physical device properties retrieved using vkGetPhysicalDeviceProperties",
        apiVersion.toHumanString()
      );

      final var vkProperties =
        VkPhysicalDeviceProperties.malloc(stack);

      VK10.vkGetPhysicalDeviceProperties(vkDevice, vkProperties);
      return new PropertiesAndExtras(
        parsePhysicalDeviceProperties(vkProperties, index),
        Optional.empty(),
        Optional.empty()
      );
    }

    /*
     * For newer versions of Vulkan, we call vkGetPhysicalDeviceProperties2
     * and fetch all the new embedded structures.
     */

    LOG.debug(
      "requested API version is {}; physical device properties retrieved using vkGetPhysicalDeviceProperties2",
      apiVersion.toHumanString()
    );

    final var vkIdProperties =
      VkPhysicalDeviceIDProperties.malloc(stack);
    vkIdProperties.sType(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_ID_PROPERTIES);
    vkIdProperties.pNext(0L);

    final var vkProperties2 =
      VkPhysicalDeviceProperties2.malloc(stack);
    vkProperties2.sType(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_PROPERTIES_2);
    vkProperties2.pNext(vkIdProperties.address());

    VkPhysicalDeviceDriverProperties vkDriverProperties = null;
    if (apiVersion.major() >= 1 && apiVersion.minor() >= 2) {
      vkDriverProperties = VkPhysicalDeviceDriverProperties.malloc(stack);
      vkDriverProperties.sType(
        VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_DRIVER_PROPERTIES);
      vkDriverProperties.pNext(0L);
      vkIdProperties.pNext(vkDriverProperties.address());
    }

    VK11.vkGetPhysicalDeviceProperties2(vkDevice, vkProperties2);

    final var properties =
      parsePhysicalDeviceProperties(vkProperties2.properties(), index);
    final var driverProperties =
      parsePhysicalDeviceDriverPropertiesOpt(Optional.ofNullable(
        vkDriverProperties));
    final var idProperties =
      parsePhysicalDeviceIdPropertiesOpt(vkIdProperties);

    return new PropertiesAndExtras(
      properties,
      driverProperties,
      idProperties
    );
  }

  private static Optional<VulkanPhysicalDeviceIDProperties>
  parsePhysicalDeviceIdPropertiesOpt(
    final VkPhysicalDeviceIDProperties vkIdProperties)
  {
    try {
      return Optional.of(parsePhysicalDeviceIdProperties(vkIdProperties));
    } catch (final IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  private static VulkanPhysicalDeviceIDProperties parsePhysicalDeviceIdProperties(
    final VkPhysicalDeviceIDProperties vkIdProperties)
  {
    return new VulkanPhysicalDeviceIDProperties(
      parseUUID(vkIdProperties.deviceUUID()),
      parseUUID(vkIdProperties.driverUUID()),
      parseUUID(vkIdProperties.deviceLUID()),
      vkIdProperties.deviceNodeMask(),
      vkIdProperties.deviceLUIDValid()
    );
  }

  private static UUID parseUUID(
    final ByteBuffer buffer)
  {
    if (buffer.capacity() < 16) {
      return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    final var data = new byte[16];
    buffer.get(data);

    final var builder = new StringBuilder(36);
    builder.append(formatByte(data[0]));
    builder.append(formatByte(data[1]));
    builder.append(formatByte(data[2]));
    builder.append(formatByte(data[3]));
    builder.append("-");
    builder.append(formatByte(data[4]));
    builder.append(formatByte(data[5]));
    builder.append("-");
    builder.append(formatByte(data[6]));
    builder.append(formatByte(data[7]));
    builder.append("-");
    builder.append(formatByte(data[8]));
    builder.append(formatByte(data[9]));
    builder.append("-");
    builder.append(formatByte(data[10]));
    builder.append(formatByte(data[11]));
    builder.append(formatByte(data[12]));
    builder.append(formatByte(data[13]));
    builder.append(formatByte(data[14]));
    builder.append(formatByte(data[15]));
    return UUID.fromString(builder.toString());
  }

  private static String formatByte(final byte b)
  {
    return String.format("%02x", Byte.valueOf(b));
  }

  private static Optional<VulkanPhysicalDeviceDriverProperties>
  parsePhysicalDeviceDriverPropertiesOpt(
    final Optional<VkPhysicalDeviceDriverProperties> vkDriverProperties)
  {
    return vkDriverProperties.map(VulkanLWJGLInstance::parsePhysicalDeviceDriverProperties);
  }

  private static VulkanPhysicalDeviceDriverProperties parsePhysicalDeviceDriverProperties(
    final VkPhysicalDeviceDriverProperties vkDeviceDriverProperties)
  {
    return new VulkanPhysicalDeviceDriverProperties(
      parseDriverId(vkDeviceDriverProperties.driverID()),
      vkDeviceDriverProperties.driverNameString(),
      vkDeviceDriverProperties.driverInfoString(),
      parseConformance(vkDeviceDriverProperties.conformanceVersion())
    );
  }

  private static VulkanConformanceVersion parseConformance(
    final VkConformanceVersion conformanceVersion)
  {
    return new VulkanConformanceVersion(
      (int) conformanceVersion.major(),
      (int) conformanceVersion.minor(),
      (int) conformanceVersion.subminor(),
      (int) conformanceVersion.patch()
    );
  }

  private static VulkanDriverIdType parseDriverId(
    final int driverID)
  {
    try {
      return VulkanDriverKnownId.of(driverID);
    } catch (final IllegalArgumentException e) {
      return new VulkanDriverUnknownId(driverID);
    }
  }

  private record PropertiesAndExtras(
    VulkanPhysicalDeviceProperties properties,
    Optional<VulkanPhysicalDeviceDriverProperties> driverProperties,
    Optional<VulkanPhysicalDeviceIDProperties> idProperties)
  {

  }

  VkInstance instance()
  {
    return this.instance;
  }
}
