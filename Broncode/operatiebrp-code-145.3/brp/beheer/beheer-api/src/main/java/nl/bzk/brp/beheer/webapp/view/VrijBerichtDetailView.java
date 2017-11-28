/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;

/**
 * Detail view voor vrij bericht.
 */
public class VrijBerichtDetailView implements VrijBerichtView {

    private final VrijBericht vrijBericht;

    /**
     * Constructor
     * @param vrijBericht een vrij bericht domein object
     */
    public VrijBerichtDetailView(final VrijBericht vrijBericht) {

        this.vrijBericht = vrijBericht;
    }

    public VrijBericht getVrijBericht() {
        return vrijBericht;
    }
}
