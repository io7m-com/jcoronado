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

import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;

/**
 * The memory properties for a physical device.
 *
 * @see "VkPhysicalDeviceMemoryProperties"
 */

@VulkanAPIStructType(vulkanStruct = "VkPhysicalDeviceMemoryProperties")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPhysicalDeviceMemoryPropertiesType
{
  /**
   * @return The memory heaps from which memory can be allocated.
   */

  @Value.Parameter
  SortedMap<VulkanMemoryHeapIndex, VulkanMemoryHeap> heaps();

  /**
   * @return The memory types that can be used to access memory allocated from
   * the heaps specified by {@link #heaps()}
   */

  @Value.Parameter
  SortedMap<VulkanMemoryTypeIndex, VulkanMemoryType> types();

  /**
   * Check preconditions for the type.
   */

  @Value.Check
  default void checkPreconditions()
  {
    for (final var entry : this.heaps().entrySet()) {
      final var index = entry.getKey();
      final var heap = entry.getValue();
      if (!Objects.equals(index, heap.index())) {
        throw new IllegalArgumentException(
          String.format(
            "Heap index %s must match key %s",
            Integer.toUnsignedString(index.value()),
            Integer.toUnsignedString(heap.index().value()))
        );
      }
    }

    for (final var entry : this.types().entrySet()) {
      final var index = entry.getKey();
      final var types = entry.getValue();
      if (!Objects.equals(index, types.index())) {
        throw new IllegalArgumentException(
          String.format(
            "Type index %s must match key %s",
            Integer.toUnsignedString(index.value()),
            Integer.toUnsignedString(types.index().value()))
        );
      }
    }
  }

  /**
   * Find a suitable memory type for the given requirements and properties.
   *
   * @param requirements The memory requirements
   * @param flags        The memory properties
   *
   * @return A memory type
   *
   * @throws VulkanMissingRequiredMemoryTypeException If no suitable memory type
   *                                                  exists
   */

  default VulkanMemoryType findSuitableMemoryType(
    final VulkanMemoryRequirements requirements,
    final Set<VulkanMemoryPropertyFlag> flags)
    throws VulkanMissingRequiredMemoryTypeException
  {
    Objects.requireNonNull(requirements, "requirements");
    Objects.requireNonNull(flags, "flags");

    final var typeFilter = requirements.memoryTypeBits();
    final var types = this.types();
    for (final var typeEntry : types.entrySet()) {
      final var typeIndex = typeEntry.getKey();
      final var type = typeEntry.getValue();
      final var bit = 1 << typeIndex.value();
      final var flagsOk = type.flags().containsAll(flags);
      final var typeBitOk = (typeFilter & bit) == bit;
      if (typeBitOk && flagsOk) {
        return type;
      }
    }

    throw new VulkanMissingRequiredMemoryTypeException(
      "No suitable memory type is available.",
      requirements
    );
  }
}
