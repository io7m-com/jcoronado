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

package com.io7m.jcoronado.api;

import java.util.List;
import java.util.Optional;

/**
 * A queue on a logical device.
 *
 * @see "VkQueue"
 */

public interface VulkanQueueType extends VulkanHandleDispatchableType
{
  /**
   * @return The properties for the queue family to which this queue belongs
   */

  VulkanQueueFamilyProperties queueFamilyProperties();

  /**
   * @return The index of the queue within the queue family to which it belongs
   */

  int queueIndex();

  /**
   * Submit the given list of queue submissions. If a fence is provided, the fence is signalled when
   * all of the command buffers have finished executing.
   *
   * @param submissions The queue submissions
   * @param fence       A fence
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkQueueSubmit")
  @VulkanExternallySynchronizedType
  void submit(
    List<VulkanSubmitInfo> submissions,
    @VulkanExternallySynchronizedType Optional<VulkanFenceType> fence)
    throws VulkanException;

  /**
   * Submit the given list of queue submissions.
   *
   * @param submissions The queue submissions
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkQueueSubmit")
  @VulkanExternallySynchronizedType
  default void submit(
    final List<VulkanSubmitInfo> submissions)
    throws VulkanException
  {
    this.submit(submissions, Optional.empty());
  }

  /**
   * Wait until all items in the given queue have finished executing.
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkQueueWaitIdle")
  @VulkanExternallySynchronizedType
  void waitIdle()
    throws VulkanException;

}
