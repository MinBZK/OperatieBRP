/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl.dispatch;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.AddressingFeature;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl.util.BzmSoapUtil;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.cxf.binding.soap.interceptor.SoapPreProtocolOutInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.springframework.beans.factory.InitializingBean;

/**
 * DispatchClient welke een SOAP Service aanroept middels de JAX-WS Dispatch API.
 */
public class DispatchClient implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger();

    private String wsdl;
    private String serviceNamespace;
    private String serviceName;
    private String portNamespace;
    private String portName;
    private URL wsdlUrl;
    private QName serviceQName;
    private QName portQName;

    private Dispatch<SOAPMessage> dispatch;

    /**
     * Zet de waarde van wsdl.
     *
     * @param wsdl
     *            wsdl
     */
    public final void setWsdl(final String wsdl) {
        this.wsdl = wsdl;
    }

    /**
     * Zet de waarde van service namespace.
     *
     * @param serviceNamespace
     *            service namespace
     */
    public final void setServiceNamespace(final String serviceNamespace) {
        this.serviceNamespace = serviceNamespace;
    }

    /**
     * Zet de waarde van service name.
     *
     * @param serviceName
     *            service name
     */
    public final void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Zet de waarde van port namespace.
     *
     * @param portNamespace
     *            port namespace
     */
    public final void setPortNamespace(final String portNamespace) {
        this.portNamespace = portNamespace;
    }

    /**
     * Zet de waarde van port name.
     *
     * @param portName
     *            port name
     */
    public final void setPortName(final String portName) {
        this.portName = portName;
    }

    @Override
    public final void afterPropertiesSet() {
        wsdlUrl = BzmSoapUtil.getWsdlUrl(wsdl);
        serviceQName = new QName(serviceNamespace, serviceName);
        portQName = new QName(portNamespace, portName);

    }

    /**
     * Stuurt het meegegeven SOAP request naar de BRP Bijhouding Service.
     *
     * @param request
     *            SOAPMessage
     * @param oinTransporteur
     *            OIN van de transporteur
     * @param oinOndertekenaar
     *            OIN van de ondertekenaar
     * @return response SOAPMessage
     */
    @SuppressWarnings("checkstyle:designforextension")
    // Non final omdat de testen anders niet kunnen werken.
    public SOAPMessage doInvokeService(final SOAPMessage request, final String oinTransporteur, final String oinOndertekenaar) {
        SOAPMessage response = null;
        try {
            response = getDispatch(oinTransporteur, oinOndertekenaar).invoke(request);
        } catch (final WebServiceException wse) {
            LOG.error("Fout tijdens aanroepen webservice", wse);
        }
        return response;
    }

    /**
     * Maakt een Dispatch<SOAPMessage> object aan indien deze nog niet bestaat en geeft deze terug. Configureert ook
     * WS-Addressing en WS-Security.
     *
     * @return dispatch
     */
    private Dispatch<SOAPMessage> getDispatch(final String oinTransporteur, final String oinOndertekenaar) {
        if (dispatch == null) {
            final Service service = Service.create(wsdlUrl, serviceQName);
            dispatch = service.createDispatch(portQName, SOAPMessage.class, Service.Mode.MESSAGE, new AddressingFeature(true));
            final Map<String, List<String>> httpRequestHeaders = new HashMap<>();
            httpRequestHeaders.put("oin-transporteur", Arrays.asList(oinTransporteur));
            httpRequestHeaders.put("oin-ondertekenaar", Arrays.asList(oinOndertekenaar));
            dispatch.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpRequestHeaders);

            // TODO: Spring gebruiken om client/interceptors te configureren, zie cxf-config.xml
            configureGlobalInterceptor(dispatch);
        }

        return dispatch;
    }

    /**
     * Voegt globale interceptors toe aan de dispatcher. Interceptors welke niet per request veranderen. Gebruikt het
     * CXF framework.
     *
     * @param soapMessageDispatch
     *            Dispatch<SOAPMessage>
     */
    private void configureGlobalInterceptor(final Dispatch<SOAPMessage> soapMessageDispatch) {
        final Client client = ((org.apache.cxf.jaxws.DispatchImpl<SOAPMessage>) soapMessageDispatch).getClient();
        client.getOutInterceptors().add(new SoapPreProtocolOutInterceptor());

        // for debugging only
        client.getOutInterceptors().add(new LoggingOutInterceptor());
        client.getInInterceptors().add(new LoggingInInterceptor());
    }

}
