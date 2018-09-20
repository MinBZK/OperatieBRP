/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.db.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3PersoonslijstFormatter;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;
import nl.moderniseringgba.migratie.conversie.viewer.service.DbService;
import nl.moderniseringgba.migratie.conversie.viewer.service.LeesService;
import nl.moderniseringgba.migratie.conversie.viewer.service.ViewerService;
import nl.moderniseringgba.migratie.conversie.viewer.util.LogRegelConverter;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test case voor de DB connectie. Wordt niet gerund tijdens de build omdat het een integratietest is. (Zie surefire
 * config in pom.xml)
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:viewer-beans.xml" })
public class DbIntegratieTest {
    @Inject
    private BrpDalService brpDalService;

    @Inject
    private DbService dbService;

    @Inject
    private ViewerService viewerService;

    @Inject
    private LeesService leesService;

    @Inject
    private LogRegelConverter logRegelConverter;

    private final FoutMelder foutMelder = new FoutMelder();

    /**
     * LET OP 'vervuilt' de DB (voegt records toe zonder ze weer weg te halen)
     */
    @Before
    public void setUp() throws IOException {
        final List<Lo3Persoonslijst> lo3Persoonslijsten = getValidLo3Persoonslijsten();

        for (final Lo3Persoonslijst lo3Persoonslijst : lo3Persoonslijsten) {
            System.out.println(lo3Persoonslijst.getActueelAdministratienummer());
            final BrpPersoonslijst brpPersoonslijst = viewerService.converteerNaarBrp(lo3Persoonslijst, null);
            brpDalService.persisteerPersoonslijst(brpPersoonslijst);
            brpDalService.persisteerBerichtLog(maakBerichtLog(brpPersoonslijst, lo3Persoonslijst));
        }
    }

    @Test
    public void testDbIntegratie() {
        final BrpPersoonslijst brpPersoonslijst = dbService.zoekBrpPersoonsLijst(8750000001L);
        final BerichtLog berichtLog = dbService.zoekBerichtLog(8750000001L);
        final Lo3Persoonslijst lo3Persoonslijst = dbService.haalLo3PersoonslijstUitBerichtLog(berichtLog);
        final List<LogRegel> logRegels = dbService.haalLogRegelsUitBerichtLog(berichtLog);

        assertNotNull(brpPersoonslijst);
        assertEquals(8750000001L, brpPersoonslijst.getActueelAdministratienummer().longValue());
        assertNotNull(lo3Persoonslijst);
        assertEquals(8750000001L, lo3Persoonslijst.getActueelAdministratienummer().longValue());
        assertEquals(1, logRegels.size());
        assertEquals("Dit is een testlogregel", logRegels.get(0).getOmschrijving());
    }

    private List<Lo3Persoonslijst> getValidLo3Persoonslijsten() throws IOException {
        final String filename = "Relateren01.xls";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        return leesService.leesLo3Persoonslijst(filename, IOUtils.toByteArray(inputStream), foutMelder);
    }

    private BerichtLog
            maakBerichtLog(final BrpPersoonslijst brpPersoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {
        final BerichtLog berichtLog =
                new BerichtLog("ggo_viewer_test", "ggo_viewer_test", new Timestamp(new Date().getTime()));

        berichtLog.setAdministratienummer(brpPersoonslijst.getActueelAdministratienummerAsBigDecimal());

        final nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel logRegel =
                new nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.LogRegel();
        logRegel.setCategorie(1);
        logRegel.setCode("test");
        logRegel.setOmschrijving("Dit is een testlogregel");
        logRegel.setSeverity(LogSeverity.INFO.getSeverity());
        logRegel.setStapel(0);
        logRegel.setType(LogType.STRUCTUUR.name());
        logRegel.setVoorkomen(0);

        berichtLog.addLogRegel(logRegel);

        final List<Lo3CategorieWaarde> gbavCategorieen = new Lo3PersoonslijstFormatter().format(lo3Persoonslijst);
        final String inhoud = Lo3Inhoud.formatInhoud(gbavCategorieen);
        berichtLog.setBerichtData(inhoud);

        return berichtLog;
    }
}
