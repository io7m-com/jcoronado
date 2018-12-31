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

package com.io7m.jcoronado.api;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * A reference to a logical Vulkan device.
 *
 * @see "VkDevice"
 */

public interface VulkanLogicalDeviceType extends VulkanHandleDispatchableType
{
  @VulkanAPIFunctionType(vulkanFunction = "vkDestroyDevice")
  @Override
  @VulkanExternallySynchronizedType void close()
    throws VulkanException;

  /**
   * @return The physical device to which this logical device belongs
   */

  VulkanPhysicalDeviceType physicalDevice();

  /**
   * @return The queues present on the logical device
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkGetDeviceQueue")
  List<VulkanQueueType> queues()
    throws VulkanException;

  /**
   * Find the queue with the given queue family and index.
   *
   * @param queue_family The queue family
   * @param queue_index  The queue index
   *
   * @return The matching queue, if any
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkGetDeviceQueue")
  default Optional<VulkanQueueType> queue(
    final int queue_family,
    final int queue_index)
    throws VulkanException
  {
    return this.queues()
      .stream()
      .filter(queue -> queue.queueFamilyProperties().queueFamilyIndex() == queue_family
        && queue.queueIndex() == queue_index)
      .findFirst();
  }

  /**
   * @return The enabled extensions for the instance
   *
   * @throws VulkanException On errors
   */

  Map<String, VulkanExtensionType> enabledExtensions()
    throws VulkanException;

  /**
   * Create a shader module.
   *
   * @param create_info The creation info
   *
   * @return A shader module
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateShaderModule")
  VulkanShaderModuleType createShaderModule(
    VulkanShaderModuleCreateInfo create_info)
    throws VulkanException;

  /**
   * Create a sampler.
   *
   * @param create_info The creation info
   *
   * @return A sampler
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateSampler")
  VulkanSamplerType createSampler(
    VulkanSamplerCreateInfo create_info)
    throws VulkanException;

  /**
   * Create an buffer view.
   *
   * @param info The buffer view creation info
   *
   * @return An buffer view
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateBufferView")
  VulkanBufferViewType createBufferView(
    VulkanBufferViewCreateInfo info)
    throws VulkanException;

  /**
   * Create an image view.
   *
   * @param info The image view creation info
   *
   * @return An image view
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateImageView")
  VulkanImageViewType createImageView(
    VulkanImageViewCreateInfo info)
    throws VulkanException;

  /**
   * Find and cast an extension with a given name to the correct API type.
   *
   * @param name  The extension name
   * @param clazz The intended extension type
   * @param <T>   The precise type
   *
   * @return The extension with the correct type, or nothing if the extension either did not exist
   * or did not have the right type
   *
   * @throws VulkanException On errors
   */

  @SuppressWarnings("unchecked")
  default <T extends VulkanExtensionType> Optional<T> findEnabledExtension(
    final String name,
    final Class<T> clazz)
    throws VulkanException
  {
    Objects.requireNonNull(name, "name");
    Objects.requireNonNull(clazz, "clazz");

    final var map = this.enabledExtensions();
    return Optional.ofNullable(map.get(name))
      .flatMap(ext -> {
        final var extension_class = ext.getClass();
        if (clazz.isAssignableFrom(extension_class)) {
          return Optional.of((T) ext);
        }
        return Optional.empty();
      });
  }

  /**
   * Flush mapped memory ranges.
   *
   * @param ranges The ranges
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkFlushMappedMemoryRanges")
  void flushMappedMemoryRanges(
    List<VulkanMappedMemoryRange> ranges)
    throws VulkanException;

  /**
   * Flush mapped memory ranges.
   *
   * @param range The range
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkFlushMappedMemoryRanges")
  default void flushMappedMemoryRange(
    final VulkanMappedMemoryRange range)
    throws VulkanException
  {
    this.flushMappedMemoryRanges(List.of(Objects.requireNonNull(range, "range")));
  }

  /**
   * Create a pipeline layout.
   *
   * @param info The pipeline layout creation info
   *
   * @return A pipeline layout
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreatePipelineLayout")
  VulkanPipelineLayoutType createPipelineLayout(
    VulkanPipelineLayoutCreateInfo info)
    throws VulkanException;

  /**
   * Create a descriptor set layout.
   *
   * @param info The descriptor set layout creation info
   *
   * @return A descriptor set layout
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateDescriptorSetLayout")
  VulkanDescriptorSetLayoutType createDescriptorSetLayout(
    VulkanDescriptorSetLayoutCreateInfo info)
    throws VulkanException;

  /**
   * Create a descriptor pool.
   *
   * @param info The descriptor pool creation info
   *
   * @return A descriptor pool
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateDescriptorPool")
  VulkanDescriptorPoolType createDescriptorPool(
    VulkanDescriptorPoolCreateInfo info)
    throws VulkanException;

  /**
   * Allocate descriptor sets.
   *
   * @param info The descriptor set allocation info
   *
   * @return A list of descriptor sets
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkAllocateDescriptorSets")
  List<VulkanDescriptorSetType> allocateDescriptorSets(
    VulkanDescriptorSetAllocateInfo info)
    throws VulkanException;

  /**
   * Update the contents of a descriptor set object.
   *
   * @param descriptor_writes An array of VulkanWriteDescriptorSet structures describing the
   *                          descriptor sets to write to
   * @param descriptor_copies An array of VulkanCopyDescriptorSet structures describing the
   *                          descriptor sets to copy between.
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkUpdateDescriptorSets")
  void updateDescriptorSets(
    List<VulkanWriteDescriptorSet> descriptor_writes,
    List<VulkanCopyDescriptorSet> descriptor_copies)
    throws VulkanException;

  /**
   * Create a render pass.
   *
   * @param render_pass_create_info The render pass creation info
   *
   * @return A render pass
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateRenderPass")
  VulkanRenderPassType createRenderPass(
    VulkanRenderPassCreateInfo render_pass_create_info)
    throws VulkanException;

  /**
   * Create a graphics pipeline.
   *
   * @param pipeline_info The pipeline creation info
   *
   * @return A graphics pipeline
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateGraphicsPipelines")
  default VulkanPipelineType createGraphicsPipeline(
    final VulkanGraphicsPipelineCreateInfo pipeline_info)
    throws VulkanException
  {
    return this.createGraphicsPipeline(Optional.empty(), pipeline_info);
  }

  /**
   * Create a graphics pipeline.
   *
   * @param cache         A pipeline cache
   * @param pipeline_info The pipeline creation info
   *
   * @return A graphics pipeline
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateGraphicsPipelines")
  default VulkanPipelineType createGraphicsPipeline(
    final VulkanPipelineCacheType cache,
    final VulkanGraphicsPipelineCreateInfo pipeline_info)
    throws VulkanException
  {
    return this.createGraphicsPipeline(Optional.of(cache), pipeline_info);
  }

  /**
   * Create a graphics pipeline.
   *
   * @param pipeline_cache A pipeline cache
   * @param pipeline_info  The pipeline creation info
   *
   * @return A graphics pipeline
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateGraphicsPipelines")
  default VulkanPipelineType createGraphicsPipeline(
    final Optional<VulkanPipelineCacheType> pipeline_cache,
    final VulkanGraphicsPipelineCreateInfo pipeline_info)
    throws VulkanException
  {
    Objects.requireNonNull(pipeline_info, "pipeline_info");
    return this.createGraphicsPipelines(pipeline_cache, List.of(pipeline_info)).get(0);
  }

  /**
   * Create a pipeline cache.
   *
   * @param pipeline_info The pipeline cache creation info
   *
   * @return A pipeline cache
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreatePipelineCache")
  VulkanPipelineCacheType createPipelineCache(
    VulkanPipelineCacheCreateInfo pipeline_info)
    throws VulkanException;

  /**
   * Retrieve the size of the data store for the pipeline cache.
   *
   * @param pipeline_cache The pipeline cache
   *
   * @return The size of the pipeline cache data
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkGetPipelineCacheData")
  long getPipelineCacheDataSize(
    VulkanPipelineCacheType pipeline_cache)
    throws VulkanException;

  /**
   * Retrieve the memory requirements for the given image.
   *
   * @param image The image
   *
   * @return The memory requirements
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkGetImageMemoryRequirements")
  VulkanMemoryRequirements getImageMemoryRequirements(
    VulkanImageType image)
    throws VulkanException;

  /**
   * The result of fetching data for a pipeline cache.
   */

  enum VulkanPipelineCacheDataResult
  {
    /**
     * Specifies that fetching data for a pipeline cache succeeded.
     */

    VK_PIPELINE_CACHE_SUCCESS,

    /**
     * Specifies that fetching data for a pipeline cache failed due to the buffer being too small.
     */

    VK_PIPELINE_CACHE_INCOMPLETE
  }

  /**
   * Retrieve the data store for the pipeline cache.
   *
   * @param pipeline_cache The pipeline cache
   * @param data           The buffer used to store the data
   *
   * @return A value indicating the result of the retrieval
   *
   * @throws VulkanException On errors
   * @see #getPipelineCacheDataSize(VulkanPipelineCacheType)
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkGetPipelineCacheData")
  VulkanPipelineCacheDataResult getPipelineCacheData(
    VulkanPipelineCacheType pipeline_cache,
    ByteBuffer data)
    throws VulkanException;

  /**
   * Create a set of graphics pipelines.
   *
   * @param pipeline_cache A pipeline cache
   * @param pipeline_infos The pipeline creation infos
   *
   * @return A list of graphics pipelines
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateGraphicsPipelines")
  List<VulkanPipelineType> createGraphicsPipelines(
    Optional<VulkanPipelineCacheType> pipeline_cache,
    List<VulkanGraphicsPipelineCreateInfo> pipeline_infos)
    throws VulkanException;

  /**
   * Create a set of graphics pipelines.
   *
   * @param pipeline_cache A pipeline cache
   * @param pipeline_infos The pipeline creation infos
   *
   * @return A list of graphics pipelines
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateGraphicsPipelines")
  default List<VulkanPipelineType> createGraphicsPipelines(
    final VulkanPipelineCacheType pipeline_cache,
    final List<VulkanGraphicsPipelineCreateInfo> pipeline_infos)
    throws VulkanException
  {
    return this.createGraphicsPipelines(Optional.of(pipeline_cache), pipeline_infos);
  }

  /**
   * Create a set of graphics pipelines.
   *
   * @param pipeline_infos The pipeline creation infos
   *
   * @return A list of graphics pipelines
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateGraphicsPipelines")
  default List<VulkanPipelineType> createGraphicsPipelines(
    final List<VulkanGraphicsPipelineCreateInfo> pipeline_infos)
    throws VulkanException
  {
    return this.createGraphicsPipelines(Optional.empty(), pipeline_infos);
  }

  /**
   * Create a query pool.
   *
   * @param create_info The query pool creation info
   *
   * @return A query pool
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateQueryPool")
  VulkanQueryPoolType createQueryPool(
    VulkanQueryPoolCreateInfo create_info)
    throws VulkanException;

  /**
   * Create a framebuffer.
   *
   * @param create_info The framebuffer creation info
   *
   * @return A pipeline
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateFramebuffer")
  VulkanFramebufferType createFramebuffer(
    VulkanFramebufferCreateInfo create_info)
    throws VulkanException;

  /**
   * Create a command pool.
   *
   * @param create_info The pool creation info
   *
   * @return A command pool
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateCommandPool")
  VulkanCommandPoolType createCommandPool(
    VulkanCommandPoolCreateInfo create_info)
    throws VulkanException;

  /**
   * Create a command buffer.
   *
   * @param create_info The command buffer creation info
   *
   * @return A list of command buffers
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkAllocateCommandBuffers")
  List<VulkanCommandBufferType> createCommandBuffers(
    VulkanCommandBufferCreateInfo create_info)
    throws VulkanException;

  /**
   * Create a command buffer.
   *
   * @param pool  The command buffer pool
   * @param level The command buffer level
   *
   * @return A command buffer
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkAllocateCommandBuffers")
  default VulkanCommandBufferType createCommandBuffer(
    final VulkanCommandPoolType pool,
    final VulkanCommandBufferLevel level)
    throws VulkanException
  {
    return this.createCommandBuffers(
      VulkanCommandBufferCreateInfo.builder()
        .setPool(pool)
        .setLevel(level)
        .setCount(1)
        .build())
      .get(0);
  }

  /**
   * Create a semaphore.
   *
   * @param create_info The semaphore creation info
   *
   * @return A semaphore
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateSemaphore")
  VulkanSemaphoreType createSemaphore(
    VulkanSemaphoreCreateInfo create_info)
    throws VulkanException;

  /**
   * Create a fence.
   *
   * @param create_info The fence creation info
   *
   * @return A fence
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateFence")
  VulkanFenceType createFence(
    VulkanFenceCreateInfo create_info)
    throws VulkanException;

  /**
   * Create an event.
   *
   * @param create_info The event creation info
   *
   * @return An event
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateEvent")
  VulkanEventType createEvent(
    VulkanEventCreateInfo create_info)
    throws VulkanException;

  /**
   * Reset the given fences.
   *
   * @param fences The fences
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkResetFences")
  void resetFences(
    List<@VulkanExternallySynchronizedType VulkanFenceType> fences)
    throws VulkanException;

  /**
   * Reset the given command pool.
   *
   * @param pool  The command pool
   * @param flags The reset flags
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkResetCommandPool")
  void resetCommandPool(
    @VulkanExternallySynchronizedType VulkanCommandPoolType pool,
    Set<VulkanCommandPoolResetFlag> flags)
    throws VulkanException;

  /**
   * Reset the given command pool.
   *
   * @param pool The command pool
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkResetCommandPool")
  default void resetCommandPool(
    final @VulkanExternallySynchronizedType VulkanCommandPoolType pool)
    throws VulkanException
  {
    this.resetCommandPool(pool, Set.of());
  }

  /**
   * Reset the given descriptor pool.
   *
   * @param pool  The descriptor pool
   * @param flags The reset flags
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkResetDescriptorPool")
  void resetDescriptorPool(
    @VulkanExternallySynchronizedType VulkanDescriptorPoolType pool,
    Set<VulkanDescriptorPoolResetFlag> flags)
    throws VulkanException;

  /**
   * Reset the given descriptor pool.
   *
   * @param pool The descriptor pool
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkResetDescriptorPool")
  default void resetDescriptorPool(
    @VulkanExternallySynchronizedType final VulkanDescriptorPoolType pool)
    throws VulkanException
  {
    this.resetDescriptorPool(pool, Set.of());
  }

  /**
   * Wait for this device to become idle.
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkDeviceWaitIdle")
  void waitIdle()
    throws VulkanException;

  /**
   * The result of waiting.
   */

  enum VulkanWaitStatus
  {
    /**
     * Specifies that waiting for a condition succeeded (the condition became  {@code true}).
     */

    VK_WAIT_SUCCEEDED,

    /**
     * Specifies that waiting for a condition timed out (the condition did not become  {@code
     * true}).
     */

    VK_WAIT_TIMED_OUT
  }

  /**
   * Wait for one or more fences to become signaled.
   *
   * @param fences        The fences upon which to wait
   * @param wait_all      {@code true} if all fences must become signalled to stop waiting, {@code
   *                      false} if any fence can become signalled
   * @param timeout_nanos The timeout period in units of nanoseconds.
   *
   * @return A value indicating whether waiting succeeded or timed out
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkWaitForFences")
  VulkanWaitStatus waitForFences(
    List<VulkanFenceType> fences,
    boolean wait_all,
    long timeout_nanos)
    throws VulkanException;

  /**
   * Wait for a fence to become signaled.
   *
   * @param fence         The fences upon which to wait
   * @param timeout_nanos The timeout period in units of nanoseconds.
   *
   * @return A value indicating whether waiting succeeded or timed out
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkWaitForFences")
  default VulkanWaitStatus waitForFence(
    final VulkanFenceType fence,
    final long timeout_nanos)
    throws VulkanException
  {
    return this.waitForFences(List.of(fence), true, timeout_nanos);
  }

  /**
   * Create a buffer.
   *
   * @param create_info The buffer creation info
   *
   * @return A buffer
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateBuffer")
  VulkanBufferType createBuffer(
    VulkanBufferCreateInfo create_info)
    throws VulkanException;

  /**
   * Create an image.
   *
   * @param create_info The image creation info
   *
   * @return A image
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateImage")
  VulkanImageType createImage(
    VulkanImageCreateInfo create_info)
    throws VulkanException;

  /**
   * Retrieve the memory requirements for the given buffer.
   *
   * @param buffer The buffer
   *
   * @return The memory requirements
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkGetBufferMemoryRequirements")
  VulkanMemoryRequirements getBufferMemoryRequirements(
    VulkanBufferType buffer)
    throws VulkanException;

  /**
   * Allocate device memory.
   *
   * @param info The allocation info
   *
   * @return Allocated memory
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkAllocateMemory")
  VulkanDeviceMemoryType allocateMemory(
    VulkanMemoryAllocateInfo info)
    throws VulkanException;

  /**
   * Bind device memory to a buffer object.
   *
   * @param buffer        The logical device that owns the buffer and memory
   * @param device_memory The device memory to attach
   * @param offset        The start offset of the region of memory which is to be bound to the
   *                      buffer. The number of bytes returned in the VkMemoryRequirements::size
   *                      member in memory, starting from memoryOffset bytes, will be bound to the
   *                      specified buffer.
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkBindBufferMemory")
  void bindBufferMemory(
    @VulkanExternallySynchronizedType VulkanBufferType buffer,
    VulkanDeviceMemoryType device_memory,
    long offset)
    throws VulkanException;

  /**
   * Bind device memory to a image object.
   *
   * @param image         The logical device that owns the image and memory
   * @param device_memory The device memory to attach
   * @param offset        The start offset of the region of memory which is to be bound to the
   *                      image. The number of bytes returned in the VkMemoryRequirements::size
   *                      member in memory, starting from memoryOffset bytes, will be bound to the
   *                      specified image.
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkBindImageMemory")
  void bindImageMemory(
    @VulkanExternallySynchronizedType VulkanImageType image,
    VulkanDeviceMemoryType device_memory,
    long offset)
    throws VulkanException;

  /**
   * Map a memory object into the application address space.
   *
   * @param memory The device memory object to be mapped
   * @param offset A zero-based byte offset from the beginning of the memory object
   * @param size   The size of the memory range to map, or VK_WHOLE_SIZE to map from offset to the
   *               end of the allocation
   * @param flags  The flags
   *
   * @return Mapped memory
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkMapMemory")
  VulkanMappedMemoryType mapMemory(
    @VulkanExternallySynchronizedType VulkanDeviceMemoryType memory,
    long offset,
    long size,
    Set<VulkanMemoryMapFlag> flags)
    throws VulkanException;

  /**
   * Combine the data stores of pipeline caches.
   *
   * @param output The output cache
   * @param caches The input caches
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkMergePipelineCaches")
  void mergePipelineCaches(
    List<VulkanPipelineCacheType> caches,
    VulkanPipelineCacheType output)
    throws VulkanException;

  /**
   * Set an event object to signaled state.
   *
   * @param event The event
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkSetEvent")
  @VulkanExternallySynchronizedType
  void setEvent(
    VulkanEventType event)
    throws VulkanException;

  /**
   * Reset an event object to non-signaled state.
   *
   * @param event The event
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkResetEvent")
  @VulkanExternallySynchronizedType
  void resetEvent(
    VulkanEventType event)
    throws VulkanException;

  /**
   * The status of an event.
   */

  enum VulkanEventStatus
  {
    /**
     * The event is signaled.
     */

    VK_EVENT_SET,

    /**
     * The event is unsignaled.
     */

    VK_EVENT_RESET
  }

  /**
   * Retrieve the status of an event object.
   *
   * @param event The event
   *
   * @return The event status
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkGetEventStatus")
  @VulkanExternallySynchronizedType
  VulkanEventStatus getEventStatus(
    VulkanEventType event)
    throws VulkanException;

  /**
   * The status of an event.
   */

  enum VulkanFenceStatus
  {
    /**
     * The fence is signaled.
     */

    VK_FENCE_SIGNALLED,

    /**
     * The fence is unsignaled.
     */

    VK_FENCE_UNSIGNALLED
  }

  /**
   * Retrieve the status of a fence object.
   *
   * @param fence The fence
   *
   * @return The fence status
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkGetFenceStatus")
  @VulkanExternallySynchronizedType
  VulkanFenceStatus getFenceStatus(
    VulkanFenceType fence)
    throws VulkanException;

  /**
   * Retrieve information about an image subresource.
   *
   * @param image             The image
   * @param image_subresource A structure selecting a specific image for the image subresource
   *
   * @return The subresource layout
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkGetImageSubresourceLayout")
  @VulkanExternallySynchronizedType
  VulkanSubresourceLayout getImageSubresourceLayout(
    VulkanImageType image,
    VulkanImageSubresource image_subresource)
    throws VulkanException;

  /**
   * Create a compute pipeline.
   *
   * @param pipeline_info The pipeline creation info
   *
   * @return A compute pipeline
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateComputePipelines")
  default VulkanPipelineType createComputePipeline(
    final VulkanComputePipelineCreateInfo pipeline_info)
    throws VulkanException
  {
    return this.createComputePipeline(Optional.empty(), pipeline_info);
  }

  /**
   * Create a compute pipeline.
   *
   * @param cache         A pipeline cache
   * @param pipeline_info The pipeline creation info
   *
   * @return A compute pipeline
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateComputePipelines")
  default VulkanPipelineType createComputePipeline(
    final VulkanPipelineCacheType cache,
    final VulkanComputePipelineCreateInfo pipeline_info)
    throws VulkanException
  {
    return this.createComputePipeline(Optional.of(cache), pipeline_info);
  }

  /**
   * Create a compute pipeline.
   *
   * @param pipeline_cache A pipeline cache
   * @param pipeline_info  The pipeline creation info
   *
   * @return A compute pipeline
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateComputePipelines")
  default VulkanPipelineType createComputePipeline(
    final Optional<VulkanPipelineCacheType> pipeline_cache,
    final VulkanComputePipelineCreateInfo pipeline_info)
    throws VulkanException
  {
    Objects.requireNonNull(pipeline_info, "pipeline_info");
    return this.createComputePipelines(pipeline_cache, List.of(pipeline_info)).get(0);
  }

  /**
   * Create a set of compute pipelines.
   *
   * @param pipeline_cache A pipeline cache
   * @param pipeline_infos The pipeline creation infos
   *
   * @return A list of compute pipelines
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateComputePipelines")
  List<VulkanPipelineType> createComputePipelines(
    Optional<VulkanPipelineCacheType> pipeline_cache,
    List<VulkanComputePipelineCreateInfo> pipeline_infos)
    throws VulkanException;

  /**
   * Create a set of compute pipelines.
   *
   * @param pipeline_cache A pipeline cache
   * @param pipeline_infos The pipeline creation infos
   *
   * @return A list of compute pipelines
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateComputePipelines")
  default List<VulkanPipelineType> createComputePipelines(
    final VulkanPipelineCacheType pipeline_cache,
    final List<VulkanComputePipelineCreateInfo> pipeline_infos)
    throws VulkanException
  {
    return this.createComputePipelines(Optional.of(pipeline_cache), pipeline_infos);
  }

  /**
   * Create a set of compute pipelines.
   *
   * @param pipeline_infos The pipeline creation infos
   *
   * @return A list of compute pipelines
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateComputePipelines")
  default List<VulkanPipelineType> createComputePipelines(
    final List<VulkanComputePipelineCreateInfo> pipeline_infos)
    throws VulkanException
  {
    return this.createComputePipelines(Optional.empty(), pipeline_infos);
  }
}
