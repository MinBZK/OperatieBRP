<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:lb="http://www.liquibase.org/xml/ns/dbchangelog">

    <xsl:output method="xml" indent="yes"/>
    <xsl:strip-space elements="*"/>

    <xsl:template match="@*|node()" name="identity">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

    <!-- Haal alle referenties met een '$' eruit, zodat de MAT$... tabellen niet meer in de diff voorkomen. -->
    <xsl:template match="//lb:changeSet[lb:createTable[contains(@tableName, '$')]]"/>
    <xsl:template match="//lb:changeSet[lb:createIndex[contains(@tableName, '$')]]"/>
    <xsl:template match="//lb:changeSet[lb:addPrimaryKey[contains(@tableName, '$')]]"/>
    <xsl:template match="//lb:changeSet[lb:addForeignKeyConstraint[contains(@baseTableName, '$') or contains(@referencedTableName, '$')]]"/>
    <xsl:template match="//lb:changeSet[lb:addUniqueConstraint[contains(@tableName, '$')]]"/>
    <xsl:template match="//lb:changeSet[lb:createSequence[contains(@sequenceName, '$')]]"/>

    <!-- Haal alle unique constraints eruit, omdat er onterechte unique constraints worden gegenereerd voor foreign key constraints.
         Aangezien we de HSQLDB alleen read only gebruiken, hebben we de unique constraints verder ook niet nodig.
         Dit is makkelijker dan uitzoeken hoe dit komt wat waarschijnlijk op een Liquibase bug uitkomt. -->
    <xsl:template match="//lb:changeSet[lb:addUniqueConstraint]"/>

    <!-- Vervangt alle BLOB (sub) types door CLOB, zodat HSQLDB ook snapt het het om tekst gaat. -->
    <xsl:template match="@type">
        <xsl:attribute name="type">
            <xsl:choose>
                <xsl:when test=". = 'BLOB SUB_TYPE 0'">
                    <xsl:text>CLOB</xsl:text>
                </xsl:when>
                <xsl:when test=". = 'BLOB SUB_TYPE 1'">
                    <xsl:text>CLOB</xsl:text>
                </xsl:when>
                <xsl:when test=". = 'BLOB SUB_TYPE TEXT'">
                    <xsl:text>CLOB</xsl:text>
                </xsl:when>
                <xsl:when test=". = 'BLOB'">
                    <xsl:text>CLOB</xsl:text>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="."/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:attribute>
    </xsl:template>

    <xsl:template match="//lb:changeSet[*[@schemaName]]"/>

</xsl:stylesheet>