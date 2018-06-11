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

package com.io7m.jcoronado.extensions.api;

import com.io7m.immutables.styles.ImmutablesStyleType;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanImageUsageFlag;
import org.immutables.value.Value;

import java.util.Set;

/**
 * @see "VkSurfaceCapabilitiesKHR"
 */

@ImmutablesStyleType
@Value.Immutable
public interface VulkanSurfaceCapabilitiesKHRType
{
  /**
   * @return the minimum number of images the specified device supports for a swapchain created for
   * the surface.
   */

  @Value.Parameter
  int minImageCount();

  /**
   * @return the maximum number of images the specified device supports for a swapchain created for
   * the surface. A value of 0 means that there is no limit on the number of images, though there
   * may be limits related to the total amount of memory used by swapchain images.
   */

  @Value.Parameter
  int maxImageCount();

  /**
   * @return the current width and height of the surface, or the special value $(0xFFFFFFFF,
   * 0xFFFFFFFF)$ indicating that the surface size will be determined by the extent of a swapchain
   * targeting the surface.
   */

  @Value.Parameter
  VulkanExtent2D currentExtent();

  /**
   * @return the smallest valid swapchain extent for the surface on the specified device.
   */

  @Value.Parameter
  VulkanExtent2D minImageExtent();

  /**
   * @return the largest valid swapchain extent for the surface on the specified device.
   */

  @Value.Parameter
  VulkanExtent2D maxImageExtent();

  /**
   * @return the maximum number of layers swapchain images can have for a swapchain created for this
   * device and surface.
   */

  @Value.Parameter
  int maxImageArrayLayers();

  /**
   * @return the presentation transforms supported for the surface on the specified device.
   */

  @Value.Parameter
  Set<VulkanSurfaceTransformFlagKHR> supportedTransforms();

  /**
   * @return the surface’s current transform relative to the presentation engine’s natural
   * orientation.
   */

  @Value.Parameter
  Set<VulkanSurfaceTransformFlagKHR> currentTransform();

  /**
   * @return the alpha compositing modes supported by the presentation engine for the surface on the
   * specified device. Opaque composition can be achieved in any alpha compositing mode by either
   * using a swapchain image format that has no alpha component, or by ensuring that all pixels in
   * the swapchain images have an alpha value of 1.0.
   */

  @Value.Parameter
  Set<VulkanCompositeAlphaFlagKHR> supportedCompositeAlpha();

  /**
   * @return the ways the application can use the presentable images of a swapchain created for the
   * surface on the specified device. VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT must be included in the
   * set but implementations may support additional usages.
   */

  @Value.Parameter
  Set<VulkanImageUsageFlag> supportedUsageFlags();
}
