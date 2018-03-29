/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.configuratie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class met configuratie voor de database.
 */
public final class DatabaseConfig {
    private static final String DEFAULT_USERNAME_PASSWORD = "brp";
    private static final String DEFAULT_PORT = "5432";

    private String driverClassName;
    private String username;
    private String password;
    private String url;
    private String host;
    private String port;
    private String databaseName;

    /**
     * Maakt een database configuratie met default waarden.
     * @param host host van de database
     * @param databaseName naam van de database
     */
    DatabaseConfig(final String host, final String databaseName) {
        this.driverClassName = "org.postgresql.xa.PGXADataSource";
        this.username = DEFAULT_USERNAME_PASSWORD;
        this.password = DEFAULT_USERNAME_PASSWORD;
        this.url = String.format("jdbc:postgresql://%s/%s", host, databaseName);
        this.host = host;
        this.port = DEFAULT_PORT;
        this.databaseName = databaseName;
    }


    /**
     * Maakt / zoekt de configiratie voor een specifieke database.
     * @param driverClassName de classname voor de driver
     * @param username gebruikersnaam
     * @param password wachtwoord
     * @param url de URL van de database
     */
    DatabaseConfig(final String driverClassName, final String username, final String password, final String url) {
        this.driverClassName = driverClassName;
        this.username = username;
        this.password = password;
        this.url = url;
        final Matcher matcher = Pattern.compile("jdbc:postgresql://(.+)/(.+)").matcher(url);
        if (matcher.find()) {
            final String [] hostPort = matcher.group(1).split(":");
            this.host = hostPort[0];
            this.port = hostPort[1];
            this.databaseName = matcher.group(2);
        }
    }

    /**
     * Geeft de classname van de driver terug.
     * @return classname van de driver
     */
    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * Geeft de username terug.
     * @return de username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Geeft het password terug.
     * @return het password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Geeft de URL terug van de database.
     * @return de URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Geeft de host terug.
     * @return de host
     */
    public String getHost() {
        return host;
    }

    /**
     * Geeft de port terug.
     * @return de port
     */
    public String getPort() {
        return port;
    }

    /**
     * Geeft de database naam terug.
     * @return de database naam
     */
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("driverClassName", driverClassName)
                .append("username", username)
                .append("password", password)
                .append("url", url)
                .toString();
    }
}
