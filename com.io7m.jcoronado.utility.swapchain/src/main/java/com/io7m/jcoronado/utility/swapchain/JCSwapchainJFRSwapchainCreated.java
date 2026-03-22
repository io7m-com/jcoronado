/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
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


package com.io7m.jcoronado.utility.swapchain;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.StackTrace;

/**
 * A swapchain was created.
 */

@Label("SwapchainCreated")
@Category("JCoronado.Swapchain")
@Description("A swapchain was created.")
@StackTrace(value = false)
public final class JCSwapchainJFRSwapchainCreated
  extends Event
  implements JCSwapchainJFREventType
{
  // CHECKSTYLE:OFF

  @Label("ID")
  @Description("The swapchain ID.")
  public String id;

  @Label("ImageCount")
  @Description("The number of images in the swapchain.")
  public int imageCount;

  @Label("ImageFormat")
  @Description("The requested image format.")
  public String format;

  @Label("PresentationMode")
  @Description("The requested presentation mode.")
  public String mode;

  @Label("ImageWidth")
  @Description("The requested image width.")
  public int width;

  @Label("ImageHeight")
  @Description("The requested image height.")
  public int height;

  @Label("MinimumImageCount")
  @Description("The minimum image count.")
  public int minimumImageCount;

  public JCSwapchainJFRSwapchainCreated()
  {

  }
}
