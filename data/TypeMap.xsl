<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet
  version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output omit-xml-declaration="yes" indent="no"/>

  <xsl:template match="/ | @* | node()">
    <xsl:apply-templates select="@* | node()" />
  </xsl:template>

  <xsl:template match="/registry/types/type[@category='handle']">
    <xsl:variable name="transformed_name"
                  select="replace(name, '^Vk', 'Vulkan')"/>
    <xsl:variable name="file"
                  select="concat($transformed_name, 'Type.java')"/>
    <xsl:variable name="path"
                  select="concat('out/', $file)"/>

    <xsl:if test="name != ''">
      <xsl:result-document href="{$path}" method="text">
        <xsl:text disable-output-escaping="yes"><![CDATA[/*
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

package com.io7m.jcoronado.api;

/**
]]></xsl:text>
        <xsl:value-of select="concat(' * @see ', '&quot;', name, '&quot;')"/>
        <xsl:text disable-output-escaping="yes"><![CDATA[
 */

]]></xsl:text>
        <xsl:value-of select="concat('public interface ', $transformed_name,'Type')"/>

        <xsl:if test="compare(type, 'VK_DEFINE_HANDLE') = 0">
          <xsl:value-of select="' extends VulkanHandleDispatchableType'"/>
        </xsl:if>
        <xsl:if test="compare(type, 'VK_DEFINE_NON_DISPATCHABLE_HANDLE') = 0">
          <xsl:value-of select="' extends VulkanHandleNonDispatchableType'"/>
        </xsl:if>

        <xsl:text>
{

}

        </xsl:text>
      </xsl:result-document>
    </xsl:if>
  </xsl:template>

</xsl:stylesheet>
