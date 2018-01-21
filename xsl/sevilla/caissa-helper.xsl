<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:template name="aaa_mypage">
		<xsl:param name="username" />
 		<xsl:variable name="profileName" select="translate($username,'- ', '')"/>
		<td>
			<a>
				<xsl:attribute name="href">
					<xsl:value-of select="$param_profile_prefix"/><xsl:value-of select="$profileName"/>
				</xsl:attribute>
				<xsl:attribute name="target">_parent</xsl:attribute>
				<xsl:value-of select="$username"/>
			</a>
		</td>
	</xsl:template>

	<xsl:template name="aaa">
		<xsl:param name="username" />
		<xsl:variable name="linkName"><xsl:value-of select="translate($username,' ','-' )"/></xsl:variable>
	
		<td>
			<a>
				<xsl:attribute name="href">
					<xsl:value-of select="$param_results_prefix"/><xsl:value-of select="$linkName"/>
				</xsl:attribute>
				<xsl:attribute name="target">_parent</xsl:attribute>
				<xsl:value-of select="$username"/>
			</a>
		</td>
	</xsl:template>

	<xsl:template name="a_image_mypage">
		<xsl:param name="username" />
 		<xsl:variable name="profileName" select="translate($username,'- ', '')"/>
 		<xsl:variable name="avatar" select="translate($profileName,'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"/>
		<td>
			<a>
				<xsl:attribute name="href">
					<xsl:value-of select="$param_profile_prefix"/><xsl:value-of select="$profileName"/>
				</xsl:attribute>
				<xsl:attribute name="target">_parent</xsl:attribute>
				<img>
					<xsl:attribute name="src">
						<xsl:value-of select='$param_avatar_prefix'/><xsl:value-of select="$avatar"/>
					</xsl:attribute>
					<xsl:attribute name="title">
						Wie is <xsl:value-of select="$username"/>?
					</xsl:attribute>
					<xsl:attribute name="class">
						<xsl:value-of select="$param_avatar_classes"/>
					</xsl:attribute>
					<xsl:attribute name="onError">
						<xsl:value-of select="$onerror"/>
					</xsl:attribute>
				</img>
			</a>
		</td>
	</xsl:template>

	<xsl:template name="a_image">
		<xsl:param name="username" />
		<xsl:variable name="linkName"><xsl:value-of select="translate($username,' ','-' )"/></xsl:variable>
 		<xsl:variable name="avatar" select="translate($linkName,'-', '')"/>
 		<xsl:variable name="avatar" select="translate($avatar,'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"/>
		
		<td>
			<a>
				<xsl:attribute name="href">
					<xsl:value-of select="$param_results_prefix"/><xsl:value-of select="$linkName"/>
				</xsl:attribute>
				<xsl:attribute name="target">_parent</xsl:attribute>
				<img>
					<xsl:attribute name="src">
						<xsl:value-of select='$param_avatar_prefix'/><xsl:value-of select="$avatar"/>
					</xsl:attribute>
					<xsl:attribute name="title">Naar resultaten van <xsl:value-of select="$username"/></xsl:attribute>
					<xsl:attribute name="class">
						<xsl:value-of select="$param_avatar_classes"/>
					</xsl:attribute>
					<xsl:attribute name="onError">
						<xsl:value-of select="$onerror"/>
					</xsl:attribute>
				</img>
			</a>
		</td>
	</xsl:template>

	<xsl:template name="only_image">
		<xsl:param name="lname" />
		<xsl:param name="nname" />
		<td>
			<img>
				<xsl:attribute name="src">
					<xsl:value-of select='$param_avatar_prefix'/><xsl:value-of select="$lname"/>
				</xsl:attribute>
				<xsl:attribute name="title">Naar resultaten van <xsl:value-of select="$nname"/></xsl:attribute>
				<xsl:attribute name="class">
					<xsl:value-of select="$param_avatar_classes"/>
				</xsl:attribute>
				<xsl:attribute name="onError">
					<xsl:value-of select="$onerror"/>
				</xsl:attribute>
			</img>
		</td>
	</xsl:template>

</xsl:stylesheet>
