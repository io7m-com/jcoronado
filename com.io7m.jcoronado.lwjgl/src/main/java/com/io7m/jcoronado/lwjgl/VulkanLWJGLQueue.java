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

package com.io7m.jcoronado.lwjgl;

import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueType;
import org.lwjgl.vulkan.VkQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.io7m.jcoronado.lwjgl.VulkanLWJGLHandle.Ownership.VULKAN_OWNED;

final class VulkanLWJGLQueue
  extends VulkanLWJGLHandle implements VulkanQueueType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLQueue.class);

  private final VulkanLWJGLLogicalDevice device;
  private final VkQueue queue;
  private final VulkanQueueFamilyProperties properties;
  private final int queue_index;

  VulkanLWJGLQueue(
    final VulkanLWJGLLogicalDevice in_device,
    final VkQueue in_queue,
    final VulkanQueueFamilyProperties in_properties,
    final int in_queue_index)
  {
    super(VULKAN_OWNED);

    this.device =
      Objects.requireNonNull(in_device, "device");
    this.queue =
      Objects.requireNonNull(in_queue, "queue");
    this.properties =
      Objects.requireNonNull(in_properties, "properties");
    this.queue_index =
      in_queue_index;
  }

  @Override
  public String toString()
  {
    return new StringBuilder(64)
      .append("[VulkanLWJGLQueue family ")
      .append(this.properties.queueFamilyIndex())
      .append(" index ")
      .append(this.queue_index)
      .append(']')
      .toString();
  }

  @Override
  protected Logger logger()
  {
    return LOG;
  }

  @Override
  protected void closeActual()
  {
    LOG.debug("destroying queue: {}", this.queue);
  }

  @Override
  public VulkanQueueFamilyProperties queueFamilyProperties()
  {
    return this.properties;
  }

  @Override
  public int queueIndex()
  {
    return this.queue_index;
  }
}
