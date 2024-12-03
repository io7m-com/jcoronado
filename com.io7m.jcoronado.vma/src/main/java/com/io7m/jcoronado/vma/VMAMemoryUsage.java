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

package com.io7m.jcoronado.vma;

import com.io7m.jcoronado.api.VulkanEnumIntegerType;

/**
 * @see "VmaMemoryUsage"
 */

public enum VMAMemoryUsage implements VulkanEnumIntegerType
{
  /**
   * No intended memory usage specified. Use other members of VmaAllocationCreateInfo to specify
   * your requirements.
   */

  VMA_MEMORY_USAGE_UNKNOWN(0),

  /**
   * Memory will be used on device only, so fast access from the device is preferred. It usually
   * means device-local GPU (video) memory. No need to be mappable on host. It is roughly equivalent
   * of `D3D12_HEAP_TYPE_DEFAULT`.
   *
   * Usage:
   *
   * - Resources written and read by device, e.g. images used as attachments. - Resources
   * transferred from host once (immutable) or infrequently and read by device multiple times, e.g.
   * textures to be sampled, vertex buffers, uniform (constant) buffers, and majority of other types
   * of resources used by device.
   *
   * Allocation may still end up in `HOST_VISIBLE` memory on some implementations. In such case, you
   * are free to map it. You can use #VMA_ALLOCATION_CREATE_MAPPED_BIT with this usage type.
   */

  VMA_MEMORY_USAGE_GPU_ONLY(1),

  /**
   * Memory will be mappable on host. It usually means CPU (system) memory. Resources created in
   * this pool may still be accessible to the device, but access to them can be slower. Guarantees
   * to be `HOST_VISIBLE` and `HOST_COHERENT`. CPU read may be uncached. It is roughly equivalent of
   * `D3D12_HEAP_TYPE_UPLOAD`.
   *
   * Usage: Staging copy of resources used as transfer source.
   */

  VMA_MEMORY_USAGE_CPU_ONLY(2),

  /**
   * Memory that is both mappable on host (guarantees to be `HOST_VISIBLE`) and preferably fast to
   * access by GPU. CPU reads may be uncached and very slow.
   *
   * Usage: Resources written frequently by host (dynamic), read by device. E.g. textures, vertex
   * buffers, uniform buffers updated every frame or every draw call.
   */

  VMA_MEMORY_USAGE_CPU_TO_GPU(3),

  /**
   * Memory mappable on host (guarantees to be `HOST_VISIBLE`) and cached. It is roughly equivalent
   * of `D3D12_HEAP_TYPE_READBACK`.
   *
   * Usage:
   *
   * - Resources written by device, read by host - results of some computations, e.g. screen
   * capture, average scene luminance for HDR tone mapping. - Any resources read or accessed
   * randomly on host, e.g. CPU-side copy of vertex buffer used as source of transfer, but also used
   * for collision detection.
   */

  VMA_MEMORY_USAGE_GPU_TO_CPU(4);

  private final int value;

  VMAMemoryUsage(
    final int i)
  {
    this.value = i;
  }

  @Override
  public int value()
  {
    return this.value;
  }
}
