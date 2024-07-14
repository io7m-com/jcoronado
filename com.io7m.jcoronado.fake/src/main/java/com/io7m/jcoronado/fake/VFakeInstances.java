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

import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanHostAllocatorType;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanInstanceProviderType;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanVersion;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A fake instance provider.
 */

public final class VFakeInstances
  implements VulkanInstanceProviderType
{
  private String providerName =
    "com.io7m.jcoronado.fake";
  private String providerVersion =
    "1.0.0";
  private VulkanVersion vulkanVersion =
    VulkanVersion.of(1, 0, 0);

  private Map<String, VulkanExtensionProperties> extensions = Map.of();
  private Map<String, VulkanLayerProperties> layers = Map.of();
  private VFakeInstance nextInstance;

  /**
   * A fake instance provider.
   */

  public VFakeInstances()
  {
    this.nextInstance =
      new VFakeInstance(this, Map.of());
  }

  /**
   * Set the extensions.
   *
   * @param newExtensions The new extensions
   */

  public void setExtensions(
    final Map<String, VulkanExtensionProperties> newExtensions)
  {
    this.extensions =
      Objects.requireNonNull(newExtensions, "extensions");
  }

  /**
   * Set the layers.
   *
   * @param newLayers The new layers
   */

  public void setLayers(
    final Map<String, VulkanLayerProperties> newLayers)
  {
    this.layers =
      Objects.requireNonNull(newLayers, "layers");
  }

  /**
   * @return The next instance that will be returned
   */

  public VulkanInstanceType getNextInstance()
  {
    return this.nextInstance;
  }

  /**
   * Set the next instance that will be returned.
   *
   * @param setNextInstance The next instance
   */

  public void setNextInstance(
    final VFakeInstance setNextInstance)
  {
    this.nextInstance =
      Objects.requireNonNull(setNextInstance, "nextInstance");
  }

  /**
   * Set the provider name.
   *
   * @param newName The name
   */

  public void setProviderName(
    final String newName)
  {
    this.providerName =
      Objects.requireNonNull(newName, "providerName");
  }

  /**
   * Set the provider version.
   *
   * @param newVersion The version
   */

  public void setProviderVersion(
    final String newVersion)
  {
    this.providerVersion =
      Objects.requireNonNull(newVersion, "providerVersion");
  }

  /**
   * Set the Vulkan version.
   *
   * @param newVulkanVersion The version
   */

  public void setVulkanVersion(
    final VulkanVersion newVulkanVersion)
  {
    this.vulkanVersion =
      Objects.requireNonNull(newVulkanVersion, "vulkanVersion");
  }

  @Override
  public String providerName()
  {
    return this.providerName;
  }

  @Override
  public String providerVersion()
  {
    return this.providerVersion;
  }

  @Override
  public VulkanVersion findSupportedInstanceVersion()
  {
    return this.vulkanVersion;
  }

  @Override
  public Map<String, VulkanExtensionProperties> extensions()
  {
    return this.extensions;
  }

  @Override
  public Map<String, VulkanLayerProperties> layers()
  {
    return this.layers;
  }

  @Override
  public VulkanInstanceType createInstance(
    final VulkanInstanceCreateInfo info,
    final Optional<VulkanHostAllocatorType> allocator)
  {
    return this.nextInstance;
  }
}
