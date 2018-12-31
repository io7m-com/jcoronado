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
 * Structure specifying subresource layout.
 *
 * @see "VkSubresourceLayout"
 */

@VulkanAPIStructType(vulkanStruct = "VkSubresourceLayout")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanSubresourceLayoutType
{
  /**
   * @return The byte offset from the start of the image or the plane where the image subresource
   * begins.
   */

  @Value.Parameter
  long offset();

  /**
   * @return The size in bytes of the image subresource. size includes any extra memory that is
   * required based on rowPitch.
   */

  @Value.Parameter
  long size();

  /**
   * @return The number of bytes between each row of texels in an image.
   */

  @Value.Parameter
  long rowPitch();

  /**
   * @return The number of bytes between each array layer of an image.
   */

  @Value.Parameter
  long arrayPitch();

  /**
   * @return The number of bytes between each slice of 3D image.
   */

  @Value.Parameter
  long depthPitch();
}
