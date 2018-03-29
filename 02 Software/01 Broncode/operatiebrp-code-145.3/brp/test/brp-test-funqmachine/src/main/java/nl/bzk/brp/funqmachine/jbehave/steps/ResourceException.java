/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;

/**
 * Exceptie die kan optreden als het resolven van een bestand niet lukt.
 */
public class ResourceException extends Exception {
    /**
     * Constructor met de oorspronkelijke exceptie.
     * @param cause de oorspronkelijke exceptie
     */
    public ResourceException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor met een omschrijving
     * @param message de omsschrijving
     */
    ResourceException(final String message) {
        super(message);
    }
}
