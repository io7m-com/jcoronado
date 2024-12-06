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

package com.io7m.jcoronado.tests.contracts;

import com.io7m.jcoronado.api.VulkanApplicationInfo;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanInstanceProviderType;
import com.io7m.jcoronado.api.VulkanMissingRequiredVersionException;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingBoolean;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingIntegerSigned32;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingIntegerSigned64;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingIntegerUnsigned32;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingIntegerUnsigned64;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingString;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingsCreateInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class VulkanInstanceContract extends VulkanOnDeviceContract
{
  protected abstract VulkanInstanceProviderType instanceProvider();

  @Test
  public final void testInstanceVulkan10()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var provider = this.instanceProvider();
    final var logger = this.logger();

    final var info =
      VulkanInstanceCreateInfo.builder()
        .setApplicationInfo(
          VulkanApplicationInfo.of(
            "com.io7m.jcoronado.tests.Test",
            VulkanVersions.encode(0, 0, 1),
            "com.io7m.jcoronado.tests",
            VulkanVersions.encode(0, 0, 1),
            VulkanVersions.encode(1, 0, 0)))
        .setEnabledExtensions(List.of())
        .setEnabledLayers(List.of("VK_LAYER_KHRONOS_validation"))
        .build();

    assertThrows(VulkanMissingRequiredVersionException.class, () -> {
      provider.createInstance(info, Optional.empty());
    });
  }

  @Test
  public final void testInstanceVulkanHighest()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var provider = this.instanceProvider();
    final var logger = this.logger();

    final var supported =
      provider.findSupportedInstanceVersion();

    final var info =
      VulkanInstanceCreateInfo.builder()
        .setApplicationInfo(
          VulkanApplicationInfo.of(
            "com.io7m.jcoronado.tests.Test",
            VulkanVersions.encode(0, 0, 1),
            "com.io7m.jcoronado.tests",
            VulkanVersions.encode(0, 0, 1),
            VulkanVersions.encode(supported)))
        .setEnabledExtensions(List.of())
        .setEnabledLayers(List.of("VK_LAYER_KHRONOS_validation"))
        .build();

    try (var instance =
           provider.createInstance(info, Optional.empty())) {
      instance.enabledExtensions().forEach(
        (name, extension) -> logger.debug("extension: {}", extension.name()));
    }
  }

  @Test
  public final void testInstancePhysicalDevices10()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var info =
      VulkanInstanceCreateInfo.builder()
        .setApplicationInfo(
          VulkanApplicationInfo.of(
            "com.io7m.jcoronado.tests.Test",
            VulkanVersions.encode(0, 0, 1),
            "com.io7m.jcoronado.tests",
            VulkanVersions.encode(0, 0, 1),
            VulkanVersions.encode(this.instanceProvider().minimumRequiredVersion())))
        .setEnabledExtensions(List.of())
        .setEnabledLayers(List.of("VK_LAYER_KHRONOS_validation"))
        .build();

    final var provider = this.instanceProvider();
    final var logger = this.logger();
    try (var instance =
           provider.createInstance(info, Optional.empty())) {
      final var devices = instance.physicalDevices();
      Assertions.assertTrue(devices.size() > 0, "At least one device required");
      for (final var device : devices) {
        logger.debug("device: {}", device.properties().name());
        logger.debug("features: {}", device.features());
        logger.debug("properties: {}", device.properties());
        logger.debug("limits: {}", device.limits());
      }
    }
  }

  @Test
  public final void testInstancePhysicalDevicesHighest()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var provider = this.instanceProvider();

    final var supported =
      provider.findSupportedInstanceVersion();

    final var info =
      VulkanInstanceCreateInfo.builder()
        .setApplicationInfo(
          VulkanApplicationInfo.of(
            "com.io7m.jcoronado.tests.Test",
            VulkanVersions.encode(0, 0, 1),
            "com.io7m.jcoronado.tests",
            VulkanVersions.encode(0, 0, 1),
            VulkanVersions.encode(supported)))
        .setEnabledExtensions(List.of())
        .setEnabledLayers(List.of("VK_LAYER_KHRONOS_validation"))
        .build();

    final var logger = this.logger();
    try (var instance =
           provider.createInstance(info, Optional.empty())) {
      final var devices = instance.physicalDevices();
      Assertions.assertTrue(devices.size() > 0, "At least one device required");
      for (final var device : devices) {
        logger.debug("device: {}", device.properties().name());
        logger.debug("features: {}", device.features());
        logger.debug("properties: {}", device.properties());
        logger.debug("limits: {}", device.limits());
      }
    }
  }

  @Test
  public final void testInstanceValidationLayerSettings0()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var info =
      VulkanInstanceCreateInfo.builder()
        .setApplicationInfo(
          VulkanApplicationInfo.of(
            "com.io7m.jcoronado.tests.Test",
            VulkanVersions.encode(0, 0, 1),
            "com.io7m.jcoronado.tests",
            VulkanVersions.encode(0, 0, 1),
            VulkanVersions.encode(this.instanceProvider().minimumRequiredVersion())))
        .setEnabledExtensions(List.of())
        .setEnabledLayers(List.of("VK_LAYER_KHRONOS_validation"))
        .addExtensionInfo(
          new VulkanLayerSettingsCreateInfo(
            List.of(
              new VulkanLayerSettingBoolean(
                "VK_LAYER_KHRONOS_validation",
                "validate_core",
                List.of(TRUE)
              ),
              new VulkanLayerSettingBoolean(
                "VK_LAYER_KHRONOS_validation",
                "validate_sync",
                List.of(TRUE)
              )
            )
          )
        ).build();

    final var provider = this.instanceProvider();
    final var logger = this.logger();
    try (var instance =
           provider.createInstance(info, Optional.empty())) {
      final var devices = instance.physicalDevices();
      Assertions.assertTrue(devices.size() > 0, "At least one device required");
      for (final var device : devices) {
        logger.debug("device: {}", device.properties().name());
        logger.debug("features: {}", device.features());
        logger.debug("properties: {}", device.properties());
        logger.debug("limits: {}", device.limits());
      }
    }
  }

  @Test
  public final void testInstanceValidationLayerSettings1()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var info =
      VulkanInstanceCreateInfo.builder()
        .setApplicationInfo(
          VulkanApplicationInfo.of(
            "com.io7m.jcoronado.tests.Test",
            VulkanVersions.encode(0, 0, 1),
            "com.io7m.jcoronado.tests",
            VulkanVersions.encode(0, 0, 1),
            VulkanVersions.encode(this.instanceProvider().minimumRequiredVersion())))
        .setEnabledExtensions(List.of())
        .setEnabledLayers(List.of("VK_LAYER_KHRONOS_validation"))
        .addExtensionInfo(
          new VulkanLayerSettingsCreateInfo(
            List.of(
              new VulkanLayerSettingIntegerSigned32(
                "VK_LAYER_KHRONOS_validation",
                "validate_core",
                List.of(1)
              ),
              new VulkanLayerSettingIntegerSigned64(
                "VK_LAYER_KHRONOS_validation",
                "validate_sync",
                List.of(1L)
              )
            )
          )
        ).build();

    final var provider = this.instanceProvider();
    final var logger = this.logger();
    try (var instance =
           provider.createInstance(info, Optional.empty())) {
      final var devices = instance.physicalDevices();
      Assertions.assertTrue(devices.size() > 0, "At least one device required");
      for (final var device : devices) {
        logger.debug("device: {}", device.properties().name());
        logger.debug("features: {}", device.features());
        logger.debug("properties: {}", device.properties());
        logger.debug("limits: {}", device.limits());
      }
    }
  }

  @Test
  public final void testInstanceValidationLayerSettings2()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var info =
      VulkanInstanceCreateInfo.builder()
        .setApplicationInfo(
          VulkanApplicationInfo.of(
            "com.io7m.jcoronado.tests.Test",
            VulkanVersions.encode(0, 0, 1),
            "com.io7m.jcoronado.tests",
            VulkanVersions.encode(0, 0, 1),
            VulkanVersions.encode(this.instanceProvider().minimumRequiredVersion())))
        .setEnabledExtensions(List.of())
        .setEnabledLayers(List.of("VK_LAYER_KHRONOS_validation"))
        .addExtensionInfo(
          new VulkanLayerSettingsCreateInfo(
            List.of(
              new VulkanLayerSettingIntegerUnsigned32(
                "VK_LAYER_KHRONOS_validation",
                "validate_core",
                List.of(1)
              ),
              new VulkanLayerSettingIntegerUnsigned64(
                "VK_LAYER_KHRONOS_validation",
                "validate_sync",
                List.of(1L)
              )
            )
          )
        ).build();

    final var provider = this.instanceProvider();
    final var logger = this.logger();
    try (var instance =
           provider.createInstance(info, Optional.empty())) {
      final var devices = instance.physicalDevices();
      Assertions.assertTrue(devices.size() > 0, "At least one device required");
      for (final var device : devices) {
        logger.debug("device: {}", device.properties().name());
        logger.debug("features: {}", device.features());
        logger.debug("properties: {}", device.properties());
        logger.debug("limits: {}", device.limits());
      }
    }
  }

  @Test
  public final void testInstanceValidationLayerSettings3()
    throws VulkanException
  {
    Assumptions.assumeTrue(this.shouldRun(), "Test should run");

    final var info =
      VulkanInstanceCreateInfo.builder()
        .setApplicationInfo(
          VulkanApplicationInfo.of(
            "com.io7m.jcoronado.tests.Test",
            VulkanVersions.encode(0, 0, 1),
            "com.io7m.jcoronado.tests",
            VulkanVersions.encode(0, 0, 1),
            VulkanVersions.encode(this.instanceProvider().minimumRequiredVersion())))
        .setEnabledExtensions(List.of())
        .setEnabledLayers(List.of("VK_LAYER_KHRONOS_validation"))
        .addExtensionInfo(
          new VulkanLayerSettingsCreateInfo(
            List.of(
              new VulkanLayerSettingString(
                "VK_LAYER_KHRONOS_validation",
                "validate_core",
                List.of("true")
              ),
              new VulkanLayerSettingString(
                "VK_LAYER_KHRONOS_validation",
                "validate_sync",
                List.of("true")
              )
            )
          )
        ).build();

    final var provider = this.instanceProvider();
    final var logger = this.logger();
    try (var instance =
           provider.createInstance(info, Optional.empty())) {
      final var devices = instance.physicalDevices();
      Assertions.assertTrue(devices.size() > 0, "At least one device required");
      for (final var device : devices) {
        logger.debug("device: {}", device.properties().name());
        logger.debug("features: {}", device.features());
        logger.debug("properties: {}", device.properties());
        logger.debug("limits: {}", device.limits());
      }
    }
  }
}
