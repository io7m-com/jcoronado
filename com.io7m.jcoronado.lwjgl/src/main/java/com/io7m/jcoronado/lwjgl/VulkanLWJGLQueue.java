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
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanSubmitInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkQueue;
import org.lwjgl.vulkan.VkSubmitInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.io7m.jcoronado.lwjgl.VulkanLWJGLHandle.Ownership.VULKAN_OWNED;

/**
 * LWJGL {@link VkQueue}
 */

public final class VulkanLWJGLQueue
  extends VulkanLWJGLHandle implements VulkanQueueType
{
  private static final Logger LOG = LoggerFactory.getLogger(VulkanLWJGLQueue.class);

  private final VulkanLWJGLLogicalDevice device;
  private final VkQueue queue;
  private final VulkanQueueFamilyProperties properties;
  private final int queue_index;
  private final MemoryStack stack_initial;

  VulkanLWJGLQueue(
    final VulkanLWJGLLogicalDevice in_device,
    final VkQueue in_queue,
    final VulkanQueueFamilyProperties in_properties,
    final int in_queue_index,
    final VulkanLWJGLHostAllocatorProxy in_host_allocator_proxy)
  {
    super(VULKAN_OWNED, in_host_allocator_proxy);

    this.device =
      Objects.requireNonNull(in_device, "device");
    this.queue =
      Objects.requireNonNull(in_queue, "queue");
    this.properties =
      Objects.requireNonNull(in_properties, "properties");
    this.queue_index =
      in_queue_index;
    this.stack_initial =
      MemoryStack.create();
  }

  /**
   * @return The underlying Vulkan queue
   */

  public VkQueue rawQueue()
  {
    return this.queue;
  }

  @Override
  public boolean equals(final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || !Objects.equals(this.getClass(), o.getClass())) {
      return false;
    }
    final VulkanLWJGLQueue that = (VulkanLWJGLQueue) o;
    return this.queue_index == that.queue_index
      && Objects.equals(this.queue, that.queue)
      && Objects.equals(this.properties, that.properties);
  }

  @Override
  public int hashCode()
  {

    return Objects.hash(this.queue, this.properties, Integer.valueOf(this.queue_index));
  }

  @Override
  public String toString()
  {
    return new StringBuilder(64)
      .append("[VulkanLWJGLQueue family ")
      .append(this.properties.queueFamilyIndex())
      .append(" index ")
      .append(this.queue_index)
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
      LOG.trace("destroying queue: {}", this);
    }
  }

  @Override
  public VulkanQueueFamilyProperties queueFamilyProperties()
  {
    return this.properties;
  }

  @Override
  public int queueIndex()
  {
    return this.queue_index;
  }

  @Override
  public void submit(
    final List<VulkanSubmitInfo> submissions,
    final Optional<VulkanFenceType> fence)
    throws VulkanException
  {
    Objects.requireNonNull(submissions, "submissions");
    Objects.requireNonNull(fence, "fence");

    final long cfence;
    if (fence.isPresent()) {
      cfence = VulkanLWJGLClassChecks.check(fence.get(), VulkanLWJGLFence.class).handle();
    } else {
      cfence = VK10.VK_NULL_HANDLE;
    }

    try (MemoryStack stack = this.stack_initial.push()) {
      final int size = submissions.size();
      final VkSubmitInfo.Buffer infos = VkSubmitInfo.mallocStack(size, stack);
      VulkanLWJGLSubmitInfos.packInfos(stack, submissions, infos);
      VulkanChecks.checkReturnCode(
        VK10.vkQueueSubmit(this.queue, infos, cfence),
        "vkQueueSubmit");
    }
  }

  @Override
  public void waitIdle()
    throws VulkanException
  {
    VulkanChecks.checkReturnCode(
      VK10.vkQueueWaitIdle(this.queue),
      "vkQueueWaitIdle");
  }
}
