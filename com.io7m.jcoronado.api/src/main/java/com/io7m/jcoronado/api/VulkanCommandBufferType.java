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
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanClearValueType.VulkanClearValueColorType;

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

  @VulkanAPIFunctionType(vulkanFunction = "vkBeginCommandBuffer")
  @VulkanExternallySynchronizedType
  void beginCommandBuffer(
    VulkanCommandBufferBeginInfo info)
    throws VulkanException;

  /**
   * Begin a command buffer.
   *
   * @param flags The usage flags
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkBeginCommandBuffer")
  @VulkanExternallySynchronizedType
  default void beginCommandBuffer(
    final Set<VulkanCommandBufferUsageFlag> flags)
    throws VulkanException
  {
    this.beginCommandBuffer(VulkanCommandBufferBeginInfo.of(flags));
  }

  /**
   * Begin a command buffer.
   *
   * @param flags The usage flags
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkBeginCommandBuffer")
  @VulkanExternallySynchronizedType
  default void beginCommandBuffer(
    final VulkanCommandBufferUsageFlag... flags)
    throws VulkanException
  {
    this.beginCommandBuffer(VulkanCommandBufferBeginInfo.of(Set.of(flags)));
  }

  /**
   * Specify how commands in the first subpass of a render pass are provided.
   *
   * @param info     The begin info
   * @param contents Specifies how the commands in the first subpass will be provided.
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdBeginRenderPass")
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

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdBindPipeline")
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

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdBindVertexBuffers")
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

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdBindIndexBuffer")
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

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdBindDescriptorSets")
  @VulkanExternallySynchronizedType
  void bindDescriptorSets(
    VulkanPipelineBindPoint pipeline_bind_point,
    VulkanPipelineLayoutType layout,
    int first_set,
    List<VulkanDescriptorSetType> descriptor_sets,
    List<Integer> dynamic_offsets)
    throws VulkanException;

  /**
   * Copy regions of an image, potentially performing format conversion.
   *
   * @param source_image        The source image
   * @param source_image_layout The layout of the source image
   * @param target_image        The target image
   * @param target_image_layout The target image layout
   * @param regions             The regions that will be used to blit
   * @param filter              The filter to apply if the blits require scaling
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdBlitImage")
  @VulkanExternallySynchronizedType
  void blitImage(
    VulkanImageType source_image,
    VulkanImageLayout source_image_layout,
    VulkanImageType target_image,
    VulkanImageLayout target_image_layout,
    List<VulkanImageBlit> regions,
    VulkanFilter filter)
    throws VulkanException;

  /**
   * Copy regions of an image, potentially performing format conversion.
   *
   * @param source_image        The source image
   * @param source_image_layout The layout of the source image
   * @param target_image        The target image
   * @param target_image_layout The target image layout
   * @param region              The regions that will be used to blit
   * @param filter              The filter to apply if the blits require scaling
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdBlitImage")
  @VulkanExternallySynchronizedType
  default void blitImage(
    final VulkanImageType source_image,
    final VulkanImageLayout source_image_layout,
    final VulkanImageType target_image,
    final VulkanImageLayout target_image_layout,
    final VulkanImageBlit region,
    final VulkanFilter filter)
    throws VulkanException
  {
    this.blitImage(
      source_image,
      source_image_layout,
      target_image,
      target_image_layout,
      List.of(region),
      filter);
  }

  /**
   * Copy data between buffer regions.
   *
   * @param source  The source buffer
   * @param target  The target buffer
   * @param regions The list of regions to be copied
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdCopyBuffer")
  @VulkanExternallySynchronizedType
  void copyBuffer(
    VulkanBufferType source,
    VulkanBufferType target,
    List<VulkanBufferCopy> regions)
    throws VulkanException;

  /**
   * Copy data from an image to a buffer.
   *
   * @param source_image  The source image
   * @param source_layout The source layout
   * @param target_buffer The target buffer
   * @param regions       The list of regions to be copied
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdCopyImageToBuffer")
  @VulkanExternallySynchronizedType
  void copyImageToBuffer(
    VulkanImageType source_image,
    VulkanImageLayout source_layout,
    VulkanBufferType target_buffer,
    List<VulkanBufferImageCopy> regions)
    throws VulkanException;

  /**
   * Copy data from a buffer to an image.
   *
   * @param source_buffer       The source buffer
   * @param target_image        The target image
   * @param target_image_layout The target image layout
   * @param regions             The list of regions to be copied
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdCopyBufferToImage")
  @VulkanExternallySynchronizedType
  void copyBufferToImage(
    VulkanBufferType source_buffer,
    VulkanImageType target_image,
    VulkanImageLayout target_image_layout,
    List<VulkanBufferImageCopy> regions)
    throws VulkanException;

  /**
   * Copy regions of an image, potentially performing format conversion.
   *
   * @param source_image        The source image
   * @param source_image_layout The layout of the source image
   * @param target_image        The target image
   * @param target_image_layout The target image layout
   * @param regions             The regions that will be used to copy
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdCopyImage")
  @VulkanExternallySynchronizedType
  void copyImage(
    VulkanImageType source_image,
    VulkanImageLayout source_image_layout,
    VulkanImageType target_image,
    VulkanImageLayout target_image_layout,
    List<VulkanImageCopy> regions)
    throws VulkanException;

  /**
   * Copy regions of an image, potentially performing format conversion.
   *
   * @param source_image        The source image
   * @param source_image_layout The layout of the source image
   * @param target_image        The target image
   * @param target_image_layout The target image layout
   * @param region              The regions that will be used to copy
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdCopyImage")
  @VulkanExternallySynchronizedType
  default void copyImage(
    final VulkanImageType source_image,
    final VulkanImageLayout source_image_layout,
    final VulkanImageType target_image,
    final VulkanImageLayout target_image_layout,
    final VulkanImageCopy region)
    throws VulkanException
  {
    this.copyImage(
      source_image,
      source_image_layout,
      target_image,
      target_image_layout,
      List.of(region));
  }

  /**
   * Clear regions within bound framebuffer attachments.
   *
   * @param attachments The attachments to clear and the clear values to use
   * @param rectangles  An array of structures defining regions within each selected attachment to
   *                    clear
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdClearAttachments")
  @VulkanExternallySynchronizedType
  void clearAttachments(
    List<VulkanClearAttachment> attachments,
    List<VulkanClearRectangle> rectangles)
    throws VulkanException;

  /**
   * Clear regions within bound framebuffer attachments.
   *
   * @param attachment The attachment to clear and the clear values to use
   * @param rectangle  A structure defining a region within each selected attachment to clear
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdClearAttachments")
  @VulkanExternallySynchronizedType
  default void clearAttachments(
    final VulkanClearAttachment attachment,
    final VulkanClearRectangle rectangle)
    throws VulkanException
  {
    this.clearAttachments(List.of(attachment), List.of(rectangle));
  }

  /**
   * Clear regions within bound framebuffer attachments.
   *
   * @param attachment The attachment to clear and the clear values to use
   * @param rectangles An array of structures defining regions within each selected attachment to
   *                   clear
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdClearAttachments")
  @VulkanExternallySynchronizedType
  default void clearAttachments(
    final VulkanClearAttachment attachment,
    final List<VulkanClearRectangle> rectangles)
    throws VulkanException
  {
    this.clearAttachments(List.of(attachment), rectangles);
  }

  /**
   * Clear regions within bound framebuffer attachments.
   *
   * @param attachment The attachment to clear and the clear values to use
   * @param rectangle  A structure defining a region within each selected attachment to clear
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdClearAttachments")
  @VulkanExternallySynchronizedType
  default void clearAttachments(
    final List<VulkanClearAttachment> attachment,
    final VulkanClearRectangle rectangle)
    throws VulkanException
  {
    this.clearAttachments(attachment, List.of(rectangle));
  }

  /**
   * Clear regions of a color image.
   *
   * @param image        The image
   * @param image_layout The image layout
   * @param color        The color value
   * @param ranges       The image subresource ranges
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdClearColorImage")
  @VulkanExternallySynchronizedType
  void clearColorImage(
    VulkanImageType image,
    VulkanImageLayout image_layout,
    VulkanClearValueColorType color,
    List<VulkanImageSubresourceRange> ranges)
    throws VulkanException;

  /**
   * Clear regions of a depth stencil image.
   *
   * @param image         The image
   * @param image_layout  The image layout
   * @param depth_stencil The depth stencil value
   * @param ranges        The image subresource ranges
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdClearDepthStencilImage")
  @VulkanExternallySynchronizedType
  void clearDepthStencilImage(
    VulkanImageType image,
    VulkanImageLayout image_layout,
    VulkanClearValueDepthStencil depth_stencil,
    List<VulkanImageSubresourceRange> ranges)
    throws VulkanException;

  /**
   * Dispatch compute work items.
   *
   * @param group_count_x The number of local workgroups to dispatch in the X dimension.
   * @param group_count_y The number of local workgroups to dispatch in the Y dimension.
   * @param group_count_z The number of local workgroups to dispatch in the Z dimension.
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdDispatch")
  @VulkanExternallySynchronizedType
  void dispatch(
    int group_count_x,
    int group_count_y,
    int group_count_z)
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

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdDraw")
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

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdDrawIndexed")
  @VulkanExternallySynchronizedType
  void drawIndexed(
    int vertex_count,
    int instance_count,
    int first_vertex,
    int vertex_offset,
    int first_instance)
    throws VulkanException;

  /**
   * Draw primitives indirectly.
   *
   * @param buffer     The buffer containing draw parameters.
   * @param offset     The byte offset into buffer where parameters begin.
   * @param draw_count The number of draws to execute, and can be zero.
   * @param stride     The byte stride between successive sets of draw parameters.
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdDrawIndirect")
  @VulkanExternallySynchronizedType
  void drawIndirect(
    VulkanBufferType buffer,
    long offset,
    int draw_count,
    int stride)
    throws VulkanException;

  /**
   * Draw primitives indirectly.
   *
   * @param buffer     The buffer containing draw parameters.
   * @param offset     The byte offset into buffer where parameters begin.
   * @param draw_count The number of draws to execute, and can be zero.
   * @param stride     The byte stride between successive sets of draw parameters.
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdDrawIndexedIndirect")
  @VulkanExternallySynchronizedType
  void drawIndexedIndirect(
    VulkanBufferType buffer,
    long offset,
    int draw_count,
    int stride)
    throws VulkanException;

  /**
   * End a render pass.
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdEndRenderPass")
  @VulkanExternallySynchronizedType
  void endRenderPass()
    throws VulkanException;

  /**
   * Insert a memory dependency.
   *
   * @param source_stage_mask      The source stage mask
   * @param target_stage_mask      The target state mask
   * @param dependency_flags       Flags specifying how execution and memory dependencies are
   *                               formed.
   * @param memory_barriers        A list of memory barriers
   * @param buffer_memory_barriers A list of buffer memory barriers
   * @param image_memory_barriers  A list of image memory barriers
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkCmdPipelineBarrier")
  @VulkanExternallySynchronizedType
  void pipelineBarrier(
    Set<VulkanPipelineStageFlag> source_stage_mask,
    Set<VulkanPipelineStageFlag> target_stage_mask,
    Set<VulkanDependencyFlag> dependency_flags,
    List<VulkanMemoryBarrier> memory_barriers,
    List<VulkanBufferMemoryBarrier> buffer_memory_barriers,
    List<VulkanImageMemoryBarrier> image_memory_barriers)
    throws VulkanException;

  /**
   * End a command buffer.
   *
   * @throws VulkanException On errors
   */

  @VulkanAPIFunctionType(vulkanFunction = "vkEndCommandBuffer")
  @VulkanExternallySynchronizedType
  void endCommandBuffer()
    throws VulkanException;
}

