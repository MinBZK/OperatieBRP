<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:brp="http://www.bzk.nl/brp/brp0200" version="1.0">
	<xsl:output method="text"/>
	<xsl:template match="soap:Envelope">
		<xsl:apply-templates select="soap:Body/brp:lvg_bvgGeefDetailsPersoon_R" />
	</xsl:template>
	<xsl:template match="brp:lvg_bvgGeefDetailsPersoon_R">
		<xsl:apply-templates select="//brp:geregistreerdPartnerschap[1]"/>
	</xsl:template>
	<xsl:template match="brp:geregistreerdPartnerschap">
		objectsleutel=<xsl:value-of select="@brp:objectSleutel"/>
	</xsl:template>
</xsl:stylesheet>