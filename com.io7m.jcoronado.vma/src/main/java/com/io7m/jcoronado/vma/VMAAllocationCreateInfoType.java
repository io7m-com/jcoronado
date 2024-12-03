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
import com.io7m.jcoronado.api.VulkanMemoryPropertyFlag;
import org.immutables.value.Value;

import java.util.Set;

/**
 * Information required to create an allocation.
 */

@ImmutablesStyleType
@Value.Immutable
public interface VMAAllocationCreateInfoType
{
  /**
   * @return The creation flags
   */

  @Value.Parameter
  Set<VMAAllocationCreateFlag> flags();

  /**
   * @return The intended usage of the memory allocation
   */

  @Value.Parameter
  VMAMemoryUsage usage();

  /**
   * @return The flags that must be set in a memory type chosen for an allocation.
   */

  @Value.Parameter
  Set<VulkanMemoryPropertyFlag> requiredFlags();

  /**
   * @return The flags that preferably should be set in a memory type chosen for an allocation.
   */

  @Value.Parameter
  Set<VulkanMemoryPropertyFlag> preferredFlags();

  /**
   * @return A bitmask containing one bit set for every memory type acceptable for this allocation.
   */

  @Value.Parameter
  long memoryTypeBits();
}
