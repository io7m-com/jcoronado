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


package com.io7m.jcoronado.api;

/**
 * <p>
 * Debugging operations that can use the functions exposed by extensions
 * such as {@code VK_EXT_debug_utils} internally.</p>
 * <p>
 * Many operations are silent no-ops if the relevant extensions are
 * not present.</p>
 */

public interface VulkanDebuggingType
{
  /**
   * A debugging region in a command buffer.
   */

  interface VulkanDebugRegionType extends VulkanHandleType
  {
    /**
     * Insert a debug label into a command buffer.
     *
     * @param label The label
     * @param color The label color
     *
     * @throws VulkanException On errors
     */

    @VulkanAPIFunctionType(
      api = "VK_EXT_debug_utils",
      vulkanFunction = "vkCmdInsertDebugUtilsLabelEXT"
    )
    void insertInto(
      String label,
      Color color)
      throws VulkanException;

    /**
     * Insert a debug label into a command buffer.
     *
     * @param label The label
     *
     * @throws VulkanException On errors
     */

    @VulkanAPIFunctionType(
      api = "VK_EXT_debug_utils",
      vulkanFunction = "vkCmdInsertDebugUtilsLabelEXT"
    )
    void insertInto(
      String label)
      throws VulkanException;
  }

  /**
   * A label color.
   *
   * @param red   The red channel
   * @param green The green channel
   * @param blue  The blue channel
   */

  record Color(
    double red,
    double green,
    double blue)
  {

  }

  /**
   * @return The opaque black color
   */

  static Color black()
  {
    return new Color(0.0, 0.0, 0.0);
  }

  /**
   * Set the name of the given object.
   *
   * @param handle The handle
   * @param name   The name
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(
    api = "VK_EXT_debug_utils",
    vulkanFunction = "vkSetDebugUtilsObjectNameEXT"
  )
  void setObjectName(
    VulkanHandleType handle,
    String name)
    throws VulkanException;

  /**
   * Open a command buffer debug label region.
   *
   * @param commandBuffer The command buffer
   * @param label         The region label
   * @param color         The region color
   *
   * @return The region
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(
    api = "VK_EXT_debug_utils",
    vulkanFunction = "vkCmdBeginDebugUtilsLabelEXT"
  )
  VulkanDebugRegionType begin(
    VulkanCommandBufferType commandBuffer,
    String label,
    Color color)
    throws VulkanException;

  /**
   * Open a command buffer debug label region.
   *
   * @param commandBuffer The command buffer
   * @param label         The region label
   *
   * @return The region
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(
    api = "VK_EXT_debug_utils",
    vulkanFunction = "vkCmdBeginDebugUtilsLabelEXT"
  )
  default VulkanDebugRegionType begin(
    final VulkanCommandBufferType commandBuffer,
    final String label)
    throws VulkanException
  {
    return this.begin(commandBuffer, label, black());
  }
}
