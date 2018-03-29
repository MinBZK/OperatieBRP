/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actionhandler;

import java.util.Map;

/**
 * Outbound handler.
 */
public interface OutboundHandler {

    /**
     * Handle the outbound message.
     * @param serviceName service to send the outbound message to
     * @param bodyContent message content
     * @param attributes message attributes
     */
    void handleMessage(String serviceName, String bodyContent, Map<String, Object> attributes);
}
