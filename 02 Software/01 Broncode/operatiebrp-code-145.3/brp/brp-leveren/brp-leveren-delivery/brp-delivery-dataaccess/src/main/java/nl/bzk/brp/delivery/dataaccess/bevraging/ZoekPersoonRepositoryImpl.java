/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.brp.service.dalapi.QueryCancelledException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

/**
 * ZoekPersoonRepositoryImpl.
 */
@Repository
public final class ZoekPersoonRepositoryImpl implements ZoekPersoonRepository {

    private static final String SQL_TIMEOUT_CODE = "57014";

    @Inject
    private DataSource masterDataSource;

    @Value("${brp.bevraging.zoekpersoon.max.statement.duur.sec:10}")
    private int maxStatementDuurSec;

    private ZoekPersoonRepositoryImpl() {
    }

    @Override
    public List<Long> zoekPersonen(final SqlStamementZoekPersoon sql, final boolean postgres) throws QueryCancelledException {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(masterDataSource);
        if (postgres) {
            //statement timeout zorgt ook voor het cancellen van query op server
            jdbcTemplate.execute(String.format("set local statement_timeout = %d", TimeUnit.SECONDS.toMillis(maxStatementDuurSec)));
        }
        try {
            return jdbcTemplate.queryForList(sql.getSql(), Long.class, (Object[]) sql.getParameters().toArray());
        } catch (DataAccessException e) {
            if (e.getCause() != null && e.getCause() instanceof SQLException && SQL_TIMEOUT_CODE.equals(((SQLException) e.getCause()).getSQLState())) {
                throw new QueryCancelledException(e);
            }
            throw e;
        }
    }

    @Override
    public String bepaalQueryPlan(final SqlStamementZoekPersoon sql) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(masterDataSource);
        return jdbcTemplate.queryForObject("explain (format json) " + sql.getSql(), String.class, (Object[]) sql.getParameters().toArray());
    }

    @Override
    public boolean isPostgres() throws SQLException {
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(masterDataSource);
            final String url = connection.getMetaData().getURL();
            return url.contains("postgres");
        } finally {
            DataSourceUtils.releaseConnection(connection, masterDataSource);
        }
    }
}
