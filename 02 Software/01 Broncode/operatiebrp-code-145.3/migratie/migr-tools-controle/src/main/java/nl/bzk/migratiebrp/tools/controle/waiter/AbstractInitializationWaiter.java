/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.controle.waiter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;

/**
 * Controle of een component is gestart.
 */
public abstract class AbstractInitializationWaiter {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final InitializationConfig config;

    /**
     * Constructor.
     * @param config configuratie
     */
    protected AbstractInitializationWaiter(final InitializationConfig config) {
        this.config = config;
    }

    /**
     * Voer de controles uit.
     */
    public final void check() {
        LOG.info("Controle op initialisatie {} gestart", config.getIdentificatie());
        checkJdbc();
        checkJmx();
        checkHttp();

        LOG.info("{} gestart", config.getIdentificatie());
    }

    private void checkJmx() {
        if (config.hasJmx()) {
            try {
                LOG.info("Controle initialisatie {} via JMX", config.getIdentificatie());
                LOG.info("JMX url: {} (username={})", config.getJmxUrl(), config.getJmxUsername());
                final Herhaal herhaling = new Herhaal(10000, 30, Herhaal.Strategie.REGELMATIG);
                herhaling.herhaal(new Runnable() {
                    @Override
                    public void run() {
                        final Map<String, Object> environment = new HashMap<>();
                        if (config.hasJmxCredentials()) {
                            environment.put(JMXConnector.CREDENTIALS, new String[]{config.getJmxUsername(), config.getJmxPassword()});
                        }
                        try (final JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(config.getJmxUrl()), environment)) {
                            final MBeanServerConnection connection = connector.getMBeanServerConnection();

                            check(connection);
                        } catch (final
                        IOException
                                | JMException e) {
                            LOG.info("Controle initialisatie {} via JMX gefaald", config.getIdentificatie(), e);
                            throw new RuntimeException("Controle initialisatie " + config.getIdentificatie() + " via JMX gefaald");
                        }
                    }
                });
            } catch (final HerhaalException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkJdbc() {
        if (config.hasJdbc()) {
            try {
                LOG.info("Controle initialisatie {} via JDBC", config.getIdentificatie());
                LOG.info("JDBC url: {} (username={})", config.getJdbcUrl(), config.getJdbcUsername());
                final Herhaal herhaling = new Herhaal(10000, 30, Herhaal.Strategie.REGELMATIG);
                herhaling.herhaal(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (config.getJdbcDriver() != null) {
                                Class.forName(config.getJdbcDriver());
                            }
                            try (Connection connection =
                                         DriverManager.getConnection(config.getJdbcUrl(), config.getJdbcUsername(), config.getJdbcPassword())) {
                                check(connection);
                            }
                        } catch (final
                        ClassNotFoundException
                                | SQLException e) {
                            LOG.info("Controle initialisatie {} via JDBC gefaald", config.getIdentificatie(), e);
                            throw new RuntimeException("Controle initialisatie " + config.getIdentificatie() + " via JDBC gefaald");
                        }
                    }
                });
            } catch (final HerhaalException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkHttp() {
        if (config.hasHttp()) {
            try {
                LOG.info("Controle initialisatie {} via HTTP", config.getIdentificatie());
                LOG.info("HTTP url: {}", config.getHttpUrl());
                final Herhaal herhaling = new Herhaal(10000, 30, Herhaal.Strategie.REGELMATIG);
                herhaling.herhaal(new Runnable() {
                    @Override
                    public void run() {
                        LOG.error("Controle initialisatie via HTTP nog niet geimplementeerd");
                    }
                });
            } catch (final HerhaalException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Hook om additionele controles via JMX te doen.
     * @param connection jmx connectie
     * @throws JMException bij fouten
     * @throws IOException bij fouten
     */
    protected void check(final MBeanServerConnection connection) throws JMException, IOException {
    }

    /**
     * Helper om te controleren dat een bepaalde MBean bestaat door het opvragen van de MBeanInfo.
     * @param connection JMX connectie
     * @param name object name van de MBean
     * @throws JMException bij fouten
     * @throws IOException bij fouten
     */
    protected static void checkMBeanInfo(final MBeanServerConnection connection, final String name) throws JMException, IOException {
        connection.getMBeanInfo(new ObjectName(name));
    }

    /**
     * Hook om additionele controles via JDBC te doen.
     * @param connection jdbc connectie
     * @throws SQLException bij fouten
     */
    protected void check(final Connection connection) throws SQLException {
    }

    /**
     * Helper om te controleren dat een bepaalde query uitgevoerd kan worden (vraagt de eerste rij op).
     * @param connection jdbc connectie
     * @param query query uit te voeren query
     * @throws SQLException bij fouten
     */
    protected static void checkQuery(final Connection connection, final String query) throws SQLException {
        try (final PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            result.next();
        }
    }

    /**
     * Configuratie.
     */
    public static final class InitializationConfig {
        private final String identificatie;
        private String jmxUrl;
        private String jmxUsername;
        private String jmxPassword;
        private String jdbcDriver;
        private String jdbcUrl;
        private String jdbcUsername;
        private String jdbcPassword;
        private String httpUrl;

        /**
         * Constructor.
         * @param identificatie identificatie
         */
        public InitializationConfig(final String identificatie) {
            this.identificatie = identificatie;
        }

        public String getIdentificatie() {
            return identificatie;
        }

        public boolean hasJmx() {
            return jmxUrl != null;
        }

        public String getJmxUrl() {
            return jmxUrl;
        }

        public boolean hasJmxCredentials() {
            return jmxUsername != null;
        }

        public String getJmxUsername() {
            return jmxUsername;
        }

        public String getJmxPassword() {
            return jmxPassword;
        }

        public boolean hasJdbc() {
            return jdbcUrl != null;
        }

        public String getJdbcDriver() {
            return jdbcDriver;
        }

        public String getJdbcUrl() {
            return jdbcUrl;
        }

        public String getJdbcUsername() {
            return jdbcUsername;
        }

        public String getJdbcPassword() {
            return jdbcPassword;
        }

        public boolean hasHttp() {
            return httpUrl != null;
        }

        public String getHttpUrl() {
            return httpUrl;
        }

        /**
         * Default configuratie.
         * @return this
         */
        public InitializationConfig withSystemConfig() {
            InitializationConfig result =
                    withJmx(
                            PropertyUtil.getProperty("jmx.service.url"),
                            PropertyUtil.getProperty("jmx.service.username"),
                            PropertyUtil.getProperty("jmx.service.password"));
            result =
                    result.withJdbc(
                            PropertyUtil.getProperty("jdbc.driver"),
                            PropertyUtil.getProperty("jdbc.url"),
                            PropertyUtil.getProperty("jdbc.username"),
                            PropertyUtil.getProperty("jdbc.password"));
            result = result.withHttp(PropertyUtil.getProperty("http.url"));
            return result;
        }

        /**
         * JMX configuratie.
         * @param jmxUrl url
         * @param jmxUsername username
         * @param jmxPassword password
         */
        public InitializationConfig withJmx(final String url, final String username, final String password) {
            jmxUrl = url;
            jmxUsername = username;
            jmxPassword = password;
            return this;
        }

        /**
         * Jdbc configuratie.
         * @param driver driver
         * @param url url
         * @param username username
         * @param password password
         * @return this
         */
        public InitializationConfig withJdbc(final String driver, final String url, final String username, final String password) {
            jdbcDriver = driver;
            jdbcUrl = url;
            jdbcUsername = username;
            jdbcPassword = password;
            return this;
        }

        /**
         * Http configuratie.
         * @param url url
         * @return this
         */
        public InitializationConfig withHttp(final String url) {
            httpUrl = url;
            return this;
        }
    }
}
