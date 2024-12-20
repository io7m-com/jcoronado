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

import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionType;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanHandleType;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanSemaphoreBinaryType;

import java.util.List;

/**
 * @see "VK_KHR_swapchain"
 */

public interface VulkanExtKHRSwapChainType extends VulkanExtensionType
{
  @Override
  default String name()
  {
    return "VK_KHR_swapchain";
  }

  @Override
  default List<String> extraNames()
  {
    return List.of(
      "VK_EXT_swapchain_maintenance1"
    );
  }

  /**
   * Create a new swap chain.
   *
   * @param device The physical device
   * @param info   The creation info
   *
   * @return The capabilities of the given surface
   *
   * @throws VulkanException On errors
   * @see "vkCreateSwapchainKHR"
   */

  VulkanKHRSwapChainType swapChainCreate(
    VulkanLogicalDeviceType device,
    VulkanSwapChainCreateInfo info)
    throws VulkanException;

  /**
   * Queue images for presentation.
   *
   * @param queue       The presentation queue
   * @param presentInfo The presentation info
   *
   * @return The present result
   *
   * @throws VulkanException On errors
   * @see "vkQueuePresentKHR"
   */

  QueuePresentResult queuePresent(
    VulkanQueueType queue,
    VulkanPresentInfoKHR presentInfo)
    throws VulkanException;

  /**
   * The result of presenting an image.
   */

  enum QueuePresentResult
  {
    /**
     * Presenting succeeded.
     */

    SUCCESS,

    /**
     * Presenting succeeded but the swapchain is suboptimal.
     */

    SUBOPTIMAL
  }

  /**
   * A created swap chain.
   */

  interface VulkanKHRSwapChainType extends VulkanHandleType
  {
    /**
     * @return The list of images associated with the swap chain
     *
     * @throws VulkanException On errors
     */

    List<VulkanImageType> images()
      throws VulkanException;

    /**
     * Attempt to acquire an image from the swap chain. If {@code timeout} is {@code 0}, the method
     * will return immediately. If {@code timeout} is {@code 0xffffffff_ffffffff}, the method will
     * wait indefinitely.
     *
     * @param semaphore A semaphore that will be signalled when an image is available
     * @param timeout   A timeout value in nanoseconds.
     *
     * @return An image acquisition
     *
     * @throws VulkanException On errors
     */

    VulkanSwapChainAcquisitionResultType acquireImageWithSemaphore(
      long timeout,
      VulkanSemaphoreBinaryType semaphore)
      throws VulkanException;

    /**
     * Attempt to acquire an image from the swap chain. If {@code timeout} is {@code 0}, the method
     * will return immediately. If {@code timeout} is {@code 0xffffffff_ffffffff}, the method will
     * wait indefinitely.
     *
     * @param fence   A fence that will be signalled when an image is available
     * @param timeout A timeout value in nanoseconds.
     *
     * @return An image acquisition
     *
     * @throws VulkanException On errors
     */

    VulkanSwapChainAcquisitionResultType acquireImageWithFence(
      long timeout,
      VulkanFenceType fence)
      throws VulkanException;

    /**
     * Attempt to acquire an image from the swap chain. If {@code timeout} is {@code 0}, the method
     * will return immediately. If {@code timeout} is {@code 0xffffffff_ffffffff}, the method will
     * wait indefinitely.
     *
     * @param fence     A fence that will be signalled when an image is available
     * @param semaphore A semaphore that will be signalled when an image is available
     * @param timeout   A timeout value in nanoseconds.
     *
     * @return An image acquisition
     *
     * @throws VulkanException On errors
     */

    VulkanSwapChainAcquisitionResultType acquireImageWithSemaphoreAndFence(
      long timeout,
      VulkanSemaphoreBinaryType semaphore,
      VulkanFenceType fence)
      throws VulkanException;
  }
}
