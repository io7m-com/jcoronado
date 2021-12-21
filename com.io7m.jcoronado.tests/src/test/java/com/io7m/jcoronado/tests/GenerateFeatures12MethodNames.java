package com.io7m.jcoronado.tests;

import org.lwjgl.vulkan.VkPhysicalDeviceVulkan12Features;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class GenerateFeatures12MethodNames
{
  private GenerateFeatures12MethodNames()
  {

  }

  public static void main(
    final String[] args)
  {
    List.of(VkPhysicalDeviceVulkan12Features.class.getMethods())
      .stream()
      .filter(m -> Objects.equals(
        m.getDeclaringClass(),
        VkPhysicalDeviceVulkan12Features.class))
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
