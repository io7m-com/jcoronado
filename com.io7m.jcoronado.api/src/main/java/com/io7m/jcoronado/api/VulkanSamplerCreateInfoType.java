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

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;

/**
 * Structure specifying how to create a sampler.
 *
 * @see "VkSamplerCreateInfo"
 */

@VulkanAPIStructType(vulkanStruct = "VkSamplerCreateInfo")
@ImmutablesStyleType
@Value.Immutable
public interface VulkanSamplerCreateInfoType
{
  /**
   * @return A set of flags describing additional parameters of the sampler.
   */

  @Value.Parameter
  Set<VulkanSamplerCreateFlag> flags();

  /**
   * @return The magnification filter to apply to lookups.
   */

  @Value.Parameter
  VulkanFilter magFilter();

  /**
   * @return The minification filter to apply to lookups.
   */

  @Value.Parameter
  VulkanFilter minFilter();

  /**
   * @return The mipmap filter to apply to lookups.
   */

  @Value.Parameter
  VulkanSamplerMipmapMode mipmapMode();

  /**
   * @return The addressing mode for outside [0..1] range for U coordinates.
   */

  @Value.Parameter
  VulkanSamplerAddressMode addressModeU();

  /**
   * @return The addressing mode for outside [0..1] range for V coordinates.
   */

  @Value.Parameter
  VulkanSamplerAddressMode addressModeV();

  /**
   * @return The addressing mode for outside [0..1] range for W coordinates.
   */

  @Value.Parameter
  VulkanSamplerAddressMode addressModeW();

  /**
   * @return The bias to be added to mipmap LOD (level-of-detail) calculation and bias provided by
   * the image sampling functions in SPIR-V.
   */

  @Value.Parameter
  float mipLodBias();

  /**
   * If this value is non-empty, enable anisotropic filtering using the given amount of anisotropy.
   *
   * @return The anisotropy value clamp used by the sampler
   */

  @VulkanAPIDeviceFeatureRequiredType(featureName = "samplerAnisotropy")
  @Value.Parameter
  OptionalDouble maxAnisotropy();

  /**
   * @return A value specifying the comparison function to apply to fetched data before filtering
   */

  @Value.Parameter
  Optional<VulkanCompareOp> compareOp();

  /**
   * @return The minimum LOD clamp value
   */

  @Value.Parameter
  float minLod();

  /**
   * @return The maximum LOD clamp value
   */

  @Value.Parameter
  float maxLod();

  /**
   * @return The predefined border color to use.
   */

  @Value.Parameter
  VulkanBorderColor borderColor();

  /**
   * @return {@code true} if the sampler coordinates should unnormalized
   */

  @Value.Parameter
  boolean unnormalizedCoordinates();
}
