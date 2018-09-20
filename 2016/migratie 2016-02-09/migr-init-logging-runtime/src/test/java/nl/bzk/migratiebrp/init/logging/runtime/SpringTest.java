/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import nl.bzk.migratiebrp.init.logging.runtime.repository.InitVullingLogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional
@Rollback(false)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:synchronisatie-logging-beans-test.xml", "classpath:synchronisatie-logging-jms-test.xml" })
public class SpringTest {

    @Inject
    private InitVullingLogRepository logRepository;

    /**
     *
     */
    @Test
    public void testSpringConfig() {
        assertNotNull(logRepository);
    }
}
