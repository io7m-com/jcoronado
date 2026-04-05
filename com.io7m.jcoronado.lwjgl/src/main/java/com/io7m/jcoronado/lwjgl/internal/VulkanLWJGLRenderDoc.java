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

package com.io7m.jcoronado.lwjgl.internal;

import com.io7m.jcoronado.layers.renderdoc.api.VulkanRenderDocType;
import org.lwjgl.util.renderdoc.RenderDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RenderDoc API.
 */

public final class VulkanLWJGLRenderDoc
  implements VulkanRenderDocType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLRenderDoc.class);

  private VulkanLWJGLRenderDoc()
  {

  }

  /**
   * Create a RenderDoc instance, returning a no-op instance if the RenderDoc
   * debugger is not attached.
   *
   * @return A RenderDoc instance
   */

  public static VulkanRenderDocType createRenderDoc()
  {
    try {
      RenderDoc.create();
    } catch (final Exception e) {
      LOG.debug("RenderDoc debugger is not attached. Returning no-op API.");
      return new RenderDocNoOp();
    }

    try (var stack = VulkanLWJGLMemoryStack.stack()) {
      final var major = stack.mallocInt(1);
      final var minor = stack.mallocInt(1);
      final var patch = stack.mallocInt(1);
      RenderDoc.RENDERDOC_GetAPIVersion(major, minor, patch);
      LOG.debug(
        "RenderDoc API: {}.{}.{}",
        Integer.valueOf(major.get(0)),
        Integer.valueOf(minor.get(0)),
        Integer.valueOf(patch.get(0))
      );
      return new VulkanLWJGLRenderDoc();
    }
  }

  @Override
  public void startFrameCapture()
  {
    RenderDoc.RENDERDOC_StartFrameCapture(0L, 0L);
  }

  @Override
  public void endFrameCapture()
  {
    RenderDoc.RENDERDOC_EndFrameCapture(0L, 0L);
  }

  @Override
  public boolean isNoOp()
  {
    return false;
  }

  private static final class RenderDocNoOp
    implements VulkanRenderDocType
  {
    RenderDocNoOp()
    {

    }

    @Override
    public void startFrameCapture()
    {

    }

    @Override
    public void endFrameCapture()
    {

    }

    @Override
    public boolean isNoOp()
    {
      return true;
    }
  }
}
