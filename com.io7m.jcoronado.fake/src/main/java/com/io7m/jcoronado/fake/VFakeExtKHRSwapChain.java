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


package com.io7m.jcoronado.fake;

import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanSemaphoreBinaryType;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanExtKHRSwapChainType;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentInfoKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainAcquisitionResultType;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainCreateInfo;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainTimedOut;

import java.util.List;
import java.util.Objects;

/**
 * The surface extension.
 */

public final class VFakeExtKHRSwapChain implements VulkanExtKHRSwapChainType
{
  private static final VulkanExtensionProperties VK_KHR_SWAPCHAIN =
    VulkanExtensionProperties.of("VK_KHR_swapchain", 1);

  private VulkanKHRSwapChainType nextSwapChain;

  /**
   * Set the next swap chain.
   *
   * @param inNextSwapChain The next swap chain
   */

  public void setNextSwapChain(
    final VulkanKHRSwapChainType inNextSwapChain)
  {
    this.nextSwapChain =
      Objects.requireNonNull(inNextSwapChain, "nextSwapChain");
  }

  /**
   * @return The extension properties
   */

  public static VulkanExtensionProperties properties()
  {
    return VK_KHR_SWAPCHAIN;
  }

  /**
   * Construct an extension.
   */

  public VFakeExtKHRSwapChain()
  {
    this.nextSwapChain =
      new SwapChain();
  }

  @Override
  public VulkanKHRSwapChainType swapChainCreate(
    final VulkanLogicalDeviceType device,
    final VulkanSwapChainCreateInfo info)
  {
    return this.nextSwapChain;
  }

  @Override
  public QueuePresentResult queuePresent(
    final VulkanQueueType queue,
    final VulkanPresentInfoKHR presentInfo)
  {
    return QueuePresentResult.SUCCESS;
  }

  private static final class SwapChain implements VulkanKHRSwapChainType
  {
    SwapChain()
    {

    }

    @Override
    public void close()
    {

    }

    @Override
    public boolean isClosed()
    {
      return false;
    }

    @Override
    public List<VulkanImageType> images()
    {
      return List.of();
    }

    @Override
    public VulkanSwapChainAcquisitionResultType acquireImageWithSemaphore(
      final long timeout,
      final VulkanSemaphoreBinaryType semaphore)
    {
      return new VulkanSwapChainTimedOut();
    }

    @Override
    public VulkanSwapChainAcquisitionResultType acquireImageWithFence(
      final long timeout,
      final VulkanFenceType fence)
    {
      return new VulkanSwapChainTimedOut();
    }

    @Override
    public VulkanSwapChainAcquisitionResultType acquireImageWithSemaphoreAndFence(
      final long timeout,
      final VulkanSemaphoreBinaryType semaphore,
      final VulkanFenceType fence)
    {
      return new VulkanSwapChainTimedOut();
    }
  }
}
