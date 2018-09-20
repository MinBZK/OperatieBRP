/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.brp;

/**
 * Exception welke gegooid wordt als de partij (van een bericht) niet gevonden kan worden in de database.
 */

public class OnbekendePartijException extends BerichtException {

    private static final long serialVersionUID = -3690591247035318057L;

    /**
     * Constrcutor.
     *
     * @param berichtId het Id van het bericht dat de exception veroorzaakt heeft
     */
    public OnbekendePartijException(final long berichtId) {
        super(berichtId);
    }
}
