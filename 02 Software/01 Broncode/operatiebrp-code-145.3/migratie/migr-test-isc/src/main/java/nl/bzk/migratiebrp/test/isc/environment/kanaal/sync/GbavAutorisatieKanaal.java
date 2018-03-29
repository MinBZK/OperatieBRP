/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.test.common.autorisatie.AutorisatieReader;
import nl.bzk.migratiebrp.test.common.autorisatie.CsvAutorisatieReader;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.util.common.JdbcConstants;

/**
 * Insert een autorisatie in de GBA-v tabellen.
 */
public class GbavAutorisatieKanaal extends LazyLoadingKanaal {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public GbavAutorisatieKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-db-gbav.xml", "classpath:infra-gbav.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {

        private static final String SELECT_AUTORISATIE_ID_SQL = "select nextval('lo3_autorisatie_id_sequence')";

        private static final String INSERT_AUTORISATIE_SQL =
                "insert into lo3_autorisatie(autorisatie_id, afnemer_code, afnemer_naam, geheimhouding_ind, verstrekkings_beperking, "
                        + "conditionele_verstrekking, spontaan_medium, selectie_soort, "
                        + "bericht_aand, eerste_selectie_datum, selectie_periode, selectie_medium, "
                        + "pl_plaatsings_bevoegdheid, adres_vraag_bevoegdheid, "
                        + "ad_hoc_medium, adres_medium, tabel_regel_start_datum, tabel_regel_eind_datum, "
                        + "sleutel_rubrieken, spontaan_rubrieken, "
                        + "selectie_rubrieken, ad_hoc_rubrieken, adres_rubrieken, afnemers_verstrekkingen) values "
                        + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        private static final int PARAM_AUTORISATIE_ID = 1;
        private static final int PARAM_AFNEMER_CODE = 2;
        private static final int PARAM_AFNEMER_NAAM = 3;
        private static final int PARAM_GEHEIDHOUDING_IND = 4;
        private static final int PARAM_VERSTREKKINGS_BEPERKING = 5;
        private static final int PARAM_CONDITIONELE_VERSTREKKING = 6;
        private static final int PARAM_SPONTAAN_MEDIUM = 7;
        private static final int PARAM_SELECTIE_SOORT = 8;
        private static final int PARAM_BERICHT_AAND = 9;
        private static final int PARAM_EERSTE_SELECTIE_DATUM = 10;
        private static final int PARAM_SELECTIE_PERIODE = 11;
        private static final int PARAM_SELECTIE_MEDIUM = 12;
        private static final int PARAM_PL_PLAATSINGS_BEVOEGDHEID = 13;
        private static final int PARAM_ADRES_VRAAG_BEVOEGDHEID = 14;
        private static final int PARAM_AD_HOC_MEDIUM = 15;
        private static final int PARAM_ADRES_MEDIUM = 16;
        private static final int PARAM_TABEL_REGEL_START_DATUM = 17;
        private static final int PARAM_TABEL_REGEL_EIND_DATUM = 18;
        private static final int PARAM_SLEUTEL_RUBRIEKEN = 19;
        private static final int PARAM_SPONTAAN_RUBRIEKEN = 20;
        private static final int PARAM_SELECTIE_RUBRIEKEN = 21;
        private static final int PARAM_AD_HOC_RUBRIEKEN = 22;
        private static final int PARAM_ADRES_RUBRIEKEN = 23;
        private static final int PARAM_AFNEMERS_VERSTREKKINGEN = 24;

        private static final int MAX_LENGTE_AFNEMERS_NAAM = 40;

        private static final String INSERT_VOORWAARDE_REGEL_SQL =
                "insert into lo3_voorwaarde_regel_aut(autorisatie_id, voorwaarde_type, voorwaarde_regel) values (?, ?, ?)";
        private static final int PARAM_VOORWAARDE_TYPE = 2;
        private static final int PARAM_VOORWAARDE_REGEL = 3;

        private static final String VOORWAARDE_TYPE_SPONTAAN = "4";
        private static final String VOORWAARDE_TYPE_SELECTIE = "5";
        private static final String VOORWAARDE_TYPE_ADHOC = "6";
        private static final String VOORWAARDE_TYPE_ADRES = "7";

        private static final int DISPLAY_PER = 250;

        private final AutorisatieReader autorisatieReader = new CsvAutorisatieReader();

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
            return "gbavAutorisatie";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            final List<Lo3Autorisatie> lo3Autorisaties;
            try {
                lo3Autorisaties = autorisatieReader.read(new ByteArrayInputStream(bericht.getInhoud().getBytes(Charset.defaultCharset())));
            } catch (final IOException e) {
                throw new KanaalException("Kan autorisatie niet inlezen", e);
            }
            LOG.info("Verwerk {} autorisaties", lo3Autorisaties.size());

            try (Connection connection = gbavDataSource.getConnection()) {
                connection.setAutoCommit(false);

                int teller = 0;
                for (final Lo3Autorisatie lo3Autorisatie : lo3Autorisaties) {
                    teller++;

                    if (teller % DISPLAY_PER == 0) {
                        LOG.info("Verwerken autorisatie  " + teller + " ...");
                    }
                    final String afnemerCode = lo3Autorisatie.getAfnemersindicatie();
                    for (final Lo3Categorie<Lo3AutorisatieInhoud> tabelRegel : lo3Autorisatie.getAutorisatieStapel()) {
                        final long autorisatieId = selectAutorisatieId(connection);
                        insertAutorisatie(connection, autorisatieId, afnemerCode, tabelRegel);
                        insertVoorwaardeRegel(connection, autorisatieId, VOORWAARDE_TYPE_ADHOC, tabelRegel.getInhoud().getVoorwaarderegelAdHoc());
                        insertVoorwaardeRegel(
                                connection,
                                autorisatieId,
                                VOORWAARDE_TYPE_ADRES,
                                tabelRegel.getInhoud().getVoorwaarderegelAdresgeorienteerd());
                        insertVoorwaardeRegel(connection, autorisatieId, VOORWAARDE_TYPE_SELECTIE, tabelRegel.getInhoud().getVoorwaarderegelSelectie());
                        insertVoorwaardeRegel(connection, autorisatieId, VOORWAARDE_TYPE_SPONTAAN, tabelRegel.getInhoud().getVoorwaarderegelSpontaan());
                    }
                    connection.commit();
                }

            } catch (final SQLException e) {
                throw new KanaalException("Kan autorisatie niet opslaan", e);
            }
        }

        private void insertAutorisatie(
                final Connection connection,
                final long autorisatieId,
                final String afnemerCode,
                final Lo3Categorie<Lo3AutorisatieInhoud> tabelRegel) throws SQLException {
            final Lo3AutorisatieInhoud inhoud = tabelRegel.getInhoud();
            final Lo3Historie historie = tabelRegel.getHistorie();

            try (final PreparedStatement statement = connection.prepareStatement(INSERT_AUTORISATIE_SQL)) {
                statement.setLong(PARAM_AUTORISATIE_ID, autorisatieId);
                setInt(statement, PARAM_AFNEMER_CODE, Integer.valueOf(afnemerCode));

                final String afnemerNaam =
                        inhoud.getAfnemernaam().length() > MAX_LENGTE_AFNEMERS_NAAM ? inhoud.getAfnemernaam().substring(0, MAX_LENGTE_AFNEMERS_NAAM)
                                : inhoud.getAfnemernaam();

                setString(statement, PARAM_AFNEMER_NAAM, afnemerNaam);
                setInt(statement, PARAM_GEHEIDHOUDING_IND, inhoud.getIndicatieGeheimhouding());
                setInt(statement, PARAM_VERSTREKKINGS_BEPERKING, inhoud.getVerstrekkingsbeperking());
                setInt(statement, PARAM_CONDITIONELE_VERSTREKKING, inhoud.getConditioneleVerstrekking());
                setString(statement, PARAM_SPONTAAN_MEDIUM, inhoud.getMediumSpontaan());
                setInt(statement, PARAM_SELECTIE_SOORT, inhoud.getSelectiesoort());
                setInt(statement, PARAM_BERICHT_AAND, inhoud.getBerichtaanduiding());
                setInt(
                        statement,
                        PARAM_EERSTE_SELECTIE_DATUM,
                        inhoud.getEersteSelectiedatum() == null ? null : inhoud.getEersteSelectiedatum().getIntegerWaarde());
                setInt(statement, PARAM_SELECTIE_PERIODE, inhoud.getSelectieperiode());
                setString(statement, PARAM_SELECTIE_MEDIUM, inhoud.getMediumSelectie());
                setInt(statement, PARAM_PL_PLAATSINGS_BEVOEGDHEID, inhoud.getPlaatsingsbevoegdheidPersoonslijst());
                setInt(statement, PARAM_ADRES_VRAAG_BEVOEGDHEID, inhoud.getAdresvraagBevoegdheid());
                setString(statement, PARAM_AD_HOC_MEDIUM, inhoud.getMediumAdHoc());
                setString(statement, PARAM_ADRES_MEDIUM, inhoud.getMediumAdresgeorienteerd());
                setInt(
                        statement,
                        PARAM_TABEL_REGEL_START_DATUM,
                        historie.getIngangsdatumGeldigheid() == null ? null : historie.getIngangsdatumGeldigheid().getIntegerWaarde());
                setInt(statement, PARAM_TABEL_REGEL_EIND_DATUM, inhoud.getDatumEinde() == null ? null : inhoud.getDatumEinde().getIntegerWaarde());
                setString(statement, PARAM_SLEUTEL_RUBRIEKEN, vertaalRubrieken(inhoud.getSleutelrubriek()));
                setString(statement, PARAM_SPONTAAN_RUBRIEKEN, vertaalRubrieken(inhoud.getRubrieknummerSpontaan()));
                setString(statement, PARAM_SELECTIE_RUBRIEKEN, vertaalRubrieken(inhoud.getRubrieknummerSelectie()));
                setString(statement, PARAM_AD_HOC_RUBRIEKEN, vertaalRubrieken(inhoud.getRubrieknummerAdHoc()));
                setString(statement, PARAM_ADRES_RUBRIEKEN, vertaalRubrieken(inhoud.getRubrieknummerAdresgeorienteerd()));
                setString(statement, PARAM_AFNEMERS_VERSTREKKINGEN, inhoud.getAfnemersverstrekking());
                statement.execute();
            }
        }

        private String vertaalRubrieken(final String rubrieken) {
            if (rubrieken == null || "".equals(rubrieken)) {
                return rubrieken;
            }
            return rubrieken.replaceAll("\\.", "").replaceAll("#", " ");
        }

        private void setString(final PreparedStatement statement, final int param, final String value) throws SQLException {
            if (value == null || "".equals(value)) {
                statement.setNull(param, Types.VARCHAR);
            } else {
                statement.setString(param, value);
            }

        }

        private void setInt(final PreparedStatement statement, final int param, final Integer value) throws SQLException {
            if (value == null) {
                statement.setNull(param, Types.BIGINT);
            } else {
                statement.setInt(param, value);
            }
        }

        private long selectAutorisatieId(final Connection connection) throws SQLException {
            try (final PreparedStatement idStatement = connection.prepareStatement(SELECT_AUTORISATIE_ID_SQL);
                 final ResultSet idResult = idStatement.executeQuery()) {
                idResult.next();

                return idResult.getLong(JdbcConstants.COLUMN_1);

            }
        }

        private void insertVoorwaardeRegel(
                final Connection connection,
                final long autorisatieId,
                final String voorwaardeType,
                final String voorwaarderegel) throws SQLException {
            if (voorwaarderegel == null || "".equals(voorwaarderegel)) {
                return;
            }

            try (final PreparedStatement statement = connection.prepareStatement(INSERT_VOORWAARDE_REGEL_SQL)) {
                statement.setLong(PARAM_AUTORISATIE_ID, autorisatieId);
                statement.setString(PARAM_VOORWAARDE_TYPE, voorwaardeType);
                statement.setString(PARAM_VOORWAARDE_REGEL, voorwaarderegel);
                statement.execute();
            }
        }
    }
}
