/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

/**
 * Delete alle abonnementen kanaal.
 */
public final class DeleteAbonnementenKanaal extends LazyLoadingKanaal {

    /**
     * Constructor.
     */
    public DeleteAbonnementenKanaal() {
        super(new Worker(), new Configuration(
            "classpath:configuratie.xml",
            "classpath:infra-db-brp.xml",
            "classpath:infra-jta.xml",
            "classpath:infra-db-sync.xml",
            "classpath:infra-em-sync.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {
        @Inject
        @Named("brpDataSource")
        private DataSource brpDataSource;

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return "deleteAbonnementen";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            // Tijdelijk
            try (final Connection connection = brpDataSource.getConnection()) {
                connection.setAutoCommit(true);

                verwijderAfnemersindicaties(connection);

                verwijderAbonnementen(connection);

            } catch (final SQLException e) {
                throw new KanaalException("Probleem bij verwijderen partijen en abonnementen.", e);
            }
        }

        private void verwijderAfnemersindicaties(final Connection connection) throws SQLException {
            // Afnemersindicatie
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate("truncate autaut.his_persafnemerindicatie cascade");

                statement.executeUpdate("truncate autaut.persafnemerindicatie cascade");
            }
        }

        private void verwijderAbonnementen(final Connection connection) throws SQLException {
            // Abonnement
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate("truncate autaut.his_toeganglevsautorisatie cascade");
                statement.executeUpdate("truncate autaut.his_levsautorisatie cascade");
                statement.executeUpdate("truncate autaut.his_dienstbundel cascade");
                statement.executeUpdate("truncate autaut.his_dienstbundello3rubriek cascade");
                statement.executeUpdate("truncate autaut.his_dienstbundelgroep cascade");
                statement.executeUpdate("truncate autaut.his_dienstbundelgroepattr cascade");
                statement.executeUpdate("truncate autaut.his_dienst cascade");
                statement.executeUpdate("truncate autaut.his_dienstattendering cascade");
                statement.executeUpdate("truncate autaut.his_dienstselectie cascade");

                statement.executeUpdate("truncate autaut.toeganglevsautorisatie cascade");
                statement.executeUpdate("truncate autaut.levsautorisatie cascade");
                statement.executeUpdate("truncate autaut.dienstbundel cascade");
                statement.executeUpdate("truncate autaut.dienstbundello3rubriek cascade");
                statement.executeUpdate("truncate autaut.dienstbundelgroep cascade");
                statement.executeUpdate("truncate autaut.dienstbundelgroepattr cascade");
                statement.executeUpdate("truncate autaut.dienst cascade");
            }
        }
    }
}
