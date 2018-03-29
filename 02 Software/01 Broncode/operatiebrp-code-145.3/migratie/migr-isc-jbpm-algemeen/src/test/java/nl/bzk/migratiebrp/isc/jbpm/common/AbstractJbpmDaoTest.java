/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(transactionManager = "iscTransactionManager")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Rollback(value = false)
@ContextConfiguration({"classpath:isc-jbpm-algemeen.xml",
        "classpath*:isc-jbpm-usecase-beans.xml",
        "classpath:test-datasource.xml",
        "classpath:test-jta.xml",
        "classpath:test-outbound.xml",})
public abstract class AbstractJbpmDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Autowired
    private JbpmConfiguration jbpmConfiguration;
    protected JbpmContext jbpmContext;

    private final String[] sqlScripts;

    protected AbstractJbpmDaoTest(final String... sqlScripts) {
        this.sqlScripts = sqlScripts;
    }

    /**
     * Geef de waarde van connection.
     * @return connection
     */
    protected Session getSession() {
        return (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
    }

    @Before
    public void setupTables() {
        jbpmContext = jbpmConfiguration.createJbpmContext();

        getSession().doWork(new ExecuteScriptWork("/sql/mig-drop.sql", true));
        for (final String sqlScript : sqlScripts) {
            // Ignore failures, vanwege initially deferred fk constraint in de scripts.
            // Dit statement faalt op een H2 database. Tests draaien prima zonder FK.
            getSession().doWork(new ExecuteScriptWork(sqlScript, true));
        }
    }

    private static final class ExecuteScriptWork implements Work {

        private final String scriptResource;
        private final boolean ignoreErrors;

        public ExecuteScriptWork(final String scriptResource, final boolean ignoreErrors) {
            this.scriptResource = scriptResource;
            this.ignoreErrors = ignoreErrors;
        }

        @Override
        public void execute(final Connection connection) throws SQLException {
            try (InputStream scriptStream = this.getClass().getResourceAsStream(scriptResource)) {
                final String sqlScript = IOUtils.toString(scriptStream);
                final List<String> statements = new LinkedList<String>();
                ScriptUtils.splitSqlScript(sqlScript, ';', statements);
                int lineNumber = 0;
                try (final Statement sqlStatement = connection.createStatement()) {
                    for (final String statement : statements) {
                        lineNumber++;
                        sqlStatement.executeUpdate(statement);

                    }
                } catch (final SQLException e) {
                    if (ignoreErrors) {
                        LOG.info("Ignoring SQL error '{}' on line {}", e.getMessage());
                    } else {
                        throw new IllegalArgumentException("SQL error on line " + lineNumber, e);
                    }
                }
            } catch (final IOException e) {
                throw new IllegalArgumentException("Could not read resource", e);
            }
        }
    }

    @After
    public void destroyContext() {
        jbpmContext.close();
    }
}
