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
package org.jboss.soa.esb.samples.quickstart.helloworldtopicnotifier;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * JMS Message Receiver.
 * <p/>
 * 
 * A small utility that subscribes to a topic and creates a listener that prints message content.
 * 
 * @author <a href="mailto:daniel.bevenius@redpill.se">Daniel Bevenius</a>
 * @author <a href="mailto:tcunning@redhat.com">Tom Cunningham</a>
 * @since Version 4.2
 */
public class ReceiveJMSMessage {
    TopicConnection topicConn;
    TopicSession topicSession;
    Topic topic;
    TopicConnectionFactory topicFactory;

    MessageListener ml = null;

    /**
     * Inner class listener that prints out message contents.
     */
    public static class ExListener implements MessageListener {
    	public void onMessage(Message msg) {
        	TextMessage tm = (TextMessage) msg;
            try {
            	System.out.println("onMessage, control channel recv text=" + tm.getText());
            } catch (Throwable t) {
            	t.printStackTrace();
            }
    	} /* method */
    } /* class */

    /**
     * Creates a subscriber and attaches a listener
     * @param topicName topic name
     */
    public void receiveOne(String topicName, boolean isDurable)
    {
		TopicSubscriber subscriber = null;
        Properties properties1 = new Properties();
		properties1.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		properties1.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		properties1.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		try {
			InitialContext iniCtx = new InitialContext(properties1);
			topicFactory = (TopicConnectionFactory) iniCtx.lookup("ConnectionFactory");
			topic = (Topic) iniCtx.lookup(topicName);

			topicConn = topicFactory.createTopicConnection("guest", "guest");
			topicConn.setClientID("clientid");
			topicSession = topicConn.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			if (isDurable) {
				subscriber = topicSession.createDurableSubscriber(topic, "testsub", null, false);
			} else {
				subscriber = topicSession.createSubscriber(topic);
			}
			ml = new ExListener();
			subscriber.setMessageListener(ml);
			topicConn.start();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String args[]) throws Exception
    {        	    
	if (args.length < 2) {
		System.err.println("Usage: java ReceiveJMSMessage <topic-name> [durable|non-durable]");
		System.exit(-1);	
	}	
    	ReceiveJMSMessage rm = new ReceiveJMSMessage();
    	rm.receiveOne(args[0], args[1].equals("durable"));
    	while (true) {
    	}
    }
    
}
