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

import com.io7m.jcoronado.api.VulkanBlendConstants;
import com.io7m.jcoronado.api.VulkanBufferCopy;
import com.io7m.jcoronado.api.VulkanBufferImageCopy;
import com.io7m.jcoronado.api.VulkanBufferMemoryBarrier;
import com.io7m.jcoronado.api.VulkanBufferType;
import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanClearAttachment;
import com.io7m.jcoronado.api.VulkanClearRectangle;
import com.io7m.jcoronado.api.VulkanClearValueDepthStencil;
import com.io7m.jcoronado.api.VulkanCommandBufferBeginInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanDependencyFlag;
import com.io7m.jcoronado.api.VulkanDescriptorSetType;
import com.io7m.jcoronado.api.VulkanDestroyedException;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanEventType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExternallySynchronizedType;
import com.io7m.jcoronado.api.VulkanFilter;
import com.io7m.jcoronado.api.VulkanImageBlit;
import com.io7m.jcoronado.api.VulkanImageCopy;
import com.io7m.jcoronado.api.VulkanImageLayout;
import com.io7m.jcoronado.api.VulkanImageMemoryBarrier;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanIndexType;
import com.io7m.jcoronado.api.VulkanMemoryBarrier;
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
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanClearValueType.VulkanClearValueColorType;
import static com.io7m.jcoronado.lwjgl.VulkanLWJGLClassChecks.checkInstanceOf;
import static com.io7m.jcoronado.lwjgl.VulkanLWJGLIntegerArrays.packIntsOrNull;
import static com.io7m.jcoronado.lwjgl.VulkanLWJGLIntegerArrays.packLongs;

/**
 * LWJGL {@link VulkanCommandBufferType}.
 */

public final class VulkanLWJGLCommandBuffer
  extends VulkanLWJGLHandle implements VulkanCommandBufferType
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLCommandBuffer.class);

  private final VkCommandBuffer handle;
  private final MemoryStack stack_initial;

  VulkanLWJGLCommandBuffer(
    final Ownership ownership,
    final VkCommandBuffer in_handle,
    final VulkanLWJGLHostAllocatorProxy in_host_allocator_proxy)
  {
    super(ownership, in_host_allocator_proxy);
    this.handle = in_handle;

    this.stack_initial =
      MemoryStack.create();
  }

  @Override
  public boolean equals(final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || !Objects.equals(this.getClass(), o.getClass())) {
      return false;
    }
    final var that = (VulkanLWJGLCommandBuffer) o;
    return Objects.equals(this.handle, that.handle);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(Long.valueOf(this.handle.address()));
  }

  @Override
  public String toString()
  {
    return new StringBuilder(32)
      .append("[VulkanLWJGLCommandBuffer 0x")
      .append(Long.toUnsignedString(this.handle.address(), 16))
      .append("]")
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

  }

  @Override
  public void beginCommandBuffer(
    final VulkanCommandBufferBeginInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var packed = VulkanLWJGLCommandBufferBeginInfos.pack(stack, info);

      VulkanChecks.checkReturnCode(
        VK10.vkBeginCommandBuffer(this.handle, packed),
        "vkBeginCommandBuffer");
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void beginQuery(
    final VulkanQueryPoolType pool,
    final int query,
    final Set<VulkanQueryControlFlag> flags)
    throws VulkanException
  {
    Objects.requireNonNull(pool, "pool");
    Objects.requireNonNull(flags, "flags");

    this.checkNotClosed();

    VK10.vkCmdBeginQuery(
      this.handle,
      checkInstanceOf(pool, VulkanLWJGLQueryPool.class).handle(),
      query,
      VulkanEnumMaps.packValues(flags));
  }

  @Override
  public @VulkanExternallySynchronizedType void endQuery(
    final VulkanQueryPoolType pool,
    final int query)
    throws VulkanException
  {
    Objects.requireNonNull(pool, "pool");

    this.checkNotClosed();

    VK10.vkCmdEndQuery(
      this.handle,
      checkInstanceOf(pool, VulkanLWJGLQueryPool.class).handle(),
      query);
  }

  @Override
  public void beginRenderPass(
    final VulkanRenderPassBeginInfo info,
    final VulkanSubpassContents contents)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");
    Objects.requireNonNull(contents, "contents");

    this.checkNotClosed();

    final var render_pass =
      checkInstanceOf(info.renderPass(), VulkanLWJGLRenderPass.class);
    final var framebuffer =
      checkInstanceOf(info.framebuffer(), VulkanLWJGLFramebuffer.class);

    try (var stack = this.stack_initial.push()) {
      final var packed =
        VulkanLWJGLRenderPassBeginInfos.pack(
          stack,
          info,
          render_pass,
          framebuffer);

      VK10.vkCmdBeginRenderPass(this.handle, packed, contents.value());
    }
  }

  @Override
  public void bindPipeline(
    final VulkanPipelineBindPoint bind_point,
    final VulkanPipelineType pipeline)
    throws VulkanException
  {
    Objects.requireNonNull(bind_point, "bind_point");
    Objects.requireNonNull(pipeline, "pipeline");

    this.checkNotClosed();

    VK10.vkCmdBindPipeline(
      this.handle,
      bind_point.value(),
      checkInstanceOf(pipeline, VulkanLWJGLPipeline.class).handle());
  }

  @Override
  public void bindVertexBuffers(
    final int first_binding,
    final int binding_count,
    final List<VulkanBufferType> buffers,
    final List<Long> offsets)
    throws VulkanException
  {
    Objects.requireNonNull(buffers, "buffers");
    Objects.requireNonNull(offsets, "offsets");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdBindVertexBuffers(
        this.handle,
        first_binding,
        packLongs(
          stack,
          buffers,
          b -> checkInstanceOf(b, VulkanLWJGLBuffer.class).handle()),
        packLongs(stack, offsets, Long::longValue));
    }
  }

  @Override
  public void bindIndexBuffer(
    final VulkanBufferType buffer,
    final long offset,
    final VulkanIndexType index_type)
    throws VulkanException
  {
    Objects.requireNonNull(buffer, "buffer");
    Objects.requireNonNull(index_type, "index_type");

    this.checkNotClosed();

    VK10.vkCmdBindIndexBuffer(
      this.handle,
      checkInstanceOf(buffer, VulkanLWJGLBuffer.class).handle(),
      offset,
      index_type.value());
  }

  @Override
  public @VulkanExternallySynchronizedType void bindDescriptorSets(
    final VulkanPipelineBindPoint pipeline_bind_point,
    final VulkanPipelineLayoutType layout,
    final int first_set,
    final List<VulkanDescriptorSetType> descriptor_sets,
    final List<Integer> dynamic_offsets)
    throws VulkanException
  {
    Objects.requireNonNull(pipeline_bind_point, "pipeline_bind_point");
    Objects.requireNonNull(layout, "layout");
    Objects.requireNonNull(descriptor_sets, "descriptor_sets");
    Objects.requireNonNull(dynamic_offsets, "dynamic_offsets");

    this.checkNotClosed();

    final var clayout = checkInstanceOf(
      layout,
      VulkanLWJGLPipelineLayout.class);

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdBindDescriptorSets(
        this.handle,
        pipeline_bind_point.value(),
        clayout.handle(),
        first_set,
        packLongs(
          stack,
          descriptor_sets,
          value -> checkInstanceOf(
            value,
            VulkanLWJGLDescriptorSet.class).handle()),
        packIntsOrNull(stack, dynamic_offsets, Integer::intValue));
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void blitImage(
    final VulkanImageType source_image,
    final VulkanImageLayout source_image_layout,
    final VulkanImageType target_image,
    final VulkanImageLayout target_image_layout,
    final List<VulkanImageBlit> regions,
    final VulkanFilter filter)
    throws VulkanException
  {
    Objects.requireNonNull(source_image, "source_image");
    Objects.requireNonNull(source_image_layout, "source_image_layout");
    Objects.requireNonNull(target_image, "target_image");
    Objects.requireNonNull(target_image_layout, "target_image_layout");
    Objects.requireNonNull(regions, "regions");
    Objects.requireNonNull(filter, "filter");

    this.checkNotClosed();

    final var csource = checkInstanceOf(source_image, VulkanLWJGLImage.class);
    final var ctarget = checkInstanceOf(target_image, VulkanLWJGLImage.class);

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdBlitImage(
        this.handle,
        csource.handle(),
        source_image_layout.value(),
        ctarget.handle(),
        target_image_layout.value(),
        VulkanLWJGLImageBlits.packList(stack, regions),
        filter.value());
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void copyImage(
    final VulkanImageType source_image,
    final VulkanImageLayout source_image_layout,
    final VulkanImageType target_image,
    final VulkanImageLayout target_image_layout,
    final List<VulkanImageCopy> regions)
    throws VulkanException
  {
    Objects.requireNonNull(source_image, "source_image");
    Objects.requireNonNull(source_image_layout, "source_image_layout");
    Objects.requireNonNull(target_image, "target_image");
    Objects.requireNonNull(target_image_layout, "target_image_layout");
    Objects.requireNonNull(regions, "regions");

    this.checkNotClosed();

    final var csource = checkInstanceOf(source_image, VulkanLWJGLImage.class);
    final var ctarget = checkInstanceOf(target_image, VulkanLWJGLImage.class);

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdCopyImage(
        this.handle,
        csource.handle(),
        source_image_layout.value(),
        ctarget.handle(),
        target_image_layout.value(),
        VulkanLWJGLImageCopies.packList(stack, regions));
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void clearAttachments(
    final List<VulkanClearAttachment> attachments,
    final List<VulkanClearRectangle> rectangles)
    throws VulkanException
  {
    Objects.requireNonNull(attachments, "attachments");
    Objects.requireNonNull(rectangles, "rectangles");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdClearAttachments(
        this.handle,
        VulkanLWJGLClearAttachments.packList(stack, attachments),
        VulkanLWJGLClearRectangles.packList(stack, rectangles));
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void clearColorImage(
    final VulkanImageType image,
    final VulkanImageLayout image_layout,
    final VulkanClearValueColorType color,
    final List<VulkanImageSubresourceRange> ranges)
    throws VulkanException
  {
    Objects.requireNonNull(image, "image");
    Objects.requireNonNull(image_layout, "image_layout");
    Objects.requireNonNull(color, "color");
    Objects.requireNonNull(ranges, "ranges");

    this.checkNotClosed();

    final var cimage = checkInstanceOf(image, VulkanLWJGLImage.class);

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdClearColorImage(
        this.handle,
        cimage.handle(),
        image_layout.value(),
        VulkanLWJGLClearValues.packColor(stack, color),
        VulkanLWJGLImageSubresourceRanges.packList(stack, ranges));
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void clearDepthStencilImage(
    final VulkanImageType image,
    final VulkanImageLayout image_layout,
    final VulkanClearValueDepthStencil depth_stencil,
    final List<VulkanImageSubresourceRange> ranges)
    throws VulkanException
  {
    Objects.requireNonNull(image, "image");
    Objects.requireNonNull(image_layout, "image_layout");
    Objects.requireNonNull(depth_stencil, "depth_stencil");
    Objects.requireNonNull(ranges, "ranges");

    this.checkNotClosed();

    final var cimage = checkInstanceOf(image, VulkanLWJGLImage.class);

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdClearDepthStencilImage(
        this.handle,
        cimage.handle(),
        image_layout.value(),
        VulkanLWJGLClearValues.packDepthStencil(stack, depth_stencil),
        VulkanLWJGLImageSubresourceRanges.packList(stack, ranges));
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void dispatch(
    final int group_count_x,
    final int group_count_y,
    final int group_count_z)
  {
    VK10.vkCmdDispatch(
      this.handle,
      group_count_x,
      group_count_y,
      group_count_z);
  }

  @Override
  public @VulkanExternallySynchronizedType void copyBuffer(
    final VulkanBufferType source,
    final VulkanBufferType target,
    final List<VulkanBufferCopy> regions)
    throws VulkanException
  {
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");
    Objects.requireNonNull(regions, "regions");

    this.checkNotClosed();

    final var csource = checkInstanceOf(source, VulkanLWJGLBuffer.class);
    final var ctarget = checkInstanceOf(target, VulkanLWJGLBuffer.class);

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdCopyBuffer(
        this.handle,
        csource.handle(),
        ctarget.handle(),
        VulkanLWJGLBufferCopy.packList(stack, regions));
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void copyImageToBuffer(
    final VulkanImageType source_image,
    final VulkanImageLayout source_layout,
    final VulkanBufferType target_buffer,
    final List<VulkanBufferImageCopy> regions)
    throws VulkanException
  {
    Objects.requireNonNull(source_image, "source_image");
    Objects.requireNonNull(source_layout, "source_layout");
    Objects.requireNonNull(target_buffer, "target_buffer");
    Objects.requireNonNull(regions, "regions");

    this.checkNotClosed();

    final var cimage = checkInstanceOf(source_image, VulkanLWJGLImage.class);
    final var cbuffer = checkInstanceOf(target_buffer, VulkanLWJGLBuffer.class);

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdCopyImageToBuffer(
        this.handle,
        cimage.handle(),
        source_layout.value(),
        cbuffer.handle(),
        VulkanLWJGLBufferImageCopy.packList(stack, regions));
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void copyBufferToImage(
    final VulkanBufferType source_buffer,
    final VulkanImageType target_image,
    final VulkanImageLayout target_image_layout,
    final List<VulkanBufferImageCopy> regions)
    throws VulkanException
  {
    Objects.requireNonNull(source_buffer, "source_buffer");
    Objects.requireNonNull(target_image, "target_image");
    Objects.requireNonNull(target_image_layout, "target_image_layout");
    Objects.requireNonNull(regions, "regions");

    this.checkNotClosed();

    final var cbuffer = checkInstanceOf(source_buffer, VulkanLWJGLBuffer.class);
    final var cimage = checkInstanceOf(target_image, VulkanLWJGLImage.class);

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdCopyBufferToImage(
        this.handle,
        cbuffer.handle(),
        cimage.handle(),
        target_image_layout.value(),
        VulkanLWJGLBufferImageCopy.packList(stack, regions));
    }
  }

  @Override
  public void draw(
    final int vertex_count,
    final int instance_count,
    final int first_vertex,
    final int first_instance)
    throws VulkanDestroyedException
  {
    this.checkNotClosed();

    VK10.vkCmdDraw(
      this.handle,
      vertex_count,
      instance_count,
      first_vertex,
      first_instance);
  }

  @Override
  public void drawIndexed(
    final int vertex_count,
    final int instance_count,
    final int first_vertex,
    final int vertex_offset,
    final int first_instance)
    throws VulkanDestroyedException
  {
    this.checkNotClosed();

    VK10.vkCmdDrawIndexed(
      this.handle,
      vertex_count,
      instance_count,
      first_vertex,
      vertex_offset,
      first_instance);
  }

  @Override
  public @VulkanExternallySynchronizedType void drawIndirect(
    final VulkanBufferType buffer,
    final long offset,
    final int draw_count,
    final int stride)
    throws VulkanException
  {
    Objects.requireNonNull(buffer, "buffer");

    this.checkNotClosed();

    VK10.vkCmdDrawIndirect(
      this.handle,
      checkInstanceOf(buffer, VulkanLWJGLBuffer.class).handle(),
      offset,
      draw_count,
      stride);
  }

  @Override
  public @VulkanExternallySynchronizedType void drawIndexedIndirect(
    final VulkanBufferType buffer,
    final long offset,
    final int draw_count,
    final int stride)
    throws VulkanException
  {
    Objects.requireNonNull(buffer, "buffer");

    this.checkNotClosed();

    VK10.vkCmdDrawIndexedIndirect(
      this.handle,
      checkInstanceOf(buffer, VulkanLWJGLBuffer.class).handle(),
      offset,
      draw_count,
      stride);
  }

  @Override
  public void executeCommands(
    final List<VulkanCommandBufferType> commandBuffers)
    throws VulkanException
  {
    Objects.requireNonNull(commandBuffers, "commandBuffers");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      final var pointers =
        stack.mallocPointer(commandBuffers.size());

      for (int index = 0; index < commandBuffers.size(); ++index) {
        final var secondary =
          checkInstanceOf(
            commandBuffers.get(index),
            VulkanLWJGLCommandBuffer.class);
        pointers.put(index, secondary.handle.address());
      }

      VK10.vkCmdExecuteCommands(
        this.handle,
        pointers
      );
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void fillBuffer(
    final VulkanBufferType buffer,
    final long offset,
    final long size,
    final int data)
    throws VulkanException
  {
    Objects.requireNonNull(buffer, "buffer");

    this.checkNotClosed();

    VK10.vkCmdFillBuffer(
      this.handle,
      checkInstanceOf(buffer, VulkanLWJGLBuffer.class).handle(),
      offset,
      size,
      data);
  }

  @Override
  public void endRenderPass()
    throws VulkanDestroyedException
  {
    this.checkNotClosed();
    VK10.vkCmdEndRenderPass(this.handle);
  }

  @Override
  public @VulkanExternallySynchronizedType void pipelineBarrier(
    final Set<VulkanPipelineStageFlag> source_stage_mask,
    final Set<VulkanPipelineStageFlag> target_stage_mask,
    final Set<VulkanDependencyFlag> dependency_flags,
    final List<VulkanMemoryBarrier> memory_barriers,
    final List<VulkanBufferMemoryBarrier> buffer_memory_barriers,
    final List<VulkanImageMemoryBarrier> image_memory_barriers)
    throws VulkanException
  {
    Objects.requireNonNull(source_stage_mask, "source_stage_mask");
    Objects.requireNonNull(target_stage_mask, "target_stage_mask");
    Objects.requireNonNull(dependency_flags, "dependency_flags");
    Objects.requireNonNull(memory_barriers, "memory_barriers");
    Objects.requireNonNull(buffer_memory_barriers, "buffer_memory_barriers");
    Objects.requireNonNull(image_memory_barriers, "image_memory_barriers");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdPipelineBarrier(
        this.handle,
        VulkanEnumMaps.packValues(source_stage_mask),
        VulkanEnumMaps.packValues(target_stage_mask),
        VulkanEnumMaps.packValues(dependency_flags),
        VulkanLWJGLMemoryBarriers.packList(stack, memory_barriers),
        VulkanLWJGLBufferMemoryBarriers.packList(stack, buffer_memory_barriers),
        VulkanLWJGLImageMemoryBarriers.packList(stack, image_memory_barriers));
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void nextSubpass(
    final VulkanSubpassContents contents)
    throws VulkanException
  {
    Objects.requireNonNull(contents, "contents");

    this.checkNotClosed();

    VK10.vkCmdNextSubpass(this.handle, contents.value());
  }

  @Override
  public @VulkanExternallySynchronizedType void setLineWidth(
    final float width)
    throws VulkanException
  {
    this.checkNotClosed();

    VK10.vkCmdSetLineWidth(this.handle, width);
  }

  @Override
  public @VulkanExternallySynchronizedType void setDepthBias(
    final float depth_bias_constant_factor,
    final float depth_bias_clamp,
    final float depth_bias_slope_factor)
    throws VulkanException
  {
    this.checkNotClosed();

    VK10.vkCmdSetDepthBias(
      this.handle,
      depth_bias_constant_factor,
      depth_bias_clamp,
      depth_bias_slope_factor);
  }

  @Override
  public @VulkanExternallySynchronizedType void setDepthBounds(
    final float min_depth_bounds,
    final float max_depth_bounds)
    throws VulkanException
  {
    this.checkNotClosed();

    VK10.vkCmdSetDepthBounds(this.handle, min_depth_bounds, max_depth_bounds);
  }

  @Override
  public @VulkanExternallySynchronizedType void setBlendConstants(
    final VulkanBlendConstants constants)
    throws VulkanException
  {
    Objects.requireNonNull(constants, "constants");

    this.checkNotClosed();

    VK10.vkCmdSetBlendConstants(this.handle, new float[]{
      constants.r(),
      constants.g(),
      constants.b(),
      constants.a(),
    });
  }

  @Override
  public @VulkanExternallySynchronizedType void setStencilReference(
    final Set<VulkanStencilFaceFlag> face_mask,
    final int reference)
    throws VulkanException
  {
    Objects.requireNonNull(face_mask, "face_mask");

    this.checkNotClosed();

    VK10.vkCmdSetStencilReference(
      this.handle,
      VulkanEnumMaps.packValues(face_mask),
      reference);
  }

  @Override
  public @VulkanExternallySynchronizedType void setStencilCompareMask(
    final Set<VulkanStencilFaceFlag> face_mask,
    final int mask)
    throws VulkanException
  {
    Objects.requireNonNull(face_mask, "face_mask");

    this.checkNotClosed();

    VK10.vkCmdSetStencilCompareMask(
      this.handle,
      VulkanEnumMaps.packValues(face_mask),
      mask);
  }

  @Override
  public @VulkanExternallySynchronizedType void setStencilWriteMask(
    final Set<VulkanStencilFaceFlag> face_mask,
    final int mask)
    throws VulkanException
  {
    Objects.requireNonNull(face_mask, "face_mask");

    this.checkNotClosed();

    VK10.vkCmdSetStencilWriteMask(
      this.handle,
      VulkanEnumMaps.packValues(face_mask),
      mask);
  }

  @Override
  public @VulkanExternallySynchronizedType void setScissor(
    final int first_scissor,
    final List<VulkanRectangle2D> rectangles)
    throws VulkanException
  {
    Objects.requireNonNull(rectangles, "rectangles");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdSetScissor(
        this.handle,
        first_scissor,
        VulkanLWJGLRect2Ds.packList(stack, rectangles));
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void setViewport(
    final int first_viewport,
    final List<VulkanViewport> viewports)
    throws VulkanException
  {
    Objects.requireNonNull(viewports, "viewports");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdSetViewport(
        this.handle,
        first_viewport,
        VulkanLWJGLViewports.packList(stack, viewports));
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void setEvent(
    final VulkanEventType event,
    final Set<VulkanPipelineStageFlag> mask)
    throws VulkanException
  {
    Objects.requireNonNull(event, "event");
    Objects.requireNonNull(mask, "mask");

    VK10.vkCmdSetEvent(
      this.handle,
      checkInstanceOf(event, VulkanLWJGLEvent.class).handle(),
      VulkanEnumMaps.packValues(mask));
  }

  @Override
  public @VulkanExternallySynchronizedType void resetEvent(
    final VulkanEventType event,
    final Set<VulkanPipelineStageFlag> mask)
    throws VulkanException
  {
    Objects.requireNonNull(event, "event");
    Objects.requireNonNull(mask, "mask");

    this.checkNotClosed();

    VK10.vkCmdResetEvent(
      this.handle,
      checkInstanceOf(event, VulkanLWJGLEvent.class).handle(),
      VulkanEnumMaps.packValues(mask));
  }

  @Override
  public @VulkanExternallySynchronizedType void resetQueryPool(
    final VulkanQueryPoolType pool,
    final int first_query,
    final int query_count)
    throws VulkanException
  {
    Objects.requireNonNull(pool, "pool");

    this.checkNotClosed();

    VK10.vkCmdResetQueryPool(
      this.handle,
      checkInstanceOf(pool, VulkanLWJGLQueryPool.class).handle(),
      first_query,
      query_count);
  }

  @Override
  public void endCommandBuffer()
    throws VulkanException
  {
    this.checkNotClosed();

    VulkanChecks.checkReturnCode(
      VK10.vkEndCommandBuffer(this.handle),
      "vkEndCommandBuffer");
  }

  @Override
  public @VulkanExternallySynchronizedType void writeTimestamp(
    final VulkanPipelineStageFlag stage,
    final VulkanQueryPoolType pool,
    final int query_index)
    throws VulkanException
  {
    Objects.requireNonNull(stage, "stage");
    Objects.requireNonNull(pool, "pool");

    this.checkNotClosed();

    VK10.vkCmdWriteTimestamp(
      this.handle,
      stage.value(),
      checkInstanceOf(pool, VulkanLWJGLQueryPool.class).handle(),
      query_index);
  }

  @Override
  public @VulkanExternallySynchronizedType void waitEvents(
    final List<VulkanEventType> events,
    final Set<VulkanPipelineStageFlag> source_stage_mask,
    final Set<VulkanPipelineStageFlag> target_stage_mask,
    final List<VulkanMemoryBarrier> memory_barriers,
    final List<VulkanBufferMemoryBarrier> buffer_memory_barriers,
    final List<VulkanImageMemoryBarrier> image_memory_barriers)
    throws VulkanException
  {
    Objects.requireNonNull(events, "events");
    Objects.requireNonNull(source_stage_mask, "source_stage_mask");
    Objects.requireNonNull(target_stage_mask, "target_stage_mask");
    Objects.requireNonNull(memory_barriers, "memory_barriers");
    Objects.requireNonNull(buffer_memory_barriers, "buffer_memory_barriers");
    Objects.requireNonNull(image_memory_barriers, "image_memory_barriers");

    this.checkNotClosed();

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdWaitEvents(
        this.handle,
        packLongs(
          stack,
          events,
          v -> checkInstanceOf(v, VulkanLWJGLEvent.class).handle()),
        VulkanEnumMaps.packValues(source_stage_mask),
        VulkanEnumMaps.packValues(target_stage_mask),
        VulkanLWJGLMemoryBarriers.packList(stack, memory_barriers),
        VulkanLWJGLBufferMemoryBarriers.packList(stack, buffer_memory_barriers),
        VulkanLWJGLImageMemoryBarriers.packList(stack, image_memory_barriers));
    }
  }

  /**
   * @return The raw handle
   */

  public VkCommandBuffer handle()
  {
    return this.handle;
  }
}
