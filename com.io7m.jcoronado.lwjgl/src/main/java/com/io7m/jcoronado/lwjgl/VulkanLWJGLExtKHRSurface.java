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
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanFormat;
import com.io7m.jcoronado.api.VulkanImageUsageFlag;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.extensions.api.VulkanColorSpaceKHR;
import com.io7m.jcoronado.extensions.api.VulkanCompositeAlphaFlagKHR;
import com.io7m.jcoronado.extensions.api.VulkanExtKHRSurfaceType;
import com.io7m.jcoronado.extensions.api.VulkanPresentModeKHR;
import com.io7m.jcoronado.extensions.api.VulkanSurfaceCapabilitiesKHR;
import com.io7m.jcoronado.extensions.api.VulkanSurfaceFormatKHR;
import com.io7m.jcoronado.extensions.api.VulkanSurfaceTransformFlagKHR;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.KHRSurface;
import org.lwjgl.vulkan.VkExtent2D;
import org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Access to the {@code VK_KHR_surface} extension.
 */

public final class VulkanLWJGLExtKHRSurface implements VulkanExtKHRSurfaceType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLExtKHRSurface.class);

  private final MemoryStack stack_initial;

  VulkanLWJGLExtKHRSurface()
  {
    this.stack_initial = MemoryStack.create();
  }

  private static Set<VulkanCompositeAlphaFlagKHR> parseCompositeAlpha(
    final int value)
  {
    final EnumSet<VulkanCompositeAlphaFlagKHR> results =
      EnumSet.noneOf(VulkanCompositeAlphaFlagKHR.class);

    for (final VulkanCompositeAlphaFlagKHR flag : VulkanCompositeAlphaFlagKHR.values()) {
      final int fv = flag.value();
      if ((value & fv) == fv) {
        results.add(flag);
      }
    }

    return results;
  }

  private static Set<VulkanImageUsageFlag> parseUsageFlags(
    final int value)
  {
    final EnumSet<VulkanImageUsageFlag> results =
      EnumSet.noneOf(VulkanImageUsageFlag.class);

    for (final VulkanImageUsageFlag flag : VulkanImageUsageFlag.values()) {
      final int fv = flag.value();
      if ((value & fv) == fv) {
        results.add(flag);
      }
    }

    return results;
  }

  private static Set<VulkanSurfaceTransformFlagKHR> parseTransform(
    final int value)
  {
    final EnumSet<VulkanSurfaceTransformFlagKHR> results =
      EnumSet.noneOf(VulkanSurfaceTransformFlagKHR.class);

    for (final VulkanSurfaceTransformFlagKHR flag : VulkanSurfaceTransformFlagKHR.values()) {
      final int fv = flag.value();
      if ((value & fv) == fv) {
        results.add(flag);
      }
    }

    return results;
  }

  private static VulkanExtent2D parseExtent(final VkExtent2D extent)
  {
    return VulkanExtent2D.of(extent.width(), extent.height());
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
          surface.handle,
          supported),
        "vkGetPhysicalDeviceSurfaceSupportKHR");
      if (supported[0] != 0) {
        results.add(queue);
      }
    }

    return results;
  }

  @Override
  public List<VulkanSurfaceFormatKHR> surfaceFormats(
    final VulkanPhysicalDeviceType in_device,
    final VulkanKHRSurfaceType in_surface)
    throws VulkanException
  {
    final VulkanLWJGLPhysicalDevice device =
      VulkanLWJGLClassChecks.check(in_device, VulkanLWJGLPhysicalDevice.class);
    final VulkanLWJGLExtKHRSurfaceValue surface =
      VulkanLWJGLClassChecks.check(in_surface, VulkanLWJGLExtKHRSurfaceValue.class);

    device.checkNotClosed();

    final List<VulkanSurfaceFormatKHR> results;
    try (MemoryStack stack = this.stack_initial.push()) {
      final int[] count = new int[1];

      VulkanChecks.checkReturnCode(
        KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(
          device.device(),
          surface.handle,
          count,
          null),
        "vkGetPhysicalDeviceSurfaceFormatsKHR");

      final int format_count = count[0];
      if (format_count == 0) {
        return List.of();
      }

      final VkSurfaceFormatKHR.Buffer formats =
        VkSurfaceFormatKHR.mallocStack(format_count, stack);

      VulkanChecks.checkReturnCode(
        KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(
          device.device(),
          surface.handle,
          count,
          formats),
        "vkGetPhysicalDeviceSurfaceFormatsKHR");

      results = new ArrayList<>(format_count);
      for (int index = 0; index < format_count; ++index) {
        formats.position(index);

        final Optional<VulkanFormat> format =
          VulkanFormat.fromInteger(formats.format());
        final Optional<VulkanColorSpaceKHR> space =
          VulkanColorSpaceKHR.fromInteger(formats.colorSpace());

        if (format.isPresent() && space.isPresent()) {
          results.add(VulkanSurfaceFormatKHR.of(format.get(), space.get()));
        }
      }
    }

    return results;
  }

  @Override
  public VulkanSurfaceCapabilitiesKHR surfaceCapabilities(
    final VulkanPhysicalDeviceType in_device,
    final VulkanKHRSurfaceType in_surface)
    throws VulkanException
  {
    final VulkanLWJGLPhysicalDevice device =
      VulkanLWJGLClassChecks.check(in_device, VulkanLWJGLPhysicalDevice.class);
    final VulkanLWJGLExtKHRSurfaceValue surface =
      VulkanLWJGLClassChecks.check(in_surface, VulkanLWJGLExtKHRSurfaceValue.class);

    device.checkNotClosed();

    try (MemoryStack stack = this.stack_initial.push()) {

      final VkSurfaceCapabilitiesKHR capabilities =
        VkSurfaceCapabilitiesKHR.mallocStack(stack);

      VulkanChecks.checkReturnCode(
        KHRSurface.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(
          device.device(),
          surface.handle,
          capabilities),
        "vkGetPhysicalDeviceSurfaceCapabilitiesKHR");

      return VulkanSurfaceCapabilitiesKHR.of(
        capabilities.minImageCount(),
        capabilities.maxImageCount(),
        parseExtent(capabilities.currentExtent()),
        parseExtent(capabilities.minImageExtent()),
        parseExtent(capabilities.maxImageExtent()),
        capabilities.maxImageArrayLayers(),
        parseTransform(capabilities.supportedTransforms()),
        parseTransform(capabilities.currentTransform()),
        parseCompositeAlpha(capabilities.supportedCompositeAlpha()),
        parseUsageFlags(capabilities.supportedUsageFlags()));
    }
  }

  @Override
  public List<VulkanPresentModeKHR> surfacePresentModes(
    final VulkanPhysicalDeviceType in_device,
    final VulkanKHRSurfaceType in_surface)
    throws VulkanException
  {
    final VulkanLWJGLPhysicalDevice device =
      VulkanLWJGLClassChecks.check(in_device, VulkanLWJGLPhysicalDevice.class);
    final VulkanLWJGLExtKHRSurfaceValue surface =
      VulkanLWJGLClassChecks.check(in_surface, VulkanLWJGLExtKHRSurfaceValue.class);

    device.checkNotClosed();

    final int[] count = new int[1];
    VulkanChecks.checkReturnCode(
      KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR(
        device.device(),
        surface.handle,
        count,
        null),
      "vkGetPhysicalDeviceSurfacePresentModesKHR");

    final int mode_count = count[0];
    if (mode_count == 0) {
      return List.of();
    }

    final int[] modes = new int[mode_count];
    VulkanChecks.checkReturnCode(
      KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR(
        device.device(),
        surface.handle,
        count,
        modes),
      "vkGetPhysicalDeviceSurfacePresentModesKHR");

    final List<VulkanPresentModeKHR> results = new ArrayList<>(mode_count);
    for (int index = 0; index < mode_count; ++index) {
      VulkanPresentModeKHR.ofInteger(modes[index]).ifPresent(results::add);
    }

    return results;
  }

  static final class VulkanLWJGLExtKHRSurfaceValue
    implements VulkanExtKHRSurfaceType.VulkanKHRSurfaceType
  {
    private long handle;

    VulkanLWJGLExtKHRSurfaceValue(
      final long in_handle)
    {
      this.handle = in_handle;
    }

    long handle()
    {
      return this.handle;
    }
  }
}
