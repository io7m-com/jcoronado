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

import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueType;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final class VulkanLWJGLLogicalDevice
  extends VulkanLWJGLObject implements VulkanLogicalDeviceType
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
    final VulkanLWJGLExtensionsRegistry in_extensions_registry,
    final Map<String, VulkanExtensionType> in_extensions_enabled,
    final VulkanLWJGLPhysicalDevice in_physical_device,
    final VkDevice in_device,
    final VulkanLogicalDeviceCreateInfo in_creation)
    throws VulkanException
  {
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
  {
    return this.queues_read;
  }

  @Override
  public Map<String, VulkanExtensionType> enabledExtensions()
  {
    return this.extensions_enabled_read_only;
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
