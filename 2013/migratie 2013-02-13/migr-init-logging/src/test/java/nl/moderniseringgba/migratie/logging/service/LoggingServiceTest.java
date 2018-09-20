/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging.service;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test InitVullingRepository
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:synchronisatie-logging-beans-test.xml",
        "classpath:synchronisatie-logging-jms-test.xml" })
public class LoggingServiceTest {

    @Inject
    private LoggingService service;

    /**
     * Zoekt een log record op anummer vergelijk de gbav en brp berichten. Daarna wordt het log record opgeslagen.
     */
    @Test
    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    public void testAddDiffToExistingLog() {
        final InitVullingLog log = service.findLog(6579659649L);
        log.setBrpLo3Bericht(log.getLo3Bericht());
        service.createAndStoreDiff(log);
        assertNotNull(service.findLog(6579659649L).getBerichtDiff());
    }

    /**
     * Zoekt een log record op anummer vergelijk de gbav en een leeg brp bericht.
     */
    @Test
    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    public void testAddDiffToExistingLogEmptyBrp() {
        final InitVullingLog log = service.findLog(2102970797L);
        service.createAndStoreDiff(log);
        assertNotNull(service.findLog(2102970797L));
        assertNull(service.findLog(2102970797L).getBerichtDiff());
    }

    /**
     * Zoekt een log record op anummer vergelijk een leeg gbav en een leeg brp bericht.
     */
    @Test
    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    public void testAddDiffToExistingLogEmptyBrpAndGbav() {
        final InitVullingLog log = service.findLog(7696126369L);
        log.setLo3Bericht(null);
        service.createAndStoreDiff(log);
        assertNotNull(service.findLog(7696126369L));
        assertNull(service.findLog(7696126369L).getBerichtDiff());
    }

    /**
     * Zoekt een log record op anummer.
     */
    @Test
    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    public void testFindLog() {
        final Long anummer = 3832803548L;
        final InitVullingLog log = service.findLog(anummer);
        assertNotNull(log);
        assertEquals(log.getAnummer(), anummer);
    }

    /**
     * Zoekt een log record op anummer.
     */
    @Test
    public void testFindLogByDate() {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Date datumVanaf = dateFormat.parse("20120708");
            final Date datumTot = dateFormat.parse("20120709");
            final List<Long> anummers = service.findLogs(datumVanaf, datumTot, null);
            assertNotNull(anummers);
            assertNotNull("Er zou een log moeten zijn.", anummers.get(0));
            assertEquals("Verkeerde anr.", anummers.get(0), Long.valueOf(2503828934l));
        } catch (final ParseException e) {
            fail("");
        }
    }

    /**
     * Zoekt een log record op anummer die niet bestaat.
     */
    @Test
    public void testFindNonExistingLog() {
        final Long anummer = 3832803549L;
        final InitVullingLog log = service.findLog(anummer);
        assertNull("Er zou geen log gevonden moeten zijn.", log);
    }

    /**
     * Maak een nieuwe log en voeg deze toe inclusief diff.
     */
    @Test
    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    public void testAddDiffToNewLog() {
        InitVullingLog log = new InitVullingLog();
        final Date now = new Date();
        log.setAnummer(2347323439L);
        log.setBerichtId(23473234);
        log.setBerichtType(1111);
        log.setDatumOpschorting(20120101);
        log.setDatumTijdOpnameGbav(now);
        log.setGemeenteVanInschrijving(1904);
        log.setPlId(112);
        log.setLo3Bericht("00940011800110010657965964901200095459314680210008Johannah0240006Maasje03100081975083103200041033033000460300410001V6110001N821000407628220008199409308230003PKA851000819750831861000819951001511770110010657965964901200095459314680210007Johanna0240004Maas03100081975083103200041033033000460300410001V6110001E821000407628220008199409308230003PKA851000819750831861000819951001020110240004Maas030110240004Maas04051051000400016310003001851000819750831861000819940930070776810008199409306910004076270100010801000400048020017200801111130410008710001P08132091000405180920008199603191010001W1030008200701091110017Frientenplantsoen1120001111600062575RE7210001I85100082007010986100082007011058129091000405180920008199603191010001W1030008200701091110013Doemaarstraat11200023111600062575RD7210001I851000820070109861000820070110140284010006251802851000820030627140284010006450101851000820020704140284010006890201851000819940603140284010006250001851000819940222");
        log.setBrpLo3Bericht("00940011800110010657965964901200095459314680210008Johannag0240006Maasje03100081975083103200041033033000460300410001V6110001N821000407628220008199409308230003PKA851000819750831861000819951001511770110010657965964901200095459314680210007Johanna0240004Maas03100081975083103200041033033000460300410001V6110001E821000407628220008199409308230003PKA851000819750831861000819951001020110240004Maas030110240004Maas04051051000400016310003001851000819750831861000819940930070776810008199409306910004076270100010801000400048020017200801111130410008710001P08132091000405180920008199603191010001W1030008200701091110017Frientenplantsoen1120001111600062575RE7210001I85100082007010986100082007011058129091000405180920008199603191010001W1030008200701091110013Doemaarstraat11200023111600062575RD7210001I851000820070109861000820070110140284010006251802851000820030627140284010006450101851000820020704140284010006890201851000819940603140284010006250001851000819940222");
        log.setRedenOpschorting('F');

        service.createAndStoreDiff(log);

        log = service.findLog(2347323439L);
        assertNotNull(log.getBerichtDiff());
        assertNotNull(log.getFingerPrints());
        assertTrue(log.getFingerPrints().size() > 0);
        assertEquals(log.getBerichtId(), Integer.valueOf(23473234));
        assertEquals(log.getDatumOpschorting(), Integer.valueOf(20120101));
        assertEquals(log.getDatumTijdOpnameGbav(), now);
        assertEquals(log.getGemeenteVanInschrijving(), Integer.valueOf(1904));
        assertEquals(log.getPlId(), Integer.valueOf(112));
        assertEquals(log.getRedenOpschorting(), Character.valueOf('F'));

    }
}
