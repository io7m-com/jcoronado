package com.io7m.jcoronado.tests.api;

import com.io7m.jcoronado.api.VulkanEnumBitmaskType;
import com.io7m.jcoronado.api.VulkanEnumIntegerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class VulkanEnumsTest
{
  /**
   * Check that enum integer values are unique.
   */

  @Test
  public void testEnumIntegerValues()
  {
    final Reflections reflections = new Reflections("com.io7m.jcoronado");

    final Set<Class<? extends VulkanEnumIntegerType>> enums =
      reflections.getSubTypesOf(VulkanEnumIntegerType.class);

    final Collection<Executable> executables = new ArrayList<>();

    for (final Class<? extends VulkanEnumIntegerType> c : enums) {
      final List<VulkanEnumIntegerType> constants = List.of(c.getEnumConstants());

      final HashSet<Integer> numbers = new HashSet<Integer>();
      for (final VulkanEnumIntegerType constant : constants) {
        numbers.add(Integer.valueOf(constant.value()));
      }

      executables.add(() -> {
        Assertions.assertEquals(
          constants.size(),
          numbers.size(),
          new StringBuilder(32)
            .append("Enum values must be unique for type ")
            .append(c.getCanonicalName())
            .toString());
      });
    }

    Assertions.assertAll(executables);
  }

  /**
   * Check that enum integer values are unique.
   */

  @Test
  public void testEnumBitmaskValues()
  {
    final Reflections reflections = new Reflections("com.io7m.jcoronado");

    final Set<Class<? extends VulkanEnumBitmaskType>> enums =
      reflections.getSubTypesOf(VulkanEnumBitmaskType.class);

    final Collection<Executable> executables = new ArrayList<>();

    for (final Class<? extends VulkanEnumBitmaskType> c : enums) {
      final List<VulkanEnumBitmaskType> constants = List.of(c.getEnumConstants());

      final HashSet<Integer> numbers = new HashSet<Integer>();
      for (final VulkanEnumBitmaskType constant : constants) {
        numbers.add(Integer.valueOf(constant.value()));
      }

      executables.add(() -> {
        Assertions.assertEquals(
          constants.size(),
          numbers.size(),
          new StringBuilder(32)
            .append("Enum values must be unique for type ")
            .append(c.getCanonicalName())
            .toString());
      });
    }

    Assertions.assertAll(executables);
  }
}
