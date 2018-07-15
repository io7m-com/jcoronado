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

/**
 * @see "VkCommandBuffer"
 */

public interface VulkanCommandBufferType extends VulkanHandleDispatchableType
{
  /**
   * Begin a command buffer.
   *
   * @param info The begin info
   *
   * @throws VulkanException On errors
   */

  void beginCommandBuffer(
    VulkanCommandBufferBeginInfo info)
    throws VulkanException;

  /**
   * Specify how commands in the first subpass of a render pass are provided.
   *
   * @param info     The begin info
   * @param contents Specifies how the commands in the first subpass will be provided.
   *
   * @throws VulkanException On errors
   */

  void beginRenderPass(
    VulkanRenderPassBeginInfo info,
    VulkanSubpassContents contents)
    throws VulkanException;

  /**
   * Bind a rendering pipeline.
   *
   * @param bind_point The bind point
   * @param pipeline   The pipeline
   *
   * @throws VulkanException On errors
   */

  void bindPipeline(
    VulkanPipelineBindPoint bind_point,
    VulkanPipelineType pipeline)
    throws VulkanException;

  /**
   * Draw primitives.
   *
   * @param vertex_count   The number of vertices to draw.
   * @param instance_count The number of instances to draw.
   * @param first_vertex   The index of the first vertex to draw.
   * @param first_instance The instance ID of the first instance to draw.
   *
   * @throws VulkanException On errors
   */

  void draw(
    int vertex_count,
    int instance_count,
    int first_vertex,
    int first_instance)
    throws VulkanException;

  /**
   * End a render pass.
   *
   * @throws VulkanException On errors
   */

  void endRenderPass()
    throws VulkanException;

  /**
   * End a command buffer.
   *
   * @throws VulkanException On errors
   */

  void endCommandBuffer()
    throws VulkanException;
}

