/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.brp;

/**
 * Een fout in de web laag. {@inheritDoc}
 */
public class BerichtException extends Exception {

    private static final long serialVersionUID = -1144552498641177934L;

    private final long        berichtId;

    /**
     * Constructor.
     *
     * @param berichtId Het berichtId van het bericht dat de {@link BerichtException} veroorzaakt heeft.
     */

    public BerichtException(final long berichtId) {
        this.berichtId = berichtId;
    }

    public long getBerichtId() {
        return berichtId;
    }

}
