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

package com.io7m.jcoronado.lwjgl.internal;

import com.io7m.jcoronado.api.VulkanCallFailedException;
import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanIncompatibleClassException;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyIndex;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanSemaphoreBinaryType;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanExtKHRSwapChainType;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentInfoKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainAcquisitionResultType;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainCreateInfo;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainNotReady;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainOK;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainOutOfDate;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainSubOptimal;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainTimedOut;
import com.io7m.junreachable.UnreachableCodeException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkExtent2D;
import org.lwjgl.vulkan.VkPresentInfoKHR;
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR;
import org.lwjgl.vulkan.VkSwapchainPresentFenceInfoEXT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLClassChecks.checkInstanceOf;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLHandle.Ownership.USER_OWNED;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLHandle.Ownership.VULKAN_OWNED;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLScalarArrays.packLongs;
import static org.lwjgl.vulkan.EXTSwapchainMaintenance1.VK_STRUCTURE_TYPE_SWAPCHAIN_PRESENT_FENCE_INFO_EXT;
import static org.lwjgl.vulkan.KHRSwapchain.VK_SUBOPTIMAL_KHR;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;

/**
 * Access to the {@code VK_KHR_swapchain} extension.
 */

public final class VulkanLWJGLExtKHRSwapChain
  implements VulkanExtKHRSwapChainType
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
    final List<VulkanQueueFamilyIndex> integers)
    throws VulkanException
  {
    return VulkanLWJGLScalarArrays.packIntsOrNull(
      stack,
      integers,
      VulkanQueueFamilyIndex::value);
  }

  private static long mapOldSwapChain(
    final Optional<VulkanKHRSwapChainType> swap_chain)
  {
    return swap_chain.map(chain -> {
      try {
        return Long.valueOf(
          checkInstanceOf(
            chain,
            VulkanLWJGLKHRSwapChain.class).chain());
      } catch (final VulkanIncompatibleClassException e) {
        throw new VulkanUncheckedException(e);
      }
    }).orElse(Long.valueOf(0L)).longValue();
  }

  private static VkExtent2D packExtent(
    final MemoryStack stack,
    final VulkanExtent2D e)
  {
    return VkExtent2D.calloc(stack).set(e.width(), e.height());
  }

  private static LongBuffer packSemaphoresBinary(
    final MemoryStack stack,
    final List<VulkanSemaphoreBinaryType> semaphores)
    throws VulkanException
  {
    return VulkanLWJGLScalarArrays.packLongsOrNull(
      stack,
      semaphores,
      value -> {
        return checkInstanceOf(value, VulkanLWJGLSemaphoreBinary.class)
          .handle();
      });
  }

  private static IntBuffer packImageIndices(
    final MemoryStack stack,
    final List<Integer> indices)
    throws VulkanException
  {
    return VulkanLWJGLScalarArrays.packIntsOrNull(
      stack,
      indices,
      Integer::intValue
    );
  }

  private static LongBuffer packSwapChains(
    final MemoryStack stack,
    final List<VulkanKHRSwapChainType> swap_chains)
    throws VulkanException
  {
    return VulkanLWJGLScalarArrays.packLongsOrNull(
      stack,
      swap_chains,
      value -> {
        return checkInstanceOf(value, VulkanLWJGLKHRSwapChain.class)
          .chain();
      });
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

  private static List<VulkanImageType> images(
    final VulkanLWJGLKHRSwapChain chain,
    final VulkanLWJGLHostAllocatorProxy host_allocator_proxy)
    throws VulkanException
  {
    final var count = new int[1];

    VulkanChecks.checkReturnCode(
      KHRSwapchain.vkGetSwapchainImagesKHR(
        chain.device.device(),
        chain.chain,
        count,
        null),
      "vkGetSwapchainImagesKHR");

    final var image_count = count[0];
    if (image_count == 0) {
      return List.of();
    }

    final var images = new long[image_count];
    VulkanChecks.checkReturnCode(
      KHRSwapchain.vkGetSwapchainImagesKHR(
        chain.device.device(),
        chain.chain,
        count,
        images),
      "vkGetSwapchainImagesKHR");

    return LongStream.of(images)
      .mapToObj(image ->
                  new VulkanLWJGLImage(
                    VULKAN_OWNED,
                    image,
                    () -> {
                    },
                    host_allocator_proxy))
      .collect(Collectors.toList());
  }

  private VulkanSwapChainAcquisitionResultType acquireImage(
    final VulkanLWJGLKHRSwapChain chain,
    final long timeout,
    final VulkanSemaphoreBinaryType semaphore,
    final VulkanFenceType fence)
    throws VulkanIncompatibleClassException, VulkanCallFailedException
  {
    final long semaphoreHandle =
      switch (semaphore) {
        case null -> {
          yield VK10.VK_NULL_HANDLE;
        }
        case final VulkanSemaphoreBinaryType b -> {
          yield checkInstanceOf(b, VulkanLWJGLSemaphoreBinary.class)
            .handle();
        }
      };

    final long fenceHandle;
    if (fence != null) {
      fenceHandle = checkInstanceOf(fence, VulkanLWJGLFence.class).handle();
    } else {
      fenceHandle = VK10.VK_NULL_HANDLE;
    }

    final var imageHandles = new int[1];
    final var result =
      KHRSwapchain.vkAcquireNextImageKHR(
        chain.device.device(),
        chain.chain,
        timeout,
        semaphoreHandle,
        fenceHandle,
        imageHandles
      );

    final var imageHandle = imageHandles[0];
    return switch (result) {
      case VK_SUCCESS -> {
        yield new VulkanSwapChainOK(imageHandle);
      }
      case VK10.VK_TIMEOUT -> {
        yield new VulkanSwapChainTimedOut();
      }
      case VK10.VK_NOT_READY -> {
        yield new VulkanSwapChainNotReady();
      }
      case VK_SUBOPTIMAL_KHR -> {
        yield new VulkanSwapChainSubOptimal();
      }
      case KHRSwapchain.VK_ERROR_OUT_OF_DATE_KHR -> {
        yield new VulkanSwapChainOutOfDate();
      }
      default -> {
        VulkanChecks.checkReturnCode(result, "vkAcquireNextImageKHR");
        throw new UnreachableCodeException();
      }
    };
  }

  @Override
  public VulkanKHRSwapChainType swapChainCreate(
    final VulkanLogicalDeviceType inDevice,
    final VulkanSwapChainCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(inDevice, "inDevice");
    Objects.requireNonNull(info, "info");

    final var device =
      checkInstanceOf(inDevice, VulkanLWJGLLogicalDevice.class);
    final var surface =
      checkInstanceOf(info.surface(), VulkanLWJGLExtKHRSurfaceValue.class);

    try (var stack = this.stack_initial.push()) {
      final var vk_info =
        VkSwapchainCreateInfoKHR.calloc(stack)
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
          .pQueueFamilyIndices(packQueueIndices(
            stack,
            info.queueFamilyIndices()))
          .surface(surface.handle())
          .oldSwapchain(mapOldSwapChain(info.oldSwapChain()));

      final var swapchain = new long[1];
      VulkanChecks.checkReturnCode(
        KHRSwapchain.vkCreateSwapchainKHR(
          device.device(),
          vk_info,
          null,
          swapchain),
        "vkCreateSwapchainKHR");

      final var swapchain_address = swapchain[0];
      if (LOG.isDebugEnabled()) {
        LOG.debug(
          "created swapchain: {}",
          Long.toUnsignedString(swapchain_address, 16));
      }
      return new VulkanLWJGLKHRSwapChain(
        this, device, swapchain_address, device.hostAllocatorProxy());
    }
  }

  @Override
  public QueuePresentResult queuePresent(
    final VulkanQueueType inQueue,
    final VulkanPresentInfoKHR presentInfo)
    throws VulkanException
  {
    final var queue =
      checkInstanceOf(inQueue, VulkanLWJGLQueue.class);

    Objects.requireNonNull(presentInfo, "presentInfo");

    try (var stack = this.stack_initial.push()) {
      final var bufferIndices =
        packImageIndices(stack, presentInfo.imageIndices());

      final var swapChains =
        presentInfo.swapChains();
      final var swapChainCount =
        swapChains.size();
      final var bufferSwapchains =
        packSwapChains(stack, swapChains);
      final var bufferSemaphores =
        packSemaphoresBinary(stack, presentInfo.waitSemaphores());

      /*
       * Optionally pack in a list of fences that will be signalled when
       * the presentation is done. This allows for safely deleting resources
       * associated with the swapchain whenever the swapchain needs to be
       * recreated.
       */

      final long fenceInfoAddress;
      if (!presentInfo.fences().isEmpty()) {
        final var bufferFences =
          packLongs(
            stack,
            presentInfo.fences(),
            f -> checkInstanceOf(f, VulkanLWJGLFence.class).handle()
          );

        final var vkFenceInfo =
          VkSwapchainPresentFenceInfoEXT.calloc(stack)
            .sType(VK_STRUCTURE_TYPE_SWAPCHAIN_PRESENT_FENCE_INFO_EXT)
            .swapchainCount(swapChainCount)
            .pFences(bufferFences);

        fenceInfoAddress = vkFenceInfo.address();
      } else {
        fenceInfoAddress = 0L;
      }

      final var vkInfo =
        VkPresentInfoKHR.calloc(stack)
          .sType(KHRSwapchain.VK_STRUCTURE_TYPE_PRESENT_INFO_KHR)
          .pNext(fenceInfoAddress)
          .pImageIndices(bufferIndices)
          .pSwapchains(bufferSwapchains)
          .swapchainCount(swapChainCount)
          .pWaitSemaphores(bufferSemaphores);

      final var code =
        KHRSwapchain.vkQueuePresentKHR(queue.rawQueue(), vkInfo);

      return switch (code) {
        case VK_SUCCESS -> {
          yield QueuePresentResult.SUCCESS;
        }
        case VK_SUBOPTIMAL_KHR -> {
          yield QueuePresentResult.SUBOPTIMAL;
        }
        default -> {
          VulkanChecks.checkReturnCode(
            code,
            "vkQueuePresentKHR"
          );
          throw new UnreachableCodeException();
        }
      };
    }
  }

  static final class VulkanLWJGLKHRSwapChain
    extends VulkanLWJGLHandle implements VulkanKHRSwapChainType
  {
    private final long chain;
    private final VulkanLWJGLLogicalDevice device;
    private final VulkanLWJGLExtKHRSwapChain extension;

    VulkanLWJGLKHRSwapChain(
      final VulkanLWJGLExtKHRSwapChain inExtension,
      final VulkanLWJGLLogicalDevice inDevice,
      final long inChain,
      final VulkanLWJGLHostAllocatorProxy inHostAllocatorProxy)
    {
      super(USER_OWNED, inHostAllocatorProxy, inChain);

      this.extension =
        Objects.requireNonNull(inExtension, "Extension");
      this.chain =
        inChain;
      this.device =
        Objects.requireNonNull(inDevice, "Device");
    }

    long chain()
    {
      return this.chain;
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
        LOG.trace("Destroying swapchain: {}", this);
      }

      KHRSwapchain.vkDestroySwapchainKHR(
        this.device.device(),
        this.chain,
        null
      );
    }

    @Override
    public List<VulkanImageType> images()
      throws VulkanException
    {
      this.checkNotClosed();
      return VulkanLWJGLExtKHRSwapChain.images(this, this.hostAllocatorProxy());
    }

    @Override
    public VulkanSwapChainAcquisitionResultType acquireImageWithSemaphore(
      final long timeout,
      final VulkanSemaphoreBinaryType semaphore)
      throws VulkanException
    {
      Objects.requireNonNull(semaphore, "semaphore");
      return this.extension.acquireImage(this, timeout, semaphore, null);
    }

    @Override
    public VulkanSwapChainAcquisitionResultType acquireImageWithFence(
      final long timeout,
      final VulkanFenceType fence)
      throws VulkanException
    {
      Objects.requireNonNull(fence, "fence");
      return this.extension.acquireImage(this, timeout, null, fence);
    }

    @Override
    public VulkanSwapChainAcquisitionResultType acquireImageWithSemaphoreAndFence(
      final long timeout,
      final VulkanSemaphoreBinaryType semaphore,
      final VulkanFenceType fence)
      throws VulkanException
    {
      Objects.requireNonNull(semaphore, "semaphore");
      Objects.requireNonNull(fence, "fence");
      return this.extension.acquireImage(this, timeout, semaphore, fence);
    }
  }
}
