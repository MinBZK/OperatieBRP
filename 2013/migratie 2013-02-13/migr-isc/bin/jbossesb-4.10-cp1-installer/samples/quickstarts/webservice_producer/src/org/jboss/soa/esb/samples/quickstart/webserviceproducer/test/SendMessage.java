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
package org.jboss.soa.esb.samples.quickstart.webserviceproducer.test;

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

import org.jboss.soa.esb.actions.StoreMessageToFile;

public class SendMessage {
    QueueConnection conn;
    QueueSession session;
    Queue que;

    public void sendMessageOverJMS(String message) throws JMSException, NamingException {
        QueueSender sender = null;

    	setupJMSConnection();
        try {
            ObjectMessage tm = null;

            sender = session.createSender(que);
            tm = session.createObjectMessage(message);
	    tm.setStringProperty(StoreMessageToFile.PROPERTY_JBESB_FILENAME, "WebServiceProducerTest.log");
            sender.send(tm);
        } finally {
            if(sender != null) {
                sender.close();
            }
            cleanupJMSConnection();
        }
    }

    private void sendMessageToJBRListener(String protocol, int port, String message) throws Throwable {
        String locatorURI = protocol + "://localhost:" + port;
        InvokerLocator locator = new InvokerLocator(locatorURI);
        System.out.println("Calling JBoss Remoting Listener using locator URI: " + locatorURI);

        Client remotingClient = null;
        try {
            remotingClient = new Client(locator);
            remotingClient.connect();

            // Deliver the message to the listener...
            Object response = remotingClient.invoke(message);
            System.out.println("JBR Class: " + response.getClass().getName());
            System.out.println("Response from JBoss Remoting Listener '" + locatorURI + "' was '" + response + "'.");
        } finally {
            if(remotingClient != null) {
                remotingClient.disconnect();
            }
        }
    }

    public void setupJMSConnection() throws JMSException, NamingException
    {
    	InitialContext iniCtx = new InitialContext();
    	Object tmp = iniCtx.lookup("ConnectionFactory");
    	QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
    	conn = qcf.createQueueConnection();
    	que = (Queue) iniCtx.lookup("queue/quickstart_webservice_producer_gw");
    	session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
    	conn.start();
    }

    public void cleanupJMSConnection() throws JMSException
    {
        conn.stop();
        session.close();
        conn.close();
    }

    private static String getMessage(String messageNum) {
        String msg = new String(StreamUtils.readStream(SendMessage.class.getResourceAsStream("soap_message_" + messageNum + ".xml")));
        return msg;
    }

    public static void main(String args[]) throws Throwable
    {        	    	
    	SendMessage sm = new SendMessage();
        String msg = getMessage("01");

        String protocol = args[0];
        if(protocol.equals("jms")) {
            sm.sendMessageOverJMS(msg);
        } else {
            sm.sendMessageToJBRListener(protocol, Integer.parseInt(args[1]), msg);
        }
    }
}
