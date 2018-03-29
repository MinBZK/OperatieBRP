/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import javax.xml.transform.Source;

/**
 * Interface voor verzending webservice clients.
 */
public interface VerzendingWebserviceClient {

    /**
     * Verstuur request.
     * @param request request
     * @param endpointUrl endpoint url
     */
    void verstuurRequest(final Source request, final String endpointUrl);
}
