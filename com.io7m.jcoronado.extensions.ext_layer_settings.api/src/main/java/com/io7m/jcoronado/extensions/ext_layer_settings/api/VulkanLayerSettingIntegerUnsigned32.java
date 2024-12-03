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
import java.util.Objects;

/**
 * An integer-typed setting.
 *
 * @param layerName   The layer name
 * @param settingName The setting name
 * @param values      The value list
 */

public record VulkanLayerSettingIntegerUnsigned32(
  String layerName,
  String settingName,
  List<Integer> values)
  implements VulkanLayerSettingType
{
  /**
   * An integer-typed setting.
   *
   * @param layerName   The layer name
   * @param settingName The setting name
   * @param values      The value list
   */

  public VulkanLayerSettingIntegerUnsigned32
  {
    Objects.requireNonNull(layerName, "layerName");
    Objects.requireNonNull(settingName, "settingName");
    values = List.copyOf(values);
  }
}
