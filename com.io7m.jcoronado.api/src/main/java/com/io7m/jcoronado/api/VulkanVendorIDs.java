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

/**
 * Functions involving vendor IDs.
 */

public final class VulkanVendorIDs
{
  private VulkanVendorIDs()
  {

  }

  /**
   * Guess a vendor ID name from the given vendor ID.
   *
   * @param id The vendor ID
   *
   * @return A vendor name
   *
   * @see VulkanPhysicalDevicePropertiesType#vendorId()
   */

  public static String vendorName(final int id)
  {
    return switch (id) {
      case 0x1002 -> "AMD";
      case 0x1010 -> "ImgTec";
      case 0x10DE -> "NVIDIA";
      case 0x13B5 -> "ARM";
      case 0x5143 -> "Qualcomm";
      case 0x8086 -> "INTEL";
      default -> "Unknown Vendor";
    };
  }
}
