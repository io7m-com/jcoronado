/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import java.util.Objects;
import java.util.UUID;

/**
 * The ID properties for the device.
 *
 * @param deviceUUID      A universally unique identifier for the device
 * @param deviceLUID      A locally unique identifier for the device
 * @param driverUUID      A universally unique identifier for the driver build
 *                        in use by the device
 * @param deviceNodeMask  A bitfield identifying the node within a linked device
 *                        adapter corresponding to the device
 * @param deviceLUIDValid A boolean value that will be {@code true} if
 *                        deviceLUID contains a valid LUID and deviceNodeMask
 *                        contains a valid node mask, and {@code false} if they
 *                        do not
 *
 * @see "VkPhysicalDeviceIDProperties"
 */

public record VulkanPhysicalDeviceIDProperties(
  UUID deviceUUID,
  UUID driverUUID,
  UUID deviceLUID,
  int deviceNodeMask,
  boolean deviceLUIDValid)
{
  /**
   * The ID properties for the device.
   *
   * @param deviceUUID      A universally unique identifier for the device
   * @param deviceLUID      A locally unique identifier for the device
   * @param driverUUID      A universally unique identifier for the driver build
   *                        in use by the device
   * @param deviceNodeMask  A bitfield identifying the node within a linked
   *                        device adapter corresponding to the device
   * @param deviceLUIDValid A boolean value that will be {@code true} if
   *                        deviceLUID contains a valid LUID and deviceNodeMask
   *                        contains a valid node mask, and {@code false} if
   *                        they do not
   *
   * @see "VkPhysicalDeviceIDProperties"
   */

  public VulkanPhysicalDeviceIDProperties
  {
    Objects.requireNonNull(deviceUUID, "deviceUUID");
    Objects.requireNonNull(driverUUID, "driverUUID");
    Objects.requireNonNull(deviceLUID, "deviceLUID");
  }
}
