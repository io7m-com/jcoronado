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

/**
 * @see "VkSamplerAddressMode"
 */

@VulkanAPIEnumType(vulkanEnum = "VkSamplerAddressMode")
public enum VulkanSamplerAddressMode implements VulkanEnumIntegerType
{
  /**
   * Specifies that the repeat wrap mode will be used.
   */

  VK_SAMPLER_ADDRESS_MODE_REPEAT(0),

  /**
   * Specifies that the mirrored repeat wrap mode will be used.
   */

  VK_SAMPLER_ADDRESS_MODE_MIRRORED_REPEAT(1),

  /**
   * Specifies that the clamp to edge wrap mode will be used.
   */

  VK_SAMPLER_ADDRESS_MODE_CLAMP_TO_EDGE(2),

  /**
   * Specifies that the clamp to border wrap mode will be used.
   */

  VK_SAMPLER_ADDRESS_MODE_CLAMP_TO_BORDER(3),

  /**
   * Specifies that the mirror clamp to edge wrap mode will be used.
   */

  VK_SAMPLER_ADDRESS_MODE_MIRROR_CLAMP_TO_EDGE(4);

  private final int value;

  VulkanSamplerAddressMode(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
