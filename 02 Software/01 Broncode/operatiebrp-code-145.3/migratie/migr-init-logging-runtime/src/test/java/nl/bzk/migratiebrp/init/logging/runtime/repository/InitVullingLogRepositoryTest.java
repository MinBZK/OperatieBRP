/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAutorisatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingProtocollering;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test InitVullingRepository
 */
@Transactional(transactionManager = "loggingTransactionManager")
@Rollback(false)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:synchronisatie-logging-beans-test.xml", "classpath:synchronisatie-logging-jms-test.xml"})
public class InitVullingLogRepositoryTest {

    private static final String FOUTMELDING_TEXT = "foutmelding";

    private static final String FOUT_TEXT = "fout";

    @Inject
    private InitVullingLogRepository logRepository;

    @PersistenceContext(name = "loggingEntityManagerFactory", unitName = "LogEntities")
    private EntityManager em;

    /**
     * Zoekt een log record op anummer.
     */
    @Test
    @Transactional
    public void testFindLogPersoon() {
        final String anummer = "3832803548";
        final InitVullingLog log = logRepository.findInitVullingLogPersoon(anummer);
        assertNotNull(log);
        assertEquals(anummer, log.getAnummer());
    }

    /**
     * Zoekt een log record op anummer die niet bestaat.
     */
    @Test
    public void testFindNonExistingLogPersoon() {
        final String anummer = "3832803549";
        final InitVullingLog log = logRepository.findInitVullingLogPersoon(anummer);
        assertNull("Er zou geen log gevonden moeten zijn.", log);
    }

    /**
     * Maak een nieuwe log en voeg deze toe inclusief diff.
     */
    @Test
    @Transactional
    public void testAddDiffToNewLogPersoon() {
        final InitVullingLog log = new InitVullingLog();
        final Date now = new Date();
        log.setAnummer("2347323439");
        log.setBerichtId(23473234);
        log.setBerichtType("Lg01");
        log.setDatumOpschorting(20120101);
        log.setDatumTijdOpnameGbav(now);
        log.setGemeenteVanInschrijving("1904");
        log.setPlId(1);
        log.setRedenOpschorting('F');

        logRepository.saveInitVullingLogPersoon(log);

        assertNotNull(log);
        assertEquals(Integer.valueOf(23473234), log.getBerichtId());
        assertEquals(Integer.valueOf(20120101), log.getDatumOpschorting());
        assertEquals(now, log.getDatumTijdOpnameGbav());
        assertEquals("1904", log.getGemeenteVanInschrijving());
        assertEquals(Integer.valueOf(1), log.getPlId());
        assertEquals(Character.valueOf('F'), log.getRedenOpschorting());

    }

    @Test
    @Transactional
    public void testAfnemersindicatie() {
        final String aNr = "1234567890";
        final long plId = 1234L;
        final InitVullingAfnemersindicatie recordToInsert = new InitVullingAfnemersindicatie(plId);
        recordToInsert.setBerichtResultaat(FOUT_TEXT);
        recordToInsert.setAdministratienummer(aNr);

        logRepository.saveInitVullingAfnemersindicatie(recordToInsert);
        em.flush();

        final InitVullingAfnemersindicatie recordSelected = logRepository.findInitVullingAfnemersindicatie(aNr);
        Assert.assertEquals(plId, recordSelected.getPlId());
        Assert.assertEquals(FOUT_TEXT, recordSelected.getBerichtResultaat());
    }

    @Test
    @Transactional
    public void testAutorisatie() {
        final InitVullingAutorisatie recordToInsert = new InitVullingAutorisatie();
        recordToInsert.setAutorisatieId(301L);
        recordToInsert.setConversieResultaat(FOUT_TEXT);
        recordToInsert.setConversieMelding(FOUTMELDING_TEXT);

        logRepository.saveInitVullingAutorisatie(recordToInsert);
        em.flush();

        final InitVullingAutorisatie recordsSelected = logRepository.findInitVullingAutorisatie(301L);
        Assert.assertEquals(Long.valueOf(301), recordsSelected.getAutorisatieId());
        Assert.assertEquals(FOUT_TEXT, recordsSelected.getConversieResultaat());
        Assert.assertEquals(FOUTMELDING_TEXT, recordsSelected.getConversieMelding());
    }

    @Test
    @Transactional
    public void testAfnemerindicaties() {
        InitVullingAfnemersindicatie recordSelected = logRepository.findInitVullingAfnemersindicatie("3452671879");
        Assert.assertEquals("id geen 12", 12, recordSelected.getPlId());
        Assert.assertEquals("anr incorrect", "3452671879", recordSelected.getAdministratienummer());
        Assert.assertEquals("VERZONDEN", recordSelected.getBerichtResultaat());
        //Assert.assertNull("Er zou geen foutmelding moeten zijn", recordSelected.getFoutmelding());
        Assert.assertEquals("Aantal stapels dient 12 te zijn", 12, recordSelected.getStapels().size());
        Assert.assertEquals(
                "Status stapel aan begin moet TE_VERWERKEN zijn",
                "TE_VERWERKEN",
                recordSelected.getStapels().iterator().next().getConversieResultaat());
//
//        recordSelected.getStapels().
//
//        final List<LogRegel> logRegels = new ArrayList<>();
//        final LogRegel logRegel = new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_14, 1, 0), LogSeverity.ERROR, SoortMeldingCode.AFN004, null);
//        logRegels.add(logRegel);
//        recordSelected.verwerkResultaat(logRegels);
//        Assert.assertEquals("Aantal stapels dient 12 te zijn na verwerken", 12, recordSelected.getStapels().size());
//        int aantalVerwerkt = 0;
//        int aantalAfn004 = 0;
//
//        final Iterator<InitVullingAfnemersindicatieStapel> iterator = recordSelected.getStapels().iterator();
//        while (iterator.hasNext()) {
//            final InitVullingAfnemersindicatieStapel stapel = iterator.next();
//            System.out.println("resultaat: " + stapel.getConversieResultaat());
//            if ("VERWERKT".equals(stapel.getConversieResultaat())) {
//                aantalVerwerkt++;
//            } else if ("AFN004".equals(stapel.getConversieResultaat())) {
//                aantalAfn004++;
//            }
//        }
//        Assert.assertEquals("aantal VERWERKT moeten 11 zijn", 11, aantalVerwerkt);
//        Assert.assertEquals("aantal AFN004 moet er 1 zijn", 1, aantalAfn004);
//        recordSelected = null;
//
//        em.flush();
//        em.getEntityManagerFactory().getCache().evictAll();
//
//        final InitVullingAfnemersindicatie recordStored = logRepository.findInitVullingAfnemersindicatie(3452671879L);
//
//        aantalVerwerkt = 0;
//        aantalAfn004 = 0;
//
//        final Iterator<InitVullingAfnemersindicatieStapel> iteratorStored = recordStored.getStapels().iterator();
//        while (iteratorStored.hasNext()) {
//            final InitVullingAfnemersindicatieStapel stapel = iteratorStored.next();
//            if ("VERWERKT".equals(stapel.getConversieResultaat())) {
//                aantalVerwerkt++;
//            } else if ("AFN004".equals(stapel.getConversieResultaat())) {
//                aantalAfn004++;
//            }
//        }
//        Assert.assertEquals("aantal VERWERKT moeten 11 zijn", 11, aantalVerwerkt);
//        Assert.assertEquals("aantal AFN004 moet er 1 zijn", 1, aantalAfn004);
    }

    @Test
    @Transactional
    public void testProtocollering() {
        final InitVullingProtocollering recordToInsert = new InitVullingProtocollering();
        recordToInsert.setActiviteitId(401);
        recordToInsert.setConversieResultaat(FOUT_TEXT);
        recordToInsert.setFoutmelding(FOUTMELDING_TEXT);

        logRepository.saveInitVullingProtocollering(recordToInsert);
        em.flush();

        final InitVullingProtocollering recordsSelected = logRepository.findInitVullingProtocollering(401);
        Assert.assertEquals(401, recordsSelected.getActiviteitId());
        Assert.assertEquals(FOUT_TEXT, recordsSelected.getConversieResultaat());
        Assert.assertEquals(FOUTMELDING_TEXT, recordsSelected.getFoutmelding());
    }
}
