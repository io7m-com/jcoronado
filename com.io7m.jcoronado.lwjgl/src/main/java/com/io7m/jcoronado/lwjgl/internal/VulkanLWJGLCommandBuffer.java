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

package com.io7m.jcoronado.lwjgl.internal;

import com.io7m.jcoronado.api.VulkanBlendConstants;
import com.io7m.jcoronado.api.VulkanBufferCopy;
import com.io7m.jcoronado.api.VulkanBufferImageCopy;
import com.io7m.jcoronado.api.VulkanBufferType;
import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanClearAttachment;
import com.io7m.jcoronado.api.VulkanClearRectangle;
import com.io7m.jcoronado.api.VulkanClearValueDepthStencil;
import com.io7m.jcoronado.api.VulkanCommandBufferBeginInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferResetFlag;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanDependencyInfo;
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
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK13;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkDependencyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanClearValueType.VulkanClearValueColorType;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLClassChecks.checkInstanceOf;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLScalarArrays.packIntsOrNull;
import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLScalarArrays.packLongs;

/**
 * LWJGL {@link VulkanCommandBufferType}.
 */

public final class VulkanLWJGLCommandBuffer
  extends VulkanLWJGLHandle
  implements VulkanCommandBufferType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLCommandBuffer.class);

  private final MemoryStack stackInitial;
  private final VkCommandBuffer buffer;

  VulkanLWJGLCommandBuffer(
    final Ownership ownership,
    final VkCommandBuffer inHandle,
    final VulkanLWJGLHostAllocatorProxy inHostAllocatorProxy)
  {
    super(ownership, inHostAllocatorProxy, inHandle.address());
    this.stackInitial = MemoryStack.create();
    this.buffer = Objects.requireNonNull(inHandle, "inHandle");
  }

  VkCommandBuffer buffer()
  {
    return this.buffer;
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

    try (var stack = this.stackInitial.push()) {
      final var packed =
        VulkanLWJGLCommandBufferBeginInfos.pack(stack, info);

      VulkanChecks.checkReturnCode(
        VK10.vkBeginCommandBuffer(this.buffer, packed),
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
      this.buffer,
      checkInstanceOf(pool, VulkanLWJGLQueryPool.class).handle(),
      query,
      VulkanEnumMaps.packValues(flags)
    );
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
      this.buffer,
      checkInstanceOf(pool, VulkanLWJGLQueryPool.class).handle(),
      query
    );
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

    try (var stack = this.stackInitial.push()) {
      final var packed =
        VulkanLWJGLRenderPassBeginInfos.pack(
          stack,
          info,
          render_pass,
          framebuffer);

      VK10.vkCmdBeginRenderPass(this.buffer, packed, contents.value());
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
      this.buffer,
      bind_point.value(),
      checkInstanceOf(pipeline, VulkanLWJGLPipeline.class).handle()
    );
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

    try (var stack = this.stackInitial.push()) {
      VK10.vkCmdBindVertexBuffers(
        this.buffer,
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
    final VulkanBufferType indexBuffer,
    final long offset,
    final VulkanIndexType index_type)
    throws VulkanException
  {
    Objects.requireNonNull(indexBuffer, "buffer");
    Objects.requireNonNull(index_type, "index_type");

    this.checkNotClosed();

    VK10.vkCmdBindIndexBuffer(
      this.buffer,
      checkInstanceOf(indexBuffer, VulkanLWJGLBuffer.class).handle(),
      offset,
      index_type.value()
    );
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

    try (var stack = this.stackInitial.push()) {
      VK10.vkCmdBindDescriptorSets(
        this.buffer,
        pipeline_bind_point.value(),
        clayout.handle(),
        first_set,
        packLongs(
          stack,
          descriptor_sets,
          value -> checkInstanceOf(
            value,
            VulkanLWJGLDescriptorSet.class).handle()),
        packIntsOrNull(stack, dynamic_offsets, Integer::intValue)
      );
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

    try (var stack = this.stackInitial.push()) {
      VK10.vkCmdBlitImage(
        this.buffer,
        csource.handle(),
        source_image_layout.value(),
        ctarget.handle(),
        target_image_layout.value(),
        VulkanLWJGLImageBlits.packList(stack, regions),
        filter.value()
      );
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

    try (var stack = this.stackInitial.push()) {
      VK10.vkCmdCopyImage(
        this.buffer,
        csource.handle(),
        source_image_layout.value(),
        ctarget.handle(),
        target_image_layout.value(),
        VulkanLWJGLImageCopies.packList(stack, regions)
      );
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

    try (var stack = this.stackInitial.push()) {
      VK10.vkCmdClearAttachments(
        this.buffer,
        VulkanLWJGLClearAttachments.packList(stack, attachments),
        VulkanLWJGLClearRectangles.packList(stack, rectangles)
      );
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

    try (var stack = this.stackInitial.push()) {
      VK10.vkCmdClearColorImage(
        this.buffer,
        cimage.handle(),
        image_layout.value(),
        VulkanLWJGLClearValues.packColor(stack, color),
        VulkanLWJGLImageSubresourceRanges.packList(stack, ranges)
      );
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

    try (var stack = this.stackInitial.push()) {
      VK10.vkCmdClearDepthStencilImage(
        this.buffer,
        cimage.handle(),
        image_layout.value(),
        VulkanLWJGLClearValues.packDepthStencil(stack, depth_stencil),
        VulkanLWJGLImageSubresourceRanges.packList(stack, ranges)
      );
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void dispatch(
    final int group_count_x,
    final int group_count_y,
    final int group_count_z)
  {
    VK10.vkCmdDispatch(
      this.buffer,
      group_count_x,
      group_count_y,
      group_count_z
    );
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

    try (var stack = this.stackInitial.push()) {
      VK10.vkCmdCopyBuffer(
        this.buffer,
        csource.handle(),
        ctarget.handle(),
        VulkanLWJGLBufferCopy.packList(stack, regions)
      );
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

    try (var stack = this.stackInitial.push()) {
      VK10.vkCmdCopyImageToBuffer(
        this.buffer,
        cimage.handle(),
        source_layout.value(),
        cbuffer.handle(),
        VulkanLWJGLBufferImageCopy.packList(stack, regions)
      );
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

    try (var stack = this.stackInitial.push()) {
      VK10.vkCmdCopyBufferToImage(
        this.buffer,
        cbuffer.handle(),
        cimage.handle(),
        target_image_layout.value(),
        VulkanLWJGLBufferImageCopy.packList(stack, regions)
      );
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
      this.buffer,
      vertex_count,
      instance_count,
      first_vertex,
      first_instance
    );
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
      this.buffer,
      vertex_count,
      instance_count,
      first_vertex,
      vertex_offset,
      first_instance
    );
  }

  @Override
  public @VulkanExternallySynchronizedType void drawIndirect(
    final VulkanBufferType indirectBuffer,
    final long offset,
    final int draw_count,
    final int stride)
    throws VulkanException
  {
    Objects.requireNonNull(indirectBuffer, "buffer");

    this.checkNotClosed();

    VK10.vkCmdDrawIndirect(
      this.buffer,
      checkInstanceOf(indirectBuffer, VulkanLWJGLBuffer.class).handle(),
      offset,
      draw_count,
      stride
    );
  }

  @Override
  public @VulkanExternallySynchronizedType void drawIndexedIndirect(
    final VulkanBufferType indirectBuffer,
    final long offset,
    final int draw_count,
    final int stride)
    throws VulkanException
  {
    Objects.requireNonNull(indirectBuffer, "buffer");

    this.checkNotClosed();

    VK10.vkCmdDrawIndexedIndirect(
      this.buffer,
      checkInstanceOf(indirectBuffer, VulkanLWJGLBuffer.class).handle(),
      offset,
      draw_count,
      stride
    );
  }

  @Override
  public void executeCommands(
    final List<VulkanCommandBufferType> commandBuffers)
    throws VulkanException
  {
    Objects.requireNonNull(commandBuffers, "commandBuffers");

    this.checkNotClosed();

    try (var stack = this.stackInitial.push()) {
      final var pointers =
        stack.mallocPointer(commandBuffers.size());

      for (int index = 0; index < commandBuffers.size(); ++index) {
        final var secondary =
          checkInstanceOf(
            commandBuffers.get(index),
            VulkanLWJGLCommandBuffer.class
          );
        pointers.put(index, secondary.buffer.address());
      }

      VK10.vkCmdExecuteCommands(
        this.buffer,
        pointers
      );
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void fillBuffer(
    final VulkanBufferType outputBuffer,
    final long offset,
    final long size,
    final int data)
    throws VulkanException
  {
    Objects.requireNonNull(outputBuffer, "buffer");

    this.checkNotClosed();

    VK10.vkCmdFillBuffer(
      this.buffer,
      checkInstanceOf(outputBuffer, VulkanLWJGLBuffer.class).handle(),
      offset,
      size,
      data
    );
  }

  @Override
  public void endRenderPass()
    throws VulkanDestroyedException
  {
    this.checkNotClosed();
    VK10.vkCmdEndRenderPass(this.buffer);
  }

  @Override
  public @VulkanExternallySynchronizedType void pipelineBarrier(
    final VulkanDependencyInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    try (var stack = this.stackInitial.push()) {
      VK13.vkCmdPipelineBarrier2(
        this.buffer,
        VulkanLWJGLDependencyInfos.pack(stack, info)
      );
    }
  }

  @Override
  public @VulkanExternallySynchronizedType void nextSubpass(
    final VulkanSubpassContents contents)
    throws VulkanException
  {
    Objects.requireNonNull(contents, "contents");

    this.checkNotClosed();

    VK10.vkCmdNextSubpass(this.buffer, contents.value());
  }

  @Override
  public @VulkanExternallySynchronizedType void setLineWidth(
    final float width)
    throws VulkanException
  {
    this.checkNotClosed();

    VK10.vkCmdSetLineWidth(this.buffer, width);
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
      this.buffer,
      depth_bias_constant_factor,
      depth_bias_clamp,
      depth_bias_slope_factor
    );
  }

  @Override
  public @VulkanExternallySynchronizedType void setDepthBounds(
    final float min_depth_bounds,
    final float max_depth_bounds)
    throws VulkanException
  {
    this.checkNotClosed();

    VK10.vkCmdSetDepthBounds(this.buffer, min_depth_bounds, max_depth_bounds);
  }

  @Override
  public @VulkanExternallySynchronizedType void setBlendConstants(
    final VulkanBlendConstants constants)
    throws VulkanException
  {
    Objects.requireNonNull(constants, "constants");

    this.checkNotClosed();

    VK10.vkCmdSetBlendConstants(this.buffer, new float[]{
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
      this.buffer,
      VulkanEnumMaps.packValues(face_mask),
      reference
    );
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
      this.buffer,
      VulkanEnumMaps.packValues(face_mask),
      mask
    );
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
      this.buffer,
      VulkanEnumMaps.packValues(face_mask),
      mask
    );
  }

  @Override
  public @VulkanExternallySynchronizedType void setScissor(
    final int first_scissor,
    final List<VulkanRectangle2D> rectangles)
    throws VulkanException
  {
    Objects.requireNonNull(rectangles, "rectangles");

    this.checkNotClosed();

    try (var stack = this.stackInitial.push()) {
      VK10.vkCmdSetScissor(
        this.buffer,
        first_scissor,
        VulkanLWJGLRect2Ds.packList(stack, rectangles)
      );
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

    try (var stack = this.stackInitial.push()) {
      VK10.vkCmdSetViewport(
        this.buffer,
        first_viewport,
        VulkanLWJGLViewports.packList(stack, viewports)
      );
    }
  }

  @Override
  public void setEvent(
    final VulkanEventType event,
    final VulkanDependencyInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(event, "event");
    Objects.requireNonNull(info, "info");

    this.checkNotClosed();

    try (var stack = this.stackInitial.push()) {
      VK13.vkCmdSetEvent2(
        this.buffer,
        checkInstanceOf(event, VulkanLWJGLEvent.class).handle(),
        VulkanLWJGLDependencyInfos.pack(stack, info)
      );
    }
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

    VK13.vkCmdResetEvent2(
      this.buffer,
      checkInstanceOf(event, VulkanLWJGLEvent.class).handle(),
      VulkanEnumMaps.packValuesLong(mask)
    );
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
      this.buffer,
      checkInstanceOf(pool, VulkanLWJGLQueryPool.class).handle(),
      first_query,
      query_count
    );
  }

  @Override
  public void endCommandBuffer()
    throws VulkanException
  {
    this.checkNotClosed();

    VulkanChecks.checkReturnCode(
      VK10.vkEndCommandBuffer(this.buffer),
      "vkEndCommandBuffer"
    );
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

    VK13.vkCmdWriteTimestamp2(
      this.buffer,
      stage.value(),
      checkInstanceOf(pool, VulkanLWJGLQueryPool.class).handle(),
      query_index
    );
  }

  @Override
  public void waitEvents(
    final List<VulkanEventType> events,
    final List<VulkanDependencyInfo> dependencyInfos)
    throws VulkanException
  {
    Objects.requireNonNull(events, "events");
    Objects.requireNonNull(dependencyInfos, "dependencyInfos");

    this.checkNotClosed();

    try (var stack = this.stackInitial.push()) {
      VK13.vkCmdWaitEvents2(
        this.buffer,
        packLongs(
          stack,
          events,
          v -> checkInstanceOf(v, VulkanLWJGLEvent.class).handle()
        ),
        VulkanLWJGLArrays.pack(
          dependencyInfos,
          VulkanLWJGLDependencyInfos::packInto,
          VkDependencyInfo::calloc,
          stack
        )
      );
    }
  }

  @Override
  public void reset(
    final Set<VulkanCommandBufferResetFlag> flags)
    throws VulkanException
  {
    Objects.requireNonNull(flags, "flags");

    this.checkNotClosed();

    VK10.vkResetCommandBuffer(
      this.buffer,
      VulkanEnumMaps.packValues(flags)
    );
  }
}
