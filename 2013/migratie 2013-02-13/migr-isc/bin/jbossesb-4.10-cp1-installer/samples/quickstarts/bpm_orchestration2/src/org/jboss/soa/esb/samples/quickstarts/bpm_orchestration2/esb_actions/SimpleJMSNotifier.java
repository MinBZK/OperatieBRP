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

package org.jboss.soa.esb.samples.quickstarts.bpm_orchestration2.esb_actions;
 
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.apache.log4j.Logger;

import java.util.Properties;

import javax.jms.TextMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;

public class SimpleJMSNotifier extends AbstractActionLifecycle {
	protected ConfigTree	_config;
	private Logger logger = Logger.getLogger(SimpleJMSNotifier.class);
	String queueName = "quickstart_helloworld_Request_gw";
	
	public Message process(Message message) throws Exception {	
		 QueueConnection conn;
     QueueSession session;
     Queue que;	
     
     Properties properties1 = new Properties();
		 properties1.put(Context.INITIAL_CONTEXT_FACTORY,
		 "org.jnp.interfaces.NamingContextFactory");
		 properties1.put(Context.URL_PKG_PREFIXES,
		 "org.jboss.naming:org.jnp.interfaces");
		 properties1.put(Context.PROVIDER_URL, "jnp://127.0.0.1:1099");
		 InitialContext iniCtx = new InitialContext(properties1);

     Object tmp = iniCtx.lookup("ConnectionFactory");
     QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
     conn = qcf.createQueueConnection();
     que = (Queue) iniCtx.lookup("queue/" + queueName);
     session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
     conn.start();
     //logger.info("Connection Started");
     
     QueueSender send = session.createSender(que);        
     TextMessage tm = session.createTextMessage((String)message.getBody().get());
     send.send(tm);        
     send.close();
     
     conn.stop();
     session.close();
     conn.close();
     
     return message;
  }
  
	public SimpleJMSNotifier(ConfigTree config) { 
		_config = config; 
	  queueName = _config.getAttribute("ALERT_QUEUE_NAME");
	  logger.info("Who to alert: " + queueName);	
	} 
}	