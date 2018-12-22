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

import com.io7m.jcoronado.api.VulkanPipelineVertexInputStateCreateInfo;
import com.io7m.jcoronado.api.VulkanVertexInputAttributeDescription;
import com.io7m.jcoronado.api.VulkanVertexInputBindingDescription;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLPipelineVertexInputStateCreateInfos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineVertexInputStateCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_B8G8R8A8_UNORM;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_D16_UNORM;
import static com.io7m.jcoronado.api.VulkanVertexInputRate.VK_VERTEX_INPUT_RATE_INSTANCE;
import static com.io7m.jcoronado.api.VulkanVertexInputRate.VK_VERTEX_INPUT_RATE_VERTEX;

public final class VulkanLWJGLPipelineVertexInputStateCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLPipelineVertexInputStateCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPipelineVertexInputStateCreateInfo()
  {
    final var desc_0 =
      VulkanVertexInputAttributeDescription.of(0, 1, VK_FORMAT_B8G8R8A8_UNORM, 23);
    final var desc_1 =
      VulkanVertexInputAttributeDescription.of(2, 3, VK_FORMAT_D16_UNORM, 26);
    final var bind_0 =
      VulkanVertexInputBindingDescription.of(3, 56, VK_VERTEX_INPUT_RATE_VERTEX);
    final var bind_1 =
      VulkanVertexInputBindingDescription.of(5, 57, VK_VERTEX_INPUT_RATE_INSTANCE);

    final var info =
      VulkanPipelineVertexInputStateCreateInfo.builder()
        .addVertexAttributeDescriptions(desc_0)
        .addVertexAttributeDescriptions(desc_1)
        .addVertexBindingDescriptions(bind_0)
        .addVertexBindingDescriptions(bind_1)
        .build();

    final var packed =
      VulkanLWJGLPipelineVertexInputStateCreateInfos.pack(this.stack, info);

    checkPacked(packed);
  }

  static void checkPacked(final VkPipelineVertexInputStateCreateInfo packed)
  {
    Assertions.assertNotNull(packed, "VkPipelineVertexInputStateCreateInfo");

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO,
          packed.sType());
      },
      () -> {
        Assertions.assertEquals(
          2,
          packed.vertexAttributeDescriptionCount());

        final var vps =
          packed.pVertexAttributeDescriptions();

        vps.position(0);
        Assertions.assertEquals(0, vps.location());
        Assertions.assertEquals(1, vps.binding());
        Assertions.assertEquals(VK_FORMAT_B8G8R8A8_UNORM.value(), vps.format());
        Assertions.assertEquals(23, vps.offset());

        vps.position(1);
        Assertions.assertEquals(2, vps.location());
        Assertions.assertEquals(3, vps.binding());
        Assertions.assertEquals(VK_FORMAT_D16_UNORM.value(), vps.format());
        Assertions.assertEquals(26, vps.offset());
      },
      () -> {
        Assertions.assertEquals(
          2,
          packed.vertexBindingDescriptionCount());

        final var vps =
          packed.pVertexBindingDescriptions();

        vps.position(0);
        Assertions.assertEquals(3, vps.binding());
        Assertions.assertEquals(VK_VERTEX_INPUT_RATE_VERTEX.value(), vps.inputRate());
        Assertions.assertEquals(56, vps.stride());

        vps.position(1);
        Assertions.assertEquals(5, vps.binding());
        Assertions.assertEquals(VK_VERTEX_INPUT_RATE_INSTANCE.value(), vps.inputRate());
        Assertions.assertEquals(57, vps.stride());
      }
    );
  }

  @Test
  public void testPipelineVertexInputStateCreateInfoEmpty()
  {
    final var info =
      VulkanPipelineVertexInputStateCreateInfo.builder()
        .build();

    final var packed =
      VulkanLWJGLPipelineVertexInputStateCreateInfos.pack(this.stack, info);

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO,
          packed.sType());
      },
      () -> {
        Assertions.assertEquals(
          0,
          packed.vertexAttributeDescriptionCount());
      },
      () -> {
        Assertions.assertEquals(
          0,
          packed.vertexBindingDescriptionCount());
      }
    );
  }
}
