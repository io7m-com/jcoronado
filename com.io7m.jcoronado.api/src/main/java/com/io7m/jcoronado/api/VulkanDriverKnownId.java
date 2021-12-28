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

/**
 * @see "VkDriverId"
 */

@VulkanAPIEnumType(vulkanEnum = "VkDriverId")
public enum VulkanDriverKnownId
  implements VulkanEnumIntegerType, VulkanDriverIdType
{
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_AMD_PROPRIETARY(1),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_AMD_OPEN_SOURCE(2),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_MESA_RADV(3),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_NVIDIA_PROPRIETARY(4),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_INTEL_PROPRIETARY_WINDOWS(5),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_INTEL_OPEN_SOURCE_MESA(6),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_IMAGINATION_PROPRIETARY(7),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_QUALCOMM_PROPRIETARY(8),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_ARM_PROPRIETARY(9),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_GOOGLE_SWIFTSHADER(10),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_GGP_PROPRIETARY(11),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_BROADCOM_PROPRIETARY(12),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_MESA_LLVMPIPE(13),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_MOLTENVK(14),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_COREAVI_PROPRIETARY(15),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_JUICE_PROPRIETARY(16),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_VERISILICON_PROPRIETARY(17),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_MESA_TURNIP(18),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_MESA_V3DV(19),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_MESA_PANVK(20),
  /**
   * A recognized driver value.
   */
  VK_DRIVER_ID_SAMSUNG_PROPRIETARY(21);

  private final int value;

  VulkanDriverKnownId(final int i)
  {
    this.value = i;
  }

  /**
   * @param driverID A driver ID
   *
   * @return A driver id for the given integer value
   *
   * @throws IllegalArgumentException For unrecognized IDs
   */

  public static VulkanDriverKnownId of(
    final int driverID)
    throws IllegalArgumentException
  {
    final var values = values();
    for (final var value : values) {
      if (value.value == driverID) {
        return value;
      }
    }

    throw new IllegalArgumentException(
      "Unrecognized driver ID: 0x" + Integer.toUnsignedString(driverID, 16));
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
