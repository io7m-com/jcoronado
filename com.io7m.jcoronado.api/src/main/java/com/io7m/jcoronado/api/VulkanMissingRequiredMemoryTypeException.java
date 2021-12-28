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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * An exception raised by no memory type being available to provide for a specific allocation.
 */

public final class VulkanMissingRequiredMemoryTypeException extends
  VulkanException
{
  private final VulkanMemoryRequirements requirements;
  private final Set<VulkanMemoryPropertyFlag> flags;
  private final Collection<VulkanMemoryHeap> heaps;
  private final Collection<VulkanMemoryType> types;

  /**
   * Construct an exception.
   *
   * @param message         The error message
   * @param in_requirements The memory requirements
   * @param in_flags        The memory properties
   * @param in_heaps        The available heaps
   * @param in_types        The available types
   */

  public VulkanMissingRequiredMemoryTypeException(
    final String message,
    final VulkanMemoryRequirements in_requirements,
    final Set<VulkanMemoryPropertyFlag> in_flags,
    final Collection<VulkanMemoryHeap> in_heaps,
    final Collection<VulkanMemoryType> in_types)
  {
    super(Objects.requireNonNull(message, "message"));

    this.requirements =
      Objects.requireNonNull(in_requirements, "requirements");
    this.flags =
      Set.copyOf(Objects.requireNonNull(in_flags, "flags"));
    this.heaps =
      List.copyOf(Objects.requireNonNull(in_heaps, "heaps"));
    this.types =
      List.copyOf(Objects.requireNonNull(in_types, "types"));
  }

  /**
   * @return The memory requirements
   */

  public VulkanMemoryRequirements requirements()
  {
    return this.requirements;
  }

  /**
   * @return The memory properties
   */

  public Set<VulkanMemoryPropertyFlag> flags()
  {
    return this.flags;
  }

  /**
   * @return The available heaps
   */

  public Collection<VulkanMemoryHeap> heaps()
  {
    return this.heaps;
  }

  /**
   * @return The available types
   */

  public Collection<VulkanMemoryType> types()
  {
    return this.types;
  }
}
