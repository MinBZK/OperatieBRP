/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.junit.After;
import org.junit.Before;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.SmartDataSource;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

public abstract class AbstractJbpmDaoTest {

    protected JbpmContext jbpmContext;

    private final String sqlScript;

    protected AbstractJbpmDaoTest(final String sqlScript) {
        this.sqlScript = sqlScript;
    }

    @Before
    @SuppressWarnings("deprecation")
    public void setupTables() {
        final JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
        jbpmContext = jbpmConfiguration.createJbpmContext();
        final Session session = jbpmContext.getSession();
        final Connection connection = session.connection();
        final DataSource dataSource = new ConnectionDatasource(connection);

        final SimpleJdbcTemplate template = new SimpleJdbcTemplate(dataSource);
        final ResourceLoader resourceLoader = new DefaultResourceLoader();
        SimpleJdbcTestUtils.executeSqlScript(template, resourceLoader, sqlScript, true);
    }

    @After
    public void destroyContext() {
        jbpmContext.close();
    }

    private static final class ConnectionDatasource implements SmartDataSource {

        private final Connection connection;

        ConnectionDatasource(final Connection connection) {
            this.connection = connection;
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return null;
        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return 0;
        }

        @Override
        public void setLogWriter(final PrintWriter arg0) throws SQLException {
        }

        @Override
        public void setLoginTimeout(final int arg0) throws SQLException {
        }

        @Override
        public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
            return false;
        }

        @Override
        public <T> T unwrap(final Class<T> arg0) throws SQLException {
            return null;
        }

        @Override
        public Connection getConnection() throws SQLException {
            return connection;
        }

        @Override
        public Connection getConnection(final String username, final String password) throws SQLException {
            return connection;
        }

        @Override
        public boolean shouldClose(final Connection con) {
            return false;
        }

    }
}
