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

/**
 * @see "VkMemoryAllocateInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkMemoryAllocateInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanMemoryAllocateInfoType
{
  /**
   * @return The size of the allocation in bytes
   */

  @Value.Parameter
  long size();

  /**
   * @return An index identifying a memory type from the memoryTypes array of the
   * VkPhysicalDeviceMemoryProperties structure
   */

  @Value.Parameter
  VulkanMemoryTypeIndex memoryTypeIndex();
}
