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

package com.io7m.jcoronado.lwjgl;

import com.io7m.jcoronado.api.VulkanCallFailedException;
import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanSemaphoreType;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import com.io7m.jcoronado.extensions.api.VulkanExtKHRSwapChainType;
import com.io7m.jcoronado.extensions.api.VulkanPresentInfoKHR;
import com.io7m.jcoronado.extensions.api.VulkanSwapChainCreateInfo;
import com.io7m.jcoronado.extensions.api.VulkanSwapChainImageAcquisition;
import com.io7m.junreachable.UnreachableCodeException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkExtent2D;
import org.lwjgl.vulkan.VkPresentInfoKHR;
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.io7m.jcoronado.lwjgl.VulkanLWJGLHandle.Ownership.USER_OWNED;
import static com.io7m.jcoronado.lwjgl.VulkanLWJGLHandle.Ownership.VULKAN_OWNED;

/**
 * Access to the {@code VK_KHR_swapchain} extension.
 */

public final class VulkanLWJGLExtKHRSwapChain implements VulkanExtKHRSwapChainType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLExtKHRSwapChain.class);

  private final MemoryStack stack_initial;

  VulkanLWJGLExtKHRSwapChain()
  {
    this.stack_initial = MemoryStack.create();
  }

  private static IntBuffer packQueueIndices(
    final MemoryStack stack,
    final List<Integer> integers)
  {
    final IntBuffer buffer = stack.mallocInt(integers.size());
    for (int index = 0; index < integers.size(); ++index) {
      buffer.put(index, integers.get(index).intValue());
    }
    return buffer;
  }

  private static long mapOldSwapChain(
    final Optional<VulkanKHRSwapChainType> swap_chain)
  {
    return swap_chain.map(chain -> {
      try {
        return Long.valueOf(
          VulkanLWJGLClassChecks.check(chain, VulkanLWJGLKHRSwapChain.class).chain());
      } catch (VulkanIncompatibleClassException e) {
        throw new VulkanUncheckedException(e);
      }
    }).orElse(Long.valueOf(0L)).longValue();
  }

  private static VkExtent2D packExtent(
    final MemoryStack stack,
    final VulkanExtent2D e)
  {
    return VkExtent2D.mallocStack(stack).set(e.width(), e.height());
  }

  private static LongBuffer packSemaphoresX(
    final MemoryStack stack,
    final List<VulkanSemaphoreType> semaphores)
    throws VulkanIncompatibleClassException
  {
    final LongBuffer buffer_semaphores = stack.mallocLong(semaphores.size());
    for (int index = 0; index < semaphores.size(); ++index) {
      final VulkanLWJGLSemaphore semaphore =
        VulkanLWJGLClassChecks.check(semaphores.get(index), VulkanLWJGLSemaphore.class);
      buffer_semaphores.put(index, semaphore.handle());
    }
    return buffer_semaphores;
  }

  private static IntBuffer packImageIndices(
    final MemoryStack stack,
    final List<Integer> indices)
  {
    final IntBuffer buffer_indices = stack.mallocInt(indices.size());
    for (int index = 0; index < indices.size(); ++index) {
      buffer_indices.put(index, indices.get(index).intValue());
    }
    return buffer_indices;
  }

  private static LongBuffer packSwapChains(
    final MemoryStack stack,
    final List<VulkanKHRSwapChainType> swap_chains,
    final int swap_chain_count)
    throws VulkanIncompatibleClassException
  {
    final LongBuffer buffer_swapchains = stack.mallocLong(swap_chain_count);
    for (int index = 0; index < swap_chain_count; ++index) {
      final VulkanLWJGLKHRSwapChain swap_chain =
        VulkanLWJGLClassChecks.check(swap_chains.get(index), VulkanLWJGLKHRSwapChain.class);
      buffer_swapchains.put(index, swap_chain.chain);
    }
    return buffer_swapchains;
  }

  @Override
  public String toString()
  {
    return new StringBuilder(64)
      .append('[')
      .append(this.getClass().getCanonicalName())
      .append(" ")
      .append(this.name())
      .append(']')
      .toString();
  }

  private List<VulkanImageType> images(
    final VulkanLWJGLKHRSwapChain chain,
    final VulkanLWJGLHostAllocatorProxy host_allocator_proxy)
    throws VulkanException
  {
    final int[] count = new int[1];

    VulkanChecks.checkReturnCode(
      KHRSwapchain.vkGetSwapchainImagesKHR(chain.device.device(), chain.chain, count, null),
      "vkGetSwapchainImagesKHR");

    final int image_count = count[0];
    if (image_count == 0) {
      return List.of();
    }

    final long[] images = new long[image_count];
    VulkanChecks.checkReturnCode(
      KHRSwapchain.vkGetSwapchainImagesKHR(chain.device.device(), chain.chain, count, images),
      "vkGetSwapchainImagesKHR");

    return LongStream.of(images)
      .mapToObj(image -> new VulkanLWJGLImage(VULKAN_OWNED, image, host_allocator_proxy))
      .collect(Collectors.toList());
  }

  private VulkanSwapChainImageAcquisition acquireImage(
    final VulkanLWJGLKHRSwapChain chain,
    final long timeout,
    final VulkanSemaphoreType semaphore,
    final VulkanFenceType fence)
    throws VulkanIncompatibleClassException, VulkanCallFailedException
  {
    final long semaphore_handle;
    if (semaphore != null) {
      semaphore_handle =
        VulkanLWJGLClassChecks.check(semaphore, VulkanLWJGLSemaphore.class).handle();
    } else {
      semaphore_handle = VK10.VK_NULL_HANDLE;
    }

    final long fence_handle;
    if (fence != null) {
      fence_handle =
        VulkanLWJGLClassChecks.check(fence, VulkanLWJGLFence.class).handle();
    } else {
      fence_handle = VK10.VK_NULL_HANDLE;
    }

    final int[] image_handles = new int[1];
    final int result =
      KHRSwapchain.vkAcquireNextImageKHR(
        chain.device.device(),
        chain.chain,
        timeout,
        semaphore_handle,
        fence_handle,
        image_handles);

    final int image_handle = image_handles[0];
    switch (result) {
      case VK10.VK_TIMEOUT:
        return VulkanSwapChainImageAcquisition.builder()
          .setSubOptimal(false)
          .setTimedOut(true)
          .build();
      case VK10.VK_NOT_READY:
        return VulkanSwapChainImageAcquisition.builder()
          .setSubOptimal(false)
          .setTimedOut(false)
          .build();
      case VK10.VK_SUCCESS:
        return VulkanSwapChainImageAcquisition.builder()
          .setSubOptimal(false)
          .setTimedOut(false)
          .setImageIndex(image_handle)
          .build();
      case KHRSwapchain.VK_SUBOPTIMAL_KHR:
        return VulkanSwapChainImageAcquisition.builder()
          .setSubOptimal(true)
          .setTimedOut(false)
          .setImageIndex(image_handle)
          .build();
      default:
        VulkanChecks.checkReturnCode(result, "vkAcquireNextImageKHR");
    }

    throw new UnreachableCodeException();
  }

  @Override
  public VulkanKHRSwapChainType swapChainCreate(
    final VulkanLogicalDeviceType in_device,
    final VulkanSwapChainCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(in_device, "in_device");
    Objects.requireNonNull(info, "info");

    final VulkanLWJGLLogicalDevice device =
      VulkanLWJGLClassChecks.check(in_device, VulkanLWJGLLogicalDevice.class);

    Objects.requireNonNull(info, "info");

    final VulkanLWJGLExtKHRSurface.VulkanLWJGLExtKHRSurfaceValue surface =
      VulkanLWJGLClassChecks.check(
        info.surface(),
        VulkanLWJGLExtKHRSurface.VulkanLWJGLExtKHRSurfaceValue.class);

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkSwapchainCreateInfoKHR vk_info =
        VkSwapchainCreateInfoKHR.mallocStack(stack)
          .sType(KHRSwapchain.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR)
          .pNext(0L)
          .minImageCount(info.minimumImageCount())
          .imageFormat(info.imageFormat().value())
          .imageColorSpace(info.imageColorSpace().value())
          .imageExtent(packExtent(stack, info.imageExtent()))
          .imageArrayLayers(info.imageArrayLayers())
          .imageUsage(VulkanEnumMaps.packValues(info.imageUsageFlags()))
          .imageSharingMode(info.imageSharingMode().value())
          .preTransform(VulkanEnumMaps.packValues(info.preTransform()))
          .compositeAlpha(VulkanEnumMaps.packValues(info.compositeAlpha()))
          .presentMode(info.presentMode().value())
          .clipped(info.clipped())
          .pQueueFamilyIndices(packQueueIndices(stack, info.queueFamilyIndices()))
          .surface(surface.handle())
          .oldSwapchain(mapOldSwapChain(info.oldSwapChain()));

      final long[] swapchain = new long[1];
      VulkanChecks.checkReturnCode(
        KHRSwapchain.vkCreateSwapchainKHR(device.device(), vk_info, null, swapchain),
        "vkCreateSwapchainKHR");

      final long swapchain_address = swapchain[0];
      if (LOG.isDebugEnabled()) {
        LOG.debug("created swapchain: {}", Long.toUnsignedString(swapchain_address, 16));
      }
      return new VulkanLWJGLKHRSwapChain(
        this, device, swapchain_address, device.hostAllocatorProxy());
    }
  }

  @Override
  public void queuePresent(
    final VulkanQueueType in_queue,
    final VulkanPresentInfoKHR present_info)
    throws VulkanException
  {
    final VulkanLWJGLQueue queue =
      VulkanLWJGLClassChecks.check(in_queue, VulkanLWJGLQueue.class);

    Objects.requireNonNull(present_info, "present_info");

    try (MemoryStack stack = this.stack_initial.push()) {
      final IntBuffer buffer_indices =
        packImageIndices(stack, present_info.imageIndices());

      final List<VulkanKHRSwapChainType> swap_chains = present_info.swapChains();
      final int swap_chain_count = swap_chains.size();
      final LongBuffer buffer_swapchains =
        packSwapChains(stack, swap_chains, swap_chain_count);

      final LongBuffer buffer_semaphores =
        packSemaphoresX(stack, present_info.waitSemaphores());

      final VkPresentInfoKHR vk_info =
        VkPresentInfoKHR.mallocStack(stack)
          .sType(KHRSwapchain.VK_STRUCTURE_TYPE_PRESENT_INFO_KHR)
          .pNext(0L)
          .pImageIndices(buffer_indices)
          .pSwapchains(buffer_swapchains)
          .swapchainCount(swap_chain_count)
          .pWaitSemaphores(buffer_semaphores);

      VulkanChecks.checkReturnCode(
        KHRSwapchain.vkQueuePresentKHR(queue.rawQueue(), vk_info),
        "vkQueuePresentKHR");
    }
  }

  private static final class VulkanLWJGLKHRSwapChain
    extends VulkanLWJGLHandle implements VulkanKHRSwapChainType
  {
    private final long chain;
    private final VulkanLWJGLLogicalDevice device;
    private final VulkanLWJGLExtKHRSwapChain extension;

    VulkanLWJGLKHRSwapChain(
      final VulkanLWJGLExtKHRSwapChain in_extension,
      final VulkanLWJGLLogicalDevice in_device,
      final long in_chain,
      final VulkanLWJGLHostAllocatorProxy in_host_allocator_proxy)
    {
      super(USER_OWNED, in_host_allocator_proxy);

      this.extension = Objects.requireNonNull(in_extension, "Extension");
      this.chain = in_chain;
      this.device = Objects.requireNonNull(in_device, "Device");
    }

    long chain()
    {
      return this.chain;
    }

    @Override
    public String toString()
    {
      return new StringBuilder(64)
        .append("[VulkanLWJGLKHRSwapChain ")
        .append("0x")
        .append(Long.toUnsignedString(this.chain, 16))
        .append(' ')
        .append(this.device)
        .append(' ')
        .append(this.extension)
        .append(']')
        .toString();
    }

    @Override
    protected Logger logger()
    {
      return LOG;
    }

    @Override
    protected void closeActual()
    {
      if (LOG.isTraceEnabled()) {
        LOG.trace("destroying swapchain: {}", this);
      }

      KHRSwapchain.vkDestroySwapchainKHR(this.device.device(), this.chain, null);
    }

    @Override
    public List<VulkanImageType> images()
      throws VulkanException
    {
      this.checkNotClosed();
      return this.extension.images(this, this.hostAllocatorProxy());
    }

    @Override
    public VulkanSwapChainImageAcquisition acquireImageWithSemaphore(
      final long timeout,
      final VulkanSemaphoreType semaphore)
      throws VulkanException
    {
      Objects.requireNonNull(semaphore, "semaphore");
      return this.extension.acquireImage(this, timeout, semaphore, null);
    }

    @Override
    public VulkanSwapChainImageAcquisition acquireImageWithFence(
      final long timeout,
      final VulkanFenceType fence)
      throws VulkanException
    {
      Objects.requireNonNull(fence, "fence");
      return this.extension.acquireImage(this, timeout, null, fence);
    }

    @Override
    public VulkanSwapChainImageAcquisition acquireImageWithSemaphoreAndFence(
      final long timeout,
      final VulkanSemaphoreType semaphore,
      final VulkanFenceType fence)
      throws VulkanException
    {
      Objects.requireNonNull(semaphore, "semaphore");
      Objects.requireNonNull(fence, "fence");
      return this.extension.acquireImage(this, timeout, semaphore, fence);
    }
  }
}
