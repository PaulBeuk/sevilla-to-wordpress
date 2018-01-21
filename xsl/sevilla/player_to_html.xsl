<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="no" encoding="UTF-8"/>
<xsl:decimal-format name="dkk" decimal-separator="," grouping-separator="."/>
<xsl:param name="param_results_prefix" select="'/interne-competitie-2014-2015-'"/>
<xsl:param name="param_profile_prefix" select="'/profile/'"/>
<xsl:param name="param_avatar_prefix" select="'/avatars/'"/>
<xsl:param name="param_avatar_notfound" select="avatar_notfound.png"/>
<xsl:param name="param_avatar_classes" select="'avatar wp-user-avatar-30 avatar-scores alignnone photo'"/>
<xsl:variable name="onerror">
	<xsl:text disable-output-escaping="yes">this.onError=null;this.src=&quot;</xsl:text>
	<xsl:value-of select="$param_avatar_notfound"/>
	<xsl:text disable-output-escaping="yes">&quot;;</xsl:text>
</xsl:variable>
<xsl:include href="caissa-helper.xsl"/>

<xsl:template match="/">
	<xsl:apply-templates select="players"/>
</xsl:template>

<xsl:template match="players">
	<xsl:for-each select="player">

		<player>
			<xsl:attribute name="Name"><xsl:value-of select="Name"/></xsl:attribute>
			<table class="table table-striped table-hover player_overview">
				<thead>
					<tr>
						<th colspan="2">Speler</th>
						<th>m/v</th>
						<th>Pos</th>
						<th>#</th>
						<th>1</th>
						<th>=</th>
						<th>0</th>
						<th>Score</th>
						<th>Perc</th>
						<th>TPR</th>
						<th>Elo</th>
					</tr>
				</thead>
				<tbody>
				<tr>
					<xsl:call-template name="a_image_mypage">
						<xsl:with-param name="username" select="Name"/>
					</xsl:call-template>
					<xsl:call-template name="aaa_mypage">
						<xsl:with-param name="username" select="Name"/>
					</xsl:call-template>

					<td><xsl:value-of select="Gender"/></td>
					<td><xsl:value-of select="Pos"/></td>
					<td><xsl:value-of select="Games"/></td>
					<td><xsl:value-of select="Wins"/></td>
					<td><xsl:value-of select="Draws"/></td>
					<td><xsl:value-of select="Losses"/></td>
					<td><xsl:value-of select="Score"/></td>
					<td><xsl:value-of select="format-number(Perc, '###,#', 'dkk')"/></td>
					<td><xsl:value-of select="TPR"/></td>
					<td><xsl:value-of select="Rating"/></td>
				</tr>
				</tbody>
			</table>
			<BR/>
			<table class="table table-striped table-hover player_results">
				<thead>
					<tr>
						<th>R</th>
						<th colspan="2">Wit</th>
						<th></th>
						<th colspan="2">Zwart</th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="game">
						<xsl:apply-templates select=".">
							<xsl:with-param name="round">
								<xsl:value-of select="round"/>
							</xsl:with-param>
							<xsl:sort select="round" data-type="number" order="ascending"/>
						</xsl:apply-templates>
					</xsl:for-each>
				</tbody>
			</table>
		</player>
	</xsl:for-each>
</xsl:template>

<xsl:template match="game">
	<tr>
	<xsl:choose>
		<xsl:when test="white = ''">
			<td><xsl:value-of select="round"/></td>
			<td colspan="5"><xsl:value-of select="result"/></td>
		</xsl:when>
		<xsl:otherwise>
			<td><xsl:value-of select="round"/></td>
			<xsl:choose>
				<xsl:when test="white = ../Name">
					<xsl:call-template name="a_image_mypage">
						<xsl:with-param name="username" select="white"/>
					</xsl:call-template>
					<xsl:call-template name="aaa_mypage">
						<xsl:with-param name="username" select="white"/>
					</xsl:call-template>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="a_image">
						<xsl:with-param name="username" select="white"/>
					</xsl:call-template>
					<xsl:call-template name="aaa">
						<xsl:with-param name="username" select="white"/>
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
			<td><xsl:value-of select="result"/></td>
			<xsl:choose>
				<xsl:when test="black = ../Name">
					<xsl:call-template name="a_image_mypage">
						<xsl:with-param name="username" select="black"/>
					</xsl:call-template>
					<xsl:call-template name="aaa_mypage">
						<xsl:with-param name="username" select="black"/>
					</xsl:call-template>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="a_image">
						<xsl:with-param name="username" select="black"/>
					</xsl:call-template>
					<xsl:call-template name="aaa">
						<xsl:with-param name="username" select="black"/>
					</xsl:call-template>
				</xsl:otherwise>
		</xsl:choose>
		</xsl:otherwise>
	</xsl:choose>
	</tr>
</xsl:template>

</xsl:stylesheet>
