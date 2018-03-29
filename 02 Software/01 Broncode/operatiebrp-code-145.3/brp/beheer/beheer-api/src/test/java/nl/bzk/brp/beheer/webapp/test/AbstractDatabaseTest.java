/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.test;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@TestExecutionListeners(
        listeners = {TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class, DBUnitLoaderTestExecutionListener.class})
public abstract class AbstractDatabaseTest {

    @Inject
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

    protected Map<String, Object> getDienstbundel(final Integer id) {
        return get("autaut.dienstbundel", id);
    }

    protected Map<String, Object> getDienstbundelGroep(final Integer id) {
        return get("autaut.dienstbundelgroep", id);
    }

    protected Map<String, Object> getDienstbundelGroepAttribuut(final Integer id) {
        return get("autaut.dienstbundelgroepattr", id);
    }

    protected Map<String, Object> getBijhouderFiatteringsuitzondering(final Integer id) {
        return get("autaut.bijhouderfiatuitz", id);
    }

    protected Map<String, Object> getBijhoudingsautorisatie(final Integer id) {
        return get("autaut.bijhautorisatie", id);
    }

    protected List<Map<String, Object>> getBijhoudingsautorisatieSoortAdministratieveHandeling(final Integer id) {
        return getChildren("autaut.bijhautorisatiesrtadmhnd", "bijhautorisatie", id);
    }

    protected Map<String, Object> getLeveringsautorisatie(final Integer id) {
        return get("autaut.levsautorisatie", id);
    }

    protected Map<String, Object> getDienstbundelLO3Rubriek(final Integer id) {
        return get("autaut.dienstbundello3rubriek", id);
    }

    protected Map<String, Object> getToegangBijhoudingsautorisatie(final Integer id) {
        return get("autaut.toegangbijhautorisatie", id);
    }

    protected Map<String, Object> getToegangLeveringsautorisatie(final Integer id) {
        return get("autaut.toeganglevsautorisatie", id);
    }

    protected Map<String, Object> get(final String table, final Integer id) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        try {
            return jdbcTemplate.queryForMap("select * from " + table + " where id = ?", id);
        } catch (final EmptyResultDataAccessException e) {
            return null;
        }
    }

    protected List<Map<String, Object>> getChildren(final String table, final String parentColumn, final Integer parentId) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        try {
            return jdbcTemplate.queryForList("select * from " + table + " where " + parentColumn + " = ?", parentId);
        } catch (final EmptyResultDataAccessException e) {
            return null;
        }
    }
}
