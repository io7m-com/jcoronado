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
 * Structure specifying parameters of a newly created query pool.
 *
 * @see "VkQueryPoolCreateInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkQueryPoolCreateInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanQueryPoolCreateInfoType
{
  /**
   * @return A set of flags specifying the creation of the pool
   */

  @Value.Parameter
  Set<VulkanQueryPoolCreateFlag> flags();

  /**
   * @return A value specifying the type of queries managed by the pool.
   */

  @Value.Parameter
  VulkanQueryKind queryType();

  /**
   * @return The number of queries managed by the pool.
   */

  @Value.Parameter
  int queryCount();

  /**
   * @return A set of flags specifying which counters will be returned in queries on the new pool
   */

  @Value.Parameter
  Set<VulkanQueryPipelineStatisticFlag> pipelineStatistics();
}
