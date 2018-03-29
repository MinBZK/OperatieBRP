/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Basis implementatie voor repository.
 */
class BasisJdbcRepository {

    /**
     * Transactionmanager.
     */
    static final String INIT_RUNTIME_TRANSACTION_MANAGER = "init-runtime-transactionManager";

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final int FETCH_SIZE = 1000;

    private NamedParameterJdbcTemplate jdbcTemplate;
    private PlatformTransactionManager platformTransactionManager;

    /**
     * Set the datasource to use.
     * @param dataSource the datasource to use
     */
    @Resource(name = "init-runtime-datasource")
    public final void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        ((JdbcTemplate) jdbcTemplate.getJdbcOperations()).setFetchSize(FETCH_SIZE);
    }

    /**
     * Set the platform transaction manager to use.
     * @param platformTransactionManager the platform transaction manager to use
     */
    @Resource
    public final void setPlatformTransactionManager(final PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
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
    final String getStringResourceData(final String resourcePath) {
        try (InputStream inputStream = BasisJdbcRepository.class.getResourceAsStream(resourcePath)) {
            if(inputStream == null) {
                throw new IOException("Resource does not exist.");
            }
            return IOUtils.toString(inputStream, Charset.defaultCharset());
        } catch (final IOException e) {
            LOG.error("Fout bij lezen van resource file: " + resourcePath, e);
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Voert het gegeven SQL script uit.
     * @param sql sql script
     */
    final void executeSql(final String sql) {
        // Split script
        final List<String> statements = new ArrayList<>();
        ScriptUtils.splitSqlScript(sql, ";", statements);

        final TransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        final TransactionTemplate transaction = new TransactionTemplate(platformTransactionManager, transactionDefinition);
        for(final String statement : statements) {
            transaction.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(final TransactionStatus transactionStatus) {
                    jdbcTemplate.getJdbcOperations().execute(statement);
                }
            });
        }
    }
}
