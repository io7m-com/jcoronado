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

import com.io7m.jcoronado.api.VulkanCallFailedException;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueIndex;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanSubmitInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A queue.
 */

public final class VFakeQueue implements VulkanQueueType
{
  private final VFakeLogicalDevice device;
  private final VulkanQueueFamilyProperties properties;
  private final VulkanQueueIndex index;
  private final AtomicBoolean closed;

  /**
   * A queue.
   *
   * @param inDevice     The owner device
   * @param inProperties The queue properties
   * @param inIndex      The queue index
   */

  public VFakeQueue(
    final VFakeLogicalDevice inDevice,
    final VulkanQueueFamilyProperties inProperties,
    final VulkanQueueIndex inIndex)
  {
    this.device =
      Objects.requireNonNull(inDevice, "device");
    this.properties =
      Objects.requireNonNull(inProperties, "properties");
    this.index =
      Objects.requireNonNull(inIndex, "index");
    this.closed =
      new AtomicBoolean(false);
  }

  @Override
  public VulkanQueueFamilyProperties queueFamilyProperties()
  {
    return this.properties;
  }

  @Override
  public VulkanQueueIndex queueIndex()
  {
    return this.index;
  }

  @Override
  public void submit(
    final List<VulkanSubmitInfo> submissions,
    final Optional<VulkanFenceType> fence)
    throws VulkanException
  {
    throw errorNotImplemented("submit");
  }

  @Override
  public void waitIdle()
    throws VulkanException
  {
    throw errorNotImplemented("waitIdle");
  }

  @Override
  public void close()
  {
    if (this.closed.compareAndSet(false, true)) {
      // Nothing
    }
  }

  @Override
  public boolean isClosed()
  {
    return this.closed.get();
  }

  private static VulkanCallFailedException errorNotImplemented(
    final String function)
  {
    final var map =
      Map.ofEntries(
        Map.entry("ErrorCode", "0x7fff_ffff"),
        Map.entry("Function", function)
      );

    return new VulkanCallFailedException(
      "Not implemented (%s)".formatted(function),
      map
    );
  }
}
