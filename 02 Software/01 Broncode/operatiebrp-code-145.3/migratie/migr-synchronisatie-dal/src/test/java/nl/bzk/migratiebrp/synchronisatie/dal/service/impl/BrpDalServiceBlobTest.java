/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.test.dal.DBUnit;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikGeslachtsnaamstam;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.TeLeverenAdministratieveHandelingenAanwezigException;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BrpDalServiceBlobTest extends AbstractDatabaseTest {

    private static final BrpLandOfGebiedCode LAND_CODE_NEDERLAND = new BrpLandOfGebiedCode("6030");
    private static final BrpGemeenteCode GEMEENTE_CODE_ALMERE = new BrpGemeenteCode("0034");

    @Inject
    private BrpPersoonslijstService service;

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
     * Met deze test wordt zowel de mapping als de opslag via JPA getest middels het opslaan van een
     * goed gevulde BrpPersoonslijst middels de BrpDalService.
     */
    @DBUnit.InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml",
            "/sql/data/brpStamgegevens-verconv.xml", "BrpDalServiceBlobTestBefore.xml"})
    @DBUnit.ExpectedAfter({"BrpDalServiceBlobTestExpected.xml"})
    @Test
    public void testOpslaanBlob() throws TeLeverenAdministratieveHandelingenAanwezigException {
        final BrpPersoonslijst brpPersoonslijst = maakBrpPersoonslijst();
        final PersoonslijstPersisteerResultaat resultaat =
                service.persisteerPersoonslijst(brpPersoonslijst, 1L, maakBerichtLog(brpPersoonslijst.getActueelAdministratienummer()));
        System.out.println("AH: " + resultaat.getAdministratieveHandelingen());
    }

    private BrpPersoonslijst maakBrpPersoonslijst() {
        return new BrpPersoonslijstTestDataBuilder().addDefaultTestStapels()
                .addGroepMetHistorieA(new BrpGeboorteInhoud(new BrpDatum(19830405, null), GEMEENTE_CODE_ALMERE, null, null, null, LAND_CODE_NEDERLAND, null))
                .addGroepMetHistorieA(new BrpNaamgebruikInhoud(BrpNaamgebruikCode.E, new BrpBoolean(true, null), null, null, new BrpString("Noam", null), null,
                        new BrpCharacter(' ', null), new BrpNaamgebruikGeslachtsnaamstam("Chomsky", null)))
                .addGroepMetHistorieA(new BrpPersoonAfgeleidAdministratiefInhoud())
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(new BrpDatum(19830405, null), new BrpLong(66L, null), BrpDatumTijd.fromDatum(20141213, null)))
                .addRelatieMetPuntOuder(false).build();
    }

    private Lo3Bericht maakBerichtLog(final String administratienummer) {
        final Lo3Bericht result = new Lo3Bericht("bericht-uuid", Lo3BerichtenBron.INITIELE_VULLING,
                new Timestamp(new GregorianCalendar(2012, 11, 27, 13, 13, 13).getTimeInMillis()), "<testBericht/>", true);
        result.setAnummer(administratienummer);
        return result;
    }
}
