/*
 * Copyright © 2026 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

package com.io7m.jcoronado.fake;

import com.io7m.jcoronado.api.VulkanFenceType;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A fake fence.
 */

public final class VFakeFence implements VulkanFenceType
{
  private final AtomicBoolean closed;
  private final AtomicBoolean signalled;

  VFakeFence(
    final boolean signalledStart)
  {
    this.closed =
      new AtomicBoolean(false);
    this.signalled =
      new AtomicBoolean(signalledStart);
  }

  /**
   * Signal the fence.
   */

  public void signal()
  {
    this.signalled.set(true);
  }

  /**
   * Reset the fence.
   */

  public void reset()
  {
    this.signalled.set(false);
  }

  /**
   * Wait for the fence to be signalled.
   *
   * @param timeout The timeout
   * @param unit    The time unit
   *
   * @throws InterruptedException On interruption
   * @throws TimeoutException     On timeouts
   */

  public void waitFor(
    final long timeout,
    final TimeUnit unit)
    throws InterruptedException, TimeoutException
  {
    var timeNow = Instant.now();
    final var timeEnd = timeNow.plusNanos(unit.toNanos(timeout));

    while (true) {
      if (this.signalled.get()) {
        return;
      }
      if (timeNow.isAfter(timeEnd)) {
        throw new TimeoutException("Timed out waiting for fence.");
      }
      Thread.sleep(16L);
      timeNow = Instant.now();
    }
  }

  /**
   * @return {@code true} if this fence is signalled
   */

  public boolean isSignalled()
  {
    return this.signalled.get();
  }

  @Override
  public void close()
  {
    if (this.closed.compareAndSet(false, true)) {
      this.signal();
    }
  }

  @Override
  public boolean isClosed()
  {
    return this.closed.get();
  }

  private static CompletableFuture[] futureOfFences(
    final ExecutorService executor,
    final List<VFakeFence> fences,
    final long timeout,
    final TimeUnit unit)
  {
    Objects.requireNonNull(fences, "Fences");
    Objects.requireNonNull(unit, "Unit");

    final var futures = new CompletableFuture[fences.size()];
    for (var index = 0; index < fences.size(); ++index) {
      final var fence = fences.get(index);
      final var future = new CompletableFuture<Void>();
      futures[index] = future;
      executor.execute(() -> {
        try {
          fence.waitFor(timeout, unit);
          future.complete(null);
        } catch (final Throwable e) {
          future.completeExceptionally(e);
        }
      });
    }
    return futures;
  }

  /**
   * Await all fences.
   *
   * @param fences  The fences
   * @param timeout The timeout
   * @param unit    The time unit
   *
   * @throws InterruptedException On interruption
   * @throws TimeoutException     On timeouts
   */

  public static void awaitAll(
    final List<VFakeFence> fences,
    final long timeout,
    final TimeUnit unit)
    throws InterruptedException, TimeoutException
  {
    Objects.requireNonNull(fences, "Fences");
    Objects.requireNonNull(unit, "Unit");

    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      try {
        CompletableFuture.allOf(futureOfFences(executor, fences, timeout, unit))
          .get(timeout, unit);
      } catch (final ExecutionException e) {
        switch (e.getCause()) {
          case final TimeoutException x -> throw x;
          case final InterruptedException x -> throw x;
          default -> throw new IllegalStateException(e);
        }
      }
    }
  }

  /**
   * Await any fences.
   *
   * @param fences  The fences
   * @param timeout The timeout
   * @param unit    The time unit
   *
   * @return {@code true} if any fences were signalled
   */

  public static boolean awaitAny(
    final List<VFakeFence> fences,
    final long timeout,
    final TimeUnit unit)
  {
    Objects.requireNonNull(fences, "Fences");
    Objects.requireNonNull(unit, "Unit");

    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      try {
        CompletableFuture.anyOf(futureOfFences(executor, fences, timeout, unit))
          .get(timeout, unit);
        return true;
      } catch (final ExecutionException | TimeoutException e) {
        return false;
      } catch (final InterruptedException e) {
        Thread.currentThread().interrupt();
        return false;
      }
    }
  }
}
