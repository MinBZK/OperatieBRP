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
package org.jboss.soa.esb.samples.quickstart.recipientList.test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.QueueSender;
import javax.jms.TextMessage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SendCBRJMSMessage {
    QueueConnection conn;
    QueueSession session;
    Queue que;
    
    
    public void setupConnection() throws JMSException, NamingException
    {
    	InitialContext iniCtx = new InitialContext();
    	Object tmp = iniCtx.lookup("ConnectionFactory");
    	QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
    	conn = qcf.createQueueConnection();
    	que = (Queue) iniCtx.lookup("queue/qsrecipientlist_cbr_Request");   //quickstart_simple_cbr_Request");
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
    
    public void sendAMessage(String msg) throws JMSException {
    	
        QueueSender send = session.createSender(que);    
        TextMessage tm = session.createTextMessage(msg);  
        send.send(tm);        
        send.close();
    }
    
    public String readAsciiFile(String fileName) throws IOException {
		  FileReader fr = null;
		  char[] thechars = null;

		  try {
			  File thefile = new File( fileName );
			  fr = new FileReader( thefile );
			  int size = (int) thefile.length();
			  thechars = new char[size];
		
			  int count, index = 0;
		
			  // 	read in the bytes from the input stream
			  while( ( count = fr.read( thechars, index, size ) ) > 0 ) {
				  size -= count;
				  index += count;
			  }
			} catch(Exception e) {	
				System.out.println(e);
			}
			finally {
				if( fr != null )
		        fr.close();
			}
			return new String(thechars);

    } // readAsciiFile
    
    public static void main(String args[]) throws Exception
    {        	    	

    	SendCBRJMSMessage sm = new SendCBRJMSMessage();
    	sm.setupConnection();
    	String fileContent = sm.readAsciiFile("SampleOrder.xml");
    	System.out.println("---------------------------------------------");
    	System.out.println(fileContent);
    	System.out.println("---------------------------------------------");
    	sm.sendAMessage(fileContent); 
    	sm.stop();
    	
    }
    
}
