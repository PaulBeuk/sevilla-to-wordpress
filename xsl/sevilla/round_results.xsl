<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="Round"/>
	<xsl:output method="xml" indent="no" encoding="UTF-8"/>
	
	<xsl:template match="/">
			<xsl:apply-templates select="SevillaRpt/Report/GroupReport/RoundHist[ID=$Round]" />
	</xsl:template>
	
	<xsl:template match="RoundHist">
		<roundResults>
			<xsl:for-each select="GameHist">
				<xsl:apply-templates select="."/>
			</xsl:for-each>
		</roundResults>
	</xsl:template>
	
	<xsl:template match="GameHist">
		<roundResult>
			<white>
				<xsl:value-of select="White"/>
			</white>
			<black>
				<xsl:value-of select="Black"/>
			</black>
			<result>
				<xsl:value-of select="Res"/>
			</result>
		</roundResult>
	</xsl:template>
</xsl:stylesheet>
