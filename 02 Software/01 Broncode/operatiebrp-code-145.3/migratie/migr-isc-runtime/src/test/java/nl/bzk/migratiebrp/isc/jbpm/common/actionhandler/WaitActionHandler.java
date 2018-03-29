/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actionhandler;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * Does nothing (not even a signal to continue to next node).
 */
public class WaitActionHandler implements ActionHandler {

    private static final long serialVersionUID = 1L;

    @Override
    public void execute(final ExecutionContext executionContext) throws Exception {
        // Do nothing
    }

}
