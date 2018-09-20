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
package org.jboss.soa.esb.samples.quickstart.funcbr.test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.QueueSender;
import javax.jms.ObjectMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Properties;

public class SendJMSMessage {
    QueueConnection conn;
    QueueSession session;
    Queue que;
    
    
    public void setupConnection(String queueName) throws JMSException, NamingException
    {
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
        ObjectMessage tm = session.createObjectMessage(msg);        
        tm.setStringProperty("jbesbfilename", "FunCBRTest.log");
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
    	SendJMSMessage sm = new SendJMSMessage();
    	sm.setupConnection(args[0]);
    	String fileContent = sm.readAsciiFile("SampleOrder.xml");
    	System.out.println("---------------------------------------------");
    	System.out.println(fileContent);
    	System.out.println("---------------------------------------------");
    	sm.sendAMessage(fileContent); 
    	sm.stop();
    	
    }
    
}
