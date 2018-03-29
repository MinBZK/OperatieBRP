/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
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
import nl.bzk.migratiebrp.util.common.operatie.ExceptionWrapper;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import nl.bzk.migratiebrp.util.common.operatie.StopHerhalingExceptionWrapper;

/**
 * Delete alle personen kanaal.
 */
public final class DeletePersonenKanaal extends LazyLoadingKanaal {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public DeletePersonenKanaal() {
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

        private static final List<String> HISTORIE_TABELLEN =
                Arrays.asList(
                        "kern.his_onderzoek",
                        "kern.his_ouderouderlijkgezag",
                        "kern.his_ouderouderschap",
                        "kern.his_persadres",
                        "kern.his_persafgeleidadministrati",
                        "kern.his_persbijhouding",
                        "kern.his_persdeelneuverkiezingen",
                        "kern.his_persgeboorte",
                        "kern.his_persgeslachtsaand",
                        "kern.his_persgeslnaamcomp",
                        "kern.his_persids",
                        "kern.his_persindicatie",
                        "kern.his_persinschr",
                        "kern.his_persmigratie",
                        "kern.his_persnaamgebruik",
                        "kern.his_persnation",
                        "kern.his_persnrverwijzing",
                        "kern.his_persoverlijden",
                        "kern.his_perspk",
                        "kern.his_persreisdoc",
                        "kern.his_perssamengesteldenaam",
                        "kern.his_persuitslkiesr",
                        "kern.his_persverblijfsr",
                        "kern.his_persverificatie",
                        "kern.his_persverstrbeperking",
                        "kern.his_persvoornaam");

        private static final List<String> PERSOON_TABELLEN =
                Arrays.asList(
                        "kern.betr",
                        "kern.persadres",
                        "kern.persgeslnaamcomp",
                        "kern.persindicatie",
                        "kern.persnation",
                        "kern.persreisdoc",
                        "kern.persverificatie",
                        "kern.persverstrbeperking",
                        "kern.persvoornaam",
                        "kern.relatie",
                        "kern.perscache",
                        "kern.pers");

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
            return "deletePersonen";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            try {
                bericht.getTestBericht().maakHerhaling().herhaal(new Runnable() {
                    @Override
                    public void run() {
                        try (final Connection connection = brpDataSource.getConnection()) {
                            performCleanUp(connection);
                        } catch (final SQLException e) {
                            LOG.info("Truncate van tabellen niet gelukt", e);
                            throw new StopHerhalingExceptionWrapper(e);
                        }
                    }

                    private void performCleanUp(final Connection connection) throws SQLException {
                        connection.setAutoCommit(false);
                        performLockEnTruncateTransactie(connection);

                    }
                });
            } catch (final HerhaalException e) {
                throw new KanaalException("Opschonen personen niet gelukt", e);
            }
        }

        private static void performLockEnTruncateTransactie(final Connection connection) throws SQLException {
            try (final Statement transactionStatement = connection.createStatement()) {
                transactionStatement.execute("BEGIN");
                lockTabellen(connection, HISTORIE_TABELLEN);
                lockTabellen(connection, PERSOON_TABELLEN);
                verwijderAlleTabellen(connection);
                transactionStatement.execute("COMMIT");
            } catch (final SQLException e) {
                LOG.info("Locken van tabellen niet gelukt", e);
                throw new ExceptionWrapper(e);
            }
        }

        private static void lockTabellen(final Connection connection, final List<String> tabellen) throws SQLException {
            try (final Statement statement = connection.createStatement()) {
                for (final String tabel : tabellen) {
                    statement.execute("lock table " + tabel + " NOWAIT");
                }
            }
        }

        private static void verwijderAlleTabellen(final Connection connection) throws SQLException {
            verwijderBlokkering(connection);
            verwijderAfnemersindicaties(connection);
            verwijderIst(connection);
            verwijderTabellen(connection, HISTORIE_TABELLEN);
            verwijderTabellen(connection, PERSOON_TABELLEN);
            aanpassenLeveringsStatussen(connection);
        }

        private static void verwijderBlokkering(final Connection connection) throws SQLException {
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate("truncate migblok.blokkering cascade");
            }
        }

        private static void verwijderAfnemersindicaties(final Connection connection) throws SQLException {
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate("truncate autaut.his_persafnemerindicatie cascade");
                statement.executeUpdate("truncate autaut.persafnemerindicatie cascade");
            }
        }

        private static void verwijderIst(final Connection connection) throws SQLException {
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate("truncate ist.stapelrelatie cascade");
                statement.executeUpdate("truncate ist.stapelvoorkomen cascade");
                statement.executeUpdate("truncate ist.stapel cascade");
            }
        }

        private static void verwijderTabellen(final Connection connection, final List<String> tabellen) throws SQLException {
            try (final Statement statement = connection.createStatement()) {
                for (final String tabel : tabellen) {
                    statement.executeUpdate("truncate " + tabel + " cascade");
                }
            }
        }

        private static void aanpassenLeveringsStatussen(Connection connection) throws SQLException {
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate("update kern.admhnd set statuslev = 4 where statuslev <> 4");
            }
        }
    }
}
