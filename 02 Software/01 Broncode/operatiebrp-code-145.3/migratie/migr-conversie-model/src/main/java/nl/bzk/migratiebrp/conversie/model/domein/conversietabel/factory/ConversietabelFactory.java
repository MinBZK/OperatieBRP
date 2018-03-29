/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpProtocolleringsniveauCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;

/**
 * Deze factory interface definieert de creatie van alle verschillende conversietabellen.
 */
public interface ConversietabelFactory {

    /**
     * @return de adellijke titel / predikaat conversietabel.
     */
    Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> createAdellijkeTitelPredikaatConversietabel();

    /**
     * @return de partij conversietabel.
     */
    Conversietabel<Lo3GemeenteCode, BrpPartijCode> createPartijConversietabel();

    /**
     * @return de partij conversietabel.
     */
    Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> createGemeenteConversietabel();

    /**
     * @return de geslachtsaanduiding conversietabel.
     */
    Conversietabel<Lo3Geslachtsaanduiding, BrpGeslachtsaanduidingCode> createGeslachtsaanduidingConversietabel();

    /**
     * @return de land conversietabel.
     */
    Conversietabel<Lo3LandCode, BrpLandOfGebiedCode> createLandConversietabel();

    /**
     * @return de reden einde relatie conversietabel.
     */
    Conversietabel<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode> createRedenEindeRelatieConversietabel();

    /**
     * @return de verblijfstitel conversietabel.
     */
    Conversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode> createVerblijfsrechtConversietabel();

    /**
     * @return de nationaliteit conversietabel.
     */
    Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> createNationaliteitConversietabel();

    /**
     * @return de reden opname nationaliteit conversietabel.
     */
    Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerkrijgingNederlandschapCode> createRedenOpnameNationaliteitConversietabel();

    /**
     * @return de reden beeindiging nationaliteit conversietabel.
     */
    Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode> createRedenBeeindigingNationaliteitConversietabel();

    /**
     * @return de naamgebruik conversietabel.
     */
    Conversietabel<Lo3AanduidingNaamgebruikCode, BrpNaamgebruikCode> createNaamgebruikConversietabel();

    /**
     * @return de aangifte adreshouding en aangever reden wijzging verblijf paar conversietabel.
     */
    Conversietabel<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar> createAangeverRedenWijzigingVerblijfConversietabel();

    /**
     * @return de functie adres conversietabel.
     */
    Conversietabel<Lo3FunctieAdres, BrpSoortAdresCode> createFunctieAdresConversietabel();

    /**
     * @return de soort reisdocument conversietabel.
     */
    Conversietabel<Lo3SoortNederlandsReisdocument, BrpSoortNederlandsReisdocumentCode> createSoortReisdocumentConversietabel();

    /**
     * @return de reden ontbreken reisdocument conversietabel.
     */
    Conversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpAanduidingInhoudingOfVermissingReisdocumentCode>
    createAanduidingInhoudingVermissingReisdocumentConversietabel();

    /**
     * @return de reden opschorting bijhouding conversietabel.
     */
    Conversietabel<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode> createRedenOpschortingBijhoudingConversietabel();

    /**
     * @return de rni deelnemer conversietabel.
     */
    Conversietabel<Lo3RNIDeelnemerCode, BrpPartijCode> createRNIDeelnemerConversietabel();

    /**
     * @return de voorvoegsel/scheidingsteken conversietabel.
     */
    Conversietabel<Lo3String, VoorvoegselScheidingstekenPaar> createVoorvoegselScheidingstekenConversietabel();

    /**
     * @return de woonplaatsnaam conversietabel.
     */
    Conversietabel<String, String> createWoonplaatsnaamConversietabel();

    /**
     * @return de soort relatie conversietabel.
     */
    Conversietabel<Lo3SoortVerbintenis, BrpSoortRelatieCode> createSoortRelatieConversietabel();

    /**
     * @return de aanduiding uitgesloten kiesrecht conversietabel.
     */
    Conversietabel<Lo3AanduidingUitgeslotenKiesrecht, BrpBoolean> createAanduidingUitgeslotenKiesrechtConversietabel();

    /**
     * @return de aanduiding europees kiesrecht conversietabel.
     */
    Conversietabel<Lo3AanduidingEuropeesKiesrecht, BrpBoolean> createAanduidingEuropeesKiesrechtConversietabel();

    /**
     * @return de indicatie pk volledig geconverteerd conversietabel.
     */
    Conversietabel<Lo3IndicatiePKVolledigGeconverteerdCode, BrpBoolean> createIndicatiePKConversietabel();

    /**
     * @return de indicatie geheim conversietabel.
     */
    Conversietabel<Lo3IndicatieGeheimCode, BrpBoolean> createIndicatieGeheimConversietabel();

    /**
     * @return de aanduiding huisnummer conversietabel.
     */
    Conversietabel<Lo3AanduidingHuisnummer, BrpAanduidingBijHuisnummerCode> createAanduidingHuisnummerConversietabel();

    /**
     * @return de signalering (belemmering verstrekking reisdocument) conversietabel.
     */
    Conversietabel<Lo3Signalering, BrpBoolean> createSignaleringConversietabel();

    /**
     * @return de indicatie document conversietabel.
     */
    Conversietabel<Lo3IndicatieDocument, BrpBoolean> createIndicatieDocumentConversietabel();

    /**
     * @return de indicatie curatele conversietabel.
     */
    Conversietabel<Lo3IndicatieCurateleregister, BrpBoolean> createIndicatieCurateleConversietabel();

    /**
     * @return de soort register soort document conversietabel.
     */
    Conversietabel<Character, BrpSoortDocumentCode> createSoortRegisterSoortDocumentConversietabel();

    /**
     * @return de verstrekkingsbeperking conversietabel.
     */
    Conversietabel<Integer, BrpProtocolleringsniveauCode> createVerstrekkingsbeperkingConversietabel();

    /**
     * @return De conversietabel voor lo3 rubrieken.
     */
    Conversietabel<String, String> createLo3RubriekConversietabel();

    /**
     * @return De conversietabel voor brp regels.
     */
    Conversietabel<Character, String> createRegelConversietabel();

    /**
     * @return de conversietabel voor autorisatie afgifte buitenlands persoonsnummer.
     */
    Conversietabel<Lo3NationaliteitCode, BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer> createAutoriteitVanAfgifteBuitenlandsPersoonsnummertabel();

    /**
     * @return de conversietabel voor soort selectie.
     */
    Conversietabel<Integer, SoortSelectie> createSoortSelectieConversietabel();

    /**
     * @return de conversietabel voor bericht aanduiding.
     */
    Conversietabel<Integer, Boolean> createBerichtaanduidingConversietabel();

    /**
     * @return de conversietabel voor medium selectie.
     */
    Conversietabel<String, LeverwijzeSelectie> createMediumSelectieConversietabel();
}
