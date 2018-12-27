jcoronado
============

[![Build Status](https://img.shields.io/travis/io7m/jcoronado.svg?style=flat-square)](https://travis-ci.org/io7m/jcoronado)
[![Maven Central](https://img.shields.io/maven-central/v/com.io7m.jcoronado/com.io7m.jcoronado.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.io7m.jcoronado%22)
[![Maven Central (snapshot)](https://img.shields.io/nexus/s/https/oss.sonatype.org/com.io7m.jcoronado/com.io7m.jcoronado.svg?style=flat-square)](https://oss.sonatype.org/content/repositories/snapshots/com/io7m/jcoronado/)
[![Codacy Badge](https://img.shields.io/codacy/grade/a2c044a2520e423ab9cc051d89a73254.svg?style=flat-square)](https://www.codacy.com/app/github_79/jcoronado?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=io7m/jcoronado&amp;utm_campaign=Badge_Grade)
[![Codecov](https://img.shields.io/codecov/c/github/io7m/jcoronado.svg?style=flat-square)](https://codecov.io/gh/io7m/jcoronado)

![jcoronado](./src/site/resources/jcoronado.jpg?raw=true)

# Usage

## Structs

| jcoronado | Vulkan |
|-----------|--------|
| VulkanApplicationInfoType | [VkApplicationInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkApplicationInfo.html) |
| VulkanAttachmentDescriptionType | [VkAttachmentDescription](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentDescription.html) |
| VulkanAttachmentReferenceType | [VkAttachmentReference](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentReference.html) |
| VulkanBlendConstantsType | [VkPipelineColorBlendAttachmentState](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineColorBlendAttachmentState.html) |
| VulkanBufferCopyType | [VkBufferCopy](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferCopy.html) |
| VulkanBufferCreateInfoType | [VkBufferCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferCreateInfo.html) |
| VulkanBufferImageCopyType | [VkBufferImageCopy](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferImageCopy.html) |
| VulkanBufferMemoryBarrierType | [VkBufferMemoryBarrier](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferMemoryBarrier.html) |
| VulkanClearValueType | [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| VulkanClearValueColorFloatingPointType | [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| VulkanClearValueColorIntegerSignedType | [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| VulkanClearValueColorIntegerUnsignedType | [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| VulkanClearValueDepthStencilType | [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| VulkanCommandBufferBeginInfoType | [VkCommandBufferBeginInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferBeginInfo.html) |
| VulkanCommandBufferCreateInfoType | [VkCommandBufferAllocateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferAllocateInfo.html) |
| VulkanCommandPoolCreateInfoType | [VkCommandPoolCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandPoolCreateInfo.html) |
| VulkanComponentMappingType | [VkComponentMapping](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkComponentMapping.html) |
| VulkanComputeWorkGroupCountType | [VkPhysicalDeviceLimits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceLimits.html) |
| VulkanComputeWorkGroupSizeType | [VkPhysicalDeviceLimits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceLimits.html) |
| VulkanCopyDescriptorSetType | [VkCopyDescriptorSet](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCopyDescriptorSet.html) |
| VulkanDescriptorBufferInfoType | [VkDescriptorBufferInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorBufferInfo.html) |
| VulkanDescriptorImageInfoType | [VkDescriptorImageInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorImageInfo.html) |
| VulkanDescriptorPoolCreateInfoType | [VkDescriptorPoolCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorPoolCreateInfo.html) |
| VulkanDescriptorPoolSizeType | [VkDescriptorPoolSize](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorPoolSize.html) |
| VulkanDescriptorSetAllocateInfoType | [VkDescriptorSetAllocateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorSetAllocateInfo.html) |
| VulkanDescriptorSetLayoutBindingType | [VkDescriptorSetLayoutBinding](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorSetLayoutBinding.html) |
| VulkanDescriptorSetLayoutCreateInfoType | [VkDescriptorSetLayoutCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorSetLayoutCreateInfo.html) |
| VulkanExtensionPropertiesType | [VkExtensionProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkExtensionProperties.html) |
| VulkanExtent2DType | [VkExtent2D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkExtent2D.html) |
| VulkanExtent3DType | [VkExtent3D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkExtent3D.html) |
| VulkanFenceCreateInfoType | [VkFenceCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFenceCreateInfo.html) |
| VulkanFramebufferCreateInfoType | [VkFramebufferCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFramebufferCreateInfo.html) |
| VulkanGraphicsPipelineCreateInfoType | [VkGraphicsPipelineCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkGraphicsPipelineCreateInfo.html) |
| VulkanHostAllocatorCallbacksType | [VkAllocationCallbacks](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAllocationCallbacks.html) |
| VulkanImageCreateInfoType | [VkImageCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageCreateInfo.html) |
| VulkanImageMemoryBarrierType | [VkImageMemoryBarrier](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageMemoryBarrier.html) |
| VulkanImageSubresourceLayersType | [VkImageSubresourceLayers](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageSubresourceLayers.html) |
| VulkanImageSubresourceRangeType | [VkImageSubresourceRange](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageSubresourceRange.html) |
| VulkanImageViewCreateInfoType | [VkImageViewCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageViewCreateInfo.html) |
| VulkanInstanceCreateInfoType | [VkInstanceCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkInstanceCreateInfo.html) |
| VulkanLayerPropertiesType | [VkLayerProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkLayerProperties.html) |
| VulkanLogicalDeviceCreateInfoType | [VkDeviceCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDeviceCreateInfo.html) |
| VulkanLogicalDeviceQueueCreateInfoType | [VkDeviceQueueCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDeviceQueueCreateInfo.html) |
| VulkanMemoryAllocateInfoType | [VkMemoryAllocateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryAllocateInfo.html) |
| VulkanMemoryBarrierType | [VkMemoryBarrier](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryBarrier.html) |
| VulkanMemoryHeapType | [VkMemoryHeap](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryHeap.html) |
| VulkanMemoryRequirementsType | [VkMemoryRequirements](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryRequirements.html) |
| VulkanOffset2DType | [VkOffset2D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkOffset2D.html) |
| VulkanOffset3DType | [VkOffset3D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkOffset3D.html) |
| VulkanPhysicalDeviceFeaturesType | [VkPhysicalDeviceFeatures](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceFeatures.html) |
| VulkanPhysicalDeviceLimitsType | [VkPhysicalDeviceLimits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceLimits.html) |
| VulkanPhysicalDeviceMemoryPropertiesType | [VkPhysicalDeviceMemoryProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceMemoryProperties.html) |
| VulkanPhysicalDevicePropertiesType | [VkPhysicalDeviceProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceProperties.html) |
| VulkanPipelineColorBlendAttachmentStateType | [VkPipelineColorBlendAttachmentState](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineColorBlendAttachmentState.html) |
| VulkanPipelineColorBlendStateCreateInfoType | [VkPipelineColorBlendStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineColorBlendStateCreateInfo.html) |
| VulkanPipelineDepthStencilStateCreateInfoType | [VkPipelineDepthStencilStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineDepthStencilStateCreateInfo.html) |
| VulkanPipelineDynamicStateCreateInfoType | [VkPipelineDynamicStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineDynamicStateCreateInfo.html) |
| VulkanPipelineInputAssemblyStateCreateInfoType | [VkPipelineInputAssemblyStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineInputAssemblyStateCreateInfo.html) |
| VulkanPipelineLayoutCreateInfoType | [VkPipelineLayoutCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineLayoutCreateInfo.html) |
| VulkanPipelineMultisampleStateCreateInfoType | [VkPipelineMultisampleStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineMultisampleStateCreateInfo.html) |
| VulkanPipelineRasterizationStateCreateInfoType | [VkPipelineRasterizationStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineRasterizationStateCreateInfo.html) |
| VulkanPipelineShaderStageCreateInfoType | [VkPipelineShaderStageCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineShaderStageCreateInfo.html) |
| VulkanPipelineTessellationStateCreateInfoType | [VkPipelineTessellationStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineTessellationStateCreateInfo.html) |
| VulkanPipelineVertexInputStateCreateInfoType | [VkPipelineVertexInputStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineVertexInputStateCreateInfo.html) |
| VulkanPipelineViewportStateCreateInfoType | [VkPipelineViewportStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineViewportStateCreateInfo.html) |
| VulkanPushConstantRangeType | [VkPushConstantRange](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPushConstantRange.html) |
| VulkanQueueFamilyPropertiesType | [VkQueueFamilyProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkQueueFamilyProperties.html) |
| VulkanRectangle2DType | [VkRect2D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkRect2D.html) |
| VulkanRenderPassBeginInfoType | [VkRenderPassBeginInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkRenderPassBeginInfo.html) |
| VulkanRenderPassCreateInfoType | [VkRenderPassCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkRenderPassCreateInfo.html) |
| VulkanSemaphoreCreateInfoType | [VkSemaphoreCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSemaphoreCreateInfo.html) |
| VulkanShaderModuleCreateInfoType | [VkShaderModuleCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkShaderModuleCreateInfo.html) |
| VulkanSpecializationMapEntryType | [VkSpecializationMapEntry](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSpecializationMapEntry.html) |
| VulkanSpecializationMapType | [VkSpecializationMap](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSpecializationMap.html) |
| VulkanStencilOpStateType | [VkStencilOpState](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkStencilOpState.html) |
| VulkanSubmitInfoType | [VkSubmitInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubmitInfo.html) |
| VulkanSubpassDependencyType | [VkSubpassDependency](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubpassDependency.html) |
| VulkanSubpassDescriptionType | [VkSubpassDescription](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubpassDescription.html) |
| VulkanVertexInputAttributeDescriptionType | [VkVertexInputAttributeDescription](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkVertexInputAttributeDescription.html) |
| VulkanVertexInputBindingDescriptionType | [VkVertexInputBindingDescription](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkVertexInputBindingDescription.html) |
| VulkanViewportType | [VkViewport](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkViewport.html) |
| VulkanWriteDescriptorSetType | [VkWriteDescriptorSet](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkWriteDescriptorSet.html) |

## Enums

| jcoronado | Vulkan |
|-----------|--------|
| VulkanAccessFlag | [VkAccessFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAccessFlagBits.html) |
| VulkanAttachmentDescriptionFlag | [VkAttachmentDescriptionFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentDescriptionFlags.html) |
| VulkanAttachmentLoadOp | [VkAttachmentLoadOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentLoadOp.html) |
| VulkanAttachmentStoreOp | [VkAttachmentStoreOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentStoreOp.html) |
| VulkanBlendFactor | [VkBlendFactor](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBlendFactor.html) |
| VulkanBlendOp | [VkBlendOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBlendOp.html) |
| VulkanBufferCreateFlag | [VkBufferCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferCreateFlagBits.html) |
| VulkanBufferUsageFlag | [VkBufferUsageFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferUsageFlagBits.html) |
| VulkanColorComponentFlag | [VkColorComponentFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkColorComponentFlags.html) |
| VulkanCommandBufferLevel | [VkCommandBufferLevel](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferLevel.html) |
| VulkanCommandBufferUsageFlag | [VkCommandBufferUsageFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferUsageFlags.html) |
| VulkanCommandPoolCreateFlag | [VkCommandPoolCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandPoolCreateFlags.html) |
| VulkanCompareOp | [VkCompareOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCompareOp.html) |
| VulkanComponentSwizzle | [VkComponentSwizzle](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkComponentSwizzle.html) |
| VulkanCullModeFlag | [VkCullModeFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCullModeFlagBits.html) |
| VulkanDependencyFlag | [VkDependencyFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDependencyFlagBits.html) |
| VulkanDescriptorPoolCreateFlag | [VkDescriptorPoolCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorPoolCreateFlagBits.html) |
| VulkanDescriptorSetLayoutCreateFlag | [VkDescriptorSetLayoutCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorSetLayoutCreateFlagBits.html) |
| VulkanDescriptorType | [VkDescriptorType](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorType.html) |
| VulkanDeviceQueueCreationFlag | [VkDeviceQueueCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDeviceQueueCreateFlagBits.html) |
| VulkanDynamicState | [VkDynamicState](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDynamicState.html) |
| VulkanFenceCreateFlag | [VkFenceCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFenceCreateFlagBits.html) |
| VulkanFormat | [VkFormat](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFormat.html) |
| VulkanFormatFeatureFlag | [VkFormatFeatureFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFormatFeatureFlagBits.html) |
| VulkanFramebufferCreateFlag | [VkFramebufferCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFramebufferCreateFlags.html) |
| VulkanFrontFace | [VkFrontFace](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFrontFace.html) |
| VulkanImageAspectFlag | [VkImageAspectFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageAspectFlagBits.html) |
| VulkanImageCreateFlag | [VkImageCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageCreateFlagBits.html) |
| VulkanImageKind | [VkImageType](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageType.html) |
| VulkanImageLayout | [VkImageLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageLayout.html) |
| VulkanImageTiling | [VkImageTiling](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageTiling.html) |
| VulkanImageUsageFlag | [VkImageUsageFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageUsageFlags.html) |
| VulkanImageViewCreateFlag | [VkImageViewCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageViewCreateFlags.html) |
| VulkanImageViewKind | [VkImageViewType](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageViewType.html) |
| VulkanInternalAllocation | [VkInternalAllocationType](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkInternalAllocationType.html) |
| VulkanLogicOp | [VkLogicOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkLogicOp.html) |
| VulkanLogicalDeviceCreateFlag | [VkDeviceCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDeviceCreateFlags.html) |
| VulkanMemoryHeapFlag | [VkMemoryHeapFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryHeapFlagBits.html) |
| VulkanMemoryMapFlag | [VkMemoryMapFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryMapFlags.html) |
| VulkanMemoryPropertyFlag | [VkMemoryPropertyFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryPropertyFlagBits.html) |
| VulkanPipelineBindPoint | [VkPipelineBindPoint](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineBindPoint.html) |
| VulkanPipelineColorBlendStateCreateFlag | [VkPipelineColorBlendStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineColorBlendStateCreateFlags.html) |
| VulkanPipelineCreateFlag | [VkPipelineCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineCreateFlagBits.html) |
| VulkanPipelineDepthStencilStateCreateFlag | [VkPipelineDepthStencilStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineDepthStencilStateCreateFlags.html) |
| VulkanPipelineDynamicStateCreateFlag | [VkPipelineDynamicStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineDynamicStateCreateFlags.html) |
| VulkanPipelineInputAssemblyStateCreateFlag | [VkPipelineInputAssemblyStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineInputAssemblyStateCreateFlags.html) |
| VulkanPipelineLayoutCreateFlag | [VkPipelineLayoutCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineLayoutCreateFlags.html) |
| VulkanPipelineMultisampleStateCreateFlag | [VkPipelineMultisampleStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineMultisampleStateCreateFlags.html) |
| VulkanPipelineRasterizationStateCreateFlag | [VulkanPipelineRasterizationStateCreateFlag](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VulkanPipelineRasterizationStateCreateFlag.html) |
| VulkanPipelineShaderStageCreateFlag | [VkPipelineShaderStageCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineShaderStageCreateFlags.html) |
| VulkanPipelineStageFlag | [VkPipelineStageFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineStageFlagBits.html) |
| VulkanPipelineTessellationStageCreateFlag | [VkPipelineTessellationStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineTessellationStateCreateFlags.html) |
| VulkanPipelineVertexInputStateCreateFlag | [VkPipelineVertexInputStateCreate](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineVertexInputStateCreate.html) |
| VulkanPipelineViewportStateCreateFlag | [VkPipelineViewportStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineViewportStateCreateFlags.html) |
| VulkanPolygonMode | [VkPolygonMode](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPolygonMode.html) |
| VulkanPrimitiveTopology | [VkPrimitiveTopology](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPrimitiveTopology.html) |
| VulkanQueueFamilyPropertyFlag | [VkQueueFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkQueueFlagBits.html) |
| VulkanRenderPassCreateFlag | [VkRenderPassCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkRenderPassCreateFlags.html) |
| VulkanSampleCountFlag | [VkSampleCountFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSampleCountFlagBits.html) |
| VulkanSemaphoreCreateFlag | [VkSemaphoreCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSemaphoreCreateFlags.html) |
| VulkanShaderModuleCreateFlag | [VkShaderModuleCreateFlag](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkShaderModuleCreateFlag.html) |
| VulkanShaderStageFlag | [VkShaderStageFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkShaderStageFlagBits.html) |
| VulkanSharingMode | [VkSharingMode](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSharingMode.html) |
| VulkanStencilOp | [VkStencilOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkStencilOp.html) |
| VulkanSubpassContents | [VkSubpassContents](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubpassContents.html) |
| VulkanSubpassDescriptionFlag | [VkSubpassDescriptionFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubpassDescriptionFlagBits.html) |
| VulkanSystemAllocationScope | [VkSystemAllocationScope](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSystemAllocationScope.html) |
| VulkanVertexInputRate | [VkVertexInputRate](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkVertexInputRate.html) |

## Functions

| jcoronado | Vulkan |
|-----------|--------|
| VulkanBufferType.close() | [vkDestroyBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyBuffer.html) |
| VulkanBufferType.close() | [vmaDestroyBuffer](https://gpuopen-librariesandsdks.github.io/VulkanMemoryAllocator/html/globals_func.html#index_v) |
| VulkanCommandBufferType.beginCommandBuffer() | [vkBeginCommandBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkBeginCommandBuffer.html) |
| VulkanCommandBufferType.beginRenderPass() | [vkCmdBeginRenderPass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBeginRenderPass.html) |
| VulkanCommandBufferType.bindDescriptorSets() | [vkCmdBindDescriptorSets](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBindDescriptorSets.html) |
| VulkanCommandBufferType.bindIndexBuffer() | [vkCmdBindIndexBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBindIndexBuffer.html) |
| VulkanCommandBufferType.bindPipeline() | [vkCmdBindPipeline](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBindPipeline.html) |
| VulkanCommandBufferType.bindVertexBuffers() | [vkCmdBindVertexBuffers](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBindVertexBuffers.html) |
| VulkanCommandBufferType.copyBuffer() | [vkCmdCopyBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdCopyBuffer.html) |
| VulkanCommandBufferType.copyBufferToImage() | [vkCmdCopyBufferToImage](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdCopyBufferToImage.html) |
| VulkanCommandBufferType.copyImageToBuffer() | [vkCmdCopyImageToBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdCopyImageToBuffer.html) |
| VulkanCommandBufferType.draw() | [vkCmdDraw](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdDraw.html) |
| VulkanCommandBufferType.drawIndexed() | [vkCmdDrawIndexed](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdDrawIndexed.html) |
| VulkanCommandBufferType.endCommandBuffer() | [vkEndCommandBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEndCommandBuffer.html) |
| VulkanCommandBufferType.endRenderPass() | [vkCmdEndRenderPass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdEndRenderPass.html) |
| VulkanCommandBufferType.pipelineBarrier() | [vkCmdPipelineBarrier](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdPipelineBarrier.html) |
| VulkanCommandPoolType.close() | [vkDestroyCommandPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyCommandPool.html) |
| VulkanDescriptorPoolType.close() | [vkDestroyDescriptorPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyDescriptorPool.html) |
| VulkanDescriptorSetLayoutType.close() | [vkDestroyDescriptorSetLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyDescriptorSetLayout.html) |
| VulkanDeviceMemoryType.close() | [vkFreeMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkFreeMemory.html) |
| VulkanFenceType.close() | [vkDestroyFence](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyFence.html) |
| VulkanFramebufferType.close() | [vkDestroyFramebuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyFramebuffer.html) |
| VulkanImageType.close() | [vkDestroyImage](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyImage.html) |
| VulkanImageType.close() | [vmaDestroyImage](https://gpuopen-librariesandsdks.github.io/VulkanMemoryAllocator/html/globals_func.html#index_v) |
| VulkanImageViewType.close() | [vkDestroyImageView](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyImageView.html) |
| VulkanInstanceType.enumeratePhysicalDevices() | [vkEnumeratePhysicalDevices](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumeratePhysicalDevices.html) |
| VulkanInstanceType.physicalDevices() | [vkEnumeratePhysicalDevices](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumeratePhysicalDevices.html) |
| VulkanLogicalDeviceType.allocateDescriptorSets() | [vkAllocateDescriptorSets](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkAllocateDescriptorSets.html) |
| VulkanLogicalDeviceType.allocateMemory() | [vkAllocateMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkAllocateMemory.html) |
| VulkanLogicalDeviceType.bindBufferMemory() | [vkBindBufferMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkBindBufferMemory.html) |
| VulkanLogicalDeviceType.createBuffer() | [vkCreateBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateBuffer.html) |
| VulkanLogicalDeviceType.createCommandBuffer() | [vkAllocateCommandBuffers](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkAllocateCommandBuffers.html) |
| VulkanLogicalDeviceType.createCommandBuffers() | [vkAllocateCommandBuffers](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkAllocateCommandBuffers.html) |
| VulkanLogicalDeviceType.createCommandPool() | [vkCreateCommandPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateCommandPool.html) |
| VulkanLogicalDeviceType.createDescriptorPool() | [vkCreateDescriptorPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateDescriptorPool.html) |
| VulkanLogicalDeviceType.createDescriptorSetLayout() | [vkCreateDescriptorSetLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateDescriptorSetLayout.html) |
| VulkanLogicalDeviceType.createFence() | [vkCreateFence](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateFence.html) |
| VulkanLogicalDeviceType.createFramebuffer() | [vkCreateFramebuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateFramebuffer.html) |
| VulkanLogicalDeviceType.createImageView() | [vkCreateImageView](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateImageView.html) |
| VulkanLogicalDeviceType.createPipeline() | [vkCreateGraphicsPipelines](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateGraphicsPipelines.html) |
| VulkanLogicalDeviceType.createPipelineLayout() | [vkCreatePipelineLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreatePipelineLayout.html) |
| VulkanLogicalDeviceType.createPipelines() | [vkCreateGraphicsPipelines](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateGraphicsPipelines.html) |
| VulkanLogicalDeviceType.createRenderPass() | [vkCreateRenderPass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateRenderPass.html) |
| VulkanLogicalDeviceType.createSemaphore() | [vkCreateSemaphore](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateSemaphore.html) |
| VulkanLogicalDeviceType.createShaderModule() | [vkCreateShaderModule](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateShaderModule.html) |
| VulkanLogicalDeviceType.getBufferMemoryRequirements() | [vkGetBufferMemoryRequirements](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetBufferMemoryRequirements.html) |
| VulkanLogicalDeviceType.mapMemory() | [vkMapMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkMapMemory.html) |
| VulkanLogicalDeviceType.resetFences() | [vkResetFences](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkResetFences.html) |
| VulkanLogicalDeviceType.updateDescriptorSets() | [vkUpdateDescriptorSets](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkUpdateDescriptorSets.html) |
| VulkanLogicalDeviceType.waitIdle() | [vkDeviceWaitIdle](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDeviceWaitIdle.html) |
| VulkanMappedMemoryType.close() | [vkUnmapMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkUnmapMemory.html) |
| VulkanMappedMemoryType.close() | [vmaUnmapMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vmaUnmapMemory.html) |
| VulkanPhysicalDeviceType.createLogicalDevice() | [vkCreateDevice](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateDevice.html) |
| VulkanPhysicalDeviceType.extensions() | [vkEnumerateDeviceExtensionProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumerateDeviceExtensionProperties.html) |
| VulkanPhysicalDeviceType.features() | [vkGetPhysicalDeviceFeatures](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceFeatures.html) |
| VulkanPhysicalDeviceType.layers() | [vkEnumerateDeviceLayerProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumerateDeviceLayerProperties.html) |
| VulkanPhysicalDeviceType.limits() | [vkGetPhysicalDeviceProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceProperties.html) |
| VulkanPhysicalDeviceType.memory() | [vkGetPhysicalDeviceMemoryProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceMemoryProperties.html) |
| VulkanPhysicalDeviceType.properties() | [vkGetPhysicalDeviceProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceProperties.html) |
| VulkanPhysicalDeviceType.queueFamilies() | [vkGetPhysicalDeviceQueueFamilyProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceQueueFamilyProperties.html) |
| VulkanPipelineLayoutType.close() | [vkDestroyPipelineLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyPipelineLayout.html) |
| VulkanPipelineType.close() | [vkDestroyPipeline](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyPipeline.html) |
| VulkanQueueType.submit() | [vkQueueSubmit](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkQueueSubmit.html) |
| VulkanQueueType.waitIdle() | [vkQueueWaitIdle](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkQueueWaitIdle.html) |
| VulkanRenderPassType.close() | [vkDestroyRenderPass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyRenderPass.html) |
| VulkanSemaphoreType.close() | [vkDestroySemaphore](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroySemaphore.html) |
| VulkanShaderModuleType.close() | [vkDestroyShaderModule](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyShaderModule.html) |

