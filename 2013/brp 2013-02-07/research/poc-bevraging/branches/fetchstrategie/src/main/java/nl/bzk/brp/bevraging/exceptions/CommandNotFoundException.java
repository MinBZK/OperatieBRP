/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.exceptions;

/**
 * Exceptie gegooid in het geval dat een {@link org.apache.commons.chain.Command} niet kan worden gevonden.
 * Dit kan vanuit het opzoeken in een {@link org.springframework.context.ApplicationContext} of een {@link org.apache.commons.chain.Catalog}.
 *
 * @see org.apache.commons.chain.Catalog#getCommand(String)
 * @see org.springframework.context.ApplicationContext#getBean(String)
 */
public class CommandNotFoundException extends CommandException {
    public CommandNotFoundException(String name) {
        super("No command named '" + name + "' is defined");
    }
}
