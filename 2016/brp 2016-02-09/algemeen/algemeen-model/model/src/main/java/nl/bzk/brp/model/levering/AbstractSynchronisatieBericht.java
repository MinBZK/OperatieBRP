/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.levering;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.MeldingBericht;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;


/**
 * Dit is het abstracte supertype voor de containers van levering berichten die gebruikt worden voor het leveren van mutaties. Hierbij zijn twee types:
 * kennisgevingberichten en vulberichten.
 */
public abstract class AbstractSynchronisatieBericht implements SynchronisatieBericht {

    private BerichtStuurgegevensGroepBericht       stuurgegevens;
    private BerichtParametersGroepBericht          berichtParameters;
    private AdministratieveHandelingSynchronisatie administratieveHandeling;

    private List<MeldingBericht> meldingen = new ArrayList<>();

    /**
     * Instantieert een nieuw Abstract levering bericht.
     *
     * @param administratieveHandeling administratieve handeling
     */
    protected AbstractSynchronisatieBericht(final AdministratieveHandelingSynchronisatie administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    @Override
    public BerichtStuurgegevensGroepBericht getStuurgegevens() {
        return stuurgegevens;
    }

    @Override
    public void setStuurgegevens(final BerichtStuurgegevensGroepBericht stuurgegevens) {
        this.stuurgegevens = stuurgegevens;
    }

    @Override
    public AdministratieveHandelingSynchronisatie getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    @Override
    public void setAdministratieveHandeling(final AdministratieveHandelingSynchronisatie administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    @Override
    public BerichtParametersGroepBericht getBerichtParameters() {
        return berichtParameters;
    }

    @Override
    public void setBerichtParameters(final BerichtParametersGroepBericht berichtParameters) {
        this.berichtParameters = berichtParameters;
    }

    @Override
    public List<MeldingBericht> getMeldingen() {
        return meldingen;
    }

    @Override
    public void setMeldingen(final List<MeldingBericht> meldingen) {
        this.meldingen = meldingen;
    }

    @Override
    public void addMelding(final MeldingBericht melding) {
        meldingen.add(melding);
    }

    @Override
    public boolean heeftMeldingenVoorLeveren() {
        return meldingen != null && !meldingen.isEmpty();
    }

    /**
     * Voegt een persoon toe aan de administratieve handeling in dit synchronisatiebericht.
     *
     * @param persoonHisVolledigView de view om toe te voegen
     * @return de index waarop de view is toegevoegd.
     */
    @Override
    public int addPersoon(final PersoonHisVolledigView persoonHisVolledigView) {
        final List<PersoonHisVolledigView> views = administratieveHandeling.getBijgehoudenPersonen();

        views.add(persoonHisVolledigView);
        administratieveHandeling.setBijgehoudenPersonen(views);

        return views.size();
    }

    @Override
    public boolean heeftPersonen() {
        return !administratieveHandeling.getBijgehoudenPersonen().isEmpty();
    }
}
