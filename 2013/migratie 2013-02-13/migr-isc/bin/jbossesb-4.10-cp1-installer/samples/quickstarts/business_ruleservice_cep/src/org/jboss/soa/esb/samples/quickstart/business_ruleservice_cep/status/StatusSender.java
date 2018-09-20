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
 * (C) 2005-2010
 */
package org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.status;

import static org.jboss.soa.esb.services.rules.RuleServicePropertiesNames.CONTINUE;
import static org.jboss.soa.esb.services.rules.RuleServicePropertiesNames.DISPOSE;

import java.util.Properties;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

/**
 * StatusSender.
 * 
 * @author dward at jboss.org
 */
public class StatusSender
{
	
	private static final Logger logger = Logger.getLogger(StatusSender.class);
	
	private static final StatusSender INSTANCE = new StatusSender();
	
	public static final StatusSender getInstance()
	{
		return INSTANCE;
	}
	
    private QueueConnection connection;
    private QueueSession session;
    private Queue queue;
	
	private StatusSender() {}
	
	public void startConnection() throws Exception
	{
		logger.info("starting connection");
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        properties.put(Context.PROVIDER_URL, "jnp://127.0.0.1:1099");
        InitialContext context = new InitialContext(properties);
        QueueConnectionFactory factory = (QueueConnectionFactory)context.lookup("ConnectionFactory");
        connection = factory.createQueueConnection();
        queue = (Queue)context.lookup("queue/quickstart_business_ruleservice_cep_GW");
        session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        connection.start();
        logger.info("connection started");
	}
	
	public void startSession() throws Exception
	{
		logger.info("starting session");
        sendMessage(session.createTextMessage("startSession"), false, false);
		logger.info("session started");
	}
	
	public void sendStatus(Status status) throws Exception
	{
		logger.info("sending status [" + status + "]");
        sendMessage(session.createObjectMessage(status), false, true);
        logger.info("status [" + status + "] sent");
	}
	
	
	public void stopSession() throws Exception
	{
		logger.info("stopping session");
        sendMessage(session.createTextMessage("stopSession"), true, true);
		logger.info("session stopped");
	}
	
	private void sendMessage(Message message, boolean dispose, boolean continueState) throws Exception
	{
        message.setBooleanProperty(DISPOSE.getName(), dispose);
        message.setBooleanProperty(CONTINUE.getName(), continueState);
		logger.info("sending message [" + message + "]");
        QueueSender sender = session.createSender(queue);
        sender.send(message);
        sender.close();
        logger.info("message [" + message + "] sent");
	}
	
	public void stopConnection() throws Exception
	{
		logger.info("stopping connection");
		connection.stop();
		session.close();
		connection.close();
		logger.info("connection stopped");
	}

}
