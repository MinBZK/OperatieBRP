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
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.util.common.JdbcConstants;

/**
 * Gbav database access.
 */
public final class GbavRepository {

    /**
     * Activiteit type voor LG01 bericht.
     */
    public static final int ACTIVITEIT_TYPE_LG01_BERICHT = 100;

    /**
     * Activiteit type voor LG01 cyclus.
     */
    public static final int ACTIVITEIT_TYPE_LG01_CYCLUS = 102;

    /**
     * Activiteit subtype voor LG01 cyclus.
     */
    public static final int ACTIVITEIT_SUBTYPE_LG01_CYCLUS = 1305;
    /**
     * Activiteit subtype voor LG01 bericht.
     */
    public static final int ACTIVITEIT_SUBTYPE_LG01_BERICHT = 1111;

    /**
     * Toestand verwerkt.
     */
    public static final int TOESTAND_VERWERKT = 8000;


    @Inject
    @Named("gbavDataSource")
    private DataSource gbavDataSource;

    /**
     * Activiteit toevoegen.
     * @param moederId moeder id
     * @param activiteitType activiteit type
     * @param subActiviteitType activiteit subtype
     * @param toestand toestand
     * @param plId persoonslijst ID
     * @param communicatiePartner communicatie partner
     * @param datum datum
     * @return activiteit id
     * @throws SQLException bij fouten
     */
    public Integer insertActiviteit(
            final Integer moederId,
            final int activiteitType,
            final int subActiviteitType,
            final int toestand,
            final int plId,
            final String communicatiePartner,
            final String datum) throws SQLException {
        final String idSql = "select nextval('activiteit_id_sequence')";
        final String insertActiviteitSql =
                "insert into activiteit(activiteit_id, moeder_id, activiteit_type, activiteit_subtype, toestand, pl_id, "
                        + "communicatie_partner, creatie_dt, start_dt, uiterlijke_actie_dt, laatste_actie_dt) "
                        + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = gbavDataSource.getConnection();
             PreparedStatement idStatement = connection.prepareStatement(idSql);
             ResultSet idResult = idStatement.executeQuery();
             PreparedStatement activiteitStatement = connection.prepareStatement(insertActiviteitSql)) {
            idResult.next();

            final Integer id = idResult.getInt(JdbcConstants.COLUMN_1);

            activiteitStatement.setInt(JdbcConstants.COLUMN_1, id);
            if (moederId == null) {
                activiteitStatement.setNull(JdbcConstants.COLUMN_2, Types.INTEGER);
            } else {
                activiteitStatement.setInt(JdbcConstants.COLUMN_2, moederId);
            }
            activiteitStatement.setInt(JdbcConstants.COLUMN_3, activiteitType);
            activiteitStatement.setInt(JdbcConstants.COLUMN_4, subActiviteitType);
            activiteitStatement.setInt(JdbcConstants.COLUMN_5, toestand);
            activiteitStatement.setInt(JdbcConstants.COLUMN_6, plId);
            if (communicatiePartner == null) {
                activiteitStatement.setNull(JdbcConstants.COLUMN_7, Types.VARCHAR);
            } else {
                activiteitStatement.setString(JdbcConstants.COLUMN_7, communicatiePartner);
            }

            final Timestamp timestamp;
            if (datum == null) {
                timestamp = Timestamp.from(java.time.Instant.now());
            } else {
                timestamp = Timestamp.valueOf(datum);
            }
            activiteitStatement.setTimestamp(JdbcConstants.COLUMN_8, timestamp);
            activiteitStatement.setTimestamp(JdbcConstants.COLUMN_9, timestamp);
            activiteitStatement.setTimestamp(JdbcConstants.COLUMN_10, timestamp);
            activiteitStatement.setTimestamp(JdbcConstants.COLUMN_11, timestamp);

            activiteitStatement.executeUpdate();

            return id;
        }

    }

    /**
     * Zoek activiteit.
     * @param moederId moeder id
     * @param activiteitType activiteit type
     * @param subActiviteitType activiteit subtype
     * @param toestand toestand
     * @return activiteit id
     * @throws SQLException bij fouten
     */
    public Integer findActiviteit(final Integer moederId, final int activiteitType, final int subActiviteitType, final int toestand) throws SQLException {
        final String sql = "SELECT activiteit_id FROM activiteit WHERE moeder_id = ? AND activiteit_type = ? AND activiteit_subtype = ? AND toestand = ?";
        try (Connection connection = gbavDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(JdbcConstants.COLUMN_1, moederId);
            statement.setInt(JdbcConstants.COLUMN_2, activiteitType);
            statement.setInt(JdbcConstants.COLUMN_3, subActiviteitType);
            statement.setInt(JdbcConstants.COLUMN_4, toestand);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt(JdbcConstants.COLUMN_1);
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * Bericht toevoegen.
     * @param inhoud inhoud
     * @param originatorOrRecipient originator of recipient
     * @param activiteitId bericht activiteit id
     * @param tijdstipVerzendingOntvangst tijdstip verzending of ontvangst
     * @throws SQLException bij fouten
     */
    public void insertBericht(final String inhoud, final String originatorOrRecipient, final Integer activiteitId, final Date tijdstipVerzendingOntvangst)
            throws SQLException {
        final int startBerichtsoortNummerPos = 8;
        final int eindBerichtsoortNummerPos = 12;

        final Timestamp timestampTijdstipVerzendingOntvangst =
                tijdstipVerzendingOntvangst == null ? new Timestamp(System.currentTimeMillis()) : new Timestamp(tijdstipVerzendingOntvangst.getTime());

        final String sql =
                "insert into lo3_bericht(lo3_bericht_id, spg_mailbox_instantie, aanduiding_in_uit, medium, "
                        + "bericht_data, kop_berichtsoort_nummer, originator_or_recipient, "
                        + "bericht_activiteit_id, creatie_dt, tijdstip_verzending_ontvangst, dispatch_sequence_number) "
                        + "values(nextval('lo3_bericht_id_sequence'), 1, 'I', 'N', ?, ?, ?, ?, ?, ?, currval('lo3_bericht_id_sequence'))";
        try (Connection connection = gbavDataSource.getConnection();
             PreparedStatement lo3BerichtStatement = connection.prepareStatement(sql)) {

            lo3BerichtStatement.setString(JdbcConstants.COLUMN_1, inhoud);
            lo3BerichtStatement.setString(JdbcConstants.COLUMN_2, inhoud.substring(startBerichtsoortNummerPos, eindBerichtsoortNummerPos));
            lo3BerichtStatement.setString(JdbcConstants.COLUMN_3, originatorOrRecipient);
            lo3BerichtStatement.setInt(JdbcConstants.COLUMN_4, activiteitId);
            lo3BerichtStatement.setTimestamp(JdbcConstants.COLUMN_5, timestampTijdstipVerzendingOntvangst);
            lo3BerichtStatement.setTimestamp(JdbcConstants.COLUMN_6, timestampTijdstipVerzendingOntvangst);
            lo3BerichtStatement.execute();
        }
    }

    /**
     * Toevoegen LO3 persoonslijst.
     * @param bijhoudingOpschortDatum opschort datum
     * @param bijhoudingOpschortReden opschort reden
     * @param berichtActiviteitId bericht activiteit id
     * @param aNummer a-nummer
     * @return pl id
     * @throws SQLException bij fouten
     */
    public Integer insertLo3Pl(
            final Integer bijhoudingOpschortDatum,
            final String bijhoudingOpschortReden,
            final Integer berichtActiviteitId,
            final Long aNummer,
            final Long bsn) throws SQLException {
        try (Connection connection = gbavDataSource.getConnection();
             PreparedStatement idStatement = connection.prepareStatement("select nextval('lo3_pl_id_sequence')");
             PreparedStatement lo3PlStatement =
                     connection.prepareStatement(
                             "insert into lo3_pl(pl_id, mutatie_activiteit_id, bijhouding_opschort_datum, "
                                     + "bijhouding_opschort_reden, creatie_dt, mutatie_dt) values(?, ?, ?, ?, now(), now())");
             PreparedStatement lo3PlPersoonStatement =
                     connection.prepareStatement(
                             "insert into lo3_pl_persoon(pl_id, persoon_type, stapel_nr, volg_nr, a_nr, burger_service_nr) " + "values(?, 'P', 0, 0, ?, ?)")) {
            try (ResultSet idResult = idStatement.executeQuery()) {
                idResult.next();

                final Integer id = idResult.getInt(JdbcConstants.COLUMN_1);

                lo3PlStatement.setInt(JdbcConstants.COLUMN_1, id);
                if (berichtActiviteitId == null) {
                    lo3PlStatement.setInt(JdbcConstants.COLUMN_2, Types.INTEGER);
                } else {
                    lo3PlStatement.setInt(JdbcConstants.COLUMN_2, berichtActiviteitId);
                }
                if (bijhoudingOpschortDatum == null) {
                    lo3PlStatement.setNull(JdbcConstants.COLUMN_3, Types.INTEGER);
                } else {
                    lo3PlStatement.setInt(JdbcConstants.COLUMN_3, bijhoudingOpschortDatum);
                }
                lo3PlStatement.setString(JdbcConstants.COLUMN_4, bijhoudingOpschortReden);
                lo3PlStatement.execute();

                lo3PlPersoonStatement.setInt(JdbcConstants.COLUMN_1, id);
                lo3PlPersoonStatement.setLong(JdbcConstants.COLUMN_2, aNummer);
                lo3PlPersoonStatement.setLong(JdbcConstants.COLUMN_3, bsn);
                lo3PlPersoonStatement.execute();

                return id;
            }
        }
    }

    /**
     * Bijwerken LO3 persoonslijst.
     * @param plId pl id
     * @param bijhoudingOpschortDatum opschort datum
     * @param bijhoudingOpschortReden opschort reden
     * @param berichtActiviteitId bericht activiteit id
     * @param aNummer a-nummer
     * @throws SQLException bij fouten
     */
    public void updateLo3Pl(
            final Integer plId,
            final Integer bijhoudingOpschortDatum,
            final String bijhoudingOpschortReden,
            final Integer berichtActiviteitId,
            final Long aNummer,
            final Long bsn) throws SQLException {
        final String sql =
                "update lo3_pl set mutatie_activiteit_id = ?, bijhouding_opschort_datum = ?, bijhouding_opschort_reden = ?, mutatie_dt = now() where pl_id = ?";
        try (Connection connection = gbavDataSource.getConnection();
             PreparedStatement updatelo3PlStatement = connection.prepareStatement(sql);
             PreparedStatement updateLo3PlPersoonStatement = connection
                     .prepareStatement("update lo3_pl_persoon set a_nr = ?, burger_service_nr = ? where pl_id = ?")) {
            updatelo3PlStatement.setInt(JdbcConstants.COLUMN_1, berichtActiviteitId);
            if (bijhoudingOpschortDatum == null) {
                updatelo3PlStatement.setNull(JdbcConstants.COLUMN_2, Types.INTEGER);
            } else {
                updatelo3PlStatement.setInt(JdbcConstants.COLUMN_2, bijhoudingOpschortDatum);
            }
            updatelo3PlStatement.setString(JdbcConstants.COLUMN_3, bijhoudingOpschortReden);
            updatelo3PlStatement.setInt(JdbcConstants.COLUMN_4, plId);
            updatelo3PlStatement.execute();

            updateLo3PlPersoonStatement.setLong(JdbcConstants.COLUMN_1, aNummer);
            updateLo3PlPersoonStatement.setLong(JdbcConstants.COLUMN_2, bsn);
            updateLo3PlPersoonStatement.setInt(JdbcConstants.COLUMN_3, plId);
            updateLo3PlPersoonStatement.execute();
        }
    }
}
