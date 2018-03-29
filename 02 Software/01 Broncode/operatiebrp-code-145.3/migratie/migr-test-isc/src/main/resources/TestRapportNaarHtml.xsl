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
		text-align: left;
	}
	a {
		color: inherit;
	}
	table {
		margin: 0 auto;
		border-collapse: collapse;
		border: 1px solid
		#222222;
		
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
		vertical-align: top;
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
	.BESTANDEN {
    }
    .BESTANDEN:hover > .BESTANDENDIV {
        display: block;
    }
    .BESTANDENDIV {
        display: none;
    }
]]>
                </style>
            </head>
            <body>

                    <h1>
                        Testresultaten van
                        <xsl:value-of select="/testrapport/tijdstip" />
                    </h1>
                
                <xsl:call-template name="totalen" />
                
                <xsl:apply-templates
                    select="resultaat[generate-id(.)=generate-id(key('resultaten-per-thema',@thema))]">
                    <xsl:sort select="@thema" data-type="text" order="ascending" />
                </xsl:apply-templates>
                
            </body>
        </html>
    </xsl:template>
    
    <xsl:template name="totalen">
        
        <xsl:variable name="totaal" select="count(//resultaat/resultaat)" />
        <xsl:variable name="totaalOk" select="count(//resultaat/resultaat[@status='OK'])" />
        <xsl:variable name="totaalNok" select="count(//resultaat/resultaat[@status='NOK'])" />
        <xsl:variable name="totaalExceptie" select="count(//resultaat/resultaat[@status='EXCEPTIE'])" />
        
        
        &#160;
        <table id="thema_{@thema}" width="100%">
            <tr>
                <th>Aantal</th><th>OK</th><th>NOK</th><th>Excepties</th><th width="100%">&#160;</th>
            </tr>
            <tr>
                <td style="white-space: nowrap;">
                    <xsl:value-of select="$totaal" />
                </td>
                <td style="white-space: nowrap;">
                    <xsl:choose>
                        <xsl:when test="$totaal = $totaalOk">
                            <xsl:attribute name="class">
                                <xsl:text>OK</xsl:text>
                            </xsl:attribute>
                        </xsl:when>
                    </xsl:choose>
                    <xsl:value-of select="$totaalOk" /> (<xsl:value-of select="format-number($totaalOk div $totaal * 100, '##')" /> %)
                </td>
                <td style="white-space: nowrap;">
                    <xsl:choose>
                        <xsl:when test="$totaalNok != 0">
                            <xsl:attribute name="class">
                                <xsl:text>NOK</xsl:text>
                            </xsl:attribute>
                        </xsl:when>
                    </xsl:choose>
                    <xsl:value-of select="$totaalNok" /> (<xsl:value-of select="format-number($totaalNok div $totaal * 100, '##')" /> %)
                </td>
                <td style="white-space: nowrap;">
                    <xsl:choose>
                        <xsl:when test="$totaalExceptie != 0">
                            <xsl:attribute name="class">
                                <xsl:text>EXCEPTIE</xsl:text>
                            </xsl:attribute>
                        </xsl:when>
                    </xsl:choose>
                    <xsl:value-of select="$totaalExceptie" /> (<xsl:value-of select="format-number($totaalExceptie div $totaal * 100, '##')" /> %)
                </td>
            </tr>
        </table>
    </xsl:template>
    
    <xsl:template match="resultaat">
        &#160;
        <table id="thema_{@thema}" width="100%">
            <tr>
                <th width="50">
                    Thema&#160;
                </th>
                <td>
                    <b>
                    <xsl:value-of select="@thema" />
                    </b>
                </td>
                <td colspan="4">&#160;</td>
            </tr>
            <tr>
                <th colspan="2">Testgeval</th>
                <th width="10%">Proces<xsl:text disable-output-escaping="yes">&amp;</xsl:text>nbsp;Ids</th>
                
                <th width="10%">Resultaat</th>
                <th width="10%">Omschrijving</th>
                <th width="25%">Bestanden</th>
            </tr>
            <xsl:for-each select="key('resultaten-per-thema', @thema)">
                <xsl:sort select="@naam" data-type="text" order="ascending" />
                <tr>

                    <td colspan="2">
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
                    <td>
                        <xsl:for-each select="processInstanceIds/processInstanceId">
                            <xsl:value-of select="text()" />
                            <xsl:value-of select="'&amp;nbsp;'" disable-output-escaping="yes" />
                        </xsl:for-each>
                    </td>
                    
                    <xsl:choose>
                        <xsl:when test="foutmelding">
                            <td colspan="3">
                                <xsl:value-of select="foutmelding/context/text()" /> (<xsl:value-of select="foutmelding/message/text()" />)
                            </td>
                        </xsl:when>
                        <xsl:otherwise>
                            <td>
                                <xsl:attribute name="class"><xsl:value-of select="resultaat/@status" /></xsl:attribute>
                                <xsl:choose>
                                    <xsl:when test="resultaat/bestand">
                                        <a>
                                            <xsl:attribute name="href">
                                                <xsl:value-of select="concat(@thema,'/',resultaat/bestand)" />
                                            </xsl:attribute>
                                            
                                            <xsl:value-of select="resultaat/@status" />
                                        </a>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="resultaat/@status" />
                                    </xsl:otherwise>
                                </xsl:choose>
                            </td>
                            <td>
                                <xsl:value-of select="resultaat/@omschrijving" />
                            </td>
                            <td class="BESTANDEN">
                                <div class="BESTANDENDIV">
                                <xsl:for-each select="bestanden/bestand">
                                    
                                    <xsl:text>&#13;&#10;</xsl:text>
                                    

                                    <a>
                                        <xsl:choose>
                                            <xsl:when test="contains(bestand,'dummy')">
                                                <xsl:attribute name="style">
                                                    <xsl:text>color: green</xsl:text>
                                                </xsl:attribute>
												<xsl:attribute name="type">
                                                    <xsl:text>text/html</xsl:text>
                                                </xsl:attribute>
                                            </xsl:when>
                                            <xsl:when test="contains(bestand,'.expected')">
                                                <xsl:attribute name="style">
                                                    <xsl:text>color: blue</xsl:text>
                                                </xsl:attribute>
												<xsl:attribute name="type">
                                                    <xsl:text>text/html</xsl:text>
                                                </xsl:attribute>
                                            </xsl:when>
                                            <xsl:when test="contains(bestand,'.txt')">
                                                <xsl:attribute name="style">
                                                    <xsl:text>color: red</xsl:text>
                                                </xsl:attribute>
                                            </xsl:when>
                                            <xsl:when test="contains(bestand,'.sql')">
                                                <xsl:attribute name="style">
                                                    <xsl:text>color: brown</xsl:text>
                                                </xsl:attribute>
												<xsl:attribute name="type">
                                                    <xsl:text>text/html</xsl:text>
                                                </xsl:attribute>
                                            </xsl:when>
                                        </xsl:choose>
                                        <xsl:attribute name="title">
                                            <xsl:value-of select="bestand" />
                                        </xsl:attribute>
                                        <xsl:attribute name="href">
                                            <xsl:value-of select="concat(ancestor::resultaat/@thema,'/',ancestor::resultaat/@naam,'/',bestand)" />
                                        </xsl:attribute>
                                        <xsl:value-of select="volgnummer" />
                                    </a> 
                                    
                                    
                                         
                                </xsl:for-each>
                                </div>
                                &#160;
                            </td>
                        </xsl:otherwise>
                    </xsl:choose>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>
    

    
    
</xsl:stylesheet>