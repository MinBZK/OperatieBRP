/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Aangever;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenWijzigingVerblijf;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPartij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.Lo3Rubriek;

/**
 * De target(adapter) interface voor alle methoden om gegevens uit dynamische stamtabellen op te zoeken a.d.h.v. de code
 * uit het brp migratie model. Het standaard gedrag is dat null waarden gemapped worden op null waarden.
 */
@SuppressWarnings("checkstyle:classfanoutcomplexity")
public interface StamtabelMapping {
    /* Class Fan-Out complexity is hier hoger dan 20 maar levert geen complexe class op */

    /**
     * @param redenVerliesNederlandschapCode
     *            de reden verlies NLschap code, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    RedenVerliesNLNationaliteit findRedenVerliesNLNationaliteitByCode(final BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode);

    /**
     * @param redenVerkrijgingNederlandschapCode
     *            de reden verkrijging NLschap code, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    RedenVerkrijgingNLNationaliteit findRedenVerkrijgingNLNationaliteitByCode(
        final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode);

    /**
     * @param gemeenteCode
     *            de gemeentecode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Gemeente findGemeenteByCode(final BrpGemeenteCode gemeenteCode);

    /**
     * @param gemeenteCode
     *            de gemeentecode, mag null zijn
     * @param soortMeldingCode
     *            de preconditie die wordt geschonden als de gemeentecode niet kan worden gevonden
     *
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Gemeente findGemeenteByCode(final BrpGemeenteCode gemeenteCode, final SoortMeldingCode soortMeldingCode);

    /**
     * @param partijCode
     *            de partij code, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Partij findPartijByCode(final BrpPartijCode partijCode);

    /**
     * @param landOfGebiedCode
     *            de landcode, mag null zijn
     * @param soortMeldingCode
     *            de preconditie die wordt geschonden als het land/gebied code niet wordt gevonden
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    LandOfGebied findLandOfGebiedByCode(final BrpLandOfGebiedCode landOfGebiedCode, final SoortMeldingCode soortMeldingCode);

    /**
     * @param landOfGebiedCode
     *            de landcode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    LandOfGebied findLandOfGebiedByCode(final BrpLandOfGebiedCode landOfGebiedCode);

    /**
     * @param verblijfstitelCode
     *            de verblijfstitelCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Verblijfsrecht findVerblijfsrechtByCode(final BrpVerblijfsrechtCode verblijfstitelCode);

    /**
     * @param aangeverCode
     *            de aangeverCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Aangever findAangeverByCode(final BrpAangeverCode aangeverCode);

    /**
     * @param redenWijzigingVerblijfCode
     *            de redenWijzigingVerblijfCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    RedenWijzigingVerblijf findRedenWijzigingVerblijfByCode(final BrpRedenWijzigingVerblijfCode redenWijzigingVerblijfCode);

    /**
     * @param brpNationaliteitCode
     *            de nationaliteit code, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    Nationaliteit findNationaliteitByCode(final BrpNationaliteitCode brpNationaliteitCode);

    /**
     * @param brpAanduidingInhoudingOfVermissingReisdocumentCode
     *            de brpAanduidingInhoudingOfVermissingReisdocumentCode, mag null zjn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    AanduidingInhoudingOfVermissingReisdocument findAanduidingInhoudingOfVermissingReisdocumentByCode(
        final BrpAanduidingInhoudingOfVermissingReisdocumentCode brpAanduidingInhoudingOfVermissingReisdocumentCode);

    /**
     * @param brpSoortNederlandsReisdocumentCode
     *            de brpSoortNederlandsReisdocumentCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    SoortNederlandsReisdocument findSoortNederlandsReisdocumentByCode(final BrpSoortNederlandsReisdocumentCode brpSoortNederlandsReisdocumentCode);

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
    RedenBeeindigingRelatie findRedenBeeindigingRelatieByCode(final BrpRedenEindeRelatieCode redenEinde);

    /**
     * @param lo3Rubriek
     *            lo3 rubriek naam, magn ull zijn
     * @return de corresponderende waarde uit de BRP (conversie) stamtabel of null als de input null is
     */
    Lo3Rubriek findLo3RubriekByNaam(String lo3Rubriek);

    /**
     * @param soortPartijCode
     *            de BrpSoortPartijCode, mag null zijn
     * @return de corresponderende waarde uit de BRP stamtabel of null als de input null is
     */
    SoortPartij findSoortPartijByNaam(BrpSoortPartijCode soortPartijCode);
}
