/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.app;

import nl.bzk.brp.serialisatie.notificator.exceptions.CommandException;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
* Applicatie die de een {@link Command} uitvoert.
*/
public class CommandRunnerApp extends AbstractApp {

    /**
     *  Constructor.
     * @param command het command dat deze runner uitvoert
     */
    public CommandRunnerApp(final Command command) {
        super(command);
    }

    @Override
    public final void run(final Context context) {

        final Command command = getCommand();
        try {
            command.execute(context);
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}
