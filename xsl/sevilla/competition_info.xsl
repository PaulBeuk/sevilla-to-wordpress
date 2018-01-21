<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:strip-space elements="*"/>
	<xsl:output method="xml" indent="yes"/>

	<xsl:template match="/">
		<report>
			<xsl:apply-templates select="SevillaRpt/Report" mode="competitionInfo"/>
		</report>
	</xsl:template>
	<xsl:template match="Report" mode="competitionInfo">
		<name>
			<xsl:value-of select="Name"/>
		</name>
	</xsl:template>
</xsl:stylesheet>
