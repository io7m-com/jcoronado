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
 * Structure specifying a clear attachment.
 *
 * @see "VkClearAttachment"
 */

@VulkanAPIStructType(vulkanStruct = "VkClearAttachment")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanClearAttachmentType
{
  /**
   * @return A mask selecting the color, depth and/or stencil aspects of the attachment to be
   * cleared.
   */

  @Value.Parameter
  Set<VulkanImageAspectFlag> aspectMask();

  /**
   * @return A value that is only meaningful if VK_IMAGE_ASPECT_COLOR_BIT is set in aspectMask, in
   * which case it is an index to the pColorAttachments array in the VkSubpassDescription structure
   * of the current subpass which selects the color attachment to clear.
   */

  @Value.Parameter
  int colorAttachment();

  /**
   * @return The color or depth/stencil value to clear the attachment to
   */

  @Value.Parameter
  VulkanClearValueType clearValue();
}
