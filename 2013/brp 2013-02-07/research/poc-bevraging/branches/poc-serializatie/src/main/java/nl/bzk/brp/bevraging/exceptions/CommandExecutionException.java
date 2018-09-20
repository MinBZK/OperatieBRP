/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.exceptions;

/**
 * Exceptie gegooid in het geval van fouten in het uitvoeren van een {@link org.apache.commons.chain.Command}.
 */
public class CommandExecutionException extends CommandException {

    /**
     * Exceptie met fout.
     * @param e de fout
     */
    public CommandExecutionException(final Exception e) {
        super(e);
    }
}
