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

import com.io7m.jcoronado.api.VulkanException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

import java.util.List;
import java.util.Objects;

/**
 * Functions to pack arrays.
 */

public final class VulkanLWJGLArrays
{
  private VulkanLWJGLArrays()
  {

  }

  /**
   * Pack an array of values.
   *
   * @param values    The input values
   * @param packer    A value packer
   * @param allocator An allocator
   * @param stack     The stack for allocations
   * @param <A>       The type of input values
   * @param <T>       The type of output structs
   * @param <B>       The type of buffers
   *
   * @return An array of packed structs
   *
   * @throws VulkanException On errors
   */

  public static <A, T extends Struct, B extends StructBuffer<T, B>> B pack(
    final List<A> values,
    final PackingFunctionType<A, T> packer,
    final BufferAllocatorType<T, B> allocator,
    final MemoryStack stack)
    throws VulkanException
  {
    Objects.requireNonNull(values, "values");
    Objects.requireNonNull(packer, "packer");
    Objects.requireNonNull(allocator, "allocator");
    Objects.requireNonNull(stack, "stack");

    final var buffer = allocator.allocate(stack, values.size());
    for (var index = 0; index < values.size(); ++index) {
      final var source = values.get(index);
      final var target = buffer.get(index);
      packer.pack(stack, source, target);
    }
    return buffer;
  }

  /**
   * Pack an array of values.
   *
   * @param values    The input values
   * @param packer    A value packer
   * @param allocator An allocator
   * @param stack     The stack for allocations
   * @param <A>       The type of input values
   * @param <T>       The type of output structs
   * @param <B>       The type of buffers
   *
   * @return An array of packed structs
   *
   * @throws VulkanException On errors
   */

  public static <A, T extends Struct, B extends StructBuffer<T, B>> B packOrNull(
    final List<A> values,
    final PackingFunctionType<A, T> packer,
    final BufferAllocatorType<T, B> allocator,
    final MemoryStack stack)
    throws VulkanException
  {
    Objects.requireNonNull(values, "values");
    Objects.requireNonNull(packer, "packer");
    Objects.requireNonNull(allocator, "allocator");
    Objects.requireNonNull(stack, "stack");

    if (values.isEmpty()) {
      return null;
    }

    return pack(values, packer, allocator, stack);
  }

  /**
   * The type of functions that can pack values of type {@code A} into structures of type {@code
   * T}.
   *
   * @param <A> The type of input values
   * @param <T> The type of output structures
   */

  public interface PackingFunctionType<A, T extends Struct>
  {
    /**
     * Pack a value.
     *
     * @param stack  The stack from which to allocate
     * @param value  The source value
     * @param output The output structure
     *
     * @throws VulkanException If required
     */

    void pack(
      MemoryStack stack,
      A value,
      T output)
      throws VulkanException;
  }

  /**
   * The type of functions that can allocate buffers for structures of type {@code T}.
   *
   * @param <T> The type structures
   * @param <B> The type of output buffers
   */

  public interface BufferAllocatorType<T extends Struct, B extends StructBuffer<T, B>>
  {
    /**
     * Allocate a buffer.
     *
     * @param stack The stack from which to allocate
     * @param count The number of elements
     *
     * @return An allocated buffer
     */

    B allocate(
      MemoryStack stack,
      int count);
  }
}
