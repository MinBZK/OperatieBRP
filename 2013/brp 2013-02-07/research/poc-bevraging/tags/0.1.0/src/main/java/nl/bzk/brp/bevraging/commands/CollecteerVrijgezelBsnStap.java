/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.util.*;

import javax.inject.Inject;
import javax.sql.DataSource;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Stap verantwoordelijk voor het ophalen van een lijst van random BSNs uit de database.
 */
@Component
public class CollecteerVrijgezelBsnStap implements Command {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String QUERY_MIN = "SELECT MIN(kp.id) FROM kern.pers kp";
    private static final String QUERY_MAX = "SELECT COUNT(kp.id) FROM kern.pers kp";

    private static final String QUERY_BSN =
            "SELECT kp.bsn FROM kern.pers kp WHERE kp.id IN (:ids) AND kp.bsn IS NOT NULL"
                    + " AND kp.rdnopschortingbijhouding IS NULL"
                    + " AND kp.datgeboorte < :datum"
                    + " AND NOT EXISTS (SELECT kb.pers FROM kern.betr kb WHERE kb.rol = 3 AND kb.pers = kp.id)"
                    + " AND NOT EXISTS (SELECT ki.pers FROM kern.persindicatie ki WHERE ki.srt=2 AND ki.pers = kp.id)";

    private static final int MAX_TRIES = 3;
    private static final double FACTOR = 1.1;
    private static final MapSqlParameterSource noParameters = new MapSqlParameterSource();

    private static final Logger LOG = LoggerFactory.getLogger(CollecteerVrijgezelBsnStap.class);

    @Inject
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean execute(final Context context) throws Exception {
        List<Integer> result = new ArrayList<Integer>();

        int minId = jdbcTemplate.queryForInt(QUERY_MIN, noParameters);
        long maxId = jdbcTemplate.queryForLong(QUERY_MAX, noParameters);
        int count = (Integer) context.get(ContextParameterNames.AANTAL_PERSOONSLIJSTEN);

        // error situatie
        if (maxId == 0) {
            LOG.warn("Geen poging gedaan, DB is leeg!");
            return true;
        }

        String datumMinimaal18 = DateFormatUtils.format(DateUtils.addYears(new Date(), -18), "yyyyMMdd");
        int tryCount = 0;

        while (result.size() < count && tryCount < MAX_TRIES) {
            LOG.info("Poging {} van {} om de Vrijgezel BSN lijst te vullen", tryCount + 1, MAX_TRIES);

            List<Long> param = getRandomList(minId, maxId, count);

            MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("ids", param).addValue("datum",
                                                                                                           Integer.valueOf(
                                                                                                                   datumMinimaal18));

            List<Integer> dbList = jdbcTemplate.queryForList(QUERY_BSN, parameters, Integer.class);

            result.addAll(dbList);
            tryCount++;
        }

        if (result.size() > count) {
            result = result.subList(0, count);
        }

        context.put("vrijgezelBsnList", result);
        LOG.info("{} BSN nummers verzameld", result.size());

        return false;
    }

    private List<Long> getRandomList(int min, long max, int count) {
        Set<Long> result = new HashSet<Long>(count);

        for (int i = 0; i < count * FACTOR; i++) {
            result.add(nextLong(max - min) + min);
        }

        return new ArrayList<Long>(result);
    }

    private long nextLong(long n) {
        Random rng = new Random();

        // error checking and 2^x checking removed for simplicity.
        long bits, val;
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);
        return val;
    }
}
