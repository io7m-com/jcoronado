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

package com.io7m.jcoronado.examples;

import com.io7m.jcoronado.utility.allocation_tracker.VulkanHostAllocatorTracker;
import com.io7m.jcoronado.api.VulkanApplicationInfo;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanResourceException;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLHostAllocatorJeMalloc;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import com.io7m.jmulticlose.core.CloseableCollection;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Supplier;

public final class Versioning implements ExampleType
{
  private static final Logger LOG = LoggerFactory.getLogger(Versioning.class);

  private static final GLFWErrorCallback GLFW_ERROR_CALLBACK =
    GLFWErrorCallback.createPrint();

  public Versioning()
  {

  }

  @Override
  public void execute()
    throws Exception
  {
    GLFW_ERROR_CALLBACK.set();
    GLFW.glfwInit();

    final var hostAllocatorMain =
      new VulkanLWJGLHostAllocatorJeMalloc();
    final var hostAllocator =
      new VulkanHostAllocatorTracker(hostAllocatorMain);

    final Supplier<VulkanException> exceptionSupplier =
      () -> new VulkanResourceException("Could not close one or more resources.");

    try (var resources = CloseableCollection.create(exceptionSupplier)) {

      /*
       * Create an instance provider.
       */

      final var instances =
        VulkanLWJGLInstanceProvider.create();

      LOG.debug(
        "instance provider: {} {}",
        instances.providerName(),
        instances.providerVersion());

      final var supported = instances.findSupportedInstanceVersion();
      LOG.debug("supported instance version is: {}", supported.toHumanString());

      /*
       * Create a new instance.
       */

      final var createInfo =
        VulkanInstanceCreateInfo.builder()
          .setApplicationInfo(
            VulkanApplicationInfo.builder()
              .setApplicationName("com.io7m.jcoronado.tests.Demo")
              .setApplicationVersion(VulkanVersions.encode(0, 0, 1))
              .setEngineName("com.io7m.jcoronado.tests")
              .setEngineVersion(VulkanVersions.encode(0, 0, 1))
              .setVulkanAPIVersion(VulkanVersions.encode(supported))
              .build())
          .build();

      final var instance =
        resources.add(instances.createInstance(
          createInfo,
          Optional.of(hostAllocator)));

    } catch (final VulkanException e) {
      LOG.error("vulkan error: ", e);
      throw e;
    } finally {
      GLFW_ERROR_CALLBACK.close();
    }
  }

}
