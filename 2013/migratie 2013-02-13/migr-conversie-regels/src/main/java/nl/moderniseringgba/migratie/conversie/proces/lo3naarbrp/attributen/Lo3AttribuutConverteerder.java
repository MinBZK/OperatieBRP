/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AangeverAdreshoudingRedenWijzigingAdresPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversietabelFactory;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.RedenVerkrijgingVerliesPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
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
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
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
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
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
 * Converteerder utility methoden.
 */
// CHECKSTYLE:OFF - Fan-out complexity - geaccepteerd
@Component
public class Lo3AttribuutConverteerder {
    // CHECKSTYLE:ON

    @Inject
    private ConversietabelFactory conversietabellen;

    /**
     * Converteer een datum.
     * 
     * @param datum
     *            lo3 datum, mag null zijn
     * @return brp datum, null als datum null is
     */
    public final BrpDatum converteerDatum(final Lo3Datum datum) {
        return datum == null ? null : datum.converteerNaarBrpDatum();
    }

    /**
     * Converteer een indicatie document.
     * 
     * @param indicatieDocument
     *            lo3 indicatie document, mag null zijn
     * @return true als indicatie aanwezig is, anders false
     */
    public final Boolean converteerIndicatieDocument(final Lo3IndicatieDocument indicatieDocument) {
        return conversietabellen.createIndicatieDocumentConversietabel().converteerNaarBrp(indicatieDocument);

    }

    /**
     * Converteer een huisnummer.
     * 
     * @param huisnummer
     *            huisnummer, mag null zijn
     * @return huisnummer, null als huisnummer null is
     */
    public final Integer converteerHuisnummer(final Lo3Huisnummer huisnummer) {
        return huisnummer == null ? null : huisnummer.getNummer();
    }

    /**
     * Converteer lengte houder.
     * 
     * @param lengteHouder
     *            lo3 lengte houder, mag null zijn
     * @return brp lengte houder, null als lengteHouder null is
     */
    public final Integer converteerLengteHouder(final Lo3LengteHouder lengteHouder) {
        return lengteHouder == null ? null : lengteHouder.getLengte();
    }

    /**
     * Converteer een indicatie gezag minderjarige naar derde heeft gezag.
     * 
     * @param indicatieGezagdMinderjarige
     *            LO3 waarde
     * @return BRP waarde
     */
    public final Boolean converteerDerdeHeeftGezag(final Lo3IndicatieGezagMinderjarige indicatieGezagdMinderjarige) {
        if (Lo3IndicatieGezagMinderjarigeEnum.DERDE.equalsElement(indicatieGezagdMinderjarige)
                || Lo3IndicatieGezagMinderjarigeEnum.OUDER_1_EN_DERDE.equalsElement(indicatieGezagdMinderjarige)
                || Lo3IndicatieGezagMinderjarigeEnum.OUDER_2_EN_DERDE.equalsElement(indicatieGezagdMinderjarige)) {
            return true;
        } else {
            return null;
        }
    }

    /**
     * Converteer een indicatie onder curatele.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final Boolean converteerOnderCuratele(final Lo3IndicatieCurateleregister waarde) {
        return conversietabellen.createIndicatieCurateleConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een soort verbintenis.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpSoortRelatieCode converteerLo3SoortVerbintenis(final Lo3SoortVerbintenis waarde) {
        return conversietabellen.createSoortRelatieConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een geslachtsaanduiding.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */

    public final BrpGeslachtsaanduidingCode converteerLo3Geslachtsaanduiding(final Lo3Geslachtsaanduiding waarde) {
        return conversietabellen.createGeslachtsaanduidingConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een gemeente code.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpGemeenteCode converteerLo3GemeenteCode(final Lo3GemeenteCode waarde) {
        return conversietabellen.createGemeenteConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een land code.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpLandCode converteerLo3LandCode(final Lo3LandCode waarde) {
        return conversietabellen.createLandConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een predikaat.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpPredikaatCode converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredikaatCode(
            final Lo3AdellijkeTitelPredikaatCode waarde) {
        final AdellijkeTitelPredikaatPaar paar =
                conversietabellen.createAdellijkeTitelPredikaatConversietabel().converteerNaarBrp(waarde);
        if (paar == null || paar.getPredikaat() == null) {
            return null;
        } else {
            return new BrpPredikaatCode(paar.getPredikaat().toString());
        }
    }

    /**
     * Converteer een voorvoegsel naar een VoorvoegselScheidingstekenPaar. Als er geen paar werd gevonden wordt een leeg
     * VoorvoegselScheidingstekenPaar geretourneerd.
     * 
     * @param waarde
     *            LO3 waarde
     * @return VoorvoegselScheidingstekenPaar
     */
    public final VoorvoegselScheidingstekenPaar converteerLo3VoorvoegselNaarVoorvoegselScheidingstekenPaar(
            final String waarde) {
        VoorvoegselScheidingstekenPaar result =
                conversietabellen.createVoorvoegselScheidingstekenConversietabel().converteerNaarBrp(waarde);
        if (result == null) {
            result = new VoorvoegselScheidingstekenPaar(null, null);
        }
        return result;
    }

    /**
     * Converteer een adellijke titel.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpAdellijkeTitelCode converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(
            final Lo3AdellijkeTitelPredikaatCode waarde) {
        final AdellijkeTitelPredikaatPaar paar =
                conversietabellen.createAdellijkeTitelPredikaatConversietabel().converteerNaarBrp(waarde);
        if (paar == null || paar.getAdellijkeTitel() == null) {
            return null;
        } else {
            return new BrpAdellijkeTitelCode(paar.getAdellijkeTitel().toString());
        }
    }

    /**
     * Converteer een reden ontbinding huwelijk.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpRedenEindeRelatieCode converteerLo3RedenOntbindingHuwelijkOfGpCode(
            final Lo3RedenOntbindingHuwelijkOfGpCode waarde) {
        return conversietabellen.createRedenEindeRelatieConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een indicatie PK volledig geconverteerd.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final Boolean converteerLo3IndicatiePKVolledigGeconverteerdCode(
            final Lo3IndicatiePKVolledigGeconverteerdCode waarde) {
        return conversietabellen.createIndicatiePKConversietabel().converteerNaarBrp(waarde);

    }

    /**
     * Converteer een indicatie geheim.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    @Definitie({ Definities.DEF035, Definities.DEF036, Definities.DEF037 })
    public final Boolean converteerLo3IndicatieGeheimCode(final Lo3IndicatieGeheimCode waarde) {
        return conversietabellen.createIndicatieGeheimConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een reden opschorting bijhouding.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    @Definitie({ Definities.DEF040, Definities.DEF041 })
    public final BrpRedenOpschortingBijhoudingCode converteerLo3RedenOpschortingBijhoudingCode(
            final Lo3RedenOpschortingBijhoudingCode waarde) {
        return conversietabellen.createRedenOpschortingBijhoudingConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een aanduiding uitgesloten kiesrecht.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final Boolean converteerLo3AanduidingUitgeslotenKiesrecht(final Lo3AanduidingUitgeslotenKiesrecht waarde) {
        return conversietabellen.createAanduidingUitgeslotenKiesrechtConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een aanduiding europees kiesrecht.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final Boolean converteerLo3AanduidingEuropeesKiesrecht(final Lo3AanduidingEuropeesKiesrecht waarde) {
        return conversietabellen.createAanduidingEuropeesKiesrechtConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een nationaliteit.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpNationaliteitCode converteerLo3NationaliteitCode(final Lo3NationaliteitCode waarde) {
        return conversietabellen.createNationaliteitConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een reden verkrijging nederlanderschap.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpRedenVerkrijgingNederlandschapCode converteerLo3RedenVerkrijgingNederlandschapCode(
            final Lo3RedenNederlandschapCode waarde) {
        final RedenVerkrijgingVerliesPaar paar =
                conversietabellen.createRedenVerkrijgingNlNationaliteitConversietabel().converteerNaarBrp(
                        waarde);
        return paar == null ? null : paar.getVerkrijging();
    }

    /**
     * Converteer een reden verlies nederlanderschap.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpRedenVerliesNederlandschapCode converteerLo3RedenVerliesNederlandschapCode(
            final Lo3RedenNederlandschapCode waarde) {
        final RedenVerkrijgingVerliesPaar paar =
                conversietabellen.createRedenVerliesNlNationaliteitConversietabel().converteerNaarBrp(waarde);
        return paar == null ? null : paar.getVerlies();
    }

    /**
     * Converteer een wijze gebruik geslachtsnaam.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpWijzeGebruikGeslachtsnaamCode converteerLo3AanduidingNaamgebruikCode(
            final Lo3AanduidingNaamgebruikCode waarde) {
        return conversietabellen.createWijzeGebruikGeslachtsnaamConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een reden ontbreken reisdocument.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpReisdocumentRedenOntbreken converteerLo3AanduidingInhoudingVermissingNederlandReisdocument(
            final Lo3AanduidingInhoudingVermissingNederlandsReisdocument waarde) {
        return conversietabellen.createReisdocumentRedenOntbrekenConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een soort reisdocument.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpReisdocumentSoort converteerLo3ReisdocumentSoort(final Lo3SoortNederlandsReisdocument waarde) {
        return conversietabellen.createSoortReisdocumentConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een autoriteit van afgifte.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpReisdocumentAutoriteitVanAfgifte converteerLo3AutoriteitVanAfgifteNederlandsReisdocument(
            final Lo3AutoriteitVanAfgifteNederlandsReisdocument waarde) {
        return conversietabellen.createReisdocumentAutoriteitVanAfgifteConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een functie adres.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpFunctieAdresCode converteerLo3FunctieAdres(final Lo3FunctieAdres waarde) {
        return conversietabellen.createFunctieAdresConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een reden wijziging adres.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpRedenWijzigingAdresCode converteerLo3AangifteAdreshoudingNaarBrpRedenWijziging(
            final Lo3AangifteAdreshouding waarde) {
        final AangeverAdreshoudingRedenWijzigingAdresPaar brpPaar =
                conversietabellen.createAangeverAdreshoudingRedenWijzigingAdresConversietabel().converteerNaarBrp(
                        waarde);
        if (brpPaar == null) {
            return null;
        }
        return brpPaar.getBrpRedenWijzigingAdresCode();
    }

    /**
     * Converteer een aangever adreshouding.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpAangeverAdreshoudingCode converteerLo3AangifteAdreshoudingNaarBrpAangeverAdreshouding(
            final Lo3AangifteAdreshouding waarde) {
        final AangeverAdreshoudingRedenWijzigingAdresPaar brpPaar =
                conversietabellen.createAangeverAdreshoudingRedenWijzigingAdresConversietabel().converteerNaarBrp(
                        waarde);
        if (brpPaar == null) {
            return null;
        }
        return brpPaar.getBrpAangeverAdreshoudingCode();
    }

    /**
     * Converteer een plaats.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpPlaatsCode converteerLo3Woonplaatsnaam(final String waarde) {
        return conversietabellen.createPlaatsConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een verblijfsrecht.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpVerblijfsrechtCode converteerLo3AanduidingVerblijfstitelCode(
            final Lo3AanduidingVerblijfstitelCode waarde) {
        return conversietabellen.createVerblijfsrechtConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een partij.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpPartijCode converteerLo3GemeenteCodeNaarBrpPartijCode(final Lo3GemeenteCode waarde) {
        return conversietabellen.createPartijConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een aanduiding huisnummer.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpAanduidingBijHuisnummerCode
            converteerAanduidingBijHuisnummer(final Lo3AanduidingHuisnummer waarde) {
        return conversietabellen.createAanduidingHuisnummerConversietabel().converteerNaarBrp(waarde);

    }

    /**
     * Converteer een signalering.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final Boolean converteerSignalering(final Lo3Signalering waarde) {
        return conversietabellen.createSignaleringConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een aanduiding bezit buitenlands reisdocument.
     * 
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final Boolean converteerAanduidingBezitBuitenlandsReisdocument(
            final Lo3AanduidingBezitBuitenlandsReisdocument waarde) {
        return conversietabellen.createAanduidingBezitBuitenlandsReisdocumentConversietabel().converteerNaarBrp(
                waarde);
    }
}
