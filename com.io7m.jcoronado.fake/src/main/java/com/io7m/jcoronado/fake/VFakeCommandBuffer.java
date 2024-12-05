/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
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


package com.io7m.jcoronado.fake;

import com.io7m.jcoronado.api.VulkanBlendConstants;
import com.io7m.jcoronado.api.VulkanBufferCopy;
import com.io7m.jcoronado.api.VulkanBufferImageCopy;
import com.io7m.jcoronado.api.VulkanBufferType;
import com.io7m.jcoronado.api.VulkanClearAttachment;
import com.io7m.jcoronado.api.VulkanClearRectangle;
import com.io7m.jcoronado.api.VulkanClearValueDepthStencil;
import com.io7m.jcoronado.api.VulkanClearValueType;
import com.io7m.jcoronado.api.VulkanCommandBufferBeginInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferResetFlag;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanDependencyInfo;
import com.io7m.jcoronado.api.VulkanDescriptorSetType;
import com.io7m.jcoronado.api.VulkanEventType;
import com.io7m.jcoronado.api.VulkanFilter;
import com.io7m.jcoronado.api.VulkanImageBlit;
import com.io7m.jcoronado.api.VulkanImageCopy;
import com.io7m.jcoronado.api.VulkanImageLayout;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanIndexType;
import com.io7m.jcoronado.api.VulkanPipelineBindPoint;
import com.io7m.jcoronado.api.VulkanPipelineLayoutType;
import com.io7m.jcoronado.api.VulkanPipelineStageFlag;
import com.io7m.jcoronado.api.VulkanPipelineType;
import com.io7m.jcoronado.api.VulkanQueryControlFlag;
import com.io7m.jcoronado.api.VulkanQueryPoolType;
import com.io7m.jcoronado.api.VulkanRectangle2D;
import com.io7m.jcoronado.api.VulkanRenderPassBeginInfo;
import com.io7m.jcoronado.api.VulkanStencilFaceFlag;
import com.io7m.jcoronado.api.VulkanSubpassContents;
import com.io7m.jcoronado.api.VulkanViewport;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A fake command buffer.
 */

public final class VFakeCommandBuffer implements VulkanCommandBufferType
{
  private final AtomicBoolean closed;

  VFakeCommandBuffer()
  {
    this.closed =
      new AtomicBoolean(false);
  }

  @Override
  public void beginCommandBuffer(
    final VulkanCommandBufferBeginInfo info)
  {

  }

  @Override
  public void beginQuery(
    final VulkanQueryPoolType pool,
    final int query,
    final Set<VulkanQueryControlFlag> flags)
  {

  }

  @Override
  public void endQuery(
    final VulkanQueryPoolType pool,
    final int query)
  {

  }

  @Override
  public void beginRenderPass(
    final VulkanRenderPassBeginInfo info,
    final VulkanSubpassContents contents)
  {

  }

  @Override
  public void bindPipeline(
    final VulkanPipelineBindPoint bind_point,
    final VulkanPipelineType pipeline)
  {

  }

  @Override
  public void bindVertexBuffers(
    final int first_binding,
    final int binding_count,
    final List<VulkanBufferType> buffers,
    final List<Long> offsets)
  {

  }

  @Override
  public void bindIndexBuffer(
    final VulkanBufferType buffer,
    final long offset,
    final VulkanIndexType index_type)
  {

  }

  @Override
  public void bindDescriptorSets(
    final VulkanPipelineBindPoint pipeline_bind_point,
    final VulkanPipelineLayoutType layout,
    final int first_set,
    final List<VulkanDescriptorSetType> descriptor_sets,
    final List<Integer> dynamic_offsets)
  {

  }

  @Override
  public void blitImage(
    final VulkanImageType source_image,
    final VulkanImageLayout source_image_layout,
    final VulkanImageType target_image,
    final VulkanImageLayout target_image_layout,
    final List<VulkanImageBlit> regions,
    final VulkanFilter filter)
  {

  }

  @Override
  public void copyBuffer(
    final VulkanBufferType source,
    final VulkanBufferType target,
    final List<VulkanBufferCopy> regions)
  {

  }

  @Override
  public void copyImageToBuffer(
    final VulkanImageType source_image,
    final VulkanImageLayout source_layout,
    final VulkanBufferType target_buffer,
    final List<VulkanBufferImageCopy> regions)
  {

  }

  @Override
  public void copyBufferToImage(
    final VulkanBufferType source_buffer,
    final VulkanImageType target_image,
    final VulkanImageLayout target_image_layout,
    final List<VulkanBufferImageCopy> regions)
  {

  }

  @Override
  public void copyImage(
    final VulkanImageType source_image,
    final VulkanImageLayout source_image_layout,
    final VulkanImageType target_image,
    final VulkanImageLayout target_image_layout,
    final List<VulkanImageCopy> regions)
  {

  }

  @Override
  public void clearAttachments(
    final List<VulkanClearAttachment> attachments,
    final List<VulkanClearRectangle> rectangles)
  {

  }

  @Override
  public void clearColorImage(
    final VulkanImageType image,
    final VulkanImageLayout image_layout,
    final VulkanClearValueType.VulkanClearValueColorType color,
    final List<VulkanImageSubresourceRange> ranges)
  {

  }

  @Override
  public void clearDepthStencilImage(
    final VulkanImageType image,
    final VulkanImageLayout image_layout,
    final VulkanClearValueDepthStencil depth_stencil,
    final List<VulkanImageSubresourceRange> ranges)
  {

  }

  @Override
  public void dispatch(
    final int group_count_x,
    final int group_count_y,
    final int group_count_z)
  {

  }

  @Override
  public void draw(
    final int vertex_count,
    final int instance_count,
    final int first_vertex,
    final int first_instance)
  {

  }

  @Override
  public void drawIndexed(
    final int vertex_count,
    final int instance_count,
    final int first_vertex,
    final int vertex_offset,
    final int first_instance)
  {

  }

  @Override
  public void drawIndirect(
    final VulkanBufferType buffer,
    final long offset,
    final int draw_count,
    final int stride)
  {

  }

  @Override
  public void drawIndexedIndirect(
    final VulkanBufferType buffer,
    final long offset,
    final int draw_count,
    final int stride)
  {

  }

  @Override
  public void executeCommands(
    final List<VulkanCommandBufferType> commandBuffers)
  {

  }

  @Override
  public void fillBuffer(
    final VulkanBufferType buffer,
    final long offset,
    final long size,
    final int data)
  {

  }

  @Override
  public void endRenderPass()
  {

  }

  @Override
  public void pipelineBarrier(
    final VulkanDependencyInfo info)
  {

  }

  @Override
  public void nextSubpass(
    final VulkanSubpassContents contents)
  {

  }

  @Override
  public void setLineWidth(
    final float width)
  {

  }

  @Override
  public void setDepthBias(
    final float depth_bias_constant_factor,
    final float depth_bias_clamp,
    final float depth_bias_slope_factor)
  {

  }

  @Override
  public void setDepthBounds(
    final float min_depth_bounds,
    final float max_depth_bounds)
  {

  }

  @Override
  public void setBlendConstants(
    final VulkanBlendConstants constants)
  {

  }

  @Override
  public void setStencilReference(
    final Set<VulkanStencilFaceFlag> face_mask,
    final int reference)
  {

  }

  @Override
  public void setStencilCompareMask(
    final Set<VulkanStencilFaceFlag> face_mask,
    final int mask)
  {

  }

  @Override
  public void setStencilWriteMask(
    final Set<VulkanStencilFaceFlag> face_mask,
    final int mask)
  {

  }

  @Override
  public void setScissor(
    final int first_scissor,
    final List<VulkanRectangle2D> rectangles)
  {

  }

  @Override
  public void setViewport(
    final int first_viewport,
    final List<VulkanViewport> viewports)
  {

  }

  @Override
  public void setEvent(
    final VulkanEventType event,
    final VulkanDependencyInfo info)
  {

  }

  @Override
  public void resetEvent(
    final VulkanEventType event,
    final Set<VulkanPipelineStageFlag> mask)
  {

  }

  @Override
  public void resetQueryPool(
    final VulkanQueryPoolType pool,
    final int first_query,
    final int query_count)
  {

  }

  @Override
  public void endCommandBuffer()
  {

  }

  @Override
  public void writeTimestamp(
    final VulkanPipelineStageFlag stage,
    final VulkanQueryPoolType pool,
    final int query_index)
  {

  }

  @Override
  public void waitEvents(
    final List<VulkanEventType> events,
    final List<VulkanDependencyInfo> dependencyInfos)
  {

  }

  @Override
  public void reset(
    final Set<VulkanCommandBufferResetFlag> flags)
  {

  }

  @Override
  public void close()
  {
    if (this.closed.compareAndSet(false, true)) {
      // Nothing
    }
  }

  @Override
  public boolean isClosed()
  {
    return this.closed.get();
  }
}
