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
import com.io7m.jcoronado.lwjgl.VMALWJGLAllocatorProvider;
import com.io7m.jcoronado.vma.VMAAllocationCreateInfo;
import com.io7m.jcoronado.vma.VMAAllocatorCreateInfo;
import com.io7m.jcoronado.vma.VMAAllocatorType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.OptionalInt;
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_SHADER_DEVICE_ADDRESS_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_STORAGE_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanMemoryAllocateFlag.VK_MEMORY_ALLOCATE_DEVICE_ADDRESS_BIT;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE;
import static com.io7m.jcoronado.vma.VMAMemoryUsage.VMA_MEMORY_USAGE_GPU_ONLY;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public abstract class VulkanVMAContract extends VulkanOnDeviceContract
{
  private VulkanPhysicalDeviceType physical_device;
  private VulkanLogicalDeviceType device;
  private VMAAllocatorType vmaAllocator;

  protected abstract VulkanInstanceType instance();

  protected abstract VulkanPhysicalDeviceType createPhysicalDevice()
    throws VulkanException;

  protected abstract VulkanLogicalDeviceType createLogicalDevice(
    VulkanPhysicalDeviceType device)
    throws VulkanException;

  protected abstract VMAAllocatorType createAllocator(
    VulkanLogicalDeviceType device)
    throws VulkanException;

  @BeforeEach
  public void testSetup()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    this.physical_device =
      this.createPhysicalDevice();
    this.device =
      this.createLogicalDevice(this.physical_device);
    this.vmaAllocator =
      this.createAllocator(this.device);
  }

  @AfterEach
  public void tearDown()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    this.vmaAllocator.close();
    this.device.close();
    this.physical_device.close();
    this.instance().close();
  }

  @Test
  public final void testBufferCreate()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var vmaInfo =
      VMAAllocationCreateInfo.builder()
        .setUsage(VMA_MEMORY_USAGE_GPU_ONLY)
        .addRequiredFlags(VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT)
        .setMemoryTypeBits(0L)
        .build();

    final var vectorSize = 4L * 4L;
    final var vertexSize = 2L * vectorSize;
    final var vertexCount = 3L;
    final var bufferSize = vertexCount * vertexSize;

    final var storageBufferInfo =
      VulkanBufferCreateInfo.builder()
        .setSize(bufferSize)
        .addUsageFlags(VK_BUFFER_USAGE_STORAGE_BUFFER_BIT)
        .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
        .build();

    final var allocation =
      this.vmaAllocator.createBuffer(vmaInfo, storageBufferInfo);

    allocation.allocation().close();
  }
}
