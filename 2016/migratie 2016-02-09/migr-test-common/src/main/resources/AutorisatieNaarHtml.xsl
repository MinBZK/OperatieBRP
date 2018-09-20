<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 	<xsl:import href="Lo3Categorieen.xsl"/>
    <xsl:import href="BrpAutorisatieGroepen.xsl"/>
    <xsl:import href="BrpGroepen.xsl"/>

	<xsl:output method="html" />

	<xsl:template match="/*">
		<html>
		<head>
		<script type="text/javascript">//<![CDATA[
			function showHideDiv(divId) {
				if(document.getElementById(divId).style.display == 'none'){
					document.getElementById(divId).style.display = 'block';
				}else{
					document.getElementById(divId).style.display = 'none';
				}
			}

			function showAll() {
				var arr = document.getElementsByTagName("div");
				for (i = 0; i < arr.length; i++) {
					if(arr[i].style.display == 'none'){
						arr[i].style.display = 'block';
					}
				}
			}

			function hideAll() {
				var arr = document.getElementsByTagName("div");
				for (i = 0; i < arr.length; i++) {
					if(arr[i].style.display == 'block'){
						arr[i].style.display = 'none';
					}
				}
			}
		//]]></script>
<style><![CDATA[
					body {
					font-family:
					Trebuchet
					MS, Helvetica,
					sans-serif;
					background-color: White;
					font-size:16px;
					}
					table {
					border-collapse: collapse;
					border: 1px solid
					#222222;
					/* white-space: nowrap; */
					}
					th {
					background-color: #046F96;
					color:
					white;
					}
					a {
					color: #154273;
					}
					th,td {
					text-align: left;
					border: 1px solid
					#222222;
					padding:
					2px;
					text-align: left;
					}
					tr {
					height: 20px;
					}
					.geen-verwachting
					{
					background-color: grey;
					color: white;
					}
					.ok
					{
					background-color: green;
					color: white;
					}
					.nok,
					.exceptie
					{
					background-color: red;
					color: white;
					}
					h3 {
					margin-bottom: 2px;
					}
				]]></style>
			</head>
		<body>
        <xsl:choose>
            <xsl:when test="count(/*/partij/naam) = 0">
                <h2>LO3 Autorisatie</h2>
                <h3>Afnemerindicatie&#160;<xsl:value-of select="format-number(//afnemersindicatie, '000000')"/>&#160;-&#160;<xsl:value-of select="//afnemernaam"/></h3>            
            </xsl:when>
            <xsl:otherwise>
                <h2>BRP Autorisatie</h2>
                <h3>Afnemerindicatie&#160;<xsl:value-of select="format-number(//partij/partijCode/waarde, '000000')"/>&#160;-&#160;<xsl:value-of select="//partij/naam"/></h3>            
            </xsl:otherwise>
        </xsl:choose>
		<a href="#" onclick="showAll();">Alles tonen</a>|<a href="#" onclick="hideAll();">Alles verbergen</a>

		<xsl:call-template name="process-stapels" />

		</body></html>
	</xsl:template>

	<xsl:template name="process-stapels">
		<xsl:for-each select="*">
			<xsl:choose>
			<xsl:when test="@class='java.util.ArrayList'">
                <xsl:choose>
                    <xsl:when test="(local-name()='autorisatietabelRegelLijst') or (local-name()='leveringsautorisatieLijst')">
                        <xsl:call-template name="process-stapel">
                            <xsl:with-param name="stapelnummer">1</xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
        				<xsl:for-each select="*">
        					<xsl:call-template name="process-stapel">
        						<xsl:with-param name="stapelnummer"><xsl:value-of select="position()" /></xsl:with-param>
        					</xsl:call-template>
        				</xsl:for-each>
                    </xsl:otherwise>
                </xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="process-stapel">
					<xsl:with-param name="stapelnummer">1</xsl:with-param>
				</xsl:call-template>
			</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="process-stapel">
		<xsl:param name="stapelnummer" />
		<xsl:variable name="stapel-naam"><xsl:value-of select="local-name()"/></xsl:variable>
		<xsl:variable name="stapel-id"><xsl:value-of select="generate-id()" /></xsl:variable>

		<h3>
			<a href="#">
				<xsl:attribute name="onclick">showHideDiv('<xsl:value-of select="$stapel-id"/>');return false;</xsl:attribute>
				<xsl:value-of select="$stapel-naam"/>
			</a>
		</h3>
		<div style="display:none">
			<xsl:attribute name="id"><xsl:value-of select="$stapel-id"/></xsl:attribute>

			<xsl:choose>
                <xsl:when test="local-name()='partij'">
                    <table>
                        <tr><th>Afnemergegevens</th></tr>
                        <tr><td><strong>Naam&#160;:&#160;&#160;</strong><xsl:value-of select="naam"/><br/>
                        <strong>Partij code&#160;:&#160;&#160;</strong><xsl:value-of select="format-number(partijCode/waarde, '000000')"/></td></tr>
                        <tr><td>
                            <xsl:for-each select="partijStapel">
                                <xsl:call-template name="process-stapel">
                                    <xsl:with-param name="stapelnummer"><xsl:value-of select="position()" /></xsl:with-param>
                                </xsl:call-template>
                            </xsl:for-each>
                        </td></tr>
                    </table>
                </xsl:when>
                <xsl:when test="local-name()='leveringsautorisatieLijst'">
                    <xsl:for-each select="leveringsautorisatie">
                     <table>
                         <tr><th>Leveringsautorisatiegegevens</th></tr>
                         <tr><td><strong>Stelsel code&#160;:&#160;&#160;</strong><xsl:value-of select="stelsel"/><br/>
                         <strong>Modelautorisatie?&#160;:&#160;&#160;</strong><xsl:value-of select="indicatieModelautorisatie"/>
                         </td></tr>
                         <tr><td>
                             <xsl:for-each select="dienstbundels">
                                 <xsl:call-template name="process-stapels"/>
                             </xsl:for-each>
                         </td></tr>
                         <tr><td>
                             <xsl:for-each select="leveringsautorisatieStapel">
                                 <xsl:call-template name="process-stapel">
                                     <xsl:with-param name="stapelnummer"><xsl:value-of select="position()" /></xsl:with-param>
                                 </xsl:call-template>
                             </xsl:for-each>
                         </td></tr>
                     </table>
                    </xsl:for-each>
                </xsl:when>
                <xsl:when test="local-name()='dienstbundel'">
                    <table>
                        <tr><th>Dienstbundelgegevens</th></tr>
                        <tr><td>
                            <xsl:for-each select="lo3rubrieken">
                                <table>
			                        <tr><td><strong>Conversierubriek(en)&#160;:&#160;&#160;</strong>
			                            <xsl:for-each select="lo3Rubriek">
			                            	<xsl:value-of select="conversieRubriek"/>,
			                            </xsl:for-each>
			                    	</td></tr>
			                    </table>
							</xsl:for-each>
                        </td></tr>
                        <tr><td>
                             <xsl:for-each select="diensten">
                                 <xsl:call-template name="process-stapels"/>
                             </xsl:for-each>
                        </td></tr>
                        <tr><td>
                            <xsl:for-each select="dienstbundelStapel">
                                <xsl:call-template name="process-stapel">
                               		<xsl:with-param name="stapelnummer"><xsl:value-of select="position()" /></xsl:with-param>
                                </xsl:call-template>
                            </xsl:for-each>
                        </td></tr>
                    </table>
                </xsl:when>
                <xsl:when test="local-name()='dienst'">
                    <table>
                        <tr><td><strong>Effectafnemersindicatie&#160;:&#160;&#160;</strong>
                        <xsl:choose>
	                        <xsl:when test="effectAfnemersindicatie='1'">Plaatsen</xsl:when>
	                        <xsl:when test="effectAfnemersindicatie='2'">Verwijderen</xsl:when>
                        </xsl:choose></td></tr>
                        <tr><td><strong>Soort dienst&#160;:&#160;&#160;</strong><xsl:choose>
                        <xsl:when test="soort='1'">Mutatielevernig obv doelbinding</xsl:when>
                        <xsl:when test="soort='2'">Mutatielevering obv afnemersindicatie</xsl:when>
                        <xsl:when test="soort='3'">Onderhouden afnemersindicatie</xsl:when>
                        <xsl:when test="soort='4'">Attendering</xsl:when>
                        <xsl:when test="soort='5'">Zoek persoon</xsl:when>
                        <xsl:when test="soort='6'">Zoek persoon met adres</xsl:when>
                        <xsl:when test="soort='7'">Zoek medebewoners van persoon</xsl:when>
                        <xsl:when test="soort='8'">Detail persoon</xsl:when>
                        <xsl:when test="soort='9'">Synchronisatie persoon</xsl:when>
                        <xsl:when test="soort='10'">Synchronisatie stamgegeven</xsl:when>
                        <xsl:when test="soort='11'">Mutatielevering stamgegeven</xsl:when>
                        <xsl:when test="soort='12'">Selectie</xsl:when>
                        <xsl:when test="soort='13'">Geef details persoon</xsl:when>
                        <xsl:when test="soort='14'">Geef synchroniciteitsgegevens persoon</xsl:when>
                        <xsl:when test="soort='15'">Geef identificerende gegevens persoon bulk</xsl:when>
                        <xsl:when test="soort='16'">Geef details terugmelding</xsl:when>
                        <xsl:when test="soort='17'">Opvragen aantal personen op adres</xsl:when>
                        <xsl:when test="soort='18'">Aanmelding gerede twijfel</xsl:when>
                        <xsl:when test="soort='19'">Intrekking terugmelding</xsl:when>
                        <xsl:when test="soort='20'">Verwijderen afnemersindicatie</xsl:when></xsl:choose>
                        </td></tr>
                        <tr><td>
                            <xsl:for-each select="dienstStapel">
                                <xsl:call-template name="process-stapel">
                                    <xsl:with-param name="stapelnummer">1</xsl:with-param>
                                </xsl:call-template>
                            </xsl:for-each>
                        </td></tr>
                        <tr><td>
                            <xsl:for-each select="dienstAttenderingStapel">
                                <xsl:call-template name="process-stapel">
                                    <xsl:with-param name="stapelnummer">1</xsl:with-param>
                                </xsl:call-template>
                            </xsl:for-each>
                        </td></tr>
                        <tr><td>
                            <xsl:for-each select="dienstSelectieStapel">
                                <xsl:call-template name="process-stapel">
                                    <xsl:with-param name="stapelnummer">1</xsl:with-param>
                                </xsl:call-template>
                            </xsl:for-each>
                        </td></tr>
                    </table>
				</xsl:when>
                <xsl:when test="local-name()='toegangLeveringsautorisatie'">
                        <tr><td><strong>Afleverpunt&#160;:&#160;&#160;</strong><xsl:value-of select="afleverpunt"/></td></tr>
                        <tr><td><strong>Geautoriseerde&#160;:&#160;&#160;</strong><xsl:value-of select="geautoriseerde"/></td></tr>
                        <tr><td><strong>Ondertekenaar&#160;:&#160;&#160;</strong><xsl:value-of select="ondertekenaar"/></td></tr>
                        <tr><td><strong>Transporteur&#160;:&#160;&#160;</strong><xsl:value-of select="transporteur"/></td></tr>
                        <tr><td><strong>Geblokkeerd?&#160;:&#160;&#160;</strong><xsl:value-of select="indicatieGeblokkeerd"/></td></tr>
                        <tr><td><strong>Nadere populatiebeperking&#160;:&#160;&#160;</strong><xsl:value-of select="naderePopulatiebeperking"/></td></tr>
                        <tr><td><strong>Ingangsdatum&#160;:&#160;&#160;</strong><xsl:value-of select="datumIngang"/></td></tr>
                        <tr><td><strong>Einddatum&#160;:&#160;&#160;</strong><xsl:value-of select="datumEinde"/></td></tr>
                    <table>
                        <tr><td>
                            <xsl:for-each select="leveringsautorisaties">
                                <xsl:call-template name="process-stapel">
                                    <xsl:with-param name="stapelnummer"><xsl:value-of select="position()" /></xsl:with-param>
                                </xsl:call-template>
                            </xsl:for-each>
                        </td></tr>
                        <tr><td>
                            <xsl:for-each select="toegangLeveringsautorisatieStapel">
                                <xsl:call-template name="process-stapel">
                               		<xsl:with-param name="stapelnummer"><xsl:value-of select="position()" /></xsl:with-param>
                                </xsl:call-template>
                            </xsl:for-each>
                        </td></tr>
                    </table>
				</xsl:when>
 				<xsl:otherwise>
					<!-- NORMALE STAPEL -->
					<xsl:call-template name="process-groepen" />
				</xsl:otherwise>
			</xsl:choose>
		</div>
	</xsl:template>

	<xsl:template name="process-groepen">
		<table>
            <!--  <tr><td><xsl:value-of select="local-name()"/></td></tr>  -->
			<xsl:for-each select="lo3Categorie|tussenGroep|brpGroep">
				<xsl:choose>
					<xsl:when test="local-name()='lo3Categorie'">
						<xsl:if test="position()=1">
							<tr>
								<xsl:apply-templates select="inhoud" mode="header" />
								<xsl:call-template name="lo3HistorieHeader" />
								<xsl:call-template name="lo3DocumentatieHeader" />
							</tr>
						</xsl:if>
						<tr>
							<xsl:apply-templates select="inhoud" mode="inhoud" />
							<xsl:call-template name="lo3Historie" />
							<xsl:call-template name="lo3Documentatie" />
						</tr>
					</xsl:when>
					<xsl:when test="local-name()='tussenGroep'">
						<xsl:if test="position()=1">
							<tr>
								<xsl:apply-templates select="inhoud" mode="header" />
								<xsl:call-template name="lo3HistorieHeader" />
								<xsl:call-template name="lo3DocumentatieHeader" />
							</tr>
						</xsl:if>
						<tr>
							<xsl:apply-templates select="inhoud" mode="inhoud" />
							<xsl:call-template name="lo3Historie" />
							<xsl:call-template name="lo3Documentatie" />
						</tr>
					</xsl:when>
					<xsl:when test="local-name()='brpGroep'">
						<xsl:if test="position()=1">
							<tr>
								<xsl:apply-templates select="inhoud" mode="header" />
								<xsl:call-template name="brpHistorieHeader" />
								<xsl:call-template name="brpActiesHeader" />
							</tr>
						</xsl:if>
						<tr>
							<xsl:apply-templates select="inhoud" mode="inhoud" />
							<xsl:call-template name="brpHistorie" />
							<xsl:call-template name="brpActiesInhoud" />
						</tr>
					</xsl:when>
				</xsl:choose>
			</xsl:for-each>
		</table>
	</xsl:template>


</xsl:stylesheet>
