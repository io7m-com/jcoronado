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
| com.io7m.jcoronado.api.VulkanApplicationInfoType| [VkApplicationInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkApplicationInfo.html) |
| com.io7m.jcoronado.api.VulkanAttachmentDescriptionType| [VkAttachmentDescription](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentDescription.html) |
| com.io7m.jcoronado.api.VulkanAttachmentReferenceType| [VkAttachmentReference](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentReference.html) |
| com.io7m.jcoronado.api.VulkanBlendConstantsType| [VkPipelineColorBlendAttachmentState](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineColorBlendAttachmentState.html) |
| com.io7m.jcoronado.api.VulkanBufferCopyType| [VkBufferCopy](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferCopy.html) |
| com.io7m.jcoronado.api.VulkanBufferCreateInfoType| [VkBufferCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanBufferImageCopyType| [VkBufferImageCopy](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferImageCopy.html) |
| com.io7m.jcoronado.api.VulkanBufferMemoryBarrierType| [VkBufferMemoryBarrier](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferMemoryBarrier.html) |
| com.io7m.jcoronado.api.VulkanClearValueType| [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| com.io7m.jcoronado.api.VulkanClearValueType$VulkanClearValueColorFloatingPointType| [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| com.io7m.jcoronado.api.VulkanClearValueType$VulkanClearValueColorIntegerSignedType| [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| com.io7m.jcoronado.api.VulkanClearValueType$VulkanClearValueColorIntegerUnsignedType| [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| com.io7m.jcoronado.api.VulkanClearValueType$VulkanClearValueDepthStencilType| [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferBeginInfoType| [VkCommandBufferBeginInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferBeginInfo.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferCreateInfoType| [VkCommandBufferAllocateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferAllocateInfo.html) |
| com.io7m.jcoronado.api.VulkanCommandPoolCreateInfoType| [VkCommandPoolCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandPoolCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanComponentMappingType| [VkComponentMapping](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkComponentMapping.html) |
| com.io7m.jcoronado.api.VulkanComputeWorkGroupCountType| [VkPhysicalDeviceLimits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceLimits.html) |
| com.io7m.jcoronado.api.VulkanComputeWorkGroupSizeType| [VkPhysicalDeviceLimits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceLimits.html) |
| com.io7m.jcoronado.api.VulkanCopyDescriptorSetType| [VkCopyDescriptorSet](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCopyDescriptorSet.html) |
| com.io7m.jcoronado.api.VulkanDescriptorBufferInfoType| [VkDescriptorBufferInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorBufferInfo.html) |
| com.io7m.jcoronado.api.VulkanDescriptorImageInfoType| [VkDescriptorImageInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorImageInfo.html) |
| com.io7m.jcoronado.api.VulkanDescriptorPoolCreateInfoType| [VkDescriptorPoolCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorPoolCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanDescriptorPoolSizeType| [VkDescriptorPoolSize](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorPoolSize.html) |
| com.io7m.jcoronado.api.VulkanDescriptorSetAllocateInfoType| [VkDescriptorSetAllocateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorSetAllocateInfo.html) |
| com.io7m.jcoronado.api.VulkanDescriptorSetLayoutBindingType| [VkDescriptorSetLayoutBinding](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorSetLayoutBinding.html) |
| com.io7m.jcoronado.api.VulkanDescriptorSetLayoutCreateInfoType| [VkDescriptorSetLayoutCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorSetLayoutCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanExtensionPropertiesType| [VkExtensionProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkExtensionProperties.html) |
| com.io7m.jcoronado.api.VulkanExtent2DType| [VkExtent2D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkExtent2D.html) |
| com.io7m.jcoronado.api.VulkanExtent3DType| [VkExtent3D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkExtent3D.html) |
| com.io7m.jcoronado.api.VulkanFenceCreateInfoType| [VkFenceCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFenceCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanFramebufferCreateInfoType| [VkFramebufferCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFramebufferCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanGraphicsPipelineCreateInfoType| [VkGraphicsPipelineCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkGraphicsPipelineCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanHostAllocatorCallbacksType| [VkAllocationCallbacks](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAllocationCallbacks.html) |
| com.io7m.jcoronado.api.VulkanImageCreateInfoType| [VkImageCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanImageMemoryBarrierType| [VkImageMemoryBarrier](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageMemoryBarrier.html) |
| com.io7m.jcoronado.api.VulkanImageSubresourceLayersType| [VkImageSubresourceLayers](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageSubresourceLayers.html) |
| com.io7m.jcoronado.api.VulkanImageSubresourceRangeType| [VkImageSubresourceRange](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageSubresourceRange.html) |
| com.io7m.jcoronado.api.VulkanImageViewCreateInfoType| [VkImageViewCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageViewCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanInstanceCreateInfoType| [VkInstanceCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkInstanceCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanLayerPropertiesType| [VkLayerProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkLayerProperties.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceCreateInfoType| [VkDeviceCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDeviceCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceQueueCreateInfoType| [VkDeviceQueueCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDeviceQueueCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanMemoryAllocateInfoType| [VkMemoryAllocateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryAllocateInfo.html) |
| com.io7m.jcoronado.api.VulkanMemoryBarrierType| [VkMemoryBarrier](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryBarrier.html) |
| com.io7m.jcoronado.api.VulkanMemoryHeapType| [VkMemoryHeap](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryHeap.html) |
| com.io7m.jcoronado.api.VulkanMemoryRequirementsType| [VkMemoryRequirements](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryRequirements.html) |
| com.io7m.jcoronado.api.VulkanOffset2DType| [VkOffset2D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkOffset2D.html) |
| com.io7m.jcoronado.api.VulkanOffset3DType| [VkOffset3D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkOffset3D.html) |
| com.io7m.jcoronado.api.VulkanPhysicalDeviceFeaturesType| [VkPhysicalDeviceFeatures](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceFeatures.html) |
| com.io7m.jcoronado.api.VulkanPhysicalDeviceLimitsType| [VkPhysicalDeviceLimits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceLimits.html) |
| com.io7m.jcoronado.api.VulkanPhysicalDeviceMemoryPropertiesType| [VkPhysicalDeviceMemoryProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceMemoryProperties.html) |
| com.io7m.jcoronado.api.VulkanPhysicalDevicePropertiesType| [VkPhysicalDeviceProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceProperties.html) |
| com.io7m.jcoronado.api.VulkanPipelineColorBlendAttachmentStateType| [VkPipelineColorBlendAttachmentState](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineColorBlendAttachmentState.html) |
| com.io7m.jcoronado.api.VulkanPipelineColorBlendStateCreateInfoType| [VkPipelineColorBlendStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineColorBlendStateCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanPipelineDepthStencilStateCreateInfoType| [VkPipelineDepthStencilStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineDepthStencilStateCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanPipelineDynamicStateCreateInfoType| [VkPipelineDynamicStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineDynamicStateCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanPipelineInputAssemblyStateCreateInfoType| [VkPipelineInputAssemblyStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineInputAssemblyStateCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanPipelineLayoutCreateInfoType| [VkPipelineLayoutCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineLayoutCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanPipelineMultisampleStateCreateInfoType| [VkPipelineMultisampleStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineMultisampleStateCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanPipelineRasterizationStateCreateInfoType| [VkPipelineRasterizationStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineRasterizationStateCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanPipelineShaderStageCreateInfoType| [VkPipelineShaderStageCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineShaderStageCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanPipelineTessellationStateCreateInfoType| [VkPipelineTessellationStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineTessellationStateCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanPipelineVertexInputStateCreateInfoType| [VkPipelineVertexInputStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineVertexInputStateCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanPipelineViewportStateCreateInfoType| [VkPipelineViewportStateCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineViewportStateCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanPushConstantRangeType| [VkPushConstantRange](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPushConstantRange.html) |
| com.io7m.jcoronado.api.VulkanQueueFamilyPropertiesType| [VkQueueFamilyProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkQueueFamilyProperties.html) |
| com.io7m.jcoronado.api.VulkanRectangle2DType| [VkRect2D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkRect2D.html) |
| com.io7m.jcoronado.api.VulkanRenderPassBeginInfoType| [VkRenderPassBeginInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkRenderPassBeginInfo.html) |
| com.io7m.jcoronado.api.VulkanRenderPassCreateInfoType| [VkRenderPassCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkRenderPassCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanSemaphoreCreateInfoType| [VkSemaphoreCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSemaphoreCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanShaderModuleCreateInfoType| [VkShaderModuleCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkShaderModuleCreateInfo.html) |
| com.io7m.jcoronado.api.VulkanSpecializationMapEntryType| [VkSpecializationMapEntry](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSpecializationMapEntry.html) |
| com.io7m.jcoronado.api.VulkanSpecializationMapType| [VkSpecializationMap](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSpecializationMap.html) |
| com.io7m.jcoronado.api.VulkanStencilOpStateType| [VkStencilOpState](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkStencilOpState.html) |
| com.io7m.jcoronado.api.VulkanSubmitInfoType| [VkSubmitInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubmitInfo.html) |
| com.io7m.jcoronado.api.VulkanSubpassDependencyType| [VkSubpassDependency](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubpassDependency.html) |
| com.io7m.jcoronado.api.VulkanSubpassDescriptionType| [VkSubpassDescription](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubpassDescription.html) |
| com.io7m.jcoronado.api.VulkanVertexInputAttributeDescriptionType| [VkVertexInputAttributeDescription](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkVertexInputAttributeDescription.html) |
| com.io7m.jcoronado.api.VulkanVertexInputBindingDescriptionType| [VkVertexInputBindingDescription](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkVertexInputBindingDescription.html) |
| com.io7m.jcoronado.api.VulkanViewportType| [VkViewport](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkViewport.html) |
| com.io7m.jcoronado.api.VulkanWriteDescriptorSetType| [VkWriteDescriptorSet](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkWriteDescriptorSet.html) |

## Enums

| jcoronado | Vulkan |
|-----------|--------|
| com.io7m.jcoronado.api.VulkanAccessFlag | [VkAccessFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAccessFlagBits.html) |
| com.io7m.jcoronado.api.VulkanAttachmentDescriptionFlag | [VkAttachmentDescriptionFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentDescriptionFlags.html) |
| com.io7m.jcoronado.api.VulkanAttachmentLoadOp | [VkAttachmentLoadOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentLoadOp.html) |
| com.io7m.jcoronado.api.VulkanAttachmentStoreOp | [VkAttachmentStoreOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentStoreOp.html) |
| com.io7m.jcoronado.api.VulkanBlendFactor | [VkBlendFactor](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBlendFactor.html) |
| com.io7m.jcoronado.api.VulkanBlendOp | [VkBlendOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBlendOp.html) |
| com.io7m.jcoronado.api.VulkanBufferCreateFlag | [VkBufferCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferCreateFlagBits.html) |
| com.io7m.jcoronado.api.VulkanBufferUsageFlag | [VkBufferUsageFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferUsageFlagBits.html) |
| com.io7m.jcoronado.api.VulkanColorComponentFlag | [VkColorComponentFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkColorComponentFlags.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferLevel | [VkCommandBufferLevel](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferLevel.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferUsageFlag | [VkCommandBufferUsageFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferUsageFlags.html) |
| com.io7m.jcoronado.api.VulkanCommandPoolCreateFlag | [VkCommandPoolCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandPoolCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanCompareOp | [VkCompareOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCompareOp.html) |
| com.io7m.jcoronado.api.VulkanComponentSwizzle | [VkComponentSwizzle](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkComponentSwizzle.html) |
| com.io7m.jcoronado.api.VulkanCullModeFlag | [VkCullModeFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCullModeFlagBits.html) |
| com.io7m.jcoronado.api.VulkanDependencyFlag | [VkDependencyFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDependencyFlagBits.html) |
| com.io7m.jcoronado.api.VulkanDescriptorPoolCreateFlag | [VkDescriptorPoolCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorPoolCreateFlagBits.html) |
| com.io7m.jcoronado.api.VulkanDescriptorSetLayoutCreateFlag | [VkDescriptorSetLayoutCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorSetLayoutCreateFlagBits.html) |
| com.io7m.jcoronado.api.VulkanDescriptorType | [VkDescriptorType](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorType.html) |
| com.io7m.jcoronado.api.VulkanDeviceQueueCreationFlag | [VkDeviceQueueCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDeviceQueueCreateFlagBits.html) |
| com.io7m.jcoronado.api.VulkanDynamicState | [VkDynamicState](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDynamicState.html) |
| com.io7m.jcoronado.api.VulkanFenceCreateFlag | [VkFenceCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFenceCreateFlagBits.html) |
| com.io7m.jcoronado.api.VulkanFormat | [VkFormat](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFormat.html) |
| com.io7m.jcoronado.api.VulkanFormatFeatureFlag | [VkFormatFeatureFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFormatFeatureFlagBits.html) |
| com.io7m.jcoronado.api.VulkanFramebufferCreateFlag | [VkFramebufferCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFramebufferCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanFrontFace | [VkFrontFace](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFrontFace.html) |
| com.io7m.jcoronado.api.VulkanImageAspectFlag | [VkImageAspectFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageAspectFlagBits.html) |
| com.io7m.jcoronado.api.VulkanImageCreateFlag | [VkImageCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageCreateFlagBits.html) |
| com.io7m.jcoronado.api.VulkanImageKind | [VkImageType](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageType.html) |
| com.io7m.jcoronado.api.VulkanImageLayout | [VkImageLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageLayout.html) |
| com.io7m.jcoronado.api.VulkanImageTiling | [VkImageTiling](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageTiling.html) |
| com.io7m.jcoronado.api.VulkanImageUsageFlag | [VkImageUsageFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageUsageFlags.html) |
| com.io7m.jcoronado.api.VulkanImageViewCreateFlag | [VkImageViewCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageViewCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanImageViewKind | [VkImageViewType](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageViewType.html) |
| com.io7m.jcoronado.api.VulkanInternalAllocation | [VkInternalAllocationType](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkInternalAllocationType.html) |
| com.io7m.jcoronado.api.VulkanLogicOp | [VkLogicOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkLogicOp.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceCreateFlag | [VkDeviceCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDeviceCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanMemoryHeapFlag | [VkMemoryHeapFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryHeapFlagBits.html) |
| com.io7m.jcoronado.api.VulkanMemoryMapFlag | [VkMemoryMapFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryMapFlags.html) |
| com.io7m.jcoronado.api.VulkanMemoryPropertyFlag | [VkMemoryPropertyFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryPropertyFlagBits.html) |
| com.io7m.jcoronado.api.VulkanPipelineBindPoint | [VkPipelineBindPoint](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineBindPoint.html) |
| com.io7m.jcoronado.api.VulkanPipelineColorBlendStateCreateFlag | [VkPipelineColorBlendStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineColorBlendStateCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanPipelineCreateFlag | [VkPipelineCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineCreateFlagBits.html) |
| com.io7m.jcoronado.api.VulkanPipelineDepthStencilStateCreateFlag | [VkPipelineDepthStencilStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineDepthStencilStateCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanPipelineDynamicStateCreateFlag | [VkPipelineDynamicStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineDynamicStateCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanPipelineInputAssemblyStateCreateFlag | [VkPipelineInputAssemblyStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineInputAssemblyStateCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanPipelineLayoutCreateFlag | [VkPipelineLayoutCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineLayoutCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanPipelineMultisampleStateCreateFlag | [VkPipelineMultisampleStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineMultisampleStateCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanPipelineRasterizationStateCreateFlag | [VulkanPipelineRasterizationStateCreateFlag](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VulkanPipelineRasterizationStateCreateFlag.html) |
| com.io7m.jcoronado.api.VulkanPipelineShaderStageCreateFlag | [VkPipelineShaderStageCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineShaderStageCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanPipelineStageFlag | [VkPipelineStageFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineStageFlagBits.html) |
| com.io7m.jcoronado.api.VulkanPipelineTessellationStageCreateFlag | [VkPipelineTessellationStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineTessellationStateCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanPipelineVertexInputStateCreateFlag | [VkPipelineVertexInputStateCreate](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineVertexInputStateCreate.html) |
| com.io7m.jcoronado.api.VulkanPipelineViewportStateCreateFlag | [VkPipelineViewportStateCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineViewportStateCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanPolygonMode | [VkPolygonMode](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPolygonMode.html) |
| com.io7m.jcoronado.api.VulkanPrimitiveTopology | [VkPrimitiveTopology](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPrimitiveTopology.html) |
| com.io7m.jcoronado.api.VulkanQueueFamilyPropertyFlag | [VkQueueFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkQueueFlagBits.html) |
| com.io7m.jcoronado.api.VulkanRenderPassCreateFlag | [VkRenderPassCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkRenderPassCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanSampleCountFlag | [VkSampleCountFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSampleCountFlagBits.html) |
| com.io7m.jcoronado.api.VulkanSemaphoreCreateFlag | [VkSemaphoreCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSemaphoreCreateFlags.html) |
| com.io7m.jcoronado.api.VulkanShaderModuleCreateFlag | [VkShaderModuleCreateFlag](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkShaderModuleCreateFlag.html) |
| com.io7m.jcoronado.api.VulkanShaderStageFlag | [VkShaderStageFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkShaderStageFlagBits.html) |
| com.io7m.jcoronado.api.VulkanSharingMode | [VkSharingMode](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSharingMode.html) |
| com.io7m.jcoronado.api.VulkanStencilOp | [VkStencilOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkStencilOp.html) |
| com.io7m.jcoronado.api.VulkanSubpassContents | [VkSubpassContents](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubpassContents.html) |
| com.io7m.jcoronado.api.VulkanSubpassDescriptionFlag | [VkSubpassDescriptionFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubpassDescriptionFlagBits.html) |
| com.io7m.jcoronado.api.VulkanSystemAllocationScope | [VkSystemAllocationScope](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSystemAllocationScope.html) |
| com.io7m.jcoronado.api.VulkanVertexInputRate | [VkVertexInputRate](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkVertexInputRate.html) |

## Functions

| jcoronado | Vulkan |
|-----------|--------|
| com.io7m.jcoronado.api.VulkanBufferType.close | [vkDestroyBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyBuffer.html) |
| com.io7m.jcoronado.api.VulkanBufferType.close | [vmaDestroyBuffer](https://gpuopen-librariesandsdks.github.io/VulkanMemoryAllocator/html/globals_func.html#index_v) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.beginCommandBuffer | [vkBeginCommandBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkBeginCommandBuffer.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.beginRenderPass | [vkCmdBeginRenderPass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBeginRenderPass.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.bindDescriptorSets | [vkCmdBindDescriptorSets](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBindDescriptorSets.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.bindIndexBuffer | [vkCmdBindIndexBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBindIndexBuffer.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.bindPipeline | [vkCmdBindPipeline](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBindPipeline.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.bindVertexBuffers | [vkCmdBindVertexBuffers](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBindVertexBuffers.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.copyBuffer | [vkCmdCopyBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdCopyBuffer.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.copyBufferToImage | [vkCmdCopyBufferToImage](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdCopyBufferToImage.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.copyImageToBuffer | [vkCmdCopyImageToBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdCopyImageToBuffer.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.draw | [vkCmdDraw](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdDraw.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.drawIndexed | [vkCmdDrawIndexed](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdDrawIndexed.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.endCommandBuffer | [vkEndCommandBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEndCommandBuffer.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.endRenderPass | [vkCmdEndRenderPass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdEndRenderPass.html) |
| com.io7m.jcoronado.api.VulkanCommandBufferType.pipelineBarrier | [vkCmdPipelineBarrier](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdPipelineBarrier.html) |
| com.io7m.jcoronado.api.VulkanCommandPoolType.close | [vkDestroyCommandPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyCommandPool.html) |
| com.io7m.jcoronado.api.VulkanDescriptorPoolType.close | [vkDestroyDescriptorPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyDescriptorPool.html) |
| com.io7m.jcoronado.api.VulkanDescriptorSetLayoutType.close | [vkDestroyDescriptorSetLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyDescriptorSetLayout.html) |
| com.io7m.jcoronado.api.VulkanDeviceMemoryType.close | [vkFreeMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkFreeMemory.html) |
| com.io7m.jcoronado.api.VulkanFenceType.close | [vkDestroyFence](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyFence.html) |
| com.io7m.jcoronado.api.VulkanFramebufferType.close | [vkDestroyFramebuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyFramebuffer.html) |
| com.io7m.jcoronado.api.VulkanImageType.close | [vkDestroyImage](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyImage.html) |
| com.io7m.jcoronado.api.VulkanImageType.close | [vmaDestroyImage](https://gpuopen-librariesandsdks.github.io/VulkanMemoryAllocator/html/globals_func.html#index_v) |
| com.io7m.jcoronado.api.VulkanImageViewType.close | [vkDestroyImageView](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyImageView.html) |
| com.io7m.jcoronado.api.VulkanInstanceType.enumeratePhysicalDevices | [vkEnumeratePhysicalDevices](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumeratePhysicalDevices.html) |
| com.io7m.jcoronado.api.VulkanInstanceType.physicalDevices | [vkEnumeratePhysicalDevices](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumeratePhysicalDevices.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.allocateDescriptorSets | [vkAllocateDescriptorSets](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkAllocateDescriptorSets.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.allocateMemory | [vkAllocateMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkAllocateMemory.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.bindBufferMemory | [vkBindBufferMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkBindBufferMemory.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createBuffer | [vkCreateBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateBuffer.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createCommandBuffer | [vkAllocateCommandBuffers](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkAllocateCommandBuffers.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createCommandBuffers | [vkAllocateCommandBuffers](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkAllocateCommandBuffers.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createCommandPool | [vkCreateCommandPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateCommandPool.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createDescriptorPool | [vkCreateDescriptorPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateDescriptorPool.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createDescriptorSetLayout | [vkCreateDescriptorSetLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateDescriptorSetLayout.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createFence | [vkCreateFence](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateFence.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createFramebuffer | [vkCreateFramebuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateFramebuffer.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createImageView | [vkCreateImageView](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateImageView.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createPipeline | [vkCreateGraphicsPipelines](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateGraphicsPipelines.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createPipelineLayout | [vkCreatePipelineLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreatePipelineLayout.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createPipelines | [vkCreateGraphicsPipelines](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateGraphicsPipelines.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createRenderPass | [vkCreateRenderPass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateRenderPass.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createSemaphore | [vkCreateSemaphore](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateSemaphore.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.createShaderModule | [vkCreateShaderModule](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateShaderModule.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.getBufferMemoryRequirements | [vkGetBufferMemoryRequirements](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetBufferMemoryRequirements.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.mapMemory | [vkMapMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkMapMemory.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.resetFences | [vkResetFences](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkResetFences.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.updateDescriptorSets | [vkUpdateDescriptorSets](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkUpdateDescriptorSets.html) |
| com.io7m.jcoronado.api.VulkanLogicalDeviceType.waitIdle | [vkDeviceWaitIdle](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDeviceWaitIdle.html) |
| com.io7m.jcoronado.api.VulkanMappedMemoryType.close | [vkUnmapMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkUnmapMemory.html) |
| com.io7m.jcoronado.api.VulkanMappedMemoryType.close | [vmaUnmapMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vmaUnmapMemory.html) |
| com.io7m.jcoronado.api.VulkanPhysicalDeviceType.createLogicalDevice | [vkCreateDevice](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateDevice.html) |
| com.io7m.jcoronado.api.VulkanPhysicalDeviceType.extensions | [vkEnumerateDeviceExtensionProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumerateDeviceExtensionProperties.html) |
| com.io7m.jcoronado.api.VulkanPhysicalDeviceType.features | [vkGetPhysicalDeviceFeatures](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceFeatures.html) |
| com.io7m.jcoronado.api.VulkanPhysicalDeviceType.layers | [vkEnumerateDeviceLayerProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumerateDeviceLayerProperties.html) |
| com.io7m.jcoronado.api.VulkanPhysicalDeviceType.limits | [vkGetPhysicalDeviceProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceProperties.html) |
| com.io7m.jcoronado.api.VulkanPhysicalDeviceType.memory | [vkGetPhysicalDeviceMemoryProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceMemoryProperties.html) |
| com.io7m.jcoronado.api.VulkanPhysicalDeviceType.properties | [vkGetPhysicalDeviceProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceProperties.html) |
| com.io7m.jcoronado.api.VulkanPhysicalDeviceType.queueFamilies | [vkGetPhysicalDeviceQueueFamilyProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceQueueFamilyProperties.html) |
| com.io7m.jcoronado.api.VulkanPipelineLayoutType.close | [vkDestroyPipelineLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyPipelineLayout.html) |
| com.io7m.jcoronado.api.VulkanPipelineType.close | [vkDestroyPipeline](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyPipeline.html) |
| com.io7m.jcoronado.api.VulkanQueueType.submit | [vkQueueSubmit](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkQueueSubmit.html) |
| com.io7m.jcoronado.api.VulkanQueueType.waitIdle | [vkQueueWaitIdle](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkQueueWaitIdle.html) |
| com.io7m.jcoronado.api.VulkanRenderPassType.close | [vkDestroyRenderPass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyRenderPass.html) |
| com.io7m.jcoronado.api.VulkanSemaphoreType.close | [vkDestroySemaphore](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroySemaphore.html) |
| com.io7m.jcoronado.api.VulkanShaderModuleType.close | [vkDestroyShaderModule](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyShaderModule.html) |

