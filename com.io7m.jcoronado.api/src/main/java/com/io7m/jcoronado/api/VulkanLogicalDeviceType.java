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
   * Create a pipeline.
   *
   * @param pipeline_info The pipeline creation info
   *
   * @return A pipeline
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateGraphicsPipelines")
  default VulkanPipelineType createPipeline(
    final VulkanGraphicsPipelineCreateInfo pipeline_info)
    throws VulkanException
  {
    Objects.requireNonNull(pipeline_info, "pipeline_info");
    return this.createPipelines(List.of(pipeline_info)).get(0);
  }

  /**
   * Create a set of pipelines.
   *
   * @param pipeline_infos The pipeline creation infos
   *
   * @return A pipeline
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateGraphicsPipelines")
  List<VulkanPipelineType> createPipelines(
    List<VulkanGraphicsPipelineCreateInfo> pipeline_infos)
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
   * Wait for this device to become idle.
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkDeviceWaitIdle")
  void waitIdle()
    throws VulkanException;

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

}
