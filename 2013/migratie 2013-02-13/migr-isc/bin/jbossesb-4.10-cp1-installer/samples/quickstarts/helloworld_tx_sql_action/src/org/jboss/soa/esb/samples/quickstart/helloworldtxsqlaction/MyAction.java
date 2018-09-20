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
package org.jboss.soa.esb.samples.quickstart.helloworldtxsqlaction;

import java.util.Map;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class MyAction extends AbstractActionLifecycle
{
    private boolean fail ;
    
  protected ConfigTree	_config;
	  
  public MyAction(ConfigTree config) { _config = config; } 
  
  public Message noOperation(Message message) { return message; } 

  @SuppressWarnings("unchecked")
 public Message displayMessage(Message message) throws Exception{	
		  boolean problem = false;
		  
		  logHeader();
		  Map<String,Object> rowData =(Map)message.getBody().get();
		 StringBuffer results = new StringBuffer();	 
		 for (Map.Entry<String,Object> curr : rowData.entrySet()) {
			  results.append("column "+curr.getKey()+" = <" + curr.getValue()+">");
			  
			  if ("data 22".equals(curr.getValue()))
			  {
				  System.out.println("DATA READ: "+curr.getValue());
				  fail = !fail ;
				  problem = fail ;
              }
		  }
		 System.out.println(results.toString());
		  logFooter();
		  
		  // Set message properties and message body so that SystemPrintln will display message 
		  message.getProperties().setProperty("jbesbfilename", "helloworldTxSQlAction.log");
		  message.getBody().add(results.toString());
		 
		  if (problem)
			  System.out.println("Will rollback transaction. Expect to see record again!");
		  else
			  System.out.println("Will commit transaction. Will not see record again!");
		  
		  if (!problem)
			  return message;
		  else
		  {
			  System.out.println("BAD READ ON DATA!");
		  
			  throw new RuntimeException();
		  }
	}
  
   // This makes it easier to read on the console
   private void logHeader() {
	   System.out.println("\n&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
   }
   private void logFooter() {
	   System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
   }  
	
}