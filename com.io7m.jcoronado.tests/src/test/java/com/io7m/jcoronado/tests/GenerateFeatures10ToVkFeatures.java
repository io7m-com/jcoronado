package com.io7m.jcoronado.tests;

import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class GenerateFeatures10ToVkFeatures
{
  private GenerateFeatures10ToVkFeatures()
  {

  }

  public static void main(
    final String[] args)
  {
    List.of(VulkanPhysicalDeviceFeatures.class.getMethods())
      .stream()
      .filter(m -> Objects.equals(
        m.getDeclaringClass(),
        VulkanPhysicalDeviceFeatures.class))
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
