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

package com.io7m.jcoronado.api;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * An exception raised by the Vulkan version not being supported.
 */

public final class VulkanMissingRequiredVersionException
  extends VulkanException
{
  private final VulkanVersion requested;
  private final VulkanVersion required;
  private final VulkanVersion supported;

  /**
   * An exception raised by the Vulkan version not being supported.
   *
   * @param inRequested The requested version
   * @param inRequired  The required version
   * @param inSupported The supported version
   */

  public VulkanMissingRequiredVersionException(
    final VulkanVersion inRequested,
    final VulkanVersion inRequired,
    final VulkanVersion inSupported)
  {
    super(
      "The current Vulkan implementation does not match the given version constraints.",
      createAttributes(inRequested, inRequired, inSupported),
      "error-vulkan-version-constraint",
      Optional.of(
        "The versions must obey the constraint: required <= requested <= supported."
      )
    );

    this.requested =
      Objects.requireNonNull(inRequested, "inRequested");
    this.required =
      Objects.requireNonNull(inRequired, "inRequired");
    this.supported =
      Objects.requireNonNull(inSupported, "inSupported");
  }

  private static Map<String, String> createAttributes(
    final VulkanVersion inRequested,
    final VulkanVersion inRequired,
    final VulkanVersion inSupported)
  {
    return Map.ofEntries(
      Map.entry("Version (Requested)", inRequested.toHumanString()),
      Map.entry("Version (Required)", inRequired.toHumanString()),
      Map.entry("Version (Supported)", inSupported.toHumanString())
    );
  }

  /**
   * @return The requested version
   */

  public VulkanVersion requested()
  {
    return this.requested;
  }

  /**
   * @return The required version
   */

  public VulkanVersion required()
  {
    return this.required;
  }

  /**
   * @return The supported version
   */

  public VulkanVersion supported()
  {
    return this.supported;
  }
}
