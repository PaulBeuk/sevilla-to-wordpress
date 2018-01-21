<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:param name="Round"/>
<xsl:output method="xml" indent="yes"/>

<xsl:template match="/">
	<xsl:apply-templates select="SevillaRpt/Report"/>
</xsl:template>

<xsl:template match="/SevillaRpt/Report">
	<COMPETITION>
		<NAME><xsl:value-of select="NAME"/></NAME>
		<xsl:for-each select="GroupReport/Ranking[Round=$Round]">
			<xsl:call-template name="playerlist"/>
		</xsl:for-each>
	</COMPETITION>
</xsl:template>

<xsl:template name="playerlist">
	<xsl:for-each select="PlayerRanking">
		<xsl:variable name="playerid"><xsl:value-of select="ID"/></xsl:variable>
		<xsl:variable name="playername"><xsl:value-of select="NAME"/></xsl:variable>
		<xsl:for-each select="../..">
			<xsl:call-template name="playerdata">
				<xsl:with-param name="id" select="$playerid"/>
				<xsl:with-param name="name" select="$playername"/>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:for-each>
</xsl:template>

<xsl:template name="playerdata">
	<xsl:param name="name" />
	<xsl:param name="id" />
	<Player>
		<NAME><xsl:value-of select="$name"/></NAME>
		<ID><xsl:value-of select="$id"/></ID>
		<xsl:for-each select="Ranking/PlayerRanking[ID=$id]">
			<Round>
				<RoundID><xsl:value-of select="../Round"/></RoundID>
				<Pos><xsl:value-of select="Pos"/></Pos>
				<TPR><xsl:value-of select="TPR"/></TPR>
				<Rating><xsl:value-of select="Rating"/></Rating>
				<Perc><xsl:value-of select="Perc"/></Perc>
			</Round>
		</xsl:for-each>
	</Player>
</xsl:template>

</xsl:stylesheet>
