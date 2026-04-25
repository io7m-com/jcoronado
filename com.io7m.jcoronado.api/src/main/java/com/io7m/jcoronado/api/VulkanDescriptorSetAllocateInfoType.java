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

/**
 * @see "VkDescriptorSetAllocateInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkDescriptorSetAllocateInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanDescriptorSetAllocateInfoType
{
  /**
   * @return The descriptor pool from which descriptor sets will be allocated
   */

  VulkanDescriptorPoolType descriptorPool();

  /**
   * @return An array of descriptor set layouts, with each member specifying how the corresponding
   * descriptor set is allocated.
   */

  List<VulkanDescriptorSetLayoutType> setLayouts();

  /**
   * If {@link VulkanDescriptorBindingFlag#VK_DESCRIPTOR_BINDING_VARIABLE_DESCRIPTOR_COUNT} is used,
   * each element in this list specifies the descriptor count for the corresponding layout
   * in {@link #setLayouts()}.
   *
   * @return The list of descriptor counts
   *
   * @see "VkDescriptorSetVariableDescriptorCountAllocateInfo"
   */

  List<Long> descriptorCounts();

  /**
   * Check preconditions for the type.
   */

  @Value.Check
  default void checkPreconditions()
  {
    final var sl = this.setLayouts();
    final var dc = this.descriptorCounts();

    if (!dc.isEmpty()) {
      if (sl.size() != dc.size()) {
        throw new IllegalArgumentException(
          "Descriptor counts list length (%d) must equal layouts list length (%d)."
            .formatted(
              Integer.valueOf(dc.size()),
              Integer.valueOf(sl.size())
            )
        );
      }
    }
  }
}
