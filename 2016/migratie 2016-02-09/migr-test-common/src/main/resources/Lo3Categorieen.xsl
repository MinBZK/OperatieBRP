<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- CATEGORIE 01: PERSOON -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud']" mode="header">
		<th>A-nummer</th>
		<th>BSN</th>
		<th>Voornamen</th>
		<th>Adellijke titel/Predikaat</th>
		<th>Voorvoegsel</th>
		<th>Naam</th>
		<th>Geboortedatum</th>
		<th>Gemeente</th>
		<th>Land</th>
		<th>Geslacht</th>
		<th>Aanduiding naamgebruik</th>
		<th>Vorig A-nummer</th>
		<th>Volgend A-nummer</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud']" mode="inhoud">
		<td><xsl:value-of select="aNummer/*/text()"/><xsl:apply-templates select="aNummer/onderzoek"/></td>
		<td><xsl:value-of select="burgerservicenummer/*/text()"/><xsl:apply-templates select="burgerservicenummer/onderzoek"/></td>
		<td><xsl:value-of select="voornamen/*/text()"/><xsl:apply-templates select="voornamen/onderzoek"/></td>
		<td><xsl:value-of select="adellijkeTitelPredikaatCode/*/text()"/><xsl:apply-templates select="adellijkeTitelPredikaatCode/onderzoek"/></td>
		<td><xsl:value-of select="voorvoegselGeslachtsnaam/*/text()"/><xsl:apply-templates select="voorvoegselGeslachtsnaam/onderzoek"/></td>
		<td><xsl:value-of select="geslachtsnaam/*/text()"/><xsl:apply-templates select="geslachtsnaam/onderzoek"/></td>
		<td><xsl:value-of select="geboortedatum/*/text()"/><xsl:apply-templates select="geboortedatum/onderzoek"/></td>
		<td><xsl:value-of select="geboorteGemeenteCode/*/text()"/><xsl:apply-templates select="geboorteGemeenteCode/onderzoek"/></td>
		<td><xsl:value-of select="geboorteLandCode/*/text()"/><xsl:apply-templates select="geboorteLandCode/onderzoek"/></td>
		<td><xsl:value-of select="geslachtsaanduiding/*/text()"/><xsl:apply-templates select="geslachtsaanduiding/onderzoek"/></td>
		<td><xsl:value-of select="aanduidingNaamgebruikCode/*/text()"/><xsl:apply-templates select="aanduidingNaamgebruikCode/onderzoek"/></td>
		<td><xsl:value-of select="vorigANummer/*/text()"/><xsl:apply-templates select="vorigANummer/onderzoek"/></td>
		<td><xsl:value-of select="volgendANummer/*/text()"/><xsl:apply-templates select="volgendANummer/onderzoek"/></td>
	</xsl:template>
	
	<!-- CATEGORIE 02/03: OUDER -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud']" mode="header">
		<th>A-nummer</th>
		<th>BSN</th>
		<th>Voornamen</th>
		<th>Adellijke titel/Predikaat</th>
		<th>Voorvoegsel</th>
		<th>Naam</th>
		<th>Geboortedatum</th>
		<th>Gemeente</th>
		<th>Land</th>
		<th>Geslacht</th>
		<th>fam.recht.betr.</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud']" mode="inhoud">
		<td><xsl:value-of select="aNummer/*/text()"/><xsl:apply-templates select="aNummer/onderzoek"/></td>
		<td><xsl:value-of select="burgerservicenummer/*/text()"/><xsl:apply-templates select="burgerservicenummer/onderzoek"/></td>
		<td><xsl:value-of select="voornamen/*/text()"/><xsl:apply-templates select="voornamen/onderzoek"/></td>
		<td><xsl:value-of select="adellijkeTitelPredikaatCode/*/text()"/><xsl:apply-templates select="adellijkeTitelPredikaatCode/onderzoek"/></td>
		<td><xsl:value-of select="voorvoegselGeslachtsnaam/*/text()"/><xsl:apply-templates select="voorvoegselGeslachtsnaam/onderzoek"/></td>
		<td><xsl:value-of select="geslachtsnaam/*/text()"/><xsl:apply-templates select="geslachtsnaam/onderzoek"/></td>
		<td><xsl:value-of select="geboortedatum/*/text()"/><xsl:apply-templates select="geboortedatum/onderzoek"/></td>
		<td><xsl:value-of select="geboorteGemeenteCode/*/text()"/><xsl:apply-templates select="geboorteGemeenteCode/onderzoek"/></td>
		<td><xsl:value-of select="geboorteLandCode/*/text()"/><xsl:apply-templates select="geboorteLandCode/onderzoek"/></td>
		<td><xsl:value-of select="geslachtsaanduiding/*/text()"/><xsl:apply-templates select="geslachtsaanduiding/onderzoek"/></td>
		<td><xsl:value-of select="familierechtelijkeBetrekking/*/text()"/><xsl:apply-templates select="familierechtelijkeBetrekking/onderzoek"/></td>
	</xsl:template>
	
	<!--  CATEGORIE 04: NATIONALITEIT -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud']" mode="header">
		<th>Nationaliteit</th>
		<th>Reden verkrijging NL</th>
		<th>Reden verlies NL</th>
		<th>Aanduidig bijz. NLschap</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud']" mode="inhoud">
		<td><xsl:value-of select="nationaliteitCode/*/text()"/><xsl:apply-templates select="nationaliteitCode/onderzoek"/></td>
		<td><xsl:value-of select="redenVerkrijgingNederlandschapCode/*/text()"/><xsl:apply-templates select="redenVerkrijgingNederlandschapCode/onderzoek"/></td>
		<td><xsl:value-of select="redenVerliesNederlandschapCode/*/text()"/><xsl:apply-templates select="redenVerliesNederlandschapCode/onderzoek"/></td>
		<td><xsl:value-of select="aanduidingBijzonderNederlandschap/*/text()"/><xsl:apply-templates select="aanduidingBijzonderNederlandschap/onderzoek"/></td>
	</xsl:template>

	<!-- CATEGORIE 05: HUWELIJk -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud']" mode="header">
		<th>01.10 A-nummer</th>
		<th>01.20 Burgerservicenummer</th>
		<th>02.10 Voornamen</th>
		<th>02.20 Adellijke titel/predikaat</th>
		<th>02.30 Voorvoegsel geslachtsnaam</th>
		<th>02.40 Geslachtsnaam</th>
		<th>03.10 Geboortedatum</th>
		<th>03.20 Geboorteplaats</th>
		<th>03.30 Geboorteland</th>
		<th>04.10 Geslachtsaanduiding</th>
		<th>06.10 Datum huwelijkssluiting/aangaan geregistreerd partnerschap</th>
		<th>06.20 Plaats huwelijkssluiting/aangaan geregistreerd partnerschap</th>
		<th>06.30 Land huwelijkssluiting/aangaan geregistreerd partnerschap</th>
		<th>07.10 Datum ontbinding huwelijk/geregistreerd partnerschap</th>
		<th>07.20 Plaats ontbinding huwelijk/geregistreerd partnerschap</th>
		<th>07.30 Land ontbinding huwelijk/geregistreerd partnerschap</th>
		<th>07.40 Reden ontbinding huwelijk/geregistreerd partnerschap</th>
		<th>15.10 Soort verbintenis</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud']" mode="inhoud">
        <td><xsl:value-of select="aNummer/*/text()"/><xsl:apply-templates select="aNummer/onderzoek"/></td>
        <td><xsl:value-of select="burgerservicenummer/*/text()"/><xsl:apply-templates select="burgerservicenummer/onderzoek"/></td>
        <td><xsl:value-of select="voornamen/*/text()"/><xsl:apply-templates select="voornamen/onderzoek"/></td>
        <td><xsl:value-of select="adellijkeTitelPredikaatCode/*/text()"/><xsl:apply-templates select="adellijkeTitelPredikaatCode/onderzoek"/></td>
        <td><xsl:value-of select="voorvoegselGeslachtsnaam/*/text()"/><xsl:apply-templates select="voorvoegselGeslachtsnaam/onderzoek"/></td>
        <td><xsl:value-of select="geslachtsnaam/*/text()"/><xsl:apply-templates select="geslachtsnaam/onderzoek"/></td>
        <td><xsl:value-of select="geboortedatum/*/text()"/><xsl:apply-templates select="geboortedatum/onderzoek"/></td>
        <td><xsl:value-of select="geboorteGemeenteCode/*/text()"/><xsl:apply-templates select="geboorteGemeenteCode/onderzoek"/></td>
        <td><xsl:value-of select="geboorteLandCode/*/text()"/><xsl:apply-templates select="geboorteLandCode/onderzoek"/></td>
        <td><xsl:value-of select="geslachtsaanduiding/*/text()"/><xsl:apply-templates select="geslachtsaanduiding/onderzoek"/></td>
        <td><xsl:value-of select="datumSluitingHuwelijkOfAangaanGp/*/text()"/><xsl:apply-templates select="datumSluitingHuwelijkOfAangaanGp/onderzoek"/></td>
        <td><xsl:value-of select="gemeenteCodeSluitingHuwelijkOfAangaanGp/*/text()"/><xsl:apply-templates select="gemeenteCodeSluitingHuwelijkOfAangaanGp/onderzoek"/></td>
        <td><xsl:value-of select="landCodeSluitingHuwelijkOfAangaanGp/*/text()"/><xsl:apply-templates select="landCodeSluitingHuwelijkOfAangaanGp/onderzoek"/></td>
        <td><xsl:value-of select="datumOntbindingHuwelijkOfGp/*/text()"/><xsl:apply-templates select="datumOntbindingHuwelijkOfGp/onderzoek"/></td>
        <td><xsl:value-of select="gemeenteCodeOntbindingHuwelijkOfGp/*/text()"/><xsl:apply-templates select="gemeenteCodeOntbindingHuwelijkOfGp/onderzoek"/></td>
        <td><xsl:value-of select="landCodeOntbindingHuwelijkOfGp/*/text()"/><xsl:apply-templates select="landCodeOntbindingHuwelijkOfGp/onderzoek"/></td>
        <td><xsl:value-of select="redenOntbindingHuwelijkOfGpCode/*/text()"/><xsl:apply-templates select="redenOntbindingHuwelijkOfGpCode/onderzoek"/></td>
        <td><xsl:value-of select="soortVerbintenis/*/text()"/><xsl:apply-templates select="soortVerbintenis/onderzoek"/></td>
	</xsl:template>
	
	<!-- CATEGORIE 06: OVERLIJDEN -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud']" mode="header">
		<th>Datum</th>
		<th>Gemeente</th>
		<th>Land</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud']" mode="inhoud">
		<td><xsl:value-of select="datum/*/text()"/><xsl:apply-templates select="datum/onderzoek"/></td>
		<td><xsl:value-of select="gemeenteCode/*/text()"/><xsl:apply-templates select="gemeenteCode/onderzoek"/></td>
		<td><xsl:value-of select="landCode/*/text()"/><xsl:apply-templates select="landCode/onderzoek"/></td>
	</xsl:template>
	
	<!-- CATEGORIE 07: INSCHRJIVING -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud']" mode="header">
		<th>Datum ingang blokkering</th>
		<th>Datum opschorting bijhouding</th>
		<th>Reden opschorting bijhouding</th>
		<th>Datum eerste inschrijving</th>
		<th>Gemeente PK</th>
		<th>Indicatie geheim</th>
        <th>Datum verificatie</th>
        <th>Omschrijving verificatie</th>
		<th>Versienummer</th>
		<th>Datumtijdstempel</th>
		<th>Indicatie PK volledig geconverteerd</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud']" mode="inhoud">
		<td><xsl:value-of select="datumIngangBlokkering/*/text()"/><xsl:apply-templates select="datumIngangBlokkering/onderzoek"/></td>
		<td><xsl:value-of select="datumOpschortingBijhouding/*/text()"/><xsl:apply-templates select="datumOpschortingBijhouding/onderzoek"/></td>
		<td><xsl:value-of select="redenOpschortingBijhoudingCode/*/text()"/><xsl:apply-templates select="redenOpschortingBijhoudingCode/onderzoek"/></td>
		<td><xsl:value-of select="datumEersteInschrijving/*/text()"/><xsl:apply-templates select="datumEersteInschrijving/onderzoek"/></td>
		<td><xsl:value-of select="gemeentePKCode/*/text()"/><xsl:apply-templates select="gemeentePKCode/onderzoek"/></td>
		<td><xsl:value-of select="indicatieGeheimCode/*/text()"/><xsl:apply-templates select="indicatieGeheimCode/onderzoek"/></td>
        <td><xsl:value-of select="datumVerificatie/*/text()"/><xsl:apply-templates select="datumVerificatie/onderzoek"/></td>
        <td><xsl:value-of select="omschrijvingVerificatie/*/text()"/><xsl:apply-templates select="omschrijvingVerificatie/onderzoek"/></td>
		<td><xsl:value-of select="versienummer/*/text()"/><xsl:apply-templates select="versienummer/onderzoek"/></td>
		<td><xsl:value-of select="datumtijdstempel/*/text()"/><xsl:apply-templates select="datumtijdstempel/onderzoek"/></td>
		<td><xsl:value-of select="indicatiePKVolledigGeconverteerdCode/*/text()"/><xsl:apply-templates select="indicatiePKVolledigGeconverteerdCode/onderzoek"/></td>
	</xsl:template>

	<!-- CATEGORIE 08: VERBLIJFPLAATS -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud']" mode="header">
		<th>gemeenteInschrijving</th>
		<th>datumInschrijving</th>
		<th>functieAdres</th>
		<th>gemeenteDeel</th>
		<th>aanvangAdreshouding</th>
		<th>straatnaam</th>
		<th>naamOpenbareRuimte</th>
		<th>huisnummer</th>
		<th>huisletter</th>
		<th>huisnummertoevoeging</th>
		<th>aanduidingHuisnummer</th>
		<th>postcode</th>
		<th>woonplaatsnaam</th>
		<th>identificatiecodeVerblijfplaats</th>
		<th>identificatiecodeNummeraanduiding</th>
		<th>locatieBeschrijving</th>
		<th>landAdresBuitenland</th>
		<th>vertrekUitNederland</th>
		<th>adresBuitenland1</th>
		<th>adresBuitenland2</th>
		<th>adresBuitenland3</th>
		<th>landVanwaarIngeschreven</th>
		<th>vestigingInNederland</th>
		<th>aangifteAdreshouding</th>
		<th>indicatieDocument</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud']" mode="inhoud">
		<td><xsl:value-of select="gemeenteInschrijving/*/text()"/><xsl:apply-templates select="gemeenteInschrijving/onderzoek"/></td>
		<td><xsl:value-of select="datumInschrijving/*/text()"/><xsl:apply-templates select="datumInschrijving/onderzoek"/></td>
		<td><xsl:value-of select="functieAdres/*/text()"/><xsl:apply-templates select="functieAdres/onderzoek"/></td>
		<td><xsl:value-of select="gemeenteDeel/*/text()"/><xsl:apply-templates select="gemeenteDeel/onderzoek"/></td>
		<td><xsl:value-of select="aanvangAdreshouding/*/text()"/><xsl:apply-templates select="aanvangAdreshouding/onderzoek"/></td>
		<td><xsl:value-of select="straatnaam/*/text()"/><xsl:apply-templates select="straatnaam/onderzoek"/></td>
		<td><xsl:value-of select="naamOpenbareRuimte/*/text()"/><xsl:apply-templates select="naamOpenbareRuimte/onderzoek"/></td>
		<td><xsl:value-of select="huisnummer/*/text()"/><xsl:apply-templates select="huisnummer/onderzoek"/></td>
		<td><xsl:value-of select="huisletter/*/text()"/><xsl:apply-templates select="huisletter/onderzoek"/></td>
		<td><xsl:value-of select="huisnummertoevoeging/*/text()"/><xsl:apply-templates select="huisnummertoevoeging/onderzoek"/></td>
		<td><xsl:value-of select="aanduidingHuisnummer/*/text()"/><xsl:apply-templates select="aanduidingHuisnummer/onderzoek"/></td>
		<td><xsl:value-of select="postcode/*/text()"/><xsl:apply-templates select="postcode/onderzoek"/></td>
		<td><xsl:value-of select="woonplaatsnaam/*/text()"/><xsl:apply-templates select="woonplaatsnaam/onderzoek"/></td>
		<td><xsl:value-of select="identificatiecodeVerblijfplaats/*/text()"/><xsl:apply-templates select="identificatiecodeVerblijfplaats/onderzoek"/></td>
		<td><xsl:value-of select="identificatiecodeNummeraanduiding/*/text()"/><xsl:apply-templates select="identificatiecodeNummeraanduiding/onderzoek"/></td>
		<td><xsl:value-of select="locatieBeschrijving/*/text()"/><xsl:apply-templates select="locatieBeschrijving/onderzoek"/></td>
		<td><xsl:value-of select="landAdresBuitenland/*/text()"/><xsl:apply-templates select="landAdresBuitenland/onderzoek"/></td>
		<td><xsl:value-of select="vertrekUitNederland/*/text()"/><xsl:apply-templates select="vertrekUitNederland/onderzoek"/></td>
		<td><xsl:value-of select="adresBuitenland1/*/text()"/><xsl:apply-templates select="adresBuitenland1/onderzoek"/></td>
		<td><xsl:value-of select="adresBuitenland2/*/text()"/><xsl:apply-templates select="adresBuitenland2/onderzoek"/></td>
		<td><xsl:value-of select="adresBuitenland3/*/text()"/><xsl:apply-templates select="adresBuitenland3/onderzoek"/></td>
		<td><xsl:value-of select="landVanwaarIngeschreven/*/text()"/><xsl:apply-templates select="landVanwaarIngeschreven/onderzoek"/></td>
		<td><xsl:value-of select="vestigingInNederland/*/text()"/><xsl:apply-templates select="vestigingInNederland/onderzoek"/></td>
		<td><xsl:value-of select="aangifteAdreshouding/*/text()"/><xsl:apply-templates select="aangifteAdreshouding/onderzoek"/></td>
		<td><xsl:value-of select="indicatieDocument/*/text()"/><xsl:apply-templates select="indicatieDocument/onderzoek"/></td>
	</xsl:template>
	
	<!-- CATEGORIE 09: KIND -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud']" mode="header">
		<th>A-nummer</th>
		<th>BSN</th>
		<th>Voornamen</th>
		<th>Adellijke titel/Predikaat</th>
		<th>Voorvoegsel</th>
		<th>Naam</th>
		<th>Geboortedatum</th>
		<th>Gemeente</th>
		<th>Land</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud']" mode="inhoud">
		<td><xsl:value-of select="aNummer/*/text()"/><xsl:apply-templates select="aNummer/onderzoek"/></td>
		<td><xsl:value-of select="burgerservicenummer/*/text()"/><xsl:apply-templates select="burgerservicenummer/onderzoek"/></td>
		<td><xsl:value-of select="voornamen/*/text()"/><xsl:apply-templates select="voornamen/onderzoek"/></td>
		<td><xsl:value-of select="adellijkeTitelPredikaatCode/*/text()"/><xsl:apply-templates select="adellijkeTitelPredikaatCode/onderzoek"/></td>
		<td><xsl:value-of select="voorvoegselGeslachtsnaam/*/text()"/><xsl:apply-templates select="voorvoegselGeslachtsnaam/onderzoek"/></td>
		<td><xsl:value-of select="geslachtsnaam/*/text()"/><xsl:apply-templates select="geslachtsnaam/onderzoek"/></td>
		<td><xsl:value-of select="geboortedatum/*/text()"/><xsl:apply-templates select="geboortedatum/onderzoek"/></td>
		<td><xsl:value-of select="geboorteGemeenteCode/*/text()"/><xsl:apply-templates select="geboorteGemeenteCode/onderzoek"/></td>
		<td><xsl:value-of select="geboorteLandCode/*/text()"/><xsl:apply-templates select="geboorteLandCode/onderzoek"/></td>
	</xsl:template>

	<!-- CATEGORIE 10: VERBLIJFSTITEL -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud']" mode="header">
		<th>Aanduiding verblijfstitel</th>
		<th>Datum einde verblijfstitel</th>
		<th>Ingangsdatum verblijfstitel</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud']" mode="inhoud">
		<td><xsl:value-of select="aanduidingVerblijfstitelCode/*/text()"/><xsl:apply-templates select="aanduidingVerblijfstitelCode/onderzoek"/></td>
		<td><xsl:value-of select="datumEindeVerblijfstitel/*/text()"/><xsl:apply-templates select="datumEindeVerblijfstitel/onderzoek"/></td>
		<td><xsl:value-of select="datumAanvangVerblijfstitel/*/text()"/><xsl:apply-templates select="datumAanvangVerblijfstitel/onderzoek"/></td>
	</xsl:template>

	<!-- CATEGORIE 11: GEZAGSVERHOUDING -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud']" mode="header">
		<th>Indicatie gezag minderjarige</th>
		<th>Indicatie curatele register</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud']" mode="inhoud">
		<td><xsl:value-of select="indicatieGezagMinderjarige/*/text()"/><xsl:apply-templates select="indicatieGezagMinderjarige/onderzoek"/></td>
		<td><xsl:value-of select="indicatieCurateleregister/*/text()"/><xsl:apply-templates select="indicatieCurateleregister/onderzoek"/></td>
	</xsl:template>

	<!-- CATEGORIE 12: REISDOCUMENT -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud']" mode="header">
		<th>Soort</th>
		<th>Nummer</th>
		<th>Datum uitgifte</th>
		<th>Autoriteit</th>
		<th>Einde geldigheid</th>
		<th>Datum inhouding/vermissing</th>
		<th>Aanduiding inhouding/vermissing</th>
		<th>lengte houder</th>
		<th>Signalering</th>
		<th>Buitenlands reisdocument</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud']" mode="inhoud">
		<td><xsl:value-of select="soortNederlandsReisdocument/*/text()"/><xsl:apply-templates select="soortNederlandsReisdocument/onderzoek"/></td>
		<td><xsl:value-of select="nummerNederlandsReisdocument/*/text()"/><xsl:apply-templates select="nummerNederlandsReisdocument/onderzoek"/></td>
		<td><xsl:value-of select="datumUitgifteNederlandsReisdocument/*/text()"/><xsl:apply-templates select="datumUitgifteNederlandsReisdocument/onderzoek"/></td>
		<td><xsl:value-of select="autoriteitVanAfgifteNederlandsReisdocument/*/text()"/><xsl:apply-templates select="autoriteitVanAfgifteNederlandsReisdocument/onderzoek"/></td>
		<td><xsl:value-of select="datumEindeGeldigheidNederlandsReisdocument/*/text()"/><xsl:apply-templates select="datumEindeGeldigheidNederlandsReisdocument/onderzoek"/></td>
		<td><xsl:value-of select="datumInhoudingVermissingNederlandsReisdocument/*/text()"/><xsl:apply-templates select="datumInhoudingVermissingNederlandsReisdocument/onderzoek"/></td>
		<td><xsl:value-of select="aanduidingInhoudingVermissingNederlandsReisdocument/*/text()"/><xsl:apply-templates select="aanduidingInhoudingVermissingNederlandsReisdocument/onderzoek"/></td>
		<td><xsl:value-of select="lengteHouder/*/text()"/><xsl:apply-templates select="lengteHouder/onderzoek"/></td>
		<td><xsl:value-of select="signalering/*/text()"/><xsl:apply-templates select="signalering/onderzoek"/></td>
		<td><xsl:value-of select="aanduidingBezitBuitenlandsReisdocument/*/text()"/><xsl:apply-templates select="aanduidingBezitBuitenlandsReisdocument/onderzoek"/></td>
	</xsl:template>
	
	<!-- CATEGORIE 13: KIESRECHT -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud']" mode="header">
		<th>Aanduiding Europees kiesrecht</th>
		<th>Datum Europees kiesrecht</th>
		<th>Einddatum uitsluiting Europees kiesrecht</th>
		<th>Aanduiding uitgesloten kiesrecht</th>
		<th>Einddatum uitsluiting kiesrecht</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud']" mode="inhoud">
		<td><xsl:value-of select="aanduidingEuropeesKiesrecht/*/text()"/><xsl:apply-templates select="aanduidingEuropeesKiesrecht/onderzoek"/></td>
		<td><xsl:value-of select="datumEuropeesKiesrecht/*/text()"/><xsl:apply-templates select="datumEuropeesKiesrecht/onderzoek"/></td>
		<td><xsl:value-of select="einddatumUitsluitingEuropeesKiesrecht/*/text()"/><xsl:apply-templates select="einddatumUitsluitingEuropeesKiesrecht/onderzoek"/></td>
		<td><xsl:value-of select="aanduidingUitgeslotenKiesrecht/*/text()"/><xsl:apply-templates select="aanduidingUitgeslotenKiesrecht/onderzoek"/></td>
		<td><xsl:value-of select="einddatumUitsluitingKiesrecht/*/text()"/><xsl:apply-templates select="einddatumUitsluitingKiesrecht/onderzoek"/></td>
	</xsl:template>
		
	<!-- CATEGORIE 14: AFNEMERSINDICATIE -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud']" mode="header">
		<th>Afnemersindicatie</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud']" mode="inhoud">
		<td><xsl:value-of select="afnemersindicatie"/></td>
	</xsl:template>

	<!-- CATEGORIE 35: AUTORISATIE -->
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud']" mode="header">
		<th>Afnemersindicatie</th>
		<th>Afnemernaam</th>
        <th>Datum ingang</th>
        <th>Datum einde</th>
		<th>Aantekening</th>
		<th>IndicatieGeheimhouding</th>
		<th>Verstrekkingsbeperking</th>
		<th>Straatnaam</th>
		<th>Huisnummer</th>
		<th>Huisletter</th>
		<th>Huisnummer toev.</th>
		<th>Postcode</th>
		<th>Gemeente</th>
		<th>Rubriek spontaan</th>
		<th>Voorwaarde spontaan</th>
		<th>Sleutel rubrieken</th>
		<th>Cond. verstr?</th>
		<th>Medium spontaan</th>
		<th>Rubriek selectie</th>
		<th>Voorwaarde selectie</th>
		<th>Selectie soort</th>
		<th>Berichtaanduiding</th>
		<th>Eerste sel. datum</th>
		<th>Selectie periode</th>
		<th>Medium selectie</th>
		<th>Rubriek adhoc</th>
		<th>Voorwaarde adhoc</th>
		<th>Plaatsingbevoegdheid</th>
		<th>Afnemersverstrekking</th>
		<th>Adresvraag bevoegd</th>
		<th>Medium adhoc</th>
		<th>Rubriek adres</th>
		<th>Voorwaarde adres</th>
		<th>Medium adres</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud']" mode="inhoud">
		<td><xsl:value-of select="afnemersindicatie"/></td>
		<td><xsl:value-of select="afnemernaam"/></td>
        <td><xsl:value-of select="datumIngang"/></td>
        <td><xsl:value-of select="datumEinde"/></td>
		<td><xsl:value-of select="aantekening"/></td>
		<td><xsl:value-of select="indicatieGeheimhouding"/></td>
		<td><xsl:value-of select="verstrekkingsbeperking"/></td>
		<td><xsl:value-of select="straatnaam"/></td>
		<td><xsl:value-of select="huisnummer"/></td>
		<td><xsl:value-of select="huisletter"/></td>
		<td><xsl:value-of select="huisnummertoevoeging"/></td>
		<td><xsl:value-of select="postcode"/></td>
		<td><xsl:value-of select="gemeente"/></td>
		<td><xsl:value-of select="rubrieknummerSpontaan"/></td>
		<td><xsl:value-of select="voorwaarderegelSpontaan"/></td>
		<td><xsl:value-of select="sleutelrubriek"/></td>
		<td><xsl:value-of select="conditioneleVerstrekking"/></td>
		<td><xsl:value-of select="mediumSpontaan"/></td>
		<td><xsl:value-of select="rubrieknummerSelectie"/></td>
		<td><xsl:value-of select="voorwaarderegelSelectie"/></td>
		<td><xsl:value-of select="selectiesoort"/></td>
		<td><xsl:value-of select="berichtaanduiding"/></td>
		<td><xsl:value-of select="eersteSelectiedatum"/></td>
		<td><xsl:value-of select="selectieperiode"/></td>
		<td><xsl:value-of select="mediumSelectie"/></td>
		<td><xsl:value-of select="rubrieknummerAdHoc"/></td>
		<td><xsl:value-of select="voorwaarderegelAdHoc"/></td>
		<td><xsl:value-of select="plaatsingsbevoegdheidPersoonslijst"/></td>
		<td><xsl:value-of select="afnemersverstrekking"/></td>
		<td><xsl:value-of select="adresvraagBevoegdheid"/></td>
		<td><xsl:value-of select="mediumAdHoc"/></td>
		<td><xsl:value-of select="rubrieknummerAdresgeorienteerd"/></td>
		<td><xsl:value-of select="voorwaarderegelAdresgeorienteerd"/></td>
		<td><xsl:value-of select="mediumAdresgeorienteerd"/></td>
	</xsl:template>

	
	<!-- DOCUMENTATIE -->
    <xsl:template name="lo3DocumentatieHeader">
		<th>Gemeente akte</th>
		<th>Nummer akte</th>
		<th>Gemeente document</th>
		<th>Datum document</th>
		<th>Beschrijving document</th>
        <th>RNI deelnemer</th>
        <th>Omschrijving verdrag</th>
	</xsl:template>
	
	<xsl:template name="lo3Documentatie">
		<td><xsl:value-of select="documentatie/gemeenteAkte/*/text()"/><xsl:apply-templates select="documentatie/gemeenteAkte/onderzoek"/></td>
		<td><xsl:value-of select="documentatie/nummerAkte/*/text()"/><xsl:apply-templates select="documentatie/nummerAkte/onderzoek"/></td>
		<td><xsl:value-of select="documentatie/gemeenteDocument/*/text()"/><xsl:apply-templates select="documentatie/gemeenteDocument/onderzoek"/></td>
		<td><xsl:value-of select="documentatie/datumDocument/*/text()"/><xsl:apply-templates select="documentatie/datumDocument/onderzoek"/></td>
		<td><xsl:value-of select="documentatie/beschrijvingDocument/*/text()"/><xsl:apply-templates select="documentatie/beschrijvingDocument/onderzoek"/></td>
        <td><xsl:value-of select="documentatie/rniDeelnemerCode/*/text()"/><xsl:apply-templates select="documentatie/rniDeelnemerCode/onderzoek"/></td>
        <td><xsl:value-of select="documentatie/omschrijvingVerdrag/*/text()"/><xsl:apply-templates select="documentatie/omschrijvingVerdrag/onderzoek"/></td>
	</xsl:template>

	<!-- HISTORIE -->
    <xsl:template name="lo3HistorieHeader">
		<th>Aanvang geldigheid</th>
		<th>Opneming</th>
		<th>Ongeldig</th>
	</xsl:template>
	
	<xsl:template name="lo3Historie">
		<td><xsl:value-of select="historie/ingangsdatumGeldigheid/*/text()"/><xsl:apply-templates select="historie/ingangsdatumGeldigheid/onderzoek"/></td>
		<td><xsl:value-of select="historie/datumVanOpneming/*/text()"/><xsl:apply-templates select="historie/datumVanOpneming/onderzoek"/></td>
		<td><xsl:value-of select="historie/indicatieOnjuist/*/text()"/><xsl:apply-templates select="historie/indicatieOnjuist/onderzoek"/></td>
	</xsl:template>

    <xsl:template match="onderzoek">
        <br/>Onderzoek:<br/>
        <xsl:value-of select="aanduidingGegevensInOnderzoek/waarde/text()"/>:<xsl:value-of select="datumIngangOnderzoek/datum/text()"/>-<xsl:value-of select="datumEindeOnderzoek/datum/text()"/>
    </xsl:template>

</xsl:stylesheet>
