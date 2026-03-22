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


package com.io7m.jcoronado.api;

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

import java.util.List;
import java.util.Set;

/**
 * @see "VkDependencyInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkDependencyInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanDependencyInfoType
{
  /**
   * @return A set of flags specifying how execution and memory dependencies are formed.
   */

  Set<VulkanDependencyFlag> flags();

  /**
   * @return The memory barriers for any memory accesses
   */

  List<VulkanMemoryBarrier> memoryBarriers();

  /**
   * @return The memory dependencies between buffer ranges
   */

  List<VulkanBufferMemoryBarrier> bufferMemoryBarriers();

  /**
   * @return The memory dependencies between image subresources
   */

  List<VulkanImageMemoryBarrier> imageMemoryBarriers();
}
