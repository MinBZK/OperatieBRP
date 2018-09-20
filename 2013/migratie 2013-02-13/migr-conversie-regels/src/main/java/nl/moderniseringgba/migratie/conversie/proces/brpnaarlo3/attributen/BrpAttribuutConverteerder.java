/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AangeverAdreshoudingRedenWijzigingAdresPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversietabelFactory;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.RedenVerkrijgingVerliesPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpWijzeGebruikGeslachtsnaamCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LengteHouder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;

import org.springframework.stereotype.Component;

/**
 * Converteerder helpers (BRP -> LO3).
 */
// CHECKSTYLE:OFF - Fanout complexity - Deze klasse raakt express veel klassen als facade
@Component
public final class BrpAttribuutConverteerder {
    // CHECKSTYLE:ON

    @Inject
    private ConversietabelFactory conversietabellen;

    /**
     * Converteer een datum.
     * 
     * @param value
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3Datum converteerDatum(final BrpDatum value) {
        return value == null ? null : value.converteerNaarLo3Datum();
    }

    /**
     * Converteer een datumtijd.
     * 
     * @param value
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3Datum converteerDatum(final BrpDatumTijd value) {
        return value == null ? null : value.converteerNaarLo3Datum();
    }

    /**
     * Converteer een gemeentecode.
     * 
     * @param value
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3GemeenteCode converteerGemeenteCode(final BrpGemeenteCode value) {
        return conversietabellen.createGemeenteConversietabel().converteerNaarLo3(value);
    }

    /**
     * Converteer een land code.
     * 
     * @param value
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3LandCode converteerLandCode(final BrpLandCode value) {
        return conversietabellen.createLandConversietabel().converteerNaarLo3(value);
    }

    /**
     * Converteer een aanduiding naamgebruik code.
     * 
     * @param wijzeGebruikGeslachtsnaamCode
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingNaamgebruikCode converteerAanduidingNaamgebruik(
            final BrpWijzeGebruikGeslachtsnaamCode wijzeGebruikGeslachtsnaamCode) {
        return conversietabellen.createWijzeGebruikGeslachtsnaamConversietabel().converteerNaarLo3(
                wijzeGebruikGeslachtsnaamCode);
    }

    /**
     * Converteer een geslachtsaanduiding.
     * 
     * @param geslachtsaanduiding
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3Geslachtsaanduiding converteerGeslachtsaanduiding(final BrpGeslachtsaanduidingCode geslachtsaanduiding) {
        return conversietabellen.createGeslachtsaanduidingConversietabel().converteerNaarLo3(geslachtsaanduiding);
    }

    /**
     * Converteer een adelijke titel en predikaat code.
     * 
     * @param adellijkeTitelCode
     *            brp waarde voor adelijke titel
     * @param predikaatCode
     *            brp waarde voor predikaat
     * @return lo3 waarde
     */
    @Requirement({ Requirements.CEL0220, Requirements.CEL0220_BL01 })
    @Definitie({ Definities.DEF017, Definities.DEF018, Definities.DEF019, Definities.DEF020 })
    public Lo3AdellijkeTitelPredikaatCode converteerAdellijkeTitelPredikaatCode(
            final BrpAdellijkeTitelCode adellijkeTitelCode,
            final BrpPredikaatCode predikaatCode) {
        final Character adellijkeTitel = adellijkeTitelCode == null ? null : adellijkeTitelCode.getCode().charAt(0);
        final Character predikaat = predikaatCode == null ? null : predikaatCode.getCode().charAt(0);
        final AdellijkeTitelPredikaatPaar input =
                predikaat == null && adellijkeTitel == null ? null : new AdellijkeTitelPredikaatPaar(adellijkeTitel,
                        predikaat, BrpGeslachtsaanduidingCode.MAN);
        return conversietabellen.createAdellijkeTitelPredikaatConversietabel().converteerNaarLo3(input);

    }

    /**
     * Converteer een nationaliteit.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3NationaliteitCode converteerNationaliteit(final BrpNationaliteitCode waarde) {
        return conversietabellen.createNationaliteitConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een aanduiding uitgesloten kiesrecht.
     * 
     * @param indicatieUitsluitingNederlandsKiesrecht
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingUitgeslotenKiesrecht converteerAanduidingUitgeslotenKiesrecht(
            final Boolean indicatieUitsluitingNederlandsKiesrecht) {
        return conversietabellen.createAanduidingUitgeslotenKiesrechtConversietabel().converteerNaarLo3(
                indicatieUitsluitingNederlandsKiesrecht);
    }

    /**
     * Converteer een aanduiding europees kiesrecht.
     * 
     * @param deelnameEuropeseVerkiezingen
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingEuropeesKiesrecht converteerAanduidingEuropeesKiesrecht(
            final Boolean deelnameEuropeseVerkiezingen) {
        return conversietabellen.createAanduidingEuropeesKiesrechtConversietabel().converteerNaarLo3(
                deelnameEuropeseVerkiezingen);
    }

    /**
     * Converteer een reden opschorting bijhouding.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3RedenOpschortingBijhoudingCode converteerRedenOpschortingBijhouding(
            final BrpRedenOpschortingBijhoudingCode waarde) {
        return conversietabellen.createRedenOpschortingBijhoudingConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een indicatie pk volledig geconverteeerd.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3IndicatiePKVolledigGeconverteerdCode converteerIndicatiePKVolledigGeconverteerd(final Boolean waarde) {
        return conversietabellen.createIndicatiePKConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een indicatie geheim.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3IndicatieGeheimCode converteerIndicatieGeheim(final Boolean waarde) {
        return conversietabellen.createIndicatieGeheimConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een datum tijd stempel.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3Datumtijdstempel converteerDatumtijdstempel(final BrpDatumTijd waarde) {
        return waarde == null ? null : waarde.converteerNaarLo3Datumtijdstempel();
    }

    /**
     * Converteer een functie adres.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3FunctieAdres converteerFunctieAdres(final BrpFunctieAdresCode waarde) {
        return conversietabellen.createFunctieAdresConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een huisnummer .
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3Huisnummer converteerHuisnummer(final Integer waarde) {
        return waarde == null ? null : new Lo3Huisnummer(waarde);
    }

    /**
     * Converteer een woonplaatsnaam.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public String converteerWoonplaatsnaam(final BrpPlaatsCode waarde) {
        return conversietabellen.createPlaatsConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een aanduiding huisnummer.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingHuisnummer converteerAanduidingHuisnummer(final BrpAanduidingBijHuisnummerCode waarde) {
        return conversietabellen.createAanduidingHuisnummerConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een aanduiding verblijfstitel.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingVerblijfstitelCode converteerAanduidingVerblijfstitel(final BrpVerblijfsrechtCode waarde) {
        return conversietabellen.createVerblijfsrechtConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een soort nederlands residocument.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3SoortNederlandsReisdocument converteerSoortNederlandsResidocument(final BrpReisdocumentSoort waarde) {
        return conversietabellen.createSoortReisdocumentConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een autoriteit van aangifte.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3AutoriteitVanAfgifteNederlandsReisdocument converteerAutoriteitVanAfgifte(
            final BrpReisdocumentAutoriteitVanAfgifte waarde) {
        return conversietabellen.createReisdocumentAutoriteitVanAfgifteConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een aanduiding inhouding vermissing nederlands reisdocument.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingInhoudingVermissingNederlandsReisdocument
            converteerAanduidingInhoudingNederlandsReisdocument(final BrpReisdocumentRedenOntbreken waarde) {
        return conversietabellen.createReisdocumentRedenOntbrekenConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een lengte houder .
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3LengteHouder converteerLengteHouder(final Integer waarde) {
        return waarde == null ? null : new Lo3LengteHouder(waarde);
    }

    /**
     * Converteer een signalering.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3Signalering converteerSignalering(final Boolean waarde) {
        return conversietabellen.createSignaleringConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een aanduiding bezit buitenlands reisdocument .
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingBezitBuitenlandsReisdocument converteerAanduidingBezitBuitenlandsReisdocument(
            final Boolean waarde) {
        return conversietabellen.createAanduidingBezitBuitenlandsReisdocumentConversietabel().converteerNaarLo3(
                waarde);
    }

    /**
     * Converteer een aangifte adres houding.
     * 
     * @param redenWijzigingAdresCode
     *            brp waarde, reden wijziging adres
     * @param aangeverAdreshoudingCode
     *            brp waarde, aangever adreshouding
     * @return lo3 waarde
     */
    public Lo3AangifteAdreshouding converteerAangifteAdreshouding(
            final BrpRedenWijzigingAdresCode redenWijzigingAdresCode,
            final BrpAangeverAdreshoudingCode aangeverAdreshoudingCode) {
        final AangeverAdreshoudingRedenWijzigingAdresPaar input =
                new AangeverAdreshoudingRedenWijzigingAdresPaar(aangeverAdreshoudingCode, redenWijzigingAdresCode);

        return conversietabellen.createAangeverAdreshoudingRedenWijzigingAdresConversietabel().converteerNaarLo3(
                input);
    }

    /**
     * Converteer een reden verkrijging nederlanderschap.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3RedenNederlandschapCode converteerRedenNederlanderschap(
            final BrpRedenVerkrijgingNederlandschapCode waarde) {
        if (waarde == null) {
            return null;
        }
        final RedenVerkrijgingVerliesPaar paar = new RedenVerkrijgingVerliesPaar(waarde, null);
        return conversietabellen.createRedenVerkrijgingNlNationaliteitConversietabel().converteerNaarLo3(paar);
    }

    /**
     * Converteer een reden verlies nederlanderschap.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3RedenNederlandschapCode converteerRedenNederlanderschap(final BrpRedenVerliesNederlandschapCode waarde) {
        if (waarde == null) {
            return null;
        }
        final RedenVerkrijgingVerliesPaar paar = new RedenVerkrijgingVerliesPaar(null, waarde);
        return conversietabellen.createRedenVerliesNlNationaliteitConversietabel().converteerNaarLo3(paar);
    }

    /**
     * Converteer een indicatie document.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3IndicatieDocument converteerIndicatieDocument(final Boolean waarde) {
        return conversietabellen.createIndicatieDocumentConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een reden ontbinding huwelijk.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3RedenOntbindingHuwelijkOfGpCode
            converteerRedenOntbindingHuwelijk(final BrpRedenEindeRelatieCode waarde) {
        return conversietabellen.createRedenEindeRelatieConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een indicatie onder curatele.
     * 
     * @param waarde
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3IndicatieCurateleregister converteerIndicatieCurateleRegister(final Boolean waarde) {
        return conversietabellen.createIndicatieCurateleConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een soort relatie code.
     * 
     * @param soortRelatieCode
     *            brp waarde
     * @return lo3 waarde
     */
    public Lo3SoortVerbintenis converteerSoortVerbintenis(final BrpSoortRelatieCode soortRelatieCode) {
        return conversietabellen.createSoortRelatieConversietabel().converteerNaarLo3(soortRelatieCode);
    }

    /**
     * Converteer een voorvoegsel.
     * 
     * @param voorvoegsel
     *            voorvoegsel
     * @param scheidingsteken
     *            scheidingsteken
     * @return lo3 waarde
     */
    @Requirement({ Requirements.CEL0230, Requirements.CEL0230_BL02 })
    @Definitie({ Definities.DEF051, Definities.DEF052, Definities.DEF053 })
    public String converteerVoorvoegsel(final String voorvoegsel, final Character scheidingsteken) {
        final VoorvoegselScheidingstekenPaar input =
                voorvoegsel == null ? null : new VoorvoegselScheidingstekenPaar(voorvoegsel, scheidingsteken);
        return conversietabellen.createVoorvoegselScheidingstekenConversietabel().converteerNaarLo3(input);
    }

    /**
     * Converteer een BRP locatie naar een LO3 locatie.
     * 
     * @param gemeenteCode
     *            gemeenteCode
     * @param plaatsCode
     *            plaatsCode
     * @param buitenlandsePlaats
     *            buitenlandse plaats
     * @param buitenlandseRegio
     *            buitenlandse regio
     * @param landCode
     *            land code
     * @param omschrijvingLocatie
     *            omschrijving locatie
     * @return lo3 gemeente land combinatie
     */
    @Definitie({ Definities.DEF004, Definities.DEF005, Definities.DEF006 })
    public Lo3GemeenteLand converteerLocatie(
            final BrpGemeenteCode gemeenteCode,
            final BrpPlaatsCode plaatsCode,
            final String buitenlandsePlaats,
            final String buitenlandseRegio,
            final BrpLandCode landCode,
            final String omschrijvingLocatie) {
        final Lo3GemeenteCode gemeente;

        // DEF004
        if (gemeenteCode != null) {
            gemeente = converteerGemeenteCode(gemeenteCode);
        } else if (buitenlandsePlaats != null) {
            if (buitenlandseRegio != null) {
                gemeente = new Lo3GemeenteCode(buitenlandsePlaats + ", " + buitenlandseRegio);
            } else {
                gemeente = new Lo3GemeenteCode(buitenlandsePlaats);
            }
        } else if (omschrijvingLocatie != null) {
            gemeente = new Lo3GemeenteCode(omschrijvingLocatie);
        } else {
            gemeente = null;
        }

        return new Lo3GemeenteLand(gemeente, converteerLandCode(landCode));

    }

    /**
     * DTO om een gemeente land combinatie door te geven.
     */
    public static final class Lo3GemeenteLand {
        private final Lo3GemeenteCode gemeenteCode;
        private final Lo3LandCode landCode;

        /**
         * Constructor.
         * 
         * @param gemeenteCode
         *            gemeente
         * @param landCode
         *            land
         */
        Lo3GemeenteLand(final Lo3GemeenteCode gemeenteCode, final Lo3LandCode landCode) {
            this.gemeenteCode = gemeenteCode;
            this.landCode = landCode;
        }

        /**
         * @return the gemeenteCode
         */
        public Lo3GemeenteCode getGemeenteCode() {
            return gemeenteCode;
        }

        /**
         * @return the landCode
         */
        public Lo3LandCode getLandCode() {
            return landCode;
        }

    }

}
