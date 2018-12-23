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

import com.io7m.jcoronado.allocation_tracker.VulkanHostAllocatorTracker;
import com.io7m.jcoronado.api.VulkanApplicationInfo;
import com.io7m.jcoronado.api.VulkanAttachmentDescription;
import com.io7m.jcoronado.api.VulkanAttachmentReference;
import com.io7m.jcoronado.api.VulkanBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanClearValueColorFloatingPoint;
import com.io7m.jcoronado.api.VulkanCommandBufferBeginInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferCreateInfo;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanCommandPoolCreateInfo;
import com.io7m.jcoronado.api.VulkanComponentMapping;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanExtensions;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanFramebufferCreateInfo;
import com.io7m.jcoronado.api.VulkanFramebufferType;
import com.io7m.jcoronado.api.VulkanGraphicsPipelineCreateInfo;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanImageViewCreateFlag;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import com.io7m.jcoronado.api.VulkanImageViewType;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanLayers;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanMissingRequiredExtensionsException;
import com.io7m.jcoronado.api.VulkanOffset2D;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendAttachmentState;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineInputAssemblyStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineMultisampleStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineRasterizationStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineShaderStageCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineVertexInputStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineViewportStateCreateInfo;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanRectangle2D;
import com.io7m.jcoronado.api.VulkanRenderPassBeginInfo;
import com.io7m.jcoronado.api.VulkanRenderPassCreateInfo;
import com.io7m.jcoronado.api.VulkanResourceException;
import com.io7m.jcoronado.api.VulkanSemaphoreCreateInfo;
import com.io7m.jcoronado.api.VulkanSemaphoreType;
import com.io7m.jcoronado.api.VulkanShaderModuleCreateInfo;
import com.io7m.jcoronado.api.VulkanShaderModuleType;
import com.io7m.jcoronado.api.VulkanSharingMode;
import com.io7m.jcoronado.api.VulkanSubmitInfo;
import com.io7m.jcoronado.api.VulkanSubpassDependency;
import com.io7m.jcoronado.api.VulkanSubpassDependencyType;
import com.io7m.jcoronado.api.VulkanSubpassDescription;
import com.io7m.jcoronado.api.VulkanTemporaryAllocatorType;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.api.VulkanVertexInputAttributeDescription;
import com.io7m.jcoronado.api.VulkanVertexInputBindingDescription;
import com.io7m.jcoronado.api.VulkanViewport;
import com.io7m.jcoronado.extensions.api.VulkanExtKHRSurfaceType;
import com.io7m.jcoronado.extensions.api.VulkanExtKHRSwapChainType;
import com.io7m.jcoronado.extensions.api.VulkanExtKHRSwapChainType.VulkanKHRSwapChainType;
import com.io7m.jcoronado.extensions.api.VulkanPresentInfoKHR;
import com.io7m.jcoronado.extensions.api.VulkanPresentModeKHR;
import com.io7m.jcoronado.extensions.api.VulkanSurfaceCapabilitiesKHR;
import com.io7m.jcoronado.extensions.api.VulkanSurfaceFormatKHR;
import com.io7m.jcoronado.extensions.api.VulkanSwapChainCreateInfo;
import com.io7m.jcoronado.lwjgl.VMALWJGLAllocatorProvider;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLHostAllocatorJeMalloc;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLTemporaryAllocator;
import com.io7m.jcoronado.vma.VMAAllocationCreateInfo;
import com.io7m.jcoronado.vma.VMAAllocatorCreateInfo;
import com.io7m.jmulticlose.core.CloseableCollection;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVulkan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_COLOR_ATTACHMENT_READ_BIT;
import static com.io7m.jcoronado.api.VulkanAccessFlag.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT;
import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_CLEAR;
import static com.io7m.jcoronado.api.VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_DONT_CARE;
import static com.io7m.jcoronado.api.VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_STORE;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_INDEX_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanBufferUsageFlag.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT;
import static com.io7m.jcoronado.api.VulkanCommandBufferLevel.VK_COMMAND_BUFFER_LEVEL_PRIMARY;
import static com.io7m.jcoronado.api.VulkanCommandBufferUsageFlag.VK_COMMAND_BUFFER_USAGE_SIMULTANEOUS_USE_BIT;
import static com.io7m.jcoronado.api.VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_IDENTITY;
import static com.io7m.jcoronado.api.VulkanCullModeFlag.VK_CULL_MODE_BACK_BIT;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_B8G8R8A8_UNORM;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R32G32B32_SFLOAT;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_R32G32_SFLOAT;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_UNDEFINED;
import static com.io7m.jcoronado.api.VulkanFrontFace.VK_FRONT_FACE_CLOCKWISE;
import static com.io7m.jcoronado.api.VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static com.io7m.jcoronado.api.VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED;
import static com.io7m.jcoronado.api.VulkanImageUsageFlag.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT;
import static com.io7m.jcoronado.api.VulkanImageViewKind.VK_IMAGE_VIEW_TYPE_2D;
import static com.io7m.jcoronado.api.VulkanIndexType.VK_INDEX_TYPE_UINT16;
import static com.io7m.jcoronado.api.VulkanMemoryPropertyFlag.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT;
import static com.io7m.jcoronado.api.VulkanPipelineBindPoint.VK_PIPELINE_BIND_POINT_GRAPHICS;
import static com.io7m.jcoronado.api.VulkanPipelineStageFlag.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT;
import static com.io7m.jcoronado.api.VulkanPolygonMode.VK_POLYGON_MODE_FILL;
import static com.io7m.jcoronado.api.VulkanPrimitiveTopology.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST;
import static com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag.VK_QUEUE_GRAPHICS_BIT;
import static com.io7m.jcoronado.api.VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT;
import static com.io7m.jcoronado.api.VulkanShaderStageFlag.VK_SHADER_STAGE_FRAGMENT_BIT;
import static com.io7m.jcoronado.api.VulkanShaderStageFlag.VK_SHADER_STAGE_VERTEX_BIT;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_CONCURRENT;
import static com.io7m.jcoronado.api.VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE;
import static com.io7m.jcoronado.api.VulkanSubpassContents.VK_SUBPASS_CONTENTS_INLINE;
import static com.io7m.jcoronado.api.VulkanVertexInputRate.VK_VERTEX_INPUT_RATE_VERTEX;
import static com.io7m.jcoronado.extensions.api.VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR;
import static com.io7m.jcoronado.extensions.api.VulkanCompositeAlphaFlagKHR.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR;
import static com.io7m.jcoronado.extensions.api.VulkanPresentModeKHR.VK_PRESENT_MODE_FIFO_KHR;
import static com.io7m.jcoronado.extensions.api.VulkanPresentModeKHR.VK_PRESENT_MODE_IMMEDIATE_KHR;
import static com.io7m.jcoronado.extensions.api.VulkanPresentModeKHR.VK_PRESENT_MODE_MAILBOX_KHR;
import static com.io7m.jcoronado.vma.VMAMemoryUsage.VMA_MEMORY_USAGE_GPU_ONLY;
import static java.util.concurrent.TimeUnit.SECONDS;

public final class HelloVulkanWithVMA
{
  private static final Logger LOG = LoggerFactory.getLogger(HelloVulkanWithVMA.class);

  private static final GLFWErrorCallback GLFW_ERROR_CALLBACK =
    GLFWErrorCallback.createPrint();

  private static final int VERTEX_POSITION_SIZE = 2 * 4;
  private static final int VERTEX_COLOR_SIZE = 3 * 4;
  private static final int VERTEX_SIZE = VERTEX_POSITION_SIZE + VERTEX_COLOR_SIZE;

  private static final class Vertex
  {
    public final double x;
    public final double y;
    public final double r;
    public final double g;
    public final double b;

    public Vertex(
      final double in_x,
      final double in_y,
      final double in_r,
      final double in_g,
      final double in_b)
    {
      this.x = in_x;
      this.y = in_y;
      this.r = in_r;
      this.g = in_g;
      this.b = in_b;
    }
  }

  private static final List<Vertex> VERTICES =
    List.of(
      new Vertex(0.0, -0.5, 1.0, 0.0, 0.0),
      new Vertex(0.5, 0.5, 0.0, 1.0, 0.0),
      new Vertex(-0.5, 0.5, 0.0, 0.0, 1.0));

  private HelloVulkanWithVMA()
  {

  }

  public static void main(
    final String[] args)
    throws VulkanException, IOException
  {
    GLFW_ERROR_CALLBACK.set();

    final var host_allocator_main =
      new VulkanLWJGLHostAllocatorJeMalloc();
    final var host_allocator =
      new VulkanHostAllocatorTracker(host_allocator_main);

    final var finished = new AtomicBoolean(false);

    /*
     * Start a background thread that waits for the user to type a single character into the
     * terminal, and sets the "finished" flag when that happens.
     */

    final var exec = Executors.newFixedThreadPool(1);
    exec.execute(() -> {
      try {
        System.in.read();
        LOG.debug("finished");
        finished.set(true);
      } catch (final IOException e) {
        LOG.error("i/o error: ", e);
      }
    });

    /*
     * Create an allocator for temporary objects.
     */

    final var alloc =
      VulkanLWJGLTemporaryAllocator.create();
    final Supplier<VulkanException> exception_supplier =
      () -> new VulkanResourceException("Could not close one or more resources.");

    try (var resources =
           CloseableCollection.create(exception_supplier)) {

      final var window = createWindow();

      /*
       * Create an instance provider.
       */

      final var instances = VulkanLWJGLInstanceProvider.create();
      LOG.debug("instance provider: {} {}", instances.providerName(), instances.providerVersion());

      final var available_extensions =
        instances.extensions();
      final var available_layers =
        instances.layers();

      /*
       * Determine which extensions are required by the window system, and which layers are
       * required.
       */

      final var required_layers =
        Set.of("VK_LAYER_LUNARG_standard_validation");
      final var required_extensions =
        requiredGLFWExtensions();

      available_extensions.forEach(HelloVulkanWithVMA::showInstanceAvailableExtension);
      available_layers.forEach(HelloVulkanWithVMA::showInstanceAvailableLayer);
      required_extensions.forEach(HelloVulkanWithVMA::showInstanceRequiredExtension);
      required_layers.forEach(HelloVulkanWithVMA::showInstanceRequiredLayer);

      /*
       * Filter the available extensions and layers based on the requirements expressed above.
       * If any required extensions are not available, an exception is raised.
       */

      final var enable_extensions =
        VulkanExtensions.filterRequiredExtensions(
          available_extensions,
          Set.of(),
          required_extensions);

      final var enable_layers =
        VulkanLayers.filterRequiredLayers(
          available_layers,
          Set.of(),
          required_layers);

      /*
       * Create a new instance.
       */

      final var create_info =
        VulkanInstanceCreateInfo.builder()
          .setApplicationInfo(
            VulkanApplicationInfo.of(
              "com.io7m.jcoronado.tests.Demo",
              VulkanVersions.encode(0, 0, 1),
              "com.io7m.jcoronado.tests",
              VulkanVersions.encode(0, 0, 1),
              VulkanVersions.encode(1, 0, 0)))
          .setEnabledExtensions(enable_extensions)
          .setEnabledLayers(enable_layers)
          .build();

      final var instance =
        resources.add(instances.createInstance(create_info, Optional.of(host_allocator)));

      /*
       * Get access to the VK_KHR_surface extension in order to produce a renderable
       * surface from the created window.
       */

      final var khr_surface_ext =
        instance.findEnabledExtension("VK_KHR_surface", VulkanExtKHRSurfaceType.class)
          .orElseThrow(() -> new IllegalStateException("Missing VK_KHR_surface extension"));

      final var surface =
        khr_surface_ext.surfaceFromWindow(instance, window);

      /*
       * List the available physical devices and pick the "best" one.
       */

      final var physical_devices = instance.physicalDevices();

      final var physical_device =
        resources.add(pickPhysicalDeviceOrAbort(khr_surface_ext, surface, physical_devices));

      LOG.debug("physical device: {}", physical_device);

      /*
       * Require the VK_KHR_get_memory_requirements2 extension in order to use VMA.
       */

      final var device_extensions =
        physical_device.extensions(Optional.empty());

      device_extensions.forEach(HelloVulkanWithVMA::showPhysicalDeviceAvailableExtension);
      if (!device_extensions.containsKey("VK_KHR_get_memory_requirements2")) {
        throw new VulkanMissingRequiredExtensionsException(
          Set.of("VK_KHR_get_memory_requirements2"),
          "Missing required extension");
      }

      /*
       * Determine the format, presentation mode, and size of the surface that will be
       * used for rendering.
       */

      final var surface_format =
        pickSurfaceFormat(physical_device, khr_surface_ext, surface);
      final var surface_present =
        pickPresentationMode(physical_device, khr_surface_ext, surface);
      final var surface_caps =
        khr_surface_ext.surfaceCapabilities(physical_device, surface);
      final var surface_extent =
        pickExtent(surface_caps);

      LOG.debug("selected surface format: {}", surface_format);
      LOG.debug("selected surface presentation mode: {}", surface_present);
      LOG.debug("selected surface extent: {}", surface_extent);

      /*
       * We know that the selected physical device has a graphics queue family, and a queue
       * family for presentation. We don't know if these are the same queue families or not.
       */

      final var graphics_queue_props =
        physical_device.queueFamilies()
          .stream()
          .filter(queue -> queue.queueFlags().contains(VK_QUEUE_GRAPHICS_BIT))
          .findFirst()
          .orElseThrow(() -> new IllegalStateException("Could not find graphics queue"));

      final var presentation_queue_props =
        khr_surface_ext.surfaceSupport(physical_device, surface)
          .stream()
          .findFirst()
          .orElseThrow(() -> new IllegalStateException("Could not find presentation queue"));

      /*
       * Put together the information needed to create a logical device. In this case,
       * all that is really needed is to specify the creation of one or more queues.
       */

      final var logical_device_info_builder =
        VulkanLogicalDeviceCreateInfo.builder();

      logical_device_info_builder.addQueueCreateInfos(
        VulkanLogicalDeviceQueueCreateInfo.builder()
          .setQueueCount(1)
          .setQueueFamilyIndex(graphics_queue_props.queueFamilyIndex())
          .setQueuePriorities(1.0f)
          .build());

      if (graphics_queue_props.queueFamilyIndex() != presentation_queue_props.queueFamilyIndex()) {
        logical_device_info_builder.addQueueCreateInfos(
          VulkanLogicalDeviceQueueCreateInfo.builder()
            .setQueueCount(1)
            .setQueueFamilyIndex(presentation_queue_props.queueFamilyIndex())
            .setQueuePriorities(1.0f)
            .build());
      }

      /*
       * Create the logical device.
       */

      final var device = resources.add(
        physical_device.createLogicalDevice(
          logical_device_info_builder
            .addEnabledExtensions("VK_KHR_swapchain")
            .addEnabledExtensions("VK_KHR_get_memory_requirements2")
            .build()));

      LOG.debug("logical device: {}", device);

      /*
       * Create a VMA allocator provider.
       */

      final var vma_allocators = VMALWJGLAllocatorProvider.create();
      LOG.debug(
        "vma allocator provider: {} {}",
        vma_allocators.providerName(),
        vma_allocators.providerVersion());

      final var vma_allocator =
        resources.add(
          vma_allocators.createAllocator(
            VMAAllocatorCreateInfo.builder()
              .setLogicalDevice(device)
              .build()));

      /*
       * Find the graphics and presentation queues.
       */

      final var graphics_queue =
        device.queue(graphics_queue_props.queueFamilyIndex(), 0)
          .orElseThrow(() -> new IllegalStateException("Could not find graphics queue"));
      final var presentation_queue =
        device.queue(presentation_queue_props.queueFamilyIndex(), 0)
          .orElseThrow(() -> new IllegalStateException("Could not find presentation queue"));

      LOG.debug("graphics queue: {}", graphics_queue);
      LOG.debug("presentation queue: {}", presentation_queue);

      /*
       * Create a swap chain used to display images onscreen.
       */

      final var khr_swapchain_ext =
        getSwapChainExtension(device);

      final var swap_chain =
        resources.add(createSwapChain(
          khr_swapchain_ext,
          surface,
          surface_format,
          surface_present,
          surface_caps,
          surface_extent,
          device,
          graphics_queue,
          presentation_queue));

      final var images = swap_chain.images();

      final var views =
        images.stream()
          .map(image -> createImageViewForImage(surface_format, device, image))
          .map(resources::add)
          .collect(Collectors.toList());

      /*
       * Load a shader module.
       */

      final var data = readShaderModule("shaders.spv");

      final var shaders =
        resources.add(createShaderModule(device, alloc, data));

      /*
       * Configure the render pass.
       *
       * This specifies that:
       *
       * We don't want multisampling.
       *
       * Reads from the image attachment should behave as if the attachment has been cleared before
       * rendering.
       *
       * Writes to the image attachment should be stored in the image.
       *
       * We don't care about reads or writes to the stencil buffer.
       *
       * We don't care about the initial layout of the image.
       *
       * The final layout of the image must be something usable for presentation to the screen.
       */

      final var color_attachment_description =
        VulkanAttachmentDescription.builder()
          .setFormat(surface_format.format())
          .setSamples(VK_SAMPLE_COUNT_1_BIT)
          .setLoadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
          .setStoreOp(VK_ATTACHMENT_STORE_OP_STORE)
          .setStencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
          .setStencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
          .setInitialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
          .setFinalLayout(VK_IMAGE_LAYOUT_PRESENT_SRC_KHR)
          .build();

      final var color_attachment_reference =
        VulkanAttachmentReference.builder()
          .setAttachment(0)
          .setLayout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
          .build();

      final var subpass_description =
        VulkanSubpassDescription.builder()
          .setPipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS)
          .addColorAttachments(color_attachment_reference)
          .build();

      final var subpass_dependency =
        VulkanSubpassDependency.builder()
          .setSrcSubpass(VulkanSubpassDependencyType.EXTERNAL)
          .addSrcStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
          .setDstSubpass(0)
          .addDstStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
          .addDstAccessMask(VK_ACCESS_COLOR_ATTACHMENT_READ_BIT)
          .addDstAccessMask(VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT)
          .build();

      final var render_pass_create_info =
        VulkanRenderPassCreateInfo.builder()
          .addAttachments(color_attachment_description)
          .addSubpasses(subpass_description)
          .addDependencies(subpass_dependency)
          .build();

      final var render_pass =
        resources.add(device.createRenderPass(render_pass_create_info));

      /*
       * Configure the rendering pipeline.
       */

      final var vertex_stage_info =
        VulkanPipelineShaderStageCreateInfo.builder()
          .setStage(VK_SHADER_STAGE_VERTEX_BIT)
          .setModule(shaders)
          .setShaderEntryPoint("R3_clip_triangle_vert_main")
          .build();

      final var fragment_stage_info =
        VulkanPipelineShaderStageCreateInfo.builder()
          .setStage(VK_SHADER_STAGE_FRAGMENT_BIT)
          .setModule(shaders)
          .setShaderEntryPoint("R3_clip_triangle_frag_main")
          .build();

      final var vertex_binding_description =
        VulkanVertexInputBindingDescription.builder()
          .setBinding(0)
          .setInputRate(VK_VERTEX_INPUT_RATE_VERTEX)
          .setStride(VERTEX_SIZE)
          .build();

      final var vertex_position_attribute =
        VulkanVertexInputAttributeDescription.builder()
          .setBinding(0)
          .setLocation(0)
          .setFormat(VK_FORMAT_R32G32_SFLOAT)
          .setOffset(0)
          .build();

      final var vertex_color_attribute =
        VulkanVertexInputAttributeDescription.builder()
          .setBinding(0)
          .setLocation(1)
          .setFormat(VK_FORMAT_R32G32B32_SFLOAT)
          .setOffset(VERTEX_POSITION_SIZE)
          .build();

      final var vertex_input_info =
        VulkanPipelineVertexInputStateCreateInfo.builder()
          .addVertexBindingDescriptions(vertex_binding_description)
          .addVertexAttributeDescriptions(vertex_position_attribute)
          .addVertexAttributeDescriptions(vertex_color_attribute)
          .build();

      final var input_assembly_info =
        VulkanPipelineInputAssemblyStateCreateInfo.builder()
          .setTopology(VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST)
          .setPrimitiveRestartEnable(false)
          .build();

      final var viewport_state_info =
        VulkanPipelineViewportStateCreateInfo.builder()
          .addScissors(VulkanRectangle2D.of(VulkanOffset2D.of(0, 0), surface_extent))
          .addViewports(VulkanViewport.of(
            0.0f,
            0.0f,
            (float) surface_extent.width(),
            (float) surface_extent.height(),
            0.0f,
            1.0f))
          .build();

      final var rasterizer =
        VulkanPipelineRasterizationStateCreateInfo.builder()
          .setCullMode(EnumSet.of(VK_CULL_MODE_BACK_BIT))
          .setDepthBiasClamp(0.0f)
          .setDepthBiasConstantFactor(0.0f)
          .setDepthBiasEnable(false)
          .setDepthBiasSlopeFactor(0.0f)
          .setDepthClampEnable(false)
          .setFrontFace(VK_FRONT_FACE_CLOCKWISE)
          .setLineWidth(1.0f)
          .setPolygonMode(VK_POLYGON_MODE_FILL)
          .setRasterizerDiscardEnable(false)
          .build();

      final var blend_state =
        VulkanPipelineColorBlendAttachmentState.builder()
          .setEnable(false)
          .build();

      final var color_blending =
        VulkanPipelineColorBlendStateCreateInfo.builder()
          .addAttachments(blend_state)
          .build();

      final var pipeline_layout_info =
        VulkanPipelineLayoutCreateInfo.builder()
          .build();

      final var pipeline_layout =
        resources.add(device.createPipelineLayout(pipeline_layout_info));

      final var multisample =
        VulkanPipelineMultisampleStateCreateInfo.builder()
          .setAlphaToCoverageEnable(false)
          .setAlphaToOneEnable(false)
          .setMinSampleShading(1.0f)
          .setRasterizationSamples(VK_SAMPLE_COUNT_1_BIT)
          .setSampleShadingEnable(false)
          .build();

      final var pipeline_info =
        VulkanGraphicsPipelineCreateInfo.builder()
          .addStages(fragment_stage_info)
          .addStages(vertex_stage_info)
          .setColorBlendState(color_blending)
          .setInputAssemblyState(input_assembly_info)
          .setLayout(pipeline_layout)
          .setMultisampleState(multisample)
          .setRasterizationState(rasterizer)
          .setRenderPass(render_pass)
          .setSubpass(0)
          .setVertexInputState(vertex_input_info)
          .setViewportState(viewport_state_info)
          .build();

      final var pipeline =
        resources.add(device.createPipeline(pipeline_info));

      /*
       * Create framebuffers.
       */

      final List<VulkanFramebufferType> framebuffers = new ArrayList<>(images.size());

      for (var index = 0; index < images.size(); ++index) {
        final var framebuffer_info =
          VulkanFramebufferCreateInfo.builder()
            .addAttachments(views.get(index))
            .setHeight(surface_extent.height())
            .setWidth(surface_extent.width())
            .setLayers(1)
            .setRenderPass(render_pass)
            .build();

        final var framebuffer =
          resources.add(device.createFramebuffer(framebuffer_info));

        framebuffers.add(framebuffer);
      }

      /*
       * Create vertex buffer.
       */

      final var vertex_buffer_create_info =
        VulkanBufferCreateInfo.builder()
          .setSize(3L * (long) VERTEX_SIZE)
          .addUsageFlags(VK_BUFFER_USAGE_VERTEX_BUFFER_BIT)
          .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
          .build();

      final var vma_alloc_create_info =
        VMAAllocationCreateInfo.builder()
          .setUsage(VMA_MEMORY_USAGE_GPU_ONLY)
          .addRequiredFlags(VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT)
          .setMemoryTypeBits(0L)
          .build();

      final var vma_vertex_buffer_result =
        vma_allocator.createBuffer(vma_alloc_create_info, vertex_buffer_create_info);

      final var vertex_buffer =
        resources.add(vma_vertex_buffer_result.result());

      LOG.trace("allocated vertex buffer: {}", vertex_buffer);

      /*
       * Populate vertex buffer by mapping the memory and writing to it. Closing the mapped
       * memory will automatically unmap it.
       */

      try (var vertex_mapped = vma_allocator.mapMemory(vma_vertex_buffer_result.allocation())) {
        final var buffer = vertex_mapped.asByteBuffer();

        final var v0 = VERTICES.get(0);
        buffer.putFloat(0, (float) v0.x);
        buffer.putFloat(4, (float) v0.y);
        buffer.putFloat(8, (float) v0.r);
        buffer.putFloat(12, (float) v0.g);
        buffer.putFloat(16, (float) v0.b);

        final var v1 = VERTICES.get(1);
        buffer.putFloat(20, (float) v1.x);
        buffer.putFloat(24, (float) v1.y);
        buffer.putFloat(28, (float) v1.r);
        buffer.putFloat(32, (float) v1.g);
        buffer.putFloat(36, (float) v1.b);

        final var v2 = VERTICES.get(2);
        buffer.putFloat(40, (float) v2.x);
        buffer.putFloat(44, (float) v2.y);
        buffer.putFloat(48, (float) v2.r);
        buffer.putFloat(52, (float) v2.g);
        buffer.putFloat(56, (float) v2.b);
      }

      /*
       * Create index buffer.
       */

      final var index_buffer_create_info =
        VulkanBufferCreateInfo.builder()
          .setSize(3L * 2L)
          .addUsageFlags(VK_BUFFER_USAGE_INDEX_BUFFER_BIT)
          .setSharingMode(VK_SHARING_MODE_EXCLUSIVE)
          .build();

      final var vma_index_buffer_result =
        vma_allocator.createBuffer(vma_alloc_create_info, index_buffer_create_info);

      final var index_buffer =
        resources.add(vma_index_buffer_result.result());

      LOG.trace("allocated index buffer: {}", index_buffer);

      /*
       * Populate index buffer by mapping the memory and writing to it. Closing the mapped
       * memory will automatically unmap it.
       */

      try (var index_mapped = vma_allocator.mapMemory(vma_index_buffer_result.allocation())) {
        final var buffer = index_mapped.asByteBuffer();
        buffer.putShort(0, (short) 0);
        buffer.putShort(2, (short) 1);
        buffer.putShort(4, (short) 2);
      }

      /*
       * Create a command pool and buffers. If the graphics and presentation queues are separate,
       * then separate command pools are required.
       */

      final var graphics_pool_info =
        VulkanCommandPoolCreateInfo.builder()
          .setQueueFamilyIndex(graphics_queue.queueIndex())
          .build();

      final var graphics_command_pool =
        resources.add(device.createCommandPool(graphics_pool_info));

      final var command_buffer_count = swap_chain.images().size();
      final var graphics_command_buffer_info =
        VulkanCommandBufferCreateInfo.builder()
          .setCount(command_buffer_count)
          .setLevel(VK_COMMAND_BUFFER_LEVEL_PRIMARY)
          .setPool(graphics_command_pool)
          .build();

      final var graphics_command_buffers =
        device.createCommandBuffers(graphics_command_buffer_info);

      /*
       * Create a pair of semaphores for synchronizing command execution.
       */

      final var render_finished =
        resources.add(device.createSemaphore(
          VulkanSemaphoreCreateInfo.builder()
            .build()));

      final var image_available =
        resources.add(device.createSemaphore(
          VulkanSemaphoreCreateInfo.builder()
            .build()));

      /*
       * Start recording commands.
       */

      for (var index = 0; index < command_buffer_count; ++index) {
        final var framebuffer = framebuffers.get(index);

        final var graphics_command_buffer =
          graphics_command_buffers.get(index);

        final var begin_info =
          VulkanCommandBufferBeginInfo.builder()
            .addFlags(VK_COMMAND_BUFFER_USAGE_SIMULTANEOUS_USE_BIT)
            .build();

        final var render_info =
          VulkanRenderPassBeginInfo.builder()
            .setFramebuffer(framebuffer)
            .setRenderArea(VulkanRectangle2D.of(VulkanOffset2D.of(0, 0), surface_extent))
            .setRenderPass(render_pass)
            .addClearValues(VulkanClearValueColorFloatingPoint.of(0.0f, 0.0f, 0.0f, 1.0f))
            .build();

        graphics_command_buffer.beginCommandBuffer(begin_info);
        graphics_command_buffer.beginRenderPass(render_info, VK_SUBPASS_CONTENTS_INLINE);
        graphics_command_buffer.bindPipeline(VK_PIPELINE_BIND_POINT_GRAPHICS, pipeline);
        graphics_command_buffer.bindVertexBuffers(
          0, 1, List.of(vertex_buffer), List.of(Long.valueOf(0L)));
        graphics_command_buffer.bindIndexBuffer(index_buffer, 0L, VK_INDEX_TYPE_UINT16);
        graphics_command_buffer.drawIndexed(3, 1, 0, 0, 0);
        graphics_command_buffer.endRenderPass();
        graphics_command_buffer.endCommandBuffer();
      }

      /*
       * Start rendering frames.
       */

      var frame = 0;
      while (!finished.get()) {
        drawFrame(
          khr_swapchain_ext,
          swap_chain,
          image_available,
          render_finished,
          graphics_command_buffers,
          graphics_queue,
          presentation_queue);

        if (frame % 10_000 == 0) {
          LOG.debug(
            "allocated command scoped memory:  {} octets",
            Long.valueOf(host_allocator.allocatedCommandScopeOctets()));
          LOG.debug(
            "allocated object scoped memory:   {} octets",
            Long.valueOf(host_allocator.allocatedObjectScopeOctets()));
          LOG.debug(
            "allocated cache scoped memory:    {} octets",
            Long.valueOf(host_allocator.allocatedCacheScopeOctets()));
          LOG.debug(
            "allocated device scoped memory:   {} octets",
            Long.valueOf(host_allocator.allocatedDeviceScopeOctets()));
          LOG.debug(
            "allocated instance scoped memory: {} octets",
            Long.valueOf(host_allocator.allocatedInstanceScopeOctets()));
        }
        ++frame;
      }

      /*
       * Wait until the device is idle before exiting.
       */

      LOG.debug("waiting for device to idle");
      device.waitIdle();

    } catch (final VulkanException e) {
      LOG.error("vulkan error: ", e);
      throw e;
    } finally {
      GLFW_ERROR_CALLBACK.close();
      exec.shutdown();
      try {
        exec.awaitTermination(5L, SECONDS);
      } catch (final InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Draw a single frame.
   */

  private static void drawFrame(
    final VulkanExtKHRSwapChainType khr_swapchain_ext,
    final VulkanKHRSwapChainType swap_chain,
    final VulkanSemaphoreType image_available,
    final VulkanSemaphoreType render_finished,
    final List<VulkanCommandBufferType> graphics_command_buffers,
    final VulkanQueueType graphics_queue,
    final VulkanQueueType queue_presentation)
    throws VulkanException
  {
    /*
     * Try to acquire an image from the swap chain, waiting indefinitely until one is available.
     * There isn't really anything sensible that we can do if an image can't be acquired in this
     * example code, so all that will happen is that the code will immediately try again.
     */

    final var acquisition =
      swap_chain.acquireImageWithSemaphore(0xffff_ffff_ffff_ffffL, image_available);

    final var image_index_option = acquisition.imageIndex();
    if (!image_index_option.isPresent()) {
      LOG.error("could not acquire image");
      return;
    }

    final var image_index = image_index_option.getAsInt();

    final var graphics_command_buffer =
      graphics_command_buffers.get(image_index);

    /*
     * Wait until the image is available (via the image available semaphore) before writing
     * any color data to the image. When writing has completed, signal that rendering has finished
     * (via the render finished semaphore).
     */

    final var submit_info =
      VulkanSubmitInfo.builder()
        .addWaitSemaphores(image_available)
        .addWaitStageMasks(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
        .addCommandBuffers(graphics_command_buffer)
        .addSignalSemaphores(render_finished)
        .build();

    graphics_queue.submit(List.of(submit_info), Optional.empty());

    /*
     * Tell the presentation queue to present.
     */

    final var presentation_info =
      VulkanPresentInfoKHR.builder()
        .addImageIndices(image_index)
        .addSwapChains(swap_chain)
        .addWaitSemaphores(render_finished)
        .build();

    khr_swapchain_ext.queuePresent(queue_presentation, presentation_info);

    /*
     * Wait until the presentation operation has finished before continuing. The reason for doing
     * this is that it's possible to submit new work to the GPU at a vastly quicker rate than it
     * can actually process it, meaning the queue of commands grows indefinitely and gives the
     * appearance of a memory leak. Waiting for the presentation queue is not the most efficient
     * way that this can be handled (because it implies that the GPU is waiting when it could
     * otherwise be rendering the next frame), but it is the simplest.
     */

    queue_presentation.waitIdle();
  }

  private static VulkanShaderModuleType createShaderModule(
    final VulkanLogicalDeviceType device,
    final VulkanTemporaryAllocatorType allocator,
    final byte[] data)
    throws VulkanException
  {
    return allocator.withAllocationBufferInitialized(
      data,
      4L,
      buffer -> device.createShaderModule(
        VulkanShaderModuleCreateInfo.of(Set.of(), buffer, (long) buffer.capacity())));
  }

  private static byte[] readShaderModule(
    final String name)
    throws IOException
  {
    try (var input = HelloVulkanWithVMA.class.getResourceAsStream(name)) {
      return input.readAllBytes();
    }
  }

  private static VulkanImageViewType createImageViewForImage(
    final VulkanSurfaceFormatKHR surface_format,
    final VulkanLogicalDeviceType device,
    final VulkanImageType image)
  {
    try {
      final var range =
        VulkanImageSubresourceRange.of(
          Set.of(VK_IMAGE_ASPECT_COLOR_BIT),
          0,
          1,
          0,
          1);

      final Set<VulkanImageViewCreateFlag> flags = Set.of();
      return device.createImageView(VulkanImageViewCreateInfo.of(
        flags,
        image,
        VK_IMAGE_VIEW_TYPE_2D,
        surface_format.format(),
        VulkanComponentMapping.of(
          VK_COMPONENT_SWIZZLE_IDENTITY,
          VK_COMPONENT_SWIZZLE_IDENTITY,
          VK_COMPONENT_SWIZZLE_IDENTITY,
          VK_COMPONENT_SWIZZLE_IDENTITY),
        range));
    } catch (final VulkanException e) {
      throw new VulkanUncheckedException(e);
    }
  }

  private static VulkanKHRSwapChainType createSwapChain(
    final VulkanExtKHRSwapChainType khr_swapchain_ext,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface,
    final VulkanSurfaceFormatKHR surface_format,
    final VulkanPresentModeKHR surface_present,
    final VulkanSurfaceCapabilitiesKHR surface_caps,
    final VulkanExtent2D surface_extent,
    final VulkanLogicalDeviceType device,
    final VulkanQueueType graphics_queue,
    final VulkanQueueType presentation_queue)
    throws VulkanException
  {
    final var minimum_image_count =
      pickMinimumImageCount(surface_caps);
    final List<Integer> queue_indices = new ArrayList<>();
    final var image_sharing_mode =
      pickImageSharingMode(graphics_queue, presentation_queue, queue_indices);
    final var image_usage_flags =
      Set.of(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT);
    final var surface_alpha_flags =
      Set.of(VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR);

    LOG.debug("swap chain image count: {}", Integer.valueOf(minimum_image_count));
    LOG.debug("swap chain image mode: {}", image_sharing_mode);

    final var swap_chain_create_info =
      VulkanSwapChainCreateInfo.of(
        surface,
        minimum_image_count,
        surface_format.format(),
        surface_format.colorSpace(),
        surface_extent,
        1,
        image_usage_flags,
        image_sharing_mode,
        queue_indices,
        surface_caps.currentTransform(),
        surface_alpha_flags,
        surface_present,
        true,
        Optional.empty());

    return khr_swapchain_ext.swapChainCreate(device, swap_chain_create_info);
  }

  private static VulkanPhysicalDeviceType pickPhysicalDeviceOrAbort(
    final VulkanExtKHRSurfaceType khr_surface_ext,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface,
    final List<VulkanPhysicalDeviceType> physical_devices)
    throws VulkanException
  {
    return pickPhysicalDevice(khr_surface_ext, surface, physical_devices)
      .orElseThrow(() -> new IllegalStateException("No suitable device found"));
  }

  private static VulkanExtKHRSwapChainType getSwapChainExtension(
    final VulkanLogicalDeviceType device)
    throws VulkanException
  {
    return device.findEnabledExtension("VK_KHR_swapchain", VulkanExtKHRSwapChainType.class)
      .orElseThrow(() -> new IllegalStateException("Missing VK_KHR_swapchain extension"));
  }

  private static VulkanSharingMode pickImageSharingMode(
    final VulkanQueueType graphics_queue,
    final VulkanQueueType presentation_queue,
    final List<Integer> queue_indices)
  {
    /*
     * If the graphics and presentation queues are separate families, then add the indices of
     * those families into the given list and enable concurrent sharing mode. Otherwise, don't
     * add any indices, and use exclusive sharing mode.
     */

    final var graphics_family = graphics_queue.queueFamilyProperties().queueFamilyIndex();
    final var presentation_family = presentation_queue.queueFamilyProperties().queueFamilyIndex();
    if (graphics_family != presentation_family) {
      queue_indices.add(Integer.valueOf(graphics_family));
      queue_indices.add(Integer.valueOf(presentation_family));
      return VK_SHARING_MODE_CONCURRENT;
    }
    return VK_SHARING_MODE_EXCLUSIVE;
  }

  private static int pickMinimumImageCount(
    final VulkanSurfaceCapabilitiesKHR surface_caps)
  {
    /*
     * Select the minimum number of images required to do double-buffering.
     */

    final var count = surface_caps.minImageCount();
    if (surface_caps.maxImageCount() > 0 && count > surface_caps.maxImageCount()) {
      return surface_caps.maxImageCount();
    }
    return count;
  }

  private static VulkanExtent2D pickExtent(
    final VulkanSurfaceCapabilitiesKHR surface_caps)
  {
    /*
     * Work out the extent of the rendered image based on the implementation-defined supported
     * limits.
     */

    if (surface_caps.currentExtent().width() != 0xffff_ffff) {
      return surface_caps.currentExtent();
    }

    return VulkanExtent2D.of(
      Math.max(
        surface_caps.minImageExtent().width(),
        Math.min(surface_caps.maxImageExtent().width(), 640)),
      Math.max(
        surface_caps.minImageExtent().height(),
        Math.min(surface_caps.maxImageExtent().height(), 480))
    );
  }

  private static VulkanPresentModeKHR pickPresentationMode(
    final VulkanPhysicalDeviceType device,
    final VulkanExtKHRSurfaceType khr_surface_ext,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface)
    throws VulkanException
  {
    /*
     * Pick the best presentation mode available.
     */

    final var modes =
      khr_surface_ext.surfacePresentModes(device, surface);

    var preferred = VK_PRESENT_MODE_FIFO_KHR;
    for (final var mode : modes) {
      if (mode == VK_PRESENT_MODE_MAILBOX_KHR) {
        return mode;
      }
      if (mode == VK_PRESENT_MODE_IMMEDIATE_KHR) {
        preferred = mode;
      }
    }

    return preferred;
  }

  private static VulkanSurfaceFormatKHR pickSurfaceFormat(
    final VulkanPhysicalDeviceType device,
    final VulkanExtKHRSurfaceType khr_surface_ext,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface)
    throws VulkanException
  {
    final var formats = khr_surface_ext.surfaceFormats(device, surface);

    /*
     * If there are no formats, try a commonly supported one.
     */

    if (formats.isEmpty()) {
      return VulkanSurfaceFormatKHR.of(
        VK_FORMAT_B8G8R8A8_UNORM,
        VK_COLOR_SPACE_SRGB_NONLINEAR_KHR);
    }

    /*
     * If there's one VK_FORMAT_UNDEFINED format, then this means that the implementation
     * doesn't have a preferred format and anything can be used.
     */

    if (formats.size() == 1) {
      final var format0 = formats.get(0);
      if (format0.format() == VK_FORMAT_UNDEFINED) {
        return VulkanSurfaceFormatKHR.of(
          VK_FORMAT_B8G8R8A8_UNORM,
          VK_COLOR_SPACE_SRGB_NONLINEAR_KHR);
      }
    }

    /*
     * Otherwise, look for a linear BGRA unsigned normalized format, with an SRGB color space.
     */

    for (final var format : formats) {
      if (format.format() == VK_FORMAT_B8G8R8A8_UNORM
        && format.colorSpace() == VK_COLOR_SPACE_SRGB_NONLINEAR_KHR) {
        return format;
      }
    }

    /*
     * Otherwise, use whatever was first.
     */

    return formats.get(0);
  }

  private static Optional<VulkanPhysicalDeviceType> pickPhysicalDevice(
    final VulkanExtKHRSurfaceType khr_surface_ext,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface,
    final List<VulkanPhysicalDeviceType> devices)
    throws VulkanException
  {
    /*
     * Consider a device usable if it has a queue capable of graphics operations, and a queue
     * capable of presentation operations (these do not need to be the same queue).
     *
     * Then, arbitrarily pick the first one matching these requirements. Real programs should
     * give the user control over the selection.
     */

    try {
      return devices.stream()
        .filter(HelloVulkanWithVMA::physicalDeviceHasSwapChainExtension)
        .filter(HelloVulkanWithVMA::physicalDeviceHasGraphicsQueue)
        .filter(device -> physicalDeviceHasPresentationQueue(khr_surface_ext, surface, device))
        .findFirst();
    } catch (final VulkanUncheckedException e) {
      throw e.getCause();
    }
  }

  private static boolean physicalDeviceHasSwapChainExtension(
    final VulkanPhysicalDeviceType device)
  {
    /*
     * Determine if the device supports the swapchain extension.
     */

    try {
      LOG.debug(
        "checking device \"{}\" for VK_KHR_swapchain support",
        device.properties().name());

      return device.extensions(Optional.empty()).containsKey("VK_KHR_swapchain");
    } catch (final VulkanException e) {
      throw new VulkanUncheckedException(e);
    }
  }

  private static boolean physicalDeviceHasPresentationQueue(
    final VulkanExtKHRSurfaceType khr_surface_ext,
    final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface,
    final VulkanPhysicalDeviceType device)
  {
    /*
     * Determine which, if any, of the available queues are capable of "presentation". That
     * is, rendering to a surface that will appear onscreen.
     */

    try {
      LOG.debug(
        "checking device \"{}\" for presentation support",
        device.properties().name());

      final var queues_presentable =
        khr_surface_ext.surfaceSupport(device, surface);
      return !queues_presentable.isEmpty();
    } catch (final VulkanException e) {
      throw new VulkanUncheckedException(e);
    }
  }

  private static boolean physicalDeviceHasGraphicsQueue(
    final VulkanPhysicalDeviceType device)
  {
    try {
      LOG.debug(
        "checking device \"{}\" for graphics queue support",
        device.properties().name());

      final var queues = device.queueFamilies();
      return queues.stream().anyMatch(queue -> queue.queueFlags().contains(VK_QUEUE_GRAPHICS_BIT));
    } catch (final VulkanException e) {
      throw new VulkanUncheckedException(e);
    }
  }

  private static long createWindow()
  {
    if (!GLFW.glfwInit()) {
      throw new IllegalStateException(
        "Unable to initialize GLFW");
    }

    if (!GLFWVulkan.glfwVulkanSupported()) {
      throw new IllegalStateException(
        "Cannot find a compatible Vulkan installable client driver (ICD)");
    }

    /*
     * Specify NO_API: If this is not done, trying to use the KHR_surface extension will
     * result in a VK_ERROR_NATIVE_WINDOW_IN_USE_KHR error code.
     */

    GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API);

    final var window =
      GLFW.glfwCreateWindow(640, 480, "com.io7m.jcoronado.tests.Demo", 0L, 0L);
    if (window == 0L) {
      throw new IllegalStateException("Could not create window");
    }
    return window;
  }

  private static void showInstanceRequiredLayer(
    final String name)
  {
    LOG.debug("instance required layer: {}", name);
  }

  private static void showInstanceRequiredExtension(
    final String name)
  {
    LOG.debug("instance required extension: {}", name);
  }

  private static void showInstanceAvailableLayer(
    final String name,
    final VulkanLayerProperties layer)
  {
    LOG.debug(
      "instance available layer: {}: {}, specification 0x{} implementation 0x{}",
      layer.name(),
      layer.description(),
      Integer.toUnsignedString(layer.specificationVersion(), 16),
      Integer.toUnsignedString(layer.implementationVersion(), 16));
  }

  private static void showInstanceAvailableExtension(
    final String name,
    final VulkanExtensionProperties extension)
  {
    LOG.debug(
      "instance available extension: {} 0x{}",
      extension.name(),
      Integer.toUnsignedString(extension.version(), 16));
  }

  private static void showPhysicalDeviceAvailableExtension(
    final String name,
    final VulkanExtensionProperties extension)
  {
    LOG.debug(
      "device available extension: {} 0x{}",
      extension.name(),
      Integer.toUnsignedString(extension.version(), 16));
  }

  private static Set<String> requiredGLFWExtensions()
  {
    final var glfw_required_extensions =
      GLFWVulkan.glfwGetRequiredInstanceExtensions();

    final HashSet<String> required =
      new HashSet<>(glfw_required_extensions.capacity());
    for (var index = 0; index < glfw_required_extensions.capacity(); ++index) {
      glfw_required_extensions.position(index);
      required.add(glfw_required_extensions.getStringASCII());
    }
    return required;
  }
}
