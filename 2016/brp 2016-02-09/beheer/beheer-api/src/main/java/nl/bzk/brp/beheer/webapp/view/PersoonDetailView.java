/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;

/**
 * Detail view van de persoon.
 */
public class PersoonDetailView implements PersoonView {

    private final PersoonHisVolledig persoon;
    /**
     * Maakt view aan voor detail view persoon.
     * @param persoon persoon waarvoor detail view moet worden gemaakt
     */
    public PersoonDetailView(final PersoonHisVolledig persoon) {
        this.persoon = persoon;
    }

    /**
     * Geef persoon terug.
     * @return de persoon
     */
    public final PersoonHisVolledig getPersoon() {
        return persoon;
    }
}
