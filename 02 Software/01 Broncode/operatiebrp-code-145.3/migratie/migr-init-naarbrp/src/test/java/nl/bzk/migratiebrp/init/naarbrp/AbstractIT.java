/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
import nl.bzk.migratiebrp.test.common.autorisatie.AutorisatieReader;
import nl.bzk.migratiebrp.test.common.autorisatie.CsvAutorisatieReader;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AbstractIT {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext hornetqContext;
    private static GenericXmlApplicationContext testContext;

    private static final Properties portProperties = new Properties();

    @Autowired
    @Named("gbavDataSource")
    protected DataSource gbavDataSource;

    private GbavPersoonRepository gbavPersoonRepository;

    private final AutorisatieReader autorisatieReader = new CsvAutorisatieReader();
    private GbavAutorisatieRepository gbavAutorisatieRepository;
    private GbavAfnemersindicatieRepository gbavAfnemersindicatieRepository;

    @Autowired
    protected ConnectionFactory connectionFactory;

    @BeforeClass
    public static void startDependencies() throws IOException {
        try (ServerSocket databasePort = new ServerSocket(0)) {
            LOGGER.info("Configuring database to port: " + databasePort.getLocalPort());
            portProperties.setProperty("test.database.port", Integer.toString(databasePort.getLocalPort()));
        }
        try (ServerSocket hornetqPort = new ServerSocket(0)) {
            LOGGER.info("Configuring hornetq to port: " + hornetqPort.getLocalPort());
            portProperties.setProperty("test.hornetq.port", Integer.toString(hornetqPort.getLocalPort()));
        }

        databaseContext = startContext("DATABASE", portProperties, "classpath:test-embedded-database.xml");
        hornetqContext = startContext("HORNETQ", portProperties, "classpath:test-embedded-hornetq.xml");
        testContext = startContext("TEST", portProperties, "classpath:test-context.xml");
    }

    @Before
    public void injectDependencies() {
        testContext.getAutowireCapableBeanFactory().autowireBean(this);
        Assert.assertNotNull("GBAV DataSource niet gekoppeld", gbavDataSource);
        gbavPersoonRepository = new GbavPersoonRepository(gbavDataSource);
        gbavAutorisatieRepository = new GbavAutorisatieRepository(gbavDataSource);
        gbavAfnemersindicatieRepository = new GbavAfnemersindicatieRepository(gbavDataSource);
        Assert.assertNotNull("ConnectionFactory niet gekoppeld", connectionFactory);
    }

    @AfterClass
    public static void stopTestContext() {
        closeContext("TEST", testContext);
        closeContext("HORNETQ", hornetqContext);
        closeContext("DATABASE", databaseContext);
    }

    private static GenericXmlApplicationContext startContext(final String name, final Properties properties, final String... configurations) {
        LOGGER.info("Starten {} ...", name);

        final GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load(configurations);
        context.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", properties));
        context.refresh();

        LOGGER.info("{} gestart", name);

        return context;
    }

    private static void closeContext(final String name, final Closeable context) {
        if (context != null) {
            try {
                LOGGER.info("Stoppen {} context ...", name);
                context.close();
                LOGGER.info("{} context gestopt", name);
            } catch (final Exception e) {
                LOGGER.warn("Probleem bij sluiten {} context", name, e);
            }
        }
    }

    protected Properties getPortProperties() {
        return portProperties;
    }

    // *****************************************************************
    // *****************************************************************
    // GBAV
    // *****************************************************************
    // *****************************************************************

    protected Integer insertGbavPersoon(final Date tijdstip, final String inhoud, final Integer plId) {
        Assert.assertEquals("Lg01", inhoud.substring(8, 12));

        try {
            final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(inhoud.substring(49));
            final Integer bijhoudingOpschortDatum =
                    toInteger(Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_07, 0, 0, Lo3ElementEnum.ELEMENT_6710));
            final String bijhoudingOpschortReden =
                    Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_07, 0, 0, Lo3ElementEnum.ELEMENT_6720);
            final Long aNummer =
                    toLong(Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_01, 0, 0, Lo3ElementEnum.ELEMENT_0110));
            final String originatorOrRecipient =
                    Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_08, 0, 0, Lo3ElementEnum.ELEMENT_0910);

            LOGGER.info(
                    "insertGbavPersoon: anr: {}, originator: {}, opschortdatum: {}, opschortreden: {}",
                    new Object[]{aNummer, originatorOrRecipient, bijhoudingOpschortDatum, bijhoudingOpschortReden,});

            final Integer cyclusActiviteitId =
                    gbavPersoonRepository.insertActiviteit(
                            null,
                            GbavPersoonRepository.ACTIVITEIT_TYPE_LG01_CYCLUS,
                            GbavPersoonRepository.ACTIVITEIT_SUBTYPE_LG01_CYCLUS,
                            GbavPersoonRepository.TOESTAND_VERWERKT);

            final Integer berichtActiviteitId =
                    gbavPersoonRepository.insertActiviteit(
                            cyclusActiviteitId,
                            GbavPersoonRepository.ACTIVITEIT_TYPE_LG01_BERICHT,
                            GbavPersoonRepository.ACTIVITEIT_SUBTYPE_LG01_BERICHT,
                            GbavPersoonRepository.TOESTAND_VERWERKT);

            gbavPersoonRepository.insertBericht(inhoud, originatorOrRecipient, berichtActiviteitId, tijdstip);

            if (plId != null) {
                gbavPersoonRepository.updateLo3Pl(plId, bijhoudingOpschortDatum, bijhoudingOpschortReden, berichtActiviteitId, aNummer, tijdstip);
                return plId;
            } else {
                return gbavPersoonRepository.insertLo3Pl(bijhoudingOpschortDatum, bijhoudingOpschortReden, berichtActiviteitId, aNummer, tijdstip);
            }
        } catch (final
        SQLException
                | BerichtSyntaxException e) {
            throw new IllegalArgumentException("Kan persoonslijst niet toevoegen aan GBA-v tabellen", e);
        }
    }

    protected void insertGbavAbonnement(final String inhoud) {
        LOGGER.info("insertGbavAbonnement");
        final List<Lo3Autorisatie> lo3Autorisaties;
        try {
            lo3Autorisaties = autorisatieReader.read(new ByteArrayInputStream(inhoud.getBytes(Charset.defaultCharset())));
            LOGGER.info("Autorisaties: {}", lo3Autorisaties);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Kan autorisatie niet inlezen", e);
        }
        for (final Lo3Autorisatie lo3Autorisatie : lo3Autorisaties) {
            LOGGER.info("Verwerk autorisatie {}", lo3Autorisatie.getAfnemersindicatie());
            gbavAutorisatieRepository.verwerkAutorisatie(lo3Autorisatie);
        }
    }

    /**
     * Inserts een afnemers indicatie.
     * @param aNummer anummer
     * @param afnemersindicatie de afnemer indicatie
     * @param ingangsdatumGeldigheid datum ingang geldigheid
     */
    protected void insertAfnemersIndicatie(final String aNummer, final String afnemersindicatie, final Integer ingangsdatumGeldigheid) {
        final Lo3AfnemersindicatieInhoud inhoud = new Lo3AfnemersindicatieInhoud(afnemersindicatie);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(ingangsdatumGeldigheid), null);
        final Lo3Categorie<Lo3AfnemersindicatieInhoud> categorie = new Lo3Categorie<>(inhoud, null, historie, null);
        final List<Lo3Categorie<Lo3AfnemersindicatieInhoud>> categorieen = Collections.singletonList(categorie);
        final Lo3Stapel<Lo3AfnemersindicatieInhoud> stapel = new Lo3Stapel<>(categorieen);
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> stapels = Collections.singletonList(stapel);
        final Lo3Afnemersindicatie lo3Afnemersindicatie = new Lo3Afnemersindicatie(aNummer, stapels);

        LOGGER.info("insertAfnemersIndicatie");
        gbavAfnemersindicatieRepository.verwerk(lo3Afnemersindicatie);
    }

    protected Integer toInteger(final String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            return Integer.valueOf(value);
        }
    }

    protected Long toLong(final String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            return Long.valueOf(value);
        }
    }

    protected Date toDate(final String value) throws ParseException {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            return format.parse(value);
        }
    }

    public static final class GbavPersoonRepository {

        /**
         * Activiteit type voor LG01 cyclus.
         */
        public static final int ACTIVITEIT_TYPE_LG01_CYCLUS = 102;
        /**
         * Activiteit type voor LG01 bericht.
         */
        public static final int ACTIVITEIT_TYPE_LG01_BERICHT = 100;
        /**
         * Activiteit type voor spontaan levering.
         */
        public static final int ACTIVITEIT_TYPE_SPONTAAN = 107;
        /**
         * Activiteit type voor leveringsbericht.
         */
        public static final int ACTIVITEIT_TYPE_LEVERING_BERICHT = 101;

        /**
         * Activiteit subtype voor LG01 cyclus.
         */
        public static final int ACTIVITEIT_SUBTYPE_LG01_CYCLUS = 1305;
        /**
         * Activiteit subtype voor LG01 bericht.
         */
        public static final int ACTIVITEIT_SUBTYPE_LG01_BERICHT = 1111;
        /**
         * Activiteit subtype voor spontaan levering.
         */
        public static final int ACTIVITEIT_SUBTYPE_SPONTAAN = 1220;
        /**
         * Activiteit subtype voor leveringsbericht.
         */
        public static final int ACTIVITEIT_SUBTYPE_LEVERING_BERICHT = 9999;

        /**
         * Toestand verwerkt.
         */
        public static final int TOESTAND_VERWERKT = 8000;

        private GbavPersoonRepository(final DataSource gbavDataSource) {
            this.gbavDataSource = gbavDataSource;
        }

        private final DataSource gbavDataSource;

        /**
         * Activiteit toevoegen.
         * @param moederId moeder id
         * @param activiteitType activiteit type
         * @param subActiviteitType activiteit subtype
         * @param toestand toestand
         * @return activiteit id
         * @throws SQLException bij fouten
         */
        public Integer insertActiviteit(final Integer moederId, final int activiteitType, final int subActiviteitType, final int toestand)
                throws SQLException {
            final String idSql = "select nextval('activiteit_id_sequence')";
            final String
                    insertActiviteitSql =
                    "insert into activiteit(activiteit_id, moeder_id, activiteit_type, activiteit_subtype, toestand, creatie_dt, start_dt, "
                            + "uiterlijke_actie_dt) "
                            + "values(?, ?, ?, ?, ?, now(), now(), now())";

            try (final Connection connection = gbavDataSource.getConnection();
                 final PreparedStatement idStatement = connection.prepareStatement(idSql);
                 final ResultSet idResult = idStatement.executeQuery();
                 final PreparedStatement activiteitStatement = connection.prepareStatement(insertActiviteitSql)) {
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
        public Integer findActiviteit(final Integer moederId, final int activiteitType, final int subActiviteitType, final int toestand)
                throws SQLException {
            final String sql =
                    "SELECT activiteit_id FROM activiteit WHERE moeder_id = ? AND activiteit_type = ? AND activiteit_subtype = ? AND toestand = ?";
            try (final Connection connection = gbavDataSource.getConnection();
                 final PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(JdbcConstants.COLUMN_1, moederId);
                statement.setInt(JdbcConstants.COLUMN_2, activiteitType);
                statement.setInt(JdbcConstants.COLUMN_3, subActiviteitType);
                statement.setInt(JdbcConstants.COLUMN_4, toestand);

                try (final ResultSet result = statement.executeQuery()) {
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
        public void insertBericht(
                final String inhoud,
                final String originatorOrRecipient,
                final Integer activiteitId,
                final Date tijdstipVerzendingOntvangst) throws SQLException {
            final Timestamp timestampTijdstipVerzendingOntvangst =
                    tijdstipVerzendingOntvangst == null ? new Timestamp(System.currentTimeMillis()) : new Timestamp(tijdstipVerzendingOntvangst.getTime());

            final String sql =
                    "insert into lo3_bericht(lo3_bericht_id, aanduiding_in_uit, medium, bericht_data, kop_berichtsoort_nummer, originator_or_recipient, "
                            + "bericht_activiteit_id, creatie_dt, tijdstip_verzending_ontvangst, dispatch_sequence_number) "
                            + "values(nextval('lo3_bericht_id_sequence'), 'I', 'N', ?, ?, ?, ?, ?, ?, currval('lo3_bericht_id_sequence'))";
            try (final Connection connection = gbavDataSource.getConnection();
                 final PreparedStatement lo3BerichtStatement = connection.prepareStatement(sql)) {

                lo3BerichtStatement.setString(JdbcConstants.COLUMN_1, inhoud);
                lo3BerichtStatement.setString(JdbcConstants.COLUMN_2, inhoud.substring(8, 12));
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
                final Date tijdstipMutatie) throws SQLException {
            final Timestamp timestampMutatie =
                    tijdstipMutatie == null ? new Timestamp(System.currentTimeMillis()) : new Timestamp(tijdstipMutatie.getTime());

            try (Connection connection = gbavDataSource.getConnection();
                 final PreparedStatement idStatement = connection.prepareStatement("select nextval('lo3_pl_id_sequence')");
                 final PreparedStatement lo3PlStatement =
                         connection.prepareStatement(
                                 "insert into lo3_pl(pl_id, mutatie_activiteit_id, bijhouding_opschort_datum, "
                                         + "bijhouding_opschort_reden, creatie_dt, mutatie_dt) values(?, ?, ?, ?, ?, ?)");
                 final PreparedStatement lo3PlPersoonStatement =
                         connection.prepareStatement(
                                 "insert into lo3_pl_persoon(pl_id, persoon_type, stapel_nr, volg_nr, a_nr) " + "values(?, 'P', 0, 0, ?)")) {
                try (final ResultSet idResult = idStatement.executeQuery()) {
                    idResult.next();

                    final Integer id = idResult.getInt(JdbcConstants.COLUMN_1);

                    lo3PlStatement.setInt(JdbcConstants.COLUMN_1, id);
                    lo3PlStatement.setInt(JdbcConstants.COLUMN_2, berichtActiviteitId);
                    if (bijhoudingOpschortDatum == null) {
                        lo3PlStatement.setNull(JdbcConstants.COLUMN_3, Types.INTEGER);
                    } else {
                        lo3PlStatement.setInt(JdbcConstants.COLUMN_3, bijhoudingOpschortDatum);
                    }
                    lo3PlStatement.setString(JdbcConstants.COLUMN_4, bijhoudingOpschortReden);
                    lo3PlStatement.setTimestamp(JdbcConstants.COLUMN_5, timestampMutatie);
                    lo3PlStatement.setTimestamp(JdbcConstants.COLUMN_6, timestampMutatie);
                    lo3PlStatement.execute();

                    lo3PlPersoonStatement.setInt(JdbcConstants.COLUMN_1, id);
                    lo3PlPersoonStatement.setLong(JdbcConstants.COLUMN_2, aNummer);
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
                final Date tijdstipMutatie) throws SQLException {
            final Timestamp timestampMutatie =
                    tijdstipMutatie == null ? new Timestamp(System.currentTimeMillis()) : new Timestamp(tijdstipMutatie.getTime());

            final String sql =
                    "update lo3_pl set mutatie_activiteit_id = ?, bijhouding_opschort_datum = ?, bijhouding_opschort_reden = ?, mutatie_dt = ? where pl_id = ?";
            try (Connection connection = gbavDataSource.getConnection();
                 final PreparedStatement updatelo3PlStatement = connection.prepareStatement(sql);
                 final PreparedStatement updateLo3PlPersoonStatement = connection.prepareStatement("update lo3_pl_persoon set a_nr = ? where pl_id = ?")) {
                updatelo3PlStatement.setInt(JdbcConstants.COLUMN_1, berichtActiviteitId);
                if (bijhoudingOpschortDatum == null) {
                    updatelo3PlStatement.setNull(JdbcConstants.COLUMN_2, Types.INTEGER);
                } else {
                    updatelo3PlStatement.setInt(JdbcConstants.COLUMN_2, bijhoudingOpschortDatum);
                }
                updatelo3PlStatement.setString(JdbcConstants.COLUMN_3, bijhoudingOpschortReden);
                updatelo3PlStatement.setTimestamp(JdbcConstants.COLUMN_4, timestampMutatie);
                updatelo3PlStatement.setInt(JdbcConstants.COLUMN_5, plId);
                updatelo3PlStatement.execute();

                updateLo3PlPersoonStatement.setLong(JdbcConstants.COLUMN_1, aNummer);
                updateLo3PlPersoonStatement.setInt(JdbcConstants.COLUMN_2, plId);
                updateLo3PlPersoonStatement.execute();
            }
        }
    }

    public static final class GbavAutorisatieRepository {

        private static final String SELECT_AUTORISATIE_ID_SQL = "select nextval('lo3_autorisatie_id_sequence')";

        private static final String INSERT_AUTORISATIE_SQL =
                "insert into lo3_autorisatie(autorisatie_id, afnemer_code, afnemer_naam, geheimhouding_ind, verstrekkings_beperking, straat_naam, "
                        + "huis_nr, huis_letter, huis_nr_toevoeging, postcode, gemeente_code, conditionele_verstrekking, spontaan_medium, selectie_soort, "
                        + "bericht_aand, eerste_selectie_datum, selectie_periode, selectie_medium, pl_plaatsings_bevoegdheid, adres_vraag_bevoegdheid, "
                        + "ad_hoc_medium, adres_medium, tabel_regel_start_datum, tabel_regel_eind_datum, sleutel_rubrieken, spontaan_rubrieken, "
                        + "selectie_rubrieken, ad_hoc_rubrieken, adres_rubrieken, afnemers_verstrekkingen) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        private static final int PARAM_AUTORISATIE_ID = 1;
        private static final int PARAM_AFNEMER_CODE = 2;
        private static final int PARAM_AFNEMER_NAAM = 3;
        private static final int PARAM_GEHEIDHOUDING_IND = 4;
        private static final int PARAM_VERSTREKKINGS_BEPERKING = 5;

        private static final int PARAM_STRAATNAAM = 6;
        private static final int PARAM_HUISNR = 7;
        private static final int PARAM_HUISLETTER = 8;
        private static final int PARAM_HUISNR_TOEVOEGING = 9;
        private static final int PARAM_PSOTCODE = 10;
        private static final int PARAM_GEMEENTE = 11;

        private static final int PARAM_CONDITIONELE_VERSTREKKING = 12;
        private static final int PARAM_SPONTAAN_MEDIUM = 13;
        private static final int PARAM_SELECTIE_SOORT = 14;
        private static final int PARAM_BERICHT_AAND = 15;
        private static final int PARAM_EERSTE_SELECTIE_DATUM = 16;
        private static final int PARAM_SELECTIE_PERIODE = 17;
        private static final int PARAM_SELECTIE_MEDIUM = 18;
        private static final int PARAM_PL_PLAATSINGS_BEVOEGDHEID = 19;
        private static final int PARAM_ADRES_VRAAG_BEVOEGDHEID = 20;
        private static final int PARAM_AD_HOC_MEDIUM = 21;
        private static final int PARAM_ADRES_MEDIUM = 22;
        private static final int PARAM_TABEL_REGEL_START_DATUM = 23;
        private static final int PARAM_TABEL_REGEL_EIND_DATUM = 24;
        private static final int PARAM_SLEUTEL_RUBRIEKEN = 25;
        private static final int PARAM_SPONTAAN_RUBRIEKEN = 26;
        private static final int PARAM_SELECTIE_RUBRIEKEN = 27;
        private static final int PARAM_AD_HOC_RUBRIEKEN = 28;
        private static final int PARAM_ADRES_RUBRIEKEN = 29;
        private static final int PARAM_AFNEMERS_VERSTREKKINGEN = 30;

        private static final int MAX_LENGTE_AFNEMERS_NAAM = 40;

        private static final String INSERT_VOORWAARDE_REGEL_SQL =
                "insert into lo3_voorwaarde_regel_aut(autorisatie_id, voorwaarde_type, voorwaarde_regel) values (?, ?, ?)";
        private static final int PARAM_VOORWAARDE_TYPE = 2;
        private static final int PARAM_VOORWAARDE_REGEL = 3;

        private static final String VOORWAARDE_TYPE_SPONTAAN = "4";
        private static final String VOORWAARDE_TYPE_SELECTIE = "5";
        private static final String VOORWAARDE_TYPE_ADHOC = "6";
        private static final String VOORWAARDE_TYPE_ADRES = "7";

        private final DataSource gbavDataSource;

        public GbavAutorisatieRepository(final DataSource gbavDataSource) {
            this.gbavDataSource = gbavDataSource;
        }

        public void verwerkAutorisatie(final Lo3Autorisatie lo3Autorisatie) {
            try (Connection connection = gbavDataSource.getConnection()) {
                connection.setAutoCommit(false);

                final String afnemerCode = lo3Autorisatie.getAfnemersindicatie();
                // LOG.info("Afnemer: " + afnemerCode);
                for (final Lo3Categorie<Lo3AutorisatieInhoud> tabelRegel : lo3Autorisatie.getAutorisatieStapel()) {
                    final long autorisatieId = selectAutorisatieId(connection);
                    insertAutorisatie(connection, autorisatieId, afnemerCode, tabelRegel);
                    insertVoorwaardeRegel(connection, autorisatieId, VOORWAARDE_TYPE_ADHOC, tabelRegel.getInhoud().getVoorwaarderegelAdHoc());
                    insertVoorwaardeRegel(connection, autorisatieId, VOORWAARDE_TYPE_ADRES, tabelRegel.getInhoud().getVoorwaarderegelAdresgeorienteerd());
                    insertVoorwaardeRegel(connection, autorisatieId, VOORWAARDE_TYPE_SELECTIE, tabelRegel.getInhoud().getVoorwaarderegelSelectie());
                    insertVoorwaardeRegel(connection, autorisatieId, VOORWAARDE_TYPE_SPONTAAN, tabelRegel.getInhoud().getVoorwaarderegelSpontaan());
                }
                connection.commit();

            } catch (final SQLException e) {
                throw new IllegalArgumentException("Kan autorisatie niet opslaan", e);
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
                setString(statement, PARAM_AFNEMER_CODE, afnemerCode);

                final String afnemerNaam =
                        inhoud.getAfnemernaam().length() > MAX_LENGTE_AFNEMERS_NAAM ? inhoud.getAfnemernaam().substring(0, MAX_LENGTE_AFNEMERS_NAAM)
                                : inhoud.getAfnemernaam();

                setString(statement, PARAM_AFNEMER_NAAM, afnemerNaam);
                setInt(statement, PARAM_GEHEIDHOUDING_IND, inhoud.getIndicatieGeheimhouding());
                setInt(statement, PARAM_VERSTREKKINGS_BEPERKING, inhoud.getVerstrekkingsbeperking());

                setString(statement, PARAM_STRAATNAAM, null);
                setString(statement, PARAM_HUISNR, null);
                setString(statement, PARAM_HUISLETTER, null);
                setString(statement, PARAM_HUISNR_TOEVOEGING, null);
                setString(statement, PARAM_PSOTCODE, null);
                setString(statement, PARAM_GEMEENTE, null);

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
            if (voorwaarderegel == null || !"".equals(voorwaarderegel)) {
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

    public static final class GbavAfnemersindicatieRepository {

        private static final String ZOEK_PERSOON_SQL = "select pl_id from lo3_pl_persoon where stapel_nr = 0 and volg_nr = 0 and a_nr = ? ";

        private static final String INSERT_AFNEMER_IND_SQL =
                "insert into lo3_pl_afnemer_ind(pl_id, stapel_nr, volg_nr, afnemer_code, geldigheid_start_datum) values (?, ?, ?, ?, ?)";
        private static final int PARAM_PL_ID = 1;
        private static final int PARAM_STAPEL_NR = 2;
        private static final int PARAM_VOLG_NR = 3;
        private static final int PARAM_AFNEMER_CODE = 4;
        private static final int PARAM_GELDIGHEID_START_DATUM = 5;

        private final DataSource gbavDataSource;

        public GbavAfnemersindicatieRepository(final DataSource gbavDataSource) {
            this.gbavDataSource = gbavDataSource;
        }

        public void verwerk(final Lo3Afnemersindicatie lo3Afnemersindicatie) {

            try (Connection connection = gbavDataSource.getConnection()) {
                final Long plId = zoekPersoon(connection, lo3Afnemersindicatie.getANummer());

                for (int stapel = 0; stapel < lo3Afnemersindicatie.getAfnemersindicatieStapels().size(); stapel++) {
                    final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatieStapel = lo3Afnemersindicatie.getAfnemersindicatieStapels().get(stapel);

                    for (int volgnummer = 0; volgnummer < afnemersindicatieStapel.size(); volgnummer++) {
                        final Lo3Categorie<Lo3AfnemersindicatieInhoud> afnemersindicatieVoorkomen = afnemersindicatieStapel.get(volgnummer);
                        final String afnemerCode = afnemersindicatieVoorkomen.getInhoud().getAfnemersindicatie();
                        final Lo3Datum geldigheidStartDatum = afnemersindicatieVoorkomen.getHistorie().getIngangsdatumGeldigheid();

                        insertAfnemersindicatie(connection, plId, stapel, volgnummer, afnemerCode, geldigheidStartDatum);
                    }
                }
            } catch (final SQLException e) {
                throw new IllegalArgumentException("SQL fout", e);
            }
        }

        private Long zoekPersoon(final Connection connection, final String aNummer) throws SQLException {
            try (final PreparedStatement statement = connection.prepareStatement(ZOEK_PERSOON_SQL)) {
                statement.setString(JdbcConstants.COLUMN_1, aNummer);

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new IllegalArgumentException("Geen persoon gevonden met a-nummer " + aNummer);
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
                final String afnemerCode,
                final Lo3Datum geldigheidStartDatum) throws SQLException {
            try (final PreparedStatement statement = connection.prepareStatement(INSERT_AFNEMER_IND_SQL)) {
                statement.setLong(PARAM_PL_ID, plId);
                statement.setInt(PARAM_STAPEL_NR, stapel);
                statement.setInt(PARAM_VOLG_NR, volgnummer);
                if (afnemerCode == null) {
                    statement.setNull(PARAM_AFNEMER_CODE, Types.NULL);
                } else {
                    statement.setString(PARAM_AFNEMER_CODE, afnemerCode);

                }
                statement.setInt(PARAM_GELDIGHEID_START_DATUM, geldigheidStartDatum.getIntegerWaarde());
                statement.execute();
            }
        }
    }

}
