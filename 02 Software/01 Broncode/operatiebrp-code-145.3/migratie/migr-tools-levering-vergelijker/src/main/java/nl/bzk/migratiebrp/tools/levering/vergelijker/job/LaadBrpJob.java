/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.job;

import javax.sql.DataSource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Job voor het vullen van de BRP database.
 */
public final class LaadBrpJob {
    private static final int FETCH_SIZE = 1000;

    /**
     * Main execute methode voor het uitvoeren van de job.
     * @param context De context van de job.
     */
    public void execute(final ConfigurableApplicationContext context) {

        final DataSource dataSource = context.getBean("leveringVergelijkerDataSource", DataSource.class);
        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        ((JdbcTemplate) jdbcTemplate.getJdbcOperations()).setFetchSize(FETCH_SIZE);

        final String sqlCreateEnVul = ResourceUtil.getStringResourceData(this, "/sql/leveringsvergelijking-create-en-vul-brp.sql");
        jdbcTemplate.getJdbcOperations().execute(sqlCreateEnVul);
    }
}
