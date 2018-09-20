/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import nl.moderniseringgba.isc.migratie.repository.GemeenteRepository;
import nl.moderniseringgba.migratie.logging.service.LoggingService;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Test de spring configuratie. Gebruikt geen test spring config dus je moet een database beschikbaar hebben e.d.
 * Vandaar de ignore.
 */
@Ignore
@TransactionConfiguration(defaultRollback = false)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:controle-beans.xml" })
public class SpringTest {

    @Inject
    private BrpDalService brpDalService;

    @Inject
    private LoggingService loggingService;

    @Inject
    private GemeenteRepository gemeenteRepo;

    /**
     * 
     */
    @Test
    public void testSpringConfig() {
        assertNotNull(brpDalService);
        assertNotNull(loggingService);
        assertNotNull(gemeenteRepo);
    }
}
