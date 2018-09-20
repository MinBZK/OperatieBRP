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
package org.jboss.soa.esb.samples.quickstart.wiretap.test;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import javax.jms.MessageListener;
import javax.jms.Message;

public class ReceiveJMSMessage {
           QueueConnection conn = null;    
           QueueSession receiverSession = null;    
           Queue receiverQueue = null;     
           QueueReceiver queueReceiver = null;
           InitialContext iniCtx = null;
           QueueConnectionFactory qcf = null;
           String receiveQueueName = "queue/quickstart_wiretap_response"; 

           // Ref:  http://docs.jboss.org/jbossas/jboss4guide/r5/html/ch6.chapt.html#d0e12917
           public static class ExListener implements MessageListener
           {
              public void onMessage(Message msg)
              {
                  TextMessage tm = (TextMessage) msg;
                  try {
                      System.out.println("onMessage, recv text=" + tm.getText());
                  } catch(Throwable t) {
                    t.printStackTrace();
                  }
              }
           }
           
           public ReceiveJMSMessage() {
                   
           }
           public void receiveOne() {
                   try {
                   if (iniCtx == null) iniCtx = new InitialContext();
                  if (qcf == null) qcf = (QueueConnectionFactory) iniCtx.lookup("ConnectionFactory");
                  if (conn == null) {
                      conn = qcf.createQueueConnection();
                      conn.start();
                  }    
                  
                  receiverQueue = (Queue) iniCtx.lookup(receiveQueueName);
                  receiverSession = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
                  queueReceiver = receiverSession.createReceiver(receiverQueue);              
                  TextMessage msg = (TextMessage) queueReceiver.receive(2000);
                 
                  // Ref:  http://docs.jboss.org/jbossas/jboss4guide/r5/html/ch6.chapt.html#d0e12917
                  queueReceiver.setMessageListener(new ExListener());

                   } catch (Exception e) {
                           System.out.println(e);
                   }
           }

           public static void main(String[] args) {
                      ReceiveJMSMessage receiver = new ReceiveJMSMessage();
                      
                      if(args[0] != null) {
                          receiver.receiveQueueName = args[0];
                          System.out.println("Receiving on: " + receiver.receiveQueueName );                     
                      }                   
                      while (true) { // loop until I'm killed
                          receiver.receiveOne();
                      }
           }
}
