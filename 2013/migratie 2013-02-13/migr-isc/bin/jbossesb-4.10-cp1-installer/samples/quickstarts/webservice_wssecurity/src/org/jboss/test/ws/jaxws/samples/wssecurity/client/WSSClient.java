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

import org.jboss.ws.core.StubExt;
import org.jboss.test.ws.jaxws.samples.wssecurity.Hello;
import org.jboss.test.ws.jaxws.samples.wssecurity.UserType;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * WSA Client.
 *
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
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
        //URL wsdlURL = new URL("http://127.0.0.1:8080/Quickstart_webservice_wss?wsdl");
        //URL wsdlURL = new URL("http://127.0.0.1:8080/jaxws-samples-wssecurity-sign?wsdl");

        Service service = Service.create(wsdlURL, serviceName);
        Hello endpoint = service.getPort(Hello.class);

        ((StubExt) endpoint).setSecurityConfig("jboss-wsse-client.xml");
        ((StubExt) endpoint).setConfigName("Standard WSSecurity Client");

        Map<String, Object> reqContext = ((BindingProvider) endpoint).getRequestContext();
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:9876/");
        //reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://127.0.0.1:8080/Quickstart_webservice_wss");
        //reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://127.0.0.1:8080/jaxws-samples-wssecurity-sign");

        return endpoint;
    }
}
