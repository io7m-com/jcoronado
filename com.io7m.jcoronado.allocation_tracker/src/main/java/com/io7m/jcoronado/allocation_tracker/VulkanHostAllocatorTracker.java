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

package com.io7m.jcoronado.allocation_tracker;

import com.io7m.jcoronado.api.VulkanHostAllocatorCallbacks;
import com.io7m.jcoronado.api.VulkanHostAllocatorType;
import com.io7m.jcoronado.api.VulkanInternalAllocation;
import com.io7m.jcoronado.api.VulkanSystemAllocationScope;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * A delegating allocation tracker that logs and tracks allocations.
 */

public final class VulkanHostAllocatorTracker implements VulkanHostAllocatorType
{
  private static final Logger LOG = LoggerFactory.getLogger(VulkanHostAllocatorTracker.class);

  private final VulkanHostAllocatorType delegate;
  private final Long2ReferenceOpenHashMap<VulkanSystemAllocationScope> allocation_scopes;
  private final Long2LongOpenHashMap allocated_command;
  private final Long2LongOpenHashMap allocated_object;
  private final Long2LongOpenHashMap allocated_cache;
  private final Long2LongOpenHashMap allocated_device;
  private final Long2LongOpenHashMap allocated_instance;
  private long allocated_command_size;
  private long allocated_object_size;
  private long allocated_cache_size;
  private long allocated_device_size;
  private long allocated_instance_size;

  /**
   * Create an allocator tracker.
   *
   * @param in_delegate The actual allocator
   */

  public VulkanHostAllocatorTracker(
    final VulkanHostAllocatorType in_delegate)
  {
    this.delegate = Objects.requireNonNull(in_delegate, "delegate");

    this.allocated_command_size = 0L;
    this.allocated_command = new Long2LongOpenHashMap();
    this.allocated_object_size = 0L;
    this.allocated_object = new Long2LongOpenHashMap();
    this.allocated_cache_size = 0L;
    this.allocated_cache = new Long2LongOpenHashMap();
    this.allocated_device_size = 0L;
    this.allocated_device = new Long2LongOpenHashMap();
    this.allocated_instance_size = 0L;
    this.allocated_instance = new Long2LongOpenHashMap();
    this.allocation_scopes = new Long2ReferenceOpenHashMap<>();
  }

  private void rememberAddressAndSize(
    final VulkanSystemAllocationScope scope,
    final long address,
    final long size)
  {
    this.allocation_scopes.put(address, scope);

    switch (scope) {
      case VK_SYSTEM_ALLOCATION_SCOPE_COMMAND:
        this.allocated_command.put(address, size);
        this.allocated_command_size += size;
        break;
      case VK_SYSTEM_ALLOCATION_SCOPE_OBJECT:
        this.allocated_object.put(address, size);
        this.allocated_object_size += size;
        break;
      case VK_SYSTEM_ALLOCATION_SCOPE_CACHE:
        this.allocated_cache.put(address, size);
        this.allocated_cache_size += size;
        break;
      case VK_SYSTEM_ALLOCATION_SCOPE_DEVICE:
        this.allocated_device.put(address, size);
        this.allocated_device_size += size;
        break;
      case VK_SYSTEM_ALLOCATION_SCOPE_INSTANCE:
        this.allocated_instance.put(address, size);
        this.allocated_instance_size += size;
        break;
    }
  }

  private void forgetAddressAndSize(
    final long address,
    final VulkanSystemAllocationScope scope)
  {
    if (scope == null) {
      final var separator = System.lineSeparator();
      throw new IllegalStateException(
        new StringBuilder(64)
          .append("Lost allocation.")
          .append(separator)
          .append("  Address: 0x")
          .append(Long.toUnsignedString(address, 16))
          .append(separator)
          .toString());
    }

    this.allocation_scopes.remove(address);

    switch (scope) {
      case VK_SYSTEM_ALLOCATION_SCOPE_COMMAND: {
        final var size = this.allocated_command.get(address);
        this.allocated_command_size = Math.max(0L, this.allocated_command_size - size);
        this.allocated_command.remove(address);
        break;
      }
      case VK_SYSTEM_ALLOCATION_SCOPE_OBJECT: {
        final var size = this.allocated_object.get(address);
        this.allocated_object_size = Math.max(0L, this.allocated_object_size - size);
        this.allocated_object.remove(address);
        break;
      }
      case VK_SYSTEM_ALLOCATION_SCOPE_CACHE: {
        final var size = this.allocated_cache.get(address);
        this.allocated_cache_size = Math.max(0L, this.allocated_cache_size - size);
        this.allocated_cache.remove(address);
        break;
      }
      case VK_SYSTEM_ALLOCATION_SCOPE_DEVICE: {
        final var size = this.allocated_device.get(address);
        this.allocated_device_size = Math.max(0L, this.allocated_device_size - size);
        this.allocated_device.remove(address);
        break;
      }
      case VK_SYSTEM_ALLOCATION_SCOPE_INSTANCE: {
        final var size = this.allocated_instance.get(address);
        this.allocated_instance_size = Math.max(0L, this.allocated_instance_size - size);
        this.allocated_instance.remove(address);
        break;
      }
    }
  }

  @Override
  public long allocate(
    final long size,
    final long alignment,
    final VulkanSystemAllocationScope scope)
  {
    final var address = this.delegate.allocate(size, alignment, scope);
    if (LOG.isTraceEnabled()) {
      LOG.trace(
        "allocate: {} {} {} 0x{}",
        scope,
        Long.valueOf(size),
        Long.valueOf(alignment),
        Long.toUnsignedString(address, 16));
    }

    this.rememberAddressAndSize(scope, address, size);
    return address;
  }

  @Override
  public long reallocate(
    final long address,
    final long size,
    final long alignment,
    final VulkanSystemAllocationScope scope)
  {
    if (LOG.isTraceEnabled()) {
      LOG.trace(
        "reallocate: {} {} {} 0x{}",
        scope,
        Long.toUnsignedString(address, 16),
        Long.valueOf(size),
        Long.valueOf(alignment));
    }

    this.forgetAddressAndSize(address, scope);
    final var new_address = this.delegate.reallocate(address, size, alignment, scope);
    this.rememberAddressAndSize(scope, new_address, size);
    return new_address;
  }

  @Override
  public void deallocate(
    final long address)
  {
    if (LOG.isTraceEnabled()) {
      LOG.trace("deallocate: 0x{}", Long.toUnsignedString(address, 16));
    }

    this.forgetAddressAndSize(address, this.allocation_scopes.get(address));
    this.delegate.deallocate(address);
  }

  @Override
  public void onAllocation(
    final long size,
    final VulkanInternalAllocation type,
    final VulkanSystemAllocationScope scope)
  {
    if (LOG.isTraceEnabled()) {
      LOG.trace(
        "onAllocation: {} {} {}",
        scope,
        Long.valueOf(size),
        type);
    }

    this.delegate.onAllocation(size, type, scope);
  }

  @Override
  public void onFree(
    final long size,
    final VulkanInternalAllocation type,
    final VulkanSystemAllocationScope scope)
  {
    if (LOG.isTraceEnabled()) {
      LOG.trace(
        "onFree: {} {} {}",
        scope,
        Long.valueOf(size),
        type);
    }

    this.delegate.onFree(size, type, scope);
  }

  @Override
  public VulkanHostAllocatorCallbacks createCallbacks()
  {
    return VulkanHostAllocatorCallbacks.of(
      this, this, this, this, this);
  }

  /**
   * @return The number of octets allocated at command scope
   */

  public long allocatedCommandScopeOctets()
  {
    return this.allocated_command_size;
  }

  /**
   * @return The number of octets allocated at cache scope
   */

  public long allocatedCacheScopeOctets()
  {
    return this.allocated_cache_size;
  }

  /**
   * @return The number of octets allocated at device scope
   */

  public long allocatedDeviceScopeOctets()
  {
    return this.allocated_device_size;
  }

  /**
   * @return The number of octets allocated at instance scope
   */

  public long allocatedInstanceScopeOctets()
  {
    return this.allocated_instance_size;
  }

  /**
   * @return The number of octets allocated at object scope
   */

  public long allocatedObjectScopeOctets()
  {
    return this.allocated_object_size;
  }
}
