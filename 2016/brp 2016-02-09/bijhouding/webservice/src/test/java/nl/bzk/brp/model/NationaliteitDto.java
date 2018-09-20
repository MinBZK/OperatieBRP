/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public class NationaliteitDto {

    private static final String SQL_OPHALEN_PERSOONNATIONALITEIT_A_LAAG_MIDDELS_BSN =
            "select nation.nation, nation.rdnverk, rdnverlies "
                    + "from kern.persnation nation, kern.pers pers "
                    + "where nation.pers = pers.id and pers.id = ? order by nation.id asc ";

    private final Integer nation;
    private final Integer rdnverk;
    private final Integer rdnverlies;


    public NationaliteitDto(final Integer nation, final Integer rdnverk, final Integer rdnverlies) {
        this.nation = nation;
        this.rdnverk = rdnverk;
        this.rdnverlies = rdnverlies;
    }

    public Integer getNation() {
        return nation;
    }

    public Integer getRdnverk() {
        return rdnverk;
    }

    public Integer getRdnverlies() {
        return rdnverlies;
    }

    public static List<NationaliteitDto> getPersoonNationaliteitLevelA(final JdbcTemplate simpleJdbcTemplate,
                                                           final Integer persoonId)
    {
        List<NationaliteitDto> nationaliteiten = new ArrayList<>();

        List<Map<String, Object>> rows =
                simpleJdbcTemplate.queryForList(SQL_OPHALEN_PERSOONNATIONALITEIT_A_LAAG_MIDDELS_BSN, persoonId);
        for (Map<String, Object> row : rows) {
            nationaliteiten.add(new NationaliteitDto(
                    (Integer) row.get("nation"),
                    (Integer) row.get("rdnverk"),
                    (Integer) row.get("rdnverlies")));
        }

        return nationaliteiten;
    }
}
