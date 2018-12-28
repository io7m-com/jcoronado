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

import com.io7m.jcoronado.api.VulkanBufferCopy;
import com.io7m.jcoronado.api.VulkanBufferImageCopy;
import com.io7m.jcoronado.api.VulkanBufferMemoryBarrier;
import com.io7m.jcoronado.api.VulkanBufferType;
import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanCommandBufferBeginInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanDependencyFlag;
import com.io7m.jcoronado.api.VulkanDescriptorSetType;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExternallySynchronizedType;
import com.io7m.jcoronado.api.VulkanFilter;
import com.io7m.jcoronado.api.VulkanImageBlit;
import com.io7m.jcoronado.api.VulkanImageLayout;
import com.io7m.jcoronado.api.VulkanImageMemoryBarrier;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanIndexType;
import com.io7m.jcoronado.api.VulkanMemoryBarrier;
import com.io7m.jcoronado.api.VulkanPipelineBindPoint;
import com.io7m.jcoronado.api.VulkanPipelineLayoutType;
import com.io7m.jcoronado.api.VulkanPipelineStageFlag;
import com.io7m.jcoronado.api.VulkanPipelineType;
import com.io7m.jcoronado.api.VulkanRenderPassBeginInfo;
import com.io7m.jcoronado.api.VulkanSubpassContents;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.io7m.jcoronado.lwjgl.VulkanLWJGLClassChecks.checkInstanceOf;
import static com.io7m.jcoronado.lwjgl.VulkanLWJGLIntegerArrays.packIntsOrNull;
import static com.io7m.jcoronado.lwjgl.VulkanLWJGLIntegerArrays.packLongs;

/**
 * LWJGL {@link VulkanCommandBufferType}.
 */

public final class VulkanLWJGLCommandBuffer
  extends VulkanLWJGLHandle implements VulkanCommandBufferType
{
  private static final Logger LOG = LoggerFactory.getLogger(VulkanLWJGLCommandBuffer.class);

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

    try (var stack = this.stack_initial.push()) {
      final var packed =
        VulkanLWJGLCommandBufferBeginInfos.pack(stack, info);

      VulkanChecks.checkReturnCode(
        VK10.vkBeginCommandBuffer(this.handle, packed),
        "vkBeginCommandBuffer");
    }
  }

  @Override
  public void beginRenderPass(
    final VulkanRenderPassBeginInfo info,
    final VulkanSubpassContents contents)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");
    Objects.requireNonNull(contents, "contents");

    final var render_pass =
      checkInstanceOf(info.renderPass(), VulkanLWJGLRenderPass.class);
    final var framebuffer =
      checkInstanceOf(info.framebuffer(), VulkanLWJGLFramebuffer.class);

    try (var stack = this.stack_initial.push()) {
      final var packed =
        VulkanLWJGLRenderPassBeginInfos.pack(stack, info, render_pass, framebuffer);

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

    final var pipe =
      checkInstanceOf(pipeline, VulkanLWJGLPipeline.class);

    VK10.vkCmdBindPipeline(this.handle, bind_point.value(), pipe.handle());
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

    try (var stack = this.stack_initial.push()) {
      final var buffers_size = buffers.size();
      final var lbuffers = stack.mallocLong(buffers_size);
      final var offsets_size = offsets.size();
      final var loffsets = stack.mallocLong(offsets_size);

      for (var index = 0; index < buffers_size; ++index) {
        final var cbuffer =
          checkInstanceOf(buffers.get(index), VulkanLWJGLBuffer.class);
        lbuffers.put(index, cbuffer.handle());
      }
      for (var index = 0; index < offsets_size; ++index) {
        loffsets.put(index, offsets.get(index).longValue());
      }

      VK10.vkCmdBindVertexBuffers(this.handle, first_binding, lbuffers, loffsets);
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

    final var cbuffer = checkInstanceOf(buffer, VulkanLWJGLBuffer.class);

    VK10.vkCmdBindIndexBuffer(this.handle, cbuffer.handle(), offset, index_type.value());
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

    final var clayout = checkInstanceOf(layout, VulkanLWJGLPipelineLayout.class);

    try (var stack = this.stack_initial.push()) {
      VK10.vkCmdBindDescriptorSets(
        this.handle,
        pipeline_bind_point.value(),
        clayout.handle(),
        first_set,
        packLongs(
          stack,
          descriptor_sets,
          value -> checkInstanceOf(value, VulkanLWJGLDescriptorSet.class).handle()),
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
  public @VulkanExternallySynchronizedType void copyBuffer(
    final VulkanBufferType source,
    final VulkanBufferType target,
    final List<VulkanBufferCopy> regions)
    throws VulkanException
  {
    Objects.requireNonNull(source, "source");
    Objects.requireNonNull(target, "target");
    Objects.requireNonNull(regions, "regions");

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
  {
    VK10.vkCmdDraw(this.handle, vertex_count, instance_count, first_vertex, first_instance);
  }

  @Override
  public void drawIndexed(
    final int vertex_count,
    final int instance_count,
    final int first_vertex,
    final int vertex_offset,
    final int first_instance)
  {
    VK10.vkCmdDrawIndexed(
      this.handle,
      vertex_count,
      instance_count,
      first_vertex,
      vertex_offset,
      first_instance);
  }

  @Override
  public void endRenderPass()
  {
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
  public void endCommandBuffer()
    throws VulkanException
  {
    VulkanChecks.checkReturnCode(
      VK10.vkEndCommandBuffer(this.handle),
      "vkEndCommandBuffer");
  }

  /**
   * @return The raw handle
   */

  public VkCommandBuffer handle()
  {
    return this.handle;
  }
}
