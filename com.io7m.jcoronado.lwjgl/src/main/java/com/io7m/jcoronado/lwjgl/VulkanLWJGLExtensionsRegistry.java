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

import com.io7m.jcoronado.api.VulkanExtensionType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final class VulkanLWJGLExtensionsRegistry
{
  private final HashMap<String, VulkanExtensionType> extensions;
  private final Map<String, VulkanExtensionType> extensions_read;

  VulkanLWJGLExtensionsRegistry()
  {
    this.extensions = new HashMap<>(32);
    this.extensions_read = Collections.unmodifiableMap(this.extensions);
    this.addExtension(new VulkanLWJGLExtKHRSurface());
    this.addExtension(new VulkanLWJGLExtKHRSwapChain());
  }

  Map<String, VulkanExtensionType> extensions()
  {
    return this.extensions_read;
  }

  private void addExtension(
    final VulkanExtensionType ext)
  {
    final var name = ext.name();
    if (this.extensions.containsKey(name)) {
      final var separator = System.lineSeparator();
      throw new IllegalStateException(
        new StringBuilder(128)
          .append("Multiple extensions with the same name.")
          .append(separator)
          .append("  Existing: ")
          .append(this.extensions.get(name))
          .append(separator)
          .append("  Incoming: ")
          .append(ext)
          .append(separator)
          .toString());
    }
    this.extensions.put(name, ext);
  }

  Map<String, VulkanExtensionType> ofNames(
    final Iterable<String> names)
  {
    final Map<String, VulkanExtensionType> matching = new HashMap<>(this.extensions_read.size());
    for (final var name : names) {
      final var extension = this.extensions_read.get(name);
      if (extension != null) {
        matching.put(name, extension);
      }
    }
    return matching;
  }
}
