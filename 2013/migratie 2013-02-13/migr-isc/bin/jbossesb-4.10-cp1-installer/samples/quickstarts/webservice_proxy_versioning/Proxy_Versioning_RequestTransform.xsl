<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:inv="http://webservice_proxy_versioning/invoicing">

	<xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>

	<xsl:template match="inv:processInvoice">
		<xsl:copy>
			<invoiceNumber><xsl:value-of select="invoiceNumber"/></invoiceNumber>
			<!-- here we add the processDate element to conform to the newer version -->
			<!-- (xsl 2 would give us the current-dateTime() function) -->
			<processDate>2005-12-13T14:13:28.443+01:00</processDate>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="*">
		<xsl:copy>
			<xsl:copy-of select="@*"/>
			<xsl:apply-templates/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="comment()|processing-instruction()">
		<xsl:copy/>
	</xsl:template>

</xsl:stylesheet>
