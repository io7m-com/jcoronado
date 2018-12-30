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

import com.io7m.jcoronado.api.VulkanDestroyedException;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanFormat;
import com.io7m.jcoronado.api.VulkanFormatFeatureFlag;
import com.io7m.jcoronado.api.VulkanFormatProperties;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceLimits;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceMemoryProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkFormatProperties;
import org.lwjgl.vulkan.VkLayerProperties;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;
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

import static com.io7m.jcoronado.api.VulkanChecks.checkReturnCode;

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

  private final VulkanLWJGLInstance instance;
  private final VkPhysicalDevice device;
  private final VulkanPhysicalDeviceProperties properties;
  private final VulkanPhysicalDeviceLimits limits;
  private final VulkanPhysicalDeviceFeatures features;
  private final VulkanPhysicalDeviceMemoryProperties memory;
  private final List<VulkanQueueFamilyProperties> queue_families;
  private final MemoryStack stack_initial;

  VulkanLWJGLPhysicalDevice(
    final VulkanLWJGLInstance in_instance,
    final VkPhysicalDevice in_device,
    final VulkanPhysicalDeviceProperties in_properties,
    final VulkanPhysicalDeviceLimits in_limits,
    final VulkanPhysicalDeviceFeatures in_features,
    final VulkanPhysicalDeviceMemoryProperties in_memory,
    final List<VulkanQueueFamilyProperties> in_queue_families,
    final VulkanLWJGLHostAllocatorProxy in_host_allocator_proxy)
  {
    super(Ownership.USER_OWNED, in_host_allocator_proxy);

    this.instance =
      Objects.requireNonNull(in_instance, "instance");
    this.device =
      Objects.requireNonNull(in_device, "device");
    this.properties =
      Objects.requireNonNull(in_properties, "properties");
    this.limits =
      Objects.requireNonNull(in_limits, "limits");
    this.features =
      Objects.requireNonNull(in_features, "features");
    this.memory =
      Objects.requireNonNull(in_memory, "memory");
    this.queue_families =
      Collections.unmodifiableList(
        Objects.requireNonNull(in_queue_families, "queue_families"));

    this.stack_initial = MemoryStack.create();
  }

  private static VkPhysicalDeviceFeatures createPhysicalDeviceFeatures(
    final MemoryStack stack,
    final VulkanLogicalDeviceCreateInfo info)
  {
    final VkPhysicalDeviceFeatures vk_features;
    final var features_opt = info.features();
    if (features_opt.isPresent()) {
      vk_features = VkPhysicalDeviceFeatures.callocStack(stack);
      packPhysicalDeviceFeatures(vk_features, features_opt.get());
    } else {
      vk_features = null;
    }
    return vk_features;
  }

  /**
   * This method was not hand-written. See: features-set2.sh
   */

  private static void packPhysicalDeviceFeatures(
    final VkPhysicalDeviceFeatures vk_features,
    final VulkanPhysicalDeviceFeatures features)
  {
    vk_features
      .alphaToOne(features.alphaToOne())
      .depthBiasClamp(features.depthBiasClamp())
      .depthBounds(features.depthBounds())
      .depthClamp(features.depthClamp())
      .drawIndirectFirstInstance(features.drawIndirectFirstInstance())
      .dualSrcBlend(features.dualSrcBlend())
      .fillModeNonSolid(features.fillModeNonSolid())
      .fragmentStoresAndAtomics(features.fragmentStoresAndAtomics())
      .fullDrawIndexUint32(features.fullDrawIndexUint32())
      .geometryShader(features.geometryShader())
      .imageCubeArray(features.imageCubeArray())
      .independentBlend(features.independentBlend())
      .inheritedQueries(features.inheritedQueries())
      .largePoints(features.largePoints())
      .logicOp(features.logicOp())
      .multiDrawIndirect(features.multiDrawIndirect())
      .multiViewport(features.multiViewport())
      .occlusionQueryPrecise(features.occlusionQueryPrecise())
      .pipelineStatisticsQuery(features.pipelineStatisticsQuery())
      .robustBufferAccess(features.robustBufferAccess())
      .samplerAnisotropy(features.samplerAnisotropy())
      .sampleRateShading(features.sampleRateShading())
      .shaderClipDistance(features.shaderClipDistance())
      .shaderCullDistance(features.shaderCullDistance())
      .shaderFloat64(features.shaderFloat64())
      .shaderImageGatherExtended(features.shaderImageGatherExtended())
      .shaderInt16(features.shaderInt16())
      .shaderInt64(features.shaderInt64())
      .shaderResourceMinLod(features.shaderResourceMinLod())
      .shaderResourceResidency(features.shaderResourceResidency())
      .shaderSampledImageArrayDynamicIndexing(features.shaderSampledImageArrayDynamicIndexing())
      .shaderStorageBufferArrayDynamicIndexing(features.shaderStorageBufferArrayDynamicIndexing())
      .shaderStorageImageArrayDynamicIndexing(features.shaderStorageImageArrayDynamicIndexing())
      .shaderStorageImageExtendedFormats(features.shaderStorageImageExtendedFormats())
      .shaderStorageImageMultisample(features.shaderStorageImageMultisample())
      .shaderStorageImageReadWithoutFormat(features.shaderStorageImageReadWithoutFormat())
      .shaderStorageImageWriteWithoutFormat(features.shaderStorageImageWriteWithoutFormat())
      .shaderTessellationAndGeometryPointSize(features.shaderTessellationAndGeometryPointSize())
      .shaderUniformBufferArrayDynamicIndexing(features.shaderUniformBufferArrayDynamicIndexing())
      .sparseBinding(features.sparseBinding())
      .sparseResidency16Samples(features.sparseResidency16Samples())
      .sparseResidency2Samples(features.sparseResidency2Samples())
      .sparseResidency4Samples(features.sparseResidency4Samples())
      .sparseResidency8Samples(features.sparseResidency8Samples())
      .sparseResidencyAliased(features.sparseResidencyAliased())
      .sparseResidencyBuffer(features.sparseResidencyBuffer())
      .sparseResidencyImage2D(features.sparseResidencyImage2D())
      .sparseResidencyImage3D(features.sparseResidencyImage3D())
      .tessellationShader(features.tessellationShader())
      .textureCompressionASTC_LDR(features.textureCompressionASTC_LDR())
      .textureCompressionBC(features.textureCompressionBC())
      .textureCompressionETC2(features.textureCompressionETC2())
      .variableMultisampleRate(features.variableMultisampleRate())
      .vertexPipelineStoresAndAtomics(features.vertexPipelineStoresAndAtomics())
      .wideLines(features.wideLines());
  }

  private static VkDeviceQueueCreateInfo.Buffer createQueueBuffer(
    final MemoryStack stack,
    final List<VulkanLogicalDeviceQueueCreateInfo> infos)
  {
    final var queue_count = infos.size();
    final var vk_queue_buffer =
      VkDeviceQueueCreateInfo.callocStack(queue_count, stack);

    for (var index = 0; index < queue_count; ++index) {
      final var queue_info = infos.get(index);
      vk_queue_buffer.position(index);
      vk_queue_buffer.sType(VK10.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
        .pNext(0L)
        .flags(0)
        .queueFamilyIndex(queue_info.queueFamilyIndex())
        .pQueuePriorities(stack.floats(queue_info.queuePriorities()));
    }
    vk_queue_buffer.position(0);
    return vk_queue_buffer;
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

  VkPhysicalDevice device()
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

    try (var stack = this.stack_initial.push()) {
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
        VkExtensionProperties.mallocStack(size, stack);

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
    try (var stack = this.stack_initial.push()) {
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
        VkLayerProperties.mallocStack(size, stack);

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

    try (var stack = this.stack_initial.push()) {
      final var vk_properties = VkFormatProperties.mallocStack(stack);
      VK10.vkGetPhysicalDeviceFormatProperties(this.device, format.value(), vk_properties);

      return VulkanFormatProperties.builder()
        .setBufferFeatures(mapBufferFeatures(vk_properties))
        .setLinearTilingFeatures(mapLinearTilingFeatures(vk_properties))
        .setOptimalTilingFeatures(mapOptimalFeatures(vk_properties))
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
  public List<VulkanQueueFamilyProperties> queueFamilies()
    throws VulkanException
  {
    this.checkNotClosed();
    return this.queue_families;
  }

  @Override
  public VulkanLogicalDeviceType createLogicalDevice(
    final VulkanLogicalDeviceCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    final var enabled_extensions = info.enabledExtensions();
    final var enabled_layers = info.enabledLayers();

    if (LOG.isDebugEnabled()) {
      LOG.debug("creating logical device");
      enabled_extensions.forEach(name -> LOG.debug("enabling extension: {}", name));
      enabled_layers.forEach(name -> LOG.debug("enabling layer: {}", name));
    }

    try (var stack = this.stack_initial.push()) {
      final var infos =
        info.queueCreateInfos();
      final var vk_queue_buffer =
        createQueueBuffer(stack, infos);
      final var vk_enabled_layers =
        VulkanStrings.stringsToPointerBuffer(stack, enabled_layers);
      final var vk_enabled_extensions =
        VulkanStrings.stringsToPointerBuffer(stack, enabled_extensions);
      final var vk_features =
        createPhysicalDeviceFeatures(stack, info);

      final var vk_device_create_info =
        VkDeviceCreateInfo.mallocStack(stack)
          .sType(VK10.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
          .pNext(0L)
          .flags(VulkanEnumMaps.packValues(info.flags()))
          .pQueueCreateInfos(vk_queue_buffer)
          .ppEnabledLayerNames(vk_enabled_layers)
          .ppEnabledExtensionNames(vk_enabled_extensions)
          .pEnabledFeatures(vk_features);

      final var vk_logical_device_ptr = stack.mallocPointer(1);
      checkReturnCode(
        VK10.vkCreateDevice(
          this.device,
          vk_device_create_info,
          this.hostAllocatorProxy().callbackBuffer(),
          vk_logical_device_ptr),
        "vkCreateDevice");

      final var vk_logical_device =
        new VkDevice(
          vk_logical_device_ptr.get(0),
          this.device,
          vk_device_create_info);

      final var registry =
        this.instance.extensionRegistry();
      final var enabled =
        registry.ofNames(enabled_extensions);

      return new VulkanLWJGLLogicalDevice(
        enabled,
        this,
        vk_logical_device,
        info,
        this.hostAllocatorProxy());
    }
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
      LOG.trace("destroying physical device: {}", this);
    }
  }
}
