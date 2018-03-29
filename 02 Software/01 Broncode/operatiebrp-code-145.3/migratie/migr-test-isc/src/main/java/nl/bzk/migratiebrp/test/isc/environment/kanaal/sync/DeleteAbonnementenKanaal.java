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
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

/**
 * Delete alle abonnementen kanaal.
 */
public final class DeleteAbonnementenKanaal extends LazyLoadingKanaal {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public DeleteAbonnementenKanaal() {
        super(new Worker(),
                new Configuration(
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
            try (Connection connection = brpDataSource.getConnection()) {
                connection.setAutoCommit(true);
                verwijderAfnemersindicaties(connection);
                verwijderAbonnementen(connection);

            } catch (final SQLException e) {
                throw new KanaalException("Probleem bij verwijderen partijen en abonnementen.", e);
            }
        }

        private void verwijderAfnemersindicaties(final Connection connection) throws SQLException {
            // Afnemersindicatie
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("truncate autaut.his_persafnemerindicatie cascade");
                statement.executeUpdate("truncate autaut.persafnemerindicatie cascade");
            }
        }

        private void verwijderAbonnementen(final Connection connection) throws SQLException {
            // Abonnement
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("truncate autaut.his_toeganglevsautorisatie cascade");
                statement.executeUpdate("truncate autaut.his_levsautorisatie cascade");
                statement.executeUpdate("truncate autaut.his_dienstbundel cascade");
                statement.executeUpdate("truncate autaut.his_dienstsel cascade");
                statement.executeUpdate("truncate autaut.his_dienstzoeken cascade");
                statement.executeUpdate("truncate autaut.his_dienst cascade");
                statement.executeUpdate("truncate autaut.his_dienstattendering cascade");

                statement.executeUpdate("truncate autaut.his_bijhautorisatie cascade");
                statement.executeUpdate("truncate autaut.his_bijhautorisatiesrtadmhnd cascade");
                statement.executeUpdate("truncate autaut.his_toegangbijhautorisatie cascade");
                statement.executeUpdate("truncate autaut.his_bijhouderfiatuitz cascade");

                statement.executeUpdate("truncate autaut.toeganglevsautorisatie cascade");
                statement.executeUpdate("truncate autaut.levsautorisatie cascade");
                statement.executeUpdate("truncate autaut.dienstbundel cascade");
                statement.executeUpdate("truncate autaut.dienstbundello3rubriek cascade");
                statement.executeUpdate("truncate autaut.dienstbundelgroep cascade");
                statement.executeUpdate("truncate autaut.dienstbundelgroepattr cascade");
                statement.executeUpdate("truncate autaut.seltaak cascade");
                statement.executeUpdate("truncate autaut.dienst cascade");

                statement.executeUpdate("truncate autaut.bijhautorisatiesrtadmhnd cascade");
                statement.executeUpdate("truncate autaut.toegangbijhautorisatie cascade");
                statement.executeUpdate("truncate autaut.bijhouderfiatuitz cascade");
                statement.executeUpdate("truncate autaut.bijhautorisatie cascade");

            }
        }
    }
}
