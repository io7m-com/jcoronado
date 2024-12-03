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

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

import java.util.List;
import java.util.Set;

/**
 * The information required to create a queue on a device.
 *
 * @see "VkDeviceQueueCreateInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkDeviceQueueCreateInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanLogicalDeviceQueueCreateInfoType
{
  /**
   * @return The flags used to create the queue(s)
   */

  @Value.Parameter
  Set<VulkanDeviceQueueCreationFlag> flags();

  /**
   * @return The queue family
   */

  @Value.Parameter
  VulkanQueueFamilyIndex queueFamilyIndex();

  /**
   * Set the priorities of the created queues. The number of priority values
   * set will implicitly set the number of queues to create.
   *
   * @return The priorities of the queues
   */

  @Value.Parameter
  List<Float> queuePriorities();

  /**
   * Check preconditions for the type.
   */

  @Value.Check
  default void checkPreconditions()
  {
    final var count = this.queuePriorities().size();
    if (count <= 0) {
      throw new IllegalArgumentException(
        new StringBuilder(32)
          .append("Queue count ")
          .append(count)
          .append(" must be positive")
          .toString());
    }
  }
}
