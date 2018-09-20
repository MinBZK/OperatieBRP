/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.app;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 * Abstracte klasse voor een applicatie.
 */
public abstract class AbstractApp {

    /** Het uit te voeren commando. */
    private final Command command;

    /**
     * Maak de applicatie.
     * @param command het command dat deze runner uitvoerd
     */
    public AbstractApp(final Command command) {
        this.command = command;
    }

    /**
     * Voer de applicatie uit.
     *
     * @param context gebruikt door de applicatie als informatiebron
     */
    public abstract void run(Context context);

    /**
     * Geef het commando dat de applicatie uit moet voeren.
     * @return command
     */
    public final Command getCommand() {
        return command;
    }
}
