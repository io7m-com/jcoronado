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

package com.io7m.jcoronado.extensions.api;

import com.io7m.jcoronado.api.VulkanEnumIntegerType;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @see "VkPresentModeKHR"
 */

public enum VulkanPresentModeKHR implements VulkanEnumIntegerType
{
  /**
   * The presentation engine does not wait for a vertical blanking period to update the current
   * image, meaning this mode may result in visible tearing. No internal queuing of presentation
   * requests is needed, as the requests are applied immediately.
   */

  VK_PRESENT_MODE_IMMEDIATE_KHR(0),

  /**
   * The presentation engine waits for the next vertical blanking period to update the current
   * image. Tearing cannot be observed. An internal single-entry queue is used to hold pending
   * presentation requests. If the queue is full when a new presentation request is received, the
   * new request replaces the existing entry, and any images associated with the prior entry become
   * available for re-use by the application. One request is removed from the queue and processed
   * during each vertical blanking period in which the queue is non-empty.
   */

  VK_PRESENT_MODE_MAILBOX_KHR(1),

  /**
   * The presentation engine waits for the next vertical blanking period to update the current
   * image. Tearing cannot be observed. An internal queue is used to hold pending presentation
   * requests. New requests are appended to the end of the queue, and one request is removed from
   * the beginning of the queue and processed during each vertical blanking period in which the
   * queue is non-empty. This is the only value of presentMode that is required to be supported.
   */

  VK_PRESENT_MODE_FIFO_KHR(2),

  /**
   * The presentation engine generally waits for the next vertical blanking period to update the
   * current image. If a vertical blanking period has already passed since the last update of the
   * current image then the presentation engine does not wait for another vertical blanking period
   * for the update, meaning this mode may result in visible tearing in this case. This mode is
   * useful for reducing visual stutter with an application that will mostly present a new image
   * before the next vertical blanking period, but may occassionally be late, and present a new
   * image just after the the next vertical blanking period. An internal queue is used to hold
   * pending presentation requests. New requests are appended to the end of the queue, and one
   * request is removed from the beginning of the queue and processed during or after each vertical
   * blanking period in which the queue is non-empty.
   */

  VK_PRESENT_MODE_FIFO_RELAXED_KHR(3);

  private final int value;

  VulkanPresentModeKHR(
    final int i)
  {
    this.value = i;
  }

  /**
   * @param x An integer value
   *
   * @return The constant associated with the given integer value, if any
   */

  public static Optional<VulkanPresentModeKHR> ofInteger(
    final int x)
  {
    return Stream.of(VulkanPresentModeKHR.values())
      .filter(mode -> mode.value == x)
      .findFirst();
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
