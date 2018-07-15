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

package com.io7m.jcoronado.lwjgl;

import com.io7m.jcoronado.api.VulkanClearValueColorFloatingPoint;
import com.io7m.jcoronado.api.VulkanClearValueColorIntegerSigned;
import com.io7m.jcoronado.api.VulkanClearValueColorIntegerUnsigned;
import com.io7m.jcoronado.api.VulkanClearValueDepthStencil;
import com.io7m.jcoronado.api.VulkanClearValueType;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkClearValue;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Objects;

/**
 * Functions to pack rectangles.
 */

public final class VulkanLWJGLClearValues
{
  private VulkanLWJGLClearValues()
  {

  }

  /**
   * Pack a structure.
   *
   * @param stack  A stack
   * @param values A structure
   *
   * @return A packed structure
   */

  public static VkClearValue.Buffer packAll(
    final MemoryStack stack,
    final List<VulkanClearValueType> values)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(values, "values");

    final int count = values.size();

    final VkClearValue.Buffer target_values =
      VkClearValue.mallocStack(values.size(), stack);

    for (int index = 0; index < count; ++index) {
      packTo(values.get(index), target_values.get(index));
    }

    return target_values;
  }

  static void packTo(
    final VulkanClearValueType clear,
    final VkClearValue target)
  {
    switch (clear.type()) {
      case DEPTH_STENCIL: {
        final VulkanClearValueDepthStencil depth_stencil =
          (VulkanClearValueDepthStencil) clear;

        target.depthStencil().set(depth_stencil.depth(), depth_stencil.stencil());
        break;
      }

      case COLOR_INTEGER_SIGNED: {
        final VulkanClearValueColorIntegerSigned color =
          (VulkanClearValueColorIntegerSigned) clear;

        final IntBuffer ib = target.color().int32();
        ib.put(0, color.red());
        ib.put(1, color.green());
        ib.put(2, color.blue());
        ib.put(3, color.alpha());
        break;
      }

      case COLOR_INTEGER_UNSIGNED: {
        final VulkanClearValueColorIntegerUnsigned color =
          (VulkanClearValueColorIntegerUnsigned) clear;

        final IntBuffer ib = target.color().uint32();
        ib.put(0, color.red());
        ib.put(1, color.green());
        ib.put(2, color.blue());
        ib.put(3, color.alpha());
        break;
      }

      case COLOR_FLOATING_POINT: {
        final VulkanClearValueColorFloatingPoint color =
          (VulkanClearValueColorFloatingPoint) clear;

        final FloatBuffer ib = target.color().float32();
        ib.put(0, color.red());
        ib.put(1, color.green());
        ib.put(2, color.blue());
        ib.put(3, color.alpha());
        break;
      }
    }
  }
}
