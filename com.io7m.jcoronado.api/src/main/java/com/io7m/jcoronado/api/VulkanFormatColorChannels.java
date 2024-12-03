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
 * The channels present in a color image.
 */

public enum VulkanFormatColorChannels
{
  /**
   * An image with a single red channel.
   */

  COLOR_CHANNELS_R {
    @Override
    public boolean isSupersetOf(
      final VulkanFormatColorChannels other)
    {
      return switch (other) {
        case COLOR_CHANNELS_R -> true;
        case COLOR_CHANNELS_RG -> false;
        case COLOR_CHANNELS_RGB -> false;
        case COLOR_CHANNELS_BGR -> false;
        case COLOR_CHANNELS_RGBA -> false;
        case COLOR_CHANNELS_ARGB -> false;
        case COLOR_CHANNELS_ABGR -> false;
        case COLOR_CHANNELS_BGRA -> false;
      };
    }
  },

  /**
   * An image with red and green channels.
   */

  COLOR_CHANNELS_RG {
    @Override
    public boolean isSupersetOf(
      final VulkanFormatColorChannels other)
    {
      return switch (other) {
        case COLOR_CHANNELS_R -> true;
        case COLOR_CHANNELS_RG -> true;
        case COLOR_CHANNELS_RGB -> false;
        case COLOR_CHANNELS_BGR -> false;
        case COLOR_CHANNELS_RGBA -> false;
        case COLOR_CHANNELS_ARGB -> false;
        case COLOR_CHANNELS_ABGR -> false;
        case COLOR_CHANNELS_BGRA -> false;
      };
    }
  },

  /**
   * An image with red, green, and blue channels.
   */

  COLOR_CHANNELS_RGB {
    @Override
    public boolean isSupersetOf(
      final VulkanFormatColorChannels other)
    {
      return switch (other) {
        case COLOR_CHANNELS_R -> true;
        case COLOR_CHANNELS_RG -> true;
        case COLOR_CHANNELS_RGB -> true;
        case COLOR_CHANNELS_BGR -> true;
        case COLOR_CHANNELS_RGBA -> false;
        case COLOR_CHANNELS_ARGB -> false;
        case COLOR_CHANNELS_ABGR -> false;
        case COLOR_CHANNELS_BGRA -> false;
      };
    }
  },

  /**
   * An image with red, green, and blue channels.
   */

  COLOR_CHANNELS_BGR {
    @Override
    public boolean isSupersetOf(
      final VulkanFormatColorChannels other)
    {
      return switch (other) {
        case COLOR_CHANNELS_R -> true;
        case COLOR_CHANNELS_RG -> true;
        case COLOR_CHANNELS_RGB -> true;
        case COLOR_CHANNELS_BGR -> true;
        case COLOR_CHANNELS_RGBA -> false;
        case COLOR_CHANNELS_ARGB -> false;
        case COLOR_CHANNELS_ABGR -> false;
        case COLOR_CHANNELS_BGRA -> false;
      };
    }
  },

  /**
   * An image with red, green, blue, and alpha channels.
   */

  COLOR_CHANNELS_RGBA {
    @Override
    public boolean isSupersetOf(
      final VulkanFormatColorChannels other)
    {
      return true;
    }
  },

  /**
   * An image with red, green, blue, and alpha channels.
   */

  COLOR_CHANNELS_ARGB {
    @Override
    public boolean isSupersetOf(
      final VulkanFormatColorChannels other)
    {
      return true;
    }
  },

  /**
   * An image with red, green, blue, and alpha channels.
   */

  COLOR_CHANNELS_ABGR {
    @Override
    public boolean isSupersetOf(
      final VulkanFormatColorChannels other)
    {
      return true;
    }
  },

  /**
   * An image with red, green, blue, and alpha channels.
   */

  COLOR_CHANNELS_BGRA {
    @Override
    public boolean isSupersetOf(
      final VulkanFormatColorChannels other)
    {
      return true;
    }
  };

  /**
   * @param other The other channels
   *
   * @return {@code true} if these channels are a superset of {@code other}
   */

  public abstract boolean isSupersetOf(
    VulkanFormatColorChannels other);
}
