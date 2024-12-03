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
 * Type-safe Vulkan frontend (Documentation)
 */

module com.io7m.jcoronado.documentation
{
  exports com.io7m.jcoronado.documentation;

  requires static com.io7m.immutables.style;
  requires static org.immutables.value;

  requires com.io7m.jcoronado.vma;
  requires com.io7m.jcoronado.api;
  requires com.io7m.jcoronado.extensions.khr.surface.api;
  requires com.io7m.jcoronado.allocation.tracker;
  requires com.io7m.jcoronado.lwjgl;
}
