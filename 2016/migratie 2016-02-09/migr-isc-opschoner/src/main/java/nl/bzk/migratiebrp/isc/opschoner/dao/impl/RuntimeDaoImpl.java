/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao.impl;

import java.net.UnknownHostException;
import java.sql.Date;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.opschoner.dao.RuntimeDao;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * DAO klasse voor lock.
 */
public final class RuntimeDaoImpl implements RuntimeDao {

    @Inject
    private JdbcTemplate template;

    @Override
    public void voegRuntimeToe(final String runtimeNaam) {

        String clientNaam;
        try {
            clientNaam = java.net.InetAddress.getLocalHost().toString();
        } catch (final UnknownHostException e) {
            clientNaam = "Onbekend";
        }

        final Object[] parameters = new Object[] {runtimeNaam, new Date(System.currentTimeMillis()), clientNaam };

        final String query = "insert into mig_runtime (runtime_naam, startdatum, client_naam) values (?, ?, ?)";
        template.update(query, parameters);

    }

    @Override
    public void verwijderRuntime(final String runtimeNaam) {

        final Object[] parameters = new Object[] {runtimeNaam };

        final String query = "delete from mig_runtime where runtime_naam = ?";
        template.update(query, parameters);

    }

}
