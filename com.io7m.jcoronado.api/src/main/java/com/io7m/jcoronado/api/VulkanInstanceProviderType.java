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

package com.io7m.jcoronado.api;

import java.util.Map;
import java.util.Optional;

/**
 * Functions to provide Vulkan instances.
 */

public interface VulkanInstanceProviderType
{
  /**
   * @return The name of the (software) provider
   */

  String providerName();

  /**
   * @return The version of the (software) provider
   */

  String providerVersion();

  /**
   * @return The supported instance version
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkEnumerateInstanceVersion")
  VulkanVersion findSupportedInstanceVersion();

  /**
   * @return The available instance extensions
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkEnumerateInstanceExtensionProperties")
  Map<String, VulkanExtensionProperties> extensions()
    throws VulkanException;

  /**
   * @return The available layers
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkEnumerateInstanceLayerProperties")
  Map<String, VulkanLayerProperties> layers()
    throws VulkanException;

  /**
   * Create a new instance.
   *
   * @param info      The creation info
   * @param allocator The optional host allocator
   *
   * @return A new instance
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCreateInstance")
  VulkanInstanceType createInstance(
    VulkanInstanceCreateInfo info,
    Optional<VulkanHostAllocatorType> allocator)
    throws VulkanException;
}
