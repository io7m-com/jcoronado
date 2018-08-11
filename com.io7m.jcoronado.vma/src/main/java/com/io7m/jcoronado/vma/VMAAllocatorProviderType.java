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

import com.io7m.jcoronado.api.VulkanException;

/**
 * Functions to provide VMA allocators.
 */

public interface VMAAllocatorProviderType
{
  /**
   * @return The name of the (software) provider
   */

  String providerName();

  /**
   * @return The version of the (software) provider
   */

  String providerVersion();

  /**
   * Create a new allocator.
   *
   * @param info The creation info
   *
   * @return A new allocator
   *
   * @throws VulkanException On errors
   */

  VMAAllocatorType createAllocator(
    VMAAllocatorCreateInfo info)
    throws VulkanException;
}
