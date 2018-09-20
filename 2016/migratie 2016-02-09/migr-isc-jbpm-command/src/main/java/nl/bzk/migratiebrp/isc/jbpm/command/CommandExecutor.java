/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command;

import nl.bzk.migratiebrp.isc.jbpm.command.exception.CommandException;

/**
 * Command executor.
 */
public interface CommandExecutor {

    /**
     * Voert het command uit.
     *
     * @param command
     *            Het uit te voeren remote command.
     * @param <T>
     *            resultaat type
     * @return Resultaat van het uitgevoerde remote command.
     * @throws CommandException
     *             In het geval er bij het uitvoeren een exceptie optreedt.
     */
    <T> T executeCommand(final Command<T> command) throws CommandException;
}
