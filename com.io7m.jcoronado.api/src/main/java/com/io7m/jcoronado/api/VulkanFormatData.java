/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

/**
 * The type of data specified by a format.
 */

public enum VulkanFormatData
{
  /**
   * Opaque or unknown data.
   */

  FORMAT_DATA_OPAQUE,

  /**
   * Components are signed floating-point values.
   */

  FORMAT_DATA_FLOATING_POINT_SIGNED,

  /**
   * Components are unsigned floating-point values.
   */

  FORMAT_DATA_FLOATING_POINT_UNSIGNED,

  /**
   * Components are signed integer values.
   */

  FORMAT_DATA_INTEGER_SIGNED,

  /**
   * Components are unsigned integer values.
   */

  FORMAT_DATA_INTEGER_UNSIGNED,

  /**
   * Components are unsigned normalized fixed-point values.
   */

  FORMAT_DATA_NORMALIZED_FIXED_POINT_UNSIGNED,

  /**
   * Components are signed normalized fixed-point values.
   */

  FORMAT_DATA_NORMALIZED_FIXED_POINT_SIGNED,

  /**
   * Components are signed scaled values.
   */

  FORMAT_DATA_SCALED_SIGNED,

  /**
   * Components are unsigned scaled values.
   */

  FORMAT_DATA_SCALED_UNSIGNED,
}
