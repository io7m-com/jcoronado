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

package com.io7m.jcoronado.tests.device;

import com.io7m.jcoronado.api.VulkanApplicationInfo;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanInstanceProviderType;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import com.io7m.jcoronado.tests.contracts.VulkanLogicalDeviceContract;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

public final class VulkanLWJGLLogicalDeviceTest
  extends VulkanLogicalDeviceContract
{
  private VulkanInstanceProviderType provider;
  private VulkanInstanceType instance;

  private static VulkanInstanceType createInstance(
    final VulkanInstanceProviderType currentProvider)
  {
    try {
      final var info =
        VulkanInstanceCreateInfo.builder()
          .setApplicationInfo(
            VulkanApplicationInfo.of(
              "com.io7m.jcoronado.tests.Test",
              VulkanVersions.encode(0, 0, 1),
              "com.io7m.jcoronado.tests",
              VulkanVersions.encode(0, 0, 1),
              VulkanVersions.encode(1, 0, 0)))
          .setEnabledLayers(List.of("VK_LAYER_KHRONOS_validation"))
          .build();

      return currentProvider.createInstance(info, Optional.empty());
    } catch (final VulkanException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  protected Logger logger()
  {
    return LoggerFactory.getLogger(VulkanLWJGLLogicalDeviceTest.class);
  }

  @Override
  protected VulkanInstanceType instance()
  {
    return this.instance;
  }

  @Override
  protected VulkanPhysicalDeviceType createPhysicalDevice()
    throws VulkanException
  {
    if (this.provider == null) {
      this.provider = VulkanLWJGLInstanceProvider.create();
    }

    var i = this.instance;
    if (i == null) {
      this.instance = createInstance(this.provider);
      i = this.instance;
    }

    return i.enumeratePhysicalDevices().findFirst().get();
  }

  @Override
  protected VulkanLogicalDeviceType createLogicalDevice(
    final VulkanPhysicalDeviceType device)
    throws VulkanException
  {
    final var queue =
      VulkanLogicalDeviceQueueCreateInfo.builder()
        .setQueueCount(1)
        .setQueueFamilyIndex(device.queueFamilies().get(0).queueFamilyIndex())
        .setQueuePriorities(1.0f)
        .build();

    return device.createLogicalDevice(
      VulkanLogicalDeviceCreateInfo.builder()
        .addQueueCreateInfos(queue)
        .build());
  }

  /**
   * Create a logical device with all supported features enabled.
   * @throws VulkanException On errors
   */

  @Test
  public void testCreateLogicalDeviceWithFeatures()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun());

    final var instances =
      VulkanLWJGLInstanceProvider.create();

    final var requestedVersion =
      VulkanVersions.encode(1, 0, 0);

    final var instanceInfo =
      VulkanInstanceCreateInfo.builder()
        .setApplicationInfo(
          VulkanApplicationInfo.of(
            "com.io7m.jcoronado.tests.Test",
            VulkanVersions.encode(0, 0, 1),
            "com.io7m.jcoronado.tests",
            VulkanVersions.encode(0, 0, 1),
            requestedVersion))
        .setEnabledLayers(List.of("VK_LAYER_KHRONOS_validation"))
        .build();

    try (var newInstance =
           instances.createInstance(instanceInfo, Optional.empty())) {

      this.logger().debug(
        "Vulkan Maximum:   {}",
        newInstance.apiVersionMaximumSupported().toHumanString());
      this.logger().debug(
        "Vulkan Requested: {}",
        newInstance.apiVersionUsed().toHumanString());

      try (var physicalDevice =
             newInstance.physicalDevices()
              .get(0)) {

        final var queue =
          VulkanLogicalDeviceQueueCreateInfo.builder()
            .setQueueCount(1)
            .setQueueFamilyIndex(
              physicalDevice.queueFamilies()
                .get(0)
                .queueFamilyIndex())
            .setQueuePriorities(1.0f)
            .build();

        final var logicalInfo =
          VulkanLogicalDeviceCreateInfo.builder()
            .setFeatures(physicalDevice.features())
            .addQueueCreateInfos(queue)
            .build();

        try (var logicalDevice =
               physicalDevice.createLogicalDevice(logicalInfo)) {
          this.logger().debug("waiting for device to idle...");
          logicalDevice.waitIdle();
          assertFalse(logicalDevice.isClosed());
        }
      }
    }
  }

  /**
   * Create a logical device with all supported features enabled, on the highest
   * supported Vulkan version.
   * @throws VulkanException On errors
   */

  @Test
  public void testCreateLogicalDeviceWithFeaturesHighest()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun());

    final var instances =
      VulkanLWJGLInstanceProvider.create();
    final var requestedVersion =
      VulkanVersions.encode(instances.findSupportedInstanceVersion());

    final var instanceInfo =
      VulkanInstanceCreateInfo.builder()
        .setApplicationInfo(
          VulkanApplicationInfo.of(
            "com.io7m.jcoronado.tests.Test",
            VulkanVersions.encode(0, 0, 1),
            "com.io7m.jcoronado.tests",
            VulkanVersions.encode(0, 0, 1),
            requestedVersion))
        .setEnabledLayers(List.of("VK_LAYER_KHRONOS_validation"))
        .build();

    try (var newInstance =
           instances.createInstance(instanceInfo, Optional.empty())) {

      this.logger().debug(
        "Vulkan Maximum:   {}",
        newInstance.apiVersionMaximumSupported().toHumanString());
      this.logger().debug(
        "Vulkan Requested: {}",
        newInstance.apiVersionUsed().toHumanString());

      try (var physicalDevice =
             newInstance.physicalDevices()
               .get(0)) {

        final var queue =
          VulkanLogicalDeviceQueueCreateInfo.builder()
            .setQueueCount(1)
            .setQueueFamilyIndex(
              physicalDevice.queueFamilies()
                .get(0)
                .queueFamilyIndex())
            .setQueuePriorities(1.0f)
            .build();

        final var logicalInfo =
          VulkanLogicalDeviceCreateInfo.builder()
            .setFeatures(physicalDevice.features())
            .addQueueCreateInfos(queue)
            .build();

        try (var logicalDevice =
               physicalDevice.createLogicalDevice(logicalInfo)) {
          this.logger().debug("waiting for device to idle...");
          logicalDevice.waitIdle();
          assertFalse(logicalDevice.isClosed());
        }
      }
    }
  }
}
