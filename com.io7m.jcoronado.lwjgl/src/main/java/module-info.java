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

/**
 * Type-safe Vulkan frontend (LWJGL implementation)
 */

module com.io7m.jcoronado.lwjgl
{
  requires static org.osgi.annotation.bundle;
  requires static org.osgi.annotation.versioning;

  requires com.io7m.jcoronado.api;
  requires com.io7m.jcoronado.extensions.ext_debug_utils.api;
  requires com.io7m.jcoronado.extensions.ext_layer_settings.api;
  requires com.io7m.jcoronado.extensions.khr.surface.api;
  requires com.io7m.jcoronado.vma;

  requires com.io7m.junreachable.core;
  requires org.slf4j;
  requires org.lwjgl;
  requires org.lwjgl.jemalloc;
  requires org.lwjgl.vulkan;
  requires org.lwjgl.vma;
  requires org.lwjgl.glfw;

  exports com.io7m.jcoronado.lwjgl;

  exports com.io7m.jcoronado.lwjgl.internal
    to com.io7m.jcoronado.tests;
}
