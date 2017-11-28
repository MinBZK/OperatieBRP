/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.service;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;

/**
 * Interface die een client naar de vrij bericht web service beschrijft.
 */
public interface VrijBerichtClientService {

    /**
     * Verstuur een vrij bericht.
     * @param vrijBericht het vrije bericht dat verstuurd moet worden
     * @param ontvangendePartijCode code van de ontvangende partij
     */
    void verstuurVrijBericht(VrijBericht vrijBericht, String ontvangendePartijCode) throws VrijBerichtClientException;

    /**
     * Exceptie die gegooid wordt bij foutief versturen van een vrij bericht.
     */
    class VrijBerichtClientException extends Exception {
        private static final long serialVersionUID = 2032718654207355752L;

        /**
         * Constructor.
         * @param message foutmelding
         */
        VrijBerichtClientException(final String message) {
            super(message);
        }

        /**
         * Constructor.
         * @param message foutmelding
         * @param cause de oorzaak
         */
        VrijBerichtClientException(final String message, final Throwable cause) {
            super(message);
        }
    }
}
