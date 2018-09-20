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
				<th>Initieren</th>
				<th>LO3 naar BRP</th>
				<th>Initieren</th>
				<th>Bijhouding</th>
				<th>Resultaat</th>
			</tr>
			<xsl:for-each select="key('resultaten-per-thema', @thema)">
				<xsl:sort select="@naam" data-type="text" order="ascending" />

				<xsl:variable name="aantalBijhoudingen" select="count(bijhoudingen)" />

				<tr>
					<td>
						<xsl:attribute name="rowspan"><xsl:value-of select="aantalBijhoudingen" /></xsl:attribute>
						<xsl:value-of select="@thema" />
					</td>
					<td>
						<xsl:attribute name="rowspan"><xsl:value-of select="aantalBijhoudingen" /></xsl:attribute>
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
						    <td colspan="4">
								<xsl:attribute name="rowspan"><xsl:value-of select="aantalBijhoudingen" /></xsl:attribute>
						    	<xsl:value-of select="foutmelding/context/text()" /> (<xsl:value-of select="foutmelding/message/text()" />)
						    </td>
						</xsl:when>
						<xsl:otherwise>
							<td>
								<xsl:attribute name="rowspan"><xsl:value-of select="aantalBijhoudingen" /></xsl:attribute>
								<xsl:apply-templates select="initieren" />
						    </td>
							<td>
								<xsl:attribute name="rowspan"><xsl:value-of select="aantalBijhoudingen" /></xsl:attribute>
								<xsl:apply-templates select="lo3NaarBrp" />
						    </td>
							<td>
								<xsl:attribute name="rowspan"><xsl:value-of select="aantalBijhoudingen" /></xsl:attribute>
								<xsl:apply-templates select="bijhouding" />
						    </td>

					    	<xsl:for-each select="bijhoudingen">
								<xsl:if test="position()!=1">
									<xsl:value-of disable-output-escaping="yes" select="'&lt;/tr>&lt;tr>'" />
								</xsl:if>

								<td>
									<xsl:value-of select="@soortAdministratieveHandeling" />
								</td>
		                        <td>
		                            <xsl:apply-templates select="uitvoeren" />
		                        </td>
						    </xsl:for-each>

						</xsl:otherwise>
					</xsl:choose>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>


	<xsl:template match="initieren|bijhouding|lo3NaarBrp">
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

	<xsl:template match="uitvoeren">
		<xsl:attribute name="class"><xsl:value-of select="@status" /></xsl:attribute>

		<xsl:choose>
			<xsl:when test="bestand">
			<a>
				<xsl:attribute name="href">
					<xsl:value-of select="concat(ancestor::resultaat/@thema,'/',bestand)" />
				</xsl:attribute>

				<xsl:value-of select="@omschrijving" />
			</a>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="@omschrijving" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="percentage">
        <xsl:param name="aantal"/>
        <xsl:param name="totaalAantal"/>

        <xsl:choose>
            <xsl:when test="contains(number($aantal div $totaalAantal * 100),'NaN')">
                <xsl:text>0 %</xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat(format-number($aantal div $totaalAantal * 100, '##'), ' %')"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

	<xsl:template name="totalen">
		<xsl:variable name="totaalInitieren" select="count(//resultaat/initieren)" />
		<xsl:variable name="totaalInitierenOk" select="count(//resultaat/initieren[@status='OK'])" />
		<xsl:variable name="totaalInitierenExceptie" select="count(//resultaat/initieren[@status='EXCEPTIE'])" />

		<xsl:variable name="totaalLo3" select="count(//resultaat/lo3NaarBrp)" />
		<xsl:variable name="totaalLo3Ok" select="count(//resultaat/lo3NaarBrp[@status='OK'])" />
		<xsl:variable name="totaalLo3Nok" select="count(//resultaat/lo3NaarBrp[@status='NOK'])" />
		<xsl:variable name="totaalLo3Exceptie" select="count(//resultaat/lo3NaarBrp[@status='EXCEPTIE'])" />
		<xsl:variable name="totaalLo3GeenVerwachting" select="count(//resultaat/lo3NaarBrp[@status='GEEN_VERWACHTING'])" />

		&#160;
		<table id="thema_{@thema}">
			<tr>
				<th>Totalen</th>
				<th>Initieren</th>
				<th>Percentage</th>
				<th>LO3 naar BRP</th>
				<th>Percentage</th>
			</tr>
			<tr class="totalen">
				<th>Totaal aantal testcases</th>
				<td><xsl:value-of select="$totaalInitieren" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalLo3" /></td>
				<td>&#160;</td>
			</tr>
			<tr>
				<th>Totaal aantal OK</th>
				<td><xsl:value-of select="$totaalInitierenOk" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalInitierenOk"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalInitieren"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalLo3Ok" /></td>
				<td><!-- <xsl:value-of select="format-number($totaalLo3Ok div $totaalLo3 * 100, '##')" /> %-->
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalLo3Ok"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalLo3"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
			</tr>
			<tr>
				<th>Totaal aantal NOK</th>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td><xsl:value-of select="$totaalLo3Nok" /></td>
				<td><!-- <xsl:value-of select="format-number($totaalLo3Nok div $totaalLo3 * 100, '##')" /> %-->
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalLo3Nok"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalLo3"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
			</tr>
			<tr>
				<th>Totaal aantal GEEN VERWACHTING</th>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td><xsl:value-of select="$totaalLo3GeenVerwachting" /></td>
				<td><!-- <xsl:value-of select="format-number($totaalLo3GeenVerwachting div $totaalLo3 * 100, '##')" /> %-->
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalLo3GeenVerwachting"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalLo3"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
			</tr>
			<tr>
				<th>Totaal aantal EXCEPTIE</th>
				<td><xsl:value-of select="$totaalInitierenExceptie" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalInitierenExceptie"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalInitieren"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalLo3Exceptie" /></td>
				<td><!-- <xsl:value-of select="format-number($totaalLo3Exceptie div $totaalLo3 * 100, '##')" /> %-->
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalLo3Exceptie"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalLo3"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
			</tr>
		</table>
	</xsl:template>
</xsl:stylesheet>