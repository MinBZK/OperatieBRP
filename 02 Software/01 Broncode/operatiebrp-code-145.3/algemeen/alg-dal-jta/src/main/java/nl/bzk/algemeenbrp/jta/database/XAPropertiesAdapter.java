/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.jta.database;

import java.util.Properties;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

/**
 * XA Properties adapter. De property namen voor de verschillende XA datasources zijn niet uniform.
 * Deze verschillen worden opgevangen door deze adapter.
 *
 * De datasource kan nu als volgt gedefinieerd worden:
 *
 * <pre>
 * {@code
 * <bean name="myDataSource"
 * class="com.atomikos.jdbc.AtomikosDataSourceBean"
 * init-method="init"
 * destroy-method="close">
 * <property name="uniqueResourceName">
 * <!--
 * | Tijdens jUnit testen kan de ApplicationContext meerdere keren worden geinstantieerd.
 * | Elke keer dient een unique naam gebruikt te worden, anders gaat Atomikos fout.
 * -->
 * <bean class="nl.bzk.algemeenbrp.jta.util.UniqueName">
 * <property name="baseName" value="myDatabase"/>
 * </bean>
 * </property>
 * <property name="xaDataSourceClassName"
 * value="${database.driver:org.postgresql.xa.PGXADataSource}"/>
 * <property name="xaProperties">
 * <bean class="nl.bzk.algemeenbrp.jta.database.XAPropertiesAdapter">
 * <property name="driver"
 * value="${database.driver:org.postgresql.xa.PGXADataSource}"/>
 * <property name="host" value="${database.host:localhost}" />
 * <property name="port" value="${database.port:5432}" />
 * <property name="name" value="${database.name:brp}" />
 * <property name="user" value="${database.username:postgres}" />
 * <property name="password" value="${database.password:postgres}" />
 * </bean>
 * </property>
 * <property name="maxPoolSize" value="${database.maxpool:20}"/>
 * <property name="minPoolSize" value="${database.minpool:3}"/>
 * </bean>
 * }
 * </pre>
 */
public class XAPropertiesAdapter implements FactoryBean<Properties>, InitializingBean {

    private String driver;
    private String url;
    private String host;
    private Integer port;
    private String name;
    private String user;
    private String password;

    private Properties xaProperties;

    /**
     * Zet de JDBC driver klasse naam.
     * @param driver JDBC driver klasse naam
     */
    @Required
    public void setDriver(final String driver) {
        this.driver = driver;
    }

    /**
     * Zet de database URL. Deze property heeft precedence boven het lost specificeren van de host en port.
     * @param url database URL
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * Zet de database host.
     * @param host host
     */
    public void setHost(final String host) {
        this.host = host;
    }

    /**
     * Zet de database poort.
     * @param port port
     */
    public void setPort(final Integer port) {
        this.port = port;
    }

    /**
     * Zet de database naam.
     * @param name naam
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Zet de database gebruikersnaam.
     * @param user gebruikersnaam
     */
    public void setUser(final String user) {
        this.user = user;
    }

    /**
     * Zet het database wachtwoord.
     * @param password wachtwoord
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public void afterPropertiesSet() {
        final Type type = Type.getByDriverClassName(driver);
        xaProperties = type.buildProperties(url, host, port, name, user, password);
    }

    @Override
    public Properties getObject() {
        return xaProperties;
    }

    @Override
    public Class<?> getObjectType() {
        return Properties.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * Ondersteunde database type om XA properties voor te maken.
     */
    private enum Type {
        /**
         * PostgreSQL.
         */
        POSTGRESQL("org.postgresql.xa.PGXADataSource") {
            @Override
            Properties buildProperties(final String url, final String serverName, final Integer portNumber, final String databaseName, final String user,
                                       final String password) {
                final Properties properties = new Properties();
                if (url == null) {
                    properties.setProperty("ServerName", serverName);
                    if (portNumber != null) {
                        properties.setProperty("PortNumber", Integer.toString(portNumber));
                    }
                    properties.setProperty("DatabaseName", databaseName);
                    if (user != null) {
                        properties.setProperty("User", user);
                    }
                    if (password != null) {
                        properties.setProperty("Password", password);
                    }
                } else {
                    properties.setProperty("Url", url);
                }

                return properties;
            }
        },

        /**
         * HsqlDB.
         */
        HSQLDB("org.hsqldb.jdbc.pool.JDBCXADataSource") {
            @Override
            Properties buildProperties(final String url, final String serverName, final Integer portNumber, final String databaseName, final String user,
                                       final String password) {
                final Properties properties = new Properties();
                if (url == null) {
                    properties.setProperty("Url",
                            "jdbc:hsqldb:hsql://" + serverName + (portNumber == null ? "" : ":" + Integer.toString(portNumber)) + "/" + databaseName);
                } else {
                    properties.setProperty("Url", url);
                }
                if (user != null) {
                    properties.setProperty("User", user);
                }
                if (password != null) {
                    properties.setProperty("Password", password);
                }

                return properties;
            }
        };

        private final String driverClassName;

        /**
         * Constructor.
         * @param driverClassName JDBC driver klasse naam
         */
        Type(final String driverClassName) {
            this.driverClassName = driverClassName;
        }

        /**
         * Zoek het database type op basis van de JDBC driver klasse naam.
         * @param driverClassName JDBC driver klasse naam
         * @return database type
         * @throws IllegalArgumentException als het database type niet gevonden kan worden
         */
        public static Type getByDriverClassName(final String driverClassName) {
            for (final Type type : values()) {
                if (type.driverClassName.equals(driverClassName)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("XaDataSource '" + driverClassName + "' not supported.");
        }

        /**
         * Bouw de XA properties met de gegeven configuratie.
         * @param serverName database server naam
         * @param portNumber database poort nummer
         * @param databaseName database naam
         * @param user database gebruiker
         * @param password database wachtwoord
         * @return xa properties
         */
        abstract Properties buildProperties(String url, String serverName, Integer portNumber, String databaseName, String user, String password);
    }
}
