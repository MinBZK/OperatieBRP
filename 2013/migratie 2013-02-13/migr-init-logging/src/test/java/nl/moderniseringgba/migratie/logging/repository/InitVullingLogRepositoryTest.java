/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.logging.domein.entities.InitVullingLog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Test InitVullingRepository
 */
@TransactionConfiguration(defaultRollback = false)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:synchronisatie-logging-beans-test.xml",
        "classpath:synchronisatie-logging-jms-test.xml" })
public class InitVullingLogRepositoryTest {

    @Inject
    private InitVullingLogRepository logRepository;

    /**
     * Zoekt een log record op anummer.
     */
    @Test
    public void testFindLog() {
        final Long anummer = Long.valueOf(3832803548L);
        final InitVullingLog log = logRepository.findLog(anummer);
        assertNotNull(log);
        assertEquals(log.getAnummer(), anummer);
    }

    /**
     * Zoekt een log record op anummer die niet bestaat.
     */
    @Test
    public void testFindNonExistingLog() {
        final Long anummer = Long.valueOf(3832803549L);
        final InitVullingLog log = logRepository.findLog(anummer);
        assertNull("Er zou geen log gevonden moeten zijn.", log);
    }

    /**
     * Maak een nieuwe log en voeg deze toe inclusief diff.
     */
    @Test
    public void testAddDiffToNewLog() {
        final InitVullingLog log = new InitVullingLog();
        final Date now = new Date();
        log.setAnummer(2347323439L);
        log.setBerichtId(Integer.valueOf(23473234));
        log.setBerichtType(1111);
        log.setDatumOpschorting(Integer.valueOf(20120101));
        log.setDatumTijdOpnameGbav(now);
        log.setGemeenteVanInschrijving(Integer.valueOf(1904));
        log.setPlId(1);
        log.setRedenOpschorting(Character.valueOf('F'));

        logRepository.persistLog(log);

        assertNotNull(log);
        assertEquals(log.getBerichtId(), Integer.valueOf(23473234));
        assertEquals(log.getDatumOpschorting(), Integer.valueOf(20120101));
        assertEquals(log.getDatumTijdOpnameGbav(), now);
        assertEquals(log.getGemeenteVanInschrijving(), Integer.valueOf(1904));
        assertEquals(log.getPlId(), Integer.valueOf(1));
        assertEquals(log.getRedenOpschorting(), Character.valueOf('F'));

    }

    @Test
    public void testFindLogByDateSucces() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dateFormat.setLenient(false);

        try {
            final Date datumVanaf = dateFormat.parse("20020708");
            final Date datumTot = dateFormat.parse("20120709");
            final List<Long> anummers = logRepository.findLogs(datumVanaf, datumTot, null);
            assertNotNull(anummers);
            assertTrue("Er moet minstens 1 log gevonden zijn.", anummers.size() > 0);
            assertEquals("", anummers.get(0), Long.valueOf("2503828934"));
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }

    @Test
    public void testFindLogByDateNoLogs() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dateFormat.setLenient(false);

        try {
            final Date datumVanaf = dateFormat.parse("20120707");
            final Date datumTot = dateFormat.parse("20120708");
            final List<Long> anummers = logRepository.findLogs(datumVanaf, datumTot, null);
            assertNotNull(anummers);
            assertTrue("Er moet minstens 1 log gevonden zijn.", anummers.size() == 0);
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }
}
