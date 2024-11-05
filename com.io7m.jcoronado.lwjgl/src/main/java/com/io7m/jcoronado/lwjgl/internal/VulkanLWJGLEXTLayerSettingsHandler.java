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


package com.io7m.jcoronado.lwjgl.internal;

import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanInstanceExtensionInfoType;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingBoolean;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingFloat32;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingFloat64;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingIntegerSigned32;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingIntegerSigned64;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingIntegerUnsigned32;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingIntegerUnsigned64;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingString;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingType;
import com.io7m.jcoronado.extensions.ext_layer_settings.api.VulkanLayerSettingsCreateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.EXTLayerSettings;
import org.lwjgl.vulkan.VkLayerSettingEXT;
import org.lwjgl.vulkan.VkLayerSettingsCreateInfoEXT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static org.lwjgl.vulkan.EXTLayerSettings.VK_LAYER_SETTING_TYPE_BOOL32_EXT;
import static org.lwjgl.vulkan.EXTLayerSettings.VK_LAYER_SETTING_TYPE_FLOAT32_EXT;
import static org.lwjgl.vulkan.EXTLayerSettings.VK_LAYER_SETTING_TYPE_FLOAT64_EXT;
import static org.lwjgl.vulkan.EXTLayerSettings.VK_LAYER_SETTING_TYPE_INT32_EXT;
import static org.lwjgl.vulkan.EXTLayerSettings.VK_LAYER_SETTING_TYPE_INT64_EXT;
import static org.lwjgl.vulkan.EXTLayerSettings.VK_LAYER_SETTING_TYPE_STRING_EXT;
import static org.lwjgl.vulkan.EXTLayerSettings.VK_LAYER_SETTING_TYPE_UINT32_EXT;
import static org.lwjgl.vulkan.EXTLayerSettings.VK_LAYER_SETTING_TYPE_UINT64_EXT;

/**
 * A handler for the layer settings extension.
 */

public final class VulkanLWJGLEXTLayerSettingsHandler
  implements VulkanLWJGLInstanceExtensionInfoHandlerType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLEXTLayerSettingsHandler.class);

  /**
   * A handler for the layer settings extension.
   */

  public VulkanLWJGLEXTLayerSettingsHandler()
  {

  }

  @Override
  public boolean supports(
    final Class<? extends VulkanInstanceExtensionInfoType> clazz)
  {
    return Objects.equals(clazz, VulkanLayerSettingsCreateInfo.class);
  }

  @Override
  public long transform(
    final MemoryStack stack,
    final VulkanInstanceExtensionInfoType info,
    final long next)
    throws VulkanException
  {
    final var settings =
      (VulkanLayerSettingsCreateInfo) info;

    final var settingsList =
      settings.settings();

    LOG.debug("Configuring layer settings.");
    for (final var setting : settingsList) {
      LOG.debug(
        "Layer setting: {}:{} {}",
        setting.layerName(),
        setting.settingName(),
        setting.values()
      );
    }

    final var settingsCount =
      settingsList.size();
    final var settingArray =
      VkLayerSettingEXT.calloc(settingsCount, stack);

    settingArray.valueCount(settingsCount);
    for (int index = 0; index < settingsCount; ++index) {
      final var settingIn =
        settingsList.get(index);

      packInto(stack, settingIn, settingArray.get(index));
    }

    final var vkInfo =
      VkLayerSettingsCreateInfoEXT.calloc(stack);

    vkInfo.sType(EXTLayerSettings.VK_STRUCTURE_TYPE_LAYER_SETTINGS_CREATE_INFO_EXT);
    vkInfo.pNext(next);
    vkInfo.pSettings(settingArray);

    LOG.trace("Next:           0x{}", Long.toUnsignedString(next, 16));
    LOG.trace("Settings count: {}", vkInfo.settingCount());
    return vkInfo.address();
  }

  private static void packInto(
    final MemoryStack sstack,
    final VulkanLayerSettingType value,
    final VkLayerSettingEXT output)
    throws VulkanException
  {
    output.pLayerName(
      sstack.UTF8(value.layerName()));
    output.pSettingName(
      sstack.UTF8(value.settingName()));

    switch (value) {
      case final VulkanLayerSettingBoolean bool -> {
        output.type(VK_LAYER_SETTING_TYPE_BOOL32_EXT);
        output.pValues(
          VulkanLWJGLScalarArrays.packIntsAsByteBuffer(
            sstack,
            bool.values(),
            x -> x.booleanValue() ? 1 : 0
          )
        );
        output.valueCount(value.values().size());
      }
      case final VulkanLayerSettingFloat32 float32 -> {
        output.type(VK_LAYER_SETTING_TYPE_FLOAT32_EXT);
        output.pValues(
          VulkanLWJGLScalarArrays.packFloatsAsByteBuffer(
            sstack,
            float32.values(),
            Float::floatValue
          )
        );
        output.valueCount(value.values().size());
      }
      case final VulkanLayerSettingFloat64 float64 -> {
        output.type(VK_LAYER_SETTING_TYPE_FLOAT64_EXT);
        output.pValues(
          VulkanLWJGLScalarArrays.packDoublesAsByteBuffer(
            sstack,
            float64.values(),
            Double::doubleValue
          )
        );
        output.valueCount(value.values().size());
      }
      case final VulkanLayerSettingIntegerSigned32 signed32 -> {
        output.type(VK_LAYER_SETTING_TYPE_INT32_EXT);
        output.pValues(
          VulkanLWJGLScalarArrays.packIntsAsByteBuffer(
            sstack,
            signed32.values(),
            Integer::intValue
          )
        );
        output.valueCount(value.values().size());
      }
      case final VulkanLayerSettingIntegerSigned64 signed64 -> {
        output.type(VK_LAYER_SETTING_TYPE_INT64_EXT);
        output.pValues(
          VulkanLWJGLScalarArrays.packLongsAsByteBuffer(
            sstack,
            signed64.values(),
            Long::longValue
          )
        );
        output.valueCount(value.values().size());
      }
      case final VulkanLayerSettingIntegerUnsigned32 unsigned32 -> {
        output.type(VK_LAYER_SETTING_TYPE_UINT32_EXT);
        output.pValues(
          VulkanLWJGLScalarArrays.packIntsAsByteBuffer(
            sstack,
            unsigned32.values(),
            Integer::intValue
          )
        );
        output.valueCount(value.values().size());
      }
      case final VulkanLayerSettingIntegerUnsigned64 unsigned64 -> {
        output.type(VK_LAYER_SETTING_TYPE_UINT64_EXT);
        output.pValues(
          VulkanLWJGLScalarArrays.packLongsAsByteBuffer(
            sstack,
            unsigned64.values(),
            Long::longValue
          )
        );
        output.valueCount(value.values().size());
      }
      case final VulkanLayerSettingString string -> {
        output.type(VK_LAYER_SETTING_TYPE_STRING_EXT);

        final var values =
          string.values();
        final var stringPtrs =
          sstack.callocPointer(values.size());

        for (int index = 0; index < values.size(); ++index) {
          stringPtrs.put(index, sstack.UTF8(values.get(index)));
        }

        output.pValues(MemoryUtil.memByteBuffer(stringPtrs));
        output.valueCount(value.values().size());
      }
    }
  }
}
