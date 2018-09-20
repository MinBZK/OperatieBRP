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
package org.jboss.soa.esb.samples.quickstart.scheduler;

import org.jboss.soa.esb.listeners.ScheduledEventMessageComposer;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.message.format.MessageType;
import org.jboss.soa.esb.schedule.SchedulingException;

import org.jboss.soa.esb.actions.StoreMessageToFile;

public class MyScheduledActionMsgComposer implements ScheduledEventMessageComposer {
    public void initialize(ConfigTree config) throws ConfigurationException {
    	System.out.println("** initialize: " + config);
    }

    public void uninitialize() {
    	System.out.println("uninitialize **");
    }
    
    public Message composeMessage() throws SchedulingException {
    	System.out.println("compose a message");
    	Message myMessage = MessageFactory.getInstance().getMessage(MessageType.JBOSS_XML);
    	myMessage.getBody().add("Hello Scheduled World");
	myMessage.getProperties().setProperty(StoreMessageToFile.PROPERTY_JBESB_FILENAME, "ScheduledServices.log");
    	return myMessage;
    }
    
   	public Message onProcessingComplete(Message message) throws SchedulingException {
   		System.out.println("onProcessingComplete");
   		return message;
   	}
}
