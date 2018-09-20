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

package org.jboss.soa.esb.samples.quickstart.helloworld.test;

import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.client.ServiceInvoker;

/**
 * Standalone class with to send ESB messages to a 'known' [category,name].
 * <p/> arg0 - service category
 * <br/>arg1 - service name
 * <br/>arg2 - Text of message to send
 * 
 * @author <a href="mailto:schifest@heuristica.com.ar">schifest@heuristica.com.ar</a>
 * @since Version 4.0
 *
 */
public class SendEsbMessage 
{
    public static void main(String args[]) throws Exception
    {
//      Setting the ConnectionFactory such that it will use scout
        System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");
        
    	if (args.length < 3)
    	{
    		System.out.println("Usage SendEsbMessage <category> <name> <text to send>");
    	}
    	
    	Message esbMessage = MessageFactory.getInstance().getMessage();

    	esbMessage.getBody().add(args[2]);
    	
        new ServiceInvoker(args[0], args[1]).deliverAsync(esbMessage);
    	
    }
    
}
