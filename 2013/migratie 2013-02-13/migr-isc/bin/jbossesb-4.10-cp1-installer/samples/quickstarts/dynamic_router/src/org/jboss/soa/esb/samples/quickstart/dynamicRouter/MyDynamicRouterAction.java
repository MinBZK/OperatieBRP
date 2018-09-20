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
package  org.jboss.soa.esb.samples.quickstart.dynamicRouter;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.QueueSender;
import javax.naming.NamingException;

import java.util.Enumeration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import org.jboss.soa.esb.samples.quickstart.dynamicRouter.test.*;
import java.util.Hashtable;

public class MyDynamicRouterAction extends AbstractActionLifecycle
{
    
  protected ConfigTree	_config;
  public MyDynamicRouterAction(ConfigTree config) { _config = config; } 
  public Message processMessage(Message message) throws Exception{
	  
	  Hashtable theHashtable = QsHashtable.readHash();
		try {
			TextMessage tm = null; 
			
			for (Enumeration e = theHashtable.keys(); e.hasMoreElements();) {
				String theKey = (String) e.nextElement();
				String theValue = (String) theHashtable.get(theKey);
				
				// If the queue is "OK" - send the incoming message to that queue 
				if (theValue.equals("OK")) {
					
					// Set up the connection
					QueueConnection conn1 = null;
					Queue que;
					QueueSession session;
					InitialContext iniCtx = new InitialContext();
					Object tmp = iniCtx.lookup("ConnectionFactory");
					QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
					conn1 = qcf.createQueueConnection();
					que = (Queue) iniCtx.lookup(theKey);
					session = conn1.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
					conn1.start();

                    // Send the message                						
					QueueSender send = session.createSender(que);
					tm = session.createTextMessage(message.getBody().get().toString());
					send.send(tm);
										
					// Close the connection
					send.close();
					conn1.stop();
					session.close();
					conn1.close();
				}
			}
		} 
		catch (Throwable t) {
			t.printStackTrace();
		} /* try and catch */

	  return message; 
        		
	} /* method */
	
} /* class */
