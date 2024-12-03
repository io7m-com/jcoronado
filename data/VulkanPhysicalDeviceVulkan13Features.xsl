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

  <xsl:template match="/registry/types/type[@name='VkPhysicalDeviceVulkan13Features']">
    <xsl:text disable-output-escaping="yes"><![CDATA[
/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

package com.io7m.jcoronado.api;

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

@ImmutablesStyleType
@Value.Immutable
public interface VulkanPhysicalDeviceFeatures13Type
{
]]></xsl:text>
    <xsl:apply-templates select="member" mode="java"/>
    <xsl:text disable-output-escaping="yes"><![CDATA[
}
]]></xsl:text>
  </xsl:template>

  <xsl:template match="member" mode="java">
    <xsl:text>&#xa;</xsl:text>
    <xsl:value-of select="concat('/** @return ', comment, ' */')"/>
    <xsl:text>&#xa;</xsl:text>
    <xsl:text>
  @Value.Default
</xsl:text>
    <xsl:text>  default </xsl:text>
    <xsl:apply-templates select="type" mode="struct-field"/>
    <xsl:text> </xsl:text>
    <xsl:value-of select="name"/>
    <xsl:text>() {&#xa;</xsl:text>
    <xsl:text>    return false;&#xa;</xsl:text>
    <xsl:text>  }&#xa;</xsl:text>
  </xsl:template>

  <xsl:template match="type" mode="struct-field">
    <xsl:if test="compare(text(), 'VkBool32') = 0">
      <xsl:text>boolean</xsl:text>
    </xsl:if>
  </xsl:template>

</xsl:stylesheet>
