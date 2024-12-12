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


package com.io7m.jcoronado.utility.swapchain;

import com.io7m.immutables.styles.ImmutablesStyleType;
import com.io7m.jcoronado.api.VulkanImageUsageFlag;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanExtKHRSurfaceType;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanExtKHRSurfaceType.VulkanKHRSurfaceType;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanSurfaceFormatKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanCompositeAlphaFlagKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanExtKHRSwapChainType;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentModeKHR;
import org.immutables.value.Value;

import java.util.List;
import java.util.Set;

import static com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanCompositeAlphaFlagKHR.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR;

/**
 * The configuration values required to create (and re-create) swapchains.
 */

@ImmutablesStyleType
@Value.Immutable
public interface JCSwapchainConfigurationType
{
  /**
   * @return The logical device.
   */

  VulkanLogicalDeviceType device();

  /**
   * @return The KHR_surface.
   */

  VulkanKHRSurfaceType surface();

  /**
   * @return The KHR_swapchain extension.
   */

  VulkanExtKHRSwapChainType swapChainExtension();

  /**
   * @return The KHR_surface extension.
   */

  VulkanExtKHRSurfaceType surfaceExtension();

  /**
   * @return The queue used for graphics operations
   */

  VulkanQueueType graphicsQueue();

  /**
   * @return The queue used for presentation operations
   */

  VulkanQueueType presentationQueue();

  /**
   * The list of presentation modes to try in order of preference (earlier
   * entries have higher preference).
   *
   * @return The list of presentation modes in preference order
   */

  List<VulkanPresentModeKHR> preferredModes();

  /**
   * The list of surface formats to try in order of preference (earlier
   * entries have higher preference).
   *
   * @return The list of surface formats in preference order
   */

  List<VulkanSurfaceFormatKHR> preferredFormats();

  /**
   * @return The usage flags required to be present on images returned
   * from the swapchain
   */

  Set<VulkanImageUsageFlag> imageUsageFlags();

  /**
   * @return The required surface alpha flags
   */

  @Value.Default
  default Set<VulkanCompositeAlphaFlagKHR> surfaceAlphaFlags()
  {
    return Set.of(VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR);
  }
}
