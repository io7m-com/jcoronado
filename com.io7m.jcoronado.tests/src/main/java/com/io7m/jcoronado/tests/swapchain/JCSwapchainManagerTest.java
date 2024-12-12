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


package com.io7m.jcoronado.tests.swapchain;

import com.io7m.jcoronado.api.VulkanDebuggingType;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanFormat;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanImageViewType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyIndex;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanSemaphoreBinaryType;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanExtKHRSurfaceType;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanSurfaceCapabilitiesKHR;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanSurfaceFormatKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanColorSpaceKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanExtKHRSwapChainType;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentModeKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainNotReady;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainOK;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainOutOfDate;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainSubOptimal;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainTimedOut;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainConfiguration;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainImageIndex;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainJFRSwapchainCreated;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainJFRSwapchainCreationFailed;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainJFRSwapchainDeleted;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainJFRSwapchainImageAcquireFailed;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainJFRSwapchainImageAcquired;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainManager;
import com.io7m.jcoronado.utility.swapchain.JCSwapchainManagerType;
import jdk.jfr.Event;
import jdk.jfr.Name;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.io7m.jcoronado.api.VulkanLogicalDeviceType.VulkanFenceStatus.VK_FENCE_SIGNALLED;
import static com.io7m.jcoronado.api.VulkanLogicalDeviceType.VulkanWaitStatus.VK_WAIT_SUCCEEDED;
import static com.io7m.jcoronado.api.VulkanLogicalDeviceType.VulkanWaitStatus.VK_WAIT_TIMED_OUT;
import static com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag.VK_QUEUE_GRAPHICS_BIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class JCSwapchainManagerTest
{
  private static final Logger LOG =
    LoggerFactory.getLogger(JCSwapchainManagerTest.class);

  private VulkanLogicalDeviceType device;
  private JCSwapchainManagerType manager;
  private VulkanExtKHRSurfaceType surfaceExtension;
  private VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface;
  private VulkanQueueType graphicsQueue;
  private VulkanQueueType presentationQueue;
  private VulkanExtKHRSwapChainType swapchainExtension;
  private JCSwapchainConfiguration configuration;
  private VulkanExtKHRSwapChainType.VulkanKHRSwapChainType swapchain;
  private VulkanDebuggingType debugging;
  private RecordingStream jfrStream;
  private ConcurrentLinkedQueue<RecordedEvent> events;
  private ArrayList<VulkanImageType> images;

  /*
   * An event that, when published, will close the JFR stream and allow
   * all events to be flushed into the event log.
   */

  @Name("FlushEvent")
  static final class FlushEvent
    extends Event
  {

  }

  @BeforeEach
  public void setup()
    throws Exception
  {
    this.images =
      new ArrayList<>();

    this.events = new ConcurrentLinkedQueue<>();

    this.jfrStream = new RecordingStream();
    this.jfrStream.onEvent("FlushEvent", _ -> this.jfrStream.close());
    this.jfrStream.enable(JCSwapchainJFRSwapchainCreated.class);
    this.jfrStream.enable(JCSwapchainJFRSwapchainCreationFailed.class);
    this.jfrStream.enable(JCSwapchainJFRSwapchainDeleted.class);
    this.jfrStream.enable(JCSwapchainJFRSwapchainImageAcquired.class);
    this.jfrStream.enable(JCSwapchainJFRSwapchainImageAcquireFailed.class);
    this.jfrStream.onEvent(this::logEvent);
    this.jfrStream.startAsync();

    this.device =
      mock(VulkanLogicalDeviceType.class);
    this.surfaceExtension =
      mock(VulkanExtKHRSurfaceType.class);
    this.surface =
      mock(VulkanExtKHRSurfaceType.VulkanKHRSurfaceType.class);
    this.graphicsQueue =
      mock(VulkanQueueType.class);
    this.presentationQueue =
      mock(VulkanQueueType.class);
    this.swapchainExtension =
      mock(VulkanExtKHRSwapChainType.class);
    this.swapchain =
      mock(VulkanExtKHRSwapChainType.VulkanKHRSwapChainType.class);
    this.debugging =
      mock(VulkanDebuggingType.class);
    when(this.swapchain.images())
      .thenReturn(this.images);

    this.configuration =
      JCSwapchainConfiguration.builder()
        .setDevice(this.device)
        .setGraphicsQueue(this.graphicsQueue)
        .setPresentationQueue(this.presentationQueue)
        .setSurface(this.surface)
        .setSurfaceExtension(this.surfaceExtension)
        .setSwapChainExtension(this.swapchainExtension)
        .build();

    when(this.device.debugging())
      .thenReturn(this.debugging);

    when(this.graphicsQueue.queueFamilyProperties())
      .thenReturn(
        VulkanQueueFamilyProperties.of(
          new VulkanQueueFamilyIndex(1),
          1,
          Set.of(VK_QUEUE_GRAPHICS_BIT),
          32,
          VulkanExtent3D.of(1, 1, 1)
        )
      );

    when(this.presentationQueue.queueFamilyProperties())
      .thenReturn(
        VulkanQueueFamilyProperties.of(
          new VulkanQueueFamilyIndex(1),
          1,
          Set.of(),
          32,
          VulkanExtent3D.of(1, 1, 1)
        )
      );

    when(this.surfaceExtension.surfaceFormats(any(), any()))
      .thenReturn(List.of(
        VulkanSurfaceFormatKHR.of(
          VulkanFormat.VK_FORMAT_R8G8B8_UNORM,
          VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
        )
      ));

    when(this.swapchainExtension.swapChainCreate(any(), any()))
      .thenReturn(this.swapchain);
  }

  private void logEvent(
    final RecordedEvent e)
  {
    LOG.debug("Event: {}", e);
    this.events.add(e);
  }

  @AfterEach
  public void tearDown()
    throws Exception
  {

  }

  @Test
  public void testCreateDelete()
    throws Exception
  {
    final var flush = new FlushEvent();

    try {
      final var image0 =
        mock(VulkanImageType.class);
      final var imageView0 =
        mock(VulkanImageViewType.class);
      final var imageReadySemaphore =
        mock(VulkanSemaphoreBinaryType.class);
      final var renderDoneSemaphore =
        mock(VulkanSemaphoreBinaryType.class);
      final var presentDoneFence =
        mock(VulkanFenceType.class);
      final var renderDoneFence =
        mock(VulkanFenceType.class);

      this.images.add(image0);

      when(this.device.createImageView(any()))
        .thenReturn(imageView0);
      when(this.device.createBinarySemaphore())
        .thenReturn(imageReadySemaphore)
        .thenReturn(renderDoneSemaphore)
        .thenThrow(new IllegalStateException());
      when(this.device.createFence())
        .thenReturn(presentDoneFence);
      when(this.device.createFence(any()))
        .thenReturn(renderDoneFence);
      when(this.device.getFenceStatus(presentDoneFence))
        .thenReturn(VK_FENCE_SIGNALLED);

      when(this.surfaceExtension.surfaceCapabilities(any(), any()))
        .thenReturn(VulkanSurfaceCapabilitiesKHR.of(
          1,
          1,
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          1,
          Set.of(),
          Set.of(),
          Set.of(),
          Set.of()
        ));

      this.manager = JCSwapchainManager.create(this.configuration);
      this.manager.close();

      /*
       * Verify that resources were deleted.
       */

      verify(renderDoneFence, new Times(1))
        .close();
      verify(presentDoneFence, new Times(1))
        .close();
      verify(imageReadySemaphore, new Times(1))
        .close();
      verify(renderDoneSemaphore, new Times(1))
        .close();
      verify(this.swapchain, new Times(1))
        .close();

    } finally {
      flush.commit();
      this.jfrStream.awaitTermination();
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainDeleted.class);
    }
  }

  /**
   * The first supported surface type is used, because we didn't claim to
   * prefer any.
   *
   * @throws Exception On errors
   */

  @Test
  public void testCreateFormatSelectionFallback()
    throws Exception
  {
    final var flush = new FlushEvent();

    try {
      final var image0 =
        mock(VulkanImageType.class);
      final var imageView0 =
        mock(VulkanImageViewType.class);
      final var sem0 =
        mock(VulkanSemaphoreBinaryType.class);
      final var presentDoneFence =
        mock(VulkanFenceType.class);
      final var renderDoneFence =
        mock(VulkanFenceType.class);

      this.images.add(image0);

      when(this.device.createImageView(any()))
        .thenReturn(imageView0);
      when(this.device.createBinarySemaphore())
        .thenReturn(sem0);
      when(this.device.createFence())
        .thenReturn(presentDoneFence);
      when(this.device.createFence(any()))
        .thenReturn(renderDoneFence);
      when(this.device.getFenceStatus(presentDoneFence))
        .thenReturn(VK_FENCE_SIGNALLED);

      when(this.surfaceExtension.surfaceFormats(any(), any()))
        .thenReturn(
          List.of(
            VulkanSurfaceFormatKHR.of(
              VulkanFormat.VK_FORMAT_R8_UNORM,
              VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
            ),
            VulkanSurfaceFormatKHR.of(
              VulkanFormat.VK_FORMAT_R8G8_UNORM,
              VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
            )
          )
        );

      when(this.surfaceExtension.surfaceCapabilities(any(), any()))
        .thenReturn(VulkanSurfaceCapabilitiesKHR.of(
          1,
          1,
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          1,
          Set.of(),
          Set.of(),
          Set.of(),
          Set.of()
        ));

      this.manager = JCSwapchainManager.create(
        JCSwapchainConfiguration.builder()
          .from(this.configuration)
          .build()
      );
      this.manager.close();
    } finally {
      flush.commit();
      this.jfrStream.awaitTermination();
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
      assertTrue(
        e.getString("format").contains("VK_FORMAT_R8_UNORM")
      );
    }
  }

  /**
   * A preferred format is used.
   *
   * @throws Exception On errors
   */

  @Test
  public void testCreateFormatSelectionPreferred0()
    throws Exception
  {
    final var flush = new FlushEvent();

    try {
      final var image0 =
        mock(VulkanImageType.class);
      final var imageView0 =
        mock(VulkanImageViewType.class);
      final var sem0 =
        mock(VulkanSemaphoreBinaryType.class);
      final var presentDoneFence =
        mock(VulkanFenceType.class);
      final var renderDoneFence =
        mock(VulkanFenceType.class);

      this.images.add(image0);

      when(this.device.createImageView(any()))
        .thenReturn(imageView0);
      when(this.device.createBinarySemaphore())
        .thenReturn(sem0);
      when(this.device.createFence())
        .thenReturn(presentDoneFence);
      when(this.device.createFence(any()))
        .thenReturn(renderDoneFence);
      when(this.device.getFenceStatus(presentDoneFence))
        .thenReturn(VK_FENCE_SIGNALLED);

      /*
       * The implementation says it supports a few formats, and one
       * of them is in the list of preferred formats we provide.
       */

      when(this.surfaceExtension.surfaceFormats(any(), any()))
        .thenReturn(
          List.of(
            VulkanSurfaceFormatKHR.of(
              VulkanFormat.VK_FORMAT_R8_UNORM,
              VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
            ),
            VulkanSurfaceFormatKHR.of(
              VulkanFormat.VK_FORMAT_R8G8B8_UNORM,
              VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
            ),
            VulkanSurfaceFormatKHR.of(
              VulkanFormat.VK_FORMAT_R8G8_UNORM,
              VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
            )
          )
        );

      when(this.surfaceExtension.surfaceCapabilities(any(), any()))
        .thenReturn(VulkanSurfaceCapabilitiesKHR.of(
          1,
          1,
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          1,
          Set.of(),
          Set.of(),
          Set.of(),
          Set.of()
        ));

      this.manager = JCSwapchainManager.create(
        JCSwapchainConfiguration.builder()
          .from(this.configuration)
          .addPreferredFormats(
            VulkanSurfaceFormatKHR.of(
              VulkanFormat.VK_FORMAT_R8G8B8_UNORM,
              VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
            )
          )
          .build()
      );
      this.manager.close();
    } finally {
      flush.commit();
      this.jfrStream.awaitTermination();
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
      assertTrue(
        e.getString("format").contains("VK_FORMAT_R8G8B8_UNORM")
      );
    }
  }

  /**
   * A preferred format is used.
   *
   * @throws Exception On errors
   */

  @Test
  public void testCreateFormatSelectionPreferred1()
    throws Exception
  {
    final var flush = new FlushEvent();

    try {
      final var image0 =
        mock(VulkanImageType.class);
      final var imageView0 =
        mock(VulkanImageViewType.class);
      final var sem0 =
        mock(VulkanSemaphoreBinaryType.class);
      final var presentDoneFence =
        mock(VulkanFenceType.class);
      final var renderDoneFence =
        mock(VulkanFenceType.class);

      this.images.add(image0);

      when(this.device.createImageView(any()))
        .thenReturn(imageView0);
      when(this.device.createBinarySemaphore())
        .thenReturn(sem0);
      when(this.device.createFence())
        .thenReturn(presentDoneFence);
      when(this.device.createFence(any()))
        .thenReturn(renderDoneFence);
      when(this.device.getFenceStatus(presentDoneFence))
        .thenReturn(VK_FENCE_SIGNALLED);

      /*
       * The implementation says it supports these formats, and we don't
       * claim to prefer any of them. We'll get the first supported format.
       */

      when(this.surfaceExtension.surfaceFormats(any(), any()))
        .thenReturn(
          List.of(
            VulkanSurfaceFormatKHR.of(
              VulkanFormat.VK_FORMAT_R8_UNORM,
              VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
            ),
            VulkanSurfaceFormatKHR.of(
              VulkanFormat.VK_FORMAT_R8G8_UNORM,
              VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
            ),
            VulkanSurfaceFormatKHR.of(
              VulkanFormat.VK_FORMAT_R8G8B8_UNORM,
              VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
            )
          )
        );

      when(this.surfaceExtension.surfaceCapabilities(any(), any()))
        .thenReturn(VulkanSurfaceCapabilitiesKHR.of(
          1,
          1,
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          1,
          Set.of(),
          Set.of(),
          Set.of(),
          Set.of()
        ));

      this.manager = JCSwapchainManager.create(
        JCSwapchainConfiguration.builder()
          .from(this.configuration)
          .build()
      );
      this.manager.close();
    } finally {
      flush.commit();
      this.jfrStream.awaitTermination();
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
      assertTrue(
        e.getString("format").contains("VK_FORMAT_R8_UNORM")
      );
    }
  }

  /**
   * The first supported mode is used, because we didn't claim to
   * prefer any.
   *
   * @throws Exception On errors
   */

  @Test
  public void testCreateModeSelectionFallback()
    throws Exception
  {
    final var flush = new FlushEvent();

    try {
      final var image0 =
        mock(VulkanImageType.class);
      final var imageView0 =
        mock(VulkanImageViewType.class);
      final var sem0 =
        mock(VulkanSemaphoreBinaryType.class);
      final var presentDoneFence =
        mock(VulkanFenceType.class);
      final var renderDoneFence =
        mock(VulkanFenceType.class);

      this.images.add(image0);

      when(this.device.createImageView(any()))
        .thenReturn(imageView0);
      when(this.device.createBinarySemaphore())
        .thenReturn(sem0);
      when(this.device.createFence())
        .thenReturn(presentDoneFence);
      when(this.device.createFence(any()))
        .thenReturn(renderDoneFence);
      when(this.device.getFenceStatus(presentDoneFence))
        .thenReturn(VK_FENCE_SIGNALLED);

      when(this.surfaceExtension.surfaceFormats(any(), any()))
        .thenReturn(
          List.of(
            VulkanSurfaceFormatKHR.of(
              VulkanFormat.VK_FORMAT_R8_UNORM,
              VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
            )
          )
        );

      when(this.surfaceExtension.surfacePresentModes(any(), any()))
        .thenReturn(
          List.of(
            VulkanPresentModeKHR.VK_PRESENT_MODE_MAILBOX_KHR,
            VulkanPresentModeKHR.VK_PRESENT_MODE_FIFO_RELAXED_KHR
          )
        );

      when(this.surfaceExtension.surfaceCapabilities(any(), any()))
        .thenReturn(VulkanSurfaceCapabilitiesKHR.of(
          1,
          1,
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          1,
          Set.of(),
          Set.of(),
          Set.of(),
          Set.of()
        ));

      this.manager = JCSwapchainManager.create(
        JCSwapchainConfiguration.builder()
          .from(this.configuration)
          .build()
      );
      this.manager.close();
    } finally {
      flush.commit();
      this.jfrStream.awaitTermination();
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
      assertTrue(
        e.getString("mode").contains("VK_PRESENT_MODE_MAILBOX_KHR")
      );
    }
  }

  /**
   * A preferred mode is used, because it is supported.
   *
   * @throws Exception On errors
   */

  @Test
  public void testCreateModeSelectionPreferred0()
    throws Exception
  {
    final var flush = new FlushEvent();

    try {
      final var image0 =
        mock(VulkanImageType.class);
      final var imageView0 =
        mock(VulkanImageViewType.class);
      final var sem0 =
        mock(VulkanSemaphoreBinaryType.class);
      final var presentDoneFence =
        mock(VulkanFenceType.class);
      final var renderDoneFence =
        mock(VulkanFenceType.class);

      this.images.add(image0);

      when(this.device.createImageView(any()))
        .thenReturn(imageView0);
      when(this.device.createBinarySemaphore())
        .thenReturn(sem0);
      when(this.device.createFence())
        .thenReturn(presentDoneFence);
      when(this.device.createFence(any()))
        .thenReturn(renderDoneFence);
      when(this.device.getFenceStatus(presentDoneFence))
        .thenReturn(VK_FENCE_SIGNALLED);

      when(this.surfaceExtension.surfaceFormats(any(), any()))
        .thenReturn(
          List.of(
            VulkanSurfaceFormatKHR.of(
              VulkanFormat.VK_FORMAT_R8_UNORM,
              VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
            )
          )
        );

      when(this.surfaceExtension.surfacePresentModes(any(), any()))
        .thenReturn(
          List.of(
            VulkanPresentModeKHR.VK_PRESENT_MODE_IMMEDIATE_KHR,
            VulkanPresentModeKHR.VK_PRESENT_MODE_MAILBOX_KHR,
            VulkanPresentModeKHR.VK_PRESENT_MODE_FIFO_RELAXED_KHR
          )
        );

      when(this.surfaceExtension.surfaceCapabilities(any(), any()))
        .thenReturn(VulkanSurfaceCapabilitiesKHR.of(
          1,
          1,
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          1,
          Set.of(),
          Set.of(),
          Set.of(),
          Set.of()
        ));

      this.manager = JCSwapchainManager.create(
        JCSwapchainConfiguration.builder()
          .from(this.configuration)
          .addPreferredModes(VulkanPresentModeKHR.VK_PRESENT_MODE_MAILBOX_KHR)
          .build()
      );
      this.manager.close();
    } finally {
      flush.commit();
      this.jfrStream.awaitTermination();
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
      assertTrue(
        e.getString("mode").contains("VK_PRESENT_MODE_MAILBOX_KHR")
      );
    }
  }

  /**
   * No modes are claimed to be supported, so we fall back to a common
   * value.
   *
   * @throws Exception On errors
   */

  @Test
  public void testCreateModeSelectionFallbackNone()
    throws Exception
  {
    final var flush = new FlushEvent();

    try {
      final var image0 =
        mock(VulkanImageType.class);
      final var imageView0 =
        mock(VulkanImageViewType.class);
      final var sem0 =
        mock(VulkanSemaphoreBinaryType.class);
      final var presentDoneFence =
        mock(VulkanFenceType.class);
      final var renderDoneFence =
        mock(VulkanFenceType.class);

      this.images.add(image0);

      when(this.device.createImageView(any()))
        .thenReturn(imageView0);
      when(this.device.createBinarySemaphore())
        .thenReturn(sem0);
      when(this.device.createFence())
        .thenReturn(presentDoneFence);
      when(this.device.createFence(any()))
        .thenReturn(renderDoneFence);
      when(this.device.getFenceStatus(presentDoneFence))
        .thenReturn(VK_FENCE_SIGNALLED);

      when(this.surfaceExtension.surfaceFormats(any(), any()))
        .thenReturn(
          List.of(
            VulkanSurfaceFormatKHR.of(
              VulkanFormat.VK_FORMAT_R8_UNORM,
              VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
            )
          )
        );

      when(this.surfaceExtension.surfacePresentModes(any(), any()))
        .thenReturn(
          List.of(

          )
        );

      when(this.surfaceExtension.surfaceCapabilities(any(), any()))
        .thenReturn(VulkanSurfaceCapabilitiesKHR.of(
          1,
          1,
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          1,
          Set.of(),
          Set.of(),
          Set.of(),
          Set.of()
        ));

      this.manager = JCSwapchainManager.create(
        JCSwapchainConfiguration.builder()
          .from(this.configuration)
          .build()
      );
      this.manager.close();
    } finally {
      flush.commit();
      this.jfrStream.awaitTermination();
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
      assertTrue(
        e.getString("mode").contains("VK_PRESENT_MODE_FIFO_KHR")
      );
    }
  }

  @Test
  public void testRecreateOnOutOfDate()
    throws Exception
  {
    final var flush = new FlushEvent();

    try {
      final var image0 =
        mock(VulkanImageType.class);
      final var imageView0 =
        mock(VulkanImageViewType.class);
      final var imageReadySemaphore0 =
        mock(VulkanSemaphoreBinaryType.class);
      final var renderDoneSemaphore0 =
        mock(VulkanSemaphoreBinaryType.class);
      final var imageReadySemaphore1 =
        mock(VulkanSemaphoreBinaryType.class);
      final var renderDoneSemaphore1 =
        mock(VulkanSemaphoreBinaryType.class);
      final var presentDoneFence0 =
        mock(VulkanFenceType.class);
      final var renderDoneFence0 =
        mock(VulkanFenceType.class);
      final var presentDoneFence1 =
        mock(VulkanFenceType.class);
      final var renderDoneFence1 =
        mock(VulkanFenceType.class);

      this.images.add(image0);

      when(this.device.createImageView(any()))
        .thenReturn(imageView0);
      when(this.device.createBinarySemaphore())
        .thenReturn(imageReadySemaphore0)
        .thenReturn(renderDoneSemaphore0)
        .thenReturn(imageReadySemaphore1)
        .thenReturn(renderDoneSemaphore1)
        .thenThrow(new IllegalStateException());
      when(this.device.createFence())
        .thenReturn(presentDoneFence0)
        .thenReturn(presentDoneFence1)
        .thenThrow(new IllegalStateException());
      when(this.device.createFence(any()))
        .thenReturn(renderDoneFence0)
        .thenReturn(renderDoneFence1)
        .thenThrow(new IllegalStateException());
      when(this.device.getFenceStatus(presentDoneFence0))
        .thenReturn(VK_FENCE_SIGNALLED);

      when(this.surfaceExtension.surfaceCapabilities(any(), any()))
        .thenReturn(VulkanSurfaceCapabilitiesKHR.of(
          1,
          1,
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          1,
          Set.of(),
          Set.of(),
          Set.of(),
          Set.of()
        ));

      when(this.device.waitForFence(eq(renderDoneFence0), anyLong()))
        .thenReturn(VK_WAIT_SUCCEEDED);
      when(this.device.waitForFence(eq(renderDoneFence1), anyLong()))
        .thenReturn(VK_WAIT_SUCCEEDED);

      when(this.swapchain.acquireImageWithSemaphore(anyLong(), any()))
        .thenReturn(new VulkanSwapChainOutOfDate())
        .thenReturn(new VulkanSwapChainOK(0));

      this.manager = JCSwapchainManager.create(this.configuration);
      this.manager.acquire();
      this.manager.close();

      /*
       * Verify that resources were deleted.
       */

      verify(renderDoneFence0, new Times(1))
        .close();
      verify(presentDoneFence0, new Times(1))
        .close();
      verify(imageReadySemaphore0, new Times(1))
        .close();
      verify(renderDoneSemaphore0, new Times(1))
        .close();

      verify(renderDoneFence1, new Times(1))
        .close();
      verify(presentDoneFence1, new Times(1))
        .close();
      verify(imageReadySemaphore1, new Times(1))
        .close();
      verify(renderDoneSemaphore1, new Times(1))
        .close();

      verify(this.swapchain, new Times(2))
        .close();

    } finally {
      flush.commit();
      this.jfrStream.awaitTermination();
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquireFailed.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainDeleted.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquired.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainDeleted.class);
    }
  }

  @Test
  public void testRecreateOnSubOptimal()
    throws Exception
  {
    final var flush = new FlushEvent();

    try {
      final var image0 =
        mock(VulkanImageType.class);
      final var imageView0 =
        mock(VulkanImageViewType.class);
      final var sem0 =
        mock(VulkanSemaphoreBinaryType.class);
      final var presentDoneFence =
        mock(VulkanFenceType.class);
      final var renderDoneFence =
        mock(VulkanFenceType.class);

      this.images.add(image0);

      when(this.device.createImageView(any()))
        .thenReturn(imageView0);
      when(this.device.createBinarySemaphore())
        .thenReturn(sem0);
      when(this.device.createFence())
        .thenReturn(presentDoneFence);
      when(this.device.createFence(any()))
        .thenReturn(renderDoneFence);
      when(this.device.getFenceStatus(presentDoneFence))
        .thenReturn(VK_FENCE_SIGNALLED);

      when(this.surfaceExtension.surfaceCapabilities(any(), any()))
        .thenReturn(VulkanSurfaceCapabilitiesKHR.of(
          1,
          1,
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          1,
          Set.of(),
          Set.of(),
          Set.of(),
          Set.of()
        ));

      when(this.device.waitForFence(eq(renderDoneFence), anyLong()))
        .thenReturn(VK_WAIT_SUCCEEDED);

      when(this.swapchain.acquireImageWithSemaphore(anyLong(), any()))
        .thenReturn(new VulkanSwapChainSubOptimal())
        .thenReturn(new VulkanSwapChainOK(0));

      this.manager = JCSwapchainManager.create(this.configuration);
      this.manager.acquire();
      this.manager.close();
    } finally {
      flush.commit();
      this.jfrStream.awaitTermination();
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquireFailed.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainDeleted.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquired.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainDeleted.class);
    }
  }

  @Test
  public void testAcquireEventually()
    throws Exception
  {
    final var flush = new FlushEvent();

    try {
      final var image0 =
        mock(VulkanImageType.class);
      final var imageView0 =
        mock(VulkanImageViewType.class);
      final var imageReadySemaphore =
        mock(VulkanSemaphoreBinaryType.class);
      final var renderDoneSemaphore =
        mock(VulkanSemaphoreBinaryType.class);
      final var presentDoneFence =
        mock(VulkanFenceType.class);
      final var renderDoneFence =
        mock(VulkanFenceType.class);

      when(imageReadySemaphore.toString())
        .thenReturn("ImageReadySemaphore");
      when(renderDoneSemaphore.toString())
        .thenReturn("RenderDoneSemaphore");
      when(imageView0.image())
        .thenReturn(image0);

      this.images.add(image0);

      when(this.device.createImageView(any()))
        .thenReturn(imageView0);
      when(this.device.createBinarySemaphore())
        .thenReturn(imageReadySemaphore)
        .thenReturn(renderDoneSemaphore)
        .thenThrow(new IllegalStateException());
      when(this.device.createFence())
        .thenReturn(presentDoneFence);
      when(this.device.createFence(any()))
        .thenReturn(renderDoneFence);
      when(this.device.getFenceStatus(presentDoneFence))
        .thenReturn(VK_FENCE_SIGNALLED);

      when(this.surfaceExtension.surfaceCapabilities(any(), any()))
        .thenReturn(VulkanSurfaceCapabilitiesKHR.of(
          1,
          1,
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          1,
          Set.of(),
          Set.of(),
          Set.of(),
          Set.of()
        ));

      when(this.device.waitForFence(eq(renderDoneFence), anyLong()))
        .thenReturn(VK_WAIT_SUCCEEDED);

      when(this.swapchain.acquireImageWithSemaphore(anyLong(), any()))
        .thenReturn(new VulkanSwapChainNotReady())
        .thenReturn(new VulkanSwapChainTimedOut())
        .thenReturn(new VulkanSwapChainNotReady())
        .thenReturn(new VulkanSwapChainTimedOut())
        .thenReturn(new VulkanSwapChainOK(0));

      this.manager = JCSwapchainManager.create(this.configuration);

      final var r = this.manager.acquire();
      assertEquals(image0, r.image());
      assertEquals(imageView0, r.imageView());
      assertEquals(new JCSwapchainImageIndex(0), r.index());
      assertEquals(renderDoneFence, r.renderFinishedFence());
      assertEquals(imageReadySemaphore, r.imageReadySemaphore());
      assertEquals(renderDoneSemaphore, r.renderFinishedSemaphore());
      assertEquals(this.swapchain, r.swapchain());

      this.manager.close();
    } finally {
      flush.commit();
      this.jfrStream.awaitTermination();
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquireFailed.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquireFailed.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquireFailed.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquireFailed.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquired.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainDeleted.class);
    }
  }

  @Test
  public void testAcquireEventuallyRenderDoneFence()
    throws Exception
  {
    final var flush = new FlushEvent();

    try {
      final var image0 =
        mock(VulkanImageType.class);
      final var imageView0 =
        mock(VulkanImageViewType.class);
      final var sem0 =
        mock(VulkanSemaphoreBinaryType.class);
      final var presentDoneFence =
        mock(VulkanFenceType.class);
      final var renderDoneFence =
        mock(VulkanFenceType.class);

      this.images.add(image0);

      when(this.device.createImageView(any()))
        .thenReturn(imageView0);
      when(this.device.createBinarySemaphore())
        .thenReturn(sem0);
      when(this.device.createFence())
        .thenReturn(presentDoneFence);
      when(this.device.createFence(any()))
        .thenReturn(renderDoneFence);
      when(this.device.getFenceStatus(presentDoneFence))
        .thenReturn(VK_FENCE_SIGNALLED);

      when(this.surfaceExtension.surfaceCapabilities(any(), any()))
        .thenReturn(VulkanSurfaceCapabilitiesKHR.of(
          1,
          1,
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          VulkanExtent2D.of(640, 480),
          1,
          Set.of(),
          Set.of(),
          Set.of(),
          Set.of()
        ));

      when(this.device.waitForFence(eq(renderDoneFence), anyLong()))
        .thenReturn(VK_WAIT_TIMED_OUT)
        .thenReturn(VK_WAIT_TIMED_OUT)
        .thenReturn(VK_WAIT_TIMED_OUT)
        .thenReturn(VK_WAIT_SUCCEEDED);

      when(this.swapchain.acquireImageWithSemaphore(anyLong(), any()))
        .thenReturn(new VulkanSwapChainOK(0));

      this.manager = JCSwapchainManager.create(this.configuration);
      this.manager.acquire();
      this.manager.close();
    } finally {
      flush.commit();
      this.jfrStream.awaitTermination();
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainCreated.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquireFailed.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquireFailed.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquireFailed.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainImageAcquired.class);
    }

    {
      final var e = this.events.poll();
      checkEventType(e, JCSwapchainJFRSwapchainDeleted.class);
    }
  }

  private static void checkEventType(
    final RecordedEvent e,
    final Class<?> expectedClass)
  {
    final var receivedName = e.getEventType().getName();
    assertEquals(
      expectedClass.getCanonicalName(),
      receivedName
    );
  }
}
