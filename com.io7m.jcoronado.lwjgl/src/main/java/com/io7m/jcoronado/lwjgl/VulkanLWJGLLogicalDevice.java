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

import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanComponentMapping;
import com.io7m.jcoronado.api.VulkanDestroyedException;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionType;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import com.io7m.jcoronado.api.VulkanImageViewType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanShaderModuleCreateInfo;
import com.io7m.jcoronado.api.VulkanShaderModuleType;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkComponentMapping;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkImageSubresourceRange;
import org.lwjgl.vulkan.VkImageViewCreateInfo;
import org.lwjgl.vulkan.VkQueue;
import org.lwjgl.vulkan.VkShaderModuleCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final class VulkanLWJGLLogicalDevice
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

  VulkanLWJGLLogicalDevice(
    final Map<String, VulkanExtensionType> in_extensions_enabled,
    final VulkanLWJGLPhysicalDevice in_physical_device,
    final VkDevice in_device,
    final VulkanLogicalDeviceCreateInfo in_creation)
    throws VulkanException
  {
    super(Ownership.USER_OWNED);

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
      new ArrayList<>();
    this.queues_read =
      Collections.unmodifiableList(this.queues);
    this.stack_initial =
      MemoryStack.create();

    this.initializeQueues();
  }

  private static VkImageSubresourceRange packSubresourceRange(
    final MemoryStack stack,
    final VulkanImageSubresourceRange range)
  {
    return VkImageSubresourceRange.mallocStack(stack)
      .aspectMask(VulkanEnumMaps.packValues(range.flags()))
      .baseArrayLayer(range.baseArrayLayer())
      .baseMipLevel(range.baseMipLevel())
      .layerCount(range.layerCount())
      .levelCount(range.levelCount());
  }

  private static VkComponentMapping packComponentMapping(
    final MemoryStack stack,
    final VulkanComponentMapping components)
  {
    return VkComponentMapping.mallocStack(stack)
      .set(
        components.r().value(),
        components.g().value(),
        components.b().value(),
        components.a().value());
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
    return "[VulkanLWJGLLogicalDevice]";
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
        VkShaderModuleCreateInfo.mallocStack(stack)
          .sType(VK10.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO)
          .pNext(0L)
          .pCode(create_info.data())
          .flags(VulkanEnumMaps.packValues(create_info.flags()));

      final long[] results = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateShaderModule(this.device, vk_create_info, null, results),
        "vkCreateShaderModule");

      final long shader_module = results[0];
      if (LOG.isDebugEnabled()) {
        LOG.debug("created shader module: 0x{}", Long.toUnsignedString(shader_module, 16));
      }
      return new VulkanLWJGLShaderModule(Ownership.USER_OWNED, this.device, shader_module);
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
        VkImageViewCreateInfo.mallocStack(stack)
          .sType(VK10.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO)
          .components(packComponentMapping(stack, info.components()))
          .flags(VulkanEnumMaps.packValues(info.flags()))
          .format(info.format().value())
          .image(image.handle())
          .subresourceRange(packSubresourceRange(stack, info.subresourceRange()))
          .viewType(info.viewType().value());

      final long[] view = new long[1];
      VulkanChecks.checkReturnCode(
        VK10.vkCreateImageView(this.device, create_info, null, view),
        "vkCreateImageView");

      return new VulkanLWJGLImageView(this.device, view[0]);
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
    LOG.debug("destroying logical device: {}", this.device);
    VK10.vkDestroyDevice(this.device, null);
  }
}
