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

import com.io7m.jcoronado.api.VulkanDestroyedException;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanFormat;
import com.io7m.jcoronado.api.VulkanFormatFeatureFlag;
import com.io7m.jcoronado.api.VulkanFormatProperties;
import com.io7m.jcoronado.api.VulkanImageCreateFlag;
import com.io7m.jcoronado.api.VulkanImageFormatProperties;
import com.io7m.jcoronado.api.VulkanImageKind;
import com.io7m.jcoronado.api.VulkanImageTiling;
import com.io7m.jcoronado.api.VulkanImageUsageFlag;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceDriverProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures10;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures11;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures12;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceIDProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceLimits;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceMemoryProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyIndex;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkFormatProperties;
import org.lwjgl.vulkan.VkImageFormatProperties;
import org.lwjgl.vulkan.VkLayerProperties;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures2;
import org.lwjgl.vulkan.VkPhysicalDeviceVulkan11Features;
import org.lwjgl.vulkan.VkPhysicalDeviceVulkan12Features;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;

import static com.io7m.jcoronado.api.VulkanChecks.checkReturnCode;
import static org.lwjgl.vulkan.VK11.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2;
import static org.lwjgl.vulkan.VK12.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_1_FEATURES;
import static org.lwjgl.vulkan.VK12.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_2_FEATURES;

/**
 * LWJGL {@link VkPhysicalDevice}
 */

public final class VulkanLWJGLPhysicalDevice
  extends VulkanLWJGLHandle implements VulkanPhysicalDeviceType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLPhysicalDevice.class);

  private static final VulkanFormatFeatureFlag[] FLAGS =
    VulkanFormatFeatureFlag.values();

  private final MemoryStack stackInitial;
  private final Optional<VulkanPhysicalDeviceDriverProperties> driverProperties;
  private final Optional<VulkanPhysicalDeviceIDProperties> idProperties;
  private final SortedMap<VulkanQueueFamilyIndex, VulkanQueueFamilyProperties> queueFamilies;
  private final VkPhysicalDevice device;
  private final VulkanLWJGLInstance instance;
  private final VulkanPhysicalDeviceFeatures features;
  private final VulkanPhysicalDeviceLimits limits;
  private final VulkanPhysicalDeviceMemoryProperties memory;
  private final VulkanPhysicalDeviceProperties properties;

  VulkanLWJGLPhysicalDevice(
    final VulkanLWJGLInstance inInstance,
    final VkPhysicalDevice inDevice,
    final VulkanPhysicalDeviceProperties inProperties,
    final VulkanPhysicalDeviceLimits inLimits,
    final VulkanPhysicalDeviceFeatures inFeatures,
    final VulkanPhysicalDeviceMemoryProperties inMemory,
    final SortedMap<VulkanQueueFamilyIndex, VulkanQueueFamilyProperties> inQueueFamilies,
    final VulkanLWJGLHostAllocatorProxy inHostAllocatorProxy,
    final Optional<VulkanPhysicalDeviceDriverProperties> inDriverProperties,
    final Optional<VulkanPhysicalDeviceIDProperties> inIdProperties)
  {
    super(Ownership.USER_OWNED, inHostAllocatorProxy);

    this.instance =
      Objects.requireNonNull(inInstance, "instance");
    this.device =
      Objects.requireNonNull(inDevice, "device");
    this.properties =
      Objects.requireNonNull(inProperties, "properties");
    this.limits =
      Objects.requireNonNull(inLimits, "limits");
    this.features =
      Objects.requireNonNull(inFeatures, "features");
    this.memory =
      Objects.requireNonNull(inMemory, "memory");
    this.queueFamilies =
      Collections.unmodifiableSortedMap(
        Objects.requireNonNull(inQueueFamilies, "queue_families"));
    this.driverProperties =
      Objects.requireNonNull(inDriverProperties, "driverProperties");
    this.idProperties =
      Objects.requireNonNull(inIdProperties, "idProperties");
    this.stackInitial =
      MemoryStack.create();
  }

  /**
   * This method was not hand-written. See: GenerateFeatures10ToVkFeatures
   */

  private static void packPhysicalDeviceFeatures10(
    final VkPhysicalDeviceFeatures vkFeatures,
    final VulkanPhysicalDeviceFeatures10 outFeatures)
  {
    vkFeatures
      .alphaToOne(
        outFeatures.alphaToOne())
      .depthBiasClamp(
        outFeatures.depthBiasClamp())
      .depthBounds(
        outFeatures.depthBounds())
      .depthClamp(
        outFeatures.depthClamp())
      .drawIndirectFirstInstance(
        outFeatures.drawIndirectFirstInstance())
      .dualSrcBlend(
        outFeatures.dualSrcBlend())
      .fillModeNonSolid(
        outFeatures.fillModeNonSolid())
      .fragmentStoresAndAtomics(
        outFeatures.fragmentStoresAndAtomics())
      .fullDrawIndexUint32(
        outFeatures.fullDrawIndexUint32())
      .geometryShader(
        outFeatures.geometryShader())
      .imageCubeArray(
        outFeatures.imageCubeArray())
      .independentBlend(
        outFeatures.independentBlend())
      .inheritedQueries(
        outFeatures.inheritedQueries())
      .largePoints(
        outFeatures.largePoints())
      .logicOp(
        outFeatures.logicOp())
      .multiDrawIndirect(
        outFeatures.multiDrawIndirect())
      .multiViewport(
        outFeatures.multiViewport())
      .occlusionQueryPrecise(
        outFeatures.occlusionQueryPrecise())
      .pipelineStatisticsQuery(
        outFeatures.pipelineStatisticsQuery())
      .robustBufferAccess(
        outFeatures.robustBufferAccess())
      .samplerAnisotropy(
        outFeatures.samplerAnisotropy())
      .sampleRateShading(
        outFeatures.sampleRateShading())
      .shaderClipDistance(
        outFeatures.shaderClipDistance())
      .shaderCullDistance(
        outFeatures.shaderCullDistance())
      .shaderFloat64(
        outFeatures.shaderFloat64())
      .shaderImageGatherExtended(
        outFeatures.shaderImageGatherExtended())
      .shaderInt16(
        outFeatures.shaderInt16())
      .shaderInt64(
        outFeatures.shaderInt64())
      .shaderResourceMinLod(
        outFeatures.shaderResourceMinLod())
      .shaderResourceResidency(
        outFeatures.shaderResourceResidency())
      .shaderSampledImageArrayDynamicIndexing(
        outFeatures.shaderSampledImageArrayDynamicIndexing())
      .shaderStorageBufferArrayDynamicIndexing(
        outFeatures.shaderStorageBufferArrayDynamicIndexing())
      .shaderStorageImageArrayDynamicIndexing(
        outFeatures.shaderStorageImageArrayDynamicIndexing())
      .shaderStorageImageExtendedFormats(
        outFeatures.shaderStorageImageExtendedFormats())
      .shaderStorageImageMultisample(
        outFeatures.shaderStorageImageMultisample())
      .shaderStorageImageReadWithoutFormat(
        outFeatures.shaderStorageImageReadWithoutFormat())
      .shaderStorageImageWriteWithoutFormat(
        outFeatures.shaderStorageImageWriteWithoutFormat())
      .shaderTessellationAndGeometryPointSize(
        outFeatures.shaderTessellationAndGeometryPointSize())
      .shaderUniformBufferArrayDynamicIndexing(
        outFeatures.shaderUniformBufferArrayDynamicIndexing())
      .sparseBinding(
        outFeatures.sparseBinding())
      .sparseResidency16Samples(
        outFeatures.sparseResidency16Samples())
      .sparseResidency2Samples(
        outFeatures.sparseResidency2Samples())
      .sparseResidency4Samples(
        outFeatures.sparseResidency4Samples())
      .sparseResidency8Samples(
        outFeatures.sparseResidency8Samples())
      .sparseResidencyAliased(
        outFeatures.sparseResidencyAliased())
      .sparseResidencyBuffer(
        outFeatures.sparseResidencyBuffer())
      .sparseResidencyImage2D(
        outFeatures.sparseResidencyImage2D())
      .sparseResidencyImage3D(
        outFeatures.sparseResidencyImage3D())
      .tessellationShader(
        outFeatures.tessellationShader())
      .textureCompressionASTC_LDR(
        outFeatures.textureCompressionASTC_LDR())
      .textureCompressionBC(
        outFeatures.textureCompressionBC())
      .textureCompressionETC2(
        outFeatures.textureCompressionETC2())
      .variableMultisampleRate(
        outFeatures.variableMultisampleRate())
      .vertexPipelineStoresAndAtomics(
        outFeatures.vertexPipelineStoresAndAtomics())
      .wideLines(
        outFeatures.wideLines());
  }

  /**
   * This method was not hand-written. See: GenerateFeatures11ToVkFeatures
   */

  private static void packPhysicalDeviceFeatures11(
    final VkPhysicalDeviceVulkan11Features vkFeatures,
    final VulkanPhysicalDeviceFeatures11 outFeatures)
  {
    vkFeatures
      .multiview(
        outFeatures.multiview())
      .multiviewGeometryShader(
        outFeatures.multiviewGeometryShader())
      .multiviewTessellationShader(
        outFeatures.multiviewTessellationShader())
      .protectedMemory(
        outFeatures.protectedMemory())
      .samplerYcbcrConversion(
        outFeatures.samplerYcbcrConversion())
      .shaderDrawParameters(
        outFeatures.shaderDrawParameters())
      .storageBuffer16BitAccess(
        outFeatures.storageBuffer16BitAccess())
      .storageInputOutput16(
        outFeatures.storageInputOutput16())
      .storagePushConstant16(
        outFeatures.storagePushConstant16())
      .uniformAndStorageBuffer16BitAccess(
        outFeatures.uniformAndStorageBuffer16BitAccess())
      .variablePointers(
        outFeatures.variablePointers())
      .variablePointersStorageBuffer(
        outFeatures.variablePointersStorageBuffer());
  }

  /**
   * This method was not hand-written. See: GenerateFeatures12ToVkFeatures
   */

  private static void packPhysicalDeviceFeatures12(
    final VkPhysicalDeviceVulkan12Features vkFeatures,
    final VulkanPhysicalDeviceFeatures12 outFeatures)
  {
    vkFeatures
      .bufferDeviceAddress(
        outFeatures.bufferDeviceAddress())
      .bufferDeviceAddressCaptureReplay(
        outFeatures.bufferDeviceAddressCaptureReplay())
      .bufferDeviceAddressMultiDevice(
        outFeatures.bufferDeviceAddressMultiDevice())
      .descriptorBindingPartiallyBound(
        outFeatures.descriptorBindingPartiallyBound())
      .descriptorBindingSampledImageUpdateAfterBind(
        outFeatures.descriptorBindingSampledImageUpdateAfterBind())
      .descriptorBindingStorageBufferUpdateAfterBind(
        outFeatures.descriptorBindingStorageBufferUpdateAfterBind())
      .descriptorBindingStorageImageUpdateAfterBind(
        outFeatures.descriptorBindingStorageImageUpdateAfterBind())
      .descriptorBindingStorageTexelBufferUpdateAfterBind(
        outFeatures.descriptorBindingStorageTexelBufferUpdateAfterBind())
      .descriptorBindingUniformBufferUpdateAfterBind(
        outFeatures.descriptorBindingUniformBufferUpdateAfterBind())
      .descriptorBindingUniformTexelBufferUpdateAfterBind(
        outFeatures.descriptorBindingUniformTexelBufferUpdateAfterBind())
      .descriptorBindingUpdateUnusedWhilePending(
        outFeatures.descriptorBindingUpdateUnusedWhilePending())
      .descriptorBindingVariableDescriptorCount(
        outFeatures.descriptorBindingVariableDescriptorCount())
      .descriptorIndexing(
        outFeatures.descriptorIndexing())
      .drawIndirectCount(
        outFeatures.drawIndirectCount())
      .hostQueryReset(
        outFeatures.hostQueryReset())
      .imagelessFramebuffer(
        outFeatures.imagelessFramebuffer())
      .runtimeDescriptorArray(
        outFeatures.runtimeDescriptorArray())
      .samplerFilterMinmax(
        outFeatures.samplerFilterMinmax())
      .samplerMirrorClampToEdge(
        outFeatures.samplerMirrorClampToEdge())
      .scalarBlockLayout(
        outFeatures.scalarBlockLayout())
      .separateDepthStencilLayouts(
        outFeatures.separateDepthStencilLayouts())
      .shaderBufferInt64Atomics(
        outFeatures.shaderBufferInt64Atomics())
      .shaderFloat16(
        outFeatures.shaderFloat16())
      .shaderInputAttachmentArrayDynamicIndexing(
        outFeatures.shaderInputAttachmentArrayDynamicIndexing())
      .shaderInputAttachmentArrayNonUniformIndexing(
        outFeatures.shaderInputAttachmentArrayNonUniformIndexing())
      .shaderInt8(
        outFeatures.shaderInt8())
      .shaderOutputLayer(
        outFeatures.shaderOutputLayer())
      .shaderOutputViewportIndex(
        outFeatures.shaderOutputViewportIndex())
      .shaderSampledImageArrayNonUniformIndexing(
        outFeatures.shaderSampledImageArrayNonUniformIndexing())
      .shaderSharedInt64Atomics(
        outFeatures.shaderSharedInt64Atomics())
      .shaderStorageBufferArrayNonUniformIndexing(
        outFeatures.shaderStorageBufferArrayNonUniformIndexing())
      .shaderStorageImageArrayNonUniformIndexing(
        outFeatures.shaderStorageImageArrayNonUniformIndexing())
      .shaderStorageTexelBufferArrayDynamicIndexing(
        outFeatures.shaderStorageTexelBufferArrayDynamicIndexing())
      .shaderStorageTexelBufferArrayNonUniformIndexing(
        outFeatures.shaderStorageTexelBufferArrayNonUniformIndexing())
      .shaderSubgroupExtendedTypes(
        outFeatures.shaderSubgroupExtendedTypes())
      .shaderUniformBufferArrayNonUniformIndexing(
        outFeatures.shaderUniformBufferArrayNonUniformIndexing())
      .shaderUniformTexelBufferArrayDynamicIndexing(
        outFeatures.shaderUniformTexelBufferArrayDynamicIndexing())
      .shaderUniformTexelBufferArrayNonUniformIndexing(
        outFeatures.shaderUniformTexelBufferArrayNonUniformIndexing())
      .storageBuffer8BitAccess(
        outFeatures.storageBuffer8BitAccess())
      .storagePushConstant8(
        outFeatures.storagePushConstant8())
      .subgroupBroadcastDynamicId(
        outFeatures.subgroupBroadcastDynamicId())
      .timelineSemaphore(
        outFeatures.timelineSemaphore())
      .uniformAndStorageBuffer8BitAccess(
        outFeatures.uniformAndStorageBuffer8BitAccess())
      .uniformBufferStandardLayout(
        outFeatures.uniformBufferStandardLayout())
      .vulkanMemoryModel(
        outFeatures.vulkanMemoryModel())
      .vulkanMemoryModelAvailabilityVisibilityChains(
        outFeatures.vulkanMemoryModelAvailabilityVisibilityChains())
      .vulkanMemoryModelDeviceScope(
        outFeatures.vulkanMemoryModelDeviceScope());
  }

  private static VkDeviceQueueCreateInfo.Buffer createQueueBuffer(
    final MemoryStack stack,
    final List<VulkanLogicalDeviceQueueCreateInfo> infos)
  {
    final var queueCount = infos.size();
    final var vkQueueBuffer =
      VkDeviceQueueCreateInfo.malloc(queueCount, stack);

    for (var index = 0; index < queueCount; ++index) {
      final var queueInfo =
        infos.get(index);
      final var priorities =
        queueInfo.queuePriorities();
      final var priorityArray =
        stack.callocFloat(priorities.size());

      for (final var x : priorities) {
        priorityArray.put(x.floatValue());
      }

      priorityArray.flip();

      vkQueueBuffer.position(index);
      vkQueueBuffer.sType(VK10.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
        .pNext(0L)
        .flags(0)
        .queueFamilyIndex(queueInfo.queueFamilyIndex().value())
        .pQueuePriorities(priorityArray);
    }
    vkQueueBuffer.position(0);
    return vkQueueBuffer;
  }

  private static Set<VulkanFormatFeatureFlag> mapOptimalFeatures(
    final VkFormatProperties vk_properties)
  {
    return unpackFlagsFromMask(vk_properties.optimalTilingFeatures());
  }

  private static Set<VulkanFormatFeatureFlag> unpackFlagsFromMask(
    final int mask)
  {
    final var results = new HashSet<VulkanFormatFeatureFlag>();
    for (final var flag : FLAGS) {
      final var value = flag.value();
      if ((mask & value) == value) {
        results.add(flag);
      }
    }
    return results;
  }

  private static Set<VulkanFormatFeatureFlag> mapLinearTilingFeatures(
    final VkFormatProperties vk_properties)
  {
    return unpackFlagsFromMask(vk_properties.linearTilingFeatures());
  }

  private static Set<VulkanFormatFeatureFlag> mapBufferFeatures(
    final VkFormatProperties vk_properties)
  {
    return unpackFlagsFromMask(vk_properties.bufferFeatures());
  }

  private void createPhysicalDeviceFeatures(
    final MemoryStack stack,
    final VkDeviceCreateInfo vkDeviceCreateInfo,
    final VulkanLogicalDeviceCreateInfo info)
  {
    final var featuresOpt = info.features();
    if (featuresOpt.isEmpty()) {
      return;
    }

    final var requestFeatures = featuresOpt.get();

    /*
     * On Vulkan 1.0, the required features are set directly in the
     * creation info structure.
     */

    final var version = this.instance.apiVersionUsed();
    if (version.major() == 1 && version.minor() == 0) {
      LOG.debug("Enabling features using direct VkPhysicalDeviceFeatures");

      final var vkFeatures =
        VkPhysicalDeviceFeatures.malloc(stack);

      packPhysicalDeviceFeatures10(vkFeatures, requestFeatures.features10());
      vkDeviceCreateInfo.pEnabledFeatures(vkFeatures);
      return;
    }

    /*
     * On Vulkan 1.1+, the required features are specified in a set of
     * structures chained in the "next" pointer.
     */

    LOG.debug("Enabling features using chained VkPhysicalDeviceFeatures2");

    final var vkFeatures11 =
      VkPhysicalDeviceVulkan11Features.malloc(stack);
    final var vkFeatures12 =
      VkPhysicalDeviceVulkan12Features.malloc(stack);
    final var vkFeatures =
      VkPhysicalDeviceFeatures2.malloc(stack);

    vkFeatures.sType(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2);
    vkFeatures11.sType(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_1_FEATURES);
    vkFeatures12.sType(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_2_FEATURES);

    packPhysicalDeviceFeatures12(vkFeatures12, requestFeatures.features12());
    packPhysicalDeviceFeatures11(vkFeatures11, requestFeatures.features11());
    packPhysicalDeviceFeatures10(
      vkFeatures.features(),
      requestFeatures.features10()
    );

    vkFeatures12.pNext(0L);
    vkFeatures11.pNext(vkFeatures12.address());
    vkFeatures.pNext(vkFeatures11.address());
    vkDeviceCreateInfo.pNext(vkFeatures.address());
    vkDeviceCreateInfo.pEnabledFeatures(null);
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
    final var that = (VulkanLWJGLPhysicalDevice) o;
    return Objects.equals(this.device, that.device);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(this.device);
  }

  /**
   * @return The underlying device
   */

  public VkPhysicalDevice device()
  {
    return this.device;
  }

  @Override
  public String toString()
  {
    return new StringBuilder(128)
      .append("[VulkanLWJGLPhysicalDevice 0x")
      .append(Integer.toUnsignedString(this.properties.vendorId(), 16))
      .append(":0x")
      .append(Integer.toUnsignedString(this.properties.id(), 16))
      .append(" \"")
      .append(this.properties.name())
      .append("\" ")
      .append(this.properties.apiVersion().toHumanString())
      .append(" (driver ")
      .append(this.properties.driverVersion().toHumanString())
      .append(')')
      .append(']').toString();
  }

  @Override
  public VulkanInstanceType instance()
  {
    return this.instance;
  }

  @Override
  public Map<String, VulkanExtensionProperties> extensions(
    final Optional<String> layer)
    throws VulkanException
  {
    Objects.requireNonNull(layer, "layer");

    this.checkNotClosed();

    try (var stack = this.stackInitial.push()) {
      final var count = new int[1];

      final var layer_ptr =
        layer.map(stack::ASCII).orElse(null);

      checkReturnCode(
        VK10.vkEnumerateDeviceExtensionProperties(
          this.device,
          layer_ptr,
          count,
          null),
        "vkEnumerateDeviceExtensionProperties");

      final var size = count[0];
      if (size == 0) {
        return Map.of();
      }

      final var device_extensions =
        VkExtensionProperties.malloc(size, stack);

      checkReturnCode(
        VK10.vkEnumerateDeviceExtensionProperties(
          this.device,
          layer_ptr,
          count,
          device_extensions),
        "vkEnumerateDeviceExtensionProperties");

      final HashMap<String, VulkanExtensionProperties> extensions =
        new HashMap<>(size);

      for (var index = 0; index < size; ++index) {
        device_extensions.position(index);
        final var extension =
          VulkanExtensionProperties.of(
            device_extensions.extensionNameString(),
            device_extensions.specVersion());
        extensions.put(extension.name(), extension);
      }

      return extensions;
    }
  }

  @Override
  public Map<String, VulkanLayerProperties> layers()
    throws VulkanException
  {
    try (var stack = this.stackInitial.push()) {
      final var count = new int[1];

      checkReturnCode(
        VK10.vkEnumerateDeviceLayerProperties(
          this.device,
          count,
          null),
        "vkEnumerateDeviceLayerProperties");

      final var size = count[0];
      if (size == 0) {
        return Map.of();
      }

      final var layers_buffer =
        VkLayerProperties.malloc(size, stack);

      checkReturnCode(
        VK10.vkEnumerateDeviceLayerProperties(
          this.device,
          count,
          layers_buffer),
        "vkEnumerateDeviceLayerProperties");

      final HashMap<String, VulkanLayerProperties> layers = new HashMap<>(size);
      for (var index = 0; index < size; ++index) {
        layers_buffer.position(index);

        final var layer =
          VulkanLayerProperties.of(
            layers_buffer.layerNameString(),
            layers_buffer.descriptionString(),
            layers_buffer.specVersion(),
            layers_buffer.implementationVersion());

        layers.put(layer.name(), layer);
      }
      return layers;
    }
  }

  @Override
  public VulkanPhysicalDeviceProperties properties()
    throws VulkanDestroyedException
  {
    this.checkNotClosed();
    return this.properties;
  }

  @Override
  public VulkanPhysicalDeviceLimits limits()
    throws VulkanDestroyedException
  {
    this.checkNotClosed();
    return this.limits;
  }

  @Override
  public VulkanPhysicalDeviceFeatures features()
    throws VulkanDestroyedException
  {
    this.checkNotClosed();
    return this.features;
  }

  @Override
  public VulkanFormatProperties formatProperties(
    final VulkanFormat format)
    throws VulkanException
  {
    Objects.requireNonNull(format, "format");

    this.checkNotClosed();

    try (var stack = this.stackInitial.push()) {
      final var vk_properties = VkFormatProperties.malloc(stack);
      VK10.vkGetPhysicalDeviceFormatProperties(
        this.device,
        format.value(),
        vk_properties);

      return VulkanFormatProperties.builder()
        .setBufferFeatures(mapBufferFeatures(vk_properties))
        .setLinearTilingFeatures(mapLinearTilingFeatures(vk_properties))
        .setOptimalTilingFeatures(mapOptimalFeatures(vk_properties))
        .build();
    }
  }

  @Override
  public VulkanImageFormatProperties imageFormatProperties(
    final VulkanFormat format,
    final VulkanImageKind type,
    final VulkanImageTiling tiling,
    final Set<VulkanImageUsageFlag> usage,
    final Set<VulkanImageCreateFlag> flags)
    throws VulkanException
  {
    Objects.requireNonNull(format, "format");
    Objects.requireNonNull(type, "type");
    Objects.requireNonNull(tiling, "tiling");
    Objects.requireNonNull(usage, "usage");
    Objects.requireNonNull(flags, "flags");

    try (var stack = this.stackInitial.push()) {
      final var vk_properties = VkImageFormatProperties.malloc(stack);

      checkReturnCode(
        VK10.vkGetPhysicalDeviceImageFormatProperties(
          this.device,
          format.value(),
          type.value(),
          tiling.value(),
          VulkanEnumMaps.packValues(usage),
          VulkanEnumMaps.packValues(flags),
          vk_properties),
        "vkGetPhysicalDeviceImageFormatProperties");

      final var extent = vk_properties.maxExtent();
      return VulkanImageFormatProperties.builder()
        .setMaxArrayLayers(vk_properties.maxArrayLayers())
        .setMaxMipLevels(vk_properties.maxMipLevels())
        .setMaxResourceSize(vk_properties.maxResourceSize())
        .setMaxExtent(
          VulkanExtent3D.builder()
            .setWidth(extent.width())
            .setHeight(extent.height())
            .setDepth(extent.depth())
            .build())
        .build();
    }
  }

  @Override
  public VulkanPhysicalDeviceMemoryProperties memory()
    throws VulkanException
  {
    this.checkNotClosed();
    return this.memory;
  }

  @Override
  public SortedMap<VulkanQueueFamilyIndex, VulkanQueueFamilyProperties> queueFamilies()
    throws VulkanException
  {
    this.checkNotClosed();
    return this.queueFamilies;
  }

  @Override
  public VulkanLogicalDeviceType createLogicalDevice(
    final VulkanLogicalDeviceCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    final var enabledExtensions =
      info.enabledExtensions();
    final var enabledLayers =
      info.enabledLayers();

    if (LOG.isDebugEnabled()) {
      LOG.debug("Creating logical device");
      enabledExtensions.forEach(name -> LOG.debug(
        "Enabling extension: {}",
        name));
      enabledLayers.forEach(name -> LOG.debug("Enabling layer: {}", name));
    }

    try (var stack = this.stackInitial.push()) {
      final var infos =
        info.queueCreateInfos();
      final var vkQueueBuffer =
        createQueueBuffer(stack, infos);
      final var vkEnabledLayers =
        VulkanStrings.stringsToPointerBuffer(stack, enabledLayers);
      final var vkEnabledExtensions =
        VulkanStrings.stringsToPointerBuffer(stack, enabledExtensions);

      final var vkDeviceCreateInfo =
        VkDeviceCreateInfo.malloc(stack)
          .sType(VK10.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
          .pNext(0L)
          .flags(VulkanEnumMaps.packValues(info.flags()))
          .pQueueCreateInfos(vkQueueBuffer)
          .ppEnabledLayerNames(vkEnabledLayers)
          .ppEnabledExtensionNames(vkEnabledExtensions);

      this.createPhysicalDeviceFeatures(stack, vkDeviceCreateInfo, info);

      final var vkLogicalDevicePtr =
        stack.mallocPointer(1);

      checkReturnCode(
        VK10.vkCreateDevice(
          this.device,
          vkDeviceCreateInfo,
          this.hostAllocatorProxy().callbackBuffer(),
          vkLogicalDevicePtr),
        "vkCreateDevice");

      final var vkLogicalDevice =
        new VkDevice(
          vkLogicalDevicePtr.get(0),
          this.device,
          vkDeviceCreateInfo);

      final var registry =
        this.instance.extensionRegistry();
      final var enabled =
        registry.ofNames(enabledExtensions);

      return new VulkanLWJGLLogicalDevice(
        enabled,
        this,
        vkLogicalDevice,
        info,
        this.hostAllocatorProxy());
    }
  }

  @Override
  public Optional<VulkanPhysicalDeviceDriverProperties> driverProperties()
  {
    return this.driverProperties;
  }

  @Override
  public Optional<VulkanPhysicalDeviceIDProperties> idProperties()
  {
    return this.idProperties;
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
      LOG.trace("Destroying physical device: {}", this);
    }
  }
}
