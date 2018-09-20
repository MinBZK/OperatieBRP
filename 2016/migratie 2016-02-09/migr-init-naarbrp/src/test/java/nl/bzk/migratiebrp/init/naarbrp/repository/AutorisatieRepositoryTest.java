/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository;

import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.init.naarbrp.repository.AutorisatieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:runtime-test-beans.xml")
public class AutorisatieRepositoryTest {

    @Inject
    private AutorisatieRepository autorisatieRepository;

    @Inject
    private DataSource dataSource;

    @Before
    public void before() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("UPDATE initvul.initvullingresult_aut SET conversie_resultaat='TE_VERZENDEN'");
    }

    @Test
    public void test() {
        // TODO: MAAK TEST
    }

}
