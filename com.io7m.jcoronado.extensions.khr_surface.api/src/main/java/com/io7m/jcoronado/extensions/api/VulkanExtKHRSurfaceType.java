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

package com.io7m.jcoronado.extensions.api;

import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionType;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;

import java.util.List;

/**
 * @see "VK_KHR_surface"
 */

public interface VulkanExtKHRSurfaceType extends VulkanExtensionType
{
  /**
   * Treat a raw memory address as if it were a window, and create a WSI-compatible surface from it.
   * The caller is responsible for ensuring that the given memory address really does refer to a
   * window.
   *
   * @param instance The current Vulkan instance
   * @param window   The window address
   *
   * @return A surface
   *
   * @throws VulkanException On errors
   */

  VulkanKHRSurfaceType surfaceFromWindow(
    VulkanInstanceType instance,
    long window)
    throws VulkanException;

  @Override
  default String name()
  {
    return "VK_KHR_surface";
  }

  /**
   * Determine which queue families, if any, support presenting to {@code surface}.
   *
   * @param device  The physical device
   * @param surface The surface
   *
   * @return The set of queue families on the physical device that support presenting to {@code
   * surface}
   *
   * @throws VulkanException On errors
   * @see "vkGetPhysicalDeviceSurfaceSupportKHR"
   */

  List<VulkanQueueFamilyProperties> surfaceSupport(
    VulkanPhysicalDeviceType device,
    VulkanKHRSurfaceType surface)
    throws VulkanException;

  /**
   * Determine the preferred formats for {@code surface}.
   *
   * @param device  The physical device
   * @param surface The surface
   *
   * @return The preferred formats for the given surface
   *
   * @throws VulkanException On errors
   * @see "vkGetPhysicalDeviceSurfaceFormatsKHR"
   */

  List<VulkanSurfaceFormatKHR> surfaceFormats(
    VulkanPhysicalDeviceType device,
    VulkanKHRSurfaceType surface)
    throws VulkanException;

  /**
   * Determine the capabilities of {@code surface}.
   *
   * @param device  The physical device
   * @param surface The surface
   *
   * @return The capabilities of the given surface
   *
   * @throws VulkanException On errors
   * @see "vkGetPhysicalDeviceSurfaceCapabilitiesKHR"
   */

  VulkanSurfaceCapabilitiesKHR surfaceCapabilities(
    VulkanPhysicalDeviceType device,
    VulkanKHRSurfaceType surface)
    throws VulkanException;

  /**
   * An abstraction over a native platform surface or window object.
   */

  interface VulkanKHRSurfaceType
  {

  }
}
