/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.stappen;

/**
 * Een wrapper voor het inkomende en uitgaande bericht ID.
 */
public class BerichtenIds {

    private final Long ingaandBerichtId;
    private final Long uitgaandBerichtId;

    /**
     * Creeer een wrapper voor het inkomende en uitgaande bericht ID.
     *
     * @param ingaandBerichtId Het id van het inkomende bericht.
     * @param uitgaandBerichtId Het id van het uitgaande bericht.
     */
    public BerichtenIds(final Long ingaandBerichtId, final Long uitgaandBerichtId) {
        this.ingaandBerichtId = ingaandBerichtId;
        this.uitgaandBerichtId = uitgaandBerichtId;
    }

    public Long getIngaandBerichtId() {
        return ingaandBerichtId;
    }

    public Long getUitgaandBerichtId() {
        return uitgaandBerichtId;
    }

}
