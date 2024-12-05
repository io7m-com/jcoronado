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

import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanBufferType;
import com.io7m.jcoronado.api.VulkanBufferViewCreateInfo;
import com.io7m.jcoronado.api.VulkanBufferViewType;
import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanCommandBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanCommandPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandPoolResetFlag;
import com.io7m.jcoronado.api.VulkanCommandPoolType;
import com.io7m.jcoronado.api.VulkanComputePipelineCreateInfo;
import com.io7m.jcoronado.api.VulkanCopyDescriptorSet;
import com.io7m.jcoronado.api.VulkanDebuggingType;
import com.io7m.jcoronado.api.VulkanDescriptorPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanDescriptorPoolResetFlag;
import com.io7m.jcoronado.api.VulkanDescriptorPoolType;
import com.io7m.jcoronado.api.VulkanDescriptorSetAllocateInfo;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutType;
import com.io7m.jcoronado.api.VulkanDescriptorSetType;
import com.io7m.jcoronado.api.VulkanDestroyedException;
import com.io7m.jcoronado.api.VulkanDeviceMemoryType;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanEventCreateInfo;
import com.io7m.jcoronado.api.VulkanEventType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionType;
import com.io7m.jcoronado.api.VulkanExternallySynchronizedType;
import com.io7m.jcoronado.api.VulkanFenceCreateInfo;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanFramebufferCreateInfo;
import com.io7m.jcoronado.api.VulkanFramebufferType;
import com.io7m.jcoronado.api.VulkanGraphicsPipelineCreateInfo;
import com.io7m.jcoronado.api.VulkanImageCreateInfo;
import com.io7m.jcoronado.api.VulkanImageSubresource;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import com.io7m.jcoronado.api.VulkanImageViewType;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanMappedMemoryRange;
import com.io7m.jcoronado.api.VulkanMappedMemoryType;
import com.io7m.jcoronado.api.VulkanMemoryAllocateInfo;
import com.io7m.jcoronado.api.VulkanMemoryMapFlag;
import com.io7m.jcoronado.api.VulkanMemoryRequirements;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanPipelineCacheCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineCacheType;
import com.io7m.jcoronado.api.VulkanPipelineLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineLayoutType;
import com.io7m.jcoronado.api.VulkanPipelineType;
import com.io7m.jcoronado.api.VulkanQueryPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanQueryPoolType;
import com.io7m.jcoronado.api.VulkanQueueIndex;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanRenderPassCreateInfo;
import com.io7m.jcoronado.api.VulkanRenderPassType;
import com.io7m.jcoronado.api.VulkanSamplerCreateInfo;
import com.io7m.jcoronado.api.VulkanSamplerType;
import com.io7m.jcoronado.api.VulkanSemaphoreCreateInfo;
import com.io7m.jcoronado.api.VulkanSemaphoreType;
import com.io7m.jcoronado.api.VulkanShaderModuleCreateInfo;
import com.io7m.jcoronado.api.VulkanShaderModuleType;
import com.io7m.jcoronado.api.VulkanSubresourceLayout;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import com.io7m.jcoronado.api.VulkanWriteDescriptorSet;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsType;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK13;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkMemoryRequirements;
import org.lwjgl.vulkan.VkQueue;
import org.lwjgl.vulkan.VkSubresourceLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.io7m.jcoronado.api.VulkanLogicalDeviceType.VulkanPipelineCacheDataResult.VK_PIPELINE_CACHE_INCOMPLETE;
import static com.io7m.jcoronado.api.VulkanLogicalDeviceType.VulkanPipelineCacheDataResult.VK_PIPELINE_CACHE_SUCCESS;
import static com.io7m.jcoronado.api.VulkanLogicalDeviceType.VulkanWaitStatus.VK_WAIT_SUCCEEDED;
import static com.io7m.jcoronado.api.VulkanLogicalDeviceType.VulkanWaitStatus.VK_WAIT_TIMED_OUT;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLClassChecks.checkInstanceOf;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLHandle.Ownership.USER_OWNED;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLHandle.Ownership.VULKAN_OWNED;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLScalarArrays.packLongs;
import static org.lwjgl.vulkan.VK10.VK_EVENT_RESET;
import static org.lwjgl.vulkan.VK10.VK_EVENT_SET;
import static org.lwjgl.vulkan.VK10.VK_INCOMPLETE;
import static org.lwjgl.vulkan.VK10.VK_NOT_READY;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;
import static org.lwjgl.vulkan.VK10.VK_TIMEOUT;

/**
 * LWJGL {@link VkDevice}
 */

public final class VulkanLWJGLLogicalDevice
  extends VulkanLWJGLHandle implements VulkanLogicalDeviceType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLLogicalDevice.class);

  private final VulkanLWJGLPhysicalDevice physical_device;
  private final VkDevice device;
  private final MemoryStack stack_initial;
  private final VulkanLogicalDeviceCreateInfo creation;
  private final List<VulkanLWJGLQueue> queues;
  private final List<VulkanQueueType> queues_read;
  private final Map<String, VulkanExtensionType> extensions_enabled_read_only;
  private final PointerBuffer pointer_buffer;
  private final VulkanDebuggingType debugging;

  VulkanLWJGLLogicalDevice(
    final Map<String, VulkanExtensionType> inExtensionsEnabled,
    final VulkanLWJGLPhysicalDevice inPhysicalDevice,
    final VkDevice inDevice,
    final VulkanLogicalDeviceCreateInfo inCreation,
    final VulkanLWJGLHostAllocatorProxy inHostAllocatorProxy)
    throws VulkanException
  {
    super(USER_OWNED, inHostAllocatorProxy, inDevice.address());

    this.extensions_enabled_read_only =
      Collections.unmodifiableMap(
        Objects.requireNonNull(inExtensionsEnabled, "in_extensions"));
    this.physical_device =
      Objects.requireNonNull(inPhysicalDevice, "inPhysicalDevice");
    this.device =
      Objects.requireNonNull(inDevice, "inDevice");
    this.creation =
      Objects.requireNonNull(inCreation, "inCreation");
    this.queues =
      new ArrayList<>(32);
    this.queues_read =
      Collections.unmodifiableList(this.queues);
    this.stack_initial =
      MemoryStack.create();
    this.pointer_buffer =
      this.stack_initial.mallocPointer(1);

    this.initializeQueues();
    this.debugging = this.initializeDebugging();
  }

  private VulkanDebuggingType initializeDebugging()
  {
    final Optional<VulkanDebugUtilsType> debug;
    try {
      debug =
        this.physical_device.instance()
          .findEnabledExtension(
            "VK_EXT_debug_utils",
            VulkanDebugUtilsType.class
          );
    } catch (final VulkanException e) {
      LOG.warn("Debugging could not be enabled: ", e);
      return new VulkanLWJGLDebuggingNoOp();
    }

    if (debug.isPresent()) {
      LOG.debug("VK_EXT_debug_utils is enabled, debugging is enabled.");
      return new VulkanLWJGLDebugging(this, debug.get());
    }

    LOG.debug("VK_EXT_debug_utils is not present, debugging is no-op.");
    return new VulkanLWJGLDebuggingNoOp();
  }

  @SuppressWarnings("unchecked")
  private static List<VulkanPipelineType> castPipelines(
    final ArrayList<VulkanLWJGLPipeline> result_pipelines)
  {
    return (List<VulkanPipelineType>) (Object) result_pipelines;
  }

  private static long mapPipelineCacheOptional(
    final Optional<VulkanPipelineCacheType> pipeline_cache)
    throws VulkanIncompatibleClassException
  {
    if (pipeline_cache.isPresent()) {
      return checkInstanceOf(
        pipeline_cache.get(),
        VulkanLWJGLPipelineCache.class).handle();
    }
    return 0L;
  }

  /**
   * @return The underlying device
   */

  public VkDevice device()
  {
    return this.device;
  }

  private void initializeQueues()
    throws VulkanException
  {
    try (var stack = this.stack_initial.push()) {
      final var queueRequests =
        this.creation.queueCreateInfos();
      final var families =
        this.physical_device.queueFamilies();

      final var queueBuffer = stack.mallocPointer(1);
      for (final var queue_info : queueRequests) {
        final var priorities =
          queue_info.queuePriorities();

        for (var queueIndex = 0; queueIndex < priorities.size(); ++queueIndex) {
          final var queueFamilyIndex =
            queue_info.queueFamilyIndex();
          final var family =
            families.get(queueFamilyIndex);

          if (LOG.isTraceEnabled()) {
            LOG.trace(
              "Requesting queue {} of family {}",
              Integer.valueOf(queueIndex),
              queueFamilyIndex
            );
          }

          VK10.vkGetDeviceQueue(
            this.device,
            queueFamilyIndex.value(),
            queueIndex,
            queueBuffer);

          final var queue = new VkQueue(queueBuffer.get(0), this.device);
          this.queues.add(
            new VulkanLWJGLQueue(
              queue,
              family,
              new VulkanQueueIndex(queueIndex),
              this.hostAllocatorProxy())
          );
        }
      }
    }
  }

  @Override
  public VulkanDebuggingType debugging()
  {
    return this.debugging;
  }

  @Override
  public VulkanPhysicalDeviceType physicalDevice()
  {
    return this.physical_device;
  }

  @Override
  public List<VulkanQueueType> queues()
    throws VulkanDestroyedException
  {
    this.checkNotClosed();
    return this.queues_read;
  }

  @Override
  public Map<String, VulkanExtensionType> enabledExtensions()
    throws VulkanDestroyedException
  {
    this.checkNotClosed();
    return this.extensions_enabled_read_only;
  }

  @Override
  public VulkanShaderModuleType createShaderModule(
    final VulkanShaderModuleCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    if (LOG.isDebugEnabled()) {
      LOG.debug(
        "Creating shader module: {} octets",
        Long.valueOf(create_info.size()));
    }

    try (var stack = this.stack_initial.push()) {
      final var results = new long[1];
      final var proxy = this.hostAllocatorProxy();

      VulkanChecks.checkReturnCode(
        VK10.vkCreateShaderModule(
          this.device,
          VulkanLWJGLShaderModules.pack(stack, create_info),
          proxy.callbackBuffer(),
          results),
        "vkCreateShaderModule");

      final var shader_module = results[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Created shader module: 0x{}",
          Long.toUnsignedString(shader_module, 16));
      }

      return new VulkanLWJGLShaderModule(
        USER_OWNED,
        this.device,
        shader_module,
        proxy);
    }
  }

  @Override
  public VulkanSamplerType createSampler(
    final VulkanSamplerCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var handle = new long[1];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK10.vkCreateSampler(
          this.device,
          VulkanLWJGLSamplerCreateInfos.pack(stack, create_info),
          proxy.callbackBuffer(),
          handle),
        "vkCreateSampler");

      final var vk_handle = handle[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Created sampler: 0x{}",
          Long.toUnsignedString(vk_handle, 16));
      }

      return new VulkanLWJGLSampler(USER_OWNED, this.device, vk_handle, proxy);
    }
  }

  @Override
  public VulkanBufferViewType createBufferView(
    final VulkanBufferViewCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    final var buffer = checkInstanceOf(info.buffer(), VulkanLWJGLBuffer.class);

    try (var stack = this.stack_initial.push()) {
      final var handles = new long[1];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK10.vkCreateBufferView(
          this.device,
          VulkanLWJGLBufferViewCreateInfos.pack(stack, info),
          proxy.callbackBuffer(),
          handles),
        "vkCreateBufferView");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Created buffer view: 0x{}",
          Long.toUnsignedString(handle, 16));
      }

      return new VulkanLWJGLBufferView(
        USER_OWNED,
        this.device,
        handle,
        buffer,
        proxy);
    }
  }

  @Override
  public VulkanImageViewType createImageView(
    final VulkanImageViewCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    final var image = checkInstanceOf(info.image(), VulkanLWJGLImage.class);

    try (var stack = this.stack_initial.push()) {
      final var view = new long[1];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK10.vkCreateImageView(
          this.device,
          VulkanLWJGLImageViews.packImageViewCreateInfo(stack, info, image),
          proxy.callbackBuffer(),
          view),
        "vkCreateImageView");

      final var view_handle = view[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Created image view: 0x{}",
          Long.toUnsignedString(view_handle, 16));
      }

      return new VulkanLWJGLImageView(this.device, view_handle, image, proxy);
    }
  }

  @Override
  public void flushMappedMemoryRanges(
    final List<VulkanMappedMemoryRange> ranges)
    throws VulkanException
  {
    Objects.requireNonNull(ranges, "ranges");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      VulkanChecks.checkReturnCode(
        VK10.vkFlushMappedMemoryRanges(
          this.device,
          VulkanLWJGLMappedMemoryRanges.packList(stack, ranges)),
        "vkFlushMappedMemoryRanges");
    }
  }

  @Override
  public VulkanPipelineLayoutType createPipelineLayout(
    final VulkanPipelineLayoutCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var proxy = this.hostAllocatorProxy();
      final var layout = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreatePipelineLayout(
          this.device,
          VulkanLWJGLPipelineLayouts.packPipelineLayoutCreateInfo(stack, info),
          proxy.callbackBuffer(),
          layout),
        "vkCreatePipelineLayout");

      final var layout_handle = layout[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Created pipeline layout: 0x{}",
          Long.toUnsignedString(layout_handle, 16));
      }

      return new VulkanLWJGLPipelineLayout(
        USER_OWNED,
        this.device,
        layout_handle,
        proxy);
    }
  }

  @Override
  public VulkanDescriptorSetLayoutType createDescriptorSetLayout(
    final VulkanDescriptorSetLayoutCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var proxy = this.hostAllocatorProxy();
      final var layout = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateDescriptorSetLayout(
          this.device,
          VulkanLWJGLDescriptorSetLayouts.packDescriptorSetLayoutCreateInfo(
            stack,
            info),
          proxy.callbackBuffer(),
          layout),
        "vkCreateDescriptorSetLayout");

      final var layout_handle = layout[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Created descriptor set layout: 0x{}",
          Long.toUnsignedString(layout_handle, 16));
      }

      return new VulkanLWJGLDescriptorSetLayout(
        this.device,
        layout_handle,
        proxy);
    }
  }

  @Override
  public VulkanDescriptorPoolType createDescriptorPool(
    final VulkanDescriptorPoolCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var layout = new long[1];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK10.vkCreateDescriptorPool(
          this.device,
          VulkanLWJGLDescriptorPoolCreateInfos.packDescriptorPoolCreateInfo(
            stack,
            info),
          proxy.callbackBuffer(),
          layout),
        "vkCreateDescriptorPool");

      final var layout_handle = layout[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Created descriptor pool: 0x{}",
          Long.toUnsignedString(layout_handle, 16));
      }

      return new VulkanLWJGLDescriptorPool(
        USER_OWNED,
        this.device,
        layout_handle,
        proxy);
    }
  }

  @Override
  public List<VulkanDescriptorSetType> allocateDescriptorSets(
    final VulkanDescriptorSetAllocateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var count = info.setLayouts().size();
      final var handles = stack.mallocLong(count);

      VulkanChecks.checkReturnCode(
        VK10.vkAllocateDescriptorSets(
          this.device,
          VulkanLWJGLDescriptorSetAllocateInfos.pack(stack, info),
          handles),
        "vkAllocateDescriptorSets");

      if (LOG.isTraceEnabled()) {
        LOG.trace("Allocated {} descriptor sets", Integer.valueOf(count));
      }

      final ArrayList<VulkanDescriptorSetType> results = new ArrayList<>(count);
      for (var index = 0; index < count; ++index) {
        results.add(new VulkanLWJGLDescriptorSet(
          handles.get(index), this.hostAllocatorProxy()));
      }
      return results;
    }
  }

  @Override
  public void updateDescriptorSets(
    final List<VulkanWriteDescriptorSet> descriptor_writes,
    final List<VulkanCopyDescriptorSet> descriptor_copies)
    throws VulkanException
  {
    Objects.requireNonNull(descriptor_writes, "descriptor_writes");
    Objects.requireNonNull(descriptor_copies, "descriptor_copies");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var writes =
        VulkanLWJGLWriteDescriptorSets.packList(stack, descriptor_writes);
      final var copies =
        VulkanLWJGLCopyDescriptorSets.packList(stack, descriptor_copies);

      VK10.vkUpdateDescriptorSets(this.device, writes, copies);
    }
  }

  @Override
  public VulkanRenderPassType createRenderPass(
    final VulkanRenderPassCreateInfo render_pass_create_info)
    throws VulkanException
  {
    Objects.requireNonNull(render_pass_create_info, "render_pass_create_info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var pass = new long[1];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK13.vkCreateRenderPass2(
          this.device,
          VulkanLWJGLRenderPasses.packRenderPassCreateInfo(
            stack,
            render_pass_create_info),
          proxy.callbackBuffer(),
          pass
        ),
        "vkCreateRenderPass"
      );

      final var pass_handle = pass[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Created render pass: 0x{}",
          Long.toUnsignedString(pass_handle, 16));
      }

      return new VulkanLWJGLRenderPass(
        USER_OWNED,
        this.device,
        pass_handle,
        proxy);
    }
  }

  @Override
  public VulkanPipelineCacheType createPipelineCache(
    final VulkanPipelineCacheCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var handles = new long[1];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK10.vkCreatePipelineCache(
          this.device,
          VulkanLWJGLPipelineCacheCreateInfos.pack(stack, create_info),
          proxy.callbackBuffer(),
          handles),
        "vkCreatePipelineCache");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Created pipeline cache: 0x{}",
          Long.toUnsignedString(handle, 16));
      }

      return new VulkanLWJGLPipelineCache(this.device, handle, proxy);
    }
  }

  @Override
  public long getPipelineCacheDataSize(
    final VulkanPipelineCacheType pipeline_cache)
    throws VulkanException
  {
    Objects.requireNonNull(pipeline_cache, "pipeline_cache");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var ptr = stack.mallocPointer(1);

      VulkanChecks.checkReturnCode(
        VK10.vkGetPipelineCacheData(
          this.device,
          checkInstanceOf(
            pipeline_cache,
            VulkanLWJGLPipelineCache.class).handle(),
          ptr,
          null),
        "vkGetPipelineCacheData");

      return ptr.get(0);
    }
  }

  @Override
  public VulkanMemoryRequirements getImageMemoryRequirements(
    final VulkanImageType image)
    throws VulkanException
  {
    Objects.requireNonNull(image, "image");

    this.checkNotClosed();

    final var cimage =
      checkInstanceOf(image, VulkanLWJGLImage.class);

    try (var stack = this.stack_initial.push()) {
      final var info = VkMemoryRequirements.calloc(stack);

      VK10.vkGetImageMemoryRequirements(this.device, cimage.handle(), info);

      return VulkanMemoryRequirements.builder()
        .setAlignment(info.alignment())
        .setMemoryTypeBits(info.memoryTypeBits())
        .setSize(info.size())
        .build();
    }
  }

  @Override
  public VulkanPipelineCacheDataResult getPipelineCacheData(
    final VulkanPipelineCacheType pipeline_cache,
    final ByteBuffer data)
    throws VulkanException
  {
    Objects.requireNonNull(pipeline_cache, "pipeline_cache");
    Objects.requireNonNull(data, "data");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var ptr = stack.mallocPointer(1);
      ptr.put(0, Integer.toUnsignedLong(data.capacity()));

      final var result =
        VK10.vkGetPipelineCacheData(
          this.device,
          checkInstanceOf(
            pipeline_cache,
            VulkanLWJGLPipelineCache.class).handle(),
          ptr,
          data);

      if (result == VK_SUCCESS) {
        return VK_PIPELINE_CACHE_SUCCESS;
      }
      if (result == VK_INCOMPLETE) {
        return VK_PIPELINE_CACHE_INCOMPLETE;
      }
      throw VulkanChecks.failed(result, "vkGetPipelineCacheData");
    }
  }

  @Override
  public List<VulkanPipelineType> createGraphicsPipelines(
    final Optional<VulkanPipelineCacheType> pipeline_cache,
    final List<VulkanGraphicsPipelineCreateInfo> pipeline_infos)
    throws VulkanException
  {
    Objects.requireNonNull(pipeline_cache, "pipeline_cache");
    Objects.requireNonNull(pipeline_infos, "pipeline_infos");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var pipes = new long[pipeline_infos.size()];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK10.vkCreateGraphicsPipelines(
          this.device,
          mapPipelineCacheOptional(pipeline_cache),
          VulkanLWJGLGraphicsPipelineCreateInfos.pack(stack, pipeline_infos),
          proxy.callbackBuffer(),
          pipes),
        "vkCreateGraphicsPipelines");

      final ArrayList<VulkanLWJGLPipeline> result_pipelines =
        new ArrayList<>(pipeline_infos.size());

      for (var index = 0; index < pipeline_infos.size(); ++index) {
        final var pipe = pipes[index];
        if (LOG.isTraceEnabled()) {
          LOG.trace("Created pipeline: 0x{}", Long.toUnsignedString(pipe, 16));
        }
        result_pipelines.add(new VulkanLWJGLPipeline(
          USER_OWNED,
          this.device,
          pipe,
          proxy));
      }

      return castPipelines(result_pipelines);
    }
  }

  @Override
  public VulkanQueryPoolType createQueryPool(final VulkanQueryPoolCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var pool = new long[1];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK10.vkCreateQueryPool(
          this.device,
          VulkanLWJGLQueryPoolCreateInfos.pack(stack, create_info),
          proxy.callbackBuffer(),
          pool),
        "vkCreateQueryPool");

      final var pool_handle = pool[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Created query pool: 0x{}",
          Long.toUnsignedString(pool_handle, 16));
      }

      return new VulkanLWJGLQueryPool(
        USER_OWNED,
        this.device,
        pool_handle,
        proxy);
    }
  }

  @Override
  public VulkanFramebufferType createFramebuffer(
    final VulkanFramebufferCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    try {
      final var views =
        create_info.attachments()
          .stream()
          .map(view -> {
            try {
              return checkInstanceOf(view, VulkanLWJGLImageView.class);
            } catch (final VulkanIncompatibleClassException e) {
              throw new VulkanUncheckedException(e);
            }
          })
          .collect(Collectors.toList());

      final var cpass = checkInstanceOf(
        create_info.renderPass(),
        VulkanLWJGLRenderPass.class);

      try (var stack = this.stack_initial.push()) {
        final var framebuffers = new long[1];
        final var proxy = this.hostAllocatorProxy();
        VulkanChecks.checkReturnCode(
          VK10.vkCreateFramebuffer(
            this.device,
            VulkanLWJGLFramebufferCreateInfos.pack(
              stack,
              create_info,
              views,
              cpass),
            proxy.callbackBuffer(),
            framebuffers),
          "vkCreateFramebuffer");

        final var handle = framebuffers[0];
        if (LOG.isTraceEnabled()) {
          LOG.trace(
            "Created framebuffer: 0x{}",
            Long.toUnsignedString(handle, 16));
        }

        return new VulkanLWJGLFramebuffer(
          USER_OWNED,
          this.device,
          handle,
          proxy);
      }
    } catch (final VulkanUncheckedException e) {
      throw e.getCause();
    }
  }

  @Override
  public VulkanCommandPoolType createCommandPool(
    final VulkanCommandPoolCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var handles = new long[1];
      final var proxy = this.hostAllocatorProxy();

      VulkanChecks.checkReturnCode(
        VK10.vkCreateCommandPool(
          this.device,
          VulkanLWJGLCommandPoolCreateInfos.pack(stack, create_info),
          proxy.callbackBuffer(),
          handles),
        "vkCreateCommandPool");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Created command pool: 0x{}",
          Long.toUnsignedString(handle, 16));
      }

      return new VulkanLWJGLCommandPool(USER_OWNED, this.device, handle, proxy);
    }
  }

  @Override
  public List<VulkanCommandBufferType> createCommandBuffers(
    final VulkanCommandBufferCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    final var cpool = checkInstanceOf(
      create_info.pool(),
      VulkanLWJGLCommandPool.class);
    try (var stack = this.stack_initial.push()) {
      final var count = create_info.count();
      final var buffers = stack.mallocPointer(count);

      VulkanChecks.checkReturnCode(
        VK10.vkAllocateCommandBuffers(
          this.device,
          VulkanLWJGLCommandBufferCreateInfos.pack(stack, create_info, cpool),
          buffers),
        "vkAllocateCommandBuffers");

      if (LOG.isTraceEnabled()) {
        for (var index = 0; index < count; ++index) {
          LOG.trace(
            "Created command buffer: 0x{}",
            Long.toUnsignedString(buffers.get(index), 16));
        }
      }

      final var proxy = this.hostAllocatorProxy();
      final List<VulkanCommandBufferType> results = new ArrayList<>(count);
      for (var index = 0; index < count; ++index) {
        final var handle = new VkCommandBuffer(buffers.get(index), this.device);
        final var buffer = new VulkanLWJGLCommandBuffer(
          VULKAN_OWNED,
          handle,
          proxy);
        results.add(buffer);
      }

      return results;
    }
  }

  @Override
  public VulkanSemaphoreType createSemaphore(
    final VulkanSemaphoreCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var proxy = this.hostAllocatorProxy();
      final var handles = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateSemaphore(
          this.device,
          VulkanLWJGLSemaphoreCreateInfos.pack(stack, create_info),
          proxy.callbackBuffer(),
          handles),
        "vkCreateSemaphore");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("Created semaphore: 0x{}", Long.toUnsignedString(handle, 16));
      }
      return new VulkanLWJGLSemaphore(USER_OWNED, this.device, handle, proxy);
    }
  }

  @Override
  public VulkanFenceType createFence(
    final VulkanFenceCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var fences = new long[1];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK10.vkCreateFence(
          this.device,
          VulkanLWJGLFenceCreateInfos.pack(stack, create_info),
          proxy.callbackBuffer(),
          fences),
        "vkCreateFence");

      final var handle = fences[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("Created fence: 0x{}", Long.toUnsignedString(handle, 16));
      }
      return new VulkanLWJGLFence(USER_OWNED, this.device, handle, proxy);
    }
  }

  @Override
  public VulkanEventType createEvent(
    final VulkanEventCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var handles = new long[1];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK10.vkCreateEvent(
          this.device,
          VulkanLWJGLEventCreateInfos.pack(stack, create_info),
          proxy.callbackBuffer(),
          handles),
        "vkCreateEvent");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("Created event: 0x{}", Long.toUnsignedString(handle, 16));
      }
      return new VulkanLWJGLEvent(USER_OWNED, this.device, handle, proxy);
    }
  }

  @Override
  public void resetFences(final List<VulkanFenceType> fences)
    throws VulkanException
  {
    Objects.requireNonNull(fences, "fences");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var array = stack.mallocLong(fences.size());
      for (var index = 0; index < fences.size(); ++index) {
        final var fence = checkInstanceOf(
          fences.get(index),
          VulkanLWJGLFence.class);
        array.put(index, fence.handle());
      }

      VulkanChecks.checkReturnCode(
        VK10.vkResetFences(this.device, array),
        "vkResetFences");
    }
  }

  @Override
  public void resetCommandPool(
    @VulkanExternallySynchronizedType final VulkanCommandPoolType pool,
    final Set<VulkanCommandPoolResetFlag> flags)
    throws VulkanException
  {
    Objects.requireNonNull(pool, "pool");
    Objects.requireNonNull(flags, "flags");

    this.checkNotClosed();

    final var cpool = checkInstanceOf(pool, VulkanLWJGLCommandPool.class);
    VulkanChecks.checkReturnCode(
      VK10.vkResetCommandPool(
        this.device,
        cpool.handle(),
        VulkanEnumMaps.packValues(flags)),
      "vkResetCommandPool");
  }

  @Override
  public void resetDescriptorPool(
    @VulkanExternallySynchronizedType final VulkanDescriptorPoolType pool,
    final Set<VulkanDescriptorPoolResetFlag> flags)
    throws VulkanException
  {
    Objects.requireNonNull(pool, "pool");
    Objects.requireNonNull(flags, "flags");

    this.checkNotClosed();

    final var cpool = checkInstanceOf(pool, VulkanLWJGLCommandPool.class);
    VulkanChecks.checkReturnCode(
      VK10.vkResetDescriptorPool(
        this.device,
        cpool.handle(),
        VulkanEnumMaps.packValues(flags)),
      "vkResetDescriptorPool");
  }

  @Override
  public void waitIdle()
    throws VulkanException
  {
    this.checkNotClosed();

    VulkanChecks.checkReturnCode(
      VK10.vkDeviceWaitIdle(this.device),
      "vkDeviceWaitIdle");
  }

  @Override
  public VulkanWaitStatus waitForFences(
    final List<VulkanFenceType> fences,
    final boolean wait_all,
    final long timeout_nanos)
    throws VulkanException
  {
    Objects.requireNonNull(fences, "fences");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var result =
        VK10.vkWaitForFences(
          this.device,
          packLongs(
            stack,
            fences,
            f -> checkInstanceOf(f, VulkanLWJGLFence.class).handle()),
          wait_all,
          timeout_nanos);

      if (result == VK_SUCCESS) {
        return VK_WAIT_SUCCEEDED;
      }
      if (result == VK_TIMEOUT) {
        return VK_WAIT_TIMED_OUT;
      }
      throw VulkanChecks.failed(result, "vkWaitForFences");
    }
  }

  @Override
  public VulkanBufferType createBuffer(
    final VulkanBufferCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var info = VulkanLWJGLBufferCreateInfos.packInfo(
        stack,
        create_info);
      final var handles = new long[1];
      final var proxy = this.hostAllocatorProxy();

      VulkanChecks.checkReturnCode(
        VK10.vkCreateBuffer(
          this.device,
          info,
          proxy.callbackBuffer(),
          handles),
        "vkCreateBuffer");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("Created buffer: 0x{}", Long.toUnsignedString(handle, 16));
      }

      final Runnable deallocate = () -> this.destroyBuffer(proxy, handle);
      return new VulkanLWJGLBuffer(
        USER_OWNED,
        handle,
        deallocate,
        proxy);
    }
  }

  @Override
  public VulkanImageType createImage(
    final VulkanImageCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var handles = new long[1];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK10.vkCreateImage(
          this.device,
          VulkanLWJGLImageCreateInfos.pack(stack, create_info),
          proxy.callbackBuffer(),
          handles),
        "vkCreateImage");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("Created image: 0x{}", Long.toUnsignedString(handle, 16));
      }

      final Runnable deallocate = () -> this.destroyImage(proxy, handle);
      return new VulkanLWJGLImage(
        USER_OWNED,
        handle,
        deallocate,
        proxy);
    }
  }

  private void destroyImage(
    final VulkanLWJGLHostAllocatorProxy proxy,
    final long handle)
  {
    if (LOG.isTraceEnabled()) {
      LOG.trace("VK10.vkDestroyImage: 0x{}", Long.toUnsignedString(handle, 16));
    }

    VK10.vkDestroyImage(this.device, handle, proxy.callbackBuffer());
  }

  private void destroyBuffer(
    final VulkanLWJGLHostAllocatorProxy proxy,
    final long handle)
  {
    if (LOG.isTraceEnabled()) {
      LOG.trace(
        "VK10.vkDestroyBuffer: 0x{}",
        Long.toUnsignedString(handle, 16));
    }

    VK10.vkDestroyBuffer(this.device, handle, proxy.callbackBuffer());
  }

  @Override
  public VulkanMemoryRequirements getBufferMemoryRequirements(
    final VulkanBufferType buffer)
    throws VulkanException
  {
    Objects.requireNonNull(buffer, "buffer");

    this.checkNotClosed();

    final var cbuffer = checkInstanceOf(buffer, VulkanLWJGLBuffer.class);

    try (var stack = this.stack_initial.push()) {
      final var info = VkMemoryRequirements.calloc(stack);

      VK10.vkGetBufferMemoryRequirements(this.device, cbuffer.handle(), info);

      return VulkanMemoryRequirements.builder()
        .setAlignment(info.alignment())
        .setMemoryTypeBits(info.memoryTypeBits())
        .setSize(info.size())
        .build();
    }
  }

  @Override
  public VulkanDeviceMemoryType allocateMemory(
    final VulkanMemoryAllocateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var handles = new long[1];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK10.vkAllocateMemory(
          this.device,
          VulkanLWJGLMemoryAllocateInfos.pack(stack, info),
          proxy.callbackBuffer(),
          handles),
        "vkAllocateMemory");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Allocated device memory: 0x{}",
          Long.toUnsignedString(handle, 16)
        );
      }

      return new VulkanLWJGLDeviceMemory(
        USER_OWNED,
        this.device,
        handle,
        proxy);
    }
  }

  @Override
  public void bindBufferMemory(
    final VulkanBufferType buffer,
    final VulkanDeviceMemoryType device_memory,
    final long offset)
    throws VulkanException
  {
    Objects.requireNonNull(buffer, "buffer");
    Objects.requireNonNull(device_memory, "device_memory");

    this.checkNotClosed();

    final var cbuffer =
      checkInstanceOf(buffer, VulkanLWJGLBuffer.class);
    final var cmemory =
      checkInstanceOf(device_memory, VulkanLWJGLDeviceMemory.class);

    VulkanChecks.checkReturnCode(
      VK10.vkBindBufferMemory(
        this.device,
        cbuffer.handle(),
        cmemory.handle(),
        offset),
      "vkBindBufferMemory");
  }

  @Override
  public void bindImageMemory(
    final VulkanImageType image,
    final VulkanDeviceMemoryType device_memory,
    final long offset)
    throws VulkanException
  {
    Objects.requireNonNull(image, "image");
    Objects.requireNonNull(device_memory, "device_memory");

    this.checkNotClosed();

    final var cimage = checkInstanceOf(image, VulkanLWJGLImage.class);
    final var cmemory = checkInstanceOf(
      device_memory,
      VulkanLWJGLDeviceMemory.class);

    VulkanChecks.checkReturnCode(
      VK10.vkBindImageMemory(
        this.device,
        cimage.handle(),
        cmemory.handle(),
        offset),
      "vkBindImageMemory");
  }

  @Override
  public VulkanMappedMemoryType mapMemory(
    final VulkanDeviceMemoryType memory,
    final long offset,
    final long size,
    final Set<VulkanMemoryMapFlag> flags)
    throws VulkanException
  {
    Objects.requireNonNull(memory, "memory");
    Objects.requireNonNull(flags, "flags");

    this.checkNotClosed();

    final var cmemory = checkInstanceOf(memory, VulkanLWJGLDeviceMemory.class);
    final var int_flags = VulkanEnumMaps.packValues(flags);
    final var int_handle = cmemory.handle();

    VulkanChecks.checkReturnCode(
      VK10.vkMapMemory(
        this.device,
        int_handle,
        offset,
        size,
        int_flags,
        this.pointer_buffer),
      "vkMapMemory");

    final var address = this.pointer_buffer.get(0);
    if (LOG.isTraceEnabled()) {
      LOG.trace(
        "Mapped memory: 0x{} -> 0x{}",
        Long.toUnsignedString(int_handle, 16),
        Long.toUnsignedString(address, 16));
    }

    final VulkanLWJGLFlushRangedFunctionType flush = (map_mem, map_off, map_size) ->
      this.flushMappedMemoryRange(VulkanMappedMemoryRange.of(
        map_mem,
        map_off,
        map_size));

    return new VulkanLWJGLMappedMemory(
      this.device,
      cmemory,
      flush,
      address,
      size);
  }

  @Override
  public void mergePipelineCaches(
    final List<VulkanPipelineCacheType> caches,
    final VulkanPipelineCacheType output)
    throws VulkanException
  {
    Objects.requireNonNull(caches, "caches");
    Objects.requireNonNull(output, "output");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      VulkanChecks.checkReturnCode(
        VK10.vkMergePipelineCaches(
          this.device,
          checkInstanceOf(output, VulkanLWJGLPipelineCache.class).handle(),
          packLongs(
            stack,
            caches,
            c -> checkInstanceOf(c, VulkanLWJGLPipelineCache.class).handle())),
        "vkMergePipelineCaches");
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void setEvent(
    final VulkanEventType event)
    throws VulkanException
  {
    Objects.requireNonNull(event, "event");

    this.checkNotClosed();

    VK10.vkSetEvent(
      this.device,
      checkInstanceOf(event, VulkanLWJGLEvent.class).handle());
  }

  @Override
  public @VulkanExternallySynchronizedType void resetEvent(
    final VulkanEventType event)
    throws VulkanException
  {
    Objects.requireNonNull(event, "event");

    VK10.vkResetEvent(
      this.device,
      checkInstanceOf(event, VulkanLWJGLEvent.class).handle());
  }

  @Override
  public @VulkanExternallySynchronizedType VulkanEventStatus getEventStatus(
    final VulkanEventType event)
    throws VulkanException
  {
    Objects.requireNonNull(event, "event");

    this.checkNotClosed();

    final var result =
      VK10.vkGetEventStatus(
        this.device,
        checkInstanceOf(
          event,
          VulkanLWJGLEvent.class).handle());

    if (result == VK_EVENT_SET) {
      return VulkanEventStatus.VK_EVENT_SET;
    }
    if (result == VK_EVENT_RESET) {
      return VulkanEventStatus.VK_EVENT_RESET;
    }

    throw VulkanChecks.failed(result, "vkGetEventStatus");
  }

  @Override
  public @VulkanExternallySynchronizedType VulkanFenceStatus getFenceStatus(
    final VulkanFenceType fence)
    throws VulkanException
  {
    Objects.requireNonNull(fence, "fence");

    this.checkNotClosed();

    final var result =
      VK10.vkGetFenceStatus(
        this.device,
        checkInstanceOf(
          fence,
          VulkanLWJGLFence.class).handle());

    if (result == VK_SUCCESS) {
      return VulkanFenceStatus.VK_FENCE_SIGNALLED;
    }
    if (result == VK_NOT_READY) {
      return VulkanFenceStatus.VK_FENCE_UNSIGNALLED;
    }

    throw VulkanChecks.failed(result, "vkGetEventStatus");
  }

  @Override
  public @VulkanExternallySynchronizedType VulkanSubresourceLayout getImageSubresourceLayout(
    final VulkanImageType image,
    final VulkanImageSubresource image_subresource)
    throws VulkanException
  {
    Objects.requireNonNull(image, "image");
    Objects.requireNonNull(image_subresource, "image_subresource");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var vk_layout = VkSubresourceLayout.calloc(stack);

      VK10.vkGetImageSubresourceLayout(
        this.device,
        checkInstanceOf(image, VulkanLWJGLImage.class).handle(),
        VulkanLWJGLImageSubresources.pack(stack, image_subresource),
        vk_layout);

      return VulkanSubresourceLayout.builder()
        .setArrayPitch(vk_layout.arrayPitch())
        .setDepthPitch(vk_layout.depthPitch())
        .setOffset(vk_layout.offset())
        .setRowPitch(vk_layout.rowPitch())
        .setSize(vk_layout.size())
        .build();
    }
  }

  @Override
  public List<VulkanPipelineType> createComputePipelines(
    final Optional<VulkanPipelineCacheType> pipeline_cache,
    final List<VulkanComputePipelineCreateInfo> pipeline_infos)
    throws VulkanException
  {
    Objects.requireNonNull(pipeline_cache, "pipeline_cache");
    Objects.requireNonNull(pipeline_infos, "pipeline_infos");

    try (var stack = this.stack_initial.push()) {
      final var pipes = new long[pipeline_infos.size()];
      final var proxy = this.hostAllocatorProxy();
      VulkanChecks.checkReturnCode(
        VK10.vkCreateComputePipelines(
          this.device,
          mapPipelineCacheOptional(pipeline_cache),
          VulkanLWJGLComputePipelineCreateInfos.pack(stack, pipeline_infos),
          proxy.callbackBuffer(),
          pipes),
        "vkCreateComputePipelines");

      final ArrayList<VulkanLWJGLPipeline> result_pipelines =
        new ArrayList<>(pipeline_infos.size());

      for (var index = 0; index < pipeline_infos.size(); ++index) {
        final var pipe = pipes[index];
        if (LOG.isTraceEnabled()) {
          LOG.trace("Created pipeline: 0x{}", Long.toUnsignedString(pipe, 16));
        }
        result_pipelines.add(new VulkanLWJGLPipeline(
          USER_OWNED,
          this.device,
          pipe,
          proxy));
      }

      return castPipelines(result_pipelines);
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
      LOG.trace("Destroying logical device: {}", this);
    }

    VK10.vkDestroyDevice(
      this.device,
      this.hostAllocatorProxy().callbackBuffer()
    );
  }
}
