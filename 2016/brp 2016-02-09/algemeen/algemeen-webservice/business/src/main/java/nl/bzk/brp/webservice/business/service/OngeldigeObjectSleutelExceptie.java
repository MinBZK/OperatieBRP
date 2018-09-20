/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

/**
 * Exception class die duidt op een ongeldige object sleutel.
 * Bijvoorbeeld: niet te decrypten, niet te deserializen, etc.
 *
 * @brp.bedrijfsregel BRALXXXX
 */
public class OngeldigeObjectSleutelExceptie extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Maak een nieuwe exceptie met een specifiek bericht en specifieke exceptie.
     *
     * @param message  het bericht
     * @param cause de exceptie
     */
    public OngeldigeObjectSleutelExceptie(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Maak een nieuwe exceptie met een specifiek bericht.
     *
     * @param message  het bericht
     */
    public OngeldigeObjectSleutelExceptie(final String message) {
        super(message);
    }

}
