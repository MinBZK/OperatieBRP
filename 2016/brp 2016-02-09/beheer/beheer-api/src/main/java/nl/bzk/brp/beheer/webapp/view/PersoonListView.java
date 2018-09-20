/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import nl.bzk.brp.model.operationeel.kern.PersoonModel;

/**
 * lijst view van persoon.
 */
public class PersoonListView implements PersoonView {

    private final PersoonModel persoon;

    /**
     * Constructor voor nieuw PersoonView.
     * @param persoon persoon waarvoor view moet worden gemaakt
     */
    public PersoonListView(final PersoonModel persoon) {
        this.persoon = persoon;
    }

    /**
     * Geef persoon terug.
     * @return de persoon
     */
    public final PersoonModel getPersoon() {
        return persoon;
    }
}
