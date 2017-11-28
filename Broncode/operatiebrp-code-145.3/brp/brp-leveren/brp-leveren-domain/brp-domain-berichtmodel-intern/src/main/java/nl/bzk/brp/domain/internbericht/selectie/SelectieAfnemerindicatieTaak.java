/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.selectie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;

/**
 * Verzoek voor plaatsen/verwijderen afnemerindicatie binnen Selectie.
 */
@JsonAutoDetect
public class SelectieAfnemerindicatieTaak {

    private Long persoonId;
    private SoortSelectie soortSelectie;
    private Integer dienstId;
    private Integer toegangLeveringsautorisatieId;
    //gevuld voor afnemerindicatie met verzenden bericht
    private SynchronisatieBerichtGegevens synchronisatieBerichtGegevens;
    //gevuld voor afnemerindicatie met bestand
    private String bericht;
    private Integer selectieTaakId;
    private ZonedDateTime tijdstipLaatsteWijziging;
    private Integer selectieRunId;
    private Integer selectietaakDatumUitvoer;

    /**
     * Gets persoon id.
     * @return the persoon id
     */
    public Long getPersoonId() {
        return persoonId;
    }

    /**
     * Sets persoon id.
     * @param persoonId the persoon id
     */
    public void setPersoonId(Long persoonId) {
        this.persoonId = persoonId;
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
     * Gets toegang leveringsautorisatie id.
     * @return the toegang leveringsautorisatie id
     */
    public Integer getToegangLeveringsautorisatieId() {
        return toegangLeveringsautorisatieId;
    }

    /**
     * Sets toegang leveringsautorisatie id.
     * @param toegangLeveringsautorisatieId the toegang leveringsautorisatie id
     */
    public void setToegangLeveringsautorisatieId(Integer toegangLeveringsautorisatieId) {
        this.toegangLeveringsautorisatieId = toegangLeveringsautorisatieId;
    }

    /**
     * Gets synchronisatie bericht gegevens.
     * @return the synchronisatie bericht gegevens
     */
    public SynchronisatieBerichtGegevens getSynchronisatieBerichtGegevens() {
        return synchronisatieBerichtGegevens;
    }

    /**
     * Sets synchronisatie bericht gegevens.
     * @param synchronisatieBerichtGegevens the synchronisatie bericht gegevens
     */
    public void setSynchronisatieBerichtGegevens(SynchronisatieBerichtGegevens synchronisatieBerichtGegevens) {
        this.synchronisatieBerichtGegevens = synchronisatieBerichtGegevens;
    }

    /**
     * Gets selectie taak id.
     * @return the selectie taak id
     */
    public Integer getSelectieTaakId() {
        return selectieTaakId;
    }

    /**
     * Sets selectie taak id.
     * @param selectieTaakId the selectie taak id
     */
    public void setSelectieTaakId(final Integer selectieTaakId) {
        this.selectieTaakId = selectieTaakId;
    }

    /**
     * Gets bericht.
     * @return the bericht
     */
    public String getBericht() {
        return bericht;
    }

    /**
     * Sets bericht.
     * @param bericht the bericht
     */
    public void setBericht(String bericht) {
        this.bericht = bericht;
    }

    /**
     * Gets tijdstip laatste wijziging.
     * @return the tijdstip laatste wijziging
     */
    public ZonedDateTime getTijdstipLaatsteWijziging() {
        return tijdstipLaatsteWijziging;
    }

    /**
     * Sets tijdstip laatste wijziging.
     * @param tijdstipLaatsteWijziging the tijdstip laatste wijziging
     */
    public void setTijdstipLaatsteWijziging(ZonedDateTime tijdstipLaatsteWijziging) {
        this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
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
}
