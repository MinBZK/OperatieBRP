/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.bevraging.app.support.PersoonIdentificatie;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Stap verantwoordelijk voor het ophalen van een lijst van random BSNs uit de database.
 */
@Component
public class CollecteerBsnStap implements Command {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private int blockSize = 10000;

    private static final String QUERY_MIN = "SELECT MIN(kp.id) FROM kern.pers kp";
    private static final String QUERY_MAX = "SELECT COUNT(kp.id) FROM kern.pers kp";

    private static final String QUERY_BSN =
            "SELECT kp.id, kp.bsn FROM kern.pers kp WHERE kp.id IN (:ids) AND kp.bsn IS NOT NULL "
                    + "AND kp.rdnopschortingbijhouding IS NULL";
    private static final int MAX_TRIES = 3;
    private static final double FACTOR = 1.1;
    private static final MapSqlParameterSource NO_PARAMETERS = new MapSqlParameterSource();

    private static final Logger LOGGER = LoggerFactory.getLogger(CollecteerBsnStap.class);

    private StopWatch timer = new StopWatch("WarmDatabaseOp");

    @Inject
    @Named("dataSource")
    public void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void setBlockSize(final int size) {
        this.blockSize = size;
    }

    @Override
    public boolean execute(final Context context) throws Exception {
        Set<PersoonIdentificatie> bsns = new HashSet<PersoonIdentificatie>();

        int minId = jdbcTemplate.queryForInt(QUERY_MIN, NO_PARAMETERS);
        long maxId = jdbcTemplate.queryForLong(QUERY_MAX, NO_PARAMETERS);
        int count = (Integer) context.get(ContextParameterNames.AANTAL_PERSOONSLIJSTEN);

        // error situatie
        if (maxId == 0L) {
            LOGGER.warn("Geen poging gedaan, DB is leeg!");
            return PROCESSING_COMPLETE;
        }

        timer.start(this.getClass().getName());

        for (int blokCount = 0; blokCount* blockSize <= count; blokCount++) {
            int tryCount = 0;
            while (bsns.size() < (blokCount+1) * blockSize && bsns.size() < count && tryCount < MAX_TRIES) {
                LOGGER.info("Poging {} van {} om blok {} van de BSN lijst te vullen", new Object[] {tryCount + 1, MAX_TRIES, (blokCount+1)});

                List<Long> ids = getRandomList(minId, maxId, blockSize);

                MapSqlParameterSource parameters = new MapSqlParameterSource("ids", ids);

                List<Map<String, Object>> dbList = jdbcTemplate.queryForList(QUERY_BSN, parameters);

                for (Map<String, Object> map : dbList) {
                    bsns.add(new PersoonIdentificatie(map));
                }

                tryCount++;
            }
        }

        List<PersoonIdentificatie> result = new ArrayList<PersoonIdentificatie>(bsns);
        if (result.size() > count) {
            result = result.subList(0, count);
        }

        timer.stop();

        context.put(ContextParameterNames.BSNLIJST, result);
        LOGGER.info("{} BSN nummers verzameld, {} gevraagd", result.size(), count);
        LOGGER.info("{} BSN nummers verzameld, in {} ms", result.size(), timer.getLastTaskTimeMillis());

        return CONTINUE_PROCESSING;
    }

    /**
     * Geeft een willekeurige lijst tussen <code>min</code> en <code>max</code> van lengte <code>count</code>.
     *
     * @param min   de minimum waarde van willekeurige getallen
     * @param max   de maximum waarde van willekeurige getallen
     * @param count het aantal willekeurige getallen
     * @return een lijst met willekeurige getallen
     */
    private List<Long> getRandomList(final int min, final long max, final int count) {
        Set<Long> result = new HashSet<Long>(count);

        for (int i = 0; i < count * FACTOR; i++) {
            result.add(nextLong(max - min) + min);
        }

        return new ArrayList<Long>(result);
    }

    /**
     * Geeft een volgende long.
     *
     * @param n seed
     * @return een long
     */
    private long nextLong(final long n) {
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
