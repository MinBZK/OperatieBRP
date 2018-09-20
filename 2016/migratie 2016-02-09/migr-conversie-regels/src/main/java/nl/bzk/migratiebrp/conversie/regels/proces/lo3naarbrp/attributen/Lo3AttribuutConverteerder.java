/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpProtocolleringsniveauCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingBijzonderNederlandschapEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
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
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

import org.springframework.stereotype.Component;

/**
 * Converteerder utility methoden.
 */
@Component
public class Lo3AttribuutConverteerder {

    @Inject
    private ConversietabelFactory conversietabellen;

    /**
     * Converteer een Lo3String naar BrpString.
     *
     * @param lo3String
     *            LO3 String
     * @return BrpString gevuld met de waarde en onderzoek uit Lo3String
     */
    public final BrpString converteerString(final Lo3String lo3String) {
        return lo3String == null ? null : new BrpString(lo3String.getWaarde(), lo3String.getOnderzoek());
    }

    /**
     * Converteer een Lo3Character naar BrpCharacter.
     *
     * @param lo3Character
     *            LO3 Character
     * @return BrpCharacter gevuld met de waarde en onderzoek uit Lo3Character
     */
    public final BrpCharacter converteerCharacter(final Lo3Character lo3Character) {
        return lo3Character == null ? null : new BrpCharacter(lo3Character.getCharacterWaarde(), lo3Character.getOnderzoek());
    }

    /**
     * Converteer een Lo3Integer naar BrpInteger.
     *
     * @param lo3Integer
     *            LO3 Integer
     * @return BrpInteger gevuld met de waarde en onderzoek uit Lo3Integer
     */
    public final BrpInteger converteerInteger(final Lo3Integer lo3Integer) {
        return lo3Integer == null ? null : new BrpInteger(lo3Integer.getIntegerWaarde(), lo3Integer.getOnderzoek());
    }

    /**
     * Converteer een Lo3Integer naar BrpLong.
     *
     * @param lo3Integer
     *            LO3 Integer
     * @return BrpLong gevuld met de waarde en onderzoek uit Lo3Integer
     */
    public final BrpLong converteerLong(final Lo3Integer lo3Integer) {
        return lo3Integer == null ? null : new BrpLong(Long.valueOf(lo3Integer.getWaarde()), lo3Integer.getOnderzoek());
    }

    /**
     * Converteer een Lo3Long naar BrpLong.
     *
     * @param lo3Long
     *            LO3 Long
     * @return BrpLong gevuld met de waarde en onderzoek uit Lo3Long
     */
    public final BrpLong converteerLong(final Lo3Long lo3Long) {
        return lo3Long == null ? null : new BrpLong(lo3Long.getLongWaarde(), lo3Long.getOnderzoek());
    }

    /**
     * Converteer een datum.
     *
     * @param datum
     *            lo3 datum, mag null zijn
     * @return brp datum, null als datum null is
     */
    public final BrpDatum converteerDatum(final Lo3Datum datum) {
        return datum == null ? null : BrpDatum.fromLo3Datum(datum);
    }

    /**
     * Converteer een datum naar Integer.
     *
     * @param datum
     *            lo3 datum
     * @return BRP integer, null als datum null is
     */
    public final BrpInteger converteerDatumToInteger(final Lo3Datum datum) {
        return datum == null ? null : new BrpInteger(datum.getIntegerWaarde(), datum.getOnderzoek());
    }

    /**
     * Converteer een indicatie document.
     *
     * @param waarde
     *            lo3 indicatie document, mag null zijn
     * @return true als indicatie aanwezig is, anders false
     */
    public final BrpBoolean converteerIndicatieDocument(final Lo3IndicatieDocument waarde) {
        return conversietabellen.createIndicatieDocumentConversietabel().converteerNaarBrp(waarde);

    }

    /**
     * Converteer een huisnummer.
     *
     * @param huisnummer
     *            lo3 huisnummer, mag null zijn
     * @return brp huisnummer, null als huisnummer null is
     */
    public final BrpInteger converteerHuisnummer(final Lo3Huisnummer huisnummer) {
        return huisnummer == null ? null : BrpInteger.wrap(huisnummer.getIntegerWaarde(), huisnummer.getOnderzoek());
    }

    /**
     * Converteer een indicatie gezag minderjarige naar derde heeft gezag.
     *
     * @param indicatieGezagdMinderjarige
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpBoolean converteerDerdeHeeftGezag(final Lo3IndicatieGezagMinderjarige indicatieGezagdMinderjarige) {
        if (Lo3IndicatieGezagMinderjarigeEnum.DERDE.equalsElement(indicatieGezagdMinderjarige)
                || Lo3IndicatieGezagMinderjarigeEnum.OUDER_1_EN_DERDE.equalsElement(indicatieGezagdMinderjarige)
                || Lo3IndicatieGezagMinderjarigeEnum.OUDER_2_EN_DERDE.equalsElement(indicatieGezagdMinderjarige))
        {
            return BrpBoolean.wrap(true, indicatieGezagdMinderjarige.getOnderzoek());
        } else {
            return null;
        }
    }

    /**
     * Converteer een indicatie gezag minderjarige naar ouder1 heeft gezag.
     *
     * @param indicatieGezagMinderjarige
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpBoolean converteerOuder1HeeftGezag(final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige) {
        if (!Validatie.isElementGevuld(indicatieGezagMinderjarige)) {
            return null;
        }

        final boolean heeftGezag =
                Lo3IndicatieGezagMinderjarigeEnum.OUDER_1.equalsElement(indicatieGezagMinderjarige)
                        || Lo3IndicatieGezagMinderjarigeEnum.OUDER_1_EN_DERDE.equalsElement(indicatieGezagMinderjarige)
                        || Lo3IndicatieGezagMinderjarigeEnum.OUDERS_1_EN_2.equalsElement(indicatieGezagMinderjarige);

        return BrpBoolean.wrap(heeftGezag, indicatieGezagMinderjarige.getOnderzoek());
    }

    /**
     * Converteer een indicatie gezag minderjarige naar derde heeft gezag.
     *
     * @param indicatieGezagMinderjarige
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpBoolean converteerOuder2HeeftGezag(final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige) {
        if (!Validatie.isElementGevuld(indicatieGezagMinderjarige)) {
            return null;
        }

        final boolean heeftGezag =
                Lo3IndicatieGezagMinderjarigeEnum.OUDER_2.equalsElement(indicatieGezagMinderjarige)
                        || Lo3IndicatieGezagMinderjarigeEnum.OUDER_2_EN_DERDE.equalsElement(indicatieGezagMinderjarige)
                        || Lo3IndicatieGezagMinderjarigeEnum.OUDERS_1_EN_2.equalsElement(indicatieGezagMinderjarige);

        return BrpBoolean.wrap(heeftGezag, indicatieGezagMinderjarige.getOnderzoek());
    }

    /**
     * Converteer een indicatie onder curatele.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpBoolean converteerOnderCuratele(final Lo3IndicatieCurateleregister waarde) {
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
     * Converteer een LO3 aktenummer.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpSoortDocumentCode converteerLo3Aktenummer(final Character waarde) {
        return conversietabellen.createSoortRegisterSoortDocumentConversietabel().converteerNaarBrp(waarde);
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
    public final BrpLandOfGebiedCode converteerLo3LandCode(final Lo3LandCode waarde) {
        return conversietabellen.createLandConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een Lo3 predikaat naar een BRP predicaat. (N.b. spelling 'predicaat' verschilt tussen Lo3 en BRP.)
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpPredicaatCode converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredicaatCode(final Lo3AdellijkeTitelPredikaatCode waarde) {
        final AdellijkeTitelPredikaatPaar paar = conversietabellen.createAdellijkeTitelPredikaatConversietabel().converteerNaarBrp(waarde);
        if (paar == null || paar.getPredikaat() == null) {
            return null;
        } else {
            final String predicaatString = paar.getPredikaat().getWaarde() != null ? paar.getPredikaat().getWaarde().toString() : null;
            final BrpPredicaatCode brpPredicaatCode = new BrpPredicaatCode(predicaatString, paar.getPredikaat().getOnderzoek());
            brpPredicaatCode.setGeslachtsaanduiding(paar.getGeslachtsaanduiding());
            return brpPredicaatCode;
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
    public final VoorvoegselScheidingstekenPaar converteerLo3VoorvoegselNaarVoorvoegselScheidingstekenPaar(final Lo3String waarde) {
        VoorvoegselScheidingstekenPaar result = conversietabellen.createVoorvoegselScheidingstekenConversietabel().converteerNaarBrp(waarde);
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
    public final BrpAdellijkeTitelCode converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(final Lo3AdellijkeTitelPredikaatCode waarde) {
        final AdellijkeTitelPredikaatPaar paar = conversietabellen.createAdellijkeTitelPredikaatConversietabel().converteerNaarBrp(waarde);
        if (paar == null || paar.getAdellijkeTitel() == null) {
            return null;
        } else {
            final String adelijkeTitelString = paar.getAdellijkeTitel().getWaarde() != null ? paar.getAdellijkeTitel().getWaarde().toString() : null;
            final BrpAdellijkeTitelCode brpAdelijkeTitelCode = new BrpAdellijkeTitelCode(adelijkeTitelString, paar.getAdellijkeTitel().getOnderzoek());
            brpAdelijkeTitelCode.setGeslachtsaanduiding(paar.getGeslachtsaanduiding());
            return brpAdelijkeTitelCode;
        }
    }

    /**
     * Converteer een reden ontbinding huwelijk.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpRedenEindeRelatieCode converteerLo3RedenOntbindingHuwelijkOfGpCode(final Lo3RedenOntbindingHuwelijkOfGpCode waarde) {
        return conversietabellen.createRedenEindeRelatieConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een indicatie PK volledig geconverteerd.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpBoolean converteerLo3IndicatiePKVolledigGeconverteerdCode(final Lo3IndicatiePKVolledigGeconverteerdCode waarde) {
        return conversietabellen.createIndicatiePKConversietabel().converteerNaarBrp(waarde);

    }

    /**
     * Converteer een indicatie geheim.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    @Definitie({Definities.DEF035, Definities.DEF036, Definities.DEF037 })
    public final BrpBoolean converteerLo3IndicatieGeheimCode(final Lo3IndicatieGeheimCode waarde) {
        return conversietabellen.createIndicatieGeheimConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een reden opschorting bijhouding.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    @Definitie({Definities.DEF078, Definities.DEF079 })
    public final BrpNadereBijhoudingsaardCode converteerLo3RedenOpschortingBijhoudingCode(final Lo3RedenOpschortingBijhoudingCode waarde) {
        return conversietabellen.createRedenOpschortingBijhoudingConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een aanduiding uitgesloten kiesrecht.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpBoolean converteerLo3AanduidingUitgeslotenKiesrecht(final Lo3AanduidingUitgeslotenKiesrecht waarde) {
        return conversietabellen.createAanduidingUitgeslotenKiesrechtConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een aanduiding europees kiesrecht.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpBoolean converteerLo3AanduidingEuropeesKiesrecht(final Lo3AanduidingEuropeesKiesrecht waarde) {
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
    public final BrpRedenVerkrijgingNederlandschapCode converteerLo3RedenVerkrijgingNederlandschapCode(final Lo3RedenNederlandschapCode waarde) {
        return conversietabellen.createRedenOpnameNationaliteitConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een reden verkrijging nederlanderschap naar een {@link BrpString}.
     *
     * @param waarde
     *            LO3 waarde
     * @return Een {@link BrpString} object
     */
    public final BrpString converteerLo3RedenVerkrijgingNederlandschapCodeToBrpString(final Lo3RedenNederlandschapCode waarde) {
        return waarde == null ? null : new BrpString(waarde.getWaarde(), waarde.getOnderzoek());
    }

    /**
     * Converteer een reden verlies nederlanderschap.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpRedenVerliesNederlandschapCode converteerLo3RedenVerliesNederlandschapCode(final Lo3RedenNederlandschapCode waarde) {
        return conversietabellen.createRedenBeeindigingNationaliteitConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een wijze gebruik geslachtsnaam.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpNaamgebruikCode converteerLo3AanduidingNaamgebruikCode(final Lo3AanduidingNaamgebruikCode waarde) {
        return conversietabellen.createNaamgebruikConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een reden ontbreken reisdocument.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpAanduidingInhoudingOfVermissingReisdocumentCode converteerLo3AanduidingInhoudingVermissingNederlandReisdocument(
        final Lo3AanduidingInhoudingVermissingNederlandsReisdocument waarde)
    {
        return conversietabellen.createAanduidingInhoudingVermissingReisdocumentConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een soort reisdocument.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpSoortNederlandsReisdocumentCode converteerLo3ReisdocumentSoort(final Lo3SoortNederlandsReisdocument waarde) {
        return conversietabellen.createSoortReisdocumentConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een autoriteit van afgifte.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpReisdocumentAutoriteitVanAfgifteCode converteerLo3AutoriteitVanAfgifteNederlandsReisdocument(
        final Lo3AutoriteitVanAfgifteNederlandsReisdocument waarde)
    {
        if (waarde == null) {
            return null;
        }
        return new BrpReisdocumentAutoriteitVanAfgifteCode(waarde.getWaarde(), waarde.getOnderzoek());
    }

    /**
     * Converteer een functie adres.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpSoortAdresCode converteerLo3FunctieAdres(final Lo3FunctieAdres waarde) {
        return conversietabellen.createFunctieAdresConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een reden wijziging adres.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpRedenWijzigingVerblijfCode converteerLo3AangifteAdreshoudingNaarBrpRedenWijziging(final Lo3AangifteAdreshouding waarde) {
        final AangeverRedenWijzigingVerblijfPaar brpPaar =
                conversietabellen.createAangeverRedenWijzigingVerblijfConversietabel().converteerNaarBrp(waarde);
        if (brpPaar == null) {
            return null;
        }
        return brpPaar.getBrpRedenWijzigingVerblijfCode();
    }

    /**
     * Converteer een aangever adreshouding.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpAangeverCode converteerLo3AangifteAdreshoudingNaarBrpAangeverAdreshouding(final Lo3AangifteAdreshouding waarde) {
        final AangeverRedenWijzigingVerblijfPaar brpPaar =
                conversietabellen.createAangeverRedenWijzigingVerblijfConversietabel().converteerNaarBrp(waarde);
        if (brpPaar == null) {
            return null;
        }
        return brpPaar.getBrpAangeverCode();
    }

    /**
     * Converteer een verblijfstitel.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpVerblijfsrechtCode converteerLo3AanduidingVerblijfstitelCode(final Lo3AanduidingVerblijfstitelCode waarde) {
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
    public final BrpAanduidingBijHuisnummerCode converteerAanduidingBijHuisnummer(final Lo3AanduidingHuisnummer waarde) {
        return conversietabellen.createAanduidingHuisnummerConversietabel().converteerNaarBrp(waarde);

    }

    /**
     * Converteer een signalering.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpBoolean converteerSignalering(final Lo3Signalering waarde) {
        return conversietabellen.createSignaleringConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een lo3 rubriek.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final String converteerFilterRubriek(final String waarde) {
        return conversietabellen.createLo3RubriekConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een verstrekkingsbeperking.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde
     */
    public final BrpProtocolleringsniveauCode converteerVerstrekkingsbeperking(final Integer waarde) {
        return conversietabellen.createVerstrekkingsbeperkingConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer een RNI Deelnemer.
     *
     * @param waarde
     *            LO3 waarde
     * @return BRP waarde (partij)
     */
    public final BrpPartijCode converteerRNIDeelnemer(final Lo3RNIDeelnemerCode waarde) {
        return conversietabellen.createRNIDeelnemerConversietabel().converteerNaarBrp(waarde);
    }

    /**
     * Converteer nationliteit naar een staatloos indicatie.
     *
     * @param nationaliteit
     *            Lo3 Nationaliteit code
     * @return BrpBoolean met de waarde true als de nationaliteit 'staatloos' is, anders null.
     */
    public final BrpBoolean converteerStaatloosIndicatie(final Lo3NationaliteitCode nationaliteit) {
        BrpBoolean result = null;
        if (nationaliteit != null
                && nationaliteit.isInhoudelijkGevuld()
                && Lo3NationaliteitCode.NATIONALITEIT_CODE_STAATLOOS.equals(nationaliteit.getWaarde()))
        {
            result = new BrpBoolean(true, nationaliteit.getOnderzoek());
        }
        return result;
    }

    /**
     * Converteer een aanuiding bijzonder Nederlandschap naar een behandeld als nederlander indicatie.
     *
     * @param aanduiding
     *            aanduiding bijzonder Nederlandschap
     * @return BrpBoolean met waarde true als aanduiding 'Behandeld als Nederlander' is, anders null.
     */
    public final BrpBoolean converteerBehandeldAlsNederlander(final Lo3AanduidingBijzonderNederlandschap aanduiding) {
        BrpBoolean result = null;
        if (aanduiding != null
                && aanduiding.isInhoudelijkGevuld()
                && Lo3AanduidingBijzonderNederlandschapEnum.BEHANDELD_ALS_NEDERLANDER.equalsElement(aanduiding))
        {
            result = new BrpBoolean(true, aanduiding.getOnderzoek());
        }
        return result;
    }

    /**
     * Converteer een aanuiding bijzonder Nederlandschap naar een vastgesteld niet Nederlander indicatie.
     *
     * @param aanduiding
     *            aanduiding bijzonder Nederlandschap
     * @return BrpBoolean met waarde true als aanduiding 'Vastgesteld niet Nederlander' is, anders null.
     */
    public final BrpBoolean converteerVastgesteldNietNederlander(final Lo3AanduidingBijzonderNederlandschap aanduiding) {
        BrpBoolean result = null;
        if (aanduiding != null
                && aanduiding.isInhoudelijkGevuld()
                && Lo3AanduidingBijzonderNederlandschapEnum.VASTGESTELD_NIET_NEDERLANDER.equalsElement(aanduiding))
        {
            result = new BrpBoolean(true, aanduiding.getOnderzoek());
        }
        return result;
    }

    /**
     * Converteer een indicatie onjuist naar een BRP Character.
     *
     * @param indicatieOnjuist
     *            de Lo3 indicatie onjuist
     * @return BRP character
     */
    public final BrpCharacter converteerOnjuistIndicatie(final Lo3IndicatieOnjuist indicatieOnjuist) {
        return indicatieOnjuist == null ? null : new BrpCharacter(indicatieOnjuist.getCodeAsCharacter(), indicatieOnjuist.getOnderzoek());
    }
}
