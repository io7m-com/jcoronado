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


package com.io7m.jcoronado.fake;

import com.io7m.jcoronado.api.VulkanExtensionType;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanVersion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * A fake instance.
 */

public final class VFakeInstance implements VulkanInstanceType
{
  private final VFakeInstances owner;
  private final AtomicBoolean closed;
  private final ArrayList<VFakePhysicalDevice> physicalDevices;
  private Map<String, VulkanExtensionType> extensions;

  /**
   * A fake instance.
   *
   * @param inOwner         The owner
   * @param startExtensions The initial extensions enabled
   */

  public VFakeInstance(
    final VFakeInstances inOwner,
    final Map<String, VulkanExtensionType> startExtensions)
  {
    this.owner =
      Objects.requireNonNull(inOwner, "owner");
    this.closed =
      new AtomicBoolean(false);
    this.extensions = new HashMap<>();
    this.extensions.putAll(startExtensions);

    this.physicalDevices = new ArrayList<>();
    this.physicalDevices.add(new VFakePhysicalDevice(this));
  }

  /**
   * Set the extensions used.
   *
   * @param newExtensions The extensions
   */

  public void setExtensions(
    final Map<String, VulkanExtensionType> newExtensions)
  {
    this.extensions = Objects.requireNonNull(newExtensions, "extensions");
  }

  @Override
  public void close()
  {
    if (this.closed.compareAndSet(false, true)) {
      // Nothing
    }
  }

  @Override
  public boolean isClosed()
  {
    return this.closed.get();
  }

  @Override
  public Stream<VulkanPhysicalDeviceType> enumeratePhysicalDevices()
  {
    return this.physicalDevices.stream()
      .map(x -> x);
  }

  @Override
  public VulkanVersion apiVersionMaximumSupported()
  {
    return this.owner.findSupportedInstanceVersion();
  }

  @Override
  public VulkanVersion apiVersionUsed()
  {
    return this.owner.findSupportedInstanceVersion();
  }

  @Override
  public Map<String, VulkanExtensionType> enabledExtensions()
  {
    return this.extensions;
  }
}
