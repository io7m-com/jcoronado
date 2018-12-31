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
 * Information required to create a buffer.
 *
 * @see "VkBufferViewCreateInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkBufferViewCreateInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanBufferViewCreateInfoType
{
  /**
   * @return A set of flags specifying additional parameters of the buffer view.
   */

  @Value.Parameter
  Set<VulkanBufferViewCreateFlag> flags();

  /**
   * @return The buffer on which the view will be created
   */

  @Value.Parameter
  VulkanBufferType buffer();

  /**
   * @return The format of the data elements in the buffer
   */

  @Value.Parameter
  VulkanFormat format();

  /**
   * @return An offset in bytes from the base address of the buffer. Accesses to the buffer view
   * from shaders use addressing that is relative to this starting offset.
   */

  @Value.Parameter
  long offset();

  /**
   * @return A size in bytes of the buffer view. If range is equal to VK_WHOLE_SIZE, the range from
   * offset to the end of the buffer is used. If VK_WHOLE_SIZE is used and the remaining size of the
   * buffer is not a multiple of the texel block size of format, the nearest smaller multiple is
   * used.
   */

  @Value.Parameter
  long range();
}
