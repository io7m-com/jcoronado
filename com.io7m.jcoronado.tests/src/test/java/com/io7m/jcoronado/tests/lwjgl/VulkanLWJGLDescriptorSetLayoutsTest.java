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

import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutBinding;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutCreateInfo;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLDescriptorSetLayouts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.LongBuffer;
import java.util.List;
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanDescriptorType.VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER;
import static com.io7m.jcoronado.api.VulkanDescriptorType.VK_DESCRIPTOR_TYPE_STORAGE_BUFFER;
import static com.io7m.jcoronado.api.VulkanDescriptorType.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER;
import static com.io7m.jcoronado.api.VulkanShaderStageFlag.VK_SHADER_STAGE_FRAGMENT_BIT;
import static com.io7m.jcoronado.api.VulkanShaderStageFlag.VK_SHADER_STAGE_GEOMETRY_BIT;
import static com.io7m.jcoronado.api.VulkanShaderStageFlag.VK_SHADER_STAGE_VERTEX_BIT;

public final class VulkanLWJGLDescriptorSetLayoutsTest
{
  private static final Logger LOG = LoggerFactory.getLogger(VulkanLWJGLDescriptorSetLayoutsTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testDescriptorSetLayoutCreateInfo_0()
    throws Exception
  {
    final var binding_0 =
      VulkanDescriptorSetLayoutBinding.of(
        23,
        VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER,
        1,
        Set.of(VK_SHADER_STAGE_VERTEX_BIT),
        List.of());

    final var binding_1 =
      VulkanDescriptorSetLayoutBinding.of(
        24,
        VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER,
        1,
        Set.of(VK_SHADER_STAGE_FRAGMENT_BIT),
        List.of());

    final var binding_2 =
      VulkanDescriptorSetLayoutBinding.of(
        25,
        VK_DESCRIPTOR_TYPE_STORAGE_BUFFER,
        1,
        Set.of(VK_SHADER_STAGE_GEOMETRY_BIT),
        List.of());

    final var create_info =
      VulkanDescriptorSetLayoutCreateInfo.builder()
        .addBindings(binding_0)
        .addBindings(binding_1)
        .addBindings(binding_2)
        .build();

    final var packed =
      VulkanLWJGLDescriptorSetLayouts.packDescriptorSetLayoutCreateInfo(this.stack, create_info);
    final var bindings = packed.pBindings();
    final var vk_bind_0 = bindings.get(0);
    final var vk_bind_1 = bindings.get(1);
    final var vk_bind_2 = bindings.get(2);

    Assertions.assertEquals(3, packed.bindingCount());

    {
      final var source = binding_0.binding();
      final var target = vk_bind_0.binding();
      Assertions.assertEquals(source, target, "binding 0");
    }
    {
      final var source = binding_0.descriptorCount();
      final var target = vk_bind_0.descriptorCount();
      Assertions.assertEquals(source, target, "descriptor count 0");
    }
    {
      final var source = binding_0.descriptorType().value();
      final var target = vk_bind_0.descriptorType();
      Assertions.assertEquals(source, target, "descriptor type 0");
    }
    {
      final var source = VK_SHADER_STAGE_VERTEX_BIT.value();
      final var target = vk_bind_0.stageFlags();
      Assertions.assertEquals(source, target, "stage flags 0");
    }
    {
      final LongBuffer source = null;
      final var target = vk_bind_0.pImmutableSamplers();
      Assertions.assertEquals(source, target, "samplers 0");
    }

    {
      final var source = binding_1.binding();
      final var target = vk_bind_1.binding();
      Assertions.assertEquals(source, target, "binding 1");
    }
    {
      final var source = binding_1.descriptorCount();
      final var target = vk_bind_1.descriptorCount();
      Assertions.assertEquals(source, target, "descriptor count 1");
    }
    {
      final var source = binding_1.descriptorType().value();
      final var target = vk_bind_1.descriptorType();
      Assertions.assertEquals(source, target, "descriptor type 1");
    }
    {
      final var source = VK_SHADER_STAGE_FRAGMENT_BIT.value();
      final var target = vk_bind_1.stageFlags();
      Assertions.assertEquals(source, target, "stage flags 1");
    }
    {
      final LongBuffer source = null;
      final var target = vk_bind_1.pImmutableSamplers();
      Assertions.assertEquals(source, target, "samplers 1");
    }

    {
      final var source = binding_2.binding();
      final var target = vk_bind_2.binding();
      Assertions.assertEquals(source, target, "binding 2");
    }
    {
      final var source = binding_2.descriptorCount();
      final var target = vk_bind_2.descriptorCount();
      Assertions.assertEquals(source, target, "descriptor count 2");
    }
    {
      final var source = binding_2.descriptorType().value();
      final var target = vk_bind_2.descriptorType();
      Assertions.assertEquals(source, target, "descriptor type 2");
    }
    {
      final var source = VK_SHADER_STAGE_GEOMETRY_BIT.value();
      final var target = vk_bind_2.stageFlags();
      Assertions.assertEquals(source, target, "stage flags 2");
    }
    {
      final LongBuffer source = null;
      final var target = vk_bind_2.pImmutableSamplers();
      Assertions.assertEquals(source, target, "samplers 2");
    }
  }
}
