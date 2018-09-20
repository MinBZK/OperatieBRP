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
package org.jboss.soa.esb.samples.quickstart.webservicewsa.client;

import org.jboss.soa.esb.samples.quickstart.webservicewsa.StatefulEndpoint;
import org.jboss.ws.extensions.addressing.jaxws.WSAddressingClientHandler;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.SOAPBinding;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.rmi.RemoteException;

/**
 * WSA Client.
 * <p/>
 * The basis of this code was lifted from JBossWS test code.  Thanks guys :-)
 *
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class WSAClient {

    public static void main(String[] args) throws MalformedURLException, RemoteException {
        StatefulEndpoint endpointPort1 = getEndpointPort();
        StatefulEndpoint endpointPort2 = getEndpointPort();

        endpointPort1.addItem("Ice Cream");
        endpointPort1.addItem("Chocolate");

        endpointPort2.addItem("Water");
        endpointPort2.addItem("Bread");

        System.out.println("\nT1. endpointPort1 basket: " + endpointPort1.getItems());
        System.out.println("T1. endpointPort2 basket: " + endpointPort2.getItems());

        System.out.println("\nT2. endpointPort1 checks out...");
        endpointPort1.checkout();

        System.out.println("\nT3. endpointPort1 basket: " + endpointPort1.getItems());
        System.out.println("T3. endpointPort2 basket: " + endpointPort2.getItems());

        System.out.println("\nT4. endpointPort2 checks out...");
        endpointPort2.checkout();

        System.out.println("\nT5. endpointPort1 basket: " + endpointPort1.getItems());
        System.out.println("T5. endpointPort2 basket: " + endpointPort2.getItems());
    }

    private static StatefulEndpoint getEndpointPort() throws MalformedURLException {
        StatefulEndpoint endpoint;
        QName serviceName = new QName("http://webservice.webservicewsa.quickstart.samples.esb.soa.jboss.org/", "WsaService");
        URL wsdlURL = new URL("http://localhost:8080/contract/contract.jsp?serviceCat=MyServiceCategory&serviceName=MyWsaService&protocol=http");

        Service service = Service.create(wsdlURL, serviceName);
        endpoint = service.getPort(StatefulEndpoint.class);

        SOAPBinding binding = (SOAPBinding)((BindingProvider)endpoint).getBinding();
        List<Handler> handlerChain = new ArrayList<Handler>();

        handlerChain.addAll(binding.getHandlerChain());
        handlerChain.add(new ClientHandler());
        handlerChain.add(new WSAddressingClientHandler());
        binding.setHandlerChain(handlerChain);

        return endpoint;
    }
}
