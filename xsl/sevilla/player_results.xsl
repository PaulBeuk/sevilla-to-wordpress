<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes" encoding="UTF-8"/>

	<xsl:param name="Round"/>

	<xsl:template match="/">
		<xsl:apply-templates select="SevillaRpt/Report/GroupReport/History[Round=$Round]"/>
	</xsl:template>

	<xsl:template match="History">
		<playerResults>
			<xsl:for-each select="PlayerHist">
				<playerResult>
					<id><xsl:value-of select="ID"/></id>
					<xsl:apply-templates select="AbsHist|GameHist"/>
				</playerResult>
			</xsl:for-each>
		</playerResults>
	</xsl:template>

	<xsl:template match="AbsHist">
		<game>
			<round><xsl:value-of select="Round"/></round>
			<white></white>
			<black></black>
			<result><xsl:value-of select="Reason"/></result>
		</game>
	</xsl:template>

	<xsl:template match="GameHist">
		<game>
			<round><xsl:value-of select="Round"/></round>
			<white><xsl:value-of select="White"/></white>
			<black><xsl:value-of select="Black"/></black>
			<result><xsl:value-of select="Res"/></result>
		</game>
	</xsl:template>
</xsl:stylesheet>
