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
 * Specifies the type of a descriptor in a descriptor set.
 *
 * @see "VkDescriptorType"
 */

public enum VulkanDescriptorType implements VulkanEnumIntegerType
{
  /**
   * Specifies a sampler descriptor
   */

  VK_DESCRIPTOR_TYPE_SAMPLER(0),

  /**
   * Specifies a combined image sampler descriptor
   */

  VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER(1),

  /**
   * Specifies a sampled image descriptor
   */

  VK_DESCRIPTOR_TYPE_SAMPLED_IMAGE(2),

  /**
   * Specifies a storage image descriptor
   */

  VK_DESCRIPTOR_TYPE_STORAGE_IMAGE(3),

  /**
   * Specifies a uniform texel buffer descriptor
   */

  VK_DESCRIPTOR_TYPE_UNIFORM_TEXEL_BUFFER(4),

  /**
   * Specifies a storage texel buffer descriptor
   */

  VK_DESCRIPTOR_TYPE_STORAGE_TEXEL_BUFFER(5),

  /**
   * Specifies a uniform buffer descriptor
   */

  VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER(6),

  /**
   * Specifies a storage buffer descriptor
   */

  VK_DESCRIPTOR_TYPE_STORAGE_BUFFER(7),

  /**
   * Specifies a dynamic uniform buffer descriptor
   */

  VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC(8),

  /**
   * Specifies a dynamic storage buffer descriptor
   */

  VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC(9),

  /**
   * Specifies an input attachment descriptor
   */

  VK_DESCRIPTOR_TYPE_INPUT_ATTACHMENT(10);

  private final int value;

  VulkanDescriptorType(final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
