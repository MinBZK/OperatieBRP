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
package org.jboss.soa.esb.samples.quickstarts.bpm_orchestration3.esb_actions;


import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.apache.log4j.Logger;

public class SetupKey extends AbstractActionLifecycle {
	
	protected ConfigTree	_config;
	private Logger logger = Logger.getLogger(SetupKey.class);
	

	public Message process(Message message) throws ActionProcessingException {		
		String data = (String) message.getProperties().getProperty("token");
		logger.info("--------------------------------");
		logger.info("businessKey: " + data);
		
		message.getBody().add("businessKey",data);
		
		if (message.getBody() != null && message.getBody().get() != null) {			
			logger.info("Body: " + message.getBody().get());
		} else {
			logger.info("Body Contents are NULL");
		}
		logger.info("--------------------------------");
		message.getBody().add("contentsAsString",message.getBody().get());
		return message;         	
	}
	 	  
  public SetupKey(ConfigTree config) { _config = config; } 
  
}	