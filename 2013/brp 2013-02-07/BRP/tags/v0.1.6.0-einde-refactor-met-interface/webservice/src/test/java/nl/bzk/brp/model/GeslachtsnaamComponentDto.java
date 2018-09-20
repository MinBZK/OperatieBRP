/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.model.gedeeld.AdellijkeTitel;
import nl.bzk.brp.model.gedeeld.Predikaat;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


/**
 * test object, 1-1 op de database tabel om data te verifieren.
 *
 */
public final class GeslachtsnaamComponentDto {

    private static final String SQL_GET_GESLACHTSNAMENCOMPONENT_LEVELA_BYPERS_ID = "select id, pers, volgnr, "
            + "voorvoegsel, scheidingsteken, naam, predikaat, adellijketitel from kern.persgeslnaamcomp where pers = ?";

    private Long                id;
    private Long                persId;
    private Integer             volgNr;
    private String              voorVoegsel;
    private String              scheidingsteken;
    private String              naam;
    private Integer             predikaatEnum;
    private Integer             adelijkeTitelEnum;

    /**
     * default constructor.
     */
    private GeslachtsnaamComponentDto() {
    }

    /**
     *
     * @param simpleJdbcTemplate
     * @param persId
     * @return
     */
    public static List<GeslachtsnaamComponentDto> getGeslachtsnaamComponentA(
            final SimpleJdbcTemplate simpleJdbcTemplate, final Long persId)
    {
        List<GeslachtsnaamComponentDto> componenenten = new ArrayList<GeslachtsnaamComponentDto>();

        List<Map<String, Object>> rows =
            simpleJdbcTemplate.queryForList(SQL_GET_GESLACHTSNAMENCOMPONENT_LEVELA_BYPERS_ID, persId);
        for (Map<String, Object> row : rows) {
            GeslachtsnaamComponentDto dto = new GeslachtsnaamComponentDto();
            dto.id = (Long) row.get("id");
            dto.persId = (Long) row.get("pers");
            dto.volgNr = (Integer) row.get("volgnr");
            dto.voorVoegsel = (String) row.get("voorvoegsel");
            dto.scheidingsteken = (String) row.get("scheidingsteken");
            dto.naam = (String) row.get("naam");
            dto.predikaatEnum = (Integer) row.get("predikaat");
            dto.adelijkeTitelEnum = (Integer) row.get("adellijketitel");
            componenenten.add(dto);
        }
        return componenenten;
    }

    /**
     * @return the persId
     */
    public Long getPersId() {
        return persId;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the volgNr
     */
    public Integer getVolgNr() {
        return volgNr;
    }

    /**
     * @return the voorVoegsel
     */
    public String getVoorVoegsel() {
        return voorVoegsel;
    }

    /**
     * @return the scheidingsteken
     */
    public String getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * @return the naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return the predikaatEnum
     */
    public Integer getPredikaatEnum() {
        return predikaatEnum;
    }

    /**
     * Retourneert het predikaat.
     *
     * @return Het predikaat.
     */
    public Predikaat getPredikaat() {
        Predikaat retval = null;
        if (null != predikaatEnum) {
            Predikaat[] allewaarden = Predikaat.values();
            // sorry, ga geen outOfIndex checken hier in een test omgeving
            return allewaarden[predikaatEnum];
        }
        return retval;
    }

    /**
     * @return the adelijkeTitelEnum
     */
    public Integer getAdelijkeTitelEnum() {
        return adelijkeTitelEnum;
    }

    /**
     * Retourneert de adelijke titel.
     *
     * @return De adelijke titel.
     */
    public AdellijkeTitel getAdelijkeTitel() {
        AdellijkeTitel retval = null;
        if (null != adelijkeTitelEnum) {
            AdellijkeTitel[] alleWaarden = AdellijkeTitel.values();
            // sorry, ga geen outOfIndex checken hier in een test omgeving
            retval = alleWaarden[adelijkeTitelEnum];
        }
        return retval;
    }

}
