/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
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


package com.io7m.jcoronado.extensions.ext_layer_settings.api;

import java.util.List;

/**
 * A setting value.
 */

public sealed interface VulkanLayerSettingType
  permits VulkanLayerSettingBoolean,
  VulkanLayerSettingFloat32,
  VulkanLayerSettingFloat64,
  VulkanLayerSettingIntegerSigned32,
  VulkanLayerSettingIntegerSigned64,
  VulkanLayerSettingIntegerUnsigned32,
  VulkanLayerSettingIntegerUnsigned64,
  VulkanLayerSettingString
{
  /**
   * @return The name of the layer to which this setting belongs
   */

  String layerName();

  /**
   * @return The name of this setting
   */

  String settingName();

  /**
   * @return The list of values for the setting
   */

  List<?> values();
}
