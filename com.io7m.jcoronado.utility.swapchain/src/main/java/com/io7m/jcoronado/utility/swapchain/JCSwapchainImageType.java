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

import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanImageViewType;
import com.io7m.jcoronado.api.VulkanSemaphoreBinaryType;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanExtKHRSwapChainType.VulkanKHRSwapChainType;

/**
 * An image acquired from a swapchain.
 */

public interface JCSwapchainImageType
  extends AutoCloseable
{
  /**
   * @return The image size
   */

  VulkanExtent2D size();

  /**
   * @return The index of the image within the swapchain
   */

  JCSwapchainImageIndex index();

  /**
   * @return The image
   */

  VulkanImageType image();

  /**
   * @return The image view
   */

  VulkanImageViewType imageView();

  @Override
  void close()
    throws VulkanException;

  /**
   * Submit this image for presentation.
   *
   * @throws VulkanException On errors
   */

  void present()
    throws VulkanException;

  /**
   * @return A semaphore signalled when the image is ready
   */

  VulkanSemaphoreBinaryType imageReadySemaphore();

  /**
   * @return A semaphore to be signalled when rendering to the image is done
   */

  VulkanSemaphoreBinaryType renderFinishedSemaphore();

  /**
   * @return The swapchain to which the image belongs
   */

  VulkanKHRSwapChainType swapchain();

  /**
   * @return A fence to be signalled when rendering to the image is done
   */

  VulkanFenceType renderFinishedFence();
}
