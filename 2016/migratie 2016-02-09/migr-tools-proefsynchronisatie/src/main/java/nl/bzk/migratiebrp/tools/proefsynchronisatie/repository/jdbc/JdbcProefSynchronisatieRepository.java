/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.domein.ProefSynchronisatieBericht;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.repository.ProefSynchronisatieRepository;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.BerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De JDBC implementatie van de ProefSynchronisatieRepository.
 */
@Repository
public final class JdbcProefSynchronisatieRepository extends AbstractJdbcRepository implements ProefSynchronisatieRepository {

    /**
     * GMT tijdzone wordt gebruikt voor Timestamps.
     */
    public static final TimeZone GBAV_TIJDZONE = TimeZone.getTimeZone("GMT+1:00");

    private static final Lo3BerichtFactory LO3_BERICHT_FACTORY = new Lo3BerichtFactory();

    private static final String DATE_TIME_FORMAT = "yyyyMMddHHmmssSSS";

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String KOLOM_AFZENDER = "afzender";
    private static final String KOLOM_BERICHT_ID = "bericht_id";
    private static final String KOLOM_BERICHT_DATUM = "bericht_datum";
    private static final String KOLOM_BERICHT = "bericht";
    private static final String KOLOM_ID = "id";
    private static final String KOLOM_MS_SEQUENCE_NUMBER = "ms_sequence_number";
    private static final String KOLOM_VERWERKT = "verwerkt";
    private static final String QUOTE_LITERAL = "'";
    private static final String PARAMETER_LITERAL = "\\?";
    private static final String CONDITIE_DATUM_TOT = " AND ber.tijdstip_verzending_ontvangst < ";

    @Override
    @Transactional(value = PROEF_SYNCHRONISATIE_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public void laadInitProefSynchronisatieBerichtenTabel(final String datumVanaf, final String datumTot) {
        final String vervangTekst;

        if (datumTot == null || "".equals(datumTot)) {
            vervangTekst = QUOTE_LITERAL + datumVanaf + QUOTE_LITERAL;
        } else {
            vervangTekst = QUOTE_LITERAL + datumVanaf + QUOTE_LITERAL + CONDITIE_DATUM_TOT + QUOTE_LITERAL + datumTot + QUOTE_LITERAL;
        }
        final String sqlInitVulling = getStringResourceData("/sql/createProefSynchronisatieBericht.sql").replaceFirst(PARAMETER_LITERAL, vervangTekst);

        getJdbcTemplate().getJdbcOperations().execute(sqlInitVulling);
    }

    @Override
    @Transactional(value = PROEF_SYNCHRONISATIE_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    public void verwerkProefSynchronisatieBericht(
        final BerichtVerwerker<ProefSynchronisatieBericht> verwerker,
        final int batchGrootte,
        final long wachtPeriode)
    {
        final String selectInitVullingSqlAut = getStringResourceData("/sql/verwerkProefSynchronisatieBericht.sql");

        final Map<String, Object> parameters = new HashMap<>();
        final SqlParameterSource namedParameters = new MapSqlParameterSource(parameters);

        final ProefSynchronisatieBerichtVerwerkerRowHandler rowHandler =
                new ProefSynchronisatieBerichtVerwerkerRowHandler(verwerker, batchGrootte, wachtPeriode);

        getJdbcTemplate().query(selectInitVullingSqlAut, namedParameters, rowHandler);

        verwerker.verwerkBerichten();
    }

    @Override
    @Transactional(value = PROEF_SYNCHRONISATIE_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public boolean updateProefSynchronisatieBerichtStatus(final List<Long> proefSynchronisatieIds) {
        final String updateProefSynchronisatieBerichtSQL = getStringResourceData("/sql/updateProefSynchronisatieBericht.sql");

        final Map<String, ?>[] batchParameters = new Map[proefSynchronisatieIds.size()];
        int batchIndex = 0;
        for (final Long proefSynchronisatieId : proefSynchronisatieIds) {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put(KOLOM_ID, proefSynchronisatieId);
            batchParameters[batchIndex] = parameters;
            batchIndex += 1;
        }

        return proefSynchronisatieIds.size() == getJdbcTemplate().batchUpdate(updateProefSynchronisatieBerichtSQL, batchParameters).length;
    }

    /**
     * Row handler om berichten te verwerken.
     */
    private static final class ProefSynchronisatieBerichtVerwerkerRowHandler implements RowCallbackHandler {
        private final BerichtVerwerker<ProefSynchronisatieBericht> verwerker;
        private final int batchGrootte;
        private int teller;
        private final long wachtPeriode;

        /**
         * Constructor.
         *
         * @param verwerker
         *            Lo3BerichtVerwerker
         * @param batchGrootte
         *            grootte van de batch
         */
        public ProefSynchronisatieBerichtVerwerkerRowHandler(
            final BerichtVerwerker<ProefSynchronisatieBericht> verwerker,
            final int batchGrootte,
            final long wachtPeriode)
        {
            this.verwerker = verwerker;
            this.batchGrootte = batchGrootte;
            this.wachtPeriode = wachtPeriode;
        }

        @Override
        public void processRow(final ResultSet rs) throws SQLException {
            final ProefSynchronisatieBericht bericht = mapProefSynchronisatieBericht(rs);
            verwerker.voegBerichtToe(bericht);
            teller += 1;

            if (teller >= batchGrootte) {
                verwerker.verwerkBerichten();
                try {
                    Thread.sleep(wachtPeriode);
                } catch (final InterruptedException exceptie) {
                    LOG.error(ExceptionUtils.getStackTrace(exceptie));
                }
                teller = 0;
            }
        }

        private ProefSynchronisatieBericht mapProefSynchronisatieBericht(final ResultSet rs) throws SQLException {

            final ProefSynchronisatieBericht proefSynchronisatieBericht = new ProefSynchronisatieBericht();
            proefSynchronisatieBericht.setAfzender(rs.getInt(KOLOM_AFZENDER));
            proefSynchronisatieBericht.setBerichtDatum(rs.getTimestamp(KOLOM_BERICHT_DATUM));
            proefSynchronisatieBericht.setBerichtId(rs.getLong(KOLOM_BERICHT_ID));
            proefSynchronisatieBericht.setId(rs.getLong(KOLOM_ID));
            proefSynchronisatieBericht.setMsSequenceNumber(rs.getLong(KOLOM_MS_SEQUENCE_NUMBER));
            proefSynchronisatieBericht.setVerwerkt(rs.getBoolean(KOLOM_VERWERKT));

            // Omdat we La01-berichten willen converteren naar Lg01-berichten voeren we een extra stap uit bij het
            // mappen.
            final String bericht = rs.getString(KOLOM_BERICHT);
            try {
                // We gaan er vanuit dat het binnenkomende bericht in Teletex encoding is.
                final Lo3Bericht lo3Bericht = LO3_BERICHT_FACTORY.getBericht(bericht);
                lo3Bericht.setBronGemeente(String.format("%04d", proefSynchronisatieBericht.getAfzender()));
                lo3Bericht.setMessageId(String.valueOf(proefSynchronisatieBericht.getBerichtId()));

                if (lo3Bericht instanceof Lg01Bericht) {
                    proefSynchronisatieBericht.setBericht(bericht);
                } else if (lo3Bericht instanceof La01Bericht) {
                    final Lg01Bericht lg01Bericht = converteerLa01BerichtNaarLg01Bericht((La01Bericht) lo3Bericht, proefSynchronisatieBericht);
                    LOG.info("Bericht met ID {} wordt geconverteerd van een La01 naar een Lg01.", lg01Bericht.getMessageId());
                    proefSynchronisatieBericht.setBericht(lg01Bericht.format());
                } else {
                    throw new UnsupportedOperationException("Het berichttype " + lo3Bericht.getBerichtType() + " wordt niet ondersteund.");
                }

            } catch (final Exception exceptie) {
                // We willen dat de proefsync berichten op de queue blijft plaatsen ook al gaat er bij
                // het converteren van het bericht iets fout. De betreffende berichten/uitval kan dan naderhand
                // geanalyseerd worden.
                LOG.error(ExceptionUtils.getStackTrace(exceptie));
                LOG.error(
                    "Bericht met ID {} kon niet worden geconverteerd naar een Lo3Bericht. Bericht wordt alsnog doorgestuurd.",
                    proefSynchronisatieBericht.getId());
                proefSynchronisatieBericht.setBericht(bericht);
            }

            return proefSynchronisatieBericht;
        }

        private Lg01Bericht converteerLa01BerichtNaarLg01Bericht(final La01Bericht la01Bericht, final ProefSynchronisatieBericht proefSynchronisatieBericht)
        {

            final Lg01Bericht lg01Bericht = new Lg01Bericht();
            final Lo3Persoonslijst pl = la01Bericht.getLo3Persoonslijst();
            lg01Bericht.setLo3Persoonslijst(pl);
            lg01Bericht.setBronGemeente(la01Bericht.getBronGemeente());

            final Long actueelANummer =
                    pl.getPersoonStapel() == null ? null : Lo3Long.unwrap(pl.getPersoonStapel().getLaatsteElement().getInhoud().getANummer());

            final Long vorigANummer =
                    pl.getPersoonStapel() == null ? null : Lo3Long.unwrap(pl.getPersoonStapel().getLaatsteElement().getInhoud().getVorigANummer());

            final SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
            format.setTimeZone(GBAV_TIJDZONE);
            final Long datumTijdMillis = Long.parseLong(format.format(proefSynchronisatieBericht.getBerichtDatum()));

            lg01Bericht.setHeader(Lo3HeaderVeld.DATUM_TIJD, String.valueOf(datumTijdMillis));
            lg01Bericht.setHeader(Lo3HeaderVeld.A_NUMMER, actueelANummer == null ? null : String.valueOf(actueelANummer));
            lg01Bericht.setHeader(Lo3HeaderVeld.OUD_A_NUMMER, vorigANummer == null ? null : String.valueOf(vorigANummer));
            lg01Bericht.setMessageId(la01Bericht.getMessageId());

            return lg01Bericht;
        }

    }

}
