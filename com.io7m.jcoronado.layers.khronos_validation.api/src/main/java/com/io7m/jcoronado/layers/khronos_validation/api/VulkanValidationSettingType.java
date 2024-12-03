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


package com.io7m.jcoronado.layers.khronos_validation.api;

import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingType;

/**
 * The type of validation settings.
 *
 * @see "https://vulkan.lunarg.com/doc/view/latest/linux/khronos_validation_layer.html#user-content-layer-details"
 */

public sealed interface VulkanValidationSettingType
  permits VulkanValidationCheckCommandBuffer,
  VulkanValidationCheckImageLayout,
  VulkanValidationCheckObjectInUse,
  VulkanValidationCheckQuery,
  VulkanValidationCheckShaders,
  VulkanValidationCheckShadersCaching,
  VulkanValidationDebugAction,
  VulkanValidationDebugDisableSPIRVVal,
  VulkanValidationDisplayApplicationName,
  VulkanValidationEnableMessageLimit,
  VulkanValidationFineGrainedLocking,
  VulkanValidationGPUAVBufferAddressOOB,
  VulkanValidationGPUAVBufferCopies,
  VulkanValidationGPUAVBuffersValidation,
  VulkanValidationGPUAVCacheInstrumentedShaders,
  VulkanValidationGPUAVDebugDumpInstrumentedShaders,
  VulkanValidationGPUAVDebugMaxInstrumentedCount,
  VulkanValidationGPUAVDebugPrintInstrumentationInfo,
  VulkanValidationGPUAVDebugValidateInstrumentedShaders,
  VulkanValidationGPUAVDescriptorChecks,
  VulkanValidationGPUAVIndirectDispatchesBuffers,
  VulkanValidationGPUAVIndirectDrawsBuffers,
  VulkanValidationGPUAVIndirectTraceRaysBuffers,
  VulkanValidationGPUAVMaxBufferDeviceAddresses,
  VulkanValidationGPUAVReserveBindingSlot,
  VulkanValidationGPUAVSelectInstrumentedShaders,
  VulkanValidationGPUAVShaderInstrumentation,
  VulkanValidationGPUAVVMALinearOutput,
  VulkanValidationGPUAVValidateRayQuery,
  VulkanValidationGPUAVWarnOnRobustOOB,
  VulkanValidationLogFilename,
  VulkanValidationMaxDuplicatedMessages,
  VulkanValidationObjectLifetime,
  VulkanValidationPrintfBufferSize,
  VulkanValidationPrintfToStdout,
  VulkanValidationPrintfVerbose,
  VulkanValidationStatelessParam,
  VulkanValidationSyncvalShaderAccessesHeuristic,
  VulkanValidationSyncvalSubmitTimeValidation,
  VulkanValidationThreadSafety,
  VulkanValidationUniqueHandles,
  VulkanValidationValidateBestPractices,
  VulkanValidationValidateBestPracticesAMD,
  VulkanValidationValidateBestPracticesARM,
  VulkanValidationValidateBestPracticesIMG,
  VulkanValidationValidateBestPracticesNVIDIA,
  VulkanValidationValidateCore,
  VulkanValidationValidateGPUBased,
  VulkanValidationValidateSync
{
  /**
   * @return This setting as a layer setting
   */

  VulkanLayerSettingType toSetting();
}
