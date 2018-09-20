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
 * (C) 2005-2006,
 * @author JBoss Inc.
 */
package org.jboss.soa.esb.samples.quickstart.nativeclient.webservice;

// For setting up the basic WS

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

// For ESB Interaction
import org.jboss.soa.esb.message.Message; // jbossesb-rosetta.jar
import org.jboss.soa.esb.message.format.MessageFactory; // jbossesb-rosetta.jar
import org.jboss.soa.esb.message.format.MessageType; // jbossesb-rosetta.jar
import org.jboss.soa.esb.client.ServiceInvoker; // jbossesb-rosetta.jar

@WebService(name = "HelloWorld", targetNamespace = "http://nativeclient/helloworld")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class HelloWorldWS {
    @WebMethod
    public String sayHello(String toWhom) {
        System.out.println("HelloWorld Hit! " + toWhom);
        String results = "";
        try {
            ServiceInvoker deliveryAdapter;
            Message requestMessage;
            Message replyMessage = null;

            // Create the delivery adapter for the target service (you'd normally cache this!!)...
            deliveryAdapter = new org.jboss.soa.esb.client.ServiceInvoker("MyServiceCategory", "MyNativeClientService");
            // Create and populate the request message...
            requestMessage = MessageFactory.getInstance().getMessage(MessageType.JBOSS_XML);
            requestMessage.getBody().add(toWhom); // inject the value from the WS client
            // Deliver the request message synchronously - timeout after 20 seconds... 
            replyMessage = deliveryAdapter.deliverSync(requestMessage, 20000);

            if (replyMessage != null) {
                results = (String) replyMessage.getBody().get();
            } else {
                results = "Hello World: " + toWhom + " on " + new java.util.Date();
            }
        } catch (Exception e) {
            System.out.println(e + "\n");
            e.printStackTrace(System.out);
        }
        return results;
    }
}


