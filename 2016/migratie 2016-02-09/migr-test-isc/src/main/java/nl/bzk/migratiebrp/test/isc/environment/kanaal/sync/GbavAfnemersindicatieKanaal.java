/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Insert afnemersindicaties in de GBA-v tabellen.
 */
public class GbavAfnemersindicatieKanaal extends LazyLoadingKanaal {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public GbavAfnemersindicatieKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-db-gbav.xml", "classpath:infra-gbav.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {

        private static final String ZOEK_PERSOON_SQL = "select pl_id from lo3_pl_persoon where stapel_nr = 0 and volg_nr = 0 and a_nr = ? ";

        private static final String INSERT_AFNEMER_IND_SQL =
                "insert into lo3_pl_afnemer_ind(pl_id, stapel_nr, volg_nr, afnemer_code, geldigheid_start_datum) values (?, ?, ?, ?, ?)";
        private static final int PARAM_PL_ID = 1;
        private static final int PARAM_STAPEL_NR = 2;
        private static final int PARAM_VOLG_NR = 3;
        private static final int PARAM_AFNEMER_CODE = 4;
        private static final int PARAM_GELDIGHEID_START_DATUM = 5;

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
            return "gbavAfnemersindicatie";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            LOG.info("Verwerk bericht: " + bericht.getInhoud());
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht.getInhoud());

            final Lo3Afnemersindicatie lo3Afnemersindicatie = getAfnemersindicaties(syncBericht);

            try (Connection connection = gbavDataSource.getConnection()) {
                final Long plId = zoekPersoon(connection, lo3Afnemersindicatie.getANummer());

                for (int stapel = 0; stapel < lo3Afnemersindicatie.getAfnemersindicatieStapels().size(); stapel++) {
                    final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatieStapel = lo3Afnemersindicatie.getAfnemersindicatieStapels().get(stapel);

                    for (int volgnummer = 0; volgnummer < afnemersindicatieStapel.size(); volgnummer++) {
                        final Lo3Categorie<Lo3AfnemersindicatieInhoud> afnemersindicatieVoorkomen = afnemersindicatieStapel.get(volgnummer);
                        final Integer afnemerCode = afnemersindicatieVoorkomen.getInhoud().getAfnemersindicatie();
                        final Lo3Datum geldigheidStartDatum = afnemersindicatieVoorkomen.getHistorie().getIngangsdatumGeldigheid();

                        insertAfnemersindicatie(connection, plId, stapel, volgnummer, afnemerCode, geldigheidStartDatum);
                    }
                }
            } catch (final SQLException e) {
                throw new KanaalException("SQL fout", e);
            }
        }

        private Long zoekPersoon(final Connection connection, final Long aNummer) throws SQLException, KanaalException {
            try (final PreparedStatement statement = connection.prepareStatement(ZOEK_PERSOON_SQL)) {
                statement.setLong(JdbcConstants.COLUMN_1, aNummer);

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new KanaalException("Geen persoon gevonden met a-nummer " + aNummer);
                    }

                    return result.getLong(JdbcConstants.COLUMN_1);
                }
            }
        }

        private void insertAfnemersindicatie(
            final Connection connection,
            final Long plId,
            final int stapel,
            final int volgnummer,
            final Integer afnemerCode,
            final Lo3Datum geldigheidStartDatum) throws SQLException
        {
            try (final PreparedStatement statement = connection.prepareStatement(INSERT_AFNEMER_IND_SQL)) {
                statement.setLong(PARAM_PL_ID, plId);
                statement.setInt(PARAM_STAPEL_NR, stapel);
                statement.setInt(PARAM_VOLG_NR, volgnummer);
                if (afnemerCode == null) {
                    statement.setNull(PARAM_AFNEMER_CODE, Types.NULL);
                } else {
                    statement.setInt(PARAM_AFNEMER_CODE, afnemerCode);

                }
                statement.setInt(PARAM_GELDIGHEID_START_DATUM, geldigheidStartDatum.getIntegerWaarde());
                statement.execute();
            }
        }

        private Lo3Afnemersindicatie getAfnemersindicaties(final SyncBericht syncBericht) throws KanaalException {
            if (syncBericht instanceof AfnemersindicatiesBericht) {
                return ((AfnemersindicatiesBericht) syncBericht).getAfnemersindicaties();
            } else {
                throw new KanaalException("Berichttype '" + syncBericht.getBerichtType() + "' niet ondersteund voor kanaal " + getKanaal() + ".");
            }
        }

    }
}
