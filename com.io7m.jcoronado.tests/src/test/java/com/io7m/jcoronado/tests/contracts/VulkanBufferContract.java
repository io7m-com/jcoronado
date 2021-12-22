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

package com.io7m.jcoronado.tests.contracts;

import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanBufferType;
import com.io7m.jcoronado.api.VulkanDeviceMemoryType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanMemoryAllocateInfo;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE;

public abstract class VulkanBufferContract extends VulkanOnDeviceContract
{
  private VulkanPhysicalDeviceType physical_device;
  private VulkanLogicalDeviceType device;

  protected abstract VulkanInstanceType instance();

  protected abstract VulkanPhysicalDeviceType createPhysicalDevice()
    throws VulkanException;

  protected abstract VulkanLogicalDeviceType createLogicalDevice(
    VulkanPhysicalDeviceType device)
    throws VulkanException;

  @BeforeEach
  public void testSetup()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    this.physical_device = this.createPhysicalDevice();
    this.device = this.createLogicalDevice(this.physical_device);
  }

  @AfterEach
  public void tearDown()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    this.device.close();
    this.physical_device.close();
    this.instance().close();
  }

  @Test
  public final void testBufferCreate()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var logger = this.logger();

    final var buffer_info =
      VulkanBufferCreateInfo.builder()
        .addQueueFamilyIndices(
          this.device.queues()
            .get(0)
            .queueFamilyProperties()
            .queueFamilyIndex())
        .addUsageFlags(VK_BUFFER_USAGE_VERTEX_BUFFER_BIT)
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .setSize(100L)
        .build();

    final VulkanBufferType escaped_buffer;
    final VulkanDeviceMemoryType escaped_memory;

    try (var buffer = this.device.createBuffer(buffer_info)) {
      Assertions.assertFalse(buffer.isClosed(), "Buffer is destroyed");
      escaped_buffer = buffer;

      logger.debug("buffer: {}", buffer);

      final var requirements = this.device.getBufferMemoryRequirements(buffer);
      logger.debug("buffer requirements: {}", requirements);

      final var memory_type =
        this.physical_device.memory().findSuitableMemoryType(
          requirements,
          Set.of());

      final var memory_info =
        VulkanMemoryAllocateInfo.builder()
          .setMemoryTypeIndex(memory_type.heapIndex())
          .setSize(requirements.size())
          .build();

      try (var memory = this.device.allocateMemory(memory_info)) {
        Assertions.assertFalse(memory.isClosed(), "Memory is destroyed");
        escaped_memory = memory;

        logger.debug("memory: {}", memory);
        this.device.bindBufferMemory(buffer, memory, 0L);
      }
    }

    Assertions.assertTrue(escaped_buffer.isClosed(), "Buffer is destroyed");
    Assertions.assertTrue(escaped_memory.isClosed(), "Memory is destroyed");
  }
}
