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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * An exception raised by one or more required extensions not being present.
 */

public final class VulkanMissingRequiredLayersException
  extends VulkanException
{
  /**
   * Construct an exception.
   *
   * @param missing The missing extensions
   * @param message    The error message
   */

  public VulkanMissingRequiredLayersException(
    final Set<String> missing,
    final String message)
  {
    super(
      Objects.requireNonNull(message, "message"),
      createAttributes(missing),
      "error-vulkan-missing-layer",
      Optional.empty()
    );
  }

  private static Map<String, String> createAttributes(
    final Iterable<String> missing)
  {
    final var r = new HashMap<String, String>();
    final var index = 0;
    for (final var m : missing) {
      r.put("Missing Layer (%d)".formatted(Integer.valueOf(index)), m);
    }
    return Map.copyOf(r);
  }
}
