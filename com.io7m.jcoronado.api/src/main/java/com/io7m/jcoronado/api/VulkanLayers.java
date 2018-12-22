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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Functions for handling Vulkan layers.
 */

public final class VulkanLayers
{
  private VulkanLayers()
  {

  }

  /**
   * Determine if all of the required layers are available.
   *
   * @param available_layers The available layers
   * @param optional_layers  The optional layers
   * @param required_layers  The required layers
   *
   * @return The set of layers to be enabled
   *
   * @throws VulkanMissingRequiredLayersException If one or more required layers are missing
   */

  public static Set<String> filterRequiredLayers(
    final Map<String, VulkanLayerProperties> available_layers,
    final Set<String> optional_layers,
    final Set<String> required_layers)
    throws VulkanMissingRequiredLayersException
  {
    Objects.requireNonNull(available_layers, "available_layers");
    Objects.requireNonNull(optional_layers, "optional_layers");
    Objects.requireNonNull(required_layers, "required_layers");

    final var enable_layers =
      required_layers.stream()
        .filter(available_layers::containsKey)
        .collect(Collectors.toSet());

    enable_layers.addAll(optional_layers);

    if (enable_layers.size() < required_layers.size()) {
      final var missing =
        required_layers.stream()
          .filter(name -> !enable_layers.contains(name))
          .collect(Collectors.toSet());

      final var builder = new StringBuilder(64);
      final var separator = System.lineSeparator();
      builder.append("Required layers are missing from the Vulkan implementation.")
        .append(separator)
        .append("  Missing:")
        .append(separator);

      for (final var name : missing) {
        builder.append("  ");
        builder.append(name);
        builder.append(separator);
      }

      throw new VulkanMissingRequiredLayersException(missing, builder.toString());
    }

    return enable_layers;
  }
}
