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
import java.util.Optional;

/**
 * Structure specifying a subpass description.
 *
 * @see "VkSubpassDescription"
 */

@ImmutablesStyleType
@Value.Immutable
public interface VulkanSubpassDescriptionType
{
  /**
   * @return Description flags
   */

  VulkanSubpassDescriptionFlag flags();

  /**
   * @return The pipeline bind point specifying whether this is a graphics or compute subpass.
   */

  VulkanPipelineBindPoint pipelineBindPoint();

  /**
   * @return A list of the render pass’s attachments that can be read in the fragment shader stage
   * during the subpass, and what layout each attachment will be in during the subpass
   */

  List<VulkanAttachmentReference> inputAttachments();

  /**
   * @return A list of the render pass’s attachments that will be used as color attachments in the
   * subpass, and what layout each attachment will be in during the subpass.
   */

  List<VulkanAttachmentReference> colorAttachments();

  /**
   * @return A list of the the render pass’s attachments that are resolved to at the end of the
   * subpass, and what layout each attachment will be in during the multisample resolve operation.
   */

  List<VulkanAttachmentReference> resolveAttachments();

  /**
   * @return The attachment that will be used for depth/stencil data and the layout it will be in
   * during the subpass.
   */

  Optional<VulkanAttachmentReference> depthStencilAttachment();

  /**
   * @return A list of render pass attachment indices describing the attachments that are not used
   * by a subpass, but whose contents must be preserved throughout the subpass.
   */

  List<Integer> preserveAttachments();
}
