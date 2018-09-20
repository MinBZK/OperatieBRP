<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="logging">
		<html>
			<head>
				<title>
					Foutmelding
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
		color: #154273; 
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
				
]]>
	</style>
			</head>
			<body>
				<center>
				<h1>Preconditie/conversie logging
				</h1>
				</center>
							
				<table>
					<col width="150px" />
					<col width="150px" />
					<col width="150px" />
					<col/>
					<col/>
					<col/>
					<col/>
					<tr>
						<th colspan="3">Herkomst</th>
						<th >Severity</th>
						<th >Code</th>
						<th >LO3 Element</th>
					</tr>
					
					<xsl:for-each select="logRegel">
						<tr>
							<td><xsl:value-of select="lo3Herkomst/categorie" /></td>
							<td><xsl:value-of select="lo3Herkomst/stapel" /></td>
							<td><xsl:value-of select="lo3Herkomst/voorkomen" /></td>

							<td><xsl:value-of select="severity" /></td>
							<td><xsl:value-of select="soortMeldingCode" /></td>
							<td><xsl:value-of select="lo3ElementNummer" /></td>
						</tr>
					</xsl:for-each>
				</table>
			
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>