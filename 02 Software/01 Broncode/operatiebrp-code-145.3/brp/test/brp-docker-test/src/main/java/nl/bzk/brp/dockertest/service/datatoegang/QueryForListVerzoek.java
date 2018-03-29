/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service.datatoegang;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Verzoek voor het querien van lijst.
 */
public class QueryForListVerzoek implements Consumer<JdbcTemplate> {

    private final String query;
    private List<Map<String, Object>> records;

    public QueryForListVerzoek(final String query) {
        this.query = query;
    }

    @Override
    public void accept(final JdbcTemplate jdbcTemplate) {
        records = jdbcTemplate.queryForList(query);
    }

    public List<Map<String, Object>> getRecords() {
        return records;
    }
}
