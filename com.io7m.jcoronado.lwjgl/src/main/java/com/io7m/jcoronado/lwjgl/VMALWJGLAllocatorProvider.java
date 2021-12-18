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

import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.vma.VMAAllocatorCreateInfo;
import com.io7m.jcoronado.vma.VMAAllocatorProviderType;
import com.io7m.jcoronado.vma.VMAAllocatorType;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.vma.Vma;
import org.lwjgl.util.vma.VmaAllocatorCreateInfo;
import org.lwjgl.util.vma.VmaVulkanFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * A LWJGL-based allocator provider.
 */

public final class VMALWJGLAllocatorProvider implements VMAAllocatorProviderType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VMALWJGLAllocatorProvider.class);

  private final MemoryStack initial_stack;

  private VMALWJGLAllocatorProvider(
    final MemoryStack in_stack)
  {
    this.initial_stack = Objects.requireNonNull(in_stack, "stack");
  }

  /**
   * @return A new allocator provider
   */

  public static VMAAllocatorProviderType create()
  {
    return new VMALWJGLAllocatorProvider(MemoryStack.create());
  }

  @Override
  public String providerName()
  {
    return "com.io7m.jcoronado.lwjgl";
  }

  @Override
  public String providerVersion()
  {
    final var pack = this.getClass().getPackage();
    final var version = pack.getImplementationVersion();
    return version == null ? "0.0.0" : version;
  }

  @Override
  public VMAAllocatorType createAllocator(
    final VMAAllocatorCreateInfo info)
    throws VulkanException
  {
    Objects.requireNonNull(info, "info");

    final var device =
      VulkanLWJGLClassChecks.checkInstanceOf(
        info.logicalDevice(),
        VulkanLWJGLLogicalDevice.class
      );

    final var physicalDevice =
      VulkanLWJGLClassChecks.checkInstanceOf(
        device.physicalDevice(),
        VulkanLWJGLPhysicalDevice.class
      );

    final var instance =
      VulkanLWJGLClassChecks.checkInstanceOf(
        physicalDevice.instance(),
        VulkanLWJGLInstance.class
      );

    try (var stack = this.initial_stack.push()) {
      final var functions =
        VmaVulkanFunctions.malloc(stack);
      final var vkInstance =
        instance.instance();
      final var vkDevice =
        device.device();
      functions.set(vkInstance, vkDevice);

      final var buffer =
        stack.mallocPointer(1);
      final var cinfo =
        VmaAllocatorCreateInfo.malloc(stack);

      cinfo.set(
        0,
        physicalDevice.device(),
        vkDevice,
        info.preferredLargeHeapBlockSize()
          .orElse(0L),
        null,
        null,
        info.frameInUseCount()
          .orElse(0),
        null,
        null,
        null,
        vkInstance,
        0,
        null
      );

      VulkanChecks.checkReturnCode(
        Vma.vmaCreateAllocator(cinfo, buffer),
        "vmaCreateAllocator"
      );

      return new VMALWJGLAllocator(
        device,
        buffer.get(0),
        device.hostAllocatorProxy()
      );
    }
  }
}
