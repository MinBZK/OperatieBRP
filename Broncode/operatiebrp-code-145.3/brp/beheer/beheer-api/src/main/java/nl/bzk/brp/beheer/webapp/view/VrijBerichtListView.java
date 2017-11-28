/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;

/**
 * Lijst view voor een vrij bericht.
 */
public class VrijBerichtListView implements VrijBerichtView {


    private final VrijBericht basisObject;

    /**
     * Constructor.
     *
     * @param basisObject
     *            vrij bericht
     */
    public VrijBerichtListView(final VrijBericht basisObject) {
        this.basisObject = basisObject;
    }

    /**
     * Geeft persoon.
     * @return Vrij bericht
     */
    public VrijBericht getVrijBericht() {
        return basisObject;
    }
}
