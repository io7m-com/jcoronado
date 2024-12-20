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

package com.io7m.jcoronado.extensions.ext_debug_utils.api;

import com.io7m.jcoronado.api.VulkanAPIFunctionType;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionType;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;

/**
 * @see "VK_EXT_debug_utils"
 */

public interface VulkanDebugUtilsType extends VulkanExtensionType
{
  /**
   * The default label color.
   */

  VulkanDebugUtilsLabelColor DEFAULT_LABEL_COLOR =
    VulkanDebugUtilsLabelColor.builder()
      .setAlpha(1.0f)
      .setRed(0.0f)
      .setBlue(0.0f)
      .setGreen(0.0f)
      .build();

  @Override
  default String name()
  {
    return "VK_EXT_debug_utils";
  }

  /**
   * Create a debug messenger object.
   *
   * @param instance The instance
   * @param info     The creation info
   *
   * @return A messenger
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(
    api = "VK_EXT_debug_utils",
    vulkanFunction = "vkCreateDebugUtilsMessengerEXT"
  )
  VulkanDebugUtilsMessengerEXTType createDebugUtilsMessenger(
    VulkanInstanceType instance,
    VulkanDebugUtilsMessengerCreateInfoEXT info)
    throws VulkanException;

  /**
   * Open a command buffer debug label region.
   *
   * @param commandBuffer The command buffer
   * @param label         The region label
   *
   * @return The section
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(
    api = "VK_EXT_debug_utils",
    vulkanFunction = "vkCmdBeginDebugUtilsLabelEXT"
  )
  VulkanDebugUtilsRegionType begin(
    VulkanCommandBufferType commandBuffer,
    VulkanDebugUtilsLabelEXT label)
    throws VulkanException;

  /**
   * Open a command buffer debug label region.
   *
   * @param commandBuffer The command buffer
   * @param label         The region label
   *
   * @return The section
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(
    api = "VK_EXT_debug_utils",
    vulkanFunction = "vkCmdBeginDebugUtilsLabelEXT"
  )
  default VulkanDebugUtilsRegionType begin(
    final VulkanCommandBufferType commandBuffer,
    final String label)
    throws VulkanException
  {
    return this.begin(
      commandBuffer,
      VulkanDebugUtilsLabelEXT.builder()
        .setColor(DEFAULT_LABEL_COLOR)
        .setName(label)
        .build()
    );
  }

  /**
   * Insert a debug label into a command buffer.
   *
   * @param commandBuffer The command buffer
   * @param label         The label
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(
    api = "VK_EXT_debug_utils",
    vulkanFunction = "vkCmdInsertDebugUtilsLabelEXT"
  )
  void insertInto(
    VulkanCommandBufferType commandBuffer,
    VulkanDebugUtilsLabelEXT label)
    throws VulkanException;

  /**
   * Insert a debug label into a command buffer.
   *
   * @param commandBuffer The command buffer
   * @param label         The label
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(
    api = "VK_EXT_debug_utils",
    vulkanFunction = "vkCmdInsertDebugUtilsLabelEXT"
  )
  default void insertInto(
    final VulkanCommandBufferType commandBuffer,
    final String label)
    throws VulkanException
  {
    this.insertInto(
      commandBuffer,
      VulkanDebugUtilsLabelEXT.builder()
        .setColor(DEFAULT_LABEL_COLOR)
        .setName(label)
        .build()
    );
  }

  /**
   * Set the name of a given object.
   *
   * @param device The logical device
   * @param info   The info
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(
    api = "VK_EXT_debug_utils",
    vulkanFunction = "vkSetDebugUtilsObjectNameEXT"
  )
  void setObjectName(
    VulkanLogicalDeviceType device,
    VulkanDebugUtilsObjectNameInfoEXT info)
    throws VulkanException;
}
