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

package com.io7m.jcoronado.extensions.khr_swapchain.api;

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

import java.util.OptionalInt;

/**
 * The result of an attempt to acquire an image from the swap chain.
 */

@ImmutablesStyleType
@Value.Immutable
public interface VulkanSwapChainImageAcquisitionType
{
  /**
   * @return The index of the acquired image in the swapchain
   */

  @Value.Parameter
  OptionalInt imageIndex();

  /**
   * If an image became available, and the swapchain no longer matches the surface properties
   * exactly but can still be used to present to the surface successfully, this method will return
   * {@code true}.
   *
   * @return {@code true} if the image is now suboptimal
   */

  @Value.Parameter
  boolean subOptimal();

  /**
   * @return {@code true} iff a timeout was specified and no image was available within that time
   */

  @Value.Parameter
  boolean timedOut();
}
