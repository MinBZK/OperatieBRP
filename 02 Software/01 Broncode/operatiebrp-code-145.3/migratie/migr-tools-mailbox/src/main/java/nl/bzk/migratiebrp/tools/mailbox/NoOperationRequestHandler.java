/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.spd.NoOperationConfirmation;
import nl.bzk.migratiebrp.voisc.spd.Operation;

/**
 * Handles NoOperationRequests.
 */
final class NoOperationRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int ERRORCODE_OVERIG = 1205;

    /**
     * Handles a NoOperationRequest.
     * @param response Operation.Builder object to which the appropriate response is added
     */
    void handleRequest(final Operation.Builder response) {
        LOGGER.debug("Receiving NoOperationRequest");
        response.add(new NoOperationConfirmation(ERRORCODE_OVERIG));
    }
}
