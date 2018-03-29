<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="foutmelding">
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
				<table>
					<col width="150px" />
					<col/>
				<tr><td>Context</td><td><xsl:value-of select="context" /></td></tr>
				<tr><td>Melding</td><td><xsl:value-of select="message" /></td></tr>
				<tr><td colspan="2">Stacktrace</td></tr>
				<tr><td colspan="2"><pre><xsl:value-of select="stacktrace" /></pre></td></tr>
				</table>
			
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>