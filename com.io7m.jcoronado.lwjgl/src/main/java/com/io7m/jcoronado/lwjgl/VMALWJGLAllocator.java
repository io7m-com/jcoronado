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

import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanBufferType;
import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanDeviceMemoryType;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.vma.VMAAllocationCreateInfo;
import com.io7m.jcoronado.vma.VMAAllocationInfo;
import com.io7m.jcoronado.vma.VMAAllocationInfoType;
import com.io7m.jcoronado.vma.VMAAllocationResult;
import com.io7m.jcoronado.vma.VMAAllocationType;
import com.io7m.jcoronado.vma.VMAAllocatorType;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.vma.Vma;
import org.lwjgl.util.vma.VmaAllocationCreateInfo;
import org.lwjgl.util.vma.VmaAllocationInfo;
import org.lwjgl.vulkan.VkBufferCreateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.LongBuffer;
import java.util.Objects;
import java.util.Optional;

import static com.io7m.jcoronado.lwjgl.VulkanLWJGLHandle.Ownership.USER_OWNED;

/**
 * @see "VmaAllocator"
 */

public final class VMALWJGLAllocator implements VMAAllocatorType
{
  private static final Logger LOG = LoggerFactory.getLogger(VMALWJGLAllocator.class);

  private final VulkanLWJGLLogicalDevice device;
  private final long allocator_address;
  private final MemoryStack stack_initial;
  private final VulkanLWJGLHostAllocatorProxy host_allocator_proxy;

  VMALWJGLAllocator(
    final VulkanLWJGLLogicalDevice in_device,
    final long in_allocator_address,
    final VulkanLWJGLHostAllocatorProxy in_host_allocator_proxy)
  {
    this.device =
      Objects.requireNonNull(in_device, "device");
    this.host_allocator_proxy =
      Objects.requireNonNull(in_host_allocator_proxy, "in_host_allocator_proxy");

    this.allocator_address = in_allocator_address;
    this.stack_initial = MemoryStack.create();
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
    final VMALWJGLAllocator that = (VMALWJGLAllocator) o;
    return this.allocator_address == that.allocator_address
      && Objects.equals(this.device, that.device);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(this.device, Long.valueOf(this.allocator_address));
  }

  @Override
  public String toString()
  {
    return new StringBuilder(32)
      .append("[VMALWJGLAllocator 0x")
      .append(Long.toUnsignedString(this.allocator_address, 16))
      .append(']')
      .toString();
  }

  private enum AllocatedItemKind
  {
    BUFFER
  }

  @Override
  public VMAAllocationResult<VulkanBufferType> createBuffer(
    final VMAAllocationCreateInfo alloc_create_info,
    final VulkanBufferCreateInfo buffer_create_info)
    throws VulkanException
  {
    Objects.requireNonNull(alloc_create_info, "alloc_create_info");
    Objects.requireNonNull(buffer_create_info, "buffer_create_info");

    try (MemoryStack stack = this.stack_initial.push()) {
      final VkBufferCreateInfo vk_buffer_create_info =
        VulkanLWJGLBufferCreateInfos.packInfo(stack, buffer_create_info);

      final VmaAllocationCreateInfo vk_alloc_create_info =
        VmaAllocationCreateInfo.mallocStack(stack)
          .flags(VulkanEnumMaps.packValues(alloc_create_info.flags()))
          .memoryTypeBits((int) alloc_create_info.memoryTypeBits())
          .preferredFlags(VulkanEnumMaps.packValues(alloc_create_info.preferredFlags()))
          .requiredFlags(VulkanEnumMaps.packValues(alloc_create_info.requiredFlags()))
          .pUserData(0L)
          .pool(0L);

      final PointerBuffer vk_allocation =
        stack.mallocPointer(1);
      final VmaAllocationInfo vk_allocation_info =
        VmaAllocationInfo.mallocStack(stack);
      final LongBuffer vk_buffer =
        stack.mallocLong(1);

      VulkanChecks.checkReturnCode(
        Vma.vmaCreateBuffer(
          this.allocator_address,
          vk_buffer_create_info,
          vk_alloc_create_info,
          vk_buffer,
          vk_allocation,
          vk_allocation_info),
        "vmaCreateBuffer");

      final Optional<VulkanDeviceMemoryType> device_memory;
      final long vk_device_memory = vk_allocation_info.deviceMemory();
      if (vk_device_memory != 0L) {
        device_memory =
          Optional.of(new VulkanLWJGLDeviceMemory(
            USER_OWNED, this.device.device(), vk_device_memory, this.host_allocator_proxy));
      } else {
        device_memory = Optional.empty();
      }

      final VMAAllocationInfo info =
        VMAAllocationInfo.builder()
          .setDeviceMemory(device_memory)
          .setMemoryType((long) vk_allocation_info.memoryType())
          .setOffset(vk_allocation_info.offset())
          .setSize(vk_allocation_info.size())
          .build();

      final VulkanLWJGLBuffer buffer =
        new VulkanLWJGLBuffer(
          USER_OWNED,
          this.device.device(),
          vk_buffer.get(0),
          this.host_allocator_proxy);

      final VMALWJGLAllocation<VulkanLWJGLBuffer> allocation =
        new VMALWJGLAllocation<>(
          this,
          vk_allocation.get(0),
          buffer,
          info,
          AllocatedItemKind.BUFFER);

      return VMAAllocationResult.of(allocation, buffer);
    }
  }

  private void destroyBuffer(
    final VMALWJGLAllocation<VulkanLWJGLBuffer> allocation)
  {
    final long buffer_handle = allocation.item.handle();
    final long alloc_handle = allocation.allocation;

    if (LOG.isTraceEnabled()) {
      LOG.trace("destroying buffer and allocation: {}", this);
    }

    Vma.vmaDestroyBuffer(this.allocator_address, buffer_handle, alloc_handle);
  }

  private static final class VMALWJGLAllocation<T> implements VMAAllocationType
  {
    private final VMAAllocationInfo info;
    private final AllocatedItemKind deallocation;
    private final long allocation;
    private final VMALWJGLAllocator allocator;
    private final T item;
    private boolean closed;

    private VMALWJGLAllocation(
      final VMALWJGLAllocator in_allocator,
      final long in_allocation,
      final T in_item,
      final VMAAllocationInfo in_info,
      final AllocatedItemKind in_deallocation)
    {
      this.allocator =
        Objects.requireNonNull(in_allocator, "in_allocator");
      this.info =
        Objects.requireNonNull(in_info, "info");
      this.item =
        Objects.requireNonNull(in_item, "item");
      this.deallocation =
        Objects.requireNonNull(in_deallocation, "in_deallocation");

      this.allocation = in_allocation;
      this.closed = false;
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
      final VMALWJGLAllocation<?> that = (VMALWJGLAllocation<?>) o;
      return Objects.equals(this.info.deviceMemory(), that.info.deviceMemory())
        && (this.info.offset() == that.info.offset());
    }

    @Override
    public int hashCode()
    {
      return Objects.hash(this.info.deviceMemory(), Long.valueOf(this.info.offset()));
    }

    @Override
    public String toString()
    {
      return new StringBuilder(32)
        .append("[VMALWJGLAllocation 0x")
        .append(Long.toUnsignedString(this.allocation, 16))
        .append(' ')
        .append(this.info.deviceMemory())
        .append(' ')
        .append(this.info.offset())
        .append(' ')
        .append(this.info.size())
        .append(']')
        .toString();
    }

    @Override
    public VMAAllocationInfoType info()
    {
      return this.info;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void close()
    {
      if (!this.closed) {
        try {
          switch (this.deallocation) {
            case BUFFER: {
              this.allocator.destroyBuffer((VMALWJGLAllocation<VulkanLWJGLBuffer>) this);
              break;
            }
          }
        } finally {
          this.closed = true;
        }
      }
    }
  }
}
