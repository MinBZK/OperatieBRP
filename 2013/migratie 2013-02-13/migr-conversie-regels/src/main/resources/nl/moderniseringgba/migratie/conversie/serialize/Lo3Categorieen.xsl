<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- CATEGORIE 01: PERSOON -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud']" mode="header">
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
	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud']" mode="inhoud">
		<td><xsl:value-of select="aNummer"/></td>
		<td><xsl:value-of select="burgerservicenummer"/></td>
		<td><xsl:value-of select="voornamen"/></td>
		<td><xsl:value-of select="adellijkeTitelPredikaatCode"/></td>
		<td><xsl:value-of select="voorvoegselGeslachtsnaam"/></td>
		<td><xsl:value-of select="geslachtsnaam"/></td>
		<td><xsl:value-of select="geboortedatum"/></td>
		<td><xsl:value-of select="geboorteGemeenteCode"/></td>
		<td><xsl:value-of select="geboorteLandCode"/></td>
		<td><xsl:value-of select="geslachtsaanduiding"/></td>
		<td><xsl:value-of select="aanduidingNaamgebruikCode"/></td>
		<td><xsl:value-of select="vorigANummer"/></td>
		<td><xsl:value-of select="volgendANummer"/></td>
	</xsl:template>
	
	<!-- CATEGORIE 02/03: OUDER -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud']" mode="header">
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
	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud']" mode="inhoud">
		<td><xsl:value-of select="aNummer"/></td>
		<td><xsl:value-of select="burgerservicenummer"/></td>
		<td><xsl:value-of select="voornamen"/></td>
		<td><xsl:value-of select="adellijkeTitelPredikaatCode"/></td>
		<td><xsl:value-of select="voorvoegselGeslachtsnaam"/></td>
		<td><xsl:value-of select="geslachtsnaam"/></td>
		<td><xsl:value-of select="geboortedatum"/></td>
		<td><xsl:value-of select="gemeenteCode"/></td>
		<td><xsl:value-of select="landCode"/></td>
		<td><xsl:value-of select="geslachtsaanduiding"/></td>
		<td><xsl:value-of select="familierechtelijkeBetrekking"/></td>
	</xsl:template>
	
	<!--  CATEGORIE 04: NATIONALITEIT -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud']" mode="header">
		<th>Nationaliteit</th>
		<th>Reden verkrijging NL</th>
		<th>Reden verlies NL</th>
		<th>Aanduidig bijz. NLschap</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud']" mode="inhoud">
		<td><xsl:value-of select="nationaliteitCode"/></td>
		<td><xsl:value-of select="redenVerkrijgingNederlandschapCode"/></td>
		<td><xsl:value-of select="redenVerliesNederlandschapCode"/></td>
		<td><xsl:value-of select="aanduidingBijzonderNederlandschap"/></td>
	</xsl:template>

	<!-- CATEGORIE 05: HUWELIJk -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud']" mode="header">
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
	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud']" mode="inhoud">
        <td><xsl:value-of select="aNummer"/></td>
        <td><xsl:value-of select="burgerservicenummer"/></td>
        <td><xsl:value-of select="voornamen"/></td>
        <td><xsl:value-of select="adellijkeTitelPredikaatCode"/></td>
        <td><xsl:value-of select="voorvoegselGeslachtsnaam"/></td>
        <td><xsl:value-of select="geslachtsnaam"/></td>
        <td><xsl:value-of select="geboortedatum"/></td>
        <td><xsl:value-of select="geboorteGemeenteCode"/></td>
        <td><xsl:value-of select="geboorteLandCode"/></td>
        <td><xsl:value-of select="geslachtsaanduiding"/></td>
        <td><xsl:value-of select="datumSluitingHuwelijkOfAangaanGp"/></td>
        <td><xsl:value-of select="gemeenteCodeSluitingHuwelijkOfAangaanGp"/></td>
        <td><xsl:value-of select="landCodeSluitingHuwelijkOfAangaanGp"/></td>
        <td><xsl:value-of select="datumOntbindingHuwelijkOfGp"/></td>
        <td><xsl:value-of select="gemeenteCodeOntbindingHuwelijkOfGp"/></td>
        <td><xsl:value-of select="landCodeOntbindingHuwelijkOfGp"/></td>
        <td><xsl:value-of select="redenOntbindingHuwelijkOfGpCode"/></td>
        <td><xsl:value-of select="soortVerbintenis"/></td>
	</xsl:template>
	
	<!-- CATEGORIE 06: OVERLIJDEN -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OverlijdenInhoud']" mode="header">
		<th>Datum</th>
		<th>Gemeente</th>
		<th>Land</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OverlijdenInhoud']" mode="inhoud">
		<td><xsl:value-of select="datum"/></td>
		<td><xsl:value-of select="gemeenteCode"/></td>
		<td><xsl:value-of select="landCode"/></td>
	</xsl:template>
	
	<!-- CATEGORIE 07: INSCHRJIVING -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud']" mode="header">
		<th>Datum ingang blokkering</th>
		<th>Datum opschorting bijhouding</th>
		<th>Reden opschorting bijhouding</th>
		<th>Datum eerste inschrijving</th>
		<th>Gemeente PK</th>
		<th>Indicatie geheim</th>
		<th>Versienummer</th>
		<th>Datumtijdstempel</th>
		<th>Indicatie PK volledig geconverteerd</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud']" mode="inhoud">
		<td><xsl:value-of select="datumIngangBlokkering"/></td>
		<td><xsl:value-of select="datumOpschortingBijhouding"/></td>
		<td><xsl:value-of select="redenOpschortingBijhoudingCode"/></td>
		<td><xsl:value-of select="datumEersteInschrijving"/></td>
		<td><xsl:value-of select="gemeentePKCode"/></td>
		<td><xsl:value-of select="indicatieGeheimCode"/></td>
		<td><xsl:value-of select="versienummer"/></td>
		<td><xsl:value-of select="datumtijdstempel"/></td>
		<td><xsl:value-of select="indicatiePKVolledigGeconverteerdCode"/></td>
	</xsl:template>

	<!-- CATEGORIE 08: VERBLIJFPLAATS -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud']" mode="header">
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
		<th>landWaarnaarVertrokken</th>
		<th>vertrekUitNederland</th>
		<th>adresBuitenland1</th>
		<th>adresBuitenland2</th>
		<th>adresBuitenland3</th>
		<th>landVanwaarIngeschreven</th>
		<th>vestigingInNederland</th>
		<th>aangifteAdreshouding</th>
		<th>indicatieDocument</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud']" mode="inhoud">
		<td><xsl:value-of select="gemeenteInschrijving"/></td>
		<td><xsl:value-of select="datumInschrijving"/></td>
		<td><xsl:value-of select="functieAdres"/></td>
		<td><xsl:value-of select="gemeenteDeel"/></td>
		<td><xsl:value-of select="aanvangAdreshouding"/></td>
		<td><xsl:value-of select="straatnaam"/></td>
		<td><xsl:value-of select="naamOpenbareRuimte"/></td>
		<td><xsl:value-of select="huisnummer"/></td>
		<td><xsl:value-of select="huisletter"/></td>
		<td><xsl:value-of select="huisnummertoevoeging"/></td>
		<td><xsl:value-of select="aanduidingHuisnummer"/></td>
		<td><xsl:value-of select="postcode"/></td>
		<td><xsl:value-of select="woonplaatsnaam"/></td>
		<td><xsl:value-of select="identificatiecodeVerblijfplaats"/></td>
		<td><xsl:value-of select="identificatiecodeNummeraanduiding"/></td>
		<td><xsl:value-of select="locatieBeschrijving"/></td>
		<td><xsl:value-of select="landWaarnaarVertrokken"/></td>
		<td><xsl:value-of select="vertrekUitNederland"/></td>
		<td><xsl:value-of select="adresBuitenland1"/></td>
		<td><xsl:value-of select="adresBuitenland2"/></td>
		<td><xsl:value-of select="adresBuitenland3"/></td>
		<td><xsl:value-of select="landVanwaarIngeschreven"/></td>
		<td><xsl:value-of select="vestigingInNederland"/></td>
		<td><xsl:value-of select="aangifteAdreshouding"/></td>
		<td><xsl:value-of select="indicatieDocument"/></td>
	</xsl:template>
	
	<!-- CATEGORIE 09: KIND -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KindInhoud']" mode="header">
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
	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KindInhoud']" mode="inhoud">
		<td><xsl:value-of select="aNummer"/></td>
		<td><xsl:value-of select="burgerservicenummer"/></td>
		<td><xsl:value-of select="voornamen"/></td>
		<td><xsl:value-of select="adellijkeTitelPredikaatCode"/></td>
		<td><xsl:value-of select="voorvoegselGeslachtsnaam"/></td>
		<td><xsl:value-of select="geslachtsnaam"/></td>
		<td><xsl:value-of select="geboortedatum"/></td>
		<td><xsl:value-of select="gemeenteCode"/></td>
		<td><xsl:value-of select="landCode"/></td>
	</xsl:template>

	<!-- CATEGORIE 10: VERBLIJFSTITEL -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud']" mode="header">
		<th>Aanduiding verblijfstitel</th>
		<th>Datum einde verblijfstitel</th>
		<th>Ingangsdatum verblijfstitel</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud']" mode="inhoud">
		<td><xsl:value-of select="aanduidingVerblijfstitelCode"/></td>
		<td><xsl:value-of select="datumEindeVerblijfstitel"/></td>
		<td><xsl:value-of select="ingangsdatumVerblijfstitel"/></td>
	</xsl:template>	

	<!-- CATEGORIE 11: GEZAGSVERHOUDING -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud']" mode="header">
		<th>Indicatie gezag minderjarige</th>
		<th>Indicatie curatele register</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud']" mode="inhoud">
		<td><xsl:value-of select="indicatieGezagMinderjarige"/></td>
		<td><xsl:value-of select="indicatieCurateleregister"/></td>
	</xsl:template>

	<!-- CATEGORIE 12: REISDOCUMENT -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud']" mode="header">
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
	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud']" mode="inhoud">
		<td><xsl:value-of select="soortNederlandsReisdocument"/></td>
		<td><xsl:value-of select="nummerNederlandsReisdocument"/></td>
		<td><xsl:value-of select="datumUitgifteNederlandsReisdocument"/></td>
		<td><xsl:value-of select="autoriteitVanAfgifteNederlandsReisdocument"/></td>
		<td><xsl:value-of select="datumEindeGeldigheidNederlandsReisdocument"/></td>
		<td><xsl:value-of select="datumInhoudingVermissingNederlandsReisdocument"/></td>
		<td><xsl:value-of select="aanduidingInhoudingVermissingNederlandsReisdocument"/></td>
		<td><xsl:value-of select="lengteHouder"/></td>
		<td><xsl:value-of select="signalering"/></td>
		<td><xsl:value-of select="aanduidingBezitBuitenlandsReisdocument"/></td>
	</xsl:template>
	
	<!-- CATEGORIE 13: KIESRECHT -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud']" mode="header">
		<th>Aanduiding Europees kiesrecht</th>
		<th>Datumm Europees kiesrecht</th>
		<th>Einddatum uitsluiting Europees kiesrecht</th>
		<th>Aanduiding uitgesloten kiesrecht</th>
		<th>Einddatum uitsluiting kiesrecht</th>
	</xsl:template>
	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud']" mode="inhoud">
		<td><xsl:value-of select="aanduidingEuropeesKiesrecht"/></td>
		<td><xsl:value-of select="datumEuropeesKiesrecht"/></td>
		<td><xsl:value-of select="einddatumUitsluitingEuropeesKiesrecht"/></td>
		<td><xsl:value-of select="aanduidingUitgeslotenKiesrecht"/></td>
		<td><xsl:value-of select="einddatumUitsluitingKiesrecht"/></td>
	</xsl:template>
		
	
	<!-- DOCUMENTATIE -->
    <xsl:template name="lo3DocumentatieHeader">
		<th>Gemeente akte</th>
		<th>Nummer akte</th>
		<th>Gemeente document</th>
		<th>Datum document</th>
		<th>Beschrijving document</th>
		<th>Extra document</th>
		<th>Extra document info</th>
	</xsl:template>
	
	<xsl:template name="lo3Documentatie">
		<td><xsl:value-of select="documentatie/gemeenteAkte"/></td>
		<td><xsl:value-of select="documentatie/nummerAkte"/></td>
		<td><xsl:value-of select="documentatie/gemeenteDocument"/></td>
		<td><xsl:value-of select="documentatie/datumDocument"/></td>
		<td><xsl:value-of select="documentatie/beschrijvingDocument"/></td>
		<td><xsl:value-of select="documentatie/extraDocument"/></td>
		<td><xsl:value-of select="documentatie/extraDocumentInformatie"/></td>
	</xsl:template>

	<!-- HISTORIE -->
    <xsl:template name="lo3HistorieHeader">
		<th>Aanvang geldigheid</th>
		<th>Opneming</th>
		<th>Ongeldig</th>
	</xsl:template>
	
	<xsl:template name="lo3Historie">
		<td><xsl:value-of select="historie/ingangsdatumGeldigheid"/></td>
		<td><xsl:value-of select="historie/datumVanOpneming"/></td>
		<td><xsl:value-of select="historie/indicatieOnjuist"/></td>
	</xsl:template>	
	
</xsl:stylesheet>