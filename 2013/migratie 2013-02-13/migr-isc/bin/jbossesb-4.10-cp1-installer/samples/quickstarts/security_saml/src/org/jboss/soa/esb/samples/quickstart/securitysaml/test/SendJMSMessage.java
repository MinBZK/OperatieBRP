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
package org.jboss.soa.esb.samples.quickstart.securitysaml.test;

import org.jboss.internal.soa.esb.util.StreamUtils;
import org.jboss.remoting.InvokerLocator;
import org.jboss.remoting.Client;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.InetAddress;
import org.jboss.internal.soa.esb.util.StreamUtils;

import org.jboss.soa.esb.actions.StoreMessageToFile;

public class SendJMSMessage {
    QueueConnection conn;
    QueueSession session;
    Queue que;

    public void sendMessageOverJMS(String message, String destination) throws JMSException, NamingException {
        QueueSender sender = null;

    	setupJMSConnection(destination);
        try {
            ObjectMessage tm = null;

            sender = session.createSender(que);
            tm = session.createObjectMessage(message);
	    	tm.setStringProperty(StoreMessageToFile.PROPERTY_JBESB_FILENAME, "SecuritySamlTest.log");
            sender.send(tm);
        } finally {
            if(sender != null) {
                sender.close();
            }
            cleanupJMSConnection();
        }
    }

    public void setupJMSConnection(final String destination) throws JMSException, NamingException
    {
    	InitialContext iniCtx = new InitialContext();
    	Object tmp = iniCtx.lookup("ConnectionFactory");
    	QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
    	conn = qcf.createQueueConnection();
    	que = (Queue) iniCtx.lookup(destination);
    	session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
    	conn.start();
    }

    public void cleanupJMSConnection() throws JMSException
    {
        conn.stop();
        session.close();
        conn.close();
    }

    public static void main(String... args) throws Throwable
    {        	    	
    	SendJMSMessage sm = new SendJMSMessage();
		String destination = args[0];
		String payload = StreamUtils.getResourceAsString(args[1], "UTF-8");

		try
		{
			sm.sendMessageOverJMS(payload, destination);
		}
		catch(final Exception e)
		{
			System.err.println("Call was not successful. See server.log for details. Exception was:");
			e.printStackTrace();	
		}
    }
}
