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
 * the maximum size of a local compute workgroup, per dimension. These three values represent the
 * maximum local workgroup size in the X, Y, and Z dimensions, respectively. The x, y, and z sizes
 * specified by the LocalSize execution mode and by the object decorated by the WorkgroupSize
 * decoration in shader modules must be less than or equal to the corresponding limit.
 *
 * @see "VkPhysicalDeviceLimits"
 */

@ImmutablesStyleType
@Value.Immutable
public interface VulkanComputeWorkGroupSizeType
{
  /**
   * @return The limit on the X dimension
   */

  @Value.Parameter
  int maximumX();

  /**
   * @return The limit on the Y dimension
   */

  @Value.Parameter
  int maximumY();

  /**
   * @return The limit on the Z dimension
   */

  @Value.Parameter
  int maximumZ();
}
