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

package com.io7m.jcoronado.tests.lwjgl;

import com.io7m.jcoronado.api.VulkanBlendConstants;
import com.io7m.jcoronado.api.VulkanCullModeFlag;
import com.io7m.jcoronado.api.VulkanDynamicState;
import com.io7m.jcoronado.api.VulkanException;
import com.io7m.jcoronado.api.VulkanExtent2D;
import com.io7m.jcoronado.api.VulkanFrontFace;
import com.io7m.jcoronado.api.VulkanGraphicsPipelineCreateInfo;
import com.io7m.jcoronado.api.VulkanLogicOp;
import com.io7m.jcoronado.api.VulkanOffset2D;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendAttachmentState;
import com.io7m.jcoronado.api.VulkanPipelineColorBlendStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineCreateFlag;
import com.io7m.jcoronado.api.VulkanPipelineDepthStencilStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineDynamicStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineInputAssemblyStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineMultisampleStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineRasterizationStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineShaderStageCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineTessellationStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineType;
import com.io7m.jcoronado.api.VulkanPipelineVertexInputStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPipelineViewportStateCreateInfo;
import com.io7m.jcoronado.api.VulkanPolygonMode;
import com.io7m.jcoronado.api.VulkanRectangle2D;
import com.io7m.jcoronado.api.VulkanSampleCountFlag;
import com.io7m.jcoronado.api.VulkanShaderStageFlag;
import com.io7m.jcoronado.api.VulkanStencilOpState;
import com.io7m.jcoronado.api.VulkanVertexInputAttributeDescription;
import com.io7m.jcoronado.api.VulkanVertexInputBindingDescription;
import com.io7m.jcoronado.api.VulkanViewport;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLGraphicsPipelineCreateInfos;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLPipelineLayout;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLRenderPass;
import com.io7m.jcoronado.lwjgl.internal.VulkanLWJGLShaderModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lwjgl.system.MemoryStack;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.io7m.jcoronado.api.VulkanBlendFactor.VK_BLEND_FACTOR_CONSTANT_ALPHA;
import static com.io7m.jcoronado.api.VulkanBlendFactor.VK_BLEND_FACTOR_CONSTANT_COLOR;
import static com.io7m.jcoronado.api.VulkanBlendFactor.VK_BLEND_FACTOR_ONE;
import static com.io7m.jcoronado.api.VulkanBlendFactor.VK_BLEND_FACTOR_ONE_MINUS_DST_COLOR;
import static com.io7m.jcoronado.api.VulkanBlendOp.VK_BLEND_OP_ADD;
import static com.io7m.jcoronado.api.VulkanBlendOp.VK_BLEND_OP_HSL_LUMINOSITY_EXT;
import static com.io7m.jcoronado.api.VulkanCompareOp.VK_COMPARE_OP_ALWAYS;
import static com.io7m.jcoronado.api.VulkanCompareOp.VK_COMPARE_OP_LESS_OR_EQUAL;
import static com.io7m.jcoronado.api.VulkanCompareOp.VK_COMPARE_OP_NEVER;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_B8G8R8A8_UNORM;
import static com.io7m.jcoronado.api.VulkanFormat.VK_FORMAT_D16_UNORM;
import static com.io7m.jcoronado.api.VulkanPrimitiveTopology.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST;
import static com.io7m.jcoronado.api.VulkanStencilOp.VK_STENCIL_OP_DECREMENT_AND_CLAMP;
import static com.io7m.jcoronado.api.VulkanStencilOp.VK_STENCIL_OP_INCREMENT_AND_CLAMP;
import static com.io7m.jcoronado.api.VulkanStencilOp.VK_STENCIL_OP_INVERT;
import static com.io7m.jcoronado.api.VulkanStencilOp.VK_STENCIL_OP_KEEP;
import static com.io7m.jcoronado.api.VulkanStencilOp.VK_STENCIL_OP_REPLACE;
import static com.io7m.jcoronado.api.VulkanStencilOp.VK_STENCIL_OP_ZERO;
import static com.io7m.jcoronado.api.VulkanVertexInputRate.VK_VERTEX_INPUT_RATE_INSTANCE;
import static com.io7m.jcoronado.api.VulkanVertexInputRate.VK_VERTEX_INPUT_RATE_VERTEX;

@ExtendWith(MockitoExtension.class)
public final class VulkanLWJGLGraphicsPipelineCreateInfosTest
{
  private static final Logger LOG = LoggerFactory.getLogger(
    VulkanLWJGLGraphicsPipelineCreateInfosTest.class);

  private MemoryStack stack = MemoryStack.create();

  @BeforeEach
  public void testSetup()
  {
    LOG.debug("testSetup");
    this.stack = this.stack.push();
  }

  @Test
  public void testPipelineColorBlendStateCreateInfo(
    final @Mock VulkanLWJGLShaderModule module,
    final @Mock VulkanLWJGLPipelineLayout layout,
    final @Mock VulkanLWJGLRenderPass render_pass)
    throws VulkanException
  {
    Mockito.when(module.handle())
      .thenReturn(Long.valueOf(0x200L));
    Mockito.when(layout.handle())
      .thenReturn(Long.valueOf(0x201L));
    Mockito.when(render_pass.handle())
      .thenReturn(Long.valueOf(0x202L));

    final var state =
      VulkanPipelineColorBlendAttachmentState.builder()
        .setEnable(true)
        .setAlphaBlendOp(VK_BLEND_OP_ADD)
        .setColorBlendOp(VK_BLEND_OP_HSL_LUMINOSITY_EXT)
        .setDstAlphaBlendFactor(VK_BLEND_FACTOR_ONE)
        .setDstColorBlendFactor(VK_BLEND_FACTOR_ONE_MINUS_DST_COLOR)
        .setSrcAlphaBlendFactor(VK_BLEND_FACTOR_CONSTANT_ALPHA)
        .setSrcColorBlendFactor(VK_BLEND_FACTOR_CONSTANT_COLOR)
        .build();

    final var blendConstants =
      VulkanBlendConstants.builder()
        .setR(1.0f)
        .setG(2.0f)
        .setB(3.0f)
        .setA(4.0f)
        .build();

    final var color_info =
      VulkanPipelineColorBlendStateCreateInfo.builder()
        .setLogicOp(VulkanLogicOp.VK_LOGIC_OP_AND_INVERTED)
        .setBlendConstants(blendConstants)
        .addAttachments(state)
        .build();

    final var front =
      VulkanStencilOpState.builder()
        .setPassOp(VK_STENCIL_OP_DECREMENT_AND_CLAMP)
        .setCompareOp(VK_COMPARE_OP_ALWAYS)
        .setDepthFailOp(VK_STENCIL_OP_INCREMENT_AND_CLAMP)
        .setFailOp(VK_STENCIL_OP_KEEP)
        .setCompareMask(23)
        .setReference(25)
        .setWriteMask(24)
        .build();

    final var back =
      VulkanStencilOpState.builder()
        .setPassOp(VK_STENCIL_OP_REPLACE)
        .setCompareOp(VK_COMPARE_OP_NEVER)
        .setDepthFailOp(VK_STENCIL_OP_ZERO)
        .setFailOp(VK_STENCIL_OP_INVERT)
        .setCompareMask(33)
        .setReference(35)
        .setWriteMask(34)
        .build();

    final var depth_info =
      VulkanPipelineDepthStencilStateCreateInfo.builder()
        .setMinDepthBounds(2.4f)
        .setMaxDepthBounds(23.0f)
        .setStencilTestEnable(true)
        .setDepthBoundsTestEnable(true)
        .setDepthCompareOp(VK_COMPARE_OP_LESS_OR_EQUAL)
        .setDepthWriteEnable(true)
        .setDepthTestEnable(true)
        .setFront(front)
        .setBack(back)
        .build();

    final var dynamic_info =
      VulkanPipelineDynamicStateCreateInfo.builder()
        .addDynamicStates(VulkanDynamicState.values())
        .build();

    final var input_info =
      VulkanPipelineInputAssemblyStateCreateInfo.builder()
        .setPrimitiveRestartEnable(true)
        .setTopology(VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST)
        .build();

    final var multisample_info =
      VulkanPipelineMultisampleStateCreateInfo.builder()
        .setAlphaToCoverageEnable(true)
        .setMinSampleShading(0.5f)
        .setRasterizationSamples(VulkanSampleCountFlag.VK_SAMPLE_COUNT_1_BIT)
        .setSampleShadingEnable(true)
        .setAlphaToOneEnable(true)
        .setSampleMask(new int[]{1, 2, 3})
        .build();

    final var raster_info =
      VulkanPipelineRasterizationStateCreateInfo.builder()
        .setDepthBiasSlopeFactor(2.5f)
        .setDepthBiasClamp(3.0f)
        .setDepthBiasConstantFactor(0.5f)
        .setDepthBiasEnable(true)
        .setFrontFace(VulkanFrontFace.VK_FRONT_FACE_CLOCKWISE)
        .setCullMode(Set.of(VulkanCullModeFlag.VK_CULL_MODE_BACK_BIT))
        .setPolygonMode(VulkanPolygonMode.VK_POLYGON_MODE_FILL)
        .setLineWidth(50.0f)
        .setRasterizerDiscardEnable(true)
        .setDepthClampEnable(true)
        .build();

    final var shader_info_0 =
      VulkanPipelineShaderStageCreateInfo.builder()
        .setShaderEntryPoint("main")
        .setModule(module)
        .setStage(VulkanShaderStageFlag.VK_SHADER_STAGE_VERTEX_BIT)
        .build();

    final var shader_info_1 =
      VulkanPipelineShaderStageCreateInfo.builder()
        .setShaderEntryPoint("main2")
        .setModule(module)
        .setStage(VulkanShaderStageFlag.VK_SHADER_STAGE_FRAGMENT_BIT)
        .build();

    final var tessellation_info =
      VulkanPipelineTessellationStateCreateInfo.builder()
        .setPatchControlPoints(3)
        .build();

    final var desc_0 =
      VulkanVertexInputAttributeDescription.builder()
        .setBinding(1)
        .setLocation(0)
        .setFormat(VK_FORMAT_B8G8R8A8_UNORM)
        .setOffset(23)
        .build();

    final var desc_1 =
      VulkanVertexInputAttributeDescription.builder()
        .setBinding(3)
        .setLocation(2)
        .setFormat(VK_FORMAT_D16_UNORM)
        .setOffset(26)
        .build();

    final var bind_0 =
      VulkanVertexInputBindingDescription.builder()
        .setBinding(3)
        .setStride(56)
        .setInputRate(VK_VERTEX_INPUT_RATE_VERTEX)
        .build();

    final var bind_1 =
      VulkanVertexInputBindingDescription.builder()
        .setBinding(5)
        .setStride(57)
        .setInputRate(VK_VERTEX_INPUT_RATE_INSTANCE)
        .build();

    final var vertex_info =
      VulkanPipelineVertexInputStateCreateInfo.builder()
        .addVertexAttributeDescriptions(desc_0)
        .addVertexAttributeDescriptions(desc_1)
        .addVertexBindingDescriptions(bind_0)
        .addVertexBindingDescriptions(bind_1)
        .build();

    final var viewport_0 =
      VulkanViewport.builder()
        .setX(101.0f)
        .setY(102.0f)
        .setWidth(103.0f)
        .setHeight(104.0f)
        .setMinDepth(0.0f)
        .setMaxDepth(1.0f)
        .build();

    final var viewport_1 =
      VulkanViewport.builder()
        .setX(105.0f)
        .setY(106.0f)
        .setWidth(107.0f)
        .setHeight(108.0f)
        .setMinDepth(0.0f)
        .setMaxDepth(1.0f)
        .build();

    final var extent_0 =
      VulkanExtent2D.builder()
        .setWidth(23)
        .setHeight(34)
        .build();

    final var extent_1 =
      VulkanExtent2D.builder()
        .setWidth(25)
        .setHeight(37)
        .build();

    final var offset_0 =
      VulkanOffset2D.builder()
        .setX(5)
        .setY(17)
        .build();

    final var offset_1 =
      VulkanOffset2D.builder()
        .setX(6)
        .setY(18)
        .build();

    final var scissor_0 =
      VulkanRectangle2D.builder()
        .setOffset(offset_0)
        .setExtent(extent_0)
        .build();
    final var scissor_1 =
      VulkanRectangle2D.builder()
        .setOffset(offset_1)
        .setExtent(extent_1)
        .build();

    final var viewport_info =
      VulkanPipelineViewportStateCreateInfo.builder()
        .addViewports(viewport_0)
        .addViewports(viewport_1)
        .addScissors(scissor_0)
        .addScissors(scissor_1)
        .build();

    final Set<VulkanPipelineCreateFlag> flags = Set.of();

    final var shader_infos =
      List.of(shader_info_0, shader_info_1);

    final Optional<VulkanPipelineType> pipe_opt =
      Optional.empty();

    final var info =
      VulkanGraphicsPipelineCreateInfo.builder()
        .setBasePipeline(pipe_opt)
        .setBasePipelineIndex(23)
        .setColorBlendState(color_info)
        .setDepthStencilState(depth_info)
        .setDynamicState(dynamic_info)
        .setFlags(flags)
        .setInputAssemblyState(input_info)
        .setLayout(layout)
        .setMultisampleState(multisample_info)
        .setRasterizationState(raster_info)
        .setRenderPass(render_pass)
        .setStages(shader_infos)
        .setSubpass(0)
        .setTessellationState(tessellation_info)
        .setVertexInputState(vertex_info)
        .setViewportState(viewport_info)
        .build();

    final var packed_0 =
      VulkanLWJGLGraphicsPipelineCreateInfos.pack(
        this.stack,
        List.of(info)).get(0);

    VulkanLWJGLPipelineColorBlendStateCreateInfosTest.checkPacked(
      packed_0.pColorBlendState(), true);
    VulkanLWJGLPipelineDepthStencilStateCreateInfosTest.checkPacked(
      packed_0.pDepthStencilState());
    VulkanLWJGLPipelineDynamicStateCreateInfosTest.checkPacked(
      packed_0.pDynamicState());
    VulkanLWJGLPipelineInputAssemblyStateCreateInfosTest.checkPacked(
      packed_0.pInputAssemblyState());
    VulkanLWJGLPipelineMultisampleStateCreateInfosTest.checkPacked(
      packed_0.pMultisampleState());
    VulkanLWJGLPipelineRasterizationStateCreateInfosTest.checkPacked(
      packed_0.pRasterizationState());
    VulkanLWJGLPipelineShaderStageCreateInfosTest.checkPacked(
      module,
      packed_0.pStages());
    VulkanLWJGLPipelineTessellationStateCreateInfosTest.checkPacked(
      packed_0.pTessellationState());
    VulkanLWJGLPipelineVertexInputStateCreateInfosTest.checkPacked(
      packed_0.pVertexInputState());
    VulkanLWJGLPipelineViewportStateCreateInfosTest.checkPacked(
      packed_0.pViewportState());
  }
}
