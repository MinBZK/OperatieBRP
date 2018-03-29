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
				<th>Thema</th>
				<th>Testgeval</th>
				<th>Proces Id</th>
			
				<th>Resultaat</th>
				<th>Omschrijving</th>
				<th>Bestanden</th>
			</tr>
			<xsl:for-each select="key('resultaten-per-thema', @thema)">
				<xsl:sort select="@naam" data-type="text" order="ascending" />
				<tr>
					<td>
						<xsl:value-of select="@thema" />
					</td>
					<td>
						<xsl:choose>
							<xsl:when test="bron">
								<a>
									<xsl:attribute name="href">
										<xsl:value-of select="concat(@thema,'/',bron)" />	
									</xsl:attribute>
									
									<xsl:value-of select="@naam" />
								</a>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="@naam" />
							</xsl:otherwise>
						</xsl:choose>
					</td>
					<td>
						<xsl:value-of select="procesInstanceId" />
					</td>

					<xsl:choose>
						<xsl:when test="foutmelding">
						    <td colspan="3">
						    	<xsl:value-of select="foutmelding/context/text()" /> (<xsl:value-of select="foutmelding/message/text()" />)
						    </td>
						</xsl:when>
						<xsl:otherwise>
							<td>
								<xsl:attribute name="class"><xsl:value-of select="resultaat/@status" /></xsl:attribute>
								<xsl:choose>
									<xsl:when test="resultaat/bestand">
										<a>
											<xsl:attribute name="href">
												<xsl:value-of select="concat(@thema,'/',resultaat/bestand)" />	
											</xsl:attribute>
										
											<xsl:value-of select="resultaat/@status" />
										</a>
									</xsl:when>
									<xsl:otherwise>	
										<xsl:value-of select="resultaat/@status" />
									</xsl:otherwise>
								</xsl:choose>
							</td>
							<td>
								<xsl:value-of select="resultaat/@omschrijving" />
							</td>
							<td>
							<xsl:for-each select="bestanden/bestand">
								<a>
									<xsl:attribute name="href">
										<xsl:value-of select="concat(ancestor::resultaat/@thema,'/',ancestor::resultaat/@naam,'/',bestand)" />	
									</xsl:attribute>
									<xsl:value-of select="volgnummer" />
								</a><xsl:value-of select="'&amp;nbsp;'" disable-output-escaping="yes" />
								
							</xsl:for-each>
							</td>
						</xsl:otherwise>
					</xsl:choose>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template name="totalen">
	
		<xsl:variable name="totaal" select="count(//resultaat/resultaat)" />
		<xsl:variable name="totaalOk" select="count(//resultaat/resultaat[@status='OK'])" />
		<xsl:variable name="totaalNok" select="count(//resultaat/resultaat[@status='NOK'])" />
		<xsl:variable name="totaalExceptie" select="count(//resultaat/resultaat[@status='EXCEPTIE'])" />


		&#160;
		<table id="thema_{@thema}">
			<tr>
				<th>Totalen</th>
				<th>Resultaat</th>
				<th>Percentage</th>
			</tr>
			<tr class="totalen">
				<th>Totaal aantal testcases</th>
				<td><xsl:value-of select="$totaal" /></td>
				<td>&#160;</td>
			</tr>
			<tr>
				<th>Totaal aantal OK</th>
				<td><xsl:value-of select="$totaalOk" /></td>
				<td><xsl:value-of select="format-number($totaalOk div $totaal * 100, '##')" /> %</td>
			</tr>
			<tr>
				<th>Totaal aantal NOK</th>
				<td><xsl:value-of select="$totaalNok" /></td>
				<td><xsl:value-of select="format-number($totaalNok div $totaal * 100, '##')" /> %</td>
			</tr>
			<tr>
				<th>Totaal aantal EXCEPTIE</th>
				<td><xsl:value-of select="$totaalExceptie" /></td>
				<td><xsl:value-of select="format-number($totaalExceptie div $totaal * 100, '##')" /> %</td>
			</tr>			
		</table>
	</xsl:template>
	
	
</xsl:stylesheet>