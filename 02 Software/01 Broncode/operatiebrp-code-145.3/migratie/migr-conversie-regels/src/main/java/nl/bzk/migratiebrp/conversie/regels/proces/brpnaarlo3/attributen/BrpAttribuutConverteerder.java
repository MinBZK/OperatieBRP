/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import org.springframework.stereotype.Component;

/**
 * Converteerder helpers (BRP -> LO3).
 */
@Component

public class BrpAttribuutConverteerder {

    private static final long VERSIENUMMER_LIMIET_LO3 = 10_000L;

    private ConversietabelFactory conversietabellen;

    /**
     * Constructor.
     * @param conversietabellen conversietabel factory
     */
    @Inject
    public BrpAttribuutConverteerder(final ConversietabelFactory conversietabellen) {
        this.conversietabellen = conversietabellen;
    }

    /**
     * Converteer een datum.
     * @param value brp waarde
     * @return lo3 waarde
     */
    public Lo3Datum converteerDatum(final BrpDatum value) {
        return value == null ? null : value.converteerNaarLo3Datum();
    }

    /**
     * Converteer een datum.
     * @param value brp waarde
     * @return lo3 waarde
     */
    public Lo3Datum converteerDatum(final BrpInteger value) {
        Lo3Datum lo3Datum = null;
        if (value != null) {
            final BrpDatum brpDatum = BrpDatum.wrap(value.getWaarde(), value.getOnderzoek());
            lo3Datum = brpDatum == null ? null : brpDatum.converteerNaarLo3Datum();
        }
        return lo3Datum;
    }

    /**
     * Converteer een string.
     * @param value brp waarde
     * @return lo3 waarde
     */
    public Lo3String converteerString(final BrpString value) {
        return value == null ? null : new Lo3String(value.getWaarde(), value.getOnderzoek());
    }

    /**
     * Converteer een character.
     * @param value brp waarde
     * @return lo3 waarde
     */
    public Lo3Character converteerCharacter(final BrpCharacter value) {
        if (value == null) {
            return null;
        }
        String waarde = null;
        if (value.getWaarde() != null) {
            waarde = String.valueOf(value.getWaarde());
        }
        return new Lo3Character(waarde, value.getOnderzoek());
    }

    /**
     * Converteer versienummer.
     * @param value brp waarde van versienummer
     * @return Lo3 waarde
     */
    public Lo3Integer converteerVersienummer(final BrpLong value) {
        final NumberFormat versienummerFormat = new DecimalFormat("0000");
        Lo3Integer result = null;
        if (value != null) {
            final long versienummer = BrpLong.unwrap(value) % VERSIENUMMER_LIMIET_LO3;
            result = new Lo3Integer(versienummerFormat.format(versienummer), value.getOnderzoek());
        }
        return result;
    }

    /**
     * Converteer burgerservicenummer.
     * @param value brp waarde van burgerservicenummer
     * @return Lo3 waarde
     */
    public Lo3String converteerBurgerservicenummer(final BrpString value) {
        Lo3String result = null;
        if (value != null) {
            final String burgerservicenummer = BrpString.unwrap(value);
            result = new Lo3String(burgerservicenummer, value.getOnderzoek());
        }
        return result;
    }

    /**
     * Converteer administratie nummer.
     * @param value brp waarde van administratie nummer
     * @return Lo3 waarde
     */
    public Lo3String converteerAdministratieNummer(final BrpString value) {
        final Lo3String result;
        if (value != null) {
            final String administratieNummer = BrpString.unwrap(value);
            result = new Lo3String(administratieNummer, value.getOnderzoek());
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Converteer een datumtijd.
     * @param value brp waarde
     * @return lo3 waarde
     */
    public Lo3Datum converteerDatum(final BrpDatumTijd value) {
        return value == null ? null : value.converteerNaarLo3Datum();
    }

    /**
     * Converteer een gemeentecode.
     * @param value brp waarde
     * @return lo3 waarde
     */
    public Lo3GemeenteCode converteerGemeenteCode(final BrpGemeenteCode value) {
        return conversietabellen.createGemeenteConversietabel().converteerNaarLo3(value);
    }

    /**
     * Converteer een gemeentecode.
     * @param value brp waarde
     * @return lo3 waarde
     */
    public Lo3GemeenteCode converteerGemeenteCode(final BrpPartijCode value) {
        return conversietabellen.createPartijConversietabel().converteerNaarLo3(value);
    }

    /**
     * Converteer een land code.
     * @param value brp waarde
     * @return lo3 waarde
     */
    public Lo3LandCode converteerLandCode(final BrpLandOfGebiedCode value) {
        return conversietabellen.createLandConversietabel().converteerNaarLo3(value);
    }

    /**
     * Converteer een aanduiding naamgebruik code.
     * @param naamgebruikCode brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingNaamgebruikCode converteerAanduidingNaamgebruik(final BrpNaamgebruikCode naamgebruikCode) {
        return conversietabellen.createNaamgebruikConversietabel().converteerNaarLo3(naamgebruikCode);
    }

    /**
     * Converteer een geslachtsaanduiding.
     * @param geslachtsaanduiding brp waarde
     * @return lo3 waarde
     */
    public Lo3Geslachtsaanduiding converteerGeslachtsaanduiding(final BrpGeslachtsaanduidingCode geslachtsaanduiding) {
        return conversietabellen.createGeslachtsaanduidingConversietabel().converteerNaarLo3(geslachtsaanduiding);
    }

    /**
     * Converteer een adelijke titel en predicaat code.
     * @param adellijkeTitelCode brp waarde voor adelijke titel
     * @param predicaatCode brp waarde voor predicaat
     * @return lo3 waarde
     */
    @Requirement({Requirements.CEL0220, Requirements.CEL0220_BL01})
    @Definitie({Definities.DEF017, Definities.DEF018, Definities.DEF019, Definities.DEF020})
    public Lo3AdellijkeTitelPredikaatCode converteerAdellijkeTitelPredikaatCode(
            final BrpAdellijkeTitelCode adellijkeTitelCode,
            final BrpPredicaatCode predicaatCode) {
        final Character adellijkeTitel = BrpValidatie.isAttribuutGevuld(adellijkeTitelCode) ? adellijkeTitelCode.getWaarde().charAt(0) : null;
        final Lo3Onderzoek adelijkeTitelOnderzoek = adellijkeTitelCode == null ? null : adellijkeTitelCode.getOnderzoek();
        final Character predicaat = BrpValidatie.isAttribuutGevuld(predicaatCode) ? predicaatCode.getWaarde().charAt(0) : null;
        final Lo3Onderzoek predicaatOnderzoek = predicaatCode == null ? null : predicaatCode.getOnderzoek();
        final BrpGeslachtsaanduidingCode geslachtsaanduiding = bepaalGeslachtsaanduiding(adellijkeTitelCode, predicaatCode);
        final AdellijkeTitelPredikaatPaar input =
                bepaalAdellijkeTitelPredikaatPaar(
                        adellijkeTitelCode,
                        predicaatCode,
                        adellijkeTitel,
                        adelijkeTitelOnderzoek,
                        predicaat,
                        predicaatOnderzoek,
                        geslachtsaanduiding);
        return conversietabellen.createAdellijkeTitelPredikaatConversietabel().converteerNaarLo3(input);

    }

    private BrpGeslachtsaanduidingCode bepaalGeslachtsaanduiding(final BrpAdellijkeTitelCode adellijkeTitelCode, final BrpPredicaatCode predicaatCode) {
        BrpGeslachtsaanduidingCode result = BrpGeslachtsaanduidingCode.MAN;
        if (adellijkeTitelCode != null && BrpValidatie.isAttribuutGevuld(adellijkeTitelCode.getGeslachtsaanduiding())) {
            result = adellijkeTitelCode.getGeslachtsaanduiding();
        }
        if (predicaatCode != null && BrpValidatie.isAttribuutGevuld(predicaatCode.getGeslachtsaanduiding())) {
            result = predicaatCode.getGeslachtsaanduiding();
        }
        return result;
    }

    private AdellijkeTitelPredikaatPaar bepaalAdellijkeTitelPredikaatPaar(
            final BrpAdellijkeTitelCode adellijkeTitelCode,
            final BrpPredicaatCode predicaatCode,
            final Character adellijkeTitel,
            final Lo3Onderzoek adelijkeTitelOnderzoek,
            final Character predicaat,
            final Lo3Onderzoek predicaatOnderzoek,
            final BrpGeslachtsaanduidingCode geslachtsaanduiding) {
        final AdellijkeTitelPredikaatPaar result;
        if (predicaatCode == null && adellijkeTitelCode == null) {
            result = null;
        } else {
            result =
                    new AdellijkeTitelPredikaatPaar(
                            BrpCharacter.wrap(adellijkeTitel, adelijkeTitelOnderzoek),
                            BrpCharacter.wrap(predicaat, predicaatOnderzoek),
                            geslachtsaanduiding);
        }
        return result;
    }

    /**
     * Converteer een nationaliteit.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3NationaliteitCode converteerNationaliteit(final BrpNationaliteitCode waarde) {
        return conversietabellen.createNationaliteitConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een aanduiding uitgesloten kiesrecht.
     * @param indicatieUitsluitingKiesrecht brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingUitgeslotenKiesrecht converteerAanduidingUitgeslotenKiesrecht(final BrpBoolean indicatieUitsluitingKiesrecht) {
        return conversietabellen.createAanduidingUitgeslotenKiesrechtConversietabel().converteerNaarLo3(indicatieUitsluitingKiesrecht);
    }

    /**
     * Converteer een aanduiding europees kiesrecht.
     * @param deelnameEuropeseVerkiezingen brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingEuropeesKiesrecht converteerAanduidingEuropeesKiesrecht(final BrpBoolean deelnameEuropeseVerkiezingen) {
        return conversietabellen.createAanduidingEuropeesKiesrechtConversietabel().converteerNaarLo3(deelnameEuropeseVerkiezingen);
    }

    /**
     * Converteer een reden opschorting bijhouding.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3RedenOpschortingBijhoudingCode converteerRedenOpschortingBijhouding(final BrpNadereBijhoudingsaardCode waarde) {
        return conversietabellen.createRedenOpschortingBijhoudingConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een indicatie pk volledig geconverteeerd.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3IndicatiePKVolledigGeconverteerdCode converteerIndicatiePKVolledigGeconverteerd(final BrpBoolean waarde) {
        return conversietabellen.createIndicatiePKConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een indicatie geheim.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3IndicatieGeheimCode converteerIndicatieGeheim(final BrpBoolean waarde) {
        return conversietabellen.createIndicatieGeheimConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een datum tijd stempel.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3Datumtijdstempel converteerDatumtijdstempel(final BrpDatumTijd waarde) {
        return waarde == null ? null : waarde.converteerNaarLo3Datumtijdstempel();
    }

    /**
     * Converteer een functie adres.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3FunctieAdres converteerFunctieAdres(final BrpSoortAdresCode waarde) {
        return conversietabellen.createFunctieAdresConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een huisnummer .
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3Huisnummer converteerHuisnummer(final BrpInteger waarde) {
        Lo3Huisnummer result = null;
        if (waarde != null) {
            final String huisnummer = !BrpValidatie.isAttribuutGevuld(waarde) ? null : "" + waarde.getWaarde();
            result = new Lo3Huisnummer(huisnummer, waarde.getOnderzoek());
        }
        return result;
    }

    /**
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingHuisnummer converteerAanduidingHuisnummer(final BrpAanduidingBijHuisnummerCode waarde) {
        return conversietabellen.createAanduidingHuisnummerConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een aanduiding verblijfstitel.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingVerblijfstitelCode converteerAanduidingVerblijfsrecht(final BrpVerblijfsrechtCode waarde) {
        return conversietabellen.createVerblijfsrechtConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een soort nederlands residocument.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3SoortNederlandsReisdocument converteerSoortNederlandsResidocument(final BrpSoortNederlandsReisdocumentCode waarde) {
        return conversietabellen.createSoortReisdocumentConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een autoriteit van aangifte.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3AutoriteitVanAfgifteNederlandsReisdocument converteerAutoriteitVanAfgifte(final BrpReisdocumentAutoriteitVanAfgifteCode waarde) {
        if (waarde == null) {
            return null;
        }
        return new Lo3AutoriteitVanAfgifteNederlandsReisdocument(waarde.getWaarde(), waarde.getOnderzoek());
    }

    /**
     * Converteer een aanduiding inhouding vermissing nederlands reisdocument.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3AanduidingInhoudingVermissingNederlandsReisdocument converteerAanduidingInhoudingNederlandsReisdocument(
            final BrpAanduidingInhoudingOfVermissingReisdocumentCode waarde) {
        return conversietabellen.createAanduidingInhoudingVermissingReisdocumentConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een signalering.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3Signalering converteerSignalering(final BrpBoolean waarde) {
        return conversietabellen.createSignaleringConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een aangifte adres houding.
     * @param redenWijzigingVerblijfCode brp waarde, reden wijziging verblijf
     * @param aangeverCode brp waarde, aangever
     * @return lo3 waarde
     */
    public Lo3AangifteAdreshouding converteerAangifteAdreshouding(
            final BrpRedenWijzigingVerblijfCode redenWijzigingVerblijfCode,
            final BrpAangeverCode aangeverCode) {
        final AangeverRedenWijzigingVerblijfPaar input = new AangeverRedenWijzigingVerblijfPaar(aangeverCode, redenWijzigingVerblijfCode);

        return conversietabellen.createAangeverRedenWijzigingVerblijfConversietabel().converteerNaarLo3(input);
    }

    /**
     * Converteer een reden verkrijging nederlanderschap.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3RedenNederlandschapCode converteerRedenNederlanderschap(final BrpRedenVerkrijgingNederlandschapCode waarde) {
        return conversietabellen.createRedenOpnameNationaliteitConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer {@link BrpString} naar een {@link Lo3RedenNederlandschapCode}.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3RedenNederlandschapCode converteerRedenNederlanderschap(final BrpString waarde) {
        if (waarde == null) {
            return null;
        }
        return new Lo3RedenNederlandschapCode(waarde.getWaarde(), waarde.getOnderzoek());
    }

    /**
     * Converteer een reden verlies nederlanderschap.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3RedenNederlandschapCode converteerRedenNederlanderschap(final BrpRedenVerliesNederlandschapCode waarde) {
        return conversietabellen.createRedenBeeindigingNationaliteitConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een indicatie document.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3IndicatieDocument converteerIndicatieDocument(final BrpBoolean waarde) {
        return conversietabellen.createIndicatieDocumentConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een reden ontbinding huwelijk.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3RedenOntbindingHuwelijkOfGpCode converteerRedenOntbindingHuwelijk(final BrpRedenEindeRelatieCode waarde) {
        return conversietabellen.createRedenEindeRelatieConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een indicatie onder curatele.
     * @param waarde brp waarde
     * @return lo3 waarde
     */
    public Lo3IndicatieCurateleregister converteerIndicatieCurateleRegister(final BrpBoolean waarde) {
        return conversietabellen.createIndicatieCurateleConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Converteer een soort relatie code.
     * @param soortRelatieCode brp waarde
     * @return lo3 waarde
     */
    public Lo3SoortVerbintenis converteerSoortVerbintenis(final BrpSoortRelatieCode soortRelatieCode) {
        return conversietabellen.createSoortRelatieConversietabel().converteerNaarLo3(soortRelatieCode);
    }

    /**
     * Converteer een voorvoegsel.
     * @param voorvoegsel voorvoegsel
     * @param scheidingsteken scheidingsteken
     * @return lo3 waarde
     */
    @Requirement({Requirements.CEL0230, Requirements.CEL0230_BL02})
    @Definitie({Definities.DEF051, Definities.DEF052, Definities.DEF053})
    public Lo3String converteerVoorvoegsel(final BrpString voorvoegsel, final BrpCharacter scheidingsteken) {
        final VoorvoegselScheidingstekenPaar input = voorvoegsel == null ? null : new VoorvoegselScheidingstekenPaar(voorvoegsel, scheidingsteken);
        return conversietabellen.createVoorvoegselScheidingstekenConversietabel().converteerNaarLo3(input);
    }

    /**
     * Converteer een BRP locatie naar een LO3 locatie.
     * @param gemeenteCode gemeenteCode
     * @param buitenlandsePlaats buitenlandse plaats
     * @param buitenlandseRegio buitenlandse regio
     * @param landCode land code
     * @param omschrijvingLocatie omschrijving locatie
     * @return lo3 gemeente land combinatie
     */
    @Definitie({Definities.DEF004, Definities.DEF005, Definities.DEF006})
    public Lo3GemeenteLand converteerLocatie(
            final BrpGemeenteCode gemeenteCode,
            final BrpString buitenlandsePlaats,
            final BrpString buitenlandseRegio,
            final BrpLandOfGebiedCode landCode,
            final BrpString omschrijvingLocatie) {
        final Lo3GemeenteCode gemeente;

        // DEF004
        if (gemeenteCode != null) {
            gemeente = converteerGemeenteCode(gemeenteCode);
        } else if (buitenlandsePlaats != null) {
            if (buitenlandseRegio != null) {
                gemeente = new Lo3GemeenteCode(buitenlandsePlaats.getWaarde() + ", " + buitenlandseRegio.getWaarde(), buitenlandsePlaats.getOnderzoek());
            } else {
                gemeente = new Lo3GemeenteCode(buitenlandsePlaats.getWaarde(), buitenlandsePlaats.getOnderzoek());
            }
        } else if (omschrijvingLocatie != null) {
            gemeente = new Lo3GemeenteCode(omschrijvingLocatie.getWaarde(), omschrijvingLocatie.getOnderzoek());
        } else {
            gemeente = null;
        }

        return new Lo3GemeenteLand(gemeente, converteerLandCode(landCode));

    }

    /**
     * Converteer een RNI Deelnemer partijcode.
     * @param waarde brp partijcode
     * @return lo3 RNI Deelnemer waarde
     */
    public Lo3RNIDeelnemerCode converteerRNIDeelnemer(final BrpPartijCode waarde) {
        return conversietabellen.createRNIDeelnemerConversietabel().converteerNaarLo3(waarde);
    }

    /**
     * Valideert een RNI Deelnemer partijcode.
     * @param waarde brp partijcode
     * @return lo3 RNI Deelnemer waarde
     */
    public boolean valideerRNIDeelnemerTegenBrp(final BrpPartijCode waarde) {
        return conversietabellen.createRNIDeelnemerConversietabel().valideerBrp(waarde);
    }

    /**
     * Converteert een {@link BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer} naar een {@link Lo3NationaliteitCode}.
     * @param autoriteitVanAfgifte de autoriteit van afgifte buitenlands persoonsnummer
     * @return een LO3 nationaliteit code
     */
    public Lo3NationaliteitCode converteerNationaliteit(final BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer autoriteitVanAfgifte) {
        return conversietabellen.createAutoriteitVanAfgifteBuitenlandsPersoonsnummertabel().converteerNaarLo3(autoriteitVanAfgifte);
    }

    /**
     * DTO om een gemeente land combinatie door te geven.
     */
    public static final class Lo3GemeenteLand {
        private final Lo3GemeenteCode gemeenteCode;
        private final Lo3LandCode landCode;

        /**
         * Constructor.
         * @param gemeenteCode gemeente
         * @param landCode land
         */
        Lo3GemeenteLand(final Lo3GemeenteCode gemeenteCode, final Lo3LandCode landCode) {
            this.gemeenteCode = gemeenteCode;
            this.landCode = landCode;
        }

        /**
         * Geef de waarde van gemeente code.
         * @return the gemeenteCode
         */
        public Lo3GemeenteCode getGemeenteCode() {
            return gemeenteCode;
        }

        /**
         * Geef de waarde van land code.
         * @return the landCode
         */
        public Lo3LandCode getLandCode() {
            return landCode;
        }

    }

}
