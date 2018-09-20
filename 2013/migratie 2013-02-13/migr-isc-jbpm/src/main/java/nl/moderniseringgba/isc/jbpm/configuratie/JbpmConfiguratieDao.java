/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.configuratie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nl.moderniseringgba.isc.jbpm.JbpmDao;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jbpm.calendar.Duration;

/**
 * Configuratie DAO via JBPM connectie.
 */
public final class JbpmConfiguratieDao extends JbpmDao implements ConfiguratieDao {

    /**
     * Static beschikbare instantie van de configuratie dao (voor integratie in de processen).
     */
    public static final ConfiguratieDao INSTANCE = new JbpmConfiguratieDao();

    private static final String SELECT_SQL = "select waarde from mig_configuratie where configuratie = ?";

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String getConfiguratie(final String configuratie) {
        LOG.debug("getConfiguratie(configuratie={})", configuratie);
        return execute(new Sql<String>() {
            @Override
            public String execute(final Connection connection) throws SQLException {
                final PreparedStatement statement = connection.prepareStatement(SELECT_SQL);

                statement.setString(1, configuratie);

                final ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    return null;
                }

                final String waarde = resultSet.getString(1);

                statement.close();

                return waarde;
            }
        });
    }

    @Override
    public Integer getConfiguratieAsInteger(final String configuratie) {
        final String waarde = getConfiguratie(configuratie);
        return waarde == null || "".equals(waarde) ? null : Integer.valueOf(getConfiguratie(configuratie));
    }

    @Override
    public Duration getConfiguratieAsDuration(final String configuratie) {
        final String waarde = getConfiguratie(configuratie);
        return waarde == null || "".equals(waarde) ? null : new Duration(waarde);
    }

}
