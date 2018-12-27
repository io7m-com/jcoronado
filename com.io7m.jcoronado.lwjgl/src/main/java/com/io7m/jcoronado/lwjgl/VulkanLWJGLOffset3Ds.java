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

import com.io7m.jcoronado.api.VulkanOffset3D;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkOffset3D;

import java.util.Objects;

/**
 * Functions to pack 3D shapes.
 */

public final class VulkanLWJGLOffset3Ds
{
  private VulkanLWJGLOffset3Ds()
  {

  }

  /**
   * Pack a structure.
   *
   * @param stack  A stack
   * @param offset A structure
   *
   * @return A packed structure
   */

  public static VkOffset3D pack(
    final MemoryStack stack,
    final VulkanOffset3D offset)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(offset, "offset");

    return VkOffset3D.mallocStack(stack)
      .set(offset.x(), offset.y(), offset.z());
  }
}
