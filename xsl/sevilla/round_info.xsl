<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<rounds>
			<xsl:apply-templates select="SevillaRpt/Report/GroupReport/Ranking" mode="roundInfo"/>
		</rounds>
	</xsl:template>

  	<xsl:template match="Ranking" mode="roundInfo">
  		<round>
			<Round>
				<xsl:value-of select="Round"/>
			</Round>
			<RankOrder>
				<xsl:value-of select="RankOrder"/>
			</RankOrder>
			<Name>
				<xsl:value-of select="Name"/>
			</Name>
			<Date>
				<xsl:value-of select="Date"/>
			</Date>
		</round>
	</xsl:template>
</xsl:stylesheet>
