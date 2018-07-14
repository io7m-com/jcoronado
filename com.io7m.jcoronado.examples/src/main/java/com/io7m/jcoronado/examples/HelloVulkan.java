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

import com.io7m.jcoronado.api.VulkanApplicationInfo;
import com.io7m.jcoronado.api.VulkanAttachmentDescription;
import com.io7m.jcoronado.api.VulkanAttachmentLoadOp;
import com.io7m.jcoronado.api.VulkanAttachmentReference;
import com.io7m.jcoronado.api.VulkanAttachmentStoreOp;
import com.io7m.jcoronado.api.VulkanComponentMapping;
import com.io7m.jcoronado.api.VulkanComponentSwizzle;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanExtensions;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanFormat;
import com.io7m.jcoronado.api.VulkanFramebufferCreateInfo;
import com.io7m.jcoronado.api.VulkanFramebufferType;
import com.io7m.jcoronado.api.VulkanFrontFace;
import com.io7m.jcoronado.api.VulkanGraphicsPipelineCreateInfo;
import com.io7m.jcoronado.api.VulkanImageAspectFlag;
import com.io7m.jcoronado.api.VulkanImageLayout;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageType;
import com.io7m.jcoronado.api.VulkanImageUsageFlag;
import com.io7m.jcoronado.api.VulkanImageViewCreateFlag;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import com.io7m.jcoronado.api.VulkanImageViewKind;
import com.io7m.jcoronado.api.VulkanImageViewType;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanInstanceProviderType;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanLayers;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.api.VulkanOffset2D;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceType;
import com.io7m.jcoronado.api.VulkanPipelineBindPoint;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendAttachmentState;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineInputAssemblyStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineLayoutCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineLayoutType;
import com.io7m.jcoronado.api.VulkanPipelineMultisampleStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineRasterizationStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineShaderStageCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineType;
import com.io7m.jcoronado.api.VulkanPipelineVertexInputStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineViewportStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPolygonMode;
import com.io7m.jcoronado.api.VulkanPrimitiveTopology;
import com.io7m.jcoronado.api.VulkanQueueFamilyProperties;
import com.io7m.jcoronado.api.VulkanQueueType;
import com.io7m.jcoronado.api.VulkanRectangle2D;
import com.io7m.jcoronado.api.VulkanRenderPassCreateInfo;
import com.io7m.jcoronado.api.VulkanRenderPassType;
import com.io7m.jcoronado.api.VulkanResourceException;
import com.io7m.jcoronado.api.VulkanSampleCountFlag;
import com.io7m.jcoronado.api.VulkanShaderModuleCreateInfo;
import com.io7m.jcoronado.api.VulkanShaderModuleType;
import com.io7m.jcoronado.api.VulkanShaderStageFlag;
import com.io7m.jcoronado.api.VulkanSharingMode;
import com.io7m.jcoronado.api.VulkanSubpassDescription;
import com.io7m.jcoronado.api.VulkanTemporaryAllocatorType;
import com.io7m.jcoronado.api.VulkanUncheckedException;
import com.io7m.jcoronado.api.VulkanVersions;
import com.io7m.jcoronado.api.VulkanViewport;
import com.io7m.jcoronado.extensions.api.VulkanColorSpaceKHR;
import com.io7m.jcoronado.extensions.api.VulkanCompositeAlphaFlagKHR;
import com.io7m.jcoronado.extensions.api.VulkanExtKHRSurfaceType;
import com.io7m.jcoronado.extensions.api.VulkanExtKHRSwapChainType;
import com.io7m.jcoronado.extensions.api.VulkanPresentModeKHR;
import com.io7m.jcoronado.extensions.api.VulkanSurfaceCapabilitiesKHR;
import com.io7m.jcoronado.extensions.api.VulkanSurfaceFormatKHR;
import com.io7m.jcoronado.extensions.api.VulkanSwapChainCreateInfo;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLInstanceProvider;
import com.io7m.jcoronado.lwjgl.VulkanLWJGLTemporaryAllocator;
import com.io7m.jmulticlose.core.CloseableCollection;
import com.io7m.jmulticlose.core.CloseableCollectionType;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVulkan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.io7m.jcoronado.api.VulkanCullModeFlag.VK_CULL_MODE_BACK_BIT;
import static com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag.VK_QUEUE_GRAPHICS_BIT;

public final class HelloVulkan
{
  private static final Logger LOG = LoggerFactory.getLogger(HelloVulkan.class);

  private static final GLFWErrorCallback GLFW_ERROR_CALLBACK =
    GLFWErrorCallback.createPrint();

  private HelloVulkan()
  {

  }

  public static void main(
    final String[] args)
    throws VulkanException, IOException
  {
    GLFW_ERROR_CALLBACK.set();

    final VulkanTemporaryAllocatorType alloc =
      VulkanLWJGLTemporaryAllocator.create();
    final Supplier<VulkanException> exception_supplier =
      () -> new VulkanResourceException("Could not close one or more resources.");

    try (CloseableCollectionType<VulkanException> resources =
           CloseableCollection.create(exception_supplier)) {

      final long window = createWindow();

      /*
       * Create an instance provider.
       */

      final VulkanInstanceProviderType instances = VulkanLWJGLInstanceProvider.create();
      LOG.debug("instance provider: {} {}", instances.providerName(), instances.providerVersion());

      final Map<String, VulkanExtensionProperties> available_extensions =
        instances.extensions();
      final Map<String, VulkanLayerProperties> available_layers =
        instances.layers();

      /*
       * Determine which extensions are required by the window system, and which layers are
       * required.
       */

      final Set<String> required_layers =
        Set.of("VK_LAYER_LUNARG_standard_validation");
      final Set<String> required_extensions =
        requiredGLFWExtensions();

      available_extensions.forEach(HelloVulkan::showAvailableExtension);
      available_layers.forEach(HelloVulkan::showAvailableLayer);
      required_extensions.forEach(HelloVulkan::showRequiredExtension);
      required_layers.forEach(HelloVulkan::showRequiredLayer);

      /*
       * Filter the available extensions and layers based on the requirements expressed above.
       * If any required extensions are not available, an exception is raised.
       */

      final Set<String> enable_extensions =
        VulkanExtensions.filterRequiredExtensions(
          available_extensions,
          Set.of(),
          required_extensions);

      final Set<String> enable_layers =
        VulkanLayers.filterRequiredLayers(
          available_layers,
          Set.of(),
          required_layers);

      /*
       * Create a new instance.
       */

      final VulkanInstanceCreateInfo create_info =
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

      final VulkanInstanceType instance = resources.add(instances.createInstance(create_info));

      /*
       * Get access to the VK_KHR_surface extension in order to produce a renderable
       * surface from the created window.
       */

      final VulkanExtKHRSurfaceType khr_surface_ext =
        instance.findEnabledExtension("VK_KHR_surface", VulkanExtKHRSurfaceType.class)
          .orElseThrow(() -> new IllegalStateException("Missing VK_KHR_surface extension"));

      final VulkanExtKHRSurfaceType.VulkanKHRSurfaceType surface =
        khr_surface_ext.surfaceFromWindow(instance, window);

      /*
       * List the available physical devices and pick the "best" one.
       */

      final List<VulkanPhysicalDeviceType> physical_devices = instance.physicalDevices();

      final VulkanPhysicalDeviceType physical_device =
        resources.add(pickPhysicalDeviceOrAbort(khr_surface_ext, surface, physical_devices));

      LOG.debug("physical device: {}", physical_device);

      /*
       * Determine the format, presentation mode, and size of the surface that will be
       * used for rendering.
       */

      final VulkanSurfaceFormatKHR surface_format =
        pickSurfaceFormat(physical_device, khr_surface_ext, surface);
      final VulkanPresentModeKHR surface_present =
        pickPresentationMode(physical_device, khr_surface_ext, surface);
      final VulkanSurfaceCapabilitiesKHR surface_caps =
        khr_surface_ext.surfaceCapabilities(physical_device, surface);
      final VulkanExtent2D surface_extent =
        pickExtent(surface_caps);

      LOG.debug("selected surface format: {}", surface_format);
      LOG.debug("selected surface presentation mode: {}", surface_present);
      LOG.debug("selected surface extent: {}", surface_extent);

      /*
       * We know that the selected physical device has a graphics queue family, and a queue
       * family for presentation. We don't know if these are the same queue families or not.
       */

      final VulkanQueueFamilyProperties graphics_queue_props =
        physical_device.queueFamilies()
          .stream()
          .filter(queue -> queue.queueFlags().contains(VK_QUEUE_GRAPHICS_BIT))
          .findFirst()
          .orElseThrow(() -> new IllegalStateException("Could not find graphics queue"));

      final VulkanQueueFamilyProperties presentation_queue_props =
        khr_surface_ext.surfaceSupport(physical_device, surface)
          .stream()
          .findFirst()
          .orElseThrow(() -> new IllegalStateException("Could not find presentation queue"));

      /*
       * Put together the information needed to create a logical device. In this case,
       * all that is really needed is to specify the creation of one or more queues.
       */

      final VulkanLogicalDeviceCreateInfo.Builder logical_device_info_builder =
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
       * Create the logical device and find the graphics and presentation queues.
       */

      final VulkanLogicalDeviceType device = resources.add(
        physical_device.createLogicalDevice(logical_device_info_builder
                                              .addEnabledExtensions("VK_KHR_swapchain")
                                              .build()));

      LOG.debug("logical device: {}", device);

      final VulkanQueueType graphics_queue =
        device.queue(graphics_queue_props.queueFamilyIndex(), 0)
          .orElseThrow(() -> new IllegalStateException("Could not find graphics queue"));
      final VulkanQueueType presentation_queue =
        device.queue(presentation_queue_props.queueFamilyIndex(), 0)
          .orElseThrow(() -> new IllegalStateException("Could not find presentation queue"));

      LOG.debug("graphics queue: {}", graphics_queue);
      LOG.debug("presentation queue: {}", presentation_queue);

      /*
       * Create a swap chain used to display images onscreen.
       */

      final VulkanExtKHRSwapChainType.VulkanKHRSwapChainType swap_chain =
        resources.add(createSwapChain(
          surface,
          surface_format,
          surface_present,
          surface_caps,
          surface_extent,
          device,
          graphics_queue,
          presentation_queue));

      final List<VulkanImageType> images = swap_chain.images();

      final List<VulkanImageViewType> views =
        images.stream()
          .map(image -> createImageViewForImage(surface_format, device, image))
          .map(resources::add)
          .collect(Collectors.toList());

      /*
       * Load a shader module.
       */

      final byte[] data = readShaderModule("shaders.spv");

      final VulkanShaderModuleType shaders =
        resources.add(createShaderModule(device, alloc, data));

      /*
       * Configure the render pass.
       */

      final VulkanAttachmentDescription color_attachment_description =
        VulkanAttachmentDescription.builder()
          .setFormat(surface_format.format())
          .setSamples(VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT)
          .setLoadOp(VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_CLEAR)
          .setStoreOp(VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_STORE)
          .setStencilLoadOp(VulkanAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_DONT_CARE)
          .setStencilStoreOp(VulkanAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_DONT_CARE)
          .setInitialLayout(VulkanImageLayout.VK_IMAGE_LAYOUT_UNDEFINED)
          .setFinalLayout(VulkanImageLayout.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR)
          .build();

      final VulkanAttachmentReference color_attachment_reference =
        VulkanAttachmentReference.builder()
          .setAttachment(0)
          .setLayout(VulkanImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
          .build();

      final VulkanSubpassDescription subpass_description =
        VulkanSubpassDescription.builder()
          .setPipelineBindPoint(VulkanPipelineBindPoint.VK_PIPELINE_BIND_POINT_GRAPHICS)
          .addColorAttachments(color_attachment_reference)
          .build();

      final VulkanRenderPassCreateInfo render_pass_create_info =
        VulkanRenderPassCreateInfo.builder()
          .addAttachments(color_attachment_description)
          .addSubpasses(subpass_description)
          .build();

      final VulkanRenderPassType render_pass =
        resources.add(device.createRenderPass(render_pass_create_info));

      /*
       * Configure the rendering pipeline.
       */

      final VulkanPipelineShaderStageCreateInfo vertex_stage_info =
        VulkanPipelineShaderStageCreateInfo.builder()
          .setStage(VulkanShaderStageFlag.VK_SHADER_STAGE_VERTEX_BIT)
          .setModule(shaders)
          .setShaderEntryPoint("R3_clip_triangle_vert_main")
          .build();

      final VulkanPipelineShaderStageCreateInfo fragment_stage_info =
        VulkanPipelineShaderStageCreateInfo.builder()
          .setStage(VulkanShaderStageFlag.VK_SHADER_STAGE_FRAGMENT_BIT)
          .setModule(shaders)
          .setShaderEntryPoint("R3_clip_triangle_frag_main")
          .build();

      final VulkanPipelineVertexInputStateCreateInfo vertex_input_info =
        VulkanPipelineVertexInputStateCreateInfo.builder()
          .build();

      final VulkanPipelineInputAssemblyStateCreateInfo input_assembly_info =
        VulkanPipelineInputAssemblyStateCreateInfo.builder()
          .setTopology(VulkanPrimitiveTopology.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST)
          .setPrimitiveRestartEnable(false)
          .build();

      final VulkanPipelineViewportStateCreateInfo viewport_state_info =
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

      final VulkanPipelineRasterizationStateCreateInfo rasterizer =
        VulkanPipelineRasterizationStateCreateInfo.builder()
          .setDepthClampEnable(false)
          .setRasterizerDiscardEnable(false)
          .setPolygonMode(VulkanPolygonMode.VK_POLYGON_MODE_FILL)
          .setLineWidth(1.0f)
          .setCullMode(EnumSet.of(VK_CULL_MODE_BACK_BIT))
          .setFrontFace(VulkanFrontFace.VK_FRONT_FACE_CLOCKWISE)
          .setDepthBiasEnable(false)
          .setDepthBiasConstantFactor(0.0f)
          .setDepthBiasClamp(0.0f)
          .setDepthBiasSlopeFactor(0.0f)
          .build();

      final VulkanPipelineColorBlendAttachmentState blend_state =
        VulkanPipelineColorBlendAttachmentState.builder()
          .setEnable(false)
          .build();

      final VulkanPipelineColorBlendStateCreateInfo color_blending =
        VulkanPipelineColorBlendStateCreateInfo.builder()
          .addAttachments(blend_state)
          .build();

      final VulkanPipelineLayoutCreateInfo pipeline_layout_info =
        VulkanPipelineLayoutCreateInfo.builder()
          .build();

      final VulkanPipelineLayoutType pipeline_layout =
        resources.add(device.createPipelineLayout(pipeline_layout_info));

      final VulkanPipelineMultisampleStateCreateInfo multisample =
        VulkanPipelineMultisampleStateCreateInfo.builder()
          .setAlphaToCoverageEnable(false)
          .setAlphaToOneEnable(false)
          .setSampleShadingEnable(false)
          .setMinSampleShading(1.0f)
          .setRasterizationSamples(VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT)
          .build();

      final VulkanGraphicsPipelineCreateInfo pipeline_info =
        VulkanGraphicsPipelineCreateInfo.builder()
          .addStages(vertex_stage_info)
          .addStages(fragment_stage_info)
          .setVertexInputState(vertex_input_info)
          .setInputAssemblyState(input_assembly_info)
          .setViewportState(viewport_state_info)
          .setMultisampleState(multisample)
          .setRasterizationState(rasterizer)
          .setColorBlendState(color_blending)
          .setLayout(pipeline_layout)
          .setRenderPass(render_pass)
          .setSubpass(0)
          .build();

      final VulkanPipelineType pipeline =
        resources.add(device.createPipeline(pipeline_info));

      /*
       * Create framebuffers.
       */

      final List<VulkanFramebufferType> framebuffers = new ArrayList<>(images.size());

      for (int index = 0; index < images.size(); ++index) {
        final VulkanFramebufferCreateInfo framebuffer_info =
          VulkanFramebufferCreateInfo.builder()
            .addAttachments(views.get(index))
            .setHeight(surface_extent.height())
            .setWidth(surface_extent.width())
            .setLayers(1)
            .setRenderPass(render_pass)
            .build();

        final VulkanFramebufferType framebuffer =
          resources.add(device.createFramebuffer(framebuffer_info));

        framebuffers.add(framebuffer);
      }

    } catch (final VulkanException e) {
      LOG.error("vulkan error: ", e);
      throw e;
    } finally {
      GLFW_ERROR_CALLBACK.close();
    }
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
    try (InputStream input = HelloVulkan.class.getResourceAsStream(name)) {
      try (ByteArrayOutputStream output = new ByteArrayOutputStream(8192)) {
        input.transferTo(output);
        return output.toByteArray();
      }
    }
  }

  private static VulkanImageViewType createImageViewForImage(
    final VulkanSurfaceFormatKHR surface_format,
    final VulkanLogicalDeviceType device,
    final VulkanImageType image)
  {
    try {
      final VulkanImageSubresourceRange range =
        VulkanImageSubresourceRange.of(
          Set.of(VulkanImageAspectFlag.VK_IMAGE_ASPECT_COLOR_BIT),
          0,
          1,
          0,
          1);

      final Set<VulkanImageViewCreateFlag> flags = Set.of();
      return device.createImageView(VulkanImageViewCreateInfo.of(
        flags,
        image,
        VulkanImageViewKind.VK_IMAGE_VIEW_TYPE_2D,
        surface_format.format(),
        VulkanComponentMapping.of(
          VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_IDENTITY,
          VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_IDENTITY,
          VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_IDENTITY,
          VulkanComponentSwizzle.VK_COMPONENT_SWIZZLE_IDENTITY),
        range));
    } catch (final VulkanException e) {
      throw new VulkanUncheckedException(e);
    }
  }

  private static VulkanExtKHRSwapChainType.VulkanKHRSwapChainType createSwapChain(
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
    final VulkanExtKHRSwapChainType khr_swapchain_ext =
      getSwapChainExtension(device);

    final int minimum_image_count =
      pickMinimumImageCount(surface_caps);
    final List<Integer> queue_indices = new ArrayList<>();
    final VulkanSharingMode image_sharing_mode =
      pickImageSharingMode(graphics_queue, presentation_queue, queue_indices);
    final Set<VulkanImageUsageFlag> image_usage_flags =
      Set.of(VulkanImageUsageFlag.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT);
    final Set<VulkanCompositeAlphaFlagKHR> surface_alpha_flags =
      Set.of(VulkanCompositeAlphaFlagKHR.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR);

    LOG.debug("swap chain image count: {}", Integer.valueOf(minimum_image_count));
    LOG.debug("swap chain image mode: {}", image_sharing_mode);

    final VulkanSwapChainCreateInfo swap_chain_create_info =
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

    final int graphics_family = graphics_queue.queueFamilyProperties().queueFamilyIndex();
    final int presentation_family = presentation_queue.queueFamilyProperties().queueFamilyIndex();
    if (graphics_family != presentation_family) {
      queue_indices.add(Integer.valueOf(graphics_family));
      queue_indices.add(Integer.valueOf(presentation_family));
      return VulkanSharingMode.VK_SHARING_MODE_CONCURRENT;
    }
    return VulkanSharingMode.VK_SHARING_MODE_EXCLUSIVE;
  }

  private static int pickMinimumImageCount(
    final VulkanSurfaceCapabilitiesKHR surface_caps)
  {
    /*
     * Select the minimum number of images required to do double-buffering.
     */

    final int count = surface_caps.minImageCount();
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

    final List<VulkanPresentModeKHR> modes =
      khr_surface_ext.surfacePresentModes(device, surface);

    VulkanPresentModeKHR preferred =
      VulkanPresentModeKHR.VK_PRESENT_MODE_FIFO_KHR;

    for (final VulkanPresentModeKHR mode : modes) {
      if (mode == VulkanPresentModeKHR.VK_PRESENT_MODE_MAILBOX_KHR) {
        return mode;
      }
      if (mode == VulkanPresentModeKHR.VK_PRESENT_MODE_IMMEDIATE_KHR) {
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
    final List<VulkanSurfaceFormatKHR> formats = khr_surface_ext.surfaceFormats(device, surface);

    /*
     * If there are no formats, try a commonly supported one.
     */

    if (formats.isEmpty()) {
      return VulkanSurfaceFormatKHR.of(
        VulkanFormat.VK_FORMAT_B8G8R8A8_UNORM,
        VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR);
    }

    /*
     * If there's one VK_FORMAT_UNDEFINED format, then this means that the implementation
     * doesn't have a preferred format and anything can be used.
     */

    if (formats.size() == 1) {
      final VulkanSurfaceFormatKHR format0 = formats.get(0);
      if (format0.format() == VulkanFormat.VK_FORMAT_UNDEFINED) {
        return VulkanSurfaceFormatKHR.of(
          VulkanFormat.VK_FORMAT_B8G8R8A8_UNORM,
          VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR);
      }
    }

    /*
     * Otherwise, look for a linear BGRA unsigned normalized format, with an SRGB color space.
     */

    for (final VulkanSurfaceFormatKHR format : formats) {
      if (format.format() == VulkanFormat.VK_FORMAT_B8G8R8A8_UNORM
        && format.colorSpace() == VulkanColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR) {
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
        .filter(HelloVulkan::physicalDeviceHasSwapChainExtension)
        .filter(HelloVulkan::physicalDeviceHasGraphicsQueue)
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

      final List<VulkanQueueFamilyProperties> queues_presentable =
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

      final List<VulkanQueueFamilyProperties> queues = device.queueFamilies();
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

    final long window =
      GLFW.glfwCreateWindow(640, 480, "com.io7m.jcoronado.tests.Demo", 0L, 0L);
    if (window == 0L) {
      throw new IllegalStateException("Could not create window");
    }
    return window;
  }

  private static void showRequiredLayer(
    final String name)
  {
    LOG.debug("required layer: {}", name);
  }

  private static void showRequiredExtension(
    final String name)
  {
    LOG.debug("required extension: {}", name);
  }

  private static void showAvailableLayer(
    final String name,
    final VulkanLayerProperties layer)
  {
    LOG.debug(
      "available layer: {}: {}, specification 0x{} implementation 0x{}",
      layer.name(),
      layer.description(),
      Integer.toUnsignedString(layer.specificationVersion(), 16),
      Integer.toUnsignedString(layer.implementationVersion(), 16));
  }

  private static void showAvailableExtension(
    final String name,
    final VulkanExtensionProperties extension)
  {
    LOG.debug(
      "available extension: {} 0x{}",
      extension.name(),
      Integer.toUnsignedString(extension.version(), 16));
  }

  private static Set<String> requiredGLFWExtensions()
  {
    final PointerBuffer glfw_required_extensions =
      GLFWVulkan.glfwGetRequiredInstanceExtensions();

    final HashSet<String> required =
      new HashSet<>(glfw_required_extensions.capacity());
    for (int index = 0; index < glfw_required_extensions.capacity(); ++index) {
      glfw_required_extensions.position(index);
      required.add(glfw_required_extensions.getStringASCII());
    }
    return required;
  }
}
