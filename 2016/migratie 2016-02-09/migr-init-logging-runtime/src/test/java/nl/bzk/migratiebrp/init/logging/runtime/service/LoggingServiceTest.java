/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAutorisatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;

import org.junit.After;
import org.junit.Before;
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
@ContextConfiguration(locations = {"classpath:synchronisatie-logging-beans-test.xml" })
public class LoggingServiceTest {

    @Inject
    private LoggingService service;

    @Before
    public void setUp() {
        Logging.initContext();
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    /**
     * Zoekt een log record op anummer.
     */
    @Test
    public void testZoekLogOpAnummer() {
        final Long anummer = 3832803548L;
        final InitVullingLog log = service.zoekInitVullingLog(anummer);
        assertNotNull(log);

        final InitVullingLog reReadlog = service.zoekInitVullingLog(anummer);
        assertNotNull(reReadlog);
        assertEquals(log.getAnummer(), anummer);
    }

    /**
     * Zoekt een log record op anummer.
     */
    @Test
    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    public void testBepaalEnOpslaanVerschillen() {
        final Long anummer = 3832803548L;
        final InitVullingLog log = service.zoekInitVullingLog(anummer);
        log.setBerichtNaTerugConversie("Dummy BRP XML bericht");
        service.bepalenEnOpslaanVerschillen(log);

        final InitVullingLog reReadlog = service.zoekInitVullingLog(anummer);
        assertNotNull(reReadlog);
        assertEquals(log.getAnummer(), anummer);
        assertNotNull(log.getFoutmelding());
    }

    /**
     * Zoekt een log record op anummer die niet bestaat.
     */
    @Test
    public void testZoekNietBestaandLog() {
        final Long anummer = 3832803549L;
        final InitVullingLog log = service.zoekInitVullingLog(anummer);
        assertNull("Er zou geen log gevonden moeten zijn.", log);
    }

    /**
     * Zoekt een autorisatie log record op afnemercode.
     */
    @Test
    public void testZoekAutorisatieOpAfnemerCode() {
        final Integer afnemerCode = 301;
        InitVullingAutorisatie log = service.zoekInitVullingAutorisatie(afnemerCode);
        assertNull(log);

        log = new InitVullingAutorisatie();
        log.setAfnemerCode(afnemerCode);
        service.persisteerInitVullingAutorisatie(log);

        final InitVullingAutorisatie reReadlog = service.zoekInitVullingAutorisatie(afnemerCode);
        assertNotNull(reReadlog);
        assertEquals(log.getAfnemerCode(), afnemerCode);
    }

    /**
     * Zoekt een autorisatie log record op autorisatieId.
     */
    @Test
    public void testZoekAfnemersIndicatieOpAnummer() {
        final long anummer = 3832803548L;
        InitVullingAfnemersindicatie log = service.zoekInitVullingAfnemerindicatie(anummer);
        assertNull(log);

        log = new InitVullingAfnemersindicatie(anummer);
        service.persisteerInitVullingAfnemerindicatie(log);

        final InitVullingAfnemersindicatie reReadlog = service.zoekInitVullingAfnemerindicatie(anummer);
        // Log wordt niet gevonden omdat er eerst op een afnemerindicatieregel wordt gezocht
        assertNull(reReadlog);
    }
}
