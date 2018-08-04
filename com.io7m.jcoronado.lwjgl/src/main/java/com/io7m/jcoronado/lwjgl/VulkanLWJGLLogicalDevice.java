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
import com.io7m.jcoronado.api.VulkanCommandPoolType;
import com.io7m.jcoronado.api.VulkanDestroyedException;
import com.io7m.jcoronado.api.VulkanDeviceMemoryType;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionType;
import com.io7m.jcoronado.api.VulkanFenceCreateInfo;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanFramebufferCreateInfo;
import com.io7m.jcoronado.api.VulkanFramebufferType;
import com.io7m.jcoronado.api.VulkanGraphicsPipelineCreateInfo;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import com.io7m.jcoronado.api.VulkanImageViewType;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanMappedMemoryType;
import com.io7m.jcoronado.api.VulkanMemoryAllocateInfo;
import com.io7m.jcoronado.api.VulkanMemoryMapFlag;
import com.io7m.jcoronado.api.VulkanMemoryRequirements;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanPipelineLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineLayoutType;
import com.io7m.jcoronado.api.VulkanPipelineType;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanRenderPassCreateInfo;
import com.io7m.jcoronado.api.VulkanRenderPassType;
import com.io7m.jcoronado.api.VulkanSemaphoreCreateInfo;
import com.io7m.jcoronado.api.VulkanSemaphoreType;
import com.io7m.jcoronado.api.VulkanShaderModuleCreateInfo;
import com.io7m.jcoronado.api.VulkanShaderModuleType;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkBufferCreateInfo;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo;
import org.lwjgl.vulkan.VkCommandPoolCreateInfo;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkFenceCreateInfo;
import org.lwjgl.vulkan.VkFramebufferCreateInfo;
import org.lwjgl.vulkan.VkGraphicsPipelineCreateInfo;
import org.lwjgl.vulkan.VkImageViewCreateInfo;
import org.lwjgl.vulkan.VkMemoryAllocateInfo;
import org.lwjgl.vulkan.VkMemoryRequirements;
import org.lwjgl.vulkan.VkPipelineLayoutCreateInfo;
import org.lwjgl.vulkan.VkQueue;
import org.lwjgl.vulkan.VkRenderPassCreateInfo;
import org.lwjgl.vulkan.VkSemaphoreCreateInfo;
import org.lwjgl.vulkan.VkShaderModuleCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.io7m.jcoronado.lwjgl.VulkanLWJGLHandle.Ownership.USER_OWNED;
import static com.io7m.jcoronado.lwjgl.VulkanLWJGLHandle.Ownership.VULKAN_OWNED;

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
    final VulkanLogicalDeviceCreateInfo in_creation)
    throws VulkanException
  {
    super(USER_OWNED);

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
    final VulkanLWJGLLogicalDevice that = (VulkanLWJGLLogicalDevice) o;
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
    try (MemoryStack stack = this.stack_initial.push()) {
      final List<VulkanLogicalDeviceQueueCreateInfo> queue_requests =
        this.creation.queueCreateInfos();
      final List<VulkanQueueFamilyProperties> families =
        this.physical_device.queueFamilies();

      final PointerBuffer queue_buffer = stack.mallocPointer(1);
      for (final VulkanLogicalDeviceQueueCreateInfo queue_info : queue_requests) {
        for (int queue_index = 0; queue_index < queue_info.queueCount(); ++queue_index) {
          final int queue_family_index =
            queue_info.queueFamilyIndex();
          final VulkanQueueFamilyProperties family =
            families.get(queue_family_index);

          VK10.vkGetDeviceQueue(
            this.device,
            queue_family_index,
            queue_index,
            queue_buffer);

          final VkQueue queue = new VkQueue(queue_buffer.get(0), this.device);
          this.queues.add(new VulkanLWJGLQueue(this, queue, family, queue_index));
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

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkShaderModuleCreateInfo vk_create_info =
        VulkanLWJGLShaderModules.packShaderModuleCreateInfo(stack, create_info);

      final long[] results = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateShaderModule(this.device, vk_create_info, null, results),
        "vkCreateShaderModule");

      final long shader_module = results[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created shader module: 0x{}", Long.toUnsignedString(shader_module, 16));
      }

      return new VulkanLWJGLShaderModule(USER_OWNED, this.device, shader_module);
    }
  }

  @Override
  public VulkanImageViewType createImageView(
    final VulkanImageViewCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    final VulkanLWJGLImage image =
      VulkanLWJGLClassChecks.check(info.image(), VulkanLWJGLImage.class);

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkImageViewCreateInfo create_info =
        VulkanLWJGLImageViews.packImageViewCreateInfo(stack, info, image);

      final long[] view = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateImageView(this.device, create_info, null, view),
        "vkCreateImageView");

      final long view_handle = view[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created image view: 0x{}", Long.toUnsignedString(view_handle, 16));
      }

      return new VulkanLWJGLImageView(this.device, view_handle);
    }
  }

  @Override
  public VulkanPipelineLayoutType createPipelineLayout(
    final VulkanPipelineLayoutCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkPipelineLayoutCreateInfo create_info =
        VulkanLWJGLPipelineLayouts.packPipelineLayoutCreateInfo(stack, info);

      final long[] layout = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreatePipelineLayout(this.device, create_info, null, layout),
        "vkCreatePipelineLayout");

      final long layout_handle = layout[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created pipeline layout: 0x{}", Long.toUnsignedString(layout_handle, 16));
      }

      return new VulkanLWJGLPipelineLayout(USER_OWNED, this.device, layout_handle);
    }
  }

  @Override
  public VulkanRenderPassType createRenderPass(
    final VulkanRenderPassCreateInfo render_pass_create_info)
    throws VulkanException
  {
    Objects.requireNonNull(render_pass_create_info, "render_pass_create_info");

    this.checkNotClosed();

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkRenderPassCreateInfo create_info =
        VulkanLWJGLRenderPasses.packRenderPassCreateInfo(stack, render_pass_create_info);

      final long[] pass = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateRenderPass(this.device, create_info, null, pass),
        "vkCreateRenderPass");

      final long pass_handle = pass[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created render pass: 0x{}", Long.toUnsignedString(pass_handle, 16));
      }

      return new VulkanLWJGLRenderPass(USER_OWNED, this.device, pass_handle);
    }
  }

  @Override
  public List<VulkanPipelineType> createPipelines(
    final List<VulkanGraphicsPipelineCreateInfo> pipeline_infos)
    throws VulkanException
  {
    Objects.requireNonNull(pipeline_infos, "pipeline_infos");

    this.checkNotClosed();

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkGraphicsPipelineCreateInfo.Buffer infos =
        VulkanLWJGLGraphicsPipelineCreateInfos.pack(stack, pipeline_infos);

      final long[] pipes = new long[pipeline_infos.size()];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateGraphicsPipelines(this.device, 0L, infos, null, pipes),
        "vkCreateGraphicsPipelines");

      final ArrayList<VulkanLWJGLPipeline> result_pipelines =
        new ArrayList<>(pipeline_infos.size());

      for (int index = 0; index < pipeline_infos.size(); ++index) {
        final long pipe = pipes[index];
        if (LOG.isTraceEnabled()) {
          LOG.trace("created pipeline: 0x{}", Long.toUnsignedString(pipe, 16));
        }
        result_pipelines.add(new VulkanLWJGLPipeline(USER_OWNED, this.device, pipe));
      }

      return castPipelines(result_pipelines);
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
      final List<VulkanLWJGLImageView> views =
        create_info.attachments()
          .stream()
          .map(view -> {
            try {
              return VulkanLWJGLClassChecks.check(view, VulkanLWJGLImageView.class);
            } catch (VulkanIncompatibleClassException e) {
              throw new VulkanUncheckedException(e);
            }
          })
          .collect(Collectors.toList());

      final VulkanLWJGLRenderPass pass =
        VulkanLWJGLClassChecks.check(create_info.renderPass(), VulkanLWJGLRenderPass.class);

      try (MemoryStack stack = this.stack_initial.push()) {
        final VkFramebufferCreateInfo info =
          VulkanLWJGLFramebufferCreateInfos.pack(stack, create_info, views, pass);

        final long[] framebuffers = new long[1];
        VulkanChecks.checkReturnCode(
          VK10.vkCreateFramebuffer(this.device, info, null, framebuffers),
          "vkCreateFramebuffer");

        final long handle = framebuffers[0];
        if (LOG.isTraceEnabled()) {
          LOG.trace("created framebuffer: 0x{}", Long.toUnsignedString(handle, 16));
        }

        return new VulkanLWJGLFramebuffer(USER_OWNED, this.device, handle);
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

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkCommandPoolCreateInfo packed =
        VulkanLWJGLCommandPoolCreateInfos.pack(stack, create_info);

      final long[] handles = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateCommandPool(this.device, packed, null, handles),
        "vkCreateCommandPool");

      final long handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created command pool: 0x{}", Long.toUnsignedString(handle, 16));
      }

      return new VulkanLWJGLCommandPool(USER_OWNED, this.device, handle);
    }
  }

  @Override
  public List<VulkanCommandBufferType> createCommandBuffers(
    final VulkanCommandBufferCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    final VulkanLWJGLCommandPool pool =
      VulkanLWJGLClassChecks.check(create_info.pool(), VulkanLWJGLCommandPool.class);

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkCommandBufferAllocateInfo packed =
        VulkanLWJGLCommandBufferCreateInfos.pack(stack, create_info, pool);

      final int count = create_info.count();
      final PointerBuffer buffers = stack.mallocPointer(count);

      VulkanChecks.checkReturnCode(
        VK10.vkAllocateCommandBuffers(this.device, packed, buffers),
        "vkAllocateCommandBuffers");

      final List<VulkanCommandBufferType> results = new ArrayList<>(count);

      if (LOG.isTraceEnabled()) {
        for (int index = 0; index < count; ++index) {
          LOG.trace("created command buffer: 0x{}", Long.toUnsignedString(buffers.get(index), 16));
        }
      }

      for (int index = 0; index < count; ++index) {
        final VkCommandBuffer handle =
          new VkCommandBuffer(buffers.get(index), this.device);
        final VulkanLWJGLCommandBuffer buffer =
          new VulkanLWJGLCommandBuffer(VULKAN_OWNED, handle);
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

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkSemaphoreCreateInfo packed =
        VulkanLWJGLSemaphoreCreateInfos.pack(stack, create_info);

      final long[] semaphores = new long[1];

      VulkanChecks.checkReturnCode(
        VK10.vkCreateSemaphore(this.device, packed, null, semaphores),
        "vkCreateSemaphore");

      final long handle = semaphores[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created semaphore: 0x{}", Long.toUnsignedString(handle, 16));
      }
      return new VulkanLWJGLSemaphore(USER_OWNED, this.device, handle);
    }
  }

  @Override
  public VulkanFenceType createFence(
    final VulkanFenceCreateInfo create_info)
    throws VulkanException
  {
    Objects.requireNonNull(create_info, "create_info");

    this.checkNotClosed();

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkFenceCreateInfo packed =
        VulkanLWJGLFenceCreateInfos.pack(stack, create_info);

      final long[] fences = new long[1];

      VulkanChecks.checkReturnCode(
        VK10.vkCreateFence(this.device, packed, null, fences),
        "vkCreateFence");

      final long handle = fences[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created semaphore: 0x{}", Long.toUnsignedString(handle, 16));
      }
      return new VulkanLWJGLFence(USER_OWNED, this.device, handle);
    }
  }

  @Override
  public void resetFences(final List<VulkanFenceType> fences)
    throws VulkanException
  {
    Objects.requireNonNull(fences, "fences");

    this.checkNotClosed();

    try (MemoryStack stack = this.stack_initial.push()) {
      final LongBuffer array = stack.mallocLong(fences.size());
      for (int index = 0; index < fences.size(); ++index) {
        final VulkanLWJGLFence fence =
          VulkanLWJGLClassChecks.check(fences.get(index), VulkanLWJGLFence.class);
        array.put(index, fence.handle());
      }

      VulkanChecks.checkReturnCode(
        VK10.vkResetFences(this.device, array),
        "vkResetFences");
    }
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

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkBufferCreateInfo info =
        VulkanLWJGLBufferCreateInfos.packInfo(stack, create_info);

      final long[] handles = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateBuffer(this.device, info, null, handles),
        "vkCreateBuffer");

      final long handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("created buffer: 0x{}", Long.toUnsignedString(handle, 16));
      }
      return new VulkanLWJGLBuffer(USER_OWNED, this.device, handle);
    }
  }

  @Override
  public VulkanMemoryRequirements getBufferMemoryRequirements(
    final VulkanBufferType buffer)
    throws VulkanException
  {
    Objects.requireNonNull(buffer, "buffer");

    this.checkNotClosed();

    final VulkanLWJGLBuffer cbuffer =
      VulkanLWJGLClassChecks.check(buffer, VulkanLWJGLBuffer.class);

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkMemoryRequirements info = VkMemoryRequirements.mallocStack(stack);

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

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkMemoryAllocateInfo cinfo =
        VkMemoryAllocateInfo.mallocStack(stack)
          .sType(VK10.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO)
          .pNext(0L)
          .allocationSize(info.size())
          .memoryTypeIndex(info.memoryTypeIndex());

      final long[] handles = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkAllocateMemory(this.device, cinfo, null, handles),
        "vkAllocateMemory");

      final long handle = handles[0];
      if (LOG.isTraceEnabled()) {
        LOG.trace("allocated device memory: 0x{}", Long.toUnsignedString(handle, 16));
      }

      return new VulkanLWJGLDeviceMemory(USER_OWNED, this.device, handles[0]);
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

    final VulkanLWJGLBuffer cbuffer =
      VulkanLWJGLClassChecks.check(buffer, VulkanLWJGLBuffer.class);
    final VulkanLWJGLDeviceMemory cmemory =
      VulkanLWJGLClassChecks.check(device_memory, VulkanLWJGLDeviceMemory.class);

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

    final VulkanLWJGLDeviceMemory cmemory =
      VulkanLWJGLClassChecks.check(memory, VulkanLWJGLDeviceMemory.class);

    final int int_flags = VulkanEnumMaps.packValues(flags);
    final long int_handle = cmemory.handle();

    VulkanChecks.checkReturnCode(
      VK10.vkMapMemory(this.device, int_handle, offset, size, int_flags, this.pointer_buffer),
      "vkMapMemory");

    final long address = this.pointer_buffer.get(0);
    if (LOG.isTraceEnabled()) {
      LOG.trace(
        "mapped memory: 0x{} -> 0x{}",
        Long.toUnsignedString(int_handle, 16),
        Long.toUnsignedString(address, 16));
    }

    return new VulkanLWJGLMappedMemory(this.device, cmemory, address, size);
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
    VK10.vkDestroyDevice(this.device, null);
  }
}
