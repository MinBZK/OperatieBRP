/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AangeverAdreshouding;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AutoriteitVanAfgifteReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Land;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Nationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Plaats;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVervallenReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenWijzigingAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortDocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verblijfsrecht;

/**
 * De target(adapter) interface voor alle methoden om gegevens uit dynamische stamtabellen op te zoeken a.d.h.v. de code
 * uit het brp migratie model. Het standaard gedrag is dat null waarden gemapped worden op null waarden.
 * 
 */
// CHECKSTYLE:OFF Class Fan-Out complexity is hier hoger dan 20 maar levert geen complexe class op
public interface StamtabelMapping {
    // CHECKSTYLE:ON

    /**
     * @param redenVerliesNederlandschapCode
     *            de reden verlies NLschap code, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    RedenVerliesNLNationaliteit findRedenVerliesNLNationaliteitByCode(
            final BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode);

    /**
     * @param redenVerkrijgingNederlandschapCode
     *            de reden verkrijging NLschap code, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    RedenVerkrijgingNLNationaliteit findRedenVerkrijgingNLNationaliteitByCode(
            final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode);

    /**
     * @param bijhoudingsgemeenteCode
     *            de bijhoudingsgemeentecode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Partij findPartijByGemeentecode(final BrpGemeenteCode bijhoudingsgemeenteCode);

    /**
     * @param bijhoudingsgemeenteCode
     *            de bijhoudingsgemeentecode, mag null zijn
     * @param preconditie
     *            de preconditie die wordt geschonden als de gemeentecode niet kan worden gevonden
     * 
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Partij findPartijByGemeentecode(final BrpGemeenteCode bijhoudingsgemeenteCode, final Precondities preconditie);

    /**
     * @param partijCode
     *            de partij code, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Partij findPartijByPartijcode(final BrpPartijCode partijCode);

    /**
     * @param plaatsCode
     *            de plaatscode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Plaats findPlaatsByCode(final BrpPlaatsCode plaatsCode);

    /**
     * @param landCode
     *            de landcode, mag null zijn
     * @param preconditie
     *            de preconditie die wordt geschonden als de landcode niet wordt gevonden
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Land findLandByLandcode(final BrpLandCode landCode, final Precondities preconditie);

    /**
     * @param landCode
     *            de landcode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Land findLandByLandcode(final BrpLandCode landCode);

    /**
     * @param verblijfsrechtCode
     *            de verblijfsrechtCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Verblijfsrecht findVerblijfsrechtByCode(final BrpVerblijfsrechtCode verblijfsrechtCode);

    /**
     * @param aangeverAdreshoudingCode
     *            de aangeverAdreshoudingCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    AangeverAdreshouding findAangeverAdreshouding(final BrpAangeverAdreshoudingCode aangeverAdreshoudingCode);

    /**
     * @param redenWijzigingAdresCode
     *            de redenWijzigingAdresCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    RedenWijzigingAdres findRedenWijzigingAdres(final BrpRedenWijzigingAdresCode redenWijzigingAdresCode);

    /**
     * @param brpNationaliteitCode
     *            de nationaliteit code, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Nationaliteit findNationaliteitByNationaliteitcode(final BrpNationaliteitCode brpNationaliteitCode);

    /**
     * @param brpReisdocumentAutoriteitVanAfgifte
     *            de brpReisdocumentAutoriteitVanAfgifte, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    AutoriteitVanAfgifteReisdocument findAutoriteitVanAfgifteReisdocumentByCode(
            final BrpReisdocumentAutoriteitVanAfgifte brpReisdocumentAutoriteitVanAfgifte);

    /**
     * @param brpReisdocumentRedenOntbreken
     *            de brpReisdocumentRedenOntbreken, mag null zjn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    RedenVervallenReisdocument findRedenVervallenReisdocumentByCode(
            final BrpReisdocumentRedenOntbreken brpReisdocumentRedenOntbreken);

    /**
     * @param brpReisdocumentSoort
     *            de brpReisdocumentSoort, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    SoortNederlandsReisdocument
            findSoortNederlandsReisdocumentByCode(final BrpReisdocumentSoort brpReisdocumentSoort);

    /**
     * @param soortDocumentCode
     *            de BrpSoortDocumentCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    SoortDocument findSoortDocumentByCode(final BrpSoortDocumentCode soortDocumentCode);

    /**
     * @param redenEinde
     *            de BrpRedenEindeRelatieCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    RedenBeeindigingRelatie findRedenBeeindigingRelatieByCode(BrpRedenEindeRelatieCode redenEinde);
}
