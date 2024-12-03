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

/**
 * Structure specifying a buffer ↔ image copy operation.
 *
 * @see "VkBufferImageCopy"
 */

@VulkanAPIStructType(vulkanStruct = "VkBufferImageCopy")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanBufferImageCopyType
{
  /**
   * @return The offset in bytes from the start of the buffer object where the image data is copied
   * from or to.
   */

  @Value.Parameter
  long bufferOffset();

  /**
   * The bufferRowLength and bufferImageHeight specify in texels a subregion of a larger two- or
   * three-dimensional image in buffer memory, and control the addressing calculations. If either of
   * these values is zero, that aspect of the buffer memory is considered to be tightly packed
   * according to the imageExtent.
   *
   * @return The buffer row length
   */

  @Value.Parameter
  int bufferRowLength();

  /**
   * @return The buffer image height
   */

  @Value.Parameter
  int bufferImageHeight();

  /**
   * @return An image specifying the specific image subresources of the image used for the source or
   * destination image data.
   */

  @Value.Parameter
  VulkanImageSubresourceLayers imageSubresource();

  /**
   * @return The initial x, y, z offsets in texels of the sub-region of the source or destination
   * image data.
   */

  @Value.Parameter
  VulkanOffset3D imageOffset();

  /**
   * @return The size in texels of the image to copy in width, height and depth.
   */

  @Value.Parameter
  VulkanExtent3D imageExtent();
}
