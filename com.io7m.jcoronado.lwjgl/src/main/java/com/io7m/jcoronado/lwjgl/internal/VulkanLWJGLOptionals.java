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

import com.io7m.jcoronado.api.VulkanException;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Struct;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Functions to pack optionals.
 */

public final class VulkanLWJGLOptionals
{
  private VulkanLWJGLOptionals()
  {

  }

  /**
   * Pack an optional value.
   *
   * @param value     The optional value
   * @param allocator The allocator function
   * @param packer    A value packer
   * @param stack     The stack for allocations
   * @param <A>       The type of input values
   * @param <T>       The type of output structs
   *
   * @return An array of packed structs
   *
   * @throws VulkanException On errors
   */

  public static <A, T extends Struct<T>> T pack(
    final Optional<A> value,
    final PackingFunctionType<A, T> packer,
    final Function<MemoryStack, T> allocator,
    final MemoryStack stack)
    throws VulkanException
  {
    Objects.requireNonNull(value, "values");
    Objects.requireNonNull(packer, "packer");
    Objects.requireNonNull(stack, "stack");

    if (value.isPresent()) {
      final var buffer = allocator.apply(stack);
      packer.pack(stack, value.get(), buffer);
      return buffer;
    }

    return null;
  }

  /**
   * The type of functions that can pack values of type {@code A} into
   * structures of type {@code T}.
   *
   * @param <A> The type of input values
   * @param <T> The type of output structures
   */

  public interface PackingFunctionType<A, T extends Struct<T>>
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
}
