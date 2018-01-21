<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:param name="Round"/>

<xsl:template match="/">
	<xsl:apply-templates select="SevillaRpt/Report/GroupReport/Ranking[Round=$Round]"/>
</xsl:template>

<xsl:template match="Ranking">
	<playerRankins>
	<xsl:for-each select="PlayerRanking">
		<xsl:if test="Games &gt; 0">
			<xsl:apply-templates select=".">
				<xsl:with-param name="pos">
					<xsl:value-of select="position()"/>
				</xsl:with-param>
				<xsl:sort select="POS" data-type="number" order="ascending"/>
			</xsl:apply-templates>
		</xsl:if>
	</xsl:for-each>
	</playerRankins>
</xsl:template>

<xsl:template match="PlayerRanking">
	<xsl:param name="pos"/>
	<playerRanking>
		<pos><xsl:value-of select="$pos"/></pos>
		<id><xsl:value-of select="ID"/></id>
		<name><xsl:value-of select="Name"/></name>
		<games><xsl:value-of select="Games"/></games>
		<wins><xsl:value-of select="Wins"/></wins>
		<draws><xsl:value-of select="Draws"/></draws>
		<losses><xsl:value-of select="Losses"/></losses>
		<percentage><xsl:value-of select="Perc"/></percentage>
		<score><xsl:value-of select="Score"/></score>
		<value><xsl:value-of select="Value"/></value>
		<balance><xsl:value-of select="Balance"/></balance>
		<TPR><xsl:value-of select="TPR"/></TPR>
		<iRtg><xsl:value-of select="IRtg"/></iRtg>
		<category><xsl:value-of select="Category"/></category>
		<gender><xsl:value-of select="Gender"/></gender>
	</playerRanking>
</xsl:template>
</xsl:stylesheet>
