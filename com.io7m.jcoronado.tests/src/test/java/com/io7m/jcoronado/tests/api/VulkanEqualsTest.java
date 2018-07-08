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

package com.io7m.jcoronado.tests.api;

import com.io7m.jcoronado.api.VulkanApplicationInfo;
import com.io7m.jcoronado.api.VulkanAttachmentDescription;
import com.io7m.jcoronado.api.VulkanAttachmentReference;
import com.io7m.jcoronado.api.VulkanBlendConstants;
import com.io7m.jcoronado.api.VulkanExtensionProperties;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanExtent3D;
import com.io7m.jcoronado.api.VulkanGraphicsPipelineCreateInfo;
import com.io7m.jcoronado.api.VulkanImageSubresourceRange;
import com.io7m.jcoronado.api.VulkanImageViewCreateInfo;
import com.io7m.jcoronado.api.VulkanInstanceCreateInfo;
import com.io7m.jcoronado.api.VulkanLayerProperties;
import com.io7m.jcoronado.api.VulkanLineWidthRange;
import com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfo;
import com.io7m.jcoronado.api.VulkanMemoryHeap;
import com.io7m.jcoronado.api.VulkanMemoryType;
import com.io7m.jcoronado.api.VulkanOffset2D;
import com.io7m.jcoronado.api.VulkanOffset3D;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceLimits;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceMemoryProperties;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceProperties;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendAttachmentState;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineDepthStencilStateCreateInfo;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public final class VulkanEqualsTest
{
  @Test
  public void testApplicationInfo()
  {
    EqualsVerifier.forClass(VulkanApplicationInfo.class)
      .withNonnullFields(
        "applicationName",
        "engineName")
      .verify();
  }

  @Test
  public void testAttachmentDescription()
  {
    EqualsVerifier.forClass(VulkanAttachmentDescription.class)
      .withNonnullFields(
        "flags",
        "format",
        "samples",
        "loadOp",
        "storeOp",
        "stencilLoadOp",
        "stencilStoreOp",
        "finalLayout",
        "initialLayout")
      .verify();
  }

  @Test
  public void testAttachmentReference()
  {
    EqualsVerifier.forClass(VulkanAttachmentReference.class)
      .withNonnullFields("layout")
      .verify();
  }

  @Test
  public void testBlendConstants()
  {
    EqualsVerifier.forClass(VulkanBlendConstants.class)
      .verify();
  }

  @Test
  public void testExtensionProperties()
  {
    EqualsVerifier.forClass(VulkanExtensionProperties.class)
      .withNonnullFields("name")
      .verify();
  }

  @Test
  public void testExtent2D()
  {
    EqualsVerifier.forClass(VulkanExtent2D.class)
      .verify();
  }

  @Test
  public void testExtent3D()
  {
    EqualsVerifier.forClass(VulkanExtent3D.class)
      .verify();
  }

  @Test
  public void testGraphicsPipelineCreateInfo()
  {
    EqualsVerifier.forClass(VulkanGraphicsPipelineCreateInfo.class)
      .withNonnullFields(
        "flags",
        "stages",
        "layout",
        "renderPass",
        "rasterizationState",
        "inputAssemblyState",
        "vertexInputState")
      .verify();
  }

  @Test
  public void testImageSubresourceRange()
  {
    EqualsVerifier.forClass(VulkanImageSubresourceRange.class)
      .withNonnullFields("flags")
      .verify();
  }

  @Test
  public void testImageViewCreateInfo()
  {
    EqualsVerifier.forClass(VulkanImageViewCreateInfo.class)
      .withNonnullFields(
        "components",
        "subresourceRange",
        "flags",
        "image",
        "viewType",
        "format")
      .verify();
  }

  @Test
  public void testInstanceCreateInfo()
  {
    EqualsVerifier.forClass(VulkanInstanceCreateInfo.class)
      .withNonnullFields(
        "applicationInfo",
        "enabledExtensions",
        "enabledLayers")
      .verify();
  }

  @Test
  public void testLayerProperties()
  {
    EqualsVerifier.forClass(VulkanLayerProperties.class)
      .withNonnullFields(
        "name",
        "description")
      .verify();
  }

  @Test
  public void testLineWidthRange()
  {
    EqualsVerifier.forClass(VulkanLineWidthRange.class)
      .verify();
  }

  @Test
  public void testLogicalDeviceCreateInfo()
  {
    EqualsVerifier.forClass(VulkanLogicalDeviceCreateInfo.class)
      .withNonnullFields(
        "flags",
        "queueCreateInfos",
        "enabledLayers",
        "enabledExtensions",
        "features")
      .verify();
  }

  @Test
  public void testLogicalDeviceQueueCreateInfo()
  {
    EqualsVerifier.forClass(VulkanLogicalDeviceQueueCreateInfo.class)
      .withNonnullFields(
        "flags",
        "queuePriorities")
      .verify();
  }

  @Test
  public void testMemoryHeapType()
  {
    EqualsVerifier.forClass(VulkanMemoryHeap.class)
      .withNonnullFields(
        "flags")
      .verify();
  }

  @Test
  public void testMemoryType()
  {
    EqualsVerifier.forClass(VulkanMemoryType.class)
      .withNonnullFields(
        "flags")
      .verify();
  }

  @Test
  public void testOffset2D()
  {
    EqualsVerifier.forClass(VulkanOffset2D.class)
      .verify();
  }

  @Test
  public void testOffset3D()
  {
    EqualsVerifier.forClass(VulkanOffset3D.class)
      .verify();
  }

  @Test
  public void testPhysicalDeviceFeatures()
  {
    EqualsVerifier.forClass(VulkanPhysicalDeviceFeatures.class)
      .verify();
  }

  @Test
  public void testPhysicalDeviceLimits()
  {
    EqualsVerifier.forClass(VulkanPhysicalDeviceLimits.class)
      .withNonnullFields(
        "lineWidthRange",
        "maxComputeWorkGroupCount",
        "maxComputeWorkGroupSize",
        "maxViewportDimensions",
        "viewportBoundsRange",
        "pointSizeRange")
      .verify();
  }

  @Test
  public void testPhysicalDeviceMemoryProperties()
  {
    EqualsVerifier.forClass(VulkanPhysicalDeviceMemoryProperties.class)
      .withNonnullFields(
        "heaps",
        "types")
      .verify();
  }

  @Test
  public void testPhysicalDeviceProperties()
  {
    EqualsVerifier.forClass(VulkanPhysicalDeviceProperties.class)
      .withNonnullFields(
        "name",
        "type",
        "apiVersion",
        "driverVersion")
      .verify();
  }

  @Test
  public void testPipelineColorBlendAttachmentState()
  {
    EqualsVerifier.forClass(VulkanPipelineColorBlendAttachmentState.class)
      .withNonnullFields(
        "colorWriteMask",
        "srcColorBlendFactor",
        "dstColorBlendFactor",
        "srcAlphaBlendFactor",
        "dstAlphaBlendFactor",
        "alphaBlendOp",
        "colorBlendOp")
      .verify();
  }

  @Test
  public void testPipelineColorBlendStateCreateInfo()
  {
    EqualsVerifier.forClass(VulkanPipelineColorBlendStateCreateInfo.class)
      .withNonnullFields(
        "logicOp",
        "flags",
        "attachments",
        "blendConstants")
      .verify();
  }

  @Test
  public void testPipelineDepthStencilStateCreateInfo()
  {
    EqualsVerifier.forClass(VulkanPipelineDepthStencilStateCreateInfo.class)
      .withNonnullFields(
        "depthCompareOp",
        "flags",
        "front",
        "back")
      .verify();
  }
}
