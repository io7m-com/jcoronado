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

import com.io7m.jcoronado.lwjgl.VulkanLWJGLCommandBuffer;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLCommandPool;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLDescriptorSetLayout;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLFramebuffer;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLImage;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLImageView;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstance;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLLogicalDevice;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLPhysicalDevice;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLPipeline;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLPipelineLayout;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLQueue;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLRenderPass;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLSemaphore;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLShaderModule;
import mockit.Mocked;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public final class VulkanLWJGLEqualsTest
{
  @Test
  public void testVulkanLWJGLCommandBuffer()
  {
    EqualsVerifier.forClass(VulkanLWJGLCommandBuffer.class)
      .withIgnoredFields("stack_initial", "ownership", "closed", "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLCommandPool()
  {
    EqualsVerifier.forClass(VulkanLWJGLCommandPool.class)
      .withIgnoredFields("ownership", "device", "closed", "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLDescriptorSetLayout()
  {
    EqualsVerifier.forClass(VulkanLWJGLDescriptorSetLayout.class)
      .withIgnoredFields("ownership", "device", "closed", "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLFramebuffer()
  {
    EqualsVerifier.forClass(VulkanLWJGLFramebuffer.class)
      .withIgnoredFields("ownership", "device", "closed", "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLImage()
  {
    EqualsVerifier.forClass(VulkanLWJGLImage.class)
      .withIgnoredFields("ownership", "closed", "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLImageView()
  {
    EqualsVerifier.forClass(VulkanLWJGLImageView.class)
      .withIgnoredFields("ownership", "device", "closed", "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLInstance()
  {
    EqualsVerifier.forClass(VulkanLWJGLInstance.class)
      .withOnlyTheseFields("instance")
      .verify();
  }

  @Test
  public void testVulkanLWJGLLogicalDevice(
    final @Mocked VulkanLWJGLQueue queue_red,
    final @Mocked VulkanLWJGLQueue queue_black)
  {
    EqualsVerifier.forClass(VulkanLWJGLLogicalDevice.class)
      .withPrefabValues(VulkanLWJGLQueue.class, queue_red, queue_black)
      .withOnlyTheseFields("physical_device", "device")
      .verify();
  }

  @Test
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
      .withIgnoredFields("ownership", "device", "closed", "host_allocator_proxy")
      .verify();
  }

  @Test
  public void testVulkanLWJGLPipelineLayout()
  {
    EqualsVerifier.forClass(VulkanLWJGLPipelineLayout.class)
      .withIgnoredFields("ownership", "device", "closed", "host_allocator_proxy")
      .verify();
  }

  @Test
  public void testVulkanLWJGLQueue(
    final @Mocked VulkanLWJGLLogicalDevice device_red,
    final @Mocked VulkanLWJGLLogicalDevice device_black)
  {
    EqualsVerifier.forClass(VulkanLWJGLQueue.class)
      .withOnlyTheseFields("queue_index", "queue", "properties")
      .withPrefabValues(VulkanLWJGLLogicalDevice.class, device_red, device_black)
      .verify();
  }

  @Test
  public void testVulkanLWJGLRenderPass()
  {
    EqualsVerifier.forClass(VulkanLWJGLRenderPass.class)
      .withIgnoredFields("ownership", "device", "closed", "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLSemaphore()
  {
    EqualsVerifier.forClass(VulkanLWJGLSemaphore.class)
      .withIgnoredFields("ownership", "device", "closed", "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }

  @Test
  public void testVulkanLWJGLShaderModule()
  {
    EqualsVerifier.forClass(VulkanLWJGLShaderModule.class)
      .withIgnoredFields("ownership", "device", "closed", "host_allocator_proxy")
      .withNonnullFields("handle")
      .verify();
  }
}
