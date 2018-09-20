/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.soa.esb.samples.quickstart.scheduler.test;

import org.jboss.soa.esb.message.Message; 
import org.jboss.soa.esb.message.format.MessageFactory; 
import org.jboss.soa.esb.message.format.MessageType; 
import org.jboss.soa.esb.client.ServiceInvoker; 
/**
 * Standalone class with to send ESB messages to a 'known' [category,name].
 * <p/> arg0 - service category
 * <br/>arg1 - service name
 * <br/>arg2 - Text of message to send
 * 
 */
public class SendEsbMessage
{
	public SendEsbMessage() {
	}
	
	public void sendMessage(String category, String serviceName, String msgText) {
		System.out.println("Inbound Data: " + msgText);
 		try {   	
			ServiceInvoker invoker = new ServiceInvoker(category,serviceName);
			Message requestMessage;
			requestMessage = MessageFactory.getInstance().getMessage(MessageType.JBOSS_XML);
			requestMessage.getBody().add(msgText); 
    		invoker.deliverAsync(requestMessage); // no waiting for a response
        } catch (Exception e) {
            System.out.println("Exception caught by client: " + e);
        }
	}

    public static void main(String args[]) throws Exception
    {
		// Setting the ConnectionFactory such that it will use scout
   
    	System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");

    	String category = "ScheduledServices";
    	String serviceName = "MyFirstScheduledService";
    	String msgText = "Default";

    	
    	if (args[0] != null && !args[0].equals("")) 
    		category = args[0];
    	
    	if (args[1] != null && !args[1].equals("")) 
    		serviceName = args[1];

    	if (args[2] != null && !args[2].equals("")) 
    		 msgText =  args[2];
    	
		SendEsbMessage sem = new SendEsbMessage();
		sem.sendMessage(category, serviceName, msgText);
    }
    
}