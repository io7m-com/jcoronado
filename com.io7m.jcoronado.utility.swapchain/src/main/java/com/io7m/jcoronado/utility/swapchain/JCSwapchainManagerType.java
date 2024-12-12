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

import java.util.Set;
import java.util.concurrent.Flow;

/**
 * <p>A swapchain manager.</p>
 * <p>The manager is capable of creating swapchains, and transparently
 * recreating them when they become suboptimal or invalid.</p>
 */

public interface JCSwapchainManagerType
  extends AutoCloseable
{
  /**
   * @return The swapchain events
   */

  Flow.Publisher<JCSwapchainEventType> events();

  /**
   * Acquire a swapchain image. The method is guaranteed to return an image
   * that is usable (if not necessarily optimal) on the current device. In
   * the case of an image being suboptimal, the swapchain will automatically
   * be recreated at the earliest possible opportunity.
   *
   * @return A swapchain image
   *
   * @throws VulkanException On errors
   */

  JCSwapchainImageType acquire()
    throws VulkanException;

  /**
   * Get a set comprising the indices of the swapchain. This can be used
   * as an index into a map to store per-frame resources externally.
   *
   * @return The set of swapchain image indices
   */

  Set<JCSwapchainImageIndex> imageIndices();

  @Override
  void close()
    throws VulkanException;
}
