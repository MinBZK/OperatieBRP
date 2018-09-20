/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import nl.bzk.brp.model.operationeel.ber.BerichtModel;

/**
 * Bericht list view.
 */
public final class BerichtListView implements BerichtView {

    private final BerichtModel bericht;

    /**
     * Constructor.
     *
     * @param bericht bericht
     */
    public BerichtListView(final BerichtModel bericht) {
        this.bericht = bericht;
    }

    /**
     * Geef bericht.
     *
     * @return bericht
     */
    public BerichtModel getBericht() {
        return bericht;
    }

}
