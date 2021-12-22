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

package com.io7m.jcoronado.tests.device;

import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanInstanceProviderType;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import com.io7m.jcoronado.tests.contracts.VulkanBufferContract;
import com.io7m.jcoronado.tests.contracts.VulkanInstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class VulkanLWJGLBufferTest extends VulkanBufferContract
{
  private VulkanInstanceProviderType provider;
  private VulkanInstanceType instance;

  private static VulkanInstanceType createInstance(
    final VulkanInstanceProviderType current_provider)
  {
    try {
      return current_provider.createInstance(VulkanInstanceInfo.info(), Optional.empty());
    } catch (final VulkanException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  protected Logger logger()
  {
    return LoggerFactory.getLogger(VulkanLWJGLBufferTest.class);
  }

  @Override
  protected VulkanInstanceType instance()
  {
    return this.instance;
  }

  @Override
  protected VulkanPhysicalDeviceType createPhysicalDevice()
    throws VulkanException
  {
    if (this.provider == null) {
      this.provider = VulkanLWJGLInstanceProvider.create();
    }

    var i = this.instance;
    if (i == null) {
      this.instance = createInstance(this.provider);
      i = this.instance;
    }

    return i.enumeratePhysicalDevices().findFirst().get();
  }

  @Override
  protected VulkanLogicalDeviceType createLogicalDevice(
    final VulkanPhysicalDeviceType device)
    throws VulkanException
  {
    final var queue =
      VulkanLogicalDeviceQueueCreateInfo.builder()
        .setQueueCount(1)
        .setQueueFamilyIndex(device.queueFamilies().get(0).queueFamilyIndex())
        .setQueuePriorities(1.0f)
        .build();

    return device.createLogicalDevice(
      VulkanLogicalDeviceCreateInfo.builder()
        .addQueueCreateInfos(queue)
        .build());
  }
}
