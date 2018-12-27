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
 * Structure specifying a image subresource range.
 *
 * @see "VkImageSubresourceRange"
 */

@VulkanAPIStructType(vulkanStruct = "VkImageSubresourceRange")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanImageSubresourceRangeType
{
  /**
   * @return The image aspect flags
   */

  @Value.Parameter
  Set<VulkanImageAspectFlag> aspectMask();

  /**
   * @return The first mipmap level accessible to the view.
   */

  @Value.Parameter
  int baseMipLevel();

  /**
   * @return The number of mipmap levels (starting from baseMipLevel) accessible to the view.
   */

  @Value.Parameter
  int levelCount();

  /**
   * @return The first array layer accessible to the view.
   */

  @Value.Parameter
  int baseArrayLayer();

  /**
   * @return The number of array layers (starting from baseArrayLayer) accessible to the view.
   */

  @Value.Parameter
  int layerCount();
}
