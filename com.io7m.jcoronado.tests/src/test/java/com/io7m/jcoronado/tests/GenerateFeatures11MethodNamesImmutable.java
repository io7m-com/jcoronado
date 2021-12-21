package com.io7m.jcoronado.tests;

import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures;
import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures11;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class GenerateFeatures11MethodNamesImmutable
{
  private GenerateFeatures11MethodNamesImmutable()
  {

  }

  public static void main(
    final String[] args)
  {
    List.of(VulkanPhysicalDeviceFeatures11.Builder.class.getMethods())
      .stream()
      .filter(m -> Objects.equals(
        m.getDeclaringClass(),
        VulkanPhysicalDeviceFeatures11.Builder.class))
      .filter(m -> m.getName().startsWith("set"))
      .sorted(Comparator.comparing(Method::getName))
      .forEach(method -> {
        final var name = method.getName();
        var original =
          name.replaceFirst("set", "");
        original =
          Character.toLowerCase(original.charAt(0)) + original.substring(1);

        System.out.printf(".%s(features11.%s())\n", name, original);
      });
  }
}
