/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.executor;

import nl.bzk.migratiebrp.isc.jbpm.command.CommandExecutor;

/**
 * Commando's verwerken.
 */
public interface CommandService extends CommandExecutor {

    /**
     * JMX Naam.
     */
    String JMX_NAME = "nl.bzk.migratiebrp.isc:name=COMMANDO";

}
