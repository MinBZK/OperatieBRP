/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and others contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * (C) 2005-2006, JBoss Inc.
 */
package org.jboss.test.ws.jaxws.samples.wssecurity.client;

import org.jboss.test.ws.jaxws.samples.wssecurity.Hello;
import org.jboss.test.ws.jaxws.samples.wssecurity.UserType;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;

/**
 * CXF WSA Client.
 *
 * @author <a href="mailto:mageshbk@jboss.com">mageshbk@jboss.com</a>
 */
public class WSSClient {

    public static void main(String[] args) throws MalformedURLException, RemoteException {
        Hello hello = getEndpointPort();

        UserType userType = new UserType();
        userType.setMsg("Hello ESB");
        UserType retObj = hello.echoUserType(userType);
        System.out.println("Echo received '" + retObj.getMsg() +  "'.");
    }

    private static Hello getEndpointPort() throws MalformedURLException {
        QName serviceName = new QName(Hello.SERVICE_NAMESPACE, Hello.SERVICE_NAME);
        URL wsdlURL = new URL("http://localhost:8080/contract/contract.jsp?serviceCat=MyServiceCategory&serviceName=MyWssService&protocol=http");

        Service service = Service.create(wsdlURL, serviceName);
        Hello endpoint = service.getPort(Hello.class);
        setupWsse(endpoint);

        Map<String, Object> reqContext = ((BindingProvider) endpoint).getRequestContext();
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:9876/");

        return endpoint;
    }

    private static void setupWsse(Hello proxy)
    {
        Client client = ClientProxy.getClient(proxy);
        Endpoint cxfEndpoint = client.getEndpoint();

        Map<String, Object> outProps = new HashMap<String, Object>();
        outProps.put("action", "Timestamp Signature Encrypt");
        outProps.put("user", "alice");
        outProps.put("signaturePropFile", "src_cxf/META-INF/alice.properties");
        outProps.put("signatureKeyIdentifier", "DirectReference");
        outProps.put("passwordCallbackClass", "org.jboss.test.ws.jaxws.samples.wssecurity.KeystorePasswordCallback");
        outProps.put("signatureParts", "{Element}{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp;{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body");
        outProps.put("encryptionPropFile", "src_cxf/META-INF/alice.properties");
        outProps.put("encryptionUser", "Bob");
        outProps.put("encryptionParts", "{Element}{http://www.w3.org/2000/09/xmldsig#}Signature;{Content}{http://schemas.xmlsoap.org/soap/envelope/}Body");
        outProps.put("encryptionSymAlgorithm", "http://www.w3.org/2001/04/xmlenc#tripledes-cbc");
        outProps.put("encryptionKeyTransportAlgorithm", "http://www.w3.org/2001/04/xmlenc#rsa-1_5");
        WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps); //request
        cxfEndpoint.getOutInterceptors().add(wssOut);
        cxfEndpoint.getOutInterceptors().add(new SAAJOutInterceptor());

        Map<String,Object> inProps= new HashMap<String,Object>();
        inProps.put("action", "Timestamp Signature Encrypt");
        inProps.put("signaturePropFile", "src_cxf/META-INF/alice.properties");
        inProps.put("passwordCallbackClass", "org.jboss.test.ws.jaxws.samples.wssecurity.KeystorePasswordCallback");
        inProps.put("decryptionPropFile", "src_cxf/META-INF/alice.properties");
        WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps); //response
        cxfEndpoint.getInInterceptors().add(wssIn);
        cxfEndpoint.getInInterceptors().add(new SAAJInInterceptor());
    }

}
