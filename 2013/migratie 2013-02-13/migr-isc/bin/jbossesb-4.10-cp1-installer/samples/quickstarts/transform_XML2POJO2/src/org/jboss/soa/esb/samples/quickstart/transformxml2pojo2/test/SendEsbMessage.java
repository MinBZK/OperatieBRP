/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.soa.esb.samples.quickstart.transformxml2pojo2.test;

import org.jboss.soa.esb.message.Message; 
import org.jboss.soa.esb.message.format.MessageFactory; 
import org.jboss.soa.esb.message.format.MessageType; 
import org.jboss.soa.esb.client.ServiceInvoker;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Standalone class with to send ESB messages to a 'known' [category,name].
 * <p/> arg0 - file name 
 * 
 */
public class SendEsbMessage 
{
    public static void main(String args[]) throws Exception
    {
//      Setting the ConnectionFactory such that it will use scout
   
    	System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");

    	String fileName = "SampleOrder.xml";
    	if (args[0] != null && !args[0].equals("")) 
    		 fileName =  args[0];
    		 
    	System.out.println("File Name: " + fileName);
    	
    	String fileContent = readAsciiFile(fileName);
    	System.out.println("File Content:\n " + fileContent + "\n");
    	
    	ServiceInvoker invoker;
    	Message requestMessage;
    	Message replyMessage = null;    	
    	invoker = new ServiceInvoker("MyTransformationServicesESB", "MyXML2POJO2TransformationService");
    	requestMessage = MessageFactory.getInstance().getMessage(MessageType.JBOSS_XML);
    	requestMessage.getBody().add(fileContent); 
    	    	
    	// now delivery it and wait for a response
    	replyMessage = invoker.deliverSync(requestMessage, 20000);
    	
    	System.out.println("Reply Message: " + replyMessage.getBody().get());
    }
    
    public static String readAsciiFile(String fileName) throws IOException {
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
    
}