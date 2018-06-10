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

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * Functions to map error codes to humanly readable names.
 */

public final class VulkanErrorCodes
{
  private static final Map<Integer, String> CODES;

  static {
    try {
      CODES = makeErrorCodes();
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private VulkanErrorCodes()
  {

  }

  private static Map<Integer, String> makeErrorCodes()
    throws IOException
  {
    try (InputStream stream =
           VulkanErrorCodes.class.getResourceAsStream("error_codes.properties")) {
      final Properties properties = new Properties();
      properties.load(stream);

      final HashMap<Integer, String> codes = new HashMap<>();
      for (final String name : properties.stringPropertyNames()) {
        final String value = properties.getProperty(name);
        final int ivalue = Integer.parseInt(value);
        codes.put(Integer.valueOf(ivalue), value);
      }
      return codes;
    }
  }

  /**
   * @param code The error code
   *
   * @return The name of the given error code, or nothing if the code is not recognized
   */

  public static Optional<String> errorName(final int code)
  {
    return Optional.ofNullable(CODES.get(Integer.valueOf(code)));
  }
}
