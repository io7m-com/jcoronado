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
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanExtKHRSurfaceType;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanSurfaceCapabilitiesKHR;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanSurfaceFormatKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentModeKHR;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The surface extension.
 */

public final class VFakeExtKHRSurface implements VulkanExtKHRSurfaceType
{
  private static final VulkanExtensionProperties VK_KHR_SURFACE =
    VulkanExtensionProperties.of("VK_KHR_surface", 1);

  private List<VulkanPresentModeKHR> surfacePresentModes;
  private VulkanSurfaceCapabilitiesKHR surfaceCapabilities;
  private List<VulkanSurfaceFormatKHR> surfaceFormats;
  private List<VulkanQueueFamilyProperties> surfaceSupport;

  /**
   * Construct an extension.
   */

  public VFakeExtKHRSurface()
  {
    this.surfacePresentModes = List.of();
    this.surfaceFormats = List.of();
    this.surfaceSupport = List.of();
    this.surfaceCapabilities =
      VulkanSurfaceCapabilitiesKHR.of(
        1,
        1,
        VulkanExtent2D.of(640, 480),
        VulkanExtent2D.of(1, 1),
        VulkanExtent2D.of(640, 480),
        1,
        Set.of(),
        Set.of(),
        Set.of(),
        Set.of()
      );
  }

  /**
   * @return The extension properties
   */

  public static VulkanExtensionProperties properties()
  {
    return VK_KHR_SURFACE;
  }

  /**
   * @param newCaps The new caps
   */

  public void setSurfaceCapabilities(
    final VulkanSurfaceCapabilitiesKHR newCaps)
  {
    this.surfaceCapabilities =
      Objects.requireNonNull(newCaps, "surfaceCapabilities");
  }

  /**
   * @param newFormats The new formats
   */

  public void setSurfaceFormats(
    final List<VulkanSurfaceFormatKHR> newFormats)
  {
    this.surfaceFormats =
      Objects.requireNonNull(newFormats, "surfaceFormats");
  }

  /**
   * @param newModes The new modes
   */

  public void setSurfacePresentModes(
    final List<VulkanPresentModeKHR> newModes)
  {
    this.surfacePresentModes =
      Objects.requireNonNull(newModes, "surfacePresentModes");
  }

  /**
   * @param newSupport The new support
   */

  public void setSurfaceSupport(
    final List<VulkanQueueFamilyProperties> newSupport)
  {
    this.surfaceSupport =
      Objects.requireNonNull(newSupport, "surfaceSupport");
  }

  @Override
  public VulkanKHRSurfaceType surfaceFromWindow(
    final VulkanInstanceType instance,
    final long window)
  {
    return new Surface();
  }

  @Override
  public List<VulkanQueueFamilyProperties> surfaceSupport(
    final VulkanPhysicalDeviceType device,
    final VulkanKHRSurfaceType surface)
  {
    return this.surfaceSupport;
  }

  @Override
  public List<VulkanSurfaceFormatKHR> surfaceFormats(
    final VulkanPhysicalDeviceType device,
    final VulkanKHRSurfaceType surface)
  {
    return this.surfaceFormats;
  }

  @Override
  public VulkanSurfaceCapabilitiesKHR surfaceCapabilities(
    final VulkanPhysicalDeviceType device,
    final VulkanKHRSurfaceType surface)
  {
    return this.surfaceCapabilities;
  }

  @Override
  public List<VulkanPresentModeKHR> surfacePresentModes(
    final VulkanPhysicalDeviceType device,
    final VulkanKHRSurfaceType surface)
  {
    return this.surfacePresentModes;
  }

  private static final class Surface implements VulkanKHRSurfaceType
  {
    Surface()
    {

    }

    @Override
    public void close()
    {

    }

    @Override
    public boolean isClosed()
    {
      return false;
    }
  }
}
