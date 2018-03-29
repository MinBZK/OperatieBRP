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
				<th>Soort Adm. Hnd.</th>
				<th>Mapping</th>
				<th>Soort bericht</th>
				<th>Conversie</th>
				<th>Filteren</th>
                <th>Bericht</th>
			</tr>
			<xsl:for-each select="key('resultaten-per-thema', @thema)">
				<xsl:sort select="@naam" data-type="text" order="ascending" />

				<xsl:variable name="aantalBerichten" select="count(levering/bericht)" />

				<tr>
					<td>
						<xsl:attribute name="rowspan"><xsl:value-of select="$aantalBerichten" /></xsl:attribute>
						<xsl:value-of select="@thema" />
					</td>
					<td>
						<xsl:attribute name="rowspan"><xsl:value-of select="$aantalBerichten" /></xsl:attribute>
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
						    <td colspan="7">
								<xsl:attribute name="rowspan"><xsl:value-of select="$aantalBerichten" /></xsl:attribute>
						    	<xsl:value-of select="foutmelding/context/text()" /> (<xsl:value-of select="foutmelding/message/text()" />)
						    </td>
						</xsl:when>
						<xsl:otherwise>
							<td>
								<xsl:attribute name="rowspan"><xsl:value-of select="$aantalBerichten" /></xsl:attribute>
								<xsl:apply-templates select="initieren" />
						    </td>
						</xsl:otherwise>
					</xsl:choose>

					<xsl:for-each select="levering">
						<xsl:if test="position()!=1">
							<xsl:value-of disable-output-escaping="yes" select="'&lt;/tr>&lt;tr>'" />
						</xsl:if>

						<xsl:variable name="aantalBerichtenInLevering" select="count(bericht)" />


						<td>
							<xsl:attribute name="rowspan"><xsl:value-of select="$aantalBerichtenInLevering" /></xsl:attribute>
							<xsl:attribute name="class"><xsl:value-of select="@soortAdministratieveHandelingStatus" /></xsl:attribute>

							<xsl:value-of select="@soortAdministratieveHandeling" />
							(<xsl:value-of select="@idAdministratieveHandeling" />)
						</td>
						<td>
							<xsl:attribute name="rowspan"><xsl:value-of select="$aantalBerichtenInLevering" /></xsl:attribute>
							<xsl:apply-templates select="mapping" />
					    </td>

					    <xsl:for-each select="bericht">
							<xsl:if test="position()!=1">
								<xsl:value-of disable-output-escaping="yes" select="'&lt;/tr>&lt;tr>'" />
							</xsl:if>
							<td>
								<xsl:attribute name="class"><xsl:value-of select="@soortBerichtStatus" /></xsl:attribute>
								<xsl:value-of select="@soortBericht" />
							</td>
							<td>
								<xsl:apply-templates select="conversie" />
						    </td>
							<td>
								<xsl:apply-templates select="filteren" />
						    </td>
	                        <td>
	                            <xsl:apply-templates select="bericht" />
	                        </td>
					    </xsl:for-each>
					</xsl:for-each>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>


	<xsl:template match="initieren|mapping|conversie|filteren|bericht">
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

		<xsl:variable name="totaalMapping" select="count(//resultaat/levering/mapping)" />
		<xsl:variable name="totaalMappingOk" select="count(//resultaat/levering/mapping[@status='OK'])" />
		<xsl:variable name="totaalMappingNok" select="count(//resultaat/levering/mapping[@status='NOK'])" />
		<xsl:variable name="totaalMappingExceptie" select="count(//resultaat/levering/mapping[@status='EXCEPTIE'])" />
		<xsl:variable name="totaalMappingGeenVerwachting" select="count(//resultaat/levering/mapping[@status='GEEN_VERWACHTING'])" />


		<xsl:variable name="totaalConversie" select="count(//resultaat/levering/bericht/conversie)" />
		<xsl:variable name="totaalConversieOk" select="count(//resultaat/levering/bericht/conversie[@status='OK'])" />
		<xsl:variable name="totaalConversieNok" select="count(//resultaat/levering/bericht/conversie[@status='NOK'])" />
		<xsl:variable name="totaalConversieExceptie" select="count(//resultaat/levering/bericht/conversie[@status='EXCEPTIE'])" />
		<xsl:variable name="totaalConversieGeenVerwachting" select="count(//resultaat/levering/bericht/conversie[@status='GEEN_VERWACHTING'])" />


		<xsl:variable name="totaalFilteren" select="count(//resultaat/levering/bericht/filteren)" />
		<xsl:variable name="totaalFilterenOk" select="count(//resultaat/levering/bericht/filteren[@status='OK'])" />
		<xsl:variable name="totaalFilterenNok" select="count(//resultaat/levering/bericht/filteren[@status='NOK'])" />
		<xsl:variable name="totaalFilterenExceptie" select="count(//resultaat/levering/bericht/filteren[@status='EXCEPTIE'])" />
		<xsl:variable name="totaalFilterenGeenVerwachting" select="count(//resultaat/levering/bericht/filteren[@status='GEEN_VERWACHTING'])" />


		<xsl:variable name="totaalBericht" select="count(//resultaat/levering/bericht/bericht)" />
		<xsl:variable name="totaalBerichtOk" select="count(//resultaat/levering/bericht/bericht[@status='OK'])" />
		<xsl:variable name="totaalBerichtNok" select="count(//resultaat/levering/bericht/bericht[@status='NOK'])" />
		<xsl:variable name="totaalBerichtExceptie" select="count(//resultaat/levering/bericht/bericht[@status='EXCEPTIE'])" />
		<xsl:variable name="totaalBerichtGeenVerwachting" select="count(//resultaat/levering/bericht/bericht[@status='GEEN_VERWACHTING'])" />


		&#160;
		<table id="thema_{@thema}">
			<tr>
				<th>Totalen</th>
				<th>Initieren</th>
				<th>Percentage</th>
				<th>Mapping</th>
				<th>Percentage</th>
				<th>Conversie</th>
				<th>Percentage</th>
				<th>Filteren</th>
				<th>Percentage</th>
                <th>Bericht</th>
                <th>Percentage</th>
			</tr>
			<tr class="totalen">
				<th>Totaal aantal testcases</th>
				<td><xsl:value-of select="$totaalInitieren" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalMapping" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalConversie" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalFilteren" /></td>
				<td>&#160;</td>
                <td><xsl:value-of select="$totaalBericht" /></td>
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
				<td><xsl:value-of select="$totaalMappingOk" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalMappingOk"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalMapping"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalConversieOk" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalConversieOk"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalConversie"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalFilterenOk" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalFilterenOk"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalFilteren"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
                <td><xsl:value-of select="$totaalBerichtOk" /></td>
                <td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalBerichtOk"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalBericht"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
			</tr>
			<tr>
				<th>Totaal aantal NOK</th>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td><xsl:value-of select="$totaalMappingNok" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalMappingNok"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalMapping"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalConversieNok" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalConversieNok"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalConversie"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalFilterenNok" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalFilterenNok"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalFilteren"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
                <td><xsl:value-of select="$totaalBerichtNok" /></td>
                <td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalBerichtNok"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalBericht"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
			</tr>
			<tr>
				<th>Totaal aantal GEEN VERWACHTING</th>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td><xsl:value-of select="$totaalMappingGeenVerwachting" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalMappingGeenVerwachting"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalMapping"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalConversieGeenVerwachting" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalConversieGeenVerwachting"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalConversie"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalFilterenGeenVerwachting" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalFilterenGeenVerwachting"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalFilteren"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
                <td><xsl:value-of select="$totaalBerichtGeenVerwachting" /></td>
                <td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalBerichtGeenVerwachting"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalBericht"/>
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
				<td><xsl:value-of select="$totaalMappingExceptie" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalMappingExceptie"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalMapping"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalConversieExceptie" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalConversieExceptie"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalConversie"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalFilterenExceptie" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalFilterenExceptie"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalFilteren"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
                <td><xsl:value-of select="$totaalBerichtExceptie" /></td>
                <td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalBerichtExceptie"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalBericht"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
			</tr>
		</table>
	</xsl:template>
</xsl:stylesheet>