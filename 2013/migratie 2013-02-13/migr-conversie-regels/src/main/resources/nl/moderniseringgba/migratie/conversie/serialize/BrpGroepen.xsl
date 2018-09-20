<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!-- Groepen zijn alphabetisch gesorteerd zodat de lijst gemakkelijk vergeleken
	 |   kan worden met de classes.
	 +-->

	<!-- Aanschrijving -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud']" mode="header">
		<th>Wijze gebruik geslachtsnaam</th>
		<th>Indicatie titels/predikaten</th>
		<th>Indicatie afgeleid</th>
		<th>Predikaat</th>
		<th>Adellijke titel</th>
		<th>Voornamen</th>
		<th>Voorvoegsel</th>
		<th>Scheidingsteken</th>
		<th>Geslachtsnaam</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud']" mode="inhoud">
		<td><xsl:value-of select="wijzeGebruikGeslachtsnaamCode"/></td>
		<td><xsl:value-of select="indicatieAanschrijvenMetTitels"/></td>
		<td><xsl:value-of select="indicatieAfgeleid"/></td>
		<td><xsl:value-of select="predikaatCode"/></td>
		<td><xsl:value-of select="adellijkeTitelCode"/></td>
		<td><xsl:value-of select="voornamen"/></td>
		<td><xsl:value-of select="voorvoegsel"/></td>
		<td><xsl:value-of select="scheidingsteken"/></td>
		<td><xsl:value-of select="geslachtsnaam"/></td>
	</xsl:template>
	


	<!-- Adres -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud']" mode="header">
		<th>Functie</th>
		<th>Reden wijziging</th>
        <th>Aangever adreshouding</th>
        <th>Datum aanvrang adreshouding</th>
		<th>Adresseerbaar object</th>
		<th>Identificatie nummeraanduiding</th>
		<th>Gemeente</th>
		<th>Naam openbare ruimte</th>
		<th>Afgekorte naam openbare ruimte</th>
		<th>Gemeentedeel</th>
		<th>Huisnummer</th>
		<th>Huisletter</th>
		<th>Huisnummer toevoeging</th>
		<th>Postcode</th>
		<th>Plaats</th>
		<th>Locatie tov adres</th>
		<th>Locatie omschrijving</th>
		<th>Buitenland Adres regel 1</th>
		<th>Buitenland Adres regel 2</th>
		<th>Buitenland Adres regel 3</th>
		<th>Buitenland Adres regel 4</th>
		<th>Buitenland Adres regel 5</th>
		<th>Buitenland Adres regel 6</th>
		<th>Land</th>
		<th>Datum vertrek uit Nederland</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud']" mode="inhoud">
		<td><xsl:value-of select="functieAdresCode"/></td>
        <td><xsl:value-of select="redenWijzigingAdresCode"/></td>
        <td><xsl:value-of select="aangeverAdreshoudingCode"/></td>
        <td><xsl:value-of select="datumAanvangAdreshouding"/></td>
		<td><xsl:value-of select="adresseerbaarObject"/></td>
        <td><xsl:value-of select="identificatiecodeNummeraanduiding"/></td>
        <td><xsl:value-of select="gemeenteCode"/></td>
        <td><xsl:value-of select="naamOpenbareRuimte"/></td>
        <td><xsl:value-of select="afgekorteNaamOpenbareRuimte"/></td>
        <td><xsl:value-of select="gemeentedeel"/></td>
        <td><xsl:value-of select="huisnummer"/></td>
        <td><xsl:value-of select="huisletter"/></td>
        <td><xsl:value-of select="huisnummertoevoeging"/></td>
        <td><xsl:value-of select="postcode"/></td>
        <td><xsl:value-of select="plaatsCode"/></td>
        <td><xsl:value-of select="locatieTovAdres"/></td>
        <td><xsl:value-of select="locatieOmschrijving"/></td>
		<td><xsl:value-of select="buitenlandsAdresRegel1"/></td>
		<td><xsl:value-of select="buitenlandsAdresRegel2"/></td>
		<td><xsl:value-of select="buitenlandsAdresRegel3"/></td>
		<td><xsl:value-of select="buitenlandsAdresRegel4"/></td>
		<td><xsl:value-of select="buitenlandsAdresRegel5"/></td>
		<td><xsl:value-of select="buitenlandsAdresRegel6"/></td>
		<td><xsl:value-of select="landCode"/></td>
        <td><xsl:value-of select="datumVertrekUitNederland"/></td>
	</xsl:template>

	<!-- AfgeleidAdmnistratief -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAfgeleidAdministratiefInhoud']" mode="header">
		<th>laatsteWijziging</th>
		<th>inOnderzoek</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAfgeleidAdministratiefInhoud']" mode="inhoud">
		<td><xsl:value-of select="laatsteWijziging"/></td>
		<td><xsl:value-of select="inOnderzoek"/></td>
	</xsl:template>

	<!-- BehandeldAlsNederlanderIndicatie -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud']" mode="header">
		<th>indicatie</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud']" mode="inhoud">
		<td><xsl:value-of select="heeftIndicatie"/></td>
	</xsl:template>
	
	<!-- BelemmeringVerstekkingReisdocumentIndicatie -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud']" mode="header">
		<th>indicatie</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud']" mode="inhoud">
		<td><xsl:value-of select="heeftIndicatie"/></td>
	</xsl:template>
		
	<!-- Betrokkenheid -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBetrokkenheidInhoud']" mode="header">
		<th>Aanvang</th>
		<th>Einde</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBetrokkenheidInhoud']" mode="inhoud">
		<td><xsl:value-of select="aanvang"/></td>
		<td><xsl:value-of select="einde"/></td>
	</xsl:template>
	
	<!-- BezitBuitenlandsReisdocumentIndicatie -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBezitBuitenlandsReisdocumentIndicatieInhoud']" mode="header">
		<th>indicatie</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBezitBuitenlandsReisdocumentIndicatieInhoud']" mode="inhoud">
		<td><xsl:value-of select="heeftIndicatie"/></td>
	</xsl:template>
	
	<!-- Bijhoudingsgemeente -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsgemeenteInhoud']" mode="header">
		<th>Bijhoudingsgemeente</th>
		<th>Datum inschrijving gemeente</th>
		<th>Onverwerkt document aanwezig</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsgemeenteInhoud']" mode="inhoud">
		<td><xsl:value-of select="bijhoudingsgemeenteCode"/></td>
		<td><xsl:value-of select="datumInschrijvingInGemeente"/></td>
		<td><xsl:value-of select="onverwerktDocumentAanwezig"/></td>
	</xsl:template>


	<!-- Bijhoudingsverantwoordelijkheid -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsverantwoordelijkheidInhoud']" mode="header">
		<th>Verantwoordelijke</th>
		<th>Datum bijhoudingsverantwoordelijkheid</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsverantwoordelijkheidInhoud']" mode="inhoud">
		<td><xsl:value-of select="verantwoordelijkeCode"/></td>
		<td><xsl:value-of select="datumBijhoudingsverantwoordelijkheid"/></td>
	</xsl:template>

	<!-- DerdeHeeftGezagIndicatie -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud']" mode="header">
		<th>Derde heeft gezag</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud']" mode="inhoud">
		<td><xsl:value-of select="derdeHeeftGezag"/></td>
	</xsl:template>

	<!-- EuropeseVerkiezingen -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpEuropeseVerkiezingenInhoud']" mode="header">
		<th>Indicatie deelname Europese verkiezingen</th>
		<th>Datum aanleiding aanpassing deelname Europese verkiezingen</th>
		<th>Datum einde uitsluiting europees kiesrecht</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpEuropeseVerkiezingenInhoud']" mode="inhoud">
		<td><xsl:value-of select="deelnameEuropeseVerkiezingen"/></td>
                <td><xsl:value-of select="datumAanleidingAanpassingDeelnameEuropeseVerkiezingen"/></td>
                <td><xsl:value-of select="datumEindeUitsluitingEuropeesKiesrecht"/></td>
	</xsl:template>

	<!-- Geboorte -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud']" mode="header">
		<th>Geboortedatum</th>
		<th>Gemeente</th>
		<th>Plaats</th>
		<th>Plaats buitenland</th>
		<th>Regio buitenland</th>
		<th>Land</th>
		<th>Locatieomschrijving</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud']" mode="inhoud">
		<td><xsl:value-of select="geboortedatum"/></td>
		<td><xsl:value-of select="gemeenteCode"/></td>
		<td><xsl:value-of select="plaatsCode"/></td>
		<td><xsl:value-of select="buitenlandseGeboorteplaats"/></td>
		<td><xsl:value-of select="buitenlandseRegioGeboorte"/></td>
		<td><xsl:value-of select="landCode"/></td>
		<td><xsl:value-of select="omschrijvingGeboortelocatie"/></td>
	</xsl:template>

	<!-- GeprivilegieerdeIndicatie -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeprivilegieerdeIndicatieInhoud']" mode="header">
		<th>Geprivilegieerde</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeprivilegieerdeIndicatieInhoud']" mode="inhoud">
		<td><xsl:value-of select="heeftIndicatie"/></td>
	</xsl:template>
	
	<!-- Geslachtsaanduiding -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud']" mode="header">
		<th>Geslacht</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud']" mode="inhoud">
		<td><xsl:value-of select="geslachtsaanduiding"/></td>
	</xsl:template>
		
	<!-- Geslachtsnaamcomponent -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud']" mode="header">
		<th>Voorvoegsel</th>
		<th>Scheidingsteken</th>
		<th>Naam</th>
		<th>Predikaat</th>
		<th>Adellijke titel</th>
		<th>volgnummer</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud']" mode="inhoud">
		<td><xsl:value-of select="voorvoegsel"/></td>
		<td><xsl:value-of select="scheidingsteken"/></td>
		<td><xsl:value-of select="naam"/></td>
		<td><xsl:value-of select="predikaatCode"/></td>
		<td><xsl:value-of select="adellijkeTitelCode"/></td>
		<td><xsl:value-of select="volgnummer"/></td>
	</xsl:template>
	
	<!-- Identificatienummers -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud']" mode="header">
		<th>A-nummer</th><th>BSN</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud']" mode="inhoud">
		<td><xsl:value-of select="administratienummer"/></td><td><xsl:value-of select="burgerservicenummer"/></td>
	</xsl:template>
	
	<!-- Immigratie -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpImmigratieInhoud']" mode="header">
		<th>Land vanwaar ingeschreven</th>
		<th>Datum vestiging in Nederland</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpImmigratieInhoud']" mode="inhoud">
		<td><xsl:value-of select="landVanwaarIngeschreven"/></td>
        <td><xsl:value-of select="datumVestigingInNederland"/></td>
	</xsl:template>

	<!-- Inschrijving -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud']" mode="header">
		<th>Vorig A-nummer</th>
		<th>Volgend A-nummer</th>
		<th>Datum inschrijving</th>
		<th>Versienummer</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud']" mode="inhoud">
		<td><xsl:value-of select="vorigAdministratienummer"/></td>
		<td><xsl:value-of select="volgendAdministratienummer"/></td>
		<td><xsl:value-of select="datumInschrijving"/></td>
		<td><xsl:value-of select="versienummer"/></td>
	</xsl:template>
	
	<!-- Nationaliteit -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud']" mode="header">
		<th>Nationaliteit</th>
		<th>Reden verkrijging</th>
		<th>Reden verlies</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud']" mode="inhoud">
		<td><xsl:value-of select="nationaliteitCode"/></td>
		<td><xsl:value-of select="redenVerkrijgingNederlandschapCode"/></td>
		<td><xsl:value-of select="redenVerliesNederlandschapCode"/></td>
	</xsl:template>
	
	<!-- OnderCurateleIndicatie -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud']" mode="header">
		<th>Onder curatele</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud']" mode="inhoud">
		<td><xsl:value-of select="onderCuratele"/></td>
	</xsl:template>
	
	<!-- Opschorting -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOpschortingInhoud']" mode="header">
		<th>Datum opschorting</th>
		<th>Reden opschorting bijhouding</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOpschortingInhoud']" mode="inhoud">
		<td><xsl:value-of select="datumOpschorting"/></td>
		<td><xsl:value-of select="redenOpschortingBijhoudingCode"/></td>
	</xsl:template>
	
	<!-- Ouder -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud']" mode="header">
		<th>indicatie</th>
		<th>aanvang</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud']" mode="inhoud">
		<td><xsl:value-of select="heeftIndicatie"/></td>
		<td><xsl:value-of select="datumAanvang"/></td>
	</xsl:template>
	
	<!-- OuderlijkGezag -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud']" mode="header">
		<th>Ouder heeft gezag</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud']" mode="inhoud">
		<td><xsl:value-of select="ouderHeeftGezag"/></td>
	</xsl:template>

	<!-- Overlijden -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOverlijdenInhoud']" mode="header">
		<th>Datum</th>
		<th>Gemeente</th>
		<th>Plaats</th>
		<th>Buitenlands plaats</th>
		<th>Buitenlands regio</th>
		<th>Land</th>
		<th>Omschrijving locatie</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOverlijdenInhoud']" mode="inhoud">
		<td><xsl:value-of select="datum"/></td>
		<td><xsl:value-of select="gemeenteCode"/></td>
		<td><xsl:value-of select="plaatsCode"/></td>
		<td><xsl:value-of select="buitenlandsePlaats"/></td>
		<td><xsl:value-of select="buitenlandseRegio"/></td>
		<td><xsl:value-of select="landCode"/></td>
		<td><xsl:value-of select="omschrijvingLocatie"/></td>
	</xsl:template>

	<!-- Persoonskaart -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpPersoonskaartInhoud']" mode="header">
		<th>Gemeente PK</th>
		<th>PK volledig geconverteerd?</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpPersoonskaartInhoud']" mode="inhoud">
		<td><xsl:value-of select="gemeentePKCode"/></td>
		<td><xsl:value-of select="indicatiePKVolledigGeconverteerd"/></td>
	</xsl:template>

	<!-- Reisdocument -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud']" mode="header">
		<th>Soort</th>
		<th>Nummer</th>
		<th>Datum ingang document</th>
		<th>Datum uitgifte</th>
		<th>Autoriteit van afgifte</th>
		<th>Datum voorziene einde geldigheid</th>
		<th>Datum inhouding/vermissing</th>
		<th>Reden ontbreken</th>
		<th>Lengte houder</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud']" mode="inhoud">
		<td><xsl:value-of select="soort"/></td>
		<td><xsl:value-of select="nummer"/></td>
		<td><xsl:value-of select="datumIngangDocument"/></td>
		<td><xsl:value-of select="datumUitgifte"/></td>
		<td><xsl:value-of select="autoriteitVanAfgifte"/></td>
		<td><xsl:value-of select="datumVoorzieneEindeGeldigheid"/></td>
		<td><xsl:value-of select="datumInhoudingVermissing"/></td>
		<td><xsl:value-of select="redenOntbreken"/></td>
		<td><xsl:value-of select="lengteHouder"/></td>
	</xsl:template>
	
	<!-- Relatie -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud']" mode="header">
		<th>Datum aanvang</th>
		<th>Gemeente aanvang</th>
		<th>Plaats aanvang</th>
		<th>Buitenlandse plaats aanvang</th>
		<th>Buitenlandse regio aanvang</th>
		<th>Land aanvang</th>
		<th>Omschrijving locatie aanvang</th>
		<th>Reden einde</th>
		<th>Datum einde</th>
		<th>Gemeente einde</th>
		<th>Plaats einde</th>
		<th>Buitenlandse plaats einde</th>
		<th>Buitenlandse regio einde</th>
		<th>Land einde</th>
		<th>Omschrijving locatie einde</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud']" mode="inhoud">
		<td><xsl:value-of select="datumAanvang"/></td>
		<td><xsl:value-of select="gemeenteCodeAanvang"/></td>
		<td><xsl:value-of select="plaatsCodeAanvang"/></td>
		<td><xsl:value-of select="buitenlandsePlaatsAanvang"/></td>
		<td><xsl:value-of select="buitenlandseRegioAanvang"/></td>
		<td><xsl:value-of select="landCodeAanvang"/></td>
		<td><xsl:value-of select="omschrijvingLocatieAanvang"/></td>
		<td><xsl:value-of select="redenEinde"/></td>
		<td><xsl:value-of select="datumEinde"/></td>
		<td><xsl:value-of select="gemeenteCodeEinde"/></td>
		<td><xsl:value-of select="plaatsCodeEinde"/></td>
		<td><xsl:value-of select="buitenlandsePlaatsEinde"/></td>
		<td><xsl:value-of select="buitenlandseRegioEinde"/></td>
		<td><xsl:value-of select="landCodeEinde"/></td>
		<td><xsl:value-of select="omschrijvingLocatieEinde"/></td>
	</xsl:template>
	
	<!-- SamengesteldeNaam -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud']" mode="header">
		<th>Predikaat</th>
		<th>Voornamen</th>
		<th>Voorvoegsel</th>
		<th>Scheiding</th>
		<th>Adellijke titel</th>
		<th>Geslachtsnaam</th>
		<th>Namenreeks</th>
		<th>Afgeleid?</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud']" mode="inhoud">
		<td><xsl:value-of select="predikaatCode"/></td>
		<td><xsl:value-of select="voornamen"/></td>
		<td><xsl:value-of select="voorvoegsel"/></td>
		<td><xsl:value-of select="scheidingsteken"/></td>
		<td><xsl:value-of select="adellijkeTitelCode"/></td>
		<td><xsl:value-of select="geslachtsnaam"/></td>
		<td><xsl:value-of select="indicatieNamenreeks"/></td>
		<td><xsl:value-of select="indicatieAfgeleid"/></td>
	</xsl:template>
	
	<!-- StatenloosIndicatie -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpStatenloosIndicatieInhoud']" mode="header">
		<th>indicatie</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpStatenloosIndicatieInhoud']" mode="inhoud">
		<td><xsl:value-of select="heeftIndicatie"/></td>
	</xsl:template>	

	<!-- UitsluitingNederlandsKiesrecht -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpUitsluitingNederlandsKiesrechtInhoud']" mode="header">
		<th>Indicatie uitsluiting Nederlands kiesrecht</th>
		<th>Datum einde uitsluiting Nederlands kiesrecht</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpUitsluitingNederlandsKiesrechtInhoud']" mode="inhoud">
		<td><xsl:value-of select="indicatieUitsluitingNederlandsKiesrecht"/></td>
        <td><xsl:value-of select="datumEindeUitsluitingNederlandsKiesrecht"/></td>
	</xsl:template>
	
	<!-- VastgesteldNietNederlanderIndicatie -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud']" mode="header">
		<th>indicatie</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud']" mode="inhoud">
		<td><xsl:value-of select="heeftIndicatie"/></td>
	</xsl:template>
	
	<!-- Verblijfsrecht -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerblijfsrechtInhoud']" mode="header">
		<th>Verblijfsrecht</th>
		<th>Datum aanvang verblijfsrecht</th>
		<th>Datum voorzien einde verblijfsrecht</th>
		<th>Datum aanvang aaneensluitend verblijfsrecht</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerblijfsrechtInhoud']" mode="inhoud">
		<td><xsl:value-of select="verblijfsrechtCode"/></td>
		<td><xsl:value-of select="aanvangVerblijfsrecht"/></td>
		<td><xsl:value-of select="voorzienEindeVerblijfsrecht"/></td>
		<td><xsl:value-of select="aanvangAaneensluitendVerblijfsrecht"/></td>
	</xsl:template>
	
	<!-- Verstrekkingsbeperking -->
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerstrekkingsbeperkingInhoud']" mode="header">
		<th>Verstrekkingsbeperking</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerstrekkingsbeperkingInhoud']" mode="inhoud">
		<td><xsl:value-of select="verstrekkingsbeperking"/></td>
	</xsl:template>
		
	<!-- Voornaam -->	
	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud']" mode="header">
		<th>Voornaam</th>
		<th>Volgnummer</th>
	</xsl:template>

	<xsl:template match="inhoud[@class='nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud']" mode="inhoud">
		<td><xsl:value-of select="voornaam"/></td>
		<td><xsl:value-of select="volgnummer"/></td>
	</xsl:template>

	<!-- HISTORIE -->
	<xsl:template name="brpHistorieHeader">
		<th>Aanvang geldigheid</th><th>Einde geldigheid</th><th>Registratie</th><th>Verval</th>
	</xsl:template>

	<xsl:template name="brpHistorie">
		<td><xsl:value-of select="historie/datumAanvangGeldigheid"/></td><td><xsl:value-of select="historie/datumEindeGeldigheid"/></td><td><xsl:value-of select="historie/datumTijdRegistratie"/></td><td><xsl:value-of select="historie/datumTijdVerval"/></td>
	</xsl:template>
	
	<!-- ACTIES -->
	<xsl:template name="brpActiesHeader">
		<th>Actie inhoud</th>
		<th>Actie verval</th>
		<th>Actie aanpassing geldigheid</th>
	</xsl:template>
	
	<xsl:template name="brpActiesInhoud">
		<td><xsl:apply-templates select="actieInhoud" /></td>
		<td><xsl:apply-templates select="actieVerval" /></td>
		<td><xsl:apply-templates select="actieGeldigheid" /></td>
	</xsl:template>
	
	<xsl:template name="brpActieInhoudRef" match="actieInhoud[@ref!='']|actieVerval[@ref!='']|actieGeldigheid[@ref!='']">
		<xsl:variable name="actie_id" select="@ref" />
		<xsl:apply-templates select="//actieInhoud[@id=$actie_id] | //actieVerval[@id=$actie_id] | //actieGeldigheid[@id=$actie_id]" />
	</xsl:template>
	
	<xsl:template name="brpActieInhoud" match="actieInhoud[@id!='']|actieVerval[@id!='']|actieGeldigheid[@id!='']">
		<table>
			<tr>
				<!--th>id</th-->
				<th>soort</th>
				<th>partijNaam</th>
				<th>partijGemeenteCode</th>
				<th>verdrag</th>
				<th>ontlening</th>
				<th>registratie</th>
				<th>documenten</th>
			</tr>
			<tr>
				<!--td><xsl:value-of select="id" /></td-->
				<td><xsl:value-of select="soortActieCode" /></td>
				<td><xsl:value-of select="partijNaam" /></td>
				<td><xsl:value-of select="partijGemeenteCode" /></td>
				<td><xsl:value-of select="verdragCode" /></td>
				<td><xsl:value-of select="datumTijdOntlening" /></td>
				<td><xsl:value-of select="datumTijdRegistratie" /></td>
				<td>
					<table>
						<xsl:for-each select="documentStapels/brpStapel/brpGroep/inhoud">
							<tr>
								<td><xsl:value-of select="soortDocumentCode" /></td>
								<td><xsl:value-of select="identificatie" /></td>
								<td><xsl:value-of select="aktenummer" /></td>
								<td><xsl:value-of select="omschrijving" /></td>
								<td><xsl:value-of select="partijCode" /></td>
							</tr>
						</xsl:for-each>
					</table>
				</td>
			</tr>
		</table>
		
	</xsl:template>
	
</xsl:stylesheet>