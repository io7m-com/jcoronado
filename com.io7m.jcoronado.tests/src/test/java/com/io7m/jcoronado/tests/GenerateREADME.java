package com.io7m.jcoronado.tests;

import com.io7m.jcoronado.api.VulkanAPIEnumType;
import com.io7m.jcoronado.api.VulkanAPIFunctionType;
import com.io7m.jcoronado.api.VulkanAPIFunctionsType;
import com.io7m.jcoronado.api.VulkanAPIStructType;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public final class GenerateREADME
{
  private GenerateREADME()
  {

  }

  public static void main(
    final String[] args)
  {
    final var reflections =
      new Reflections(
        "com.io7m.jcoronado",
        new MethodAnnotationsScanner(),
        new SubTypesScanner(),
        new TypeAnnotationsScanner());

    final var repeats = new HashSet<String>();
    System.out.println();
    showStructs(reflections);
    showEnums(reflections);
    showFunctions(reflections, repeats);
  }

  private static void showFunctions(
    final Reflections reflections,
    final HashSet<String> repeats)
  {
    final var methods0 =
      reflections.getMethodsAnnotatedWith(VulkanAPIFunctionType.class);
    final var methods1 =
      reflections.getMethodsAnnotatedWith(VulkanAPIFunctionsType.class);

    final ArrayList<Method> methods2 = new ArrayList<>();
    methods2.addAll(methods0);
    methods2.addAll(methods1);
    methods2.sort(
      Comparator.<Method, String>comparing(m -> m.getDeclaringClass().getName())
        .thenComparing(Method::getName));

    System.out.println("## Functions");
    System.out.println();
    System.out.println("| jcoronado | Vulkan |");
    System.out.println("|-----------|--------|");

    for (final var method : methods2) {
      final var full_name = method.getDeclaringClass().getCanonicalName() + "." + method.getName();
      if (repeats.contains(full_name)) {
        continue;
      }
      repeats.add(full_name);

      {
        final var annotation = method.getAnnotation(VulkanAPIFunctionType.class);
        if (annotation != null) {
          showFunction(method, annotation);
          continue;
        }
      }

      {
        final var annotation = method.getAnnotation(VulkanAPIFunctionsType.class);
        if (annotation != null) {
          for (final var function : annotation.value()) {
            showFunction(method, function);
            continue;
          }
        }
      }
    }

    System.out.println();
  }

  private static void showFunction(
    final Method method,
    final VulkanAPIFunctionType annotation)
  {
    System.out.printf(
      "| %s.%s() | [%s](%s) |\n",
      method.getDeclaringClass().getSimpleName(),
      method.getName(),
      annotation.vulkanFunction(),
      apiFunctionURI(annotation));
  }

  private static void showStructs(
    final Reflections reflections)
  {
    final var types = new ArrayList<>(reflections.getTypesAnnotatedWith(VulkanAPIStructType.class));
    types.sort(Comparator.comparing(Class::getName));

    System.out.println("## Structs");
    System.out.println();
    System.out.println("| jcoronado | Vulkan |");
    System.out.println("|-----------|--------|");

    for (final var type : types) {
      if (!type.isInterface()) {
        continue;
      }

      final var annotation = type.getAnnotation(VulkanAPIStructType.class);
      if (annotation == null) {
        System.err.println("error: " + type);
        continue;
      }

      showStruct(type, annotation);
    }

    System.out.println();
  }

  private static void showStruct(
    final Class<?> type,
    final VulkanAPIStructType annotation)
  {
    System.out.printf(
      "| %s | [%s](%s) |\n",
      type.getSimpleName(),
      annotation.vulkanStruct(),
      apiStructURI(annotation));
  }

  private static void showEnums(
    final Reflections reflections)
  {
    final var types = new ArrayList<>(reflections.getTypesAnnotatedWith(VulkanAPIEnumType.class));
    types.sort(Comparator.comparing(Class::getName));

    System.out.println("## Enums");
    System.out.println();
    System.out.println("| jcoronado | Vulkan |");
    System.out.println("|-----------|--------|");

    for (final var type : types) {
      final var annotation = type.getAnnotation(VulkanAPIEnumType.class);
      if (annotation == null) {
        System.err.println("error: " + type);
        continue;
      }

      showEnum(type, annotation);
    }

    System.out.println();
  }

  private static void showEnum(
    final Class<?> type,
    final VulkanAPIEnumType annotation)
  {
    System.out.printf(
      "| %s | [%s](%s) |\n",
      type.getSimpleName(),
      annotation.vulkanEnum(),
      apiEnumURI(annotation));
  }

  private static String apiFunctionURI(
    final VulkanAPIFunctionType function)
  {
    final var api = function.api();
    switch (api) {
      case "vulkan": {
        return String.format(
          "%s/%s.html",
          "https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html",
          function.vulkanFunction());
      }
      case "vma": {
        return "https://gpuopen-librariesandsdks.github.io/VulkanMemoryAllocator/html/globals_func.html#index_v";
      }
      default: {
        throw new IllegalStateException("Unrecognized API: " + api);
      }
    }
  }

  private static String apiStructURI(
    final VulkanAPIStructType struct)
  {
    final var api = struct.api();
    switch (api) {
      case "vulkan": {
        return String.format(
          "%s/%s.html",
          "https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html",
          struct.vulkanStruct());
      }
      case "vma": {
        return "https://gpuopen-librariesandsdks.github.io/VulkanMemoryAllocator/html/globals_func.html#index_v";
      }
      default: {
        throw new IllegalStateException("Unrecognized API: " + api);
      }
    }
  }

  private static String apiEnumURI(
    final VulkanAPIEnumType enum_type)
  {
    final var api = enum_type.api();
    switch (api) {
      case "vulkan": {
        return String.format(
          "%s/%s.html",
          "https://www.khronos.org/registry/vulkan/specs/1.1-extensions/man/html",
          enum_type.vulkanEnum());
      }
      case "vma": {
        return "https://gpuopen-librariesandsdks.github.io/VulkanMemoryAllocator/html/globals_func.html#index_v";
      }
      default: {
        throw new IllegalStateException("Unrecognized API: " + api);
      }
    }
  }
}
