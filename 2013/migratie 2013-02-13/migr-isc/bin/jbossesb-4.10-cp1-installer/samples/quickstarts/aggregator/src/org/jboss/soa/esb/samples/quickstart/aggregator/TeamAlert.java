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
package org.jboss.soa.esb.samples.quickstart.aggregator;

import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class TeamAlert {
	
	  protected Message  _message;
	  protected ConfigTree	_config;
	  
	  public TeamAlert (ConfigTree config) { _config = config; } 
	  	  
	  public Message sendResponseBlue(Message message) {
		  try {
			   logHeader();
			   System.out.println(message.getBody().get());
			   System.out.println("Blue Blue Blue");
			   logFooter();
			   ReturnJMSMessage.sendMessage(message,"quickstart_Fun_CBR_Blue_Alert");
		   } catch (Exception e) {
			   logHeader();
			   System.out.println(e.getMessage());
			   logFooter();
		   }
		   return message;
	  }
	  
	  public Message sendResponseRed(Message message) {
		  try {
			   logHeader();
			   System.out.println(message.getBody().get());
			   System.out.println("Red Red Red");
			   logFooter();
			   ReturnJMSMessage.sendMessage(message,"quickstart_Fun_CBR_Red_Alert");
		   } catch (Exception e) {
			   logHeader();
			   System.out.println(e.getMessage());
			   logFooter();
		   }
		   return message;
	  }

	  public Message sendResponseGreen(Message message) {
		  try {
			   logHeader();
			   System.out.println(message.getBody().get());
			   System.out.println("Green Green Green");
			   logFooter();
			   ReturnJMSMessage.sendMessage(message,"quickstart_Fun_CBR_Green_Alert");
		   } catch (Exception e) {
			   logHeader();
			   System.out.println(e.getMessage());
			   logFooter();
		   }
		   return message;
	  }
	  
	  private void logHeader() {
		  System.out.println("\n&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
	  }
	  private void logFooter() {
		  System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
	  }
	
}
