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

import java.util.Optional;
import java.util.Set;

/**
 * @see "VkPipelineMultisampleStateCreateInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkPipelineMultisampleStateCreateInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanPipelineMultisampleStateCreateInfoType
{
  /**
   * @return Flags reserved for future use
   */

  @Value.Parameter
  Set<VulkanPipelineMultisampleStateCreateFlag> flags();

  /**
   * @return The number of samples per pixel used in rasterization.
   */

  @Value.Parameter
  VulkanSampleCountFlag rasterizationSamples();

  /**
   * @return {@code true} if sample shading should be enabled
   */

  @Value.Parameter
  boolean sampleShadingEnable();

  /**
   * @return minimum fraction of sample shading if {@link #sampleShadingEnable()} is {@code true}
   */

  @Value.Parameter
  float minSampleShading();

  /**
   * @return static coverage information that is ANDed with the coverage information generated
   * during rasterization.
   */

  @Value.Parameter
  Optional<int[]> sampleMask();

  /**
   * @return {@code true} if a temporary coverage value is generated based on the alpha component of
   * the fragment’s first color output
   */

  @Value.Parameter
  boolean alphaToCoverageEnable();

  /**
   * @return {@code true} if the alpha component of the fragment’s first color output is replaced
   * with one as described in "Multisample Coverage".
   */

  @Value.Parameter
  boolean alphaToOneEnable();
}
