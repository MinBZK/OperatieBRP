/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.selectie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.collect.Maps;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SelectieFragmentSchrijfTaak.
 */
@JsonAutoDetect
public final class SelectieFragmentSchrijfBericht {
    private Integer selectieRunId;
    private Integer selectietaakDatumUitvoer;
    private Integer toegangLeveringsAutorisatieId;
    private Integer dienstId;
    private Integer selectietaakId;
    private List<String> berichten = new ArrayList<>();
    private Map<Long, ZonedDateTime> protocolleringPersonen = Maps.newHashMap();

    /**
     * Gets toegang leverings autorisatie id.
     * @return the toegang leverings autorisatie id
     */
    public Integer getToegangLeveringsAutorisatieId() {
        return toegangLeveringsAutorisatieId;
    }

    /**
     * Sets toegang leverings autorisatie id.
     * @param toegangLeveringsAutorisatieId the toegang leverings autorisatie id
     */
    public void setToegangLeveringsAutorisatieId(Integer toegangLeveringsAutorisatieId) {
        this.toegangLeveringsAutorisatieId = toegangLeveringsAutorisatieId;
    }

    /**
     * Gets dienst id.
     * @return the dienst id
     */
    public Integer getDienstId() {
        return dienstId;
    }

    /**
     * Sets dienst id.
     * @param dienstId the dienst id
     */
    public void setDienstId(Integer dienstId) {
        this.dienstId = dienstId;
    }

    /**
     * Gets berichten.
     * @return the berichten
     */
    public List<String> getBerichten() {
        return berichten;
    }

    /**
     * Sets berichten.
     * @param berichten the berichten
     */
    public void setBerichten(List<String> berichten) {
        this.berichten = berichten;
    }

    /**
     * Gets selectie run id.
     * @return the selectie run id
     */
    public Integer getSelectieRunId() {
        return selectieRunId;
    }

    /**
     * Sets selectie run id.
     * @param selectieRunId the selectie run id
     */
    public void setSelectieRunId(Integer selectieRunId) {
        this.selectieRunId = selectieRunId;
    }

    /**
     * Gets selectietaak id.
     * @return the selectietaak id
     */
    public Integer getSelectietaakId() {
        return selectietaakId;
    }

    /**
     * Sets selectietaak id.
     * @param selectietaakId the selectietaak id
     */
    public void setSelectietaakId(Integer selectietaakId) {
        this.selectietaakId = selectietaakId;
    }

    /**
     * Gets selectietaak datum uitvoer.
     * @return the selectietaak datum uitvoer
     */
    public Integer getSelectietaakDatumUitvoer() {
        return selectietaakDatumUitvoer;
    }

    /**
     * Sets selectietaak datum uitvoer.
     * @param selectietaakDatumUitvoer the selectietaak datum uitvoer
     */
    public void setSelectietaakDatumUitvoer(Integer selectietaakDatumUitvoer) {
        this.selectietaakDatumUitvoer = selectietaakDatumUitvoer;
    }

    /**
     * Sets personen.
     * @param protocolleringPersonen the personen
     */
    public void setProtocolleringPersonen(final Map<Long, ZonedDateTime> protocolleringPersonen) {
        this.protocolleringPersonen = protocolleringPersonen;
    }

    /**
     * Gets personen.
     * @return the personen
     */
    public Map<Long, ZonedDateTime> getProtocolleringPersonen() {
        return protocolleringPersonen;
    }
}
