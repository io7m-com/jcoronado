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


package com.io7m.jcoronado.api;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Functions over device feature structures.
 */

public final class VulkanPhysicalDeviceFeaturesFunctions
{
  private VulkanPhysicalDeviceFeaturesFunctions()
  {

  }

  private static boolean randomBoolean()
  {
    return Math.random() >= 0.5;
  }

  private static final VulkanPhysicalDeviceFeatures NO_FEATURES_REQUIRED =
    VulkanPhysicalDeviceFeatures.builder()
      .setFeatures10(VulkanPhysicalDeviceFeatures10.builder().build())
      .setFeatures11(VulkanPhysicalDeviceFeatures11.builder().build())
      .setFeatures12(VulkanPhysicalDeviceFeatures12.builder().build())
      .setFeatures13(VulkanPhysicalDeviceFeatures13.builder().build())
      .build();

  /**
   * @return No features
   */

  public static VulkanPhysicalDeviceFeatures none()
  {
    return NO_FEATURES_REQUIRED;
  }

  /**
   * @return A random feature selection
   */

  public static VulkanPhysicalDeviceFeatures random()
  {
    return VulkanPhysicalDeviceFeatures.builder()
      .setFeatures10(random10())
      .setFeatures11(random11())
      .setFeatures12(random12())
      .setFeatures13(random13())
      .build();
  }

  /**
   * Apply a bitwise OR to all fields.
   *
   * @param a The left features
   * @param b The right features
   *
   * @return The OR of all features
   */

  public static VulkanPhysicalDeviceFeatures or(
    final VulkanPhysicalDeviceFeatures a,
    final VulkanPhysicalDeviceFeatures b)
  {
    Objects.requireNonNull(a, "a");
    Objects.requireNonNull(b, "b");

    return VulkanPhysicalDeviceFeatures.builder()
      .setFeatures10(or(a.features10(), b.features10()))
      .setFeatures11(or(a.features11(), b.features11()))
      .setFeatures12(or(a.features12(), b.features12()))
      .setFeatures13(or(a.features13(), b.features13()))
      .build();
  }

  /**
   * @param f The features
   *
   * @return The features as a boolean map
   */

  public static Map<String, Boolean> mapOf10(
    final VulkanPhysicalDeviceFeatures10 f)
  {
    final var m = new TreeMap<String, Boolean>();
    m.put("AlphaToOne", f.alphaToOne());
    m.put("DepthBiasClamp", f.depthBiasClamp());
    m.put("DepthBounds", f.depthBounds());
    m.put("DepthClamp", f.depthClamp());
    m.put("DrawIndirectFirstInstance", f.drawIndirectFirstInstance());
    m.put("DualSrcBlend", f.dualSrcBlend());
    m.put("FillModeNonSolid", f.fillModeNonSolid());
    m.put("FragmentStoresAndAtomics", f.fragmentStoresAndAtomics());
    m.put("FullDrawIndexUint32", f.fullDrawIndexUint32());
    m.put("GeometryShader", f.geometryShader());
    m.put("ImageCubeArray", f.imageCubeArray());
    m.put("IndependentBlend", f.independentBlend());
    m.put("InheritedQueries", f.inheritedQueries());
    m.put("LargePoints", f.largePoints());
    m.put("LogicOp", f.logicOp());
    m.put("MultiDrawIndirect", f.multiDrawIndirect());
    m.put("MultiViewport", f.multiViewport());
    m.put("OcclusionQueryPrecise", f.occlusionQueryPrecise());
    m.put("PipelineStatisticsQuery", f.pipelineStatisticsQuery());
    m.put("RobustBufferAccess", f.robustBufferAccess());
    m.put("SampleRateShading", f.sampleRateShading());
    m.put("SamplerAnisotropy", f.samplerAnisotropy());
    m.put("ShaderClipDistance", f.shaderClipDistance());
    m.put("ShaderCullDistance", f.shaderCullDistance());
    m.put("ShaderFloat64", f.shaderFloat64());
    m.put("ShaderImageGatherExtended", f.shaderImageGatherExtended());
    m.put("ShaderInt16", f.shaderInt16());
    m.put("ShaderInt64", f.shaderInt64());
    m.put("ShaderResourceMinLod", f.shaderResourceMinLod());
    m.put("ShaderResourceResidency", f.shaderResourceResidency());
    m.put(
      "ShaderSampledImageArrayDynamicIndexing",
      f.shaderSampledImageArrayDynamicIndexing());
    m.put(
      "ShaderStorageBufferArrayDynamicIndexing",
      f.shaderStorageBufferArrayDynamicIndexing());
    m.put(
      "ShaderStorageImageArrayDynamicIndexing",
      f.shaderStorageImageArrayDynamicIndexing());
    m.put(
      "ShaderStorageImageExtendedFormats",
      f.shaderStorageImageExtendedFormats());
    m.put("ShaderStorageImageMultisample", f.shaderStorageImageMultisample());
    m.put(
      "ShaderStorageImageReadWithoutFormat",
      f.shaderStorageImageReadWithoutFormat());
    m.put(
      "ShaderStorageImageWriteWithoutFormat",
      f.shaderStorageImageWriteWithoutFormat());
    m.put(
      "ShaderTessellationAndGeometryPointSize",
      f.shaderTessellationAndGeometryPointSize());
    m.put(
      "ShaderUniformBufferArrayDynamicIndexing",
      f.shaderUniformBufferArrayDynamicIndexing());
    m.put("SparseBinding", f.sparseBinding());
    m.put("SparseResidency16Samples", f.sparseResidency16Samples());
    m.put("SparseResidency2Samples", f.sparseResidency2Samples());
    m.put("SparseResidency4Samples", f.sparseResidency4Samples());
    m.put("SparseResidency8Samples", f.sparseResidency8Samples());
    m.put("SparseResidencyAliased", f.sparseResidencyAliased());
    m.put("SparseResidencyBuffer", f.sparseResidencyBuffer());
    m.put("SparseResidencyImage2D", f.sparseResidencyImage2D());
    m.put("SparseResidencyImage3D", f.sparseResidencyImage3D());
    m.put("TessellationShader", f.tessellationShader());
    m.put("TextureCompressionASTC_LDR", f.textureCompressionASTC_LDR());
    m.put("TextureCompressionBC", f.textureCompressionBC());
    m.put("TextureCompressionETC2", f.textureCompressionETC2());
    m.put("VariableMultisampleRate", f.variableMultisampleRate());
    m.put("VertexPipelineStoresAndAtomics", f.vertexPipelineStoresAndAtomics());
    m.put("WideLines", f.wideLines());
    return m;
  }

  /**
   * @return A random feature selection
   */

  public static VulkanPhysicalDeviceFeatures10 random10()
  {
    return VulkanPhysicalDeviceFeatures10.builder()
      .setAlphaToOne(randomBoolean())
      .setDepthBiasClamp(randomBoolean())
      .setDepthBounds(randomBoolean())
      .setDepthClamp(randomBoolean())
      .setDrawIndirectFirstInstance(randomBoolean())
      .setDualSrcBlend(randomBoolean())
      .setFillModeNonSolid(randomBoolean())
      .setFragmentStoresAndAtomics(randomBoolean())
      .setFullDrawIndexUint32(randomBoolean())
      .setGeometryShader(randomBoolean())
      .setImageCubeArray(randomBoolean())
      .setIndependentBlend(randomBoolean())
      .setInheritedQueries(randomBoolean())
      .setLargePoints(randomBoolean())
      .setLogicOp(randomBoolean())
      .setMultiDrawIndirect(randomBoolean())
      .setMultiViewport(randomBoolean())
      .setOcclusionQueryPrecise(randomBoolean())
      .setPipelineStatisticsQuery(randomBoolean())
      .setRobustBufferAccess(randomBoolean())
      .setSampleRateShading(randomBoolean())
      .setSamplerAnisotropy(randomBoolean())
      .setShaderClipDistance(randomBoolean())
      .setShaderCullDistance(randomBoolean())
      .setShaderFloat64(randomBoolean())
      .setShaderImageGatherExtended(randomBoolean())
      .setShaderInt16(randomBoolean())
      .setShaderInt64(randomBoolean())
      .setShaderResourceMinLod(randomBoolean())
      .setShaderResourceResidency(randomBoolean())
      .setShaderSampledImageArrayDynamicIndexing(randomBoolean())
      .setShaderStorageBufferArrayDynamicIndexing(randomBoolean())
      .setShaderStorageImageArrayDynamicIndexing(randomBoolean())
      .setShaderStorageImageExtendedFormats(randomBoolean())
      .setShaderStorageImageMultisample(randomBoolean())
      .setShaderStorageImageReadWithoutFormat(randomBoolean())
      .setShaderStorageImageWriteWithoutFormat(randomBoolean())
      .setShaderTessellationAndGeometryPointSize(randomBoolean())
      .setShaderUniformBufferArrayDynamicIndexing(randomBoolean())
      .setSparseBinding(randomBoolean())
      .setSparseResidency16Samples(randomBoolean())
      .setSparseResidency2Samples(randomBoolean())
      .setSparseResidency4Samples(randomBoolean())
      .setSparseResidency8Samples(randomBoolean())
      .setSparseResidencyAliased(randomBoolean())
      .setSparseResidencyBuffer(randomBoolean())
      .setSparseResidencyImage2D(randomBoolean())
      .setSparseResidencyImage3D(randomBoolean())
      .setTessellationShader(randomBoolean())
      .setTextureCompressionASTC_LDR(randomBoolean())
      .setTextureCompressionBC(randomBoolean())
      .setTextureCompressionETC2(randomBoolean())
      .setVariableMultisampleRate(randomBoolean())
      .setVertexPipelineStoresAndAtomics(randomBoolean())
      .setWideLines(randomBoolean())
      .build();
  }

  /**
   * Apply a bitwise OR to all fields.
   *
   * @param a The left features
   * @param b The right features
   *
   * @return The OR of all features
   */

  public static VulkanPhysicalDeviceFeatures10 or(
    final VulkanPhysicalDeviceFeatures10 a,
    final VulkanPhysicalDeviceFeatures10 b)
  {
    Objects.requireNonNull(a, "a");
    Objects.requireNonNull(b, "b");

    return VulkanPhysicalDeviceFeatures10.builder()
      .setAlphaToOne(
        a.alphaToOne() || b.alphaToOne())
      .setDepthBiasClamp(
        a.depthBiasClamp() || b.depthBiasClamp())
      .setDepthBounds(
        a.depthBounds() || b.depthBounds())
      .setDepthClamp(
        a.depthClamp() || b.depthClamp())
      .setDrawIndirectFirstInstance(
        a.drawIndirectFirstInstance() || b.drawIndirectFirstInstance())
      .setDualSrcBlend(
        a.dualSrcBlend() || b.dualSrcBlend())
      .setFillModeNonSolid(
        a.fillModeNonSolid() || b.fillModeNonSolid())
      .setFragmentStoresAndAtomics(
        a.fragmentStoresAndAtomics() || b.fragmentStoresAndAtomics())
      .setFullDrawIndexUint32(
        a.fullDrawIndexUint32() || b.fullDrawIndexUint32())
      .setGeometryShader(
        a.geometryShader() || b.geometryShader())
      .setImageCubeArray(
        a.imageCubeArray() || b.imageCubeArray())
      .setIndependentBlend(
        a.independentBlend() || b.independentBlend())
      .setInheritedQueries(
        a.inheritedQueries() || b.inheritedQueries())
      .setLargePoints(
        a.largePoints() || b.largePoints())
      .setLogicOp(
        a.logicOp() || b.logicOp())
      .setMultiDrawIndirect(
        a.multiDrawIndirect() || b.multiDrawIndirect())
      .setMultiViewport(
        a.multiViewport() || b.multiViewport())
      .setOcclusionQueryPrecise(
        a.occlusionQueryPrecise() || b.occlusionQueryPrecise())
      .setPipelineStatisticsQuery(
        a.pipelineStatisticsQuery() || b.pipelineStatisticsQuery())
      .setRobustBufferAccess(
        a.robustBufferAccess() || b.robustBufferAccess())
      .setSampleRateShading(
        a.sampleRateShading() || b.sampleRateShading())
      .setSamplerAnisotropy(
        a.samplerAnisotropy() || b.samplerAnisotropy())
      .setShaderClipDistance(
        a.shaderClipDistance() || b.shaderClipDistance())
      .setShaderCullDistance(
        a.shaderCullDistance() || b.shaderCullDistance())
      .setShaderFloat64(
        a.shaderFloat64() || b.shaderFloat64())
      .setShaderImageGatherExtended(
        a.shaderImageGatherExtended() || b.shaderImageGatherExtended())
      .setShaderInt16(
        a.shaderInt16() || b.shaderInt16())
      .setShaderInt64(
        a.shaderInt64() || b.shaderInt64())
      .setShaderResourceMinLod(
        a.shaderResourceMinLod() || b.shaderResourceMinLod())
      .setShaderResourceResidency(
        a.shaderResourceResidency() || b.shaderResourceResidency())
      .setShaderSampledImageArrayDynamicIndexing(
        a.shaderSampledImageArrayDynamicIndexing() || b.shaderSampledImageArrayDynamicIndexing())
      .setShaderStorageBufferArrayDynamicIndexing(
        a.shaderStorageBufferArrayDynamicIndexing() || b.shaderStorageBufferArrayDynamicIndexing())
      .setShaderStorageImageArrayDynamicIndexing(
        a.shaderStorageImageArrayDynamicIndexing() || b.shaderStorageImageArrayDynamicIndexing())
      .setShaderStorageImageExtendedFormats(
        a.shaderStorageImageExtendedFormats() || b.shaderStorageImageExtendedFormats())
      .setShaderStorageImageMultisample(
        a.shaderStorageImageMultisample() || b.shaderStorageImageMultisample())
      .setShaderStorageImageReadWithoutFormat(
        a.shaderStorageImageReadWithoutFormat() || b.shaderStorageImageReadWithoutFormat())
      .setShaderStorageImageWriteWithoutFormat(
        a.shaderStorageImageWriteWithoutFormat() || b.shaderStorageImageWriteWithoutFormat())
      .setShaderTessellationAndGeometryPointSize(
        a.shaderTessellationAndGeometryPointSize() || b.shaderTessellationAndGeometryPointSize())
      .setShaderUniformBufferArrayDynamicIndexing(
        a.shaderUniformBufferArrayDynamicIndexing() || b.shaderUniformBufferArrayDynamicIndexing())
      .setSparseBinding(
        a.sparseBinding() || b.sparseBinding())
      .setSparseResidency16Samples(
        a.sparseResidency16Samples() || b.sparseResidency16Samples())
      .setSparseResidency2Samples(
        a.sparseResidency2Samples() || b.sparseResidency2Samples())
      .setSparseResidency4Samples(
        a.sparseResidency4Samples() || b.sparseResidency4Samples())
      .setSparseResidency8Samples(
        a.sparseResidency8Samples() || b.sparseResidency8Samples())
      .setSparseResidencyAliased(
        a.sparseResidencyAliased() || b.sparseResidencyAliased())
      .setSparseResidencyBuffer(
        a.sparseResidencyBuffer() || b.sparseResidencyBuffer())
      .setSparseResidencyImage2D(
        a.sparseResidencyImage2D() || b.sparseResidencyImage2D())
      .setSparseResidencyImage3D(
        a.sparseResidencyImage3D() || b.sparseResidencyImage3D())
      .setTessellationShader(
        a.tessellationShader() || b.tessellationShader())
      .setTextureCompressionASTC_LDR(
        a.textureCompressionASTC_LDR() || b.textureCompressionASTC_LDR())
      .setTextureCompressionBC(
        a.textureCompressionBC() || b.textureCompressionBC())
      .setTextureCompressionETC2(
        a.textureCompressionETC2() || b.textureCompressionETC2())
      .setVariableMultisampleRate(
        a.variableMultisampleRate() || b.variableMultisampleRate())
      .setVertexPipelineStoresAndAtomics(
        a.vertexPipelineStoresAndAtomics() || b.vertexPipelineStoresAndAtomics())
      .setWideLines(
        a.wideLines() || b.wideLines())
      .build();
  }

  /**
   * @return A random feature selection
   */

  public static VulkanPhysicalDeviceFeatures11 random11()
  {
    return VulkanPhysicalDeviceFeatures11.builder()
      .setMultiview(randomBoolean())
      .setMultiviewGeometryShader(randomBoolean())
      .setMultiviewTessellationShader(randomBoolean())
      .setProtectedMemory(randomBoolean())
      .setSamplerYcbcrConversion(randomBoolean())
      .setShaderDrawParameters(randomBoolean())
      .setStorageBuffer16BitAccess(randomBoolean())
      .setStorageInputOutput16(randomBoolean())
      .setStoragePushConstant16(randomBoolean())
      .setUniformAndStorageBuffer16BitAccess(randomBoolean())
      .setVariablePointers(randomBoolean())
      .setVariablePointersStorageBuffer(randomBoolean())
      .build();
  }

  /**
   * Apply a bitwise OR to all fields.
   *
   * @param a The left features
   * @param b The right features
   *
   * @return The OR of all features
   */

  public static VulkanPhysicalDeviceFeatures11 or(
    final VulkanPhysicalDeviceFeatures11 a,
    final VulkanPhysicalDeviceFeatures11 b)
  {
    Objects.requireNonNull(a, "a");
    Objects.requireNonNull(b, "b");

    return VulkanPhysicalDeviceFeatures11.builder()
      .setMultiview(
        a.multiview() || b.multiview())
      .setMultiviewGeometryShader(
        a.multiviewGeometryShader() || b.multiviewGeometryShader())
      .setMultiviewTessellationShader(
        a.multiviewTessellationShader() || b.multiviewTessellationShader())
      .setProtectedMemory(
        a.protectedMemory() || b.protectedMemory())
      .setSamplerYcbcrConversion(
        a.samplerYcbcrConversion() || b.samplerYcbcrConversion())
      .setShaderDrawParameters(
        a.shaderDrawParameters() || b.shaderDrawParameters())
      .setStorageBuffer16BitAccess(
        a.storageBuffer16BitAccess() || b.storageBuffer16BitAccess())
      .setStorageInputOutput16(
        a.storageInputOutput16() || b.storageInputOutput16())
      .setStoragePushConstant16(
        a.storagePushConstant16() || b.storagePushConstant16())
      .setUniformAndStorageBuffer16BitAccess(
        a.uniformAndStorageBuffer16BitAccess() || b.uniformAndStorageBuffer16BitAccess())
      .setVariablePointers(
        a.variablePointers() || b.variablePointers())
      .setVariablePointersStorageBuffer(
        a.variablePointersStorageBuffer() || b.variablePointersStorageBuffer())
      .build();
  }

  /**
   * @return A random feature selection
   */

  public static VulkanPhysicalDeviceFeatures12 random12()
  {
    return VulkanPhysicalDeviceFeatures12.builder()
      .setBufferDeviceAddress(randomBoolean())
      .setBufferDeviceAddressCaptureReplay(randomBoolean())
      .setBufferDeviceAddressMultiDevice(randomBoolean())
      .setDescriptorBindingPartiallyBound(randomBoolean())
      .setDescriptorBindingSampledImageUpdateAfterBind(randomBoolean())
      .setDescriptorBindingStorageBufferUpdateAfterBind(randomBoolean())
      .setDescriptorBindingStorageImageUpdateAfterBind(randomBoolean())
      .setDescriptorBindingStorageTexelBufferUpdateAfterBind(randomBoolean())
      .setDescriptorBindingUniformBufferUpdateAfterBind(randomBoolean())
      .setDescriptorBindingUniformTexelBufferUpdateAfterBind(randomBoolean())
      .setDescriptorBindingUpdateUnusedWhilePending(randomBoolean())
      .setDescriptorBindingVariableDescriptorCount(randomBoolean())
      .setDescriptorIndexing(randomBoolean())
      .setDrawIndirectCount(randomBoolean())
      .setHostQueryReset(randomBoolean())
      .setImagelessFramebuffer(randomBoolean())
      .setRuntimeDescriptorArray(randomBoolean())
      .setSamplerFilterMinmax(randomBoolean())
      .setSamplerMirrorClampToEdge(randomBoolean())
      .setScalarBlockLayout(randomBoolean())
      .setSeparateDepthStencilLayouts(randomBoolean())
      .setShaderBufferInt64Atomics(randomBoolean())
      .setShaderFloat16(randomBoolean())
      .setShaderInputAttachmentArrayDynamicIndexing(randomBoolean())
      .setShaderInputAttachmentArrayNonUniformIndexing(randomBoolean())
      .setShaderInt8(randomBoolean())
      .setShaderOutputLayer(randomBoolean())
      .setShaderOutputViewportIndex(randomBoolean())
      .setShaderSampledImageArrayNonUniformIndexing(randomBoolean())
      .setShaderSharedInt64Atomics(randomBoolean())
      .setShaderStorageBufferArrayNonUniformIndexing(randomBoolean())
      .setShaderStorageImageArrayNonUniformIndexing(randomBoolean())
      .setShaderStorageTexelBufferArrayDynamicIndexing(randomBoolean())
      .setShaderStorageTexelBufferArrayNonUniformIndexing(randomBoolean())
      .setShaderSubgroupExtendedTypes(randomBoolean())
      .setShaderUniformBufferArrayNonUniformIndexing(randomBoolean())
      .setShaderUniformTexelBufferArrayDynamicIndexing(randomBoolean())
      .setShaderUniformTexelBufferArrayNonUniformIndexing(randomBoolean())
      .setStorageBuffer8BitAccess(randomBoolean())
      .setStoragePushConstant8(randomBoolean())
      .setSubgroupBroadcastDynamicId(randomBoolean())
      .setTimelineSemaphore(randomBoolean())
      .setUniformAndStorageBuffer8BitAccess(randomBoolean())
      .setUniformBufferStandardLayout(randomBoolean())
      .setVulkanMemoryModel(randomBoolean())
      .setVulkanMemoryModelAvailabilityVisibilityChains(randomBoolean())
      .setVulkanMemoryModelDeviceScope(randomBoolean())
      .build();
  }

  /**
   * Apply a bitwise OR to all fields.
   *
   * @param a The left features
   * @param b The right features
   *
   * @return The OR of all features
   */

  public static VulkanPhysicalDeviceFeatures12 or(
    final VulkanPhysicalDeviceFeatures12 a,
    final VulkanPhysicalDeviceFeatures12 b)
  {
    Objects.requireNonNull(a, "a");
    Objects.requireNonNull(b, "b");

    return VulkanPhysicalDeviceFeatures12.builder()
      .setBufferDeviceAddress(
        a.bufferDeviceAddress() || b.bufferDeviceAddress())
      .setBufferDeviceAddressCaptureReplay(
        a.bufferDeviceAddressCaptureReplay() || b.bufferDeviceAddressCaptureReplay())
      .setBufferDeviceAddressMultiDevice(
        a.bufferDeviceAddressMultiDevice() || b.bufferDeviceAddressMultiDevice())
      .setDescriptorBindingPartiallyBound(
        a.descriptorBindingPartiallyBound() || b.descriptorBindingPartiallyBound())
      .setDescriptorBindingSampledImageUpdateAfterBind(
        a.descriptorBindingSampledImageUpdateAfterBind() || b.descriptorBindingSampledImageUpdateAfterBind())
      .setDescriptorBindingStorageBufferUpdateAfterBind(
        a.descriptorBindingStorageBufferUpdateAfterBind() || b.descriptorBindingStorageBufferUpdateAfterBind())
      .setDescriptorBindingStorageImageUpdateAfterBind(
        a.descriptorBindingStorageImageUpdateAfterBind() || b.descriptorBindingStorageImageUpdateAfterBind())
      .setDescriptorBindingStorageTexelBufferUpdateAfterBind(
        a.descriptorBindingStorageTexelBufferUpdateAfterBind() || b.descriptorBindingStorageTexelBufferUpdateAfterBind())
      .setDescriptorBindingUniformBufferUpdateAfterBind(
        a.descriptorBindingUniformBufferUpdateAfterBind() || b.descriptorBindingUniformBufferUpdateAfterBind())
      .setDescriptorBindingUniformTexelBufferUpdateAfterBind(
        a.descriptorBindingUniformTexelBufferUpdateAfterBind() || b.descriptorBindingUniformTexelBufferUpdateAfterBind())
      .setDescriptorBindingUpdateUnusedWhilePending(
        a.descriptorBindingUpdateUnusedWhilePending() || b.descriptorBindingUpdateUnusedWhilePending())
      .setDescriptorBindingVariableDescriptorCount(
        a.descriptorBindingVariableDescriptorCount() || b.descriptorBindingVariableDescriptorCount())
      .setDescriptorIndexing(
        a.descriptorIndexing() || b.descriptorIndexing())
      .setDrawIndirectCount(
        a.drawIndirectCount() || b.drawIndirectCount())
      .setHostQueryReset(
        a.hostQueryReset() || b.hostQueryReset())
      .setImagelessFramebuffer(
        a.imagelessFramebuffer() || b.imagelessFramebuffer())
      .setRuntimeDescriptorArray(
        a.runtimeDescriptorArray() || b.runtimeDescriptorArray())
      .setSamplerFilterMinmax(
        a.samplerFilterMinmax() || b.samplerFilterMinmax())
      .setSamplerMirrorClampToEdge(
        a.samplerMirrorClampToEdge() || b.samplerMirrorClampToEdge())
      .setScalarBlockLayout(
        a.scalarBlockLayout() || b.scalarBlockLayout())
      .setSeparateDepthStencilLayouts(
        a.separateDepthStencilLayouts() || b.separateDepthStencilLayouts())
      .setShaderBufferInt64Atomics(
        a.shaderBufferInt64Atomics() || b.shaderBufferInt64Atomics())
      .setShaderFloat16(
        a.shaderFloat16() || b.shaderFloat16())
      .setShaderInputAttachmentArrayDynamicIndexing(
        a.shaderInputAttachmentArrayDynamicIndexing() || b.shaderInputAttachmentArrayDynamicIndexing())
      .setShaderInputAttachmentArrayNonUniformIndexing(
        a.shaderInputAttachmentArrayNonUniformIndexing() || b.shaderInputAttachmentArrayNonUniformIndexing())
      .setShaderInt8(
        a.shaderInt8() || b.shaderInt8())
      .setShaderOutputLayer(
        a.shaderOutputLayer() || b.shaderOutputLayer())
      .setShaderOutputViewportIndex(
        a.shaderOutputViewportIndex() || b.shaderOutputViewportIndex())
      .setShaderSampledImageArrayNonUniformIndexing(
        a.shaderSampledImageArrayNonUniformIndexing() || b.shaderSampledImageArrayNonUniformIndexing())
      .setShaderSharedInt64Atomics(
        a.shaderSharedInt64Atomics() || b.shaderSharedInt64Atomics())
      .setShaderStorageBufferArrayNonUniformIndexing(
        a.shaderStorageBufferArrayNonUniformIndexing() || b.shaderStorageBufferArrayNonUniformIndexing())
      .setShaderStorageImageArrayNonUniformIndexing(
        a.shaderStorageImageArrayNonUniformIndexing() || b.shaderStorageImageArrayNonUniformIndexing())
      .setShaderStorageTexelBufferArrayDynamicIndexing(
        a.shaderStorageTexelBufferArrayDynamicIndexing() || b.shaderStorageTexelBufferArrayDynamicIndexing())
      .setShaderStorageTexelBufferArrayNonUniformIndexing(
        a.shaderStorageTexelBufferArrayNonUniformIndexing() || b.shaderStorageTexelBufferArrayNonUniformIndexing())
      .setShaderSubgroupExtendedTypes(
        a.shaderSubgroupExtendedTypes() || b.shaderSubgroupExtendedTypes())
      .setShaderUniformBufferArrayNonUniformIndexing(
        a.shaderUniformBufferArrayNonUniformIndexing() || b.shaderUniformBufferArrayNonUniformIndexing())
      .setShaderUniformTexelBufferArrayDynamicIndexing(
        a.shaderUniformTexelBufferArrayDynamicIndexing() || b.shaderUniformTexelBufferArrayDynamicIndexing())
      .setShaderUniformTexelBufferArrayNonUniformIndexing(
        a.shaderUniformTexelBufferArrayNonUniformIndexing() || b.shaderUniformTexelBufferArrayNonUniformIndexing())
      .setStorageBuffer8BitAccess(
        a.storageBuffer8BitAccess() || b.storageBuffer8BitAccess())
      .setStoragePushConstant8(
        a.storagePushConstant8() || b.storagePushConstant8())
      .setSubgroupBroadcastDynamicId(
        a.subgroupBroadcastDynamicId() || b.subgroupBroadcastDynamicId())
      .setTimelineSemaphore(
        a.timelineSemaphore() || b.timelineSemaphore())
      .setUniformAndStorageBuffer8BitAccess(
        a.uniformAndStorageBuffer8BitAccess() || b.uniformAndStorageBuffer8BitAccess())
      .setUniformBufferStandardLayout(
        a.uniformBufferStandardLayout() || b.uniformBufferStandardLayout())
      .setVulkanMemoryModel(
        a.vulkanMemoryModel() || b.vulkanMemoryModel())
      .setVulkanMemoryModelAvailabilityVisibilityChains(
        a.vulkanMemoryModelAvailabilityVisibilityChains() || b.vulkanMemoryModelAvailabilityVisibilityChains())
      .setVulkanMemoryModelDeviceScope(
        a.vulkanMemoryModelDeviceScope() || b.vulkanMemoryModelDeviceScope())
      .build();
  }

  /**
   * @return A random feature selection
   */

  public static VulkanPhysicalDeviceFeatures13 random13()
  {
    return VulkanPhysicalDeviceFeatures13.builder()
      .setComputeFullSubgroups(randomBoolean())
      .setDescriptorBindingInlineUniformBlockUpdateAfterBind(randomBoolean())
      .setDynamicRendering(randomBoolean())
      .setInlineUniformBlock(randomBoolean())
      .setMaintenance4(randomBoolean())
      .setPipelineCreationCacheControl(randomBoolean())
      .setPrivateData(randomBoolean())
      .setRobustImageAccess(randomBoolean())
      .setShaderDemoteToHelperInvocation(randomBoolean())
      .setShaderIntegerDotProduct(randomBoolean())
      .setShaderTerminateInvocation(randomBoolean())
      .setShaderZeroInitializeWorkgroupMemory(randomBoolean())
      .setSubgroupSizeControl(randomBoolean())
      .setSynchronization2(randomBoolean())
      .setTextureCompressionASTC_HDR(randomBoolean())
      .build();
  }

  /**
   * Apply a bitwise OR to all fields.
   *
   * @param a The left features
   * @param b The right features
   *
   * @return The OR of all features
   */

  public static VulkanPhysicalDeviceFeatures13 or(
    final VulkanPhysicalDeviceFeatures13 a,
    final VulkanPhysicalDeviceFeatures13 b)
  {
    Objects.requireNonNull(a, "a");
    Objects.requireNonNull(b, "b");

    return VulkanPhysicalDeviceFeatures13.builder()
      .setComputeFullSubgroups(
        a.computeFullSubgroups() || b.computeFullSubgroups())
      .setDescriptorBindingInlineUniformBlockUpdateAfterBind(
        a.descriptorBindingInlineUniformBlockUpdateAfterBind()
        || b.descriptorBindingInlineUniformBlockUpdateAfterBind())
      .setDynamicRendering(
        a.dynamicRendering() || b.dynamicRendering())
      .setInlineUniformBlock(
        a.inlineUniformBlock() || b.inlineUniformBlock())
      .setMaintenance4(
        a.maintenance4() || b.maintenance4())
      .setPipelineCreationCacheControl(
        a.pipelineCreationCacheControl() || b.pipelineCreationCacheControl())
      .setPrivateData(
        a.privateData() || b.privateData())
      .setRobustImageAccess(
        a.robustImageAccess() || b.robustImageAccess())
      .setShaderDemoteToHelperInvocation(
        a.shaderDemoteToHelperInvocation() || b.shaderDemoteToHelperInvocation())
      .setShaderIntegerDotProduct(
        a.shaderIntegerDotProduct() || b.shaderIntegerDotProduct())
      .setShaderTerminateInvocation(
        a.shaderTerminateInvocation() || b.shaderTerminateInvocation())
      .setShaderZeroInitializeWorkgroupMemory(
        a.shaderZeroInitializeWorkgroupMemory() || b.shaderZeroInitializeWorkgroupMemory())
      .setSubgroupSizeControl(
        a.subgroupSizeControl() || b.subgroupSizeControl())
      .setSynchronization2(
        a.synchronization2() || b.synchronization2())
      .setTextureCompressionASTC_HDR(
        a.textureCompressionASTC_HDR() || b.textureCompressionASTC_HDR())
      .build();
  }

  /**
   * @param f The features
   *
   * @return The features as a boolean map
   */

  public static Map<String, Boolean> mapOf11(
    final VulkanPhysicalDeviceFeatures11 f)
  {
    final var m = new TreeMap<String, Boolean>();
    m.put("Multiview", f.multiview());
    m.put("MultiviewGeometryShader", f.multiviewGeometryShader());
    m.put("MultiviewTessellationShader", f.multiviewTessellationShader());
    m.put("ProtectedMemory", f.protectedMemory());
    m.put("SamplerYcbcrConversion", f.samplerYcbcrConversion());
    m.put("ShaderDrawParameters", f.shaderDrawParameters());
    m.put("StorageBuffer16BitAccess", f.storageBuffer16BitAccess());
    m.put("StorageInputOutput16", f.storageInputOutput16());
    m.put("StoragePushConstant16", f.storagePushConstant16());
    m.put(
      "UniformAndStorageBuffer16BitAccess",
      f.uniformAndStorageBuffer16BitAccess());
    m.put("VariablePointers", f.variablePointers());
    m.put("VariablePointersStorageBuffer", f.variablePointersStorageBuffer());
    return m;
  }

  /**
   * @param f The features
   *
   * @return The features as a boolean map
   */

  public static Map<String, Boolean> mapOf12(
    final VulkanPhysicalDeviceFeatures12 f)
  {
    final var m = new TreeMap<String, Boolean>();
    m.put("BufferDeviceAddress", f.bufferDeviceAddress());
    m.put(
      "BufferDeviceAddressCaptureReplay",
      f.bufferDeviceAddressCaptureReplay());
    m.put("BufferDeviceAddressMultiDevice", f.bufferDeviceAddressMultiDevice());
    m.put(
      "DescriptorBindingPartiallyBound",
      f.descriptorBindingPartiallyBound());
    m.put(
      "DescriptorBindingSampledImageUpdateAfterBind",
      f.descriptorBindingSampledImageUpdateAfterBind());
    m.put(
      "DescriptorBindingStorageBufferUpdateAfterBind",
      f.descriptorBindingStorageBufferUpdateAfterBind());
    m.put(
      "DescriptorBindingStorageImageUpdateAfterBind",
      f.descriptorBindingStorageImageUpdateAfterBind());
    m.put(
      "DescriptorBindingStorageTexelBufferUpdateAfterBind",
      f.descriptorBindingStorageTexelBufferUpdateAfterBind());
    m.put(
      "DescriptorBindingUniformBufferUpdateAfterBind",
      f.descriptorBindingUniformBufferUpdateAfterBind());
    m.put(
      "DescriptorBindingUniformTexelBufferUpdateAfterBind",
      f.descriptorBindingUniformTexelBufferUpdateAfterBind());
    m.put(
      "DescriptorBindingUpdateUnusedWhilePending",
      f.descriptorBindingUpdateUnusedWhilePending());
    m.put(
      "DescriptorBindingVariableDescriptorCount",
      f.descriptorBindingVariableDescriptorCount());
    m.put("DescriptorIndexing", f.descriptorIndexing());
    m.put("DrawIndirectCount", f.drawIndirectCount());
    m.put("HostQueryReset", f.hostQueryReset());
    m.put("ImagelessFramebuffer", f.imagelessFramebuffer());
    m.put("RuntimeDescriptorArray", f.runtimeDescriptorArray());
    m.put("SamplerFilterMinmax", f.samplerFilterMinmax());
    m.put("SamplerMirrorClampToEdge", f.samplerMirrorClampToEdge());
    m.put("ScalarBlockLayout", f.scalarBlockLayout());
    m.put("SeparateDepthStencilLayouts", f.separateDepthStencilLayouts());
    m.put("ShaderBufferInt64Atomics", f.shaderBufferInt64Atomics());
    m.put("ShaderFloat16", f.shaderFloat16());
    m.put(
      "ShaderInputAttachmentArrayDynamicIndexing",
      f.shaderInputAttachmentArrayDynamicIndexing());
    m.put(
      "ShaderInputAttachmentArrayNonUniformIndexing",
      f.shaderInputAttachmentArrayNonUniformIndexing());
    m.put("ShaderInt8", f.shaderInt8());
    m.put("ShaderOutputLayer", f.shaderOutputLayer());
    m.put("ShaderOutputViewportIndex", f.shaderOutputViewportIndex());
    m.put(
      "ShaderSampledImageArrayNonUniformIndexing",
      f.shaderSampledImageArrayNonUniformIndexing());
    m.put("ShaderSharedInt64Atomics", f.shaderSharedInt64Atomics());
    m.put(
      "ShaderStorageBufferArrayNonUniformIndexing",
      f.shaderStorageBufferArrayNonUniformIndexing());
    m.put(
      "ShaderStorageImageArrayNonUniformIndexing",
      f.shaderStorageImageArrayNonUniformIndexing());
    m.put(
      "ShaderStorageTexelBufferArrayDynamicIndexing",
      f.shaderStorageTexelBufferArrayDynamicIndexing());
    m.put(
      "ShaderStorageTexelBufferArrayNonUniformIndexing",
      f.shaderStorageTexelBufferArrayNonUniformIndexing());
    m.put("ShaderSubgroupExtendedTypes", f.shaderSubgroupExtendedTypes());
    m.put(
      "ShaderUniformBufferArrayNonUniformIndexing",
      f.shaderUniformBufferArrayNonUniformIndexing());
    m.put(
      "ShaderUniformTexelBufferArrayDynamicIndexing",
      f.shaderUniformTexelBufferArrayDynamicIndexing());
    m.put(
      "ShaderUniformTexelBufferArrayNonUniformIndexing",
      f.shaderUniformTexelBufferArrayNonUniformIndexing());
    m.put("StorageBuffer8BitAccess", f.storageBuffer8BitAccess());
    m.put("StoragePushConstant8", f.storagePushConstant8());
    m.put("SubgroupBroadcastDynamicId", f.subgroupBroadcastDynamicId());
    m.put("TimelineSemaphore", f.timelineSemaphore());
    m.put(
      "UniformAndStorageBuffer8BitAccess",
      f.uniformAndStorageBuffer8BitAccess());
    m.put("UniformBufferStandardLayout", f.uniformBufferStandardLayout());
    m.put("VulkanMemoryModel", f.vulkanMemoryModel());
    m.put(
      "VulkanMemoryModelAvailabilityVisibilityChains",
      f.vulkanMemoryModelAvailabilityVisibilityChains());
    m.put("VulkanMemoryModelDeviceScope", f.vulkanMemoryModelDeviceScope());
    return m;
  }

  /**
   * @param f The features
   *
   * @return The features as a boolean map
   */

  public static Map<String, Boolean> mapOf13(
    final VulkanPhysicalDeviceFeatures13 f)
  {
    final var m = new TreeMap<String, Boolean>();
    m.put("ComputeFullSubgroups", f.computeFullSubgroups());
    m.put(
      "DescriptorBindingInlineUniformBlockUpdateAfterBind",
      f.descriptorBindingInlineUniformBlockUpdateAfterBind());
    m.put("DynamicRendering", f.dynamicRendering());
    m.put("InlineUniformBlock", f.inlineUniformBlock());
    m.put("Maintenance4", f.maintenance4());
    m.put("PipelineCreationCacheControl", f.pipelineCreationCacheControl());
    m.put("PrivateData", f.privateData());
    m.put("RobustImageAccess", f.robustImageAccess());
    m.put("ShaderDemoteToHelperInvocation", f.shaderDemoteToHelperInvocation());
    m.put("ShaderIntegerDotProduct", f.shaderIntegerDotProduct());
    m.put("ShaderTerminateInvocation", f.shaderTerminateInvocation());
    m.put(
      "ShaderZeroInitializeWorkgroupMemory",
      f.shaderZeroInitializeWorkgroupMemory());
    m.put("SubgroupSizeControl", f.subgroupSizeControl());
    m.put("Synchronization2", f.synchronization2());
    m.put("TextureCompressionASTC_HDR", f.textureCompressionASTC_HDR());
    return m;
  }

  /**
   * @param f The features
   *
   * @return The features as a boolean map
   */

  public static Map<String, Boolean> mapOf(
    final VulkanPhysicalDeviceFeatures f)
  {
    final var m = new TreeMap<String, Boolean>();
    m.putAll(mapOf10(f.features10()));
    m.putAll(mapOf11(f.features11()));
    m.putAll(mapOf12(f.features12()));
    m.putAll(mapOf13(f.features13()));
    return m;
  }

  /**
   * Determine if the given set of features in {@code supported} satisfies
   * those in {@code requested}. An empty set is returned if everything is
   * supported.
   *
   * @param supported The supported features
   * @param requested The requested features
   *
   * @return The features that were not available
   */

  public static Set<String> isSupported10(
    final VulkanPhysicalDeviceFeatures10 supported,
    final VulkanPhysicalDeviceFeatures10 requested)
  {
    final var supportedMap =
      mapOf10(supported);
    final var requiredMap =
      mapOf10(requested);
    final var failedSet =
      new TreeSet<String>();

    for (final var entry : requiredMap.entrySet()) {
      final var requestedValue = entry.getValue();
      final var supportedValue = supportedMap.get(entry.getKey());
      if (requestedValue.booleanValue()) {
        if (!supportedValue.booleanValue()) {
          failedSet.add(entry.getKey());
        }
      }
    }
    return failedSet;
  }

  /**
   * Determine if the given set of features in {@code supported} satisfies
   * those in {@code requested}. An empty set is returned if everything is
   * supported.
   *
   * @param supported The supported features
   * @param requested The requested features
   *
   * @return The features that were not available
   */

  public static Set<String> isSupported11(
    final VulkanPhysicalDeviceFeatures11 supported,
    final VulkanPhysicalDeviceFeatures11 requested)
  {
    final var supportedMap =
      mapOf11(supported);
    final var requiredMap =
      mapOf11(requested);
    final var failedSet =
      new TreeSet<String>();

    for (final var entry : requiredMap.entrySet()) {
      final var requestedValue = entry.getValue();
      final var supportedValue = supportedMap.get(entry.getKey());
      if (requestedValue.booleanValue()) {
        if (!supportedValue.booleanValue()) {
          failedSet.add(entry.getKey());
        }
      }
    }
    return failedSet;
  }

  /**
   * Determine if the given set of features in {@code supported} satisfies
   * those in {@code requested}. An empty set is returned if everything is
   * supported.
   *
   * @param supported The supported features
   * @param requested The requested features
   *
   * @return The features that were not available
   */

  public static Set<String> isSupported12(
    final VulkanPhysicalDeviceFeatures12 supported,
    final VulkanPhysicalDeviceFeatures12 requested)
  {
    final var supportedMap =
      mapOf12(supported);
    final var requiredMap =
      mapOf12(requested);
    final var failedSet =
      new TreeSet<String>();

    for (final var entry : requiredMap.entrySet()) {
      final var requestedValue = entry.getValue();
      final var supportedValue = supportedMap.get(entry.getKey());
      if (requestedValue.booleanValue()) {
        if (!supportedValue.booleanValue()) {
          failedSet.add(entry.getKey());
        }
      }
    }
    return failedSet;
  }

  /**
   * Determine if the given set of features in {@code supported} satisfies
   * those in {@code requested}. An empty set is returned if everything is
   * supported.
   *
   * @param supported The supported features
   * @param requested The requested features
   *
   * @return The features that were not available
   */

  public static Set<String> isSupported13(
    final VulkanPhysicalDeviceFeatures13 supported,
    final VulkanPhysicalDeviceFeatures13 requested)
  {
    final var supportedMap =
      mapOf13(supported);
    final var requiredMap =
      mapOf13(requested);
    final var failedSet =
      new TreeSet<String>();

    for (final var entry : requiredMap.entrySet()) {
      final var requestedValue = entry.getValue();
      final var supportedValue = supportedMap.get(entry.getKey());
      if (requestedValue.booleanValue()) {
        if (!supportedValue.booleanValue()) {
          failedSet.add(entry.getKey());
        }
      }
    }
    return failedSet;
  }

  /**
   * Determine if the given set of features in {@code supported} satisfies
   * those in {@code requested}. An empty set is returned if everything is
   * supported.
   *
   * @param supported The supported features
   * @param requested The requested features
   *
   * @return The features that were not available
   */

  public static Set<String> isSupported(
    final VulkanPhysicalDeviceFeatures supported,
    final VulkanPhysicalDeviceFeatures requested)
  {
    final var failedSet = new TreeSet<String>();
    failedSet.addAll(
      isSupported10(supported.features10(), requested.features10()));
    failedSet.addAll(
      isSupported11(supported.features11(), requested.features11()));
    failedSet.addAll(
      isSupported12(supported.features12(), requested.features12()));
    failedSet.addAll(
      isSupported13(supported.features13(), requested.features13()));
    return failedSet;
  }
}
