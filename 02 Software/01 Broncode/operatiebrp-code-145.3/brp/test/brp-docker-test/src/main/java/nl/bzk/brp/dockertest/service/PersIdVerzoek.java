/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import java.util.function.Consumer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Databaseverzoek voor het ophalen van Persoon.Id obv Persoon.Bsn
 */
final class PersIdVerzoek implements Consumer<JdbcTemplate> {

    private static final int INDEX_LOCK_COLUMN = 2;

    private final String persBsn;
    private int persId;
    private int lockVersie;

    PersIdVerzoek(final String persBsn) {
        this.persBsn = persBsn;
    }

    int getPersId() {
        return persId;
    }

    int getLockVersie() {
        return lockVersie;
    }

    @Override
    public void accept(final JdbcTemplate jdbcTemplate) {
        final SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select id, lockversie from kern.pers where bsn = ?", persBsn);
        if (sqlRowSet.next()) {
            persId = sqlRowSet.getInt(1);
            lockVersie = sqlRowSet.getInt(INDEX_LOCK_COLUMN);
        }
    }
}
