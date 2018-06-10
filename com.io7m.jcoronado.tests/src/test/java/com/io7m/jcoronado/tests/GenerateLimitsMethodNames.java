package com.io7m.jcoronado.tests;

import org.lwjgl.vulkan.VkPhysicalDeviceLimits;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class GenerateLimitsMethodNames
{
  private GenerateLimitsMethodNames()
  {

  }

  public static void main(
    final String[] args)
  {
    List.of(VkPhysicalDeviceLimits.class.getMethods())
      .stream()
      .filter(m -> Objects.equals(m.getDeclaringClass(), VkPhysicalDeviceLimits.class))
      .filter(m -> !m.getName().startsWith("n"))
      .sorted(Comparator.comparing(Method::getName))
      .forEach(method -> {
        System.out.printf("@Value.Parameter\n%s %s();\n\n", method.getReturnType().getName(),  method.getName());
      });
  }
}
