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

import java.util.Objects;

/**
 * Functions for encoding and decoding Vulkan version numbers.
 */

public final class VulkanVersions
{
  private VulkanVersions()
  {

  }

  /**
   * Encode a version to an integer.
   *
   * @param version The input version
   *
   * @return An encoded version
   */

  public static int encode(
    final VulkanVersion version)
  {
    Objects.requireNonNull(version, "version");
    return encode(version.major(), version.minor(), version.patch());
  }

  /**
   * Encode the given version components to an integer.
   *
   * @param major The major component
   * @param minor The minor component
   * @param patch The patch component
   *
   * @return The encoded version
   */

  public static int encode(
    final int major,
    final int minor,
    final int patch)
  {
    return major << 22 | minor << 12 | patch;
  }

  /**
   * @param version The encoded version
   *
   * @return The decoded major version component
   */

  public static int decodeMajor(
    final int version)
  {
    return version >> 22;
  }

  /**
   * @param version The encoded version
   *
   * @return The decoded minor version component
   */

  public static int decodeMinor(
    final int version)
  {
    return version >> 12 & 1023;
  }

  /**
   * @param version The encoded version
   *
   * @return The decoded patch version component
   */

  public static int decodePatch(
    final int version)
  {
    return version & 4095;
  }

  /**
   * Decode an encoded version.
   *
   * @param version The encoded version
   *
   * @return A decoded version
   */

  public static VulkanVersion decode(
    final int version)
  {
    return VulkanVersion.of(
      decodeMajor(version),
      decodeMinor(version),
      decodePatch(version));
  }
}
