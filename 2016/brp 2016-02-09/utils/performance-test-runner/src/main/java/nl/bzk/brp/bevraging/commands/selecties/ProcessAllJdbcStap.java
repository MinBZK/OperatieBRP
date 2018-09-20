/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands.selecties;

import java.util.List;

import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.bevraging.commands.BevraagInfo;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Stap die met JDBC alle persoonBlobs ophaalt.
 */
@Component
public class ProcessAllJdbcStap implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAllJdbcStap.class);

    private final Integer PAGE_SIZE = 200;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Named("dataSource")
    public void setDatasource(DataSource datasource) {
        jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Autowired
    private ResultSetExtractor<List<BevraagInfo>> resultSetExtractor;

    @Override
    @Transactional
    public boolean execute(final Context context) throws Exception {
        jdbcTemplate.setFetchSize(PAGE_SIZE);

        try {
            List<BevraagInfo> result = jdbcTemplate.query("SELECT pershistorievollediggegevens FROM kern.perscache ORDER BY pers;", resultSetExtractor);

            context.put(ContextParameterNames.TASK_INFO_LIJST, result);
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }

        return CONTINUE_PROCESSING;
    }
}
