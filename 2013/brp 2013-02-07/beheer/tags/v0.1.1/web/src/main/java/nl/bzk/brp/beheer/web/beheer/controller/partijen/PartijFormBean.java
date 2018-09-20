/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.controller.partijen;

import javax.validation.Valid;

import nl.bzk.brp.beheer.model.Partij;

/**
 * Form bean voor partij.
 *
 */
public class PartijFormBean {

    @Valid
    private Partij  partij;

    private boolean leesModus;

    /**
     * Constructor.
     */
    public PartijFormBean() {
        partij = new Partij(null);
        //TODO tijdelijk hardcoded
        partij.setPartijStatushHis("A");
        partij.setGemStatusHis("A");

        leesModus = false;
    }

    public Partij getPartij() {
        return partij;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    public boolean isLeesModus() {
        return leesModus;
    }

    public void setLeesModus(final boolean leesModus) {
        this.leesModus = leesModus;
    }
}
