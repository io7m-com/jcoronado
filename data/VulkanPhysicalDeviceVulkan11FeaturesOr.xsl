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

  <xsl:template match="/registry/types/type[@name='VkPhysicalDeviceVulkan11Features']">
    <xsl:apply-templates select="member" mode="java">
      <xsl:sort select="name" />
    </xsl:apply-templates>
  </xsl:template>

  <xsl:template match="member" mode="java">
    <xsl:text>.set</xsl:text>
    <xsl:value-of select="concat(upper-case(substring(name,1,1)), substring(name, 2),' '[not(last())])"/>
    <xsl:text>(&#xa;  a.</xsl:text>
    <xsl:value-of select="name"/>
    <xsl:text>() || b.</xsl:text>
    <xsl:value-of select="name"/>
    <xsl:text>())&#xa;</xsl:text>
  </xsl:template>

</xsl:stylesheet>
