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

import java.util.Set;

/**
 * The properties of a queue family.
 *
 * @see "VkQueueFamilyProperties"
 */

@VulkanAPIStructType(vulkanStruct = "VkQueueFamilyProperties")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanQueueFamilyPropertiesType
{
  /**
   * @return The index of the queue family
   */

  @Value.Parameter
  VulkanQueueFamilyIndex queueFamilyIndex();

  /**
   * @return The number of queues available in the family
   */

  @Value.Parameter
  int queueCount();

  /**
   * @return The property flags for the queue family
   */

  @Value.Parameter
  Set<VulkanQueueFamilyPropertyFlag> queueFlags();

  /**
   * @return The unsigned integer count of meaningful bits in the timestamps written via
   * vkCmdWriteTimestamp.
   */

  @Value.Parameter
  int timestampValidBits();

  /**
   * @return The minimum granularity supported for image transfer operations on the queues in this
   * queue family.
   */

  @Value.Parameter
  VulkanExtent3D minImageTransferGranularity();

  /**
   * All commands that are allowed on a queue that supports transfer operations
   * are also allowed on a queue that supports either graphics or compute
   * operations. Thus, if the capabilities of a queue family
   * include VK_QUEUE_GRAPHICS_BIT or VK_QUEUE_COMPUTE_BIT, then reporting
   * the VK_QUEUE_TRANSFER_BIT capability separately for that queue family
   * is optional.
   *
   * @return {@code true} if the queue flags imply transfer support
   *
   * @see "https://registry.khronos.org/vulkan/specs/1.3-extensions/man/html/VkQueueFlagBits.html"
   */

  default boolean queueFlagImpliesTransfer()
  {
    final var f = this.queueFlags();
    return f.contains(VulkanQueueFamilyPropertyFlag.VK_QUEUE_TRANSFER_BIT)
           || f.contains(VulkanQueueFamilyPropertyFlag.VK_QUEUE_COMPUTE_BIT)
           || f.contains(VulkanQueueFamilyPropertyFlag.VK_QUEUE_GRAPHICS_BIT);
  }
}
