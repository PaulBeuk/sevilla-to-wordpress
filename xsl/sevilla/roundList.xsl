<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" indent="no" encoding="UTF-8"/>

<xsl:template match="/ROUNDS">
	<lu class="previous_rounds">
		<xsl:apply-templates select="ROUND"/>
	</lu>
</xsl:template>

<xsl:template match="ROUND">
	<li>
		<a>
			<xsl:attribute name="href">
				<xsl:value-of select="URL"/>
			</xsl:attribute>
			<xsl:attribute name="target">_parent</xsl:attribute>
			<xsl:value-of select="NAME"/>
		</a>
	</li>
</xsl:template>

</xsl:stylesheet>
