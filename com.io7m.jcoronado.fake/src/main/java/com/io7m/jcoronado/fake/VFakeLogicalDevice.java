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

import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanBufferType;
import com.io7m.jcoronado.api.VulkanBufferViewCreateInfo;
import com.io7m.jcoronado.api.VulkanBufferViewType;
import com.io7m.jcoronado.api.VulkanCallFailedException;
import com.io7m.jcoronado.api.VulkanCommandBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanCommandPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandPoolResetFlag;
import com.io7m.jcoronado.api.VulkanCommandPoolType;
import com.io7m.jcoronado.api.VulkanComputePipelineCreateInfo;
import com.io7m.jcoronado.api.VulkanCopyDescriptorSet;
import com.io7m.jcoronado.api.VulkanDescriptorPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanDescriptorPoolResetFlag;
import com.io7m.jcoronado.api.VulkanDescriptorPoolType;
import com.io7m.jcoronado.api.VulkanDescriptorSetAllocateInfo;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutType;
import com.io7m.jcoronado.api.VulkanDescriptorSetType;
import com.io7m.jcoronado.api.VulkanDeviceMemoryType;
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
import com.io7m.jcoronado.api.VulkanWriteDescriptorSet;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A logical device.
 */

public final class VFakeLogicalDevice implements VulkanLogicalDeviceType
{
  private final VFakePhysicalDevice physicalDevice;
  private final AtomicBoolean closed;
  private List<VulkanQueueType> queues;

  /**
   * A logical device.
   *
   * @param inPhysicalDevice The physical device
   */

  public VFakeLogicalDevice(
    final VFakePhysicalDevice inPhysicalDevice)
  {
    this.physicalDevice =
      Objects.requireNonNull(inPhysicalDevice, "inPhysicalDevice");
    this.closed =
      new AtomicBoolean(false);
    this.queues =
      List.of();
  }

  /**
   * Set the queues.
   *
   * @param newQueues The queues
   */

  public void setQueues(
    final List<VulkanQueueType> newQueues)
  {
    this.queues = Objects.requireNonNull(newQueues, "queues");
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

  @Override
  public VulkanPhysicalDeviceType physicalDevice()
  {
    return this.physicalDevice;
  }

  @Override
  public List<VulkanQueueType> queues()
    throws VulkanException
  {
    return this.queues;
  }

  @Override
  public Map<String, VulkanExtensionType> enabledExtensions()
    throws VulkanException
  {
    throw errorNotImplemented("enabledExtensions");
  }

  @Override
  public VulkanShaderModuleType createShaderModule(
    final VulkanShaderModuleCreateInfo create_info)
    throws VulkanException
  {
    throw errorNotImplemented("createShaderModule");
  }

  @Override
  public VulkanSamplerType createSampler(
    final VulkanSamplerCreateInfo create_info)
    throws VulkanException
  {
    throw errorNotImplemented("createSampler");
  }

  @Override
  public VulkanBufferViewType createBufferView(
    final VulkanBufferViewCreateInfo info)
    throws VulkanException
  {
    throw errorNotImplemented("createBufferView");
  }

  @Override
  public VulkanImageViewType createImageView(
    final VulkanImageViewCreateInfo info)
    throws VulkanException
  {
    throw errorNotImplemented("createImageView");
  }

  @Override
  public void flushMappedMemoryRanges(
    final List<VulkanMappedMemoryRange> ranges)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("flushMappedMemoryRanges");
  }

  @Override
  public VulkanPipelineLayoutType createPipelineLayout(
    final VulkanPipelineLayoutCreateInfo info)
    throws VulkanException
  {
    throw errorNotImplemented("createPipelineLayout");
  }

  @Override
  public VulkanDescriptorSetLayoutType createDescriptorSetLayout(
    final VulkanDescriptorSetLayoutCreateInfo info)
    throws VulkanException
  {
    throw errorNotImplemented("createDescriptorSetLayout");
  }

  @Override
  public VulkanDescriptorPoolType createDescriptorPool(
    final VulkanDescriptorPoolCreateInfo info)
    throws VulkanException
  {
    throw errorNotImplemented("createDescriptorPool");
  }

  @Override
  public List<VulkanDescriptorSetType> allocateDescriptorSets(
    final VulkanDescriptorSetAllocateInfo info)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("allocateDescriptorSets");
  }

  @Override
  public void updateDescriptorSets(
    final List<VulkanWriteDescriptorSet> descriptor_writes,
    final List<VulkanCopyDescriptorSet> descriptor_copies)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("updateDescriptorSets");
  }

  @Override
  public VulkanRenderPassType createRenderPass(
    final VulkanRenderPassCreateInfo render_pass_create_info)
    throws VulkanException
  {
    throw errorNotImplemented("createRenderPass");
  }

  @Override
  public VulkanPipelineCacheType createPipelineCache(
    final VulkanPipelineCacheCreateInfo pipeline_info)
    throws VulkanException
  {
    throw errorNotImplemented("createPipelineCache");
  }

  @Override
  public long getPipelineCacheDataSize(
    final VulkanPipelineCacheType pipeline_cache)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("getPipelineCacheDataSize");
  }

  @Override
  public VulkanMemoryRequirements getImageMemoryRequirements(
    final VulkanImageType image)
    throws VulkanException
  {
    throw errorNotImplemented("getImageMemoryRequirements");
  }

  @Override
  public VulkanPipelineCacheDataResult getPipelineCacheData(
    final VulkanPipelineCacheType pipeline_cache,
    final ByteBuffer data)
    throws VulkanException
  {
    throw errorNotImplemented("getPipelineCacheData");
  }

  @Override
  public List<VulkanPipelineType> createGraphicsPipelines(
    final Optional<VulkanPipelineCacheType> pipeline_cache,
    final List<VulkanGraphicsPipelineCreateInfo> pipeline_infos)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("createGraphicsPipelines");
  }

  @Override
  public VulkanQueryPoolType createQueryPool(
    final VulkanQueryPoolCreateInfo create_info)
    throws VulkanException
  {
    throw errorNotImplemented("createQueryPool");
  }

  @Override
  public VulkanFramebufferType createFramebuffer(
    final VulkanFramebufferCreateInfo create_info)
    throws VulkanException
  {
    throw errorNotImplemented("createFramebuffer");
  }

  @Override
  public VulkanCommandPoolType createCommandPool(
    final VulkanCommandPoolCreateInfo create_info)
    throws VulkanException
  {
    throw errorNotImplemented("createCommandPool");
  }

  @Override
  public List<VulkanCommandBufferType> createCommandBuffers(
    final VulkanCommandBufferCreateInfo create_info)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("createCommandBuffers");
  }

  @Override
  public VulkanSemaphoreType createSemaphore(
    final VulkanSemaphoreCreateInfo create_info)
    throws VulkanException
  {
    throw errorNotImplemented("createSemaphore");
  }

  @Override
  public VulkanFenceType createFence(
    final VulkanFenceCreateInfo create_info)
    throws VulkanException
  {
    throw errorNotImplemented("createFence");
  }

  @Override
  public VulkanEventType createEvent(
    final VulkanEventCreateInfo create_info)
    throws VulkanException
  {
    throw errorNotImplemented("createEvent");
  }

  @Override
  public void resetFences(
    final List<@VulkanExternallySynchronizedType VulkanFenceType> fences)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("resetFences");
  }

  @Override
  public void resetCommandPool(
    final VulkanCommandPoolType pool,
    final Set<VulkanCommandPoolResetFlag> flags)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("resetCommandPool");
  }

  @Override
  public void resetDescriptorPool(
    final VulkanDescriptorPoolType pool,
    final Set<VulkanDescriptorPoolResetFlag> flags)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("resetDescriptorPool");
  }

  @Override
  public void waitIdle()
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("waitIdle");
  }

  @Override
  public VulkanWaitStatus waitForFences(
    final List<VulkanFenceType> fences,
    final boolean wait_all,
    final long timeout_nanos)
    throws VulkanException
  {
    throw errorNotImplemented("waitForFences");
  }

  @Override
  public VulkanBufferType createBuffer(
    final VulkanBufferCreateInfo create_info)
    throws VulkanException
  {
    throw errorNotImplemented("createBuffer");
  }

  @Override
  public VulkanImageType createImage(
    final VulkanImageCreateInfo create_info)
    throws VulkanException
  {
    throw errorNotImplemented("createImage");
  }

  @Override
  public VulkanMemoryRequirements getBufferMemoryRequirements(
    final VulkanBufferType buffer)
    throws VulkanException
  {
    throw errorNotImplemented("getBufferMemoryRequirements");
  }

  @Override
  public VulkanDeviceMemoryType allocateMemory(
    final VulkanMemoryAllocateInfo info)
    throws VulkanException
  {
    throw errorNotImplemented("allocateMemory");
  }

  @Override
  public void bindBufferMemory(
    final VulkanBufferType buffer,
    final VulkanDeviceMemoryType device_memory,
    final long offset)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("bindBufferMemory");
  }

  @Override
  public void bindImageMemory(
    final VulkanImageType image,
    final VulkanDeviceMemoryType device_memory,
    final long offset)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("bindImageMemory");
  }

  @Override
  public VulkanMappedMemoryType mapMemory(
    final VulkanDeviceMemoryType memory,
    final long offset,
    final long size,
    final Set<VulkanMemoryMapFlag> flags)
    throws VulkanException
  {
    throw errorNotImplemented("mapMemory");
  }

  @Override
  public void mergePipelineCaches(
    final List<VulkanPipelineCacheType> caches,
    final VulkanPipelineCacheType output)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("mergePipelineCaches");
  }

  @Override
  public void setEvent(
    final VulkanEventType event)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("setEvent");
  }

  @Override
  public void resetEvent(
    final VulkanEventType event)
    throws VulkanCallFailedException
  {
    throw errorNotImplemented("resetEvent");
  }

  @Override
  public VulkanEventStatus getEventStatus(
    final VulkanEventType event)
    throws VulkanException
  {
    throw errorNotImplemented("getEventStatus");
  }

  @Override
  public VulkanFenceStatus getFenceStatus(
    final VulkanFenceType fence)
    throws VulkanException
  {
    throw errorNotImplemented("getFenceStatus");
  }

  private static VulkanCallFailedException errorNotImplemented(
    final String function)
  {
    return new VulkanCallFailedException(
      0x7fff_ffff,
      function,
      "Not implemented (%s)".formatted(function)
    );
  }

  @Override
  public VulkanSubresourceLayout getImageSubresourceLayout(
    final VulkanImageType image,
    final VulkanImageSubresource image_subresource)
    throws VulkanException
  {
    throw errorNotImplemented("getImageSubresourceLayout");
  }

  @Override
  public List<VulkanPipelineType> createComputePipelines(
    final Optional<VulkanPipelineCacheType> pipeline_cache,
    final List<VulkanComputePipelineCreateInfo> pipeline_infos)
    throws VulkanException
  {
    throw errorNotImplemented("createComputePipelines");
  }
}
