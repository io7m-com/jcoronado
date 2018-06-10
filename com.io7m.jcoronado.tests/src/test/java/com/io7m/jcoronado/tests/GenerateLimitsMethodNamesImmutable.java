package com.io7m.jcoronado.tests;

import com.io7m.jcoronado.api.VulkanPhysicalDeviceLimits;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class GenerateLimitsMethodNamesImmutable
{
  private GenerateLimitsMethodNamesImmutable()
  {

  }

  public static void main(
    final String[] args)
  {
    List.of(VulkanPhysicalDeviceLimits.Builder.class.getMethods())
      .stream()
      .filter(m -> Objects.equals(
        m.getDeclaringClass(),
        VulkanPhysicalDeviceLimits.Builder.class))
      .filter(m -> m.getName().startsWith("set"))
      .sorted(Comparator.comparing(Method::getName))
      .forEach(method -> {
        final String name = method.getName();
        String original =
          name.replaceFirst("set", "");
        original =
          Character.toLowerCase(original.charAt(0)) + original.substring(1);

        System.out.printf(".%s(vk_limits.%s())\n", name, original);
      });
  }
}
