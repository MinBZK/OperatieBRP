/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;

/**
 * Bericht list view.
 */
public final class BerichtListView implements BerichtView {

    private final Bericht bericht;

    /**
     * Constructor.
     *
     * @param bericht
     *            bericht
     */
    public BerichtListView(final Bericht bericht) {
        this.bericht = bericht;
    }

    /**
     * Geef bericht.
     *
     * @return bericht
     */
    public Bericht getBericht() {
        return bericht;
    }

}
