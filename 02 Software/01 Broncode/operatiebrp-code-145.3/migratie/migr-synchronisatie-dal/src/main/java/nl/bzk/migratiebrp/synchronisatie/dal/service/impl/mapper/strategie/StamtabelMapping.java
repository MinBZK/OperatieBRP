/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AutoriteitAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortPartij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

/**
 * De target(adapter) interface voor alle methoden om gegevens uit dynamische stamtabellen op te zoeken a.d.h.v. de code
 * uit het brp migratie model. Het standaard gedrag is dat null waarden gemapped worden op null waarden.
 */

public interface StamtabelMapping {
    /* Class Fan-Out complexity is hier hoger dan 20 maar levert geen complexe class op */

    /**
     * @param redenVerliesNederlandschapCode de reden verlies NLschap code, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    RedenVerliesNLNationaliteit findRedenVerliesNLNationaliteitByCode(BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode);

    /**
     * @param redenVerkrijgingNederlandschapCode de reden verkrijging NLschap code, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    RedenVerkrijgingNLNationaliteit findRedenVerkrijgingNLNationaliteitByCode(BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode);

    /**
     * @param gemeenteCode de gemeentecode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Gemeente findGemeenteByCode(BrpGemeenteCode gemeenteCode);

    /**
     * @param gemeenteCode de gemeentecode, mag null zijn
     * @param soortMeldingCode de preconditie die wordt geschonden als de gemeentecode niet kan worden gevonden
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Gemeente findGemeenteByCode(BrpGemeenteCode gemeenteCode, SoortMeldingCode soortMeldingCode);

    /**
     * @param partijCode de partij code, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Partij findPartijByCode(BrpPartijCode partijCode);

    /**
     * @param landOfGebiedCode de landcode, mag null zijn
     * @param soortMeldingCode de preconditie die wordt geschonden als het land/gebied code niet wordt gevonden
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    LandOfGebied findLandOfGebiedByCode(BrpLandOfGebiedCode landOfGebiedCode, SoortMeldingCode soortMeldingCode);

    /**
     * @param landOfGebiedCode de landcode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    LandOfGebied findLandOfGebiedByCode(BrpLandOfGebiedCode landOfGebiedCode);

    /**
     * @param verblijfstitelCode de verblijfstitelCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Verblijfsrecht findVerblijfsrechtByCode(BrpVerblijfsrechtCode verblijfstitelCode);

    /**
     * @param aangeverCode de aangeverCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Aangever findAangeverByCode(BrpAangeverCode aangeverCode);

    /**
     * @param redenWijzigingVerblijfCode de redenWijzigingVerblijfCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    RedenWijzigingVerblijf findRedenWijzigingVerblijfByCode(BrpRedenWijzigingVerblijfCode redenWijzigingVerblijfCode);

    /**
     * @param brpNationaliteitCode de nationaliteit code, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Nationaliteit findNationaliteitByCode(BrpNationaliteitCode brpNationaliteitCode);

    /**
     * @param brpAanduidingInhoudingOfVermissingReisdocumentCode de brpAanduidingInhoudingOfVermissingReisdocumentCode, mag null zjn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    AanduidingInhoudingOfVermissingReisdocument findAanduidingInhoudingOfVermissingReisdocumentByCode(
            BrpAanduidingInhoudingOfVermissingReisdocumentCode brpAanduidingInhoudingOfVermissingReisdocumentCode);

    /**
     * @param brpSoortNederlandsReisdocumentCode de brpSoortNederlandsReisdocumentCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    SoortNederlandsReisdocument findSoortNederlandsReisdocumentByCode(BrpSoortNederlandsReisdocumentCode brpSoortNederlandsReisdocumentCode);

    /**
     * @param soortDocumentCode de BrpSoortDocumentCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    SoortDocument findSoortDocumentByCode(BrpSoortDocumentCode soortDocumentCode);

    /**
     * @param redenEinde de BrpRedenEindeRelatieCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    RedenBeeindigingRelatie findRedenBeeindigingRelatieByCode(BrpRedenEindeRelatieCode redenEinde);

    /**
     * @param lo3Rubriek lo3 rubriek naam, magn ull zijn
     * @return de corresponderende waarde uit de BRP (conversie) stamtabel of null als de input null is
     */
    Lo3Rubriek findLo3RubriekByNaam(String lo3Rubriek);

    /**
     * @param soortPartijCode de BrpSoortPartijCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    SoortPartij findSoortPartijByNaam(BrpSoortPartijCode soortPartijCode);

    /**
     * @param autoriteitVanAfgifteBuitenlandsPersoonsnummer de autoriteit van afgifte buitenlands persoonsnummer
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    AutoriteitAfgifteBuitenlandsPersoonsnummer findAutoriteitVanAfgifteBuitenlandsPersoonsnummer(
            BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer autoriteitVanAfgifteBuitenlandsPersoonsnummer);
}
