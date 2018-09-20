<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:key name="resultaten-per-thema" match="resultaat" use="@thema" />
	
	<xsl:template match="testrapport">
		<html>
			<head>
				<title>
					Testrapport van
					<xsl:value-of select="/testrapport/tijdstip" />
				</title>
	<style>
	<![CDATA[
	body {
		font-family:
		Trebuchet
		MS, Helvetica,
		sans-serif;
		background-color: White;
		font-size: 16px;
	}			
	a {
		color: inherit; 
	}
	table {
		margin: 0 auto;
		border-collapse: collapse;
		border: 1px solid
		#222222;
		white-space: nowrap;
	}
	th {
		background-color: #046F96;
		color:
		white;
	}
	th,td {
		text-align: left;
		border: 1px solid
		#222222;
		padding:
		5px;
		text-align: left;
	}
	tr {
		height: 24px;
	}
	.GEEN_VERWACHTING
	{
		background-color: grey;
		color: white;
	}
	.OK {
		background-color: green;
		color: white;
	}
	.NOK,
	.EXCEPTIE {
		background-color: red;
		color: white;
	}
	.totalen {
		font-weight:bold;
	}
				
]]>
	</style>
			</head>
			<body>
				<center>
				<h1>
					Testresultaten van
					<xsl:value-of select="/testrapport/tijdstip" />
				</h1>
				</center>
				<xsl:apply-templates
					select="resultaat[generate-id(.)=generate-id(key('resultaten-per-thema',@thema))]">
					<xsl:sort select="@thema" data-type="text" order="ascending" />
				</xsl:apply-templates>
					<xsl:call-template name="totalen" />
			</body>
		</html>
	</xsl:template>
			

	<xsl:template match="resultaat">
		&#160;
		<table id="thema_{@thema}">
			<tr>
				<th>
					Thema
				</th>
				<th>
					Testgeval
				</th>
			
				<th>
					Preconditie
				</th>
				<th>
					Conversie
				</th>
			
			</tr>
			<xsl:for-each select="key('resultaten-per-thema', @thema)">
				<xsl:sort select="@naam" data-type="text" order="ascending" />
				<tr>
					<td>
						<xsl:value-of select="@thema" />
					</td>
					<td>
						<xsl:choose>
							<xsl:when test="@bron">
								<a>
									<xsl:attribute name="href">
										<xsl:value-of select="concat(@thema,'/',@bron)" />	
									</xsl:attribute>
									
									<xsl:value-of select="@naam" />
								</a>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="@naam" />
							</xsl:otherwise>
						</xsl:choose>
					</td>

					<xsl:choose>
						<xsl:when test="foutmelding">
						    <td colspan="2">
						    	<xsl:value-of select="foutmelding/context/text()" /> (<xsl:value-of select="foutmelding/message/text()" />)
						    </td>
						</xsl:when>
						<xsl:otherwise>
							<td>
								<xsl:apply-templates select="preconditie" />
						    </td>	
							<td>
								<xsl:apply-templates select="conversie" />
						    </td>		
						</xsl:otherwise>
					</xsl:choose>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
	
	
	<xsl:template match="input|preconditie|conversie">
		<xsl:attribute name="class"><xsl:value-of select="@status" /></xsl:attribute>
		
		<xsl:choose>
			<xsl:when test="bestand">
			<a>
				<xsl:attribute name="href">
					<xsl:value-of select="concat(ancestor::resultaat/@thema,'/',bestand)" />	
				</xsl:attribute>
			
				<xsl:value-of select="@status" />
			</a>
			</xsl:when>
			<xsl:otherwise>	
				<xsl:value-of select="@status" />
			</xsl:otherwise>
		</xsl:choose>
		<xsl:if test="verschillenBestand">
		&#160;(
			<a>
				<xsl:attribute name="href">
					<xsl:value-of select="concat(ancestor::resultaat/@thema,'/',verschillenBestand)" />	
				</xsl:attribute>
				verschillen
			</a>)
		</xsl:if>
	</xsl:template>
	


	<xsl:template name="totalen">

		<xsl:variable name="totaalPreconditie" select="count(//resultaat/preconditie)" />
		<xsl:variable name="totaalPreconditieOk" select="count(//resultaat/preconditie[@status='OK'])" />
		<xsl:variable name="totaalPreconditieNok" select="count(//resultaat/preconditie[@status='NOK'])" />
		<xsl:variable name="totaalPreconditieExceptie" select="count(//resultaat/preconditie[@status='EXCEPTIE'])" />
		
		<xsl:variable name="totaalConversie" select="count(//resultaat/conversie)" />
		<xsl:variable name="totaalConversieOk" select="count(//resultaat/conversie[@status='OK'])" />
		<xsl:variable name="totaalConversieNok" select="count(//resultaat/conversie[@status='NOK'])" />
		<xsl:variable name="totaalConversieExceptie" select="count(//resultaat/conversie[@status='EXCEPTIE'])" />
	
		&#160;
		<table id="thema_{@thema}">
			<tr>
				<th>Totalen</th>
				<th>Preconditie</th>
				<th>Percentage</th>
				<th>Conversie</th>
				<th>Percentage</th>
			</tr>
			<tr class="totalen">
				<th>Totaal aantal testcases</th>
				<td><xsl:value-of select="$totaalPreconditie" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalConversie" /></td>
				<td>&#160;</td>
			</tr>
			
			<tr>
				<th>Totaal aantal OK</th>
				<td><xsl:value-of select="$totaalPreconditieOk" /></td>
				<td><xsl:value-of select="format-number($totaalPreconditieOk div $totaalPreconditie * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalConversieOk" /></td>
				<td><xsl:value-of select="format-number($totaalConversieOk div $totaalConversie * 100, '##')" /> %</td>
			</tr>

			<tr>
				<th>Totaal aantal NOK</th>
				<td><xsl:value-of select="$totaalPreconditieNok" /></td>
				<td><xsl:value-of select="format-number($totaalPreconditieNok div $totaalPreconditie * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalConversieNok" /></td>
				<td><xsl:value-of select="format-number($totaalConversieNok div $totaalConversie * 100, '##')" /> %</td>
			</tr>
			
			<tr>
				<th>Totaal aantal EXCEPTIE</th>
				<td><xsl:value-of select="$totaalPreconditieExceptie" /></td>
				<td><xsl:value-of select="format-number($totaalPreconditieExceptie div $totaalPreconditie * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalConversieExceptie" /></td>
				<td><xsl:value-of select="format-number($totaalConversieExceptie div $totaalConversie * 100, '##')" /> %</td>
			</tr>
		</table>
	</xsl:template>
	
	
</xsl:stylesheet>