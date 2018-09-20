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
package org.jboss.soa.esb.samples.quickstart.jmsrouter.test;

import java.util.Properties;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author <a href="mailto:daniel.bevenius@gmail.com">Daniel Bevenius</a>
 *
 */
public class SendJMSMessage
{
    private Connection connection;
    private Session session;
    private Destination gatewayDestination;
    private Destination responseDestination;
    private Destination replyToDestination;
    private String correlationId;

	private String propertyKey = "MyProperty";

    public void setupConnection(String destination) throws JMSException, NamingException
    {
		InitialContext iniCtx = new InitialContext();

    	ConnectionFactory connectionFactory = (ConnectionFactory) iniCtx.lookup("ConnectionFactory");

    	connection = connectionFactory.createConnection();

    	gatewayDestination = (Destination) iniCtx.lookup("queue/quickstart_jms_router_Request_gw");
    	responseDestination = (Destination)iniCtx.lookup(destination);
    	replyToDestination = (Destination) iniCtx.lookup("queue/quickstart_jms_router_replyToQueue");
    	session = connection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
    	connection.start();
    	System.out.println("Connection Started");
    }

    public void stop() throws JMSException
    {
        session.close();
        connection.stop();
        connection.close();
    }

    public void sendAMessage(String msg) throws JMSException {
    	correlationId = "QuickstartId[" + java.util.Calendar.getInstance().get( java.util.Calendar.SECOND ) + "]";
        MessageProducer producer = session.createProducer(gatewayDestination);
        ObjectMessage objectMsg = session.createObjectMessage(msg);
        objectMsg.setJMSCorrelationID( correlationId );
		objectMsg.setJMSReplyTo( replyToDestination );
		objectMsg.setStringProperty(propertyKey, "My property value");

        producer.send(objectMsg);
    	System.out.println("Sent message with CorrelationID : " + correlationId );
    	System.out.println("");
        producer.close();
    }

    public void receiveAMessage() throws JMSException {

        MessageConsumer consumer = session.createConsumer(responseDestination, "JMSCorrelationID = '" + correlationId + "'");
        Message jmsMsg = consumer.receive();
        System.out.println("Received from " + responseDestination + ":");
        System.out.println("\t[JMSMessageID : " +  jmsMsg.getJMSMessageID() + "]" );
		System.out.println("\t[JMSCorrelelationID : " +  jmsMsg.getJMSCorrelationID() + "]" );
		System.out.println("\t[JMSReplyto : " +  jmsMsg.getJMSReplyTo() + "]" );
		if ( jmsMsg instanceof ObjectMessage )
		{
    		System.out.println("\t[MessageType : ObjectMessage]");
    		System.out.println( "\t[Object : " +  ((ObjectMessage)jmsMsg).getObject() + "]" );
		}
		else if ( jmsMsg instanceof TextMessage )
		{
    		System.out.println("\t[MessageType : TextMessage]");
    		System.out.println( "\t[Text : " +  ((TextMessage)jmsMsg).getText() + "]" );
		}
		System.out.println("\t[Property: "+ propertyKey + " : " +  jmsMsg.getStringProperty(propertyKey) + "]" );

        consumer.close();
    }


    public static void main(String args[]) throws Exception
    {
    	SendJMSMessage sm = new SendJMSMessage();
    	sm.setupConnection(args[1]);
    	sm.sendAMessage(args[0]);
    	sm.receiveAMessage();
    	sm.stop();
    }

}
