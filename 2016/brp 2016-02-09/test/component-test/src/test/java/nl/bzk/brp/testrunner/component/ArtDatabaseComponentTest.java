/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import junit.framework.TestCase;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.component.services.datatoegang.VerzoekMetJdbcTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(JUnit4.class)
public class ArtDatabaseComponentTest extends TestCase {


    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Test
    public void testMinimaleOmgeving() throws Exception {
        final BrpOmgeving omgeving = new OmgevingBouwer().metArtDataDatabase().maak();
        omgeving.start();
        omgeving.stop();
    }

    @Test
    public void testDataLezen() throws Exception {
        final BrpOmgeving omgeving = new OmgevingBouwer().metArtDataDatabase().maak();
        omgeving.start();
        omgeving.database().voerUit(new VerzoekMetJdbcTemplate() {
            @Override
            public void voerUitMet(final JdbcTemplate jdbcTemplate) {
                final Integer integer = jdbcTemplate.queryForObject("select count(*) from kern.pers", Integer.class);
                assertTrue(integer > 0);
            }
        });

        omgeving.database().leegDatabase();
        omgeving.database().voerUit(new VerzoekMetJdbcTemplate() {
            @Override
            public void voerUitMet(final JdbcTemplate jdbcTemplate) {
                final Integer integer = jdbcTemplate.queryForObject("select count(*) from kern.pers", Integer.class);
                assertTrue(integer == 0);
            }
        });
        omgeving.stop();
    }



}
