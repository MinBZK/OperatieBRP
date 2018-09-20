/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.controle.check;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Check database connection.
 */
public final class CheckDatabase implements Check {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final CheckPing ping;

    private final String protocol;
    private final String host;
    private final String database;
    private String user;
    private String password;

    /**
     * Constructor.
     *
     * @param protocol
     *            protocol
     * @param host
     *            host
     * @param database
     *            database
     */
    public CheckDatabase(final String protocol, final String host, final String database) {
        try {
            loadDriver(protocol);
        } catch (final ClassNotFoundException e) {
            LOG.error("DATABASE: Database driver niet geladen.");
            e.printStackTrace();
        }

        ping = new CheckPing(host);

        this.protocol = protocol;
        this.host = host;
        this.database = database;
    }

    private static void loadDriver(final String protocol) throws ClassNotFoundException {
        switch (protocol.toLowerCase()) {
            case "postgresql":
                Class.forName("org.postgresql.Driver");
                break;
            default:
                LOG.error("DATABASE: Onbekend protocol. Geen driver pre-loaded.");
        }
    }

    /**
     * Zet de waarde van user.
     *
     * @param user
     *            user
     */
    public void setUser(final String user) {
        this.user = user;
    }

    /**
     * Zet de waarde van password.
     *
     * @param password
     *            password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public void check() {
        ping.check();

        LOG.info("DATABASE: Controleren toegang database '" + database + "' op " + host);
        final Properties connectionProps = new Properties();
        if (user != null) {
            connectionProps.put("user", user);
        }
        if (password != null) {
            connectionProps.put("password", password);
        }
        try (Connection conn = DriverManager.getConnection("jdbc:" + protocol + "://" + host + "/" + database, connectionProps)) {
            LOG.info("DATABASE: Succesvol verbonden met de database.");
        } catch (final SQLException e) {
            LOG.error("DATABASE: Er kan *GEEN* verbinding worden gemaakt met de database");
            LOG.error(ExceptionUtils.getStackTrace(e));
        }
    }

}
