/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * TODO: Add documentation
 */
@Component
public class RuimBlobsOpStap implements Command {

    private JdbcTemplate jdbcTemplate;

    @Inject
    @Named("dataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean execute(final Context context) throws Exception {

        jdbcTemplate.execute("TRUNCATE kern.plblob");

        return false;
    }
}