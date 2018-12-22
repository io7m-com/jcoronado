package com.io7m.jcoronado.tests;

import com.io7m.jcoronado.api.VulkanPhysicalDeviceFeatures;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class GenerateFeaturesMethodNamesImmutable
{
  private GenerateFeaturesMethodNamesImmutable()
  {

  }

  public static void main(
    final String[] args)
  {
    List.of(VulkanPhysicalDeviceFeatures.Builder.class.getMethods())
      .stream()
      .filter(m -> Objects.equals(
        m.getDeclaringClass(),
        VulkanPhysicalDeviceFeatures.Builder.class))
      .filter(m -> m.getName().startsWith("set"))
      .sorted(Comparator.comparing(Method::getName))
      .forEach(method -> {
        final var name = method.getName();
        var original =
          name.replaceFirst("set", "");
        original =
          Character.toLowerCase(original.charAt(0)) + original.substring(1);

        System.out.printf(".%s(vk_features.%s())\n", name, original);
      });
  }
}
