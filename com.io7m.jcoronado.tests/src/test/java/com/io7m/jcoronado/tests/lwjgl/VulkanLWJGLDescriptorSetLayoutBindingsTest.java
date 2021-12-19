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
import com.io7m.jcoronado.lwjgl.VulkanLWJGLDescriptorSetLayoutBindings;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLSampler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lwjgl.system.MemoryStack;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.LongBuffer;
import java.util.List;
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanDescriptorType.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER;
import static com.io7m.jcoronado.api.VulkanShaderStageFlag.VK_SHADER_STAGE_VERTEX_BIT;

@ExtendWith(MockitoExtension.class)
public final class VulkanLWJGLDescriptorSetLayoutBindingsTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLDescriptorSetLayoutBindingsTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testDescriptorSetLayoutBinding_0()
    throws Exception
  {
    final var binding =
      VulkanDescriptorSetLayoutBinding.of(
        1,
        VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER,
        1,
        Set.of(VK_SHADER_STAGE_VERTEX_BIT),
        List.of());

    final var packed =
      VulkanLWJGLDescriptorSetLayoutBindings.pack(this.stack, binding);

    Assertions.assertAll(
      () -> {
        final var source = binding.binding();
        final var target = packed.binding();
        Assertions.assertEquals(source, target, "binding");
      },
      () -> {
        final var source = binding.descriptorCount();
        final var target = packed.descriptorCount();
        Assertions.assertEquals(source, target, "descriptor count");
      },
      () -> {
        final var source = binding.descriptorType().value();
        final var target = packed.descriptorType();
        Assertions.assertEquals(source, target, "descriptor type");
      },
      () -> {
        final var source = VK_SHADER_STAGE_VERTEX_BIT.value();
        final var target = packed.stageFlags();
        Assertions.assertEquals(source, target, "stage flags");
      },
      () -> {
        final LongBuffer source = null;
        final var target = packed.pImmutableSamplers();
        Assertions.assertEquals(source, target, "samplers");
      }
    );
  }

  @Test
  public void testDescriptorSetLayoutBinding_0(
    final @Mock VulkanLWJGLSampler sampler_0,
    final @Mock VulkanLWJGLSampler sampler_1,
    final @Mock VulkanLWJGLSampler sampler_2)
    throws Exception
  {
    Mockito.when(sampler_0.handle())
      .thenReturn(23L);
    Mockito.when(sampler_1.handle())
      .thenReturn(64L);
    Mockito.when(sampler_2.handle())
      .thenReturn(39L);

    final var binding =
      VulkanDescriptorSetLayoutBinding.of(
        1,
        VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER,
        3,
        Set.of(VK_SHADER_STAGE_VERTEX_BIT),
        List.of(sampler_0, sampler_1, sampler_2));

    final var packed =
      VulkanLWJGLDescriptorSetLayoutBindings.pack(this.stack, binding);

    Assertions.assertAll(
      () -> {
        final var source = binding.binding();
        final var target = packed.binding();
        Assertions.assertEquals(source, target, "binding");
      },
      () -> {
        final var source = binding.descriptorCount();
        final var target = packed.descriptorCount();
        Assertions.assertEquals(source, target, "descriptor count");
      },
      () -> {
        final var source = binding.descriptorType().value();
        final var target = packed.descriptorType();
        Assertions.assertEquals(source, target, "descriptor type");
      },
      () -> {
        final var source = VK_SHADER_STAGE_VERTEX_BIT.value();
        final var target = packed.stageFlags();
        Assertions.assertEquals(source, target, "stage flags");
      },
      () -> {
        final var source = LongBuffer.allocate(3);
        source.put(0, 23L);
        source.put(1, 64L);
        source.put(2, 39L);

        final var target = packed.pImmutableSamplers();
        Assertions.assertEquals(source.get(0), target.get(0));
        Assertions.assertEquals(source.get(1), target.get(1));
        Assertions.assertEquals(source.get(2), target.get(2));
      }
    );
  }
}
