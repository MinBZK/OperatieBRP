/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;

/**
 * Exceptie kan gegooid worden als er tijdens het uitvoeren van de steps een andere exceptie optreedt.
 */
public class StepException extends RuntimeException {
    StepException(final String message, final Throwable cause) {
        super(message, cause);
    }

    StepException(final Throwable cause) {
        super(cause);
    }
}
