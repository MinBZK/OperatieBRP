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

public class JustAnotherAction extends AbstractActionLifecycle
{
    
  public Message displayMessage(Message message) throws ActionProcessingException {		
		  String content =  (String) message.getBody().get();
		  message.getBody().add(content + " |JUST_ANOTHER_ACTION| ");;
		  String newContent = (String) message.getBody().get();		  
		  logHeader();
		  System.out.println("JustAnotherAction Body: " + newContent);
		  logFooter();
		  
		  return message;         	
  }
   
   public void exceptionHandler(Message message, Throwable exception) {
	  String content =  (String) message.getBody().get();
	  logHeader();
	  System.out.println("JustAnotherAction exceptionHandler:\n " + content);
	  logFooter();
   }   
   // This makes it easier to read on the console
   private void logHeader() {
	   System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
   }
   private void logFooter() {
	   System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
   }

   protected ConfigTree	_config;
	  
   public JustAnotherAction(ConfigTree config) { _config = config; } 
   
   

}