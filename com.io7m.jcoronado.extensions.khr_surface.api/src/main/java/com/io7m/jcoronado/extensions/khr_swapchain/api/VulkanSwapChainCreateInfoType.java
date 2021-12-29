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

package com.io7m.jcoronado.extensions.khr_swapchain.api;

import com.io7m.immutables.styles.ImmutablesStyleType;
import com.io7m.jcoronado.api.VulkanAPIStructType;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanFormat;
import com.io7m.jcoronado.api.VulkanImageUsageFlag;
import com.io7m.jcoronado.api.VulkanQueueFamilyIndex;
import com.io7m.jcoronado.api.VulkanSharingMode;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanExtKHRSurfaceType;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanSurfaceTransformFlagKHR;
import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Information needed to create a swap chain.
 *
 * @see "VkSwapchainCreateInfoKHR"
 */

@VulkanAPIStructType(api = "VK_KHR_swapchain", vulkanStruct = "VkSwapchainCreateInfoKHR")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanSwapChainCreateInfoType
{
  /**
   * @return The swapchain's target surface
   */

  @Value.Parameter
  VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface();

  /**
   * @return The minimum number of presentation images the application needs
   */

  @Value.Parameter
  int minimumImageCount();

  /**
   * @return The format of the presentation images
   */

  @Value.Parameter
  VulkanFormat imageFormat();

  /**
   * @return The colorspace of the presentation images
   */

  @Value.Parameter
  VulkanColorSpaceKHR imageColorSpace();

  /**
   * @return The dimensions of the presentation images
   */

  @Value.Parameter
  VulkanExtent2D imageExtent();

  /**
   * @return The number of views for multiview/stereo presentation
   */

  @Value.Parameter
  int imageArrayLayers();

  /**
   * @return The sharing mode used for the presentation images
   */

  @Value.Parameter
  Set<VulkanImageUsageFlag> imageUsageFlags();

  /**
   * @return The sharing mode used for the presentation images
   */

  @Value.Parameter
  VulkanSharingMode imageSharingMode();

  /**
   * @return The queue family indices having access to the images in case of concurrent sharing mode
   */

  @Value.Parameter
  List<VulkanQueueFamilyIndex> queueFamilyIndices();

  /**
   * @return The transform, relative to the device's natural orientation, applied to the image
   * content prior to presentation
   */

  @Value.Parameter
  Set<VulkanSurfaceTransformFlagKHR> preTransform();

  /**
   * @return The alpha blending mode used when compositing this surface with other surfaces in the
   * window system
   */

  @Value.Parameter
  Set<VulkanCompositeAlphaFlagKHR> compositeAlpha();

  /**
   * @return The presentation mode to use for presents on this swap chain
   */

  @Value.Parameter
  VulkanPresentModeKHR presentMode();

  /**
   * @return {@code true} if presentable images may be affected by window clip regions
   */

  @Value.Parameter
  boolean clipped();

  /**
   * @return The existing swap chain to replace, if any
   */

  @Value.Parameter
  Optional<VulkanExtKHRSwapChainType.VulkanKHRSwapChainType> oldSwapChain();
}
