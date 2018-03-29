/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import java.net.URL;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.jaxws.ServiceImpl;

/**
 * Abstracte basis voor web service clients.
 */
abstract class AbstractWebServiceClient implements VerzendingWebserviceClient {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String WSDL_BESTAND = "/wsdl/berichtverwerking.wsdl";
    static final String NAMESPACE_URI = "http://www.bzk.nl/brp/levering/berichtverwerking/service";
    private static final QName SERVICE_NAME = new QName(NAMESPACE_URI, "BrpBerichtVerwerkingService");

    Dispatch<Source> webserviceClient;

    /**
     * Initialiseert een {@link ServiceImpl}.
     * @return een {@link ServiceImpl} instantie
     */
    ServiceImpl initService() {
        final Bus bus = new SpringBusFactory().createBus();
        URL  url = getClass().getResource(WSDL_BESTAND);
        if (url == null) {
            throw new IllegalStateException(String.format("Wsdl bestand is niet gevonden : %s", WSDL_BESTAND));
        }
        return new ServiceImpl(bus, url, SERVICE_NAME, null);
    }

    abstract String getSoapAction();

    /**
     * Verstuurt een request naar een opgegeven afleverpunt.
     * @param request request
     * @param endpointUrl endpoint url
     */
    @Override
    public void verstuurRequest(final Source request, final String endpointUrl) {
        webserviceClient.getRequestContext().put("schema-validation-enabled", Boolean.TRUE.toString().toLowerCase());
        webserviceClient.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
        webserviceClient.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
        webserviceClient.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, getSoapAction());
        logSettings(webserviceClient.getRequestContext().entrySet());
        webserviceClient.invoke(request);
    }

    /**
     * Log de settings.
     * @param entries De set met settings.
     */

    private void logSettings(final Set<Map.Entry<String, Object>> entries) {
        final StringBuilder settings = new StringBuilder();
        for (final Map.Entry<String, Object> entry : entries) {
            settings.append("[");
            settings.append(entry.getKey());
            settings.append("=");
            settings.append(entry.getValue().toString());
            settings.append("],");
        }
        LOGGER.debug(settings.toString());
    }
}
