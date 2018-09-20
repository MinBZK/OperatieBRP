/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.correlatie;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

/**
 * In-memory (non-persistent) process correlatie store.
 */
public final class InMemoryProcessCorrelatieStore implements ProcessCorrelatieStore {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Map<String, ProcessData> STORE = new HashMap<String, ProcessData>();

    @Override
    public void bewaarProcessCorrelatie(final String messageId, final ProcessData processData) {
        LOG.info("bewaarProcessCorrelatie(messageId={}, processData={})", messageId, processData);
        STORE.put(messageId, processData);
    }

    @Override
    public ProcessData zoekProcessCorrelatie(final String messageId) {
        LOG.info("zoekProcessCorrelatie(messageId={})", messageId);
        return STORE.get(messageId);
    }

}
