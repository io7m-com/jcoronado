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
 * Structure specifying the parameters of a buffer memory barrier.
 *
 * @see "VkBufferMemoryBarrier"
 */

@VulkanAPIStructType(vulkanStruct = "VkBufferMemoryBarrier")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanBufferMemoryBarrierType
{
  /**
   * @return The source access mask
   */

  @Value.Parameter
  Set<VulkanAccessFlag> sourceAccessMask();

  /**
   * @return The target access mask
   */

  @Value.Parameter
  Set<VulkanAccessFlag> targetAccessMask();

  /**
   * @return The source queue family for a queue family ownership transfer.
   */

  @Value.Parameter
  VulkanQueueFamilyIndex sourceQueueFamilyIndex();

  /**
   * @return The target queue family for a queue family ownership transfer.
   */

  @Value.Parameter
  VulkanQueueFamilyIndex targetQueueFamilyIndex();

  /**
   * @return The buffer affected by this barrier.
   */

  @Value.Parameter
  VulkanBufferType buffer();

  /**
   * @return An offset in bytes into the backing memory for buffer; this is relative to the base
   * offset as bound to the buffer
   */

  @Value.Parameter
  long offset();

  /**
   * @return A size in bytes of the affected area of backing memory for buffer, or VK_WHOLE_SIZE to
   * use the range from offset to the end of the buffer.
   */

  @Value.Parameter
  long size();
}
