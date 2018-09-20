/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.isc;

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
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;

/**
 * Delete processen.
 */
public class DeleteProcessenKanaal extends LazyLoadingKanaal {

    /**
     * Constructor.
     */
    public DeleteProcessenKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-db-isc.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {

        private static final String[] VERWIJDER_PROCESSEN_STATEMENTS = {"truncate jbpm_taskinstance cascade",
                                                                        "truncate jbpm_variableinstance cascade",
                                                                        "truncate jbpm_processinstance cascade",
                                                                        "truncate jbpm_log cascade",
                                                                        "truncate jbpm_job cascade",
                                                                        "truncate jbpm_moduleinstance cascade",
                                                                        "truncate jbpm_tokenvariablemap cascade",
                                                                        "truncate jbpm_comment cascade",
                                                                        "truncate jbpm_swimlaneinstance cascade",
                                                                        "truncate jbpm_runtimeaction cascade",
                                                                        "truncate jbpm_taskactorpool cascade",
                                                                        "truncate mig_virtueel_proces cascade",
                                                                        "truncate mig_telling_bericht cascade",
                                                                        "truncate mig_telling_proces cascade", };

        @Inject
        @Named("iscDataSource")
        private DataSource iscDataSource;

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return "deleteProcessen";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            // Tijdelijk
            try (final Connection connection = iscDataSource.getConnection()) {
                connection.setAutoCommit(true);

                verwijderProcessen(connection);
            } catch (final
                SQLException
                | HerhaalException e)
            {
                throw new KanaalException("Probleem bij verwijderen processen.", e);
            }
        }

        private void verwijderProcessen(final Connection connection) throws HerhaalException {
            Herhaal.herhaalOperatie(new Runnable() {
                @Override
                public void run() {
                    try (Statement statement = connection.createStatement()) {
                        for (final String sql : VERWIJDER_PROCESSEN_STATEMENTS) {
                            statement.executeUpdate(sql);
                        }
                    } catch (final SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        }
    }
}
