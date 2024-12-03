/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
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


package com.io7m.jcoronado.lwjgl.internal;

import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanDebuggingType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanHandleType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsLabelColor;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsLabelEXT;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsObjectNameInfoEXT;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsRegionType;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsType;

import java.util.Objects;

/**
 * A debugging interface.
 */

public final class VulkanLWJGLDebugging
  implements VulkanDebuggingType
{
  private final VulkanLogicalDeviceType device;
  private final VulkanDebugUtilsType extension;

  VulkanLWJGLDebugging(
    final VulkanLogicalDeviceType inDevice,
    final VulkanDebugUtilsType inExtension)
  {
    this.device =
      Objects.requireNonNull(inDevice, "inDevice");
    this.extension =
      Objects.requireNonNull(inExtension, "extension");
  }

  @Override
  public void setObjectName(
    final VulkanHandleType handle,
    final String name)
    throws VulkanException
  {
    Objects.requireNonNull(handle, "handle");
    Objects.requireNonNull(name, "name");

    this.extension.setObjectName(
      this.device,
      VulkanDebugUtilsObjectNameInfoEXT.builder()
        .setObjectName(name)
        .setObjectHandle(handle)
        .build()
    );
  }

  @Override
  public VulkanDebugRegionType begin(
    final VulkanCommandBufferType commandBuffer,
    final String label,
    final Color color)
    throws VulkanException
  {
    return new DebugRegion(
      this,
      commandBuffer,
      this.extension.begin(
        commandBuffer,
        VulkanDebugUtilsLabelEXT.builder()
          .setColor(VulkanDebugUtilsLabelColor.builder()
                      .setRed((float) color.red())
                      .setGreen((float) color.green())
                      .setBlue((float) color.blue())
                      .setAlpha(1.0f)
                      .build())
          .setName(label)
          .build()
      )
    );
  }

  private static final class DebugRegion
    implements VulkanDebugRegionType
  {
    private final VulkanLWJGLDebugging debugging;
    private final VulkanCommandBufferType commandBuffer;
    private final VulkanDebugUtilsRegionType region;

    DebugRegion(
      final VulkanLWJGLDebugging inDebugging,
      final VulkanCommandBufferType inCommandBuffer,
      final VulkanDebugUtilsRegionType inRegion)
    {
      this.debugging =
        Objects.requireNonNull(inDebugging, "debugging");
      this.commandBuffer =
        Objects.requireNonNull(inCommandBuffer, "commandBuffer");
      this.region =
        Objects.requireNonNull(inRegion, "region");
    }

    @Override
    public void insertInto(
      final String label,
      final Color color)
      throws VulkanException
    {
      this.debugging.extension.insertInto(
        this.commandBuffer,
        VulkanDebugUtilsLabelEXT.builder()
          .setName(label)
          .setColor(VulkanDebugUtilsLabelColor.builder()
                      .setRed((float) color.red())
                      .setGreen((float) color.green())
                      .setBlue((float) color.blue())
                      .setAlpha(1.0f)
                      .build())
          .build()
      );
    }

    @Override
    public void insertInto(
      final String label)
      throws VulkanException
    {
      this.debugging.extension.insertInto(
        this.commandBuffer,
        VulkanDebugUtilsLabelEXT.builder()
          .setName(label)
          .setColor(VulkanDebugUtilsLabelColor.builder()
                      .setRed(0.0f)
                      .setGreen(0.0f)
                      .setBlue(0.0f)
                      .setAlpha(1.0f)
                      .build())
          .build()
      );
    }

    @Override
    public void close()
      throws VulkanException
    {
      this.region.close();
    }

    @Override
    public boolean isClosed()
    {
      return this.region.isClosed();
    }
  }
}
