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

import com.io7m.jcoronado.api.VulkanDescriptorPoolSize;
import com.io7m.jcoronado.api.VulkanDescriptorSetLayoutBinding;
import com.io7m.jcoronado.api.VulkanDescriptorType;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLDescriptorPoolSizes;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLDescriptorSetLayoutBindings;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLSampler;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.LongBuffer;
import java.util.List;
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanDescriptorType.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER;
import static com.io7m.jcoronado.api.VulkanShaderStageFlag.VK_SHADER_STAGE_VERTEX_BIT;

public final class VulkanLWJGLDescriptorPoolSizesTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLDescriptorPoolSizesTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testDescriptorPoolSizes_0()
  {
    for (final var type : VulkanDescriptorType.values()) {
      final var binding =
        VulkanDescriptorPoolSize.of(type, 23);
      final var packed =
        VulkanLWJGLDescriptorPoolSizes.packDescriptorPoolSize(this.stack, binding);

      Assertions.assertAll(
        () -> {
          final var source = binding.descriptorCount();
          final var target = packed.descriptorCount();
          Assertions.assertEquals(source, target, "descriptor count");
        },
        () -> {
          final var source = binding.type().value();
          final var target = packed.type();
          Assertions.assertEquals(source, target, "descriptor type");
        }
      );
    }
  }
}
