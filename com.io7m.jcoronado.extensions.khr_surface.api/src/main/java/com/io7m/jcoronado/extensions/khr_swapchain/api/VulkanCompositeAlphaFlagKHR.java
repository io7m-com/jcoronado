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

import com.io7m.jcoronado.api.VulkanAPIEnumType;
import com.io7m.jcoronado.api.VulkanEnumBitmaskType;

/**
 * @see "VkCompositeAlphaFlagBitsKHR"
 */

@VulkanAPIEnumType(api = "VK_KHR_swapchain", vulkanEnum = "VkCompositeAlphaFlagBitsKHR")
public enum VulkanCompositeAlphaFlagKHR implements VulkanEnumBitmaskType
{
  /**
   * The alpha channel, if it exists, of the images is ignored in the compositing process. Instead,
   * the image is treated as if it has a constant alpha of 1.0.
   */

  VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR(0x00000001),

  /**
   * The alpha channel, if it exists, of the images is respected in the compositing process. The
   * non-alpha channels of the image are expected to already be multiplied by the alpha channel by
   * the application.
   */

  VK_COMPOSITE_ALPHA_PRE_MULTIPLIED_BIT_KHR(0x00000002),

  /**
   * The alpha channel, if it exists, of the images is respected in the compositing process. The
   * non-alpha channels of the image are not expected to already be multiplied by the alpha channel
   * by the application; instead, the compositor will multiply the non-alpha channels of the image
   * by the alpha channel during compositing.
   */

  VK_COMPOSITE_ALPHA_POST_MULTIPLIED_BIT_KHR(0x00000004),

  /**
   * The way in which the presentation engine treats the alpha channel in the images is unknown to
   * the Vulkan API. Instead, the application is responsible for setting the composite alpha
   * blending mode using native window system commands. If the application does not set the blending
   * mode using native window system commands, then a platform-specific default will be used.
   */

  VK_COMPOSITE_ALPHA_INHERIT_BIT_KHR(0x00000008);

  private final int value;

  VulkanCompositeAlphaFlagKHR(
    final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
