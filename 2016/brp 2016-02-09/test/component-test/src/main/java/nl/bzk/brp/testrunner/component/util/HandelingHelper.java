/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.component.BrpOmgeving;
import nl.bzk.brp.testrunner.component.RouteringCentrale;
import nl.bzk.brp.testrunner.component.services.datatoegang.VerzoekMetJdbcTemplate;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.apache.commons.lang.mutable.MutableLong;
import org.springframework.jdbc.core.JdbcTemplate;
import sun.java2d.xr.MutableInteger;

/**
 */
public class HandelingHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final BrpOmgeving omgeving;

    public HandelingHelper(final BrpOmgeving omgeving) {
        this.omgeving = omgeving;

    }

    public void leverLaatsteHandeling(final int bsn) {
        leverHandeling(bsn, 1);
    }

    /**
     * ==== Handeling nogmaals leveren
     * Simuleert het (nogmaals) leveren van een administratieve handeling die voor
     * een wijziging van een persoonslijst heeft gezorgd. Dit houdt in dat de kolom
     * `tslev` van de tabel kern.admhnd wordt leeg gemaakt, en dat de de waarde van de
     * `ID` kolom op de queue voor verwerkte administratieve handelingen wordt geplaatst.
     * De mutatielevering software verwerkt deze administratieve handeling alsof deze zo juist
     * door de bijhouding is verwerkt.
     *
     * @param bsn het burgerservicenummer van de persoon, waarvan een handeling opnieuwe verwerkt
     *      worden door de mutatielevering software.
     * @param index in aanduiding van de hoeveel na laatste handeling opnieuw geleverd
     *      gaat worden. `1` is de een na laatste, `2` de twee na laatste, etc. Technisch
     *      gezien kan `0` voor de laatste worden opgegeven, maar je kan ook
     *      link:#laatste_handeling_nogmaals_leveren[Laatste handeling nogmaals leveren] gebruiken
     */
    public void leverHandeling(final int bsn, int index) {
        LOGGER.info("Lever laatste handeling voor persoon met BSN: " + bsn);

        final long handelingId = geefHandeling(bsn, index);
        // reset handeling
        omgeving.database().voerUitMetTransactie(new VerzoekMetJdbcTemplate() {
            @Override
            public void voerUitMet(final JdbcTemplate jdbcTemplate) {
                jdbcTemplate.update("UPDATE kern.admhnd SET tslev = now()");
                jdbcTemplate.update("UPDATE kern.admhnd SET tslev = null WHERE id = ?", handelingId);

            }
        });
        // plaats handeling op queue (asynchroon)
        final RouteringCentrale routeringCentrale = omgeving.geefComponent(RouteringCentrale.class);
        routeringCentrale.verstuurHandeling(handelingId);

        // wacht tot verwerkt
        final MutableBoolean isVerwerkt = new MutableBoolean(false);
        for(int i = 0 ; i < 50 && !isVerwerkt.booleanValue() ; i++) {
            final int iteratie = i;
            LOGGER.info(String.format("Wacht tot handeling %d verwerkt is...", handelingId));
            omgeving.database().voerUit(new VerzoekMetJdbcTemplate() {
                @Override
                public void voerUitMet(final JdbcTemplate jdbcTemplate) {

                    final List<Map<String, Object>> list = jdbcTemplate
                        .queryForList("SELECT id FROM kern.admhnd WHERE id = ? AND tslev IS NOT NULL;", handelingId);
                    if (!list.isEmpty()) {
                        isVerwerkt.setValue(true);
                        return;
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(500 * iteratie);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        if (!isVerwerkt.booleanValue()) {
            throw new RuntimeException("Handeling niet binnen de tijd verwerkt");
        }
        LOGGER.info("Handeling verwerkt");
    }

    /**
     * Reset voor een persoon (BSN) een specifieke levering door de tslev van de
     * bijbehorende handeling op NULL te zetten, ook wordt de identifier van de
     * administratieve handeling teruggegeven.
     *
     *
     * @param bsn Burgerservicenummer van de persoon
     * @param index numerieke index van de handeling, waarbij de laatste bij 1 begint en dan oploopt bij terugtellen.
     * @return de handelingIds die zijn gereset, gesorteerd op tslaatstewijz van de *handeling* (oudste eerst)
     */
    private long geefHandeling(final int bsn, final int index) {

        final MutableLong handelingId = new MutableLong(0);
        omgeving.database().voerUit(new VerzoekMetJdbcTemplate() {
            @Override
            public void voerUitMet(final JdbcTemplate jdbcTemplate) {
                //handelingId.setValue(jdbcTemplate.queryForObject("select admhnd from kern.pers where bsn = ?",
                 //   new Object[]{bsn}, Integer.class));

                //

                String query = "SELECT a.id, hp.tslaatstewijz "
                    + "FROM kern.admhnd a JOIN kern.his_persafgeleidadministrati hp ON (hp.admhnd = a.id) "
                    + "WHERE hp.pers = (SELECT id FROM kern.pers where bsn = ?) "
                    + "ORDER BY hp.tslaatstewijz DESC, hp.id DESC ";
                if (index > -1) {
                    query += " LIMIT " + index;
                }

                final List<Map<String, Object>> list = jdbcTemplate.queryForList(query, bsn);

                if (list.isEmpty()) {
                    throw new RuntimeException("Persoon heeft geen handelingen");
                }

                Collections.reverse(list);
                handelingId.setValue(list.iterator().next().get("id"));

            }
        });
        return handelingId.longValue();
    }
}
