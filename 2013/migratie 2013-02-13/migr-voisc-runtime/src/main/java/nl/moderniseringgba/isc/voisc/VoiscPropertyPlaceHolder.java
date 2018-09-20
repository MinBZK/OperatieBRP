/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 * Laad alle properties uit de database en voegt die toe aan de properties voor de PropertyPlaceHolder functionaliteit.
 */
public class VoiscPropertyPlaceHolder extends PropertyPlaceholderConfigurer {

    private static final Log LOGGER = LogFactory.getLog(VoiscPropertyPlaceHolder.class);
    private String configFile;
    private Properties voiscProperties;

    /**
     * Constructor.
     */
    public VoiscPropertyPlaceHolder() {
        super();
        setPlaceholderPrefix("${");
        setIgnoreUnresolvablePlaceholders(true);
    }

    @Override
    protected final void loadProperties(final Properties props) throws IOException {
        if (null == props) {
            throw new IOException("No properties passed by Spring framework - cannot proceed");
        }
        LOGGER.info("Reading configuration properties from database");
        try {
            final Properties properties = loadConfigProperties();

            final DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
            final JdbcTemplate template = new JdbcTemplate(dataSource);

            final String nameColumn = properties.getProperty("voisc.config.nameColumn");
            final String valueColumn = properties.getProperty("voisc.config.valueColumn");
            final String tableName = properties.getProperty("voisc.config.tableName");
            final String whereClause = properties.getProperty("voisc.config.whereClause");

            final String sql =
                    String.format("SELECT %s, %s FROM %s %s", nameColumn, valueColumn, tableName, whereClause);

            template.query(sql, new RowCallbackHandler() {
                @Override
                public void processRow(final ResultSet rs) throws SQLException {
                    final String name = rs.getString(nameColumn);
                    final String value = rs.getString(valueColumn);

                    if (null == name || null == value) {
                        throw new SQLException("Configuration database contains empty data. Name='"
                                + (name == null ? "" : name) + "', Value='" + (value == null ? "" : value) + "'.");
                    }

                    props.setProperty(name, value);
                }
            });
            // CHECKSTYLE:OFF - Alle fouten afvangen en een melding teruggeven daarvan, anders crasht de app.
        } catch (final Exception e) {
            LOGGER.fatal("There is an error in either 'application.properties' or the configuration database.");
            throw new IOException(e);
        }
        // CHECKSTYLE:ON
        if (props.size() == 0) {
            LOGGER.fatal("The configuration database could not be reached or does not contain any properties");
        }
        voiscProperties = props;
    }

    /**
     * Load config properties where to find the config database.
     * 
     * @return properties with all settings needed for the config database.
     * @throws IOException
     *             If property file cannot be found.
     */
    private Properties loadConfigProperties() throws IOException {
        final Properties properties = new Properties();
        final InputStream in = VoiscPropertyPlaceHolder.class.getClassLoader().getResourceAsStream(configFile);
        properties.load(in);
        in.close();
        return properties;
    }

    /**
     * @param configFile
     *            the configFile to set
     */
    public final void setConfigFile(final String configFile) {
        this.configFile = configFile;
    }

    /**
     * @return Poroperties de properties die gezet zijn.
     */
    public final Properties getProperties() {
        return voiscProperties;
    }
}
