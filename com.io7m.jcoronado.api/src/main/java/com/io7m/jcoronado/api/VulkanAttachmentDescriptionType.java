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
 * Structure specifying an attachment description
 *
 * @see "VkAttachmentDescription"
 */

@VulkanAPIStructType(vulkanStruct = "VkAttachmentDescription")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanAttachmentDescriptionType
{
  /**
   * @return flags specifying additional properties of the attachment.
   */

  Set<VulkanAttachmentDescriptionFlag> flags();

  /**
   * @return the format of the image view that will be used for the attachment.
   */

  @Value.Default
  default VulkanFormat format()
  {
    return VulkanFormat.VK_FORMAT_UNDEFINED;
  }

  /**
   * @return the number of samples of the image.
   */

  @Value.Default
  default VulkanSampleCountFlag samples()
  {
    return VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT;
  }

  /**
   * @return A value specifying how the contents of color and depth components of the attachment are
   * treated at the beginning of the subpass where it is first used.
   */

  @Value.Default
  default VulkanAttachmentLoadOp loadOp()
  {
    return VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_LOAD;
  }

  /**
   * @return A value specifying how the contents of color and depth components of the attachment are
   * treated at the end of the subpass where it is last used.
   */

  @Value.Default
  default VulkanAttachmentStoreOp storeOp()
  {
    return VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_STORE;
  }

  /**
   * @return A value specifying how the contents of stencil components of the attachment are treated
   * at the beginning of the subpass where it is first used.
   */

  @Value.Default
  default VulkanAttachmentLoadOp stencilLoadOp()
  {
    return VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_LOAD;
  }

  /**
   * @return A value specifying how the contents of stencil components of the attachment are treated
   * at the end of the last subpass where it is used.
   */

  @Value.Default
  default VulkanAttachmentStoreOp stencilStoreOp()
  {
    return VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_STORE;
  }

  /**
   * @return The layout the attachment image subresource will be in when a render pass instance
   * begins.
   */

  @Value.Default
  default VulkanImageLayout initialLayout()
  {
    return VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED;
  }

  /**
   * @return The layout the attachment image subresource will be transitioned to when a render pass
   * instance ends. During a render pass instance, an attachment can use a different layout in each
   * subpass, if desired.
   */

  @Value.Default
  default VulkanImageLayout finalLayout()
  {
    return VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED;
  }
}
