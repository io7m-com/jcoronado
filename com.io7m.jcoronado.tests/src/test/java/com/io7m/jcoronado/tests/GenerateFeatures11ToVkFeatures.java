package com.io7m.jcoronado.tests;

import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures;
import org.lwjgl.vulkan.VkPhysicalDeviceVulkan11Features;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class GenerateFeatures11ToVkFeatures
{
  private GenerateFeatures11ToVkFeatures()
  {

  }

  public static void main(
    final String[] args)
  {
    List.of(VkPhysicalDeviceVulkan11Features.class.getMethods())
      .stream()
      .filter(m -> Objects.equals(
        m.getDeclaringClass(),
        VkPhysicalDeviceVulkan11Features.class))
      .filter(m -> Objects.equals(m.getReturnType(), boolean.class))
      .sorted(Comparator.comparing(Method::getName))
      .forEach(method -> {
        System.out.printf(
          ".%s(features.%s())\n",
          method.getName(),
          method.getName());
      });
  }
}
