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
 * The type of Vulkan submission information.
 *
 * @see "VkSubmitInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkSubmitInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanSubmitInfoType
{
  /**
   * @return A list of semaphores upon which to wait before the command buffers for this batch begin
   * execution. If semaphores to wait on are provided, they define a semaphore wait operation.
   */

  @Value.Parameter
  List<VulkanSemaphoreType> waitSemaphores();

  /**
   * @return A list of pipeline stages at which each corresponding semaphore wait will occur.
   */

  @Value.Parameter
  List<VulkanPipelineStageFlag> waitStageMasks();

  /**
   * @return A list of command buffers to execute in the batch.
   */

  @Value.Parameter
  List<VulkanCommandBufferType> commandBuffers();

  /**
   * @return A list of semaphores which will be signaled when the command buffers for this batch
   * have completed execution. If semaphores to be signaled are provided, they define a semaphore
   * signal operation.
   */

  @Value.Parameter
  List<VulkanSemaphoreType> signalSemaphores();

  /**
   * Check preconditions for the type.
   */

  @Value.Check
  default void checkPreconditions()
  {
    final var size_0 = this.waitSemaphores().size();
    final var size_1 = this.waitStageMasks().size();
    if (size_0 != size_1) {
      final var separator = System.lineSeparator();
      throw new IllegalArgumentException(
        new StringBuilder(64)
          .append("Number of wait semaphores must match the number of stage masks.")
          .append(separator)
          .append("  Semaphores:  ")
          .append(size_0)
          .append(separator)
          .append("  Stage masks: ")
          .append(size_1)
          .append(separator)
          .toString());
    }
  }
}
