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
 * Delete alle personen kanaal.
 */
public final class DeleteInitVullingKanaal extends LazyLoadingKanaal {

    /**
     * Constructor.
     */
    public DeleteInitVullingKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-db-gbav.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {
        @Inject
        @Named("gbavDataSource")
        private DataSource gbavDataSource;

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return "deleteInitVulling";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            // Tijdelijk
            try (final Connection connection = gbavDataSource.getConnection()) {
                connection.setAutoCommit(true);

                try (final Statement statement = connection.createStatement()) {
                    statement.executeUpdate("truncate initvul.fingerprint cascade");
                    statement.executeUpdate("truncate initvul.verschil_analyse cascade");
                    statement.executeUpdate("truncate initvul.initvullingresult cascade");
                    statement.executeUpdate("truncate initvul.initvullingresult_afnind_stapel cascade");
                    statement.executeUpdate("truncate initvul.initvullingresult_afnind_regel cascade");
                    statement.executeUpdate("truncate initvul.initvullingresult_afnind cascade");
                    statement.executeUpdate("truncate initvul.initvullingresult_aut cascade");
                    statement.executeUpdate("truncate initvul.initvullingresult_protocollering_activiteit cascade");
                    statement.executeUpdate("truncate initvul.initvullingresult_protocollering_brp_dienst cascade");
                    statement.executeUpdate("truncate initvul.initvullingresult_protocollering_brp_pers cascade");
                    statement.executeUpdate("truncate initvul.initvullingresult_protocollering_brp_toeglevaut cascade");
                    statement.executeUpdate("truncate initvul.initvullingresult_protocollering_dienst cascade");
                    statement.executeUpdate("truncate initvul.initvullingresult_protocollering_toeglevaut cascade");
                    statement.executeUpdate("truncate initvul.initvullingresult_protocollering cascade");
                }

            } catch (final SQLException e) {
                throw new KanaalException("Probleem bij verwijderen init vulling result tabellen.", e);
            }
        }

    }
}
