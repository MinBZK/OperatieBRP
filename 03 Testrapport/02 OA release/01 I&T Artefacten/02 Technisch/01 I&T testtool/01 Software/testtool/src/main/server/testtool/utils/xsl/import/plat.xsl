<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs"
	xmlns:exsl="http://exslt.org/common" xmlns:utils="java:nl.operatiebrp.ient.transform.DocumentUtils" extension-element-prefixes="exsl"
	version="2.0" xmlns:fn="http://whatever">
	<xsl:output method="xml" omit-xml-declaration="yes" indent="no"/> 

	<!-- exclude velden -->
	<xsl:variable name="excludeVelden" as="element()*">
		<excludes>
			<exclude></exclude>
		</excludes>
	</xsl:variable>

	<!-- exclude attributen -->
	<xsl:variable name="excludeAttributen" as="element()*">
		<excludes>
			<exclude>objectSleutel</exclude>
			<exclude>voorkomenSleutel</exclude>
			<exclude>communicatieID</exclude>
		</excludes>
	</xsl:variable>

	<!-- attributen welke gevuld/niet gevuld als resultaat moeten bevatten -->
	<xsl:variable name="controleAttributen" as="element()*">
		<controles>
			<controle>referentieID</controle>
		</controles>
	</xsl:variable>
	
	<!-- velden welke gevuld/niet gevuld als resultaat moeten bevatten -->
	<xsl:variable name="controleVelden" as="element()*">
		<controles>
			<controle>actieInhoud</controle>
			<controle>referentienummer</controle>
			<controle>tijdstipVerzending</controle>
			<controle>tijdstipLaatsteWijziging</controle>
			<controle>tijdstipLaatsteWijzigingGBASystematiek</controle>
			<controle>actieAanpassingGeldigheid</controle>
			<controle>actieVerval</controle>
			<controle>objectSleutelGegeven</controle>
			<controle>voorkomenSleutelGegeven</controle>
		</controles>
	</xsl:variable>

	<!-- (pad van twee) velden welke gevuld/niet gevuld als resultaat moeten bevatten -->
	<xsl:variable name="controleVeldenDubbel" as="element()*">
		<controles>
			<controle>administratieveHandeling.tijdstipRegistratie</controle>
			<controle>synchronisatie.tijdstipRegistratie</controle>
		</controles>
	</xsl:variable>

	<!-- voert de regels uit zoals opgesteld in BRP leveringen.xsl (enkel values) -->
	<xsl:template name="getElement">
		<xsl:param name="elementNaam" />
		<xsl:param name="element" />
		<xsl:choose>
			<xsl:when test="exsl:node-set($controleVelden)//controle/text()[contains($elementNaam, .)]">
				<xsl:choose>
					<xsl:when test="text()=''">
						<xsl:text>niet gevuld</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>gevuld</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="exsl:node-set($controleVeldenDubbel)//controle/text()[contains(replace(translate($element, '0123456789', ''), '.*?([^.]+\.[^.]+)$', '$1'), .)]">
				<xsl:choose>
					<xsl:when test="text()=''">
						<xsl:text>niet gevuld</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>gevuld</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="$elementNaam='tijdstipRegistratie' and not(contains(//synchronisatie/soortNaam/text(), 'GBA') or (//synchronisatie/soortNaam/text()='Plaatsing afnemerindicatie'))">
				<xsl:choose>
					<xsl:when test="text()=''">
						<xsl:text>niet gevuld</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>gevuld</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="$elementNaam='tijdstipVerval' and not(contains(//synchronisatie/soortNaam/text(), 'GBA') or (//synchronisatie/soortNaam/text()='Plaatsing afnemerindicatie'))">
				<xsl:choose>
					<xsl:when test="text()=''">
						<xsl:text>niet gevuld</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>gevuld</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="." />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="getAttribuut">
		<xsl:param name="elementNaam" />

		<xsl:choose>
			<xsl:when test="exsl:node-set($controleAttributen)//controle/text()[contains($elementNaam, .)]">
				<xsl:choose>
					<xsl:when test="text()=''">
						<xsl:text>niet gevuld</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>gevuld</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="." />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="/">
		<xsl:value-of select="utils:reset()" />
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="*" mode="all">
		<xsl:if test=".[not(*)] and text()">
			<xsl:variable name="elementNaam" select="name()" />
			<xsl:choose>
				<xsl:when test="not(exsl:node-set($excludeVelden)//exclude/text()[contains($elementNaam, .)])">
					<xsl:variable name="element">
						<xsl:for-each select="ancestor-or-self::*">
							<xsl:call-template name="print-step"/>
						</xsl:for-each>
					</xsl:variable>
					<xsl:element name="{$element}">
						<xsl:call-template name="getElement">
							<xsl:with-param name="elementNaam" select="$elementNaam" />
							<xsl:with-param name="element" select="$element" />
						</xsl:call-template>
					</xsl:element>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
		<xsl:call-template name="attributes" />

		<xsl:apply-templates select="*" mode="all" />
	</xsl:template> 

	<xsl:template match="*">
		<xsl:if test=".[not(*)]">
			<xsl:variable name="elementNaam" select="name()" />
			<xsl:choose>
				<xsl:when test="not(exsl:node-set($excludeVelden)//exclude/text()[contains($elementNaam, .)])">
					<xsl:variable name="element">
						<xsl:for-each select="ancestor-or-self::*">
							<xsl:call-template name="print-step"/>
						</xsl:for-each>
					</xsl:variable>
					<xsl:element name="{$element}">
						<xsl:call-template name="getElement">
							<xsl:with-param name="elementNaam" select="$elementNaam" />
							<xsl:with-param name="element" select="$element" />
						</xsl:call-template>
					</xsl:element>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
		<xsl:call-template name="attributes" />

		<xsl:apply-templates select="*" mode="child-only" />
	</xsl:template> 

	<xsl:template match="*" mode="child-only">
		<xsl:if test=".[not(*)]">
			<xsl:variable name="elementNaam" select="name()" />
			<xsl:choose>
				<xsl:when test="not(exsl:node-set($excludeVelden)//exclude/text()[contains($elementNaam, .)])">
					<xsl:variable name="element">
						<xsl:for-each select="ancestor-or-self::*">
							<xsl:call-template name="print-step"/>
						</xsl:for-each>
					</xsl:variable>
					<xsl:element name="{$element}">
						<xsl:call-template name="getElement">
							<xsl:with-param name="elementNaam" select="$elementNaam" />
							<xsl:with-param name="element" select="$element" />
						</xsl:call-template>
					</xsl:element>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<xsl:template name="attributes">
		<xsl:for-each select="@*">
			<xsl:variable name="elementNaam" select="name()" />
			<xsl:choose>
				<xsl:when test="not(exsl:node-set($excludeAttributen)//exclude/text()[contains($elementNaam, .)])">
					<xsl:variable name="element">
						<xsl:for-each select="ancestor-or-self::*">
							<xsl:call-template name="print-step"/>
						</xsl:for-each>
						<xsl:text>.</xsl:text>
						<xsl:value-of select="name()"/>
					</xsl:variable>
			
					<xsl:element name="{$element}">
						<xsl:call-template name="getAttribuut">
							<xsl:with-param name="elementNaam" select="$elementNaam" />
						</xsl:call-template>
					</xsl:element>
				</xsl:when>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="print-pad">
		<xsl:text>.</xsl:text>
		<xsl:value-of select="name()"/>
	</xsl:template>

	<xsl:template name="print-step">
		<xsl:variable name="elementNaam" select="name()" />
		<xsl:if test="position()>1">
			<xsl:text>.</xsl:text>
		</xsl:if>
		<xsl:value-of select="$elementNaam" />
		<xsl:variable name="positie">
			<xsl:number value="count(preceding-sibling::*[$elementNaam=name()]) + 1" />
		</xsl:variable>
		<xsl:variable name="elementPad">
			<xsl:for-each select="ancestor-or-self::*">
				<xsl:call-template name="print-pad" />
			</xsl:for-each>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="$elementNaam = 'actie'">
				<!-- actie uniek maken adhv objectsleutel match met actieInhoud -->
				<xsl:variable name="elementValue" select="@objectSleutel" />
				<xsl:variable name="key">
					<xsl:for-each select="//actieInhoud[text()=$elementValue]/../descendant-or-self::*[not(*)]">
						<xsl:variable name="element">
							<xsl:for-each select="ancestor-or-self::*">
								<xsl:call-template name="print-pad"/>
							</xsl:for-each>
						</xsl:variable>
						<xsl:call-template name="getElement">
							<xsl:with-param name="elementNaam" select="name()" />
							<xsl:with-param name="element" select="$element" />
						</xsl:call-template>
					</xsl:for-each>
				</xsl:variable>
				<xsl:value-of select="utils:getSortedHash(string($key), $positie, $elementPad)" />
			</xsl:when>
			<xsl:when test="$elementNaam = 'onderzoek' and *[./name() = 'onderzoek']">
				<xsl:variable name="key1" select="string-join(text(), '')" />
				<xsl:variable name="key2">
					<xsl:for-each select=".//voorkomenSleutelGegeven">
						<xsl:variable name="keyValue" select="text()" />
						<xsl:variable name="element">
							<xsl:for-each select="ancestor-or-self::*">
								<xsl:call-template name="print-pad"/>
							</xsl:for-each>
						</xsl:variable>
						<xsl:for-each select="//*[@voorkomenSleutel=$keyValue and not(*)]">
							<xsl:call-template name="getElement">
								<xsl:with-param name="elementNaam" select="name()" />
								<xsl:with-param name="element" select="$element" />
							</xsl:call-template>
						</xsl:for-each>
					</xsl:for-each>
				</xsl:variable>
				<xsl:value-of select="utils:getHash(string-join(($key1, $key2), ','), $positie, $elementPad)" />
			</xsl:when>
			<xsl:when test="$elementNaam = 'gegevenInOnderzoek'">
				<xsl:variable name="key1" select="elementNaam" />
				<xsl:variable name="key2">
					<xsl:for-each select=".//voorkomenSleutelGegeven">
						<xsl:variable name="keyValue" select="text()" />
						<xsl:for-each select="//*[@voorkomenSleutel=$keyValue and not(*)]">
							<xsl:variable name="element">
								<xsl:for-each select="ancestor-or-self::*">
									<xsl:call-template name="print-pad"/>
								</xsl:for-each>
							</xsl:variable>
							<xsl:call-template name="getElement">
								<xsl:with-param name="elementNaam" select="name()" />
								<xsl:with-param name="element" select="$element" />
							</xsl:call-template>
						</xsl:for-each>
					</xsl:for-each>
				</xsl:variable>
				<xsl:value-of select="utils:getHash(string-join(($key1, $key2), ','), $positie, $elementPad)" />
			</xsl:when>
			<xsl:when test="$elementNaam = name(following-sibling::*[1]) or $elementNaam = name(preceding-sibling::*[1])">
				<xsl:variable name="key">
					<xsl:for-each select=".//descendant-or-self::*[not(*)]">
						<xsl:variable name="element">
							<xsl:for-each select="ancestor-or-self::*">
								<xsl:call-template name="print-pad"/>
							</xsl:for-each>
						</xsl:variable>
						<xsl:call-template name="getElement">
							<xsl:with-param name="elementNaam" select="name()" />
							<xsl:with-param name="element" select="$element" />
						</xsl:call-template>
					</xsl:for-each>
				</xsl:variable>
				<xsl:value-of select="utils:getHash(string-join($key, ','), $positie, $elementPad)" />
			</xsl:when>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>
