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
 * Functions for handling Vulkan extensions.
 */

public final class VulkanExtensions
{
  private VulkanExtensions()
  {

  }

  /**
   * Determine if all of the required extensions are available.
   *
   * @param available_extensions The available extensions
   * @param optional_extensions  The optional extensions
   * @param required_extensions  The required extensions
   *
   * @return The set of extensions to be enabled
   *
   * @throws VulkanMissingRequiredExtensionsException If one or more required extensions are
   *                                                  missing
   */

  public static Set<String> filterRequiredExtensions(
    final Map<String, VulkanExtensionProperties> available_extensions,
    final Set<String> optional_extensions,
    final Set<String> required_extensions)
    throws VulkanMissingRequiredExtensionsException
  {
    Objects.requireNonNull(available_extensions, "available_extensions");
    Objects.requireNonNull(optional_extensions, "optional_extensions");
    Objects.requireNonNull(required_extensions, "required_extensions");

    final Set<String> enable_extensions =
      required_extensions.stream()
        .filter(available_extensions::containsKey)
        .collect(Collectors.toSet());

    enable_extensions.addAll(optional_extensions);

    if (enable_extensions.size() < required_extensions.size()) {
      final Set<String> missing =
        required_extensions.stream()
          .filter(name -> !enable_extensions.contains(name))
          .collect(Collectors.toSet());

      final StringBuilder builder = new StringBuilder(64);
      final String separator = System.lineSeparator();
      builder.append("Required extensions are missing from the Vulkan implementation.")
        .append(separator)
        .append("  Missing:")
        .append(separator);

      for (final String name : missing) {
        builder.append("  ");
        builder.append(name);
        builder.append(separator);
      }

      throw new VulkanMissingRequiredExtensionsException(missing, builder.toString());
    }

    return enable_extensions;
  }
}
