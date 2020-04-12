package com.io7m.jcoronado.tests;

import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class GenerateFeaturesMethodNames
{
  private GenerateFeaturesMethodNames()
  {

  }

  public static void main(
    final String[] args)
  {
    List.of(VkPhysicalDeviceFeatures.class.getMethods())
      .stream()
      .filter(m -> Objects.equals(
        m.getDeclaringClass(),
        VkPhysicalDeviceFeatures.class))
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
