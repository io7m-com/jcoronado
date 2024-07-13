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

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

/**
 * The features supported by a physical device.
 *
 * @see "VkPhysicalDeviceFeatures"
 */

@VulkanAPIStructType(vulkanStruct = "VkPhysicalDeviceFeatures")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPhysicalDeviceFeaturesType
{
  /**
   * @return The features supported by a physical Vulkan 1.0 device.
   *
   * @see "VkPhysicalDeviceVulkan11Features"
   */

  VulkanPhysicalDeviceFeatures10 features10();

  /**
   * @return The features supported by a physical Vulkan 1.1 device.
   *
   * @see "VkPhysicalDeviceVulkan11Features"
   */

  VulkanPhysicalDeviceFeatures11 features11();

  /**
   * @return The features supported by a physical Vulkan 1.2 device.
   *
   * @see "VkPhysicalDeviceVulkan12Features"
   */

  VulkanPhysicalDeviceFeatures12 features12();

  /**
   * @return The features supported by a physical Vulkan 1.3 device.
   *
   * @see "VkPhysicalDeviceVulkan13Features"
   */

  VulkanPhysicalDeviceFeatures13 features13();
}
