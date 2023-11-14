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

import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanPipelineShaderStageCreateInfo;
import com.io7m.jcoronado.api.VulkanShaderStageFlag;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLPipelineShaderStageCreateInfos;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLShaderModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineShaderStageCreateInfo;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public final class VulkanLWJGLPipelineShaderStageCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLPipelineShaderStageCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  static void checkPacked(
    final VulkanLWJGLShaderModule module,
    final VkPipelineShaderStageCreateInfo.Buffer packed)
  {
    Assertions.assertNotNull(packed, "VkPipelineShaderStageCreateInfo");

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO,
          packed.sType());
      },
      () -> {
        packed.position(0);

        Assertions.assertEquals(
          "main",
          packed.pNameString());
        Assertions.assertEquals(
          module.handle(),
          packed.module());
        Assertions.assertEquals(
          VK10.VK_SHADER_STAGE_VERTEX_BIT,
          packed.stage());

        packed.position(1);

        Assertions.assertEquals(
          "main2",
          packed.pNameString());
        Assertions.assertEquals(
          module.handle(),
          packed.module());
        Assertions.assertEquals(
          VK10.VK_SHADER_STAGE_FRAGMENT_BIT,
          packed.stage());
      }
    );
  }

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPipelineShaderStageCreateInfo(
    final @Mock VulkanLWJGLShaderModule module)
    throws VulkanIncompatibleClassException
  {
    Mockito.when(module.handle())
      .thenReturn(Long.valueOf(0x200L));

    final var info =
      VulkanPipelineShaderStageCreateInfo.builder()
        .setShaderEntryPoint("main")
        .setModule(module)
        .setStage(VulkanShaderStageFlag.VK_SHADER_STAGE_VERTEX_BIT)
        .build();

    final var packed =
      VulkanLWJGLPipelineShaderStageCreateInfos.pack(this.stack, info);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO,
          packed.sType());
      },
      () -> {
        Assertions.assertEquals(
          "main",
          packed.pNameString());
      },
      () -> {
        Assertions.assertEquals(
          module.handle(),
          packed.module());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_SHADER_STAGE_VERTEX_BIT,
          packed.stage());
      }
    );
  }

  @Test
  public void testPipelineShaderStageCreateInfos(
    final @Mock VulkanLWJGLShaderModule module)
    throws VulkanIncompatibleClassException
  {
    Mockito.when(module.handle())
      .thenReturn(Long.valueOf(0x200L));

    final var info_0 =
      VulkanPipelineShaderStageCreateInfo.builder()
        .setShaderEntryPoint("main")
        .setModule(module)
        .setStage(VulkanShaderStageFlag.VK_SHADER_STAGE_VERTEX_BIT)
        .build();

    final var info_1 =
      VulkanPipelineShaderStageCreateInfo.builder()
        .setShaderEntryPoint("main2")
        .setModule(module)
        .setStage(VulkanShaderStageFlag.VK_SHADER_STAGE_FRAGMENT_BIT)
        .build();

    final var packed =
      VulkanLWJGLPipelineShaderStageCreateInfos.packAll(
        this.stack,
        List.of(
          info_0,
          info_1));

    checkPacked(module, packed);
  }
}
