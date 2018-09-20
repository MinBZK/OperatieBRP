/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, JBoss Inc., and individual contributors as indicated
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
package org.jboss.soa.esb.samples.quickstart.hibernateaction;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.ListenerTagNames;
import org.jboss.soa.esb.message.Message;

/**
 * MyAction displays the messages that the Hibernate Event Listener sends.     The Order is 
 * taken from the body of the message and then displayed to the console.
 * 
 * @author <a href="mailto:tcunning@redhat.com">tcunning@redhat.com</a>
 * @since Version 4.2
 */
public class MyAction extends AbstractActionLifecycle
{
	protected ConfigTree _config;
	  
	public MyAction(ConfigTree config) { _config = config; } 
  
	public Message noOperation(Message message) { return message; } 

	/**
	 * Display the Order in the body of the message
	 * @param message message
	 * @return message
	 * @throws Exception
	 */
	public Message displayMessage(Message message) {		
		logHeader();
		Order o = (Order) message.getBody().get();
		System.out.println(o.toString());
		logFooter();
		return message;         	
	}
  
	/**
	 * Display a header log line.
	 */
	private void logHeader() {
		System.out.println("\n&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
	}
	
	/**
	 * Display a footer log line.
	 */
	private void logFooter() {
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
	}
}
