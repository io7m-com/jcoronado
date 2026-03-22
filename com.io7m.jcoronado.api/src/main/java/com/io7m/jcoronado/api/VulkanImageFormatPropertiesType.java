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

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

import java.util.Set;

/**
 * Structure specifying image format properties.
 */

@VulkanAPIStructType(vulkanStruct = "VkImageFormatProperties")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanImageFormatPropertiesType
{
  /**
   * @return The maximum image dimensions.
   */

  @Value.Default
  default VulkanExtent3D maxExtent()
  {
    return VulkanExtent3D.builder().build();
  }

  /**
   * @return The maximum number of mipmap levels.
   */

  @Value.Default
  default int maxMipLevels()
  {
    return 0;
  }

  /**
   * @return The maximum number of array layers.
   */

  @Value.Default
  default int maxArrayLayers()
  {
    return 0;
  }

  /**
   * @return A set of flags specifying all the supported sample counts for this image
   */

  Set<VulkanSampleCountFlag> sampleCounts();

  /**
   * @return An upper bound on the total image size in bytes, inclusive of all image subresources.
   */

  @Value.Default
  default long maxResourceSize()
  {
    return 0L;
  }
}
