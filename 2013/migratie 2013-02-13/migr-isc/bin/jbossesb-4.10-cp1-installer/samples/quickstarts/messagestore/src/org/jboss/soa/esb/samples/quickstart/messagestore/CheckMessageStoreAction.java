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
package org.jboss.soa.esb.samples.quickstart.messagestore;

import java.net.URI;

import org.jboss.internal.soa.esb.persistence.format.MessageStoreFactory;
import org.jboss.soa.esb.actions.AbstractActionPipelineProcessor;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.services.persistence.MessageStore;
import org.jboss.soa.esb.services.persistence.MessageStoreException;

public class CheckMessageStoreAction extends AbstractActionPipelineProcessor {

    protected ConfigTree _config;

    public CheckMessageStoreAction(ConfigTree config) {
        _config = config;
    }
    /**
     * Check if the message is in the message store. The Unique Identifyer is set on the message property 
     * with name MessageStore.MESSAGE_URI. 
     * 
     * @param originalMessage
     * @return
     * @throws Exception
     */
    public Message process(Message originalMessage)
    {
        return originalMessage ;
    }
    
    /**
     * Process a successful pipeline process. 
     * Invoked when the pipeline processing completes successfully.
     * 
     * @param originalMessage The original message.
     */
    public void processSuccess(final Message originalMessage)
    {
        //I can call the messagestore API directory if it is deployed in the same JVM
        String messageStoreClass = "org.jboss.internal.soa.esb.persistence.format.db.DBMessageStoreImpl";
        MessageStore messageStore = MessageStoreFactory.getInstance().getMessageStore(messageStoreClass);
        //Try to pull the message out
        try
        {
            Message message = messageStore.getMessage((URI) originalMessage.getProperties().getProperty(MessageStore.MESSAGE_URI));
            //Print out the content of the message
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println("Body (from the stored message): " + message.getBody().get()) ;
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        }
        catch (final MessageStoreException mse)
        {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println("Error from message store: " + mse.getMessage()) ;
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        }
    }
}