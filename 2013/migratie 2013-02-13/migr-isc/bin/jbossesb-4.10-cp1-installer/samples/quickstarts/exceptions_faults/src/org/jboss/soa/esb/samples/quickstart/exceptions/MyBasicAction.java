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
package org.jboss.soa.esb.samples.quickstart.exceptions;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.samples.quickstart.exceptions.MyBusinessException;

public class MyBasicAction extends AbstractActionLifecycle
{
    
  protected ConfigTree	_config;
	  
  public MyBasicAction(ConfigTree config) { _config = config; } 
  
  public Message process(Message message) {
	  System.out.println("The default method called");
	  return message;	 
  }
  
  public Message displayMessage(Message message) throws ActionProcessingException {		
		  String content =  (String) message.getBody().get();
		  message.getBody().add(content + " |BASIC_ACTION| ");
		  String newContent =  (String) message.getBody().get();
		  logHeader();
		  System.out.println("MyBasicAction Body: " + newContent);
		  logFooter();
		  return message;         	
	}
   
   public Message causesException(Message message) throws ActionProcessingException {
   	  System.out.println("About to cause an exception");
   		throw new ActionProcessingException("BAD STUFF HAPPENED");
   }
   
   public Message causesMyException(Message message) throws ActionProcessingException {
   	  System.out.println("About to cause an exception");
   	  try {
   	  	// some logic that bubbles up MyBusinessException
   	  	throw new MyBusinessException("Business Logic Violation");
   	  } catch (MyBusinessException mbe) {
   	  	System.out.println(mbe.getMessage());
   	  	throw new ActionProcessingException(mbe);
     	}
     	// 		return message;  // unreachable due to the way this demo code is setup
   }
   
   // This makes it easier to read on the console
   private void logHeader() {
	   System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
   }
   private void logFooter() {
	   System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
   }
    	
}