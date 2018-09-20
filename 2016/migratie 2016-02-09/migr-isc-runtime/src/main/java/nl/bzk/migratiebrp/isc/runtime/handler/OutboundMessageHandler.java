/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.handler;

import java.util.Map;

import nl.bzk.migratiebrp.isc.jbpm.common.actionhandler.OutboundHandler;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import nl.bzk.migratiebrp.isc.runtime.service.Service;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

/**
 * Outbound message handler.
 */
@Component
public final class OutboundMessageHandler implements OutboundHandler {

    private Service syncOutboundService;
    private Service voiscOutboundService;

    /**
     * Zet de outbound service voor SYNC.
     *
     * @param syncOutboundService
     *            de te zetten outbound service voor SYNC
     */
    @Required
    public void setSyncOutboundService(final Service syncOutboundService) {
        this.syncOutboundService = syncOutboundService;
    }

    /**
     * Zet de outbound service voor VOISC.
     *
     * @param voiscOutboundService
     *            de te zetten outbound service voor VOISC
     */
    @Required
    public void setVoiscOutboundService(final Service voiscOutboundService) {
        this.voiscOutboundService = voiscOutboundService;
    }

    @Override
    public void handleMessage(final String serviceName, final String bodyContent, final Map<String, Object> attributes) {
        final Message message = maakMessage(bodyContent, attributes);

        final Service outbound;
        switch (serviceName) {
            case "VOSPG-Outbound":
                outbound = voiscOutboundService;
                break;
            case "Sync-Outbound":
                outbound = syncOutboundService;
                break;
            default:
                throw new IllegalArgumentException("Onbekende ESB Handler service: " + serviceName);
        }
        outbound.verwerk(message);
    }

    private Message maakMessage(final String bodyContent, final Map<String, Object> attributes) {
        final Message result = new Message();
        result.setContent(bodyContent);
        result.setAllAttributes(attributes);
        return result;
    }

}
