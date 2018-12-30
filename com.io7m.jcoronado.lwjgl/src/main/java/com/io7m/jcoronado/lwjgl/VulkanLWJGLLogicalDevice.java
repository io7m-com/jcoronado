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

import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanBufferType;
import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanCommandBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanCommandPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandPoolResetFlag;
import com.io7m.jcoronado.api.VulkanCommandPoolType;
import com.io7m.jcoronado.api.VulkanCopyDescriptorSet;
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
import com.io7m.jcoronado.api.VulkanPipelineLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineLayoutType;
import com.io7m.jcoronado.api.VulkanPipelineType;
import com.io7m.jcoronado.api.VulkanQueryPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanQueryPoolType;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanRenderPassCreateInfo;
import com.io7m.jcoronado.api.VulkanRenderPassType;
import com.io7m.jcoronado.api.VulkanSamplerCreateInfo;
import com.io7m.jcoronado.api.VulkanSamplerType;
import com.io7m.jcoronado.api.VulkanSemaphoreCreateInfo;
import com.io7m.jcoronado.api.VulkanSemaphoreType;
import com.io7m.jcoronado.api.VulkanShaderModuleCreateInfo;
import com.io7m.jcoronado.api.VulkanShaderModuleType;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import com.io7m.jcoronado.api.VulkanWriteDescriptorSet;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkMemoryAllocateInfo;
import org.lwjgl.vulkan.VkMemoryRequirements;
import org.lwjgl.vulkan.VkQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.io7m.jcoronado.lwjgl.VulkanLWJGLClassChecks.checkInstanceOf;
import static com.io7m.jcoronado.lwjgl.VulkanLWJGLHandle.Ownership.USER_OWNED;
import static com.io7m.jcoronado.lwjgl.VulkanLWJGLHandle.Ownership.VULKAN_OWNED;
import static org.lwjgl.vulkan.VK10.VK_EVENT_RESET;
import static org.lwjgl.vulkan.VK10.VK_EVENT_SET;

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

  VulkanLWJGLLogicalDevice(
    final Map<String, VulkanExtensionType> in_extensions_enabled,
    final VulkanLWJGLPhysicalDevice in_physical_device,
    final VkDevice in_device,
    final VulkanLogicalDeviceCreateInfo in_creation,
    final VulkanLWJGLHostAllocatorProxy in_host_allocator_proxy)
    throws VulkanException
  {
    super(USER_OWNED, in_host_allocator_proxy);

    this.extensions_enabled_read_only =
      Collections.unmodifiableMap(
        Objects.requireNonNull(in_extensions_enabled, "in_extensions"));
    this.physical_device =
      Objects.requireNonNull(in_physical_device, "in_physical_device");
    this.device =
      Objects.requireNonNull(in_device, "in_device");
    this.creation =
      Objects.requireNonNull(in_creation, "in_creation");
    this.queues =
      new ArrayList<>(32);
    this.queues_read =
      Collections.unmodifiableList(this.queues);
    this.stack_initial =
      MemoryStack.create();
    this.pointer_buffer =
      this.stack_initial.mallocPointer(1);

    this.initializeQueues();
  }

  @SuppressWarnings("unchecked")
  private static List<VulkanPipelineType> castPipelines(
    final ArrayList<VulkanLWJGLPipeline> result_pipelines)
  {
    return (List<VulkanPipelineType>) (Object) result_pipelines;
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
    final var that = (VulkanLWJGLLogicalDevice) o;
    return Objects.equals(this.physical_device, that.physical_device)
      && Objects.equals(this.device, that.device);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(this.physical_device, this.device);
  }

  VkDevice device()
  {
    return this.device;
  }

  private void initializeQueues()
    throws VulkanException
  {
    try (var stack = this.stack_initial.push()) {
      final var queue_requests =
        this.creation.queueCreateInfos();
      final var families =
        this.physical_device.queueFamilies();

      final var queue_buffer = stack.mallocPointer(1);
      for (final var queue_info : queue_requests) {
        for (var queue_index = 0; queue_index < queue_info.queueCount(); ++queue_index) {
          final var queue_family_index =
            queue_info.queueFamilyIndex();
          final var family =
            families.get(queue_family_index);

          VK10.vkGetDeviceQueue(
            this.device,
            queue_family_index,
            queue_index,
            queue_buffer);

          final var queue = new VkQueue(queue_buffer.get(0), this.device);
          this.queues.add(new VulkanLWJGLQueue(
            this, queue, family, queue_index, this.hostAllocatorProxy()));
        }
      }
    }
  }

  @Override
  public String toString()
  {
    return new StringBuilder(32)
      .append("[VulkanLWJGLLogicalDevice 0x")
      .append(Long.toUnsignedString(this.device.address(), 16))
      .append(']')
      .toString();
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
      LOG.debug("creating shader module: {} octets", Long.valueOf(create_info.size()));
    }

    try (var stack = this.stack_initial.push()) {
      final var vk_create_info =
        VulkanLWJGLShaderModules.packShaderModuleCreateInfo(stack, create_info);

      final var results = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateShaderModule(
          this.device,
          vk_create_info,
          this.hostAllocatorProxy().callbackBuffer(),
          results),
        "vkCreateShaderModule");

      final var shader_module = results[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created shader module: 0x{}", Long.toUnsignedString(shader_module, 16));
      }

      return new VulkanLWJGLShaderModule(
        USER_OWNED,
        this.device,
        shader_module,
        this.hostAllocatorProxy());
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
      final var vk_create_info = VulkanLWJGLSamplerCreateInfos.pack(stack, create_info);

      final var handle = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateSampler(
          this.device,
          vk_create_info,
          this.hostAllocatorProxy().callbackBuffer(),
          handle),
        "vkCreateSampler");

      final var vk_handle = handle[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created sampler: 0x{}", Long.toUnsignedString(vk_handle, 16));
      }

      return new VulkanLWJGLSampler(USER_OWNED, this.device, vk_handle, this.hostAllocatorProxy());
    }
  }

  @Override
  public VulkanImageViewType createImageView(
    final VulkanImageViewCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    final var image =
      checkInstanceOf(info.image(), VulkanLWJGLImage.class);

    try (var stack = this.stack_initial.push()) {
      final var create_info =
        VulkanLWJGLImageViews.packImageViewCreateInfo(stack, info, image);

      final var view = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateImageView(
          this.device,
          create_info,
          this.hostAllocatorProxy().callbackBuffer(),
          view),
        "vkCreateImageView");

      final var view_handle = view[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created image view: 0x{}", Long.toUnsignedString(view_handle, 16));
      }

      return new VulkanLWJGLImageView(this.device, view_handle, image, this.hostAllocatorProxy());
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
      final var create_info =
        VulkanLWJGLPipelineLayouts.packPipelineLayoutCreateInfo(stack, info);

      final var layout = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreatePipelineLayout(
          this.device,
          create_info,
          this.hostAllocatorProxy().callbackBuffer(),
          layout),
        "vkCreatePipelineLayout");

      final var layout_handle = layout[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created pipeline layout: 0x{}", Long.toUnsignedString(layout_handle, 16));
      }

      return new VulkanLWJGLPipelineLayout(
        USER_OWNED,
        this.device,
        layout_handle,
        this.hostAllocatorProxy());
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
      final var create_info =
        VulkanLWJGLDescriptorSetLayouts.packDescriptorSetLayoutCreateInfo(stack, info);

      final var layout = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateDescriptorSetLayout(
          this.device,
          create_info,
          this.hostAllocatorProxy().callbackBuffer(),
          layout),
        "vkCreateDescriptorSetLayout");

      final var layout_handle = layout[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created descriptor set layout: 0x{}", Long.toUnsignedString(layout_handle, 16));
      }

      return new VulkanLWJGLDescriptorSetLayout(
        this.device,
        layout_handle,
        this.hostAllocatorProxy());
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
      final var create_info =
        VulkanLWJGLDescriptorPoolCreateInfos.packDescriptorPoolCreateInfo(stack, info);

      final var layout = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateDescriptorPool(
          this.device,
          create_info,
          this.hostAllocatorProxy().callbackBuffer(),
          layout),
        "vkCreateDescriptorPool");

      final var layout_handle = layout[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created descriptor pool: 0x{}", Long.toUnsignedString(layout_handle, 16));
      }

      return new VulkanLWJGLDescriptorPool(
        USER_OWNED,
        this.device,
        layout_handle,
        this.hostAllocatorProxy());
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
      final var alloc_info =
        VulkanLWJGLDescriptorSetAllocateInfos.pack(stack, info);

      final var count = info.setLayouts().size();
      final var handles = stack.mallocLong(count);

      VulkanChecks.checkReturnCode(
        VK10.vkAllocateDescriptorSets(
          this.device,
          alloc_info,
          handles),
        "vkAllocateDescriptorSets");

      if (LOG.isTraceEnabled()) {
        LOG.trace("allocated {} descriptor sets", Integer.valueOf(count));
      }

      final ArrayList<VulkanDescriptorSetType> results = new ArrayList<>(count);
      for (var index = 0; index < count; ++index) {
        results.add(new VulkanLWJGLDescriptorSet(
          this.device, handles.get(index), this.hostAllocatorProxy()));
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
      final var create_info =
        VulkanLWJGLRenderPasses.packRenderPassCreateInfo(stack, render_pass_create_info);

      final var pass = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateRenderPass(
          this.device,
          create_info,
          this.hostAllocatorProxy().callbackBuffer(),
          pass),
        "vkCreateRenderPass");

      final var pass_handle = pass[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created render pass: 0x{}", Long.toUnsignedString(pass_handle, 16));
      }

      return new VulkanLWJGLRenderPass(
        USER_OWNED,
        this.device,
        pass_handle,
        this.hostAllocatorProxy());
    }
  }

  @Override
  public List<VulkanPipelineType> createPipelines(
    final List<VulkanGraphicsPipelineCreateInfo> pipeline_infos)
    throws VulkanException
  {
    Objects.requireNonNull(pipeline_infos, "pipeline_infos");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var infos =
        VulkanLWJGLGraphicsPipelineCreateInfos.pack(stack, pipeline_infos);

      final var pipes = new long[pipeline_infos.size()];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateGraphicsPipelines(
          this.device,
          0L,
          infos,
          this.hostAllocatorProxy().callbackBuffer(),
          pipes),
        "vkCreateGraphicsPipelines");

      final ArrayList<VulkanLWJGLPipeline> result_pipelines =
        new ArrayList<>(pipeline_infos.size());

      for (var index = 0; index < pipeline_infos.size(); ++index) {
        final var pipe = pipes[index];
        if (LOG.isTraceEnabled()) {
          LOG.trace("created pipeline: 0x{}", Long.toUnsignedString(pipe, 16));
        }
        result_pipelines.add(new VulkanLWJGLPipeline(
          USER_OWNED,
          this.device,
          pipe,
          this.hostAllocatorProxy()));
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
      final var vk_create_info =
        VulkanLWJGLQueryPoolCreateInfos.pack(stack, create_info);

      final var pool = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateQueryPool(
          this.device,
          vk_create_info,
          this.hostAllocatorProxy().callbackBuffer(),
          pool),
        "vkCreateQueryPool");

      final var pool_handle = pool[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created query pool: 0x{}", Long.toUnsignedString(pool_handle, 16));
      }

      return new VulkanLWJGLQueryPool(
        USER_OWNED,
        this.device,
        pool_handle,
        this.hostAllocatorProxy());
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
            } catch (VulkanIncompatibleClassException e) {
              throw new VulkanUncheckedException(e);
            }
          })
          .collect(Collectors.toList());

      final var pass =
        checkInstanceOf(
          create_info.renderPass(),
          VulkanLWJGLRenderPass.class);

      try (var stack = this.stack_initial.push()) {
        final var info =
          VulkanLWJGLFramebufferCreateInfos.pack(stack, create_info, views, pass);

        final var framebuffers = new long[1];
        VulkanChecks.checkReturnCode(
          VK10.vkCreateFramebuffer(
            this.device,
            info,
            this.hostAllocatorProxy().callbackBuffer(),
            framebuffers),
          "vkCreateFramebuffer");

        final var handle = framebuffers[0];
        if (LOG.isTraceEnabled()) {
          LOG.trace("created framebuffer: 0x{}", Long.toUnsignedString(handle, 16));
        }

        return new VulkanLWJGLFramebuffer(
          USER_OWNED,
          this.device,
          handle,
          this.hostAllocatorProxy());
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
      final var packed =
        VulkanLWJGLCommandPoolCreateInfos.pack(stack, create_info);

      final var handles = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateCommandPool(
          this.device,
          packed,
          this.hostAllocatorProxy().callbackBuffer(),
          handles),
        "vkCreateCommandPool");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created command pool: 0x{}", Long.toUnsignedString(handle, 16));
      }

      return new VulkanLWJGLCommandPool(USER_OWNED, this.device, handle, this.hostAllocatorProxy());
    }
  }

  @Override
  public List<VulkanCommandBufferType> createCommandBuffers(
    final VulkanCommandBufferCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    final var pool =
      checkInstanceOf(create_info.pool(), VulkanLWJGLCommandPool.class);

    try (var stack = this.stack_initial.push()) {
      final var packed =
        VulkanLWJGLCommandBufferCreateInfos.pack(stack, create_info, pool);

      final var count = create_info.count();
      final var buffers = stack.mallocPointer(count);

      VulkanChecks.checkReturnCode(
        VK10.vkAllocateCommandBuffers(this.device, packed, buffers),
        "vkAllocateCommandBuffers");

      final List<VulkanCommandBufferType> results = new ArrayList<>(count);

      if (LOG.isTraceEnabled()) {
        for (var index = 0; index < count; ++index) {
          LOG.trace("created command buffer: 0x{}", Long.toUnsignedString(buffers.get(index), 16));
        }
      }

      for (var index = 0; index < count; ++index) {
        final var handle =
          new VkCommandBuffer(buffers.get(index), this.device);
        final var buffer =
          new VulkanLWJGLCommandBuffer(VULKAN_OWNED, handle, this.hostAllocatorProxy());
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
      final var packed = VulkanLWJGLSemaphoreCreateInfos.pack(stack, create_info);

      final var handles = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateSemaphore(
          this.device,
          packed,
          this.hostAllocatorProxy().callbackBuffer(),
          handles),
        "vkCreateSemaphore");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created semaphore: 0x{}", Long.toUnsignedString(handle, 16));
      }
      return new VulkanLWJGLSemaphore(USER_OWNED, this.device, handle, this.hostAllocatorProxy());
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
      final var packed = VulkanLWJGLFenceCreateInfos.pack(stack, create_info);

      final var fences = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateFence(
          this.device,
          packed,
          this.hostAllocatorProxy().callbackBuffer(),
          fences),
        "vkCreateFence");

      final var handle = fences[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created fence: 0x{}", Long.toUnsignedString(handle, 16));
      }
      return new VulkanLWJGLFence(USER_OWNED, this.device, handle, this.hostAllocatorProxy());
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
      final var packed = VulkanLWJGLEventCreateInfos.pack(stack, create_info);

      final var handles = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateEvent(
          this.device,
          packed,
          this.hostAllocatorProxy().callbackBuffer(),
          handles),
        "vkCreateEvent");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created event: 0x{}", Long.toUnsignedString(handle, 16));
      }
      return new VulkanLWJGLEvent(USER_OWNED, this.device, handle, this.hostAllocatorProxy());
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
        final var fence =
          checkInstanceOf(fences.get(index), VulkanLWJGLFence.class);
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
  public VulkanBufferType createBuffer(
    final VulkanBufferCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var info = VulkanLWJGLBufferCreateInfos.packInfo(stack, create_info);
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
        LOG.trace("created buffer: 0x{}", Long.toUnsignedString(handle, 16));
      }

      return new VulkanLWJGLBuffer(
        USER_OWNED,
        this.device,
        handle,
        () -> this.destroyBuffer(proxy, handle),
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
      final var info = VulkanLWJGLImageCreateInfos.pack(stack, create_info);
      final var handles = new long[1];
      final var proxy = this.hostAllocatorProxy();

      VulkanChecks.checkReturnCode(
        VK10.vkCreateImage(
          this.device,
          info,
          proxy.callbackBuffer(),
          handles),
        "vkCreateImage");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created image: 0x{}", Long.toUnsignedString(handle, 16));
      }

      return new VulkanLWJGLImage(
        USER_OWNED,
        this.device,
        handle,
        () -> this.destroyImage(proxy, handle),
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
      LOG.trace("VK10.vkDestroyBuffer: 0x{}", Long.toUnsignedString(handle, 16));
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

    final var cbuffer =
      checkInstanceOf(buffer, VulkanLWJGLBuffer.class);

    try (var stack = this.stack_initial.push()) {
      final var info = VkMemoryRequirements.mallocStack(stack);

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
      final var cinfo =
        VkMemoryAllocateInfo.mallocStack(stack)
          .sType(VK10.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO)
          .pNext(0L)
          .allocationSize(info.size())
          .memoryTypeIndex(info.memoryTypeIndex());

      final var handles = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkAllocateMemory(
          this.device,
          cinfo,
          this.hostAllocatorProxy().callbackBuffer(),
          handles),
        "vkAllocateMemory");

      final var handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("allocated device memory: 0x{}", Long.toUnsignedString(handle, 16));
      }

      return new VulkanLWJGLDeviceMemory(
        USER_OWNED,
        this.device,
        handles[0],
        this.hostAllocatorProxy());
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
      VK10.vkBindBufferMemory(this.device, cbuffer.handle(), cmemory.handle(), offset),
      "vkBindBufferMemory");
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

    final var cmemory =
      checkInstanceOf(memory, VulkanLWJGLDeviceMemory.class);

    final var int_flags = VulkanEnumMaps.packValues(flags);
    final var int_handle = cmemory.handle();

    VulkanChecks.checkReturnCode(
      VK10.vkMapMemory(this.device, int_handle, offset, size, int_flags, this.pointer_buffer),
      "vkMapMemory");

    final var address = this.pointer_buffer.get(0);
    if (LOG.isTraceEnabled()) {
      LOG.trace(
        "mapped memory: 0x{} -> 0x{}",
        Long.toUnsignedString(int_handle, 16),
        Long.toUnsignedString(address, 16));
    }

    return new VulkanLWJGLMappedMemory(
      this.device,
      cmemory,
      (map_mem, map_off, map_size) ->
        this.flushMappedMemoryRange(VulkanMappedMemoryRange.of(map_mem, map_off, map_size)),
      address,
      size);
  }

  @Override
  public @VulkanExternallySynchronizedType void setEvent(
    final VulkanEventType event)
    throws VulkanException
  {
    Objects.requireNonNull(event, "event");

    this.checkNotClosed();

    VK10.vkSetEvent(this.device, checkInstanceOf(event, VulkanLWJGLEvent.class).handle());
  }

  @Override
  public @VulkanExternallySynchronizedType void resetEvent(
    final VulkanEventType event)
    throws VulkanException
  {
    Objects.requireNonNull(event, "event");

    VK10.vkResetEvent(this.device, checkInstanceOf(event, VulkanLWJGLEvent.class).handle());
  }

  @Override
  public @VulkanExternallySynchronizedType VulkanEventStatus getEventStatus(
    final VulkanEventType event)
    throws VulkanException
  {
    Objects.requireNonNull(event, "event");

    this.checkNotClosed();

    final var result =
      VK10.vkGetEventStatus(this.device, checkInstanceOf(event, VulkanLWJGLEvent.class).handle());

    if (result == VK_EVENT_SET) {
      return VulkanEventStatus.VK_EVENT_SET;
    }
    if (result == VK_EVENT_RESET) {
      return VulkanEventStatus.VK_EVENT_RESET;
    }

    throw VulkanChecks.failed(result, "vkGetEventStatus");
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
      LOG.trace("destroying logical device: {}", this);
    }
    VK10.vkDestroyDevice(this.device, this.hostAllocatorProxy().callbackBuffer());
  }
}
