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
                <th>Precondities</th>
				<th>LO3 naar BRP</th>
				<th>Opslaan BRP</th>
                <th>Logging</th>
				<th>Lezen BRP</th>
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
						    <td colspan="6">
						    	<xsl:value-of select="foutmelding/context/text()" /> (<xsl:value-of select="foutmelding/message/text()" />)
						    </td>
						</xsl:when>
						<xsl:otherwise>
							<td>
								<xsl:apply-templates select="initieren" />
						    </td>
                            <td>
                                <xsl:apply-templates select="precondities" />
                            </td>
							<td>
								<xsl:apply-templates select="lo3NaarBrp" />
						    </td>
							<td>
								<xsl:apply-templates select="opslaanBrp" />
						    </td>
							<td>
								<xsl:apply-templates select="conversieLog" />
						    </td>
							<td>
								<xsl:apply-templates select="lezenBrp" />
						    </td>
						</xsl:otherwise>
					</xsl:choose>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>


	<xsl:template match="initieren|precondities|lo3NaarBrp|conversieLog|opslaanBrp|lezenBrp">
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
		<xsl:variable name="totaalInlezen" select="count(//resultaat/inlezen)" />
		<xsl:variable name="totaalInlezenOk" select="count(//resultaat/inlezen[@status='OK'])" />
		<xsl:variable name="totaalInlezenExceptie" select="count(//resultaat/inlezen[@status='EXCEPTIE'])" />

        <xsl:variable name="totaalPrecondities" select="count(//resultaat/precondities)" />
        <xsl:variable name="totaalPreconditiesOk" select="count(//resultaat/precondities[@status='OK'])" />
        <xsl:variable name="totaalPreconditiesNok" select="count(//resultaat/precondities[@status='NOK'])" />
        <xsl:variable name="totaalPreconditiesExceptie" select="count(//resultaat/precondities[@status='EXCEPTIE'])" />

		<xsl:variable name="totaalLo3" select="count(//resultaat/lo3NaarBrp)" />
		<xsl:variable name="totaalLo3Ok" select="count(//resultaat/lo3NaarBrp[@status='OK'])" />
		<xsl:variable name="totaalLo3Nok" select="count(//resultaat/lo3NaarBrp[@status='NOK'])" />
		<xsl:variable name="totaalLo3Exceptie" select="count(//resultaat/lo3NaarBrp[@status='EXCEPTIE'])" />
		<xsl:variable name="totaalLo3GeenVerwachting" select="count(//resultaat/lo3NaarBrp[@status='GEEN_VERWACHTING'])" />

		<xsl:variable name="totaalLog" select="count(//resultaat/conversieLog)" />
		<xsl:variable name="totaalLogOk" select="count(//resultaat/conversieLog[@status='OK'])" />
		<xsl:variable name="totaalLogNok" select="count(//resultaat/conversieLog[@status='NOK'])" />
		<xsl:variable name="totaalLogGeenVerwachting" select="count(//resultaat/conversieLog[@status='GEEN_VERWACHTING'])" />

		<xsl:variable name="totaalOpslaan" select="count(//resultaat/opslaanBrp)" />
		<xsl:variable name="totaalOpslaanOk" select="count(//resultaat/opslaanBrp[@status='OK'])" />
		<xsl:variable name="totaalOpslaanExceptie" select="count(//resultaat/opslaanBrp[@status='EXCEPTIE'])" />

		<xsl:variable name="totaalLezen" select="count(//resultaat/lezenBrp)" />
		<xsl:variable name="totaalLezenOk" select="count(//resultaat/lezenBrp[@status='OK'])" />
		<xsl:variable name="totaalLezenNok" select="count(//resultaat/lezenBrp[@status='NOK'])" />
		<xsl:variable name="totaalLezenExceptie" select="count(//resultaat/lezenBrp[@status='EXCEPTIE'])" />

		&#160;
		<table id="thema_{@thema}">
			<tr>
				<th>Totalen</th>
                <th>Precondities</th>
                <th>Percentage</th>
				<th>LO3 naar BRP</th>
				<th>Percentage</th>
				<th>Opslaan BRP</th>
				<th>Percentage</th>
				<th>Logging conversie</th>
				<th>Percentage</th>
				<th>Lezen BRP</th>
				<th>Percentage</th>
			</tr>
			<tr class="totalen">
				<th>Totaal aantal testcases</th>
                <td><xsl:value-of select="$totaalPrecondities" /></td>
                <td>&#160;</td>
				<td><xsl:value-of select="$totaalLo3" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalOpslaan" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalLog" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalLezen" /></td>
				<td>&#160;</td>
			</tr>
			<tr>
				<th>Totaal aantal OK</th>
                <td><xsl:value-of select="$totaalPreconditiesOk" /></td>
                <td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalPreconditiesOk"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalPrecondities"/>
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
				<td><xsl:value-of select="$totaalOpslaanOk" /></td>
				<td><!-- <xsl:value-of select="format-number($totaalOpslaanOk div $totaalOpslaan * 100, '##')" /> %-->
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalOpslaanOk"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalOpslaan"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalLogOk" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalLogOk"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalLog"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalLezenOk" /></td>
				<td><!-- <xsl:value-of select="format-number($totaalLezenOk div $totaalLezen * 100, '##')" /> %-->
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalLezenOk"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalLezen"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
			</tr>
			<tr>
				<th>Totaal aantal NOK</th>
				<td><xsl:value-of select="$totaalPreconditiesNok" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalPreconditiesNok"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalPrecondities"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
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
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td><xsl:value-of select="$totaalLogNok" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalLogNok"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalLog"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td><xsl:value-of select="$totaalLezenNok" /></td>
				<td><!-- <xsl:value-of select="format-number($totaalLezenNok div $totaalLezen * 100, '##')" /> %-->
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalLezenNok"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalLezen"/>
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
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td><xsl:value-of select="$totaalLogGeenVerwachting" /></td>
				<td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalLogGeenVerwachting"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalLog"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td class="GEEN_VERWACHTING">&#160;</td>
			</tr>
			<tr>
				<th>Totaal aantal EXCEPTIE</th>
                <td><xsl:value-of select="$totaalPreconditiesExceptie" /></td>
                <td>
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalPreconditiesExceptie"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalPrecondities"/>
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
				<td><xsl:value-of select="$totaalOpslaanExceptie" /></td>
				<td><!-- <xsl:value-of select="format-number($totaalOpslaanExceptie div $totaalOpslaan * 100, '##')" /> %-->
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalOpslaanExceptie"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalOpslaan"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td class="GEEN_VERWACHTING">&#160;</td>
				<td><xsl:value-of select="$totaalLezenExceptie" /></td>
				<td><!-- <xsl:value-of select="format-number($totaalLezenExceptie div $totaalLezen * 100, '##')" /> %-->
                    <xsl:call-template name="percentage">
                        <xsl:with-param name="aantal">
                            <xsl:value-of select="$totaalLezenExceptie"/>
                        </xsl:with-param>
                        <xsl:with-param name="totaalAantal">
                            <xsl:value-of select="$totaalLezen"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
			</tr>
		</table>
	</xsl:template>


</xsl:stylesheet>