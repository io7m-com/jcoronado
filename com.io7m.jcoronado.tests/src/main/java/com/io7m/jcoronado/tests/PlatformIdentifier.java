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

package com.io7m.jcoronado.tests;

import com.io7m.jcoronado.api.VulkanApplicationInfo;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanVendorIDs;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import com.io7m.jmulticlose.core.CloseableCollection;

import java.util.Optional;

public final class PlatformIdentifier
{
  private PlatformIdentifier()
  {

  }

  public static void main(
    final String[] args)
    throws Exception
  {
    final var instances =
      VulkanLWJGLInstanceProvider.create();
    final var highest =
      instances.findSupportedInstanceVersion();

    try (var resources = CloseableCollection.create()) {
      final var instanceCreateInfo =
        VulkanInstanceCreateInfo.builder()
        .setApplicationInfo(
          VulkanApplicationInfo.builder()
            .setApplicationName("com.io7m.jcoronado")
            .setApplicationVersion(VulkanVersions.encode(1, 0, 0))
            .setEngineName("com.io7m.jcoronado")
            .setEngineVersion(VulkanVersions.encode(1, 0, 0))
            .setVulkanAPIVersion(VulkanVersions.encode(highest))
            .build())
        .build();

      final var instance =
        resources.add(instances.createInstance(instanceCreateInfo, Optional.empty()));
      final var physicalDevice =
        instance.physicalDevices()
          .get(0);
      final var properties =
        physicalDevice.properties();
      final var driverProperties =
        physicalDevice.driverProperties().orElseThrow();

      System.out.printf(
        "|%s|%s|%s|%s|%s|%s|%s|%n",
        System.getProperty("os.name"),
        System.getProperty("os.arch"),
        VulkanVendorIDs.vendorName(properties.vendorId()),
        properties.name(),
        driverProperties.driverName(),
        properties.apiVersion().toHumanString(),
        properties.driverVersion().toHumanString()
      );
    }
  }
}
