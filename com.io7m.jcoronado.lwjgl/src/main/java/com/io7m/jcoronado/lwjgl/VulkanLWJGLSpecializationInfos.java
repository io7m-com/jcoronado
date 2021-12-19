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

package com.io7m.jcoronado.lwjgl;

import com.io7m.jcoronado.api.VulkanSpecializationMap;
import com.io7m.jcoronado.api.VulkanSpecializationMapEntry;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkSpecializationInfo;
import org.lwjgl.vulkan.VkSpecializationMapEntry;

import java.util.Objects;
import java.util.Optional;

/**
 * Functions to pack pipeline shader stage create infos.
 */

public final class VulkanLWJGLSpecializationInfos
{
  private VulkanLWJGLSpecializationInfos()
  {

  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param map   A structure
   *
   * @return A packed structure (or null)
   */

  public static VkSpecializationInfo packOptional(
    final MemoryStack stack,
    final Optional<VulkanSpecializationMap> map)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(map, "map");

    return map.map(v -> pack(stack, v)).orElse(null);
  }

  /**
   * Pack a structure.
   *
   * @param stack A stack
   * @param map   A structure
   *
   * @return A packed structure
   */

  public static VkSpecializationInfo pack(
    final MemoryStack stack,
    final VulkanSpecializationMap map)
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(map, "map");

    final var map_entries = map.entries();
    final var entry_buffer = VkSpecializationMapEntry.malloc(
      map_entries.size(),
      stack);

    for (var index = 0; index < map_entries.size(); ++index) {
      final var source = map_entries.get(index);
      final var target = VkSpecializationMapEntry.create(entry_buffer.address(
        index));
      packEntryInto(source, target);
    }

    final var info = VkSpecializationInfo.malloc(stack);
    info.pData(map.data());
    info.pMapEntries(entry_buffer);
    return info;
  }

  private static VkSpecializationMapEntry packEntryInto(
    final VulkanSpecializationMapEntry entry,
    final VkSpecializationMapEntry target)
  {
    return target
      .constantID(entry.constantID())
      .offset(Math.toIntExact(entry.offset()))
      .size(entry.size());
  }
}
