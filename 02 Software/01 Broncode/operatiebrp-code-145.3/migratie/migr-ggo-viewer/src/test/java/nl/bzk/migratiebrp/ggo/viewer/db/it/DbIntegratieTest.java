/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.db.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3Severity;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3SoortMelding;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.ggo.viewer.Lo3PersoonslijstTestHelper;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.service.DbService;
import nl.bzk.migratiebrp.ggo.viewer.service.ViewerService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test case voor de DB connectie. Wordt niet gerund tijdens de build omdat het een integratietest is. (Zie surefire
 * config in pom.xml)
 *
 * N.B. Deze test ruimt nu zijn eigen troep op, maar zal nog wel falen als er reeds PL's in de DB staan met dezelfde
 * identificatienummers.
 */
@Transactional(transactionManager = "syncDalTransactionManager")
@Rollback(false)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@ContextConfiguration(locations = {"classpath:viewer-beans.xml", "classpath:synchronisatie-beans.xml"})
public class DbIntegratieTest {
    @Inject
    private BrpDalService brpDalService;

    @Inject
    private BrpPersoonslijstService brpPersoonslijstService;

    @Inject
    private DbService dbService;

    @Inject
    private ViewerService viewerService;

    private final FoutMelder foutMelder = new FoutMelder();

    public void zetOp() throws Exception {
        final List<Lo3Persoonslijst> lo3Persoonslijsten = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Relateren01.xls", foutMelder);
        lo3Persoonslijsten.addAll(Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("PL_alle_cats.xls", foutMelder));
        lo3Persoonslijsten.addAll(Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("PL_alle_cats_extra_brp.xls", foutMelder));

        Logging.initContext();
        for (final Lo3Persoonslijst lo3Persoonslijst : lo3Persoonslijsten) {
            final BrpPersoonslijst brpPersoonslijst = viewerService.converteerNaarBrp(lo3Persoonslijst, foutMelder);
            assertNotNull("BrpPersoonslijst mag niet null zijn: " + foutMelder.getFoutRegels().toString(), brpPersoonslijst);
            final Lo3Bericht lo3Bericht =
                    new Lo3Bericht("DbIntegratieTest", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(System.currentTimeMillis()), "TEST_DATA", true);
            brpPersoonslijstService.persisteerPersoonslijst(brpPersoonslijst, lo3Bericht);
            brpDalService.persisteerLo3Bericht(maakLo3Bericht(brpPersoonslijst, lo3Persoonslijst));
        }
        Logging.destroyContext();
    }

    @Ignore("Staat ook al uit in Maven pom")
    @Test
    @Transactional
    @Rollback
    public void testDbIntegratie() throws Exception {
        zetOp();

        final BrpPersoonslijst brpPersoonslijst = dbService.zoekBrpPersoonsLijst("8750000001");
        final Lo3Bericht lo3Bericht = dbService.zoekLo3Bericht("8750000001");
        final Lo3Persoonslijst lo3Persoonslijst = dbService.haalLo3PersoonslijstUitLo3Bericht(lo3Bericht);
        final List<GgoFoutRegel> logRegels = dbService.haalLogRegelsUitLo3Bericht(lo3Bericht);

        assertNotNull(brpPersoonslijst);
        assertEquals("8750000001", brpPersoonslijst.getActueelAdministratienummer());
        assertNotNull(lo3Persoonslijst);
        assertEquals("8750000001", lo3Persoonslijst.getActueelAdministratienummer());
        assertEquals(1, logRegels.size());
        assertEquals(Lo3SoortMelding.PRE001.getCode(), logRegels.iterator().next().getCode());
    }

    private Lo3Bericht maakLo3Bericht(final BrpPersoonslijst brpPersoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {
        final Lo3Bericht lo3Bericht =
                new Lo3Bericht("ggo_viewer_test", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(new Date().getTime()), "asdf", true);

        lo3Bericht.setAnummer(brpPersoonslijst.getActueelAdministratienummer());
        lo3Bericht.addMelding("01", 0, 0, null, Lo3SoortMelding.PRE001, Lo3Severity.INFO, null, null);

        final List<Lo3CategorieWaarde> gbavCategorieen = new Lo3PersoonslijstFormatter().format(lo3Persoonslijst);
        final String inhoud = Lo3Inhoud.formatInhoud(gbavCategorieen);
        lo3Bericht.setBerichtdata(inhoud);

        return lo3Bericht;
    }
}
