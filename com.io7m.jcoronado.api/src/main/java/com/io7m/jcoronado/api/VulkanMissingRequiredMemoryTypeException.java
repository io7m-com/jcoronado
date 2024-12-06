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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * An exception raised by no memory type being available to provide for a specific allocation.
 */

public final class VulkanMissingRequiredMemoryTypeException
  extends VulkanException
{
  /**
   * Construct an exception.
   *
   * @param message      The error message
   * @param requirements The memory requirements
   */

  public VulkanMissingRequiredMemoryTypeException(
    final String message,
    final VulkanMemoryRequirements requirements)
  {
    super(
      Objects.requireNonNull(message, "message"),
      createAttributes(requirements),
      "error-vulkan-memory-type-unavailable",
      Optional.empty()
    );
  }

  private static Map<String, String> createAttributes(
    final VulkanMemoryRequirements requirements)
  {
    final var typeString =
      "0x%s".formatted(
        Integer.toUnsignedString(requirements.memoryTypeBits(), 16)
      );
    final var sizeString =
      Long.toUnsignedString(requirements.size());
    final var alignString =
      Long.toUnsignedString(requirements.alignment());

    return Map.ofEntries(
      Map.entry("Type Bits", typeString),
      Map.entry("Size", sizeString),
      Map.entry("Alignment", alignString)
    );
  }
}
