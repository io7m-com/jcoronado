/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
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


package com.io7m.jcoronado.fake;

import com.io7m.jcoronado.api.VulkanComputeWorkGroupCount;
import com.io7m.jcoronado.api.VulkanComputeWorkGroupSize;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanFormat;
import com.io7m.jcoronado.api.VulkanFormatProperties;
import com.io7m.jcoronado.api.VulkanImageCreateFlag;
import com.io7m.jcoronado.api.VulkanImageFormatProperties;
import com.io7m.jcoronado.api.VulkanImageKind;
import com.io7m.jcoronado.api.VulkanImageTiling;
import com.io7m.jcoronado.api.VulkanImageUsageFlag;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanLineWidthRange;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceDriverProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeaturesFunctions;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceIDProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceLimits;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceMemoryProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDevicePropertiesType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanPointSizeRange;
import com.io7m.jcoronado.api.VulkanQueueFamilyIndex;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag;
import com.io7m.jcoronado.api.VulkanVersion;
import com.io7m.jcoronado.api.VulkanViewportBoundsRange;
import com.io7m.jcoronado.api.VulkanViewportDimensions;
import com.io7m.junreachable.UnimplementedCodeException;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The default fake physical device.
 */

public final class VFakePhysicalDevice
  implements VulkanPhysicalDeviceType
{
  private final AtomicBoolean closed;
  private VFakeInstance instance;
  private VulkanPhysicalDeviceProperties properties;
  private VulkanPhysicalDeviceLimits limits;
  private VulkanPhysicalDeviceFeatures features;
  private VulkanPhysicalDeviceMemoryProperties memory;
  private TreeMap<VulkanQueueFamilyIndex, VulkanQueueFamilyProperties> queueFamilies;
  private Map<String, VulkanExtensionProperties> extensions;
  private VFakeLogicalDevice nextDevice;

  /**
   * Construct a device.
   *
   * @param newInstance The owning instance
   */

  public VFakePhysicalDevice(
    final VFakeInstance newInstance)
  {
    this.instance =
      Objects.requireNonNull(newInstance, "instance");
    this.closed =
      new AtomicBoolean(false);
    this.queueFamilies =
      new TreeMap<>();

    this.queueFamilies.put(
      new VulkanQueueFamilyIndex(0),
      VulkanQueueFamilyProperties.of(
        new VulkanQueueFamilyIndex(0),
        1,
        Set.of(VulkanQueueFamilyPropertyFlag.values()),
        32,
        VulkanExtent3D.of(1, 1, 1)
      )
    );

    this.properties =
      VulkanPhysicalDeviceProperties.builder()
        .setType(VulkanPhysicalDevicePropertiesType.Type.VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU)
        .setApiVersion(VulkanVersion.of(1, 0, 0))
        .setDriverVersion(VulkanVersion.of(1, 0, 0))
        .setId(this.hashCode())
        .setVendorId(0x494F374D)
        .setName("Fake")
        .build();

    this.limits =
      VulkanPhysicalDeviceLimits.builder()
        .setBufferImageGranularity(131072)
        .setDiscreteQueuePriorities(2)
        .setFramebufferColorSampleCounts(1)
        .setFramebufferDepthSampleCounts(1)
        .setFramebufferNoAttachmentsSampleCounts(1)
        .setFramebufferStencilSampleCounts(1)
        .setLineWidthGranularity(1.0f)
        .setLineWidthRange(VulkanLineWidthRange.of(1.0f, 8.0f))
        .setMaxBoundDescriptorSets(4)
        .setMaxClipDistances(8)
        .setMaxColorAttachments(4)
        .setMaxCombinedClipAndCullDistances(8)
        .setMaxComputeSharedMemorySize(16384)
        .setMaxComputeWorkGroupCount(
          VulkanComputeWorkGroupCount.of(65535, 65535, 65535))
        .setMaxComputeWorkGroupInvocations(128)
        .setMaxComputeWorkGroupSize(VulkanComputeWorkGroupSize.of(128, 128, 64))
        .setMaxCullDistances(8)
        .setMaxDescriptorSetInputAttachments(4)
        .setMaxDescriptorSetSampledImages(96)
        .setMaxDescriptorSetSamplers(96)
        .setMaxDescriptorSetStorageBuffers(96)
        .setMaxDescriptorSetStorageBuffersDynamic(4)
        .setMaxDescriptorSetStorageImages(96)
        .setMaxDescriptorSetUniformBuffers(64)
        .setMaxDescriptorSetUniformBuffersDynamic(8)
        .setMaxDrawIndexedIndexValue(0x7fffffff)
        .setMaxDrawIndirectCount(65535)
        .setMaxFragmentCombinedOutputResources(4)
        .setMaxFragmentDualSrcAttachments(1)
        .setMaxFragmentInputComponents(64)
        .setMaxFragmentOutputAttachments(4)
        .setMaxFramebufferHeight(4096)
        .setMaxFramebufferWidth(4096)
        .setMaxFramebufferLayers(1)
        .setMaxGeometryInputComponents(64)
        .setMaxGeometryOutputComponents(64)
        .setMaxGeometryOutputVertices(256)
        .setMaxGeometryShaderInvocations(32)
        .setMaxGeometryTotalOutputComponents(1024)
        .setMaxImageArrayLayers(256)
        .setMaxImageDimension1D(4096)
        .setMaxImageDimension2D(4096)
        .setMaxImageDimension3D(256)
        .setMaxImageDimensionCube(4096)
        .setMaxInterpolationOffset(0.5f)
        .setMaxMemoryAllocationCount(4096)
        .setMaxPerStageDescriptorInputAttachments(4)
        .setMaxPerStageDescriptorSampledImages(16)
        .setMaxPerStageDescriptorSamplers(16)
        .setMaxPerStageDescriptorStorageBuffers(4)
        .setMaxPerStageDescriptorStorageImages(4)
        .setMaxPerStageDescriptorUniformBuffers(12)
        .setMaxPerStageResources(16384)
        .setMaxPushConstantsSize(128)
        .setMaxSampleMaskWords(1)
        .setMaxSamplerAllocationCount(4000)
        .setMaxSamplerAnisotropy(16)
        .setMaxSamplerLodBias(2)
        .setMaxStorageBufferRange(134217728)
        .setMaxTessellationControlPerPatchOutputComponents(120)
        .setMaxTessellationControlPerVertexInputComponents(64)
        .setMaxTessellationControlPerVertexOutputComponents(64)
        .setMaxTessellationControlTotalOutputComponents(2048)
        .setMaxTessellationEvaluationInputComponents(64)
        .setMaxTessellationEvaluationOutputComponents(64)
        .setMaxTessellationGenerationLevel(64)
        .setMaxTessellationPatchSize(32)
        .setMaxTexelBufferElements(65536)
        .setMaxTexelGatherOffset(7)
        .setMaxTexelOffset(7)
        .setMaxUniformBufferRange(16384)
        .setMaxVertexInputAttributeOffset(2047)
        .setMaxVertexInputAttributes(16)
        .setMaxVertexInputBindingStride(2048)
        .setMaxVertexInputBindings(16)
        .setMaxVertexOutputComponents(64)
        .setMaxViewportDimensions(VulkanViewportDimensions.of(4096, 4096))
        .setMaxViewports(16)
        .setMinInterpolationOffset(-0)
        .setMinMemoryMapAlignment(64)
        .setMinStorageBufferOffsetAlignment(256)
        .setMinTexelBufferOffsetAlignment(256)
        .setMinTexelGatherOffset(-8)
        .setMinTexelOffset(-8)
        .setMinUniformBufferOffsetAlignment(256)
        .setMipmapPrecisionBits(4)
        .setNonCoherentAtomSize(256)
        .setOptimalBufferCopyOffsetAlignment(1024)
        .setOptimalBufferCopyRowPitchAlignment(1024)
        .setPointSizeGranularity(1.0f)
        .setPointSizeRange(VulkanPointSizeRange.of(1.0f, 1.0f))
        .setSampledImageColorSampleCounts(1)
        .setSampledImageDepthSampleCounts(1)
        .setSampledImageIntegerSampleCounts(0)
        .setSampledImageStencilSampleCounts(1)
        .setSparseAddressSpaceSize(0x7fffffff)
        .setStandardSampleLocations(true)
        .setStorageImageSampleCounts(1)
        .setStrictLines(true)
        .setSubPixelInterpolationOffsetBits(1024)
        .setSubPixelPrecisionBits(4)
        .setSubTexelPrecisionBits(4)
        .setTimestampComputeAndGraphics(true)
        .setTimestampPeriod(1.0f)
        .setViewportBoundsRange(VulkanViewportBoundsRange.of(-8192, 8191))
        .setViewportSubPixelBits(0)
        .build();

    this.features =
      VulkanPhysicalDeviceFeaturesFunctions.none();

    this.nextDevice =
      new VFakeLogicalDevice(this);
  }

  /**
   * @return The extensions
   */

  public Map<String, VulkanExtensionProperties> getExtensions()
  {
    return this.extensions;
  }

  /**
   * Set the extensions.
   *
   * @param inExtensions The extensions
   */

  public void setExtensions(
    final Map<String, VulkanExtensionProperties> inExtensions)
  {
    this.extensions = Objects.requireNonNull(inExtensions, "extensions");
  }

  /**
   * @return A mutable view of the queue families
   */

  public TreeMap<VulkanQueueFamilyIndex, VulkanQueueFamilyProperties> getQueueFamilies()
  {
    return this.queueFamilies;
  }

  /**
   * Set the features.
   *
   * @param newFeatures The features
   */

  public void setFeatures(
    final VulkanPhysicalDeviceFeatures newFeatures)
  {
    this.features =
      Objects.requireNonNull(newFeatures, "newFeatures");
  }

  /**
   * Set the limits.
   *
   * @param newLimits The limits
   */

  public void setLimits(
    final VulkanPhysicalDeviceLimits newLimits)
  {
    this.limits =
      Objects.requireNonNull(newLimits, "newLimits");
  }

  /**
   * Set the memory properties.
   *
   * @param newMemory The memory properties
   */

  public void setMemory(
    final VulkanPhysicalDeviceMemoryProperties newMemory)
  {
    this.memory =
      Objects.requireNonNull(newMemory, "newMemory");
  }

  /**
   * Set the properties.
   *
   * @param newProperties The properties
   */

  public void setProperties(
    final VulkanPhysicalDeviceProperties newProperties)
  {
    this.properties =
      Objects.requireNonNull(newProperties, "newProperties");
  }

  @Override
  public VulkanInstanceType instance()
  {
    return this.instance;
  }

  @Override
  public Map<String, VulkanExtensionProperties> extensions(
    final Optional<String> layer)
  {
    return this.extensions;
  }

  @Override
  public Map<String, VulkanLayerProperties> layers()
  {
    return Map.of();
  }

  @Override
  public VulkanPhysicalDeviceProperties properties()
  {
    return this.properties;
  }

  @Override
  public VulkanPhysicalDeviceLimits limits()
  {
    return this.limits;
  }

  @Override
  public VulkanPhysicalDeviceFeatures features()
  {
    return this.features;
  }

  @Override
  public VulkanFormatProperties formatProperties(
    final VulkanFormat format)
  {
    throw new UnimplementedCodeException();
  }

  @Override
  public VulkanImageFormatProperties imageFormatProperties(
    final VulkanFormat format,
    final VulkanImageKind type,
    final VulkanImageTiling tiling,
    final Set<VulkanImageUsageFlag> usage,
    final Set<VulkanImageCreateFlag> flags)
  {
    throw new UnimplementedCodeException();
  }

  @Override
  public VulkanPhysicalDeviceMemoryProperties memory()
  {
    return this.memory;
  }

  @Override
  public SortedMap<VulkanQueueFamilyIndex, VulkanQueueFamilyProperties> queueFamilies()
  {
    return this.queueFamilies;
  }

  /**
   * Set the next device.
   *
   * @param inNextDevice The next device
   */

  public void setNextDevice(
    final VFakeLogicalDevice inNextDevice)
  {
    this.nextDevice = Objects.requireNonNull(inNextDevice, "nextDevice");
  }

  @Override
  public VulkanLogicalDeviceType createLogicalDevice(
    final VulkanLogicalDeviceCreateInfo info)
  {
    return this.nextDevice;
  }

  @Override
  public Optional<VulkanPhysicalDeviceDriverProperties> driverProperties()
  {
    return Optional.empty();
  }

  @Override
  public Optional<VulkanPhysicalDeviceIDProperties> idProperties()
  {
    return Optional.empty();
  }

  @Override
  public void close()
  {
    if (this.closed.compareAndSet(false, true)) {
      // Nothing
    }
  }

  @Override
  public boolean isClosed()
  {
    return this.closed.get();
  }
}
