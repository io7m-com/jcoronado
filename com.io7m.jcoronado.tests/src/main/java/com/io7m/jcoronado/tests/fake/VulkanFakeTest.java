/*
 * Copyright © 2026 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

package com.io7m.jcoronado.tests.fake;

import com.io7m.jcoronado.api.VulkanApplicationInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferLevel;
import com.io7m.jcoronado.api.VulkanCommandBufferSubmitInfo;
import com.io7m.jcoronado.api.VulkanCommandPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandPoolType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyIndex;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueIndex;
import com.io7m.jcoronado.api.VulkanSubmitInfo;
import com.io7m.jcoronado.fake.VFakeFence;
import com.io7m.jcoronado.fake.VFakeInstances;
import com.io7m.jcoronado.fake.VFakeLogicalDevice;
import com.io7m.jcoronado.fake.VFakeQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.io7m.jcoronado.api.VulkanCommandBufferUsageFlag.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class VulkanFakeTest
{
  private VFakeInstances instances;
  private VulkanInstanceType instance;
  private VulkanPhysicalDeviceType physDevice;
  private VFakeLogicalDevice device;
  private VulkanCommandPoolType commandPool;

  @BeforeEach
  public void setup()
    throws Exception
  {
    this.instances =
      new VFakeInstances();
    this.instance =
      this.instances.createInstance(
        VulkanInstanceCreateInfo.builder()
          .setApplicationInfo(
            VulkanApplicationInfo.builder()
              .setApplicationName("com.io7m.jcoronado.tests")
              .setApplicationVersion(1)
              .setEngineName("com.io7m.jcoronado.tests")
              .setEngineVersion(1)
              .setVulkanAPIVersion(1)
              .build()
          )
          .build(),
        Optional.empty()
      );

    this.physDevice =
      this.instance.physicalDevices().get(0);
    this.device =
      (VFakeLogicalDevice) this.physDevice.createLogicalDevice(
        VulkanLogicalDeviceCreateInfo.builder()
          .build()
      );
    this.commandPool =
      this.device.createCommandPool(
        VulkanCommandPoolCreateInfo.builder()
          .setQueueFamilyIndex(VulkanQueueFamilyIndex.ignored())
          .build()
      );
  }

  @Test
  public void testFencesAwaitAll()
    throws Exception
  {
    final VFakeFence fence =
      (VFakeFence) this.device.createFence();

    final var queue =
      new VFakeQueue(
        this.device,
        VulkanQueueFamilyProperties.builder()
          .setQueueFamilyIndex(new VulkanQueueFamilyIndex(0))
          .setTimestampValidBits(32)
          .setMinImageTransferGranularity(VulkanExtent3D.of(32, 32, 32))
          .setQueueCount(1)
          .build(),
        new VulkanQueueIndex(0)
      );

    final var buffer =
      this.device.createCommandBuffer(
        this.commandPool,
        VulkanCommandBufferLevel.VK_COMMAND_BUFFER_LEVEL_PRIMARY
      );

    buffer.beginCommandBuffer(VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);
    buffer.endCommandBuffer();

    final var signalled = new AtomicBoolean(false);
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      executor.execute(() -> {
        try {
          this.device.waitForFence(fence, Long.MAX_VALUE);
          signalled.set(true);
        } catch (final VulkanException e) {
          throw new RuntimeException(e);
        }
      });

      queue.submit(
        List.of(
          VulkanSubmitInfo.builder()
            .addCommandBuffers(
              VulkanCommandBufferSubmitInfo.builder()
                .setCommandBuffer(buffer)
                .build()
            )
            .build()
        ),
        Optional.of(fence)
      );
    }

    this.device.waitForFences(List.of(fence), true, Long.MAX_VALUE);
    assertTrue(signalled.get());
  }

  @Test
  public void testFencesAwaitAny()
    throws Exception
  {
    final VFakeFence fence =
      (VFakeFence) this.device.createFence();

    final var queue =
      new VFakeQueue(
        this.device,
        VulkanQueueFamilyProperties.builder()
          .setQueueFamilyIndex(new VulkanQueueFamilyIndex(0))
          .setTimestampValidBits(32)
          .setMinImageTransferGranularity(VulkanExtent3D.of(32, 32, 32))
          .setQueueCount(1)
          .build(),
        new VulkanQueueIndex(0)
      );

    final var buffer =
      this.device.createCommandBuffer(
        this.commandPool,
        VulkanCommandBufferLevel.VK_COMMAND_BUFFER_LEVEL_PRIMARY
      );

    buffer.beginCommandBuffer(VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);
    buffer.endCommandBuffer();

    final var signalled = new AtomicBoolean(false);
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      executor.execute(() -> {
        try {
          this.device.waitForFence(fence, Long.MAX_VALUE);
          signalled.set(true);
        } catch (final VulkanException e) {
          throw new RuntimeException(e);
        }
      });

      queue.submit(
        List.of(
          VulkanSubmitInfo.builder()
            .addCommandBuffers(
              VulkanCommandBufferSubmitInfo.builder()
                .setCommandBuffer(buffer)
                .build()
            )
            .build()
        ),
        Optional.of(fence)
      );
    }

    this.device.waitForFences(List.of(fence), false, Long.MAX_VALUE);
    assertTrue(signalled.get());
  }

  @Test
  public void testFencesAwaitAllTimeout()
    throws Exception
  {
    final VFakeFence fence =
      (VFakeFence) this.device.createFence();

    final var queue =
      new VFakeQueue(
        this.device,
        VulkanQueueFamilyProperties.builder()
          .setQueueFamilyIndex(new VulkanQueueFamilyIndex(0))
          .setTimestampValidBits(32)
          .setMinImageTransferGranularity(VulkanExtent3D.of(32, 32, 32))
          .setQueueCount(1)
          .build(),
        new VulkanQueueIndex(0)
      );

    final var buffer =
      this.device.createCommandBuffer(
        this.commandPool,
        VulkanCommandBufferLevel.VK_COMMAND_BUFFER_LEVEL_PRIMARY
      );

    buffer.beginCommandBuffer(VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);
    buffer.endCommandBuffer();

    this.device.waitForFences(List.of(fence), true, 1000L);
    assertFalse(fence.isSignalled());
  }

  @Test
  public void testFencesAwaitAnyTimeout()
    throws Exception
  {
    final VFakeFence fence =
      (VFakeFence) this.device.createFence();

    final var queue =
      new VFakeQueue(
        this.device,
        VulkanQueueFamilyProperties.builder()
          .setQueueFamilyIndex(new VulkanQueueFamilyIndex(0))
          .setTimestampValidBits(32)
          .setMinImageTransferGranularity(VulkanExtent3D.of(32, 32, 32))
          .setQueueCount(1)
          .build(),
        new VulkanQueueIndex(0)
      );

    final var buffer =
      this.device.createCommandBuffer(
        this.commandPool,
        VulkanCommandBufferLevel.VK_COMMAND_BUFFER_LEVEL_PRIMARY
      );

    buffer.beginCommandBuffer(VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);
    buffer.endCommandBuffer();

    this.device.waitForFences(List.of(fence), false, 1000L);
    assertFalse(fence.isSignalled());
  }
}
