/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import java.util.function.Consumer;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Databaseverzoek voor het ophalen van Persoon.Admhnd obv Persoon.Bsn
 */
final class PersHandelingVerzoek implements Consumer<JdbcTemplate> {

    private final String persBsn;
    private Long handelingId;

    PersHandelingVerzoek(final String persBsn) {
        this.persBsn = persBsn;
    }

    @Override
    public void accept(final JdbcTemplate jdbcTemplate) {
        this.handelingId = jdbcTemplate.queryForObject("select admhnd from kern.pers where bsn = ? ", Long.class, persBsn);
    }

    Long getHandelingId() {
        return handelingId;
    }
}
