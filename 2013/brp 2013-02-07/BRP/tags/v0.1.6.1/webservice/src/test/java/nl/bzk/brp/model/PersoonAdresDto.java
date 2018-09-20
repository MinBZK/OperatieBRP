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
 * @author boen
 *
 */
public final class PersoonAdresDto {
    private final Long id;
    private final Long historischId;
    private final String adreseerbaarObject;
    private final String huisnr;
    private final String postcode;

    private PersoonAdresDto(final Long id, final Long historischId, final String adreseerbaarObject, final String huisnr, final String postcode) {
        this.id = id; this.historischId = historischId; this.adreseerbaarObject = adreseerbaarObject;
        this.huisnr = huisnr; this.postcode = postcode;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the historischId
     */
    public Long getHistorischId() {
        return historischId;
    }

    /**
     * @return the adreseerbaarObject
     */
    public String getAdreseerbaarObject() {
        return adreseerbaarObject;
    }

    /**
     * @return the huisnr
     */
    public String getHuisnr() {
        return huisnr;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    public static List<PersoonAdresDto> persoonAdresLevelA(final SimpleJdbcTemplate simpleJdbcTemplate, final String bsn)  {
        String sql = "select pa.id, pa.adresseerbaarobject, pa.huisnr, pa.postcode from kern.persadres pa, kern.pers pe where pa.pers = pe.id and pe.bsn = ?";
        List<PersoonAdresDto> persoonAdressen = new ArrayList<PersoonAdresDto>();

        List<Map<String, Object>> rows = simpleJdbcTemplate.queryForList(sql, bsn);
        for (Map<String, Object> row : rows) {
            persoonAdressen.add(new PersoonAdresDto(
                    (Long) row.get("id"), null,
                    (String) row.get("adresseerbaarobject"),
                    (String) row.get("huisnr"),
                    (String) row.get("postcode")
            ));
        }
        return persoonAdressen;
    }

    public static List<PersoonAdresDto> persoonAdresLevelC(final SimpleJdbcTemplate simpleJdbcTemplate, final String bsn)  {
        String sql = "select pa.id, pa.adresseerbaarobject, pa.huisnr, pa.postcode from kern.his_persadres pa, kern.persadres paN, kern.pers pe where paN.pers = pe.id and pa.persadres=paN.id and pe.bsn = ?";
        List<PersoonAdresDto> persoonAdressen = new ArrayList<PersoonAdresDto>();

        List<Map<String, Object>> rows = simpleJdbcTemplate.queryForList(sql, bsn);
        for (Map<String, Object> row : rows) {
            persoonAdressen.add(new PersoonAdresDto(
                    (Long) row.get("id"), null,
                    (String) row.get("adresseerbaarobject"),
                    (String) row.get("huisnr"),
                    (String) row.get("postcode")
            ));
        }
        return persoonAdressen;
    }

}
