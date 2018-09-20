<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:key name="resultaten-per-thema" match="resultaat" use="@thema" />
	<xsl:key name="sql-per-bestand" match="sqlControle" use="@filename" />
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
				<th rowspan="2">Thema</th>
				<th rowspan="2">Testgeval</th>
                <th rowspan="2">Syntax/Precondities</th>
                <th rowspan="2">LO3 naar BRP</th>
                <th rowspan="2">Converteer log</th>
                <th rowspan="2">Kruimelpad</th>
                <th rowspan="2">Opslaan BRP</th>
                <th rowspan="2">Lezen BRP</th>
                <th colspan="2">BRP naar LO3</th>
				<th rowspan="2">SQL Controles</th>
			</tr>
			<tr>
				<th>Basis</th>
				<th>Analyse</th>
			</tr>
			<xsl:for-each select="key('resultaten-per-thema', @thema)">
                <xsl:variable name="positionCounter" select="position()"/>
                <xsl:variable name="aantalPlen" select="@aantalResultaten" />
				<xsl:sort select="@naam" data-type="text" order="ascending" />
                <xsl:for-each select="conversieResultaten/conversieResultaat">
				<tr>
				    <xsl:choose>
                        <xsl:when test="position() = '1'">
		                    <td rowspan="{$aantalPlen}">
		                        <xsl:value-of select="ancestor::resultaat/@thema" />
		                    </td>
				        </xsl:when>
				    </xsl:choose>
                    <xsl:choose>
                       <xsl:when test="position() = '1'">
							<td rowspan="{$aantalPlen}">
								<xsl:choose>
									<xsl:when test="ancestor::resultaat/@bron">
										<a>
											<xsl:attribute name="href">
												<xsl:value-of select="concat(ancestor::resultaat/@thema,'/',ancestor::resultaat/@bron)" />
											</xsl:attribute>

											<xsl:value-of select="ancestor::resultaat/@naam" />
										</a>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="ancestor::resultaat/@naam" />
									</xsl:otherwise>
								</xsl:choose>
							</td>
                        </xsl:when>
                    </xsl:choose>
					<xsl:choose>
						<xsl:when test="ancestor::resultaat/foutmelding">
						    <td colspan="5">
						    	<xsl:value-of select="ancestor::resultaat/foutmelding/context/text()" /> (<xsl:value-of select="ancestor::resultaat/foutmelding/message/text()" />)
						    </td>
						</xsl:when>
						<xsl:otherwise>
				           <td>
				               <xsl:apply-templates select="syntaxPrecondities" />
				           </td>
				           <td>
				               <xsl:apply-templates select="lo3NaarBrp" />
				           </td>
				           <td>
				               <xsl:apply-templates select="conversieLog" />
				           </td>
                           <td>
                               <xsl:apply-templates select="kruimelpad" />
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
				           <td>
				               <xsl:apply-templates select="brpNaarLo3-va" />
				           </td>
		                    <xsl:choose>
		                        <xsl:when test="position() = '1'">
		        				    <td rowspan="{$aantalPlen}">
		            				      <xsl:apply-templates select="ancestor::resultaat/sqlControles" />
								    </td>
		                        </xsl:when>
		                    </xsl:choose>
						</xsl:otherwise>
					</xsl:choose>
				</tr>
            </xsl:for-each>
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template match="sqlControles">
    	<table>
    	   <xsl:for-each select="sqlControle">
	           <tr><td><xsl:value-of select="@filename"/></td><td><xsl:apply-templates select="testStap" /></td></tr>
           </xsl:for-each>
	    </table>
	</xsl:template>

	<xsl:template match="syntaxPrecondities|lo3NaarBrp|conversieLog|kruimelpad|opslaanBrp|lezenBrp|brpNaarLo3|brpNaarLo3-va|testStap">
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

        <xsl:variable name="totaalSyntaxPreconditie" select="count(//resultaat/conversieResultaten/conversieResultaat/syntaxPrecondities)" />
        <xsl:variable name="totaalSyntaxPreconditieOk" select="count(//resultaat/conversieResultaten/conversieResultaat/syntaxPrecondities[@status='OK'])" />
        <xsl:variable name="totaalSyntaxPreconditieNok" select="count(//resultaat/conversieResultaten/conversieResultaat/syntaxPrecondities[@status='NOK'])" />
        <xsl:variable name="totaalSyntaxPreconditieExceptie" select="count(//resultaat/conversieResultaten/conversieResultaat/syntaxPrecondities[@status='EXCEPTIE'])" />
        <xsl:variable name="totaalSyntaxPreconditieGeenVerwachting" select="count(//resultaat/conversieResultaten/conversieResultaat/syntaxPrecondities[@status='GEEN_VERWACHTING'])" />

		<xsl:variable name="totaalLo3" select="count(//resultaat/conversieResultaten/conversieResultaat/lo3NaarBrp)" />
		<xsl:variable name="totaalLo3Ok" select="count(//resultaat/conversieResultaten/conversieResultaat/lo3NaarBrp[@status='OK'])" />
		<xsl:variable name="totaalLo3Nok" select="count(//resultaat/conversieResultaten/conversieResultaat/lo3NaarBrp[@status='NOK'])" />
		<xsl:variable name="totaalLo3Exceptie" select="count(//resultaat/conversieResultaten/conversieResultaat/lo3NaarBrp[@status='EXCEPTIE'])" />
		<xsl:variable name="totaalLo3GeenVerwachting" select="count(//resultaat/conversieResultaten/conversieResultaat/lo3NaarBrp[@status='GEEN_VERWACHTING'])" />

		<xsl:variable name="totaalOpslaan" select="count(//resultaat/conversieResultaten/conversieResultaat/opslaanBrp)" />
		<xsl:variable name="totaalOpslaanOk" select="count(//resultaat/conversieResultaten/conversieResultaat/opslaanBrp[@status='OK'])" />
		<xsl:variable name="totaalOpslaanExceptie" select="count(//resultaat/conversieResultaten/conversieResultaat/opslaanBrp[@status='EXCEPTIE'])" />

		<xsl:variable name="totaalLezen" select="count(//resultaat/conversieResultaten/conversieResultaat/lezenBrp)" />
		<xsl:variable name="totaalLezenOk" select="count(//resultaat/conversieResultaten/conversieResultaat/lezenBrp[@status='OK'])" />
		<xsl:variable name="totaalLezenNok" select="count(//resultaat/conversieResultaten/conversieResultaat/lezenBrp[@status='NOK'])" />
		<xsl:variable name="totaalLezenExceptie" select="count(//resultaat/conversieResultaten/conversieResultaat/lezenBrp[@status='EXCEPTIE'])" />

		<xsl:variable name="totaalBrp" select="count(//resultaat/conversieResultaten/conversieResultaat/brpNaarLo3)" />
		<xsl:variable name="totaalBrpOk" select="count(//resultaat/conversieResultaten/conversieResultaat/brpNaarLo3[@status='OK'])" />
		<xsl:variable name="totaalBrpNok" select="count(//resultaat/conversieResultaten/conversieResultaat/brpNaarLo3[@status='NOK'])" />
		<xsl:variable name="totaalBrpExceptie" select="count(//resultaat/conversieResultaten/conversieResultaat/brpNaarLo3[@status='EXCEPTIE'])" />
		<xsl:variable name="totaalBrpGeenVerwachting" select="count(//resultaat/conversieResultaten/conversieResultaat/brpNaarLo3[@status='GEEN_VERWACHTING'])" />

		<xsl:variable name="totaalBrpVa" select="count(//resultaat/conversieResultaten/conversieResultaat/brpNaarLo3Va)" />
		<xsl:variable name="totaalBrpVaOk" select="count(//resultaat/conversieResultaten/conversieResultaat/brpNaarLo3Va[@status='OK'])" />
		<xsl:variable name="totaalBrpVaNok" select="count(//resultaat/conversieResultaten/conversieResultaat/brpNaarLo3Va[@status='NOK'])" />
		<xsl:variable name="totaalBrpVaExceptie" select="count(//resultaat/conversieResultaten/conversieResultaat/brpNaarLo3Va[@status='EXCEPTIE'])" />
		<xsl:variable name="totaalBrpVaGeenVerwachting" select="count(//resultaat/conversieResultaten/conversieResultaat/brpNaarLo3Va[@status='GEEN_VERWACHTING'])" />

        <xsl:variable name="totaalSqlControles" select="count(//resultaat/sqlControles/sqlControle/testStap)" />
        <xsl:variable name="totaalSqlControlesOk" select="count(//resultaat/sqlControles/sqlControle/testStap[@status='OK'])" />
        <xsl:variable name="totaalSqlControlesNok" select="count(//resultaat/sqlControles/sqlControle/testStap[@status='NOK'])" />
        <xsl:variable name="totaalSqlControlesExceptie" select="count(//resultaat/sqlControles/sqlControle/testStap[@status='EXCEPTIE'])" />
        <xsl:variable name="totaalSqlControlesGeenVerwachting" select="count(//resultaat/sqlControles/sqlControle/testStap[@status='GEEN_VERWACHTING'])" />

		&#160;
		<table id="thema_{@thema}">
			<tr>
				<th rowspan="2">Totalen</th>
				<th rowspan="2">Syntax/Precondities</th>
                <th rowspan="2">Percentage</th>
				<th rowspan="2">LO3 naar BRP</th>
				<th rowspan="2">Percentage</th>
				<th rowspan="2">Opslaan BRP</th>
				<th rowspan="2">Percentage</th>
				<th rowspan="2">Lezen BRP</th>
				<th rowspan="2">Percentage</th>
				<th colspan="2">BRP naar LO3</th>
				<th colspan="2">Percentage</th>
                <th rowspan="2">SQL Controles</th>
                <th rowspan="2">Percentage</th>
			</tr>
			<tr>
				<th>Basis</th>
				<th>Analyse</th>
				<th>Basis</th>
				<th>Analyse</th>
			</tr>
			<tr class="totalen">
				<th>Totaal aantal testcases</th>
				<td><xsl:value-of select="$totaalSyntaxPreconditie" /></td>
                <td>&#160;</td>
                <td><xsl:value-of select="$totaalLo3" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalOpslaan" /></td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalLezen" /></td>
                <td>&#160;</td>
                <td><xsl:value-of select="$totaalBrp" /></td>
                <td>&#160;</td>
                <td><xsl:value-of select="$totaalBrpVa" /></td>
				<td>&#160;</td>
                <td><xsl:value-of select="$totaalSqlControles" /></td>
                <td>&#160;</td>
			</tr>
			<tr>
				<th>Totaal aantal OK</th>
                <td><xsl:value-of select="$totaalSyntaxPreconditieOk" /></td>
                <td><xsl:value-of select="format-number($totaalSyntaxPreconditieOk div $totaalSyntaxPreconditie * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalLo3Ok" /></td>
				<td><xsl:value-of select="format-number($totaalLo3Ok div $totaalLo3 * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalOpslaanOk" /></td>
				<td><xsl:value-of select="format-number($totaalOpslaanOk div $totaalOpslaan * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalLezenOk" /></td>
				<td><xsl:value-of select="format-number($totaalLezenOk div $totaalLezen * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalBrpOk" /></td>
				<td><xsl:value-of select="format-number($totaalBrpOk div $totaalBrp * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalBrpVaOk" /></td>
				<td><xsl:value-of select="format-number($totaalBrpVaOk div $totaalBrpVa * 100, '##')" /> %</td>
                <td><xsl:value-of select="$totaalSqlControlesOk" /></td>
                <td><xsl:value-of select="format-number($totaalSqlControlesOk div $totaalSqlControles * 100, '##')" /> %</td>
			</tr>
			<tr>
				<th>Totaal aantal NOK</th>
                <td><xsl:value-of select="$totaalSyntaxPreconditieNok" /></td>
                <td><xsl:value-of select="format-number($totaalSyntaxPreconditieNok div $totaalSyntaxPreconditie * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalLo3Nok" /></td>
				<td><xsl:value-of select="format-number($totaalLo3Nok div $totaalLo3 * 100, '##')" /> %</td>
				<td>&#160;</td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalLezenNok" /></td>
				<td><xsl:value-of select="format-number($totaalLezenNok div $totaalLezen * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalBrpNok" /></td>
				<td><xsl:value-of select="format-number($totaalBrpNok div $totaalBrp * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalBrpVaNok" /></td>
				<td><xsl:value-of select="format-number($totaalBrpVaNok div $totaalBrpVa * 100, '##')" /> %</td>
                <td><xsl:value-of select="$totaalSqlControlesNok" /></td>
                <td><xsl:value-of select="format-number($totaalSqlControlesNok div $totaalSqlControles * 100, '##')" /> %</td>
			</tr>
			<tr>
				<th>Totaal aantal GEEN VERWACHTING</th>
				<td><xsl:value-of select="$totaalSyntaxPreconditieGeenVerwachting" /></td>
				<td><xsl:value-of select="format-number($totaalSyntaxPreconditieGeenVerwachting div $totaalSyntaxPreconditie * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalLo3GeenVerwachting" /></td>
				<td><xsl:value-of select="format-number($totaalLo3GeenVerwachting div $totaalLo3 * 100, '##')" /> %</td>
				<td>&#160;</td>
				<td>&#160;</td>
				<td>&#160;</td>
				<td>&#160;</td>
				<td><xsl:value-of select="$totaalBrpGeenVerwachting" /></td>
				<td><xsl:value-of select="format-number($totaalBrpGeenVerwachting div $totaalBrp * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalBrpVaGeenVerwachting" /></td>
				<td><xsl:value-of select="format-number($totaalBrpVaGeenVerwachting div $totaalBrpVa * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalSqlControlesGeenVerwachting" /></td>
                <td><xsl:value-of select="format-number($totaalSqlControlesGeenVerwachting div $totaalSqlControles * 100, '##')" /> %</td>
			</tr>
			<tr>
				<th>Totaal aantal EXCEPTIE</th>
                <td><xsl:value-of select="$totaalSyntaxPreconditieExceptie" /></td>
                <td><xsl:value-of select="format-number($totaalSyntaxPreconditieExceptie div $totaalSyntaxPreconditie * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalLo3Exceptie" /></td>
				<td><xsl:value-of select="format-number($totaalLo3Exceptie div $totaalLo3 * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalOpslaanExceptie" /></td>
				<td><xsl:value-of select="format-number($totaalOpslaanExceptie div $totaalOpslaan * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalLezenExceptie" /></td>
				<td><xsl:value-of select="format-number($totaalLezenExceptie div $totaalLezen * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalBrpExceptie" /></td>
				<td><xsl:value-of select="format-number($totaalBrpExceptie div $totaalBrp * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalBrpVaExceptie" /></td>
				<td><xsl:value-of select="format-number($totaalBrpVaExceptie div $totaalBrpVa * 100, '##')" /> %</td>
				<td><xsl:value-of select="$totaalSqlControlesExceptie" /></td>
                <td><xsl:value-of select="format-number($totaalSqlControlesExceptie div $totaalSqlControles * 100, '##')" /> %</td>
			</tr>
		</table>
	</xsl:template>
</xsl:stylesheet>
