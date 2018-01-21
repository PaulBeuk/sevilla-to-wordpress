<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes"/>

	<xsl:template match="/">
		<categories>
			<xsl:apply-templates select="SevillaRpt/Report/CompData/Category" mode="categoryInfo"/>
		</categories>
	</xsl:template>
	
	<xsl:template match="Category" mode="categoryInfo">
		<category>
			<ID><xsl:value-of select="ID"/></ID>
 	 		<Name><xsl:value-of select="Name"/></Name>
			<ShortName><xsl:value-of select="ShortName"/></ShortName>
 		</category>
 	</xsl:template>
</xsl:stylesheet>
