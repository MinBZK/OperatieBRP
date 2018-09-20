/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;

/**
 * Relatie view.
 */
public final class RelatieView extends ObjectView<RelatieHisVolledig> {

    private final List<ObjectView<BetrokkenheidHisVolledig>> betrokkenheden = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param basisObject relatie
     */
    public RelatieView(final RelatieHisVolledig basisObject) {
        super(basisObject);
    }

    /**
     * Geef de view voor de gegeven betrokkenheid.
     *
     * @param betrokkenheid betrokkenheid
     * @return view
     */
    public ObjectView<?> getView(final BetrokkenheidHisVolledig betrokkenheid) {
        return ViewUtil.geefViewVoor(betrokkenheden, betrokkenheid);
    }

    /**
     * Geef alle betrokkenheid views.
     *
     * @return betrokkenheid views
     */
    public List<ObjectView<BetrokkenheidHisVolledig>> getBetrokkenheden() {
        return betrokkenheden;
    }
}
