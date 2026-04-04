/*
 * Copyright © 2026 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

package com.io7m.jcoronado.layers.lunarg_gfxreconstruct.api;

import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingBoolean;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingType;

import java.util.List;

/**
 * @param enable Whether to force alignment
 *
 * @see "https://github.com/LunarG/gfxreconstruct/blob/dev/layer/vk_layer_settings.txt"
 * @see "https://vulkan.lunarg.com/doc/sdk/latest/linux/capture_tools.html"
 */

public record VulkanGFXRCapturePageGuardAlignBufferSizes(
  boolean enable)
  implements VulkanGFXRSettingType
{
  @Override
  public VulkanLayerSettingType toSetting()
  {
    return new VulkanLayerSettingBoolean(
      "VK_LAYER_LUNARG_gfxreconstruct",
      "page_guard_align_buffer_sizes",
      List.of(this.enable)
    );
  }
}
