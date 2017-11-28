/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.selectie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;

/**
 * SelectieAutorisatieBericht.
 */
@JsonAutoDetect
public final class SelectieAutorisatieBericht {

    private Integer toegangLeveringsAutorisatieId;
    private Integer dienstId;
    private Integer selectietaakId;
    private HistorieVorm historieVorm;
    private ZonedDateTime peilmomentFormeel;
    private Integer peilmomentMaterieel;
    private boolean lijstGebruiken = false;

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
     * @param soortDienst the soort dienst
     */
    public void setDienstId(Integer soortDienst) {
        this.dienstId = soortDienst;
    }

    /**
     * Sets selectietaak id.
     * @param selectietaakId the selectietaak id
     */
    public void setSelectietaakId(final Integer selectietaakId) {
        this.selectietaakId = selectietaakId;
    }

    /**
     * Gets selectietaak id.
     * @return the selectietaak id
     */
    public Integer getSelectietaakId() {
        return selectietaakId;
    }

    /**
     * Gets historie vorm.
     * @return the historie vorm
     */
    public HistorieVorm getHistorieVorm() {
        return historieVorm;
    }

    /**
     * Sets historie vorm.
     * @param historieVorm the historie vorm
     */
    public void setHistorieVorm(HistorieVorm historieVorm) {
        this.historieVorm = historieVorm;
    }

    /**
     * Gets peilmoment formeel.
     * @return the peilmoment formeel
     */
    public ZonedDateTime getPeilmomentFormeel() {
        return peilmomentFormeel;
    }

    /**
     * Sets peilmoment formeel.
     * @param peilmomentFormeel the peilmoment formeel
     */
    public void setPeilmomentFormeel(final ZonedDateTime peilmomentFormeel) {
        this.peilmomentFormeel = peilmomentFormeel;
    }

    /**
     * Gets peilmoment materieel.
     * @return the peilmoment materieel
     */
    public Integer getPeilmomentMaterieel() {
        return peilmomentMaterieel;
    }

    /**
     * Sets peilmoment materieel.
     * @param peilmomentMaterieel the peilmoment materieel
     */
    public void setPeilmomentMaterieel(Integer peilmomentMaterieel) {
        this.peilmomentMaterieel = peilmomentMaterieel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SelectieAutorisatieBericht that = (SelectieAutorisatieBericht) o;

        return selectietaakId.equals(that.selectietaakId) && toegangLeveringsAutorisatieId.equals(that.toegangLeveringsAutorisatieId) && dienstId
                .equals(that.dienstId);
    }

    public boolean isLijstGebruiken() {
        return lijstGebruiken;
    }

    public void setLijstGebruiken(boolean lijstGebruiken) {
        this.lijstGebruiken = lijstGebruiken;
    }

    @Override
    public int hashCode() {
        int result = toegangLeveringsAutorisatieId.hashCode();
        result = 31 * result + dienstId.hashCode();
        result = 31 * result + selectietaakId.hashCode();
        return result;
    }
}
