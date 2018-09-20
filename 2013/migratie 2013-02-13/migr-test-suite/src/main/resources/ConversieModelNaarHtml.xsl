<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" />

	<xsl:import href="Lo3Categorieen.xsl"/>
	<xsl:import href="BrpGroepen.xsl"/>

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
			<xsl:when test="/*/identificatienummerStapel/*[last()]/administratienummer">
				<h2>BRP Groepen</h2>
			</xsl:when>
			<xsl:when test="/*/persoonStapel/*[last()]/aNummer">
				<h2>LO3 CategorieÃ«n</h2>
			</xsl:when>
		</xsl:choose>
		<h3>A-nummer&#160;<xsl:value-of select="/*/identificatienummerStapel/*[last()]/administratienummer | /*/persoonStapel/*[last()]/aNummer"/></h3>
		<a href="#" onclick="showAll();">Alles tonen</a>|<a href="#" onclick="hideAll();">Alles verbergen</a>

		<xsl:call-template name="process-stapels" />
		
		</body></html>
	</xsl:template>
	
	<xsl:template name="process-stapels">
		<xsl:for-each select="*">
			<xsl:choose>
			<xsl:when test="@class='java.util.ArrayList'">
				<xsl:for-each select="*">
					<xsl:call-template name="process-stapel">
						<xsl:with-param name="stapelnummer"><xsl:value-of select="position()" /></xsl:with-param>
					</xsl:call-template>
				</xsl:for-each>
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
		<xsl:variable name="stapel-naam"><xsl:value-of select="local-name()"/>-<xsl:value-of select="$stapelnummer"/></xsl:variable>
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
				<xsl:when test="local-name()='relatie'">
					<table>
						<tr>
							<td><xsl:value-of select="soortRelatieCode" /></td>
							<td><xsl:value-of select="rolCode" /></td>
						</tr>
						<tr><td colspan="2">
							<xsl:for-each select="relatieStapel">
								<xsl:call-template name="process-stapel">
									<xsl:with-param name="stapelnummer">1</xsl:with-param>
								</xsl:call-template>							
							</xsl:for-each>
						</td></tr>
						<tr><td colspan="2">
							<xsl:for-each select="betrokkenheden/*">
								<xsl:call-template name="process-stapel">
									<xsl:with-param name="stapelnummer"><xsl:value-of select="position()" /></xsl:with-param>
								</xsl:call-template>
							</xsl:for-each>
						</td></tr>
					</table>
				</xsl:when>
				<xsl:when test="local-name()='betrokkenheid'">
					<table>
						<tr><td><xsl:value-of select="rol" /></td></tr>
						<tr><td>
							<xsl:for-each select="identificatienummerStapel|samengesteldeNaamStapel|geslachtsaanduidingStapel|geboorteStapel|ouderlijkGezagStapel|ouderStapel">
								<xsl:call-template name="process-stapel">
									<xsl:with-param name="stapelnummer">1</xsl:with-param>
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
			<xsl:for-each select="lo3Categorie|migratieGroep|brpGroep">
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
					<xsl:when test="local-name()='migratieGroep'">
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