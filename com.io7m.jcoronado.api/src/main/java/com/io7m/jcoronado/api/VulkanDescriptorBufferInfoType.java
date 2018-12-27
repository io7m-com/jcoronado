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
 * @see "VkDescriptorBufferInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkDescriptorBufferInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanDescriptorBufferInfoType
{
  /**
   * @return The buffer resource
   */

  @Value.Parameter
  VulkanBufferType buffer();

  /**
   * @return The offset in bytes from the start of buffer. Access to buffer memory via this
   * descriptor uses addressing that is relative to this starting offset.
   */

  @Value.Parameter
  long offset();

  /**
   * @return The size in bytes that is used for this descriptor update, or VK_WHOLE_SIZE to use the
   * range from offset to the end of the buffer.
   */

  @Value.Parameter
  long range();
}
