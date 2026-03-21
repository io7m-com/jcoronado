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

import java.util.List;
import java.util.Set;

/**
 * @see "VkImageCreateInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkImageCreateInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanImageCreateInfoType
{
  /**
   * @return A set of flags describing additional parameters of the image.
   */

  Set<VulkanImageCreateFlag> flags();

  /**
   * @return A value specifying the basic dimensionality of the image. Layers in array textures do
   * not count as a dimension for the purposes of the image type.
   */

  @Value.Default
  default VulkanImageKind imageType()
  {
    return VulkanImageKind.VK_IMAGE_TYPE_2D;
  }

  /**
   * @return A value describing the format and type of the texel blocks that will be contained in
   * the image.
   */

  @Value.Default
  default VulkanFormat format()
  {
    return VulkanFormat.VK_FORMAT_UNDEFINED;
  }

  /**
   * @return A value describing the number of data elements in each dimension of the base level.
   */

  @Value.Default
  default VulkanExtent3D extent()
  {
    return VulkanExtent3D.builder().build();
  }

  /**
   * @return The number of levels of detail available for minified sampling of the image.
   */

  @Value.Default
  default int mipLevels()
  {
    return 1;
  }

  /**
   * @return The the number of layers in the image.
   */

  @Value.Default
  default int arrayLayers()
  {
    return 1;
  }

  /**
   * @return A value specifying the number of samples per texel.
   */

  Set<VulkanSampleCountFlag> samples();

  /**
   * @return A value specifying the tiling arrangement of the texel blocks in memory.
   */

  @Value.Default
  default VulkanImageTiling tiling()
  {
    return VulkanImageTiling.VK_IMAGE_TILING_OPTIMAL;
  }

  /**
   * @return A value describing the intended usage of the image.
   */

  Set<VulkanImageUsageFlag> usage();

  /**
   * @return A value specifying the sharing mode of the image when it will be accessed by multiple
   * queue families.
   */

  @Value.Default
  default VulkanSharingMode sharingMode()
  {
    return VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE;
  }

  /**
   * @return A list of queue families that will access this image (ignored if sharingMode is not
   * VK_SHARING_MODE_CONCURRENT).
   */

  List<Integer> queueFamilyIndices();

  /**
   * @return A value specifying the initial layout of all image subresources of the image.
   */

  @Value.Default
  default VulkanImageLayout initialLayout()
  {
    return VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED;
  }
}
