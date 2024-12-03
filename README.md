jcoronado
===

[![Maven Central](https://img.shields.io/maven-central/v/com.io7m.jcoronado/com.io7m.jcoronado.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.io7m.jcoronado%22)
[![Maven Central (snapshot)](https://img.shields.io/nexus/s/com.io7m.jcoronado/com.io7m.jcoronado?server=https%3A%2F%2Fs01.oss.sonatype.org&style=flat-square)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/io7m/jcoronado/)
[![Codecov](https://img.shields.io/codecov/c/github/io7m-com/jcoronado.svg?style=flat-square)](https://codecov.io/gh/io7m-com/jcoronado)
![Java Version](https://img.shields.io/badge/21-java?label=java&color=e6c35c)

![com.io7m.jcoronado](./src/site/resources/jcoronado.jpg?raw=true)

| JVM | Platform | Status |
|-----|----------|--------|
| OpenJDK (Temurin) Current | Linux | [![Build (OpenJDK (Temurin) Current, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/jcoronado/main.linux.temurin.current.yml)](https://www.github.com/io7m-com/jcoronado/actions?query=workflow%3Amain.linux.temurin.current)|
| OpenJDK (Temurin) LTS | Linux | [![Build (OpenJDK (Temurin) LTS, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/jcoronado/main.linux.temurin.lts.yml)](https://www.github.com/io7m-com/jcoronado/actions?query=workflow%3Amain.linux.temurin.lts)|
| OpenJDK (Temurin) Current | Windows | [![Build (OpenJDK (Temurin) Current, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/jcoronado/main.windows.temurin.current.yml)](https://www.github.com/io7m-com/jcoronado/actions?query=workflow%3Amain.windows.temurin.current)|
| OpenJDK (Temurin) LTS | Windows | [![Build (OpenJDK (Temurin) LTS, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/jcoronado/main.windows.temurin.lts.yml)](https://www.github.com/io7m-com/jcoronado/actions?query=workflow%3Amain.windows.temurin.lts)|


## Background

The `jcoronado` package provides a very thin layer over the [Vulkan](https://www.vulkan.org/)
API that intends to provide some degree of memory and type safety. The intention
of the package is to make Vulkan feel like a Java API, without sacrificing
performance. Internally, the package uses the excellent [LWJGL3](https://www.lwjgl.org/)
Vulkan bindings, and adds a thin layer of immutable types and interfaces.

## Features

* Type-safe [Vulkan](https://www.vulkan.org/) frontend
* Strong separation of API and implementation to allow for switching to different bindings at compile-time
* Extensive use of `try-with-resources` to prevent resource leaks
* Strongly-typed interfaces with a heavy emphasis on immutable value types
* Type-safe extension mechanism
* Fully documented (JavaDoc)
* Example code included
* [OSGi](https://www.osgi.org/) ready.
* [JPMS](https://en.wikipedia.org/wiki/Java_Platform_Module_System) ready.
* ISC license

## Building

Install a Vulkan SDK. On Linux, there will be almost certainly be distribution
packages available with names such as `vulkan-validationlayers`, `vulkan-tools`,
etc. On Windows, install the [LunarG SDK](https://vulkan.lunarg.com/). On
Windows, ensure that you have the right vendor drivers installed for your
graphics card; if you don't do this, the library (and the test suite) will
raise exceptions with messages such as "Missing `vulkan-1.dll`".

Then run:

```
$ mvn clean package
```

If this step fails, it's a bug. Please report it!

## Current Test Platforms

The package regularly passes tests on all of the following platforms:

| Node | OS   | Arch       | GPU Vendor | GPU Model | Driver ID      | Driver Version | Driver Info | Vulkan API |
|------|------|------------|------------|-----------|----------------|----------------|-------------|------------|
|banana|Windows 10|amd64|NVIDIA|GeForce GTX 1660 Ti|NVIDIA|456.284.0|456.71|1.2.142|
|github|Linux|amd64|Unknown|llvmpipe (LLVM 12.0.0, 256 bits)|llvmpipe|0.0.1|Mesa 21.0.3 (LLVM 12.0.0)|1.0.2|
|prune|Linux|amd64|INTEL|Intel(R) HD Graphics 620 (KBL GT2)|Intel open-source Mesa driver|21.3.2|Mesa 21.3.2|1.2.195|
|starfruit|Linux|amd64|Unknown|llvmpipe (LLVM 11.0.1, 256 bits)|llvmpipe|0.0.1|Mesa 20.3.5 (LLVM 11.0.1)|1.0.2|
|sunflower|Linux|amd64|AMD|AMD RADV NAVI10|radv|21.3.1|Mesa 21.3.1|1.2.195|
|walnut|Windows 10|amd64|AMD|AMD Radeon(TM) Graphics|AMD proprietary driver|2.0.198|21.10.2|1.2.188|

The `node` field is an informal name given to each testing node so that we
can track test results over time.

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
| VulkanBufferViewCreateInfoType | [VkBufferViewCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferViewCreateInfo.html) |
| VulkanClearAttachmentType | [VkClearAttachment](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearAttachment.html) |
| VulkanClearRectangleType | [VkClearRect](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearRect.html) |
| VulkanClearValueType | [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| VulkanClearValueColorFloatingPointType | [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| VulkanClearValueColorIntegerSignedType | [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| VulkanClearValueColorIntegerUnsignedType | [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| VulkanClearValueColorType | [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| VulkanClearValueDepthStencilType | [VkClearValue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkClearValue.html) |
| VulkanCommandBufferBeginInfoType | [VkCommandBufferBeginInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferBeginInfo.html) |
| VulkanCommandBufferCreateInfoType | [VkCommandBufferAllocateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferAllocateInfo.html) |
| VulkanCommandPoolCreateInfoType | [VkCommandPoolCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandPoolCreateInfo.html) |
| VulkanComponentMappingType | [VkComponentMapping](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkComponentMapping.html) |
| VulkanComputePipelineCreateInfoType | [VkComputePipelineCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkComputePipelineCreateInfo.html) |
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
| VulkanEventCreateInfoType | [VkEventCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkEventCreateInfo.html) |
| VulkanExtensionPropertiesType | [VkExtensionProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkExtensionProperties.html) |
| VulkanExtent2DType | [VkExtent2D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkExtent2D.html) |
| VulkanExtent3DType | [VkExtent3D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkExtent3D.html) |
| VulkanFenceCreateInfoType | [VkFenceCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFenceCreateInfo.html) |
| VulkanFormatPropertiesType | [VkFormatProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFormatProperties.html) |
| VulkanFramebufferCreateInfoType | [VkFramebufferCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFramebufferCreateInfo.html) |
| VulkanGraphicsPipelineCreateInfoType | [VkGraphicsPipelineCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkGraphicsPipelineCreateInfo.html) |
| VulkanHostAllocatorCallbacksType | [VkAllocationCallbacks](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAllocationCallbacks.html) |
| VulkanImageBlitType | [VkImageBlit](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageBlit.html) |
| VulkanImageCopyType | [VkImageCopy](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageCopy.html) |
| VulkanImageCreateInfoType | [VkImageCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageCreateInfo.html) |
| VulkanImageFormatPropertiesType | [VkImageFormatProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageFormatProperties.html) |
| VulkanImageMemoryBarrierType | [VkImageMemoryBarrier](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageMemoryBarrier.html) |
| VulkanImageSubresourceLayersType | [VkImageSubresourceLayers](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageSubresourceLayers.html) |
| VulkanImageSubresourceRangeType | [VkImageSubresourceRange](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageSubresourceRange.html) |
| VulkanImageSubresourceType | [VkImageSubresource](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageSubresource.html) |
| VulkanImageViewCreateInfoType | [VkImageViewCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkImageViewCreateInfo.html) |
| VulkanInstanceCreateInfoType | [VkInstanceCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkInstanceCreateInfo.html) |
| VulkanLayerPropertiesType | [VkLayerProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkLayerProperties.html) |
| VulkanLogicalDeviceCreateInfoType | [VkDeviceCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDeviceCreateInfo.html) |
| VulkanLogicalDeviceQueueCreateInfoType | [VkDeviceQueueCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDeviceQueueCreateInfo.html) |
| VulkanMappedMemoryRangeType | [VkMappedMemoryRange](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMappedMemoryRange.html) |
| VulkanMemoryAllocateInfoType | [VkMemoryAllocateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryAllocateInfo.html) |
| VulkanMemoryBarrierType | [VkMemoryBarrier](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryBarrier.html) |
| VulkanMemoryHeapType | [VkMemoryHeap](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryHeap.html) |
| VulkanMemoryRequirementsType | [VkMemoryRequirements](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkMemoryRequirements.html) |
| VulkanOffset2DType | [VkOffset2D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkOffset2D.html) |
| VulkanOffset3DType | [VkOffset3D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkOffset3D.html) |
| VulkanPhysicalDeviceFeatures10Type | [VkPhysicalDeviceFeatures](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceFeatures.html) |
| VulkanPhysicalDeviceFeatures11Type | [VkPhysicalDeviceVulkan11Features](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceVulkan11Features.html) |
| VulkanPhysicalDeviceFeatures12Type | [VkPhysicalDeviceVulkan12Features](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceVulkan12Features.html) |
| VulkanPhysicalDeviceFeaturesType | [VkPhysicalDeviceFeatures](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceFeatures.html) |
| VulkanPhysicalDeviceLimitsType | [VkPhysicalDeviceLimits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceLimits.html) |
| VulkanPhysicalDeviceMemoryPropertiesType | [VkPhysicalDeviceMemoryProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceMemoryProperties.html) |
| VulkanPhysicalDevicePropertiesType | [VkPhysicalDeviceProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPhysicalDeviceProperties.html) |
| VulkanPipelineCacheCreateInfoType | [VkPipelineCacheCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineCacheCreateInfo.html) |
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
| VulkanQueryPoolCreateInfoType | [VkQueryPoolCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkQueryPoolCreateInfo.html) |
| VulkanQueueFamilyPropertiesType | [VkQueueFamilyProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkQueueFamilyProperties.html) |
| VulkanRectangle2DType | [VkRect2D](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkRect2D.html) |
| VulkanRenderPassBeginInfoType | [VkRenderPassBeginInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkRenderPassBeginInfo.html) |
| VulkanRenderPassCreateInfoType | [VkRenderPassCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkRenderPassCreateInfo.html) |
| VulkanSamplerCreateInfoType | [VkSamplerCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSamplerCreateInfo.html) |
| VulkanSemaphoreCreateInfoType | [VkSemaphoreCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSemaphoreCreateInfo.html) |
| VulkanShaderModuleCreateInfoType | [VkShaderModuleCreateInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkShaderModuleCreateInfo.html) |
| VulkanSpecializationMapEntryType | [VkSpecializationMapEntry](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSpecializationMapEntry.html) |
| VulkanSpecializationMapType | [VkSpecializationMap](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSpecializationMap.html) |
| VulkanStencilOpStateType | [VkStencilOpState](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkStencilOpState.html) |
| VulkanSubmitInfoType | [VkSubmitInfo](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubmitInfo.html) |
| VulkanSubpassDependencyType | [VkSubpassDependency](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubpassDependency.html) |
| VulkanSubpassDescriptionType | [VkSubpassDescription](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubpassDescription.html) |
| VulkanSubresourceLayoutType | [VkSubresourceLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubresourceLayout.html) |
| VulkanVertexInputAttributeDescriptionType | [VkVertexInputAttributeDescription](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkVertexInputAttributeDescription.html) |
| VulkanVertexInputBindingDescriptionType | [VkVertexInputBindingDescription](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkVertexInputBindingDescription.html) |
| VulkanViewportType | [VkViewport](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkViewport.html) |
| VulkanWriteDescriptorSetType | [VkWriteDescriptorSet](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkWriteDescriptorSet.html) |
| VulkanDebugUtilsLabelEXTType | [VkDebugUtilsLabelEXT](https://www.khronos.org/registry/vulkan/specs/1.2-extensions/man/html/VK_EXT_debug_utils.html) |
| VulkanDebugUtilsMessengerCallbackDataEXTType | [VkDebugUtilsMessengerCallbackDataEXT](https://www.khronos.org/registry/vulkan/specs/1.2-extensions/man/html/VK_EXT_debug_utils.html) |
| VulkanDebugUtilsMessengerCreateInfoEXTType | [VkDebugUtilsMessengerCreateInfoEXT](https://www.khronos.org/registry/vulkan/specs/1.2-extensions/man/html/VK_EXT_debug_utils.html) |
| VulkanDebugUtilsObjectNameInfoEXTType | [VkDebugUtilsObjectNameInfoEXT](https://www.khronos.org/registry/vulkan/specs/1.2-extensions/man/html/VK_EXT_debug_utils.html) |
| VulkanSurfaceCapabilitiesKHRType | [VkSurfaceCapabilitiesKHR](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/html/vkspec.html#VK_KHR_surface) |
| VulkanSurfaceFormatKHRType | [VkSurfaceFormatKHR](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/html/vkspec.html#VK_KHR_surface) |
| VulkanPresentInfoKHRType | [VkPresentInfoKHR](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/html/vkspec.html#VK_KHR_swapchain) |
| VulkanSwapChainCreateInfoType | [VkSwapchainCreateInfoKHR](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/html/vkspec.html#VK_KHR_swapchain) |

## Enums

| jcoronado | Vulkan |
|-----------|--------|
| VulkanAccessFlag | [VkAccessFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAccessFlagBits.html) |
| VulkanAttachmentDescriptionFlag | [VkAttachmentDescriptionFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentDescriptionFlags.html) |
| VulkanAttachmentLoadOp | [VkAttachmentLoadOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentLoadOp.html) |
| VulkanAttachmentStoreOp | [VkAttachmentStoreOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkAttachmentStoreOp.html) |
| VulkanBlendFactor | [VkBlendFactor](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBlendFactor.html) |
| VulkanBlendOp | [VkBlendOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBlendOp.html) |
| VulkanBorderColor | [VkBorderColor](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBorderColor.html) |
| VulkanBufferCreateFlag | [VkBufferCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferCreateFlagBits.html) |
| VulkanBufferUsageFlag | [VkBufferUsageFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferUsageFlagBits.html) |
| VulkanBufferViewCreateFlag | [VkBufferViewCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkBufferViewCreateFlags.html) |
| VulkanColorComponentFlag | [VkColorComponentFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkColorComponentFlags.html) |
| VulkanCommandBufferLevel | [VkCommandBufferLevel](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferLevel.html) |
| VulkanCommandBufferResetFlag | [VkCommandBufferResetFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferResetFlagBits.html) |
| VulkanCommandBufferUsageFlag | [VkCommandBufferUsageFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandBufferUsageFlags.html) |
| VulkanCommandPoolCreateFlag | [VkCommandPoolCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandPoolCreateFlags.html) |
| VulkanCommandPoolResetFlag | [VkCommandPoolResetFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandPoolResetFlagBits.html) |
| VulkanCommandPoolTrimFlag | [VkCommandPoolTrimFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCommandPoolTrimFlags.html) |
| VulkanCompareOp | [VkCompareOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCompareOp.html) |
| VulkanComponentSwizzle | [VkComponentSwizzle](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkComponentSwizzle.html) |
| VulkanCullModeFlag | [VkCullModeFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkCullModeFlagBits.html) |
| VulkanDependencyFlag | [VkDependencyFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDependencyFlagBits.html) |
| VulkanDescriptorPoolCreateFlag | [VkDescriptorPoolCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorPoolCreateFlagBits.html) |
| VulkanDescriptorPoolResetFlag | [VkDescriptorPoolResetFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorPoolResetFlagBits.html) |
| VulkanDescriptorSetLayoutCreateFlag | [VkDescriptorSetLayoutCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorSetLayoutCreateFlagBits.html) |
| VulkanDescriptorType | [VkDescriptorType](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDescriptorType.html) |
| VulkanDeviceQueueCreationFlag | [VkDeviceQueueCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDeviceQueueCreateFlagBits.html) |
| VulkanDriverKnownId | [VkDriverId](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDriverId.html) |
| VulkanDynamicState | [VkDynamicState](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDynamicState.html) |
| VulkanEventCreateFlag | [VkEventCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkEventCreateFlagBits.html) |
| VulkanFenceCreateFlag | [VkFenceCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFenceCreateFlagBits.html) |
| VulkanFilter | [VkFilter](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkFilter.html) |
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
| VulkanPipelineCacheCreateFlag | [VkPipelineCacheCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkPipelineCacheCreateFlags.html) |
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
| VulkanQueryControlFlag | [VkQueryControlFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkQueryControlFlagBits.html) |
| VulkanQueryKind | [VkQueryType](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkQueryType.html) |
| VulkanQueryPipelineStatisticFlag | [VkQueryPipelineStatisticFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkQueryPipelineStatisticFlagBits.html) |
| VulkanQueryPoolCreateFlag | [VkQueryPoolCreateFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkQueryPoolCreateFlagBits.html) |
| VulkanQueueFamilyPropertyFlag | [VkQueueFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkQueueFlagBits.html) |
| VulkanRenderPassCreateFlag | [VkRenderPassCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkRenderPassCreateFlags.html) |
| VulkanSampleCountFlag | [VkSampleCountFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSampleCountFlagBits.html) |
| VulkanSamplerAddressMode | [VkSamplerAddressMode](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSamplerAddressMode.html) |
| VulkanSamplerCreateFlag | [VkSamplerCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSamplerCreateFlags.html) |
| VulkanSamplerMipmapMode | [VkSamplerMipmapMode](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSamplerMipmapMode.html) |
| VulkanSemaphoreCreateFlag | [VkSemaphoreCreateFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSemaphoreCreateFlags.html) |
| VulkanShaderModuleCreateFlag | [VkShaderModuleCreateFlag](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkShaderModuleCreateFlag.html) |
| VulkanShaderStageFlag | [VkShaderStageFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkShaderStageFlagBits.html) |
| VulkanSharingMode | [VkSharingMode](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSharingMode.html) |
| VulkanStencilFaceFlag | [VkStencilFaceFlags](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkStencilFaceFlags.html) |
| VulkanStencilOp | [VkStencilOp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkStencilOp.html) |
| VulkanSubpassContents | [VkSubpassContents](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubpassContents.html) |
| VulkanSubpassDescriptionFlag | [VkSubpassDescriptionFlagBits](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSubpassDescriptionFlagBits.html) |
| VulkanSystemAllocationScope | [VkSystemAllocationScope](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkSystemAllocationScope.html) |
| VulkanVertexInputRate | [VkVertexInputRate](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkVertexInputRate.html) |
| VulkanDebugUtilsMessageSeverityFlag | [VkDebugUtilsMessengerCreateFlagsEXT](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDebugUtilsMessengerCreateFlagsEXT.html) |
| VulkanDebugUtilsMessageTypeFlag | [VkDebugUtilsMessageTypeFlagBitsEXT](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDebugUtilsMessageTypeFlagBitsEXT.html) |
| VulkanDebugUtilsMessengerCallbackDataFlag | [VkDebugUtilsMessengerCallbackDataFlagsEXT](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDebugUtilsMessengerCallbackDataFlagsEXT.html) |
| VulkanDebugUtilsMessengerCreateFlag | [VkDebugUtilsMessengerCreateFlagsEXT](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/VkDebugUtilsMessengerCreateFlagsEXT.html) |
| VulkanSurfaceTransformFlagKHR | [VkSurfaceTransformFlagBitsKHR](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/html/vkspec.html#VK_KHR_surface) |
| VulkanColorSpaceKHR | [VkColorSpaceKHR](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/html/vkspec.html#VK_KHR_swapchain) |
| VulkanCompositeAlphaFlagKHR | [VkCompositeAlphaFlagBitsKHR](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/html/vkspec.html#VK_KHR_swapchain) |
| VulkanPresentModeKHR | [VkPresentModeKHR](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/html/vkspec.html#VK_KHR_swapchain) |

## Functions

| jcoronado | Vulkan |
|-----------|--------|
| VulkanBufferType.close() | [vkDestroyBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyBuffer.html) |
| VulkanBufferType.close() | [vmaDestroyBuffer](https://gpuopen-librariesandsdks.github.io/VulkanMemoryAllocator/html/globals_func.html#index_v) |
| VulkanBufferViewType.close() | [vkDestroyBufferView](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyBufferView.html) |
| VulkanCommandBufferType.beginCommandBuffer() | [vkBeginCommandBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkBeginCommandBuffer.html) |
| VulkanCommandBufferType.beginQuery() | [vkCmdBeginQuery](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBeginQuery.html) |
| VulkanCommandBufferType.beginRenderPass() | [vkCmdBeginRenderPass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBeginRenderPass.html) |
| VulkanCommandBufferType.bindDescriptorSets() | [vkCmdBindDescriptorSets](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBindDescriptorSets.html) |
| VulkanCommandBufferType.bindIndexBuffer() | [vkCmdBindIndexBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBindIndexBuffer.html) |
| VulkanCommandBufferType.bindPipeline() | [vkCmdBindPipeline](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBindPipeline.html) |
| VulkanCommandBufferType.bindVertexBuffers() | [vkCmdBindVertexBuffers](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBindVertexBuffers.html) |
| VulkanCommandBufferType.blitImage() | [vkCmdBlitImage](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdBlitImage.html) |
| VulkanCommandBufferType.clearAttachments() | [vkCmdClearAttachments](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdClearAttachments.html) |
| VulkanCommandBufferType.clearColorImage() | [vkCmdClearColorImage](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdClearColorImage.html) |
| VulkanCommandBufferType.clearDepthStencilImage() | [vkCmdClearDepthStencilImage](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdClearDepthStencilImage.html) |
| VulkanCommandBufferType.copyBuffer() | [vkCmdCopyBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdCopyBuffer.html) |
| VulkanCommandBufferType.copyBufferToImage() | [vkCmdCopyBufferToImage](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdCopyBufferToImage.html) |
| VulkanCommandBufferType.copyImage() | [vkCmdCopyImage](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdCopyImage.html) |
| VulkanCommandBufferType.copyImageToBuffer() | [vkCmdCopyImageToBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdCopyImageToBuffer.html) |
| VulkanCommandBufferType.dispatch() | [vkCmdDispatch](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdDispatch.html) |
| VulkanCommandBufferType.draw() | [vkCmdDraw](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdDraw.html) |
| VulkanCommandBufferType.drawIndexed() | [vkCmdDrawIndexed](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdDrawIndexed.html) |
| VulkanCommandBufferType.drawIndexedIndirect() | [vkCmdDrawIndexedIndirect](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdDrawIndexedIndirect.html) |
| VulkanCommandBufferType.drawIndirect() | [vkCmdDrawIndirect](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdDrawIndirect.html) |
| VulkanCommandBufferType.endCommandBuffer() | [vkEndCommandBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEndCommandBuffer.html) |
| VulkanCommandBufferType.endQuery() | [vkCmdEndQuery](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdEndQuery.html) |
| VulkanCommandBufferType.endRenderPass() | [vkCmdEndRenderPass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdEndRenderPass.html) |
| VulkanCommandBufferType.executeCommands() | [vkCmdExecuteCommands](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdExecuteCommands.html) |
| VulkanCommandBufferType.fillBuffer() | [vkCmdFillBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdFillBuffer.html) |
| VulkanCommandBufferType.nextSubpass() | [vkCmdNextSubpass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdNextSubpass.html) |
| VulkanCommandBufferType.pipelineBarrier() | [vkCmdPipelineBarrier](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdPipelineBarrier.html) |
| VulkanCommandBufferType.reset() | [vkResetCommandBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkResetCommandBuffer.html) |
| VulkanCommandBufferType.resetEvent() | [vkCmdResetEvent](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdResetEvent.html) |
| VulkanCommandBufferType.resetQueryPool() | [vkCmdResetQueryPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdResetQueryPool.html) |
| VulkanCommandBufferType.setBlendConstants() | [vkCmdSetBlendConstants](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdSetBlendConstants.html) |
| VulkanCommandBufferType.setDepthBias() | [vkCmdSetDepthBias](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdSetDepthBias.html) |
| VulkanCommandBufferType.setDepthBounds() | [vkCmdSetDepthBounds](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdSetDepthBounds.html) |
| VulkanCommandBufferType.setEvent() | [vkCmdSetEvent](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdSetEvent.html) |
| VulkanCommandBufferType.setLineWidth() | [vkCmdSetLineWidth](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdSetLineWidth.html) |
| VulkanCommandBufferType.setScissor() | [vkCmdSetScissor](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdSetScissor.html) |
| VulkanCommandBufferType.setStencilCompareMask() | [vkCmdSetStencilCompareMask](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdSetStencilCompareMask.html) |
| VulkanCommandBufferType.setStencilReference() | [vkCmdSetStencilReference](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdSetStencilReference.html) |
| VulkanCommandBufferType.setStencilWriteMask() | [vkCmdSetStencilWriteMask](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdSetStencilWriteMask.html) |
| VulkanCommandBufferType.setViewport() | [vkCmdSetViewport](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdSetViewport.html) |
| VulkanCommandBufferType.waitEvents() | [vkCmdWaitEvents](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdWaitEvents.html) |
| VulkanCommandBufferType.writeTimestamp() | [vkCmdWriteTimestamp](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCmdWriteTimestamp.html) |
| VulkanCommandPoolType.close() | [vkDestroyCommandPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyCommandPool.html) |
| VulkanDescriptorPoolType.close() | [vkDestroyDescriptorPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyDescriptorPool.html) |
| VulkanDescriptorSetLayoutType.close() | [vkDestroyDescriptorSetLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyDescriptorSetLayout.html) |
| VulkanDeviceMemoryType.close() | [vkFreeMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkFreeMemory.html) |
| VulkanEventType.close() | [vkDestroyEvent](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyEvent.html) |
| VulkanFenceType.close() | [vkDestroyFence](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyFence.html) |
| VulkanFramebufferType.close() | [vkDestroyFramebuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyFramebuffer.html) |
| VulkanImageType.close() | [vkDestroyImage](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyImage.html) |
| VulkanImageType.close() | [vmaDestroyImage](https://gpuopen-librariesandsdks.github.io/VulkanMemoryAllocator/html/globals_func.html#index_v) |
| VulkanImageViewType.close() | [vkDestroyImageView](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyImageView.html) |
| VulkanInstanceProviderType.createInstance() | [vkCreateInstance](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateInstance.html) |
| VulkanInstanceProviderType.extensions() | [vkEnumerateInstanceExtensionProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumerateInstanceExtensionProperties.html) |
| VulkanInstanceProviderType.findSupportedInstanceVersion() | [vkEnumerateInstanceVersion](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumerateInstanceVersion.html) |
| VulkanInstanceProviderType.layers() | [vkEnumerateInstanceLayerProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumerateInstanceLayerProperties.html) |
| VulkanInstanceType.close() | [vkDestroyInstance](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyInstance.html) |
| VulkanInstanceType.enumeratePhysicalDevices() | [vkEnumeratePhysicalDevices](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumeratePhysicalDevices.html) |
| VulkanInstanceType.physicalDevices() | [vkEnumeratePhysicalDevices](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumeratePhysicalDevices.html) |
| VulkanLogicalDeviceType.allocateDescriptorSets() | [vkAllocateDescriptorSets](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkAllocateDescriptorSets.html) |
| VulkanLogicalDeviceType.allocateMemory() | [vkAllocateMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkAllocateMemory.html) |
| VulkanLogicalDeviceType.bindBufferMemory() | [vkBindBufferMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkBindBufferMemory.html) |
| VulkanLogicalDeviceType.bindImageMemory() | [vkBindImageMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkBindImageMemory.html) |
| VulkanLogicalDeviceType.close() | [vkDestroyDevice](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyDevice.html) |
| VulkanLogicalDeviceType.createBuffer() | [vkCreateBuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateBuffer.html) |
| VulkanLogicalDeviceType.createBufferView() | [vkCreateBufferView](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateBufferView.html) |
| VulkanLogicalDeviceType.createCommandBuffer() | [vkAllocateCommandBuffers](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkAllocateCommandBuffers.html) |
| VulkanLogicalDeviceType.createCommandBuffers() | [vkAllocateCommandBuffers](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkAllocateCommandBuffers.html) |
| VulkanLogicalDeviceType.createCommandPool() | [vkCreateCommandPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateCommandPool.html) |
| VulkanLogicalDeviceType.createComputePipeline() | [vkCreateComputePipelines](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateComputePipelines.html) |
| VulkanLogicalDeviceType.createComputePipelines() | [vkCreateComputePipelines](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateComputePipelines.html) |
| VulkanLogicalDeviceType.createDescriptorPool() | [vkCreateDescriptorPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateDescriptorPool.html) |
| VulkanLogicalDeviceType.createDescriptorSetLayout() | [vkCreateDescriptorSetLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateDescriptorSetLayout.html) |
| VulkanLogicalDeviceType.createEvent() | [vkCreateEvent](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateEvent.html) |
| VulkanLogicalDeviceType.createFence() | [vkCreateFence](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateFence.html) |
| VulkanLogicalDeviceType.createFramebuffer() | [vkCreateFramebuffer](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateFramebuffer.html) |
| VulkanLogicalDeviceType.createGraphicsPipeline() | [vkCreateGraphicsPipelines](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateGraphicsPipelines.html) |
| VulkanLogicalDeviceType.createGraphicsPipelines() | [vkCreateGraphicsPipelines](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateGraphicsPipelines.html) |
| VulkanLogicalDeviceType.createImage() | [vkCreateImage](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateImage.html) |
| VulkanLogicalDeviceType.createImageView() | [vkCreateImageView](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateImageView.html) |
| VulkanLogicalDeviceType.createPipelineCache() | [vkCreatePipelineCache](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreatePipelineCache.html) |
| VulkanLogicalDeviceType.createPipelineLayout() | [vkCreatePipelineLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreatePipelineLayout.html) |
| VulkanLogicalDeviceType.createQueryPool() | [vkCreateQueryPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateQueryPool.html) |
| VulkanLogicalDeviceType.createRenderPass() | [vkCreateRenderPass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateRenderPass.html) |
| VulkanLogicalDeviceType.createSampler() | [vkCreateSampler](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateSampler.html) |
| VulkanLogicalDeviceType.createSemaphore() | [vkCreateSemaphore](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateSemaphore.html) |
| VulkanLogicalDeviceType.createShaderModule() | [vkCreateShaderModule](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateShaderModule.html) |
| VulkanLogicalDeviceType.flushMappedMemoryRange() | [vkFlushMappedMemoryRanges](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkFlushMappedMemoryRanges.html) |
| VulkanLogicalDeviceType.flushMappedMemoryRanges() | [vkFlushMappedMemoryRanges](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkFlushMappedMemoryRanges.html) |
| VulkanLogicalDeviceType.getBufferMemoryRequirements() | [vkGetBufferMemoryRequirements](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetBufferMemoryRequirements.html) |
| VulkanLogicalDeviceType.getEventStatus() | [vkGetEventStatus](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetEventStatus.html) |
| VulkanLogicalDeviceType.getFenceStatus() | [vkGetFenceStatus](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetFenceStatus.html) |
| VulkanLogicalDeviceType.getImageMemoryRequirements() | [vkGetImageMemoryRequirements](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetImageMemoryRequirements.html) |
| VulkanLogicalDeviceType.getImageSubresourceLayout() | [vkGetImageSubresourceLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetImageSubresourceLayout.html) |
| VulkanLogicalDeviceType.getPipelineCacheData() | [vkGetPipelineCacheData](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPipelineCacheData.html) |
| VulkanLogicalDeviceType.getPipelineCacheDataSize() | [vkGetPipelineCacheData](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPipelineCacheData.html) |
| VulkanLogicalDeviceType.mapMemory() | [vkMapMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkMapMemory.html) |
| VulkanLogicalDeviceType.mergePipelineCaches() | [vkMergePipelineCaches](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkMergePipelineCaches.html) |
| VulkanLogicalDeviceType.queue() | [vkGetDeviceQueue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetDeviceQueue.html) |
| VulkanLogicalDeviceType.queues() | [vkGetDeviceQueue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetDeviceQueue.html) |
| VulkanLogicalDeviceType.resetCommandPool() | [vkResetCommandPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkResetCommandPool.html) |
| VulkanLogicalDeviceType.resetDescriptorPool() | [vkResetDescriptorPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkResetDescriptorPool.html) |
| VulkanLogicalDeviceType.resetEvent() | [vkResetEvent](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkResetEvent.html) |
| VulkanLogicalDeviceType.resetFences() | [vkResetFences](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkResetFences.html) |
| VulkanLogicalDeviceType.setEvent() | [vkSetEvent](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkSetEvent.html) |
| VulkanLogicalDeviceType.updateDescriptorSets() | [vkUpdateDescriptorSets](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkUpdateDescriptorSets.html) |
| VulkanLogicalDeviceType.waitForFence() | [vkWaitForFences](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkWaitForFences.html) |
| VulkanLogicalDeviceType.waitForFences() | [vkWaitForFences](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkWaitForFences.html) |
| VulkanLogicalDeviceType.waitIdle() | [vkDeviceWaitIdle](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDeviceWaitIdle.html) |
| VulkanMappedMemoryType.close() | [vkUnmapMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkUnmapMemory.html) |
| VulkanMappedMemoryType.close() | [vmaUnmapMemory](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vmaUnmapMemory.html) |
| VulkanMappedMemoryType.flush() | [vkFlushMappedMemoryRanges](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkFlushMappedMemoryRanges.html) |
| VulkanMappedMemoryType.flushRange() | [vkFlushMappedMemoryRanges](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkFlushMappedMemoryRanges.html) |
| VulkanPhysicalDeviceType.createLogicalDevice() | [vkCreateDevice](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkCreateDevice.html) |
| VulkanPhysicalDeviceType.createLogicalDevice() | [vkGetDeviceQueue](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetDeviceQueue.html) |
| VulkanPhysicalDeviceType.extensions() | [vkEnumerateDeviceExtensionProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumerateDeviceExtensionProperties.html) |
| VulkanPhysicalDeviceType.features() | [vkGetPhysicalDeviceFeatures2](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceFeatures2.html) |
| VulkanPhysicalDeviceType.features() | [vkGetPhysicalDeviceFeatures](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceFeatures.html) |
| VulkanPhysicalDeviceType.formatProperties() | [vkGetPhysicalDeviceFormatProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceFormatProperties.html) |
| VulkanPhysicalDeviceType.imageFormatProperties() | [vkGetPhysicalDeviceImageFormatProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceImageFormatProperties.html) |
| VulkanPhysicalDeviceType.layers() | [vkEnumerateDeviceLayerProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkEnumerateDeviceLayerProperties.html) |
| VulkanPhysicalDeviceType.limits() | [vkGetPhysicalDeviceProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceProperties.html) |
| VulkanPhysicalDeviceType.limits() | [vkGetPhysicalDeviceProperties2](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceProperties2.html) |
| VulkanPhysicalDeviceType.memory() | [vkGetPhysicalDeviceMemoryProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceMemoryProperties.html) |
| VulkanPhysicalDeviceType.properties() | [vkGetPhysicalDeviceProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceProperties.html) |
| VulkanPhysicalDeviceType.queueFamilies() | [vkGetPhysicalDeviceQueueFamilyProperties](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkGetPhysicalDeviceQueueFamilyProperties.html) |
| VulkanPipelineCacheType.close() | [vkDestroyPipelineCache](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyPipelineCache.html) |
| VulkanPipelineLayoutType.close() | [vkDestroyPipelineLayout](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyPipelineLayout.html) |
| VulkanPipelineType.close() | [vkDestroyPipeline](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyPipeline.html) |
| VulkanQueryPoolType.close() | [vkDestroyQueryPool](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyQueryPool.html) |
| VulkanQueueType.submit() | [vkQueueSubmit](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkQueueSubmit.html) |
| VulkanQueueType.waitIdle() | [vkQueueWaitIdle](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkQueueWaitIdle.html) |
| VulkanRenderPassType.close() | [vkDestroyRenderPass](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyRenderPass.html) |
| VulkanSamplerType.close() | [vkDestroySampler](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroySampler.html) |
| VulkanSemaphoreType.close() | [vkDestroySemaphore](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroySemaphore.html) |
| VulkanShaderModuleType.close() | [vkDestroyShaderModule](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyShaderModule.html) |
| VulkanDebugUtilsMessengerEXTType.close() | [vkDestroyDebugUtilsMessengerEXT](https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html/vkDestroyDebugUtilsMessengerEXT.html) |

## Missing Functions

* `vkAcquirePerformanceConfigurationINTEL`
* `vkBindBufferMemory2`
* `vkBindImageMemory2`
* `vkCmdBeginRenderPass2`
* `vkCmdBindInvocationMaskHUAWEI`
* `vkCmdCopyQueryPoolResults`
* `vkCmdDispatchBase`
* `vkCmdDispatchIndirect`
* `vkCmdDrawIndexedIndirectCount`
* `vkCmdDrawIndirectCount`
* `vkCmdEndRenderPass2`
* `vkCmdNextSubpass2`
* `vkCmdPushConstants`
* `vkCmdResolveImage`
* `vkCmdSetDeviceMask`
* `vkCmdSetPerformanceMarkerINTEL`
* `vkCmdSetPerformanceOverrideINTEL`
* `vkCmdSetPerformanceStreamMarkerINTEL`
* `vkCmdSubpassShadingHUAWEI`
* `vkCmdUpdateBuffer`
* `vkCreateDescriptorUpdateTemplate`
* `vkCreateRenderPass2`
* `vkCreateSamplerYcbcrConversion`
* `vkCreateScreenSurfaceQNX`
* `vkCreateStreamDescriptorSurfaceGGP`
* `vkDestroyDescriptorUpdateTemplate`
* `vkDestroySamplerYcbcrConversion`
* `vkEnumeratePhysicalDeviceGroups`
* `vkFreeCommandBuffers`
* `vkFreeDescriptorSets`
* `vkGetBufferDeviceAddress`
* `vkGetBufferMemoryRequirements2`
* `vkGetBufferOpaqueCaptureAddress`
* `vkGetDescriptorSetLayoutSupport`
* `vkGetDeviceGroupPeerMemoryFeatures`
* `vkGetDeviceMemoryCommitment`
* `vkGetDeviceMemoryOpaqueCaptureAddress`
* `vkGetDeviceProcAddr`
* `vkGetDeviceQueue2`
* `vkGetDeviceSubpassShadingMaxWorkgroupSizeHUAWEI`
* `vkGetImageMemoryRequirements2`
* `vkGetImageSparseMemoryRequirements`
* `vkGetImageSparseMemoryRequirements2`
* `vkGetInstanceProcAddr`
* `vkGetPerformanceParameterINTEL`
* `vkGetPhysicalDeviceExternalBufferProperties`
* `vkGetPhysicalDeviceExternalFenceProperties`
* `vkGetPhysicalDeviceExternalSemaphoreProperties`
* `vkGetPhysicalDeviceFormatProperties2`
* `vkGetPhysicalDeviceImageFormatProperties2`
* `vkGetPhysicalDeviceMemoryProperties2`
* `vkGetPhysicalDeviceQueueFamilyProperties2`
* `vkGetPhysicalDeviceScreenPresentationSupportQNX`
* `vkGetPhysicalDeviceSparseImageFormatProperties`
* `vkGetPhysicalDeviceSparseImageFormatProperties2`
* `vkGetQueryPoolResults`
* `vkGetRenderAreaGranularity`
* `vkGetSemaphoreCounterValue`
* `vkInitializePerformanceApiINTEL`
* `vkInvalidateMappedMemoryRanges`
* `vkQueueBindSparse`
* `vkQueueSetPerformanceConfigurationINTEL`
* `vkReleasePerformanceConfigurationINTEL`
* `vkResetQueryPool`
* `vkSignalSemaphore`
* `vkTrimCommandPool`
* `vkUninitializePerformanceApiINTEL`
* `vkUpdateDescriptorSetWithTemplate`
* `vkWaitSemaphores`

128 of 193 functions implemented

Coverage: 66.32%

