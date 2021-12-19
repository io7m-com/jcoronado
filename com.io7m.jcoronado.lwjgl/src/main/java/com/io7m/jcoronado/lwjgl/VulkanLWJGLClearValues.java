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
import com.io7m.junreachable.UnreachableCodeException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkClearColorValue;
import org.lwjgl.vulkan.VkClearDepthStencilValue;
import org.lwjgl.vulkan.VkClearValue;

import java.util.List;
import java.util.Objects;

import static com.io7m.jcoronado.api.VulkanClearValueType.VulkanClearValueColorType;

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

    final var count = values.size();
    final var target_values = VkClearValue.mallocStack(values.size(), stack);

    for (var index = 0; index < count; ++index) {
      packTo(values.get(index), target_values.get(index));
    }

    return target_values;
  }

  /**
   * Pack a structure.
   *
   * @param stack  A stack
   * @param source A structure
   *
   * @return A packed structure
   */

  public static VkClearValue pack(
    final MemoryStack stack,
    final VulkanClearValueType source)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(source, "source");

    return packTo(source, VkClearValue.mallocStack(stack));
  }

  /**
   * Pack a structure.
   *
   * @param source The source value
   * @param target The target structure
   *
   * @return A packed structure
   */

  public static VkClearValue packTo(
    final VulkanClearValueType source,
    final VkClearValue target)
  {
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");

    switch (source.type()) {
      case DEPTH_STENCIL: {
        final var depth_stencil = (VulkanClearValueDepthStencil) source;
        packToDepthStencil(depth_stencil, target.depthStencil());
        break;
      }

      case COLOR: {
        packToColor((VulkanClearValueColorType) source, target.color());
        break;
      }
    }
    return target;
  }

  private static VkClearDepthStencilValue packToDepthStencil(
    final VulkanClearValueDepthStencil source,
    final VkClearDepthStencilValue target)
  {
    target.set(source.depth(), source.stencil());
    return target;
  }

  private static VkClearColorValue packToColor(
    final VulkanClearValueColorType source,
    final VkClearColorValue target)
  {
    switch (source.colorType()) {
      case COLOR_INTEGER_SIGNED: {
        final var color = (VulkanClearValueColorIntegerSigned) source;
        final var ib = target.int32();
        ib.put(0, color.red());
        ib.put(1, color.green());
        ib.put(2, color.blue());
        ib.put(3, color.alpha());
        return target;
      }

      case COLOR_INTEGER_UNSIGNED: {
        final var color = (VulkanClearValueColorIntegerUnsigned) source;
        final var ib = target.uint32();
        ib.put(0, color.red());
        ib.put(1, color.green());
        ib.put(2, color.blue());
        ib.put(3, color.alpha());
        return target;
      }

      case COLOR_FLOATING_POINT: {
        final var color = (VulkanClearValueColorFloatingPoint) source;
        final var ib = target.float32();
        ib.put(0, color.red());
        ib.put(1, color.green());
        ib.put(2, color.blue());
        ib.put(3, color.alpha());
        return target;
      }
    }

    throw new UnreachableCodeException();
  }

  /**
   * Pack a structure.
   *
   * @param stack  A stack
   * @param source A structure
   *
   * @return A packed structure
   */

  public static VkClearColorValue packColor(
    final MemoryStack stack,
    final VulkanClearValueColorType source)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(source, "source");

    return packToColor(source, VkClearColorValue.mallocStack(stack));
  }

  /**
   * Pack a structure.
   *
   * @param stack  A stack
   * @param source A structure
   *
   * @return A packed structure
   */

  public static VkClearDepthStencilValue packDepthStencil(
    final MemoryStack stack,
    final VulkanClearValueDepthStencil source)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(source, "source");

    return packToDepthStencil(
      source,
      VkClearDepthStencilValue.mallocStack(stack));
  }
}
