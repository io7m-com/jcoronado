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

import java.util.List;

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

  @VulkanExternallySynchronizedType
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

  @VulkanExternallySynchronizedType
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

  @VulkanExternallySynchronizedType
  void bindPipeline(
    VulkanPipelineBindPoint bind_point,
    VulkanPipelineType pipeline)
    throws VulkanException;

  /**
   * Bind vertex buffers to a command buffer.
   *
   * @param first_binding The index of the first vertex input binding whose state is updated by the
   *                      command
   * @param binding_count The number of vertex input bindings whose state is updated by the command
   * @param buffers       An array of buffer handles
   * @param offsets       An array of buffer offsets
   *
   * @throws VulkanException On errors
   */

  @VulkanExternallySynchronizedType
  void bindVertexBuffers(
    int first_binding,
    int binding_count,
    List<VulkanBufferType> buffers,
    List<Long> offsets)
    throws VulkanException;

  /**
   * Bind index buffer to a command buffer.
   *
   * @param buffer     The index buffer
   * @param offset     The starting offset in bytes within buffer used in index buffer address
   *                   calculations
   * @param index_type The type of indices
   *
   * @throws VulkanException On errors
   */

  @VulkanExternallySynchronizedType
  void bindIndexBuffer(
    VulkanBufferType buffer,
    long offset,
    VulkanIndexType index_type)
    throws VulkanException;

  /**
   * Bind descriptor sets to a command buffer.
   *
   * @param pipeline_bind_point The pipeline bind point
   * @param layout              The pipeline layout
   * @param first_set           The set number of the first descriptor set to be bound
   * @param descriptor_sets     The descriptor sets
   * @param dynamic_offsets     An array of dynamic offsets
   *
   * @throws VulkanException On errors
   */

  @VulkanExternallySynchronizedType
  void bindDescriptorSets(
    VulkanPipelineBindPoint pipeline_bind_point,
    VulkanPipelineLayoutType layout,
    int first_set,
    List<VulkanDescriptorSetType> descriptor_sets,
    List<Integer> dynamic_offsets)
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

  @VulkanExternallySynchronizedType
  void draw(
    int vertex_count,
    int instance_count,
    int first_vertex,
    int first_instance)
    throws VulkanException;

  /**
   * Draw primitives using an index buffer.
   *
   * @param vertex_count   The number of vertices to draw.
   * @param instance_count The number of instances to draw.
   * @param first_vertex   The index of the first vertex to draw.
   * @param vertex_offset  The value added to the vertex index before indexing into the vertex
   *                       buffer.
   * @param first_instance The instance ID of the first instance to draw.
   *
   * @throws VulkanException On errors
   */

  @VulkanExternallySynchronizedType
  void drawIndexed(
    int vertex_count,
    int instance_count,
    int first_vertex,
    int vertex_offset,
    int first_instance)
    throws VulkanException;

  /**
   * End a render pass.
   *
   * @throws VulkanException On errors
   */

  @VulkanExternallySynchronizedType
  void endRenderPass()
    throws VulkanException;

  /**
   * End a command buffer.
   *
   * @throws VulkanException On errors
   */

  @VulkanExternallySynchronizedType
  void endCommandBuffer()
    throws VulkanException;
}

