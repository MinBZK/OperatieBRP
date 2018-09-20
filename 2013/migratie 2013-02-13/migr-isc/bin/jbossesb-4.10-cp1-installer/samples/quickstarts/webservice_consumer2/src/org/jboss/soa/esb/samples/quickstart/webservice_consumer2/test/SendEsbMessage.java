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
package org.jboss.soa.esb.samples.quickstart.webservice_consumer2.test;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.message.format.MessageType;

import org.jboss.soa.esb.actions.StoreMessageToFile;

public class SendEsbMessage
{
   /*
    * Send a message directly the the ESB internal JMS listener. Demonstrates
    * how one can bypass the gateway and speak directly to an ESB service.
    */
   private void sendMessage() throws Exception
   {
      // Create the delivery adapter for the target service (cache it)
      System.setProperty("javax.xml.registry.ConnectionFactoryClass",
            "org.apache.ws.scout.registry.ConnectionFactoryImpl");

      // Create the delivery adapter for the target service (cache it)
      ServiceInvoker deliveryAdapter = new ServiceInvoker("MyServiceCategory",
            "WebserviceConsumer2");
      
      // Create and populate the request message...
      Message requestMessage = MessageFactory.getInstance().getMessage(
            MessageType.JAVA_SERIALIZED);
      
      // Deliver the request message synchronously - timeout after 20
      // seconds...
      deliveryAdapter.deliverAsync(requestMessage);

      // need to cleanup connection pool for service invoker
      //deliveryAdapter.close();
   }

   public static void main(String args[]) throws Exception
   {
      SendEsbMessage sm = new SendEsbMessage();
      sm.sendMessage();
   }
}
