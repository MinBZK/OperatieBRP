/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.repository.jdbc;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Resource;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Basis implementatie voor repository.
 */
public abstract class AbstractJdbcRepository {

    /**
     * Transactionmanager.
     */
    protected static final String PROEF_SYNCHRONISATIE_TRANSACTION_MANAGER = "proefSynchronisatie-transactionManager";

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final int FETCH_SIZE = 1000;

    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Set the datasource to use.
     * @param dataSource the datasource to use
     */
    @Resource(name = "proefSynchronisatie-datasource")
    public final void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        ((JdbcTemplate) jdbcTemplate.getJdbcOperations()).setFetchSize(FETCH_SIZE);
    }

    /**
     * Geef de waarde van jdbc template.
     * @return jdbc template
     */
    protected final NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * Haalt de string op die in het opgegeven resource pad zit.
     * @param resourcePath Het pad van de resource.
     * @return String representatie van de resource.
     */
    protected final String getStringResourceData(final String resourcePath) {
        final InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        try {
            final String data = IOUtils.toString(inputStream);
            inputStream.close();
            return data;
        } catch (final IOException e) {
            LOG.error("Fout bij lezen van resource file: " + resourcePath, e);
            return null;
        }
    }
}
