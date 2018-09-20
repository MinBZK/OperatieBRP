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


/** test object, 1-1 op de database tabel om data te verifieren. */
public final class GeslachtsnaamcomponentDto {

    private static final String SQL_GET_GESLACHTSNAMENCOMPONENT_LEVELA_BYPERS_ID =
        "select p.id, p.pers, p.volgnr, p.voorvoegsel, p.scheidingsteken, p.naam, pr.code predikaat, a.code titel "
            + " from kern.persgeslnaamcomp p, kern.predikaat pr, kern.adellijketitel a "
            + " where p.pers = ? and p.predikaat = pr.id and p.adellijketitel = a.id";

    private Integer id;
    private Integer persId;
    private Integer volgNr;
    private String  voorVoegsel;
    private String  scheidingsteken;
    private String  naam;
    private String  predikaatCode;
    private String  adelijkeTitelCode;

    /** default constructor. */
    private GeslachtsnaamcomponentDto() {
    }

    /**
     * @param simpleJdbcTemplate
     * @param persId
     * @return
     */
    public static List<GeslachtsnaamcomponentDto> getGeslachtsnaamcomponentA(
        final SimpleJdbcTemplate simpleJdbcTemplate, final Integer persId)
    {
        List<GeslachtsnaamcomponentDto> componenenten = new ArrayList<GeslachtsnaamcomponentDto>();

        List<Map<String, Object>> rows =
            simpleJdbcTemplate.queryForList(SQL_GET_GESLACHTSNAMENCOMPONENT_LEVELA_BYPERS_ID, persId);
        for (Map<String, Object> row : rows) {
            GeslachtsnaamcomponentDto dto = new GeslachtsnaamcomponentDto();
            dto.id = (Integer) row.get("id");
            dto.persId = (Integer) row.get("pers");
            dto.volgNr = (Integer) row.get("volgnr");
            dto.voorVoegsel = (String) row.get("voorvoegsel");
            dto.scheidingsteken = (String) row.get("scheidingsteken");
            dto.naam = (String) row.get("naam");
            dto.predikaatCode = (String) row.get("predikaat");
            dto.adelijkeTitelCode = (String) row.get("titel");
            componenenten.add(dto);
        }
        return componenenten;
    }

    /** @return the persId */
    public Integer getPersId() {
        return persId;
    }

    /** @return the id */
    public Integer getId() {
        return id;
    }

    /** @return the volgNr */
    public Integer getVolgNr() {
        return volgNr;
    }

    /** @return the voorVoegsel */
    public String getVoorVoegsel() {
        return voorVoegsel;
    }

    /** @return the scheidingsteken */
    public String getScheidingsteken() {
        return scheidingsteken;
    }

    /** @return the naam */
    public String getNaam() {
        return naam;
    }

    /** @return the predikaatCode */
    public String getPredikaatCode() {
        return predikaatCode;
    }

    /** @return the adelijkeTitelCode */
    public String getAdelijkeTitelCode() {
        return adelijkeTitelCode;
    }

}
