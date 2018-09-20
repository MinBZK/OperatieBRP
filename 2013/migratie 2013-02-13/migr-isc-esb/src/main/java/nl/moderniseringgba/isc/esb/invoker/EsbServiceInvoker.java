/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.invoker;

import java.util.List;

import org.jboss.soa.esb.Service;
import org.jboss.soa.esb.addressing.PortReference.Extension;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;

/**
 * Service invoker die op de ESB werkt.
 */
public final class EsbServiceInvoker implements ServiceInvoker {

    private final String category;
    private final String service;

    /**
     * Constructor.
     * 
     * @param category
     *            category
     * @param service
     *            service
     */
    public EsbServiceInvoker(final String category, final String service) {
        this.category = category;
        this.service = service;
    }

    @Override
    public void deliverAsync(final Message message) {
        try {
            new org.jboss.soa.esb.client.ServiceInvoker(category, service).deliverAsync(message);
        } catch (final MessageDeliverException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deliverAsync(final Message message, final List<Extension> extensions) {
        try {
            new org.jboss.soa.esb.client.ServiceInvoker(new Service(category, service), extensions)
                    .deliverAsync(message);
        } catch (final MessageDeliverException e) {
            throw new RuntimeException(e);
        }
    }

}
