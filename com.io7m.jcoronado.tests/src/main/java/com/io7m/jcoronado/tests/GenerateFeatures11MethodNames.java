package com.io7m.jcoronado.tests;

import org.lwjgl.vulkan.VkPhysicalDeviceVulkan11Features;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class GenerateFeatures11MethodNames
{
  private GenerateFeatures11MethodNames()
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
      .filter(m -> !m.getName().startsWith("n"))
      .filter(m -> Objects.equals(m.getReturnType(), boolean.class))
      .sorted(Comparator.comparing(Method::getName))
      .forEach(method -> {
        System.out.printf(
          "@Value.Parameter\n%s %s();\n\n",
          method.getReturnType().getName(),
          method.getName());
      });
  }
}
