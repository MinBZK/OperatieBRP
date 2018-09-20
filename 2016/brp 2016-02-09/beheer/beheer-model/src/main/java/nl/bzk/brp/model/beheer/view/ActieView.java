/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;

/**
 * Actie view.
 */
public final class ActieView {

    private final Actie actie;
    private final List<PersoonView> personen = new ArrayList<>();
    private final List<RelatieView> relaties = new ArrayList<>();
    private final List<OnderzoekView> onderzoeken = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param actie actie
     */
    public ActieView(final Actie actie) {
        this.actie = actie;
    }

    /**
     * Geef de view voor het gegeven persoon.
     *
     * @param persoon persoon
     * @return view
     */
    public PersoonView getView(final PersoonHisVolledig persoon) {
        for (final PersoonView persoonView : personen) {
            if (persoonView.isViewVoor(persoon)) {
                return persoonView;
            }
        }

        final PersoonView persoonView = new PersoonView(persoon);
        personen.add(persoonView);
        return persoonView;
    }

    /**
     * Geef de view voor het gegeven relatie.
     *
     * @param relatie relatie
     * @return view
     */
    public RelatieView getView(final RelatieHisVolledig relatie) {
        for (final RelatieView view : relaties) {
            if (view.isViewVoor(relatie)) {
                return view;
            }
        }

        final RelatieView view = new RelatieView(relatie);
        relaties.add(view);
        return view;
    }

    /**
     * Geef de view voor het gegeven onderzoek.
     *
     * @param onderzoek onderzoek
     * @return view
     */
    public OnderzoekView getView(final OnderzoekHisVolledig onderzoek) {
        for (final OnderzoekView view : onderzoeken) {
            if (view.isViewVoor(onderzoek)) {
                return view;
            }
        }

        final OnderzoekView view = new OnderzoekView(onderzoek);
        onderzoeken.add(view);
        return view;
    }

    /**
     * Geeft de actie terug.
     * 
     * @return De actie.
     */
    public Actie getActie() {
        return actie;
    }

    /**
     * Geef alle persoon views.
     *
     * @return persoon views
     */
    public List<PersoonView> getPersonen() {
        return personen;
    }

    /**
     * Geef alle relatie views.
     *
     * @return relatie views
     */
    public List<RelatieView> getRelaties() {
        return relaties;
    }

    /**
     * Geef alle onderzoek views.
     *
     * @return onderzoek views
     */
    public List<OnderzoekView> getOnderzoeken() {
        return onderzoeken;
    }

}
