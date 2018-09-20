/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.service;

import java.util.List;

import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

/**
 * De context zoals deze gebruikt wordt in de stappen voor het verwerkingsproces van mutaties naar aanleiding
 * van Administratieve Handelingen.
 */
public class AdministratieveHandelingVerwerkingContext implements StappenContext {


    private AdministratieveHandelingModel huidigeAdministratieveHandeling;

    private List<Integer> betrokkenPersonenIds;

    private List<PersoonHisVolledig> betrokkenPersonenVolledig;

    private List<Persoon> betrokkenPersonen;

    public AdministratieveHandelingModel getHuidigeAdministratieveHandeling() {
        return huidigeAdministratieveHandeling;
    }

    public void setHuidigeAdministratieveHandeling(
            final AdministratieveHandelingModel huidigeAdministratieveHandeling)
    {
        this.huidigeAdministratieveHandeling = huidigeAdministratieveHandeling;
    }

    public List<Integer> getBetrokkenPersonenIds() {
        return betrokkenPersonenIds;
    }

    public void setBetrokkenPersonenIds(final List<Integer> betrokkenPersonenIds) {
        this.betrokkenPersonenIds = betrokkenPersonenIds;
    }

    public List<PersoonHisVolledig> getBetrokkenPersonenVolledig() {
        return betrokkenPersonenVolledig;
    }

    public void setBetrokkenPersonenVolledig(final List<PersoonHisVolledig> betrokkenPersonenVolledig) {
        this.betrokkenPersonenVolledig = betrokkenPersonenVolledig;
    }

    public List<Persoon> getBetrokkenPersonen() {
        return betrokkenPersonen;
    }

    public void setBetrokkenPersonen(final List<Persoon> betrokkenPersonen) {
        this.betrokkenPersonen = betrokkenPersonen;
    }

    @Override
    public Long getReferentieId() {
        if(null == huidigeAdministratieveHandeling) {
            return null;
        }
        return huidigeAdministratieveHandeling.getID();
    }
}
