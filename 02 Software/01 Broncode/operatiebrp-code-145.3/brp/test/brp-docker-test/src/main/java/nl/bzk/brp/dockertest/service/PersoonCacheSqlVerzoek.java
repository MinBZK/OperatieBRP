/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Verzoek voor het ophalen van de perscache gegevens.
 */
public final class PersoonCacheSqlVerzoek implements Consumer<JdbcTemplate> {
    private List<Map<String, Object>> persoonCacheData;

    @Override
    public void accept(final JdbcTemplate jdbcTemplate) {
        final String query =
                "SELECT id, pershistorievollediggegevens,afnemerindicatiegegevens,lockversieafnemerindicatiege from kern.perscache";
        persoonCacheData = jdbcTemplate.queryForList(query);
    }

    List<Map<String, Object>> getPersoonCacheData() {
        return persoonCacheData;
    }
}
