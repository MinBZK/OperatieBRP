<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version='1.0'
                xmlns:xslthl="http://xslthl.sf.net"
                exclude-result-prefixes="xslthl">

    <xsl:import href="urn:docbkx:stylesheet"/>
    <xsl:import href="urn:docbkx:stylesheet/highlight.xsl"/>

    <xsl:param name="fop1.extensions" select="1"/>
    <xsl:param name="highlight.source" select="1"/>

    <!-- Standaard TOC generatie, maar dan 'table' uit 'book' gehaald -->
    <xsl:param name="generate.toc">
        book      toc,title
    </xsl:param>

    <xsl:template match='xslthl:keyword'>
        <b class="color: green"><xsl:apply-templates/></b>
    </xsl:template>

    <!-- verwijder de tussenpagina -->
    <xsl:template name="book.titlepage.verso"/>
    <xsl:template name="book.titlepage.before.verso"/>

    <xsl:attribute-set name="monospace.verbatim.properties">
        <!--<xsl:attribute name="wrap-option">wrap</xsl:attribute>-->
        <!--<xsl:attribute name="hyphenation-character">\</xsl:attribute>-->
        <xsl:attribute name="font-size">8pt</xsl:attribute>
    </xsl:attribute-set>

</xsl:stylesheet>