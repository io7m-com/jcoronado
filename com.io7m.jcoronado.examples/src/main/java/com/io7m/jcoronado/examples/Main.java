/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> http://io7m.com
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

package com.io7m.jcoronado.examples;

import com.io7m.seltzer.api.SStructuredErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public final class Main
{
  private static final Logger LOG =
    LoggerFactory.getLogger(Main.class);

  private static final Map<String, ExampleType> EXAMPLES =
    Map.ofEntries(
      Map.entry("hello-swapchain", new HelloSwapChain()),
      Map.entry("hello", new HelloVulkan()),
      Map.entry("hello-vma", new HelloVulkanWithVMA()),
      Map.entry("framebuffer", new Framebuffer()),
      Map.entry("versioning", new Versioning()),
      Map.entry("buffer-copy", new BufferCopy()),
      Map.entry("memory-requirements", new MemoryRequirements())
    );

  private Main()
  {

  }

  public static void main(
    final String[] args)
    throws Exception
  {
    if (args.length < 1) {
      usage();
    }

    final var exampleClass = EXAMPLES.get(args[0]);
    if (exampleClass == null) {
      usage();
    }

    try {
      exampleClass.execute();
    } catch (final Exception e) {
      if (e instanceof final SStructuredErrorType<?> x) {
        LOG.error("{}: {}: {}", e.getClass(), x.errorCode(), x.message());
        for (final var entry : x.attributes().entrySet()) {
          LOG.error("  {}: {}", entry.getKey(), entry.getValue());
        }
        throw e;
      }
    }
  }

  private static void usage()
  {
    System.out.println("Usage: [example]");
    System.out.println("  Where [example] is one of:");
    for (final var name : EXAMPLES.keySet()) {
      System.out.print("    ");
      System.out.println(name);
    }
    System.exit(1);
  }
}
