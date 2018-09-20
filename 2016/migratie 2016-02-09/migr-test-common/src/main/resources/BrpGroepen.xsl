<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!-- Groepen zijn alphabetisch gesorteerd zodat de lijst gemakkelijk vergeleken
     |   kan worden met de classes.
     +-->

    <!-- Adres -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud']"
                  mode="header">
        <th>Soort</th>
        <th>Reden wijziging</th>
        <th>Aangever adreshouding</th>
        <th>Datum aanvang adreshouding</th>
        <th>Identificatiecode adresseerbaar object</th>
        <th>Identificatie nummeraanduiding</th>
        <th>Gemeente</th>
        <th>Naam openbare ruimte</th>
        <th>Afgekorte naam openbare ruimte</th>
        <th>Gemeentedeel</th>
        <th>Huisnummer</th>
        <th>Huisletter</th>
        <th>Huisnummer toevoeging</th>
        <th>Postcode</th>
        <th>Woonplaatsnaam</th>
        <th>Locatie tov adres</th>
        <th>Locatie omschrijving</th>
        <th>Buitenland Adres regel 1</th>
        <th>Buitenland Adres regel 2</th>
        <th>Buitenland Adres regel 3</th>
        <th>Buitenland Adres regel 4</th>
        <th>Buitenland Adres regel 5</th>
        <th>Buitenland Adres regel 6</th>
        <th>Land/gebied</th>
        <th>Indicatie persoon aangetroffen op adres</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="soortAdresCode/waarde/text()"/>
            <xsl:apply-templates select="soortAdresCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="redenWijzigingAdresCode/waarde/text()"/>
            <xsl:apply-templates select="redenWijzigingAdresCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="aangeverAdreshoudingCode/waarde/text()"/>
            <xsl:apply-templates select="aangeverAdreshoudingCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumAanvangAdreshouding/waarde/text()"/>
            <xsl:apply-templates select="datumAanvangAdreshouding/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="identificatiecodeAdresseerbaarObject/waarde/text()"/>
            <xsl:apply-templates select="identificatiecodeAdresseerbaarObject/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="identificatiecodeNummeraanduiding/waarde/text()"/>
            <xsl:apply-templates select="identificatiecodeNummeraanduiding/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="gemeenteCode/waarde/text()"/>
            <xsl:apply-templates select="gemeenteCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="naamOpenbareRuimte/waarde/text()"/>
            <xsl:apply-templates select="naamOpenbareRuimte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="afgekorteNaamOpenbareRuimte/waarde/text()"/>
            <xsl:apply-templates select="afgekorteNaamOpenbareRuimte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="gemeentedeel/waarde/text()"/>
            <xsl:apply-templates select="gemeentedeel/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="huisnummer/waarde/text()"/>
            <xsl:apply-templates select="huisnummer/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="huisletter/waarde/text()"/>
            <xsl:apply-templates select="huisletter/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="huisnummertoevoeging/waarde/text()"/>
            <xsl:apply-templates select="huisnummertoevoeging/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="postcode/waarde/text()"/>
            <xsl:apply-templates select="postcode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="woonplaatsnaam/waarde/text()"/>
            <xsl:apply-templates select="woonplaatsnaam/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="locatieTovAdres/waarde/text()"/>
            <xsl:apply-templates select="locatieTovAdres/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="locatieOmschrijving/waarde/text()"/>
            <xsl:apply-templates select="locatieOmschrijving/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsAdresRegel1/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsAdresRegel1/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsAdresRegel2/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsAdresRegel2/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsAdresRegel3/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsAdresRegel3/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsAdresRegel4/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsAdresRegel4/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsAdresRegel5/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsAdresRegel5/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsAdresRegel6/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsAdresRegel6/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="landOfGebiedCode/waarde/text()"/>
            <xsl:apply-templates select="landOfGebiedCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="indicatiePersoonAangetroffenOpAdres/waarde/text()"/>
            <xsl:apply-templates select="indicatiePersoonAangetroffenOpAdres/onderzoek"/>
        </td>
    </xsl:template>

    <!-- BehandeldAlsNederlanderIndicatie -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud']"
            mode="header">
        <th>indicatie</th>
        <th>Migratie reden opname nationaliteit</th>
        <th>Migratie reden beendiging nationaliteit</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatie/waarde/text()"/>
            <xsl:apply-templates select="indicatie/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="migrRdnOpnameNation/waarde/text()"/>
            <xsl:apply-templates select="migrRdnOpnameNation/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="migrRdnBeeindigingNation/waarde/text()"/>
            <xsl:apply-templates select="migrRdnBeeindigingNation/onderzoek"/>
        </td>
    </xsl:template>

    <!-- SignaleringMetBetrekkingToVerstrekkenReisdocumentInhoud -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud']"
            mode="header">
        <th>Signalering mbt verstrekken reisdocument?</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatie/waarde/text()"/>
            <xsl:apply-templates select="indicatie/onderzoek"/>
        </td>
    </xsl:template>

    <!-- BezitBuitenlandsReisdocument -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBezitBuitenlandsReisdocumentIndicatieInhoud']"
            mode="header">
        <th>Bezit buitenlands reisdocument?</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBezitBuitenlandsReisdocumentIndicatieInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatie/waarde/text()"/>
            <xsl:apply-templates select="indicatie/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Bijhouding -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud']"
            mode="header">
        <th>Bijhoudingspartij</th>
        <th>Bijhoudingsaard</th>
        <th>Nadere bijhoudingsaard</th>
        <th>Onverwerkt document aanwezig</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="bijhoudingspartijCode/waarde/text()"/>
            <xsl:apply-templates select="bijhoudingspartijCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="bijhoudingsaardCode/waarde/text()"/>
            <xsl:apply-templates select="bijhoudingsaardCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="nadereBijhoudingsaardCode/waarde/text()"/>
            <xsl:apply-templates select="nadereBijhoudingsaardCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="indicatieOnverwerktDocumentAanwezig/waarde/text()"/>
            <xsl:apply-templates select="indicatieOnverwerktDocumentAanwezig/onderzoek"/>
        </td>
    </xsl:template>

    <!-- BijzondereVerblijfsrechtelijkePositieIndicatie -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud']"
            mode="header">
        <th>Bijzondere Verblijfsrechtelijke Positie?</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatie/waarde/text()"/>
            <xsl:apply-templates select="indicatie/onderzoek"/>
        </td>
    </xsl:template>

    <!-- DerdeHeeftGezagIndicatie -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud']"
            mode="header">
        <th>Derde heeft gezag?</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatie/waarde/text()"/>
            <xsl:apply-templates select="indicatie/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Deelname EU Verkiezingen -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud']"
            mode="header">
        <th>Deelname Europese verkiezingen?</th>
        <th>Datum aanleiding aanpassing deelname Europese verkiezingen</th>
        <th>Datum vorzien einde uitsluiting Europees verkiezingen</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatieDeelnameEuVerkiezingen/waarde/text()"/>
            <xsl:apply-templates select="indicatieDeelnameEuVerkiezingen/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumAanleidingAanpassingDeelnameEuVerkiezingen/waarde/text()"/>
            <xsl:apply-templates select="datumAanleidingAanpassingDeelnameEuVerkiezingen/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumVoorzienEindeUitsluitingEuVerkiezingen/waarde/text()"/>
            <xsl:apply-templates select="datumVoorzienEindeUitsluitingEuVerkiezingen/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Geboorte -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud']"
                  mode="header">
        <th>Geboortedatum</th>
        <th>Gemeente</th>
        <th>Woonplaatsnaam geboorte</th>
        <th>Plaats buitenland</th>
        <th>Regio buitenland</th>
        <th>Land/gebied</th>
        <th>Locatieomschrijving</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="geboortedatum/waarde/text()"/>
            <xsl:apply-templates select="geboortedatum/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="gemeenteCode/waarde/text()"/>
            <xsl:apply-templates select="gemeenteCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="woonplaatsnaamGeboorte/waarde/text()"/>
            <xsl:apply-templates select="woonplaatsnaamGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsePlaatsGeboorte/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsePlaatsGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandseRegioGeboorte/waarde/text()"/>
            <xsl:apply-templates select="buitenlandseRegioGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="landOfGebiedCode/waarde/text()"/>
            <xsl:apply-templates select="landOfGebiedCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="omschrijvingGeboortelocatie/waarde/text()"/>
            <xsl:apply-templates select="omschrijvingGeboortelocatie/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Geslachtsaanduiding -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud']"
            mode="header">
        <th>Geslacht</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="geslachtsaanduidingCode/waarde/text()"/>
            <xsl:apply-templates select="geslachtsaanduidingCode/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Geslachtsnaamcomponent -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud']"
            mode="header">
        <th>Voorvoegsel</th>
        <th>Scheidingsteken</th>
        <th>Stam</th>
        <th>Predicaat</th>
        <th>Adellijke titel</th>
        <th>Volgnummer</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="voorvoegsel/waarde/text()"/>
            <xsl:apply-templates select="voorvoegsel/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="scheidingsteken/waarde/text()"/>
            <xsl:apply-templates select="scheidingsteken/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="stam/waarde/text()"/>
            <xsl:apply-templates select="stam/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="predicaatCode/waarde/text()"/>
            <xsl:apply-templates select="predicaatCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="adellijkeTitelCode/waarde/text()"/>
            <xsl:apply-templates select="adellijkeTitelCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="volgnummer/waarde/text()"/>
            <xsl:apply-templates select="volgnummer/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Identificatienummers -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud']"
            mode="header">
        <th>A-nummer</th>
        <th>BSN</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="administratienummer/waarde/text()"/>
            <xsl:apply-templates select="administratienummer/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="burgerservicenummer/waarde/text()"/>
            <xsl:apply-templates select="burgerservicenummer/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Migratie -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud']"
                  mode="header">
        <th>Soort migratie</th>
        <th>Reden wijziging</th>
        <th>Aangever</th>
        <th>Land/Gebied</th>
        <th>Buitenlands adres regel 1</th>
        <th>Buitenlands adres regel 2</th>
        <th>Buitenlands adres regel 3</th>
        <th>Buitenlands adres regel 4</th>
        <th>Buitenlands adres regel 5</th>
        <th>Buitenlands adres regel 6</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="soortMigratieCode/waarde/text()"/>
            <xsl:apply-templates select="soortMigratieCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="redenWijzigingMigratieCode/waarde/text()"/>
            <xsl:apply-templates select="redenWijzigingMigratieCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="aangeverMigratieCode/waarde/text()"/>
            <xsl:apply-templates select="aangeverMigratieCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="landOfGebiedCodeMigratie/waarde/text()"/>
            <xsl:apply-templates select="landOfGebiedCodeMigratie/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsAdresRegel1/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsAdresRegel1/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsAdresRegel2/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsAdresRegel2/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsAdresRegel3/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsAdresRegel3/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsAdresRegel4/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsAdresRegel4/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsAdresRegel5/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsAdresRegel5/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsAdresRegel6/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsAdresRegel6/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Inschrijving -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud']"
                  mode="header">
        <th>Datum inschrijving</th>
        <th>Versienummer</th>
        <th>Datumtijdstempel</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="datumInschrijving/waarde/text()"/>
            <xsl:apply-templates select="datumInschrijving/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="versienummer/waarde/text()"/>
            <xsl:apply-templates select="versienummer/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumtijdstempel/waarde/text()"/>
            <xsl:apply-templates select="datumtijdstempel/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Nummerverwijzing -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud']"
                  mode="header">
        <th>Vorige A-nummer</th>
        <th>Volgende A-nummer</th>
        <th>Vorige BSN</th>
        <th>Volgende BSN</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="vorigeAdministratienummer/waarde/text()"/>
            <xsl:apply-templates select="vorigeAdministratienummer/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="volgendeAdministratienummer/waarde/text()"/>
            <xsl:apply-templates select="volgendeAdministratienummer/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="vorigeBurgerservicenummer/waarde/text()"/>
            <xsl:apply-templates select="vorigeBurgerservicenummer/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="volgendeBurgerservicenummer/waarde/text()"/>
            <xsl:apply-templates select="volgendeBurgerservicenummer/onderzoek"/>
        </td>
    </xsl:template>

    <!-- IST Relatie -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud']"
                  mode="header">
        <th>Categorie</th>
        <th>Stapel</th>
        <th>Voorkomen</th>
        <th>SoortDocument</th>
        <th>Partij</th>
        <th>Aktenummer</th>
        <th>Rubriek8220DatumDocument</th>
        <th>DocumentOmschrijving</th>
        <th>Rubriek8310AanduidingGegevensInOnderzoek</th>
        <th>Rubriek8320DatumIngangOnderzoek</th>
        <th>Rubriek8330DatumEindeOnderzoek</th>
        <th>Rubriek8410OnjuistOfStrijdigOpenbareOrde</th>
        <th>Rubriek8510IngangsdatumGeldigheid</th>
        <th>Rubriek8610DatumVanOpneming</th>
        <th>Rubriek6210DatumIngangFamilierechtelijkeBetrekking</th>
        <th>Anummer</th>
        <th>Bsn</th>
        <th>Voornamen</th>
        <th>Predicaat</th>
        <th>AdellijkeTitel</th>
        <th>Voorvoegsel</th>
        <th>Scheidingsteken</th>
        <th>Geslachtsnaamstam</th>
        <th>DatumGeboorte</th>
        <th>GemeenteGeboorte</th>
        <th>BuitenlandsePlaatsGeboorte</th>
        <th>OmschrijvingLocatieGeboorte</th>
        <th>LandOfGebiedGeboorte</th>
        <th>Geslachtsaanduiding</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="standaardGegevens/categorie/waarde/text()"/><xsl:value-of select="standaardGegevens/categorie/text()"/>
            <xsl:apply-templates select="standaardGegevens/categorie/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/stapel/waarde/text()"/><xsl:value-of select="standaardGegevens/stapel/text()"/>
            <xsl:apply-templates select="standaardGegevens/stapel/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/voorkomen/waarde/text()"/><xsl:value-of select="standaardGegevens/voorkomen/text()"/>
            <xsl:apply-templates select="standaardGegevens/voorkomen/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/soortDocument/waarde/text()"/><xsl:value-of select="standaardGegevens/soortDocument/text()"/>
            <xsl:apply-templates select="standaardGegevens/soortDocument/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/partij/waarde/text()"/><xsl:value-of select="standaardGegevens/partij/text()"/>
            <xsl:apply-templates select="standaardGegevens/partij/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/aktenummer/waarde/text()"/><xsl:value-of select="standaardGegevens/aktenummer/text()"/>
            <xsl:apply-templates select="standaardGegevens/aktenummer/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8220DatumDocument/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8220DatumDocument/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8220DatumDocument/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/documentOmschrijving/waarde/text()"/><xsl:value-of select="standaardGegevens/documentOmschrijving/text()"/>
            <xsl:apply-templates select="standaardGegevens/documentOmschrijving/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8310AanduidingGegevensInOnderzoek/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8310AanduidingGegevensInOnderzoek/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8310AanduidingGegevensInOnderzoek/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8320DatumIngangOnderzoek/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8320DatumIngangOnderzoek/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8320DatumIngangOnderzoek/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8330DatumEindeOnderzoek/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8330DatumEindeOnderzoek/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8330DatumEindeOnderzoek/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8410OnjuistOfStrijdigOpenbareOrde/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8410OnjuistOfStrijdigOpenbareOrde/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8410OnjuistOfStrijdigOpenbareOrde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8510IngangsdatumGeldigheid/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8510IngangsdatumGeldigheid/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8510IngangsdatumGeldigheid/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8610DatumVanOpneming/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8610DatumVanOpneming/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8610DatumVanOpneming/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="rubriek6210DatumIngangFamilierechtelijkeBetrekking/waarde/text()"/>
            <xsl:apply-templates select="rubriek6210DatumIngangFamilierechtelijkeBetrekking/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="anummer/waarde/text()"/>
            <xsl:apply-templates select="anummer/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="bsn/waarde/text()"/>
            <xsl:apply-templates select="bsn/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="voornamen/waarde/text()"/>
            <xsl:apply-templates select="voornamen/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="predicaatCode/waarde/text()"/>
            <xsl:apply-templates select="predicaatCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="adellijkeTitelCode/waarde/text()"/>
            <xsl:apply-templates select="adellijkeTitelCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="voorvoegsel/waarde/text()"/>
            <xsl:apply-templates select="voorvoegsel/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="scheidingsteken/waarde/text()"/>
            <xsl:apply-templates select="scheidingsteken/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="geslachtsnaamstam/waarde/text()"/>
            <xsl:apply-templates select="geslachtsnaamstam/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumGeboorte/waarde/text()"/>
            <xsl:apply-templates select="datumGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="gemeenteCodeGeboorte/waarde/text()"/>
            <xsl:apply-templates select="gemeenteCodeGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsePlaatsGeboorte/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsePlaatsGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="omschrijvingLocatieGeboorte/waarde/text()"/>
            <xsl:apply-templates select="omschrijvingLocatieGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="landOfGebiedCodeGeboorte/waarde/text()"/>
            <xsl:apply-templates select="landOfGebiedCodeGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="geslachtsaanduidingCode/waarde/text()"/>
            <xsl:apply-templates select="geslachtsaanduidingCode/onderzoek"/>
        </td>
    </xsl:template>

    <!-- IST Huwelijk of Geregistreerd partnerschap -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud']"
                  mode="header">
        <th>Categorie</th>
        <th>Stapel</th>
        <th>Voorkomen</th>
        <th>SoortDocument</th>
        <th>Partij</th>
        <th>Aktenummer</th>
        <th>Rubriek8220DatumDocument</th>
        <th>DocumentOmschrijving</th>
        <th>Rubriek8310AanduidingGegevensInOnderzoek</th>
        <th>Rubriek8320DatumIngangOnderzoek</th>
        <th>Rubriek8330DatumEindeOnderzoek</th>
        <th>Rubriek8410OnjuistOfStrijdigOpenbareOrde</th>
        <th>Rubriek8510IngangsdatumGeldigheid</th>
        <th>Rubriek8610DatumVanOpneming</th>
        <th>Rubriek6210DatumIngangFamilierechtelijkeBetrekking</th>
        <th>Anummer</th>
        <th>Bsn</th>
        <th>Voornamen</th>
        <th>Predicaat</th>
        <th>AdellijkeTitel</th>
        <th>Voorvoegsel</th>
        <th>Scheidingsteken</th>
        <th>Geslachtsnaam</th>
        <th>DatumGeboorte</th>
        <th>GemeenteGeboorte</th>
        <th>BuitenlandsePlaatsGeboorte</th>
        <th>OmschrijvingLocatieGeboorte</th>
        <th>LandGeboorte</th>
        <th>Geslachtsaanduiding</th>
        <th>DatumAanvang</th>
        <th>GemeenteAanvang</th>
        <th>BuitenlandsePlaatsAanvang</th>
        <th>OmschrijvingLocatieAanvang</th>
        <th>LandOfGebiedAanvang</th>
        <th>RedenBeeindigingRelatie</th>
        <th>DatumEinde</th>
        <th>GemeenteEinde</th>
        <th>BuitenlandsePlaatsEinde</th>
        <th>OmschrijvingLocatieEinde</th>
        <th>LandOfGebiedEinde</th>
        <th>SoortRelatie</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="standaardGegevens/categorie/waarde/text()"/><xsl:value-of select="standaardGegevens/categorie/text()"/>
            <xsl:apply-templates select="standaardGegevens/categorie/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/stapel/waarde/text()"/><xsl:value-of select="standaardGegevens/stapel/text()"/>
            <xsl:apply-templates select="standaardGegevens/stapel/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/voorkomen/waarde/text()"/><xsl:value-of select="standaardGegevens/voorkomen/text()"/>
            <xsl:apply-templates select="standaardGegevens/voorkomen/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/soortDocument/waarde/text()"/><xsl:value-of select="standaardGegevens/soortDocument/text()"/>
            <xsl:apply-templates select="standaardGegevens/soortDocument/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/partij/waarde/text()"/><xsl:value-of select="standaardGegevens/partij/text()"/>
            <xsl:apply-templates select="standaardGegevens/partij/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/aktenummer/waarde/text()"/><xsl:value-of select="standaardGegevens/aktenummer/text()"/>
            <xsl:apply-templates select="standaardGegevens/aktenummer/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8220DatumDocument/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8220DatumDocument/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8220DatumDocument/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/documentOmschrijving/waarde/text()"/><xsl:value-of select="standaardGegevens/documentOmschrijving/text()"/>
            <xsl:apply-templates select="standaardGegevens/documentOmschrijving/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8310AanduidingGegevensInOnderzoek/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8310AanduidingGegevensInOnderzoek/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8310AanduidingGegevensInOnderzoek/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8320DatumIngangOnderzoek/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8320DatumIngangOnderzoek/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8320DatumIngangOnderzoek/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8330DatumEindeOnderzoek/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8330DatumEindeOnderzoek/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8330DatumEindeOnderzoek/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8410OnjuistOfStrijdigOpenbareOrde/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8410OnjuistOfStrijdigOpenbareOrde/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8410OnjuistOfStrijdigOpenbareOrde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8510IngangsdatumGeldigheid/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8510IngangsdatumGeldigheid/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8510IngangsdatumGeldigheid/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8610DatumVanOpneming/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8610DatumVanOpneming/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8610DatumVanOpneming/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/rubriek6210DatumIngangFamilierechtelijkeBetrekking/waarde/text()"/><xsl:value-of
                select="relatie/rubriek6210DatumIngangFamilierechtelijkeBetrekking/text()"/>
            <xsl:apply-templates select="relatie/rubriek6210DatumIngangFamilierechtelijkeBetrekking/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/anummer/waarde/text()"/><xsl:value-of select="relatie/anummer/text()"/>
            <xsl:apply-templates select="relatie/anummer/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/bsn/waarde/text()"/><xsl:value-of select="relatie/bsn/text()"/>
            <xsl:apply-templates select="relatie/bsn/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/voornamen/waarde/text()"/><xsl:value-of select="relatie/voornamen/text()"/>
            <xsl:apply-templates select="relatie/voornamen/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/predicaatCode/waarde/text()"/><xsl:value-of select="relatie/predicaatCode/text()"/>
            <xsl:apply-templates select="relatie/predicaatCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/adellijkeTitelCode/waarde/text()"/><xsl:value-of select="relatie/adellijkeTitelCode/text()"/>
            <xsl:apply-templates select="relatie/adellijkeTitelCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/voorvoegsel/waarde/text()"/><xsl:value-of select="relatie/voorvoegsel/text()"/>
            <xsl:apply-templates select="relatie/voorvoegsel/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/scheidingsteken/waarde/text()"/><xsl:value-of select="relatie/scheidingsteken/text()"/>
            <xsl:apply-templates select="relatie/scheidingsteken/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/geslachtsnaamstam/waarde/text()"/><xsl:value-of select="relatie/geslachtsnaamstam/text()"/>
            <xsl:apply-templates select="relatie/geslachtsnaamstam/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/datumGeboorte/waarde/text()"/><xsl:value-of select="relatie/datumGeboorte/text()"/>
            <xsl:apply-templates select="relatie/datumGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/gemeenteCodeGeboorte/waarde/text()"/><xsl:value-of select="relatie/gemeenteCodeGeboorte/text()"/>
            <xsl:apply-templates select="relatie/gemeenteCodeGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/buitenlandsePlaatsGeboorte/waarde/text()"/><xsl:value-of select="relatie/buitenlandsePlaatsGeboorte/text()"/>
            <xsl:apply-templates select="relatie/buitenlandsePlaatsGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/omschrijvingLocatieGeboorte/waarde/text()"/><xsl:value-of select="relatie/omschrijvingLocatieGeboorte/text()"/>
            <xsl:apply-templates select="relatie/omschrijvingLocatieGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/landOfGebiedCodeGeboorte/waarde/text()"/><xsl:value-of select="relatie/landOfGebiedCodeGeboorte/text()"/>
            <xsl:apply-templates select="relatie/landOfGebiedCodeGeboorte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="relatie/geslachtsaanduidingCode/waarde/text()"/><xsl:value-of select="relatie/geslachtsaanduidingCode/text()"/>
            <xsl:apply-templates select="relatie/geslachtsaanduidingCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumAanvang/waarde/text()"/>
            <xsl:apply-templates select="datumAanvang/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="gemeenteCodeAanvang/waarde/text()"/>
            <xsl:apply-templates select="gemeenteCodeAanvang/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsePlaatsAanvang/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsePlaatsAanvang/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="omschrijvingLocatieAanvang/waarde/text()"/>
            <xsl:apply-templates select="omschrijvingLocatieAanvang/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="landOfGebiedCodeAanvang/waarde/text()"/>
            <xsl:apply-templates select="landOfGebiedCodeAanvang/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="redenBeeindigingRelatieCode/waarde/text()"/>
            <xsl:apply-templates select="redenBeeindigingRelatieCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumEinde/waarde/text()"/>
            <xsl:apply-templates select="datumEinde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="gemeenteCodeEinde/waarde/text()"/>
            <xsl:apply-templates select="gemeenteCodeEinde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsePlaatsEinde/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsePlaatsEinde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="omschrijvingLocatieEinde/waarde/text()"/>
            <xsl:apply-templates select="omschrijvingLocatieEinde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="landOfGebiedCodeEinde/waarde/text()"/>
            <xsl:apply-templates select="landOfGebiedCodeEinde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="soortRelatieCode/waarde/text()"/>
            <xsl:apply-templates select="soortRelatieCode/onderzoek"/>
        </td>
    </xsl:template>

    <!-- IST Gezagsverhouding -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud']"
                  mode="header">
        <th>Categorie</th>
        <th>Stapel</th>
        <th>Voorkomen</th>
        <th>SoortDocument</th>
        <th>Partij</th>
        <th>Rubriek8220DatumDocument</th>
        <th>DocumentOmschrijving</th>
        <th>Rubriek8310AanduidingGegevensInOnderzoek</th>
        <th>Rubriek8320DatumIngangOnderzoek</th>
        <th>Rubriek8330DatumEindeOnderzoek</th>
        <th>Rubriek8410OnjuistOfStrijdigOpenbareOrde</th>
        <th>Rubriek8510IngangsdatumGeldigheid</th>
        <th>Rubriek8610DatumVanOpneming</th>
        <th>indicatieOuder1HeeftGezag</th>
        <th>indicatieOuder2HeeftGezag</th>
        <th>indicatieDerdeHeeftGezag</th>
        <th>indicatieOnderCuratele</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="standaardGegevens/categorie/waarde/text()"/><xsl:value-of select="standaardGegevens/categorie/text()"/>
            <xsl:apply-templates select="standaardGegevens/categorie/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/stapel/waarde/text()"/><xsl:value-of select="standaardGegevens/stapel/text()"/>
            <xsl:apply-templates select="standaardGegevens/stapel/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/voorkomen/waarde/text()"/><xsl:value-of select="standaardGegevens/voorkomen/text()"/>
            <xsl:apply-templates select="standaardGegevens/voorkomen/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/soortDocument/waarde/text()"/><xsl:value-of select="standaardGegevens/soortDocument/text()"/>
            <xsl:apply-templates select="standaardGegevens/soortDocument/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/partij/waarde/text()"/><xsl:value-of select="standaardGegevens/partij/text()"/>
            <xsl:apply-templates select="standaardGegevens/partij/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8220DatumDocument/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8220DatumDocument/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8220DatumDocument/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/documentOmschrijving/waarde/text()"/><xsl:value-of select="standaardGegevens/documentOmschrijving/text()"/>
            <xsl:apply-templates select="standaardGegevens/documentOmschrijving/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8310AanduidingGegevensInOnderzoek/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8310AanduidingGegevensInOnderzoek/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8310AanduidingGegevensInOnderzoek/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8320DatumIngangOnderzoek/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8320DatumIngangOnderzoek/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8320DatumIngangOnderzoek/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8330DatumEindeOnderzoek/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8330DatumEindeOnderzoek/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8330DatumEindeOnderzoek/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8410OnjuistOfStrijdigOpenbareOrde/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8410OnjuistOfStrijdigOpenbareOrde/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8410OnjuistOfStrijdigOpenbareOrde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8510IngangsdatumGeldigheid/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8510IngangsdatumGeldigheid/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8510IngangsdatumGeldigheid/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="standaardGegevens/rubriek8610DatumVanOpneming/waarde/text()"/><xsl:value-of
                select="standaardGegevens/rubriek8610DatumVanOpneming/text()"/>
            <xsl:apply-templates select="standaardGegevens/rubriek8610DatumVanOpneming/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="indicatieOuder1HeeftGezag/waarde/text()"/>
            <xsl:apply-templates select="indicatieOuder1HeeftGezag/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="indicatieOuder2HeeftGezag/waarde/text()"/>
            <xsl:apply-templates select="indicatieOuder2HeeftGezag/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="indicatieDerdeHeeftGezag/waarde/text()"/>
            <xsl:apply-templates select="indicatieDerdeHeeftGezag/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="indicatieOnderCuratele/waarde/text()"/>
            <xsl:apply-templates select="indicatieOnderCuratele/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Naamgebruik -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud']"
                  mode="header">
        <th>Naamgebruik</th>
        <th>Indicatie afgeleid</th>
        <th>Predicaat</th>
        <th>Adellijke titel</th>
        <th>Voornamen</th>
        <th>Voorvoegsel</th>
        <th>Scheidingsteken</th>
        <th>Geslachtsnaamstam</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="naamgebruikCode/waarde/text()"/>
            <xsl:apply-templates select="naamgebruikCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="indicatieAfgeleid/waarde/text()"/>
            <xsl:apply-templates select="indicatieAfgeleid/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="predicaatCode/waarde/text()"/>
            <xsl:apply-templates select="predicaatCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="adellijkeTitelCode/waarde/text()"/>
            <xsl:apply-templates select="adellijkeTitelCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="voornamen/waarde/text()"/>
            <xsl:apply-templates select="voornamen/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="voorvoegsel/waarde/text()"/>
            <xsl:apply-templates select="voorvoegsel/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="scheidingsteken/waarde/text()"/>
            <xsl:apply-templates select="scheidingsteken/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="geslachtsnaamstam/waarde/text()"/>
            <xsl:apply-templates select="geslachtsnaamstam/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Nationaliteit -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud']"
                  mode="header">
        <th>Nationaliteit</th>
        <th>Reden verkrijging</th>
        <th>Reden verlies</th>
        <th>Einde bijhouding?</th>
        <th>Migratie datum</th>
        <th>Migratie opname bijhouding</th>
        <th>Migratie beeindiging bijhouding</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="nationaliteitCode/waarde/text()"/>
            <xsl:apply-templates select="nationaliteitCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="redenVerkrijgingNederlandschapCode/waarde/text()"/>
            <xsl:apply-templates select="redenVerkrijgingNederlandschapCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="redenVerliesNederlandschapCode/waarde/text()"/>
            <xsl:apply-templates select="redenVerliesNederlandschapCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="eindeBijhouding/waarde/text()"/>
            <xsl:apply-templates select="eindeBijhouding/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="migratieDatum/waarde/text()"/>
            <xsl:apply-templates select="migratieDatum/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="migratieRedenOpnameNationaliteit/waarde/text()"/>
            <xsl:apply-templates select="migratieRedenOpnameNationaliteit/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="migratieRedenBeeindigingNationaliteit/waarde/text()"/>
            <xsl:apply-templates select="migratieRedenBeeindigingNationaliteit/onderzoek"/>
        </td>
    </xsl:template>

    <!-- OnderCurateleIndicatie -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud']"
            mode="header">
        <th>Onder curatele</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatie/waarde/text()"/>
            <xsl:apply-templates select="indicatie/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Ouder -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud']"
                  mode="header">
        <th>Ouder?</th>
        <th>Adresgevende ouder?</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="indicatieOuder/waarde/text()"/>
            <xsl:apply-templates select="indicatieOuder/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="indicatieOuderUitWieKindIsGeboren/waarde/text()"/>
            <xsl:apply-templates select="indicatieOuderUitWieKindIsGeboren/onderzoek"/>
        </td>
    </xsl:template>

    <!-- OuderlijkGezag -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud']"
            mode="header">
        <th>Ouder heeft gezag</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="ouderHeeftGezag/waarde/text()"/>
            <xsl:apply-templates select="ouderHeeftGezag/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Overlijden -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud']"
                  mode="header">
        <th>Datum</th>
        <th>Gemeente</th>
        <th>Woonplaatsnaam overlijden</th>
        <th>Buitenlands plaats</th>
        <th>Buitenlands regio</th>
        <th>Land/gebied</th>
        <th>Omschrijving locatie</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="datum/waarde/text()"/>
            <xsl:apply-templates select="datum/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="gemeenteCode/waarde/text()"/>
            <xsl:apply-templates select="gemeenteCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="woonplaatsnaamOverlijden/waarde/text()"/>
            <xsl:apply-templates select="woonplaatsnaamOverlijden/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsePlaats/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsePlaats/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandseRegio/waarde/text()"/>
            <xsl:apply-templates select="buitenlandseRegio/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="landOfGebiedCode/waarde/text()"/>
            <xsl:apply-templates select="landOfGebiedCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="omschrijvingLocatie/waarde/text()"/>
            <xsl:apply-templates select="omschrijvingLocatie/onderzoek"/>
        </td>
    </xsl:template>

    <!-- PersoonAfgeleidAdmnistratief -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud']"
            mode="header">
        <th>Onderzoek?</th>
        <th>Onverwerkt Bijhoudingsvoorstel?</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatieOnderzoekNaarNietOpgenomenGegevens/waarde/text()"/>
            <xsl:apply-templates select="indicatieOnderzoekNaarNietOpgenomenGegevens/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig/waarde/text()"/>
            <xsl:apply-templates select="indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Persoonskaart -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud']"
                  mode="header">
        <th>Gemeente PK</th>
        <th>PK volledig geconverteerd?</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="gemeentePKCode/waarde/text()"/>
            <xsl:apply-templates select="gemeentePKCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="indicatiePKVolledigGeconverteerd/waarde/text()"/>
            <xsl:apply-templates select="indicatiePKVolledigGeconverteerd/onderzoek"/>
        </td>
    </xsl:template>

    <!-- RelatieAfgeleidAdmnistratief -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieAfgeleidAdministratiefInhoud']"
            mode="header">
        <th>Asymmetrisch?</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieAfgeleidAdministratiefInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatieAsymmetrisch/waarde/text()"/>
            <xsl:apply-templates select="indicatieAsymmetrisch/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Reisdocument -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud']"
                  mode="header">
        <th>Soort</th>
        <th>Nummer</th>
        <th>Datum ingang document</th>
        <th>Datum uitgifte</th>
        <th>Autoriteit van afgifte</th>
        <th>Datum einde document</th>
        <th>Datum inhouding/vermissing</th>
        <th>Aanduiding inhouding/vermissing</th>
        <th>Lengte houder</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="soort/waarde/text()"/>
            <xsl:apply-templates select="soort/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="nummer/waarde/text()"/>
            <xsl:apply-templates select="nummer/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumIngangDocument/waarde/text()"/>
            <xsl:apply-templates select="datumIngangDocument/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumUitgifte/waarde/text()"/>
            <xsl:apply-templates select="datumUitgifte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="autoriteitVanAfgifte/waarde/text()"/>
            <xsl:apply-templates select="autoriteitVanAfgifte/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumEindeDocument/waarde/text()"/>
            <xsl:apply-templates select="datumEindeDocument/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumInhoudingOfVermissing/waarde/text()"/>
            <xsl:apply-templates select="datumInhoudingOfVermissing/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="aanduidingInhoudingOfVermissing/waarde/text()"/>
            <xsl:apply-templates select="aanduidingInhoudingOfVermissing/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="lengteHouder/waarde/text()"/>
            <xsl:apply-templates select="lengteHouder/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Relatie -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud']"
                  mode="header">
        <th>Datum aanvang</th>
        <th>Gemeente aanvang</th>
        <th>Woonplaatsnaam aanvang</th>
        <th>Buitenlandse plaats aanvang</th>
        <th>Buitenlandse regio aanvang</th>
        <th>Land aanvang</th>
        <th>Omschrijving locatie aanvang</th>
        <th>Reden einde</th>
        <th>Datum einde</th>
        <th>Gemeente einde</th>
        <th>Woonplaatsnaam einde</th>
        <th>Buitenlandse plaats einde</th>
        <th>Buitenlandse regio einde</th>
        <th>Land einde</th>
        <th>Omschrijving locatie einde</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="datumAanvang/waarde/text()"/>
            <xsl:apply-templates select="datumAanvang/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="gemeenteCodeAanvang/waarde/text()"/>
            <xsl:apply-templates select="gemeenteCodeAanvang/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="woonplaatsnaamAanvang/waarde/text()"/>
            <xsl:apply-templates select="woonplaatsnaamAanvang/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsePlaatsAanvang/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsePlaatsAanvang/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandseRegioAanvang/waarde/text()"/>
            <xsl:apply-templates select="buitenlandseRegioAanvang/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="landOfGebiedCodeAanvang/waarde/text()"/>
            <xsl:apply-templates select="landOfGebiedCodeAanvang/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="omschrijvingLocatieAanvang/waarde/text()"/>
            <xsl:apply-templates select="omschrijvingLocatieAanvang/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="redenEindeRelatieCode/waarde/text()"/>
            <xsl:apply-templates select="redenEindeRelatieCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumEinde/waarde/text()"/>
            <xsl:apply-templates select="datumEinde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="gemeenteCodeEinde/waarde/text()"/>
            <xsl:apply-templates select="gemeenteCodeEinde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="woonplaatsnaamEinde/waarde/text()"/>
            <xsl:apply-templates select="woonplaatsnaamEinde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandsePlaatsEinde/waarde/text()"/>
            <xsl:apply-templates select="buitenlandsePlaatsEinde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="buitenlandseRegioEinde/waarde/text()"/>
            <xsl:apply-templates select="buitenlandseRegioEinde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="landOfGebiedCodeEinde/waarde/text()"/>
            <xsl:apply-templates select="landOfGebiedCodeEinde/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="omschrijvingLocatieEinde/waarde/text()"/>
            <xsl:apply-templates select="omschrijvingLocatieEinde/onderzoek"/>
        </td>
    </xsl:template>

    <!-- SamengesteldeNaam -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud']"
            mode="header">
        <th>Predicaat</th>
        <th>Voornamen</th>
        <th>Voorvoegsel</th>
        <th>Scheiding</th>
        <th>Adellijke titel</th>
        <th>Geslachtsnaamstam</th>
        <th>Namenreeks?</th>
        <th>Afgeleid?</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="predicaatCode/waarde/text()"/>
            <xsl:apply-templates select="predicaatCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="voornamen/waarde/text()"/>
            <xsl:apply-templates select="voornamen/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="voorvoegsel/waarde/text()"/>
            <xsl:apply-templates select="voorvoegsel/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="scheidingsteken/waarde/text()"/>
            <xsl:apply-templates select="scheidingsteken/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="adellijkeTitelCode/waarde/text()"/>
            <xsl:apply-templates select="adellijkeTitelCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="geslachtsnaamstam/waarde/text()"/>
            <xsl:apply-templates select="geslachtsnaamstam/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="indicatieNamenreeks/waarde/text()"/>
            <xsl:apply-templates select="indicatieNamenreeks/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="indicatieAfgeleid/waarde/text()"/>
            <xsl:apply-templates select="indicatieAfgeleid/onderzoek"/>
        </td>
    </xsl:template>

    <!-- StaatloosIndicatie -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud']"
            mode="header">
        <th>Staatloos?</th>
        <th>Migratie reden opname nationaliteit</th>
        <th>Migratie reden beendiging nationaliteit</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatie/waarde/text()"/>
            <xsl:apply-templates select="indicatie/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="migrRdnOpnameNation/waarde/text()"/>
            <xsl:apply-templates select="migrRdnOpnameNation/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="migrRdnBeeindigingNation/waarde/text()"/>
            <xsl:apply-templates select="migrRdnBeeindigingNation/onderzoek"/>
        </td>
    </xsl:template>

    <!-- UitsluitingNederlandsKiesrecht -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud']"
            mode="header">
        <th>Indicatie uitsluiting kiesrecht</th>
        <th>Datum voorzien einde uitsluiting kiesrecht</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatieUitsluitingKiesrecht/waarde/text()"/>
            <xsl:apply-templates select="indicatieUitsluitingKiesrecht/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumVoorzienEindeUitsluitingKiesrecht/waarde/text()"/>
            <xsl:apply-templates select="datumVoorzienEindeUitsluitingKiesrecht/onderzoek"/>
        </td>
    </xsl:template>

    <!-- VastgesteldNietNederlanderIndicatie -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud']"
            mode="header">
        <th>Vastgesteld niet nederlander?</th>
        <th>Migratie reden opname nationaliteit</th>
        <th>Migratie reden beendiging nationaliteit</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatie/waarde/text()"/>
            <xsl:apply-templates select="indicatie/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="migrRdnOpnameNation/waarde/text()"/>
            <xsl:apply-templates select="migrRdnOpnameNation/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="migrRdnBeeindigingNation/waarde/text()"/>
            <xsl:apply-templates select="migrRdnBeeindigingNation/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Verblijfsrecht -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud']"
            mode="header">
        <th>Aanduiding verblijfsrecht</th>
        <th>Datum mededeling verblijfsrecht</th>
        <th>Datum aanvang verblijfsrecht</th>
        <th>Datum voorzien einde verblijfsrecht</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="aanduidingVerblijfsrechtCode/waarde/text()"/>
            <xsl:apply-templates select="aanduidingVerblijfsrechtCode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumMededelingVerblijfsrecht/waarde/text()"/>
            <xsl:apply-templates select="datumMededelingVerblijfsrecht/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumAanvangVerblijfsrecht/waarde/text()"/>
            <xsl:apply-templates select="datumAanvangVerblijfsrecht/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumVoorzienEindeVerblijfsrecht/waarde/text()"/>
            <xsl:apply-templates select="datumVoorzienEindeVerblijfsrecht/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Verificatie -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud']"
            mode="header">
        <th>Partij</th>
        <th>Soort</th>
        <th>Datum</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="partij/waarde/text()"/>
            <xsl:apply-templates select="partij/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="soort/waarde/text()"/>
            <xsl:apply-templates select="soort/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datum/waarde/text()"/>
            <xsl:apply-templates select="datum/onderzoek"/>
        </td>
    </xsl:template>

    <!-- VerstrekkingsbeperkingIndicatie -->
    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud']"
            mode="header">
        <th>Verstrekkingsbeperking?</th>
    </xsl:template>

    <xsl:template
            match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud']"
            mode="inhoud">
        <td>
            <xsl:value-of select="indicatie/waarde/text()"/>
            <xsl:apply-templates select="indicatie/onderzoek"/>
        </td>
    </xsl:template>

    <!-- Voornaam -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud']"
                  mode="header">
        <th>Voornaam</th>
        <th>Volgnummer</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="voornaam/waarde/text()"/>
            <xsl:apply-templates select="voornaam/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="volgnummer/waarde/text()"/>
            <xsl:apply-templates select="volgnummer/onderzoek"/>
        </td>
    </xsl:template>

    <!-- AUTORISATIE -->
    <!-- Afnemersindicatie -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud']"
                  mode="header">
        <th>Datum aanvang materiele periode</th>
        <th>Datum tijd einde volgen</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="datumAanvangMaterielePeriode/waarde/text()"/>
            <xsl:apply-templates select="datumAanvangMaterielePeriode/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="datumTijdEindeVolgen/waarde/text()"/>
            <xsl:apply-templates select="datumTijdEindeVolgen/onderzoek"/>
        </td>
    </xsl:template>

    <!-- HISTORIE -->
    <xsl:template name="brpHistorieHeader">
    	<xsl:call-template name="brpMaterieleHistorieHeader" />
    	<xsl:call-template name="brpFormeleHistorieHeader" />
    	<xsl:call-template name="brpNadereAanduidingVervalHeader" />
  	</xsl:template>
  	 
    <xsl:template name="brpMaterieleHistorieHeader">
        <th>Aanvang geldigheid</th>
        <th>Einde geldigheid</th>
    </xsl:template>
    <xsl:template name="brpFormeleHistorieHeader">
        <th>Registratie</th>
        <th>Verval</th>
    </xsl:template>
    <xsl:template name="brpNadereAanduidingVervalHeader">
        <th>Nadere aanduiding verval</th>
    </xsl:template>

    <xsl:template name="brpHistorie">
    	<xsl:call-template name="brpMaterieleHistorie" />
    	<xsl:call-template name="brpFormeleHistorie" />
    	<xsl:call-template name="brpNadereAanduidingVerval" />
    </xsl:template>
    
    <xsl:template name="brpMaterieleHistorie">
        <td>
            <xsl:value-of select="historie/datumAanvangGeldigheid/waarde/text()"/><xsl:value-of select="historie/datumAanvangGeldigheid/text()"/>
            <xsl:apply-templates select="historie/datumAanvangGeldigheid/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="historie/datumEindeGeldigheid/waarde/text()"/><xsl:value-of select="historie/datumEindeGeldigheid/text()"/>
            <xsl:apply-templates select="historie/datumEindeGeldigheid/onderzoek"/>
        </td>
    </xsl:template>
    <xsl:template name="brpFormeleHistorie">
        <td>
            <xsl:value-of select="historie/datumTijdRegistratie/waarde/text()"/><xsl:value-of select="historie/datumTijdRegistratie/text()"/>
            <xsl:apply-templates select="historie/datumTijdRegistratie/onderzoek"/>
        </td>
        <td>
            <xsl:value-of select="historie/datumTijdVerval/waarde/text()"/><xsl:value-of select="historie/datumTijdVerval/text()"/>
            <xsl:apply-templates select="historie/datumTijdVerval/onderzoek"/>
        </td>
    </xsl:template>
    <xsl:template name="brpNadereAanduidingVerval">
        <td>
            <xsl:value-of select="historie/nadereAanduidingVerval/waarde/text()"/><xsl:value-of select="historie/nadereAanduidingVerval/text()"/>
            <xsl:apply-templates select="historie/nadereAanduidingVerval/onderzoek"/>
        </td>
    </xsl:template>

    <!-- ACTIES -->
    <xsl:template name="brpActiesHeader">
        <th>Actie inhoud</th>
        <th>Actie verval</th>
        <th>Actie aanpassing geldigheid</th>
    </xsl:template>

    <xsl:template name="brpActiesInhoud">
        <td>
            <xsl:apply-templates select="actieInhoud"/>
        </td>
        <td>
            <xsl:apply-templates select="actieVerval"/>
        </td>
        <td>
            <xsl:apply-templates select="actieGeldigheid"/>
        </td>
    </xsl:template>

    <xsl:template name="brpActieInhoudRef"
                  match="actieInhoud[@ref!='']|actieVerval[@ref!='']|actieGeldigheid[@ref!='']">
        <xsl:variable name="actie_id" select="@ref"/>
        <xsl:apply-templates
                select="//actieInhoud[@id=$actie_id] | //actieVerval[@id=$actie_id] | //actieGeldigheid[@id=$actie_id]"/>
    </xsl:template>

    <xsl:template name="brpActieInhoud" match="actieInhoud[@id!='']|actieVerval[@id!='']|actieGeldigheid[@id!='']">
        <table>
            <tr>
                <!--th>id</th-->
                <th>soort</th>
                <th>partijCode</th>
                <th>registratie</th>
                <th>ontlening</th>
                <th>actieBronnen</th>
                <th>lo3Herkomst</th>
            </tr>
            <tr>
                <!--td><xsl:value-of select="id" /></td-->
                <td>
                    <xsl:value-of select="soortActieCode/waarde/text()"/>
                    <xsl:apply-templates select="soortActieCode/onderzoek"/>
                </td>
                <td>
                    <xsl:value-of select="partijCode/waarde/text()"/>
                    <xsl:apply-templates select="partijCode/onderzoek"/>
                </td>
                <td>
                    <xsl:value-of select="datumTijdRegistratie/waarde/text()"/>
                    <xsl:apply-templates select="datumTijdRegistratie/onderzoek"/>
                </td>
                <td>
                    <xsl:value-of select="datumOntlening/waarde/text()"/>
                    <xsl:apply-templates select="datumOntlening/onderzoek"/>
                </td>
                <td>
                    <table>
                        <xsl:for-each select="actieBronnen/brpActieBron">
                            <tr>
                                <td>
                                    <table>
                                        <xsl:for-each select="documentStapel/brpGroep/inhoud">
                                            <tr>
                                                <td>
                                                    <xsl:value-of select="soortDocumentCode/waarde/text()"/>
                                                    <xsl:apply-templates select="soortDocumentCode/onderzoek"/>
                                                </td>
                                                <td>
                                                    <xsl:value-of select="identificatie/waarde/text()"/>
                                                    <xsl:apply-templates select="identificatie/onderzoek"/>
                                                </td>
                                                <td>
                                                    <xsl:value-of select="aktenummer/waarde/text()"/>
                                                    <xsl:apply-templates select="aktenummer/onderzoek"/>
                                                </td>
                                                <td>
                                                    <xsl:value-of select="omschrijving/waarde/text()"/>
                                                    <xsl:apply-templates select="omschrijving/onderzoek"/>
                                                </td>
                                                <td>
                                                    <xsl:value-of select="partijCode/waarde/text()"/>
                                                    <xsl:apply-templates select="partijCode/onderzoek"/>
                                                </td>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </td>
                                <td>
                                    <xsl:value-of select="rechtsgrondOmschrijving/waarde/text()"/>
                                    <xsl:apply-templates select="rechtsgrondOmschrijving/onderzoek"/>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </table>
                </td>
                <td>
                    <xsl:value-of select="lo3Herkomst"/>
                </td>

            </tr>
        </table>

    </xsl:template>

    <xsl:template match="onderzoek">
        <br/>Onderzoek:
        <br/>
        <xsl:value-of select="aanduidingGegevensInOnderzoek/waarde/text()"/>:<xsl:value-of select="datumIngangOnderzoek/waarde/text()"/>-<xsl:value-of
            select="datumEindeOnderzoek/waarde/text()"/>
    </xsl:template>

</xsl:stylesheet>
