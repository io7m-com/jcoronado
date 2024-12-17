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

import com.io7m.jcoronado.api.VulkanComponentMapping;
import com.io7m.jcoronado.api.VulkanDebuggingType;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanFenceCreateInfo;
import com.io7m.jcoronado.api.VulkanFenceType;
import com.io7m.jcoronado.api.VulkanFormat;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanImageViewCreateFlag;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import com.io7m.jcoronado.api.VulkanImageViewType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanQueueFamilyIndex;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanResourceException;
import com.io7m.jcoronado.api.VulkanSemaphoreBinaryType;
import com.io7m.jcoronado.api.VulkanSharingMode;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanExtKHRSurfaceType;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanSurfaceCapabilitiesKHR;
import com.io7m.jcoronado.extensions.khr_surface.api.VulkanSurfaceFormatKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanColorSpaceKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanExtKHRSwapChainType;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentInfoKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanPresentModeKHR;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainCreateInfo;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainNotReady;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainOK;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainOutOfDate;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainSubOptimal;
import com.io7m.jcoronado.extensions.khr_swapchain.api.VulkanSwapChainTimedOut;
import com.io7m.jmulticlose.core.CloseableCollection;
import com.io7m.jmulticlose.core.CloseableCollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.io7m.jcoronado.api.VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_IDENTITY;
import static com.io7m.jcoronado.api.VulkanFenceCreateFlag.VK_FENCE_CREATE_SIGNALED_BIT;
import static com.io7m.jcoronado.api.VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT;
import static com.io7m.jcoronado.api.VulkanImageViewKind.VK_IMAGE_VIEW_TYPE_2D;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_CONCURRENT;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE;

/**
 * The swapchain manager.
 */

public final class JCSwapchainManager
  implements JCSwapchainManagerType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(JCSwapchainManager.class);

  private static final Duration RENDER_FENCE_TIMEOUT =
    Duration.ofSeconds(1L);

  private static final VulkanComponentMapping IDENTITY_SWIZZLE =
    VulkanComponentMapping.of(
      VK_COMPONENT_SWIZZLE_IDENTITY,
      VK_COMPONENT_SWIZZLE_IDENTITY,
      VK_COMPONENT_SWIZZLE_IDENTITY,
      VK_COMPONENT_SWIZZLE_IDENTITY
    );

  private final JCSwapchainConfiguration configuration;
  private final VulkanLogicalDeviceType device;
  private final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface;
  private final VulkanExtKHRSurfaceType surfaceExtension;
  private final VulkanQueueType graphicsQueue;
  private final VulkanQueueType presentationQueue;
  private final VulkanExtKHRSwapChainType swapchainExtension;
  private final ConcurrentHashMap.KeySetView<SwapchainHolder, Boolean> deletions;
  private final AtomicBoolean closed;
  private final SubmissionPublisher<JCSwapchainEventType> events;
  private SwapchainHolder current;

  private JCSwapchainManager(
    final JCSwapchainConfiguration inConfiguration)
  {
    this.configuration =
      Objects.requireNonNull(inConfiguration, "configuration");

    this.device =
      this.configuration.device();
    this.surface =
      this.configuration.surface();
    this.surfaceExtension =
      this.configuration.surfaceExtension();
    this.swapchainExtension =
      this.configuration.swapChainExtension();
    this.graphicsQueue =
      this.configuration.graphicsQueue();
    this.presentationQueue =
      this.configuration.presentationQueue();
    this.deletions =
      ConcurrentHashMap.newKeySet();
    this.closed =
      new AtomicBoolean(false);
    this.events =
      new SubmissionPublisher<>(Runnable::run, Integer.MAX_VALUE);
  }

  /**
   * Create a swapchain manager.
   *
   * @param configuration The configuration
   *
   * @return A manager
   *
   * @throws VulkanException On errors
   */

  public static JCSwapchainManagerType create(
    final JCSwapchainConfiguration configuration)
    throws VulkanException
  {
    Objects.requireNonNull(configuration, "configuration");

    final var manager = new JCSwapchainManager(configuration);
    manager.recreate();
    return manager;
  }

  /**
   * If the graphics and presentation queues are separate families, then
   * add the indices of those families into the given list and enable
   * concurrent sharing mode. Otherwise, don't add any indices, and use
   * exclusive sharing mode.
   */

  private static VulkanSharingMode pickImageSharingMode(
    final VulkanQueueType graphicsQueue,
    final VulkanQueueType presentationQueue,
    final List<VulkanQueueFamilyIndex> queueIndices)
  {
    final var graphicsFamily =
      graphicsQueue.queueFamilyProperties().queueFamilyIndex();
    final var presentationFamily =
      presentationQueue.queueFamilyProperties().queueFamilyIndex();

    if (!Objects.equals(graphicsFamily, presentationFamily)) {
      queueIndices.add(graphicsFamily);
      queueIndices.add(presentationFamily);
      return VK_SHARING_MODE_CONCURRENT;
    }
    return VK_SHARING_MODE_EXCLUSIVE;
  }

  /**
   * Select the minimum number of images required.
   */

  private static int pickImageCount(
    final VulkanSurfaceCapabilitiesKHR surfaceCaps)
  {
    /*
     * Oddly, many implementations will return a minimum image count of N,
     * but then return an error unless N+1 is used.
     */

    final var minPlus = surfaceCaps.minImageCount() + 1;
    if (surfaceCaps.maxImageCount() == 0) {
      return minPlus;
    }

    return Math.clamp(
      minPlus,
      surfaceCaps.minImageCount(),
      surfaceCaps.maxImageCount()
    );
  }

  /**
   * Work out the extent of the rendered image based on the
   * implementation-defined supported limits.
   */

  private static VulkanExtent2D pickExtent(
    final VulkanSurfaceCapabilitiesKHR caps)
  {
    final var minExtent =
      caps.minImageExtent();
    final var maxExtent =
      caps.maxImageExtent();

    LOG.trace("Minimum surface extent: {}", minExtent);
    LOG.trace("Maximum surface extent: {}", maxExtent);

    final var x = Math.clamp(
      (long) caps.currentExtent().width(),
      minExtent.width(),
      maxExtent.width()
    );
    final var y = Math.clamp(
      (long) caps.currentExtent().height(),
      minExtent.height(),
      maxExtent.height()
    );

    return VulkanExtent2D.of(x, y);
  }

  @Override
  public Flow.Publisher<JCSwapchainEventType> events()
  {
    return this.events;
  }

  @Override
  public JCSwapchainImageType acquire()
    throws VulkanException
  {
    if (LOG.isTraceEnabled()) {
      LOG.trace("Acquiring swapchain image.");
    }

    while (true) {
      try {
        return this.current.acquire(this.device);
      } catch (final SwapchainOutOfDate _) {
        this.recreate();
      } catch (final SwapchainNotReady _) {
        // Nothing
      } catch (final SwapchainRenderingDoneFenceTimedOut _) {
        // Nothing
      } catch (final SwapchainAcquireTimedOut _) {
        // Nothing
      } finally {
        this.cleanUp();
      }
    }
  }

  @Override
  public Set<JCSwapchainImageIndex> imageIndices()
  {
    return Set.copyOf(this.current.swapChainImages.keySet());
  }

  @Override
  public void close()
    throws VulkanException
  {
    if (this.closed.compareAndSet(false, true)) {
      final var resources =
        CloseableCollection.create(() -> {
          return new VulkanResourceException(
            "One or more resources failed to close."
          );
        });

      resources.add(this.current);
      this.deletions.forEach(resources::add);
      resources.add(this.events);
      resources.close();
    }
  }

  private void recreate()
    throws VulkanException
  {
    final var oldSwapchain = this.current;
    this.current = this.createNewSwapchain();
    if (oldSwapchain != null) {
      this.deletions.add(oldSwapchain);
    }
    this.cleanUp();
    this.events.submit(new JCSwapchainEventRecreated());
  }

  private void cleanUp()
  {
    final var deletionsNow =
      List.copyOf(this.deletions);

    for (final var holder : deletionsNow) {
      try {
        if (holder.isReadyForDeletion()) {
          holder.close();
          this.deletions.remove(holder);
        }
      } catch (final VulkanException e) {
        LOG.debug("Failed to close swapchain holder: ", e);
      }
    }
  }

  private SwapchainHolder createNewSwapchain()
    throws VulkanException
  {
    final var resources =
      CloseableCollection.create(() -> {
        return new VulkanResourceException(
          "One or more resources failed to close.");
      });

    final var id = UUID.randomUUID();
    final var eventCreated = new JCSwapchainJFRSwapchainCreated();
    eventCreated.id = id.toString();
    eventCreated.begin();

    final var eventCreationFailed = new JCSwapchainJFRSwapchainCreationFailed();
    eventCreationFailed.id = id.toString();
    eventCreationFailed.begin();

    try {
      final var physicalDevice =
        this.device.physicalDevice();
      final var debugging =
        this.device.debugging();

      final var format = this.pickSurfaceFormat();
      eventCreated.format = format.toString();
      eventCreationFailed.format = eventCreated.format;

      final var mode = this.pickPresentationMode();
      eventCreated.mode = mode.toString();
      eventCreationFailed.mode = eventCreated.mode;

      final var caps =
        this.surfaceExtension.surfaceCapabilities(physicalDevice, this.surface);

      final var extent = pickExtent(caps);
      eventCreated.width = extent.width();
      eventCreated.height = extent.height();
      eventCreationFailed.width = eventCreated.width;
      eventCreationFailed.height = eventCreated.height;

      final var minImageCount = pickImageCount(caps);
      eventCreated.minimumImageCount = minImageCount;
      eventCreationFailed.minimumImageCount = eventCreated.minimumImageCount;

      final List<VulkanQueueFamilyIndex> queueIndices =
        new ArrayList<>();

      final var imageSharingMode =
        pickImageSharingMode(
          this.graphicsQueue,
          this.presentationQueue,
          queueIndices
        );

      final var swapChainCreateInfo =
        VulkanSwapChainCreateInfo.of(
          this.surface,
          minImageCount,
          format.format(),
          format.colorSpace(),
          extent,
          1,
          this.configuration.imageUsageFlags(),
          imageSharingMode,
          queueIndices,
          caps.currentTransform(),
          this.configuration.surfaceAlphaFlags(),
          mode,
          true,
          Optional.empty()
        );

      final var swapChain =
        resources.add(
          this.swapchainExtension.swapChainCreate(
            this.device,
            swapChainCreateInfo
          )
        );

      debugging.setObjectName(
        swapChain,
        "SwapChain[0x%s]".formatted(
          Integer.toUnsignedString(swapChain.hashCode(), 16))
      );

      final var swapchainVulkanImages =
        swapChain.images();
      final var imageCount =
        swapchainVulkanImages.size();

      final var swapchainImageViews =
        new HashMap<JCSwapchainImageIndex, VulkanImageViewType>(imageCount);
      final var imageReadySemaphores =
        new HashMap<JCSwapchainFrameIndex, VulkanSemaphoreBinaryType>(imageCount);
      final var renderDoneSemaphores =
        new HashMap<JCSwapchainFrameIndex, VulkanSemaphoreBinaryType>(imageCount);
      final var renderDoneFences =
        new HashMap<JCSwapchainFrameIndex, VulkanFenceType>(imageCount);
      final var presentDoneFences =
        new HashMap<JCSwapchainFrameIndex, VulkanFenceType>(imageCount);

      for (var index = 0; index < imageCount; ++index) {
        this.createPerImageResources(
          index,
          resources,
          format,
          swapchainVulkanImages,
          debugging,
          imageReadySemaphores,
          renderDoneSemaphores,
          renderDoneFences,
          presentDoneFences,
          swapchainImageViews
        );
      }

      eventCreated.imageCount = swapchainImageViews.size();
      eventCreated.end();
      eventCreated.commit();

      return new SwapchainHolder(
        this,
        id,
        swapChain,
        swapchainImageViews,
        imageReadySemaphores,
        renderDoneSemaphores,
        renderDoneFences,
        presentDoneFences,
        resources,
        extent,
        format.format()
      );
    } catch (final Exception e) {
      eventCreationFailed.errorMessage = e.getMessage();
      eventCreationFailed.end();
      eventCreationFailed.commit();
      resources.close();
      throw e;
    }
  }

  private void createPerImageResources(
    final int index,
    final CloseableCollectionType<VulkanResourceException> resources,
    final VulkanSurfaceFormatKHR format,
    final List<VulkanImageType> swapchainVulkanImages,
    final VulkanDebuggingType debugging,
    final Map<JCSwapchainFrameIndex, VulkanSemaphoreBinaryType> imageReadySemaphores,
    final Map<JCSwapchainFrameIndex, VulkanSemaphoreBinaryType> renderDoneSemaphores,
    final Map<JCSwapchainFrameIndex, VulkanFenceType> renderDoneFences,
    final Map<JCSwapchainFrameIndex, VulkanFenceType> presentDoneFences,
    final Map<JCSwapchainImageIndex, VulkanImageViewType> swapchainImageViews)
    throws VulkanException
  {
    final var frameIndex =
      new JCSwapchainFrameIndex(index);
    final var swapchainIndex =
      new JCSwapchainImageIndex(index);

    final var imageView =
      resources.add(
        this.createImageViewForImage(
          format,
          swapchainVulkanImages.get(index)
        )
      );

    debugging.setObjectName(
      imageView,
      "SwapchainImageView[%d]".formatted(Integer.valueOf(index))
    );
    debugging.setObjectName(
      imageView.image(),
      "SwapchainImage[%d]".formatted(Integer.valueOf(index))
    );

    final var imageReadySemaphore =
      resources.add(this.device.createBinarySemaphore());

    debugging.setObjectName(
      imageReadySemaphore,
      "SwapchainSemaphore[ImageReady][%d]".formatted(Integer.valueOf(index))
    );

    final var renderDoneSemaphore =
      resources.add(this.device.createBinarySemaphore());

    debugging.setObjectName(
      renderDoneSemaphore,
      "SwapchainSemaphore[RenderDone][%d]".formatted(Integer.valueOf(index))
    );

    final var presentDoneFence =
      resources.add(this.device.createFence());

    debugging.setObjectName(
      presentDoneFence,
      "SwapchainFence[PresentDone][%d]".formatted(Integer.valueOf(index))
    );

    /*
     * The "render done" fence is created in the already-signalled state.
     * This is because there may not be a previous frame that has been
     * rendered, and we don't want to wait for rendering that is not
     * taking place!
     */

    final var renderDoneFence =
      resources.add(this.device.createFence(
        VulkanFenceCreateInfo.builder()
          .addFlags(VK_FENCE_CREATE_SIGNALED_BIT)
          .build()
      ));

    debugging.setObjectName(
      renderDoneFence,
      "SwapchainFence[RenderDone][%d]".formatted(Integer.valueOf(index))
    );

    imageReadySemaphores.put(
      frameIndex, imageReadySemaphore);
    renderDoneSemaphores.put(
      frameIndex, renderDoneSemaphore);
    renderDoneFences.put(
      frameIndex, renderDoneFence);
    presentDoneFences.put(
      frameIndex, presentDoneFence);
    swapchainImageViews.put(
      swapchainIndex, imageView);
  }

  private VulkanImageViewType createImageViewForImage(
    final VulkanSurfaceFormatKHR surfaceFormat,
    final VulkanImageType image)
    throws VulkanException
  {
    final var range =
      VulkanImageSubresourceRange.of(
        Set.of(VK_IMAGE_ASPECT_COLOR_BIT),
        0,
        1,
        0,
        1
      );

    final Set<VulkanImageViewCreateFlag> flags = Set.of();
    return this.device.createImageView(
      VulkanImageViewCreateInfo.of(
        flags,
        image,
        VK_IMAGE_VIEW_TYPE_2D,
        surfaceFormat.format(),
        IDENTITY_SWIZZLE,
        range
      )
    );
  }

  /**
   * Pick a suitable presentation mode based on the given preferences.
   */

  private VulkanPresentModeKHR pickPresentationMode()
    throws VulkanException
  {
    final var availableList =
      this.surfaceExtension.surfacePresentModes(
        this.device.physicalDevice(),
        this.surface
      );
    final var available =
      Set.copyOf(availableList);

    final var preferred =
      this.configuration.preferredModes();

    /*
     * Check to see if any of our preferred modes matches one of those
     * the implementation says it supports.
     */

    for (final var mode : preferred) {
      if (available.contains(mode)) {
        return mode;
      }
    }

    /*
     * None of our preferred formats were supported (or we didn't claim to
     * prefer any). Use the first supported mode if there is one.
     */

    if (!availableList.isEmpty()) {
      return availableList.get(0);
    }

    /*
     * Otherwise fall back to the most widely supported option at the time
     * of writing.
     *
     * @see "https://vulkan.gpuinfo.org/listsurfacepresentmodes.php"
     */

    return VulkanPresentModeKHR.VK_PRESENT_MODE_FIFO_KHR;
  }

  /**
   * Pick a suitable surface format based on the given preferences.
   */

  private VulkanSurfaceFormatKHR pickSurfaceFormat()
    throws VulkanException
  {
    final var availableList =
      this.surfaceExtension.surfaceFormats(
        this.device.physicalDevice(),
        this.surface
      );
    final var available =
      Set.copyOf(availableList);

    final var preferred =
      this.configuration.preferredFormats();

    /*
     * Check to see if any of our preferred formats matches one of those
     * the implementation says it supports.
     */

    for (final var format : preferred) {
      if (available.contains(format)) {
        return format;
      }
    }

    /*
     * None of our preferred formats were supported (or we didn't claim to
     * prefer any). The spec says that at least one format must be returned,
     * but implementations have been seen in the wild that return an empty
     * list. For those implementations, the assumption is that any RGBA
     * format mandated by the spec as renderable can be used.
     */

    if (availableList.isEmpty()) {
      return VulkanSurfaceFormatKHR.of(
        VulkanFormat.VK_FORMAT_B8G8R8A8_UNORM,
        VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR
      );
    }

    return availableList.get(0);
  }

  private static final class SwapchainOutOfDate
    extends Exception
  {
    SwapchainOutOfDate()
    {
      super("Swap chain reports VK_ERROR_OUT_OF_DATE_KHR or VK_SUBOPTIMAL_KHR.");
    }
  }

  private static final class SwapchainNotReady
    extends Exception
  {
    SwapchainNotReady()
    {
      super("Swap chain reports VK_NOT_READY.");
    }
  }

  private static final class SwapchainRenderingDoneFenceTimedOut
    extends Exception
  {
    SwapchainRenderingDoneFenceTimedOut(
      final String message)
    {
      super(message);
    }
  }

  private static final class SwapchainAcquireTimedOut
    extends Exception
  {
    SwapchainAcquireTimedOut()
    {
      super("Swap chain reports VK_TIMED_OUT.");
    }
  }

  private static final class SwapchainImage
    implements JCSwapchainImageType
  {
    private final SwapchainHolder holder;
    private final JCSwapchainImageIndex index;
    private final VulkanImageViewType imageView;
    private final VulkanSemaphoreBinaryType imageReadySemaphore;
    private final VulkanSemaphoreBinaryType renderFinishedSemaphore;
    private final VulkanFenceType presentDoneFence;
    private final VulkanExtKHRSwapChainType.VulkanKHRSwapChainType swapchain;
    private final VulkanFenceType renderFinishedFence;
    private final VulkanExtent2D size;
    private final VulkanFormat format;

    SwapchainImage(
      final SwapchainHolder inHolder,
      final JCSwapchainImageIndex inIndex,
      final VulkanImageViewType inImageView,
      final VulkanSemaphoreBinaryType inImageReadySemaphore,
      final VulkanSemaphoreBinaryType inRenderFinishedSemaphore,
      final VulkanFenceType inPresentDoneFence,
      final VulkanExtKHRSwapChainType.VulkanKHRSwapChainType inSwapchain,
      final VulkanFenceType inRenderFinishedFence,
      final VulkanFormat inFormat,
      final VulkanExtent2D inSize)
    {
      this.holder =
        Objects.requireNonNull(inHolder, "inHolder");
      this.index =
        Objects.requireNonNull(inIndex, "inIndex");
      this.imageView =
        Objects.requireNonNull(inImageView, "imageView");
      this.imageReadySemaphore =
        Objects.requireNonNull(inImageReadySemaphore, "imageReadySemaphore");
      this.renderFinishedSemaphore =
        Objects.requireNonNull(
          inRenderFinishedSemaphore, "renderFinishedSemaphore");
      this.presentDoneFence =
        Objects.requireNonNull(inPresentDoneFence, "inPresentDoneFence");
      this.swapchain =
        Objects.requireNonNull(inSwapchain, "swapchain");
      this.renderFinishedFence =
        Objects.requireNonNull(inRenderFinishedFence, "renderFinishedFence");
      this.format =
        Objects.requireNonNull(inFormat, "format");
      this.size =
        Objects.requireNonNull(inSize, "size");
    }

    @Override
    public String toString()
    {
      return "[SwapchainImage %s]".formatted(this.index);
    }

    @Override
    public VulkanFormat format()
    {
      return this.format;
    }

    @Override
    public VulkanExtent2D size()
    {
      return this.size;
    }

    @Override
    public JCSwapchainImageIndex index()
    {
      return this.index;
    }

    @Override
    public VulkanImageType image()
    {
      return this.imageView.image();
    }

    @Override
    public VulkanImageViewType imageView()
    {
      return this.imageView;
    }

    @Override
    public void close()
    {

    }

    @Override
    public void present()
      throws VulkanException
    {
      if (LOG.isTraceEnabled()) {
        LOG.trace(
          "Present: {} (wait {}, signal {})",
          this.index,
          this.imageReadySemaphore,
          this.presentDoneFence
        );
      }

      final var info =
        VulkanPresentInfoKHR.builder()
          .addImageIndices(this.index.value())
          .addWaitSemaphores(this.renderFinishedSemaphore)
          .addSwapChains(this.swapchain)
          .addFences(this.presentDoneFence)
          .build();

      final var manager = this.holder.manager;
      manager.swapchainExtension.queuePresent(manager.presentationQueue, info);
    }

    @Override
    public VulkanSemaphoreBinaryType imageReadySemaphore()
    {
      return this.imageReadySemaphore;
    }

    @Override
    public VulkanSemaphoreBinaryType renderFinishedSemaphore()
    {
      return this.renderFinishedSemaphore;
    }

    @Override
    public VulkanExtKHRSwapChainType.VulkanKHRSwapChainType swapchain()
    {
      return this.swapchain;
    }

    @Override
    public VulkanFenceType renderFinishedFence()
    {
      return this.renderFinishedFence;
    }
  }

  private static final class SwapchainHolder
    implements AutoCloseable
  {
    private final CloseableCollectionType<VulkanResourceException> resources;
    private final JCSwapchainManager manager;
    private final Map<JCSwapchainFrameIndex, VulkanFenceType> presentDoneFences;
    private final Map<JCSwapchainFrameIndex, VulkanFenceType> renderDoneFences;
    private final Map<JCSwapchainFrameIndex, VulkanSemaphoreBinaryType> imageReadySemaphores;
    private final Map<JCSwapchainFrameIndex, VulkanSemaphoreBinaryType> renderDoneSemaphores;
    private final Map<JCSwapchainImageIndex, VulkanImageViewType> swapChainImages;
    private final UUID id;
    private final VulkanExtKHRSwapChainType.VulkanKHRSwapChainType swapchain;
    private final VulkanExtent2D extent;
    private final VulkanFormat format;
    private JCSwapchainFrameIndex frameIndex;

    SwapchainHolder(
      final JCSwapchainManager inManager,
      final UUID inId,
      final VulkanExtKHRSwapChainType.VulkanKHRSwapChainType inSwapchain,
      final Map<JCSwapchainImageIndex, VulkanImageViewType> inSwapchainImages,
      final Map<JCSwapchainFrameIndex, VulkanSemaphoreBinaryType> inImageReadySemaphores,
      final Map<JCSwapchainFrameIndex, VulkanSemaphoreBinaryType> inRenderDoneSemaphores,
      final Map<JCSwapchainFrameIndex, VulkanFenceType> inRenderDoneFences,
      final Map<JCSwapchainFrameIndex, VulkanFenceType> inPresentDoneFences,
      final CloseableCollectionType<VulkanResourceException> inResources,
      final VulkanExtent2D inExtent,
      final VulkanFormat inFormat)
    {
      this.manager =
        Objects.requireNonNull(inManager, "manager");
      this.swapchain =
        Objects.requireNonNull(inSwapchain, "swapChain");
      this.swapChainImages =
        Map.copyOf(inSwapchainImages);
      this.imageReadySemaphores =
        Map.copyOf(inImageReadySemaphores);
      this.renderDoneSemaphores =
        Map.copyOf(inRenderDoneSemaphores);
      this.renderDoneFences =
        Map.copyOf(inRenderDoneFences);
      this.presentDoneFences =
        Map.copyOf(inPresentDoneFences);
      this.resources =
        Objects.requireNonNull(inResources, "resources");
      this.extent =
        Objects.requireNonNull(inExtent, "inExtent");
      this.format =
        Objects.requireNonNull(inFormat, "format");
      this.frameIndex =
        new JCSwapchainFrameIndex(0);
      this.id =
        Objects.requireNonNull(inId, "id");
    }

    private static void traceResettingFences(
      final List<VulkanFenceType> fences)
    {
      if (LOG.isTraceEnabled()) {
        LOG.trace("Resetting fences {}", fences);
      }
    }

    private static void traceAcquiredImage(
      final JCSwapchainImageIndex imageIndex)
    {
      if (LOG.isTraceEnabled()) {
        LOG.trace("Acquire: Acquired image index {}", imageIndex);
      }
    }

    private static void traceWaitingForImage(
      final VulkanSemaphoreBinaryType readySemaphore)
    {
      if (LOG.isTraceEnabled()) {
        LOG.trace("Acquire: Waiting for image (signal {})", readySemaphore);
      }
    }

    private static void waitForRenderDoneFence(
      final VulkanLogicalDeviceType device,
      final VulkanFenceType renderDoneFence)
      throws VulkanException, SwapchainRenderingDoneFenceTimedOut
    {
      if (LOG.isTraceEnabled()) {
        LOG.trace("Acquire: Waiting for fence {}", renderDoneFence);
      }

      final var waitStatus =
        device.waitForFence(renderDoneFence, RENDER_FENCE_TIMEOUT.toNanos());

      switch (waitStatus) {
        case VK_WAIT_SUCCEEDED -> {

        }
        case VK_WAIT_TIMED_OUT -> {
          throw new SwapchainRenderingDoneFenceTimedOut(
            "Timed out waiting for render done fence %s"
              .formatted(renderDoneFence)
          );
        }
      }
    }

    @Override
    public void close()
      throws VulkanException
    {
      final var eventDeleted =
        new JCSwapchainJFRSwapchainDeleted();

      try {
        eventDeleted.id = this.id.toString();
        eventDeleted.begin();
        this.resources.close();
      } finally {
        eventDeleted.end();
        eventDeleted.commit();
      }
    }

    public JCSwapchainImageType acquire(
      final VulkanLogicalDeviceType device)
      throws VulkanException,
      SwapchainOutOfDate,
      SwapchainNotReady,
      SwapchainRenderingDoneFenceTimedOut,
      SwapchainAcquireTimedOut
    {
      Objects.requireNonNull(device, "device");

      final var readySemaphore =
        this.imageReadySemaphores.get(this.frameIndex);
      final var renderDoneSemaphore =
        this.renderDoneSemaphores.get(this.frameIndex);
      final var renderDoneFence =
        this.renderDoneFences.get(this.frameIndex);
      final var presentDoneFence =
        this.presentDoneFences.get(this.frameIndex);

      Objects.requireNonNull(readySemaphore, "readySemaphore");
      Objects.requireNonNull(renderDoneSemaphore, "renderDoneSemaphore");
      Objects.requireNonNull(renderDoneFence, "renderDoneFence");
      Objects.requireNonNull(presentDoneFence, "presentDoneFence");

      final var eventAcquire =
        new JCSwapchainJFRSwapchainImageAcquired();
      eventAcquire.id = this.id.toString();
      eventAcquire.frameIndex = this.frameIndex.value();
      eventAcquire.begin();

      final var eventAcquireFailed =
        new JCSwapchainJFRSwapchainImageAcquireFailed();
      eventAcquireFailed.id = eventAcquire.id;
      eventAcquireFailed.frameIndex = eventAcquire.frameIndex;
      eventAcquireFailed.begin();

      try {
        waitForRenderDoneFence(device, renderDoneFence);

        traceWaitingForImage(readySemaphore);

        final var acquisition =
          this.swapchain.acquireImageWithSemaphore(0L, readySemaphore);

        final var imageIndex =
          switch (acquisition) {
            case final VulkanSwapChainOK ok -> {
              yield new JCSwapchainImageIndex(ok.imageIndex());
            }
            case final VulkanSwapChainNotReady _ -> {
              throw new SwapchainNotReady();
            }
            case final VulkanSwapChainOutOfDate _,
                 final VulkanSwapChainSubOptimal _ -> {
              throw new SwapchainOutOfDate();
            }
            case final VulkanSwapChainTimedOut _ -> {
              throw new SwapchainAcquireTimedOut();
            }
          };

        traceAcquiredImage(imageIndex);

        {
          final var fences =
            List.of(renderDoneFence, presentDoneFence);

          traceResettingFences(fences);
          device.resetFences(fences);
        }

        final var image =
          new SwapchainImage(
            this,
            imageIndex,
            this.swapChainImages.get(imageIndex),
            readySemaphore,
            renderDoneSemaphore,
            presentDoneFence,
            this.swapchain,
            renderDoneFence,
            this.format,
            this.extent
          );

        this.frameIndex = this.nextFrameIndex();
        eventAcquire.imageIndex = imageIndex.value();
        eventAcquire.end();
        eventAcquire.commit();
        return image;
      } catch (final Exception e) {
        eventAcquireFailed.errorMessage = e.getMessage();
        eventAcquireFailed.end();
        eventAcquireFailed.commit();
        throw e;
      }
    }

    private JCSwapchainFrameIndex nextFrameIndex()
    {
      return new JCSwapchainFrameIndex(
        (this.frameIndex.value() + 1) % this.swapChainImages.size()
      );
    }

    public boolean isReadyForDeletion()
      throws VulkanException
    {
      for (final var fence : this.presentDoneFences.values()) {
        switch (this.manager.device.getFenceStatus(fence)) {
          case VK_FENCE_SIGNALLED -> {

          }
          case VK_FENCE_UNSIGNALLED -> {
            return false;
          }
        }
      }
      return true;
    }
  }
}
