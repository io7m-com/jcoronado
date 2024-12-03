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

import com.io7m.jcoronado.api.VulkanDescriptorSetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LWJGL {@code VkDescriptorSet}
 */

public final class VulkanLWJGLDescriptorSet
  extends VulkanLWJGLHandle implements VulkanDescriptorSetType
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLDescriptorSet.class);

  VulkanLWJGLDescriptorSet(
    final long inHandle,
    final VulkanLWJGLHostAllocatorProxy inHostAllocatorProxy)
  {
    super(Ownership.USER_OWNED, inHostAllocatorProxy, inHandle);
  }

  @Override
  protected Logger logger()
  {
    return LOG;
  }

  @Override
  protected void closeActual()
  {

  }
}