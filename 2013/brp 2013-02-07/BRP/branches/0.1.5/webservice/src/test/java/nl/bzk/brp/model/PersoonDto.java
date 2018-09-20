/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * .
 *
 */
public final class PersoonDto {
    private static final String SQL_PERSOON_BY_BSN =
            "select id, bsn, anr from kern.pers where bsn = ?";
    private final Long id;
    private final String bsn;
    private final String anr;

    private PersoonDto(final Long id, final String bsn, final String anr) {
        this.id = id; this.bsn = bsn; this.anr = anr;
    }
    /**
     *
     * @return het id
     */
    public Long getId() {
        return id;
    }
    /**
     * @return de bsn
     */
    public String getBsn() {
        return bsn;
    }
    /**
     * @return the anr
     */
    public String getAnr() {
        return anr;
    }

    /**
     * .
     * @param simpleJdbcTemplate
     * @param bsn
     * @return
     */
    public static List<PersoonDto> persoonMetBsnNumerBestaatLevelA(final SimpleJdbcTemplate simpleJdbcTemplate, final String bsn)  {
        List<PersoonDto> personen = new ArrayList<PersoonDto>();

        List<Map<String, Object>> rows = simpleJdbcTemplate.queryForList(SQL_PERSOON_BY_BSN, bsn);
        for (Map<String, Object> row : rows) {
            personen.add(new PersoonDto(
                    (Long) row.get("id"),
                    (String) row.get("bsn"),
                    (String) row.get("anr")
            ));
        }
        return personen;
    }


}
