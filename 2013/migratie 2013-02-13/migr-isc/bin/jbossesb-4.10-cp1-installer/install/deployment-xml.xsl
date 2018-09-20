<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:param name="jmsProvider" />
	
	<xsl:template match="jmsQueue">
		<xsl:choose>
			<xsl:when test="$jmsProvider = 'hornetq'">
				<depends>org.hornetq:module=JMS,type=Queue,name="<xsl:value-of select='.'/>"</depends>
			</xsl:when>
			<xsl:otherwise>
		        <depends>jboss.esb.destination:service=Queue,name=<xsl:value-of select='.'/></depends>		
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="jmsTopic">
		<xsl:choose>
			<xsl:when test="$jmsProvider = 'hornetq'">
				<depends>org.hornetq:module=JMS,type=Topic,name="<xsl:value-of select='.'/>"</depends>
			</xsl:when>
			<xsl:otherwise>
		        <depends>jboss.esb.destination:service=Topic,name=<xsl:value-of select='.'/></depends>		
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- Copy everything else... -->
	<xsl:template match="@*|node()">	
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			<xsl:apply-templates/>
		</xsl:copy>
	</xsl:template>	
</xsl:stylesheet>