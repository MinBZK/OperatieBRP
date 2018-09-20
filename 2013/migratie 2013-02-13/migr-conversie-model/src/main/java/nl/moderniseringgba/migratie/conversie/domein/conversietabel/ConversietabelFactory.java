/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;
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
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;

/**
 * Deze factory interface definieert de creatie van alle verschillende conversietabellen.
 * 
 */
public interface ConversietabelFactory {

    /**
     * @return de adellijke titel / predikaat conversietabel.
     */
    Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>
            createAdellijkeTitelPredikaatConversietabel();

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
    Conversietabel<Lo3LandCode, BrpLandCode> createLandConversietabel();

    /**
     * @return de reden einde relatie conversietabel.
     */
    Conversietabel<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode>
            createRedenEindeRelatieConversietabel();

    /**
     * @return de verblijfsrecht conversietabel.
     */
    Conversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode> createVerblijfsrechtConversietabel();

    /**
     * @return de nationaliteit conversietabel.
     */
    Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> createNationaliteitConversietabel();

    /**
     * @return de reden verkrijging NL nationaliteit conversietabel.
     */
    Conversietabel<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar>
            createRedenVerkrijgingNlNationaliteitConversietabel();

    /**
     * @return de reden verlies NL nationaliteit conversietabel.
     */
    Conversietabel<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar>
            createRedenVerliesNlNationaliteitConversietabel();

    /**
     * @return de wijze gebruik geslachtsnaam conversietabel.
     */
    Conversietabel<Lo3AanduidingNaamgebruikCode, BrpWijzeGebruikGeslachtsnaamCode>
            createWijzeGebruikGeslachtsnaamConversietabel();

    /**
     * @return de aangifte adreshouding en reden wijzging adres conversietabel.
     */
    Conversietabel<Lo3AangifteAdreshouding, AangeverAdreshoudingRedenWijzigingAdresPaar>
            createAangeverAdreshoudingRedenWijzigingAdresConversietabel();

    /**
     * @return de functie adres conversietabel.
     */
    Conversietabel<Lo3FunctieAdres, BrpFunctieAdresCode> createFunctieAdresConversietabel();

    /**
     * @return de soort reisdocument conversietabel.
     */
    Conversietabel<Lo3SoortNederlandsReisdocument, BrpReisdocumentSoort> createSoortReisdocumentConversietabel();

    /**
     * @return de reden ontbreken reisdocument conversietabel.
     */
    Conversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpReisdocumentRedenOntbreken>
            createReisdocumentRedenOntbrekenConversietabel();

    /**
     * @return de autoriteit afgifte reisdocument conversietabel.
     */
    Conversietabel<Lo3AutoriteitVanAfgifteNederlandsReisdocument, BrpReisdocumentAutoriteitVanAfgifte>
            createReisdocumentAutoriteitVanAfgifteConversietabel();

    /**
     * @return de reden opschorting bijhouding conversietabel.
     */
    Conversietabel<Lo3RedenOpschortingBijhoudingCode, BrpRedenOpschortingBijhoudingCode>
            createRedenOpschortingBijhoudingConversietabel();

    /**
     * @return de voorvoegsel/scheidingsteken conversietabel.
     */
    Conversietabel<String, VoorvoegselScheidingstekenPaar> createVoorvoegselScheidingstekenConversietabel();

    /**
     * @return de plaats conversietabel.
     */
    Conversietabel<String, BrpPlaatsCode> createPlaatsConversietabel();

    /**
     * @return de naam openbare ruimte conversietabel.
     */
    Conversietabel<String, String> createNaamOpenbareRuimteConversietabel();

    /**
     * @return de soort relatie conversietabel.
     */
    Conversietabel<Lo3SoortVerbintenis, BrpSoortRelatieCode> createSoortRelatieConversietabel();

    /**
     * @return de aanduiding uitgesloten kiesrecht conversietabel.
     */
    Conversietabel<Lo3AanduidingUitgeslotenKiesrecht, Boolean> createAanduidingUitgeslotenKiesrechtConversietabel();

    /**
     * @return de aanduiding europees kiesrecht conversietabel.
     */
    Conversietabel<Lo3AanduidingEuropeesKiesrecht, Boolean> createAanduidingEuropeesKiesrechtConversietabel();

    /**
     * @return de indicatie pk volledig geconverteerd conversietabel.
     */
    Conversietabel<Lo3IndicatiePKVolledigGeconverteerdCode, Boolean> createIndicatiePKConversietabel();

    /**
     * @return de indicatie geheim conversietabel.
     */
    Conversietabel<Lo3IndicatieGeheimCode, Boolean> createIndicatieGeheimConversietabel();

    /**
     * @return de aanduiding huisnummer conversietabel.
     */
    Conversietabel<Lo3AanduidingHuisnummer, BrpAanduidingBijHuisnummerCode>
            createAanduidingHuisnummerConversietabel();

    /**
     * @return de signalering (belemmering verstrekking reisdocument) conversietabel.
     */
    Conversietabel<Lo3Signalering, Boolean> createSignaleringConversietabel();

    /**
     * @return de aanduiding bezit buitenlands reisdocument conversietabel.
     */
    Conversietabel<Lo3AanduidingBezitBuitenlandsReisdocument, Boolean>
            createAanduidingBezitBuitenlandsReisdocumentConversietabel();

    /**
     * @return de indicatie document conversietabel.
     */
    Conversietabel<Lo3IndicatieDocument, Boolean> createIndicatieDocumentConversietabel();

    /**
     * @return de indicatie curatele conversietabel.
     */
    Conversietabel<Lo3IndicatieCurateleregister, Boolean> createIndicatieCurateleConversietabel();
}
