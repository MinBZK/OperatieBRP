/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.repository.jdbc;

import javax.inject.Inject;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.repository.ProefSynchronisatieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:/proefSynchronisatie-test-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcGbavRepositoryTest {

    private static final String DATUM_VANAF = "2013-01-01";
    private static final String DATUM_TOT = "2014-01-01";

    @Inject
    private ProefSynchronisatieRepository proefSynchronisatieRepository;

    @Test
    public void testCreate() {

        proefSynchronisatieRepository.laadInitProefSynchronisatieBerichtenTabel(DATUM_VANAF, DATUM_TOT);

    }
}
