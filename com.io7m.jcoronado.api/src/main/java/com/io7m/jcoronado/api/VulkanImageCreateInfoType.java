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

  @Value.Parameter
  Set<VulkanImageCreateFlag> flags();

  /**
   * @return A value specifying the basic dimensionality of the image. Layers in array textures do
   * not count as a dimension for the purposes of the image type.
   */

  @Value.Parameter
  VulkanImageKind imageType();

  /**
   * @return A value describing the format and type of the texel blocks that will be contained in
   * the image.
   */

  @Value.Parameter
  VulkanFormat format();

  /**
   * @return A value describing the number of data elements in each dimension of the base level.
   */

  @Value.Parameter
  VulkanExtent3D extent();

  /**
   * @return The number of levels of detail available for minified sampling of the image.
   */

  @Value.Parameter
  int mipLevels();

  /**
   * @return The the number of layers in the image.
   */

  @Value.Parameter
  int arrayLayers();

  /**
   * @return A value specifying the number of samples per texel.
   */

  @Value.Parameter
  Set<VulkanSampleCountFlag> samples();

  /**
   * @return A value specifying the tiling arrangement of the texel blocks in memory.
   */

  @Value.Parameter
  VulkanImageTiling tiling();

  /**
   * @return A value describing the intended usage of the image.
   */

  @Value.Parameter
  Set<VulkanImageUsageFlag> usage();

  /**
   * @return A value specifying the sharing mode of the image when it will be accessed by multiple
   * queue families.
   */

  @Value.Parameter
  VulkanSharingMode sharingMode();

  /**
   * @return A list of queue families that will access this image (ignored if sharingMode is not
   * VK_SHARING_MODE_CONCURRENT).
   */

  @Value.Parameter
  List<Integer> queueFamilyIndices();

  /**
   * @return A value specifying the initial layout of all image subresources of the image.
   */

  @Value.Parameter
  VulkanImageLayout initialLayout();
}
