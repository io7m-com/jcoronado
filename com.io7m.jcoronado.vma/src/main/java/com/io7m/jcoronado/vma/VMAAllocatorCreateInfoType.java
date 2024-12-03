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

package com.io7m.jcoronado.vma;

import com.io7m.immutables.styles.ImmutablesStyleType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import org.immutables.value.Value;

import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * Information required to create an allocator.
 */

@ImmutablesStyleType
@Value.Immutable
public interface VMAAllocatorCreateInfoType
{
  /**
   * @return The logical device used for allocations
   */

  VulkanLogicalDeviceType logicalDevice();

  /**
   * @return The preferred size of a single VkDeviceMemory block to be allocated from large heaps > 1 GiB.
   */

  OptionalLong preferredLargeHeapBlockSize();

  /**
   * @see "https://gpuopen-librariesandsdks.github.io/VulkanMemoryAllocator/html/struct_vma_allocator_create_info.html"
   *
   * @return The maximum number of additional frames that are in use at the same time as current frame.
   */

  OptionalInt frameInUseCount();
}
