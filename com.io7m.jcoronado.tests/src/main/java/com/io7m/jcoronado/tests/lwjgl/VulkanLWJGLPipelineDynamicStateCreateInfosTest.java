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

import com.io7m.jcoronado.api.VulkanDynamicState;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanPipelineDynamicStateCreateInfo;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLPipelineDynamicStateCreateInfos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPipelineDynamicStateCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class VulkanLWJGLPipelineDynamicStateCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLPipelineDynamicStateCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  static void checkPacked(
    final VkPipelineDynamicStateCreateInfo packed)
  {
    Assertions.assertNotNull(packed, "VkPipelineDynamicStateCreateInfo");

    Assertions.assertAll(
      () -> {
        Assertions.assertEquals(0L, packed.pNext());
      },
      () -> {
        Assertions.assertEquals(
          VK10.VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO,
          packed.sType());
      },
      () -> {
        final HashSet<Integer> received = new HashSet<>();
        final HashSet<Integer> expected = new HashSet<>();

        expected.addAll(
          List.of(VulkanDynamicState.values())
            .stream()
            .map(v -> Integer.valueOf(v.value()))
            .collect(Collectors.toList()));

        final var states = packed.pDynamicStates();
        for (var index = 0; index < states.capacity(); ++index) {
          received.add(Integer.valueOf(states.get(index)));
        }

        Assertions.assertEquals(expected, received);
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
  public void testPipelineDynamicStateCreateInfo()
    throws VulkanException
  {
    final var info =
      VulkanPipelineDynamicStateCreateInfo.builder()
        .addDynamicStates(VulkanDynamicState.values())
        .build();

    final var packed =
      VulkanLWJGLPipelineDynamicStateCreateInfos.pack(this.stack, info);

    checkPacked(packed);
    checkPacked(
      VulkanLWJGLPipelineDynamicStateCreateInfos.packOptional(
        this.stack,
        Optional.of(info)));
    Assertions.assertNull(
      VulkanLWJGLPipelineDynamicStateCreateInfos.packOptional(
        this.stack,
        Optional.empty()));
  }
}
