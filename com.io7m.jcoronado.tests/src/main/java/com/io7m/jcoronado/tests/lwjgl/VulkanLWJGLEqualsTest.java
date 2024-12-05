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

package com.io7m.jcoronado.tests.lwjgl;

import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLBuffer;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLBufferView;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLCommandBuffer;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLCommandPool;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLDescriptorSet;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLDescriptorSetLayout;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLDeviceMemory;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLEvent;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLFence;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLFramebuffer;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLImage;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLImageView;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLInstance;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLLogicalDevice;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLPhysicalDevice;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLPipeline;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLPipelineLayout;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLQueryPool;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLQueue;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLRenderPass;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLSampler;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLSemaphoreBinary;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLSemaphoreTimeline;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLShaderModule;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public final class VulkanLWJGLEqualsTest
{
  @Test
  public void testVulkanLWJGLEvent()
  {
    EqualsVerifier.forClass(VulkanLWJGLEvent.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLDescriptorSet()
  {
    EqualsVerifier.forClass(VulkanLWJGLDescriptorSet.class)
      .withIgnoredFields(
        "ownership",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLCommandBuffer()
  {
    EqualsVerifier.forClass(VulkanLWJGLCommandBuffer.class)
      .withIgnoredFields(
        "stackInitial",
        "buffer",
        "ownership",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLCommandPool()
  {
    EqualsVerifier.forClass(VulkanLWJGLCommandPool.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLDescriptorSetLayout()
  {
    EqualsVerifier.forClass(VulkanLWJGLDescriptorSetLayout.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLFramebuffer()
  {
    EqualsVerifier.forClass(VulkanLWJGLFramebuffer.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLImage()
  {
    EqualsVerifier.forClass(VulkanLWJGLImage.class)
      .withIgnoredFields(
        "ownership",
        "deallocate",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLBuffer()
  {
    EqualsVerifier.forClass(VulkanLWJGLBuffer.class)
      .withIgnoredFields(
        "ownership",
        "deallocate",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLBufferView()
  {
    EqualsVerifier.forClass(VulkanLWJGLBufferView.class)
      .withOnlyTheseFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLImageView()
  {
    EqualsVerifier.forClass(VulkanLWJGLImageView.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "image",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLInstance()
  {
    EqualsVerifier.forClass(VulkanLWJGLInstance.class)
      .withOnlyTheseFields("handle")
      .verify();
  }

  @Test
  @Disabled("https://github.com/jqno/equalsverifier/issues/564")
  public void testVulkanLWJGLLogicalDevice(
    final @Mock VulkanLWJGLQueue queue_red,
    final @Mock VulkanLWJGLQueue queue_black)
  {
    EqualsVerifier.forClass(VulkanLWJGLLogicalDevice.class)
      .withPrefabValues(VulkanLWJGLQueue.class, queue_red, queue_black)
      .withOnlyTheseFields("physical_device", "device")
      .verify();
  }

  @Test
  @Disabled("https://github.com/jqno/equalsverifier/issues/564")
  public void testVulkanLWJGLPhysicalDevice()
  {
    EqualsVerifier.forClass(VulkanLWJGLPhysicalDevice.class)
      .withOnlyTheseFields("device")
      .verify();
  }

  @Test
  public void testVulkanLWJGLPipeline()
  {
    EqualsVerifier.forClass(VulkanLWJGLPipeline.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .verify();
  }

  @Test
  public void testVulkanLWJGLPipelineLayout()
  {
    EqualsVerifier.forClass(VulkanLWJGLPipelineLayout.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .verify();
  }

  @Test
  public void testVulkanLWJGLQueue(
    final @Mock VulkanLWJGLLogicalDevice device_red,
    final @Mock VulkanLWJGLLogicalDevice device_black)
  {
    EqualsVerifier.forClass(VulkanLWJGLQueue.class)
      .withOnlyTheseFields("handle")
      .withPrefabValues(
        VulkanLWJGLLogicalDevice.class,
        device_red,
        device_black)
      .verify();
  }

  @Test
  public void testVulkanLWJGLRenderPass()
  {
    EqualsVerifier.forClass(VulkanLWJGLRenderPass.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLSemaphoreBinary()
  {
    EqualsVerifier.forClass(VulkanLWJGLSemaphoreBinary.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLSemaphoreTimeline()
  {
    EqualsVerifier.forClass(VulkanLWJGLSemaphoreTimeline.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLShaderModule()
  {
    EqualsVerifier.forClass(VulkanLWJGLShaderModule.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLDeviceMemory()
  {
    EqualsVerifier.forClass(VulkanLWJGLDeviceMemory.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLSampler()
  {
    EqualsVerifier.forClass(VulkanLWJGLSampler.class)
      .withOnlyTheseFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLQueryPool()
  {
    EqualsVerifier.forClass(VulkanLWJGLQueryPool.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLFence()
  {
    EqualsVerifier.forClass(VulkanLWJGLFence.class)
      .withIgnoredFields(
        "ownership",
        "device",
        "closed",
        "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }
}
