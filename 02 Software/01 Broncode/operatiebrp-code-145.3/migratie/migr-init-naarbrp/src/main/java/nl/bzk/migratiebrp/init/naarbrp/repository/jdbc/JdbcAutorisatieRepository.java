/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository.jdbc;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRecordsType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.MediumType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieBericht;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.AutorisatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.BerichtVerwerker;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De JDBC implementatie van de AutorisatieRepository.
 */
@Repository
public final class JdbcAutorisatieRepository extends BasisJdbcRepository implements AutorisatieRepository {

    private static final String AUTORISATIE_ID = "autorisatie_id";
    private static final String AFNEMER_CODE = "afnemer_code";
    private static final String VERSTREKKINGS_BEPERKING = "verstrekkings_beperking";
    private static final String TABEL_REGEL_START_DATUM = "tabel_regel_start_datum";
    private static final String TABEL_REGEL_EIND_DATUM = "tabel_regel_eind_datum";
    private static final String VOORWAARDE_REGEL_SPONTAAN = "voorwaarde_regel_spontaan";
    private static final String SPONTAAN_RUBRIEKEN = "spontaan_rubrieken";
    private static final String SPONTAAN_MEDIUM = "spontaan_medium";
    private static final String SLEUTEL_RUBRIEKEN = "sleutel_rubrieken";
    private static final String VOORWAARDE_REGEL_SELECTIE = "voorwaarde_regel_selectie";
    private static final String SELECTIE_SOORT = "selectie_soort";
    private static final String SELECTIE_RUBRIEKEN = "selectie_rubrieken";
    private static final String SELECTIE_PERIODE = "selectie_periode";
    private static final String PL_PLAATSINGS_BEVOEGDHEID = "pl_plaatsings_bevoegdheid";
    private static final String SELECTIE_MEDIUM = "selectie_medium";
    private static final String INDICATIE_GEHEIMHOUDING = "indicatie_geheimhouding";
    private static final String EERSTE_SELECTIE_DATUM = "eerste_selectie_datum";
    private static final String CONDITIONELE_VERSTREKKING = "conditionele_verstrekking";
    private static final String BERICHT_AAND = "bericht_aand";
    private static final String AFNEMERS_VERSTREKKINGEN = "afnemers_verstrekkingen";
    private static final String AFNEMER_NAAM = "afnemer_naam";
    private static final String ADRES_VRAAG_BEVOEGDHEID = "adres_vraag_bevoegdheid";
    private static final String VOORWAARDE_REGEL_ADRES = "voorwaarde_regel_adres";
    private static final String ADRES_RUBRIEKEN = "adres_rubrieken";
    private static final String ADRES_MEDIUM = "adres_medium";
    private static final String VOORWAARDE_REGEL_ADHOC = "voorwaarde_regel_adhoc";
    private static final String ADHOC_RUBRIEKEN = "adhoc_rubrieken";
    private static final String ADHOC_MEDIUM = "adhoc_medium";
    private static final String CONVERSIE_RESULTAAT = "conversie_resultaat";
    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * {@inheritDoc}
     *
     * Let op: transactie timeout is verhoogd naar Integer.MAX_VALUE omdat dit statement zeer lang
     * kan duren.
     */
    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, timeout = Integer.MAX_VALUE)
    public void laadInitVullingAutTable() {
        final String sqlInitVulling = getStringResourceData("/sql/laadAutorisatie.sql");
        getJdbcTemplate().getJdbcOperations().execute(sqlInitVulling);
    }

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public void updateAutorisatieBerichtStatus(final List<String> afnemerCodes, final ConversieResultaat conversieResultaat) {
        final String updateInitVullingSqlAut = getStringResourceData("/sql/update_init_vulling_aut.sql");

        @SuppressWarnings("unchecked") final Map<String, ?>[] batchParameters = new Map[afnemerCodes.size()];
        int batchIndex = 0;
        for (final String afnemerCode : afnemerCodes) {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put(AFNEMER_CODE, afnemerCode);
            parameters.put(CONVERSIE_RESULTAAT, conversieResultaat.toString());
            batchParameters[batchIndex] = parameters;
            batchIndex += 1;
        }

        getJdbcTemplate().batchUpdate(updateInitVullingSqlAut, batchParameters);
    }

    @Override
    @Transactional(value = INIT_RUNTIME_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    public boolean verwerkAutorisatie(final ConversieResultaat zoekConversieResultaat, final BerichtVerwerker<AutorisatieBericht> verwerker,
                                      final int batchGrootte) {
        if (haalAutorisatieBatchOp(zoekConversieResultaat, verwerker, batchGrootte)) {
            LOG.debug("Berichten verwerken.");
            verwerker.verwerkBerichten();
        }
        return verwerker.aantalBerichten() >= batchGrootte;
    }

    private boolean haalAutorisatieBatchOp(final ConversieResultaat zoekConversieResultaat, final BerichtVerwerker<AutorisatieBericht> verwerker,
                                           final int batchGrootte) {

        final String selectInitVullingSqlAut = getStringResourceData("/sql/Verwerk_init_vulling_aut.sql") + " LIMIT " + batchGrootte;

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(CONVERSIE_RESULTAAT, zoekConversieResultaat.toString());
        final SqlParameterSource namedParameters = new MapSqlParameterSource(parameters);

        final AutorisatieBerichtVerwerkerRowHandler
                rowHandler =
                new AutorisatieBerichtVerwerkerRowHandler(getJdbcTemplate(), zoekConversieResultaat, verwerker);

        getJdbcTemplate().query(selectInitVullingSqlAut, namedParameters, rowHandler);

        return verwerker.aantalBerichten() > 0;
    }

    /**
     * Row handler om berichten te verwerken.
     */
    private static final class AutorisatieBerichtVerwerkerRowHandler implements RowCallbackHandler {
        private final NamedParameterJdbcTemplate jdbcTemplate;
        private final ConversieResultaat zoekConversieResultaat;
        private final BerichtVerwerker<AutorisatieBericht> verwerker;

        /**
         * Constructor.
         * @param verwerker Lo3BerichtVerwerker
         */
        AutorisatieBerichtVerwerkerRowHandler(final NamedParameterJdbcTemplate jdbcTemplate, ConversieResultaat zoekConversieResultaat,
                                              final BerichtVerwerker<AutorisatieBericht> verwerker) {
            this.jdbcTemplate = jdbcTemplate;
            this.zoekConversieResultaat = zoekConversieResultaat;
            this.verwerker = verwerker;
        }

        @Override
        public void processRow(final ResultSet rs) throws SQLException {
            final AutorisatieBericht bericht = mapAutorisatieBericht(rs);
            final int afnemerCode = rs.getInt(AFNEMER_CODE);
            LOG.debug("Voeg resultaat toe: {}", afnemerCode);
            verwerker.voegBerichtToe(bericht);
        }

        private AutorisatieBericht mapAutorisatieBericht(final ResultSet rs) throws SQLException {
            final AutorisatieType autorisaties = new AutorisatieType();

            final Map<String, Object> parameters = new HashMap<>();
            parameters.put(AFNEMER_CODE, rs.getString(AFNEMER_CODE));
            parameters.put(CONVERSIE_RESULTAAT, zoekConversieResultaat.toString());
            final SqlParameterSource namedParameters = new MapSqlParameterSource(parameters);

            final List<AutorisatieRecordType> gevondenAutorisatiesVoorAfnemer = jdbcTemplate
                    .query("select * from initvul.initvullingresult_aut where afnemer_code = :afnemer_code and conversie_resultaat = :conversie_resultaat",
                            namedParameters, new AutorisatieMapper());

            autorisaties.setAutorisatieTabelRegels(new AutorisatieRecordsType());
            autorisaties.getAutorisatieTabelRegels().getAutorisatieTabelRegel().addAll(gevondenAutorisatiesVoorAfnemer);
            autorisaties.setAfnemerCode(rs.getString(AFNEMER_CODE));

            final AutorisatieBericht result = new AutorisatieBericht(autorisaties);
            result.setMessageId(rs.getString(AFNEMER_CODE));

            return result;

        }
    }

    /**
     * Mapper voor autorisaties.
     */
    private static final class AutorisatieMapper implements RowMapper<AutorisatieRecordType> {
        private static String getString(final ResultSet rs, final String columnLabel) throws SQLException {
            return rs.getString(columnLabel);
        }

        private static MediumType getMedium(final ResultSet rs, final String columnLabel) throws SQLException {
            final String value = getString(rs, columnLabel);

            if (value == null || "".equals(value)) {
                return null;
            } else {
                return MediumType.fromValue(value);
            }
        }

        private static Integer getInteger(final ResultSet rs, final String columnLabel) throws SQLException {
            final int value = rs.getInt(columnLabel);
            if (rs.wasNull()) {
                return null;
            } else {
                return value;
            }
        }

        private static BigInteger getBigInteger(final ResultSet rs, final String columnLabel) throws SQLException {
            final Integer value = getInteger(rs, columnLabel);
            return value == null ? null : BigInteger.valueOf(value);
        }

        private static Long getLong(final ResultSet rs, final String columnLabel) throws SQLException {
            final long value = rs.getLong(columnLabel);
            if (rs.wasNull()) {
                return null;
            } else {
                return value;
            }
        }

        private static Short getShort(final ResultSet rs, final String columnLabel) throws SQLException {
            final Integer value = getInteger(rs, columnLabel);
            return value == null ? null : value.shortValue();
        }

        @Override
        public AutorisatieRecordType mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final AutorisatieRecordType record = new AutorisatieRecordType();
            record.setAutorisatieId(getLong(rs, AUTORISATIE_ID));
            record.setAdHocMedium(getMedium(rs, ADHOC_MEDIUM));
            record.setAdHocRubrieken(RubriekenFormatter.format(getString(rs, ADHOC_RUBRIEKEN)));
            record.setAdHocVoorwaarderegel(getString(rs, VOORWAARDE_REGEL_ADHOC));
            record.setAdresMedium(getMedium(rs, ADRES_MEDIUM));
            record.setAdresRubrieken(RubriekenFormatter.format(getString(rs, ADRES_RUBRIEKEN)));
            record.setAdresVoorwaarderegel(getString(rs, VOORWAARDE_REGEL_ADRES));
            record.setAdresVraagBevoegdheid(getInteger(rs, ADRES_VRAAG_BEVOEGDHEID));
            record.setAfnemerNaam(getString(rs, AFNEMER_NAAM));
            record.setAfnemersVerstrekkingen(getString(rs, AFNEMERS_VERSTREKKINGEN));
            record.setBerichtAand(getInteger(rs, BERICHT_AAND));
            record.setConditioneleVerstrekking(getInteger(rs, CONDITIONELE_VERSTREKKING));
            record.setEersteSelectieDatum(getBigInteger(rs, EERSTE_SELECTIE_DATUM));
            record.setGeheimhoudingInd(getShort(rs, INDICATIE_GEHEIMHOUDING));
            record.setPlPlaatsingsBevoegdheid(getInteger(rs, PL_PLAATSINGS_BEVOEGDHEID));
            record.setSelectieMedium(getMedium(rs, SELECTIE_MEDIUM));
            record.setSelectiePeriode(getInteger(rs, SELECTIE_PERIODE));
            record.setSelectieRubrieken(RubriekenFormatter.format(getString(rs, SELECTIE_RUBRIEKEN)));
            record.setSelectieSoort(getInteger(rs, SELECTIE_SOORT));
            record.setSelectieVoorwaarderegel(getString(rs, VOORWAARDE_REGEL_SELECTIE));
            record.setSleutelRubrieken(RubriekenFormatter.format(getString(rs, SLEUTEL_RUBRIEKEN)));
            record.setSpontaanMedium(getMedium(rs, SPONTAAN_MEDIUM));
            record.setSpontaanRubrieken(RubriekenFormatter.format(getString(rs, SPONTAAN_RUBRIEKEN)));
            record.setSpontaanVoorwaarderegel(getString(rs, VOORWAARDE_REGEL_SPONTAAN));
            record.setTabelRegelEindDatum(getBigInteger(rs, TABEL_REGEL_EIND_DATUM));
            record.setTabelRegelStartDatum(getBigInteger(rs, TABEL_REGEL_START_DATUM));
            record.setVerstrekkingsBeperking(getShort(rs, VERSTREKKINGS_BEPERKING));

            return record;
        }
    }

    /**
     * Converteert de rubrieken van formaat 'xxxxx xxxxxx xxxxx' naar 'xx.xx.xx#xx.xx.xx#xx.xx.xx'.
     */
    public static final class RubriekenFormatter {
        private static final String NR_FORMAT = "##.##";
        private static final int BEGIN_CATEGORIE = 0;
        private static final int EIND_CATEGORIE = 2;
        private static final int BEGIN_GROEP = 2;
        private static final int EIND_GROEP = 4;
        private static final int BEGIN_VOLG_NUMMER = 4;
        private static final int EIND_VOLG_NUMMER = 6;
        private static final String PUNT = ".";

        private RubriekenFormatter() {
            // Niet geinstantieerd.
        }

        /**
         * Converteer rubrieken.
         * @param rubrieken in het formaat 'xxxxx xxxxxx xxxxxx'
         * @return String in het formaat 'xx.xx.xx#xx.xx.xx'
         */
        public static String format(final String rubrieken) {
            if (rubrieken == null) {
                return null;
            }

            final StringBuilder geconverteerdeRubrieken = new StringBuilder();
            final DecimalFormat f = new DecimalFormat(NR_FORMAT);
            final DecimalFormatSymbols fs = f.getDecimalFormatSymbols();
            fs.setGroupingSeparator('.');
            f.setDecimalFormatSymbols(fs);

            final String[] losseRubrieken = rubrieken.split(" ");
            for (int i = 0; i < losseRubrieken.length; i++) {
                final String losseRubriek = pad(losseRubrieken[i], 6, "0");

                // converteer van '\d[5,6]' naar '\d\d.\d\d.\d\d'
                // met daarbij 'categorie.groep.rubriekVolgNummer'
                // vervolgens weer aan elkaar 'plakken' met een '#'
                geconverteerdeRubrieken.append(losseRubriek.substring(BEGIN_CATEGORIE, EIND_CATEGORIE)).append(PUNT)
                        .append(losseRubriek.substring(BEGIN_GROEP, EIND_GROEP)).append(PUNT)
                        .append(losseRubriek.substring(BEGIN_VOLG_NUMMER, EIND_VOLG_NUMMER));

                if (i < losseRubrieken.length - 1) {
                    geconverteerdeRubrieken.append("#");
                }
            }

            return geconverteerdeRubrieken.toString();
        }

        private static String pad(final String str, final int requiredLength, final String padString) {
            if (str.length() < requiredLength) {
                return pad(padString + str, requiredLength, padString);
            } else {
                return str;
            }
        }
    }

}
