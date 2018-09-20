/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.test;

import java.util.Map;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = RepositoryConfiguratie.TRANSACTION_MANAGER, defaultRollback = true)
@TestExecutionListeners(listeners = {TransactionalTestExecutionListener.class,
                                     DependencyInjectionTestExecutionListener.class,
                                     DBUnitLoaderTestExecutionListener.class
                                      })
public abstract class AbstractDatabaseTest {

    @Autowired
    @Named(value = RepositoryConfiguratie.DATA_SOURCE_MASTER)
    private DataSource dataSource;

    protected DataSource getDataSource() {
        return dataSource;
    }

    protected Map<String, Object> getAfleverwijze(final Integer id) {
        return get("autaut.afleverwijze", id);
    }

    protected Map<String, Object> getConversieLO3Rubriek(final Integer id) {
        return get("conv.convlo3rubriek", id);
    }

    protected Map<String, Object> getDienst(final Integer id) {
        return get("autaut.dienst", id);
    }

    protected Map<String, Object> getAbonnement(final Integer id) {
        return get("autaut.abonnement", id);
    }

    protected Map<String, Object> getAbonnementLO3Rubriek(final Integer id) {
        return get("autaut.abonnementlo3rubriek", id);
    }

    protected Map<String, Object> getToegangAbonnement(final Integer id) {
        return get("autaut.toegangabonnement", id);
    }

    protected Map<String, Object> get(final String table, final Integer id) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        try {
            return jdbcTemplate.queryForMap("select * from " + table + " where id = ?", id);
        } catch (final EmptyResultDataAccessException e) {
            return null;
        }
    }
}
