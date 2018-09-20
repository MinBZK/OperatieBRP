/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;

/**
 * Onderzoek view.
 */
public final class OnderzoekView extends ObjectView<OnderzoekHisVolledig> {

    private final List<ObjectView<PartijOnderzoekHisVolledig>> partijen = new ArrayList<>();
    private final List<ObjectView<PersoonOnderzoekHisVolledig>> personen = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param basisObject onderzoek
     */
    public OnderzoekView(final OnderzoekHisVolledig basisObject) {
        super(basisObject);
    }

    /**
     * Geef de view voor het gegeven partij.
     *
     * @param partij partij
     * @return view
     */
    public ObjectView<?> getView(final PartijOnderzoekHisVolledig partij) {
        return ViewUtil.geefViewVoor(partijen, partij);
    }

    /**
     * Geef de view voor het gegeven persoon.
     *
     * @param persoon persoon
     * @return view
     */
    public ObjectView<?> getView(final PersoonOnderzoekHisVolledig persoon) {
        return ViewUtil.geefViewVoor(personen, persoon);
    }

    /**
     * Geef alle partij views.
     *
     * @return partij views
     */
    public List<ObjectView<PartijOnderzoekHisVolledig>> getPartijen() {
        return partijen;
    }

    /**
     * Geef alle persoon views.
     *
     * @return persoon views
     */
    public List<ObjectView<PersoonOnderzoekHisVolledig>> getPersonen() {
        return personen;
    }
}
