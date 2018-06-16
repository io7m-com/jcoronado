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
 * Structure specifying a subpass dependency.
 *
 * @see "VkSubpassDependency"
 */

@ImmutablesStyleType
@Value.Immutable
public interface VulkanSubpassDependencyType
{
  /**
   * @return The subpass index of the first subpass in the dependency, or VK_SUBPASS_EXTERNAL.
   */

  @Value.Parameter
  int srcSubpass();

  /**
   * @return The subpass index of the second subpass in the dependency, or VK_SUBPASS_EXTERNAL.
   */

  @Value.Parameter
  int dstSubpass();

  /**
   * @return The source stage mask
   */

  @Value.Parameter
  Set<VulkanPipelineStageFlag> srcStageMask();

  /**
   * @return The destination stage mask
   */

  @Value.Parameter
  Set<VulkanPipelineStageFlag> dstStageMask();

  /**
   * @return The source access mask
   */

  @Value.Parameter
  Set<VulkanAccessFlag> srcAccessMask();

  /**
   * @return The destination access mask
   */

  @Value.Parameter
  Set<VulkanAccessFlag> dstAccessMask();

  /**
   * @return The dependency flags for the subpass
   */

  @Value.Parameter
  Set<VulkanDependencyFlag> dependencyFlags();
}
