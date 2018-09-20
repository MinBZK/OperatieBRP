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
package org.jboss.soa.esb.samples.quickstart.jmstransacted.test;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * 
 * @author <a href="mailto:daniel.bevenius@gmail.com">Daniel Bevenius</a>				
 *
 */
public class SendJMSMessage 
{
    private QueueConnection connection;
    private QueueSession session;
    private Queue queue;
    
    public void setupConnection() throws JMSException, NamingException
    {
		InitialContext iniCtx = new InitialContext();

    	QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) iniCtx.lookup("ConnectionFactory");
    	
    	connection = queueConnectionFactory.createQueueConnection();
    	
    	queue = (Queue) iniCtx.lookup("queue/quickstart_jms_transacted_Request_gw");
    	session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
    	connection.start();
    	System.out.println("Connection Started");
    }
    
    public void stop() throws JMSException 
    { 
        connection.stop();
        session.close();
        connection.close();
    }
    
    public void sendAMessage(String msg) throws JMSException {
    	
        QueueSender sender = session.createSender(queue);        
        ObjectMessage objectMsg = session.createObjectMessage(msg);
        
        sender.send(objectMsg);        
        sender.close();
    }
       
    
    public static void main(String args[]) throws Exception
    {        	    	
    	SendJMSMessage sm = new SendJMSMessage();
    	sm.setupConnection();
    	sm.sendAMessage(args[0]); 
    	sm.stop();
    }
    
}
