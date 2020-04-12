package com.io7m.jcoronado.tests;

import com.io7m.jcoronado.api.VulkanAPIFunctionType;
import com.io7m.jcoronado.api.VulkanAPIFunctionsType;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public final class GenerateFunctionCoverage
{
  private GenerateFunctionCoverage()
  {

  }

  public static void main(
    final String[] args)
    throws Exception
  {
    final var reflections =
      new Reflections(
        "com.io7m.jcoronado",
        new MethodAnnotationsScanner(),
        new SubTypesScanner(),
        new TypeAnnotationsScanner());

    showMissingFunctions(reflections);
  }

  static void showMissingFunctions(
    final Reflections reflections)
    throws
    ParserConfigurationException,
    SAXException,
    IOException,
    XPathExpressionException
  {
    final var reflect_names = loadNamesReflectively(reflections);
    final var registry_names = loadVulkanRegistryFunctionNames();

    System.out.println("## Missing Functions");
    System.out.println();

    for (final var name : registry_names) {
      if (!reflect_names.containsKey(name)) {
        System.out.println("* `" + name + "`");
      } else {
        // System.out.println("PRESENT " + name);
      }
    }

    final var expected = (double) registry_names.size();
    final var received = (double) reflect_names.size();
    System.out.println();
    System.out.printf(
      "%d of %d functions implemented\n",
      Integer.valueOf(reflect_names.size()),
      Integer.valueOf(registry_names.size()));
    System.out.println();
    System.out.printf(
      "Coverage: %.2f%%\n",
      Double.valueOf((received / expected) * 100.0));
  }

  private static Map<String, Method> loadNamesReflectively(
    final Reflections reflections)
  {
    final HashMap<String, Method> methods = new HashMap<>(256);

    final var funcs0 = reflections.getMethodsAnnotatedWith(VulkanAPIFunctionType.class);
    for (final var func : funcs0) {
      final var annot = func.getDeclaredAnnotation(VulkanAPIFunctionType.class);
      if (annot == null) {
        continue;
      }
      methods.put(annot.vulkanFunction(), func);
    }

    final var funcs1 =
      reflections.getMethodsAnnotatedWith(VulkanAPIFunctionsType.class);
    for (final var func : funcs1) {
      final var annot = func.getDeclaredAnnotation(VulkanAPIFunctionsType.class);
      if (annot == null) {
        continue;
      }
      for (final var annot_func : annot.value()) {
        methods.put(annot_func.vulkanFunction(), func);
      }
    }

    return methods;
  }

  private static TreeSet<String> loadVulkanRegistryFunctionNames()
    throws
    ParserConfigurationException,
    SAXException,
    IOException,
    XPathExpressionException
  {
    final var factory = DocumentBuilderFactory.newInstance();
    final var builder = factory.newDocumentBuilder();
    final var doc = builder.parse("Vulkan-Headers/registry/vk.xml");
    final var xpath_factory = XPathFactory.newInstance();
    final var xpath = xpath_factory.newXPath();
    final var expr = xpath.compile("//registry/commands/command/proto/name");

    final var names = new TreeSet<String>();
    final var nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    for (var index = 0; index < nodes.getLength(); ++index) {
      final var item = nodes.item(index);
      final var name = item.getTextContent();
      if (isAllowed(name)) {
        names.add(name);
      }
    }
    return names;
  }

  private static boolean isAllowed(final String name)
  {
    if (name.endsWith("AMD")) {
      return false;
    }
    if (name.endsWith("FUCHSIA")) {
      return false;
    }
    if (name.endsWith("NN")) {
      return false;
    }
    if (name.endsWith("MVK")) {
      return false;
    }
    if (name.endsWith("GOOGLE")) {
      return false;
    }
    if (name.endsWith("NV")) {
      return false;
    }
    if (name.endsWith("NVX")) {
      return false;
    }
    if (name.endsWith("KHR")) {
      return false;
    }
    if (name.endsWith("EXT")) {
      return false;
    }
    return !name.endsWith("ANDROID");
  }
}
