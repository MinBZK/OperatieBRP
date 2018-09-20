/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.exceptions;

/**
 * Basis exceptie voor fouten met {@link org.apache.commons.chain.Command}s.
 */
public class CommandException extends RuntimeException {

    /**
     * Exceptie met foutmelding.
     * @param message de foutmelding
     */
    public CommandException(final String message) {
        super(message);
    }

    /**
     * Exceptie met foutmelding en fout.
     * @param message de foutmelding
     * @param e de fout
     */
    public CommandException(final String message, final Throwable e) {
        super(message, e);
    }

    /**
     * Exceptie met fout.
     * @param e de fout
     */
    public CommandException(final Throwable e) {
        super(e);
    }
}
