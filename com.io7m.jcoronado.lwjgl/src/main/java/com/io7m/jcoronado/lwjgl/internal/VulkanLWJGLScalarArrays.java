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
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.List;
import java.util.Objects;

/**
 * Functions to pack integer arrays.
 */

public final class VulkanLWJGLScalarArrays
{
  private VulkanLWJGLScalarArrays()
  {

  }

  /**
   * Pack a list of long values.
   *
   * @param stack  The stack
   * @param values The input list
   * @param getter A function from {@code T} to {@code long}
   * @param <T>    The type of input values
   *
   * @return A packed list
   *
   * @throws VulkanException If required
   */

  public static <T> LongBuffer packLongs(
    final MemoryStack stack,
    final List<T> values,
    final LongGetterType<T> getter)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(values, "values");
    Objects.requireNonNull(getter, "getter");

    final var size = values.size();
    final var buffer = stack.mallocLong(size);
    for (var index = 0; index < size; ++index) {
      final var value = values.get(index);
      buffer.put(index, getter.get(value));
    }
    return buffer;
  }

  /**
   * Pack a list of long values.
   *
   * @param stack  The stack
   * @param values The input list
   * @param getter A function from {@code T} to {@code long}
   * @param <T>    The type of input values
   *
   * @return A packed list
   *
   * @throws VulkanException If required
   */

  public static <T> ByteBuffer packLongsAsByteBuffer(
    final MemoryStack stack,
    final List<T> values,
    final LongGetterType<T> getter)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(values, "values");
    Objects.requireNonNull(getter, "getter");

    final var size = values.size();
    final var buffer = stack.malloc(size * 8);
    for (var index = 0; index < size; ++index) {
      final var value = values.get(index);
      buffer.asLongBuffer().put(index, getter.get(value));
    }
    return buffer;
  }

  /**
   * Pack a list of long values. Returns {@code null} if the input list is
   * empty.
   *
   * @param stack  The stack
   * @param values The input list
   * @param getter A function from {@code T} to {@code long}
   * @param <T>    The type of input values
   *
   * @return A packed list
   *
   * @throws VulkanException If required
   */

  public static <T> LongBuffer packLongsOrNull(
    final MemoryStack stack,
    final List<T> values,
    final LongGetterType<T> getter)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(values, "values");
    Objects.requireNonNull(getter, "getter");

    if (values.isEmpty()) {
      return null;
    }
    return packLongs(stack, values, getter);
  }

  /**
   * Pack a list of int values.
   *
   * @param stack  The stack
   * @param values The input list
   * @param getter A function from {@code T} to {@code int}
   * @param <T>    The type of input values
   *
   * @return A packed list
   *
   * @throws VulkanException If required
   */

  public static <T> IntBuffer packInts(
    final MemoryStack stack,
    final List<T> values,
    final IntGetterType<T> getter)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(values, "values");
    Objects.requireNonNull(getter, "getter");

    final var size = values.size();
    final var buffer = stack.mallocInt(size);
    for (var index = 0; index < size; ++index) {
      final var value = values.get(index);
      buffer.put(index, getter.get(value));
    }
    return buffer;
  }

  /**
   * Pack a list of int values.
   *
   * @param stack  The stack
   * @param values The input list
   * @param getter A function from {@code T} to {@code int}
   * @param <T>    The type of input values
   *
   * @return A packed list
   *
   * @throws VulkanException If required
   */

  public static <T> ByteBuffer packIntsAsByteBuffer(
    final MemoryStack stack,
    final List<T> values,
    final IntGetterType<T> getter)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(values, "values");
    Objects.requireNonNull(getter, "getter");

    final var size = values.size();
    final var buffer = stack.malloc(size * 4);
    for (var index = 0; index < size; ++index) {
      final var value = values.get(index);
      buffer.asIntBuffer().put(index, getter.get(value));
    }
    return buffer;
  }

  /**
   * Pack a list of int values. Returns {@code null} if the input list is
   * empty.
   *
   * @param stack  The stack
   * @param values The input list
   * @param getter A function from {@code T} to {@code int}
   * @param <T>    The type of input values
   *
   * @return A packed list
   *
   * @throws VulkanException If required
   */

  public static <T> IntBuffer packIntsOrNull(
    final MemoryStack stack,
    final List<T> values,
    final IntGetterType<T> getter)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(values, "values");
    Objects.requireNonNull(getter, "getter");

    if (values.isEmpty()) {
      return null;
    }
    return packInts(stack, values, getter);
  }

  /**
   * Pack a list of pointer values.
   *
   * @param stack  The stack
   * @param values The input list
   * @param getter A function from {@code T} to {@code long}
   * @param <T>    The type of input values
   *
   * @return A packed list
   *
   * @throws VulkanException If required
   */

  public static <T> PointerBuffer packPointers(
    final MemoryStack stack,
    final List<T> values,
    final LongGetterType<T> getter)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(values, "values");
    Objects.requireNonNull(getter, "getter");

    final var size = values.size();
    final var buffer = stack.mallocPointer(size);
    for (var index = 0; index < size; ++index) {
      final var value = values.get(index);
      buffer.put(index, getter.get(value));
    }
    return buffer;
  }

  /**
   * Pack a list of pointer values. Returns {@code null} if the input list is
   * empty.
   *
   * @param stack  The stack
   * @param values The input list
   * @param getter A function from {@code T} to {@code long}
   * @param <T>    The type of input values
   *
   * @return A packed list
   *
   * @throws VulkanException If required
   */

  public static <T> PointerBuffer packPointersOrNull(
    final MemoryStack stack,
    final List<T> values,
    final LongGetterType<T> getter)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(values, "values");
    Objects.requireNonNull(getter, "getter");

    if (values.isEmpty()) {
      return null;
    }
    return packPointers(stack, values, getter);
  }

  /**
   * Pack a list of float values.
   *
   * @param stack  The stack
   * @param values The input list
   * @param getter A function from {@code T} to {@code float}
   * @param <T>    The type of input values
   *
   * @return A packed list
   *
   * @throws VulkanException If required
   */

  public static <T> ByteBuffer packFloatsAsByteBuffer(
    final MemoryStack stack,
    final List<T> values,
    final FloatGetterType<T> getter)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(values, "values");
    Objects.requireNonNull(getter, "getter");

    final var size = values.size();
    final var buffer = stack.malloc(size * 4);
    for (var index = 0; index < size; ++index) {
      final var value = values.get(index);
      buffer.asFloatBuffer().put(index, getter.get(value));
    }
    return buffer;
  }

  /**
   * Pack a list of double values.
   *
   * @param stack  The stack
   * @param values The input list
   * @param getter A function from {@code T} to {@code double}
   * @param <T>    The type of input values
   *
   * @return A packed list
   *
   * @throws VulkanException If required
   */

  public static <T> ByteBuffer packDoublesAsByteBuffer(
    final MemoryStack stack,
    final List<T> values,
    final DoubleGetterType<T> getter)
    throws VulkanException
  {
    Objects.requireNonNull(stack, "stack");
    Objects.requireNonNull(values, "values");
    Objects.requireNonNull(getter, "getter");

    final var size = values.size();
    final var buffer = stack.malloc(size * 8);
    for (var index = 0; index < size; ++index) {
      final var value = values.get(index);
      buffer.asDoubleBuffer().put(index, getter.get(value));
    }
    return buffer;
  }

  /**
   * A function from {@code T} to {@code long}
   *
   * @param <T> The type of input values
   */

  public interface LongGetterType<T>
  {
    /**
     * @param value The input value
     *
     * @return A long value from {@code value}
     *
     * @throws VulkanException If required
     */

    long get(T value)
      throws VulkanException;

  }

  /**
   * A function from {@code T} to {@code int}
   *
   * @param <T> The type of input values
   */

  public interface IntGetterType<T>
  {
    /**
     * @param value The input value
     *
     * @return An int value from {@code value}
     *
     * @throws VulkanException If required
     */

    int get(T value)
      throws VulkanException;
  }

  /**
   * A function from {@code T} to {@code float}
   *
   * @param <T> The type of input values
   */

  public interface FloatGetterType<T>
  {
    /**
     * @param value The input value
     *
     * @return A float value from {@code value}
     *
     * @throws VulkanException If required
     */

    float get(T value)
      throws VulkanException;
  }

  /**
   * A function from {@code T} to {@code double}
   *
   * @param <T> The type of input values
   */

  public interface DoubleGetterType<T>
  {
    /**
     * @param value The input value
     *
     * @return A double value from {@code value}
     *
     * @throws VulkanException If required
     */

    double get(T value)
      throws VulkanException;
  }
}
