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
package org.jboss.soa.esb.samples.quickstart.spring_helloworld.test;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.soa.esb.actions.StoreMessageToFile;

/**
 * Send a JMS message that the ESB will listen for.
 * 
 * @author <a href="mailto:james.williams@redhat.com">James Williams</a>.
 * 
 */
public class SendJMSMessage
{
   private QueueConnection conn;
   private QueueSession session;
   private Queue que;

   public void setupConnection() throws JMSException, NamingException
   {
      InitialContext iniCtx = new InitialContext();
      Object tmp = iniCtx.lookup("ConnectionFactory");
      QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
      conn = qcf.createQueueConnection();
      que = (Queue) iniCtx.lookup("queue/quickstart_spring_helloworld_Request");
      session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
      conn.start();
      System.out.println("Connection Started");
   }

   public void stop() throws JMSException
   {
      conn.stop();
      session.close();
      conn.close();
   }

   public void sendAMessage(String msg) throws JMSException
   {

      QueueSender send = session.createSender(que);
      ObjectMessage tm = session.createObjectMessage(msg);
	  tm.setStringProperty(StoreMessageToFile.PROPERTY_JBESB_FILENAME, "SpringHelloWorldTest.log");
      send.send(tm);
      send.close();
   }

   public static void main(String args[]) throws Exception
   {
      SendJMSMessage sm = new SendJMSMessage();
      sm.setupConnection();
      sm.sendAMessage(args[0]);
      sm.stop();
   }
}
