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
 * The basic properties of a device.
 *
 * @see "VkPhysicalDeviceProperties"
 */

@ImmutablesStyleType
@Value.Immutable
public interface VulkanPhysicalDevicePropertiesType
{
  /**
   * @return The device name
   */

  @Value.Parameter
  String name();

  /**
   * @return The device type
   */

  @Value.Parameter
  Type type();

  /**
   * @return The device ID (unique for a specific vendor)
   */

  @Value.Parameter
  int id();

  /**
   * @return The vendor ID
   */

  @Value.Parameter
  int vendorId();

  /**
   * @return The highest version of Vulkan supported
   */

  @Value.Parameter
  VulkanVersion apiVersion();

  /**
   * @return The version of the underlying device driver
   */

  @Value.Parameter
  VulkanVersion driverVersion();

  /**
   * The type of device.
   */

  enum Type
  {
    /**
     * The device does not match any other available types.
     */

    VK_PHYSICAL_DEVICE_TYPE_OTHER(0),

    /**
     * The device is typically one embedded in or tightly coupled with the host.
     */

    VK_PHYSICAL_DEVICE_TYPE_INTEGRATED_GPU(1),

    /**
     * The device is typically a separate processor connected to the host via
     * an interlink.
     */

    VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU(2),

    /**
     * The device is typically a virtual node in a virtualization environment.
     */

    VK_PHYSICAL_DEVICE_TYPE_VIRTUAL_GPU(3),

    /**
     * The device is typically running on the same processors as the host.
     */

    VK_PHYSICAL_DEVICE_TYPE_CPU(4);

    private final int value;

    Type(final int i)
    {
      this.value = i;
    }

    /**
     * @param x An integer representation of the type
     *
     * @return A device type for the given integer
     *
     * @see #value()
     */

    public static Type ofInt(
      final int x)
    {
      switch (x) {
        case 0:
          return VK_PHYSICAL_DEVICE_TYPE_OTHER;
        case 1:
          return VK_PHYSICAL_DEVICE_TYPE_INTEGRATED_GPU;
        case 2:
          return VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU;
        case 3:
          return VK_PHYSICAL_DEVICE_TYPE_VIRTUAL_GPU;
        case 4:
          return VK_PHYSICAL_DEVICE_TYPE_CPU;
        default:
          return VK_PHYSICAL_DEVICE_TYPE_OTHER;
      }
    }

    /**
     * @return The integer value of the type
     */

    public int value()
    {
      return this.value;
    }
  }
}
