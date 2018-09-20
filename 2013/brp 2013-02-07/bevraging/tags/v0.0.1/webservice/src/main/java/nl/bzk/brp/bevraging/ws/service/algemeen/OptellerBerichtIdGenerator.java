/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.algemeen;

import nl.bzk.brp.bevraging.ws.service.BerichtIdGenerator;

/**
 * Eenvoudige implementatie van de {@link BerichtIdGenerator} interface die gewoon intern een teller bij houdt en deze
 * na elke bevraging ophoogt.
 *
 * Deze implementatie is niet thread-safe. Daarnaast dient er, ter voorkoming van dubbele ids, ook altijd slechts een
 * enkele instance beschikbaar te zijn. Ook is deze generator niet 'persistant', dus na elke (her)start begint de
 * teller weer van vooraf aan.
 */
public class OptellerBerichtIdGenerator implements BerichtIdGenerator {

    private long teller = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public final long volgendeId() {
        return teller++;
    }

}
