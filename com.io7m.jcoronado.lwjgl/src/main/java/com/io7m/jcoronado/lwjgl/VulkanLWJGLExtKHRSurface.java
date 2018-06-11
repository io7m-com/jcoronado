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

import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.extensions.api.VulkanExtKHRSurfaceType;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.vulkan.KHRSurface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Access to the {@code VK_KHR_surface} extension.
 */

public final class VulkanLWJGLExtKHRSurface implements VulkanExtKHRSurfaceType
{
  private static final Logger LOG = LoggerFactory.getLogger(VulkanLWJGLExtKHRSurface.class);

  VulkanLWJGLExtKHRSurface()
  {

  }

  @Override
  public String toString()
  {
    return new StringBuilder(64)
      .append('[')
      .append(this.getClass().getCanonicalName())
      .append(" ")
      .append(this.name())
      .append(']')
      .toString();
  }

  @Override
  public VulkanKHRSurfaceType surfaceFromWindow(
    final VulkanInstanceType instance,
    final long window)
    throws VulkanException
  {
    final VulkanLWJGLInstance instance_lwjgl =
      VulkanLWJGLClassChecks.check(instance, VulkanLWJGLInstance.class);

    if (window == 0L) {
      throw new NullPointerException("Window address is 0x0");
    }

    LOG.debug("creating surface for window 0x{}", Long.toUnsignedString(window, 16));

    final long[] surface_holder = new long[1];
    VulkanChecks.checkReturnCode(
      GLFWVulkan.glfwCreateWindowSurface(
        instance_lwjgl.instance(), window, null, surface_holder),
      "glfwCreateWindowSurface");

    LOG.debug(
      "created surface for window 0x{}: surface 0x{}",
      Long.toUnsignedString(window, 16),
      Long.toUnsignedString(surface_holder[0], 16));

    return new VulkanLWJGLExtKHRSurfaceValue(surface_holder[0]);
  }

  @Override
  public List<VulkanQueueFamilyProperties> surfaceSupport(
    final VulkanPhysicalDeviceType in_device,
    final VulkanKHRSurfaceType in_surface)
    throws VulkanException
  {
    final VulkanLWJGLPhysicalDevice device =
      VulkanLWJGLClassChecks.check(in_device, VulkanLWJGLPhysicalDevice.class);
    final VulkanLWJGLExtKHRSurfaceValue surface =
      VulkanLWJGLClassChecks.check(in_surface, VulkanLWJGLExtKHRSurfaceValue.class);

    device.checkNotClosed();

    final List<VulkanQueueFamilyProperties> queues = device.queueFamilies();
    final List<VulkanQueueFamilyProperties> results = new ArrayList<>(queues.size());

    final int[] supported = new int[1];
    for (final VulkanQueueFamilyProperties queue : queues) {
      supported[0] = 0;
      VulkanChecks.checkReturnCode(
        KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR(
          device.device(),
          queue.queueFamilyIndex(),
          surface.pointer,
          supported),
        "vkGetPhysicalDeviceSurfaceSupportKHR");
      if (supported[0] != 0) {
        results.add(queue);
      }
    }

    return results;
  }

  private static final class VulkanLWJGLExtKHRSurfaceValue
    implements VulkanExtKHRSurfaceType.VulkanKHRSurfaceType
  {
    private long pointer;

    VulkanLWJGLExtKHRSurfaceValue(
      final long in_pointer)
    {
      this.pointer = in_pointer;
    }
  }
}
