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
package org.jboss.soa.esb.samples.quickstart.deadletter;

import java.net.URI;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.internal.soa.esb.persistence.format.MessageStoreFactory;
import org.jboss.soa.esb.Service;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.couriers.FaultMessageException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.services.persistence.MessageStore;
import org.jboss.soa.esb.services.persistence.MessageStoreException;
import org.jboss.soa.esb.services.registry.RegistryException;
/**
 * 
 * @author kstam
 *
 */
public class MyFailingSyncAction extends AbstractActionLifecycle {

    protected ConfigTree _config;
    private Logger logger = Logger.getLogger(this.getClass());
    
    private static final String ID = "SYNC-ID" ;
    private static final String DATE = "SYNC-DATE" ;

    public MyFailingSyncAction(ConfigTree config) {
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
    public Message process(Message message) throws ActionProcessingException
    {
        //The DeadLetterService is custom configurable Service. It is defined in 
        //the jbossesb.esb an by default it stores the message in the
        //MessageStore under the DLQ categorization.
        MessageStore ms = MessageStoreFactory.getInstance().getMessageStore();
       	Message dlqMessage = null; 
       	message.getBody().add(ID, "ID:" + Integer.toHexString(System.identityHashCode(message))) ;
        message.getBody().add(DATE, new Date()) ;
        try {
            //empty out the DLQ
            Map<URI, Message> messageMap = ms.getAllMessages(MessageStore.CLASSIFICATION_DLQ);
            for (URI key : messageMap.keySet()) {
                ms.removeMessage(key, MessageStore.CLASSIFICATION_DLQ);
            }
            Service noneExistingService = new Service("none-exising-category", "none-existing-service-name");
            ServiceInvoker si = new ServiceInvoker(noneExistingService);
            si.deliverSync(message, 1000);
        } catch (MessageStoreException mse) {
            throw new ActionProcessingException(mse.getMessage(), mse);
        } catch (MessageDeliverException mde) {
            //Adding this control code to show where the message now is.
            //We should get here on and we should have a message in the DLQ.
            try {
                Map<URI, Message> messageMap = ms.getAllMessages(MessageStore.CLASSIFICATION_DLQ);
                while (messageMap.size() == 0) { //we may have to wait for the DLQ Service to act.
                    logger.info("...Waiting for the DLQ Service to act.");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ie) {
                        logger.error(ie);
                    }
                    messageMap = ms.getAllMessages(MessageStore.CLASSIFICATION_DLQ);
                }
                for (URI key : messageMap.keySet()) {
                    dlqMessage = messageMap.get(key);
                    logger.info("*******************************");
                    logger.info("Message in the DLQ queue should be the same message: " + compare(message.getBody(), dlqMessage.getBody()));
                    logger.info("Message=" + message.getBody());
                    logger.info("dlqMessage=" + dlqMessage.getBody());
                    logger.info("*******************************");
                }
            } catch (MessageStoreException mse) {
                throw new ActionProcessingException(mse.getMessage(), mse);
            }
        } catch (RegistryException re) {
            throw new ActionProcessingException(re.getMessage(), re);
        } catch (FaultMessageException fme) {
            throw new ActionProcessingException(fme.getMessage(), fme);
        }
        return dlqMessage;

    }
    
    private static boolean compare(final Body lhs, final Body rhs)
    {
        return compare(ID, lhs, rhs) && compare(DATE, lhs, rhs) ;
    }
    
    private static boolean compare(final String name, final Body lhs, final Body rhs)
    {
        final Object lhsObject = lhs.get(name) ;
        final Object rhsObject = rhs.get(name) ;
        
        return ((lhsObject != null) && lhsObject.equals(rhsObject)) ;
    }
}
