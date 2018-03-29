/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Service;

/**
 * Webservice client voor Verzending.
 */
@org.springframework.stereotype.Service
final class VerzendingVerwerkPersoonWebServiceClientImpl extends AbstractWebServiceClient implements Verzending.VerwerkPersoonWebServiceClient {

    private static final QName SYNC_PERSOON_PORT_NAME = new QName(NAMESPACE_URI, "lvgSynchronisatieVerwerking");
    private static final String SOAP_ACTION_VERWERK_SYNCHRONISATIE_PERSOON = "verwerkSynchronisatiePersoon";

    /**
     * Initialiseert webservice client voor protocolleer synchronisatie persoon.
     */
    @PostConstruct
    public void initWebServiceClient() {
        webserviceClient = initService().createDispatch(SYNC_PERSOON_PORT_NAME, Source.class, Service.Mode.PAYLOAD);
        /// future calls to getRequestContext() will use a thread local request context, allowing the request context to be threadsafe.
        webserviceClient.getRequestContext().put("thread.local.request.context", Boolean.TRUE.toString().toLowerCase());
    }

    @Override
    String getSoapAction() {
        return SOAP_ACTION_VERWERK_SYNCHRONISATIE_PERSOON;
    }

}
