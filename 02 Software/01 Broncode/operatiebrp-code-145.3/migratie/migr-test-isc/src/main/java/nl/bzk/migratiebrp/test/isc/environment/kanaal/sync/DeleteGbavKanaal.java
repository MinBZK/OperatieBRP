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
 * Delete GBA-v tabellen voor init vulling.
 */
public class DeleteGbavKanaal extends LazyLoadingKanaal {

    /**
     * Constructor.
     */
    public DeleteGbavKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-db-gbav.xml", "classpath:infra-gbav.xml"));
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
            return "deleteGbav";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            // Tijdelijk
            try (final Connection connection = gbavDataSource.getConnection()) {
                connection.setAutoCommit(true);

                try (final Statement statement = connection.createStatement()) {
                    statement.executeUpdate("truncate activiteit cascade");
                    statement.executeUpdate("truncate afnemer cascade");
                    statement.executeUpdate("truncate gebeurtenis cascade");
                    statement.executeUpdate("truncate gebeurtenis_data cascade");
                    truncateLo3Tabellen(statement);
                    // statement.executeUpdate("truncate lookup_codering cascade");
                    // statement.executeUpdate("truncate lookup_codewaarde cascade");
                    statement.executeUpdate("truncate miteller cascade");
                    statement.executeUpdate("truncate miteller_marker cascade");
                    // statement.executeUpdate("truncate proefsyncbericht cascade");
                    // statement.executeUpdate("truncate spg_mailbox cascade");
                    // statement.executeUpdate("truncate spg_schema cascade");
                    // statement.executeUpdate("truncate toestand_overgang cascade");
                }

            } catch (final SQLException e) {
                throw new KanaalException("Probleem bij verwijderen init vulling result tabellen.", e);
            }
        }

        private void truncateLo3Tabellen(final Statement statement) throws SQLException {
            statement.executeUpdate("truncate lo3_adres cascade");
            statement.executeUpdate("truncate lo3_adres_afnemer_ind cascade");
            statement.executeUpdate("truncate lo3_afnemers_verstrekking_aut cascade");
            // statement.executeUpdate("truncate lo3_akte_aand cascade");
            statement.executeUpdate("truncate lo3_autorisatie cascade");
            statement.executeUpdate("truncate lo3_bericht cascade");
            // statement.executeUpdate("truncate lo3_categorie cascade");
            // statement.executeUpdate("truncate lo3_categorie_groep cascade");
            // statement.executeUpdate("truncate lo3_element cascade");
            // statement.executeUpdate("truncate lo3_gba_deelnemer cascade");
            // statement.executeUpdate("truncate lo3_gemeente cascade");
            // statement.executeUpdate("truncate lo3_groep cascade");
            // statement.executeUpdate("truncate lo3_land cascade");
            // statement.executeUpdate("truncate lo3_mailbox cascade");
            // statement.executeUpdate("truncate lo3_nationaliteit cascade");
            statement.executeUpdate("truncate lo3_nl_nat_verkrijg_verlies_reden cascade");
            statement.executeUpdate("truncate lo3_nl_reis_doc_autoriteit cascade");
            statement.executeUpdate("truncate lo3_nl_reis_doc_soort cascade");
            statement.executeUpdate("truncate lo3_pl cascade");
            statement.executeUpdate("truncate lo3_pl_afnemer_ind cascade");
            statement.executeUpdate("truncate lo3_pl_gezagsverhouding cascade");
            statement.executeUpdate("truncate lo3_pl_nationaliteit cascade");
            statement.executeUpdate("truncate lo3_pl_overlijden cascade");
            statement.executeUpdate("truncate lo3_pl_paw_index_2 cascade");
            statement.executeUpdate("truncate lo3_pl_persoon cascade");
            statement.executeUpdate("truncate lo3_pl_reis_doc cascade");
            statement.executeUpdate("truncate lo3_pl_serialized cascade");
            statement.executeUpdate("truncate lo3_pl_verblijfplaats cascade");
            statement.executeUpdate("truncate lo3_pl_verblijfstitel cascade");
            // statement.executeUpdate("truncate lo3_relatie_eind_reden cascade");
            // statement.executeUpdate("truncate lo3_rni_deelnemer cascade");
            statement.executeUpdate("truncate lo3_rubriek_aut cascade");
            // statement.executeUpdate("truncate lo3_titel_predikaat cascade");
            // statement.executeUpdate("truncate lo3_verblijfstitel_aand cascade");
            // statement.executeUpdate("truncate lo3_voorvoegsel cascade");
            statement.executeUpdate("truncate lo3_voorwaarde_regel_aut cascade");
            statement.executeUpdate("truncate lo3_vospg_instructie cascade");
        }

    }
}
