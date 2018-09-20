/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.verwerking.service;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;


/**
 * Protocollering fout afhandeling om fouten af te handelen.
 */
@Service
public class ProtocolleringFoutAfhandeling implements ErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final void handleError(final Throwable t) {
        LOGGER.error("Fout bij het afhandelen van een protocollering bericht.", t);
    }
}
