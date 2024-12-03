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

import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueIndex;
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

import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLHandle.Ownership.VULKAN_OWNED;

/**
 * LWJGL {@link VkQueue}
 */

public final class VulkanLWJGLQueue
  extends VulkanLWJGLHandle implements VulkanQueueType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLQueue.class);

  private final VkQueue queue;
  private final VulkanQueueFamilyProperties properties;
  private final VulkanQueueIndex queueIndex;
  private final MemoryStack stackInitial;

  VulkanLWJGLQueue(
    final VkQueue inQueue,
    final VulkanQueueFamilyProperties inProperties,
    final VulkanQueueIndex inQueueIndex,
    final VulkanLWJGLHostAllocatorProxy inHostAllocatorProxy)
  {
    super(VULKAN_OWNED, inHostAllocatorProxy, inQueue.address());

    this.queue =
      Objects.requireNonNull(inQueue, "queue");
    this.properties =
      Objects.requireNonNull(inProperties, "properties");
    this.queueIndex =
      inQueueIndex;
    this.stackInitial =
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
  protected Logger logger()
  {
    return LOG;
  }

  @Override
  protected void closeActual()
  {
    if (LOG.isTraceEnabled()) {
      LOG.trace("Destroying queue: {}", this);
    }
  }

  @Override
  public VulkanQueueFamilyProperties queueFamilyProperties()
  {
    return this.properties;
  }

  @Override
  public VulkanQueueIndex queueIndex()
  {
    return this.queueIndex;
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
      cfence = VulkanLWJGLClassChecks.checkInstanceOf(
        fence.get(),
        VulkanLWJGLFence.class).handle();
    } else {
      cfence = VK10.VK_NULL_HANDLE;
    }

    try (var stack = this.stackInitial.push()) {
      final var size = submissions.size();
      final var infos = VkSubmitInfo.malloc(size, stack);
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
      "vkQueueWaitIdle"
    );
  }
}
