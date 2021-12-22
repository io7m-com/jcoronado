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
import com.io7m.jcoronado.api.VulkanBufferUsageFlag;
import com.io7m.jcoronado.api.VulkanBufferViewCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanFormat;
import com.io7m.jcoronado.api.VulkanImageCreateInfo;
import com.io7m.jcoronado.api.VulkanImageKind;
import com.io7m.jcoronado.api.VulkanImageLayout;
import com.io7m.jcoronado.api.VulkanImageTiling;
import com.io7m.jcoronado.api.VulkanImageUsageFlag;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanMemoryAllocateInfo;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanSampleCountFlag;
import com.io7m.jcoronado.api.VulkanSharingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.io7m.jcoronado.api.VulkanCommandBufferLevel.VK_COMMAND_BUFFER_LEVEL_PRIMARY;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class VulkanLogicalDeviceContract extends VulkanOnDeviceContract
{
  private VulkanPhysicalDeviceType physicalDevice;
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

    this.physicalDevice =
      this.createPhysicalDevice();
    this.device =
      this.createLogicalDevice(this.physicalDevice);
  }

  @AfterEach
  public void tearDown()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    this.device.close();
    this.physicalDevice.close();
    this.instance().close();
  }

  @Test
  public final void testLogicalDevice()
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    Assertions.assertEquals(this.physicalDevice, this.device.physicalDevice());
    assertFalse(this.physicalDevice.isClosed(), "Device is not closed");
  }

  /**
   * Try creating an image.
   *
   * @throws VulkanException On errors
   */

  @Test
  public final void testCreateImage()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var image = this.device.createImage(
      VulkanImageCreateInfo.builder()
        .setArrayLayers(1)
        .setSamples(Set.of(VulkanSampleCountFlag.VK_SAMPLE_COUNT_8_BIT))
        .setExtent(VulkanExtent3D.of(256, 256, 1))
        .setFormat(VulkanFormat.VK_FORMAT_R8_UNORM)
        .setImageType(VulkanImageKind.VK_IMAGE_TYPE_2D)
        .setInitialLayout(VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED)
        .setMipLevels(1)
        .setSharingMode(VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE)
        .setTiling(VulkanImageTiling.VK_IMAGE_TILING_OPTIMAL)
        .setUsage(Set.of(VulkanImageUsageFlag.VK_IMAGE_USAGE_SAMPLED_BIT))
        .build()
    )) {
      assertFalse(image.isClosed());
    }
  }

  /**
   * Try creating a buffer.
   *
   * @throws VulkanException On errors
   */

  @Test
  public final void testCreateBuffer()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var buffer = this.device.createBuffer(
      VulkanBufferCreateInfo.builder()
        .setSharingMode(VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE)
        .setSize(128L)
        .setUsageFlags(Set.of(VulkanBufferUsageFlag.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT))
        .build()
    )) {
      assertFalse(buffer.isClosed());
    }
  }

  /**
   * Try creating a buffer view.
   *
   * @throws VulkanException On errors
   */

  @Test
  public final void testCreateBufferView()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    try (var buffer = this.device.createBuffer(
      VulkanBufferCreateInfo.builder()
        .setSharingMode(VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE)
        .setSize(128L)
        .setUsageFlags(Set.of(VulkanBufferUsageFlag.VK_BUFFER_USAGE_STORAGE_TEXEL_BUFFER_BIT))
        .build()
    )) {
      final var bufferMemoryRequirements =
        this.device.getBufferMemoryRequirements(buffer);

      final var bufferMemoryType =
        this.physicalDevice.memory()
          .findSuitableMemoryType(
            bufferMemoryRequirements,
            Set.of(
              VK_MEMORY_PROPERTY_HOST_COHERENT_BIT,
              VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT));

      try (var memory =
             this.device.allocateMemory(
               VulkanMemoryAllocateInfo.builder()
                 .setSize(128L)
                 .setMemoryTypeIndex(bufferMemoryType.heapIndex())
                 .build())) {

        this.device.bindBufferMemory(buffer, memory, 0L);

        try (var view = this.device.createBufferView(
          VulkanBufferViewCreateInfo.builder()
            .setFormat(VulkanFormat.VK_FORMAT_R8_UNORM)
            .setBuffer(buffer)
            .setOffset(0L)
            .setRange(1L)
            .build()
        )) {
          assertFalse(view.isClosed());
          assertEquals(buffer, view.buffer());
        }
      }
    }
  }

  /**
   * Try recording a simple command buffer.
   *
   * @throws VulkanException On errors
   */

  @Test
  public final void testRecordCommandBuffer()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var queue =
      this.device.queues()
        .get(0);

    try (var pool = this.device.createCommandPool(
      VulkanCommandPoolCreateInfo.builder()
        .setQueueFamilyIndex(queue.queueIndex())
        .build())) {

      try (var buffer =
             this.device.createCommandBuffer(
               pool,
               VK_COMMAND_BUFFER_LEVEL_PRIMARY)) {
        buffer.beginCommandBuffer();
        buffer.endCommandBuffer();
      }
    }
  }
}
