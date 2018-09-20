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
package org.jboss.soa.esb.samples.quickstart.simplecbr.test;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class ReceiveJMSMessage {
   QueueConnection conn = null;    
   QueueSession receiverSession = null;    
   Queue receiverQueue = null;     
   QueueReceiver queueReceiver = null;
   InitialContext iniCtx = null;
   QueueConnectionFactory qcf = null;
   String receiveQueueName = "queue/quickstart_simplecbr_response"; 
    private boolean initialised ;
   
   public ReceiveJMSMessage() {
       
   }
   public void receiveOne() {
        if (!initialised) {
            initialise() ;
        }
        
        if (receiverQueue != null) {
           try {
               TextMessage msg = (TextMessage) queueReceiver.receive();
               if (msg != null) {
              System.out.println("*********************************************************");
              System.out.println(msg.getText());
              System.out.println("*********************************************************");
               }
           } catch (final Exception ex) {
               cleanup() ;
               System.out.println(ex.getMessage()) ;
           }
        }
        
        if (!initialised) {
            System.out.println("Pausing before reinitialising") ;
            try {
                Thread.sleep(5000) ;
            } catch (final InterruptedException ie) {
                // do nothing
            }
        }
   }
    
    private void initialise()
    {
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
           initialised = true ;
           System.out.println("Initialised") ;
        } catch (final Exception ex) {
            cleanup() ;
        }
    }
    
    public void cleanup() {
        System.out.println("Closing connections");
        queueReceiver = null ;
        receiverSession = null ;
        receiverQueue = null ;
        if (receiverSession!=null) {
            try {
                receiverSession.close();
            } catch (final Exception ex) {
                // Do nothing ;
            }
        }
        if (conn!=null) {
            try {
                conn.close() ;
            } catch (final Exception ex) {
                // Do nothing ;
            }
        }
        conn = null ;
        qcf = null ;
        iniCtx = null ;
        initialised = false ;
        System.out.println("Closing completed");
    }
    
   public static void main(String[] args) {
          final ReceiveJMSMessage receiver = new ReceiveJMSMessage();
          
          if ((args.length > 0) && (args[0] != null)) {
              receiver.receiveQueueName = args[0];
              System.out.println("Receiving on: " + receiver.receiveQueueName );                 
           Runtime.getRuntime().addShutdownHook(new Thread() {
               public void run() {
                   receiver.cleanup() ;
               }
           }) ;
           while (true) { // loop until I'm killed
               receiver.receiveOne();
               try {
                   Thread.sleep(500);
               } catch (InterruptedException e) {break;}
           } 
          
       } else {
           System.out.println("Usage <queue-name>");
       }
   }
}
