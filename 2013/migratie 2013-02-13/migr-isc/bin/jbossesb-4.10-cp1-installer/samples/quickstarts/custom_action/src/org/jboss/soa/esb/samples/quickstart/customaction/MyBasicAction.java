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
package org.jboss.soa.esb.samples.quickstart.customaction;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.actions.ActionProcessingException;

public class MyBasicAction extends AbstractActionLifecycle
{
    
  protected ConfigTree	_config;
	  
  public MyBasicAction(ConfigTree config) { _config = config; } 
  
  public Message noOperation(Message message) { return message; } 

  public Message process(Message message) {
	  System.out.println("The default method called");
	  return message;	 
  }
  
  public Message displayMessage(Message message) throws ActionProcessingException {		
		  String content = (String) message.getBody().get();
		  logHeader();
		  System.out.println("Body: " + content);
		  logFooter();
		  message.getBody().add(new String(content + " " + new java.util.Date()));
		  return message;         	
	}
   
   public void exceptionHandler(Message message, Throwable exception) {
	   logHeader();
	   System.out.println("!ERROR!");
	   System.out.println(exception.getMessage());
	   System.out.println("For Message: ");
	   System.out.println(message.getBody().get());
	   logFooter();
   }
	

   // This makes it easier to read on the console
   private void logHeader() {
	   System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
   }
   private void logFooter() {
	   System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
   }
    
	
}