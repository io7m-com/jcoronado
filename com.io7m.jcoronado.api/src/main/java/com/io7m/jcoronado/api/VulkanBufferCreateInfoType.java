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
import java.util.Set;

/**
 * Information required to create a buffer.
 *
 * @see "VkBufferCreateInfo"
 */

@ImmutablesStyleType
@Value.Immutable
public interface VulkanBufferCreateInfoType
{
  /**
   * @return A bitmask of VkBufferCreateFlagBits specifying additional parameters of the buffer.
   */

  @Value.Parameter
  Set<VulkanBufferCreateFlag> flags();

  /**
   * @return The size in bytes of the buffer to be created.
   */

  @Value.Parameter
  long size();

  /**
   * @return A bitmask of VkBufferUsageFlagBits specifying allowed usages of the buffer.
   */

  @Value.Parameter
  Set<VulkanBufferUsageFlag> usageFlags();

  /**
   * @return A VkSharingMode value specifying the sharing mode of the buffer when it will be
   * accessed by multiple queue families.
   */

  @Value.Parameter
  VulkanSharingMode sharingMode();

  /**
   * @return A list of queue families that will access this buffer (ignored if sharingMode is not
   * VK_SHARING_MODE_CONCURRENT).
   */

  @Value.Parameter
  List<Integer> queueFamilyIndices();
}
