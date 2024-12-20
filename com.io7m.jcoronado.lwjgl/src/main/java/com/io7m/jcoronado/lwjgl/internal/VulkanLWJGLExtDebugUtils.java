/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

package com.io7m.jcoronado.lwjgl.internal;

import com.io7m.jcoronado.api.VulkanChecks;
import com.io7m.jcoronado.api.VulkanCommandBufferType;
import com.io7m.jcoronado.api.VulkanEnumMaps;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanInstanceType;
import com.io7m.jcoronado.api.VulkanLogicalDeviceType;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsLabelEXT;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageSeverityFlag;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessageTypeFlag;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessengerCallbackDataEXT;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessengerCallbackEXTType;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessengerCreateInfoEXT;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsMessengerEXTType;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsObjectNameInfoEXT;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsRegionType;
import com.io7m.jcoronado.extensions.ext_debug_utils.api.VulkanDebugUtilsType;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.EXTDebugUtils;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDebugUtilsLabelEXT;
import org.lwjgl.vulkan.VkDebugUtilsMessengerCallbackDataEXT;
import org.lwjgl.vulkan.VkDebugUtilsMessengerCallbackEXTI;
import org.lwjgl.vulkan.VkDebugUtilsMessengerCreateInfoEXT;
import org.lwjgl.vulkan.VkDebugUtilsObjectNameInfoEXT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.OptionalInt;

import static com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLClassChecks.checkInstanceOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.lwjgl.vulkan.EXTDebugUtils.VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT;
import static org.lwjgl.vulkan.EXTDebugUtils.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT;
import static org.lwjgl.vulkan.EXTDebugUtils.VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT;
import static org.lwjgl.vulkan.EXTDebugUtils.vkCmdBeginDebugUtilsLabelEXT;
import static org.lwjgl.vulkan.EXTDebugUtils.vkCmdEndDebugUtilsLabelEXT;
import static org.lwjgl.vulkan.EXTDebugUtils.vkCmdInsertDebugUtilsLabelEXT;
import static org.lwjgl.vulkan.EXTDebugUtils.vkSetDebugUtilsObjectNameEXT;

/**
 * The EXT_debug_utils extension.
 */

public final class VulkanLWJGLExtDebugUtils
  implements VulkanDebugUtilsType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(VulkanLWJGLExtDebugUtils.class);

  private final MemoryStack stackInitial;

  VulkanLWJGLExtDebugUtils()
  {
    this.stackInitial = MemoryStack.create();
  }

  private static int transformCallbackData(
    final int severity,
    final int type,
    final long callbackData,
    final long unused,
    final VulkanDebugUtilsMessengerCallbackEXTType callback)
  {
    final var data =
      VkDebugUtilsMessengerCallbackDataEXT.createSafe(callbackData);

    final var severityFlags =
      VulkanEnumMaps.unpackValues(
        VulkanDebugUtilsMessageSeverityFlag.class,
        VulkanDebugUtilsMessageSeverityFlag::values,
        severity
      );

    final var typeFlags =
      VulkanEnumMaps.unpackValues(
        VulkanDebugUtilsMessageTypeFlag.class,
        VulkanDebugUtilsMessageTypeFlag::values,
        type
      );

    final String messageString =
      copyString(data.pMessage());
    final String messageIdString =
      copyString(data.pMessageIdName());

    final var unpackedInfo =
      VulkanDebugUtilsMessengerCallbackDataEXT.builder()
        .setMessage(messageString)
        .setMessageIdName(messageIdString)
        .setMessageIdNumber(data.messageIdNumber())
        .build();

    return callback.call(
      severityFlags,
      typeFlags,
      unpackedInfo
    ) ? 1 : 0;
  }

  private static String copyString(
    final ByteBuffer messageBuffer)
  {
    if (messageBuffer == null) {
      return "(null)";
    }

    final var messageBytes = new byte[messageBuffer.capacity()];
    messageBuffer.get(messageBytes);
    return new String(messageBytes, UTF_8);
  }

  private static void packLabel(
    final MemoryStack stack,
    final VulkanDebugUtilsLabelEXT label,
    final VkDebugUtilsLabelEXT lwjglInfo)
  {
    final var color = label.color();
    final var red = color.red();
    final var blue = color.blue();
    final var green = color.green();
    final var alpha = color.alpha();

    lwjglInfo.set(
      VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT,
      0L,
      stack.UTF8(label.name()),
      stack.floats(red, blue, green, alpha)
    );
  }

  @Override
  public VulkanDebugUtilsMessengerEXTType createDebugUtilsMessenger(
    final VulkanInstanceType instance,
    final VulkanDebugUtilsMessengerCreateInfoEXT info)
    throws VulkanException
  {
    Objects.requireNonNull(instance, "instance");
    Objects.requireNonNull(info, "info");

    LOG.debug("Creating debug callback");

    final var lwjglInstance =
      checkInstanceOf(instance, VulkanLWJGLInstance.class);

    try (var stack = this.stackInitial.push()) {
      final var lwjglInfo =
        VkDebugUtilsMessengerCreateInfoEXT.calloc(stack);

      final var lwjglCallback =
        (VkDebugUtilsMessengerCallbackEXTI)
          (severity, type, callbackData, unused) -> {
            return transformCallbackData(
              severity,
              type,
              callbackData,
              unused,
              info.callback());
          };

      final var packedFlags =
        VulkanEnumMaps.packValues(info.flags());
      final var packedSeverity =
        VulkanEnumMaps.packValues(info.severity());
      final var packedType =
        VulkanEnumMaps.packValues(info.type());

      lwjglInfo.set(
        VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT,
        0L,
        packedFlags,
        packedSeverity,
        packedType,
        lwjglCallback,
        0L
      );

      final var buffer =
        stack.mallocLong(1);

      VulkanChecks.checkReturnCode(
        EXTDebugUtils.vkCreateDebugUtilsMessengerEXT(
          lwjglInstance.instance(),
          lwjglInfo,
          lwjglInstance.hostAllocatorProxy().callbackBuffer(),
          buffer
        ),
        "vkCreateDebugUtilsMessengerEXT"
      );

      return new VulkanLWJGLExtDebugUtilsMessenger(
        lwjglInstance.instance(),
        buffer.get(0),
        lwjglInstance.hostAllocatorProxy()
      );
    }
  }

  @Override
  public VulkanDebugUtilsRegionType begin(
    final VulkanCommandBufferType commandBuffer,
    final VulkanDebugUtilsLabelEXT label)
    throws VulkanException
  {
    final var lwjglCommandBuffer =
      checkInstanceOf(commandBuffer, VulkanLWJGLCommandBuffer.class);

    try (var stack = this.stackInitial.push()) {
      final var lwjglInfo = VkDebugUtilsLabelEXT.calloc(stack);
      packLabel(stack, label, lwjglInfo);
      vkCmdBeginDebugUtilsLabelEXT(
        lwjglCommandBuffer.buffer(),
        lwjglInfo
      );
    }

    return new Region(lwjglCommandBuffer);
  }

  @Override
  public void insertInto(
    final VulkanCommandBufferType commandBuffer,
    final VulkanDebugUtilsLabelEXT label)
    throws VulkanException
  {
    final var lwjglCommandBuffer =
      checkInstanceOf(commandBuffer, VulkanLWJGLCommandBuffer.class);

    try (var stack = this.stackInitial.push()) {
      final var lwjglInfo = VkDebugUtilsLabelEXT.calloc(stack);
      packLabel(stack, label, lwjglInfo);
      vkCmdInsertDebugUtilsLabelEXT(lwjglCommandBuffer.buffer(), lwjglInfo);
    }
  }

  @Override
  public void setObjectName(
    final VulkanLogicalDeviceType device,
    final VulkanDebugUtilsObjectNameInfoEXT info)
    throws VulkanException
  {
    final var lwjglDevice =
      checkInstanceOf(device, VulkanLWJGLLogicalDevice.class);

    if (info.objectHandle() instanceof final VulkanLWJGLHandle handle) {
      handle.setName(info.objectName());

      final var handleTypeOpt =
        objectTypeOfHandleInt(handle);

      if (handleTypeOpt.isEmpty()) {
        return;
      }

      try (var stack = this.stackInitial.push()) {
        final var debugInfo =
          VkDebugUtilsObjectNameInfoEXT.calloc(stack)
            .sType(VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT)
            .pObjectName(stack.UTF8(info.objectName()))
            .objectHandle(handle.handle())
            .objectType(handleTypeOpt.getAsInt());

        vkSetDebugUtilsObjectNameEXT(lwjglDevice.device(), debugInfo);
      }
    }
  }

  private static OptionalInt objectTypeOfHandleInt(
    final VulkanLWJGLHandle handle)
  {
    return switch (handle) {
      case final VulkanLWJGLBuffer _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_BUFFER);
      }
      case final VulkanLWJGLBufferView _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_BUFFER_VIEW);
      }
      case final VulkanLWJGLCommandBuffer _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_COMMAND_BUFFER);
      }
      case final VulkanLWJGLCommandPool _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_COMMAND_POOL);
      }
      case final VulkanLWJGLDescriptorPool _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_DESCRIPTOR_POOL);
      }
      case final VulkanLWJGLDescriptorSet _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_DESCRIPTOR_SET);
      }
      case final VulkanLWJGLDescriptorSetLayout _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_DESCRIPTOR_SET_LAYOUT);
      }
      case final VulkanLWJGLDeviceMemory _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_DEVICE_MEMORY);
      }
      case final VulkanLWJGLEvent _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_EVENT);
      }
      case final VulkanLWJGLFence _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_FENCE);
      }
      case final VulkanLWJGLFramebuffer _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_FRAMEBUFFER);
      }
      case final VulkanLWJGLImage _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_IMAGE);
      }
      case final VulkanLWJGLImageView _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_IMAGE_VIEW);
      }
      case final VulkanLWJGLInstance _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_INSTANCE);
      }
      case final VulkanLWJGLLogicalDevice _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_DEVICE);
      }
      case final VulkanLWJGLPhysicalDevice _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_PHYSICAL_DEVICE);
      }
      case final VulkanLWJGLPipeline _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_PIPELINE);
      }
      case final VulkanLWJGLPipelineCache _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_PIPELINE_CACHE);
      }
      case final VulkanLWJGLPipelineLayout _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_PIPELINE_LAYOUT);
      }
      case final VulkanLWJGLQueryPool _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_QUERY_POOL);
      }
      case final VulkanLWJGLQueue _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_QUEUE);
      }
      case final VulkanLWJGLRenderPass _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_RENDER_PASS);
      }
      case final VulkanLWJGLSampler _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_SAMPLER);
      }
      case final VulkanLWJGLSemaphoreTimeline _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_SEMAPHORE);
      }
      case final VulkanLWJGLSemaphoreBinary _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_SEMAPHORE);
      }
      case final VulkanLWJGLShaderModule _ -> {
        yield OptionalInt.of(VK10.VK_OBJECT_TYPE_SHADER_MODULE);
      }
      default -> {
        LOG.warn("Unrecognized handle type: {}", handle);
        yield OptionalInt.empty();
      }
    };
  }

  private static final class Region
    implements VulkanDebugUtilsRegionType
  {
    private final VulkanLWJGLCommandBuffer commandBuffer;
    private boolean closed;

    Region(
      final VulkanLWJGLCommandBuffer inCommandBuffer)
    {
      this.commandBuffer = inCommandBuffer;
      this.closed = false;
    }

    @Override
    public void close()
    {
      if (!this.isClosed()) {
        this.closed = true;
        vkCmdEndDebugUtilsLabelEXT(this.commandBuffer.buffer());
      }
    }

    @Override
    public boolean isClosed()
    {
      return this.closed;
    }
  }
}
