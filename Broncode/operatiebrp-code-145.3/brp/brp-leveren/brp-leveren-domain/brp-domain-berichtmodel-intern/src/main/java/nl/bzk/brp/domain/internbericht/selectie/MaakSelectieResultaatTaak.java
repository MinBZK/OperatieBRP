/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.selectie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.sql.Timestamp;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;

/**
 * MaakSelectieResultaatTaak. Value object met de benodigde informatie om voor een selectie taak
 * selectie resultaat berichten te maken voor een run.
 */
@JsonAutoDetect
public final class MaakSelectieResultaatTaak {
    private Integer selectieRunId;
    private Integer toegangLeveringsAutorisatieId;
    private Integer dienstId;
    private SoortSelectie soortSelectie;
    private Integer selectietaakId;
    private Integer datumUitvoer;
    private Integer peilmomentMaterieelResultaat;
    private Timestamp peilmomentFormeelResultaat;
    private Integer aantalPersonen;
    //indicator of taak ongeldig is. Kan alleen voorkomen bij standaard selecties
    private boolean ongeldig;

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
    public void setToegangLeveringsAutorisatieId(final Integer toegangLeveringsAutorisatieId) {
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
     * Gets soort selectie.
     * @return the soort selectie
     */
    public SoortSelectie getSoortSelectie() {
        return soortSelectie;
    }

    /**
     * Sets soort selectie.
     * @param soortSelectie the soort selectie
     */
    public void setSoortSelectie(SoortSelectie soortSelectie) {
        this.soortSelectie = soortSelectie;
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
     * Gets datum uitvoer.
     * @return the datum uitvoer
     */
    public Integer getDatumUitvoer() {
        return datumUitvoer;
    }

    /**
     * Sets datum uitvoer.
     * @param datumUitvoer the datum uitvoer
     */
    public void setDatumUitvoer(Integer datumUitvoer) {
        this.datumUitvoer = datumUitvoer;
    }

    /**
     * Gets peilmoment materieel resultaat.
     * @return the peilmoment materieel resultaat
     */
    public Integer getPeilmomentMaterieelResultaat() {
        return peilmomentMaterieelResultaat;
    }

    /**
     * Sets peilmoment materieel resultaat.
     * @param peilmomentMaterieelResultaat the peilmoment materieel resultaat
     */
    public void setPeilmomentMaterieelResultaat(Integer peilmomentMaterieelResultaat) {
        this.peilmomentMaterieelResultaat = peilmomentMaterieelResultaat;
    }

    /**
     * Gets peilmoment formeel resultaat.
     * @return the peilmoment formeel resultaat
     */
    public Timestamp getPeilmomentFormeelResultaat() {
        return peilmomentFormeelResultaat;
    }

    /**
     * Sets peilmoment formeel resultaat.
     * @param peilmomentFormeelResultaat the peilmoment formeel resultaat
     */
    public void setPeilmomentFormeelResultaat(Timestamp peilmomentFormeelResultaat) {
        this.peilmomentFormeelResultaat = peilmomentFormeelResultaat;
    }

    /**
     * Gets aantal personen.
     * @return the aantal personen
     */
    public Integer getAantalPersonen() {
        return aantalPersonen;
    }

    /**
     * Sets aantal personen.
     * @param aantalPersonen the aantal personen
     */
    public void setAantalPersonen(final Integer aantalPersonen) {
        this.aantalPersonen = aantalPersonen;
    }

    /**
     * Is ongeldig boolean.
     * @return the boolean
     */
    public boolean isOngeldig() {
        return ongeldig;
    }

    /**
     * Sets ongeldig.
     * @param ongeldig the ongeldig
     */
    public void setOngeldig(boolean ongeldig) {
        this.ongeldig = ongeldig;
    }
}
