<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet
  version="2.0"
  xmlns:math="http://www.w3.org/2005/xpath-functions/math"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output omit-xml-declaration="yes" indent="no"/>

  <xsl:param name="project"/>

  <xsl:template match="/ | @* | node()">
    <xsl:apply-templates select="@* | node()" />
  </xsl:template>

  <xsl:template match="/registry/enums[@name='VkPresentModeKHR']">
    <xsl:text disable-output-escaping="yes"><![CDATA[
/*
 * Copyright © 2018 Mark Raynsford <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jcoronado.extensions.api;

public enum VulkanPresentModeKHR
{
]]></xsl:text>
    <xsl:apply-templates select="enum" mode="java"/>
    <xsl:text disable-output-escaping="yes"><![CDATA[
  ;

  private final int value;

  VulkanPresentModeKHR(
    final int i)
  {
    this.value = i;
  }

  /**
   * @return The integer value of the constant
   */

  public int value()
  {
    return this.value;
  }
}
]]></xsl:text>
  </xsl:template>

  <xsl:template match="enum" mode="java">
    <xsl:value-of select="concat('  /** ', @comment, ' */')"/>
    <xsl:text>&#xa;</xsl:text>
    <xsl:text>&#xa;</xsl:text>
    <xsl:value-of select="concat('  ', @name, '(', @value, ')', ',')"/>
    <xsl:text>&#xa;</xsl:text>
    <xsl:text>&#xa;</xsl:text>
  </xsl:template>

</xsl:stylesheet>
