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

package org.jboss.soa.esb.samples.quickstart.securityjbpm.test;

import java.util.Set;
import java.io.Serializable;
import java.util.HashSet;
import org.jboss.soa.esb.services.security.auth.AuthenticationRequestImpl;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.services.security.SecurityService;
import org.jboss.soa.esb.services.security.auth.AuthenticationRequest;
import org.jboss.soa.esb.services.security.principals.User;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.couriers.FaultMessageException;
import org.jboss.soa.esb.services.security.PublicCryptoUtil;

/**
 * Standalone class with to send ESB messages to a 'known' [category,name].
 * <p/> arg0 - service category
 * <br/>arg1 - service name
 * <br/>arg2 - Text of message to send
 * <br/>arg3 - username
 * <br/>arg4 - password
 * 
 * @since Version 4.0
 *
 */
public class SendEsbMessage 
{
    public static void main(String args[]) throws Exception
    {
//      Setting the ConnectionFactory such that it will use scout
        System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");
        
        if (args.length < 5)
        {
            System.out.println("Usage SendEsbMessage <category> <name> <text to send> <username> <password>");
        }
        Message esbMessage = MessageFactory.getInstance().getMessage();
        
		//	create an AuthenticationRequest
		AuthenticationRequest authRequest = new AuthenticationRequestImpl.Builder().username(args[3]).password(args[4].toCharArray()).build();
		
		// 	set the authentication request on the message
		esbMessage.getContext().setContext(SecurityService.AUTH_REQUEST, PublicCryptoUtil.INSTANCE.encrypt((Serializable) authRequest));
		
        final String message = args[2];
        esbMessage.getBody().add(message);
        
        ServiceInvoker invoker = new ServiceInvoker(args[0], args[1]);
        
        try {
            invoker.deliverAsync(esbMessage);
        }catch(Exception ex) {
           ex.printStackTrace();
        } 
        System.exit(0);
    }
}
