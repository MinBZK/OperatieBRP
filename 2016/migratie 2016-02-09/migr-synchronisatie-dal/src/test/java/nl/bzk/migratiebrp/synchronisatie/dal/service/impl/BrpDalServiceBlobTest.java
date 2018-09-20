/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BrpDalServiceBlobTest extends AbstractDatabaseTest {

    private static final BrpLandOfGebiedCode LAND_CODE_NEDERLAND = new BrpLandOfGebiedCode(Short.parseShort("6030"));
    private static final BrpGemeenteCode GEMEENTE_CODE_ALMERE = new BrpGemeenteCode((short) 34);

    @Inject
    private BrpDalService service;

    @Before
    public void setup() {
        service.getSyncParameters().setInitieleVulling(false);
        Logging.initContext();
        SynchronisatieLogging.init();
    }

    @After
    public void teardown() {
        Logging.destroyContext();
    }

    /**
     * Met deze test wordt zowel de mapping als de opslag via JPA getest middels het opslaan van een goed gevulde
     * BrpPersoonslijst middels de BrpDalService.
     */
    @DBUnit.InsertBefore({"/sql/data/brpStamgegevens-kern.xml",
                          "/sql/data/brpStamgegevens-autaut.xml",
                          "/sql/data/brpStamgegevens-conv.xml",
                          "/sql/data/brpStamgegevens-verconv.xml",
                          "BrpDalServiceBlobTestBefore.xml" })
    @DBUnit.ExpectedAfter({"BrpDalServiceBlobTestExpected.xml" })
    @DBUnit.NotExpectedAfter("BrpDalServiceBlobTestNotExpected.xml")
    @Test
    public void testVerwijderenBlob() {
        final BrpPersoonslijst brpPersoonslijst = maakBrpPersoonslijst();
        service.persisteerPersoonslijst(brpPersoonslijst, maakBerichtLog(brpPersoonslijst.getActueelAdministratienummer()));
    }

    private BrpPersoonslijst maakBrpPersoonslijst() {
        return new BrpPersoonslijstTestDataBuilder().addDefaultTestStapels()
                                                    .addGroepMetHistorieA(
                                                        new BrpGeboorteInhoud(
                                                            new BrpDatum(19830405, null),
                                                            GEMEENTE_CODE_ALMERE,
                                                            null,
                                                            null,
                                                            null,
                                                            LAND_CODE_NEDERLAND,
                                                            null))
                                                    .addGroepMetHistorieA(
                                                        new BrpNaamgebruikInhoud(
                                                            BrpNaamgebruikCode.E,
                                                            new BrpBoolean(true, null),
                                                            null,
                                                            null,
                                                            new BrpString("Noam", null),
                                                            null,
                                                            new BrpCharacter(' ', null),
                                                            new BrpString("Chomsky", null)))
                                                    .addGroepMetHistorieA(new BrpPersoonAfgeleidAdministratiefInhoud(new BrpBoolean(false)))
                                                    .addGroepMetHistorieA(
                                                        new BrpInschrijvingInhoud(
                                                            new BrpDatum(19830405, null),
                                                            new BrpLong(66L, null),
                                                            BrpDatumTijd.fromDatum(20141213, null)))
                                                    .addRelatieMetPuntOuder(false)
                                                    .build();
    }

    private Lo3Bericht maakBerichtLog(final Long administratienummer) {
        final Lo3Bericht result =
                new Lo3Bericht(
                    "bericht-uuid",
                    Lo3BerichtenBron.INITIELE_VULLING,
                    new Timestamp(new GregorianCalendar(2012, 11, 27, 13, 13, 13).getTimeInMillis()),
                    "<testBericht/>",
                    true);
        result.setAnummer(administratienummer);
        return result;
    }
}
