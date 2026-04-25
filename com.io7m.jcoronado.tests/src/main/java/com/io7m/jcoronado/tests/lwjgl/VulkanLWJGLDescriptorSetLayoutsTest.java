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

import com.io7m.jcoronado.api.VulkanDescriptorBindingFlag;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutBinding;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutCreateInfo;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLDescriptorSetLayouts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkDescriptorSetLayoutBindingFlagsCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.LongBuffer;
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanDescriptorBindingFlag.VK_DESCRIPTOR_BINDING_PARTIALLY_BOUND;
import static com.io7m.jcoronado.api.VulkanDescriptorBindingFlag.VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND;
import static com.io7m.jcoronado.api.VulkanDescriptorBindingFlag.VK_DESCRIPTOR_BINDING_UPDATE_UNUSED_WHILE_PENDING;
import static com.io7m.jcoronado.api.VulkanDescriptorBindingFlag.VK_DESCRIPTOR_BINDING_VARIABLE_DESCRIPTOR_COUNT;
import static com.io7m.jcoronado.api.VulkanDescriptorType.VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER;
import static com.io7m.jcoronado.api.VulkanDescriptorType.VK_DESCRIPTOR_TYPE_STORAGE_BUFFER;
import static com.io7m.jcoronado.api.VulkanDescriptorType.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER;
import static com.io7m.jcoronado.api.VulkanShaderStageFlag.VK_SHADER_STAGE_FRAGMENT_BIT;
import static com.io7m.jcoronado.api.VulkanShaderStageFlag.VK_SHADER_STAGE_GEOMETRY_BIT;
import static com.io7m.jcoronado.api.VulkanShaderStageFlag.VK_SHADER_STAGE_VERTEX_BIT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class VulkanLWJGLDescriptorSetLayoutsTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLDescriptorSetLayoutsTest.class);

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
      VulkanDescriptorSetLayoutBinding.builder()
        .addStageFlags(VK_SHADER_STAGE_VERTEX_BIT)
        .setBinding(23)
        .setDescriptorCount(1)
        .setDescriptorType(VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER)
        .build();

    final var binding_1 =
      VulkanDescriptorSetLayoutBinding.builder()
        .addStageFlags(VK_SHADER_STAGE_FRAGMENT_BIT)
        .setBinding(24)
        .setDescriptorCount(1)
        .setDescriptorType(VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER)
        .build();

    final var binding_2 =
      VulkanDescriptorSetLayoutBinding.builder()
        .addStageFlags(VK_SHADER_STAGE_GEOMETRY_BIT)
        .setBinding(25)
        .setDescriptorCount(1)
        .setDescriptorType(VK_DESCRIPTOR_TYPE_STORAGE_BUFFER)
        .build();

    final var create_info =
      VulkanDescriptorSetLayoutCreateInfo.builder()
        .addBindings(binding_0)
        .addBindings(binding_1)
        .addBindings(binding_2)
        .build();

    final var packed =
      VulkanLWJGLDescriptorSetLayouts.packDescriptorSetLayoutCreateInfo(
        this.stack,
        create_info);
    final var bindings = packed.pBindings();
    final var vk_bind_0 = bindings.get(0);
    final var vk_bind_1 = bindings.get(1);
    final var vk_bind_2 = bindings.get(2);

    assertEquals(3, packed.bindingCount());

    {
      final var source = binding_0.binding();
      final var target = vk_bind_0.binding();
      assertEquals(source, target, "binding 0");
    }
    {
      final var source = binding_0.descriptorCount();
      final var target = vk_bind_0.descriptorCount();
      assertEquals(source, target, "descriptor count 0");
    }
    {
      final var source = binding_0.descriptorType().value();
      final var target = vk_bind_0.descriptorType();
      assertEquals(source, target, "descriptor type 0");
    }
    {
      final var source = VK_SHADER_STAGE_VERTEX_BIT.value();
      final var target = vk_bind_0.stageFlags();
      assertEquals(source, target, "stage flags 0");
    }
    {
      final LongBuffer source = null;
      final var target = vk_bind_0.pImmutableSamplers();
      assertEquals(source, target, "samplers 0");
    }

    {
      final var source = binding_1.binding();
      final var target = vk_bind_1.binding();
      assertEquals(source, target, "binding 1");
    }
    {
      final var source = binding_1.descriptorCount();
      final var target = vk_bind_1.descriptorCount();
      assertEquals(source, target, "descriptor count 1");
    }
    {
      final var source = binding_1.descriptorType().value();
      final var target = vk_bind_1.descriptorType();
      assertEquals(source, target, "descriptor type 1");
    }
    {
      final var source = VK_SHADER_STAGE_FRAGMENT_BIT.value();
      final var target = vk_bind_1.stageFlags();
      assertEquals(source, target, "stage flags 1");
    }
    {
      final LongBuffer source = null;
      final var target = vk_bind_1.pImmutableSamplers();
      assertEquals(source, target, "samplers 1");
    }

    {
      final var source = binding_2.binding();
      final var target = vk_bind_2.binding();
      assertEquals(source, target, "binding 2");
    }
    {
      final var source = binding_2.descriptorCount();
      final var target = vk_bind_2.descriptorCount();
      assertEquals(source, target, "descriptor count 2");
    }
    {
      final var source = binding_2.descriptorType().value();
      final var target = vk_bind_2.descriptorType();
      assertEquals(source, target, "descriptor type 2");
    }
    {
      final var source = VK_SHADER_STAGE_GEOMETRY_BIT.value();
      final var target = vk_bind_2.stageFlags();
      assertEquals(source, target, "stage flags 2");
    }
    {
      final LongBuffer source = null;
      final var target = vk_bind_2.pImmutableSamplers();
      assertEquals(source, target, "samplers 2");
    }
  }

  @Test
  public void testDescriptorSetLayoutCreateInfo_1()
    throws Exception
  {
    final var binding_0 =
      VulkanDescriptorSetLayoutBinding.builder()
        .addStageFlags(VK_SHADER_STAGE_VERTEX_BIT)
        .setBinding(23)
        .setDescriptorCount(1)
        .setDescriptorType(VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER)
        .build();

    final var binding_1 =
      VulkanDescriptorSetLayoutBinding.builder()
        .addStageFlags(VK_SHADER_STAGE_FRAGMENT_BIT)
        .setBinding(24)
        .setDescriptorCount(1)
        .setDescriptorType(VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER)
        .build();

    final var binding_2 =
      VulkanDescriptorSetLayoutBinding.builder()
        .addStageFlags(VK_SHADER_STAGE_GEOMETRY_BIT)
        .setBinding(25)
        .setDescriptorCount(1)
        .setDescriptorType(VK_DESCRIPTOR_TYPE_STORAGE_BUFFER)
        .build();

    final var flags0 =
      Set.of(
        VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND
      );
    final var flags1 =
      Set.of(
        VK_DESCRIPTOR_BINDING_UPDATE_UNUSED_WHILE_PENDING,
        VK_DESCRIPTOR_BINDING_VARIABLE_DESCRIPTOR_COUNT
      );
    final var flags2 =
      Set.of(
        VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND,
        VK_DESCRIPTOR_BINDING_PARTIALLY_BOUND
      );

    final var create_info =
      VulkanDescriptorSetLayoutCreateInfo.builder()
        .addBindings(binding_0)
        .addBindings(binding_1)
        .addBindings(binding_2)
        .addBindingsFlags(flags0)
        .addBindingsFlags(flags1)
        .addBindingsFlags(flags2)
        .build();

    final var packed =
      VulkanLWJGLDescriptorSetLayouts.packDescriptorSetLayoutCreateInfo(
        this.stack,
        create_info
      );
    final var bindings = packed.pBindings();
    final var vk_bind_0 = bindings.get(0);
    final var vk_bind_1 = bindings.get(1);
    final var vk_bind_2 = bindings.get(2);
    assertEquals(3, packed.bindingCount());

    final var packedFlagInfo =
      VkDescriptorSetLayoutBindingFlagsCreateInfo.create(packed.pNext());

    assertEquals(3, packedFlagInfo.bindingCount());

    {
      final var f = packedFlagInfo.pBindingFlags().get(0);
      final var e = VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND.value();
      assertEquals(e, f);
    }

    {
      final var f = packedFlagInfo.pBindingFlags().get(1);
      var e = VK_DESCRIPTOR_BINDING_UPDATE_UNUSED_WHILE_PENDING.value();
      e |= VK_DESCRIPTOR_BINDING_VARIABLE_DESCRIPTOR_COUNT.value();
      assertEquals(e, f);
    }

    {
      final var f = packedFlagInfo.pBindingFlags().get(2);
      var e = VK_DESCRIPTOR_BINDING_UPDATE_AFTER_BIND.value();
      e |= VK_DESCRIPTOR_BINDING_PARTIALLY_BOUND.value();
      assertEquals(e, f);
    }

    {
      final var source = binding_0.binding();
      final var target = vk_bind_0.binding();
      assertEquals(source, target, "binding 0");
    }
    {
      final var source = binding_0.descriptorCount();
      final var target = vk_bind_0.descriptorCount();
      assertEquals(source, target, "descriptor count 0");
    }
    {
      final var source = binding_0.descriptorType().value();
      final var target = vk_bind_0.descriptorType();
      assertEquals(source, target, "descriptor type 0");
    }
    {
      final var source = VK_SHADER_STAGE_VERTEX_BIT.value();
      final var target = vk_bind_0.stageFlags();
      assertEquals(source, target, "stage flags 0");
    }
    {
      final LongBuffer source = null;
      final var target = vk_bind_0.pImmutableSamplers();
      assertEquals(source, target, "samplers 0");
    }

    {
      final var source = binding_1.binding();
      final var target = vk_bind_1.binding();
      assertEquals(source, target, "binding 1");
    }
    {
      final var source = binding_1.descriptorCount();
      final var target = vk_bind_1.descriptorCount();
      assertEquals(source, target, "descriptor count 1");
    }
    {
      final var source = binding_1.descriptorType().value();
      final var target = vk_bind_1.descriptorType();
      assertEquals(source, target, "descriptor type 1");
    }
    {
      final var source = VK_SHADER_STAGE_FRAGMENT_BIT.value();
      final var target = vk_bind_1.stageFlags();
      assertEquals(source, target, "stage flags 1");
    }
    {
      final LongBuffer source = null;
      final var target = vk_bind_1.pImmutableSamplers();
      assertEquals(source, target, "samplers 1");
    }

    {
      final var source = binding_2.binding();
      final var target = vk_bind_2.binding();
      assertEquals(source, target, "binding 2");
    }
    {
      final var source = binding_2.descriptorCount();
      final var target = vk_bind_2.descriptorCount();
      assertEquals(source, target, "descriptor count 2");
    }
    {
      final var source = binding_2.descriptorType().value();
      final var target = vk_bind_2.descriptorType();
      assertEquals(source, target, "descriptor type 2");
    }
    {
      final var source = VK_SHADER_STAGE_GEOMETRY_BIT.value();
      final var target = vk_bind_2.stageFlags();
      assertEquals(source, target, "stage flags 2");
    }
    {
      final LongBuffer source = null;
      final var target = vk_bind_2.pImmutableSamplers();
      assertEquals(source, target, "samplers 2");
    }
  }
}
