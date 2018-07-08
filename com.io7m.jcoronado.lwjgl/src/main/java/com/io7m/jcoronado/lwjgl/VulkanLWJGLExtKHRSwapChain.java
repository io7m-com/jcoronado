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

import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import com.io7m.jcoronado.extensions.api.VulkanExtKHRSwapChainType;
import com.io7m.jcoronado.extensions.api.VulkanSwapChainCreateInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.VkExtent2D;
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.IntBuffer;
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
    final VulkanLWJGLKHRSwapChain chain)
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
      .mapToObj(image -> new VulkanLWJGLImage(VULKAN_OWNED, image))
      .collect(Collectors.toList());
  }

  @Override
  public VulkanKHRSwapChainType swapChainCreate(
    final VulkanLogicalDeviceType in_device,
    final VulkanSwapChainCreateInfo info)
    throws VulkanException
  {
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
      return new VulkanLWJGLKHRSwapChain(this, device, swapchain_address);
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
      final long in_chain)
    {
      super(USER_OWNED);

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
      return this.extension.images(this);
    }
  }
}
