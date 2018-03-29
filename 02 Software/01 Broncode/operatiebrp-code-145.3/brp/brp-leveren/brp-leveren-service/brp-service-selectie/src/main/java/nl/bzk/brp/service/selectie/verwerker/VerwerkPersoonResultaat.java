/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Bevat het resultaat van de "maak bericht logica".
 */
final class VerwerkPersoonResultaat {
    private Integer selectieTaakId;
    private Integer selectieRunId;
    private Persoonslijst persoonslijst;
    private Autorisatiebundel autorisatiebundel;
    //gevuld als soortselectie afnemerindicatie selectie
    private SynchronisatieBerichtGegevens synchronisatieBerichtGegevens;
    //gevuld als soortselectie standaard of model selectie
    private String persoonFragment;

    /**
     * Gets xml.
     * @return the xml
     */
    public String getPersoonFragment() {
        return persoonFragment;
    }

    /**
     * Sets xml.
     * @param persoonFragment het persoonFragment
     */
    public void setPersoonFragment(String persoonFragment) {
        this.persoonFragment = persoonFragment;
    }

    /**
     * Gets persoonslijst.
     * @return the persoonslijst
     */
    public Persoonslijst getPersoonslijst() {
        return persoonslijst;
    }

    /**
     * Sets persoonslijst.
     * @param persoonslijst the persoonslijst
     */
    public void setPersoonslijst(final Persoonslijst persoonslijst) {
        this.persoonslijst = persoonslijst;
    }

    /**
     * Gets selectie autorisatiebundel.
     * @return the selectie autorisatiebundel
     */
    public Autorisatiebundel getAutorisatiebundel() {
        return autorisatiebundel;
    }

    /**
     * Sets selectie autorisatiebundel.
     * @param selectieAutorisatiebundel the selectie autorisatiebundel
     */
    public void setAutorisatiebundel(final Autorisatiebundel selectieAutorisatiebundel) {
        this.autorisatiebundel = selectieAutorisatiebundel;
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
     * Gets dienst.
     * @return the dienst
     */
    public Dienst getDienst() {
        return autorisatiebundel.getDienst();
    }

    /**
     * Gets toegang leverinsautorisatie id.
     * @return the toegang leverinsautorisatie id
     */
    public Integer getToegangLeverinsautorisatieId() {
        return autorisatiebundel.getToegangLeveringsautorisatie().getId();
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
     * @param selectieTaakId selectieTaakId
     */
    public void setSelectieTaakId(Integer selectieTaakId) {
        this.selectieTaakId = selectieTaakId;
    }

    /**
     * Gets selectie taak id.
     * @return the selectie taak id
     */
    public Integer getSelectieTaakId() {
        return selectieTaakId;
    }
}
