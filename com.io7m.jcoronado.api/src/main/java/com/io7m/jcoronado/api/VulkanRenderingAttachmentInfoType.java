/*
 * Copyright © 2026 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import java.util.Optional;
import java.util.Set;

/**
 * Structure specifying rendering attachment info.
 *
 * @see "VkRenderingAttachmentInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkRenderingAttachmentInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanRenderingAttachmentInfoType
{
  /**
   * @return The image view
   */

  VulkanImageViewType imageView();

  /**
   * @return The image layout
   */

  VulkanImageLayout imageLayout();

  /**
   * @return The resolve mode flags
   */

  Set<VulkanResolveModeFlag> resolveMode();

  /**
   * @return The resolved image view
   */

  Optional<VulkanImageViewType> resolveImageView();

  /**
   * @return The resolved image layout
   */

  @Value.Default
  default VulkanImageLayout resolveImageLayout()
  {
    return VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED;
  }

  /**
   * @return The attachment load op
   */

  VulkanAttachmentLoadOp loadOp();

  /**
   * @return The attachment store op
   */

  VulkanAttachmentStoreOp storeOp();

  /**
   * @return The attachment clear value
   */

  VulkanClearValueType clearValue();
}
