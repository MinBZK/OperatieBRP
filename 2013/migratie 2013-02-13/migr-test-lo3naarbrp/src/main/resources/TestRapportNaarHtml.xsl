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
			
				<th>LO3 naar BRP</th>
                <th>Converteer log</th>
				<th>Rondverteer</th>
				<th>Opslaan BRP</th>
				<th>Lezen BRP</th>
				<th>BRP naar LO3</th>
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

					<xsl:choose>
						<xsl:when test="foutmelding">
						    <td colspan="5">
						    	<xsl:value-of select="foutmelding/context/text()" /> (<xsl:value-of select="foutmelding/message/text()" />)
						    </td>
						</xsl:when>
						<xsl:otherwise>
							<td>
								<xsl:apply-templates select="lo3NaarBrp" />
						    </td>	
                            <td>
                                <xsl:apply-templates select="conversieLog" />
                            </td>   
							<td>
								<xsl:apply-templates select="rondverteer" />
						    </td>	
							<td>
								<xsl:apply-templates select="opslaanBrp" />
						    </td>	
							<td>
								<xsl:apply-templates select="lezenBrp" />
						    </td>	
							<td>
								<xsl:apply-templates select="brpNaarLo3" />
						    </td>			
						</xsl:otherwise>
					</xsl:choose>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
	
	
	<xsl:template match="lo3NaarBrp|conversieLog|rondverteer|opslaanBrp|lezenBrp|brpNaarLo3">
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
	
		<xsl:variable name="totaalLo3" select="count(//resultaat/lo3NaarBrp)" />
		<xsl:variable name="totaalLo3Ok" select="count(//resultaat/lo3NaarBrp[@status='OK'])" />
		<xsl:variable name="totaalLo3Nok" select="count(//resultaat/lo3NaarBrp[@status='NOK'])" />
		<xsl:variable name="totaalLo3Exceptie" select="count(//resultaat/lo3NaarBrp[@status='EXCEPTIE'])" />
		<xsl:variable name="totaalLo3GeenVerwachting" select="count(//resultaat/lo3NaarBrp[@status='GEEN_VERWACHTING'])" />
	
		<xsl:variable name="totaalRond" select="count(//resultaat/rondverteer)" />
		<xsl:variable name="totaalRondOk" select="count(//resultaat/rondverteer[@status='OK'])" />
		<xsl:variable name="totaalRondNok" select="count(//resultaat/rondverteer[@status='NOK'])" />
		<xsl:variable name="totaalRondExceptie" select="count(//resultaat/rondverteer[@status='EXCEPTIE'])" />
	
		<xsl:variable name="totaalOpslaan" select="count(//resultaat/opslaanBrp)" />
		<xsl:variable name="totaalOpslaanOk" select="count(//resultaat/opslaanBrp[@status='OK'])" />
		<xsl:variable name="totaalOpslaanExceptie" select="count(//resultaat/opslaanBrp[@status='EXCEPTIE'])" />
	
		<xsl:variable name="totaalLezen" select="count(//resultaat/lezenBrp)" />
		<xsl:variable name="totaalLezenOk" select="count(//resultaat/lezenBrp[@status='OK'])" />
		<xsl:variable name="totaalLezenNok" select="count(//resultaat/lezenBrp[@status='NOK'])" />
		<xsl:variable name="totaalLezenExceptie" select="count(//resultaat/lezenBrp[@status='EXCEPTIE'])" />
	
		<xsl:variable name="totaalTerug" select="count(//resultaat/brpNaarLo3)" />
		<xsl:variable name="totaalTerugOk" select="count(//resultaat/brpNaarLo3[@status='OK'])" />
		<xsl:variable name="totaalTerugNok" select="count(//resultaat/brpNaarLo3[@status='NOK'])" />
		<xsl:variable name="totaalTerugExceptie" select="count(//resultaat/brpNaarLo3[@status='EXCEPTIE'])" />

		&#160;
		<table id="thema_{@thema}">
			<tr>
				<th>Totalen</th>
				<th>LO3 naar BRP</th>
				<th>Percentage</th>
				<th>Rondverteer</th>
				<th>Percentage</th>
				<th>Opslaan BRP</th>
				<th>Percentage</th>
				<th>Lezen BRP</th>
				<th>Percentage</th>
				<th>BRP naar LO3</th>
				<th>Percentage</th>
			</tr>
			<tr class="totalen">
				<th>Totaal aantal testcases</th>
				<td><xsl:value-of select="$totaalLo3" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalRond" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalOpslaan" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalLezen" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalTerug" /></td>
				<td>&#160;</td>
			</tr>
			<tr>
				<th>Totaal aantal OK</th>
				<td><xsl:value-of select="$totaalLo3Ok" /></td>
				<td><xsl:value-of select="format-number($totaalLo3Ok div $totaalLo3 * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalRondOk" /></td>
				<td><xsl:value-of select="format-number($totaalRondOk div $totaalRond * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalOpslaanOk" /></td>
				<td><xsl:value-of select="format-number($totaalOpslaanOk div $totaalOpslaan * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalLezenOk" /></td>
				<td><xsl:value-of select="format-number($totaalLezenOk div $totaalLezen * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalTerugOk" /></td>
				<td><xsl:value-of select="format-number($totaalTerugOk div $totaalTerug * 100, '##')" /> %</td>
			</tr>
			<tr>
				<th>Totaal aantal NOK</th>
				<td><xsl:value-of select="$totaalLo3Nok" /></td>
				<td><xsl:value-of select="format-number($totaalLo3Nok div $totaalLo3 * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalRondNok" /></td>
				<td><xsl:value-of select="format-number($totaalRondNok div $totaalRond * 100, '##')" /> %</td>
				<td>&#160;</td>				
				<td>&#160;</td>				
				<td><xsl:value-of select="$totaalLezenNok" /></td>
				<td><xsl:value-of select="format-number($totaalLezenNok div $totaalLezen * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalTerugNok" /></td>
				<td><xsl:value-of select="format-number($totaalTerugNok div $totaalTerug * 100, '##')" /> %</td>
			</tr>
			<tr>
				<th>Totaal aantal GEEN VERWACHTING</th>
				<td><xsl:value-of select="$totaalLo3GeenVerwachting" /></td>
				<td><xsl:value-of select="format-number($totaalLo3GeenVerwachting div $totaalLo3 * 100, '##')" /> %</td>
				<td>&#160;</td>				
				<td>&#160;</td>				
				<td>&#160;</td>				
				<td>&#160;</td>				
				<td>&#160;</td>				
				<td>&#160;</td>				
				<td>&#160;</td>				
				<td>&#160;</td>				
			</tr>
			<tr>
				<th>Totaal aantal EXCEPTIE</th>
				<td><xsl:value-of select="$totaalLo3Exceptie" /></td>
				<td><xsl:value-of select="format-number($totaalLo3Exceptie div $totaalLo3 * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalRondExceptie" /></td>
				<td><xsl:value-of select="format-number($totaalRondExceptie div $totaalRond * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalOpslaanExceptie" /></td>
				<td><xsl:value-of select="format-number($totaalOpslaanExceptie div $totaalOpslaan * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalLezenExceptie" /></td>
				<td><xsl:value-of select="format-number($totaalLezenExceptie div $totaalLezen * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalTerugExceptie" /></td>
				<td><xsl:value-of select="format-number($totaalTerugExceptie div $totaalTerug * 100, '##')" /> %</td>
			</tr>			
		</table>
	</xsl:template>
	
	
</xsl:stylesheet>