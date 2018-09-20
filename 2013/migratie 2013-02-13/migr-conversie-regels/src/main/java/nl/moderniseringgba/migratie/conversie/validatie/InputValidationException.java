/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.validatie;

/**
 * Wanneer de input niet voldoet aan de gestelde eisen dan wordt deze exceptie gegooid.
 * 
 */
public final class InputValidationException extends Exception {

    private static final long serialVersionUID = -2393652745313083179L;

    /**
     * Maakt een InputValidationException object.
     * 
     * @param message
     *            de omschrijving van de exception
     * @param cause
     *            de reden
     */
    public InputValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Maakt een InputValidationException object.
     * 
     * @param message
     *            de omschrijving van de exception
     */
    public InputValidationException(final String message) {
        super(message);
    }

    /**
     * Maakt een InputValidationException object.
     * 
     * @param message
     *            de omschrijving van de exception
     * @param id
     *            het id van de bron waarin de InputValidationException optreedt
     */
    public InputValidationException(final String message, final String id) {
        super("Id=" + id + ". " + message);
    }
}
