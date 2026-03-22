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

import java.util.List;
import java.util.Optional;

/**
 * Structure specifying parameters of a pipeline for dynamic rendering.
 *
 * @see "VkPipelineRenderingCreateInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkPipelineRenderingCreateInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPipelineRenderingCreateInfoType
{
  /**
   * @return The formats of the color attachments
   */

  List<VulkanFormat> colorAttachmentFormats();

  /**
   * @return The format of the depth attachment
   */

  Optional<VulkanFormat> depthAttachmentFormat();

  /**
   * @return The format of the stencil attachment
   */

  Optional<VulkanFormat> stencilAttachmentFormat();

  /**
   * @return A bitfield of view indices describing which views are
   * active during rendering. It must match VkRenderingInfo::viewMask
   * when rendering.
   */

  @Value.Default
  default long viewMask()
  {
    return 0L;
  }
}
