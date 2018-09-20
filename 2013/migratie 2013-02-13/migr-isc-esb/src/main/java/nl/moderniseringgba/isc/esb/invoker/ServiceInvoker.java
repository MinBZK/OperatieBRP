/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.invoker;

import java.util.List;

import org.jboss.soa.esb.addressing.PortReference.Extension;
import org.jboss.soa.esb.message.Message;

/**
 * Ontkoppelpunt om berichten af te leveren.
 */
public interface ServiceInvoker {

    /**
     * Lever een bericht af.
     * 
     * @param message
     *            bericht
     */
    void deliverAsync(Message message);

    /**
     * Lever een bericht af.
     * 
     * @param message
     *            bericht
     * @param extensions
     *            extensions
     */
    void deliverAsync(Message message, List<Extension> extensions);
}
