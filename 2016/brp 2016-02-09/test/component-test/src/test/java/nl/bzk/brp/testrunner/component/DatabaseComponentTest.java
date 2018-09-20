/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import junit.framework.TestCase;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.testrunner.component.services.datatoegang.VerzoekMetEntityManager;
import nl.bzk.brp.testrunner.component.services.datatoegang.VerzoekMetJdbcTemplate;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class DatabaseComponentTest extends TestCase {


    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Test
    public void testMinimaleOmgeving() throws Exception {
        final Omgeving omgeving = new OmgevingBouwer().metLegeDatabase().maak();
        omgeving.start();
        omgeving.stop();
    }

    @Test
    public void testDataLezen() throws Exception {
        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().maak();
        omgeving.start();
        omgeving.database().voerUit(new VerzoekMetEntityManager() {
            @Override
            public void voerUitMet(final EntityManager entityManager) {
                final List<Gemeente> resultList = entityManager.createQuery("from Gemeente", Gemeente.class).getResultList();
                assertFalse(resultList.isEmpty());
            }
        });
        omgeving.stop();
    }

    @Test
    public void testDataSchrijven() {
        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().maak();
        omgeving.start();

        //read uncommitted
        omgeving.database().voerUitMetTransactie(new VerzoekMetEntityManager() {
            @Override
            public void voerUitMet(final EntityManager entityManager) {
                entityManager.createNativeQuery("delete from kern.gem").executeUpdate();
                final List<Gemeente> resultList = entityManager.createQuery("from Gemeente", Gemeente.class).getResultList();
                assertTrue(resultList.isEmpty());
            }
        });

        //read committed
        omgeving.database().voerUit(new VerzoekMetEntityManager() {
            @Override
            public void voerUitMet(final EntityManager entityManager) {
                final List<Gemeente> resultList = entityManager.createQuery("from Gemeente", Gemeente.class).getResultList();
                assertTrue(resultList.isEmpty());
            }
        });

        omgeving.stop();
    }

    @Test
    @Ignore
    public void testResetComponent() {
        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().maak();
        omgeving.start();

        // delete data
        omgeving.database().voerUitMetTransactie(new VerzoekMetEntityManager() {
            @Override
            public void voerUitMet(final EntityManager entityManager) {
                entityManager.createNativeQuery("delete from kern.gem").executeUpdate();
            }
        });

        // herstart component, state wordt verworpen
        omgeving.database().herstart();

        // data is hersteld
        omgeving.database().voerUit(new VerzoekMetEntityManager() {
            @Override
            public void voerUitMet(final EntityManager entityManager) {
                final List<Gemeente> resultList = entityManager.createQuery("from Gemeente", Gemeente.class).getResultList();
                assertFalse(resultList.isEmpty());
            }
        });
        omgeving.stop();

    }

    @Test
    public void testDataSchrijvenMetDSL() {

        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().maak();
        omgeving.start();
        omgeving.persoonDsl().uitBestand("/dsl/persoon1.groovy");
        omgeving.database().voerUit(new VerzoekMetEntityManager() {
            @Override
            public void voerUitMet(final EntityManager entityManager) {
                final BigInteger resultaat = (BigInteger) entityManager.createNativeQuery("select count(*) from Kern.Pers").getSingleResult();
                assertEquals(1, resultaat.intValue());
            }
        });
        omgeving.stop();
    }

    @Test
    public void testSqlScript() {

        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().maak();
        omgeving.start();
        omgeving.database().voerUit(new VerzoekMetJdbcTemplate() {
            @Override
            public void voerUitMet(JdbcTemplate jdbcTemplate) {
                final List list = jdbcTemplate.queryForList("select * from autaut.levsautorisatie");
                assertTrue(list.isEmpty());
            }
        });
        omgeving.leveringautorisaties().uitBestand("/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding");
        omgeving.database().voerUit(new VerzoekMetJdbcTemplate() {
            @Override
            public void voerUitMet(JdbcTemplate jdbcTemplate) {
                final List list = jdbcTemplate.queryForList("select * from autaut.levsautorisatie");
                assertFalse(list.isEmpty());
            }
        });
        omgeving.stop();
    }


}
