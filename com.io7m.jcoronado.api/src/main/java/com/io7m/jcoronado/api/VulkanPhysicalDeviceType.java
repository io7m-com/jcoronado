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

package com.io7m.jcoronado.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A reference to a physical Vulkan device.
 *
 * @see "VkPhysicalDevice"
 */

public interface VulkanPhysicalDeviceType extends VulkanObjectType
{
  /**
   * @return The instance to which the physical device belongs
   */

  VulkanInstanceType instance();

  /**
   * @param layer The layer that will be inspected for extensions
   *
   * @return The available device extensions
   *
   * @throws VulkanException On errors
   */

  Map<String, VulkanExtensionProperties> extensions(
    Optional<String> layer)
    throws VulkanException;

  /**
   * @return The available layers
   *
   * @throws VulkanException On errors
   */

  Map<String, VulkanLayerProperties> layers()
    throws VulkanException;

  /**
   * @return The basic properties of the device
   *
   * @throws VulkanException On errors
   */

  VulkanPhysicalDeviceProperties properties()
    throws VulkanException;

  /**
   * @return The limits of the device
   *
   * @throws VulkanException On errors
   */

  VulkanPhysicalDeviceLimits limits()
    throws VulkanException;

  /**
   * @return The features of the device
   *
   * @throws VulkanException On errors
   */

  VulkanPhysicalDeviceFeatures features()
    throws VulkanException;

  /**
   * @return The memory properties of the device
   *
   * @throws VulkanException On errors
   */

  VulkanPhysicalDeviceMemoryProperties memory()
    throws VulkanException;

  /**
   * @return A read-only list of the available queue families
   *
   * @throws VulkanException On errors
   */

  List<VulkanQueueFamilyProperties> queueFamilies()
    throws VulkanException;

  /**
   * Create a new logical device from this physical device.
   *
   * @param info The creation info
   *
   * @return A new logical device
   *
   * @throws VulkanException On errors
   */

  VulkanLogicalDeviceType createLogicalDevice(
    VulkanLogicalDeviceCreateInfo info)
    throws VulkanException;
}
