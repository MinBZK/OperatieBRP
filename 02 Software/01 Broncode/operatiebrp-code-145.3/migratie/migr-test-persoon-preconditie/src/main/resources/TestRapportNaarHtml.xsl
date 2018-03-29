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
					Syntax/Precondities
				</th>
				<th>
					LO3 naar BRP
				</th>
				<th>
					Converteer log
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
								<xsl:value-of select="foutmelding/context/text()" />
								(
								<xsl:value-of select="foutmelding/message/text()" />
								)
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
						</xsl:otherwise>
					</xsl:choose>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template match="syntaxPrecondities|lo3NaarBrp|conversieLog">
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
					<xsl:value-of
					select="concat(ancestor::resultaat/@thema,'/',verschillenBestand)" />	
				</xsl:attribute>
				verschillen
			</a>
			)
		</xsl:if>
	</xsl:template>



	<xsl:template name="totalen">

        <xsl:variable name="totaalSyntaxPreconditie" select="count(//resultaat/syntaxPrecondities)" />
        <xsl:variable name="totaalSyntaxPreconditieOk" select="count(//resultaat/syntaxPrecondities[@status='OK'])" />
        <xsl:variable name="totaalSyntaxPreconditieNok" select="count(//resultaat/syntaxPrecondities[@status='NOK'])" />
        <xsl:variable name="totaalSyntaxPreconditieExceptie" select="count(//resultaat/syntaxPrecondities[@status='EXCEPTIE'])" />
        <xsl:variable name="totaalSyntaxPreconditieGeenVerwachting" select="count(//resultaat/syntaxPrecondities[@status='GEEN_VERWACHTING'])" />

        <xsl:variable name="totaalConversieLog" select="count(//resultaat/conversieLog)" />
        <xsl:variable name="totaalConversieLogOk" select="count(//resultaat/conversieLog[@status='OK'])" />
        <xsl:variable name="totaalConversieLogNok" select="count(//resultaat/conversieLog[@status='NOK'])" />
        <xsl:variable name="totaalConversieLogExceptie" select="count(//resultaat/conversieLog[@status='EXCEPTIE'])" />
        <xsl:variable name="totaalConversieLogGeenVerwachting" select="count(//resultaat/conversieLog[@status='GEEN_VERWACHTING'])" />

        <xsl:variable name="totaalLo3" select="count(//resultaat/lo3NaarBrp)" />
        <xsl:variable name="totaalLo3Ok" select="count(//resultaat/lo3NaarBrp[@status='OK'])" />
        <xsl:variable name="totaalLo3Nok" select="count(//resultaat/lo3NaarBrp[@status='NOK'])" />
        <xsl:variable name="totaalLo3Exceptie" select="count(//resultaat/lo3NaarBrp[@status='EXCEPTIE'])" />
        <xsl:variable name="totaalLo3GeenVerwachting" select="count(//resultaat/lo3NaarBrp[@status='GEEN_VERWACHTING'])" />

        &#160;
        <table id="thema_{@thema}">
            <tr>
                <th>Totalen</th>
                <th>Syntax/Precondities</th>
                <th>Percentage</th>
                <th>LO3 naar BRP</th>
                <th>Percentage</th>
                <th>Converteer log</th>
                <th>Percentage</th>
            </tr>
            <tr class="totalen">
                <th>Totaal aantal testcases</th>
                <td><xsl:value-of select="$totaalSyntaxPreconditie" /></td>
                <td>&#160;</td>
                <td><xsl:value-of select="$totaalLo3" /></td>
                <td>&#160;</td>
                <td><xsl:value-of select="$totaalConversieLog" /></td>
                <td>&#160;</td>
            </tr>
            <tr>
                <th>Totaal aantal OK</th>
                <td><xsl:value-of select="$totaalSyntaxPreconditieOk" /></td>
                <td><xsl:value-of select="format-number($totaalSyntaxPreconditieOk div $totaalSyntaxPreconditie * 100, '##')" /> %</td>
                <td><xsl:value-of select="$totaalLo3Ok" /></td>
                <td><xsl:value-of select="format-number($totaalLo3Ok div $totaalLo3 * 100, '##')" /> %</td>
                <td><xsl:value-of select="$totaalConversieLogOk" /></td>
                <td><xsl:value-of select="format-number($totaalConversieLogOk div $totaalConversieLog * 100, '##')" /> %</td>
            </tr>
            <tr>
                <th>Totaal aantal NOK</th>
                <td><xsl:value-of select="$totaalSyntaxPreconditieNok" /></td>
                <td><xsl:value-of select="format-number($totaalSyntaxPreconditieNok div $totaalSyntaxPreconditie * 100, '##')" /> %</td>
                <td><xsl:value-of select="$totaalLo3Nok" /></td>
                <td><xsl:value-of select="format-number($totaalLo3Nok div $totaalLo3 * 100, '##')" /> %</td>
                <td><xsl:value-of select="$totaalConversieLogNok" /></td>
                <td><xsl:value-of select="format-number($totaalConversieLogNok div $totaalConversieLog * 100, '##')" /> %</td>
            </tr>
            <tr>
                <th>Totaal aantal GEEN VERWACHTING</th>
                <td><xsl:value-of select="$totaalSyntaxPreconditieGeenVerwachting" /></td>
                <td><xsl:value-of select="format-number($totaalSyntaxPreconditieGeenVerwachting div $totaalSyntaxPreconditie * 100, '##')" /> %</td>
                <td><xsl:value-of select="$totaalLo3GeenVerwachting" /></td>
                <td><xsl:value-of select="format-number($totaalLo3GeenVerwachting div $totaalLo3 * 100, '##')" /> %</td>
                <td><xsl:value-of select="$totaalConversieLogGeenVerwachting" /></td>
                <td><xsl:value-of select="format-number($totaalConversieLogGeenVerwachting div $totaalConversieLog * 100, '##')" /> %</td>
            </tr>
            <tr>
                <th>Totaal aantal EXCEPTIE</th>
                <td><xsl:value-of select="$totaalSyntaxPreconditieExceptie" /></td>
                <td><xsl:value-of select="format-number($totaalSyntaxPreconditieExceptie div $totaalSyntaxPreconditie * 100, '##')" /> %</td>
                <td><xsl:value-of select="$totaalLo3Exceptie" /></td>
                <td><xsl:value-of select="format-number($totaalLo3Exceptie div $totaalLo3 * 100, '##')" /> %</td>
                <td><xsl:value-of select="$totaalConversieLogExceptie" /></td>
                <td><xsl:value-of select="format-number($totaalConversieLogExceptie div $totaalConversieLog * 100, '##')" /> %</td>
            </tr>           
        </table>
    </xsl:template>
</xsl:stylesheet>