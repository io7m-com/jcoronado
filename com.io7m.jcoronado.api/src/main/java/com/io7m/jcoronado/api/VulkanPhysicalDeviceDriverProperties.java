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

/**
 * Physical device driver properties.
 *
 * @param driverId           The driver ID
 * @param conformanceVersion The test suite conformance version
 * @param driverInfo         The driver info
 * @param driverName         The driver name
 *
 * @see "VkPhysicalDeviceDriverProperties"
 */

@VulkanAPIStructType(vulkanStruct = "VkPhysicalDeviceDriverProperties")
public record VulkanPhysicalDeviceDriverProperties(
  VulkanDriverIdType driverId,
  String driverName,
  String driverInfo,
  VulkanConformanceVersion conformanceVersion)
{
  /**
   * Physical device driver properties.
   *
   * @param driverId           The driver ID
   * @param conformanceVersion The test suite conformance version
   * @param driverInfo         The driver info
   * @param driverName         The driver name
   *
   * @see "VkPhysicalDeviceDriverProperties"
   */

  public VulkanPhysicalDeviceDriverProperties
  {
    Objects.requireNonNull(driverId, "driverId");
    Objects.requireNonNull(driverName, "driverName");
    Objects.requireNonNull(driverInfo, "driverInfo");
    Objects.requireNonNull(conformanceVersion, "conformanceVersion");
  }
}
